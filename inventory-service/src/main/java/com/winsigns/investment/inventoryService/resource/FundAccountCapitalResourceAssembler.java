package com.winsigns.investment.inventoryService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.controller.FundAccountCapitalController;
import com.winsigns.investment.inventoryService.model.Capital;
import com.winsigns.investment.inventoryService.model.CapitalDetail;

public class FundAccountCapitalResourceAssembler
    extends ResourceAssemblerSupport<Capital, FundAccountCapitalResource> {

  public FundAccountCapitalResourceAssembler() {
    super(FundAccountCapitalController.class, FundAccountCapitalResource.class);
  }

  @Override
  public FundAccountCapitalResource toResource(Capital fundAccountCapital) {
    FundAccountCapitalResource fundAccountCapitalResource =
        createResourceWithId(fundAccountCapital.getId(), fundAccountCapital);

    fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalController.class)
        .readFundAccountCapitalDetails(fundAccountCapital.getId())).withRel(
            CapitalDetail.class.getAnnotation(Relation.class).collectionRelation()));
    fundAccountCapitalResource.add(linkTo(methodOn(FundAccountCapitalController.class)
        .setInvestmentLimit(fundAccountCapital.getId(), new SetInvestmentLimitCommand()))
            .withRel("set-investment-limit"));

    return fundAccountCapitalResource;
  }

  @Override
  protected FundAccountCapitalResource instantiateResource(Capital entity) {
    return new FundAccountCapitalResource(entity);
  }
}
