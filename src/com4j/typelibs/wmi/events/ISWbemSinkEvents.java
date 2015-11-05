package com4j.typelibs.wmi.events;

import com4j.*;

/**
 * A sink for events arising from asynchronous operations
 */
@IID("{75718CA0-F029-11D1-A1AC-00C04FB6C223}")
public abstract class ISWbemSinkEvents {
  // Methods:
  /**
   * <p>
   * Event triggered when an Object is available
   * </p>
   * @param objWbemObject Mandatory com4j.typelibs.wmi.ISWbemObject parameter.
   * @param objWbemAsyncContext Mandatory com4j.typelibs.wmi.ISWbemNamedValueSet parameter.
   */

  @DISPID(1)
  public void onObjectReady(
    com4j.typelibs.wmi.ISWbemObject objWbemObject,
    com4j.typelibs.wmi.ISWbemNamedValueSet objWbemAsyncContext) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event triggered when an asynchronous operation is completed
   * </p>
   * @param iHResult Mandatory com4j.typelibs.wmi.WbemErrorEnum parameter.
   * @param objWbemErrorObject Mandatory com4j.typelibs.wmi.ISWbemObject parameter.
   * @param objWbemAsyncContext Mandatory com4j.typelibs.wmi.ISWbemNamedValueSet parameter.
   */

  @DISPID(2)
  public void onCompleted(
    com4j.typelibs.wmi.WbemErrorEnum iHResult,
    com4j.typelibs.wmi.ISWbemObject objWbemErrorObject,
    com4j.typelibs.wmi.ISWbemNamedValueSet objWbemAsyncContext) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event triggered to report the progress of an asynchronous operation
   * </p>
   * @param iUpperBound Mandatory int parameter.
   * @param iCurrent Mandatory int parameter.
   * @param strMessage Mandatory java.lang.String parameter.
   * @param objWbemAsyncContext Mandatory com4j.typelibs.wmi.ISWbemNamedValueSet parameter.
   */

  @DISPID(3)
  public void onProgress(
    int iUpperBound,
    int iCurrent,
    java.lang.String strMessage,
    com4j.typelibs.wmi.ISWbemNamedValueSet objWbemAsyncContext) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event triggered when an object path is available following a Put operation
   * </p>
   * @param objWbemObjectPath Mandatory com4j.typelibs.wmi.ISWbemObjectPath parameter.
   * @param objWbemAsyncContext Mandatory com4j.typelibs.wmi.ISWbemNamedValueSet parameter.
   */

  @DISPID(4)
  public void onObjectPut(
    com4j.typelibs.wmi.ISWbemObjectPath objWbemObjectPath,
    com4j.typelibs.wmi.ISWbemNamedValueSet objWbemAsyncContext) {
        throw new UnsupportedOperationException();
  }


  // Properties:
}
