package com.winsigns.investment.tradeService.service.stock.SSEAStock;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.tradeService.service.common.AbstractTradeService;
import com.winsigns.investment.tradeService.service.common.ITradeType;
import com.winsigns.investment.tradeService.service.stock.StockInvestService;

@Service
public class SSEAStockTradeService extends AbstractTradeService {

  @Autowired
  StockInvestService investService;

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
