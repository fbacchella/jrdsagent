package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Property
 */
@IID("{1A388F98-D4BA-11D1-8B09-00600806D9B6}")
public interface ISWbemProperty extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The value of this Property
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
   * The value of this Property
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
   * The name of this Property
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
   * Indicates whether this Property is local or propagated
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
   * The originating class of this Property
   * </p>
   * <p>
   * Getter method for the COM property "Origin"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  java.lang.String origin();


  /**
   * <p>
   * The CIM Type of this Property
   * </p>
   * <p>
   * Getter method for the COM property "CIMType"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.WbemCimtypeEnum
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(12)
  com4j.typelibs.wmi.WbemCimtypeEnum cimType();


  /**
   * <p>
   * The collection of Qualifiers of this Property
   * </p>
   * <p>
   * Getter method for the COM property "Qualifiers_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemQualifierSet
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(13)
  com4j.typelibs.wmi.ISWbemQualifierSet qualifiers_();


  @VTID(13)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemQualifierSet.class})
  com4j.typelibs.wmi.ISWbemQualifier qualifiers_(
    java.lang.String name,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * Indicates whether this Property is an array type
   * </p>
   * <p>
   * Getter method for the COM property "IsArray"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(14)
  boolean isArray();


  // Properties:
}
