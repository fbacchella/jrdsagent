package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines the security impersonation level
 * </p>
 */
public enum WbemImpersonationLevelEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemImpersonationLevelAnonymous(1),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemImpersonationLevelIdentify(2),
  /**
   * <p>
   * The value of this constant is 3
   * </p>
   */
  wbemImpersonationLevelImpersonate(3),
  /**
   * <p>
   * The value of this constant is 4
   * </p>
   */
  wbemImpersonationLevelDelegate(4),
  ;

  private final int value;
  WbemImpersonationLevelEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
