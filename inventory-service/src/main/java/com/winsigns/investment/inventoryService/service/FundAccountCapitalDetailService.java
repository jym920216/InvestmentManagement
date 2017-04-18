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
import com.winsigns.investment.inventoryService.model.Capital;
import com.winsigns.investment.inventoryService.model.CapitalDetail;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;
import com.winsigns.investment.inventoryService.model.FundAccountCapitalSerial;
import com.winsigns.investment.inventoryService.repository.CapitalDetailRepository;
import com.winsigns.investment.inventoryService.repository.CapitalRepository;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;

@Service
public class FundAccountCapitalDetailService {
  @Autowired
  private CapitalDetailRepository fundAccountCapitalDetailRepository;

  @Autowired
  private CapitalRepository fundAccountCapitalRepository;

  @Autowired
  private ECACashPoolRepository ecaCashPoolRepository;

  /*
   * 查询所有产品账户资金明细
   */
  public Collection<CapitalDetail> findAll() {
    return fundAccountCapitalDetailRepository.findAll();
  }

  /*
   * 查询特定产品账户资金的明细
   */
  public Collection<CapitalDetail> findByFACapitalId(Long faCapitalId) {

    Capital fundAccountCapital = fundAccountCapitalRepository.findOne(faCapitalId);
    if (fundAccountCapital == null)
      return null;

    return fundAccountCapitalDetailRepository.findByCapital(fundAccountCapital);
  }

  /*
   * 查询特定产品账户资金明细
   */
  public CapitalDetail findOne(Long fundAccountCapitalDetailId) {
    return fundAccountCapitalDetailRepository.findOne(fundAccountCapitalDetailId);
  }

  /*
   * 增加产品账户明细
   */
  public CapitalDetail addFundAccountCapitalDetail(Long fundAccountCapitalId,
      CreateFundAccountCapitalDetailCommand crtFundAccountCapitalDetailCmd) {

    Capital fundAccountCapital = fundAccountCapitalRepository.findOne(fundAccountCapitalId);
    if (fundAccountCapital == null)
      return null;

    CapitalDetail fundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findByCapitalAndExternalCapitalAccountId(
            fundAccountCapital, crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());

    if (fundAccountCapitalDetail == null) {
      fundAccountCapitalDetail = new CapitalDetail();

      fundAccountCapitalDetail.setCapital(fundAccountCapital);
      fundAccountCapitalDetail.setExternalCapitalAccountId(
          crtFundAccountCapitalDetailCmd.getExternalCapitalAccountId());

      fundAccountCapitalDetail = fundAccountCapitalDetailRepository.save(fundAccountCapitalDetail);
    }
    return fundAccountCapitalDetail;
  }

  /*
   * 从外部资金账户资金池分配资金到产品账户资金明细中
   */
  public CapitalDetail assignFrom(Long faCapitalDetailId,
      AssignAccountCommand assignAccountCommand) {
    CapitalDetail fundAccountCapitalDetail =
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
  public CapitalDetail assignTo(Long faCapitalDetailId, AssignAccountCommand assignAccountCommand) {
    CapitalDetail fundAccountCapitalDetail =
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
  public Collection<CapitalDetail> enfeoff(Long dstFACapitalDetailId, Long srcFACapitalDetailId,
      Double assignedCash) {

    List<CapitalDetail> result = new ArrayList<CapitalDetail>();

    CapitalDetail dstFundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(dstFACapitalDetailId);
    if (dstFundAccountCapitalDetail == null)
      return null;

    CapitalDetail srcFundAccountCapitalDetail =
        fundAccountCapitalDetailRepository.findOne(srcFACapitalDetailId);
    if (srcFundAccountCapitalDetail == null)
      return null;

    if (dstFundAccountCapitalDetail.getCapital().getCurrency() != srcFundAccountCapitalDetail
        .getCapital().getCurrency())
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
