package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * An Event source
 */
@IID("{27D54D92-0EBE-11D2-8B22-00600806D9B6}")
public interface ISWbemEventSource extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Retrieve the next event within a specified time period. The timeout is specified in milliseconds.
   * </p>
   * @param iTimeoutMs Optional parameter. Default value is -1
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com4j.typelibs.wmi.ISWbemObject nextEvent(
    @Optional @DefaultValue("-1") int iTimeoutMs);


  /**
   * <p>
   * The Security Configurator for this Object
   * </p>
   * <p>
   * Getter method for the COM property "Security_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemSecurity
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  com4j.typelibs.wmi.ISWbemSecurity security_();


  // Properties:
}
