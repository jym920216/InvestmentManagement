package com.winsigns.investment.investService.exception;

import com.winsigns.investment.framework.exception.CustomArgumentsException;

public class InstructionInvalidException extends CustomArgumentsException {

  /**
   * 
   */
  private static final long serialVersionUID = 5326493578218941112L;

  public InstructionInvalidException(Object[] args) {
    super(args);
  }

}
