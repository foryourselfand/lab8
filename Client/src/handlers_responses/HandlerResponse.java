package handlers_responses;

import application.ContextClient;
import communication.Response;

public interface HandlerResponse {
	void processing(ContextClient context, Response response);
}
