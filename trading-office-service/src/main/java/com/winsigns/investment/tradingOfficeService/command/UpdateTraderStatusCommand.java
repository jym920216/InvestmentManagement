package com.winsigns.investment.tradingOfficeService.command;

import com.winsigns.investment.tradingOfficeService.constant.TraderStatus;

import lombok.Data;

@Data
public class UpdateTraderStatusCommand {

  private Long traderId;

  private TraderStatus status;

}
