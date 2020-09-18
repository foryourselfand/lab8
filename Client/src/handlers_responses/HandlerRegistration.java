package handlers_responses;

import application.ContextClient;
import communication.Response;

import javax.swing.*;

public class HandlerRegistration implements HandlerResponse {
	@Override
	public void processing(ContextClient context, Response response) {
		JOptionPane.showMessageDialog(new JFrame(), response.getResultCommand(), "Информация", JOptionPane.INFORMATION_MESSAGE);
	}
}
