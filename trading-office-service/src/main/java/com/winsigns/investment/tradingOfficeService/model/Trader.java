package com.winsigns.investment.tradingOfficeService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.tradingOfficeService.constant.TraderStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Trader extends AbstractEntity {

  @Getter
  @Setter
  private Long traderId;

  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private TraderStatus status;

}
