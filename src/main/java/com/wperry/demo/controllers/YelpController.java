package com.wperry.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wperry.demo.dtos.Review;
import com.wperry.demo.services.VisionService;
import com.wperry.demo.services.YelpService;

import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
public class YelpController {

  private YelpService service;
  private VisionService visionService;

  // Ideally I would have use spring-doc to generate some api documentation
  // Also I would make the path more reflective of the yelp api where location is
  // optional and coordinates depends on location
  @GetMapping(path = "{location}/{name}", produces = "application/json")
  public ResponseEntity<List<Review>> getYelpReviews(@PathVariable String location, @PathVariable String name) {
    List<Review> reviews = this.service.getBusinessReviews(location, name);

    if (reviews.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    reviews.forEach(review -> {
      val user = review.getUser();
      val likeliHood = this.visionService.getLikeliHoods(user.getImageUrl());
      user.setLikeliHoods(likeliHood);
    });

    return ResponseEntity.ok().body(reviews);
  }
}
