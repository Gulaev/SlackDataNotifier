package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    analysisZoroms();
    analysisKivals();
    analysisAmazonUK();
  }

  private void analysisAmazonUs() {
    sendMessageService.sendMessage("\n\n╰┈➤ Mighty-X US\n");
    System.out.println("\n\n╰┈➤ Mighty-X US\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X US")).toList();
    Map<String, String> result = new HashMap<>();
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
          message.append(String.format("Product Title: <%s|%s> \n",currentProduct.getSheetLink(),
              currentProduct.getTitle()));
          result.put(currentProduct.getAsin(), message.toString());
//          sendMessageService.sendMessage(message.toString());
//          System.out.println(message);
        }
      }
    }
    sortByAsinAndSendMessage("US", result);
  }

  private void analysisAmazonUK() {
    System.out.println("\n\n╰┈➤ Mighty-X UK\n");
    sendMessageService.sendMessage("\n\n╰┈➤ Mighty-X UK\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> ukProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Mighty-X UK")).toList();
    Map<String, String> result = new HashMap<>();
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
          message.append(String.format("Product Title: <%s|%s> \n",currentProduct.getSheetLink(),
              currentProduct.getTitle()));
//          sendMessageService.sendMessage(message.toString());
//          System.out.println(message);
        }
      }
    }
    sortByAsinAndSendMessage("UK", result);
  }

  private void analysisZoroms() {
    System.out.println("\n\n╰┈➤ ZOROM'S\n");
    sendMessageService.sendMessage("\n\n╰┈➤ ZOROM'S\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("ZOROM'S")).toList();
    Map<String, String> result = new HashMap<>();
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
        message.append(String.format("Product Title: <%s|%s> \n",currentProduct.getSheetLink(),
            currentProduct.getTitle()));
        result.put(currentProduct.getAsin(), message.toString());
//        sendMessageService.sendMessage(message.toString());
//        System.out.println(message);
      }
    }
    sortByAsinAndSendMessage("ZOROM'S", result);
  }


  private void analysisKivals() {
    System.out.println("\n\n╰┈➤ Kivals\n");
    sendMessageService.sendMessage("\n\n╰┈➤ Kivals\n");
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = currentProducts.stream()
        .filter(a -> a.getShopTitle().equals("Kivals")).toList();
    Map<String, String> result = new HashMap<>();
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
        message.append(String.format("Product Title: <%s|%s> \n",currentProduct.getSheetLink(),
            currentProduct.getTitle()));
        result.put(currentProduct.getAsin(), message.toString());
//        sendMessageService.sendMessage(message.toString());
//        System.out.println(message);
      }
    }
    sortByAsinAndSendMessage("Kivals", result);
  }

  private void sortByAsinAndSendMessage(String shopName, Map<String, String> result) {
    List<String> asinOrder;
    switch (shopName) {
      case "US":
        asinOrder = Arrays.asList(
            "B078Q32FXQ", "B078Q7WJSG", "B08J4G25JZ", "B08J3ZRTLQ", "B099S5CF9W", "B08HZ94M16",
            "B06XQ8B8Y8", "B07HHL3W1M", "B09R83Z3JQ", "B0BPMMSQ13", "B08HZ5SMF4", "B07TW7STZY",
            "B01HQWDQ1E", "B071H9RYJX", "B07DXCGCHF", "B07BJ9K2WT", "B06XQZ12NH", "B07JLMKJGX",
            "B09R8355XC", "B09R851CL3", "B08J3VBG3Y", "B099S6CZLT", "B07R2D594Z", "B07XJPY5PZ",
            "B076ZVLMYR", "B07R17G8PR", "B07BJB629Z", "B07BJ9VK6L", "B0BN8K48JH", "B0BNJ9FW5B"
        );
        break;
      case "ZOROM'S":
        asinOrder = Arrays.asList(
            "B086692GCS", "B08X1XZC14", "B09R4WV42F", "B08X1YFHYJ", "B08X17XN1T", "B09R4WL9WP",
            "B0895NXVPQ", "B08X15TN31"
        );
        break;
      case "Kivals":
        asinOrder = Arrays.asList(
            "B0C1T5RNCH", "B0C2D7JYQT", "B0C1ZZ766Q", "B0C2D8MQS3"
        );
        break;
      case "UK":
        asinOrder = Arrays.asList(
            "B078Q32FXQ", "B078Q7WJSG", "B08J4G25JZ", "B08J3ZRTLQ", "B06XQ8B8Y8", "B08HZ94M16",
            "B07HHL3W1M", "B07R2D594Z"
        );
        break;
      default:
        asinOrder = new ArrayList<>();
    }

    if (!asinOrder.isEmpty()) {
      Comparator<String> byAsinOrder = Comparator.comparingInt(asinOrder::indexOf);
      Map<String, String> sortedResults = result.entrySet().stream()
          .sorted(Map.Entry.comparingByKey(byAsinOrder))
          .collect(Collectors.toMap(
              Map.Entry::getKey,
              Map.Entry::getValue,
              (oldValue, newValue) -> oldValue, LinkedHashMap::new));

      sortedResults.forEach((asin, msg) -> {
        sendMessageService.sendMessage(msg);
        System.out.println(asin);
        System.out.println(msg);
      });
    }
  }
}
