package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;

public interface FundAccountCapitalPoolRepository
    extends JpaRepository<FundAccountCapitalPool, Long> {

  public List<FundAccountCapitalPool> findByFundAccountId(Long fundAccountId);
}
