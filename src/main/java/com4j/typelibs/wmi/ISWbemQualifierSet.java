package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of Qualifiers
 */
@IID("{9B16ED16-D3DF-11D1-8B08-00600806D9B6}")
public interface ISWbemQualifierSet extends Com4jObject,Iterable<Com4jObject> {
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
   * Get a named Qualifier from this collection
   * </p>
   * @param name Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemQualifier
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemQualifier item(
    java.lang.String name,
    @Optional @DefaultValue("0") int iFlags);


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
   * Add a Qualifier to this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param varVal Mandatory java.lang.Object parameter.
   * @param bPropagatesToSubclass Optional parameter. Default value is false
   * @param bPropagatesToInstance Optional parameter. Default value is false
   * @param bIsOverridable Optional parameter. Default value is false
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemQualifier
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemQualifier add(
    java.lang.String strName,
    java.lang.Object varVal,
    @Optional @DefaultValue("-1") boolean bPropagatesToSubclass,
    @Optional @DefaultValue("-1") boolean bPropagatesToInstance,
    @Optional @DefaultValue("-1") boolean bIsOverridable,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Remove a Qualifier from this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  void remove(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);


  // Properties:
}
