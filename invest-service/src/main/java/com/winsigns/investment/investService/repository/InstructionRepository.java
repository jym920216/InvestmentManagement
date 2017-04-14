package com.winsigns.investment.investService.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.model.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

  public List<Instruction> findByInvestManagerIdAndCreateDateBetweenAndExecutionStatusNotAndInstructionBasketIsNullOrderByCreateTimeDesc(
      Long investManagerId, Date beginDate, Date endDate, InstructionStatus status);

  public List<Instruction> findByInvestManagerIdAndExecutionStatusAndInstructionBasketIsNullOrderByCreateTimeDesc(
      Long investManagerId, InstructionStatus status);

  public List<Instruction> findByInstructionBasketIsNullOrderByCreateTimeDesc();

}
