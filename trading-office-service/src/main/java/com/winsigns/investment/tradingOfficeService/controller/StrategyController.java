package com.winsigns.investment.tradingOfficeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradingOfficeService.command.SetStrategyCommand;
import com.winsigns.investment.tradingOfficeService.model.CurrentStrategy;
import com.winsigns.investment.tradingOfficeService.resource.CurrentStrategyResource;
import com.winsigns.investment.tradingOfficeService.resource.CurrentStrategyResourceAssembler;
import com.winsigns.investment.tradingOfficeService.strategy.StrategyManager;

@RestController
@RequestMapping(path = "/strategies",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class StrategyController {

  @Autowired
  StrategyManager strategyManager;

  @GetMapping
  public CurrentStrategyResource readCurrentStrategy() {

    CurrentStrategy strategy = strategyManager.getCurrentStrategy();

    return new CurrentStrategyResourceAssembler().toResource(strategy);
  }

  @PostMapping
  public CurrentStrategyResource setCurrentStrategy(@RequestBody SetStrategyCommand command) {

    CurrentStrategy strategy = strategyManager.SetCurrentStrategy(command);

    return new CurrentStrategyResourceAssembler().toResource(strategy);
  }


}
