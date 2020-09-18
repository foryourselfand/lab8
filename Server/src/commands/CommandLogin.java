package commands;

import communication.Argument;
import communication.Response;

public class CommandLogin extends Command {
	@Override
	public String getName() {
		return "login";
	}
	
	@Override
	public String getManual() {
		return "";
	}
	
	@Override
	public Response execute() {
		try {
			int id = context.getHandlerDatabase().isExistingUser((String) arguments[0].getValue(), (String) arguments[1].getValue());
			return new Response(getName(), (String) arguments[0].getValue() + " " + (String) arguments[1].getValue() + " " + id, new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
