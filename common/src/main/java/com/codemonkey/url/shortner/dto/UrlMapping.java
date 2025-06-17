package com.codemonkey.url.shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlMapping {

  private String longUrl;
  private String shortUrl;
}
