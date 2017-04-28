package com.winsigns.investment.investService.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.ResponseResourceApplication;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.constant.InstructionMessageCode;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.instruction.InstructionCheckManager;
import com.winsigns.investment.investService.integration.FundServiceIntegration;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.repository.InstructionMessageRepository;
import com.winsigns.investment.investService.repository.InstructionRepository;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 指令服务
 * <p>
 * 创建指令<br>
 * 修改指令<br>
 * 删除指令<br>
 * 提交指令<br>
 * 
 * 
 * @author yimingjin
 *
 */
@Service
@Slf4j
public class InstructionService {

  final static String applyTopic = "resource-application";

  final static JsonDeserializer<Boolean> keyDeserializer =
      new JsonDeserializer<Boolean>(Boolean.class);
  final static JsonDeserializer<ResponseResourceApplication> valueDeserializer =
      new JsonDeserializer<ResponseResourceApplication>(ResponseResourceApplication.class);

  @Autowired
  InvestServiceManager investServiceManager;

  @Autowired
  InstructionCheckManager instructionCheckManager;

  @Autowired
  InstructionRepository instructionRepository;

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  @Autowired
  FundServiceIntegration fundService;

  @Autowired
  InstructionBasketService basketService;

  /**
   * 查询一条指令
   * 
   * @param instructionId
   * @return
   */
  public Instruction readInstruction(Long instructionId) {

    Assert.notNull(instructionId);

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

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

    instructionCheckManager.checkAndUpdate(newInstruction);
    return instructionRepository.save(newInstruction);
  }

  /**
   * 修改一条指令
   * 
   * @param instructionId 指令id
   * @param instructionCommand
   * @return
   */
  @Transactional
  public Instruction updateInstruction(Long instructionId,
      UpdateInstructionCommand instructionCommand) {
    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.MODIFY)) {
      return null;
    }

    thisInstruction.setPortfolioId(instructionCommand.getPortfolioId());
    thisInstruction.setSecurityId(instructionCommand.getSecurityId());
    thisInstruction.setInvestService(instructionCommand.getInvestService());
    thisInstruction.setInvestType(instructionCommand.getInvestType());
    thisInstruction.setCurrency(instructionCommand.getCurrency());
    thisInstruction.setCostPrice(instructionCommand.getCostPrice());
    thisInstruction.setVolumeType(instructionCommand.getVolumeType());
    thisInstruction.setQuantity(instructionCommand.getQuantity());
    thisInstruction.setAmount(instructionCommand.getAmount());

    instructionCheckManager.checkAndUpdate(thisInstruction);
    return instructionRepository.save(thisInstruction);
  }

  /**
   * 删除指令
   * <p>
   * 逻辑删除
   * 
   * @param instructionId
   */
  @Transactional
  public void deleteInstruction(Long instructionId) {

    Assert.notNull(instructionId);

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.isBasket()) {
      if (!thisInstruction.getExecutionStatus()
          .isSupportedOperator(InstructionOperatorType.DELETE)) {
        return;
      }

      thisInstruction.setExecutionStatus(InstructionStatus.DELETED);
      instructionRepository.save(thisInstruction);
    } else {
      basketService.deleteInstructionBasket(instructionId);
    }
  }

  /**
   * 批量删除指令
   * 
   * @param instructionIds
   */
  @Transactional
  public void deleteInstruction(List<Long> instructionIds) {
    for (Long id : instructionIds) {
      deleteInstruction(id);
    }
  }

  /**
   * 根据条件查找正常状态的指令
   * 
   * @param investManagerId
   * @return
   */
  public Collection<Instruction> findNormalInstructionByCondition(Long investManagerId,
      Long traderId, Date beginDate, Date endDate) {
    if (beginDate == null) {
      beginDate = new Date();
    }
    if (endDate == null) {
      endDate = new Date();
    }
    if (investManagerId != null) {
      return instructionRepository
          .findByInvestManagerIdAndCreateDateBetweenAndExecutionStatusNotAndInstructionBasketIsNullOrderByCreateTimeDesc(
              investManagerId, beginDate, endDate, InstructionStatus.DELETED);
    } else if (traderId != null) {
      return instructionRepository
          .findByTraderIdAndCreateDateBetweenAndExecutionStatusNotAndInstructionBasketIsNullOrderByCreateTimeDesc(
              traderId, beginDate, endDate, InstructionStatus.DELETED);
    } else {
      return instructionRepository
          .findByCreateDateBetweenAndExecutionStatusNotAndInstructionBasketIsNullOrderByCreateTimeDesc(
              beginDate, endDate, InstructionStatus.DELETED);
    }
  }

  /**
   * 根据条件查找已删除的指令
   * 
   * @param investManagerId
   * @return
   */
  public Collection<Instruction> findDeletedInstructionByCondition(Long investManagerId) {
    return instructionRepository
        .findByInvestManagerIdAndExecutionStatusAndInstructionBasketIsNullOrderByCreateTimeDesc(
            investManagerId, InstructionStatus.DELETED);
  }

  /**
   * 提交指令
   * 
   * @param instructionId
   * @return
   */
  @Transactional
  public Instruction commitInstruction(Long instructionId) {

    Instruction thisInstruction = instructionRepository.findOne(instructionId);

    Assert.notNull(thisInstruction);

    if (!thisInstruction.getExecutionStatus().isSupportedOperator(InstructionOperatorType.COMMIT)) {
      thisInstruction.addInstructionMessage(
          new InstructionMessage(thisInstruction, "executionStatus", InstructionMessageType.ERROR,
              InstructionMessageCode.INSTRUCTION_OPERATOR_NOT_SUPPORT));
      return thisInstruction;
    }

    if (!thisInstruction.isBasket()) {
      if (!instructionCheckManager.commitCheck(thisInstruction)) {
        return thisInstruction;
      }

      if (investServiceManager.commitInstruction(thisInstruction)) {
        thisInstruction.setExecutionStatus(InstructionStatus.COMMITING);
        thisInstruction.setCommitTime();
        instructionRepository.save(thisInstruction);
      } else {
        thisInstruction.addInstructionMessage("executionStatus", InstructionMessageType.ERROR,
            InstructionMessageCode.INSTRUCTION_COMMIT_FAIL);
        thisInstruction = instructionRepository.save(thisInstruction);
      }
    } else {
      // TODO 篮子的提交待处理
    }

    return thisInstruction;
  }

  /**
   * 处理资源申请的应答
   * 
   * @param record
   */
  @KafkaListener(topics = {applyTopic})
  public void responseResourceApplication(ConsumerRecord<String, String> record) {
    log.info(record.key());
    log.info(record.value());

    ResponseResourceApplication response =
        valueDeserializer.deserialize("", record.value().getBytes());

    Instruction thisInstruction = instructionRepository.findOne(response.getInstructionId());
    Assert.notNull(thisInstruction);
    if (response.getHeader().getResult()) {
      // TODO 分配交易员
      thisInstruction.setExecutionStatus(InstructionStatus.ASSIGNING);
      thisInstruction = instructionRepository.save(thisInstruction);
    } else {
      InstructionMessage thisMessage =
          new InstructionMessage(thisInstruction, "executionStatus", InstructionMessageType.ERROR,
              InstructionMessageCode.INSTRUCTION_COMMIT_FAIL, response.getHeader().getMessage());
      instructionMessageRepository.save(thisMessage);
    }
  }
}
