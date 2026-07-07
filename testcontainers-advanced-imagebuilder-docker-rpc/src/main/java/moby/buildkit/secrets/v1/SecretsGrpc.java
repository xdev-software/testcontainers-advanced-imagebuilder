package moby.buildkit.secrets.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SecretsGrpc {

  private SecretsGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.buildkit.secrets.v1.Secrets";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest,
      moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> getGetSecretMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSecret",
      requestType = moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest.class,
      responseType = moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest,
      moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> getGetSecretMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest, moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> getGetSecretMethod;
    if ((getGetSecretMethod = SecretsGrpc.getGetSecretMethod) == null) {
      synchronized (SecretsGrpc.class) {
        if ((getGetSecretMethod = SecretsGrpc.getGetSecretMethod) == null) {
          SecretsGrpc.getGetSecretMethod = getGetSecretMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest, moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSecret"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SecretsMethodDescriptorSupplier("GetSecret"))
              .build();
        }
      }
    }
    return getGetSecretMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SecretsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecretsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecretsStub>() {
        @java.lang.Override
        public SecretsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecretsStub(channel, callOptions);
        }
      };
    return SecretsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SecretsBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecretsBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecretsBlockingV2Stub>() {
        @java.lang.Override
        public SecretsBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecretsBlockingV2Stub(channel, callOptions);
        }
      };
    return SecretsBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SecretsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecretsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecretsBlockingStub>() {
        @java.lang.Override
        public SecretsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecretsBlockingStub(channel, callOptions);
        }
      };
    return SecretsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SecretsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecretsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecretsFutureStub>() {
        @java.lang.Override
        public SecretsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecretsFutureStub(channel, callOptions);
        }
      };
    return SecretsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getSecret(moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSecretMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Secrets.
   */
  public static abstract class SecretsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SecretsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Secrets.
   */
  public static final class SecretsStub
      extends io.grpc.stub.AbstractAsyncStub<SecretsStub> {
    private SecretsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecretsStub(channel, callOptions);
    }

    /**
     */
    public void getSecret(moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSecretMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Secrets.
   */
  public static final class SecretsBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SecretsBlockingV2Stub> {
    private SecretsBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretsBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecretsBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse getSecret(moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetSecretMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Secrets.
   */
  public static final class SecretsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SecretsBlockingStub> {
    private SecretsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecretsBlockingStub(channel, callOptions);
    }

    /**
     */
    public moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse getSecret(moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSecretMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Secrets.
   */
  public static final class SecretsFutureStub
      extends io.grpc.stub.AbstractFutureStub<SecretsFutureStub> {
    private SecretsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecretsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse> getSecret(
        moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSecretMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SECRET = 0;

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
        case METHODID_GET_SECRET:
          serviceImpl.getSecret((moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse>) responseObserver);
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
          getGetSecretMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretRequest,
              moby.buildkit.secrets.v1.SecretsOuterClass.GetSecretResponse>(
                service, METHODID_GET_SECRET)))
        .build();
  }

  private static abstract class SecretsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SecretsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.buildkit.secrets.v1.SecretsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Secrets");
    }
  }

  private static final class SecretsFileDescriptorSupplier
      extends SecretsBaseDescriptorSupplier {
    SecretsFileDescriptorSupplier() {}
  }

  private static final class SecretsMethodDescriptorSupplier
      extends SecretsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SecretsMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SecretsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SecretsFileDescriptorSupplier())
              .addMethod(getGetSecretMethod())
              .build();
        }
      }
    }
    return result;
  }
}
