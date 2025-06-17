package com.codemonkey.url.shortner.service;

import com.codemonkey.url.shortner.dto.UrlRequest;
import com.codemonkey.url.shortner.dto.UrlResponse;
import reactor.core.publisher.Mono;

public interface UrlService {

  Mono<UrlResponse> shortenUrl(UrlRequest request);

  Mono<String> getLongUrlFromCache(String shortUrl);
}
