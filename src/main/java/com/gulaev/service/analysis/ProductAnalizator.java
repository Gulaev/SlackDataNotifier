package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.Date;
import java.util.List;

public class ProductAnalizator {

  private RateCountAnalysisService rateCountAnalysisService;
  private SalesAnalysisService salesAnalysisService;
  private StarRateAnalysisService starRateAnalysisService;
  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public ProductAnalizator() {
    this.rateCountAnalysisService = new RateCountAnalysisService();
    this.salesAnalysisService = new SalesAnalysisService();
    this.starRateAnalysisService = new StarRateAnalysisService();
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void startAnalysis() {
    StringBuilder message = new StringBuilder();
    message = analysisAmazonUs(message);
    message = analysisAmazonUK(message);
    message = analysisZoroms(message);
    message = analysisKivals(message);
    sendMessageService.sendMessage(message.toString());
    System.out.println(message);
  }

  private StringBuilder analysisAmazonUs(StringBuilder message) {
    message.append("\n\n╰┈➤ Mighty-X US\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X US")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true)) {
        boolean ifSendMessage = false;
        if (salesAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (rateCountAnalysisResult.containsKey(true)) {
          message.append(rateCountAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (starRatingAnalysisResult.containsKey(true)) {
          message.append(starRatingAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (ifSendMessage) {
          message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
          message.append(
              String.format("https://www.%s/dp/%s \n", currentProduct.getShopName().toLowerCase(),
                  currentProduct.getAsin()));
          message.append(currentProduct.getSheetLink()).append(" \n");
        }
      }
    }
    return message;
  }

  private StringBuilder analysisAmazonUK(StringBuilder message) {
    message.append("\n\n╰┈➤ Mighty-X UK\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X UK")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true)) {
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
        message.append(
            String.format("https://www.%s/dp/%s \n", currentProduct.getShopName().toLowerCase(),
                currentProduct.getAsin()));
        message.append(currentProduct.getSheetLink()).append(" \n");
      }
    }
    return message;
  }

  private StringBuilder analysisZoroms(StringBuilder message) {
    message.append("\n\n╰┈➤ ZOROM'S\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("ZOROM'S")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true)) {
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
        message.append(
            String.format("https://www.%s/dp/%s \n", currentProduct.getShopName().toLowerCase(),
                currentProduct.getAsin()));
        message.append(currentProduct.getSheetLink()).append(" \n");
      }
    }
    return message;
  }


  private StringBuilder analysisKivals(StringBuilder message) {
    message.append("\n\n╰┈➤ Kivals\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Kivals")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true)) {
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
        message.append(
            String.format("https://www.%s/dp/%s \n", currentProduct.getShopName().toLowerCase(),
                currentProduct.getAsin()));
        message.append(currentProduct.getSheetLink()).append(" \n");
      }
    }
    return message;
  }

}
