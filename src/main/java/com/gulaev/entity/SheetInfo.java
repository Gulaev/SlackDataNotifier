package com.gulaev.entity;

public class SheetInfo {

  private final String spreadsheetId;
  private final String sheetName;

  public SheetInfo(String spreadsheetId, String sheetName) {
    this.spreadsheetId = spreadsheetId;
    this.sheetName = sheetName;
  }

  public String getSpreadsheetId() {
    return spreadsheetId;
  }

  public String getSheetName() {
    return sheetName;
  }
}

