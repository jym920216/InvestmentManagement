package com.winsigns.investment.tradeService.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.tradeService.command.CreateEntrustCommand;
import com.winsigns.investment.tradeService.command.UpdateEntrustCommand;
import com.winsigns.investment.tradeService.constant.EntrustStatus;
import com.winsigns.investment.tradeService.integration.InvestServiceIntegration;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.repository.EntrustRepository;

/**
 * 委托服务
 * <p>
 * 查询一条委托<br>
 * 增加一条委托<br>
 * 修改一条委托<br>
 * 删除一条委托<br>
 * 
 * @author yimingjin
 *
 */
@Service
public class EntrustService {

  @Autowired
  EntrustRepository entrustRepository;

  @Autowired
  InvestServiceIntegration investService;

  /**
   * 根据条件查询指定指令下的未删除的委托
   * 
   * @param instructionId
   * @return
   */
  public Collection<Entrust> findNormalEntrustByCondition(Long instructionId) {
    Assert.notNull(instructionId);
    return entrustRepository.findByInstructionIdAndStatusNotOrderByEntrustTimeDesc(instructionId,
        EntrustStatus.DELETED);
  }

  /**
   * 查询一条委托
   * 
   * @param entrustId
   * @return 指定的委托
   */
  public Entrust readEntrust(Long entrustId) {

    Assert.notNull(entrustId);

    Entrust thisEntrust = entrustRepository.findOne(entrustId);

    Assert.notNull(thisEntrust);

    return thisEntrust;
  }

  /**
   * 增加一条委托
   * 
   * @param command
   * @return
   */
  public Entrust addEntrust(CreateEntrustCommand command) {

    Entrust newEntrust = new Entrust();

    newEntrust.setInstructionId(command.getInstructionId());;
    newEntrust.setSecurityId(investService.getInstructionSecurityId(command.getInstructionId()));

    return entrustRepository.save(newEntrust);
  }

  /**
   * 修改一条委托
   * 
   * @param entrustId
   * @param command
   * @return
   */
  public Entrust updateEntrust(Long entrustId, UpdateEntrustCommand command) {
    Assert.notNull(entrustId);
    Entrust thisEntrust = entrustRepository.findOne(entrustId);
    Assert.notNull(thisEntrust);


    return entrustRepository.save(thisEntrust);
  }

  /**
   * 删除一条委托
   * 
   * @param entrustId
   */
  public void deleteEntrust(Long entrustId) {

    Assert.notNull(entrustId);

    Entrust thisEntrust = entrustRepository.findOne(entrustId);

    Assert.notNull(thisEntrust);

    thisEntrust.setStatus(EntrustStatus.DELETED);
    entrustRepository.save(thisEntrust);
  }

}
