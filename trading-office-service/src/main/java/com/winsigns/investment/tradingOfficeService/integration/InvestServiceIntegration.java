package com.winsigns.investment.tradingOfficeService.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.tradingOfficeService.model.Instruction;

import lombok.Data;

/**
 * 与invest-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class InvestServiceIntegration extends AbstractIntegration {

  static final String INVEST_SERVICE = "invest-service";

  static final private String UNASSIGN_INSTRUCTIONS_URL = "/instructions/unassign";

  static final private String UPDATE_INSTRUCTION_URL = "/instructions/%d/trader";

  static public InvestServiceIntegration getInvestServiceIntegration() {
    return SpringManager.getApplicationContext().getBean(InvestServiceIntegration.class);
  }

  @Override
  public String getIntegrationName() {
    return INVEST_SERVICE;
  }

  @HystrixCommand(fallbackMethod = "defaultUnassignInstructions")
  public List<Instruction> getUnassignInstructions() {

    List<Instruction> instructions = new ArrayList<Instruction>();
    ObjectNode base = getNode(this.getIntegrationURI() + String.format(UNASSIGN_INSTRUCTIONS_URL));
    if (!base.has("_embedded") || !base.get("_embedded").has("instructions")) {
      return instructions;
    }
    JsonNode root = base.get("_embedded").get("instructions");

    for (JsonNode node : root) {
      try {
        Instruction instruction = this.objectMapper.readerFor(Instruction.class).readValue(node);
        instructions.add(instruction);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return instructions;
  }

  public List<Instruction> defaultUnassignInstructions() {
    return new ArrayList<Instruction>();
  }

  /**
   * 更新指令的命令
   * 
   * @author yimingjin
   *
   */
  @Data
  class UpdateInstructionTraderCommand {

    // 指令序号
    private Long instructionId;

    // 交易员
    private Long traderId;
  }

  public void updateInstructionTrader(Long instructionId, Long traderId) {

    UpdateInstructionTraderCommand command = new UpdateInstructionTraderCommand();
    command.setInstructionId(instructionId);
    command.setTraderId(traderId);
    HttpEntity<UpdateInstructionTraderCommand> requestEntity =
        new HttpEntity<UpdateInstructionTraderCommand>(command);
    restTemplate.put(
        this.getIntegrationURI() + String.format(UPDATE_INSTRUCTION_URL, instructionId),
        requestEntity);
  }

}
