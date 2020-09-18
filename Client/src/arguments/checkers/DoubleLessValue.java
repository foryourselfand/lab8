package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DoubleLessValue implements Checker {
	private final Double value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (Double.parseDouble(argument) > value) {
			throw new CheckerException("Число больше максимального порога!");
		}
	}
}
