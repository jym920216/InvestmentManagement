package com.winsigns.investment.investService.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsigns.investment.framework.hal.HALResponse;
import com.winsigns.investment.framework.i18n.i18nHelper;
import com.winsigns.investment.investService.constant.CurrencyCode;
import com.winsigns.investment.investService.constant.InstructionOperatorType;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.service.common.InvestServiceManager;

import lombok.Getter;

public class InstructionResource extends HALResponse<Instruction> {

  @Getter
  final List<InstructionOperatorType> supportedOperator;

  @Getter
  final String executionStatusLabel;

  @Getter
  final Map<String, Map<String, String>> supprotedInvestService =
      new HashMap<String, Map<String, String>>();

  @Getter
  final CurrencyCode[] supportedCurrencies;

  @Getter
  final HashMap<String, String> errors = new HashMap<String, String>();

  @Getter
  final HashMap<String, String> warnings = new HashMap<String, String>();

  @SuppressWarnings("serial")
  public InstructionResource(Instruction instruction) {
    super(instruction);

    // 2.成交价/成交均价
    // 3.数量/总金额

    // 0.字段信息
    for (InstructionMessage instructionMessage : instruction.getMessages()) {
      switch (instructionMessage.getMessageType()) {
        case ERROR:
          errors.put(instructionMessage.getFieldName(), instructionMessage.getMessageCode().i18n());
          break;
        case WARNING:
          warnings.put(instructionMessage.getFieldName(),
              instructionMessage.getMessageCode().i18n());
          break;
        default:
          break;

      }
    }
    // 1.状态支持的操作
    this.supportedOperator = instruction.getExecutionStatus().getSupportedOperator();
    // 2.状态的国际化
    this.executionStatusLabel = instruction.getExecutionStatus().i18n();
    // 3.支持的投资服务
    for (Map.Entry<String, Enum<?>[]> info : InvestServiceManager.getInstance().getServicesInfo()
        .entrySet()) {
      this.supprotedInvestService.put(info.getKey(), new HashMap<String, String>() {
        {
          Enum<?>[] enums = info.getValue();
          for (int i = 0; i < enums.length; ++i) {
            this.put(enums[i].name(), i18nHelper.i18n(enums[i]));
          }
        }
      });
    }
    // 4.支持的币种
    this.supportedCurrencies = CurrencyCode.values();
  }

}
