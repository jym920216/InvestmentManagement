package com.winsigns.investment.tradingOfficeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsigns.investment.tradingOfficeService.command.JoinTradingOfficeCommand;
import com.winsigns.investment.tradingOfficeService.command.LeaveTradingOfficeCommand;
import com.winsigns.investment.tradingOfficeService.command.UpdateTraderStatusCommand;
import com.winsigns.investment.tradingOfficeService.exception.TraderJoinedAleadyExcepiton;
import com.winsigns.investment.tradingOfficeService.exception.TraderLeftAleadyExcepiton;
import com.winsigns.investment.tradingOfficeService.exception.TraderNotNullExcepiton;
import com.winsigns.investment.tradingOfficeService.model.Trader;
import com.winsigns.investment.tradingOfficeService.repository.TraderRepository;

@Service
public class TraderService {

  @Autowired
  TraderRepository traderRepository;

  /**
   * 加入集中交易室
   * 
   * @param command
   * @return
   */
  public Trader joinTradingOffice(JoinTradingOfficeCommand command) {

    if (command.getUserId() == null) {
      throw new TraderNotNullExcepiton(new Object[] {command.getUserId()});
    }

    // TODO 外调获取交易员身份
    Trader trader = traderRepository.findByTraderId(command.getUserId());
    if (trader != null) {
      throw new TraderJoinedAleadyExcepiton(new Object[] {command.getUserId()});
    }

    trader = new Trader();
    trader.setTraderId(command.getUserId());
    trader.setStatus(command.getStatus());
    trader = traderRepository.save(trader);
    return trader;
  }

  /**
   * 离开集中交易室
   * 
   * @param command
   */
  public void leaveTradingOffice(LeaveTradingOfficeCommand command) {

    Trader trader = traderRepository.findByTraderId(command.getTraderId());
    if (trader == null) {
      throw new TraderLeftAleadyExcepiton(new Object[] {command.getTraderId()});
    }
    // TODO 外调取消交易员身份
    traderRepository.delete(trader);
  }

  /**
   * 获取一个特定的交易员
   * 
   * @param traderId
   * @return
   */
  public Trader getTrader(Long traderId) {

    Trader trader = traderRepository.findByTraderId(traderId);
    if (trader == null) {
      throw new TraderLeftAleadyExcepiton(new Object[] {traderId});
    }
    return trader;
  }

  /**
   * 更新交易员的当前状态
   * 
   * @param command
   * @return
   */
  public Trader updateTraderStatus(UpdateTraderStatusCommand command) {

    Trader trader = traderRepository.findByTraderId(command.getTraderId());
    if (trader == null) {
      throw new TraderLeftAleadyExcepiton(new Object[] {command.getTraderId()});
    }

    trader.setStatus(command.getStatus());

    trader = traderRepository.save(trader);
    return trader;

  }



}
