package moby.upload.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class UploadGrpc {

  private UploadGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.upload.v1.Upload";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<moby.upload.v1.UploadOuterClass.BytesMessage,
      moby.upload.v1.UploadOuterClass.BytesMessage> getPullMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Pull",
      requestType = moby.upload.v1.UploadOuterClass.BytesMessage.class,
      responseType = moby.upload.v1.UploadOuterClass.BytesMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<moby.upload.v1.UploadOuterClass.BytesMessage,
      moby.upload.v1.UploadOuterClass.BytesMessage> getPullMethod() {
    io.grpc.MethodDescriptor<moby.upload.v1.UploadOuterClass.BytesMessage, moby.upload.v1.UploadOuterClass.BytesMessage> getPullMethod;
    if ((getPullMethod = UploadGrpc.getPullMethod) == null) {
      synchronized (UploadGrpc.class) {
        if ((getPullMethod = UploadGrpc.getPullMethod) == null) {
          UploadGrpc.getPullMethod = getPullMethod =
              io.grpc.MethodDescriptor.<moby.upload.v1.UploadOuterClass.BytesMessage, moby.upload.v1.UploadOuterClass.BytesMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Pull"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.upload.v1.UploadOuterClass.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.upload.v1.UploadOuterClass.BytesMessage.getDefaultInstance()))
              .setSchemaDescriptor(new UploadMethodDescriptorSupplier("Pull"))
              .build();
        }
      }
    }
    return getPullMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UploadStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadStub>() {
        @java.lang.Override
        public UploadStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadStub(channel, callOptions);
        }
      };
    return UploadStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static UploadBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadBlockingV2Stub>() {
        @java.lang.Override
        public UploadBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadBlockingV2Stub(channel, callOptions);
        }
      };
    return UploadBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UploadBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadBlockingStub>() {
        @java.lang.Override
        public UploadBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadBlockingStub(channel, callOptions);
        }
      };
    return UploadBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UploadFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadFutureStub>() {
        @java.lang.Override
        public UploadFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadFutureStub(channel, callOptions);
        }
      };
    return UploadFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<moby.upload.v1.UploadOuterClass.BytesMessage> pull(
        io.grpc.stub.StreamObserver<moby.upload.v1.UploadOuterClass.BytesMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getPullMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Upload.
   */
  public static abstract class UploadImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UploadGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Upload.
   */
  public static final class UploadStub
      extends io.grpc.stub.AbstractAsyncStub<UploadStub> {
    private UploadStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<moby.upload.v1.UploadOuterClass.BytesMessage> pull(
        io.grpc.stub.StreamObserver<moby.upload.v1.UploadOuterClass.BytesMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getPullMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Upload.
   */
  public static final class UploadBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<UploadBlockingV2Stub> {
    private UploadBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<moby.upload.v1.UploadOuterClass.BytesMessage, moby.upload.v1.UploadOuterClass.BytesMessage>
        pull() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getPullMethod(), getCallOptions());
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Upload.
   */
  public static final class UploadBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UploadBlockingStub> {
    private UploadBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Upload.
   */
  public static final class UploadFutureStub
      extends io.grpc.stub.AbstractFutureStub<UploadFutureStub> {
    private UploadFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_PULL = 0;

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
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PULL:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.pull(
              (io.grpc.stub.StreamObserver<moby.upload.v1.UploadOuterClass.BytesMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getPullMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              moby.upload.v1.UploadOuterClass.BytesMessage,
              moby.upload.v1.UploadOuterClass.BytesMessage>(
                service, METHODID_PULL)))
        .build();
  }

  private static abstract class UploadBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UploadBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.upload.v1.UploadOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Upload");
    }
  }

  private static final class UploadFileDescriptorSupplier
      extends UploadBaseDescriptorSupplier {
    UploadFileDescriptorSupplier() {}
  }

  private static final class UploadMethodDescriptorSupplier
      extends UploadBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UploadMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UploadGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UploadFileDescriptorSupplier())
              .addMethod(getPullMethod())
              .build();
        }
      }
    }
    return result;
  }
}
