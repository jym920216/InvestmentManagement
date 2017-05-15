package com.winsigns.investment.tradingOfficeService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradingOfficeService.command.AllotInstructionCommand;
import com.winsigns.investment.tradingOfficeService.service.AllocationService;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

  @Autowired
  AllocationService allocationService;

  @GetMapping
  public ResponseEntity<?> allotInstruction(AllotInstructionCommand command) {

    allocationService.assign(command);

    return null;
    // return new ResponseEntity<Object>(sequence, responseHeaders, HttpStatus.OK);
  }
}
