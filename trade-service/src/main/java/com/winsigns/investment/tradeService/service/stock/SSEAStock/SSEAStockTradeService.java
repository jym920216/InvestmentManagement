package com.winsigns.investment.tradeService.service.stock.SSEAStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradeService.service.common.AbstractTradeService;
import com.winsigns.investment.tradeService.service.stock.StockInvestService;
import com.winsigns.investment.tradeService.service.stock.StockTradeType;

@Service
public class SSEAStockTradeService extends AbstractTradeService {

  @Autowired
  StockInvestService investService;

  @Override
  public StockInvestService getSupportedInvestService() {
    return investService;
  }

  @Override
  public String getSupportedSecurity() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StockTradeType[] getTradeType() {
    return StockTradeType.values();
  }

}
