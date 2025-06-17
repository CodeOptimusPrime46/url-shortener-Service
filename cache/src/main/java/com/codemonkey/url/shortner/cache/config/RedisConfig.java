//package com.codemonkey.url.shortner.cache.config;
//
//import com.codemonkey.url.shortner.dto.UrlMapping;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//  @Bean
//  public ReactiveRedisTemplate<String, UrlMapping> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//    RedisSerializer<String> keySerializer = new StringRedisSerializer();
//    Jackson2JsonRedisSerializer<UrlMapping> valueSerializer = new Jackson2JsonRedisSerializer<>(UrlMapping.class);
//    RedisSerializationContextBuilder<String, UrlMapping> builder = RedisSerializationContext.newSerializationContext(keySerializer);
//    RedisSerializationContext<String, UrlMapping> context = builder
//        .value(valueSerializer)
//        .build();
//    return new ReactiveRedisTemplate<>(factory, context);
//  }
//}
