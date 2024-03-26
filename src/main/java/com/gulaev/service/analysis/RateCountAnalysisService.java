package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RateCountAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public RateCountAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeRateCountChanges() {
    Date currentDate = productRepository.getMostRecentUploadDate();

    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);

    for (AmazonProduct currentProduct : currentProducts) {
      Date currentUploadedDate = productRepository.getMostRecentUploadDate();
      List<AmazonProduct> productsByPreviousDates = new ArrayList<>();
      Integer currentRateCount = parseRateCount(currentProduct.getRateCount());
      Double averageRateCountPreviousDates;
      for (int i = 1; i <= 3; i++) {
        currentUploadedDate = productRepository.getMaxDateBeforeInputDate(currentUploadedDate);
        Optional<AmazonProduct> productByDate = productRepository
            .findProductByDate(currentUploadedDate, currentProduct);
        productByDate.ifPresent(productsByPreviousDates::add);
      }
      averageRateCountPreviousDates = productsByPreviousDates.stream()
          .mapToInt(p -> parseRateCount(p.getRateCount())).average().orElse(Double.NaN);

      double percentageChange = ((averageRateCountPreviousDates -
          currentRateCount) / currentRateCount) * 100;

      if (Math.abs(percentageChange) > 20) {
        String sign = percentageChange > 0 ? "+" : "";
        String messageFormat = "Attention: The rate count change for yesterday is %s%.2f%%, which is beyond the +/-20%% threshold of the average of the last three days!%n by product https://www.%s/dp/%s";
        String formattedMessage = String.format(messageFormat, sign, percentageChange,
            currentProduct.getShopName().toLowerCase(), currentProduct.getAsin());
        System.out.println(formattedMessage);
        sendMessageService.sendMessage(formattedMessage);
      } else {
        System.out.println("The rate count change for yesterday is within normal limits.");
      }
    }
  }

  private Integer parseRateCount(String rateCount) {
    String numericPart = rateCount.replaceAll("[^\\d,]", "").replace(",", "");
    return Integer.parseInt(numericPart);
  }
}