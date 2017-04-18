package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public interface FundAccountCapitalPoolRepository extends JpaRepository<FundAccountCapitalPool, Long> {
  public FundAccountCapitalPool findByFundAccountIdAndCurrency(Long fundAccountId, CurrencyCode currency);
}
