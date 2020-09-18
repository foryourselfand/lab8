package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LongLargerValue implements Checker {
	private final Long value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (Long.parseLong(argument) <= value)
			throw new CheckerException("Число меньше либо равно минимального порога!");
	}
}
