package com.winsigns.investment.tradingOfficeService.model;

import javax.persistence.Entity;

import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class TradingOffice extends AbstractEntity {

  @Getter
  @Setter
  private Long userId;
}
