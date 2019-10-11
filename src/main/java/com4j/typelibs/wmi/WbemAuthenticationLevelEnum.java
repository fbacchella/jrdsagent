package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines the security authentication level
 * </p>
 */
public enum WbemAuthenticationLevelEnum {
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemAuthenticationLevelDefault, // 0
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemAuthenticationLevelNone, // 1
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemAuthenticationLevelConnect, // 2
  /**
   * <p>
   * The value of this constant is 3
   * </p>
   */
  wbemAuthenticationLevelCall, // 3
  /**
   * <p>
   * The value of this constant is 4
   * </p>
   */
  wbemAuthenticationLevelPkt, // 4
  /**
   * <p>
   * The value of this constant is 5
   * </p>
   */
  wbemAuthenticationLevelPktIntegrity, // 5
  /**
   * <p>
   * The value of this constant is 6
   * </p>
   */
  wbemAuthenticationLevelPktPrivacy, // 6
}
