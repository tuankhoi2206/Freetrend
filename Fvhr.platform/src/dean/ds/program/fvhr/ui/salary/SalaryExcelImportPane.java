package ds.program.fvhr.ui.salary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.dao.salary.SalaryDAO2;
import ds.program.fvhr.domain.ATTENDANTDB;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import fv.components.FileUploadSelect;
import fv.util.DbUtils;
import fv.util.ExcelBinder;
import fv.util.HSSFUtils;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.button.ButtonGroup;

public class SalaryExcelImportPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private FileUploadSelect fs;
	private SelectField sfMonth;
	private SelectField sfYear;
	private Button btnExecute;
	private Grid rootLayout;
	private RadioButton rad2;
	private RadioButton rad1;
	private String errorMessage;

	/**
	 * Creates a new <code>SalaryExcelImportPane</code>.
	 */
	public SalaryExcelImportPane() {
		super();

		// Add design-time configured components.
		initComponents();
		initUI();
	}
	
	private void initUI(){
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getMonthEditor(), true);
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		
		if (month==0){//thang hien tai la thang 1
			ListBinder.refreshIndex(sfMonth, 12);
			ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1, false), true);
			sfYear.setSelectedIndex(1);
		}else{
			ListBinder.refreshIndex(sfMonth, month);
			ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(0, false), true);
			sfYear.setSelectedIndex(0);
		}
	}
	
	private boolean validateForm(){
		if (fs.getFileWrapper()==null||fs.getFileWrapper().getFile()==null){
			errorMessage="Chọn file excel";
			return false;
		}
		return true;
	}

	private void execute(ActionEvent e) {
		if (!validateForm()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, errorMessage);
			return;
		}
		StopWatch sw = new StopWatch();
		sw.start();
		String month = sfMonth.getSelectedItem().toString();
		String year = sfYear.getSelectedItem().toString();
		File f = fs.getFileWrapper().getFile();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(f));
			ExcelBinder binder = new ExcelBinder();
			binder.setWorkBook(wb);
			binder.setSheetIndex(0);
			Map<String, Object> replaceNullProperties = new HashMap<String, Object>();
			replaceNullProperties.put("POSSN", "CN");
			binder.setReplaceNullProperties(replaceNullProperties);
			List<Object[]> list = new ArrayList<Object[]>();
			list.add(new Object[]{"EMPSN",0,String.class});
			list.add(new Object[]{"BSALY",1,Number.class});
			list.add(new Object[]{"COMBSALY",2,Number.class});
			list.add(new Object[]{"BONUS1",3,Number.class});
			list.add(new Object[]{"BONUS2",4,Number.class});
			list.add(new Object[]{"BONUS3",5,Number.class});
			list.add(new Object[]{"BONUS4",6,Number.class});
			list.add(new Object[]{"BONUS8",7,Number.class});
			list.add(new Object[]{"BONUS5",8,Number.class});
			list.add(new Object[]{"BORM",9,Number.class});
			list.add(new Object[]{"KQT",10,Number.class});
			list.add(new Object[]{"JOINLUM",11,Number.class});
			list.add(new Object[]{"YLBX",12,Number.class});
			list.add(new Object[]{"JOININSU",13,Number.class});
			list.add(new Object[]{"BH_TNGHIEP",14,Number.class});
			list.add(new Object[]{"BONUS9",15,Number.class});
			list.add(new Object[]{"TEMP1",16,Number.class});
			list.add(new Object[]{"TEMP2",17,Number.class});
			list.add(new Object[]{"BAC",18,Number.class});
			list.add(new Object[]{"CODE_TAX",19,String.class});
			list.add(new Object[]{"NOTE",20,String.class});
			list.add(new Object[]{"BONUS1_HOL",21,Number.class});
			list.add(new Object[]{"POSSN",22,String.class});
			list.add(new Object[]{"DEPSN",24,String.class});
			binder.setIndexPropertiesList(list);
			SalaryDAO2 dao = new SalaryDAO2("ATTENDANTDB_"+month+year,ATTENDANTDB.class);
