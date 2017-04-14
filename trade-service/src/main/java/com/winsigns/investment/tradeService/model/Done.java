package com.winsigns.investment.tradeService.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.tradeService.constant.DoneStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "done", collectionRelation = "dones")
public class Done extends AbstractEntity {
  // 委托
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private Entrust entrust;

  // 成交价格
  @Getter
  @Setter
  private Double donePrice;

  // 成交数量
  @Getter
  @Setter
  private Long doneQuantity;

  // 委托状态
  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
  private DoneStatus status = DoneStatus.NORMAL;

  // 成交时间
  @Getter
  @Setter
  @Temporal(TemporalType.TIMESTAMP)
  private Date doneTime = new Timestamp(System.currentTimeMillis());

}
