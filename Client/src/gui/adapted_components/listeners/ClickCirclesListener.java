package gui.adapted_components.listeners;

import gui.Circle;
import gui.adapted_components.VisualPanel;
import gui.windows.ShowWindow;
import lombok.AllArgsConstructor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

@AllArgsConstructor
public class ClickCirclesListener implements MouseListener {
	private final VisualPanel visualPanel;
	
	public int isHit(double x, double y) {
		double radiusMax = Double.MAX_VALUE;
		int result = - 1;
		for (Map.Entry<Integer, Circle> entry : visualPanel.getCircles().entrySet()) {
			if ((getDistance(entry.getValue().getX(), entry.getValue().getY(), x, y) < entry.getValue().getRadius()) && (entry.getValue().getRadius() < radiusMax)) {
				radiusMax = entry.getValue().getRadius();
				result = entry.getKey();
			}
		}
		return result;
	}
	
	public double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();
		
		y = VisualPanel.HEIGHT - y;
		
		if (isHit(x, y) != - 1) {
			ShowWindow showWindow = new ShowWindow(visualPanel.getContext(), isHit(x, y));
			showWindow.setVisible(true);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
	}
	
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
	}
	
	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	}
	
	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	}
}
