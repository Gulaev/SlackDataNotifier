package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.Date;
import java.util.List;

public class RateCountAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public RateCountAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeRateCountChanges() {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date dateBeforeCurrent = productRepository.getMaxDateBeforeInputDate(currentDate);
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> productsByPreviousDate = productRepository.getProductsByDate(
        dateBeforeCurrent);

    for (AmazonProduct currentProduct : currentProducts) {
      for (AmazonProduct previousDateProduct : productsByPreviousDate) {
        if (currentProduct.getTitle().equals(previousDateProduct.getTitle()) &&
            currentProduct.getShopName().equals(previousDateProduct.getShopName()) &&
            currentProduct.getAsin().equals(previousDateProduct.getAsin())) {
          Integer currentRateCount = parseRateCount(currentProduct.getRateCount());
          Integer previousRateCount = parseRateCount(previousDateProduct.getRateCount());

          int rateCountDifference = currentRateCount - previousRateCount;

          if (Math.abs(rateCountDifference) >= 20) {
            String messageFormat = "Notice: Rate count has changed by %d for product: %s \nhttps://www.%s/dp/%s";
            String formattedMessage = String.format(messageFormat,
                rateCountDifference, currentProduct.getTitle(),
                currentProduct.getShopName().toLowerCase(), currentProduct.getAsin());
            System.out.println(formattedMessage);
            sendMessageService.sendMessage(formattedMessage);
          } else {
            System.out.println("Rate count is normal");
          }
        }
      }
    }
  }

  private Integer parseRateCount(String rateCount) {
    String numericPart = rateCount.replaceAll("[^\\d,]", "").replace(",", "");
    return Integer.parseInt(numericPart);
  }
}