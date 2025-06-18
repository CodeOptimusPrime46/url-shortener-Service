package com.codemonkey.url.shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {

  @Schema(description = "The long URL to be shortened", example = "https://www.example.com/some/long/url")
  @NonNull
  @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http or https")
  private String longUrl;
}
