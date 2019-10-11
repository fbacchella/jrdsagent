package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A collection of Properties
 */
@IID("{DEA0A7B2-D4BA-11D1-8B09-00600806D9B6}")
public interface ISWbemPropertySet extends Com4jObject,Iterable<Com4jObject> {
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
   * Get a named Property from this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemProperty
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemProperty item(
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
   * Add a Property to this collection
   * </p>
   * @param strName Mandatory java.lang.String parameter.
   * @param iCimType Mandatory com4j.typelibs.wmi.WbemCimtypeEnum parameter.
   * @param bIsArray Optional parameter. Default value is false
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemProperty
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemProperty add(
    java.lang.String strName,
    com4j.typelibs.wmi.WbemCimtypeEnum iCimType,
    @Optional @DefaultValue("0") boolean bIsArray,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Remove a Property from this collection
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
