package com.winsigns.investment.tradingOfficeService.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.chrono.ChronoIntegration;
import com.winsigns.investment.framework.integration.ServiceNotFoundException;
import com.winsigns.investment.framework.manager.ManagerSupport;
import com.winsigns.investment.tradingOfficeService.command.SetStrategyCommand;
import com.winsigns.investment.tradingOfficeService.exception.StrategyNotSupportedExcepiton;
import com.winsigns.investment.tradingOfficeService.integration.InvestServiceIntegration;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy.CurrentStrategyType;
import com.winsigns.investment.tradingOfficeService.model.Instruction;
import com.winsigns.investment.tradingOfficeService.repository.CurrentStrategyRepository;
import com.winsigns.investment.tradingOfficeService.repository.InstructionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StrategyManager extends ManagerSupport<AllocationStrategy> {

  @Autowired
  CurrentStrategyRepository currentStrategyRepository;

  @Autowired
  ChronoIntegration chrono;

  @Autowired
  InvestServiceIntegration investService;

  @Autowired
  InstructionRepository instructionRepository;

  @Override
  public AllocationStrategy getDefaultMember() {
    return null;
  }

  /**
   * 设置当前的分配策略
   * 
   * @param command
   * @return
   */
  public CurrentStrategy SetCurrentStrategy(SetStrategyCommand command) {

    AllocationStrategy strategy = this.getMember(command.getStrategyName());
    if (strategy == null) {
      throw new StrategyNotSupportedExcepiton(new Object[] {command.getStrategyName()});
    }

    CurrentStrategy currentStrategy = currentStrategyRepository.findByType(CurrentStrategyType.ORG);
    if (currentStrategy == null) {
      currentStrategy = new CurrentStrategy();
    }
    currentStrategy.setCurrentStrategyName(strategy.getName());
    currentStrategy.setUpdateTime(chrono.getTimeStamp());

    return currentStrategyRepository.save(currentStrategy);
  }

  /**
   * 获取当前的分配策略
   * 
   * @return
   */
  public CurrentStrategy getCurrentStrategy() {

    return currentStrategyRepository.findByType(CurrentStrategyType.ORG);

  }

  public Long allot(Instruction instruction) {
    return this.getMember(getCurrentStrategy().getCurrentStrategyName()).allot(instruction);
  }

  @Scheduled(fixedRate = 1000)
  public void allot() {
    try {
      List<Instruction> instructions = investService.getUnassignInstructions();

      instructions.forEach(thisInstruction -> {

        Long traderId = this.allot(thisInstruction);
        if (traderId != null) { // 分配成功
          Instruction instruction =
              instructionRepository.findByInstructionId(thisInstruction.getInstructionId());
          if (instruction == null) {
            instruction = thisInstruction;
          }
          instruction.setTraderId(traderId);
          instruction.setAllottedTime(chrono.getTimeStamp());
          instructionRepository.save(instruction);
          log.info("指令%d分配完成", thisInstruction.getInstructionId());

          investService.updateInstructionTrader(instruction.getInstructionId(),
              instruction.getTraderId());

        }
      });
    } catch (ServiceNotFoundException e) {
      log.error(e.getFullMessage());
    }
  }

}
