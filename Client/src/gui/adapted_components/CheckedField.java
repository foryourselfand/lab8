package gui.adapted_components;

import arguments.exceptions.CheckerException;
import arguments.exceptions.TypeException;
import arguments.valid.ValidArgument;
import gui.adapted_components.listeners.CheckListener;
import lombok.Setter;

import javax.swing.*;

public class CheckedField<T> extends JTextField {
	private final ValidArgument<T> validArgument;
	@Setter
	private T value;
	
	public CheckedField(ValidArgument validArgument) {
		super();
		this.validArgument = validArgument;
		addFocusListener(new CheckListener(this));
	}
	
	public void setValue() throws CheckerException, TypeException {
		value = validArgument.get(getText());
	}
	
	public T getValue() {
		return value;
	}
}
