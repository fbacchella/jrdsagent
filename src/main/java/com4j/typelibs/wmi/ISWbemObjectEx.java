package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Class or Instance
 */
@IID("{269AD56A-8A67-4129-BC8C-0506DCFE9880}")
public interface ISWbemObjectEx extends com4j.typelibs.wmi.ISWbemObject {
  // Methods:
  /**
   * <p>
   * Refresh this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   */

  @DISPID(26) //= 0x1a. The runtime will prefer the VTID if present
  @VTID(32)
  void refresh_(
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * The collection of System Properties of this Object
   * </p>
   * <p>
   * Getter method for the COM property "SystemProperties_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPropertySet
   */

  @DISPID(27) //= 0x1b. The runtime will prefer the VTID if present
  @VTID(33)
  com4j.typelibs.wmi.ISWbemPropertySet systemProperties_();


  @VTID(33)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemPropertySet.class})
  com4j.typelibs.wmi.ISWbemProperty systemProperties_(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * Retrieve a textual representation of this Object
   * </p>
   * @param iObjectTextFormat Mandatory com4j.typelibs.wmi.WbemObjectTextFormatEnum parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(28) //= 0x1c. The runtime will prefer the VTID if present
  @VTID(34)
  java.lang.String getText_(
    com4j.typelibs.wmi.WbemObjectTextFormatEnum iObjectTextFormat,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Set this Object using the supplied textual representation
   * </p>
   * @param bsText Mandatory java.lang.String parameter.
   * @param iObjectTextFormat Mandatory com4j.typelibs.wmi.WbemObjectTextFormatEnum parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   */

  @DISPID(29) //= 0x1d. The runtime will prefer the VTID if present
  @VTID(35)
  void setFromText_(
    java.lang.String bsText,
    com4j.typelibs.wmi.WbemObjectTextFormatEnum iObjectTextFormat,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  // Properties:
}
