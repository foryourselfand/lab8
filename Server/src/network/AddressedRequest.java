package network;

import communication.Request;

import java.net.SocketAddress;

public class AddressedRequest {
	private final Request request;
	private SocketAddress socketAddress;
	
	public AddressedRequest(Request request) {
		this.request = request;
	}
	
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}
	
	public Request getRequest() {
		return request;
	}
}
