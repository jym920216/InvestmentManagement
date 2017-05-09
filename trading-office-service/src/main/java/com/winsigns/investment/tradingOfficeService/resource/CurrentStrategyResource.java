package com.winsigns.investment.tradingOfficeService.resource;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;

@Relation(value = "current-strategy", collectionRelation = "current-strategys")
public class CurrentStrategyResource extends HALResponse<CurrentStrategy> {

  public CurrentStrategyResource(CurrentStrategy currentStrategy) {
    super(currentStrategy);
  }

}
