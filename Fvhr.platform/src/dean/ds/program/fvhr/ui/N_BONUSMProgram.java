package ds.program.fvhr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TooManyListenersException;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.fv.app.filetransfer.UploadEvent;
import nextapp.echo2.fv.app.filetransfer.UploadListener;
import nextapp.echo2.fv.app.filetransfer.UploadSelect;
import nextapp.echo2.app.list.DefaultListModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import ds.program.fvhr.domain.N_N_EMP_OTHER_BONUS;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.echo2app.program.DefaultProgram;

public class N_BONUSMProgram extends DefaultProgram {
	

	OBJ_UTILITY obj_util;
	
	Grid rootLayout;
	TextField txt_sothe;
	DscField txt_sotien;
	SelectField sf_xuong;
	SelectField sf_nhom;
	SelectField sf_donvi;
	DscGroupRadioButton GR_rad_btm;
	RadioButton	rad_btn1;
	RadioButton rad_btn2;
	Button export_e;
	
	Calendar ca = Calendar.getInstance();
	SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();
	IGenericDAO<N_N_EMP_OTHER_BONUS, String> obj_dao ;
	
	
	public N_BONUSMProgram(){
		obj_util 	= new OBJ_UTILITY();
		obj_dao		= Application.getApp().getDao(N_N_EMP_OTHER_BONUS.class);
		InitComponent();
		
	}

