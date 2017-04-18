package com.winsigns.investment.inventoryService.capital.generalCapital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.capital.common.AbstractCapitalService;
import com.winsigns.investment.inventoryService.constant.CurrencyCode;
import com.winsigns.investment.inventoryService.exception.ResourceApplicationExcepiton;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;

@Service
public class GeneralCapitalService extends AbstractCapitalService {

  public enum ErrorCode {
    // 未找到资金
    NOT_FIND_CAPITAL_RESOURCE,
    // 可用资金不足
    AVAILABLE_CAPITAL_NOT_ENOUGH;

    public String toString() {
      return "SSEAStockPositionService:" + this.name();
    }
  }

  @Autowired
  GeneralCapitalRepository generalCapitalRepository;

  @Autowired
  CapitalDetailRepository capitalDetailRepository;

  @Override
  public void apply(Long fundAccountId, CurrencyCode currency, Double appliedCapital)
      throws ResourceApplicationExcepiton {

    GeneralCapital capital =
        generalCapitalRepository.findByFundAccountIdAndCurrency(fundAccountId, currency);
    if (capital == null) {
      throw new ResourceApplicationExcepiton(ErrorCode.NOT_FIND_CAPITAL_RESOURCE.toString());
    }

    CapitalDetail capitalDetail =
        capitalDetailRepository.findByCapitalAndExternalCapitalAccountIdIsNull(capital);
    if (capitalDetail == null) {
      throw new ResourceApplicationExcepiton(ErrorCode.NOT_FIND_CAPITAL_RESOURCE.toString());
    }

    if (appliedCapital.doubleValue() >= 0) { // 卖出
      capitalDetail.changeAvailableCapital(appliedCapital.doubleValue());
      capitalDetail.changeCash(appliedCapital.doubleValue());
    } else { // 买入
      capitalDetail.changeAvailableCapital(appliedCapital.doubleValue());
      capitalDetail.changeCash(appliedCapital.doubleValue());
      if (capitalDetail.getAvailableCapital().doubleValue() < 0) {
        throw new ResourceApplicationExcepiton(ErrorCode.AVAILABLE_CAPITAL_NOT_ENOUGH.toString());
      }
    }
    capitalDetailRepository.save(capitalDetail);
  }
}
