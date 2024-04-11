package com.gulaev;

import com.gulaev.service.analysis.ProductAnalizator;

public class Main {

  public static void main(String[] args) {
    ProductAnalizator productAnalizator = new ProductAnalizator();
    productAnalizator.startAnalysis();
  }
}