package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.tradeService.command.CommitInstructionCommand;
import com.winsigns.investment.tradeService.model.Done;

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
  RemoteInvestService getSupportedInvestService();

  /**
   * 
   * @return 所使用的资金服务
   */
  RemoteCapitalService getUsedCapitalService();

  /**
   * 
   * @return 所使用的持仓服务
   */
  RemotePositionService getUsedPositionService();

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
  ITradeType[] getTradeType();

  /**
   * 获取指定名字的交易类型
   * 
   * @param name 名字
   * @return
   */
  ITradeType getTradeType(String name);

  /**
   * 价格类型
   * 
   * @return
   */
  IPriceType[] getPriceType();

  /**
   * 计算指令需要的资源
   * 
   * @param command
   * @return
   */
  Resource calculateRequiredResource(CommitInstructionCommand command);

  /**
   * 虚拟成交
   */
  void virtualDone(CommitInstructionCommand command, Resource resource);

  /**
   * 成交信息
   * 
   * @param thisDone
   */
  void done(Done thisDone);

}
