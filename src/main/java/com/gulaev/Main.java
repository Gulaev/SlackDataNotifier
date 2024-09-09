package com.gulaev;

import com.gulaev.service.analysis.ProductAnalizator;
import com.gulaev.service.google.SheetsUpdateProcessor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  public static void main(String[] args) {
    ProductAnalizator productAnalizator = new ProductAnalizator();
    String dateString = "2024.09.09";
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    try {
      date = formatter.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    SheetsUpdateProcessor updateProcessor = new SheetsUpdateProcessor(
        "IV", "!IT7", "IU", date);

    productAnalizator.startAnalysis();
    updateProcessor.process();

  }
}
