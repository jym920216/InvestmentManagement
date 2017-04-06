package com.winsigns.investment.investService.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.investService.constant.CurrencyCode;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.constant.InstructionType;

import lombok.Getter;
import lombok.Setter;

/**
 * 指令的实体
 * 
 * @author yimingjin
 *
 */
@Entity
@Relation(value = "instruction", collectionRelation = "instructions")
public class Instruction extends AbstractEntity {
  // 投资组合
  @Getter
  @Setter
  private Long portfolioId;

  // 投资标的
  @Getter
  @Setter
  private Long securityId;

  // 投资服务
  @Getter
  @Setter
  private String investService;

  // 投资方向
  @Getter
  @Setter
  private String investDirection;

  // 币种
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  // 成本价
  @Getter
  @Setter
  private Double costPrice;

  // 数量类型
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private InstructionType volumeType;

  // 指令数量
  @Getter
  @Setter
  private Double quantity;

  // 指令金额
  @Getter
  @Setter
  private Double amount;

  // 投资经理
  @Getter
  @Setter
  private Long investManagerId;

  // 指令状态
  @Getter
  @Setter
  private InstructionStatus executionStatus;

  // 创建日期
  @Getter
  @Setter
  @Temporal(TemporalType.DATE)
  private Date createDate = new Date();

  @OneToMany(mappedBy = "instruction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Setter
  @Getter
  private List<InstructionMessage> messages = new ArrayList<InstructionMessage>();

  public void addInstructionMessage(InstructionMessage message) {
    this.messages.add(message);
  }
}
