package com.winsigns.investment.inventoryService.command;

import lombok.Data;

@Data
public class TransferCommand {

  private Long faCapitalPoolId;

  private Long ecaCashPoolId;

  private Double occurAmount;

}
