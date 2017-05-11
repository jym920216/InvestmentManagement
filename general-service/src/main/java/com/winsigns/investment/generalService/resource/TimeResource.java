package com.winsigns.investment.generalService.resource;


import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.winsigns.investment.framework.hal.ResourceSupport;

import lombok.Getter;

@Relation(value = "time", collectionRelation = "times")
public class TimeResource extends ResourceSupport {

  @Getter
  @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMdd HH:mm:ss.sss")
  private final Date time;

  public TimeResource(Date time) {
    this.time = time;
  }

}
