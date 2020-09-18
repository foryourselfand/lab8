package arguments.checkers;

import arguments.exceptions.CheckerException;
import lombok.AllArgsConstructor;

import java.util.StringTokenizer;

@AllArgsConstructor
public class StringSize implements Checker {
	private final Integer value;
	
	@Override
	public void check(String argument) throws CheckerException {
		if (new StringTokenizer(argument).countTokens() != value)
			throw new CheckerException("Количество слов в строке не равно заданному!");
	}
}
