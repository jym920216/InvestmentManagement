package com.winsigns.investment.investService.service.common;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public abstract class AbstractInvestService implements IInvestService {

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @PostConstruct
  private void register() {
    InvestServiceManager.getInstance().register(this);
  }

}
