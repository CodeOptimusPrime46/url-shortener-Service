package com.codemonkey.url.shortner.core.repository;

import com.codemonkey.url.shortner.core.dto.UrlMappingCore;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortnerRepository extends ReactiveMongoRepository<UrlMappingCore, String> {

}
