package com.winsigns.investment.inventoryService.position.stock.sseAStock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.constant.ExternalTradeAccountType;
import com.winsigns.investment.inventoryService.position.common.AbstractPositionService;
import com.winsigns.investment.inventoryService.position.common.CapitalLiquidation;
import com.winsigns.investment.inventoryService.position.stock.StockPosition;
import com.winsigns.investment.inventoryService.position.stock.StockPositionRepository;


@Service
public class SSEAStockPositionService extends AbstractPositionService {

  @Autowired
  StockPositionRepository stockPositionRepository;

  @Override
  public ExternalTradeAccountType getAccountType() {
    return ExternalTradeAccountType.SSE_A_STOCK_ACCOUNT;
  }

  @Override
  public CapitalLiquidation supportCapitalLiquidation() {
    return null;
  }

  @Override
  public boolean apply(Long portfolioId, Long securityId, String type) {

    List<StockPosition> positions =
        stockPositionRepository.findByPortfolioIdAndSecurityId(portfolioId, securityId);

    for (StockPosition position : positions) {

    }

    return false;
  }

}
