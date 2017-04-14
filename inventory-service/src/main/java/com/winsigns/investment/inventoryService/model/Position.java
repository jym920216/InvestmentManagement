package com.winsigns.investment.inventoryService.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.hateoas.core.Relation;

import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "position", collectionRelation = "positions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@DiscriminatorValue("position")
public class Position extends MeasureHost {

  @Getter
  @Setter
  private Long portfolioId;

  @Getter
  @Setter
  private Long externalTradeAccountId;

  @Getter
  @Setter
  private Long securityId;

  @Override
  protected Class<? extends MeasureHostType> defineType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MeasureHost parent() {
    // TODO Auto-generated method stub
    return null;
  }

}
