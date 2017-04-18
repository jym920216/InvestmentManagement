package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public class FundAccountCapitalPoolResource extends HALResponse<FundAccountCapitalPool> {

  public FundAccountCapitalPoolResource(FundAccountCapitalPool fundAccountCapital) {
    super(fundAccountCapital);
  }

}
