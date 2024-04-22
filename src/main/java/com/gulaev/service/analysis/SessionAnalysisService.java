package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SessionAnalysisService {

  private AmazonProductRepository productRepository;

  public SessionAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
  }

  public Map<Boolean, String> analyzeSessionChangesForProduct(AmazonProduct product) {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date currentDateBefore = productRepository.getMaxDateBeforeInputDate(currentDate);
    Date dayBeforeAfter = productRepository.getMaxDateBeforeInputDate(currentDateBefore);
    Optional<AmazonProduct> currentProductOptional =
        productRepository.findProductByDate(currentDateBefore, product);
    if (currentProductOptional.isEmpty()) {
      return Collections.singletonMap(false, "No product data available for the specified date.");
    }
    AmazonProduct currentProduct = currentProductOptional.get();

    HashMap<Boolean, String> answer = new HashMap<>();
    List<Double> sessionByPreviousDate = new ArrayList<>();
    Double currentProductSession = Double.parseDouble(currentProduct.getSession());
    Double averageSessionsTotalByPreviousDates;
    Date currentUploadedDate = dayBeforeAfter;

    // Collect units sold on the previous dates
    for (int i = 1; i <= 3; i++) {
      currentUploadedDate = productRepository.getMaxDateBeforeInputDate(currentUploadedDate);
      Optional<AmazonProduct> previousDateProduct = productRepository.findProductByDate(
          currentUploadedDate, currentProduct);

      previousDateProduct.ifPresent(p -> {
        String session = p.getSession();
        if (session != null) {
          sessionByPreviousDate.add(Double.parseDouble(session));
        }
      });
    }

      // Calculate the average units sold in previous dates
      averageSessionsTotalByPreviousDates = sessionByPreviousDate.stream()
          .mapToDouble(Double::doubleValue).average().orElse(Double.NaN);

      // Calculate the percentage change between current and average previous sales
      double percentageChange = ((currentProductSession - averageSessionsTotalByPreviousDates)
          / averageSessionsTotalByPreviousDates) * 100;

      // Determine if the change is significant (more than 15% increase or decrease)
      if (Math.abs(percentageChange) >= 15) {
        String messageFormat;
        if (percentageChange >= 15) {
          messageFormat = "📈 Session increased by %.2f%% compared to the average! Sessions: %s For Date: %s\n";
        } else {
          messageFormat = "📉 Session decreased by %.2f%% compared to the average! Sessions: %s For Date: %s\n";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedMessage = String.format(messageFormat, Math.abs(percentageChange),
            currentProduct.getSession(), formatter.format(dayBeforeAfter));
        answer.put(true, formattedMessage);
      } else {
        String message = "Session changes are within normal range.";
        answer.put(false, message);
        return answer;
      }
      return answer;
    }
  }