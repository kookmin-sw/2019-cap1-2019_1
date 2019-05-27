// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/speech/v1/cloud_speech.proto

package com.google.cloud.speech.v1;

public interface StreamingRecognizeRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.cloud.speech.v1.StreamingRecognizeRequest)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * Provides information to the recognizer that specifies how to process the
   * request. The first `StreamingRecognizeRequest` message must contain a
   * `streaming_config`  message.
   * </pre>
   *
   * <code>optional .google.cloud.speech.v1.StreamingRecognitionConfig streaming_config = 1;</code>
   */
  com.google.cloud.speech.v1.StreamingRecognitionConfig getStreamingConfig();

  /**
   * <pre>
   * The audio data to be recognized. Sequential chunks of audio data are sent
   * in sequential `StreamingRecognizeRequest` messages. The first
   * `StreamingRecognizeRequest` message must not contain `audio_content` data
   * and all subsequent `StreamingRecognizeRequest` messages must contain
   * `audio_content` data. The audio bytes must be encoded as specified in
   * `RecognitionConfig`. Note: as with all bytes fields, protobuffers use a
   * pure binary representation (not base64). See
   * [audio limits](https://cloud.google.com/speech/limits#content).
   * </pre>
   *
   * <code>optional bytes audio_content = 2;</code>
   */
  com.google.protobuf.ByteString getAudioContent();

  public com.google.cloud.speech.v1.StreamingRecognizeRequest.StreamingRequestCase getStreamingRequestCase();
}
