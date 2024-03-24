package com.gulaev.dao.implementation;

import com.gulaev.dao.config.Config;
import com.gulaev.dao.repository.AmazonProductRepository;
import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.List;
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
}
