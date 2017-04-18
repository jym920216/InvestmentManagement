package com.winsigns.investment.inventoryService.capital.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CapitalServiceManager {

  List<ICapitalService> services = new ArrayList<ICapitalService>();

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
}
