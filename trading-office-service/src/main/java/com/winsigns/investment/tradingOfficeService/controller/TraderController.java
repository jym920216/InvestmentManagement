package com.winsigns.investment.tradingOfficeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradingOfficeService.command.JoinTradingOfficeCommand;
import com.winsigns.investment.tradingOfficeService.command.LeaveTradingOfficeCommand;
import com.winsigns.investment.tradingOfficeService.command.UpdateTraderStatusCommand;
import com.winsigns.investment.tradingOfficeService.model.Trader;
import com.winsigns.investment.tradingOfficeService.resource.TraderResource;
import com.winsigns.investment.tradingOfficeService.resource.TraderResourceAssembler;
import com.winsigns.investment.tradingOfficeService.service.TraderService;

@RestController
@RequestMapping(path = "/traders",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class TraderController {

  @Autowired
  TraderService traderService;

  @PostMapping
  public ResponseEntity<?> joinTradingOffice(@RequestBody JoinTradingOfficeCommand command) {

    HttpHeaders responseHeaders = new HttpHeaders();
    Trader trader = traderService.joinTradingOffice(command);
    responseHeaders.setLocation(
        linkTo(methodOn(TraderController.class).getTrader(trader.getTraderId())).toUri());

    return new ResponseEntity<Object>(trader, responseHeaders, HttpStatus.CREATED);
  }

  @GetMapping("/{traderId}")
  public TraderResource getTrader(@PathVariable Long traderId) {

    Trader trader = traderService.getTrader(traderId);

    return new TraderResourceAssembler().toResource(trader);
  }

  @PutMapping("/{traderId}")
  public TraderResource updateTraderStatus(@PathVariable Long traderId,
      @RequestBody UpdateTraderStatusCommand command) {

    command.setTraderId(traderId);
    Trader trader = traderService.updateTraderStatus(command);

    return new TraderResourceAssembler().toResource(trader);
  }

  @DeleteMapping("/{traderId}")
  public ResponseEntity<?> leaveTradingOffice(@PathVariable Long traderId) {

    LeaveTradingOfficeCommand command = new LeaveTradingOfficeCommand();
    command.setTraderId(traderId);
    traderService.leaveTradingOffice(command);

    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }
}
