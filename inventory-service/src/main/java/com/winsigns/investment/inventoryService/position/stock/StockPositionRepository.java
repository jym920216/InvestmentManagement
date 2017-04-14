package com.winsigns.investment.inventoryService.position.stock;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPositionRepository extends JpaRepository<StockPosition, Long> {
  public List<StockPosition> findByPortfolioIdAndSecurityId(Long portfolioId, Long securityId);
}
