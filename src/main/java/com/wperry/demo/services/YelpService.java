package com.wperry.demo.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wperry.demo.configurations.YelpConfiguration;
import com.wperry.demo.dtos.Businesses;
import com.wperry.demo.dtos.Review;
import com.wperry.demo.dtos.Reviews;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

@Service
@AllArgsConstructor
public class YelpService {

  private YelpConfiguration configuration;

  public List<Review> getBusinessReviews(String location, String name) {
    val businessId = this.getLocationId(location, name);
    if (null == businessId) {
      return Collections.EMPTY_LIST;
    }
    return this.getReview(businessId);
  }

  private String encodeValue(String value) throws UnsupportedEncodingException {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
  }

  @SneakyThrows
  private String getLocationId(String location, String name) {
    val url = MessageFormat.format("{0}businesses/search?location={1}&term={2}",
        this.configuration.getBaseUrl(),
        this.encodeValue(location),
        this.encodeValue(name));

    val headers = new HttpHeaders();
    headers.setBearerAuth(this.configuration.getKey());
    val response = new RestTemplate().exchange(
        RequestEntity.get(new URI(url)).headers(headers).build(),
        Businesses.class);

    val businessResponse = response.getBody();
    if (null != businessResponse) {
      val businesses = businessResponse.getBusinesses();
      if (businesses.size() > 1) {
        val business = businesses.stream()
            .filter(b -> b.getName().equalsIgnoreCase(name) || b.getAlias().equalsIgnoreCase(name))
            .findFirst();
        if (business.isPresent()) {
          return business.get().getId();
        }
      }
    }

    return null;
  }

  @SneakyThrows
  private List<Review> getReview(String id) {
    val url = MessageFormat.format("{0}businesses/{1}/reviews", this.configuration.getBaseUrl(), id);

    val headers = new HttpHeaders();
    headers.setBearerAuth(this.configuration.getKey());
    headers.setContentType(MediaType.APPLICATION_JSON);
    val response = new RestTemplate().exchange(
        RequestEntity.get(new URI(url)).headers(headers).build(),
        Reviews.class);

    val reviewResponse = response.getBody();
    if (null != reviewResponse) {
      return reviewResponse.getReviews();
    }

    return Collections.EMPTY_LIST;
  }
}
