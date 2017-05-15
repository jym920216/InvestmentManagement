package com.winsigns.investment.tradingOfficeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradingOfficeService.model.Trader;

public interface TraderRepository extends JpaRepository<Trader, Long> {

  public Trader findByTraderId(Long traderId);

}
