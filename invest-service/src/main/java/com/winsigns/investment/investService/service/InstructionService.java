package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.repository.InstructionRepository;
import com.winsigns.investment.investService.resource.InstructionResource;

/**
 * 指令服务
 * <p>
 * 创建指令<br>
 * 修改指令<br>
 * 删除指令<br>
 * 创建篮子<br>
 * 
 * @author yimingjin
 *
 */
@Service
public class InstructionService {

  Logger log = LoggerFactory.getLogger(InstructionService.class);

  @Autowired
  InstructionRepository instructionRepository;

  public Instruction findOne(Long instructionId) {
    return instructionRepository.findOne(instructionId);
  }

  /**
   * 查询一条指令
   * 
   * @param instructionId
   * @return
   */
  public InstructionResource readInstruction(Instruction thisInstruction) {

    if (thisInstruction == null) {
      return null;
    }

    // 准备各项过滤条件的Link
    // 1.基金产品与组合
    // 2.投资标的
    // 3.买卖类型
    // 4.成交价/成交均价
    // 5.数量/总金额
    // 6.状态
    Link fundLink = new Link("/fund-service/funds");

    InstructionResource thisResource = new InstructionResource(thisInstruction);
    thisResource.add(fundLink);

    return thisResource;
  }

  /**
   * 增加一条指令
   * 
   * @param instructionCommand 可以为空，则产生一条全空的指令
   * @return
   */
  public Instruction addInstruction(CreateInstructionCommand instructionCommand) {

    Instruction newInstruction = new Instruction();

    if (instructionCommand != null) {

      newInstruction.setPortfolioId(instructionCommand.getPortfolioId());
      newInstruction.setSecurityId(instructionCommand.getSecurityId());
      newInstruction.setInvestSvc(instructionCommand.getInvestSvc());
      newInstruction.setInvestDirection(instructionCommand.getInvestDirection());
      newInstruction.setCurrency(Currency.getInstance(instructionCommand.getCurrency()));
      newInstruction.setCostPrice(instructionCommand.getCostPrice());
      newInstruction.setVolumeType(instructionCommand.getVolumeType());
      newInstruction.setQuantity(instructionCommand.getQuantity());
      newInstruction.setAmount(instructionCommand.getAmount());
      newInstruction.setInstructionStatus(InstructionStatus.Draft);
      newInstruction.setCreateDate(new Date());
    }

    return instructionRepository.save(newInstruction);
  }

  @Transactional
  public Instruction updateInstruction(Long instructionId,
      UpdateInstructionCommand instructionCommand) {
    Instruction instruction = instructionRepository.findOne(instructionId);

    instruction.setPortfolioId(instructionCommand.getPortfolioId());
    instruction.setSecurityId(instructionCommand.getSecurityId());
    instruction.setInvestSvc(instructionCommand.getInvestSvc());
    instruction.setInvestDirection(instructionCommand.getInvestDirection());
    instruction.setCurrency(Currency.getInstance(instructionCommand.getCurrency()));
    instruction.setCostPrice(instructionCommand.getCostPrice());
    instruction.setVolumeType(instructionCommand.getVolumeType());
    instruction.setQuantity(instructionCommand.getQuantity());
    instruction.setAmount(instructionCommand.getAmount());

    return instructionRepository.save(instruction);
  }

  public void deleteInstruction(Long instructionId) {

    instructionRepository.delete(instructionId);
  }

  public Collection<Instruction> findByCreateDate(Date createDate) {
    return instructionRepository.findByCreateDate(createDate);
  }

  public Collection<Instruction> findAll() {
    return instructionRepository.findAll();
  }

  public boolean commitInstruction(Long instructionId) {

    Instruction instruction = instructionRepository.findOne(instructionId);

    if (instruction.getInstructionStatus() != InstructionStatus.Draft)
      return false;

    instruction.setInstructionStatus(InstructionStatus.Executing);
    instructionRepository.save(instruction);

    // TODO 向kafka 发送异步请求

    return true;
  }

}
