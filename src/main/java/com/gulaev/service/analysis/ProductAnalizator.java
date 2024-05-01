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
  private SessionAnalysisService sessionAnalysisService;
  private SendMessageService sendMessageService;
  private RankAnalysisService rankAnalysisService;
  private BestSellerRankAnalysisService sellerRankAnalysisService;

  public ProductAnalizator() {
    this.rateCountAnalysisService = new RateCountAnalysisService();
    this.salesAnalysisService = new SalesAnalysisService();
    this.starRateAnalysisService = new StarRateAnalysisService();
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
    this.sessionAnalysisService = new SessionAnalysisService();
    this.rankAnalysisService = new RankAnalysisService();
    this.sellerRankAnalysisService = new BestSellerRankAnalysisService();
  }

  public void startAnalysis() {
    analysisAmazonUs();
    analysisAmazonUK();
    analysisZoroms();
    analysisKivals();
  }

  private void analysisAmazonUs() {
    sendMessageService.sendMessage("\n\n╰┈➤ Mighty-X US\n");
    System.out.println("\n\n╰┈➤ Mighty-X US\n");
//    message.append("\n\n╰┈➤ Mighty-X US\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X US")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      StringBuilder message = new StringBuilder();
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      var sessionAnalysisServiceResult =
          sessionAnalysisService.analyzeSessionChangesForProduct(currentProduct);
      var rankAnalysisResult =
          rankAnalysisService.analysisRank(currentProduct);
      var sellerRankAnalysisResult =
          sellerRankAnalysisService.bestSellerRankAnalysis(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true) || sessionAnalysisServiceResult.containsKey(true)
          || rankAnalysisResult.containsKey(true) || sellerRankAnalysisResult.containsKey(true)) {
        boolean ifSendMessage = false;
        if (salesAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (sessionAnalysisServiceResult.containsKey(true)) {
          message.append(sessionAnalysisServiceResult.get(true));
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
        if (rankAnalysisResult.containsKey(true)) {
          message.append(rankAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (sellerRankAnalysisResult.containsKey(true)) {
          message.append(sellerRankAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (ifSendMessage) {
          message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
          message.append(currentProduct.getSheetLink()).append(" \n");
          sendMessageService.sendMessage(message.toString());
          System.out.println(message);
        }
      }
    }
  }

  private void analysisAmazonUK() {
//    message.append("\n\n╰┈➤ Mighty-X UK\n");
    System.out.println("\n\n╰┈➤ Mighty-X UK\n");
    sendMessageService.sendMessage("\n\n╰┈➤ Mighty-X UK\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> ukProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X UK")).toList();
    for (AmazonProduct currentProduct : ukProducts) {
      StringBuilder message = new StringBuilder();
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      var sessionAnalysisServiceResult =
          sessionAnalysisService.analyzeSessionChangesForProduct(currentProduct);
      var rankAnalysisResult =
          rankAnalysisService.analysisRank(currentProduct);
      var sellerRankAnalysisResult =
          sellerRankAnalysisService.bestSellerRankAnalysis(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true) || sessionAnalysisServiceResult.containsKey(true)
          || rankAnalysisResult.containsKey(true) || sellerRankAnalysisResult.containsKey(true)) {
        boolean ifSendMessage = false;
        if (salesAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (sessionAnalysisServiceResult.containsKey(true)) {
          message.append(sessionAnalysisServiceResult.get(true));
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
        if (rankAnalysisResult.containsKey(true)) {
          message.append(rankAnalysisResult.get(true));
          ifSendMessage = true;
        }
        if (sellerRankAnalysisResult.containsKey(true)) {
          message.append(sellerRankAnalysisResult.get(true));
        }
        if (ifSendMessage) {
          message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
          message.append(currentProduct.getSheetLink()).append(" \n");
          sendMessageService.sendMessage(message.toString());
          System.out.println(message);
        }
      }
    }
  }

  private void analysisZoroms() {
//    message.append("\n\n╰┈➤ ZOROM'S\n");
    System.out.println("\n\n╰┈➤ ZOROM'S\n");
    sendMessageService.sendMessage("\n\n╰┈➤ ZOROM'S\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("ZOROM'S")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      StringBuilder message = new StringBuilder();
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      var rankAnalysisResult =
          rankAnalysisService.analysisRank(currentProduct);
      var sellerRankAnalysisResult =
          sellerRankAnalysisService.bestSellerRankAnalysis(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true) || rankAnalysisResult.containsKey(true)) {
        if (salesAnalysisResult.containsKey(true) || sellerRankAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
        }
        if (rateCountAnalysisResult.containsKey(true)) {
          message.append(rateCountAnalysisResult.get(true));
        }
        if (starRatingAnalysisResult.containsKey(true)) {
          message.append(starRatingAnalysisResult.get(true));
        }
        if (rankAnalysisResult.containsKey(true)) {
          message.append(rankAnalysisResult.get(true));
        }
        if (sellerRankAnalysisResult.containsKey(true)) {
          message.append(sellerRankAnalysisResult.get(true));
        }
        message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
        message.append(currentProduct.getSheetLink()).append(" \n");
        sendMessageService.sendMessage(message.toString());
        System.out.println(message);
      }
    }
  }


  private void analysisKivals() {
//    message.append("\n\n╰┈➤ Kivals\n");
    System.out.println("\n\n╰┈➤ Kivals\n");
    sendMessageService.sendMessage("\n\n╰┈➤ Kivals\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Kivals")).toList();
    for (AmazonProduct currentProduct : usProducts) {
      StringBuilder message = new StringBuilder();
      var salesAnalysisResult =
          salesAnalysisService.analyzeSalesChangeForProduct(currentProduct);
      var rateCountAnalysisResult =
          rateCountAnalysisService.analyzeRateChangeForProduct(currentProduct);
      var starRatingAnalysisResult =
          starRateAnalysisService.analyzeStarRateForProduct(currentProduct);
      var rankAnalysisResult =
          rankAnalysisService.analysisRank(currentProduct);
      var sellerRankAnalysisResult =
          sellerRankAnalysisService.bestSellerRankAnalysis(currentProduct);
      if (salesAnalysisResult.containsKey(true) || rateCountAnalysisResult.containsKey(true) ||
          starRatingAnalysisResult.containsKey(true) || rankAnalysisResult.containsKey(true)) {
        if (salesAnalysisResult.containsKey(true) || sellerRankAnalysisResult.containsKey(true)) {
          message.append(salesAnalysisResult.get(true));
        }
        if (rateCountAnalysisResult.containsKey(true)) {
          message.append(rateCountAnalysisResult.get(true));
        }
        if (starRatingAnalysisResult.containsKey(true)) {
          message.append(starRatingAnalysisResult.get(true));
        }
        if (rankAnalysisResult.containsKey(true)) {
          message.append(rankAnalysisResult.get(true));
        }
        if (sellerRankAnalysisResult.containsKey(true)) {
          message.append(sellerRankAnalysisResult.get(true));
        }
        message.append(String.format("Product Title: %s \n", currentProduct.getTitle()));
        message.append(currentProduct.getSheetLink()).append(" \n");
        sendMessageService.sendMessage(message.toString());
        System.out.println(message);
      }
    }
  }
}
