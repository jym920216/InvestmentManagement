package com.winsigns.investment.tradingOfficeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradingOfficeService.model.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

  public Instruction findByInstructionId(Long instructionId);

}
