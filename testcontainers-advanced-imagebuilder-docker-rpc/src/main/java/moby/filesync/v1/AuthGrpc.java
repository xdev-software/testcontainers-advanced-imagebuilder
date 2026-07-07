package moby.filesync.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthGrpc {

  private AuthGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.filesync.v1.Auth";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.CredentialsRequest,
      moby.filesync.v1.AuthOuterClass.CredentialsResponse> getCredentialsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Credentials",
      requestType = moby.filesync.v1.AuthOuterClass.CredentialsRequest.class,
      responseType = moby.filesync.v1.AuthOuterClass.CredentialsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.CredentialsRequest,
      moby.filesync.v1.AuthOuterClass.CredentialsResponse> getCredentialsMethod() {
    io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.CredentialsRequest, moby.filesync.v1.AuthOuterClass.CredentialsResponse> getCredentialsMethod;
    if ((getCredentialsMethod = AuthGrpc.getCredentialsMethod) == null) {
      synchronized (AuthGrpc.class) {
        if ((getCredentialsMethod = AuthGrpc.getCredentialsMethod) == null) {
          AuthGrpc.getCredentialsMethod = getCredentialsMethod =
              io.grpc.MethodDescriptor.<moby.filesync.v1.AuthOuterClass.CredentialsRequest, moby.filesync.v1.AuthOuterClass.CredentialsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Credentials"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.CredentialsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.CredentialsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthMethodDescriptorSupplier("Credentials"))
              .build();
        }
      }
    }
    return getCredentialsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.FetchTokenRequest,
      moby.filesync.v1.AuthOuterClass.FetchTokenResponse> getFetchTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FetchToken",
      requestType = moby.filesync.v1.AuthOuterClass.FetchTokenRequest.class,
      responseType = moby.filesync.v1.AuthOuterClass.FetchTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.FetchTokenRequest,
      moby.filesync.v1.AuthOuterClass.FetchTokenResponse> getFetchTokenMethod() {
    io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.FetchTokenRequest, moby.filesync.v1.AuthOuterClass.FetchTokenResponse> getFetchTokenMethod;
    if ((getFetchTokenMethod = AuthGrpc.getFetchTokenMethod) == null) {
      synchronized (AuthGrpc.class) {
        if ((getFetchTokenMethod = AuthGrpc.getFetchTokenMethod) == null) {
          AuthGrpc.getFetchTokenMethod = getFetchTokenMethod =
              io.grpc.MethodDescriptor.<moby.filesync.v1.AuthOuterClass.FetchTokenRequest, moby.filesync.v1.AuthOuterClass.FetchTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FetchToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.FetchTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.FetchTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthMethodDescriptorSupplier("FetchToken"))
              .build();
        }
      }
    }
    return getFetchTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest,
      moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> getGetTokenAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTokenAuthority",
      requestType = moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest.class,
      responseType = moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest,
      moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> getGetTokenAuthorityMethod() {
    io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest, moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> getGetTokenAuthorityMethod;
    if ((getGetTokenAuthorityMethod = AuthGrpc.getGetTokenAuthorityMethod) == null) {
      synchronized (AuthGrpc.class) {
        if ((getGetTokenAuthorityMethod = AuthGrpc.getGetTokenAuthorityMethod) == null) {
          AuthGrpc.getGetTokenAuthorityMethod = getGetTokenAuthorityMethod =
              io.grpc.MethodDescriptor.<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest, moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTokenAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthMethodDescriptorSupplier("GetTokenAuthority"))
              .build();
        }
      }
    }
    return getGetTokenAuthorityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest,
      moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> getVerifyTokenAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VerifyTokenAuthority",
      requestType = moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest.class,
      responseType = moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest,
      moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> getVerifyTokenAuthorityMethod() {
    io.grpc.MethodDescriptor<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest, moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> getVerifyTokenAuthorityMethod;
    if ((getVerifyTokenAuthorityMethod = AuthGrpc.getVerifyTokenAuthorityMethod) == null) {
      synchronized (AuthGrpc.class) {
        if ((getVerifyTokenAuthorityMethod = AuthGrpc.getVerifyTokenAuthorityMethod) == null) {
          AuthGrpc.getVerifyTokenAuthorityMethod = getVerifyTokenAuthorityMethod =
              io.grpc.MethodDescriptor.<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest, moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VerifyTokenAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthMethodDescriptorSupplier("VerifyTokenAuthority"))
              .build();
        }
      }
    }
    return getVerifyTokenAuthorityMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthStub>() {
        @java.lang.Override
        public AuthStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthStub(channel, callOptions);
        }
      };
    return AuthStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static AuthBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthBlockingV2Stub>() {
        @java.lang.Override
        public AuthBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthBlockingV2Stub(channel, callOptions);
        }
      };
    return AuthBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthBlockingStub>() {
        @java.lang.Override
        public AuthBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthBlockingStub(channel, callOptions);
        }
      };
    return AuthBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthFutureStub>() {
        @java.lang.Override
        public AuthFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthFutureStub(channel, callOptions);
        }
      };
    return AuthFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void credentials(moby.filesync.v1.AuthOuterClass.CredentialsRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.CredentialsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCredentialsMethod(), responseObserver);
    }

    /**
     */
    default void fetchToken(moby.filesync.v1.AuthOuterClass.FetchTokenRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.FetchTokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchTokenMethod(), responseObserver);
    }

    /**
     */
    default void getTokenAuthority(moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTokenAuthorityMethod(), responseObserver);
    }

    /**
     */
    default void verifyTokenAuthority(moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyTokenAuthorityMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Auth.
   */
  public static abstract class AuthImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AuthGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Auth.
   */
  public static final class AuthStub
      extends io.grpc.stub.AbstractAsyncStub<AuthStub> {
    private AuthStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthStub(channel, callOptions);
    }

    /**
     */
    public void credentials(moby.filesync.v1.AuthOuterClass.CredentialsRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.CredentialsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCredentialsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fetchToken(moby.filesync.v1.AuthOuterClass.FetchTokenRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.FetchTokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFetchTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTokenAuthority(moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTokenAuthorityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void verifyTokenAuthority(moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest request,
        io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyTokenAuthorityMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Auth.
   */
  public static final class AuthBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<AuthBlockingV2Stub> {
    private AuthBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.CredentialsResponse credentials(moby.filesync.v1.AuthOuterClass.CredentialsRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCredentialsMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.FetchTokenResponse fetchToken(moby.filesync.v1.AuthOuterClass.FetchTokenRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getFetchTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse getTokenAuthority(moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetTokenAuthorityMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse verifyTokenAuthority(moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getVerifyTokenAuthorityMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Auth.
   */
  public static final class AuthBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AuthBlockingStub> {
    private AuthBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthBlockingStub(channel, callOptions);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.CredentialsResponse credentials(moby.filesync.v1.AuthOuterClass.CredentialsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCredentialsMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.FetchTokenResponse fetchToken(moby.filesync.v1.AuthOuterClass.FetchTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFetchTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse getTokenAuthority(moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTokenAuthorityMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse verifyTokenAuthority(moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyTokenAuthorityMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Auth.
   */
  public static final class AuthFutureStub
      extends io.grpc.stub.AbstractFutureStub<AuthFutureStub> {
    private AuthFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.filesync.v1.AuthOuterClass.CredentialsResponse> credentials(
        moby.filesync.v1.AuthOuterClass.CredentialsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCredentialsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.filesync.v1.AuthOuterClass.FetchTokenResponse> fetchToken(
        moby.filesync.v1.AuthOuterClass.FetchTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFetchTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse> getTokenAuthority(
        moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTokenAuthorityMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse> verifyTokenAuthority(
        moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyTokenAuthorityMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREDENTIALS = 0;
  private static final int METHODID_FETCH_TOKEN = 1;
  private static final int METHODID_GET_TOKEN_AUTHORITY = 2;
  private static final int METHODID_VERIFY_TOKEN_AUTHORITY = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREDENTIALS:
          serviceImpl.credentials((moby.filesync.v1.AuthOuterClass.CredentialsRequest) request,
              (io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.CredentialsResponse>) responseObserver);
          break;
        case METHODID_FETCH_TOKEN:
          serviceImpl.fetchToken((moby.filesync.v1.AuthOuterClass.FetchTokenRequest) request,
              (io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.FetchTokenResponse>) responseObserver);
          break;
        case METHODID_GET_TOKEN_AUTHORITY:
          serviceImpl.getTokenAuthority((moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse>) responseObserver);
          break;
        case METHODID_VERIFY_TOKEN_AUTHORITY:
          serviceImpl.verifyTokenAuthority((moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCredentialsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.filesync.v1.AuthOuterClass.CredentialsRequest,
              moby.filesync.v1.AuthOuterClass.CredentialsResponse>(
                service, METHODID_CREDENTIALS)))
        .addMethod(
          getFetchTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.filesync.v1.AuthOuterClass.FetchTokenRequest,
              moby.filesync.v1.AuthOuterClass.FetchTokenResponse>(
                service, METHODID_FETCH_TOKEN)))
        .addMethod(
          getGetTokenAuthorityMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.filesync.v1.AuthOuterClass.GetTokenAuthorityRequest,
              moby.filesync.v1.AuthOuterClass.GetTokenAuthorityResponse>(
                service, METHODID_GET_TOKEN_AUTHORITY)))
        .addMethod(
          getVerifyTokenAuthorityMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityRequest,
              moby.filesync.v1.AuthOuterClass.VerifyTokenAuthorityResponse>(
                service, METHODID_VERIFY_TOKEN_AUTHORITY)))
        .build();
  }

  private static abstract class AuthBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.filesync.v1.AuthOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Auth");
    }
  }

  private static final class AuthFileDescriptorSupplier
      extends AuthBaseDescriptorSupplier {
    AuthFileDescriptorSupplier() {}
  }

  private static final class AuthMethodDescriptorSupplier
      extends AuthBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AuthMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthFileDescriptorSupplier())
              .addMethod(getCredentialsMethod())
              .addMethod(getFetchTokenMethod())
              .addMethod(getGetTokenAuthorityMethod())
              .addMethod(getVerifyTokenAuthorityMethod())
              .build();
        }
      }
    }
    return result;
  }
}
