package com.codemonkey.url.shortener.cache;

import reactor.core.publisher.Mono;

public interface UrlCache {

  Mono<Void> save(String shortKey, String originalUrl);

  Mono<String> get(String shortKey);
}
