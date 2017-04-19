package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResourceAssembler;

@RestController
@RequestMapping(path = "/fa-capital-details",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class FundAccountCapitalDetailController {

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @GetMapping
  public Resources<FundAccountCapitalDetailResource> readFundAccountCapitalDetails(
      @RequestParam Long externalCapitalAccountId) {

    Link link = linkTo(methodOn(FundAccountCapitalDetailController.class)
        .readFundAccountCapitalDetails(externalCapitalAccountId)).withRel(
            FundAccountCapitalDetail.class.getAnnotation(Relation.class).collectionRelation());

    List<FundAccountCapitalDetail> details =
        capitalServiceManager.readFundAccountCapitalDetailByECA(externalCapitalAccountId);

    return new Resources<FundAccountCapitalDetailResource>(
        new FundAccountCapitalDetailResourceAssembler().toResources(details), link);
  }

  /**
   * 查询一条指定的产品账户资金明细
   * 
   * @param faCapitalDetailId
   * @return
   */
  @GetMapping("/{faCapitalDetailId}")
  public FundAccountCapitalDetailResource readFundAccountCapitalDetail(
      @PathVariable Long faCapitalDetailId) {
    FundAccountCapitalDetail fundAccountCapitalDetail =
        capitalServiceManager.readFundAccountCapitalDetail(faCapitalDetailId);
    FundAccountCapitalDetailResource fundAccountCapitalResource =
        new FundAccountCapitalDetailResourceAssembler().toResource(fundAccountCapitalDetail);

    return fundAccountCapitalResource;
  }
}
