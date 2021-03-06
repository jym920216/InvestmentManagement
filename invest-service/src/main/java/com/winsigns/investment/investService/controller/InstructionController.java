package com.winsigns.investment.investService.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
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

import com.winsigns.investment.framework.hal.Resources;
import com.winsigns.investment.investService.command.BatchDeleteInstructionCommand;
import com.winsigns.investment.investService.command.CreateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionCommand;
import com.winsigns.investment.investService.command.UpdateInstructionTraderCommand;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.resource.InstructionResource;
import com.winsigns.investment.investService.resource.InstructionResourceAssembler;
import com.winsigns.investment.investService.service.InstructionService;

@RestController
@RequestMapping(path = "/instructions",
    produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
public class InstructionController {

  @Autowired
  InstructionService instructionService;

  /**
   * 获取指令
   * 
   * @return
   */
  @GetMapping
  public Resources<InstructionResource> readInstructions(
      @RequestParam(required = false) Long investManagerId,
      @RequestParam(required = false) Long traderId,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date beginDate,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date endDate) {
    Link link = linkTo(InstructionController.class).withSelfRel();
    Link deleteLink =
        linkTo(methodOn((InstructionController.class)).deleteInstructions(null)).withRel("deletes");
    Link investmanagerLink = linkTo(methodOn((InstructionController.class))
        .getInstructionsByInvestManager(investManagerId, beginDate, endDate))
            .withRel("by-investmanager");
    Link traderLink = linkTo(methodOn((InstructionController.class))
        .getInstructionsByTrader(traderId, beginDate, endDate)).withRel("by-trader");
    Link unassignLink = linkTo(methodOn((InstructionController.class)).getUnassignedInstructions())
        .withRel("by-unassign");
    Collection<Instruction> instructions = instructionService
        .findNormalInstructionByCondition(investManagerId, traderId, beginDate, endDate);
    return new Resources<InstructionResource>(
        new InstructionResourceAssembler().toResources(instructions), link, deleteLink,
        investmanagerLink, traderLink, unassignLink);
  }

  /**
   * 投资经理获取指令
   * 
   * @param investManagerId
   * @param beginDate
   * @param endDate
   * @return
   */
  @GetMapping("/investmanager")
  public Resources<InstructionResource> getInstructionsByInvestManager(
      @RequestParam(required = false) Long investManagerId,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date beginDate,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date endDate) {
    Link link = linkTo(methodOn(InstructionController.class)
        .getInstructionsByInvestManager(investManagerId, beginDate, endDate)).withSelfRel();
    List<Instruction> instructions =
        instructionService.getInstructionsByInvestManager(investManagerId, beginDate, endDate);
    return new Resources<InstructionResource>(
        new InstructionResourceAssembler().toResources(instructions), link);
  }

  /**
   * 交易员获取指令
   * 
   * @param traderId
   * @param beginDate
   * @param endDate
   * @return
   */
  @GetMapping("/trader")
  public Resources<InstructionResource> getInstructionsByTrader(
      @RequestParam(required = false) Long traderId,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date beginDate,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date endDate) {
    Link link = linkTo(
        methodOn(InstructionController.class).getInstructionsByTrader(traderId, beginDate, endDate))
            .withSelfRel();
    List<Instruction> instructions =
        instructionService.getInstructionsByTrader(traderId, beginDate, endDate);
    return new Resources<InstructionResource>(
        new InstructionResourceAssembler().toResources(instructions), link);
  }

  /**
   * 未分配的指令
   * 
   * @return
   */
  @GetMapping("/unassign")
  public Resources<InstructionResource> getUnassignedInstructions() {
    Link link =
        linkTo(methodOn(InstructionController.class).getUnassignedInstructions()).withSelfRel();
    List<Instruction> instructions = instructionService.getUnassignedInstructions();
    return new Resources<InstructionResource>(
        new InstructionResourceAssembler().toResources(instructions), link);
  }

  /**
   * 创建一条指令
   * 
   * @param instructionCommand
   * @return
   */
  @PostMapping
  public ResponseEntity<?> createInstruction(
      @RequestBody CreateInstructionCommand instructionCommand) {

    Instruction instruction = instructionService.addInstruction(instructionCommand);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(
        linkTo(methodOn(InstructionController.class).readInstruction(instruction.getId())).toUri());
    return new ResponseEntity<Object>(instruction, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * 批量删除指令
   * <p>
   * delete 无法传入body，暂时采用post
   * 
   * @param command
   * @return
   */
  @PostMapping("/deletes")
  public ResponseEntity<?> deleteInstructions(@RequestBody BatchDeleteInstructionCommand command) {
    instructionService.deleteInstruction(command.getInstructionIds());
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  /**
   * 获取一条具体的指令
   * 
   * @param instructionId
   * @return
   */
  @GetMapping("/{instructionId}")
  public InstructionResource readInstruction(@PathVariable Long instructionId) {
    return new InstructionResourceAssembler()
        .toResource(instructionService.readInstruction(instructionId));
  }

  /**
   * 修改一条指令
   * 
   * @param instructionId
   * @param instructionCommand
   * @return
   */
  @PutMapping("/{instructionId}")
  public InstructionResource updateInstruction(@PathVariable Long instructionId,
      @RequestBody UpdateInstructionCommand instructionCommand) {
    return new InstructionResourceAssembler()
        .toResource(instructionService.updateInstruction(instructionId, instructionCommand));
  }

  /**
   * 删除一条指令
   * 
   * @param instructionId
   * @return
   */
  @DeleteMapping("/{instructionId}")
  public ResponseEntity<?> deleteInstruction(@PathVariable Long instructionId) {
    instructionService.deleteInstruction(instructionId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  /**
   * 提交一条指令
   * 
   * @param instructionId
   * @return
   */
  @PostMapping("/{instructionId}/commit")
  public InstructionResource commitInstruction(@PathVariable Long instructionId) {

    Instruction thisInstruction = instructionService.commitInstruction(instructionId);

    return new InstructionResourceAssembler().toResource(thisInstruction);
  }

  /**
   * 修改一条指令的交易员
   * 
   * @param instructionId
   * @param instructionCommand
   * @return
   */
  @PutMapping("/{instructionId}/trader")
  public InstructionResource updateInstructionForTrader(@PathVariable Long instructionId,
      @RequestBody UpdateInstructionTraderCommand command) {
    command.setInstructionId(instructionId);
    return new InstructionResourceAssembler()
        .toResource(instructionService.updateInstructionForTrader(command));
  }
}
