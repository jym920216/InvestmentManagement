package com.winsigns.investment.tradeService.service.stock;

import org.springframework.stereotype.Component;

import com.winsigns.investment.tradeService.service.common.MockInvestService;

@Component
public class StockInvestService extends MockInvestService {

  public enum StockInvestType {

    BUY,

    SELL
  }

  @Override
  public StockInvestType[] getInstructionType() {
    return StockInvestType.values();
  }
}
