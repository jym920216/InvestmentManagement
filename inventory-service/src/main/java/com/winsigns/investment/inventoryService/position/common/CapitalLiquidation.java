package com.winsigns.investment.inventoryService.position.common;

import com.winsigns.investment.inventoryService.constant.ExternalCapitalAccountType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CapitalLiquidation {

  private ExternalCapitalAccountType accountType;

  private String currencyCode;
}
