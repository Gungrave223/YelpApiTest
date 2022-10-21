package com.wperry.demo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Coordinates {
  private double latitude;
  private double longitude;
}
