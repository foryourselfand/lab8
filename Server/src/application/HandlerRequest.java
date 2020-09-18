package application;

import lombok.AllArgsConstructor;
import network.AddressedRequest;
import network.AddressedResponse;

@AllArgsConstructor
public class HandlerRequest implements Runnable {
	private final ContextServer context;
	
	@Override
	public void run() {
		try {
			if (! context.getQueueAddressedRequests().isEmpty()) {
				AddressedRequest addressedRequest = context.getQueueAddressedRequests().poll();
				context.getQueueAddressedResponse().add(new AddressedResponse(addressedRequest.getSocketAddress(), context.getHandlerCommands().executeCommand(addressedRequest.getRequest())));
			}
		} catch (Exception ignore) {
		}
	}
}
