package com.winsigns.investment.tradeService.service.common;

/**
 * 交易服务的接口
 * 
 * @author yimingjin
 *
 */
public interface ITradeService {

  /**
   *
   * @return 服务名称
   */
  String getName();

  /**
   * 
   * @return 服务简称
   */
  String getSimpleName();

  /**
   * 
   * @return 所支持的投资服务
   */
  MockInvestService getSupportedInvestService();


  /**
   * 投资标的
   * 
   * @return
   */
  // TODO
  String getSupportedSecurity();

  /**
   * 
   * @return 交易类型
   */
  Enum<?>[] getTradeType();

}
