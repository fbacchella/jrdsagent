package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines behavior of various interface calls
 * </p>
 */
public enum WbemFlagEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 16
   * </p>
   */
  wbemFlagReturnImmediately(16),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagReturnWhenComplete(0),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagBidirectional(0),
  /**
   * <p>
   * The value of this constant is 32
   * </p>
   */
  wbemFlagForwardOnly(32),
  /**
   * <p>
   * The value of this constant is 64
   * </p>
   */
  wbemFlagNoErrorObject(64),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagReturnErrorObject(0),
  /**
   * <p>
   * The value of this constant is 128
   * </p>
   */
  wbemFlagSendStatus(128),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagDontSendStatus(0),
  /**
   * <p>
   * The value of this constant is 256
   * </p>
   */
  wbemFlagEnsureLocatable(256),
  /**
   * <p>
   * The value of this constant is 512
   * </p>
   */
  wbemFlagDirectRead(512),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagSendOnlySelected(0),
  /**
   * <p>
   * The value of this constant is 131072
   * </p>
   */
  wbemFlagUseAmendedQualifiers(131072),
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemFlagGetDefault(0),
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemFlagSpawnInstance(1),
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemFlagUseCurrentTime(1),
  ;

  private final int value;
  WbemFlagEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
