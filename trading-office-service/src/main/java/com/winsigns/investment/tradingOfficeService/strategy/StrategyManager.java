package com.winsigns.investment.tradingOfficeService.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.framework.chrono.ChronoIntegration;
import com.winsigns.investment.framework.manager.ManagerSupport;
import com.winsigns.investment.tradingOfficeService.command.SetStrategyCommand;
import com.winsigns.investment.tradingOfficeService.exception.StrategyNotSupportedExcepiton;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy.CurrentStrategyType;
import com.winsigns.investment.tradingOfficeService.repository.CurrentStrategyRepository;

@Service
public class StrategyManager extends ManagerSupport<AllocationStrategy> {

  @Autowired
  CurrentStrategyRepository currentStrategyRepository;

  @Autowired
  ChronoIntegration chronoIntegration;

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
    currentStrategy.setUpdateTime(chronoIntegration.getTimeStamp());

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

  public void assign() {

  }

}
