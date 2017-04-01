package com.winsigns.investment.investService.model;

import java.util.Currency;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.investService.constant.InstructionStatus;
import com.winsigns.investment.investService.constant.InstructionType;

import lombok.Data;

/**
 * 指令的实体
 * 
 * @author yimingjin
 *
 */
@Data
@Entity
@Relation(value = "instruction", collectionRelation = "instructions")
public class Instruction extends AbstractEntity {

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
  private InstructionType volumeType;

  // 指令数量
  private Double quantity;

  // 指令金额
  private Double amount;

  // 指令状态
  private InstructionStatus instructionStatus;

  // 创建日期
  @Temporal(TemporalType.DATE)
  private Date createDate;

}
