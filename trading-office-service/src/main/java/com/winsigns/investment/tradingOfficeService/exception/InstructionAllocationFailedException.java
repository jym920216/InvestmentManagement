package com.winsigns.investment.tradingOfficeService.exception;

import com.winsigns.investment.framework.exception.CustomArgumentsException;

public class InstructionAllocationFailedException extends CustomArgumentsException {

  /**
   * 
   */
  private static final long serialVersionUID = 5326493578218941112L;

  public InstructionAllocationFailedException(Object[] args) {
    super(args);
  }

}
