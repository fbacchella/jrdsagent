package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines content of generated object text
 * </p>
 */
public enum WbemTextFlagEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemTextFlagNoFlavors(1),
  ;

  private final int value;
  WbemTextFlagEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
