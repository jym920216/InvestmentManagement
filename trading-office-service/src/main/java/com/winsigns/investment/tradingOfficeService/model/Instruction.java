package com.winsigns.investment.tradingOfficeService.model;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Instruction extends AbstractEntity {

  @Setter
  @Getter
  @JsonProperty("id")
  private Long instructionId;

  @Setter
  @Getter
  private Long traderId;


  @Getter
  @Setter
  private Date allottedTime;
}
