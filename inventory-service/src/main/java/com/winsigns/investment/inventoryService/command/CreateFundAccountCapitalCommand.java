package com.winsigns.investment.inventoryService.command;

import com.winsigns.investment.inventoryService.constant.CurrencyCode;

import lombok.Data;

@Data
public class CreateFundAccountCapitalCommand {

  private Long fundAccountId;

  private String externalCapitalAccountType;

  private CurrencyCode currency;

}
