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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.command.CreateCashPoolCommand;
import com.winsigns.investment.inventoryService.command.TransferCommand;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.resource.CapitalDetailResource;
import com.winsigns.investment.inventoryService.resource.CapitalDetailResourceAssembler;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResource;
import com.winsigns.investment.inventoryService.resource.ECACashPoolResourceAssembler;
import com.winsigns.investment.inventoryService.service.CapitalDetailService;
import com.winsigns.investment.inventoryService.service.ECACashPoolService;

@RestController
@RequestMapping(path = "/eca-cash-pools",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class ECACashPoolController {

  @Autowired
  ECACashPoolService ecaCashPoolService;

  @Autowired
  CapitalDetailService capitalDetailService;

  /**
   * 获取外部资金池列表
   * 
   * @param externalCapitalAccountId
   * @return
   */
  @GetMapping
  public Resources<ECACashPoolResource> readECACashPools(
      @RequestParam(required = false) Long externalCapitalAccountId) {
    if (externalCapitalAccountId == null) {
      Link link = linkTo(ECACashPoolController.class).withSelfRel();
      return new Resources<ECACashPoolResource>(
          new ECACashPoolResourceAssembler().toResources(ecaCashPoolService.findAll()), link);
    } else {
      Resources<ECACashPoolResource> resources =
          new Resources<ECACashPoolResource>(new ECACashPoolResourceAssembler().toResources(
              ecaCashPoolService.findByExternalCapitalAccountId(externalCapitalAccountId)));
      for (ECACashPoolResource resource : resources) {
        // 添加内嵌的资金明细
        List<CapitalDetail> details = resource.getContent().getDetails();
        if (!details.isEmpty()) {
          resource.add(CapitalDetail.class.getAnnotation(Relation.class).collectionRelation(),
              new CapitalDetailResourceAssembler().toResources(details));
        }
      }
      return resources;
    }
  }

  /**
   * 创建一个外部资金池
   * 
   * @param createCashPoolCommand
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createECACashPool(
      @RequestBody CreateCashPoolCommand createCashPoolCommand) {

    ECACashPool ecaCashPool = ecaCashPoolService.addECACashPool(createCashPoolCommand);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(ECACashPoolController.class).readECACashPool(ecaCashPool.getId())).toUri());
    return new ResponseEntity<Object>(ecaCashPool, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 获取一个具体的外部资金池
   * 
   * @param ecaCashPoolId
   * @return
   */
  @GetMapping("/{ecaCashPoolId}")
  public ECACashPoolResource readECACashPool(@PathVariable Long ecaCashPoolId) {
    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCashPoolId);
    ECACashPoolResource ecaCashPoolResource =
        new ECACashPoolResourceAssembler().toResourceWithMeasures(ecaCashPool);

    // 添加内嵌的资金明细
    List<CapitalDetail> details = ecaCashPool.getDetails();
    if (!details.isEmpty()) {
      ecaCashPoolResource.add(
          CapitalDetail.class.getAnnotation(Relation.class).collectionRelation(),
          new CapitalDetailResourceAssembler().toResources(details));
    }
    return ecaCashPoolResource;
  }

  /**
   * 将资金转入到产品账户
   * 
   * @param ecaCashPoolId
   * @param command
   * @return
   */
  @PostMapping("/{ecaCashPoolId}/to-fa")
  public CapitalDetailResource transferToFundAccount(@PathVariable Long ecaCashPoolId,
      @RequestBody TransferCommand command) {
    command.setEcaCashPoolId(ecaCashPoolId);
    CapitalDetail capitalDetail = capitalDetailService.transferFromECAToFA(command);

    return new CapitalDetailResourceAssembler().toResource(capitalDetail);
  }

}
