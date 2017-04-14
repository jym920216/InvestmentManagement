package com.winsigns.investment.inventoryService.position.common;

/**
 * 持仓服务的接口
 * 
 * @author yimingjin
 *
 */
public interface IPositionService {

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
   * 投资服务申请持仓
   * 
   * @param portfolioId
   * @param securityId
   * @param type
   * @return
   */
  boolean apply(Long portfolioId, Long securityId, String type);

}
