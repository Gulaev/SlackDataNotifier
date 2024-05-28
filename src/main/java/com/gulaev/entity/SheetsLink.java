package com.gulaev.entity;

import java.util.Objects;
import lombok.Data;

@Data
public class SheetsLink {

  private long id;
  private String asin;
  private String shopTitle;
  private String sheetLink;
  private String srTitle;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SheetsLink that = (SheetsLink) o;
    return asin.equals(that.asin) && shopTitle.equals(that.shopTitle) && sheetLink.equals(
        that.sheetLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asin, shopTitle, sheetLink);
  }
}
