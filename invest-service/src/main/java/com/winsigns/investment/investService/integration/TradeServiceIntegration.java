package com.winsigns.investment.investService.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.integration.AbstractIntegration;

/**
 * 与trade-service的交互
 * 
 * @author yimingjin
 *
 */
@Component
public class TradeServiceIntegration extends AbstractIntegration {

  private String portfolioURL = "/portfolios/%d";

  @Override
  public String getIntegrationName() {
    return "trade-service";
  }

  @HystrixCommand(fallbackMethod = "defaultPortfolioInvestManager")
  public Long getPortfolioInvestManager(Long portfolioId) {
    ResponseEntity<String> resultStr = restTemplate.getForEntity(
        this.getIntegrationURI() + String.format(portfolioURL, portfolioId), String.class);
    return JsonPath.read(resultStr.getBody(), "$.investManagerId");
  }

  public Long defaultPortfolioInvestManager(Long portfolioId) {
    return null;
  }
}
