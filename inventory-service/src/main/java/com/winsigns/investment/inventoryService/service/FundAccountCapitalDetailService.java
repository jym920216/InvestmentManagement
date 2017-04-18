package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.AssignAccountCommand;
import com.winsigns.investment.inventoryService.command.CreateFundAccountCapitalDetailCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalDetail;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalPool;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalSerial;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.FundAccountCapitalPoolRepository;

@Service
public class FundAccountCapitalDetailService {
  @Autowired
  private FundAccountCapitalDetailRepository fundAccountCapitalDetailRepository;

  @Autowired
  private FundAccountCapitalPoolRepository fundAccountCapitalRepository;

  @Autowired
  private ECACashPoolRepository ecaCashPoolRepository;

  /*
   * 查询所有产品账户资金明细
   */
  public Collection<FundAccountCapitalDetail> findAll() {
    return fundAccountCapitalDetailRepository.findAll();
  }

  /*
   * 查询特定产品账户资金的明细
   */
  public Collection<FundAccountCapitalDetail> findByFACapitalId(Long faCapitalId) {

    FundAccountCapitalPool fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
    if (fundAccountCapital == null)
      return null;

    return fundAccountCapitalDetailRepository.findByCapitalPool(fundAccountCapital);
  }

  /*
   * 查询特定产品账户资金明细
   */
  public FundAccountCapitalDetail findOne(Long fundAccountCapitalDetailId) {
    return fundAccountCapitalDetailRepository.findOne(fundAccountCapitalDetailId);
  }

  /*
   * 增加产品账户明细
   */
  public FundAccountCapitalDetail addFundAccountCapitalDetail(Long fundAccountCapitalId,
      CreateFundAccountCapitalDetailCommand crtFundAccountCapitalDetailCmd) {

    FundAccountCapitalPool fundAccountCapital =
        fundAccountCapitalRepository.findOne(fundAccountCapitalId);
    if (fundAccountCapital == null)
      return null;

    FundAccountCapitalDetail fundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findByCapitalPoolAndExternalCapitalAccountId(
            fundAccountCapital, crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());

    if (fundAccountCapitalDetail == null) {
      fundAccountCapitalDetail = new FundAccountCapitalDetail();

      fundAccountCapitalDetail.setCapitalPool(fundAccountCapital);
      fundAccountCapitalDetail.setExternalCapitalAccountId(
          crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());

      fundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
    }
    return fundAccountCapitalDetail;
  }

  /*
   * 从外部资金账户资金池分配资金到产品账户资金明细中
   */
  public FundAccountCapitalDetail assignFrom(Long faCapitalDetailId,
      AssignAccountCommand assignAccountCommand) {
    FundAccountCapitalDetail fundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(faCapitalDetailId);
    if (fundAccountCapitalDetail == null)
      return null;

    FundAccountCapitalSerial fundAccountCapitalSerial = new FundAccountCapitalSerial();
    fundAccountCapitalSerial.setEcaCashPoolId(assignAccountCommand.getEcaCashPoolId());
    fundAccountCapitalSerial.setCapitalDetail(fundAccountCapitalDetail);
    fundAccountCapitalSerial.setAssignedCash(Math.floor(assignAccountCommand.getAssignedCash()));
    fundAccountCapitalSerial.setAssignedDate(new Date());
    fundAccountCapitalSerial.operator();

    ECACashPool ecaCashPool =
        ecaCashPoolRepository.findOne(assignAccountCommand.getEcaCashPoolId());
    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() - assignAccountCommand.getAssignedCash());

    ECACashSerial ecaCashSerial = new ECACashSerial();
    ecaCashSerial.setEcaCashPool(ecaCashPool);
    ecaCashSerial.setAssignedDate(new Date());
    ecaCashSerial.setAssignedCash(-Math.abs(assignAccountCommand.getAssignedCash()));
    ecaCashSerial.setLinkedFASerialId(fundAccountCapitalSerial.getId());
    ecaCashSerial.operator();

    ecaCashPoolRepository.save(ecaCashPool);

    return fundAccountCapitalDetail;
  }

  /*
   * 从产品账户资金明细归还资金到外部资金账户资金池中
   */
  public FundAccountCapitalDetail assignTo(Long faCapitalDetailId,
      AssignAccountCommand assignAccountCommand) {
    FundAccountCapitalDetail fundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(faCapitalDetailId);
    if (fundAccountCapitalDetail == null)
      return null;

    FundAccountCapitalSerial fundAccountCapitalSerial = new FundAccountCapitalSerial();
    fundAccountCapitalSerial.setEcaCashPoolId(assignAccountCommand.getEcaCashPoolId());
    fundAccountCapitalSerial.setCapitalDetail(fundAccountCapitalDetail);
    fundAccountCapitalSerial.setAssignedCash(-Math.floor(assignAccountCommand.getAssignedCash()));
    fundAccountCapitalSerial.setAssignedDate(new Date());

    ECACashPool ecaCashPool =
        ecaCashPoolRepository.findOne(assignAccountCommand.getEcaCashPoolId());
    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() + assignAccountCommand.getAssignedCash());
    ecaCashPoolRepository.save(ecaCashPool);

    ECACashSerial ecaCashSerial = new ECACashSerial();
    ecaCashSerial.setEcaCashPool(ecaCashPool);
    ecaCashSerial.setAssignedDate(new Date());
    ecaCashSerial.setAssignedCash(Math.abs(assignAccountCommand.getAssignedCash()));
    ecaCashSerial.setLinkedFASerialId(fundAccountCapitalSerial.getId());
    ecaCashSerial.operator();

    return fundAccountCapitalDetail;
  }

  /*
   * 产品账户之间的资金互转
   */
  @Transactional
  public Collection<FundAccountCapitalDetail> enfeoff(Long dstFACapitalDetailId,
      Long srcFACapitalDetailId, Double assignedCash) {

    List<FundAccountCapitalDetail> result = new ArrayList<FundAccountCapitalDetail>();

    FundAccountCapitalDetail dstFundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(dstFACapitalDetailId);
    if (dstFundAccountCapitalDetail == null)
      return null;

    FundAccountCapitalDetail srcFundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(srcFACapitalDetailId);
    if (srcFundAccountCapitalDetail == null)
      return null;

    if (dstFundAccountCapitalDetail.getCapitalPool().getCurrency() != srcFundAccountCapitalDetail
        .getCapitalPool().getCurrency())
      return null;

    FundAccountCapitalSerial dstfundAccountCapitalSerial = new FundAccountCapitalSerial();
    FundAccountCapitalSerial srcfundAccountCapitalSerial = new FundAccountCapitalSerial();

    Date date = new Date();
    dstfundAccountCapitalSerial.setCapitalDetail(dstFundAccountCapitalDetail);
    dstfundAccountCapitalSerial.setAssignedCash(Math.floor(assignedCash));
    dstfundAccountCapitalSerial.setLinkedSerialId(srcfundAccountCapitalSerial.getId());
    dstfundAccountCapitalSerial.setAssignedDate(date);

    srcfundAccountCapitalSerial.setCapitalDetail(srcFundAccountCapitalDetail);
    srcfundAccountCapitalSerial.setAssignedCash(-Math.floor(assignedCash));
    srcfundAccountCapitalSerial.setLinkedSerialId(dstfundAccountCapitalSerial.getId());
    srcfundAccountCapitalSerial.setAssignedDate(date);

    result.add(dstFundAccountCapitalDetail);
    result.add(srcFundAccountCapitalDetail);


    return result;
  }
}
