package com.winsigns.investment.tradeService.exception;

import com.winsigns.investment.framework.exception.CommonException;

public class ResourceApplicationExcepiton extends CommonException {
  /**
   * 
   */
  private static final long serialVersionUID = -6038447188657669743L;


  @Override
  protected String getPrefix() {
    return super.getPrefix() + "ResourceApplicationExcepiton.";
  }

  public ResourceApplicationExcepiton() {

  }

  public ResourceApplicationExcepiton(CommonException e) {
    super(e);
  }

  public ResourceApplicationExcepiton(String e) {
    super(e);
  }
}
