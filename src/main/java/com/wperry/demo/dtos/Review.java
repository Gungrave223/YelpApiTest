package com.wperry.demo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

  private String id;
  private String url;
  private String text;
  private int rating;
  private YelpUser user;
  @JsonProperty("time_created")
  private String timeCreated;
}
