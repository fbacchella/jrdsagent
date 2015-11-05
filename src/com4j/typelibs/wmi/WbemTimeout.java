package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines timeout constants
 * </p>
 */
public enum WbemTimeout implements ComEnum {
  /**
   * <p>
   * The value of this constant is -1
   * </p>
   */
  wbemTimeoutInfinite(-1),
  ;

  private final int value;
  WbemTimeout(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
