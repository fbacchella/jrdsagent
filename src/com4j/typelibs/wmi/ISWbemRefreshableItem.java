package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A single item in a Refresher
 */
@IID("{5AD4BF92-DAAB-11D3-B38F-00105A1F473A}")
public interface ISWbemRefreshableItem extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The index of this item in the parent refresher
   * </p>
   * <p>
   * Getter method for the COM property "Index"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  int index();


  /**
   * <p>
   * The parent refresher
   * </p>
   * <p>
   * Getter method for the COM property "Refresher"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemRefresher
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  com4j.typelibs.wmi.ISWbemRefresher refresher();


  @VTID(8)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemRefresher.class})
  com4j.typelibs.wmi.ISWbemRefreshableItem refresher(
    int iIndex);

  /**
   * <p>
   * Whether this item represents a single object or an object set
   * </p>
   * <p>
   * Getter method for the COM property "IsSet"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(9)
  boolean isSet();


  /**
   * <p>
   * The object
   * </p>
   * <p>
   * Getter method for the COM property "Object"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectEx
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  com4j.typelibs.wmi.ISWbemObjectEx object();


  /**
   * <p>
   * The object set
   * </p>
   * <p>
   * Getter method for the COM property "ObjectSet"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemObjectSet objectSet();


  @VTID(11)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemObjectSet.class})
  com4j.typelibs.wmi.ISWbemObject objectSet(
    java.lang.String strObjectPath,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * Remove this item from the parent refresher
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(12)
  void remove(
    @Optional @DefaultValue("0") int iFlags);


  // Properties:
}
