package com.codemonkey.url.shortener.service.shortcode.generator.impl;

import com.codemonkey.url.shortener.service.shortcode.generator.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Base62ShortCodeGeneratorImpl implements ShortCodeGenerator {

  private static final String COUNTER_KEY = "url:counter";
  private static final char[] BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  private final ReactiveStringRedisTemplate redisTemplate;

  @Override
  public Mono<String> generateShortUrl() {
    return redisTemplate.opsForValue()
        .increment(COUNTER_KEY)
        .map(this::encodeBase62);
  }

  private String encodeBase62(long num) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
      sb.append(BASE62[(int) (num % 62)]);
      num /= 62;
    }
    return sb.reverse().toString();
  }
}
