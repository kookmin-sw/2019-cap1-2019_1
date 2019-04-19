// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/logging/type/log_severity.proto

package com.google.logging.type;

/**
 * <pre>
 * The severity of the event described in a log entry.  These guideline severity
 * levels are ordered, with numerically smaller levels treated as less severe
 * than numerically larger levels. If the source of the log entries uses a
 * different set of severity levels, the client should select the closest
 * corresponding `LogSeverity` value. For example, Java's FINE, FINER, and
 * FINEST levels might all map to `LogSeverity.DEBUG`. If the original severity
 * code must be preserved, it can be stored in the payload.
 * </pre>
 *
 * Protobuf enum {@code google.logging.type.LogSeverity}
 */
public enum LogSeverity
    implements com.google.protobuf.Internal.EnumLite {
  /**
   * <pre>
   * The log entry has no assigned severity level.
   * </pre>
   *
   * <code>DEFAULT = 0;</code>
   */
  DEFAULT(0),
  /**
   * <pre>
   * Debug or trace information.
   * </pre>
   *
   * <code>DEBUG = 100;</code>
   */
  DEBUG(100),
  /**
   * <pre>
   * Routine information, such as ongoing status or performance.
   * </pre>
   *
   * <code>INFO = 200;</code>
   */
  INFO(200),
  /**
   * <pre>
   * Normal but significant events, such as start up, shut down, or
   * configuration.
   * </pre>
   *
   * <code>NOTICE = 300;</code>
   */
  NOTICE(300),
  /**
   * <pre>
   * Warning events might cause problems.
   * </pre>
   *
   * <code>WARNING = 400;</code>
   */
  WARNING(400),
  /**
   * <pre>
   * Error events are likely to cause problems.
   * </pre>
   *
   * <code>ERROR = 500;</code>
   */
  ERROR(500),
  /**
   * <pre>
   * Critical events cause more severe problems or brief outages.
   * </pre>
   *
   * <code>CRITICAL = 600;</code>
   */
  CRITICAL(600),
  /**
   * <pre>
   * A person must take an action immediately.
   * </pre>
   *
   * <code>ALERT = 700;</code>
   */
  ALERT(700),
  /**
   * <pre>
   * One or more systems are unusable.
   * </pre>
   *
   * <code>EMERGENCY = 800;</code>
   */
  EMERGENCY(800),
  UNRECOGNIZED(-1),
  ;

  /**
   * <pre>
   * The log entry has no assigned severity level.
   * </pre>
   *
   * <code>DEFAULT = 0;</code>
   */
  public static final int DEFAULT_VALUE = 0;
  /**
   * <pre>
   * Debug or trace information.
   * </pre>
   *
   * <code>DEBUG = 100;</code>
   */
  public static final int DEBUG_VALUE = 100;
  /**
   * <pre>
   * Routine information, such as ongoing status or performance.
   * </pre>
   *
   * <code>INFO = 200;</code>
   */
  public static final int INFO_VALUE = 200;
  /**
   * <pre>
   * Normal but significant events, such as start up, shut down, or
   * configuration.
   * </pre>
   *
   * <code>NOTICE = 300;</code>
   */
  public static final int NOTICE_VALUE = 300;
  /**
   * <pre>
   * Warning events might cause problems.
   * </pre>
   *
   * <code>WARNING = 400;</code>
   */
  public static final int WARNING_VALUE = 400;
  /**
   * <pre>
   * Error events are likely to cause problems.
   * </pre>
   *
   * <code>ERROR = 500;</code>
   */
  public static final int ERROR_VALUE = 500;
  /**
   * <pre>
   * Critical events cause more severe problems or brief outages.
   * </pre>
   *
   * <code>CRITICAL = 600;</code>
   */
  public static final int CRITICAL_VALUE = 600;
  /**
   * <pre>
   * A person must take an action immediately.
   * </pre>
   *
   * <code>ALERT = 700;</code>
   */
  public static final int ALERT_VALUE = 700;
  /**
   * <pre>
   * One or more systems are unusable.
   * </pre>
   *
   * <code>EMERGENCY = 800;</code>
   */
  public static final int EMERGENCY_VALUE = 800;


  public final int getNumber() {
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static LogSeverity valueOf(int value) {
    return forNumber(value);
  }

  public static LogSeverity forNumber(int value) {
    switch (value) {
      case 0: return DEFAULT;
      case 100: return DEBUG;
      case 200: return INFO;
      case 300: return NOTICE;
      case 400: return WARNING;
      case 500: return ERROR;
      case 600: return CRITICAL;
      case 700: return ALERT;
      case 800: return EMERGENCY;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<LogSeverity>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      LogSeverity> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<LogSeverity>() {
          public LogSeverity findValueByNumber(int number) {
            return LogSeverity.forNumber(number);
          }
        };

  private final int value;

  private LogSeverity(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.logging.type.LogSeverity)
}
