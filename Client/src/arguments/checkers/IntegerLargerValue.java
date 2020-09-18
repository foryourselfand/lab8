package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerLargerValue implements Checker {
	private final Integer value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (Integer.parseInt(argument) < value)
			throw new CheckerException("Число меньше минимального порога!");
	}
}
