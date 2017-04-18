package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "capital", collectionRelation = "capitals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "externalCapitalAccountType")
@DiscriminatorValue("capital")
public class Capital extends AbstractEntity {

  /*
   * 产品账户序号
   */
  @Getter
  @Setter
  private Long fundAccountId;

  /*
   * 币种
   */
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private CurrencyCode currency;

  /*
   * 投资限额
   */
  @Getter
  @Setter
  private Double investmentLimit;

  @OneToMany(mappedBy = "capital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  private List<CapitalDetail> capitalDetails = new ArrayList<CapitalDetail>();

}
