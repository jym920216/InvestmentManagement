package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.Capital;

public interface CapitalRepository extends JpaRepository<Capital, Long> {
  public Capital findByFundAccountIdAndCurrency(Long fundAccountId, CurrencyCode currency);
}
