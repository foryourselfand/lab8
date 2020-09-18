package commands;

import communication.Argument;
import communication.Response;

public class CommandRemoveByManufactureCost extends Command {
	@Override
	public String getName() {
		return "remove_all_by_manufacture_cost";
	}
	
	@Override
	public String getManual() {
		return "Удалить элементы из коллекции, значение поля \"manufactureCost\" которого эквивалентно заданному. Параметры: manufactureCost.";
	}
	
	@Override
	public Response execute() {
		try {
			return new Response(getName(), context.getProductList().removeAllByManufactureCost((Long) arguments[0].getValue(), context.getHandlerDatabase().isExistingUser(login, password)), new Argument(context.getProductList().getArray()));
		} catch (Exception e) {
			return new Response(getName(), "Вы не прошли авторизацию.");
		}
	}
}
