package application;

import lombok.AllArgsConstructor;
import network.AddressedResponse;

import java.io.IOException;

@AllArgsConstructor
public class Transmitter implements Runnable {
	private final ContextServer context;
	
	@Override
	public void run() {
		try {
			if (! context.getQueueAddressedResponse().isEmpty()) {
				AddressedResponse response = context.getQueueAddressedResponse().poll();
				try {
					context.getHandlerClient().sendAddressedResponse(response);
				} catch (IOException e) {
					context.logger.warn("Произошла ошибка при отправке ответа: " + e.getMessage());
				}
			}
		} catch (Exception ignore) {
		}
	}
}
