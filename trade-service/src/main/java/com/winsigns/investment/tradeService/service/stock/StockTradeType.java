package com.winsigns.investment.tradeService.service.stock;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.service.common.ITradeType;

public enum StockTradeType implements ITradeType {

  BUY,

  SELL;

  public String i18n() {
    return i18nHelper.i18n(this);
  }

  // 支持的操作
  private static HashMap<StockTradeType, List<Enum<?>>> supportInvestTypes =
      new HashMap<StockTradeType, List<Enum<?>>>();
  static {
    supportInvestTypes.put(BUY, asList(StockInvestService.StockInvestType.BUY));
    supportInvestTypes.put(SELL, asList(StockInvestService.StockInvestType.SELL));
  }

  @Override
  public List<Enum<?>> getSupportInvestTypes() {
    return supportInvestTypes.get(this);
  }
}
