package com.winsigns.investment.tradingOfficeService.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class CurrentStrategy extends AbstractEntity {

  public enum CurrentStrategyType {
    ORG
  }

  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrentStrategyType type = CurrentStrategyType.ORG;

  @Getter
  @Setter
  private String currentStrategyName;


  @Getter
  @Setter
  private Date updateTime;
}
