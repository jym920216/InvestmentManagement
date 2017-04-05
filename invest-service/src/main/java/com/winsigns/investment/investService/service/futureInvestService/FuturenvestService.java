package com.winsigns.investment.investService.service.futureInvestService;

import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.service.common.AbstractInvestService;

@Service
public class FuturenvestService extends AbstractInvestService {

  public enum FutureDirection {

    BUY,

    SELL;

    public String i18n() {
      return i18nHelper.i18n(this);
    }
  }

  @Override
  public FutureDirection[] getDirection() {
    return FutureDirection.values();
  }

}
