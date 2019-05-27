// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/logging/v2/logging_config.proto

package com.google.logging.v2;

/**
 * <pre>
 * The parameters to `UpdateSink`.
 * </pre>
 *
 * Protobuf type {@code google.logging.v2.UpdateSinkRequest}
 */
public  final class UpdateSinkRequest extends
    com.google.protobuf.GeneratedMessageLite<
        UpdateSinkRequest, UpdateSinkRequest.Builder> implements
    // @@protoc_insertion_point(message_implements:google.logging.v2.UpdateSinkRequest)
    UpdateSinkRequestOrBuilder {
  private UpdateSinkRequest() {
    sinkName_ = "";
  }
  public static final int SINK_NAME_FIELD_NUMBER = 1;
  private java.lang.String sinkName_;
  /**
   * <pre>
   * The resource name of the sink to update.
   * Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * The updated sink must be provided in the request and have the
   * same name that is specified in `sinkName`.  If the sink does not
   * exist, it is created.
   * </pre>
   *
   * <code>optional string sink_name = 1;</code>
   */
  public java.lang.String getSinkName() {
    return sinkName_;
  }
  /**
   * <pre>
   * The resource name of the sink to update.
   * Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * The updated sink must be provided in the request and have the
   * same name that is specified in `sinkName`.  If the sink does not
   * exist, it is created.
   * </pre>
   *
   * <code>optional string sink_name = 1;</code>
   */
  public com.google.protobuf.ByteString
      getSinkNameBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(sinkName_);
  }
  /**
   * <pre>
   * The resource name of the sink to update.
   * Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * The updated sink must be provided in the request and have the
   * same name that is specified in `sinkName`.  If the sink does not
   * exist, it is created.
   * </pre>
   *
   * <code>optional string sink_name = 1;</code>
   */
  private void setSinkName(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    sinkName_ = value;
  }
  /**
   * <pre>
   * The resource name of the sink to update.
   * Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * The updated sink must be provided in the request and have the
   * same name that is specified in `sinkName`.  If the sink does not
   * exist, it is created.
   * </pre>
   *
   * <code>optional string sink_name = 1;</code>
   */
  private void clearSinkName() {
    
    sinkName_ = getDefaultInstance().getSinkName();
  }
  /**
   * <pre>
   * The resource name of the sink to update.
   * Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * The updated sink must be provided in the request and have the
   * same name that is specified in `sinkName`.  If the sink does not
   * exist, it is created.
   * </pre>
   *
   * <code>optional string sink_name = 1;</code>
   */
  private void setSinkNameBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    sinkName_ = value.toStringUtf8();
  }

  public static final int SINK_FIELD_NUMBER = 2;
  private com.google.logging.v2.LogSink sink_;
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  public boolean hasSink() {
    return sink_ != null;
  }
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  public com.google.logging.v2.LogSink getSink() {
    return sink_ == null ? com.google.logging.v2.LogSink.getDefaultInstance() : sink_;
  }
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  private void setSink(com.google.logging.v2.LogSink value) {
    if (value == null) {
      throw new NullPointerException();
    }
    sink_ = value;
    
    }
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  private void setSink(
      com.google.logging.v2.LogSink.Builder builderForValue) {
    sink_ = builderForValue.build();
    
  }
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  private void mergeSink(com.google.logging.v2.LogSink value) {
    if (sink_ != null &&
        sink_ != com.google.logging.v2.LogSink.getDefaultInstance()) {
      sink_ =
        com.google.logging.v2.LogSink.newBuilder(sink_).mergeFrom(value).buildPartial();
    } else {
      sink_ = value;
    }
    
  }
  /**
   * <pre>
   * The updated sink, whose name must be the same as the sink
   * identifier in `sinkName`.  If `sinkName` does not exist, then
   * this method creates a new sink.
   * </pre>
   *
   * <code>optional .google.logging.v2.LogSink sink = 2;</code>
   */
  private void clearSink() {  sink_ = null;
    
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!sinkName_.isEmpty()) {
      output.writeString(1, getSinkName());
    }
    if (sink_ != null) {
      output.writeMessage(2, getSink());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!sinkName_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getSinkName());
    }
    if (sink_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getSink());
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.logging.v2.UpdateSinkRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.logging.v2.UpdateSinkRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * <pre>
   * The parameters to `UpdateSink`.
   * </pre>
   *
   * Protobuf type {@code google.logging.v2.UpdateSinkRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.google.logging.v2.UpdateSinkRequest, Builder> implements
      // @@protoc_insertion_point(builder_implements:google.logging.v2.UpdateSinkRequest)
      com.google.logging.v2.UpdateSinkRequestOrBuilder {
    // Construct using com.google.logging.v2.UpdateSinkRequest.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * The resource name of the sink to update.
     * Example: `"projects/my-project-id/sinks/my-sink-id"`.
     * The updated sink must be provided in the request and have the
     * same name that is specified in `sinkName`.  If the sink does not
     * exist, it is created.
     * </pre>
     *
     * <code>optional string sink_name = 1;</code>
     */
    public java.lang.String getSinkName() {
      return instance.getSinkName();
    }
    /**
     * <pre>
     * The resource name of the sink to update.
     * Example: `"projects/my-project-id/sinks/my-sink-id"`.
     * The updated sink must be provided in the request and have the
     * same name that is specified in `sinkName`.  If the sink does not
     * exist, it is created.
     * </pre>
     *
     * <code>optional string sink_name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getSinkNameBytes() {
      return instance.getSinkNameBytes();
    }
    /**
     * <pre>
     * The resource name of the sink to update.
     * Example: `"projects/my-project-id/sinks/my-sink-id"`.
     * The updated sink must be provided in the request and have the
     * same name that is specified in `sinkName`.  If the sink does not
     * exist, it is created.
     * </pre>
     *
     * <code>optional string sink_name = 1;</code>
     */
    public Builder setSinkName(
        java.lang.String value) {
      copyOnWrite();
      instance.setSinkName(value);
      return this;
    }
    /**
     * <pre>
     * The resource name of the sink to update.
     * Example: `"projects/my-project-id/sinks/my-sink-id"`.
     * The updated sink must be provided in the request and have the
     * same name that is specified in `sinkName`.  If the sink does not
     * exist, it is created.
     * </pre>
     *
     * <code>optional string sink_name = 1;</code>
     */
    public Builder clearSinkName() {
      copyOnWrite();
      instance.clearSinkName();
      return this;
    }
    /**
     * <pre>
     * The resource name of the sink to update.
     * Example: `"projects/my-project-id/sinks/my-sink-id"`.
     * The updated sink must be provided in the request and have the
     * same name that is specified in `sinkName`.  If the sink does not
     * exist, it is created.
     * </pre>
     *
     * <code>optional string sink_name = 1;</code>
     */
    public Builder setSinkNameBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setSinkNameBytes(value);
      return this;
    }

    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public boolean hasSink() {
      return instance.hasSink();
    }
    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public com.google.logging.v2.LogSink getSink() {
      return instance.getSink();
    }
    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public Builder setSink(com.google.logging.v2.LogSink value) {
      copyOnWrite();
      instance.setSink(value);
      return this;
      }
    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public Builder setSink(
        com.google.logging.v2.LogSink.Builder builderForValue) {
      copyOnWrite();
      instance.setSink(builderForValue);
      return this;
    }
    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public Builder mergeSink(com.google.logging.v2.LogSink value) {
      copyOnWrite();
      instance.mergeSink(value);
      return this;
    }
    /**
     * <pre>
     * The updated sink, whose name must be the same as the sink
     * identifier in `sinkName`.  If `sinkName` does not exist, then
     * this method creates a new sink.
     * </pre>
     *
     * <code>optional .google.logging.v2.LogSink sink = 2;</code>
     */
    public Builder clearSink() {  copyOnWrite();
      instance.clearSink();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.logging.v2.UpdateSinkRequest)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.google.logging.v2.UpdateSinkRequest();
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
        com.google.logging.v2.UpdateSinkRequest other = (com.google.logging.v2.UpdateSinkRequest) arg1;
        sinkName_ = visitor.visitString(!sinkName_.isEmpty(), sinkName_,
            !other.sinkName_.isEmpty(), other.sinkName_);
        sink_ = visitor.visitMessage(sink_, other.sink_);
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

                sinkName_ = s;
                break;
              }
              case 18: {
                com.google.logging.v2.LogSink.Builder subBuilder = null;
                if (sink_ != null) {
                  subBuilder = sink_.toBuilder();
                }
                sink_ = input.readMessage(com.google.logging.v2.LogSink.parser(), extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(sink_);
                  sink_ = subBuilder.buildPartial();
                }

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
        if (PARSER == null) {    synchronized (com.google.logging.v2.UpdateSinkRequest.class) {
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


  // @@protoc_insertion_point(class_scope:google.logging.v2.UpdateSinkRequest)
  private static final com.google.logging.v2.UpdateSinkRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new UpdateSinkRequest();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.google.logging.v2.UpdateSinkRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<UpdateSinkRequest> PARSER;

  public static com.google.protobuf.Parser<UpdateSinkRequest> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
