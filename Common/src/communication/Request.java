package communication;

import lombok.Getter;

import java.io.Serializable;

public class Request implements Serializable {
	@Getter
	private final String name;
	@Getter
	private final Argument[] arguments;
	
	private String login;
	private String password;
	
	public Request(String name, Argument... arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String getLogin() {
		return login;
	}
	
	public Request setLogin(String login) {
		this.login = login;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Request setPassword(String password) {
		this.password = password;
		return this;
	}
}
