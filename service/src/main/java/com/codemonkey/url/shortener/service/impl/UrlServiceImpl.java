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

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

  private final UrlCache urlCache;

  private final UrlShortenerRepository repository;

  private final ShortCodeGenerator shortCodeGenerator;

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

  @Override
  public Mono<String> getLongUrlFromCache(String shortUrl) {
    return urlCache.get(shortUrl);
  }
}
