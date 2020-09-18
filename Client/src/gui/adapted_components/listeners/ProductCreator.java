package gui.adapted_components.listeners;

import elements.*;
import gui.windows.ProductWindow;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@AllArgsConstructor
public abstract class ProductCreator implements ActionListener {
	protected final String name;
	protected final ProductWindow productWindow;
	
	public Product createProduct() {
		return new Product(
				(String) productWindow.getNameOrganization().getValue(),
				new Coordinates(
						(Double) productWindow.getCoordinateX().getValue(),
						(Integer) productWindow.getCoordinateY().getValue()
				),
				(Double) productWindow.getPrice().getValue(),
				(String) productWindow.getPartNumber().getValue(),
				(Long) productWindow.getManufactureCost().getValue(),
				UnitOfMeasure.valueOf(productWindow.getUnitOfMeasureGroup().getSelection().getActionCommand()),
				new Organization(
						(String) productWindow.getOrganizationName().getValue(),
						(Long) productWindow.getAnnualTurnover().getValue(),
						OrganizationType.valueOf(productWindow.getOrganizationType().getSelection().getActionCommand())
				));
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			Product product = createProduct();
			sendSpecialRequest(product);
			productWindow.setVisible(false);
			productWindow.getContext().getWorkWindow().setEnabled(true);
			productWindow.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Некорректно введены данные!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public abstract void sendSpecialRequest(Product product);
}
