package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Class or Instance
 */
@IID("{76A6415A-CB41-11D1-8B02-00600806D9B6}")
public interface ISWbemObject extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Save this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectPath
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  com4j.typelibs.wmi.ISWbemObjectPath put_(
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Save this Object asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(8)
  void putAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Delete this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(9)
  void delete_(
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Delete this Object asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(10)
  void deleteAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Return all instances of this Class
   * </p>
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(11)
  com4j.typelibs.wmi.ISWbemObjectSet instances_(
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Return all instances of this Class asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(12)
  void instancesAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Enumerate subclasses of this Class
   * </p>
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(13)
  com4j.typelibs.wmi.ISWbemObjectSet subclasses_(
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Enumerate subclasses of this Class asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(14)
  void subclassesAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Get the Associators of this Object
   * </p>
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

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(15)
  com4j.typelibs.wmi.ISWbemObjectSet associators_(
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
   * Get the Associators of this Object asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
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

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(16)
  void associatorsAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
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
   * Get the References to this Object
   * </p>
   * @param strResultClass Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 16
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectSet
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(17)
  com4j.typelibs.wmi.ISWbemObjectSet references_(
    @Optional @DefaultValue("") java.lang.String strResultClass,
    @Optional @DefaultValue("") java.lang.String strRole,
    @Optional @DefaultValue("0") boolean bClassesOnly,
    @Optional @DefaultValue("0") boolean bSchemaOnly,
    @Optional @DefaultValue("") java.lang.String strRequiredQualifier,
    @Optional @DefaultValue("16") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Get the References to this Object asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strResultClass Optional parameter. Default value is ""
   * @param strRole Optional parameter. Default value is ""
   * @param bClassesOnly Optional parameter. Default value is false
   * @param bSchemaOnly Optional parameter. Default value is false
   * @param strRequiredQualifier Optional parameter. Default value is ""
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(18)
  void referencesAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
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
   * Execute a Method of this Object
   * </p>
   * @param strMethodName Mandatory java.lang.String parameter.
   * @param objWbemInParameters Optional parameter. Default value is unprintable.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(19)
  com4j.typelibs.wmi.ISWbemObject execMethod_(
    java.lang.String strMethodName,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemInParameters,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet);


  /**
   * <p>
   * Execute a Method of this Object asynchronously
   * </p>
   * @param objWbemSink Mandatory com4j.Com4jObject parameter.
   * @param strMethodName Mandatory java.lang.String parameter.
   * @param objWbemInParameters Optional parameter. Default value is unprintable.
   * @param iFlags Optional parameter. Default value is 0
   * @param objWbemNamedValueSet Optional parameter. Default value is unprintable.
   * @param objWbemAsyncContext Optional parameter. Default value is unprintable.
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(20)
  void execMethodAsync_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemSink,
    java.lang.String strMethodName,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemInParameters,
    @Optional @DefaultValue("0") int iFlags,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemNamedValueSet,
    @Optional @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemAsyncContext);


  /**
   * <p>
   * Clone this Object
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(21)
  com4j.typelibs.wmi.ISWbemObject clone_();


  /**
   * <p>
   * Get the MOF text of this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(16) //= 0x10. The runtime will prefer the VTID if present
  @VTID(22)
  java.lang.String getObjectText_(
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Create a subclass of this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(23)
  com4j.typelibs.wmi.ISWbemObject spawnDerivedClass_(
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Create an Instance of this Object
   * </p>
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObject
   */

  @DISPID(18) //= 0x12. The runtime will prefer the VTID if present
  @VTID(24)
  com4j.typelibs.wmi.ISWbemObject spawnInstance_(
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * Compare this Object with another
   * </p>
   * @param objWbemObject Mandatory com4j.Com4jObject parameter.
   * @param iFlags Optional parameter. Default value is 0
   * @return  Returns a value of type boolean
   */

  @DISPID(19) //= 0x13. The runtime will prefer the VTID if present
  @VTID(25)
  boolean compareTo_(
    @MarshalAs(NativeType.Dispatch) com4j.Com4jObject objWbemObject,
    @Optional @DefaultValue("0") int iFlags);


  /**
   * <p>
   * The collection of Qualifiers of this Object
   * </p>
   * <p>
   * Getter method for the COM property "Qualifiers_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemQualifierSet
   */

  @DISPID(20) //= 0x14. The runtime will prefer the VTID if present
  @VTID(26)
  com4j.typelibs.wmi.ISWbemQualifierSet qualifiers_();


  @VTID(26)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemQualifierSet.class})
  com4j.typelibs.wmi.ISWbemQualifier qualifiers_(
    java.lang.String name,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * The collection of Properties of this Object
   * </p>
   * <p>
   * Getter method for the COM property "Properties_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemPropertySet
   */

  @DISPID(21) //= 0x15. The runtime will prefer the VTID if present
  @VTID(27)
  com4j.typelibs.wmi.ISWbemPropertySet properties_();


  @VTID(27)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemPropertySet.class})
  com4j.typelibs.wmi.ISWbemProperty properties_(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * The collection of Methods of this Object
   * </p>
   * <p>
   * Getter method for the COM property "Methods_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemMethodSet
   */

  @DISPID(22) //= 0x16. The runtime will prefer the VTID if present
  @VTID(28)
  com4j.typelibs.wmi.ISWbemMethodSet methods_();


  @VTID(28)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemMethodSet.class})
  com4j.typelibs.wmi.ISWbemMethod methods_(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * An array of strings describing the class derivation heirarchy, in most-derived-from order (the first element in the array defines the superclass and the last element defines the dynasty class).
   * </p>
   * <p>
   * Getter method for the COM property "Derivation_"
   * </p>
   * @return  Returns a value of type java.lang.Object
   */

  @DISPID(23) //= 0x17. The runtime will prefer the VTID if present
  @VTID(29)
  @ReturnValue(type=NativeType.VARIANT)
  java.lang.Object derivation_();


  /**
   * <p>
   * The path of this Object
   * </p>
   * <p>
   * Getter method for the COM property "Path_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemObjectPath
   */

  @DISPID(24) //= 0x18. The runtime will prefer the VTID if present
  @VTID(30)
  com4j.typelibs.wmi.ISWbemObjectPath path_();


  /**
   * <p>
   * The Security Configurator for this Object
   * </p>
   * <p>
   * Getter method for the COM property "Security_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemSecurity
   */

  @DISPID(25) //= 0x19. The runtime will prefer the VTID if present
  @VTID(31)
  com4j.typelibs.wmi.ISWbemSecurity security_();


  // Properties:
}
