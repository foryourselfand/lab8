package commands;

import application.ContextServer;
import communication.Argument;
import communication.Response;

public abstract class Command {
	protected ContextServer context;
	protected Argument[] arguments;
	protected String login;
	protected String password;
	
	public void setContext(ContextServer context) {
		this.context = context;
	}
	
	public Command setParameters(Argument[] arguments) {
		this.arguments = arguments;
		return this;
	}
	
	public Command setLogin(String login) {
		this.login = login;
		return this;
	}
	
	public Command setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public abstract String getName();
	
	public abstract String getManual();
	
	public abstract Response execute();
}
