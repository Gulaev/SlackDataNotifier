package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BestSellerRankAnalysisService {

  private static AmazonProductRepository productRepository = new AmazonProductRepositoryImpl();

  public BestSellerRankAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
  }

  public Map<Boolean, String> bestSellerRankAnalysis(AmazonProduct currentProduct) {
    HashMap<Boolean, String> answer = new HashMap<>();
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date dateBeforeCurrent = productRepository.getMaxDateBeforeInputDate(currentDate);
    Optional<AmazonProduct> previousDateProduct = productRepository.findProductByDate(dateBeforeCurrent, currentProduct);

    if (!previousDateProduct.isPresent() || previousDateProduct.get().getBestSellerRank() == null
        || currentProduct.getBestSellerRank() == null) {
      System.out.println("No comparison product found.");
      answer.put(false, "No comparison product found.");
      return answer;
    }

    Map<String, Integer> currentCategories = parseCategories(currentProduct.getBestSellerRank());
    Map<String, Integer> previousDateCategories = parseCategories(previousDateProduct.get().getBestSellerRank());

    // Analysis of rank changes
    for (Map.Entry<String, Integer> entry : currentCategories.entrySet()) {
      String category = entry.getKey();
      Integer currentRank = entry.getValue();
      Integer previousRank = previousDateCategories.getOrDefault(category, null);

      if (previousRank != null) {
        int rankChange = previousRank - currentRank;
        if (Math.abs(rankChange) >= 5) {
          if (rankChange > 0) {
            answer.put(true, "Sharp decrease \n");
            return answer;
          } else {
            answer.put(true, "Sharp increase \n");
            return answer;
          }
        }
      }
    }

    if (answer.isEmpty()) {
      answer.put(false, "No significant change");
    }

    return answer;
  }

  public static Map<String, Integer> parseCategories(String text) {
    Map<String, Integer> categories = new HashMap<>();
    String[] lines = text.split("/n");
    for (String line: lines) {
      String[] numberAndTitle = line.split(" in ");
      String number = numberAndTitle[0];
      String title = numberAndTitle[1];
      if (number.contains("#")) {
        number = number.replace("#", "");
      }
      if (number.contains(",")) {
        number = number.replace(",", "");
      }
      if (number.contains(" ")) {
        number = number.replace(" ", "");
      }
      if (title.contains("(")) {
        int indexStart = title.indexOf("(");
        title = title.replace(title.substring(indexStart, title.length()), "");
      }
      int finalNumber = Integer.parseInt(number);
      categories.put(title, finalNumber);
    }
    return categories;
  }
}
