package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RateCountAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public RateCountAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public Map<Boolean, String> analyzeRateChangeForProduct(AmazonProduct currentProduct) {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date dateBeforeCurrent = productRepository.getMaxDateBeforeInputDate(currentDate);
//    List<AmazonProduct> productsByPreviousDate = productRepository.getProductsByDate(
//        dateBeforeCurrent);
    HashMap<Boolean, String> answer = new HashMap<>();

    Optional<AmazonProduct> previousDateProduct = productRepository.
        findProductByDate(dateBeforeCurrent, currentProduct);

//        productsByPreviousDate.stream()
//        .filter(p -> p.getAsin().equals(currentProduct.getAsin())
//            && p.getTitle().equals(currentProduct.getTitle())
//            && p.getShopName().equals(currentProduct.getShopName())).findFirst();

    if (!previousDateProduct.isPresent()) {
      System.out.println("No comparison product found.");
      answer.put(false, "No comparison product found.");
      return answer;
    }

    Integer currentRateCount = parseRateCount(currentProduct.getRateCount());
    Integer previousRateCount = parseRateCount(previousDateProduct.get().getRateCount());
    int rateCountDifference = currentRateCount - previousRateCount;

    if (Math.abs(rateCountDifference) >= 20) {
      String messageFormat = "Notice: Rate count has changed by %d\n";
      String formattedMessage = String.format(messageFormat,
          rateCountDifference);
      answer.put(true, formattedMessage);
      return answer;
    } else {
      String stringAnswer = "Rate count is normal";
      System.out.println(stringAnswer);
      answer.put(false, stringAnswer);
      return answer;
    }
  }

  private Integer parseRateCount(String rateCount) {
    String numericPart = rateCount.replaceAll("[^\\d,]", "").replace(",", "");
    return Integer.parseInt(numericPart);
  }
}