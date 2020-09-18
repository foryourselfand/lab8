package gui.windows;

import application.ContextClient;
import arguments.checkers.IntegerLargerValue;
import arguments.valid.ValidInteger;
import arguments.valid.ValidLong;
import elements.Product;
import gui.adapted_components.LanguageMenu;
import gui.adapted_components.VisualPanel;
import gui.adapted_components.listeners.AddListener;
import gui.adapted_components.listeners.FilterUnitListener;
import gui.adapted_components.listeners.RemoveParamListener;
import gui.adapted_components.listeners.UpdateListener;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

public class WorkWindow extends JFrame {
	public static final int WIDTH = 1310;
	public static final int HEIGHT = 600;
	
	private final JTextArea console;
	private final JButton addButton;
	private final JButton addIfMinButton;
	private final JButton filterLessUnitOfMeasureButton;
	private final JButton removeByIdButton;
	private final JButton updateByIdButton;
	private final JPanel generalEastPanel;
	private final ContextClient context;
	private final JLabel userName;
	private final DefaultTableModel modelTable;
	private final JTextField filterField;
	
	@Getter
	private final VisualPanel visualPanel;
	private int quantityEastButton;
	
	public WorkWindow(ContextClient context) {
		super();
		
		this.context = context;
		
		JMenuBar menuBar = new JMenuBar();
		LanguageMenu languageMenu = new LanguageMenu(context);
		context.getComponents().put("work.language.title", languageMenu);
		menuBar.add(languageMenu);
		
		JPanel generalCenterPanel = new JPanel();
		generalCenterPanel.setLayout(new BorderLayout());
		userName = new JLabel();
		userName.setFont(new Font(Font.DIALOG, Font.ITALIC, 18));
		userName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		generalCenterPanel.add(userName, BorderLayout.NORTH);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		String[] columnNames = {
				"user_id",
				"id",
				"name",
				"coordinate_x",
				"coordinate_y",
				"creation_date",
				"price",
				"part_number",
				"manufacture_cost",
				"unit_of_measure",
				"organization_id",
				"organization_name",
				"organization_annual_turnover",
				"organization_type"
		};
		
		modelTable = new DefaultTableModel(0, columnNames.length) {
			@Override
			public Class getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount()))
					returnValue = getValueAt(0, column).getClass();
				else
					returnValue = Object.class;
				return returnValue;
			}
		};
		modelTable.setColumnIdentifiers(columnNames);
		JTable table = new JTable(modelTable);
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		tablePanel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
		filterField = new JTextField();
		JButton filterButton = new JButton("filter");
		context.getComponents().put("work.filter", filterButton);
		
		filterButton.addActionListener(actionEvent->{
			String text = filterField.getText();
			if (text.length() == 0) {
				sorter.setRowFilter(null);
			} else {
				try {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				} catch (PatternSyntaxException pse) {
					JOptionPane.showMessageDialog(new JFrame(), "Неправильное регулярное выражение!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BorderLayout());
		filterPanel.add(filterField, BorderLayout.CENTER);
		filterPanel.add(filterButton, BorderLayout.EAST);
		tablePanel.add(filterPanel, BorderLayout.SOUTH);
		
		visualPanel = new VisualPanel(context);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Таблица", tablePanel);
		tabbedPane.addTab("Визуализация", visualPanel);
		generalCenterPanel.add(tabbedPane, BorderLayout.CENTER);
		
		console = new JTextArea();
		console.setRows(10);
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
		console.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		console.setEnabled(false);
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		JScrollPane scrollConsole = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		consolePanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
		consolePanel.add(scrollConsole);
		generalCenterPanel.add(consolePanel, BorderLayout.SOUTH);
		
		quantityEastButton = 0;
		generalEastPanel = new JPanel();
		GridLayout eastGrid = new GridLayout();
		generalEastPanel.setLayout(eastGrid);
		addButton = new JButton("add");
		addIfMinButton = new JButton("add if min");
		JButton clearButton = new JButton("clear");
		JButton helpButton = new JButton("help");
		JButton infoButton = new JButton("info");
		JButton printAscendingButton = new JButton("print ascending");
		filterLessUnitOfMeasureButton = new JButton("filter less unit of measure");
		removeByIdButton = new JButton("remove by id");
		JButton removeByManufactureCostButton = new JButton("remove by manufacture cost");
		JButton removeFirstButton = new JButton("remove first");
		updateByIdButton = new JButton("update by id");
		
		addButton.addActionListener(actionEvent->{
			ProductWindow productWindow = new ProductWindow(context, ProductWindowType.ADD);
			productWindow.setTitle(addButton.getText());
			productWindow.getConfirmButton().addActionListener(new AddListener("add", productWindow));
			productWindow.setTitle(addButton.getText());
			productWindow.setVisible(true);
			setEnabled(false);
		});
		addIfMinButton.addActionListener(actionEvent->{
			ProductWindow productWindow = new ProductWindow(context, ProductWindowType.ADD);
			productWindow.setTitle(addButton.getText());
			productWindow.getConfirmButton().addActionListener(new AddListener("add_if_min", productWindow));
			productWindow.setVisible(true);
			productWindow.setTitle(addIfMinButton.getText());
			setEnabled(false);
		});
		clearButton.addActionListener(actionEvent->context.sendRequest("clear"));
		helpButton.addActionListener(actionEvent->context.sendRequest("help"));
		infoButton.addActionListener(actionEvent->context.sendRequest("info"));
		printAscendingButton.addActionListener(actionEvent->context.sendRequest("print_ascending"));
		
		filterLessUnitOfMeasureButton.addActionListener(actionEvent->{
			UnitOfMeasureWindow unitOfMeasureWindow = new UnitOfMeasureWindow(context);
			unitOfMeasureWindow.getConfirmButton().addActionListener(new FilterUnitListener("filter_less_than_unit_of_measure", unitOfMeasureWindow));
			unitOfMeasureWindow.setTitle(filterLessUnitOfMeasureButton.getText());
			unitOfMeasureWindow.setVisible(true);
			setEnabled(false);
		});
		removeByIdButton.addActionListener(actionEvent->{
			ParameterWindow parameterWindow = new ParameterWindow(context, "id:", new ValidInteger(new IntegerLargerValue(1)));
			parameterWindow.setWidth(250);
			parameterWindow.getConfirmButton().addActionListener(new RemoveParamListener("remove_by_id", parameterWindow));
			parameterWindow.setTitle(removeByIdButton.getText());
			parameterWindow.setVisible(true);
			setEnabled(false);
		});
		removeByManufactureCostButton.addActionListener(actionEvent->{
			ParameterWindow parameterWindow = new ParameterWindow(context, "manufacture cost:", new ValidLong());
			parameterWindow.setWidth(250);
			parameterWindow.getConfirmButton().addActionListener(new RemoveParamListener("remove_all_by_manufacture_cost", parameterWindow));
			parameterWindow.setTitle(removeByIdButton.getText());
			parameterWindow.setVisible(true);
			setEnabled(false);
		});
		removeFirstButton.addActionListener(actionEvent->context.sendRequest("remove_first"));
		updateByIdButton.addActionListener(actionEvent->{
			ProductWindow productWindow = new ProductWindow(context, ProductWindowType.UPDATE);
			productWindow.setTitle(addButton.getText());
			productWindow.getConfirmButton().addActionListener(new UpdateListener("update", productWindow));
			productWindow.setTitle(updateByIdButton.getText());
			productWindow.setVisible(true);
			setEnabled(false);
		});
		
		addEastButton(addButton);
		addEastButton(addIfMinButton);
		addEastButton(clearButton);
		addEastButton(helpButton);
		addEastButton(infoButton);
		addEastButton(printAscendingButton);
		addEastButton(filterLessUnitOfMeasureButton);
		addEastButton(removeByIdButton);
		addEastButton(removeByManufactureCostButton);
		addEastButton(removeFirstButton);
		addEastButton(updateByIdButton);
		eastGrid.setColumns(1);
		eastGrid.setRows(quantityEastButton);
		generalEastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		
		setLayout(new BorderLayout());
		add(menuBar, BorderLayout.NORTH);
		add(generalCenterPanel, BorderLayout.CENTER);
		add(generalEastPanel, BorderLayout.EAST);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Основное окно");
		context.getComponents().put("work.title", this);
	}
	
	private void addEastButton(JButton button) {
		quantityEastButton++;
		generalEastPanel.add(button);
	}
	
	public void setUserSettings(String userName, Color color) {
		this.userName.setText(userName);
		this.userName.setForeground(color);
	}
	
	public void updateTable(Product[] arrayProduct) {
		modelTable.setRowCount(0);
		for (Product product : arrayProduct) {
			Vector row = new Vector();
			row.add(product.getIdUser());
			row.add(product.getId());
			row.add(product.getName());
			row.add(product.getCoordinates().getX());
			row.add(product.getCoordinates().getY());
			row.add(product.getCreationDate());
			row.add(product.getPrice());
			row.add(product.getPartNumber());
			row.add(product.getManufactureCost());
			row.add(product.getUnitOfMeasure());
			row.add(product.getOrganization().getId());
			row.add(product.getOrganization().getName());
			row.add(product.getOrganization().getAnnualTurnover());
			row.add(product.getOrganization().getOrganizationType());
			modelTable.addRow(row);
		}
	}
	
	public void updateConsole(String line) {
		console.append(line + "\n");
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

//			if (component instanceof JTabbedPane) {
//				for (int i = 0; i < ((JTabbedPane) component).getTabCount(); i++) {
//					((JTabbedPane) component).setTitleAt(i, String.valueOf(context.getComponents().get(str)));
//				}
//			}
		}
	}
}
