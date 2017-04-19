package com.winsigns.investment.fundService.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winsigns.investment.framework.integration.AbstractIntegration;
import com.winsigns.investment.fundService.command.CreateCashPoolCommand;
import com.winsigns.investment.fundService.constant.CurrencyCode;
import com.winsigns.investment.fundService.model.ExternalCapitalAccount;
import com.winsigns.investment.fundService.model.FundAccount;
import com.winsigns.investment.fundService.model.FundAccountCapitalPool;
import com.winsigns.investment.fundService.model.FundAccountCapitalPool.FundAccoutCapitalDetail;

/**
 * Created by colin on 2017/2/28.
 */

@Component
public class InventoryServiceIntegration extends AbstractIntegration {

  private static String INVENTORY_SERVICE = "inventory-service";

  private String getECACashPoolPath = "/eca-cash-pools?externalCapitalAccountId=";

  private String postECACashPoolPath = "/eca-cash-pools";

  private String getFACapitalDetails = "/fa-capital-details?externalCapitalAccountId=%d";

  @Override
  public String getIntegrationName() {
    return INVENTORY_SERVICE;
  }

  @HystrixCommand(fallbackMethod = "defaultECACashPools")
  public JsonNode getECACashPools(Long externalCapitalAccountId) {

    JsonNode node =
        this.getNode(this.getIntegrationURI() + getECACashPoolPath + externalCapitalAccountId);
    return node.get("_embedded").get("eca-cash-pools");
  }

  public JsonNode defaultECACashPools(Long externalCapitalAccountId) {
    return null;
  }

  public boolean createECACashPools(Long externalCapitalAccountId,
      Collection<CurrencyCode> supportedCurrency) {

    CreateCashPoolCommand cashChangeCommand = new CreateCashPoolCommand();
    cashChangeCommand.setExternalCapitalAccountId(externalCapitalAccountId);

    for (CurrencyCode currency : supportedCurrency) {
      cashChangeCommand.setCurrency(currency);

      HttpEntity<CreateCashPoolCommand> requestEntity =
          new HttpEntity<CreateCashPoolCommand>(cashChangeCommand);

      restTemplate.postForEntity(this.getIntegrationURI() + postECACashPoolPath, requestEntity,
          String.class);
    }
    return true;
  }

  /**
   * 获取产品账户属性
   * 
   * @param account
   * @return
   */
  @HystrixCommand(fallbackMethod = "defaultFundAccountCapitalDetails")
  public List<FundAccountCapitalPool> queryFundAccountCapitalDetails(
      ExternalCapitalAccount account) {
    List<FundAccountCapitalPool> result = new ArrayList<FundAccountCapitalPool>();

    String node = this
        .getJson(this.getIntegrationURI() + String.format(getFACapitalDetails, account.getId()));

    // 每一个币种下罗列所有产品账户
    for (CurrencyCode currency : account.getAccountType().getSupportedCurrency()) {

      FundAccountCapitalPool pool = new FundAccountCapitalPool();
      pool.setCurrency(currency);
      pool.setCurrencyLabel(currency.i18n());

      for (FundAccount fundAccount : account.getFund().getFundAccounts()) {
        List<Object> details = JsonPath.read(node, "$._embedded.fa-capital-details[?]",
            Filter.filter(Criteria.where("fundAccountId").is(fundAccount.getId()).and("currency")
                .is(currency.name())));

        FundAccoutCapitalDetail detail = new FundAccoutCapitalDetail();
        detail.setFundAccountId(fundAccount.getId());
        detail.setName(fundAccount.getName());
        if (!details.isEmpty()) {
          detail.setId(Long.valueOf(JsonPath.read(details.get(0), "$.id").toString()));
          detail.setCash(JsonPath.read(details.get(0), "$.cash"));
        }
        pool.getDetails().add(detail);
      }
      result.add(pool);
    }
    return result;
  }

  public List<FundAccountCapitalPool> defaultFundAccountCapitalDetails(
      ExternalCapitalAccount account) {
    return new ArrayList<FundAccountCapitalPool>();
  }
}
