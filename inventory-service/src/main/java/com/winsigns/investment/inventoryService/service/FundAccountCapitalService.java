package com.winsigns.investment.inventoryService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalPoolCommand;
import com.winsigns.investment.inventoryService.command.SetInvestmentLimitCommand;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalPoolRepository;

@Service
public class FundAccountCapitalService {
  @Autowired
  private FundAccountCapitalPoolRepository fundAccountCapitalRepository;

  public Collection<FundAccountCapitalPool> findAll() {
    return fundAccountCapitalRepository.findAll();
  }

  public FundAccountCapitalPool findOne(Long fundAccountCapitalId) {
    return fundAccountCapitalRepository.findOne(fundAccountCapitalId);
  }

  public FundAccountCapitalPool addFundAccountCapital(
      CreateFundAccountCapitalPoolCommand createFundAccountCapitalCommand) {
    FundAccountCapitalPool fundAccountCapital = fundAccountCapitalRepository.findByFundAccountIdAndCurrency(
        createFundAccountCapitalCommand.getFundAccountId(),
        createFundAccountCapitalCommand.getCurrency());

    if (fundAccountCapital == null) {
      fundAccountCapital = new FundAccountCapitalPool();

      fundAccountCapital.setFundAccountId(createFundAccountCapitalCommand.getFundAccountId());
      fundAccountCapital.setCurrency(createFundAccountCapitalCommand.getCurrency());
      fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    }
    return fundAccountCapital;
  }

  public FundAccountCapitalPool setInvestmentLimit(Long faCapitalId,
      SetInvestmentLimitCommand setInvestmentLimitCommand) {
    FundAccountCapitalPool fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
    if (fundAccountCapital == null)
      return null;
    fundAccountCapital.setInvestmentLimit(setInvestmentLimitCommand.getInvestmentLimit());
    fundAccountCapital = fundAccountCapitalRepository.save(fundAccountCapital);
    return fundAccountCapital;
  }

}
