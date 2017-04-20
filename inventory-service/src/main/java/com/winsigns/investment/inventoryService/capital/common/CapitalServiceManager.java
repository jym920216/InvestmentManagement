package com.winsigns.investment.inventoryService.capital.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.CapitalSerialRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalPoolRepository;
import com.winsigns.investment.inventoryService.service.CapitalDetailService;
import com.winsigns.investment.inventoryService.service.ECACashPoolService;

@Component
public class CapitalServiceManager {

  List<ICapitalService> services = new ArrayList<ICapitalService>();

  @Autowired
  FundAccountCapitalPoolRepository capitalPoolRepository;

  @Autowired
  CapitalDetailService capitalDetailService;

  @Autowired
  CapitalSerialRepository capitalSerialRepository;

  @Autowired
  ECACashPoolService ecaCashPoolService;

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

  public List<FundAccountCapitalPool> findCapitalPoolsByFundAccount(Long fundAccountId) {
    return capitalPoolRepository.findByFundAccountId(fundAccountId);
  }

  /**
   * 获取指定的产品账户资金池
   * 
   * @param faCapitalPoolId
   * @return
   */
  public FundAccountCapitalPool readFundAccountCapitalPool(Long faCapitalPoolId) {
    return capitalPoolRepository.findOne(faCapitalPoolId);
  }

  /**
   * 创建一个指定类型的产品账户资金池
   * 
   * @param command
   * @return
   */
  public FundAccountCapitalPool createFundAccountCapitalPool(
      CreateFundAccountCapitalPoolCommand command) {
    return this.getService(command.getAccountType()).createFundAccountCapitalPool(command);
  }

  /**
   * 设置投资限额
   * 
   * @param command
   * @return
   */
  public FundAccountCapitalPool setInvestmentLimit(SetInvestmentLimitCommand command) {
    Assert.notNull(command.getFaCapitalPoolId());
    FundAccountCapitalPool capitalPool =
        capitalPoolRepository.findOne(command.getFaCapitalPoolId());
    Assert.notNull(capitalPool);
    // TODO 与已占用投资限额需要比较
    capitalPool.setInvestmentLimit(command.getInvestmentLimit());
    return capitalPoolRepository.save(capitalPool);
  }

}
