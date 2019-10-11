package com4j.typelibs.wmi  ;

import com4j.*;

/**
 * <p>
 * Defines the errors that may be returned by the WBEM Scripting library
 * </p>
 */
public enum WbemErrorEnum implements ComEnum {
  /**
   * <p>
   * The value of this constant is 0
   * </p>
   */
  wbemNoErr(0),
  /**
   * <p>
   * The value of this constant is -2147217407
   * </p>
   */
  wbemErrFailed(-2147217407),
  /**
   * <p>
   * The value of this constant is -2147217406
   * </p>
   */
  wbemErrNotFound(-2147217406),
  /**
   * <p>
   * The value of this constant is -2147217405
   * </p>
   */
  wbemErrAccessDenied(-2147217405),
  /**
   * <p>
   * The value of this constant is -2147217404
   * </p>
   */
  wbemErrProviderFailure(-2147217404),
  /**
   * <p>
   * The value of this constant is -2147217403
   * </p>
   */
  wbemErrTypeMismatch(-2147217403),
  /**
   * <p>
   * The value of this constant is -2147217402
   * </p>
   */
  wbemErrOutOfMemory(-2147217402),
  /**
   * <p>
   * The value of this constant is -2147217401
   * </p>
   */
  wbemErrInvalidContext(-2147217401),
  /**
   * <p>
   * The value of this constant is -2147217400
   * </p>
   */
  wbemErrInvalidParameter(-2147217400),
  /**
   * <p>
   * The value of this constant is -2147217399
   * </p>
   */
  wbemErrNotAvailable(-2147217399),
  /**
   * <p>
   * The value of this constant is -2147217398
   * </p>
   */
  wbemErrCriticalError(-2147217398),
  /**
   * <p>
   * The value of this constant is -2147217397
   * </p>
   */
  wbemErrInvalidStream(-2147217397),
  /**
   * <p>
   * The value of this constant is -2147217396
   * </p>
   */
  wbemErrNotSupported(-2147217396),
  /**
   * <p>
   * The value of this constant is -2147217395
   * </p>
   */
  wbemErrInvalidSuperclass(-2147217395),
  /**
   * <p>
   * The value of this constant is -2147217394
   * </p>
   */
  wbemErrInvalidNamespace(-2147217394),
  /**
   * <p>
   * The value of this constant is -2147217393
   * </p>
   */
  wbemErrInvalidObject(-2147217393),
  /**
   * <p>
   * The value of this constant is -2147217392
   * </p>
   */
  wbemErrInvalidClass(-2147217392),
  /**
   * <p>
   * The value of this constant is -2147217391
   * </p>
   */
  wbemErrProviderNotFound(-2147217391),
  /**
   * <p>
   * The value of this constant is -2147217390
   * </p>
   */
  wbemErrInvalidProviderRegistration(-2147217390),
  /**
   * <p>
   * The value of this constant is -2147217389
   * </p>
   */
  wbemErrProviderLoadFailure(-2147217389),
  /**
   * <p>
   * The value of this constant is -2147217388
   * </p>
   */
  wbemErrInitializationFailure(-2147217388),
  /**
   * <p>
   * The value of this constant is -2147217387
   * </p>
   */
  wbemErrTransportFailure(-2147217387),
  /**
   * <p>
   * The value of this constant is -2147217386
   * </p>
   */
  wbemErrInvalidOperation(-2147217386),
  /**
   * <p>
   * The value of this constant is -2147217385
   * </p>
   */
  wbemErrInvalidQuery(-2147217385),
  /**
   * <p>
   * The value of this constant is -2147217384
   * </p>
   */
  wbemErrInvalidQueryType(-2147217384),
  /**
   * <p>
   * The value of this constant is -2147217383
   * </p>
   */
  wbemErrAlreadyExists(-2147217383),
  /**
   * <p>
   * The value of this constant is -2147217382
   * </p>
   */
  wbemErrOverrideNotAllowed(-2147217382),
  /**
   * <p>
   * The value of this constant is -2147217381
   * </p>
   */
  wbemErrPropagatedQualifier(-2147217381),
  /**
   * <p>
   * The value of this constant is -2147217380
   * </p>
   */
  wbemErrPropagatedProperty(-2147217380),
  /**
   * <p>
   * The value of this constant is -2147217379
   * </p>
   */
  wbemErrUnexpected(-2147217379),
  /**
   * <p>
   * The value of this constant is -2147217378
   * </p>
   */
  wbemErrIllegalOperation(-2147217378),
  /**
   * <p>
   * The value of this constant is -2147217377
   * </p>
   */
  wbemErrCannotBeKey(-2147217377),
  /**
   * <p>
   * The value of this constant is -2147217376
   * </p>
   */
  wbemErrIncompleteClass(-2147217376),
  /**
   * <p>
   * The value of this constant is -2147217375
   * </p>
   */
  wbemErrInvalidSyntax(-2147217375),
  /**
   * <p>
   * The value of this constant is -2147217374
   * </p>
   */
  wbemErrNondecoratedObject(-2147217374),
  /**
   * <p>
   * The value of this constant is -2147217373
   * </p>
   */
  wbemErrReadOnly(-2147217373),
  /**
   * <p>
   * The value of this constant is -2147217372
   * </p>
   */
  wbemErrProviderNotCapable(-2147217372),
  /**
   * <p>
   * The value of this constant is -2147217371
   * </p>
   */
  wbemErrClassHasChildren(-2147217371),
  /**
   * <p>
   * The value of this constant is -2147217370
   * </p>
   */
  wbemErrClassHasInstances(-2147217370),
  /**
   * <p>
   * The value of this constant is -2147217369
   * </p>
   */
  wbemErrQueryNotImplemented(-2147217369),
  /**
   * <p>
   * The value of this constant is -2147217368
   * </p>
   */
  wbemErrIllegalNull(-2147217368),
  /**
   * <p>
   * The value of this constant is -2147217367
   * </p>
   */
  wbemErrInvalidQualifierType(-2147217367),
  /**
   * <p>
   * The value of this constant is -2147217366
   * </p>
   */
  wbemErrInvalidPropertyType(-2147217366),
  /**
   * <p>
   * The value of this constant is -2147217365
   * </p>
   */
  wbemErrValueOutOfRange(-2147217365),
  /**
   * <p>
   * The value of this constant is -2147217364
   * </p>
   */
  wbemErrCannotBeSingleton(-2147217364),
  /**
   * <p>
   * The value of this constant is -2147217363
   * </p>
   */
  wbemErrInvalidCimType(-2147217363),
  /**
   * <p>
   * The value of this constant is -2147217362
   * </p>
   */
  wbemErrInvalidMethod(-2147217362),
  /**
   * <p>
   * The value of this constant is -2147217361
   * </p>
   */
  wbemErrInvalidMethodParameters(-2147217361),
  /**
   * <p>
   * The value of this constant is -2147217360
   * </p>
   */
  wbemErrSystemProperty(-2147217360),
  /**
   * <p>
   * The value of this constant is -2147217359
   * </p>
   */
  wbemErrInvalidProperty(-2147217359),
  /**
   * <p>
   * The value of this constant is -2147217358
   * </p>
   */
  wbemErrCallCancelled(-2147217358),
  /**
   * <p>
   * The value of this constant is -2147217357
   * </p>
   */
  wbemErrShuttingDown(-2147217357),
  /**
   * <p>
   * The value of this constant is -2147217356
   * </p>
   */
  wbemErrPropagatedMethod(-2147217356),
  /**
   * <p>
   * The value of this constant is -2147217355
   * </p>
   */
  wbemErrUnsupportedParameter(-2147217355),
  /**
   * <p>
   * The value of this constant is -2147217354
   * </p>
   */
  wbemErrMissingParameter(-2147217354),
  /**
   * <p>
   * The value of this constant is -2147217353
   * </p>
   */
  wbemErrInvalidParameterId(-2147217353),
  /**
   * <p>
   * The value of this constant is -2147217352
   * </p>
   */
  wbemErrNonConsecutiveParameterIds(-2147217352),
  /**
   * <p>
   * The value of this constant is -2147217351
   * </p>
   */
  wbemErrParameterIdOnRetval(-2147217351),
  /**
   * <p>
   * The value of this constant is -2147217350
   * </p>
   */
  wbemErrInvalidObjectPath(-2147217350),
  /**
   * <p>
   * The value of this constant is -2147217349
   * </p>
   */
  wbemErrOutOfDiskSpace(-2147217349),
  /**
   * <p>
   * The value of this constant is -2147217348
   * </p>
   */
  wbemErrBufferTooSmall(-2147217348),
  /**
   * <p>
   * The value of this constant is -2147217347
   * </p>
   */
  wbemErrUnsupportedPutExtension(-2147217347),
  /**
   * <p>
   * The value of this constant is -2147217346
   * </p>
   */
  wbemErrUnknownObjectType(-2147217346),
  /**
   * <p>
   * The value of this constant is -2147217345
   * </p>
   */
  wbemErrUnknownPacketType(-2147217345),
  /**
   * <p>
   * The value of this constant is -2147217344
   * </p>
   */
  wbemErrMarshalVersionMismatch(-2147217344),
  /**
   * <p>
   * The value of this constant is -2147217343
   * </p>
   */
  wbemErrMarshalInvalidSignature(-2147217343),
  /**
   * <p>
   * The value of this constant is -2147217342
   * </p>
   */
  wbemErrInvalidQualifier(-2147217342),
  /**
   * <p>
   * The value of this constant is -2147217341
   * </p>
   */
  wbemErrInvalidDuplicateParameter(-2147217341),
  /**
   * <p>
   * The value of this constant is -2147217340
   * </p>
   */
  wbemErrTooMuchData(-2147217340),
  /**
   * <p>
   * The value of this constant is -2147217339
   * </p>
   */
  wbemErrServerTooBusy(-2147217339),
  /**
   * <p>
   * The value of this constant is -2147217338
   * </p>
   */
  wbemErrInvalidFlavor(-2147217338),
  /**
   * <p>
   * The value of this constant is -2147217337
   * </p>
   */
  wbemErrCircularReference(-2147217337),
  /**
   * <p>
   * The value of this constant is -2147217336
   * </p>
   */
  wbemErrUnsupportedClassUpdate(-2147217336),
  /**
   * <p>
   * The value of this constant is -2147217335
   * </p>
   */
  wbemErrCannotChangeKeyInheritance(-2147217335),
  /**
   * <p>
   * The value of this constant is -2147217328
   * </p>
   */
  wbemErrCannotChangeIndexInheritance(-2147217328),
  /**
   * <p>
   * The value of this constant is -2147217327
   * </p>
   */
  wbemErrTooManyProperties(-2147217327),
  /**
   * <p>
   * The value of this constant is -2147217326
   * </p>
   */
  wbemErrUpdateTypeMismatch(-2147217326),
  /**
   * <p>
   * The value of this constant is -2147217325
   * </p>
   */
  wbemErrUpdateOverrideNotAllowed(-2147217325),
  /**
   * <p>
   * The value of this constant is -2147217324
   * </p>
   */
  wbemErrUpdatePropagatedMethod(-2147217324),
  /**
   * <p>
   * The value of this constant is -2147217323
   * </p>
   */
  wbemErrMethodNotImplemented(-2147217323),
  /**
   * <p>
   * The value of this constant is -2147217322
   * </p>
   */
  wbemErrMethodDisabled(-2147217322),
  /**
   * <p>
   * The value of this constant is -2147217321
   * </p>
   */
  wbemErrRefresherBusy(-2147217321),
  /**
   * <p>
   * The value of this constant is -2147217320
   * </p>
   */
  wbemErrUnparsableQuery(-2147217320),
  /**
   * <p>
   * The value of this constant is -2147217319
   * </p>
   */
  wbemErrNotEventClass(-2147217319),
  /**
   * <p>
   * The value of this constant is -2147217318
   * </p>
   */
  wbemErrMissingGroupWithin(-2147217318),
  /**
   * <p>
   * The value of this constant is -2147217317
   * </p>
   */
  wbemErrMissingAggregationList(-2147217317),
  /**
   * <p>
   * The value of this constant is -2147217316
   * </p>
   */
  wbemErrPropertyNotAnObject(-2147217316),
  /**
   * <p>
   * The value of this constant is -2147217315
   * </p>
   */
  wbemErrAggregatingByObject(-2147217315),
  /**
   * <p>
   * The value of this constant is -2147217313
   * </p>
   */
  wbemErrUninterpretableProviderQuery(-2147217313),
  /**
   * <p>
   * The value of this constant is -2147217312
   * </p>
   */
  wbemErrBackupRestoreWinmgmtRunning(-2147217312),
  /**
   * <p>
   * The value of this constant is -2147217311
   * </p>
   */
  wbemErrQueueOverflow(-2147217311),
  /**
   * <p>
   * The value of this constant is -2147217310
   * </p>
   */
  wbemErrPrivilegeNotHeld(-2147217310),
  /**
   * <p>
   * The value of this constant is -2147217309
   * </p>
   */
  wbemErrInvalidOperator(-2147217309),
  /**
   * <p>
   * The value of this constant is -2147217308
   * </p>
   */
  wbemErrLocalCredentials(-2147217308),
  /**
   * <p>
   * The value of this constant is -2147217307
   * </p>
   */
  wbemErrCannotBeAbstract(-2147217307),
  /**
   * <p>
   * The value of this constant is -2147217306
   * </p>
   */
  wbemErrAmendedObject(-2147217306),
  /**
   * <p>
   * The value of this constant is -2147217305
   * </p>
   */
  wbemErrClientTooSlow(-2147217305),
  /**
   * <p>
   * The value of this constant is -2147217304
   * </p>
   */
  wbemErrNullSecurityDescriptor(-2147217304),
  /**
   * <p>
   * The value of this constant is -2147217303
   * </p>
   */
  wbemErrTimeout(-2147217303),
  /**
   * <p>
   * The value of this constant is -2147217302
   * </p>
   */
  wbemErrInvalidAssociation(-2147217302),
  /**
   * <p>
   * The value of this constant is -2147217301
   * </p>
   */
  wbemErrAmbiguousOperation(-2147217301),
  /**
   * <p>
   * The value of this constant is -2147217300
   * </p>
   */
  wbemErrQuotaViolation(-2147217300),
  /**
   * <p>
   * The value of this constant is -2147217299
   * </p>
   */
  wbemErrTransactionConflict(-2147217299),
  /**
   * <p>
   * The value of this constant is -2147217298
   * </p>
   */
  wbemErrForcedRollback(-2147217298),
  /**
   * <p>
   * The value of this constant is -2147217297
   * </p>
   */
  wbemErrUnsupportedLocale(-2147217297),
  /**
   * <p>
   * The value of this constant is -2147217296
   * </p>
   */
  wbemErrHandleOutOfDate(-2147217296),
  /**
   * <p>
   * The value of this constant is -2147217295
   * </p>
   */
  wbemErrConnectionFailed(-2147217295),
  /**
   * <p>
   * The value of this constant is -2147217294
   * </p>
   */
  wbemErrInvalidHandleRequest(-2147217294),
  /**
   * <p>
   * The value of this constant is -2147217293
   * </p>
   */
  wbemErrPropertyNameTooWide(-2147217293),
  /**
   * <p>
   * The value of this constant is -2147217292
   * </p>
   */
  wbemErrClassNameTooWide(-2147217292),
  /**
   * <p>
   * The value of this constant is -2147217291
   * </p>
   */
  wbemErrMethodNameTooWide(-2147217291),
  /**
   * <p>
   * The value of this constant is -2147217290
   * </p>
   */
  wbemErrQualifierNameTooWide(-2147217290),
  /**
   * <p>
   * The value of this constant is -2147217289
   * </p>
   */
  wbemErrRerunCommand(-2147217289),
  /**
   * <p>
   * The value of this constant is -2147217288
   * </p>
   */
  wbemErrDatabaseVerMismatch(-2147217288),
  /**
   * <p>
   * The value of this constant is -2147217287
   * </p>
   */
  wbemErrVetoPut(-2147217287),
  /**
   * <p>
   * The value of this constant is -2147217286
   * </p>
   */
  wbemErrVetoDelete(-2147217286),
  /**
   * <p>
   * The value of this constant is -2147217280
   * </p>
   */
  wbemErrInvalidLocale(-2147217280),
  /**
   * <p>
   * The value of this constant is -2147217279
   * </p>
   */
  wbemErrProviderSuspended(-2147217279),
  /**
   * <p>
   * The value of this constant is -2147217278
   * </p>
   */
  wbemErrSynchronizationRequired(-2147217278),
  /**
   * <p>
   * The value of this constant is -2147217277
   * </p>
   */
  wbemErrNoSchema(-2147217277),
  /**
   * <p>
   * The value of this constant is -2147217276
   * </p>
   */
  wbemErrProviderAlreadyRegistered(-2147217276),
  /**
   * <p>
   * The value of this constant is -2147217275
   * </p>
   */
  wbemErrProviderNotRegistered(-2147217275),
  /**
   * <p>
   * The value of this constant is -2147217274
   * </p>
   */
  wbemErrFatalTransportError(-2147217274),
  /**
   * <p>
   * The value of this constant is -2147217273
   * </p>
   */
  wbemErrEncryptedConnectionRequired(-2147217273),
  /**
   * <p>
   * The value of this constant is -2147213311
   * </p>
   */
  wbemErrRegistrationTooBroad(-2147213311),
  /**
   * <p>
   * The value of this constant is -2147213310
   * </p>
   */
  wbemErrRegistrationTooPrecise(-2147213310),
  /**
   * <p>
   * The value of this constant is -2147209215
   * </p>
   */
  wbemErrTimedout(-2147209215),
  /**
   * <p>
   * The value of this constant is -2147209214
   * </p>
   */
  wbemErrResetToDefault(-2147209214),
  ;

  private final int value;
  WbemErrorEnum(int value) { this.value=value; }
  public int comEnumValue() { return value; }
}
