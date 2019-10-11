package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * A Datetime
 */
@IID("{5E97458A-CF77-11D3-B38F-00105A1F473A}")
public interface ISWbemDateTime extends Com4jObject {
  // Methods:
  /**
   * <p>
   * The DMTF datetime
   * </p>
   * <p>
   * Getter method for the COM property "Value"
   * </p>
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(7)
  @DefaultMethod
  java.lang.String value();


  /**
   * <p>
   * The DMTF datetime
   * </p>
   * <p>
   * Setter method for the COM property "Value"
   * </p>
   * @param strValue Mandatory java.lang.String parameter.
   */

  @DISPID(0) //= 0x0. The runtime will prefer the VTID if present
  @VTID(8)
  @DefaultMethod
  void value(
    java.lang.String strValue);


  /**
   * <p>
   * The Year component of the value (must be in the range 0-9999)
   * </p>
   * <p>
   * Getter method for the COM property "Year"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(9)
  int year();


  /**
   * <p>
   * The Year component of the value (must be in the range 0-9999)
   * </p>
   * <p>
   * Setter method for the COM property "Year"
   * </p>
   * @param iYear Mandatory int parameter.
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(10)
  void year(
    int iYear);


  /**
   * <p>
   * Whether the Year component is specified
   * </p>
   * <p>
   * Getter method for the COM property "YearSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(11)
  boolean yearSpecified();


  /**
   * <p>
   * Whether the Year component is specified
   * </p>
   * <p>
   * Setter method for the COM property "YearSpecified"
   * </p>
   * @param bYearSpecified Mandatory boolean parameter.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(12)
  void yearSpecified(
    boolean bYearSpecified);


  /**
   * <p>
   * The Month component of the value (must be in the range 1-12)
   * </p>
   * <p>
   * Getter method for the COM property "Month"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(13)
  int month();


  /**
   * <p>
   * The Month component of the value (must be in the range 1-12)
   * </p>
   * <p>
   * Setter method for the COM property "Month"
   * </p>
   * @param iMonth Mandatory int parameter.
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(14)
  void month(
    int iMonth);


  /**
   * <p>
   * Whether the Month component is specified
   * </p>
   * <p>
   * Getter method for the COM property "MonthSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(15)
  boolean monthSpecified();


  /**
   * <p>
   * Whether the Month component is specified
   * </p>
   * <p>
   * Setter method for the COM property "MonthSpecified"
   * </p>
   * @param bMonthSpecified Mandatory boolean parameter.
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(16)
  void monthSpecified(
    boolean bMonthSpecified);


  /**
   * <p>
   * The Day component of the value (must be in the range 1-31, or 0-999999 for interval values)
   * </p>
   * <p>
   * Getter method for the COM property "Day"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(17)
  int day();


  /**
   * <p>
   * The Day component of the value (must be in the range 1-31, or 0-999999 for interval values)
   * </p>
   * <p>
   * Setter method for the COM property "Day"
   * </p>
   * @param iDay Mandatory int parameter.
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(18)
  void day(
    int iDay);


  /**
   * <p>
   * Whether the Day component is specified
   * </p>
   * <p>
   * Getter method for the COM property "DaySpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(19)
  boolean daySpecified();


  /**
   * <p>
   * Whether the Day component is specified
   * </p>
   * <p>
   * Setter method for the COM property "DaySpecified"
   * </p>
   * @param bDaySpecified Mandatory boolean parameter.
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(20)
  void daySpecified(
    boolean bDaySpecified);


  /**
   * <p>
   * The Hours component of the value (must be in the range 0-23)
   * </p>
   * <p>
   * Getter method for the COM property "Hours"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(21)
  int hours();


  /**
   * <p>
   * The Hours component of the value (must be in the range 0-23)
   * </p>
   * <p>
   * Setter method for the COM property "Hours"
   * </p>
   * @param iHours Mandatory int parameter.
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(22)
  void hours(
    int iHours);


  /**
   * <p>
   * Whether the Hours component is specified
   * </p>
   * <p>
   * Getter method for the COM property "HoursSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(23)
  boolean hoursSpecified();


  /**
   * <p>
   * Whether the Hours component is specified
   * </p>
   * <p>
   * Setter method for the COM property "HoursSpecified"
   * </p>
   * @param bHoursSpecified Mandatory boolean parameter.
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(24)
  void hoursSpecified(
    boolean bHoursSpecified);


  /**
   * <p>
   * The Minutes component of the value (must be in the range 0-59)
   * </p>
   * <p>
   * Getter method for the COM property "Minutes"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(25)
  int minutes();


  /**
   * <p>
   * The Minutes component of the value (must be in the range 0-59)
   * </p>
   * <p>
   * Setter method for the COM property "Minutes"
   * </p>
   * @param iMinutes Mandatory int parameter.
   */

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(26)
  void minutes(
    int iMinutes);


  /**
   * <p>
   * Whether the Minutes component is specified
   * </p>
   * <p>
   * Getter method for the COM property "MinutesSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(27)
  boolean minutesSpecified();


  /**
   * <p>
   * Whether the Minutes component is specified
   * </p>
   * <p>
   * Setter method for the COM property "MinutesSpecified"
   * </p>
   * @param bMinutesSpecified Mandatory boolean parameter.
   */

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(28)
  void minutesSpecified(
    boolean bMinutesSpecified);


