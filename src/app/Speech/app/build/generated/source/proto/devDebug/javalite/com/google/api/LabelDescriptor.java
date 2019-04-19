// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/label.proto

package com.google.api;

/**
 * <pre>
 * A description of a label.
 * </pre>
 *
 * Protobuf type {@code google.api.LabelDescriptor}
 */
public  final class LabelDescriptor extends
    com.google.protobuf.GeneratedMessageLite<
        LabelDescriptor, LabelDescriptor.Builder> implements
    // @@protoc_insertion_point(message_implements:google.api.LabelDescriptor)
    LabelDescriptorOrBuilder {
  private LabelDescriptor() {
    key_ = "";
    description_ = "";
  }
  /**
   * <pre>
   * Value types that can be used as label values.
   * </pre>
   *
   * Protobuf enum {@code google.api.LabelDescriptor.ValueType}
   */
  public enum ValueType
      implements com.google.protobuf.Internal.EnumLite {
    /**
     * <pre>
     * A variable-length string. This is the default.
     * </pre>
     *
     * <code>STRING = 0;</code>
     */
    STRING(0),
    /**
     * <pre>
     * Boolean; true or false.
     * </pre>
     *
     * <code>BOOL = 1;</code>
     */
    BOOL(1),
    /**
     * <pre>
     * A 64-bit signed integer.
     * </pre>
     *
     * <code>INT64 = 2;</code>
     */
    INT64(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <pre>
     * A variable-length string. This is the default.
     * </pre>
     *
     * <code>STRING = 0;</code>
     */
    public static final int STRING_VALUE = 0;
    /**
     * <pre>
     * Boolean; true or false.
     * </pre>
     *
     * <code>BOOL = 1;</code>
     */
    public static final int BOOL_VALUE = 1;
    /**
     * <pre>
     * A 64-bit signed integer.
     * </pre>
     *
     * <code>INT64 = 2;</code>
     */
    public static final int INT64_VALUE = 2;


    public final int getNumber() {
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ValueType valueOf(int value) {
      return forNumber(value);
    }

    public static ValueType forNumber(int value) {
      switch (value) {
        case 0: return STRING;
        case 1: return BOOL;
        case 2: return INT64;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ValueType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ValueType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ValueType>() {
            public ValueType findValueByNumber(int number) {
              return ValueType.forNumber(number);
            }
          };

    private final int value;

    private ValueType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.api.LabelDescriptor.ValueType)
  }

  public static final int KEY_FIELD_NUMBER = 1;
  private java.lang.String key_;
  /**
   * <pre>
   * The label key.
   * </pre>
   *
   * <code>optional string key = 1;</code>
   */
  public java.lang.String getKey() {
    return key_;
  }
  /**
   * <pre>
   * The label key.
   * </pre>
   *
   * <code>optional string key = 1;</code>
   */
  public com.google.protobuf.ByteString
      getKeyBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(key_);
  }
  /**
   * <pre>
   * The label key.
   * </pre>
   *
   * <code>optional string key = 1;</code>
   */
  private void setKey(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    key_ = value;
  }
  /**
   * <pre>
   * The label key.
   * </pre>
   *
   * <code>optional string key = 1;</code>
   */
  private void clearKey() {
    
    key_ = getDefaultInstance().getKey();
  }
  /**
   * <pre>
   * The label key.
   * </pre>
   *
   * <code>optional string key = 1;</code>
   */
  private void setKeyBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    key_ = value.toStringUtf8();
  }

  public static final int VALUE_TYPE_FIELD_NUMBER = 2;
  private int valueType_;
  /**
   * <pre>
   * The type of data that can be assigned to the label.
   * </pre>
   *
   * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
   */
  public int getValueTypeValue() {
    return valueType_;
  }
  /**
   * <pre>
   * The type of data that can be assigned to the label.
   * </pre>
   *
   * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
   */
  public com.google.api.LabelDescriptor.ValueType getValueType() {
    com.google.api.LabelDescriptor.ValueType result = com.google.api.LabelDescriptor.ValueType.forNumber(valueType_);
    return result == null ? com.google.api.LabelDescriptor.ValueType.UNRECOGNIZED : result;
  }
  /**
   * <pre>
   * The type of data that can be assigned to the label.
   * </pre>
   *
   * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
   */
  private void setValueTypeValue(int value) {
      valueType_ = value;
  }
  /**
   * <pre>
   * The type of data that can be assigned to the label.
   * </pre>
   *
   * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
   */
  private void setValueType(com.google.api.LabelDescriptor.ValueType value) {
    if (value == null) {
      throw new NullPointerException();
    }
    
    valueType_ = value.getNumber();
  }
  /**
   * <pre>
   * The type of data that can be assigned to the label.
   * </pre>
   *
   * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
   */
  private void clearValueType() {
    
    valueType_ = 0;
  }

  public static final int DESCRIPTION_FIELD_NUMBER = 3;
  private java.lang.String description_;
  /**
   * <pre>
   * A human-readable description for the label.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  public java.lang.String getDescription() {
    return description_;
  }
  /**
   * <pre>
   * A human-readable description for the label.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  public com.google.protobuf.ByteString
      getDescriptionBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(description_);
  }
  /**
   * <pre>
   * A human-readable description for the label.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  private void setDescription(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    description_ = value;
  }
  /**
   * <pre>
   * A human-readable description for the label.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  private void clearDescription() {
    
    description_ = getDefaultInstance().getDescription();
  }
  /**
   * <pre>
   * A human-readable description for the label.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  private void setDescriptionBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    description_ = value.toStringUtf8();
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!key_.isEmpty()) {
      output.writeString(1, getKey());
    }
    if (valueType_ != com.google.api.LabelDescriptor.ValueType.STRING.getNumber()) {
      output.writeEnum(2, valueType_);
    }
    if (!description_.isEmpty()) {
      output.writeString(3, getDescription());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!key_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getKey());
    }
    if (valueType_ != com.google.api.LabelDescriptor.ValueType.STRING.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, valueType_);
    }
    if (!description_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(3, getDescription());
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static com.google.api.LabelDescriptor parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.api.LabelDescriptor parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.api.LabelDescriptor parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.api.LabelDescriptor parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.api.LabelDescriptor parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.api.LabelDescriptor parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.api.LabelDescriptor parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.google.api.LabelDescriptor parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.api.LabelDescriptor parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.api.LabelDescriptor parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.api.LabelDescriptor prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * <pre>
   * A description of a label.
   * </pre>
   *
   * Protobuf type {@code google.api.LabelDescriptor}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.google.api.LabelDescriptor, Builder> implements
      // @@protoc_insertion_point(builder_implements:google.api.LabelDescriptor)
      com.google.api.LabelDescriptorOrBuilder {
    // Construct using com.google.api.LabelDescriptor.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * The label key.
     * </pre>
     *
     * <code>optional string key = 1;</code>
     */
    public java.lang.String getKey() {
      return instance.getKey();
    }
    /**
     * <pre>
     * The label key.
     * </pre>
     *
     * <code>optional string key = 1;</code>
     */
    public com.google.protobuf.ByteString
        getKeyBytes() {
      return instance.getKeyBytes();
    }
    /**
     * <pre>
     * The label key.
     * </pre>
     *
     * <code>optional string key = 1;</code>
     */
    public Builder setKey(
        java.lang.String value) {
      copyOnWrite();
      instance.setKey(value);
      return this;
    }
    /**
     * <pre>
     * The label key.
     * </pre>
     *
     * <code>optional string key = 1;</code>
     */
    public Builder clearKey() {
      copyOnWrite();
      instance.clearKey();
      return this;
    }
    /**
     * <pre>
     * The label key.
     * </pre>
     *
     * <code>optional string key = 1;</code>
     */
    public Builder setKeyBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setKeyBytes(value);
      return this;
    }

    /**
     * <pre>
     * The type of data that can be assigned to the label.
     * </pre>
     *
     * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
     */
    public int getValueTypeValue() {
      return instance.getValueTypeValue();
    }
    /**
     * <pre>
     * The type of data that can be assigned to the label.
     * </pre>
     *
     * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
     */
    public Builder setValueTypeValue(int value) {
      copyOnWrite();
      instance.setValueTypeValue(value);
      return this;
    }
    /**
     * <pre>
     * The type of data that can be assigned to the label.
     * </pre>
     *
     * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
     */
    public com.google.api.LabelDescriptor.ValueType getValueType() {
      return instance.getValueType();
    }
    /**
     * <pre>
     * The type of data that can be assigned to the label.
     * </pre>
     *
     * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
     */
    public Builder setValueType(com.google.api.LabelDescriptor.ValueType value) {
      copyOnWrite();
      instance.setValueType(value);
      return this;
    }
    /**
     * <pre>
     * The type of data that can be assigned to the label.
     * </pre>
     *
     * <code>optional .google.api.LabelDescriptor.ValueType value_type = 2;</code>
     */
    public Builder clearValueType() {
      copyOnWrite();
      instance.clearValueType();
      return this;
    }

    /**
     * <pre>
     * A human-readable description for the label.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public java.lang.String getDescription() {
      return instance.getDescription();
    }
    /**
     * <pre>
     * A human-readable description for the label.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public com.google.protobuf.ByteString
        getDescriptionBytes() {
      return instance.getDescriptionBytes();
    }
    /**
     * <pre>
     * A human-readable description for the label.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public Builder setDescription(
        java.lang.String value) {
      copyOnWrite();
      instance.setDescription(value);
      return this;
    }
    /**
     * <pre>
     * A human-readable description for the label.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public Builder clearDescription() {
      copyOnWrite();
      instance.clearDescription();
      return this;
    }
    /**
     * <pre>
     * A human-readable description for the label.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public Builder setDescriptionBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDescriptionBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.LabelDescriptor)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.google.api.LabelDescriptor();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        com.google.api.LabelDescriptor other = (com.google.api.LabelDescriptor) arg1;
        key_ = visitor.visitString(!key_.isEmpty(), key_,
            !other.key_.isEmpty(), other.key_);
        valueType_ = visitor.visitInt(valueType_ != 0, valueType_,    other.valueType_ != 0, other.valueType_);
        description_ = visitor.visitString(!description_.isEmpty(), description_,
            !other.description_.isEmpty(), other.description_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 10: {
                String s = input.readStringRequireUtf8();

                key_ = s;
                break;
              }
              case 16: {
                int rawValue = input.readEnum();

                valueType_ = rawValue;
                break;
              }
              case 26: {
                String s = input.readStringRequireUtf8();

                description_ = s;
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (com.google.api.LabelDescriptor.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:google.api.LabelDescriptor)
  private static final com.google.api.LabelDescriptor DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new LabelDescriptor();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.google.api.LabelDescriptor getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<LabelDescriptor> PARSER;

  public static com.google.protobuf.Parser<LabelDescriptor> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
