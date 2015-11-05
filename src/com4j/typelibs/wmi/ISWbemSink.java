package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * Asynchronous operation control
 */
@IID("{75718C9F-F029-11D1-A1AC-00C04FB6C223}")
public interface ISWbemSink extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Cancel an asynchronous operation
   * </p>
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  void cancel();


  // Properties:
}
