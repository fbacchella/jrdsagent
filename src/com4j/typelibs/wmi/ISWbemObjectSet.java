package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of Classes or Instances
 */
@IID("{76A6415F-CB41-11D1-8B02-00600806D9B6}")
public interface ISWbemObjectSet extends Com4jObject,Iterable<Com4jObject> {
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
   * Get an Object with a specific path from this collection
   * </p>
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemObject item(
    java.lang.String strObjectPath,
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
   * The Security Configurator for this Object
   * </p>
   * <p>
   * Getter method for the COM property "Security_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemSecurity
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemSecurity security_();


  /**
   * <p>
   * Get an Object with a specific index from this collection
   * </p>
   * @param lIndex Mandatory int parameter.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemObject itemIndex(
    int lIndex);


  // Properties:
}
