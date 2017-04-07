package com.winsigns.investment.investService.service.common;

/**
 * 投资服务的接口
 * 
 * @author yimingjin
 *
 */
public interface IInvestService {

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
   * @return 指令类型
   */
  Enum<?>[] getInstructionType();
}
