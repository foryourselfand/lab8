package gui.adapted_components.listeners;

import communication.Argument;
import gui.windows.ParameterWindow;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public class RemoveParamListener implements ActionListener {
	private final String name;
	private final ParameterWindow parameterWindow;
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			parameterWindow.getContext().sendRequest(name, new Argument(parameterWindow.getCheckedField().getValue()));
			parameterWindow.getContext().getWorkWindow().setEnabled(true);
			parameterWindow.setVisible(false);
			parameterWindow.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Некорректно введены данные!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
