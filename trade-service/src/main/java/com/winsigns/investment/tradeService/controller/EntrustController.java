package com.winsigns.investment.tradeService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.tradeService.command.CreateEntrustCommand;
import com.winsigns.investment.tradeService.command.UpdateEntrustCommand;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.resource.EntrustResource;
import com.winsigns.investment.tradeService.resource.EntrustResourceAssembler;
import com.winsigns.investment.tradeService.service.EntrustService;

@RestController
@RequestMapping(path = "/entrusts",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class EntrustController {

  @Autowired
  EntrustService entrustService;

  @GetMapping
  public Resources<EntrustResource> readEntrusts(@RequestParam Long instructionId) {
    Link link = linkTo(EntrustController.class).withSelfRel();
    Collection<Entrust> entrusts = entrustService.findNormalEntrustByCondition(instructionId);
    return new Resources<EntrustResource>(new EntrustResourceAssembler().toResources(entrusts),
        link);
  }

  /**
   * 创建一条委托
   * 
   * @param command
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createEntrust(@RequestBody CreateEntrustCommand command) {

    Entrust entrust = entrustService.addEntrust(command);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(EntrustController.class).readEntrust(entrust.getId())).toUri());
    return new ResponseEntity<Object>(entrust, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 获取一条具体的委托
   * 
   * @param entrustId
   * @return
   */
  @GetMapping("/{entrustId}")
  public EntrustResource readEntrust(@PathVariable Long entrustId) {
    return new EntrustResourceAssembler().toResource(entrustService.readEntrust(entrustId));
  }

  /**
   * 修改一条委托
   * 
   * @param entrustId
   * @param command
   * @return
   */
  @PutMapping("/{entrustId}")
  public EntrustResource updateEntrust(@PathVariable Long entrustId,
      @RequestBody UpdateEntrustCommand command) {
    command.setEntrustId(entrustId);
    return new EntrustResourceAssembler().toResource(entrustService.updateEntrust(command));
  }

  /**
   * 删除一条委托
   * 
   * @param entrustId
   * @return
   */
  @DeleteMapping("/{entrustId}")
  public ResponseEntity<?> deleteEntrust(@PathVariable Long entrustId) {
    entrustService.deleteEntrust(entrustId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

}
