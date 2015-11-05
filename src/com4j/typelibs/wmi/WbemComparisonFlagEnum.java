package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines settings for object comparison
 * </p>
 */
public enum WbemComparisonFlagEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemComparisonFlagIncludeAll(0),
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemComparisonFlagIgnoreQualifiers(1),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemComparisonFlagIgnoreObjectSource(2),
  /**
   * <p>
   * The value of this constant is 4
   * </p>
   */
  wbemComparisonFlagIgnoreDefaultValues(4),
  /**
   * <p>
   * The value of this constant is 8
   * </p>
   */
  wbemComparisonFlagIgnoreClass(8),
  /**
   * <p>
   * The value of this constant is 16
   * </p>
   */
  wbemComparisonFlagIgnoreCase(16),
  /**
   * <p>
   * The value of this constant is 32
   * </p>
   */
  wbemComparisonFlagIgnoreFlavor(32),
  ;

  private final int value;
  WbemComparisonFlagEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
