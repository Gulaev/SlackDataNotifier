package com.gulaev.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.gulaev.dao.implementation.AmazonProductRepositoryImpl;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import com.gulaev.entity.SheetInfo;
import java.io.IOException;
import java.io.InputStream;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetsUpdater {

  private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
  private static final String CREDENTIALS_FILE_PATH = "googleCredentials.json";
  private final Sheets sheets = this.getSheetsService();
  private final AmazonProductRepository productRepository = new AmazonProductRepositoryImpl();
  private String columnPrefix;
  private String columnSession;
  private String columnUnitsTotal;

  public GoogleSheetsUpdater(String columnPrefix, String columnSession, String columnUnitsTotal) {
    this.columnPrefix = columnPrefix;
    this.columnSession = columnSession;
    this.columnUnitsTotal = columnUnitsTotal;
  }

//  static {
//    sheetMapping.put("B08HZ5SMF4", new SheetInfo("1bSru6mMAq4hIFg5zq_0DHU_B00MmNdU9bC0mBk2XB6c", "Daily Data Input"));
//  }

  public void updateUnitsTotal(List<AmazonProduct> products, Map<String, SheetInfo> sheetMapping ) throws IOException {
      String sheetsColumn = "!%s8";
      sheetsColumn = String.format(sheetsColumn, columnUnitsTotal);
      Date uploadedOn = products.get(0).getUploadedOn();
      LocalDate localDate = uploadedOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate dayBefore = localDate.minusDays(1);
      Date dayBeforeDate = Date.from(dayBefore.atStartOfDay(ZoneId.systemDefault()).toInstant());
      List<AmazonProduct> productsDayBefore = productRepository.getProductsByDate(dayBeforeDate);

    for (AmazonProduct product : productsDayBefore) {
        String asin = product.getAsin();
        if (sheetMapping.containsKey(asin)) {
          SheetInfo info = sheetMapping.get(asin);
          String spreadsheetId = info.getSpreadsheetId();
          String sheetName = info.getSheetName();
          String unitsTotal = product.getUnitsTotal();


          if (unitsTotal != null && !unitsTotal.isEmpty()) {
            int sessionsValue = Integer.parseInt(unitsTotal);
            List<List<Object>> values = List.of(List.of(sessionsValue));
            ValueRange body = new ValueRange().setValues(values);
            String range = sheetName + sheetsColumn;
            sheets.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("USER_ENTERED")
                .execute();
            System.out.println("Updated sessions data for " + asin + " in sheet '" + sheetName + "' at HJ8.");
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }

    System.out.println("Data export to Google Sheets complete.");
  }


  public void updateStarRating(List<AmazonProduct> products, Map<String, SheetInfo> sheetMapping ) throws IOException {
    String sheetsColumn = "!%s10";
    sheetsColumn = String.format(sheetsColumn, columnPrefix);

    for (AmazonProduct product : products) {
      String asin = product.getAsin();
      if (sheetMapping.containsKey(asin)) {
        SheetInfo info = sheetMapping.get(asin);
        String spreadsheetId = info.getSpreadsheetId();
        String sheetName = info.getSheetName();
        String starRating = product.getStarRating();


        if (starRating != null && !starRating.isEmpty()) {
          String[] split = starRating.split(" ");
          List<List<Object>> values = List.of(List.of(split[0]));
          ValueRange body = new ValueRange().setValues(values);
          String range = sheetName + sheetsColumn;
          sheets.spreadsheets().values().update(spreadsheetId, range, body)
              .setValueInputOption("USER_ENTERED")
              .execute();
          System.out.println("Updated sessions data for " + asin + " in sheet '" + sheetName + "' at HJ8.");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  public void updateRateCount(List<AmazonProduct> products, Map<String, SheetInfo> sheetMapping) throws IOException {
    String sheetsColumn = "!%s9";
    sheetsColumn = String.format(sheetsColumn, columnPrefix);

    for (AmazonProduct product : products) {
      String asin = product.getAsin();
      if (sheetMapping.containsKey(asin)) {
        SheetInfo info = sheetMapping.get(asin);
        String spreadsheetId = info.getSpreadsheetId();
        String sheetName = info.getSheetName();
        String rateCount = product.getRateCount();

        if (rateCount != null && !rateCount.isEmpty()) {
          String[] s = rateCount.split(" ");
          String rateCountStr = s[0];
          if (rateCountStr.contains(","))
            rateCountStr = rateCountStr.replaceAll(",", "");

          List<List<Object>> values = List.of(List.of(rateCountStr));
          ValueRange body = new ValueRange().setValues(values);
          String range = sheetName + sheetsColumn;
          sheets.spreadsheets().values().update(spreadsheetId, range, body)
              .setValueInputOption("USER_ENTERED")
              .execute();
          System.out.println("Updated sessions data for " + asin + " in sheet '" + sheetName + "' at HJ8.");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  public void updateSessions(List<AmazonProduct> products, Map<String, SheetInfo> sheetMapping ) throws IOException {

    for (AmazonProduct product : products) {
      String asin = product.getAsin();
      if (sheetMapping.containsKey(asin)) {
        SheetInfo info = sheetMapping.get(asin);
        String spreadsheetId = info.getSpreadsheetId();
        String sheetName = info.getSheetName();
        String sessions = product.getSessions();


        if (sessions != null && !sessions.isEmpty()) {
          List<List<Object>> values = List.of(List.of(sessions));
          ValueRange body = new ValueRange().setValues(values);
          String range = sheetName + columnSession;
          sheets.spreadsheets().values().update(spreadsheetId, range, body)
              .setValueInputOption("USER_ENTERED")
              .execute();
          System.out.println("Updated sessions data for " + asin + " in sheet '" + sheetName + "' at HJ8.");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }

  }

  private Sheets getSheetsService() {
    try {
      InputStream in = GoogleSheetsUpdater.class.getResourceAsStream("/" + CREDENTIALS_FILE_PATH);
      GoogleCredentials credentials = ServiceAccountCredentials.fromStream(in)
          .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
      HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

      return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), requestInitializer)
          .setApplicationName(APPLICATION_NAME)
          .build();
    } catch (Exception e) {
      System.out.println("Connection to sheets invalid");
      System.out.println(e.getMessage());
    }
    return null;
  }
}

