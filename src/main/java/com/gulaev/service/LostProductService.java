package com.gulaev.service;

import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.implementation.SheetsLinkRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.dao.repository.SheetsLinkRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.entity.SheetsLink;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LostProductService {

    private AmazonProductRepository amazonProductRepository;
    private SheetsLinkRepository sheetsLinkRepository;

    public LostProductService() {
        this.amazonProductRepository = new AmazonProductRepositoryImpl();
        this.sheetsLinkRepository = new SheetsLinkRepositoryImpl();
    }

    public Map<Boolean, String> findLostProduct() {
        Map<Boolean, String> answer = new HashMap<>();
        Date currentDate = amazonProductRepository.getMostRecentUploadDate();
        Date beforeDate = amazonProductRepository.getMaxDateBeforeInputDate(currentDate);
        List<AmazonProduct> currentDateProduct = amazonProductRepository.findProductsByDateRange(
            currentDate, beforeDate);
        List<SheetsLink> allSheetLinks = sheetsLinkRepository.getAll();
        List<SheetsLink> lostLinks = new ArrayList<>();
        for (SheetsLink link : allSheetLinks) {
            boolean found = containsProduct(currentDateProduct, link);
            if (!found) {
                lostLinks.add(link);
            }
        }
        if (!lostLinks.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (SheetsLink link: lostLinks) {
                message.append(String.format("\n \uD83C\uDD98 <%s|%s> no data for today \uD83C\uDD98",
                    link.getSheetLink(), link.getSrTitle()));
            }
            answer.put(true, message.toString());
            System.out.println(message);
        } else {
            answer.put(false, "All product loaded");
        }
        return answer;
    }

    private boolean containsProduct(List<AmazonProduct> products, SheetsLink link) {
        for (AmazonProduct product : products) {
            if (link.getAsin().equals(product.getAsin()) && link.getShopTitle().equals(product.getShopTitle())) {
                return true;
            }
        }
        return false;
    }
}
