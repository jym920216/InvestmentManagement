package com.winsigns.investment.tradingOfficeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradingOfficeService.command.AllotInstructionCommand;
import com.winsigns.investment.tradingOfficeService.model.Instruction;
import com.winsigns.investment.tradingOfficeService.repository.InstructionRepository;
import com.winsigns.investment.tradingOfficeService.strategy.StrategyManager;

@Service
public class AllocationService {

  @Autowired
  StrategyManager strategyManager;

  @Autowired
  InstructionRepository instructionRepository;

  public void assign(AllotInstructionCommand command) {

    Instruction instruction = new Instruction();
    instruction.setInstructionId(command.getInstructionId());
    instructionRepository.save(instruction);
  }
}
