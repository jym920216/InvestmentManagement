package com.winsigns.investment.investService.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.investService.model.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

  public List<Instruction> findByInstructionBasketIsNull(Sort sort);

}
