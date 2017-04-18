package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.model.Capital;
import com.winsigns.investment.inventoryService.repository.CapitalRepository;

@Service
public class FundAccountCapitalService {
  @Autowired
  private CapitalRepository fundAccountCapitalRepository;

  public Collection<Capital> findAll() {
    return fundAccountCapitalRepository.findAll();
  }

  public Capital findOne(Long fundAccountCapitalId) {
    return fundAccountCapitalRepository.findOne(fundAccountCapitalId);
  }

  public Capital addFundAccountCapital(
      CreateFundAccountCapitalCommand createFundAccountCapitalCommand) {
    Capital fundAccountCapital = fundAccountCapitalRepository.findByFundAccountIdAndCurrency(
        createFundAccountCapitalCommand.getFundAccountId(),
        createFundAccountCapitalCommand.getCurrency());

    if (fundAccountCapital == null) {
      fundAccountCapital = new Capital();

      fundAccountCapital.setFundAccountId(createFundAccountCapitalCommand.getFundAccountId());
      fundAccountCapital.setCurrency(createFundAccountCapitalCommand.getCurrency());
      fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    }
    return fundAccountCapital;
  }

  public Capital setInvestmentLimit(Long faCapitalId,
      SetInvestmentLimitCommand setInvestmentLimitCommand) {
    Capital fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
    if (fundAccountCapital == null)
      return null;
    fundAccountCapital.setInvestmentLimit(setInvestmentLimitCommand.getInvestmentLimit());
    fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    return fundAccountCapital;
  }

}