	private void InitComponent(){
		rootLayout = new Grid();
		rootLayout.setSize(5);
		rootLayout.setRowHeight(0, new Extent(40));
		rootLayout.setRowHeight(1, new Extent(40));
		rootLayout.setRowHeight(2, new Extent(40));
		rootLayout.setRowHeight(3,new Extent(50));
		rootLayout.setRowHeight(4,new Extent(80));
		rootLayout.setColumnWidth(2,new Extent(100));
		
		// rootLayout.setBorder(new Border(new Extent(2), Color.GREEN,
		// Border.STYLE_DASHED));
		
		this.add(rootLayout);
		
		// ----------------
	
		GR_rad_btm = new DscGroupRadioButton();
		GR_rad_btm.setOrientation(1);
		
		rad_btn1 	= 	new RadioButton();
		rad_btn2	=	new RadioButton();
		
		rad_btn1.setText("Tien Thuong");
		rad_btn1.setSelected(true);
		rad_btn2.setText("Bu Luong");
		
		GR_rad_btm.add(rad_btn1);
		GR_rad_btm.add(rad_btn2);
		
		// --------------
		
		txt_sotien = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txt_sotien.setText("0");

		txt_sothe = new DscField(DscField.INPUT_TYPE_INTEGER);
		txt_sothe.setMaximumLength(8);

		sf_xuong = new SelectField();
		sf_xuong.setModel(obj_util.Get_Model_Fact());
		sf_xuong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doAction_sf_xuong();
			}
		});
		
		sf_nhom = new SelectField();
		sf_nhom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doAction_sf_nhom();
		
			}
		});
		
		sf_donvi = new SelectField();
		
		export_e = new Button("Export Excel");
		export_e.setStyleName("Default.ToolbarButton");
		
		
		
		
			// --------------
		
		
		
		
		rootLayout.add(new Label("So the:"));
		rootLayout.add(txt_sothe);
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label("Xuong:"));
		rootLayout.add(sf_xuong);
		
			// -----------
		rootLayout.add(new Label("So tien:"));
		rootLayout.add(txt_sotien);
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label("Nhom:"));
		rootLayout.add(sf_nhom);
		
			// -----------
		
		rootLayout.add(new Label(" "));
		rootLayout.add(GR_rad_btm);
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label("Don vi:"));
		rootLayout.add(sf_donvi);
			// -----------
		rootLayout.add(new Label(" "));
		
		Button btn_save = new Button("Save");
		btn_save.setStyleName("Default.ToolbarButton");
		btn_save.setWidth(new Extent(50));
		rootLayout.add(btn_save);
		btn_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				do_BtnSave();
			}
		});
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label(" "));
		rootLayout.add(export_e);
		
			// -----------------
		rootLayout.add(new Label(" "));
		rootLayout.add(getFileUploadSelect());
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label(" "));
		
	}

	protected void do_BtnSave() {
		if(rad_btn1.isSelected()){
			doSaveBonus1();
		}else{
			doSaveBonus5();
		}
	}

	protected void doAction_sf_nhom() {
		// TODO Auto-generated method stub
		String index1 = "";
		String index2 = "";
		if(sf_nhom.getSelectedIndex() != -1 && sf_nhom.getSelectedIndex() != 0){
			index1 = (String) sf_xuong.getSelectedItem();
			index2 = (String) sf_nhom.getSelectedItem();
			sf_donvi.setModel(obj_util.Get_Model_Dept(index1, index2));
			sf_donvi.setSelectedIndex(0);
		}else{
			sf_donvi.setModel(new DefaultListModel(new String[]{""}));
			sf_donvi.setSelectedIndex(0);
		}
	
	}

	protected void doAction_sf_xuong() {
		// TODO Auto-generated method stub
		String indexItem = "";
	
		if(sf_xuong.getSelectedIndex() != -1 && sf_xuong.getSelectedIndex() != 0){
			indexItem = (String) sf_xuong.getSelectedItem();
			sf_nhom.setModel(obj_util.Get_Model_Group(indexItem));
			sf_nhom.setSelectedIndex(0);
		}else{
			sf_nhom.setSelectedIndex(0);
			sf_nhom.setModel(new DefaultListModel(new String[]{""}));
			sf_donvi.setSelectedIndex(0);
			sf_donvi.setModel(new DefaultListModel(new String[]{""}));
		
		}
	}
	
	// Import Excel
	
	private UploadSelect getFileUploadSelect(){
		
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("Import");
		fileUploadSelect.setWidth(new Extent(300,Extent.PX));
		fileUploadSelect.setHeight(new Extent(50));
		
		try {
			
			fileUploadSelect.addUploadListener(new UploadListener() {
				@Override
				public void fileUpload(UploadEvent e) {
					if (!(e.getFileName().endsWith(".xls") || (e.getFileName().endsWith(".XLS")))) {
						Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, "Format Wrong");
						
						return;
					}
					
					try {
						assert res.getFile().exists() && res.getFile().isDirectory() : "";
						File outputFile = new File(res.getFile(), "temp.xls");
						long unwrite = e.getSize();
						OutputStream out = new FileOutputStream(outputFile);
						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = e.getInputStream().read(buf)) > 0) {
							// when number of remain bytes is greater than
							// buffer size
							if (unwrite >= len)
								out.write(buf, 0, len);
							else {
								out.write(buf, 0, (int) unwrite);
								break;
							}
							unwrite -= len;
						}
						out.flush();
						out.close();
						doFileUpload(outputFile);
						outputFile = null;
						
					} catch (IOException e1) {
						
						System.out.println("KimNgan=========DANG BI DIEN");
						
					}
				}
				
				
				@Override
				public void invalidFileUpload(UploadEvent e) {
					// strange things happened...
					if(check_input_data()){

							doInvalidFileUpload();
					
					}else{
						OBJ_UTILITY.ShowMessageInfo("Kiem tra thong tin nhap du lieu");
					}
					
				}
			});
		} catch (TooManyListenersException ex) {
			// TODO: handle exception
			
			throw new RuntimeException(ex.toString());
			
		}
		return fileUploadSelect;
	}
	
	protected void doInvalidFileUpload() {
		
		
		
	}

	private void doSaveBonus5(){
		
		String str_money 	=	"";
		String empsn		= 	"";
		
		str_money 	= txt_sotien.getText();
		empsn		= txt_sothe.getText();
		
		Date date 	= 	null;
		try {
			date	=	sf.parse(sf.format(ca.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		N_N_EMP_OTHER_BONUS obj_update ;
		
			obj_update = obj_dao.findById(empsn);

		if(obj_update == null ){
			
			obj_update = new N_N_EMP_OTHER_BONUS();
			
		}
			
		obj_update.setEMPSN(empsn);
		obj_update.setBONUS5(Long.valueOf(str_money));
		obj_update.setDATE_B5(date);
		
		try{
			obj_dao.saveOrUpdate(obj_update);
			OBJ_UTILITY.ShowMessageOK(" Save Complete Bonus 5  !");
		}catch (Exception e) {
			OBJ_UTILITY.ShowMessageError("Update false ! " + empsn);
		}		
		
		
	}

	
	private void doSaveBonus1() { // save tien thuong
		
		String str_money 	=	"";
		String empsn		= 	"";
		
		str_money 	= txt_sotien.getText();
		empsn		= txt_sothe.getText();
		
		Date date 	= 	null;
		try {
			date	=	sf.parse(sf.format(ca.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		N_N_EMP_OTHER_BONUS obj_update ;
		
			obj_update = obj_dao.findById(empsn);

		if(obj_update == null ){
			
			obj_update = new N_N_EMP_OTHER_BONUS();
			
		}
			
		obj_update.setEMPSN(empsn);
		obj_update.setBONUS1(Long.valueOf(str_money));
		obj_update.setDATE_B1(date);
		
		try{
			obj_dao.saveOrUpdate(obj_update);
			OBJ_UTILITY.ShowMessageOK(" Save Complete Bonus 1 !");
		}catch (Exception e) {
			OBJ_UTILITY.ShowMessageError("Update false !! " + empsn);
		}		
	}

	protected boolean check_input_data() {
		// TODO Auto-generated method stub
	    Boolean flag = true;
	    
	    return  obj_util.check_empsn(txt_sothe.getText());
		
	}
	
	// -----------------
	private void doFileUpload(File nameFile){
		InputStream input = null;
		
		try {
			input = new FileInputStream(nameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			
			HSSFWorkbook 	wb 			= new HSSFWorkbook(input);
			HSSFDataFormat dataFormat 	= wb.createDataFormat();
			
			HSSFSheet sheet = wb.getSheetAt(0);
			
			SaveDataByExcel(sheet);
			
			

		} catch (FileNotFoundException e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"文件不存在1!");
			e.printStackTrace();
		} catch (Exception e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"Excel格式不對,請檢查!");
			e.printStackTrace();
		}finally{
			
		}
	}
	// End Import Excel

	private void SaveDataByExcel(HSSFSheet sheet) {
		
		HSSFRow row;
		HSSFCell cell;
		String empsn ="";
		long bonus1	=0;
		long bonus5	=0;
		Date date = null;
		String str_error ="";
		boolean flag_save = true;
		try {
			date = sf.parse(sf.format(ca.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int n = sheet.getPhysicalNumberOfRows();
		
		for (int i = 1; i < n; i++) {
			
			row	= sheet.getRow(i);
			for (int j = 0; j < 3 ; j++) {
				cell = row.getCell(j);
				switch (j) {
				case 0:
					empsn = cell.getStringCellValue();
					break;
				case 1:
					try{
						bonus1 =  (long) cell.getNumericCellValue();
					}catch (Exception e) {
						bonus1 = 0;
						continue;
					}
					break;
				case 2:
					try{
						bonus5	= (long) cell.getNumericCellValue();
					}catch (Exception e) {
						bonus5 = 0;
						continue;
					}
					break;
					
				default:
					break;
				}
				
			}

			N_N_EMP_OTHER_BONUS obj_update = null ;
			
			obj_update = obj_dao.findById(empsn);
			
			if(obj_update == null){
				obj_update = new N_N_EMP_OTHER_BONUS();
			}
			
			obj_update.setEMPSN(empsn);
			if(bonus1 != 0){
				obj_update.setBONUS1(bonus1);
				obj_update.setDATE_B1(date);
			}
			
			if(bonus5 != 0){
				obj_update.setBONUS5(bonus5);
				obj_update.setDATE_B5(date);
			}
			try{
				obj_dao.saveOrUpdate(obj_update);
				flag_save = true;
				empsn = "";
				bonus1 =0;
				bonus5 =0;
			}catch (Exception e) {
				flag_save = false;
				str_error = empsn + ";"+str_error;
			}
		}
		
		if(flag_save == false){
			OBJ_UTILITY.ShowMessageError("Not Update empsn : " + str_error );
		}
		
	}
}
