package com.gulaev;

import com.gulaev.service.analysis.RateCountAnalysisService;
import com.gulaev.service.analysis.SalesAnalysisService;
import com.gulaev.service.SendMessageService;
import com.gulaev.service.analysis.StarRateAnalysisService;

public class Main {

  public static void main(String[] args) {
    SendMessageService sendMessageService = new SendMessageService();
    SalesAnalysisService salesAnalysisService = new SalesAnalysisService();
    RateCountAnalysisService rateCountAnalysisService = new RateCountAnalysisService();
    StarRateAnalysisService starRateAnalysisService = new StarRateAnalysisService();
    salesAnalysisService.analyzeSalesChanges();
    starRateAnalysisService.analyzeStarRate();
//    rateCountAnalysisService.analyzeRateCountChanges();
  }
}