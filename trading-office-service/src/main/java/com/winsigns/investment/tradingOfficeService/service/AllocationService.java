package com.winsigns.investment.tradingOfficeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradingOfficeService.strategy.StrategyManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AllocationService {

  @Autowired
  StrategyManager strategyManager;

  public void assign() {

    strategyManager.assign();

  }

}
