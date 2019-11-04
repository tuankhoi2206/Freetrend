package ds.program.fvhr.ui.salary;

import java.util.ResourceBundle;
import nextapp.echo2.app.ContentPane;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.DscDataObjectSetTable;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Alignment;

public class SalaryBrowserPane extends ContentPane {

	private ResourceBundle resourceBundle;
	private DscPageNavigation pageNavigation;
	private DscDataObjectSetTable dataTable;

	/**
	 * Creates a new <code>SalaryBrowserPane</code>.
	 */
	public SalaryBrowserPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	private void doDataSelectedChange(ActionEvent e) {
		//TODO Implement.
	}
	
	public DscPageNavigation getPageNavigation() {
		return pageNavigation;
	}
	
	public DscDataObjectSetTable getDataTable() {
		return dataTable;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(25, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		pageNavigation = new DscPageNavigation();
		pageNavigation.setStyleName("Default.Toolbar");
		SplitPaneLayoutData pageNavigationLayoutData = new SplitPaneLayoutData();
		pageNavigationLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		pageNavigation.setLayoutData(pageNavigationLayoutData);
		splitPane1.add(pageNavigation);
		dataTable = new DscDataObjectSetTable();
		dataTable.setStyleName("Table.DscPageableSortableTable");
		dataTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDataSelectedChange(e);
			}
		});
		splitPane1.add(dataTable);
	}
}
