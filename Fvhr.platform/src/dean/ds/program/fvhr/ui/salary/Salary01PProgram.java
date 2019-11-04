package ds.program.fvhr.ui.salary;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fv.components.SimpleReportProgram;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;

public class Salary01PProgram extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;

	/**
	 * Creates a new <code>Salary01PProgram</code>.
	 */
	public Salary01PProgram() {
		super();

		// Add design-time configured components.
		initComponents();
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
		Grid grid1 = new Grid();
		add(grid1);
		Label label1 = new Label();
		label1.setText("Tháng");
		grid1.add(label1);
		Row row1 = new Row();
		grid1.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label2 = new Label();
		label2.setText("Năm");
		row1.add(label2);
		sfYear = new SelectField();
		row1.add(sfYear);
	}
}
