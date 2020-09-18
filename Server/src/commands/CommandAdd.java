package commands;

import communication.Argument;
import communication.Response;
import elements.Product;

public class CommandAdd extends Command {
	@Override
	public String getName() {
		return "add";
	}
	
	@Override
	public String getManual() {
		return "Добавить новый элемент в коллекцию. Параметры: {element}.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().add((Product) arguments[0].getValue(), context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
