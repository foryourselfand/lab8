package gui.adapted_components.listeners;

import arguments.exceptions.CheckerException;
import arguments.exceptions.TypeException;
import gui.adapted_components.CheckedField;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@AllArgsConstructor
public class CheckListener implements FocusListener {
	private final CheckedField checkedField;
	
	@Override
	public void focusGained(FocusEvent focusEvent) {
		checkedField.setValue(null);
		checkedField.setBackground(Color.WHITE);
	}
	
	@Override
	public void focusLost(FocusEvent focusEvent) {
		try {
			checkedField.setValue();
		} catch (CheckerException | TypeException e) {
			checkedField.setBackground(Color.RED);
		}
	}
}
