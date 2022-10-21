package com.wperry.demo.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//Ideally would this class should not exist
public class Businesses {

  private List<Business> businesses;
}
