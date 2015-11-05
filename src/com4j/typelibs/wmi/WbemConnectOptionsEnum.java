package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Used to define connection behavior
 * </p>
 */
public enum WbemConnectOptionsEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 128
   * </p>
   */
  wbemConnectFlagUseMaxWait(128),
  ;

  private final int value;
  WbemConnectOptionsEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
