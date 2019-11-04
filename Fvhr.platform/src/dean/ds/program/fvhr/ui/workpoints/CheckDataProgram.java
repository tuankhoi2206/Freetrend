package ds.program.fvhr.ui.workpoints;

import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import fv.components.SimpleReportProgram;

public class CheckDataProgram extends SimpleReportProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private CheckDataBrowserContent browserContent;

	/**
	 * Creates a new <code>CheckDataProgram</code>.
	 */
	public CheckDataProgram() {
		super();

		// Add design-time configured components.
		initComponents();
		browserContent = new CheckDataBrowserContent();
		add(browserContent);
	}

	@Override
	protected void doRefresh() {
	}
	

	@Override
	protected void doSearch() {
	}
	

	@Override
	protected HSSFWorkbook generateWorkbook() throws IOException {
		return null;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}
}
