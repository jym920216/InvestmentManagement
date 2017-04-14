package com.winsigns.investment.tradeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winsigns.investment.tradeService.model.Done;

public interface DoneRepository extends JpaRepository<Done, Long> {

}
