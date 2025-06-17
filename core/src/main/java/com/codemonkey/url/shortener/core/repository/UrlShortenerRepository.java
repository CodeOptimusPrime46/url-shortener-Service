package com.codemonkey.url.shortener.core.repository;

import com.codemonkey.url.shortener.core.dto.UrlMappingCore;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends ReactiveMongoRepository<UrlMappingCore, String> {

}
