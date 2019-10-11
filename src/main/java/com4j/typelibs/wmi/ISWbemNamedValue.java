package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A named value
 */
@IID("{76A64164-CB41-11D1-8B02-00600806D9B6}")
public interface ISWbemNamedValue extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The Value of this Named element
   * </p>
   * <p>
   * Getter method for the COM property "Value"
   * </p>
   * @return  Returns a value of type java.lang.Object
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(7)
  @DefaultMethod
  @ReturnValue(type=NativeType.VARIANT)
  java.lang.Object value();


  /**
   * <p>
   * The Value of this Named element
   * </p>
   * <p>
   * Setter method for the COM property "Value"
   * </p>
   * @param varValue Mandatory java.lang.Object parameter.
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  void value(
    java.lang.Object varValue);


  /**
   * <p>
   * The Name of this Value
   * </p>
   * <p>
   * Getter method for the COM property "Name"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(9)
  java.lang.String name();


  // Properties:
}
