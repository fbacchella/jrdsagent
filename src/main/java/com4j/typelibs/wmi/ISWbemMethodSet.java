package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of Methods
 */
@IID("{C93BA292-D955-11D1-8B09-00600806D9B6}")
public interface ISWbemMethodSet extends Com4jObject,Iterable<Com4jObject> {
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
   * Get a named Method from this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemMethod
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemMethod item(
    java.lang.String strName,
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


  // Properties:
}
