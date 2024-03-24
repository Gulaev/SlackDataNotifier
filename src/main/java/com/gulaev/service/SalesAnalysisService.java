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

public class SalesAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public SalesAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeSalesChanges() {
    Date endDate = productRepository.getMostRecentUploadDate();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    calendar.add(Calendar.DAY_OF_MONTH, -3);
    Date startDate = calendar.getTime();

    List<AmazonProduct> products = productRepository.findProductsByDateRange(startDate, endDate);
    if (products == null || products.isEmpty()) { // Assuming at least 50 products per day
      System.out.println("Недостаточно данных для анализа.");
      return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Map<String, List<AmazonProduct>> productsByDate = products.stream()
        .collect(Collectors.groupingBy(product -> sdf.format(product.getUploadedOn())));

    if (productsByDate.size() < 3) {
      System.out.println("Недостаточно данных за последние 4 дня.");
      return;
    }

    Map<String, Double> totalSalesByDate = new HashMap<>();
    productsByDate.forEach((date, dailyProducts) -> {
      double dailyTotal = dailyProducts.stream()
          .mapToDouble(p -> Double.parseDouble(p.getUnitsTotal()))
          .sum();
      totalSalesByDate.put(date, dailyTotal);
    });

    List<String> sortedDates = new ArrayList<>(totalSalesByDate.keySet());
    Collections.sort(sortedDates);
    double averageSales = sortedDates.stream()
        .limit(sortedDates.size() - 1)
        .mapToDouble(totalSalesByDate::get)
        .average()
        .orElse(Double.NaN);

    double yesterdaySales = totalSalesByDate.get(sortedDates.get(sortedDates.size() - 1));
    double percentageChange = ((yesterdaySales - averageSales) / averageSales) * 100;

    if (yesterdaySales < averageSales * 0.85) {
      String messageFormat = "Attention: Yesterday's sales were %.2f%% below the average of the last three days!%n";
      String formattedMessage = String.format(messageFormat, -percentageChange);
      sendMessageService.sendMessage(formattedMessage);
    } else {
      System.out.println("Продажи за вчера в норме.");
    }
  }
}
