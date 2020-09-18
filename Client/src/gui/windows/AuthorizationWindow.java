package gui.windows;

import application.ContextClient;
import arguments.checkers.StringSize;
import arguments.valid.ValidString;
import communication.Argument;
import gui.adapted_components.CheckedField;
import gui.adapted_components.CheckedFieldPassword;
import gui.adapted_components.LanguageMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class AuthorizationWindow extends JFrame {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 160;
	
	private final ContextClient context;
	
	private final CheckedField loginField;
	private final CheckedFieldPassword passwordField;
	
	public AuthorizationWindow(ContextClient context) {
		super();
		
		this.context = context;
		
		JMenuBar menuBar = new JMenuBar();
		LanguageMenu languageMenu = new LanguageMenu(context);
		context.getComponents().put("auth.language.title", languageMenu);
		menuBar.add(languageMenu);
		
		JLabel loginText = new JLabel("Логин");
		context.getComponents().put("auth.loginText", loginText);
		
		JLabel passwordText = new JLabel("Пароль");
		context.getComponents().put("auth.passwordText", passwordText);
		
		loginField = new CheckedField(new ValidString(new StringSize(1)));
		passwordField = new CheckedFieldPassword(new ValidString(new StringSize(1)));
		passwordField.setEchoChar('*');
		
		JButton signInButton = new JButton("Вход");
		context.getComponents().put("auth.signInButton", signInButton);
		
		JButton signUpButton = new JButton("Регистрация");
		context.getComponents().put("auth.signUpButton", signUpButton);
		
		signInButton.addActionListener(actionEvent->{
			try {
				context.sendRequest("login", new Argument(loginField.getValue()), new Argument(passwordField.getValue()));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Некорректно введены данные!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		signUpButton.addActionListener(actionEvent->{
			try {
				context.sendRequest("registration", new Argument(loginField.getValue()), new Argument(passwordField.getValue()));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Некорректно введены данные!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		BorderLayout generalLayout = new BorderLayout();
		GridLayout elementsLayout = new GridLayout();
		JPanel elementsPanel = new JPanel();
		
		elementsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		elementsLayout.setColumns(2);
		elementsLayout.setRows(3);
		elementsPanel.setLayout(elementsLayout);
		elementsPanel.add(loginText);
		elementsPanel.add(loginField);
		elementsPanel.add(passwordText);
		elementsPanel.add(passwordField);
		elementsPanel.add(signInButton);
		elementsPanel.add(signUpButton);
		
		
		setLayout(generalLayout);
		setSize(WIDTH, HEIGHT);
		add(elementsPanel);
		add(menuBar, BorderLayout.NORTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Окно авторизации");
		context.getComponents().put("auth.title", this);
	}
	
	public void applyLocale(String baseName) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
		for (String str : context.getComponents().keySet()) {
			Component component = context.getComponents().get(str);
			if (component instanceof JButton)
				((JButton) component).setText(resourceBundle.getString(str));
			else if (component instanceof JLabel)
				((JLabel) component).setText(resourceBundle.getString(str));
			else if (component instanceof JFrame)
				((JFrame) component).setTitle(resourceBundle.getString(str));
			else if (component instanceof LanguageMenu) {
				((LanguageMenu) component).setText(resourceBundle.getString(str));
				((LanguageMenu) component).updateUI();
			}
		}
	}
}
