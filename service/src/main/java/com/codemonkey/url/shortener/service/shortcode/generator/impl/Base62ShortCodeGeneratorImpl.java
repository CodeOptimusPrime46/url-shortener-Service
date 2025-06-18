package com.codemonkey.url.shortener.service.shortcode.generator.impl;

import com.codemonkey.url.shortener.service.shortcode.generator.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Generates Base62-encoded short codes using a Redis-backed counter. Each call to {@link #generateShortUrl()} increments a Redis key and encodes the value in Base62.
 */
@Component
@RequiredArgsConstructor
public class Base62ShortCodeGeneratorImpl implements ShortCodeGenerator {

  private static final String COUNTER_KEY = "url:counter";
  private static final char[] BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  private final ReactiveStringRedisTemplate redisTemplate;

  /**
   * Generates a new short URL code by incrementing a Redis counter and encoding it in Base62.
   *
   * @return a {@link Mono} emitting the generated short code
   */
  @Override
  public Mono<String> generateShortUrl() {
    return redisTemplate.opsForValue()
        .increment(COUNTER_KEY)
        .map(this::encodeBase62);
  }

  /**
   * Encodes a given number into a Base62 string.
   *
   * @param num the number to encode
   * @return the Base62-encoded string
   */
  private String encodeBase62(long num) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
      sb.append(BASE62[(int) (num % 62)]);
      num /= 62;
    }
    return sb.reverse().toString();
  }
}