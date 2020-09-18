package commands;

import communication.Argument;
import communication.Response;
import elements.Product;

public class CommandUpdateById extends Command {
	@Override
	public String getName() {
		return "update";
	}
	
	@Override
	public String getManual() {
		return "Обновить значение элемента коллекции по полю \"id\". Параметры: id {element}.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().updateById((Integer) arguments[0].getValue(), (Product) arguments[1].getValue(), context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
