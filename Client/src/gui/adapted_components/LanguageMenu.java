package gui.adapted_components;

import application.ContextClient;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class LanguageMenu extends JMenu {
	public LanguageMenu(ContextClient context) {
		super();
		setText("Язык");
		
		JRadioButtonMenuItem language1 = new JRadioButtonMenuItem("Русский");
		language1.setActionCommand("resources/ru_RU");
		language1.setSelected(true);
		
		JRadioButtonMenuItem language2 = new JRadioButtonMenuItem("Magyar");
		language2.setActionCommand("resources/hu_HU");
		
		JRadioButtonMenuItem language3 = new JRadioButtonMenuItem("Română");
		language3.setActionCommand("resources/ro_RO");
		
		JRadioButtonMenuItem language4 = new JRadioButtonMenuItem("English");
		language4.setActionCommand("resources/en_EN");
		
		List<JRadioButtonMenuItem> languages = Arrays.asList(language1, language2, language3, language4);
		
		ButtonGroup group = new ButtonGroup();
		
		for (JRadioButtonMenuItem language : languages) {
			group.add(language);
			add(language);
			language.addActionListener(actionEvent->{
				context.getAuthorizationWindow().applyLocale(language.getActionCommand());
				context.getWorkWindow().applyLocale(language.getActionCommand());
			});
		}
	}
}
