package com.winsigns.investment.tradingOfficeService.constant;

import com.winsigns.investment.framework.i18n.i18nHelper;

public enum TraderStatus {

  NORMAL,

  BUSY;

  /**
   * 国际化
   * 
   * @return
   */
  public String i18n() {
    return i18nHelper.i18n(this);
  }
}
