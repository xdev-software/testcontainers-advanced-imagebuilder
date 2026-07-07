package moby.filesync.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * FileSend allows sending files from the server back to the client.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class FileSendGrpc {

  private FileSendGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.filesync.v1.FileSend";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<fsutil.types.Wire.Packet,
      fsutil.types.Wire.Packet> getDiffCopyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DiffCopy",
      requestType = fsutil.types.Wire.Packet.class,
      responseType = fsutil.types.Wire.Packet.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<fsutil.types.Wire.Packet,
      fsutil.types.Wire.Packet> getDiffCopyMethod() {
    io.grpc.MethodDescriptor<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet> getDiffCopyMethod;
    if ((getDiffCopyMethod = FileSendGrpc.getDiffCopyMethod) == null) {
      synchronized (FileSendGrpc.class) {
        if ((getDiffCopyMethod = FileSendGrpc.getDiffCopyMethod) == null) {
          FileSendGrpc.getDiffCopyMethod = getDiffCopyMethod =
              io.grpc.MethodDescriptor.<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DiffCopy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setSchemaDescriptor(new FileSendMethodDescriptorSupplier("DiffCopy"))
              .build();
        }
      }
    }
    return getDiffCopyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileSendStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSendStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSendStub>() {
        @java.lang.Override
        public FileSendStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSendStub(channel, callOptions);
        }
      };
    return FileSendStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static FileSendBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSendBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSendBlockingV2Stub>() {
        @java.lang.Override
        public FileSendBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSendBlockingV2Stub(channel, callOptions);
        }
      };
    return FileSendBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileSendBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSendBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSendBlockingStub>() {
        @java.lang.Override
        public FileSendBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSendBlockingStub(channel, callOptions);
        }
      };
    return FileSendBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileSendFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSendFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSendFutureStub>() {
        @java.lang.Override
        public FileSendFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSendFutureStub(channel, callOptions);
        }
      };
    return FileSendFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> diffCopy(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDiffCopyMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FileSend.
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public static abstract class FileSendImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FileSendGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FileSend.
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public static final class FileSendStub
      extends io.grpc.stub.AbstractAsyncStub<FileSendStub> {
    private FileSendStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSendStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSendStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> diffCopy(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDiffCopyMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FileSend.
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public static final class FileSendBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<FileSendBlockingV2Stub> {
    private FileSendBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSendBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSendBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>
        diffCopy() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getDiffCopyMethod(), getCallOptions());
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service FileSend.
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public static final class FileSendBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FileSendBlockingStub> {
    private FileSendBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSendBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSendBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FileSend.
   * <pre>
   * FileSend allows sending files from the server back to the client.
   * </pre>
   */
  public static final class FileSendFutureStub
      extends io.grpc.stub.AbstractFutureStub<FileSendFutureStub> {
    private FileSendFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSendFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSendFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DIFF_COPY = 0;

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
        case METHODID_DIFF_COPY:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.diffCopy(
              (io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getDiffCopyMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              fsutil.types.Wire.Packet,
              fsutil.types.Wire.Packet>(
                service, METHODID_DIFF_COPY)))
        .build();
  }

  private static abstract class FileSendBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileSendBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.filesync.v1.FilesyncPacket.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileSend");
    }
  }

  private static final class FileSendFileDescriptorSupplier
      extends FileSendBaseDescriptorSupplier {
    FileSendFileDescriptorSupplier() {}
  }

  private static final class FileSendMethodDescriptorSupplier
      extends FileSendBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FileSendMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FileSendGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileSendFileDescriptorSupplier())
              .addMethod(getDiffCopyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
