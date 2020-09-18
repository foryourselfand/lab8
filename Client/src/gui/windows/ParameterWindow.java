package gui.windows;

import application.ContextClient;
import arguments.valid.ValidArgument;
import gui.adapted_components.CheckedField;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


@Getter
public class ParameterWindow extends JFrame {
	public static final int WIDTH = 250;
	public static final int HEIGHT = 90;
	
	private final ContextClient context;
	private final CheckedField checkedField;
	private final JButton confirmButton;
	
	public ParameterWindow(ContextClient context, String label, ValidArgument validArgument) {
		super();
		
		this.context = context;
		JLabel jLabel = new JLabel(label);
		checkedField = new CheckedField(validArgument);
		confirmButton = new JButton("confirm");
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout());
		
		panel.add(jLabel, BorderLayout.WEST);
		panel.add(checkedField, BorderLayout.CENTER);
		panel.add(confirmButton, BorderLayout.SOUTH);
		
		add(panel);
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				context.getWorkWindow().setEnabled(true);
				e.getWindow().dispose();
			}
		});
		
	}
	
	public void setWidth(int width) {
		super.setSize(width, HEIGHT);
	}
}
