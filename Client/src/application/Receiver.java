package application;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Receiver implements Runnable {
	private final ContextClient context;
	
	@Override
	public void run() {
		while (true) {
			try {
				context.getResponses().add(context.getHandlerServer().receiveResponse());
			} catch (Exception e) {
				System.out.println("Ошибка при чтении ответа.");
			}
		}
	}
}
