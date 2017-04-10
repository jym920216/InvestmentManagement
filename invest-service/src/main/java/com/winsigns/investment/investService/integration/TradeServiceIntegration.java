package com.winsigns.investment.investService.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.investService.command.SendInstructionCommand;
import com.winsigns.investment.investService.model.Instruction;

/**
 * 与trade-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class TradeServiceIntegration extends AbstractIntegration {

  private Logger log = LoggerFactory.getLogger(TradeServiceIntegration.class);

  private String tradeURL = "/trades";

  @Override
  public String getIntegrationName() {
    return "trade-service";
  }

  public boolean commitInstruction(Instruction instruction) {

    SendInstructionCommand command = new SendInstructionCommand();

    HttpEntity<SendInstructionCommand> requestEntity =
        new HttpEntity<SendInstructionCommand>(command);

    try {
      ResponseEntity<String> resultStr = restTemplate
          .postForEntity(this.getIntegrationURI() + tradeURL, requestEntity, String.class);

      if (resultStr.getStatusCode().compareTo(HttpStatus.OK) == 0) {
        return true;
      }
      return false;
    } catch (RestClientException e) {
      log.warn(e.getMessage());
      return false;
    }
  }
}
