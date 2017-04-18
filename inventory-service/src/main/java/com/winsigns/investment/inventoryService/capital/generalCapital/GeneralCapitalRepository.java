package com.winsigns.investment.inventoryService.capital.generalCapital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;

public interface GeneralCapitalRepository extends JpaRepository<GeneralCapital, Long> {

  public GeneralCapital findByFundAccountIdAndCurrency(Long fundAccountId, CurrencyCode currency);

}
