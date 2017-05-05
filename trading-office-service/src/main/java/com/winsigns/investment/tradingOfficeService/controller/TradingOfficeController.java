package com.winsigns.investment.tradingOfficeService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradingOfficeService.service.TradingOfficeService;

@RestController
@RequestMapping("/trading-office")
public class TradingOfficeController {

  @Autowired
  TradingOfficeService tradingOfficeService;

  @GetMapping
  public ResponseEntity<?> getOperatorSequence() {


    return null;
    // return new ResponseEntity<Object>(sequence, responseHeaders, HttpStatus.OK);
  }
}
