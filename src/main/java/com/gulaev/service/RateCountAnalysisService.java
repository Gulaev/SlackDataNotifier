package com.gulaev.service;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RateCountAnalysisService {


  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public RateCountAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeRateCountChanges() {
    Date endDate = productRepository.getMostRecentUploadDate();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    calendar.add(Calendar.DAY_OF_MONTH, -3);
    Date startDate = calendar.getTime();

    List<AmazonProduct> products = productRepository.findProductsByDateRange(startDate, endDate);
//    if (products == null || products.isEmpty()) {
//      System.out.println("Недостаточно данных для анализа.");
//      return;
//    }
//
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Map<String, List<AmazonProduct>> productsByDate = products.stream()
        .collect(Collectors.groupingBy(product -> sdf.format(product.getUploadedOn())));

//    if (productsByDate.size() < 3) {
//      System.out.println("Недостаточно данных за последние 3 дня.");
//      return;
//    }

    Map<String, Integer> rateCountByDate = new HashMap<>();
    productsByDate.forEach((date, dailyProducts) -> {
      Integer dailyTotalRateCount = dailyProducts.stream()
          .mapToInt(p -> parseRateCount(p.getRateCount()))
          .sum();
      rateCountByDate.put(date, dailyTotalRateCount);
    });

    List<String> sortedDates = new ArrayList<>(rateCountByDate.keySet());
    Collections.sort(sortedDates);
    double averageRateCount = sortedDates.stream()
        .limit(sortedDates.size() - 1)
        .mapToInt(rateCountByDate::get)
        .average()
        .orElse(Double.NaN);

    double yesterdayRateCount = rateCountByDate.get(sortedDates.get(sortedDates.size() - 1));
    double percentageChange = ((yesterdayRateCount - averageRateCount) / averageRateCount) * 100;

    if (Math.abs(percentageChange) > 20) {
      String sign = percentageChange > 0 ? "+" : "";
      String messageFormat = "Attention: The rate count change for yesterday is %s%.2f%%, which is beyond the +/-20%% threshold of the average of the last three days!%n";
      String formattedMessage = String.format(messageFormat, sign, percentageChange);
      sendMessageService.sendMessage(formattedMessage);
    } else {
      System.out.println("The rate count change for yesterday is within normal limits.");
    }
  }

  private Integer parseRateCount(String rateCount) {
    String numericPart = rateCount.replaceAll("[^\\d,]", "").replace(",", "");
    return Integer.parseInt(numericPart);
  }

}
