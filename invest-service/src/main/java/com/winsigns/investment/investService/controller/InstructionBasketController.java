package com.winsigns.investment.investService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.core.Relation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.investService.command.CreateInstructionBasketCommand;
import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionBasket;
import com.winsigns.investment.investService.resource.InstructionResource;
import com.winsigns.investment.investService.resource.InstructionResourceAssembler;
import com.winsigns.investment.investService.service.InstructionService;

@RestController
@RequestMapping(path = "/instruction-baskets",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class InstructionBasketController {

  @Autowired
  InstructionService instructionService;

  /**
   * 创建一个篮子
   * 
   * @param instructionCommand
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createInstruction(@RequestBody CreateInstructionBasketCommand command) {

    InstructionBasket basket = instructionService.addInstructionBasket(command);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(InstructionBasketController.class).readInstructionBasket(basket.getId()))
            .toUri());
    return new ResponseEntity<Object>(basket, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 获取一个指令篮子以及下面的指令
   * 
   * @param instructionBasketId
   * @return
   */
  @GetMapping("/{instructionBasketId}")
  public InstructionResource readInstructionBasket(@PathVariable Long instructionBasketId) {
    InstructionBasket basket = instructionService.readInstructionBasket(instructionBasketId);
    InstructionResource resource = new InstructionResourceAssembler()
        .toResource(instructionService.readInstruction(instructionBasketId));

    resource.add(Instruction.class.getAnnotation(Relation.class).collectionRelation(),
        new InstructionResourceAssembler().toResources(basket.getInstructions()));
    return resource;
  }

  /**
   * 在篮子下创建一条指令
   * 
   * @param instructionCommand
   * @return
   */
  @PostMapping("/{instructionBasketId}")
  public ResponseEntity<?> createInstruction(@PathVariable Long instructionBasketId,
      @RequestBody CreateInstructionCommand instructionCommand) {

    Instruction instruction =
        instructionService.addInstructionOfBasket(instructionBasketId, instructionCommand);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(InstructionController.class).readInstruction(instruction.getId())).toUri());
    return new ResponseEntity<Object>(instruction, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 删除一个篮子以及其下所有指令
   * 
   * @param instructionBasketId
   * @return
   */
  @DeleteMapping("/{instructionBasketId}")
  public ResponseEntity<?> deleteInstruction(@PathVariable Long instructionBasketId) {
    instructionService.deleteInstruction(instructionBasketId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }
}
