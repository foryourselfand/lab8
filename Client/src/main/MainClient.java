package main;

import application.ContextClient;

public class MainClient {
	public static void main(String[] args) {
		ContextClient context = new ContextClient();
		context.initialization(args);
		context.run();
	}
}
