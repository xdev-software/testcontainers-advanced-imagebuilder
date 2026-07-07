package moby.sshforward.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SSHGrpc {

  private SSHGrpc() {}

  public static final java.lang.String SERVICE_NAME = "moby.sshforward.v1.SSH";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.CheckAgentRequest,
      moby.sshforward.v1.Ssh.CheckAgentResponse> getCheckAgentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckAgent",
      requestType = moby.sshforward.v1.Ssh.CheckAgentRequest.class,
      responseType = moby.sshforward.v1.Ssh.CheckAgentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.CheckAgentRequest,
      moby.sshforward.v1.Ssh.CheckAgentResponse> getCheckAgentMethod() {
    io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.CheckAgentRequest, moby.sshforward.v1.Ssh.CheckAgentResponse> getCheckAgentMethod;
    if ((getCheckAgentMethod = SSHGrpc.getCheckAgentMethod) == null) {
      synchronized (SSHGrpc.class) {
        if ((getCheckAgentMethod = SSHGrpc.getCheckAgentMethod) == null) {
          SSHGrpc.getCheckAgentMethod = getCheckAgentMethod =
              io.grpc.MethodDescriptor.<moby.sshforward.v1.Ssh.CheckAgentRequest, moby.sshforward.v1.Ssh.CheckAgentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckAgent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.sshforward.v1.Ssh.CheckAgentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.sshforward.v1.Ssh.CheckAgentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SSHMethodDescriptorSupplier("CheckAgent"))
              .build();
        }
      }
    }
    return getCheckAgentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.BytesMessage,
      moby.sshforward.v1.Ssh.BytesMessage> getForwardAgentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ForwardAgent",
      requestType = moby.sshforward.v1.Ssh.BytesMessage.class,
      responseType = moby.sshforward.v1.Ssh.BytesMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.BytesMessage,
      moby.sshforward.v1.Ssh.BytesMessage> getForwardAgentMethod() {
    io.grpc.MethodDescriptor<moby.sshforward.v1.Ssh.BytesMessage, moby.sshforward.v1.Ssh.BytesMessage> getForwardAgentMethod;
    if ((getForwardAgentMethod = SSHGrpc.getForwardAgentMethod) == null) {
      synchronized (SSHGrpc.class) {
        if ((getForwardAgentMethod = SSHGrpc.getForwardAgentMethod) == null) {
          SSHGrpc.getForwardAgentMethod = getForwardAgentMethod =
              io.grpc.MethodDescriptor.<moby.sshforward.v1.Ssh.BytesMessage, moby.sshforward.v1.Ssh.BytesMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ForwardAgent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.sshforward.v1.Ssh.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  moby.sshforward.v1.Ssh.BytesMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SSHMethodDescriptorSupplier("ForwardAgent"))
              .build();
        }
      }
    }
    return getForwardAgentMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SSHStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SSHStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SSHStub>() {
        @java.lang.Override
        public SSHStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SSHStub(channel, callOptions);
        }
      };
    return SSHStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SSHBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SSHBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SSHBlockingV2Stub>() {
        @java.lang.Override
        public SSHBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SSHBlockingV2Stub(channel, callOptions);
        }
      };
    return SSHBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SSHBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SSHBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SSHBlockingStub>() {
        @java.lang.Override
        public SSHBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SSHBlockingStub(channel, callOptions);
        }
      };
    return SSHBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SSHFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SSHFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SSHFutureStub>() {
        @java.lang.Override
        public SSHFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SSHFutureStub(channel, callOptions);
        }
      };
    return SSHFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void checkAgent(moby.sshforward.v1.Ssh.CheckAgentRequest request,
        io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.CheckAgentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckAgentMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.BytesMessage> forwardAgent(
        io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.BytesMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getForwardAgentMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SSH.
   */
  public static abstract class SSHImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SSHGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SSH.
   */
  public static final class SSHStub
      extends io.grpc.stub.AbstractAsyncStub<SSHStub> {
    private SSHStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SSHStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SSHStub(channel, callOptions);
    }

    /**
     */
    public void checkAgent(moby.sshforward.v1.Ssh.CheckAgentRequest request,
        io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.CheckAgentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckAgentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.BytesMessage> forwardAgent(
        io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.BytesMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getForwardAgentMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SSH.
   */
  public static final class SSHBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SSHBlockingV2Stub> {
    private SSHBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SSHBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SSHBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public moby.sshforward.v1.Ssh.CheckAgentResponse checkAgent(moby.sshforward.v1.Ssh.CheckAgentRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCheckAgentMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<moby.sshforward.v1.Ssh.BytesMessage, moby.sshforward.v1.Ssh.BytesMessage>
        forwardAgent() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getForwardAgentMethod(), getCallOptions());
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service SSH.
   */
  public static final class SSHBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SSHBlockingStub> {
    private SSHBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SSHBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SSHBlockingStub(channel, callOptions);
    }

    /**
     */
    public moby.sshforward.v1.Ssh.CheckAgentResponse checkAgent(moby.sshforward.v1.Ssh.CheckAgentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckAgentMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SSH.
   */
  public static final class SSHFutureStub
      extends io.grpc.stub.AbstractFutureStub<SSHFutureStub> {
    private SSHFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SSHFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SSHFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<moby.sshforward.v1.Ssh.CheckAgentResponse> checkAgent(
        moby.sshforward.v1.Ssh.CheckAgentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckAgentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_AGENT = 0;
  private static final int METHODID_FORWARD_AGENT = 1;

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
        case METHODID_CHECK_AGENT:
          serviceImpl.checkAgent((moby.sshforward.v1.Ssh.CheckAgentRequest) request,
              (io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.CheckAgentResponse>) responseObserver);
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
        case METHODID_FORWARD_AGENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.forwardAgent(
              (io.grpc.stub.StreamObserver<moby.sshforward.v1.Ssh.BytesMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCheckAgentMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              moby.sshforward.v1.Ssh.CheckAgentRequest,
              moby.sshforward.v1.Ssh.CheckAgentResponse>(
                service, METHODID_CHECK_AGENT)))
        .addMethod(
          getForwardAgentMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              moby.sshforward.v1.Ssh.BytesMessage,
              moby.sshforward.v1.Ssh.BytesMessage>(
                service, METHODID_FORWARD_AGENT)))
        .build();
  }

  private static abstract class SSHBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SSHBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return moby.sshforward.v1.Ssh.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SSH");
    }
  }

  private static final class SSHFileDescriptorSupplier
      extends SSHBaseDescriptorSupplier {
    SSHFileDescriptorSupplier() {}
  }

  private static final class SSHMethodDescriptorSupplier
      extends SSHBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SSHMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SSHGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SSHFileDescriptorSupplier())
              .addMethod(getCheckAgentMethod())
              .addMethod(getForwardAgentMethod())
              .build();
        }
      }
    }
    return result;
  }
}
