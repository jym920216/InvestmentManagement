package com.winsigns.investment.tradeService.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.tradeService.command.SendInstructionCommand;

@Service
public class TradeServiceManager {

  private List<ITradeService> services;

  private static TradeServiceManager instance = new TradeServiceManager();

  public static TradeServiceManager getInstance() {
    return instance;
  }

  private TradeServiceManager() {
    services = new ArrayList<ITradeService>();
  }

  /**
   * 将交易服务注册到该管理者中
   * 
   * @param service
   */
  public void register(ITradeService service) {
    services.add(service);
  }

  /**
   * 获取指定名字的交易服务
   * 
   * @param name 交易服务名
   * @return 指定交易服务
   */
  public ITradeService getService(String name) {
    for (ITradeService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }

  /**
   * 获取特定投资服务可用的交易服务列表
   * 
   * @param investService
   * @return
   */
  public List<ITradeService> getAvailableTradeServices(String investService) {
    Assert.notNull(investService);

    List<ITradeService> result = new ArrayList<ITradeService>();

    for (ITradeService service : services) {
      if (service.getSupportedInvestService().getName().equals(investService)) {
        result.add(service);
      }
    }
    return result;
  }

  public void acceptInstruction(SendInstructionCommand command) {

    List<ITradeService> services = this.getAvailableTradeServices(command.getInvestService());

    Double maxCapital = 0.0;
    Integer maxPosition = 0;

    for (ITradeService service : services) {
      // service.init(commitInstructionCmd);

      // maxCapital = Math.max(maxCapital, service.calculateRequiredCapital());
      // maxPosition = Math.max(maxPosition, service.calculateRequiredPosition());
    }

    // 取大值向资金服务申请资源
  }

}
