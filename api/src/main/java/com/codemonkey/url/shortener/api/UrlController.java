package com.codemonkey.url.shortener.api;

import com.codemonkey.url.shortener.dto.UrlMapping;
import com.codemonkey.url.shortener.dto.UrlRequest;
import com.codemonkey.url.shortener.dto.UrlResponse;
import com.codemonkey.url.shortener.dto.exception.NoUrlFoundException;
import com.codemonkey.url.shortener.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller for URL shortening and retrieval operations. Provides endpoints to shorten URLs, redirect to original URLs, and fetch URL mappings. Handles requests in a reactive
 * manner using Project Reactor.
 */
@Slf4j
@RestController
@RequestMapping("${spring.api.version:}/")
@Tag(name = "URL Shortener", description = "API for URL shortening and retrieval")
public class UrlController {

  private final UrlService urlService;

  @Autowired // can be avoided since we have a single constructor
  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @Operation(summary = "Shorten a URL", description = "Creates a shortened version of the provided long URL")
  @PostMapping("/shorten")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ResponseEntity<UrlResponse>> shorten(@Valid @RequestBody UrlRequest urlRequest) {
    log.debug("Received request to shorten URL: {}", urlRequest.getLongUrl());
    return urlService.shortenUrl(urlRequest)
        .map(res -> ResponseEntity
            .status(HttpStatus.CREATED)
            .body(res))
        .defaultIfEmpty(ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build());
  }

  @Operation(summary = "Retrieve long URL and redirects", description = "Retrieves the original long URL for a given shortened URL and redirects to it")
  @GetMapping(value = "/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<Void>> redirectToLongUrl(@PathVariable String shortUrl) {
    return urlService.getLongUrlFromCache(shortUrl)
        .switchIfEmpty(Mono.error(new NoUrlFoundException("Short URL not found: " + shortUrl)))
        .map(url -> {
              log.info("Short URL {}, Original URL {}", shortUrl, url);
              HttpHeaders headers = new HttpHeaders();
              headers.setLocation(URI.create(url));
              return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        );
  }

  @Operation(summary = "Retrieve long URL", description = "Retrieves the original long URL for a given shortened URL")
  @GetMapping(value = "/get/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Mono<ResponseEntity<UrlMapping>> getLongUrl(@PathVariable String shortUrl) {
    return urlService.getLongUrlFromCache(shortUrl)
        .switchIfEmpty(Mono.error(new NoUrlFoundException("Short URL not found: " + shortUrl)))
        .map(url -> {
              log.info("Short URL {}, Original URL {}", shortUrl, url);
              UrlMapping urlMapping = new UrlMapping(shortUrl, url);
              return ResponseEntity.ok().body(urlMapping);
            }
        );
  }
}
