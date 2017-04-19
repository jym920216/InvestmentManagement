package com.winsigns.investment.inventoryService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {
  @GetMapping(path = "/",
      produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
  public HttpEntity<HALResponse<String>> root() {
    HALResponse<String> halResponse = new HALResponse<String>("");

    halResponse.addCollectionLink(ECACashPoolController.class, ECACashPool.class);
    halResponse.addCollectionLink(FundAccountCapitalPoolController.class,
        FundAccountCapitalPool.class);
    halResponse.addCollectionLink(FundAccountCapitalDetailController.class,
        FundAccountCapitalDetail.class);

    return new ResponseEntity<>(halResponse, HttpStatus.OK);
  }
}
