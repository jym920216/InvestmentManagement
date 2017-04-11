package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.FundAccountCapital;

public interface FundAccountCapitalRepository extends JpaRepository<FundAccountCapital, Long> {
  public FundAccountCapital findByFundAccountIdAndExternalCapitalAccountTypeAndCurrency(
      Long fundAccountId, String externalCapitalAccountType, CurrencyCode currency);
}
