package com.gulaev.entity;

import java.util.Date;
import lombok.Data;

@Data
public class AmazonProduct {

  private long id;
  private String starRating;
  private String title;
  private String rateCount;
  private String price;
  private String asin;
  private String shopName;
  private Date uploadedOn;
  private String unitsTotal;
  private String shopTitle;
  private String sheetLink;
  private String sessions;
  private String rank;
  private String bestSellerRank;
}
