package com.winsigns.investment.investService.service.common;

import com.winsigns.investment.investService.model.Instruction;

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
   * @return 投资类型
   */
  Enum<?>[] getInvestType();

  /**
   * 获取指定名字的投资类型
   * 
   * @param name 名字
   * @return
   */
  Enum<?> getInvestType(String name);

  /**
   * 提交指令
   * 
   * @param instruction
   */
  boolean commitInstruction(Instruction instruction);
}
