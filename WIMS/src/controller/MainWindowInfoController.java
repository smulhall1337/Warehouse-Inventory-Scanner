package controller;

import javax.swing.JTable;

import gui.ColumnHeaderControllerPanel;
import gui.EntityAndFieldPanel;
import gui.MainWindow;

public class MainWindowInfoController {

	public static final String DEFAULT_ENTITY_NAME = DBNamesManager.getItemEntityDisplayname();
	private JTable mainTable;
	private EntityAndFieldPanel entityAndFieldPanel;
	private ColumnHeaderControllerPanel columnCheckBoxesPanel;
	private MainWindow mainWindow;
	
	public MainWindowInfoController(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		this.mainTable = mainWindow.getDisplayTable();
		this.entityAndFieldPanel = mainWindow.getEntityAndFieldPanel();
		this.columnCheckBoxesPanel = mainWindow.getColumnCheckBoxesPanel();
	}

	public JTable getMainTable() {
		return mainWindow.getDisplayTable();
	}

	public EntityAndFieldPanel getEntityAndFieldPanel() {
		return mainWindow.getEntityAndFieldPanel();
	}

	public ColumnHeaderControllerPanel getColumnCheckBoxesPanel() {
		return mainWindow.getColumnCheckBoxesPanel();
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainTable(JTable mainTable) {
		this.mainTable = mainTable;
	}

	public void setEntityAndFieldPanel(EntityAndFieldPanel entityAndFieldPanel) {
		this.entityAndFieldPanel = entityAndFieldPanel;
	}

	public void setColumnCheckBoxesPanel(
			ColumnHeaderControllerPanel columnCheckBoxesPanel) {
		this.columnCheckBoxesPanel = columnCheckBoxesPanel;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
}
