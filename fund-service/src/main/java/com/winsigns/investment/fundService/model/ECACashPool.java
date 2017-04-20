package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import com.winsigns.investment.fundService.constant.CurrencyCode;

import lombok.Data;

@Data
public class ECACashPool {

  private Long id;

  private CurrencyCode currency;

  private String currencyLabel;

  private Double unassignedCapital;

  @Data
  public static class CapitalDetail {
    private Long id;

    private Long fundAccountId;

    private String name;

    private Double cash;
  }

  private List<CapitalDetail> details = new ArrayList<CapitalDetail>();
}
