package com.winsigns.investment.inventoryService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.inventoryService.model.Capital;
import com.winsigns.investment.inventoryService.model.CapitalDetail;

public interface CapitalDetailRepository extends JpaRepository<CapitalDetail, Long> {
  public CapitalDetail findByCapitalAndExternalCapitalAccountId(Capital capital,
      Long externalCapitalAccountId);

  public List<CapitalDetail> findByCapital(Capital capital);

  public CapitalDetail findByCapitalAndExternalCapitalAccountIdIsNull(Capital capital);
}


