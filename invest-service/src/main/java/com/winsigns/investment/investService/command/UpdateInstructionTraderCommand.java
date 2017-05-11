package com.winsigns.investment.investService.command;

import lombok.Data;

/**
 * 更新指令的命令
 * 
 * @author yimingjin
 *
 */
@Data
public class UpdateInstructionTraderCommand {

  // 指令序号
  private Long instructionId;

  // 交易员
  private Long traderId;
}
