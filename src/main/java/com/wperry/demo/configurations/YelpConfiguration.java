package com.wperry.demo.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "yelp")
public class YelpConfiguration {
  private String baseUrl;
  private String key;
}
