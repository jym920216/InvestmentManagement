package com.winsigns.investment.investService.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.controller.InstructionController;
import com.winsigns.investment.investService.model.Instruction;

public class InstructionResourceAssembler
    extends ResourceAssemblerSupport<Instruction, InstructionResource> {

  final static String fundURL = "http://fund-service/funds/tree?investManagerId=%d";
  final static String securityURL = "http://TODO";


  public InstructionResourceAssembler() {
    super(InstructionController.class, InstructionResource.class);
  }

  @Override
  public InstructionResource toResource(Instruction instruction) {
    InstructionResource resource = createResourceWithId(instruction.getId(), instruction);
    if (instruction.getExecutionStatus() == InstructionStatus.DRAFT) {
      resource
          .add(linkTo(methodOn(InstructionController.class).commitInstruction(instruction.getId()))
              .withRel("commit"));
    }

    // 准备各项过滤条件的Link
    // 1.基金产品与组合
    // 2.投资标的
    resource.add(new Link(String.format(fundURL, instruction.getInvestManagerId()), "fundtree"));
    resource
        .add(new Link(String.format(securityURL, instruction.getInvestManagerId()), "security"));
    return resource;
  }

  @Override
  protected InstructionResource instantiateResource(Instruction entity) {
    return new InstructionResource(entity);
  }


}
