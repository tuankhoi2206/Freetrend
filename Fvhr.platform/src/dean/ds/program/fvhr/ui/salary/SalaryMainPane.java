package ds.program.fvhr.ui.salary;

import java.util.ResourceBundle;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;
import dsc.echo2app.program.BrowserContent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Extent;
import ds.program.fvhr.ui.salary.AwesomeSalaryDataContent;

public class SalaryMainPane extends ContentPane {

	private ResourceBundle resourceBundle;
	private BrowserContent browserContent;
	private AwesomeSalaryDataContent dataContent;
	private SplitPane splitPane;
	/**
	 * Creates a new <code>SalaryMainPane</code>.
	 */
	public SalaryMainPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	public BrowserContent getBrowserContent() {
		return browserContent;
	}
	
	public AwesomeSalaryDataContent getDataContent() {
		return dataContent;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		splitPane = new SplitPane();
		splitPane.setSeparatorPosition(new Extent(300, Extent.PX));
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitHerzBar.png");
		splitPane.setSeparatorHorizontalImage(new FillImage(imageReference1));
		splitPane.setResizable(true);
		add(splitPane);
		browserContent = new BrowserContent();
		splitPane.add(browserContent);
		dataContent = new AwesomeSalaryDataContent();
		splitPane.add(dataContent);
	}
}
