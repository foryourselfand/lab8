package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerLessValue implements Checker {
	private final Integer value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (Integer.parseInt(argument) > value)
			throw new CheckerException("Число больше максимального порога!");
	}
}
