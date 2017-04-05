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
import com.winsigns.investment.webGateway.http.entity.Instruction;

/**
 * Created by wfl on 17-4-5.
 */
@Service
public class InvestService {
  private static String invest_SERVICE = "invest-service";

  @Autowired
  private LoadBalancerClient loadBalancer;

  @Autowired
  private RestOperations restTemplate;

  public List<Instruction> getAllInstruct() {
    ResponseEntity<String> resultStr = restTemplate
        .getForEntity(loadBalancer.choose(invest_SERVICE).getUri() + "/instructions", String.class);

    Object obj = resultStr.getBody();
    System.out.println(obj);
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    List<Instruction> list = new ArrayList<Instruction>();
    JsonNode node = null;
    try {
      node = objectMapper.readerFor(JsonNode.class).readValue(resultStr.getBody());
      JsonNode jsonNode = node.get("_embedded").get("instructions");
      for (JsonNode item : jsonNode) {
        Instruction instruction = objectMapper.readerFor(Instruction.class).readValue(item);

        list.add(instruction);
      }
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Instruction> getInstructById(String instructionId) {
    ResponseEntity<String> resultStr = restTemplate.getForEntity(
        loadBalancer.choose(invest_SERVICE).getUri() + "/instructions/" + instructionId,
        String.class);
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    List<Instruction> list = new ArrayList<Instruction>();
    JsonNode node = null;
    try {
      node = objectMapper.readerFor(JsonNode.class).readValue(resultStr.getBody());
      if (node != null) {
        Instruction instruction = new Instruction();
        instruction.setPortfolioId(node.get("id").asLong());
        instruction.setFundAccName("产品账户");
        instruction.setFundName("基金产品");
        instruction.setPortfolioName("投资组合");
        instruction.setSecurityCode("600000");
        instruction.setSecurityName("浦发银行");
        instruction.setAmount(1201.32);
        instruction.setCostPrice(25.32);
        instruction.setEditer(false);
        list.add(instruction);
      }
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
