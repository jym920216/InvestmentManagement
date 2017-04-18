package com.winsigns.investment.inventoryService.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalSerial;

public interface FundAccountCapitalSerialRepository
    extends JpaRepository<FundAccountCapitalSerial, Long> {

  @Query("select sum(assignedCash) from FundAccountCapitalSerial a where a.capitalDetail = :capitalDetail and a.assignedDate = :assignedDate")
  public Double findByCapitalDetailAndAssignedDate(
      @Param("capitalDetail") CapitalDetail capitalDetail,
      @Param("assignedDate") Date assignedDate);

}
