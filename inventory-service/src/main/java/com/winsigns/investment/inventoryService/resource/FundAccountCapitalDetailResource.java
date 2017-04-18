package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.CapitalDetail;

public class FundAccountCapitalDetailResource extends HALResponse<CapitalDetail> {

  public FundAccountCapitalDetailResource(CapitalDetail fundAccountCapitalDetail) {
    super(fundAccountCapitalDetail);
  }

}
