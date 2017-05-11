package com.winsigns.investment.tradingOfficeService.strategy;

import com.winsigns.investment.tradingOfficeService.model.Instruction;

public interface IStrategy {

  public Long allot(Instruction instruction);

}
