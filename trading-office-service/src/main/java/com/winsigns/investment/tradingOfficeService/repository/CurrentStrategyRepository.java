package com.winsigns.investment.tradingOfficeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy.CurrentStrategyType;

public interface CurrentStrategyRepository extends JpaRepository<CurrentStrategy, Long> {

  public CurrentStrategy findByType(CurrentStrategyType type);

}
