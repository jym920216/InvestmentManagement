package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Currency;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.integration.FundServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.repository.InstructionMessageRepository;
import com.winsigns.investment.investService.repository.InstructionRepository;

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

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  @Autowired
  FundServiceIntegration fundService;

  /**
   * 查询一条指令
   * 
   * @param instructionId
   * @return
   */
  public Instruction readInstruction(Long instructionId) {

    if (instructionId == null) {
      return null;
    }

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    if (thisInstruction == null) {
      return null;
    }

    return thisInstruction;
  }

  /**
   * 增加一条指令
   * 
   * @param instructionCommand
   * @return
   */
  public Instruction addInstruction(CreateInstructionCommand instructionCommand) {

    // 投资经理必须输入，以后可能在controller中通过session赋值
    Assert.notNull(instructionCommand.getInvestManagerId());

    Instruction newInstruction = new Instruction();

    newInstruction.setInvestManagerId(instructionCommand.getInvestManagerId());
    newInstruction.setExecutionStatus(InstructionStatus.DRAFT);
    newInstruction = instructionRepository.save(newInstruction);
    check(newInstruction);
    return newInstruction;
  }

  @Transactional
  public Instruction updateInstruction(Long instructionId,
      UpdateInstructionCommand instructionCommand) {
    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    if (thisInstruction == null) {
      return null;
    }

    thisInstruction.setPortfolioId(instructionCommand.getPortfolioId());
    thisInstruction.setSecurityId(instructionCommand.getSecurityId());
    thisInstruction.setInvestService(instructionCommand.getInvestService());
    thisInstruction.setInvestDirection(instructionCommand.getInvestDirection());
    thisInstruction.setCurrency(Currency.getInstance(instructionCommand.getCurrency()));
    thisInstruction.setCostPrice(instructionCommand.getCostPrice());
    thisInstruction.setVolumeType(instructionCommand.getVolumeType());
    thisInstruction.setQuantity(instructionCommand.getQuantity());
    thisInstruction.setAmount(instructionCommand.getAmount());

    check(thisInstruction);
    return instructionRepository.save(thisInstruction);
  }

  protected void check(Instruction thisInstruction) {
    instructionMessageRepository.deleteByInstruction(thisInstruction);
    checkPortfolio(thisInstruction);
  }

  /**
   * 检查指令的投资组合信息
   * 
   * @param thisInstruction
   */
  protected void checkPortfolio(Instruction thisInstruction) {

    Long portfolioId = thisInstruction.getPortfolioId();

    if (portfolioId == null) {
      thisInstruction.addInstructionMessage(new InstructionMessage(thisInstruction, "portfolioId",
          InstructionMessageType.ERROR, InstructionMessageCode.PORTFOLIO_NOT_NULL));
    } else {
      // 检查该投资组合是否为该投资经理管理
      Long investManagerId = fundService.getPortfolioInvestManager(portfolioId);
      if (investManagerId == null
          || !investManagerId.equals(thisInstruction.getInvestManagerId())) {
        thisInstruction.addInstructionMessage(
            new InstructionMessage(thisInstruction, "portfolioId", InstructionMessageType.ERROR,
                InstructionMessageCode.PORTFOLIO_NOT_MATCHED_INVESTMANAGER));
      }
    }
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

    if (instruction.getExecutionStatus() != InstructionStatus.DRAFT)
      return false;

    instruction.setExecutionStatus(InstructionStatus.COMMITING);
    instructionRepository.save(instruction);

    // TODO 向kafka 发送异步请求

    return true;
  }

}
