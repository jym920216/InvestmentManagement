package com.winsigns.investment.tradeService.command;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.winsigns.investment.tradeService.constant.InstructionType;

public class CommitInstructionCommand {

  // 指令序号
  private Long instructionId;

  // 投资组合
  private Long portfolioId;

  // 投资标的
  private Long securityId;

  // 投资服务
  private String investSvc;

  // 投资方向
  private String investDirection;

  // 币种
  private String currency;

  // 成本价
  private Double costPrice;

  // 数量类型
  @Enumerated(EnumType.STRING)
  private InstructionType volumeType;

  // 指令数量
  private Integer quantity;

  // 指令金额
  private Double amount;

  public Long getInstructionId() {
    return instructionId;
  }

  public void setInstructionId(Long instructionId) {
    this.instructionId = instructionId;
  }

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

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Double getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(Double costPrice) {
    this.costPrice = costPrice;
  }

  public InstructionType getVolumeType() {
    return volumeType;
  }

  public void setVolumeType(InstructionType volumeType) {
    this.volumeType = volumeType;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

}
