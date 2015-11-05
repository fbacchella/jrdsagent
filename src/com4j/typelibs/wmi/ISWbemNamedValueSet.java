package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of named values
 */
@IID("{CF2376EA-CE8C-11D1-8B05-00600806D9B6}")
public interface ISWbemNamedValueSet extends Com4jObject,Iterable<Com4jObject> {
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
   * Get a named value from this Collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemNamedValue
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemNamedValue item(
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


  /**
   * <p>
   * Add a named value to this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param varValue Mandatory java.lang.Object parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemNamedValue
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemNamedValue add(
    java.lang.String strName,
    java.lang.Object varValue,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Remove a named value from this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  void remove(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Make a copy of this collection
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemNamedValueSet
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(12)
  com4j.typelibs.wmi.ISWbemNamedValueSet clone();


  @VTID(12)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemNamedValueSet.class})
  com4j.typelibs.wmi.ISWbemNamedValue clone(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * Delete all items in this collection
   * </p>
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(13)
  void deleteAll();


  // Properties:
}
