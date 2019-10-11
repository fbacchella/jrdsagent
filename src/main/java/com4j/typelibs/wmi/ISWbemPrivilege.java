package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Privilege Override
 */
@IID("{26EE67BD-5804-11D2-8B4A-00600806D9B6}")
public interface ISWbemPrivilege extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Whether the Privilege is to be enabled or disabled
   * </p>
   * <p>
   * Getter method for the COM property "IsEnabled"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(7)
  @DefaultMethod
  boolean isEnabled();


  /**
   * <p>
   * Whether the Privilege is to be enabled or disabled
   * </p>
   * <p>
   * Setter method for the COM property "IsEnabled"
   * </p>
   * @param bIsEnabled Mandatory boolean parameter.
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  void isEnabled(
    boolean bIsEnabled);


  /**
   * <p>
   * The name of the Privilege
   * </p>
   * <p>
   * Getter method for the COM property "Name"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(9)
  java.lang.String name();


  /**
   * <p>
   * The display name of the Privilege
   * </p>
   * <p>
   * Getter method for the COM property "DisplayName"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  java.lang.String displayName();


  /**
   * <p>
   * The Privilege identifier
   * </p>
   * <p>
   * Getter method for the COM property "Identifier"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.WbemPrivilegeEnum
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.WbemPrivilegeEnum identifier();


  // Properties:
}
