package com.codemonkey.url.shortener.core.repository;

import com.codemonkey.url.shortener.core.dto.UrlMappingCore;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Reactive MongoDB repository for URL mappings. Provides methods to find and persist {@link UrlMappingCore} entities.
 */
@Repository
public interface UrlShortenerRepository extends ReactiveMongoRepository<UrlMappingCore, String> {

  /**
   * Finds a URL mapping by its original long URL.
   *
   * @param longUrl the original long URL
   * @return a {@link Mono} emitting the found {@link UrlMappingCore}, or empty if not found
   */
  Mono<UrlMappingCore> findByLongUrl(String longUrl);

}
