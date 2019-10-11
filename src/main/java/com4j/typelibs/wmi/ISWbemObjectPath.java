package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * An Object path
 */
@IID("{5791BC27-CE9C-11D1-97BF-0000F81E849C}")
public interface ISWbemObjectPath extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The full path
   * </p>
   * <p>
   * Getter method for the COM property "Path"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(7)
  @DefaultMethod
  java.lang.String path();


  /**
   * <p>
   * The full path
   * </p>
   * <p>
   * Setter method for the COM property "Path"
   * </p>
   * @param strPath Mandatory java.lang.String parameter.
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  void path(
    java.lang.String strPath);


  /**
   * <p>
   * The relative path
   * </p>
   * <p>
   * Getter method for the COM property "RelPath"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(9)
  java.lang.String relPath();


  /**
   * <p>
   * The relative path
   * </p>
   * <p>
   * Setter method for the COM property "RelPath"
   * </p>
   * @param strRelPath Mandatory java.lang.String parameter.
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(10)
  void relPath(
    java.lang.String strRelPath);


  /**
   * <p>
   * The name of the Server
   * </p>
   * <p>
   * Getter method for the COM property "Server"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(11)
  java.lang.String server();


  /**
   * <p>
   * The name of the Server
   * </p>
   * <p>
   * Setter method for the COM property "Server"
   * </p>
   * @param strServer Mandatory java.lang.String parameter.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(12)
  void server(
    java.lang.String strServer);


  /**
   * <p>
   * The Namespace path
   * </p>
   * <p>
   * Getter method for the COM property "Namespace"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(13)
  java.lang.String namespace();


  /**
   * <p>
   * The Namespace path
   * </p>
   * <p>
   * Setter method for the COM property "Namespace"
   * </p>
   * @param strNamespace Mandatory java.lang.String parameter.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(14)
  void namespace(
    java.lang.String strNamespace);


  /**
   * <p>
   * The parent Namespace path
   * </p>
   * <p>
   * Getter method for the COM property "ParentNamespace"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(15)
  java.lang.String parentNamespace();


  /**
   * <p>
   * The Display Name for this path
   * </p>
   * <p>
   * Getter method for the COM property "DisplayName"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(16)
  java.lang.String displayName();


  /**
   * <p>
   * The Display Name for this path
   * </p>
   * <p>
   * Setter method for the COM property "DisplayName"
   * </p>
   * @param strDisplayName Mandatory java.lang.String parameter.
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(17)
  void displayName(
    java.lang.String strDisplayName);


  /**
   * <p>
   * The Class name
   * </p>
   * <p>
   * Getter method for the COM property "Class"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(18)
  java.lang.String _class();


  /**
   * <p>
   * The Class name
   * </p>
   * <p>
   * Setter method for the COM property "Class"
   * </p>
   * @param strClass Mandatory java.lang.String parameter.
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(19)
  void _class(
    java.lang.String strClass);


  /**
   * <p>
   * Indicates whether this path addresses a Class
   * </p>
   * <p>
   * Getter method for the COM property "IsClass"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(20)
  boolean isClass();


  /**
   * <p>
   * Coerce this path to address a Class
   * </p>
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(21)
  void setAsClass();


  /**
   * <p>
   * Indicates whether this path addresses a Singleton Instance
   * </p>
   * <p>
   * Getter method for the COM property "IsSingleton"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(22)
  boolean isSingleton();


  /**
   * <p>
   * Coerce this path to address a Singleton Instance
   * </p>
   */

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(23)
  void setAsSingleton();


  /**
   * <p>
   * The collection of Key value bindings for this path
   * </p>
   * <p>
   * Getter method for the COM property "Keys"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemNamedValueSet
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(24)
  com4j.typelibs.wmi.ISWbemNamedValueSet keys();


  @VTID(24)
  @ReturnValue(defaultPropertyThrough={com4j.typelibs.wmi.ISWbemNamedValueSet.class})
  com4j.typelibs.wmi.ISWbemNamedValue keys(
    java.lang.String strName,
    @Optional @DefaultValue("0") int iFlags);

  /**
   * <p>
   * Defines the security components of this path
   * </p>
   * <p>
   * Getter method for the COM property "Security_"
   * </p>
   * @return  Returns a value of type com4j.typelibs.wmi.ISWbemSecurity
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(25)
  com4j.typelibs.wmi.ISWbemSecurity security_();


  /**
   * <p>
   * Defines locale component of this path
   * </p>
   * <p>
   * Getter method for the COM property "Locale"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(26)
  java.lang.String locale();


  /**
   * <p>
   * Defines locale component of this path
   * </p>
   * <p>
   * Setter method for the COM property "Locale"
   * </p>
   * @param strLocale Mandatory java.lang.String parameter.
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(27)
  void locale(
    java.lang.String strLocale);


  /**
   * <p>
   * Defines authentication authority component of this path
   * </p>
   * <p>
   * Getter method for the COM property "Authority"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(28)
  java.lang.String authority();


  /**
   * <p>
   * Defines authentication authority component of this path
   * </p>
   * <p>
   * Setter method for the COM property "Authority"
   * </p>
   * @param strAuthority Mandatory java.lang.String parameter.
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(29)
  void authority(
    java.lang.String strAuthority);


  // Properties:
}
