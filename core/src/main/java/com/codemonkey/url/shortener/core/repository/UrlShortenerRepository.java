package com.codemonkey.url.shortener.core.repository;

import com.codemonkey.url.shortener.core.dto.UrlMappingCore;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UrlShortenerRepository extends ReactiveMongoRepository<UrlMappingCore, String> {

  Mono<UrlMappingCore> findByLongUrl(String longUrl);

}
