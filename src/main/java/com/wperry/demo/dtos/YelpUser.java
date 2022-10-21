package com.wperry.demo.dtos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.vision.v1.Likelihood;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpUser {
  private String id;
  @JsonProperty("image_url")
  private String imageUrl;
  @JsonProperty("profile_url")
  private String profileUrl;
  private String name;

  private List<Map<String, Likelihood>> likelihoods;

  @JsonIgnore
  public void setLikeliHoods(List<Map<String, Likelihood>> likelihoods) {
    this.likelihoods = likelihoods;
  }
}
