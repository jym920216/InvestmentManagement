package com.winsigns.investment.fundService.model;

import java.util.ArrayList;
import java.util.List;

import com.winsigns.investment.fundService.constant.CurrencyCode;

import lombok.Data;

@Data
public class FundAccountCapitalPool {

  private CurrencyCode currency;

  private String currencyLabel;

  @Data
  public static class FundAccoutCapitalDetail {
    private Long id;

    private Long fundAccountId;

    private String name;

    private Double cash;
  }

  private List<FundAccoutCapitalDetail> details = new ArrayList<FundAccoutCapitalDetail>();
}
