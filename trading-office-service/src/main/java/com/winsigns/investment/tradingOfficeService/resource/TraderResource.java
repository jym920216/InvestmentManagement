package com.winsigns.investment.tradingOfficeService.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.tradingOfficeService.constant.TraderStatus;
import com.winsigns.investment.tradingOfficeService.model.Trader;

import lombok.Getter;

@Relation(value = "trader", collectionRelation = "traders")
public class TraderResource extends HALResponse<Trader> {

  @Getter
  protected final List<Item> supportedStatus = new ArrayList<Item>();

  public TraderResource(Trader trader) {
    super(trader);

    for (TraderStatus status : TraderStatus.values()) {
      this.supportedStatus.add(new Item(status.name(), status.i18n()));
    }
  }

}
