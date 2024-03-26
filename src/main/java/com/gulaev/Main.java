package com.gulaev;

import com.gulaev.service.analysis.RateCountAnalysisService;
import com.gulaev.service.analysis.SalesAnalysisService;
import com.gulaev.service.SendMessageService;

public class Main {

  public static void main(String[] args) {
    SendMessageService sendMessageService = new SendMessageService();
    SalesAnalysisService salesAnalysisService = new SalesAnalysisService();
    RateCountAnalysisService rateCountAnalysisService = new RateCountAnalysisService();
    salesAnalysisService.analyzeSalesChanges();
//    rateCountAnalysisService.analyzeRateCountChanges();
  }
}