package com.winsigns.investment.tradeService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.tradeService.model.Entrust;

public class DoneResource extends HALResponse<Entrust> {

  public DoneResource(Entrust entrust) {
    super(entrust);

  }

}
