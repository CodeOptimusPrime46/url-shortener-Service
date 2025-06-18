package com.codemonkey.url.shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponse {

  @Schema(description = "The original long URL", example = "https://www.example.com/some/long/url")
  private String shortUrl;
}
