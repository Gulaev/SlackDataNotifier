package com.gulaev.dao.repository;

import com.gulaev.entity.AmazonProduct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface AmazonProductRepository {

  List<AmazonProduct> findProductsByDateRange(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

  List<AmazonProduct> getProductsByDate(@Param("date")Date date);

  Date getMostRecentUploadDate();

  Optional<AmazonProduct> findProductByDate(@Param("date")Date date, @Param("product")AmazonProduct product);

  Date getMaxDateBeforeInputDate(@Param("date") Date date);

}
