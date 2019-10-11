package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines a privilege
 * </p>
 */
public enum WbemPrivilegeEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 1
   * </p>
   */
  wbemPrivilegeCreateToken(1),
  /**
   * <p>
   * The value of this constant is 2
   * </p>
   */
  wbemPrivilegePrimaryToken(2),
  /**
   * <p>
   * The value of this constant is 3
   * </p>
   */
  wbemPrivilegeLockMemory(3),
  /**
   * <p>
   * The value of this constant is 4
   * </p>
   */
  wbemPrivilegeIncreaseQuota(4),
  /**
   * <p>
   * The value of this constant is 5
   * </p>
   */
  wbemPrivilegeMachineAccount(5),
  /**
   * <p>
   * The value of this constant is 6
   * </p>
   */
  wbemPrivilegeTcb(6),
  /**
   * <p>
   * The value of this constant is 7
   * </p>
   */
  wbemPrivilegeSecurity(7),
  /**
   * <p>
   * The value of this constant is 8
   * </p>
   */
  wbemPrivilegeTakeOwnership(8),
  /**
   * <p>
   * The value of this constant is 9
   * </p>
   */
  wbemPrivilegeLoadDriver(9),
  /**
   * <p>
   * The value of this constant is 10
   * </p>
   */
  wbemPrivilegeSystemProfile(10),
  /**
   * <p>
   * The value of this constant is 11
   * </p>
   */
  wbemPrivilegeSystemtime(11),
  /**
   * <p>
   * The value of this constant is 12
   * </p>
   */
  wbemPrivilegeProfileSingleProcess(12),
  /**
   * <p>
   * The value of this constant is 13
   * </p>
   */
  wbemPrivilegeIncreaseBasePriority(13),
  /**
   * <p>
   * The value of this constant is 14
   * </p>
   */
  wbemPrivilegeCreatePagefile(14),
  /**
   * <p>
   * The value of this constant is 15
   * </p>
   */
  wbemPrivilegeCreatePermanent(15),
  /**
   * <p>
   * The value of this constant is 16
   * </p>
   */
  wbemPrivilegeBackup(16),
  /**
   * <p>
   * The value of this constant is 17
   * </p>
   */
  wbemPrivilegeRestore(17),
  /**
   * <p>
   * The value of this constant is 18
   * </p>
   */
  wbemPrivilegeShutdown(18),
  /**
   * <p>
   * The value of this constant is 19
   * </p>
   */
  wbemPrivilegeDebug(19),
  /**
   * <p>
   * The value of this constant is 20
   * </p>
   */
  wbemPrivilegeAudit(20),
  /**
   * <p>
   * The value of this constant is 21
   * </p>
   */
  wbemPrivilegeSystemEnvironment(21),
  /**
   * <p>
   * The value of this constant is 22
   * </p>
   */
  wbemPrivilegeChangeNotify(22),
  /**
   * <p>
   * The value of this constant is 23
   * </p>
   */
  wbemPrivilegeRemoteShutdown(23),
  /**
   * <p>
   * The value of this constant is 24
   * </p>
   */
  wbemPrivilegeUndock(24),
  /**
   * <p>
   * The value of this constant is 25
   * </p>
   */
  wbemPrivilegeSyncAgent(25),
  /**
   * <p>
   * The value of this constant is 26
   * </p>
   */
  wbemPrivilegeEnableDelegation(26),
  /**
   * <p>
   * The value of this constant is 27
   * </p>
   */
  wbemPrivilegeManageVolume(27),
  ;

  private final int value;
  WbemPrivilegeEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
