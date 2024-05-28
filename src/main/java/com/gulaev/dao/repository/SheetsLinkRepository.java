package com.gulaev.dao.repository;

import com.gulaev.entity.SheetsLink;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface SheetsLinkRepository {

  Optional<SheetsLink> getLinkByAsinAndShopTitle(@Param("asin")String asin, @Param("shopTitle")String shopTitle);

  List<SheetsLink> getAll();

}
