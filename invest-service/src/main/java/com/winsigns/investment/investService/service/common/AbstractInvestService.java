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
  TradeServiceIntegration tradeService;

  @PostConstruct
  private void register() {
    InvestServiceManager.getInstance().register(this);
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
  public Enum<?> getInvestType(String name) {
    for (Enum<?> type : this.getInvestType()) {
      if (type.name().equals(name)) {
        return type;
      }
    }
    return null;
  }

  @Override
  public boolean commitInstruction(Instruction instruction) {
    return tradeService.commitInstruction(instruction);
  }
}
