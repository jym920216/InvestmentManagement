package com.winsigns.investment.tradingOfficeService.strategy;

import org.springframework.stereotype.Component;

import com.winsigns.investment.tradingOfficeService.model.Instruction;

@Component
public class PriorityAllocationStrategy extends AllocationStrategy {

  @Override
  public Long allot(Instruction instruction) {
    return 123456L;
  }
}
