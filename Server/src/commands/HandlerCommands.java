package commands;

import application.ContextServer;
import communication.Request;
import communication.Response;

import java.util.HashMap;
import java.util.Map;

public class HandlerCommands {
	private final ContextServer context;
	private final HashMap<String, Command> commands;
	
	public HandlerCommands(ContextServer context) {
		this.context = context;
		commands = new HashMap<>();
	}
	
	public HandlerCommands setCommand(Command command) {
		command.setContext(context);
		commands.put(command.getName(), command);
		return this;
	}
	
	public Response executeCommand(Request request) {
		if ((request.getLogin() == null || request.getPassword() == null) && ! request.getName().equals("login") && ! request.getName().equals("registration"))
			return new Response("", "Выполните вход при помощи команды login");
		if (request.getLogin() == null || request.getPassword() == null)
			commands.get(request.getName()).setParameters(request.getArguments());
		else
			commands.get(request.getName()).setParameters(request.getArguments()).setLogin(request.getLogin()).setPassword(request.getPassword());
		return commands.get(request.getName()).execute();
	}
	
	public Map<String, Command> getCommands() {
		return commands;
	}
}
