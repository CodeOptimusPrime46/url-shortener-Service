package com.codemonkey.url.shortener.service.impl;


import com.codemonkey.url.shortener.cache.UrlCache;
import com.codemonkey.url.shortener.core.dto.UrlMappingCore;
import com.codemonkey.url.shortener.core.repository.UrlShortenerRepository;
import com.codemonkey.url.shortener.dto.UrlRequest;
import com.codemonkey.url.shortener.dto.UrlResponse;
import com.codemonkey.url.shortener.service.UrlService;
import com.codemonkey.url.shortener.service.shortcode.generator.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service implementation for URL shortening and retrieval.
 * <p>
 * Handles creation of short URLs, checks for existing mappings, and interacts with cache and repository layers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

  private final UrlCache urlCache;

  private final UrlShortenerRepository repository;

  private final ShortCodeGenerator shortCodeGenerator;

  /**
   * Shortens a given long URL. If the URL already exists, return the existing short URL. Otherwise, generates a new short URL, saves the mapping, and caches it.
   *
   * @param request the URL request containing the long URL
   * @return a Mono emitting the response with the short URL
   */
  @Override
  public Mono<UrlResponse> shortenUrl(UrlRequest request) {
    return repository.findByLongUrl(request.getLongUrl())
        .flatMap(existing -> {
          log.info("Found existing URL mapping for {} with shortUrl: {}", request.getLongUrl(), existing.getShortUrl());
          return Mono.just(new UrlResponse(existing.getShortUrl()));
        }).switchIfEmpty(
            shortCodeGenerator.generateShortUrl()
                .flatMap(shortUrl -> {
                  UrlMappingCore core = UrlMappingCore.builder()
                      .shortUrl(shortUrl)
                      .longUrl(request.getLongUrl())
                      .build();
                  return repository.insert(core)
                      .flatMap(saved -> urlCache.save(shortUrl, request.getLongUrl())
                          .thenReturn(new UrlResponse(shortUrl)));
                })
        );
  }

  /**
   * Retrieves the long URL from the cache for a given short URL.
   *
   * @param shortUrl the short URL key
   * @return a Mono emitting the long URL if found
   */
  @Override
  public Mono<String> getLongUrlFromCache(String shortUrl) {
    return urlCache.get(shortUrl)
        .switchIfEmpty(repository.findById(shortUrl)
            .map(UrlMappingCore::getLongUrl));
  }
}
