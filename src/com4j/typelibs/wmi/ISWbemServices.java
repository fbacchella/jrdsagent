package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A connection to a Namespace
 */
@IID("{76A6415C-CB41-11D1-8B02-00600806D9B6}")
public interface ISWbemServices extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Get a single Class or Instance
   * </p>
   * @param strObjectPath Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com4j.typelibs.wmi.ISWbemObject get(
    @Optional @DefaultValue("") java.lang.String strObjectPath,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Get a single Class or Instance asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strObjectPath Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  void getAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("") java.lang.String strObjectPath,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Delete a Class or Instance
   * </p>
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(9)
  void delete(
    java.lang.String strObjectPath,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Delete a Class or Instance asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  void deleteAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strObjectPath,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Enumerate the Instances of a Class
   * </p>
   * @param strClass Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemObjectSet instancesOf(
    java.lang.String strClass,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Enumerate the Instances of a Class asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strClass Mandatory java.lang.String parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(12)
  void instancesOfAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strClass,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Enumerate the subclasses of a Class
   * </p>
   * @param strSuperclass Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(13)
  com4j.typelibs.wmi.ISWbemObjectSet subclassesOf(
    @Optional @DefaultValue("") java.lang.String strSuperclass,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Enumerate the subclasses of a Class asynchronously 
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strSuperclass Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(14)
  void subclassesOfAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("") java.lang.String strSuperclass,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Execute a Query
   * </p>
   * @param strQuery Mandatory java.lang.String parameter.
   * @param strQueryLanguage Optional parameter. Default value is "WQL"
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(15)
  com4j.typelibs.wmi.ISWbemObjectSet execQuery(
    java.lang.String strQuery,
    @Optional @DefaultValue("WQL") java.lang.String strQueryLanguage,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Execute an asynchronous Query
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strQuery Mandatory java.lang.String parameter.
   * @param strQueryLanguage Optional parameter. Default value is "WQL"
   * @param lFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(16)
  void execQueryAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strQuery,
    @Optional @DefaultValue("WQL") java.lang.String strQueryLanguage,
    @Optional @DefaultValue("0") int lFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Get the Associators of a class or instance
   * </p>
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strAssocClass Optional parameter. Default value is ""
   * @param strResultClass Optional parameter. Default value is ""
   * @param strResultRole Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredAssocQualifier Optional parameter. Default value is ""
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(17)
  com4j.typelibs.wmi.ISWbemObjectSet associatorsOf(
    java.lang.String strObjectPath,
    @Optional @DefaultValue("") java.lang.String strAssocClass,
    @Optional @DefaultValue("") java.lang.String strResultClass,
    @Optional @DefaultValue("") java.lang.String strResultRole,
    @Optional @DefaultValue("") java.lang.String strRole,
    @Optional @DefaultValue("0") boolean bClassesOnly,
    @Optional @DefaultValue("0") boolean bSchemaOnly,
    @Optional @DefaultValue("") java.lang.String strRequiredAssocQualifier,
    @Optional @DefaultValue("") java.lang.String strRequiredQualifier,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Get the Associators of a class or instance asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strAssocClass Optional parameter. Default value is ""
   * @param strResultClass Optional parameter. Default value is ""
   * @param strResultRole Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredAssocQualifier Optional parameter. Default value is ""
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(18)
  void associatorsOfAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strObjectPath,
    @Optional @DefaultValue("") java.lang.String strAssocClass,
    @Optional @DefaultValue("") java.lang.String strResultClass,
    @Optional @DefaultValue("") java.lang.String strResultRole,
    @Optional @DefaultValue("") java.lang.String strRole,
    @Optional @DefaultValue("0") boolean bClassesOnly,
    @Optional @DefaultValue("0") boolean bSchemaOnly,
    @Optional @DefaultValue("") java.lang.String strRequiredAssocQualifier,
    @Optional @DefaultValue("") java.lang.String strRequiredQualifier,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Get the References to a class or instance
   * </p>
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strResultClass Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(19)
  com4j.typelibs.wmi.ISWbemObjectSet referencesTo(
    java.lang.String strObjectPath,
    @Optional @DefaultValue("") java.lang.String strResultClass,
    @Optional @DefaultValue("") java.lang.String strRole,
    @Optional @DefaultValue("0") boolean bClassesOnly,
    @Optional @DefaultValue("0") boolean bSchemaOnly,
    @Optional @DefaultValue("") java.lang.String strRequiredQualifier,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Get the References to a class or instance asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strResultClass Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(20)
  void referencesToAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strObjectPath,
    @Optional @DefaultValue("") java.lang.String strResultClass,
    @Optional @DefaultValue("") java.lang.String strRole,
    @Optional @DefaultValue("0") boolean bClassesOnly,
    @Optional @DefaultValue("0") boolean bSchemaOnly,
    @Optional @DefaultValue("") java.lang.String strRequiredQualifier,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Execute a Query to receive Notifications
   * </p>
   * @param strQuery Mandatory java.lang.String parameter.
   * @param strQueryLanguage Optional parameter. Default value is "WQL"
   * @param iFlags Optional parameter. Default value is 48
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemEventSource
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(21)
  com4j.typelibs.wmi.ISWbemEventSource execNotificationQuery(
    java.lang.String strQuery,
    @Optional @DefaultValue("WQL") java.lang.String strQueryLanguage,
    @Optional @DefaultValue("48") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Execute an asynchronous Query to receive Notifications
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strQuery Mandatory java.lang.String parameter.
   * @param strQueryLanguage Optional parameter. Default value is "WQL"
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(16) //= 0x10. The runtime will prefer the VTID if present
  @VTID(22)
  void execNotificationQueryAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strQuery,
    @Optional @DefaultValue("WQL") java.lang.String strQueryLanguage,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Execute a Method
   * </p>
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strMethodName Mandatory java.lang.String parameter.
   * @param objWbemInParameters Optional parameter. Default value is unprintable.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(23)
  com4j.typelibs.wmi.ISWbemObject execMethod(
    java.lang.String strObjectPath,
    java.lang.String strMethodName,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemInParameters,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Execute a Method asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strObjectPath Mandatory java.lang.String parameter.
   * @param strMethodName Mandatory java.lang.String parameter.
   * @param objWbemInParameters Optional parameter. Default value is unprintable.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(18) //= 0x12. The runtime will prefer the VTID if present
  @VTID(24)
  void execMethodAsync(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strObjectPath,
    java.lang.String strMethodName,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemInParameters,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * The Security Configurator for this Object
   * </p>
   * <p>
   * Getter method for the COM property "Security_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemSecurity
   */

  @DISPID(19) //= 0x13. The runtime will prefer the VTID if present
  @VTID(25)
  com4j.typelibs.wmi.ISWbemSecurity security_();


  // Properties:
}
