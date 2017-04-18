package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.Capital;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.Position;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {
  @GetMapping(path = "/",
      produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
  public HttpEntity<HALResponse<String>> root() {
    HALResponse<String> halResponse = new HALResponse<String>("");

    halResponse.add(linkTo(methodOn((ECACashPoolController.class)).readECACashPools(null))
        .withRel(ECACashPool.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(linkTo(methodOn((FundAccountCapitalController.class)).readFundAccountCapitals())
        .withRel(Capital.class.getAnnotation(Relation.class).collectionRelation()));

    halResponse.add(
        linkTo(methodOn((FundAccountCapitalDetailController.class)).readFundAccountCapitalDetails())
            .withRel(
                CapitalDetail.class.getAnnotation(Relation.class).collectionRelation()));

    return new ResponseEntity<>(halResponse, HttpStatus.OK);
  }
}