  /**
   * <p>
   * The Seconds component of the value (must be in the range 0-59)
   * </p>
   * <p>
   * Getter method for the COM property "Seconds"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(29)
  int seconds();


  /**
   * <p>
   * The Seconds component of the value (must be in the range 0-59)
   * </p>
   * <p>
   * Setter method for the COM property "Seconds"
   * </p>
   * @param iSeconds Mandatory int parameter.
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(30)
  void seconds(
    int iSeconds);


  /**
   * <p>
   * Whether the Seconds component is specified
   * </p>
   * <p>
   * Getter method for the COM property "SecondsSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(31)
  boolean secondsSpecified();


  /**
   * <p>
   * Whether the Seconds component is specified
   * </p>
   * <p>
   * Setter method for the COM property "SecondsSpecified"
   * </p>
   * @param bSecondsSpecified Mandatory boolean parameter.
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(32)
  void secondsSpecified(
    boolean bSecondsSpecified);


  /**
   * <p>
   * The Microseconds component of the value (must be in the range 0-999999)
   * </p>
   * <p>
   * Getter method for the COM property "Microseconds"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(33)
  int microseconds();


  /**
   * <p>
   * The Microseconds component of the value (must be in the range 0-999999)
   * </p>
   * <p>
   * Setter method for the COM property "Microseconds"
   * </p>
   * @param iMicroseconds Mandatory int parameter.
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(34)
  void microseconds(
    int iMicroseconds);


  /**
   * <p>
   * Whether the Microseconds component is specified
   * </p>
   * <p>
   * Getter method for the COM property "MicrosecondsSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(35)
  boolean microsecondsSpecified();


  /**
   * <p>
   * Whether the Microseconds component is specified
   * </p>
   * <p>
   * Setter method for the COM property "MicrosecondsSpecified"
   * </p>
   * @param bMicrosecondsSpecified Mandatory boolean parameter.
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(36)
  void microsecondsSpecified(
    boolean bMicrosecondsSpecified);


  /**
   * <p>
   * The UTC component of the value (must be in the range -720 to 720)
   * </p>
   * <p>
   * Getter method for the COM property "UTC"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(37)
  int utc();


  /**
   * <p>
   * The UTC component of the value (must be in the range -720 to 720)
   * </p>
   * <p>
   * Setter method for the COM property "UTC"
   * </p>
   * @param iUTC Mandatory int parameter.
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(38)
  void utc(
    int iUTC);


  /**
   * <p>
   * Whether the UTC component is specified
   * </p>
   * <p>
   * Getter method for the COM property "UTCSpecified"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(16) //= 0x10. The runtime will prefer the VTID if present
  @VTID(39)
  boolean utcSpecified();


  /**
   * <p>
   * Whether the UTC component is specified
   * </p>
   * <p>
   * Setter method for the COM property "UTCSpecified"
   * </p>
   * @param bUTCSpecified Mandatory boolean parameter.
   */

  @DISPID(16) //= 0x10. The runtime will prefer the VTID if present
  @VTID(40)
  void utcSpecified(
    boolean bUTCSpecified);


  /**
   * <p>
   * Indicates whether this value describes an absolute date and time or is an interval
   * </p>
   * <p>
   * Getter method for the COM property "IsInterval"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(41)
  boolean isInterval();


  /**
   * <p>
   * Indicates whether this value describes an absolute date and time or is an interval
   * </p>
   * <p>
   * Setter method for the COM property "IsInterval"
   * </p>
   * @param bIsInterval Mandatory boolean parameter.
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(42)
  void isInterval(
    boolean bIsInterval);


  /**
   * <p>
   * Retrieve value in Variant compatible (VT_DATE) format
   * </p>
   * @param bIsLocal Optional parameter. Default value is false
   * @return  Returns a value of type java.util.Date
   */

  @DISPID(18) //= 0x12. The runtime will prefer the VTID if present
  @VTID(43)
  java.util.Date getVarDate(
    @Optional @DefaultValue("-1") boolean bIsLocal);


  /**
   * <p>
   * Set the value using Variant compatible (VT_DATE) format
   * </p>
   * @param dVarDate Mandatory java.util.Date parameter.
   * @param bIsLocal Optional parameter. Default value is false
   */

  @DISPID(19) //= 0x13. The runtime will prefer the VTID if present
  @VTID(44)
  void setVarDate(
    java.util.Date dVarDate,
    @Optional @DefaultValue("-1") boolean bIsLocal);


  /**
   * <p>
   * Retrieve value in FILETIME compatible string representation
   * </p>
   * @param bIsLocal Optional parameter. Default value is false
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(20) //= 0x14. The runtime will prefer the VTID if present
  @VTID(45)
  java.lang.String getFileTime(
    @Optional @DefaultValue("-1") boolean bIsLocal);


  /**
   * <p>
   * Set the value using FILETIME compatible string representation
   * </p>
   * @param strFileTime Mandatory java.lang.String parameter.
   * @param bIsLocal Optional parameter. Default value is false
   */

  @DISPID(21) //= 0x15. The runtime will prefer the VTID if present
  @VTID(46)
  void setFileTime(
    java.lang.String strFileTime,
    @Optional @DefaultValue("-1") boolean bIsLocal);


  // Properties:
}
