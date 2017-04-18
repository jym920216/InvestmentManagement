package com.winsigns.investment.inventoryService.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.controller.FundAccountCapitalPoolController;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public class FundAccountCapitalPoolResourceAssembler
    extends ResourceAssemblerSupport<FundAccountCapitalPool, FundAccountCapitalPoolResource> {

  public FundAccountCapitalPoolResourceAssembler() {
    super(FundAccountCapitalPoolController.class, FundAccountCapitalPoolResource.class);
  }

  @Override
  public FundAccountCapitalPoolResource toResource(FundAccountCapitalPool fundAccountCapital) {
    FundAccountCapitalPoolResource fundAccountCapitalResource =
        createResourceWithId(fundAccountCapital.getId(), fundAccountCapital);

    // fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalPoolController.class)
    // .readFundAccountCapitalDetails(fundAccountCapital.getId())).withRel(
    // FundAccountCapitalDetail.class.getAnnotation(Relation.class).collectionRelation()));
    // fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalPoolController.class)
    // .setInvestmentLimit(fundAccountCapital.getId(), new SetInvestmentLimitCommand()))
    // .withRel("set-investment-limit"));

    return fundAccountCapitalResource;
  }

  @Override
  protected FundAccountCapitalPoolResource instantiateResource(FundAccountCapitalPool entity) {
    return new FundAccountCapitalPoolResource(entity);
  }
}
