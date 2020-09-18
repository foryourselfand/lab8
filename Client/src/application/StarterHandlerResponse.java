package application;

import communication.Response;
import elements.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StarterHandlerResponse implements Runnable {
	private final ContextClient context;
	
	@Override
	public void run() {
		while (true) {
			if (! context.getResponses().isEmpty()) {
				Response response = context.getResponses().poll();
				if (response.getArgument() != null)
					context.updateArray((Product[]) response.getArgument().getValue());
				if (context.getMapHandlers().containsKey(response.getNameCommand()))
					context.getMapHandlers().get(response.getNameCommand()).processing(context, response);
				else if (response.getNameCommand() != null && response.getResultCommand() != null)
					context.getWorkWindow().updateConsole(response.getNameCommand() + ": " + response.getResultCommand());
			}
		}
	}
}
