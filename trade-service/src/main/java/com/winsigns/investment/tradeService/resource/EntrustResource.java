package com.winsigns.investment.tradeService.resource;

import static com.winsigns.investment.tradeService.service.common.TradeServiceManager.getTradeServiceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.model.Item;
import com.winsigns.investment.tradeService.constant.EntrustOperatorType;
import com.winsigns.investment.tradeService.model.Entrust;
import com.winsigns.investment.tradeService.model.EntrustMessage;
import com.winsigns.investment.tradeService.service.common.ITradeService;
import com.winsigns.investment.tradeService.service.common.ITradeType;

import lombok.Getter;

public class EntrustResource extends HALResponse<Entrust> {
  @Getter
  protected final HashMap<String, String> errors = new HashMap<String, String>();

  @Getter
  protected final HashMap<String, String> warnings = new HashMap<String, String>();

  @Getter
  protected final String tradeServiceLabel;

  @Getter
  protected final String tradeTypeLabel;

  @Getter
  protected final String statusLabel;

  @Getter
  protected final List<Item> supportedOperator = new ArrayList<Item>();

  @Getter
  protected final List<Item> supprotedInvestService = new ArrayList<Item>();

  public EntrustResource(Entrust entrust) {
    super(entrust);

    // 0.字段信息
    for (EntrustMessage entrustMessage : entrust.getMessages()) {
      switch (entrustMessage.getMessageType()) {
        case ERROR:
          errors.put(entrustMessage.getFieldName(), entrustMessage.getMessageCode().i18n());
          break;
        case WARNING:
          warnings.put(entrustMessage.getFieldName(), entrustMessage.getMessageCode().i18n());
          break;
        default:
          break;

      }
    }

    // 1.字段的国际化
    this.statusLabel = entrust.getStatus().i18n();
    ITradeService service = getTradeServiceManager().getService(entrust.getTradeService());
    if (service != null) {
      this.tradeServiceLabel = service.getSimpleName();
      if (service.getTradeType(entrust.getTradeType()) != null) {
        this.tradeTypeLabel = service.getTradeType(entrust.getTradeType()).i18n();
      } else {
        this.tradeTypeLabel = null;
      }
    } else {
      this.tradeServiceLabel = null;
      this.tradeTypeLabel = null;
    }
    // 2.状态支持的操作
    for (EntrustOperatorType type : entrust.getStatus().getSupportedOperator()) {
      this.supportedOperator.add(new Item(type.name(), type.i18n()));
    }
    // 3.支持的交易服务
    for (Map.Entry<ITradeService, ITradeType[]> info : getTradeServiceManager().getServicesInfo()
        .entrySet()) {
      ITradeType[] types = info.getValue();
      for (int i = 0; i < types.length; ++i) {
        this.supprotedInvestService.add(new Item(info.getKey().getName() + "." + types[i].name(),
            info.getKey().getSimpleName() + "-" + types[i].i18n()));
      }
    }
  }

}
