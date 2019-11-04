package ds.program.fvhr.ui.salary;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ds.program.fvhr.dao.hrreport.ReportDao;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import fv.components.SimpleReportProgram;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import echopointng.ComboBox;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import echopointng.GroupBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.button.ButtonGroup;
/**
 * Giao dien thiet ke giong delphi----khu`ng.....
 * @author Hieu
 *
 */
public class SalaryExcelProgram extends SimpleReportProgram {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private SelectField sfFact;
	private ComboBox cboDept;
	private ComboBox cboATM;
	private SplitPane splitPane1;
	private SalaryDataTable dataTableControl;
	private ReportDao dao;
	private RadioButton rad1;
	private RadioButton rad2;
	/**
	 * Creates a new <code>SalaryExcelProgram</code>.
	 */
	public SalaryExcelProgram() {
		super();

		// Add design-time configured components.
		initComponents();
		dao = new ReportDao();
		dataTableControl = new SalaryDataTable(dao);
		splitPane1.add(dataTableControl.getTable());
		moreInit();
	}
	
	
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);
		return ret;
	}
	
	private void moreInit(){
		setReportFileName("DuLieuLuong");
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), false);
		sfMonth.setWidth(new Extent(43));
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getStringMonthEditor(), true);
		sfYear.setWidth(new Extent(60));
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(5, false), true);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		sfMonth.setSelectedIndex(cal.get(Calendar.MONTH));
		ListBinder.refreshIndex(sfYear, Integer.valueOf(cal.get(Calendar.YEAR)));
		//ListBinder.bindComboBox(cboATM, FVGenericInfo.getATMGroupEditor(), false);
		ListBinder.bindComboBox(cboATM, FVGenericInfo.getATMGroupEditor(), false);
		cboATM.getTextField().setBackground(Color.WHITE);
		cboDept.getTextField().setBackground(Color.WHITE);
		cboDept.setPopUpAlwaysOnTop(true);
		ListBinder.bindComboBox(cboDept, FVGenericInfo.getAllDept());
		cboDept.getTextField().setMaximumLength(5);
		cboATM.getTextField().setMaximumLength(4);
		cboATM.setPopUpAlwaysOnTop(true);
	}

	
	protected void doRefresh() {
		if (!validateUI()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, getErrorMessage());
			return;
		}
		dataTableControl.refresh(buildParams());
	}

	
	protected void doSearch() {
	}
	
	
	protected void doReset() {
		super.doReset();
		sfFact.setSelectedIndex(-1);
		cboATM.setText("");
		cboDept.setText("");
		rad1.setSelected(false);
		rad2.setSelected(false);
	}

	
	protected HSSFWorkbook generateWorkbook() throws IOException {
		return dataTableControl.export();
	}

	private void deptChanged(ActionEvent e) {
		sfFact.setSelectedIndex(-1);
		rad1.setSelected(false);
		rad2.setSelected(false);
	}

	private void factChanged(ActionEvent e) {
		cboDept.setText("");
		rad1.setSelected(false);
		rad2.setSelected(false);
	}

	private void atmChanged(ActionEvent e) {
		//TODO Implement.
	}
	
	
	public boolean validateUI() {
		if (sfFact.getSelectedIndex()<0&&cboDept.getText().equals("")&&cboATM.getText().equals("")
				&&!rad1.isSelected()&&!rad2.isSelected()){
			setErrorMessage("Chọn thông số chạy báo cáo");
			return false;
		}
		return true;
	}
	
	public Map<String, Object> buildParams(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", sfMonth.getSelectedItem().toString());
		map.put("year", sfYear.getSelectedItem().toString());
		if (sfFact.getSelectedIndex()>0){
			map.put("fact", sfFact.getSelectedItem().toString());
		}
		if (!cboDept.getText().equals("")){
			map.put("dept", cboDept.getText());
		}
		if (!cboATM.getText().equals("")){
			map.put("atm", cboATM.getText());
		}
		if (rad1.isSelected()||rad2.isSelected()){
			if (rad1.isSelected()) map.put("opt1", "true");
			else map.put("opt2", "true");
		}
		return map;
	}

	private void optSelected(ActionEvent e) {
		sfFact.setSelectedIndex(-1);
		cboDept.setText("");
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(140, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setResizable(true);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		SplitPaneLayoutData grid1LayoutData = new SplitPaneLayoutData();
		grid1LayoutData.setBackground(new Color(0xd9d9ff));
		grid1LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.setLayoutData(grid1LayoutData);
		grid1.setSize(1);
		splitPane1.add(grid1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(3, Extent.PX));
		grid1.add(row1);
		Label label1 = new Label();
		label1.setText("Tháng");
		row1.add(label1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label2 = new Label();
		label2.setText("Năm");
		row1.add(label2);
		sfYear = new SelectField();
		row1.add(sfYear);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(3, Extent.PX));
		grid1.add(row2);
		Label label3 = new Label();
		label3.setText("Xưởng");
		row2.add(label3);
		sfFact = new SelectField();
		sfFact.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				factChanged(e);
			}
		});
		row2.add(sfFact);
		Label label4 = new Label();
		label4.setText("Mã đơn vị");
		row2.add(label4);
		cboDept = new ComboBox();
		cboDept.setActionOnSelection(true);
		cboDept.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				deptChanged(e);
			}
		});
		row2.add(cboDept);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(3, Extent.PX));
		grid1.add(row3);
		Label label5 = new Label();
		label5.setText("Mã ATM");
		row3.add(label5);
		cboATM = new ComboBox();
		cboATM.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				atmChanged(e);
			}
		});
		row3.add(cboATM);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setInsets(new Insets(new Extent(3, Extent.PX)));
		grid1.add(groupBox1);
		Row row4 = new Row();
		row4.setCellSpacing(new Extent(3, Extent.PX));
		groupBox1.add(row4);
		rad1 = new RadioButton();
		rad1.setText("FVLS_TB020");
		ButtonGroup gg = new ButtonGroup();
		rad1.setGroup(gg);
		rad1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		row4.add(rad1);
		rad2 = new RadioButton();
		rad2.setText("FVL_KDAO_TB019_BEP");
		rad2.setGroup(gg);
		rad2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				optSelected(e);
			}
		});
		row4.add(rad2);
	}
}
