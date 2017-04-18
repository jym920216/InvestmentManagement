package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.inventoryService.measure.FACapitalDetailMHT;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "capital-detail", collectionRelation = "capital-details")
public class FundAccountCapitalDetail extends MeasureHost {

  // 具体的资金服务的资金
  @ManyToOne
  @JsonIgnore
  @Getter
  @Setter
  private FundAccountCapitalPool capitalPool;

  // 外部资金账户id
  @Getter
  @Setter
  private Long externalCapitalAccountId;

  // 现金
  @Getter
  @Setter
  private Double cash;

  // 可用资金
  @Getter
  @Setter
  private Double availableCapital;

  // 可取资金
  @Getter
  @Setter
  private Double desirableCapital;

  @OneToMany(mappedBy = "capitalDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  List<FundAccountCapitalSerial> fundAccountCapitalSerials =
      new ArrayList<FundAccountCapitalSerial>();

  @Override
  protected Class<? extends MeasureHostType> defineType() {
    return FACapitalDetailMHT.class;
  }

  @Override
  public MeasureHost parent() {
    // TODO Auto-generated method stub
    return null;
  }

  public Double changeCash(Double cash) {
    this.cash += cash;
    return this.cash;
  }

  public Double changeAvailableCapital(Double availableCapital) {
    this.availableCapital += availableCapital;
    return this.availableCapital;
  }

  public Double changeDesirableCapital(Double desirableCapital) {
    this.desirableCapital += desirableCapital;
    return this.desirableCapital;
  }
}
