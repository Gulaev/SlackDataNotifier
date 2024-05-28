package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RankAnalysisService {
  private AmazonProductRepository productRepository;

  public RankAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
  }


  public Map<Boolean, String> analysisRank(AmazonProduct currentProduct) {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date beforeDate = productRepository.getMaxDateBeforeInputDate(currentDate);
    Optional<AmazonProduct> previousDateProductOpt = productRepository.findProductByDate(beforeDate, currentProduct);
//        productRepository.getProductsByDate(beforeDate).stream()
//        .filter(p -> p.getTitle().equals(currentProduct.getTitle()) && p.getShopName().equals(currentProduct.getShopName()))
//        .findFirst();

    HashMap<Boolean, String> answer = new HashMap<>();

    if (previousDateProductOpt.isPresent()) {
      String message = "";
      AmazonProduct previousDateProduct = previousDateProductOpt.get();
      if (currentProduct.getRank() == null || previousDateProduct.getRank() == null) {
        answer.put(false, "Rank is null");
        return answer;
      }
      if (!currentProduct.getRank().equals(previousDateProduct.getRank())) {
        if (currentProduct.getRank().equals("No Rank")) {
          message = String.format("\uD83D\uDEA8 Rank lost \n");
          answer.put(true, message);
        } else {
          message = String.format("⚡ Product %s \n", currentProduct.getRank());
          answer.put(true, message);
        }
      } else {
        answer.put(false, "Rank is unchanged");
      }
    } else {
      answer.put(false, "Previous date product not found for");
    }

    return answer;
  }


}
