package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.Capital;

public class FundAccountCapitalResource extends HALResponse<Capital> {

  public FundAccountCapitalResource(Capital fundAccountCapital) {
    super(fundAccountCapital);
  }

}