//			SalaryDAO2 dao = new SalaryDAO2("ATTENDANTDB",ATTENDANTDB.class);
			boolean is1 = rad1.isSelected();
			StringBuilder sb = new StringBuilder();
			for (int i=0;i<list.size();i++){
				sb.append(list.get(i)[0]).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			Integer[] types = DbUtils.getTypes("ATTENDANTDB_"+month+year, sb.toString(), Application.getApp().getConnection());
//			Integer[] types = DbUtils.getTypes("ATTENDANTDB", sb.toString(), Application.getApp().getConnection());
			HSSFSheet sheet = wb.getSheetAt(0);//TODO bind from form
			HSSFRow row;
			HSSFCell cell;
			StringBuilder importMessage=new StringBuilder("Lỗi dòng ");
			boolean error=false;
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				//validate empsn
				row = sheet.getRow(i);
				if (row==null){
					importMessage.append(i+", ");
					error=true;
					continue;
				}
				cell = row.getCell(0);
				if (cell==null) {
					importMessage.append(i+", ");
					error=true;
					continue;
				}
				String empsn = HSSFUtils.getStringCellValue(cell, true);
				if (!empsn.matches("[0-9]{8}")){
					importMessage.append(i+", ");
					error=true;
					continue;
				}
				ATTENDANTDB data = new ATTENDANTDB();
				binder.setDataObject(data);
				binder.rowToObject(i);
				int effect = dao.updateFromExcel(data, list, types);
				if (effect==0){
					importMessage.append(i+", ");
					error=true;
					continue;
				}
				dao.calculate(is1, data.getEMPSN(), month, year);
				if (i%5000==0){
					System.gc();
				}
			}
			if (error){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_INFORMATION, "Đã cập nhật dữ liệu lương từ Excel vào hệ thống.\r\n" + importMessage.toString());				
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sw.stop();
		
		System.out.println("Elapse Time: " + (float)sw.getTime()/1000);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif");
		this.setIcon(imageReference1);
		this.setTitle("Cập nhật dữ liệu lương từ excel");
		this.setModal(true);
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(rootLayout);
		Label label1 = new Label();
		label1.setText("Chọn file excel");
		rootLayout.add(label1);
		fs = new FileUploadSelect();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fs.setIcon(imageReference2);
		fs.setIconWidth(new Extent(24, Extent.PX));
		fs.setUploadFileSize(20971520);
		fs.setIconHeight(new Extent(24, Extent.PX));
		rootLayout.add(fs);
		Label label2 = new Label();
		label2.setText("Tính lương tháng");
		rootLayout.add(label2);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(1, Extent.PX));
		rootLayout.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label3 = new Label();
		label3.setText("Năm");
		row1.add(label3);
		sfYear = new SelectField();
		row1.add(sfYear);
		Row row2 = new Row();
		row2.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		row2.setCellSpacing(new Extent(3, Extent.PX));
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setColumnSpan(2);
		row2.setLayoutData(row2LayoutData);
		rootLayout.add(row2);
		rad1 = new RadioButton();
		rad1.setSelected(true);
		rad1.setText("Tính lương 1 mức");
		ButtonGroup ml = new ButtonGroup();
		rad1.setGroup(ml);
		row2.add(rad1);
		rad2 = new RadioButton();
		rad2.setText("Tính lương 2 mức");
		rad2.setGroup(ml);
		row2.add(rad2);
		Label label4 = new Label();
		rootLayout.add(label4);
		btnExecute = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau_hover.gif");
		btnExecute.setRolloverBackgroundImage(new FillImage(imageReference3));
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau_press.gif");
		btnExecute.setPressedBackgroundImage(new FillImage(imageReference4));
		btnExecute.setHeight(new Extent(26, Extent.PX));
		btnExecute.setWidth(new Extent(87, Extent.PX));
		btnExecute.setPressedEnabled(true);
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau.gif");
		btnExecute.setBackgroundImage(new FillImage(imageReference5));
		btnExecute.setRolloverEnabled(true);
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execute(e);
			}
		});
		rootLayout.add(btnExecute);
	}
}
