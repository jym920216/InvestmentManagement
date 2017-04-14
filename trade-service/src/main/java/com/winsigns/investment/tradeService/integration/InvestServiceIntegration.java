package com.winsigns.investment.tradeService.integration;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.winsigns.investment.framework.integration.AbstractIntegration;

/**
 * 与trade-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class InvestServiceIntegration extends AbstractIntegration {

  static final String INVEST_SERVICE = "invest-service";

  private String instructionURL = "/instructions/%d";

  @Override
  public String getIntegrationName() {
    return INVEST_SERVICE;
  }

  public Long getInstructionSecurityId(Long instructionId) {

    ObjectNode node =
        getNode(this.getIntegrationURI() + String.format(instructionURL, instructionId));

    return node.get("securityId").asLong();

  }

}
