package com.winsigns.investment.inventoryService.resource;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;

import lombok.Getter;

public class FundAccountCapitalDetailResource extends HALResponse<FundAccountCapitalDetail> {

  @Getter
  final Long fundAccountId;

  @Getter
  final CurrencyCode currency;

  @Getter
  final String currencyLabel;

  public FundAccountCapitalDetailResource(FundAccountCapitalDetail fundAccountCapitalDetail) {
    super(fundAccountCapitalDetail);
    this.fundAccountId = fundAccountCapitalDetail.getCapitalPool().getFundAccountId();
    this.currency = fundAccountCapitalDetail.getCapitalPool().getCurrency();
    this.currencyLabel = this.currency.i18n();
  }

}
