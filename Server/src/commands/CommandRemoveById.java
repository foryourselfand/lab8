package commands;

import communication.Argument;
import communication.Response;

public class CommandRemoveById extends Command {
	@Override
	public String getName() {
		return "remove_by_id";
	}
	
	@Override
	public String getManual() {
		return "Удалить элемент из коллекции по его \"id\". Параметры: id.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().removeById((Integer) arguments[0].getValue(), context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
