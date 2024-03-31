package com.gulaev.service.analysis;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.service.SendMessageService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SalesAnalysisService {

  private AmazonProductRepository productRepository;
  private SendMessageService sendMessageService;

  public SalesAnalysisService() {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.sendMessageService = new SendMessageService();
  }

  public void analyzeSalesChanges() {
    Date currentDate = productRepository.getMostRecentUploadDate();
    List<AmazonProduct> currentProducts = productRepository.getProductsByDate(currentDate);

    for (AmazonProduct currentProduct : currentProducts) {
      Date currentUploadedDate = productRepository.getMostRecentUploadDate();
      List<AmazonProduct> productsByPreviousDates = new ArrayList<>();
      Double currentProductUnitsTotal = Double.parseDouble(currentProduct.getUnitsTotal());
      Double averageUnitsTotalByPreviousDates;
      for (int i = 1; i <= 3; i++) {
        currentUploadedDate = productRepository.getMaxDateBeforeInputDate(currentUploadedDate);
        Optional<AmazonProduct> productByDate = productRepository.findProductByDate(
            currentUploadedDate,
            currentProduct);
        productByDate.ifPresent(productsByPreviousDates::add);
      }
      averageUnitsTotalByPreviousDates = productsByPreviousDates.stream()
          .mapToInt(p -> Integer.parseInt(p.getUnitsTotal())).average().orElse(Double.NaN);

      double percentageChange = ((averageUnitsTotalByPreviousDates -
          currentProductUnitsTotal) / currentProductUnitsTotal) * 100;

      if (averageUnitsTotalByPreviousDates < currentProductUnitsTotal * 0.85) {
        String messageFormat = "Attention: Yesterday's sales were %.2f%% below the average of the last three days!%nBy product: %s \nhttps://www.%s/dp/%s ";
        String formattedMessage = String.format(messageFormat,
            -percentageChange, currentProduct.getTitle(),
            currentProduct.getShopName().toLowerCase(), currentProduct.getAsin());
        System.out.println(formattedMessage);
        sendMessageService.sendMessage(formattedMessage);

      } else {
        System.out.println("Продажи за вчера в норме.");
      }
    }
  }
}
