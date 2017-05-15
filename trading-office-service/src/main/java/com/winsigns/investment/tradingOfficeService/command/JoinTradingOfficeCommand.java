package com.winsigns.investment.tradingOfficeService.command;

import com.winsigns.investment.tradingOfficeService.constant.TraderStatus;

import lombok.Data;

@Data
public class JoinTradingOfficeCommand {

  private Long userId;

  private TraderStatus status = TraderStatus.NORMAL;

}
