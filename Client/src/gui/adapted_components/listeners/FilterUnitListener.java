package gui.adapted_components.listeners;

import communication.Argument;
import elements.UnitOfMeasure;
import gui.windows.UnitOfMeasureWindow;
import lombok.AllArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public class FilterUnitListener implements ActionListener {
	private final String name;
	private final UnitOfMeasureWindow unitOfMeasureWindow;
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		unitOfMeasureWindow.getContext().sendRequest(name, new Argument(UnitOfMeasure.valueOf(unitOfMeasureWindow.getUnitOfMeasureGroup().getSelection().getActionCommand())));
		unitOfMeasureWindow.getContext().getWorkWindow().setEnabled(true);
		unitOfMeasureWindow.setVisible(false);
		unitOfMeasureWindow.dispose();
	}
}
