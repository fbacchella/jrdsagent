package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Qualifier
 */
@IID("{79B05932-D3B7-11D1-8B06-00600806D9B6}")
public interface ISWbemQualifier extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The value of this Qualifier
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
   * The value of this Qualifier
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
   * The name of this Qualifier
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
   * Indicates whether this Qualifier is local or propagated
   * </p>
   * <p>
   * Getter method for the COM property "IsLocal"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  boolean isLocal();


  /**
   * <p>
   * Determines whether this Qualifier can propagate to subclasses
   * </p>
   * <p>
   * Getter method for the COM property "PropagatesToSubclass"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  boolean propagatesToSubclass();


  /**
   * <p>
   * Determines whether this Qualifier can propagate to subclasses
   * </p>
   * <p>
   * Setter method for the COM property "PropagatesToSubclass"
   * </p>
   * @param bPropagatesToSubclass Mandatory boolean parameter.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(12)
  void propagatesToSubclass(
    boolean bPropagatesToSubclass);


  /**
   * <p>
   * Determines whether this Qualifier can propagate to instances
   * </p>
   * <p>
   * Getter method for the COM property "PropagatesToInstance"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(13)
  boolean propagatesToInstance();


  /**
   * <p>
   * Determines whether this Qualifier can propagate to instances
   * </p>
   * <p>
   * Setter method for the COM property "PropagatesToInstance"
   * </p>
   * @param bPropagatesToInstance Mandatory boolean parameter.
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(14)
  void propagatesToInstance(
    boolean bPropagatesToInstance);


  /**
   * <p>
   * Determines whether this Qualifier can be overridden where propagated
   * </p>
   * <p>
   * Getter method for the COM property "IsOverridable"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(15)
  boolean isOverridable();


  /**
   * <p>
   * Determines whether this Qualifier can be overridden where propagated
   * </p>
   * <p>
   * Setter method for the COM property "IsOverridable"
   * </p>
   * @param bIsOverridable Mandatory boolean parameter.
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(16)
  void isOverridable(
    boolean bIsOverridable);


  /**
   * <p>
   * Determines whether the value of this Qualifier has been amended
   * </p>
   * <p>
   * Getter method for the COM property "IsAmended"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(17)
  boolean isAmended();


  // Properties:
}
