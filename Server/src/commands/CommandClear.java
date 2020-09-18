package commands;

import communication.Argument;
import communication.Response;

public class CommandClear extends Command {
	@Override
	public String getName() {
		return "clear";
	}
	
	@Override
	public String getManual() {
		return "Очистить коллекцию.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().clear(context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
