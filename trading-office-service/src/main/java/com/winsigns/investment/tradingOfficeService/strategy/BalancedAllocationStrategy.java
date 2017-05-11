package com.winsigns.investment.tradingOfficeService.strategy;

import org.springframework.stereotype.Component;

import com.winsigns.investment.tradingOfficeService.model.Instruction;

/**
 * 均衡分配策略
 * 
 * <p>
 * 看哪位交易员工作量少就分配给谁， 每个交易员当前工作量评价的方法为所有待处理指令的数目，篮子暂时算一个
 * 
 * @author yimingjin
 *
 */
@Component
public class BalancedAllocationStrategy extends AllocationStrategy {

  @Override
  public Long allot(Instruction instruction) {
    return 123456L;
  }
}
