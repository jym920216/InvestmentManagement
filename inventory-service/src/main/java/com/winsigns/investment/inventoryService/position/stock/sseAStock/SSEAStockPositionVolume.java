package com.winsigns.investment.inventoryService.position.stock.sseAStock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.IMeasure;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;
import com.winsigns.investment.framework.measure.TradingMeasureValue;
import com.winsigns.investment.framework.model.OperatorEntity;

@Component
public class SSEAStockPositionVolume extends Measure {

  @Autowired
  SSEAStockPositionMHT sseAStockPositionMHT;

  @Override
  public MeasureHostType getSupportedHostType() {
    return sseAStockPositionMHT;
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
    // TODO Auto-generated method stub
    return null;
  }

  // // 成交量
  // private MockCalculateFactor tradeVolume =
  // new MockCalculateFactor("SSEAStockTradeServiceMHT.SSEAStockTradeVolume");
  //
  //
  // @Override
  // public List<ICalculateFactor> getCalculateFactors() {
  //
  // List<ICalculateFactor> factors = new ArrayList<ICalculateFactor>();
  // factors.add(tradeVolume);
  // return factors;
  // }
  //
  // @Override
  // protected TradingMeasureValue doCalculate(Long measureHostId, boolean isFloat, String version)
  // {
  // double value = 0.0;
  //
  // // for (IMeasureHost ts : position.getSupportedTradeService()) {
  // // value += ts.getMeasureValue(this, version, isFloat).getValue();
  // // }
  // // return new TradingMeasureValue(measureHost, this, value, version, isFloat);
  // return null;
  // }
}
