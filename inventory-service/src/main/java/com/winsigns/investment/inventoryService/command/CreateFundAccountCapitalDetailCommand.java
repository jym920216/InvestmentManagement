package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class CreateFundAccountCapitalDetailCommand {

  private Long faCapitalPoolId;

  private Long externalCapitalAccountId;

}
