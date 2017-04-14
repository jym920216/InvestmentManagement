package com.winsigns.investment.tradeService.service.common;

import com.winsigns.investment.tradeService.constant.CurrencyCode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 模拟资金服务
 * 
 * @author yimingjin
 *
 */
@Data
@AllArgsConstructor
public class MockCapitalService {

  String name;

  CurrencyCode currency;

}
