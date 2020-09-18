package commands;

import communication.Argument;
import communication.Response;
import elements.Product;

public class CommandAddIfMin extends Command {
	@Override
	public String getName() {
		return "add_if_min";
	}
	
	@Override
	public String getManual() {
		return "Добавить новый элемент в коллекцию если его значение станет наименьшим. Параметры: {element}.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().addIfMin((Product) arguments[0].getValue(), context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
