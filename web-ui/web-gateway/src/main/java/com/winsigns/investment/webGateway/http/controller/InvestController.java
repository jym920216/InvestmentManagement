package com.winsigns.investment.webGateway.http.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.webGateway.http.service.InvestService;


@RestController
@RequestMapping(path = "instructions")
public class InvestController {

  @Autowired
  InvestService investService;

  @GetMapping
  public List getAllInstruct() {
    return investService.getAllInstruct();
  }

  @GetMapping("/{instructionId}")
  public List getInstructById(@PathVariable String instructionId) {
    return investService.getInstructById(instructionId);
  }
}
