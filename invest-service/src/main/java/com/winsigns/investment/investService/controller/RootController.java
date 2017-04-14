package com.winsigns.investment.investService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionBasket;

/**
 * Created by colin on 2017/2/22.
 */

@RestController
public class RootController {
  @GetMapping(path = "/",
      produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
  public HttpEntity<HALResponse<String>> root() {
    HALResponse<String> halResponse = new HALResponse<String>("");
    halResponse.addCollectionLink(InstructionController.class, Instruction.class);
    halResponse.addCollectionLink(InstructionBasketController.class, InstructionBasket.class);
    return new ResponseEntity<>(halResponse, HttpStatus.OK);
  }
}
