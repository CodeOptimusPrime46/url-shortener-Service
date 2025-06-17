package com.codemonkey.url.shortner.service.impl;


import com.codemonkey.url.shortner.cache.UrlCache;
import com.codemonkey.url.shortner.core.dto.UrlMappingCore;
import com.codemonkey.url.shortner.core.repository.UrlShortnerRepository;
import com.codemonkey.url.shortner.dto.UrlRequest;
import com.codemonkey.url.shortner.dto.UrlResponse;
import com.codemonkey.url.shortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

  private final UrlCache urlCache;

  private final UrlShortnerRepository repository;

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
