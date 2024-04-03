package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.Date;
import java.util.List;

public class ProductAnaizator {

  private RateCountAnalysisService rateCountAnalysisService;
  private SalesAnalysisService salesAnalysisService;
  private StarRateAnalysisService starRateAnalysisService;
  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public ProductAnaizator() {
    this.rateCountAnalysisService = new RateCountAnalysisService();
    this.salesAnalysisService = new SalesAnalysisService();
    this.starRateAnalysisService = new StarRateAnalysisService();
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void startAnalysis() {
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    for (AmazonProduct currentProduct: currentProducts) {
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true)) {
        StringBuilder message = new StringBuilder();
        if (salesAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
        }
        if (rateCountAnalysisResult.containsKey(true)) {
          message.append(rateCountAnalysisResult.get(true));
        }
        if (starRatingAnalysisResult.containsKey(true)) {
          message.append(starRatingAnalysisResult.get(true));
        }
        message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
        message.append(String.format("https://www.%s/dp/%s \n", currentProduct.getShopName().toLowerCase(),
            currentProduct.getAsin()));
        sendMessageService.sendMessage(message.toString());
        System.out.println(message);
      }
    }
  }
}
