package com.winsigns.investment.inventoryService.position.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.inventoryService.constant.ExternalTradeAccountType;

@Service
public abstract class AbstractPositionService implements IPositionService {

  @Autowired
  PositionServiceManager manager;

  @PostConstruct
  public void register() {
    manager.register(this);
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  // 账户类型
  abstract public ExternalTradeAccountType getAccountType();

  // 支持资金清算
  abstract public CapitalLiquidation supportCapitalLiquidation();

}
