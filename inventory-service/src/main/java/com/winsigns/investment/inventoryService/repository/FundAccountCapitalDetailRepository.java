package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public interface FundAccountCapitalDetailRepository
    extends JpaRepository<FundAccountCapitalDetail, Long> {
  public FundAccountCapitalDetail findByCapitalPoolAndExternalCapitalAccountId(
      FundAccountCapitalPool capitalPool, Long externalCapitalAccountId);

  public List<FundAccountCapitalDetail> findByCapitalPool(FundAccountCapitalPool capitalPool);

  public FundAccountCapitalDetail findByCapitalPoolAndExternalCapitalAccountIdIsNull(
      FundAccountCapitalPool capitalPool);
}


