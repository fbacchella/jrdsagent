package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of Privilege Overrides
 */
@IID("{26EE67BF-5804-11D2-8B4A-00600806D9B6}")
public interface ISWbemPrivilegeSet extends Com4jObject,Iterable<Com4jObject> {
  // Methods:
  /**
   * <p>
   * Getter method for the COM property "_NewEnum"
   * </p>
   */

  @DISPID(-4) //= 0xfffffffc. The runtime will prefer the VTID if present
  @VTID(7)
  java.util.Iterator<Com4jObject> iterator();

  /**
   * <p>
   * Get a named Privilege from this collection
   * </p>
   * @param iPrivilege Mandatory com4j.typelibs.wmi.WbemPrivilegeEnum parameter.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPrivilege
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemPrivilege item(
    com4j.typelibs.wmi.WbemPrivilegeEnum iPrivilege);


  /**
   * <p>
   * The number of items in this collection
   * </p>
   * <p>
   * Getter method for the COM property "Count"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(9)
  int count();


  /**
   * <p>
   * Add a Privilege to this collection
   * </p>
   * @param iPrivilege Mandatory com4j.typelibs.wmi.WbemPrivilegeEnum parameter.
   * @param bIsEnabled Optional parameter. Default value is false
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPrivilege
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemPrivilege add(
    com4j.typelibs.wmi.WbemPrivilegeEnum iPrivilege,
    @Optional @DefaultValue("-1") boolean bIsEnabled);


  /**
   * <p>
   * Remove a Privilege from this collection
   * </p>
   * @param iPrivilege Mandatory com4j.typelibs.wmi.WbemPrivilegeEnum parameter.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  void remove(
    com4j.typelibs.wmi.WbemPrivilegeEnum iPrivilege);


  /**
   * <p>
   * Delete all items in this collection
   * </p>
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(12)
  void deleteAll();


  /**
   * <p>
   * Add a named Privilege to this collection
   * </p>
   * @param strPrivilege Mandatory java.lang.String parameter.
   * @param bIsEnabled Optional parameter. Default value is false
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPrivilege
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(13)
  com4j.typelibs.wmi.ISWbemPrivilege addAsString(
    java.lang.String strPrivilege,
    @Optional @DefaultValue("-1") boolean bIsEnabled);


  // Properties:
}
