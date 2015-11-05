package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines the valid CIM Types of a Property value
 * </p>
 */
public enum WbemCimtypeEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 16
   * </p>
   */
  wbemCimtypeSint8(16),
  /**
   * <p>
   * The value of this constant is 17
   * </p>
   */
  wbemCimtypeUint8(17),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemCimtypeSint16(2),
  /**
   * <p>
   * The value of this constant is 18
   * </p>
   */
  wbemCimtypeUint16(18),
  /**
   * <p>
   * The value of this constant is 3
   * </p>
   */
  wbemCimtypeSint32(3),
  /**
   * <p>
   * The value of this constant is 19
   * </p>
   */
  wbemCimtypeUint32(19),
  /**
   * <p>
   * The value of this constant is 20
   * </p>
   */
  wbemCimtypeSint64(20),
  /**
   * <p>
   * The value of this constant is 21
   * </p>
   */
  wbemCimtypeUint64(21),
  /**
   * <p>
   * The value of this constant is 4
   * </p>
   */
  wbemCimtypeReal32(4),
  /**
   * <p>
   * The value of this constant is 5
   * </p>
   */
  wbemCimtypeReal64(5),
  /**
   * <p>
   * The value of this constant is 11
   * </p>
   */
  wbemCimtypeBoolean(11),
  /**
   * <p>
   * The value of this constant is 8
   * </p>
   */
  wbemCimtypeString(8),
  /**
   * <p>
   * The value of this constant is 101
   * </p>
   */
  wbemCimtypeDatetime(101),
  /**
   * <p>
   * The value of this constant is 102
   * </p>
   */
  wbemCimtypeReference(102),
  /**
   * <p>
   * The value of this constant is 103
   * </p>
   */
  wbemCimtypeChar16(103),
  /**
   * <p>
   * The value of this constant is 13
   * </p>
   */
  wbemCimtypeObject(13),
  ;

  private final int value;
  WbemCimtypeEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
