package com.codemonkey.url.shortener.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {

  @NonNull
  @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http or https")
  private String longUrl;
}
