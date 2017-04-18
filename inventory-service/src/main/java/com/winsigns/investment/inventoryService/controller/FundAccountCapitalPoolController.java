package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalDetailResourceAssembler;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalPoolResource;
import com.winsigns.investment.inventoryService.resource.FundAccountCapitalPoolResourceAssembler;

@RestController
@RequestMapping(path = "/fa-capital-pools",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class FundAccountCapitalPoolController {

  @Autowired
  CapitalServiceManager capitalServiceManager;

  /**
   * 创建一个产品账户资金池
   * 
   * @param createFundAccountCapitalCommand
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createFundAccountCapitalPool(
      @RequestBody CreateFundAccountCapitalPoolCommand command) {

    FundAccountCapitalPool fundAccountCapitalPool =
        capitalServiceManager.createFundAccountCapitalPool(command);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(linkTo(methodOn(FundAccountCapitalPoolController.class)
        .readFundAccountCapitalPool(fundAccountCapitalPool.getId())).toUri());
    return new ResponseEntity<Object>(fundAccountCapitalPool, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 获取一个产品账户资金池
   * 
   * @param faCapitalPoolId
   * @return
   */
  @GetMapping("/{faCapitalPoolId}")
  public FundAccountCapitalPoolResource readFundAccountCapitalPool(
      @PathVariable Long faCapitalPoolId) {
    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    FundAccountCapitalPoolResource capitalPoolResource =
        new FundAccountCapitalPoolResourceAssembler().toResource(capitalPool);

    List<FundAccountCapitalDetail> details = capitalPool.getDetails();
    if (!details.isEmpty()) {
      capitalPoolResource.add(
          FundAccountCapitalDetail.class.getAnnotation(Relation.class).collectionRelation(),
          new FundAccountCapitalDetailResourceAssembler().toResources(details));
    }
    return capitalPoolResource;
  }
}
