package com.winsigns.investment.tradingOfficeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradingOfficeService.controller.TraderController;
import com.winsigns.investment.tradingOfficeService.model.Trader;

public class TraderResourceAssembler extends ResourceAssemblerSupport<Trader, TraderResource> {

  public TraderResourceAssembler() {
    super(TraderController.class, TraderResource.class);
  }

  @Override
  public TraderResource toResource(Trader trader) {

    TraderResource resource = createResourceWithId(trader.getTraderId(), trader);

    return resource;
  }

  @Override
  protected TraderResource instantiateResource(Trader entity) {
    return new TraderResource(entity);
  }


}
