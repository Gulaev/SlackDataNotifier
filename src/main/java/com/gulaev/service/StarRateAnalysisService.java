package com.gulaev.service;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.util.Calendar;
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
    Date endDate = productRepository.getMostRecentUploadDate();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date startDate = calendar.getTime();
    List<AmazonProduct> productsByCurrentDate = productRepository.getProductsByDate(endDate);
    List<AmazonProduct> productsByPreviousDate = productRepository.getProductsByDate(startDate);

    for (AmazonProduct product: productsByCurrentDate) {
      for (AmazonProduct previousDateProduct: productsByPreviousDate) {
        if (product.getTitle().equals(previousDateProduct.getTitle()) &&
            product.getShopName().equals(previousDateProduct.getShopName())) {
          if (!product.getStarRating().equals(previousDateProduct.getStarRating())) {
            String message = String.format("Star rating for product by link https://www.%s/dp/%s is different; it was %s, now it's %s",
                product.getShopName().toLowerCase(), product.getAsin(),
                previousDateProduct.getStarRating(), product.getStarRating());
            sendMessageService.sendMessage(message);
          }
        }
      }
    }
  }

}
