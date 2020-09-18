package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DoubleLargerValue implements Checker {
	private final Double value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (Double.parseDouble(argument) < value)
			throw new CheckerException("Число меньше минимального порога!");
	}
}
