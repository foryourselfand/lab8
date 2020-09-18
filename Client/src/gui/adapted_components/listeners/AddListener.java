package gui.adapted_components.listeners;

import communication.Argument;
import elements.Product;
import gui.windows.ProductWindow;

public class AddListener extends ProductCreator {
	public AddListener(String name, ProductWindow productWindow) {
		super(name, productWindow);
	}
	
	@Override
	public void sendSpecialRequest(Product product) {
		productWindow.getContext().sendRequest(name, new Argument(product));
	}
}
