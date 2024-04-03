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

public class StarRateAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public StarRateAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }


  public Map<Boolean, String> analyzeStarRateForProduct(AmazonProduct currentProduct) {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date beforeDate = productRepository.getMaxDateBeforeInputDate(currentDate);
    Optional<AmazonProduct> previousDateProductOpt = productRepository.findProductByDate(beforeDate, currentProduct);
//        productRepository.getProductsByDate(beforeDate).stream()
//        .filter(p -> p.getTitle().equals(currentProduct.getTitle()) && p.getShopName().equals(currentProduct.getShopName()))
//        .findFirst();

    HashMap<Boolean, String> answer = new HashMap<>();

    if (previousDateProductOpt.isPresent()) {
      AmazonProduct previousDateProduct = previousDateProductOpt.get();
      if (!currentProduct.getStarRating().equals(previousDateProduct.getStarRating())) {
        String message = String.format(
            "The star rating has changed: it was %s, now %s \n",
            previousDateProduct.getStarRating(), currentProduct.getStarRating());
        answer.put(true, message);
      } else {
        answer.put(false, "Star rating is unchanged");
      }
    } else {
      answer.put(false, "Previous date product not found for");
    }

    return answer;
  }


}
