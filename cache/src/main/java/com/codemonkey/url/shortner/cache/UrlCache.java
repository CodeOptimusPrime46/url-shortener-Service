package com.codemonkey.url.shortner.cache;

import reactor.core.publisher.Mono;

public interface UrlCache {

  Mono<Void> save(String shortKey, String originalUrl);

  Mono<String> get(String shortKey);
}
