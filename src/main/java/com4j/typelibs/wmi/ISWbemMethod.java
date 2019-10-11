package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Method
 */
@IID("{422E8E90-D955-11D1-8B09-00600806D9B6}")
public interface ISWbemMethod extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The name of this Method
   * </p>
   * <p>
   * Getter method for the COM property "Name"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  java.lang.String name();


  /**
   * <p>
   * The originating class of this Method
   * </p>
   * <p>
   * Getter method for the COM property "Origin"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  java.lang.String origin();


  /**
   * <p>
   * The in parameters for this Method.
   * </p>
   * <p>
   * Getter method for the COM property "InParameters"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(9)
  com4j.typelibs.wmi.ISWbemObject inParameters();


  /**
   * <p>
   * The out parameters for this Method.
   * </p>
   * <p>
   * Getter method for the COM property "OutParameters"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemObject outParameters();


  /**
   * <p>
   * The collection of Qualifiers of this Method.
   * </p>
   * <p>
   * Getter method for the COM property "Qualifiers_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemQualifierSet
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemQualifierSet qualifiers_();


  @VTID(11)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemQualifierSet.class})
  com4j.typelibs.wmi.ISWbemQualifier qualifiers_(
    java.lang.String name,
    @Optional @DefaultValue("0") int iFlags);

  // Properties:
}
