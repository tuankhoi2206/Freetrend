package ds.program.fvhr.ui.salary;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fv.components.SimpleReportProgram;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import echopointng.GroupBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.button.ButtonGroup;

public class SalaryBH00RProgram extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private SelectField sfFact;
	private RadioButton radCN;
	private RadioButton radCB;
	private RadioButton radATM;
	private RadioButton rad0ATM;
	private Grid rootLayout;

	/**
	 * Creates a new <code>SalaryBH00RProgram</code>.
	 */
	public SalaryBH00RProgram() {
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
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(rootLayout);
		Label label1 = new Label();
		label1.setText("Tháng");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label1.setLayoutData(label1LayoutData);
		rootLayout.add(label1);
		Row row1 = new Row();
		rootLayout.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label2 = new Label();
		label2.setText("Năm");
		row1.add(label2);
		sfYear = new SelectField();
		row1.add(sfYear);
		Label label3 = new Label();
		label3.setText("Xưởng");
		rootLayout.add(label3);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(80, Extent.PX));
		sfFact.setDisabledBackground(new Color(0x808080));
		sfFact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		rootLayout.add(sfFact);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Bảng tổng BHXH-BHTN-BHYT");
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setColumnSpan(2);
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setSize(1);
		groupBox1.add(grid2);
		radCN = new RadioButton();
		radCN.setText("Công nhân không sử dụng ATM");
		ButtonGroup opt = new ButtonGroup();
		radCN.setGroup(opt);
		grid2.add(radCN);
		radCB = new RadioButton();
		radCB.setText("Cán bộ không sử dụng ATM");
		radCB.setGroup(opt);
		grid2.add(radCB);
		radATM = new RadioButton();
		radATM.setText("CB-CN sử dụng ATM");
		radATM.setGroup(opt);
		grid2.add(radATM);
		rad0ATM = new RadioButton();
		rad0ATM.setText("CB-CN không sử dụng ATM");
		rad0ATM.setGroup(opt);
		grid2.add(rad0ATM);
	}

	protected void sfFactChanged(ActionEvent e) {
	}
}
