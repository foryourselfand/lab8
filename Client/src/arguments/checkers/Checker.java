package arguments.checkers;

import arguments.exceptions.CheckerException;

public interface Checker {
	void check(String argument) throws CheckerException;
}
