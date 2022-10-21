package com.wperry.demo.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// Ideally would this class should not exists
public class Reviews {

  private List<Review> reviews;
}
