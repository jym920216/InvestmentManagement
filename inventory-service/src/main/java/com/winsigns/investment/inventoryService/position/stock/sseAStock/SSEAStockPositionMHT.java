package com.winsigns.investment.inventoryService.position.stock.sseAStock;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.measure.MeasureHost;
import com.winsigns.investment.framework.measure.MeasureHostType;

@Component
public class SSEAStockPositionMHT extends MeasureHostType {

  @Override
  protected JpaRepository<? extends MeasureHost, Long> getRepository() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void listen(ConsumerRecord<String, String> record) {
    // TODO Auto-generated method stub

  }

}
