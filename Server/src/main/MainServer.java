package main;

import application.ContextServer;

public class MainServer {
	public static void main(String[] args) {
		ContextServer context = new ContextServer();
		context.initialization(args);
		context.run();
	}
}
