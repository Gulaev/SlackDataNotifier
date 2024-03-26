package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.Date;
import java.util.List;

public class StarRateAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public StarRateAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeStarRate() {
    Date currentDate = productRepository.getMostRecentUploadDate();
    Date beforeDate = productRepository.getMaxDateBeforeInputDate(currentDate);
    List<AmazonProduct> productsByCurrentDate = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> productsByPreviousDate = productRepository.getProductsByDate(beforeDate);

    for (AmazonProduct product : productsByCurrentDate) {
      for (AmazonProduct previousDateProduct : productsByPreviousDate) {
        if (product.getTitle().equals(previousDateProduct.getTitle()) &&
            product.getShopName().equals(previousDateProduct.getShopName())) {
          if (!product.getStarRating().equals(previousDateProduct.getStarRating())) {
            String message = String.format(
                "Star rating for product by link https://www.%s/dp/%s is different; it was %s, now it's %s",
                product.getShopName().toLowerCase(), product.getAsin(),
                previousDateProduct.getStarRating(), product.getStarRating());
            sendMessageService.sendMessage(message);
          }
        }
      }
    }
  }

}
