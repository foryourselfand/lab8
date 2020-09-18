package network;

import communication.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.SocketAddress;

@AllArgsConstructor
@Getter
public class AddressedResponse {
	private final SocketAddress socketAddress;
	private final Response response;
}
