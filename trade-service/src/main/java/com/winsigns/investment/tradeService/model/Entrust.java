package com.winsigns.investment.tradeService.model;

import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.tradeService.constant.InstructionVolumeType;


@Entity
public class Entrust extends AbstractEntity {

  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 投资服务
  private String investSvc;

  // 投资方向
  private String investDirection;

  // 币种
  private Currency currency;

  // 成本价
  private Double costPrice;

  // 数量类型
  @Enumerated(EnumType.STRING)
  private InstructionVolumeType volumeType;

  // 指令数量
  private Double quantity;

  // 指令金额
  private Double amount;

  public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public Long getSecurityId() {
    return securityId;
  }

  public void setSecurityId(Long securityId) {
    this.securityId = securityId;
  }

  public String getInvestSvc() {
    return investSvc;
  }

  public void setInvestSvc(String investSvc) {
    this.investSvc = investSvc;
  }

  public String getInvestDirection() {
    return investDirection;
  }

  public void setInvestDirection(String investDirection) {
    this.investDirection = investDirection;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Double getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(Double costPrice) {
    this.costPrice = costPrice;
  }

  public InstructionVolumeType getVolumeType() {
    return volumeType;
  }

  public void setVolumeType(InstructionVolumeType volumeType) {
    this.volumeType = volumeType;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

}
