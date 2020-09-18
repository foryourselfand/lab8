package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.CheckerException;
import arguments.exceptions.TypeException;

public abstract class ValidArgument<T> {
	private final Checker[] checkers;
	T result;
	
	public ValidArgument(Checker... checkers) {
		this.checkers = checkers;
	}
	
	private void validation(String argument) throws CheckerException {
		for (Checker checker : checkers) {
			try {
				checker.check(argument);
			} catch (CheckerException e) {
				result = null;
				throw e;
			}
		}
	}
	
	public T get(String argument) throws CheckerException, TypeException {
		T value = parse(argument);
		validation(argument);
		return value;
	}
	
	protected abstract T parse(String argument) throws TypeException;
}
