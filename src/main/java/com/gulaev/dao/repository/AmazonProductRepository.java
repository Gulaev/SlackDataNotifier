package com.gulaev.dao.repository;

import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmazonProductRepository {

  List<AmazonProduct> findProductsByDateRange(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

  List<AmazonProduct> getProductsByDate(@Param("date")Date date);

  Date getMostRecentUploadDate();

}
