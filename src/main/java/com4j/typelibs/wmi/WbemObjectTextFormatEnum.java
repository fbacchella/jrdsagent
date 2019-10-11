package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines object text formats
 * </p>
 */
public enum WbemObjectTextFormatEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemObjectTextFormatCIMDTD20(1),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemObjectTextFormatWMIDTD20(2),
  ;

  private final int value;
  WbemObjectTextFormatEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
