package com.codemonkey.url.shortener.cache.impl;

import com.codemonkey.url.shortener.cache.UrlCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RedisUrlCacheImpl implements UrlCache {

  private final ReactiveRedisTemplate<String, String> redisTemplate;
//  private static final String KEY_PREFIX = "shorturl:";

  @Override
  public Mono<Void> save(String shortKey, String originalUrl) {
    return redisTemplate.opsForValue().set(shortKey, originalUrl).then();
  }

  @Override
  public Mono<String> get(String shortKey) {
    return redisTemplate.opsForValue().get(shortKey);
  }
}
