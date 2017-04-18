package com.winsigns.investment.inventoryService.capital.common;

import com.winsigns.investment.framework.service.IService;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;

/**
 * 资金服务的接口
 * 
 * @author yimingjin
 *
 */
public interface ICapitalService extends IService {

  /**
   * 投资服务申请资金
   * 
   * @param fundAccountId 产品账户
   * @param currency 币种
   * @param appliedCapital 申请的资金
   * @throws ResourceApplicationExcepiton
   */
  void apply(Long fundAccountId, CurrencyCode currency, Double appliedCapital)
      throws ResourceApplicationExcepiton;
}
