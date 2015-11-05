package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * Used to obtain Namespace connections
 */
@IID("{76A6415B-CB41-11D1-8B02-00600806D9B6}")
public interface ISWbemLocator extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Connect to a Namespace
   * </p>
   * @param strServer Optional parameter. Default value is "."
   * @param strNamespace Optional parameter. Default value is ""
   * @param strUser Optional parameter. Default value is ""
   * @param strPassword Optional parameter. Default value is ""
   * @param strLocale Optional parameter. Default value is ""
   * @param strAuthority Optional parameter. Default value is ""
   * @param iSecurityFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemServices
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com4j.typelibs.wmi.ISWbemServices connectServer(
    @Optional @DefaultValue(".") java.lang.String strServer,
    @Optional @DefaultValue("") java.lang.String strNamespace,
    @Optional @DefaultValue("") java.lang.String strUser,
    @Optional @DefaultValue("") java.lang.String strPassword,
    @Optional @DefaultValue("") java.lang.String strLocale,
    @Optional @DefaultValue("") java.lang.String strAuthority,
    @Optional @DefaultValue("0") int iSecurityFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


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
