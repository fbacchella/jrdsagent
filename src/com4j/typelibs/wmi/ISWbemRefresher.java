package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Collection of Refreshable Objects
 */
@IID("{14D8250E-D9C2-11D3-B38F-00105A1F473A}")
public interface ISWbemRefresher extends Com4jObject,Iterable<Com4jObject> {
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
   * Get an item from this refresher
   * </p>
   * @param iIndex Mandatory int parameter.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemRefreshableItem
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  com4j.typelibs.wmi.ISWbemRefreshableItem item(
    int iIndex);


  /**
   * <p>
   * The number of items in this refresher
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
   * Add a refreshable instance to this refresher
   * </p>
   * @param objWbemServices Mandatory com4j.typelibs.wmi.ISWbemServicesEx parameter.
   * @param bsInstancePath Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemRefreshableItem
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemRefreshableItem add(
    com4j.typelibs.wmi.ISWbemServices objWbemServices,
    java.lang.String bsInstancePath,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Add a refreshable enumerator to this refresher
   * </p>
   * @param objWbemServices Mandatory com4j.typelibs.wmi.ISWbemServicesEx parameter.
   * @param bsClassName Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemRefreshableItem
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemRefreshableItem addEnum(
    com4j.typelibs.wmi.ISWbemServices objWbemServices,
    java.lang.String bsClassName,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Remove an item from this refresher
   * </p>
   * @param iIndex Mandatory int parameter.
   * @param iFlags Optional parameter. Default value is 0
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(12)
  void remove(
    int iIndex,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Refresh all items in this collection
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(13)
  void refresh(
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Whether to attempt auto-reconnection to a remote provider
   * </p>
   * <p>
   * Getter method for the COM property "AutoReconnect"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(14)
  boolean autoReconnect();


  /**
   * <p>
   * Whether to attempt auto-reconnection to a remote provider
   * </p>
   * <p>
   * Setter method for the COM property "AutoReconnect"
   * </p>
   * @param bCount Mandatory boolean parameter.
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(15)
  void autoReconnect(
    boolean bCount);


  /**
   * <p>
   * Delete all items in this collection
   * </p>
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(16)
  void deleteAll();


  // Properties:
}
