package application;

import lombok.AllArgsConstructor;

import java.io.EOFException;
import java.io.IOException;

@AllArgsConstructor
public class Receiver implements Runnable {
	private final ContextServer context;
	
	@Override
	public void run() {
		while (true) {
			try {
				context.getQueueAddressedRequests().add(context.getHandlerClient().receiveAddressedRequest());
			} catch (EOFException e) {
				context.logger.warn("Не удалось принять запрос, т.к. он слишком объемный и не помещается в буффер.");
			} catch (ClassNotFoundException | ClassCastException e) {
				context.logger.warn("Пришел запрос, который не удалось дессериализовать.");
			} catch (IOException e) {
				context.logger.warn("Произошла ошибка при чтении запроса: " + e.getMessage());
			} catch (Exception e) {
				context.logger.warn(e.getMessage());
			}
		}
	}
}
