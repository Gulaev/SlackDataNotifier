package com.gulaev.service.google;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.entity.SheetInfo;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SheetsUpdateProcessor {

  private Map<String, SheetInfo> migthyXUKMapping = SheetMapper.getMigthyXUKMapping();
  private Map<String, SheetInfo> migthyXUSMapping = SheetMapper.getMigthyXUSMapping();
  private Map<String, SheetInfo> zoromsMapping = SheetMapper.getZoromsMapping();
  private Map<String, SheetInfo> kivalsMapping = SheetMapper.getKivalsMapping();
  private AmazonProductRepository productRepository;
  private GoogleSheetsUpdater updater;
  private Date currentDate;

  public SheetsUpdateProcessor(String columnPrefix, String columnSession, String columnUnitsTotal, Date currentDate) {
    this.productRepository = new AmazonProductRepositoryImpl();
    this.updater = new GoogleSheetsUpdater(columnPrefix, columnSession, columnUnitsTotal);
    this.currentDate = currentDate;
  }

  public void process() {
    this.updateForUS(migthyXUSMapping);
    this.updateForUK(migthyXUKMapping);
    this.updateForKivals(kivalsMapping);
    this.updateForZoroms(zoromsMapping);
  }

  private void updateForUS(Map<String, SheetInfo> sheetMapping) {
    List<AmazonProduct> products = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> usProducts = products.stream()
        .filter(p -> p.getShopTitle().equals("Mighty-X US")).collect(Collectors.toList());
    try {
      updater.updateRateCount(usProducts, sheetMapping);
      updater.updateStarRating(usProducts, sheetMapping);
      updater.updateUnitsTotal(usProducts, sheetMapping);
      updater.updateSessions(getProductsByMagazineForSessions("Mighty-X US"), sheetMapping);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateForUK(Map<String, SheetInfo> sheetMapping) {
    List<AmazonProduct> products = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> ukProducts = products.stream()
        .filter(p -> p.getShopTitle().equals("Mighty-X UK")).collect(Collectors.toList());
    try {
      updater.updateStarRating(ukProducts, sheetMapping);
      updater.updateUnitsTotal(ukProducts, sheetMapping);
      updater.updateSessions(getProductsByMagazineForSessions("Mighty-X UK"), sheetMapping);
      updater.updateRateCount(ukProducts, sheetMapping);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateForKivals(Map<String, SheetInfo> sheetMapping) {
    List<AmazonProduct> products = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> kivalsProducts = products.stream()
        .filter(p -> p.getShopTitle().equals("Kivals")).collect(Collectors.toList());
    try {
      updater.updateRateCount(kivalsProducts, sheetMapping);
      updater.updateStarRating(kivalsProducts, sheetMapping);
      updater.updateUnitsTotal(kivalsProducts, sheetMapping);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateForZoroms(Map<String, SheetInfo> sheetMapping) {
    List<AmazonProduct> products = productRepository.getProductsByDate(currentDate);
    List<AmazonProduct> zoromsProducts = products.stream()
        .filter(p -> p.getShopTitle().equals("ZOROM'S")).collect(Collectors.toList());
    try {
      updater.updateRateCount(zoromsProducts, sheetMapping);
      updater.updateStarRating(zoromsProducts, sheetMapping);
      updater.updateUnitsTotal(zoromsProducts, sheetMapping);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<AmazonProduct> getProductsByMagazineForSessions(String shopName) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DAY_OF_MONTH, -3);
    Date dateMinusThreeDays = calendar.getTime();
    List<AmazonProduct> products = productRepository.getProductsByDate(dateMinusThreeDays);
    List<AmazonProduct> sortedProducts = products.stream()
        .filter(p -> p.getShopTitle().equals(shopName)).collect(Collectors.toList());
    return sortedProducts;
  }
}
