package com.codemonkey.url.shortener.cache.impl;

import com.codemonkey.url.shortener.cache.UrlCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Redis-based implementation of the UrlCache interface using a reactive Redis template. Provides methods to save and retrieve URL mappings in a non-blocking manner.
 */
@Component
@RequiredArgsConstructor
public class RedisUrlCacheImpl implements UrlCache {

  private final ReactiveRedisTemplate<String, String> redisTemplate;

  /**
   * Saves the mapping from a short key to the original URL in Redis.
   *
   * @param shortKey the shortened key
   * @param originalUrl the original URL to be mapped
   * @return a Mono that completes when the operation is done
   */
  @Override
  public Mono<Void> save(String shortKey, String originalUrl) {
    return redisTemplate.opsForValue().set(shortKey, originalUrl).then();
  }

  /**
   * Retrieves the original URL mapped to the given short key from Redis.
   *
   * @param shortKey the shortened key
   * @return a Mono emitting the original URL, or empty if not found
   */
  @Override
  public Mono<String> get(String shortKey) {
    return redisTemplate.opsForValue().get(shortKey);
  }
}
