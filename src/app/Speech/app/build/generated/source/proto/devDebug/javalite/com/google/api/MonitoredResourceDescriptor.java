// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/monitored_resource.proto

package com.google.api;

/**
 * <pre>
 * A descriptor that describes the schema of [MonitoredResource][google.api.MonitoredResource].
 * </pre>
 *
 * Protobuf type {@code google.api.MonitoredResourceDescriptor}
 */
public  final class MonitoredResourceDescriptor extends
    com.google.protobuf.GeneratedMessageLite<
        MonitoredResourceDescriptor, MonitoredResourceDescriptor.Builder> implements
    // @@protoc_insertion_point(message_implements:google.api.MonitoredResourceDescriptor)
    MonitoredResourceDescriptorOrBuilder {
  private MonitoredResourceDescriptor() {
    type_ = "";
    displayName_ = "";
    description_ = "";
    labels_ = emptyProtobufList();
  }
  private int bitField0_;
  public static final int TYPE_FIELD_NUMBER = 1;
  private java.lang.String type_;
  /**
   * <pre>
   * The monitored resource type. For example, the type `"cloudsql_database"`
   * represents databases in Google Cloud SQL.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  public java.lang.String getType() {
    return type_;
  }
  /**
   * <pre>
   * The monitored resource type. For example, the type `"cloudsql_database"`
   * represents databases in Google Cloud SQL.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  public com.google.protobuf.ByteString
      getTypeBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(type_);
  }
  /**
   * <pre>
   * The monitored resource type. For example, the type `"cloudsql_database"`
   * represents databases in Google Cloud SQL.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  private void setType(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    type_ = value;
  }
  /**
   * <pre>
   * The monitored resource type. For example, the type `"cloudsql_database"`
   * represents databases in Google Cloud SQL.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  private void clearType() {
    
    type_ = getDefaultInstance().getType();
  }
  /**
   * <pre>
   * The monitored resource type. For example, the type `"cloudsql_database"`
   * represents databases in Google Cloud SQL.
   * </pre>
   *
   * <code>optional string type = 1;</code>
   */
  private void setTypeBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    type_ = value.toStringUtf8();
  }

  public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
  private java.lang.String displayName_;
  /**
   * <pre>
   * A concise name for the monitored resource type that can be displayed in
   * user interfaces. For example, `"Google Cloud SQL Database"`.
   * </pre>
   *
   * <code>optional string display_name = 2;</code>
   */
  public java.lang.String getDisplayName() {
    return displayName_;
  }
  /**
   * <pre>
   * A concise name for the monitored resource type that can be displayed in
   * user interfaces. For example, `"Google Cloud SQL Database"`.
   * </pre>
   *
   * <code>optional string display_name = 2;</code>
   */
  public com.google.protobuf.ByteString
      getDisplayNameBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(displayName_);
  }
  /**
   * <pre>
   * A concise name for the monitored resource type that can be displayed in
   * user interfaces. For example, `"Google Cloud SQL Database"`.
   * </pre>
   *
   * <code>optional string display_name = 2;</code>
   */
  private void setDisplayName(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    displayName_ = value;
  }
  /**
   * <pre>
   * A concise name for the monitored resource type that can be displayed in
   * user interfaces. For example, `"Google Cloud SQL Database"`.
   * </pre>
   *
   * <code>optional string display_name = 2;</code>
   */
  private void clearDisplayName() {
    
    displayName_ = getDefaultInstance().getDisplayName();
  }
  /**
   * <pre>
   * A concise name for the monitored resource type that can be displayed in
   * user interfaces. For example, `"Google Cloud SQL Database"`.
   * </pre>
   *
   * <code>optional string display_name = 2;</code>
   */
  private void setDisplayNameBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    displayName_ = value.toStringUtf8();
  }

  public static final int DESCRIPTION_FIELD_NUMBER = 3;
  private java.lang.String description_;
  /**
   * <pre>
   * A detailed description of the monitored resource type that can be used in
   * documentation.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  public java.lang.String getDescription() {
    return description_;
  }
  /**
   * <pre>
   * A detailed description of the monitored resource type that can be used in
   * documentation.
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
   * A detailed description of the monitored resource type that can be used in
   * documentation.
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
   * A detailed description of the monitored resource type that can be used in
   * documentation.
   * </pre>
   *
   * <code>optional string description = 3;</code>
   */
  private void clearDescription() {
    
    description_ = getDefaultInstance().getDescription();
  }
  /**
   * <pre>
   * A detailed description of the monitored resource type that can be used in
   * documentation.
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

  public static final int LABELS_FIELD_NUMBER = 4;
  private com.google.protobuf.Internal.ProtobufList<com.google.api.LabelDescriptor> labels_;
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  public java.util.List<com.google.api.LabelDescriptor> getLabelsList() {
    return labels_;
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  public java.util.List<? extends com.google.api.LabelDescriptorOrBuilder> 
      getLabelsOrBuilderList() {
    return labels_;
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  public int getLabelsCount() {
    return labels_.size();
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  public com.google.api.LabelDescriptor getLabels(int index) {
    return labels_.get(index);
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  public com.google.api.LabelDescriptorOrBuilder getLabelsOrBuilder(
      int index) {
    return labels_.get(index);
  }
  private void ensureLabelsIsMutable() {
    if (!labels_.isModifiable()) {
      labels_ =
          com.google.protobuf.GeneratedMessageLite.mutableCopy(labels_);
     }
  }

  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void setLabels(
      int index, com.google.api.LabelDescriptor value) {
    if (value == null) {
      throw new NullPointerException();
    }
    ensureLabelsIsMutable();
    labels_.set(index, value);
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void setLabels(
      int index, com.google.api.LabelDescriptor.Builder builderForValue) {
    ensureLabelsIsMutable();
    labels_.set(index, builderForValue.build());
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void addLabels(com.google.api.LabelDescriptor value) {
    if (value == null) {
      throw new NullPointerException();
    }
    ensureLabelsIsMutable();
    labels_.add(value);
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void addLabels(
      int index, com.google.api.LabelDescriptor value) {
    if (value == null) {
      throw new NullPointerException();
    }
    ensureLabelsIsMutable();
    labels_.add(index, value);
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void addLabels(
      com.google.api.LabelDescriptor.Builder builderForValue) {
    ensureLabelsIsMutable();
    labels_.add(builderForValue.build());
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void addLabels(
      int index, com.google.api.LabelDescriptor.Builder builderForValue) {
    ensureLabelsIsMutable();
    labels_.add(index, builderForValue.build());
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void addAllLabels(
      java.lang.Iterable<? extends com.google.api.LabelDescriptor> values) {
    ensureLabelsIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(
        values, labels_);
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void clearLabels() {
    labels_ = emptyProtobufList();
  }
  /**
   * <pre>
   * A set of labels that can be used to describe instances of this monitored
   * resource type. For example, Google Cloud SQL databases can be labeled with
   * their `"database_id"` and their `"zone"`.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
   */
  private void removeLabels(int index) {
    ensureLabelsIsMutable();
    labels_.remove(index);
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!type_.isEmpty()) {
      output.writeString(1, getType());
    }
    if (!displayName_.isEmpty()) {
      output.writeString(2, getDisplayName());
    }
    if (!description_.isEmpty()) {
      output.writeString(3, getDescription());
    }
    for (int i = 0; i < labels_.size(); i++) {
      output.writeMessage(4, labels_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!type_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getType());
    }
    if (!displayName_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(2, getDisplayName());
    }
    if (!description_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(3, getDescription());
    }
    for (int i = 0; i < labels_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, labels_.get(i));
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.api.MonitoredResourceDescriptor parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.google.api.MonitoredResourceDescriptor parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.api.MonitoredResourceDescriptor parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.api.MonitoredResourceDescriptor prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * <pre>
   * A descriptor that describes the schema of [MonitoredResource][google.api.MonitoredResource].
   * </pre>
   *
   * Protobuf type {@code google.api.MonitoredResourceDescriptor}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.google.api.MonitoredResourceDescriptor, Builder> implements
      // @@protoc_insertion_point(builder_implements:google.api.MonitoredResourceDescriptor)
      com.google.api.MonitoredResourceDescriptorOrBuilder {
    // Construct using com.google.api.MonitoredResourceDescriptor.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * The monitored resource type. For example, the type `"cloudsql_database"`
     * represents databases in Google Cloud SQL.
     * </pre>
     *
     * <code>optional string type = 1;</code>
     */
    public java.lang.String getType() {
      return instance.getType();
    }
    /**
     * <pre>
     * The monitored resource type. For example, the type `"cloudsql_database"`
     * represents databases in Google Cloud SQL.
     * </pre>
     *
     * <code>optional string type = 1;</code>
     */
    public com.google.protobuf.ByteString
        getTypeBytes() {
      return instance.getTypeBytes();
    }
    /**
     * <pre>
     * The monitored resource type. For example, the type `"cloudsql_database"`
     * represents databases in Google Cloud SQL.
     * </pre>
     *
     * <code>optional string type = 1;</code>
     */
    public Builder setType(
        java.lang.String value) {
      copyOnWrite();
      instance.setType(value);
      return this;
    }
    /**
     * <pre>
     * The monitored resource type. For example, the type `"cloudsql_database"`
     * represents databases in Google Cloud SQL.
     * </pre>
     *
     * <code>optional string type = 1;</code>
     */
    public Builder clearType() {
      copyOnWrite();
      instance.clearType();
      return this;
    }
    /**
     * <pre>
     * The monitored resource type. For example, the type `"cloudsql_database"`
     * represents databases in Google Cloud SQL.
     * </pre>
     *
     * <code>optional string type = 1;</code>
     */
    public Builder setTypeBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setTypeBytes(value);
      return this;
    }

    /**
     * <pre>
     * A concise name for the monitored resource type that can be displayed in
     * user interfaces. For example, `"Google Cloud SQL Database"`.
     * </pre>
     *
     * <code>optional string display_name = 2;</code>
     */
    public java.lang.String getDisplayName() {
      return instance.getDisplayName();
    }
    /**
     * <pre>
     * A concise name for the monitored resource type that can be displayed in
     * user interfaces. For example, `"Google Cloud SQL Database"`.
     * </pre>
     *
     * <code>optional string display_name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getDisplayNameBytes() {
      return instance.getDisplayNameBytes();
    }
    /**
     * <pre>
     * A concise name for the monitored resource type that can be displayed in
     * user interfaces. For example, `"Google Cloud SQL Database"`.
     * </pre>
     *
     * <code>optional string display_name = 2;</code>
     */
    public Builder setDisplayName(
        java.lang.String value) {
      copyOnWrite();
      instance.setDisplayName(value);
      return this;
    }
    /**
     * <pre>
     * A concise name for the monitored resource type that can be displayed in
     * user interfaces. For example, `"Google Cloud SQL Database"`.
     * </pre>
     *
     * <code>optional string display_name = 2;</code>
     */
    public Builder clearDisplayName() {
      copyOnWrite();
      instance.clearDisplayName();
      return this;
    }
    /**
     * <pre>
     * A concise name for the monitored resource type that can be displayed in
     * user interfaces. For example, `"Google Cloud SQL Database"`.
     * </pre>
     *
     * <code>optional string display_name = 2;</code>
     */
    public Builder setDisplayNameBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDisplayNameBytes(value);
      return this;
    }

    /**
     * <pre>
     * A detailed description of the monitored resource type that can be used in
     * documentation.
     * </pre>
     *
     * <code>optional string description = 3;</code>
     */
    public java.lang.String getDescription() {
      return instance.getDescription();
    }
    /**
     * <pre>
     * A detailed description of the monitored resource type that can be used in
     * documentation.
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
     * A detailed description of the monitored resource type that can be used in
     * documentation.
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
     * A detailed description of the monitored resource type that can be used in
     * documentation.
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
     * A detailed description of the monitored resource type that can be used in
     * documentation.
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

    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public java.util.List<com.google.api.LabelDescriptor> getLabelsList() {
      return java.util.Collections.unmodifiableList(
          instance.getLabelsList());
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public int getLabelsCount() {
      return instance.getLabelsCount();
    }/**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public com.google.api.LabelDescriptor getLabels(int index) {
      return instance.getLabels(index);
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder setLabels(
        int index, com.google.api.LabelDescriptor value) {
      copyOnWrite();
      instance.setLabels(index, value);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder setLabels(
        int index, com.google.api.LabelDescriptor.Builder builderForValue) {
      copyOnWrite();
      instance.setLabels(index, builderForValue);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder addLabels(com.google.api.LabelDescriptor value) {
      copyOnWrite();
      instance.addLabels(value);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder addLabels(
        int index, com.google.api.LabelDescriptor value) {
      copyOnWrite();
      instance.addLabels(index, value);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder addLabels(
        com.google.api.LabelDescriptor.Builder builderForValue) {
      copyOnWrite();
      instance.addLabels(builderForValue);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder addLabels(
        int index, com.google.api.LabelDescriptor.Builder builderForValue) {
      copyOnWrite();
      instance.addLabels(index, builderForValue);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder addAllLabels(
        java.lang.Iterable<? extends com.google.api.LabelDescriptor> values) {
      copyOnWrite();
      instance.addAllLabels(values);
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder clearLabels() {
      copyOnWrite();
      instance.clearLabels();
      return this;
    }
    /**
     * <pre>
     * A set of labels that can be used to describe instances of this monitored
     * resource type. For example, Google Cloud SQL databases can be labeled with
     * their `"database_id"` and their `"zone"`.
     * </pre>
     *
     * <code>repeated .google.api.LabelDescriptor labels = 4;</code>
     */
    public Builder removeLabels(int index) {
      copyOnWrite();
      instance.removeLabels(index);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.MonitoredResourceDescriptor)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.google.api.MonitoredResourceDescriptor();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        labels_.makeImmutable();
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        com.google.api.MonitoredResourceDescriptor other = (com.google.api.MonitoredResourceDescriptor) arg1;
        type_ = visitor.visitString(!type_.isEmpty(), type_,
            !other.type_.isEmpty(), other.type_);
        displayName_ = visitor.visitString(!displayName_.isEmpty(), displayName_,
            !other.displayName_.isEmpty(), other.displayName_);
        description_ = visitor.visitString(!description_.isEmpty(), description_,
            !other.description_.isEmpty(), other.description_);
        labels_= visitor.visitList(labels_, other.labels_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
          bitField0_ |= other.bitField0_;
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

                type_ = s;
                break;
              }
              case 18: {
                String s = input.readStringRequireUtf8();

                displayName_ = s;
                break;
              }
              case 26: {
                String s = input.readStringRequireUtf8();

                description_ = s;
                break;
              }
              case 34: {
                if (!labels_.isModifiable()) {
                  labels_ =
                      com.google.protobuf.GeneratedMessageLite.mutableCopy(labels_);
                }
                labels_.add(
                    input.readMessage(com.google.api.LabelDescriptor.parser(), extensionRegistry));
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
        if (PARSER == null) {    synchronized (com.google.api.MonitoredResourceDescriptor.class) {
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


  // @@protoc_insertion_point(class_scope:google.api.MonitoredResourceDescriptor)
  private static final com.google.api.MonitoredResourceDescriptor DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new MonitoredResourceDescriptor();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.google.api.MonitoredResourceDescriptor getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<MonitoredResourceDescriptor> PARSER;

  public static com.google.protobuf.Parser<MonitoredResourceDescriptor> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
