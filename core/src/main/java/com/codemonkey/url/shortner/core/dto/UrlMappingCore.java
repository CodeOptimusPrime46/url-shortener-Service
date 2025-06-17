package com.codemonkey.url.shortner.core.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("url_mapping")
public class UrlMappingCore {

  @Id
  private String shortUrl;
  private String longUrl;
}
