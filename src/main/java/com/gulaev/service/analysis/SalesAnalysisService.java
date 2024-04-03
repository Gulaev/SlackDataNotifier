package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SalesAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public SalesAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public Map<Boolean, String> analyzeSalesChangeForProduct(AmazonProduct currentProduct) {
    Date currentDate = productRepository.getMostRecentUploadDate();
    HashMap<Boolean, String> answer = new HashMap<>();
    List<Double> unitsTotalByPreviousDates = new ArrayList<>();
    Double currentProductUnitsTotal = Double.parseDouble(currentProduct.getUnitsTotal());
    Double averageUnitsTotalByPreviousDates;
    Date currentUploadedDate = currentDate;

    for (int i = 1; i <= 3; i++) {
      currentUploadedDate = productRepository.getMaxDateBeforeInputDate(currentUploadedDate);
//      List<AmazonProduct> productsByPreviousDate = productRepository.getProductsByDate(currentUploadedDate);
      Optional<AmazonProduct> previousDateProduct = productRepository.findProductByDate(currentUploadedDate, currentProduct);
//          productsByPreviousDate.stream()
//              .filter(p -> p.getAsin().equals(currentProduct.getAsin())
//                  && p.getTitle().equals(currentProduct.getTitle())
//                  && p.getShopName().equals(currentProduct.getShopName())).findFirst();

      previousDateProduct.ifPresent(product ->
          unitsTotalByPreviousDates.add(Double.parseDouble(product.getUnitsTotal())));
    }

    averageUnitsTotalByPreviousDates = unitsTotalByPreviousDates.stream()
        .mapToDouble(Double::doubleValue).average().orElse(Double.NaN);

    double percentageChange = ((currentProductUnitsTotal - averageUnitsTotalByPreviousDates)
        / averageUnitsTotalByPreviousDates) * 100;

    if (currentProductUnitsTotal < averageUnitsTotalByPreviousDates * 0.85) {
      String messageFormat = "Attention: Yesterday's sales were %.2f%% below the average of the last three days!\n";
      String formattedMessage = String.format(messageFormat, -percentageChange);
      answer.put(true, formattedMessage);
    } else {
      String message = "Yesterday's sales were normal.";
      System.out.println(message);
      answer.put(false, message);
    }

    return answer;
  }
}
