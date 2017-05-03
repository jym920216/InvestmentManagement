package com.winsigns.investment.investService.service.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.integration.TradeServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 实现一种简单的投资服务的抽象类
 * 
 * @author yimingjin
 *
 */
@Service
public abstract class AbstractInvestService implements IInvestService {

  @Autowired
  InvestServiceManager investServiceManager;

  @Autowired
  TradeServiceIntegration tradeService;

  @PostConstruct
  private void register() {
    investServiceManager.register(this);
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String getSimpleName() {
    return i18nHelper.i18n(getName());
  }

  @Override
  public IInvestType getInvestType(String name) {
    for (IInvestType type : this.getInvestType()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    return DefaultInvestType.DEFAULT;
  }

  @Override
  public void commitInstruction(Instruction instruction) {
    tradeService.commitInstruction(instruction);
  }

}
