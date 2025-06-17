package com.codemonkey.url.shortener.service;

import com.codemonkey.url.shortener.dto.UrlRequest;
import com.codemonkey.url.shortener.dto.UrlResponse;
import reactor.core.publisher.Mono;

public interface UrlService {

  Mono<UrlResponse> shortenUrl(UrlRequest request);

  Mono<String> getLongUrlFromCache(String shortUrl);
}
