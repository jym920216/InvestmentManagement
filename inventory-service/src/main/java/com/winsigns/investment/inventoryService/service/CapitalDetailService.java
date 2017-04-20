package com.winsigns.investment.inventoryService.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.winsigns.investment.inventoryService.capital.common.CapitalServiceManager;
import com.winsigns.investment.inventoryService.command.CreateCapitalDetailCommand;
import com.winsigns.investment.inventoryService.command.TransferCommand;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECAToFACapitalSerial;
import com.winsigns.investment.inventoryService.model.FAToECACapitalSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.CapitalSerialRepository;

@Service
public class CapitalDetailService {

  @Autowired
  CapitalDetailRepository capitalDetailRepository;

  @Autowired
  CapitalServiceManager capitalServiceManager;

  @Autowired
  ECACashPoolService ecaCashPoolService;

  @Autowired
  CapitalSerialRepository capitalSerialRepository;

  /**
   * 查询一条具体的产品资金账户明细
   * 
   * @param faCapitalDetailId
   * @return
   */
  public CapitalDetail readCapitalDetail(Long faCapitalDetailId) {
    Assert.notNull(faCapitalDetailId);
    return capitalDetailRepository.findOne(faCapitalDetailId);
  }

  /**
   * 创建产品资金账户明细
   * 
   * @param command
   * @return
   */
  public CapitalDetail addFundAccountCapitalDetail(CreateCapitalDetailCommand command) {

    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Long ecaCapitalId = command.getEcaCashPoolId();

    Assert.notNull(faCapitalPoolId);
    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    Assert.notNull(ecaCapitalId);
    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCapitalId);
    Assert.notNull(ecaCashPool);

    CapitalDetail capitalDetail =
        capitalDetailRepository.findByCapitalPoolAndCashPool(capitalPool, ecaCashPool);

    if (capitalDetail == null) {
      capitalDetail = new CapitalDetail();

      capitalDetail.setCapitalPool(capitalPool);
      capitalDetail.setCashPool(ecaCashPool);

      capitalDetail = capitalDetailRepository.save(capitalDetail);
    }
    return capitalDetail;
  }

  /**
   * 获取特定产品账户资金池的明细
   * 
   * @param faCapitalPoolId
   * @return
   */
  public List<CapitalDetail> readDetailsByFACapitalPool(Long faCapitalPoolId) {
    Assert.notNull(faCapitalPoolId);
    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    return capitalPool.getDetails();
  }

  /**
   * 根据产品账户资金池和外部资金账户资金池获取资金明细
   * 
   * @param capitalPool
   * @param ecaCashPool
   * @return
   */
  public CapitalDetail readCapitalDetail(FundAccountCapitalPool capitalPool,
      ECACashPool ecaCashPool) {
    return capitalDetailRepository.findByCapitalPoolAndCashPool(capitalPool, ecaCashPool);
  }

  /**
   * 从产品账户转出资金到外部资金账户
   * 
   * @param command
   * @return
   */
  public CapitalDetail transferFromFAToECA(TransferCommand command) {

    Long ecaCashPoolId = command.getEcaCashPoolId();
    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Double occurAmount = Math.floor(command.getOccurAmount());
    Assert.notNull(ecaCashPoolId);
    Assert.notNull(faCapitalPoolId);

    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCashPoolId);
    Assert.notNull(ecaCashPool);

    Assert.isTrue(capitalPool.getCurrency().equals(ecaCashPool.getCurrency()));

    ecaCashPool.changeUnassignedCapital(occurAmount);

    CapitalDetail capitalDetail = this.readCapitalDetail(capitalPool, ecaCashPool);
    if (capitalDetail == null) {
      CreateCapitalDetailCommand crtCommand = new CreateCapitalDetailCommand();
      crtCommand.setFaCapitalPoolId(faCapitalPoolId);
      crtCommand.setEcaCashPoolId(ecaCashPoolId);
      capitalDetail = this.addFundAccountCapitalDetail(crtCommand);
    }
    capitalDetail.changeCash(-occurAmount);
    capitalDetail.changeAvailableCapital(-occurAmount);
    capitalDetail.changeDesirableCapital(-occurAmount);
    capitalDetailRepository.save(capitalDetail);
    // 添加流水
    FAToECACapitalSerial faToECACapitalSerial = new FAToECACapitalSerial();
    faToECACapitalSerial.setSourceAccountId(faCapitalPoolId);
    faToECACapitalSerial.setMatchAccountId(ecaCashPoolId);
    faToECACapitalSerial.setCurrency(capitalPool.getCurrency());
    faToECACapitalSerial.setOccurAmount(occurAmount);
    capitalSerialRepository.save(faToECACapitalSerial);

    return capitalDetail;
  }

  /**
   * 从外部资金账户分配资金到产品账户
   * 
   * @param command
   * @return
   */
  @Transactional
  public CapitalDetail transferFromECAToFA(TransferCommand command) {

    Long ecaCashPoolId = command.getEcaCashPoolId();
    Long faCapitalPoolId = command.getFaCapitalPoolId();
    Double occurAmount = Math.floor(command.getOccurAmount());
    Assert.notNull(ecaCashPoolId);
    Assert.notNull(faCapitalPoolId);

    FundAccountCapitalPool capitalPool =
        capitalServiceManager.readFundAccountCapitalPool(faCapitalPoolId);
    Assert.notNull(capitalPool);

    ECACashPool ecaCashPool = ecaCashPoolService.findECACashPool(ecaCashPoolId);
    Assert.notNull(ecaCashPool);

    Assert.isTrue(capitalPool.getCurrency().equals(ecaCashPool.getCurrency()));

    ecaCashPool.changeUnassignedCapital(-occurAmount);

    CapitalDetail capitalDetail = this.readCapitalDetail(capitalPool, ecaCashPool);
    if (capitalDetail == null) {
      CreateCapitalDetailCommand crtCommand = new CreateCapitalDetailCommand();
      crtCommand.setFaCapitalPoolId(faCapitalPoolId);
      crtCommand.setEcaCashPoolId(ecaCashPoolId);
      capitalDetail = this.addFundAccountCapitalDetail(crtCommand);
    }
    capitalDetail.changeCash(occurAmount);
    capitalDetail.changeAvailableCapital(occurAmount);
    capitalDetail.changeDesirableCapital(occurAmount);
    capitalDetailRepository.save(capitalDetail);

    // 添加流水
    ECAToFACapitalSerial ecaToFACapitalSerial = new ECAToFACapitalSerial();
    ecaToFACapitalSerial.setSourceAccountId(ecaCashPoolId);
    ecaToFACapitalSerial.setMatchAccountId(faCapitalPoolId);
    ecaToFACapitalSerial.setCurrency(capitalPool.getCurrency());
    ecaToFACapitalSerial.setOccurAmount(occurAmount);
    capitalSerialRepository.save(ecaToFACapitalSerial);

    return capitalDetail;
  }
}
