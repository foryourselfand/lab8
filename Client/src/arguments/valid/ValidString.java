package arguments.valid;

import arguments.checkers.Checker;

public class ValidString extends ValidArgument<String> {
	public ValidString(Checker... checkers) {
		super(checkers);
	}
	
	@Override
	public String parse(String argument) {
		result = argument;
		return result;
	}
}
