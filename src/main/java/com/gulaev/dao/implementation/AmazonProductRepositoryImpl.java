package com.gulaev.dao.implementation;

import com.gulaev.dao.config.Config;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class AmazonProductRepositoryImpl implements AmazonProductRepository {

  @Override
  public List<AmazonProduct> findProductsByDateRange(Date startDate, Date endDate) {
    try (SqlSession sqlSession = Config.getSessionFactory().openSession(true)){
      AmazonProductRepository mapper = sqlSession.getMapper(AmazonProductRepository.class);
      return mapper.findProductsByDateRange(startDate, endDate);
    }
  }

  @Override
  public List<AmazonProduct> getProductsByDate(Date date) {
    try (SqlSession sqlSession = Config.getSessionFactory().openSession(true)){
      AmazonProductRepository mapper = sqlSession.getMapper(AmazonProductRepository.class);
      return mapper.getProductsByDate(date);
    }
  }

  @Override
  public Date getMostRecentUploadDate() {
    try (SqlSession sqlSession = Config.getSessionFactory().openSession(true)) {
      AmazonProductRepository mapper = sqlSession.getMapper(AmazonProductRepository.class);
      return mapper.getMostRecentUploadDate();
    }
  }

  @Override
  public Optional<AmazonProduct> findProductByDate(Date date, AmazonProduct product) {
    try (SqlSession sqlSession = Config.getSessionFactory().openSession(true)){
      AmazonProductRepository mapper = sqlSession.getMapper(AmazonProductRepository.class);
//      System.out.println(product.toString());
//      System.out.println(date.toString());
      return mapper.findProductByDate(date, product);
    }  }

  @Override
  public Date getMaxDateBeforeInputDate(Date date) {
    try (SqlSession sqlSession = Config.getSessionFactory().openSession(true)) {
      AmazonProductRepository mapper = sqlSession.getMapper(AmazonProductRepository.class);
      return mapper.getMaxDateBeforeInputDate(date);
    }  }
}
