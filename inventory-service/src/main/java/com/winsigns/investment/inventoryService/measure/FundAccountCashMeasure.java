package com.winsigns.investment.inventoryService.measure;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.measure.TradingMeasureValue;
import com.winsigns.investment.framework.model.OperatorEntity;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalSerialRepository;

@Component
public class FundAccountCashMeasure extends Measure {

  @Autowired
  CapitalDetailRepository fundAccountCapitalDetailRepository;

  @Autowired
  FundAccountCapitalSerialRepository fundAccountCapitalSerialRepository;

  @Autowired
  FACapitalDetailMHT faCapitalDetailMHT;

  @Override
  public MeasureHostType getSupportedHostType() {
    return faCapitalDetailMHT;
  }

  @Override
  public List<Class<? extends OperatorEntity>> getConcernedOperator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IMeasure> getDependentMeasure() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected TradingMeasureValue doCalculate(MeasureHost measureHost, boolean isFloat,
      String version) {

    CapitalDetail fundAccountCapitalDetail = (CapitalDetail) measureHost;

    Double value = fundAccountCapitalSerialRepository
        .findByCapitalDetailAndAssignedDate(fundAccountCapitalDetail, new Date());

    return new TradingMeasureValue(fundAccountCapitalDetail, this, isFloat, version, value);

  }

}
