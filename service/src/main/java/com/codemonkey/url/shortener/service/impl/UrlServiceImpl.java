package com.codemonkey.url.shortener.service.impl;


import com.codemonkey.url.shortener.cache.UrlCache;
import com.codemonkey.url.shortener.core.dto.UrlMappingCore;
import com.codemonkey.url.shortener.core.repository.UrlShortenerRepository;
import com.codemonkey.url.shortener.dto.UrlRequest;
import com.codemonkey.url.shortener.dto.UrlResponse;
import com.codemonkey.url.shortener.service.UrlService;
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

  private static String generateShortKey(UrlRequest request) {
    // TODO: Implement a more robust short key generation logic
    return "short" + request.getLongUrl();
  }

  @Override
  public Mono<UrlResponse> shortenUrl(UrlRequest request) {
    String shortUrl = UrlServiceImpl.generateShortKey(request);
    UrlResponse urlResponse = new UrlResponse(shortUrl);
    return repository.findById(shortUrl)
        .flatMap(existing -> {
          log.info("Found existing URL mapping for {} with shortUrl: {}", existing, shortUrl);
          return Mono.just(urlResponse);
        })
        .switchIfEmpty(
            repository.insert(
                UrlMappingCore.builder()
                    .shortUrl(shortUrl)
                    .longUrl(request.getLongUrl())
                    .build()
            ).flatMap(savedCore ->
                urlCache.save(shortUrl, request.getLongUrl())
                    .thenReturn(urlResponse)
            )
        );
  }

  @Override
  public Mono<String> getLongUrlFromCache(String shortUrl) {
    return urlCache.get(shortUrl);
  }
}
