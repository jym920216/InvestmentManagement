package com.winsigns.investment.tradingOfficeService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.tradingOfficeService.controller.StrategyController;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;

public class CurrentStrategyResourceAssembler
    extends ResourceAssemblerSupport<CurrentStrategy, CurrentStrategyResource> {

  public CurrentStrategyResourceAssembler() {
    super(StrategyController.class, CurrentStrategyResource.class);
  }

  @Override
  public CurrentStrategyResource toResource(CurrentStrategy currentStrategy) {

    CurrentStrategyResource currentStrategyResource =
        createResourceWithId(currentStrategy.getId(), currentStrategy);

    return currentStrategyResource;
  }

  @Override
  protected CurrentStrategyResource instantiateResource(CurrentStrategy entity) {
    return new CurrentStrategyResource(entity);
  }


}
