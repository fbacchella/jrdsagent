package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Security Configurator
 */
@IID("{B54D66E6-2287-11D2-8B33-00600806D9B6}")
public interface ISWbemSecurity extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The security impersonation level
   * </p>
   * <p>
   * Getter method for the COM property "ImpersonationLevel"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.WbemImpersonationLevelEnum
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com4j.typelibs.wmi.WbemImpersonationLevelEnum impersonationLevel();


  /**
   * <p>
   * The security impersonation level
   * </p>
   * <p>
   * Setter method for the COM property "ImpersonationLevel"
   * </p>
   * @param iImpersonationLevel Mandatory com4j.typelibs.wmi.WbemImpersonationLevelEnum parameter.
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(8)
  void impersonationLevel(
    com4j.typelibs.wmi.WbemImpersonationLevelEnum iImpersonationLevel);


  /**
   * <p>
   * The security authentication level
   * </p>
   * <p>
   * Getter method for the COM property "AuthenticationLevel"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.WbemAuthenticationLevelEnum
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(9)
  com4j.typelibs.wmi.WbemAuthenticationLevelEnum authenticationLevel();


  /**
   * <p>
   * The security authentication level
   * </p>
   * <p>
   * Setter method for the COM property "AuthenticationLevel"
   * </p>
   * @param iAuthenticationLevel Mandatory com4j.typelibs.wmi.WbemAuthenticationLevelEnum parameter.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  void authenticationLevel(
    com4j.typelibs.wmi.WbemAuthenticationLevelEnum iAuthenticationLevel);


  /**
   * <p>
   * The collection of privileges for this object
   * </p>
   * <p>
   * Getter method for the COM property "Privileges"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPrivilegeSet
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemPrivilegeSet privileges();


  @VTID(11)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemPrivilegeSet.class})
  com4j.typelibs.wmi.ISWbemPrivilege privileges(
    com4j.typelibs.wmi.WbemPrivilegeEnum iPrivilege);

  // Properties:
}
