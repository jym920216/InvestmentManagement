package com.winsigns.investment.tradeService.command;

import com.winsigns.investment.tradeService.constant.CurrencyCode;
import com.winsigns.investment.tradeService.constant.InstructionType;

import lombok.Data;

@Data
public class SendInstructionCommand {

  // 指令序号
  Long instructionId;

  // 投资组合
  Long portfolioId;

  // 投资标的
  Long securityId;

  // 投资服务
  String investService;

  // 投资方向
  String investDirection;

  // 币种
  CurrencyCode currency;

  // 成本价
  Double costPrice;

  // 数量类型
  InstructionType volumeType;

  // 指令数量
  Integer quantity;

  // 指令金额
  Double amount;
}
