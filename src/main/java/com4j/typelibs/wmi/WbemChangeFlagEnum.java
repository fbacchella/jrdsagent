package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines semantics of putting a Class or Instance
 * </p>
 */
public enum WbemChangeFlagEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemChangeFlagCreateOrUpdate(0),
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemChangeFlagUpdateOnly(1),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemChangeFlagCreateOnly(2),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemChangeFlagUpdateCompatible(0),
  /**
   * <p>
   * The value of this constant is 32
   * </p>
   */
  wbemChangeFlagUpdateSafeMode(32),
  /**
   * <p>
   * The value of this constant is 64
   * </p>
   */
  wbemChangeFlagUpdateForceMode(64),
  /**
   * <p>
   * The value of this constant is 128
   * </p>
   */
  wbemChangeFlagStrongValidation(128),
  /**
   * <p>
   * The value of this constant is 65536
   * </p>
   */
  wbemChangeFlagAdvisory(65536),
  ;

  private final int value;
  WbemChangeFlagEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
