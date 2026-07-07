package software.xdev.testcontainers.imagebuilder;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import moby.buildkit.v1.ControlGrpc;
import moby.buildkit.v1.ControlOuterClass;
import pb.Ops;


public class TEST
{
	public static void main(final String[] args)
	{
		final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
			.usePlaintext()
			.build();
		
		final ControlGrpc.ControlBlockingV2Stub stub = ControlGrpc.newBlockingV2Stub(channel);
		
		stub.solve(ControlOuterClass.SolveRequest.newBuilder()
				.putFrontendInputs("", Ops.Definition.newBuilder()..build())
			.build())
	}
}
