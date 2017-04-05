package com.winsigns.investment.webGateway.http.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by wfl on 17-4-5.
 */
@Service
public class FundService {
  private static String FUND_SERVICE = "fund-service";

  @Autowired
  private LoadBalancerClient loadBalancer;

  @Autowired
  private RestOperations restTemplate;

  static protected class Item {

    protected Long id;

    protected String name;

    protected String shortName;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getShortName() {
      return shortName;
    }

    public void setShortName(String shortName) {
      this.shortName = shortName;
    }
  }

  final static String strEmbedded = "_embedded";
  final static String strLabel = "label";
  final static String strValue = "value";
  final static String strChildren = "childrean";
  final static String strOptions = "options";
  final static String strFunds = "funds";
  final static String strFundAccounts = "fundAccounts";
  final static String strPortfolios = "portfolios";

  public List getFundTree() {

    ResponseEntity<String> resultStr = restTemplate
        .getForEntity(loadBalancer.choose(FUND_SERVICE).getUri() + "/funds/tree", String.class);

    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    List<?> list = new ArrayList<>();

    try {
      JsonNode tmp = objectMapper.readerFor(JsonNode.class).readValue(resultStr.getBody());
      JsonNode root = tmp.get(strEmbedded).get("funds");

      ObjectNode newRoot = objectMapper.createObjectNode();
      ArrayNode options = objectMapper.createArrayNode();

      for (JsonNode node : root) {
        Item fundItem = objectMapper.readerFor(Item.class).readValue(node);
        ObjectNode fundNode = objectMapper.createObjectNode();
        fundNode.put(strValue, fundItem.getId());
        fundNode.put(strLabel, fundItem.getShortName());

        JsonNode fundaccounts = node.get(strEmbedded).get(strFundAccounts);
        ArrayNode newfundaccounts = objectMapper.createArrayNode();
        for (JsonNode fundaccount : fundaccounts) {
          Item fundAccountItem = objectMapper.readerFor(Item.class).readValue(fundaccount);
          ObjectNode fundAccountNode = objectMapper.createObjectNode();
          fundAccountNode.put(strValue, fundAccountItem.getId());
          fundAccountNode.put(strLabel, fundAccountItem.getName());

          JsonNode portfolios = fundaccount.get(strEmbedded).get(strPortfolios);
          ArrayNode newPortfolios = objectMapper.createArrayNode();
          for (JsonNode portfolio : portfolios) {
            Item portfolioItem = objectMapper.readerFor(Item.class).readValue(portfolio);
            ObjectNode portfolioNode = objectMapper.createObjectNode();
            portfolioNode.put(strValue, portfolioItem.getId());
            portfolioNode.put(strLabel, portfolioItem.getName());
            newPortfolios.add(portfolioNode);
          }

          fundAccountNode.set(strChildren, newPortfolios);
          newfundaccounts.add(fundAccountNode);
        }
        fundNode.set(strChildren, newfundaccounts);

        options.add(fundNode);
      }
      newRoot.set(strOptions, options);
      System.out.print(newRoot);
    } catch (IOException e) {
      throw new RuntimeException(e);

    }
    return list;
  }

}
