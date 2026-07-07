package io.grpc.health.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Health is gRPC's mechanism for checking whether a server is able to handle
 * RPCs. Its semantics are documented in
 * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class HealthGrpc {

  private HealthGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.health.v1.Health";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Check",
      requestType = io.grpc.health.v1.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getCheckMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse> getCheckMethod;
    if ((getCheckMethod = HealthGrpc.getCheckMethod) == null) {
      synchronized (HealthGrpc.class) {
        if ((getCheckMethod = HealthGrpc.getCheckMethod) == null) {
          HealthGrpc.getCheckMethod = getCheckMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Check"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthMethodDescriptorSupplier("Check"))
              .build();
        }
      }
    }
    return getCheckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.HealthListRequest,
      io.grpc.health.v1.HealthListResponse> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "List",
      requestType = io.grpc.health.v1.HealthListRequest.class,
      responseType = io.grpc.health.v1.HealthListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.HealthListRequest,
      io.grpc.health.v1.HealthListResponse> getListMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.HealthListRequest, io.grpc.health.v1.HealthListResponse> getListMethod;
    if ((getListMethod = HealthGrpc.getListMethod) == null) {
      synchronized (HealthGrpc.class) {
        if ((getListMethod = HealthGrpc.getListMethod) == null) {
          HealthGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.HealthListRequest, io.grpc.health.v1.HealthListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "List"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthMethodDescriptorSupplier("List"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getWatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Watch",
      requestType = io.grpc.health.v1.HealthCheckRequest.class,
      responseType = io.grpc.health.v1.HealthCheckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest,
      io.grpc.health.v1.HealthCheckResponse> getWatchMethod() {
    io.grpc.MethodDescriptor<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse> getWatchMethod;
    if ((getWatchMethod = HealthGrpc.getWatchMethod) == null) {
      synchronized (HealthGrpc.class) {
        if ((getWatchMethod = HealthGrpc.getWatchMethod) == null) {
          HealthGrpc.getWatchMethod = getWatchMethod =
              io.grpc.MethodDescriptor.<io.grpc.health.v1.HealthCheckRequest, io.grpc.health.v1.HealthCheckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Watch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.health.v1.HealthCheckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthMethodDescriptorSupplier("Watch"))
              .build();
        }
      }
    }
    return getWatchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HealthStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthStub>() {
        @java.lang.Override
        public HealthStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthStub(channel, callOptions);
        }
      };
    return HealthStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static HealthBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthBlockingV2Stub>() {
        @java.lang.Override
        public HealthBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthBlockingV2Stub(channel, callOptions);
        }
      };
    return HealthBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HealthBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthBlockingStub>() {
        @java.lang.Override
        public HealthBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthBlockingStub(channel, callOptions);
        }
      };
    return HealthBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HealthFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthFutureStub>() {
        @java.lang.Override
        public HealthFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthFutureStub(channel, callOptions);
        }
      };
    return HealthFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Check gets the health of the specified service. If the requested service
     * is unknown, the call will fail with status NOT_FOUND. If the caller does
     * not specify a service name, the server should respond with its overall
     * health status.
     * Clients should set a deadline when calling Check, and can declare the
     * server unhealthy if they do not receive a timely response.
     * </pre>
     */
    default void check(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckMethod(), responseObserver);
    }

    /**
     * <pre>
     * List provides a non-atomic snapshot of the health of all the available
     * services.
     * The server may respond with a RESOURCE_EXHAUSTED error if too many services
     * exist.
     * Clients should set a deadline when calling List, and can declare the server
     * unhealthy if they do not receive a timely response.
     * Clients should keep in mind that the list of health services exposed by an
     * application can change over the lifetime of the process.
     * </pre>
     */
    default void list(io.grpc.health.v1.HealthListRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    default void watch(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWatchMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Health.
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public static abstract class HealthImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HealthGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Health.
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public static final class HealthStub
      extends io.grpc.stub.AbstractAsyncStub<HealthStub> {
    private HealthStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthStub(channel, callOptions);
    }

    /**
     * <pre>
     * Check gets the health of the specified service. If the requested service
     * is unknown, the call will fail with status NOT_FOUND. If the caller does
     * not specify a service name, the server should respond with its overall
     * health status.
     * Clients should set a deadline when calling Check, and can declare the
     * server unhealthy if they do not receive a timely response.
     * </pre>
     */
    public void check(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List provides a non-atomic snapshot of the health of all the available
     * services.
     * The server may respond with a RESOURCE_EXHAUSTED error if too many services
     * exist.
     * Clients should set a deadline when calling List, and can declare the server
     * unhealthy if they do not receive a timely response.
     * Clients should keep in mind that the list of health services exposed by an
     * application can change over the lifetime of the process.
     * </pre>
     */
    public void list(io.grpc.health.v1.HealthListRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    public void watch(io.grpc.health.v1.HealthCheckRequest request,
        io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getWatchMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Health.
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public static final class HealthBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<HealthBlockingV2Stub> {
    private HealthBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Check gets the health of the specified service. If the requested service
     * is unknown, the call will fail with status NOT_FOUND. If the caller does
     * not specify a service name, the server should respond with its overall
     * health status.
     * Clients should set a deadline when calling Check, and can declare the
     * server unhealthy if they do not receive a timely response.
     * </pre>
     */
    public io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCheckMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List provides a non-atomic snapshot of the health of all the available
     * services.
     * The server may respond with a RESOURCE_EXHAUSTED error if too many services
     * exist.
     * Clients should set a deadline when calling List, and can declare the server
     * unhealthy if they do not receive a timely response.
     * Clients should keep in mind that the list of health services exposed by an
     * application can change over the lifetime of the process.
     * </pre>
     */
    public io.grpc.health.v1.HealthListResponse list(io.grpc.health.v1.HealthListRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, io.grpc.health.v1.HealthCheckResponse>
        watch(io.grpc.health.v1.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getWatchMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Health.
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public static final class HealthBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HealthBlockingStub> {
    private HealthBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Check gets the health of the specified service. If the requested service
     * is unknown, the call will fail with status NOT_FOUND. If the caller does
     * not specify a service name, the server should respond with its overall
     * health status.
     * Clients should set a deadline when calling Check, and can declare the
     * server unhealthy if they do not receive a timely response.
     * </pre>
     */
    public io.grpc.health.v1.HealthCheckResponse check(io.grpc.health.v1.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List provides a non-atomic snapshot of the health of all the available
     * services.
     * The server may respond with a RESOURCE_EXHAUSTED error if too many services
     * exist.
     * Clients should set a deadline when calling List, and can declare the server
     * unhealthy if they do not receive a timely response.
     * Clients should keep in mind that the list of health services exposed by an
     * application can change over the lifetime of the process.
     * </pre>
     */
    public io.grpc.health.v1.HealthListResponse list(io.grpc.health.v1.HealthListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Performs a watch for the serving status of the requested service.
     * The server will immediately send back a message indicating the current
     * serving status.  It will then subsequently send a new message whenever
     * the service's serving status changes.
     * If the requested service is unknown when the call is received, the
     * server will send a message setting the serving status to
     * SERVICE_UNKNOWN but will *not* terminate the call.  If at some
     * future point, the serving status of the service becomes known, the
     * server will send a new message with the service's serving status.
     * If the call terminates with status UNIMPLEMENTED, then clients
     * should assume this method is not supported and should not retry the
     * call.  If the call terminates with any other status (including OK),
     * clients should retry the call with appropriate exponential backoff.
     * </pre>
     */
    public java.util.Iterator<io.grpc.health.v1.HealthCheckResponse> watch(
        io.grpc.health.v1.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getWatchMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Health.
   * <pre>
   * Health is gRPC's mechanism for checking whether a server is able to handle
   * RPCs. Its semantics are documented in
   * https://github.com/grpc/grpc/blob/master/doc/health-checking.md.
   * </pre>
   */
  public static final class HealthFutureStub
      extends io.grpc.stub.AbstractFutureStub<HealthFutureStub> {
    private HealthFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Check gets the health of the specified service. If the requested service
     * is unknown, the call will fail with status NOT_FOUND. If the caller does
     * not specify a service name, the server should respond with its overall
     * health status.
     * Clients should set a deadline when calling Check, and can declare the
     * server unhealthy if they do not receive a timely response.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.health.v1.HealthCheckResponse> check(
        io.grpc.health.v1.HealthCheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List provides a non-atomic snapshot of the health of all the available
     * services.
     * The server may respond with a RESOURCE_EXHAUSTED error if too many services
     * exist.
     * Clients should set a deadline when calling List, and can declare the server
     * unhealthy if they do not receive a timely response.
     * Clients should keep in mind that the list of health services exposed by an
     * application can change over the lifetime of the process.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.health.v1.HealthListResponse> list(
        io.grpc.health.v1.HealthListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK = 0;
  private static final int METHODID_LIST = 1;
  private static final int METHODID_WATCH = 2;

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
        case METHODID_CHECK:
          serviceImpl.check((io.grpc.health.v1.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse>) responseObserver);
          break;
        case METHODID_LIST:
          serviceImpl.list((io.grpc.health.v1.HealthListRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthListResponse>) responseObserver);
          break;
        case METHODID_WATCH:
          serviceImpl.watch((io.grpc.health.v1.HealthCheckRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.health.v1.HealthCheckResponse>) responseObserver);
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
          getCheckMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.grpc.health.v1.HealthCheckRequest,
              io.grpc.health.v1.HealthCheckResponse>(
                service, METHODID_CHECK)))
        .addMethod(
          getListMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.grpc.health.v1.HealthListRequest,
              io.grpc.health.v1.HealthListResponse>(
                service, METHODID_LIST)))
        .addMethod(
          getWatchMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              io.grpc.health.v1.HealthCheckRequest,
              io.grpc.health.v1.HealthCheckResponse>(
                service, METHODID_WATCH)))
        .build();
  }

  private static abstract class HealthBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HealthBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.health.v1.HealthProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Health");
    }
  }

  private static final class HealthFileDescriptorSupplier
      extends HealthBaseDescriptorSupplier {
    HealthFileDescriptorSupplier() {}
  }

  private static final class HealthMethodDescriptorSupplier
      extends HealthBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HealthMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (HealthGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HealthFileDescriptorSupplier())
              .addMethod(getCheckMethod())
              .addMethod(getListMethod())
              .addMethod(getWatchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
