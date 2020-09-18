package gui.adapted_components;

import arguments.exceptions.CheckerException;
import arguments.exceptions.TypeException;
import arguments.valid.ValidArgument;
import gui.adapted_components.listeners.CheckListenerPassword;
import lombok.Setter;

import javax.swing.*;

public class CheckedFieldPassword<T> extends JPasswordField {
	private final ValidArgument<T> validArgument;
	@Setter
	private T value;
	
	public CheckedFieldPassword(ValidArgument validArgument) {
		super();
		this.validArgument = validArgument;
		addFocusListener(new CheckListenerPassword(this));
	}
	
	public void setValue() throws CheckerException, TypeException {
		value = validArgument.get(getText());
	}
	
	public T getValue() {
		return value;
	}
}
