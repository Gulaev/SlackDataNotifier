package com.gulaev.dao.config;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Config {

  private static final SqlSessionFactory SESSION_FACTORY;

  static {
    try (InputStream inputStream = Resources.getResourceAsStream("myBatis-config.xml")) {
      SESSION_FACTORY = new SqlSessionFactoryBuilder()
          .build(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static SqlSessionFactory getSessionFactory() {
    return SESSION_FACTORY;
  }
}