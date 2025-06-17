package com.codemonkey.url.shortener.service.shortcode.generator;

import reactor.core.publisher.Mono;

public interface ShortCodeGenerator {

  Mono<String> generateShortUrl();
}
