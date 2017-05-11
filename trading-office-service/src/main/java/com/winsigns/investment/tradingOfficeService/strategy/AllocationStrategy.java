package com.winsigns.investment.tradingOfficeService.strategy;


import com.winsigns.investment.framework.manager.MemberSupport;
import com.winsigns.investment.tradingOfficeService.repository.InstructionRepository;

public abstract class AllocationStrategy extends MemberSupport<StrategyManager>
    implements IStrategy {

  protected InstructionRepository instructionRepository;

}
