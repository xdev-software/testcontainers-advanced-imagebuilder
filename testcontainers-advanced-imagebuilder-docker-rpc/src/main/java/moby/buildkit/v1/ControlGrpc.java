package moby.buildkit.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class ControlGrpc {

  private ControlGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.buildkit.v1.Control";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.DiskUsageRequest,
      moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> getDiskUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DiskUsage",
      requestType = moby.buildkit.v1.ControlOuterClass.DiskUsageRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.DiskUsageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.DiskUsageRequest,
      moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> getDiskUsageMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.DiskUsageRequest, moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> getDiskUsageMethod;
    if ((getDiskUsageMethod = ControlGrpc.getDiskUsageMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getDiskUsageMethod = ControlGrpc.getDiskUsageMethod) == null) {
          ControlGrpc.getDiskUsageMethod = getDiskUsageMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.DiskUsageRequest, moby.buildkit.v1.ControlOuterClass.DiskUsageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DiskUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.DiskUsageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.DiskUsageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("DiskUsage"))
              .build();
        }
      }
    }
    return getDiskUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.PruneRequest,
      moby.buildkit.v1.ControlOuterClass.UsageRecord> getPruneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Prune",
      requestType = moby.buildkit.v1.ControlOuterClass.PruneRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.UsageRecord.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.PruneRequest,
      moby.buildkit.v1.ControlOuterClass.UsageRecord> getPruneMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.PruneRequest, moby.buildkit.v1.ControlOuterClass.UsageRecord> getPruneMethod;
    if ((getPruneMethod = ControlGrpc.getPruneMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getPruneMethod = ControlGrpc.getPruneMethod) == null) {
          ControlGrpc.getPruneMethod = getPruneMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.PruneRequest, moby.buildkit.v1.ControlOuterClass.UsageRecord>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Prune"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.PruneRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.UsageRecord.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("Prune"))
              .build();
        }
      }
    }
    return getPruneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.SolveRequest,
      moby.buildkit.v1.ControlOuterClass.SolveResponse> getSolveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Solve",
      requestType = moby.buildkit.v1.ControlOuterClass.SolveRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.SolveResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.SolveRequest,
      moby.buildkit.v1.ControlOuterClass.SolveResponse> getSolveMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.SolveRequest, moby.buildkit.v1.ControlOuterClass.SolveResponse> getSolveMethod;
    if ((getSolveMethod = ControlGrpc.getSolveMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getSolveMethod = ControlGrpc.getSolveMethod) == null) {
          ControlGrpc.getSolveMethod = getSolveMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.SolveRequest, moby.buildkit.v1.ControlOuterClass.SolveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Solve"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.SolveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.SolveResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("Solve"))
              .build();
        }
      }
    }
    return getSolveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.StatusRequest,
      moby.buildkit.v1.ControlOuterClass.StatusResponse> getStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Status",
      requestType = moby.buildkit.v1.ControlOuterClass.StatusRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.StatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.StatusRequest,
      moby.buildkit.v1.ControlOuterClass.StatusResponse> getStatusMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.StatusRequest, moby.buildkit.v1.ControlOuterClass.StatusResponse> getStatusMethod;
    if ((getStatusMethod = ControlGrpc.getStatusMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getStatusMethod = ControlGrpc.getStatusMethod) == null) {
          ControlGrpc.getStatusMethod = getStatusMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.StatusRequest, moby.buildkit.v1.ControlOuterClass.StatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Status"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.StatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.StatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("Status"))
              .build();
        }
      }
    }
    return getStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BytesMessage,
      moby.buildkit.v1.ControlOuterClass.BytesMessage> getSessionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Session",
      requestType = moby.buildkit.v1.ControlOuterClass.BytesMessage.class,
      responseType = moby.buildkit.v1.ControlOuterClass.BytesMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BytesMessage,
      moby.buildkit.v1.ControlOuterClass.BytesMessage> getSessionMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BytesMessage, moby.buildkit.v1.ControlOuterClass.BytesMessage> getSessionMethod;
    if ((getSessionMethod = ControlGrpc.getSessionMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getSessionMethod = ControlGrpc.getSessionMethod) == null) {
          ControlGrpc.getSessionMethod = getSessionMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.BytesMessage, moby.buildkit.v1.ControlOuterClass.BytesMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Session"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.BytesMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("Session"))
              .build();
        }
      }
    }
    return getSessionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.ListWorkersRequest,
      moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> getListWorkersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListWorkers",
      requestType = moby.buildkit.v1.ControlOuterClass.ListWorkersRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.ListWorkersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.ListWorkersRequest,
      moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> getListWorkersMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.ListWorkersRequest, moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> getListWorkersMethod;
    if ((getListWorkersMethod = ControlGrpc.getListWorkersMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getListWorkersMethod = ControlGrpc.getListWorkersMethod) == null) {
          ControlGrpc.getListWorkersMethod = getListWorkersMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.ListWorkersRequest, moby.buildkit.v1.ControlOuterClass.ListWorkersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListWorkers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.ListWorkersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.ListWorkersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("ListWorkers"))
              .build();
        }
      }
    }
    return getListWorkersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.InfoRequest,
      moby.buildkit.v1.ControlOuterClass.InfoResponse> getInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Info",
      requestType = moby.buildkit.v1.ControlOuterClass.InfoRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.InfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.InfoRequest,
      moby.buildkit.v1.ControlOuterClass.InfoResponse> getInfoMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.InfoRequest, moby.buildkit.v1.ControlOuterClass.InfoResponse> getInfoMethod;
    if ((getInfoMethod = ControlGrpc.getInfoMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getInfoMethod = ControlGrpc.getInfoMethod) == null) {
          ControlGrpc.getInfoMethod = getInfoMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.InfoRequest, moby.buildkit.v1.ControlOuterClass.InfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Info"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.InfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.InfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("Info"))
              .build();
        }
      }
    }
    return getInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest,
      moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> getListenBuildHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListenBuildHistory",
      requestType = moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest,
      moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> getListenBuildHistoryMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest, moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> getListenBuildHistoryMethod;
    if ((getListenBuildHistoryMethod = ControlGrpc.getListenBuildHistoryMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getListenBuildHistoryMethod = ControlGrpc.getListenBuildHistoryMethod) == null) {
          ControlGrpc.getListenBuildHistoryMethod = getListenBuildHistoryMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest, moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListenBuildHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("ListenBuildHistory"))
              .build();
        }
      }
    }
    return getListenBuildHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest,
      moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> getUpdateBuildHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateBuildHistory",
      requestType = moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest.class,
      responseType = moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest,
      moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> getUpdateBuildHistoryMethod() {
    io.grpc.MethodDescriptor<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest, moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> getUpdateBuildHistoryMethod;
    if ((getUpdateBuildHistoryMethod = ControlGrpc.getUpdateBuildHistoryMethod) == null) {
      synchronized (ControlGrpc.class) {
        if ((getUpdateBuildHistoryMethod = ControlGrpc.getUpdateBuildHistoryMethod) == null) {
          ControlGrpc.getUpdateBuildHistoryMethod = getUpdateBuildHistoryMethod =
              io.grpc.MethodDescriptor.<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest, moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateBuildHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ControlMethodDescriptorSupplier("UpdateBuildHistory"))
              .build();
        }
      }
    }
    return getUpdateBuildHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ControlStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlStub>() {
        @java.lang.Override
        public ControlStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlStub(channel, callOptions);
        }
      };
    return ControlStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static ControlBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlBlockingV2Stub>() {
        @java.lang.Override
        public ControlBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlBlockingV2Stub(channel, callOptions);
        }
      };
    return ControlBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ControlBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlBlockingStub>() {
        @java.lang.Override
        public ControlBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlBlockingStub(channel, callOptions);
        }
      };
    return ControlBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ControlFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ControlFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ControlFutureStub>() {
        @java.lang.Override
        public ControlFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ControlFutureStub(channel, callOptions);
        }
      };
    return ControlFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void diskUsage(moby.buildkit.v1.ControlOuterClass.DiskUsageRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDiskUsageMethod(), responseObserver);
    }

    /**
     */
    default void prune(moby.buildkit.v1.ControlOuterClass.PruneRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UsageRecord> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPruneMethod(), responseObserver);
    }

    /**
     */
    default void solve(moby.buildkit.v1.ControlOuterClass.SolveRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.SolveResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSolveMethod(), responseObserver);
    }

    /**
     */
    default void status(moby.buildkit.v1.ControlOuterClass.StatusRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.StatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStatusMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BytesMessage> session(
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BytesMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSessionMethod(), responseObserver);
    }

    /**
     */
    default void listWorkers(moby.buildkit.v1.ControlOuterClass.ListWorkersRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListWorkersMethod(), responseObserver);
    }

    /**
     */
    default void info(moby.buildkit.v1.ControlOuterClass.InfoRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.InfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInfoMethod(), responseObserver);
    }

    /**
     */
    default void listenBuildHistory(moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListenBuildHistoryMethod(), responseObserver);
    }

    /**
     */
    default void updateBuildHistory(moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateBuildHistoryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Control.
   */
  public static abstract class ControlImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ControlGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Control.
   */
  public static final class ControlStub
      extends io.grpc.stub.AbstractAsyncStub<ControlStub> {
    private ControlStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlStub(channel, callOptions);
    }

    /**
     */
    public void diskUsage(moby.buildkit.v1.ControlOuterClass.DiskUsageRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDiskUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void prune(moby.buildkit.v1.ControlOuterClass.PruneRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UsageRecord> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getPruneMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void solve(moby.buildkit.v1.ControlOuterClass.SolveRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.SolveResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSolveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void status(moby.buildkit.v1.ControlOuterClass.StatusRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.StatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BytesMessage> session(
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BytesMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getSessionMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void listWorkers(moby.buildkit.v1.ControlOuterClass.ListWorkersRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListWorkersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void info(moby.buildkit.v1.ControlOuterClass.InfoRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.InfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listenBuildHistory(moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListenBuildHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateBuildHistory(moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest request,
        io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateBuildHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Control.
   */
  public static final class ControlBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<ControlBlockingV2Stub> {
    private ControlBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.DiskUsageResponse diskUsage(moby.buildkit.v1.ControlOuterClass.DiskUsageRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDiskUsageMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, moby.buildkit.v1.ControlOuterClass.UsageRecord>
        prune(moby.buildkit.v1.ControlOuterClass.PruneRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getPruneMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.SolveResponse solve(moby.buildkit.v1.ControlOuterClass.SolveRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getSolveMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, moby.buildkit.v1.ControlOuterClass.StatusResponse>
        status(moby.buildkit.v1.ControlOuterClass.StatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<moby.buildkit.v1.ControlOuterClass.BytesMessage, moby.buildkit.v1.ControlOuterClass.BytesMessage>
        session() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getSessionMethod(), getCallOptions());
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.ListWorkersResponse listWorkers(moby.buildkit.v1.ControlOuterClass.ListWorkersRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListWorkersMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.InfoResponse info(moby.buildkit.v1.ControlOuterClass.InfoRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent>
        listenBuildHistory(moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getListenBuildHistoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse updateBuildHistory(moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getUpdateBuildHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Control.
   */
  public static final class ControlBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ControlBlockingStub> {
    private ControlBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlBlockingStub(channel, callOptions);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.DiskUsageResponse diskUsage(moby.buildkit.v1.ControlOuterClass.DiskUsageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDiskUsageMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<moby.buildkit.v1.ControlOuterClass.UsageRecord> prune(
        moby.buildkit.v1.ControlOuterClass.PruneRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getPruneMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.SolveResponse solve(moby.buildkit.v1.ControlOuterClass.SolveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSolveMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<moby.buildkit.v1.ControlOuterClass.StatusResponse> status(
        moby.buildkit.v1.ControlOuterClass.StatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.ListWorkersResponse listWorkers(moby.buildkit.v1.ControlOuterClass.ListWorkersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListWorkersMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.InfoResponse info(moby.buildkit.v1.ControlOuterClass.InfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent> listenBuildHistory(
        moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListenBuildHistoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse updateBuildHistory(moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateBuildHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Control.
   */
  public static final class ControlFutureStub
      extends io.grpc.stub.AbstractFutureStub<ControlFutureStub> {
    private ControlFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ControlFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ControlFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.v1.ControlOuterClass.DiskUsageResponse> diskUsage(
        moby.buildkit.v1.ControlOuterClass.DiskUsageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDiskUsageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.v1.ControlOuterClass.SolveResponse> solve(
        moby.buildkit.v1.ControlOuterClass.SolveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSolveMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.v1.ControlOuterClass.ListWorkersResponse> listWorkers(
        moby.buildkit.v1.ControlOuterClass.ListWorkersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListWorkersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.v1.ControlOuterClass.InfoResponse> info(
        moby.buildkit.v1.ControlOuterClass.InfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse> updateBuildHistory(
        moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateBuildHistoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DISK_USAGE = 0;
  private static final int METHODID_PRUNE = 1;
  private static final int METHODID_SOLVE = 2;
  private static final int METHODID_STATUS = 3;
  private static final int METHODID_LIST_WORKERS = 4;
  private static final int METHODID_INFO = 5;
  private static final int METHODID_LISTEN_BUILD_HISTORY = 6;
  private static final int METHODID_UPDATE_BUILD_HISTORY = 7;
  private static final int METHODID_SESSION = 8;

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
        case METHODID_DISK_USAGE:
          serviceImpl.diskUsage((moby.buildkit.v1.ControlOuterClass.DiskUsageRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.DiskUsageResponse>) responseObserver);
          break;
        case METHODID_PRUNE:
          serviceImpl.prune((moby.buildkit.v1.ControlOuterClass.PruneRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UsageRecord>) responseObserver);
          break;
        case METHODID_SOLVE:
          serviceImpl.solve((moby.buildkit.v1.ControlOuterClass.SolveRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.SolveResponse>) responseObserver);
          break;
        case METHODID_STATUS:
          serviceImpl.status((moby.buildkit.v1.ControlOuterClass.StatusRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.StatusResponse>) responseObserver);
          break;
        case METHODID_LIST_WORKERS:
          serviceImpl.listWorkers((moby.buildkit.v1.ControlOuterClass.ListWorkersRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.ListWorkersResponse>) responseObserver);
          break;
        case METHODID_INFO:
          serviceImpl.info((moby.buildkit.v1.ControlOuterClass.InfoRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.InfoResponse>) responseObserver);
          break;
        case METHODID_LISTEN_BUILD_HISTORY:
          serviceImpl.listenBuildHistory((moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent>) responseObserver);
          break;
        case METHODID_UPDATE_BUILD_HISTORY:
          serviceImpl.updateBuildHistory((moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest) request,
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse>) responseObserver);
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
        case METHODID_SESSION:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.session(
              (io.grpc.stub.StreamObserver<moby.buildkit.v1.ControlOuterClass.BytesMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getDiskUsageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.DiskUsageRequest,
              moby.buildkit.v1.ControlOuterClass.DiskUsageResponse>(
                service, METHODID_DISK_USAGE)))
        .addMethod(
          getPruneMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.PruneRequest,
              moby.buildkit.v1.ControlOuterClass.UsageRecord>(
                service, METHODID_PRUNE)))
        .addMethod(
          getSolveMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.SolveRequest,
              moby.buildkit.v1.ControlOuterClass.SolveResponse>(
                service, METHODID_SOLVE)))
        .addMethod(
          getStatusMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.StatusRequest,
              moby.buildkit.v1.ControlOuterClass.StatusResponse>(
                service, METHODID_STATUS)))
        .addMethod(
          getSessionMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.BytesMessage,
              moby.buildkit.v1.ControlOuterClass.BytesMessage>(
                service, METHODID_SESSION)))
        .addMethod(
          getListWorkersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.ListWorkersRequest,
              moby.buildkit.v1.ControlOuterClass.ListWorkersResponse>(
                service, METHODID_LIST_WORKERS)))
        .addMethod(
          getInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.InfoRequest,
              moby.buildkit.v1.ControlOuterClass.InfoResponse>(
                service, METHODID_INFO)))
        .addMethod(
          getListenBuildHistoryMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.BuildHistoryRequest,
              moby.buildkit.v1.ControlOuterClass.BuildHistoryEvent>(
                service, METHODID_LISTEN_BUILD_HISTORY)))
        .addMethod(
          getUpdateBuildHistoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryRequest,
              moby.buildkit.v1.ControlOuterClass.UpdateBuildHistoryResponse>(
                service, METHODID_UPDATE_BUILD_HISTORY)))
        .build();
  }

  private static abstract class ControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ControlBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.buildkit.v1.ControlOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Control");
    }
  }

  private static final class ControlFileDescriptorSupplier
      extends ControlBaseDescriptorSupplier {
    ControlFileDescriptorSupplier() {}
  }

  private static final class ControlMethodDescriptorSupplier
      extends ControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ControlMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ControlGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ControlFileDescriptorSupplier())
              .addMethod(getDiskUsageMethod())
              .addMethod(getPruneMethod())
              .addMethod(getSolveMethod())
              .addMethod(getStatusMethod())
              .addMethod(getSessionMethod())
              .addMethod(getListWorkersMethod())
              .addMethod(getInfoMethod())
              .addMethod(getListenBuildHistoryMethod())
              .addMethod(getUpdateBuildHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
