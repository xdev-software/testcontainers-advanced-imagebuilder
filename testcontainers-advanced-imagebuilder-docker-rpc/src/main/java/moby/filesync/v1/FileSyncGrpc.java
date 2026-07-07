package moby.filesync.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * FileSync exposes local files from the client to the server.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class FileSyncGrpc {

  private FileSyncGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.filesync.v1.FileSync";

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
    if ((getDiffCopyMethod = FileSyncGrpc.getDiffCopyMethod) == null) {
      synchronized (FileSyncGrpc.class) {
        if ((getDiffCopyMethod = FileSyncGrpc.getDiffCopyMethod) == null) {
          FileSyncGrpc.getDiffCopyMethod = getDiffCopyMethod =
              io.grpc.MethodDescriptor.<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DiffCopy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setSchemaDescriptor(new FileSyncMethodDescriptorSupplier("DiffCopy"))
              .build();
        }
      }
    }
    return getDiffCopyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fsutil.types.Wire.Packet,
      fsutil.types.Wire.Packet> getTarStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TarStream",
      requestType = fsutil.types.Wire.Packet.class,
      responseType = fsutil.types.Wire.Packet.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<fsutil.types.Wire.Packet,
      fsutil.types.Wire.Packet> getTarStreamMethod() {
    io.grpc.MethodDescriptor<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet> getTarStreamMethod;
    if ((getTarStreamMethod = FileSyncGrpc.getTarStreamMethod) == null) {
      synchronized (FileSyncGrpc.class) {
        if ((getTarStreamMethod = FileSyncGrpc.getTarStreamMethod) == null) {
          FileSyncGrpc.getTarStreamMethod = getTarStreamMethod =
              io.grpc.MethodDescriptor.<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TarStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fsutil.types.Wire.Packet.getDefaultInstance()))
              .setSchemaDescriptor(new FileSyncMethodDescriptorSupplier("TarStream"))
              .build();
        }
      }
    }
    return getTarStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileSyncStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSyncStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSyncStub>() {
        @java.lang.Override
        public FileSyncStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSyncStub(channel, callOptions);
        }
      };
    return FileSyncStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static FileSyncBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSyncBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSyncBlockingV2Stub>() {
        @java.lang.Override
        public FileSyncBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSyncBlockingV2Stub(channel, callOptions);
        }
      };
    return FileSyncBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileSyncBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSyncBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSyncBlockingStub>() {
        @java.lang.Override
        public FileSyncBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSyncBlockingStub(channel, callOptions);
        }
      };
    return FileSyncBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileSyncFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileSyncFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileSyncFutureStub>() {
        @java.lang.Override
        public FileSyncFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileSyncFutureStub(channel, callOptions);
        }
      };
    return FileSyncFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> diffCopy(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDiffCopyMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> tarStream(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getTarStreamMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FileSync.
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public static abstract class FileSyncImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FileSyncGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FileSync.
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public static final class FileSyncStub
      extends io.grpc.stub.AbstractAsyncStub<FileSyncStub> {
    private FileSyncStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSyncStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSyncStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> diffCopy(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDiffCopyMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> tarStream(
        io.grpc.stub.StreamObserver<fsutil.types.Wire.Packet> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getTarStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FileSync.
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public static final class FileSyncBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<FileSyncBlockingV2Stub> {
    private FileSyncBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSyncBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSyncBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>
        diffCopy() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getDiffCopyMethod(), getCallOptions());
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<fsutil.types.Wire.Packet, fsutil.types.Wire.Packet>
        tarStream() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getTarStreamMethod(), getCallOptions());
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service FileSync.
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public static final class FileSyncBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FileSyncBlockingStub> {
    private FileSyncBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSyncBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSyncBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FileSync.
   * <pre>
   * FileSync exposes local files from the client to the server.
   * </pre>
   */
  public static final class FileSyncFutureStub
      extends io.grpc.stub.AbstractFutureStub<FileSyncFutureStub> {
    private FileSyncFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileSyncFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileSyncFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DIFF_COPY = 0;
  private static final int METHODID_TAR_STREAM = 1;

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
        case METHODID_TAR_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.tarStream(
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
        .addMethod(
          getTarStreamMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              fsutil.types.Wire.Packet,
              fsutil.types.Wire.Packet>(
                service, METHODID_TAR_STREAM)))
        .build();
  }

  private static abstract class FileSyncBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileSyncBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.filesync.v1.FilesyncPacket.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileSync");
    }
  }

  private static final class FileSyncFileDescriptorSupplier
      extends FileSyncBaseDescriptorSupplier {
    FileSyncFileDescriptorSupplier() {}
  }

  private static final class FileSyncMethodDescriptorSupplier
      extends FileSyncBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FileSyncMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FileSyncGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileSyncFileDescriptorSupplier())
              .addMethod(getDiffCopyMethod())
              .addMethod(getTarStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
