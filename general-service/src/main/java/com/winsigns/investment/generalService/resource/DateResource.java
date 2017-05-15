package com.winsigns.investment.generalService.resource;


import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.winsigns.investment.framework.hal.ResourceSupport;

import lombok.Getter;

@Relation(value = "date", collectionRelation = "dates")
public class DateResource extends ResourceSupport {

  @Getter
  @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMdd")
  private final Date date;

  public DateResource(Date date) {
    this.date = date;
  }

}
