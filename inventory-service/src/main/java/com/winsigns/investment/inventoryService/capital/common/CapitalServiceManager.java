package com.winsigns.investment.inventoryService.capital.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalPoolRepository;

@Component
public class CapitalServiceManager {

  List<ICapitalService> services = new ArrayList<ICapitalService>();

  @Autowired
  FundAccountCapitalPoolRepository capitalPoolRepository;

  /**
   * 将资金服务注册到该管理者中
   * 
   * @param service
   */
  public void register(ICapitalService service) {
    services.add(service);
  }

  /**
   * 获取名字的资金服务
   * 
   * @param name 持仓服务
   * @return 指定持仓服务
   */
  public ICapitalService getService(String name) {
    for (ICapitalService service : services) {
      if (service.getName().equals(name)) {
        return service;
      }
    }
    return null;
  }

  /**
   * 获取指定账户类型的资金服务
   * 
   * @param accountType 账户类型
   * @return
   */
  public ICapitalService getService(ExternalCapitalAccountType accountType) {
    for (ICapitalService service : services) {
      if (service.getAccountType().equals(accountType)) {
        return service;
      }
    }
    return null;
  }

  public FundAccountCapitalPool readFundAccountCapitalPool(Long faCapitalPoolId) {
    return capitalPoolRepository.findOne(faCapitalPoolId);
  }

  public FundAccountCapitalPool createFundAccountCapitalPool(
      CreateFundAccountCapitalPoolCommand command) {
    return this.getService(command.getAccountType()).createFundAccountCapitalPool(command);
  }

}
