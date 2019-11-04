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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;

import ds.program.fvhr.domain.N_N_EMP_JOB;
import ds.program.fvhr.domain.N_N_JOB;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.DefaultProgram;
import fv.util.Vni2Uni;

public class N_EMP_JOBMProgram extends DefaultProgram{
	
	Grid rootLayout;
	TextField txt_sothe ;
	SelectField sf_congviec ;
	SelectField sf_xeploai;
	DscDateField df_ngaybd ;
	Label lb_money;
	Label lb_name;
	Label lb_dept;
	Label lb_job;
	Label lb_kind;
	Button bt_save ;
	
	OBJ_UTILITY obj_util;
	SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();
	Calendar ca = Calendar.getInstance();
	public N_EMP_JOBMProgram(){
		
		obj_util = new OBJ_UTILITY();
		InitComponent();
	
	}
	
	private void InitComponent(){
		
		rootLayout = new Grid();
		rootLayout.setSize(5);
		rootLayout.setColumnWidth(0,new Extent(100));
		rootLayout.setRowHeight(0,new Extent(20));
		rootLayout.setColumnWidth(2,new Extent(100));
		rootLayout.setRowHeight(2, new Extent(50));
		rootLayout.setRowHeight(5, new Extent(50));
		this.add(rootLayout);
		// ------------------------
		
		txt_sothe = new TextField();
		txt_sothe.setMaximumLength(8);
		txt_sothe.setWidth(new Extent(155));
		txt_sothe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Show_info_empsn(txt_sothe.getText());
			}
		});
		
		sf_congviec = new SelectField();
		sf_congviec.setWidth(new Extent(160));
		sf_congviec.setModel(Get_Model_Job());

		
		sf_xeploai = new SelectField();
		sf_xeploai.setModel(Get_Model_Kind());
		sf_xeploai.setWidth(new Extent(160));
		sf_xeploai.setSelectedIndex(0);
		sf_xeploai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doAction_sf_xeploai();
			}
		});
		df_ngaybd = new DscDateField();
		df_ngaybd.getTextField().setText(sf.format(ca.getTime()));
		df_ngaybd.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		
		bt_save = new Button();
		bt_save.setText("Save");
		bt_save.setWidth(new Extent(40));
		bt_save.setStyleName("Default.ToolbarButton");
		bt_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				doAction_Save();
				
			}
		});
		
		lb_name 	= new Label();
		lb_kind 	= new Label();
		lb_dept		= new Label();
		lb_job		= new Label();
		
		
		// -------------------------
		rootLayout.add(new Label(" "),0);
		rootLayout.add(new Label(" "),1);
		rootLayout.add(new Label(" "),2);
		rootLayout.add(new Label(" "),3);
		rootLayout.add(new Label(" "),4);
		// ------------------------
		rootLayout.add(new Label("So the:"),5);
		rootLayout.add(txt_sothe,6);
		
		rootLayout.add(lb_name,7);
		rootLayout.add(lb_dept,8);
		rootLayout.add(new Label(" "),9);
		// -----------------------
		rootLayout.add(new Label("Cong viec:"),10);
		rootLayout.add(sf_congviec,11);
		rootLayout.add(lb_job,12);
		rootLayout.add(new Label(" "),13);
		rootLayout.add(new Label(" "),14);
		// ----------------------
		rootLayout.add(new Label("Xep loai"),15);
		rootLayout.add(sf_xeploai,16);
		rootLayout.add(lb_kind,17);
		rootLayout.add(new Label(" "),18);
		rootLayout.add(new Label(" "),19);
		// ----------------------
		rootLayout.add(new Label(),20); 
		lb_money = new Label("");
	    rootLayout.add(lb_money);
	    rootLayout.add(new Label(" "),22);
		rootLayout.add(new Label(" "),23);
		rootLayout.add(new Label(" "),24);
		
		// ----------------------
		rootLayout.add(new Label("Ngay bat dau:"));
	    rootLayout.add(df_ngaybd);
	    rootLayout.add(new Label(" "));
		rootLayout.add(new Label(" "));
		rootLayout.add(new Label(" "));
		// ----------------------
		rootLayout.add(new Label(" "));
		rootLayout.add(getFileUploadSelect());
	
		
		
	}
	
	protected void Show_info_empsn(String empsn) {
		String str_name	= "";
		String str_dept	= "";
		String str_job	= "";
		String str_kind	= "";
		OBJ_EMPSN info_empsn 	= new OBJ_EMPSN(empsn);
		N_N_EMP_JOB obj_emp_job = new N_N_EMP_JOB();
		N_N_JOB	obj_job			= new N_N_JOB();
		ArrayList<Object> list = info_empsn.List_EMP_INFO();
		
		
		if(list.size() > 0){
			str_name 	= 	(String) list.get(2) + " " + (String) list.get(3);
			str_dept	=	(String) list.get(4) + " - " + (String) list.get(5) + " - " + (String) list.get(6);
		}
		
		
		obj_emp_job = Get_Info_EMP_JOB_by_empsn(empsn);
		
		if(obj_emp_job != null){
			 obj_job = Get_JOB_by_ID(obj_emp_job.getID_JOB());
			 str_job = obj_job.getNAME() + " - " + obj_job.getKIND();
		}
		
		lb_name.setText(Vni2Uni.convertToUnicode(str_name));
		lb_dept.setText(Vni2Uni.convertToUnicode(str_dept));
		lb_job.setText(Vni2Uni.convertToUnicode(str_job));
		lb_kind.setText(" ");
		
		
	}

	protected void doAction_sf_xeploai() {
		
		String name = Vni2Uni.convertToVNI(sf_congviec.getSelectedItem().toString());
		
		String kind = sf_xeploai.getSelectedItem().toString();
		
		IGenericDAO<N_N_JOB, String> obj_dao = Application.getApp().getDao(N_N_JOB.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_N_JOB.class);
		obj_dc.add(Restrictions.eq("NAME", name));
		obj_dc.add(Restrictions.eq("KIND", kind));
		
		List<N_N_JOB> list  = obj_dao.findByCriteria(1, obj_dc);
		
		if(list!= null && list.size() > 0){
			for(N_N_JOB obj : list){
				if(obj.getMONEY() != null){
					lb_money.setText(String.valueOf(obj.getMONEY()));
				}else{
					lb_money.setText("0");
				}
			}
		}else{
			
			lb_money.setText("0");
		}
			
	}

	private DefaultListModel Get_Model_Kind(){
		DefaultListModel model = new DefaultListModel();
			model.add("");
			model.add("A");
			model.add("B");
			model.add("C");
		return model;
	}
	
	private DefaultListModel Get_Model_Job(){
		DefaultListModel model = new DefaultListModel();
		String sql = "";
		sql = "select distinct t.name from n_n_job t where t.date_e is null";
		
		
		
		List<String> list = null;
		try {
			list = obj_util.Exe_Sql_String(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.add("");
		
		for(String str : list){
			model.add(Vni2Uni.convertToUnicode(str));
		}
		
		return model;
	}
	
	protected void doAction_Save() {
	}

	private UploadSelect getFileUploadSelect(){
		
		final Resource res = Application.getSpringContext().getResource("classpath:/conf/format");
		UploadSelect fileUploadSelect = new UploadSelect();
		fileUploadSelect.setEnabledSendButtonText("SAVE");
		fileUploadSelect.setWidth(new Extent(250,Extent.PX));
		fileUploadSelect.setHeight(new Extent(60));
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
						do_fileUpload(outputFile);
						outputFile = null;
					} catch (IOException e1) {
						System.out.println("KimNgan=========Error not import File");
						// TODO: handle exception
						// e1.printStackTrace();
					}
				}

				@Override
				public void invalidFileUpload(UploadEvent e) {
					do_invalidFileUpload();
				}
			});
		} catch (TooManyListenersException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.toString());
		}
		return fileUploadSelect;
	}
	
	protected void do_invalidFileUpload() {
		
		// truong hop khong co file import thi se luu so the txt_field
		String name			= "";
		String kind			= "";
		String key			= "";
		Date date 			= null;
		String 	date_str	= df_ngaybd.getText();
		String empsn 		= txt_sothe.getText();
		String 	note		= "";
		
		if(obj_util.check_empsn(empsn)){
			System.out.println(sf_xeploai.getSelectedIndex());
			if(sf_congviec.getSelectedIndex()!=0 && sf_congviec.getSelectedIndex() != -1
					&& sf_xeploai.getSelectedIndex()!=0 && sf_congviec.getSelectedIndex()!=-1	){
						
						name 	= sf_congviec.getSelectedItem().toString();
						kind	= sf_xeploai.getSelectedItem().toString();
			}
				key = Get_ID_JOB(name, kind);
			
				try {
					date = 	sf.parse(date_str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
				this.SaveData(key, empsn, date, note);
		}else{
			OBJ_UTILITY.ShowMessageError("Kiem tra lai so the");
		}
	}

	// -----------------
	private void do_fileUpload(File nameFile){
		InputStream input = null;
		String key	= "";
		String name = "";
		String kind = "";
		String 	date_str	= df_ngaybd.getText();
		Date date = null;
		
		try {
			date = sf.parse(date_str);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			input = new FileInputStream(nameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
				
			
			HSSFWorkbook wb = new HSSFWorkbook(input);
			HSSFDataFormat dataFormat = wb.createDataFormat();
			if(sf_congviec.getSelectedIndex()!=0 && sf_congviec.getSelectedIndex() != -1
				&& sf_xeploai.getSelectedIndex()!=0 && sf_congviec.getSelectedIndex() !=-1	){
					
					name 	= sf_congviec.getSelectedItem().toString();
					kind	= sf_xeploai.getSelectedItem().toString();
			}
				key	= this.Get_ID_JOB(name, kind);
			
			HSSFSheet sheet = wb.getSheetAt(0);
			SaveData_by_Excel(sheet,key,date);
		
		} catch (FileNotFoundException e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"文件不存在1!");
			e.printStackTrace();
		} catch (Exception e) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK ,"Excel格式不對,請檢查!");
			e.printStackTrace();
		}finally{
			
		}
	}
	
	// get id by name and kind in N_N_JOB
	
	private void SaveData_by_Excel(HSSFSheet sheet, String key, Date date) {
		HSSFRow 	row;
		HSSFCell 	cell;
		String 		empsn	="";
		String 		note	="";
		int 		n 		= sheet.getPhysicalNumberOfRows();
		
		for (int i = 1; i < n; i++) {
			row = sheet.getRow(i);
			
			for (int j = 0; j < 2; j++) {
				
				switch (j) {
				case 0:
					if(row.getCell(j).getStringCellValue() != null){
						empsn 	= row.getCell(j).getStringCellValue();
					}else{
						
					}
					break;
					
				case 1 :
					if(row.getCell(j).getStringCellValue() != null){
						note	= row.getCell(j).getStringCellValue();
					}else{
						note 	= "";
					}
					break;
					
				default:
					break;
				}
			}
			
			try{
				SaveData(key, empsn, date, note);
			}catch (Exception e) {
				
			}
			empsn 	= "";
			note	= "";
						
		}
		
	}

	
	private String Get_ID_JOB(String name, String kind){
		
		N_N_JOB obj_Job = new N_N_JOB();
		IGenericDAO<N_N_JOB, String> obj_dao = Application.getApp().getDao(N_N_JOB.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_N_JOB.class);
		obj_dc.add(Restrictions.eq("NAME", Vni2Uni.convertToVNI(name)));
		obj_dc.add(Restrictions.eq("KIND", kind));
		
		List<N_N_JOB> list  = obj_dao.findByCriteria(1, obj_dc);
		if(list!=null && list.size() > 0){
			obj_Job = list.get(0);
		}
		
		return obj_Job.getID_JOB();
		
	}
	
	private N_N_JOB Get_JOB_by_ID(String pk){
		N_N_JOB obj_job = new N_N_JOB();
		IGenericDAO<N_N_JOB, String> obj_dao = Application.getApp().getDao(N_N_JOB.class);
		
		obj_job = obj_dao.findById(pk);
		
		return obj_job;
	}
	
	private N_N_EMP_JOB Get_Info_EMP_JOB_by_empsn(String empsn){
		
		N_N_EMP_JOB obj_info = new N_N_EMP_JOB();
		IGenericDAO<N_N_EMP_JOB, String> obj_dao = Application.getApp().getDao(N_N_EMP_JOB.class);
		DetachedCriteria obj_dc = DetachedCriteria.forClass(N_N_EMP_JOB.class);
		obj_dc.add(Restrictions.eq("EMPSN", empsn));
		obj_dc.add(Restrictions.isNull("DATE_E"));

		List<N_N_EMP_JOB> list = obj_dao.findByCriteria(1, obj_dc);
		
		if(list != null && list.size() > 0){
			obj_info = list.get(0);
		}
		
		return obj_info;
	}
	
	private void SaveData(String id_key,String empsn,Date date,String note){
		
		
		IGenericDAO<N_N_EMP_JOB, String> obj_dao = Application.getApp().getDao(N_N_EMP_JOB.class);
		N_N_EMP_JOB obj_save = new N_N_EMP_JOB();
		obj_save.setID_JOB(id_key);
		obj_save.setEMPSN(empsn);
		obj_save.setDATE_B(date);
		obj_save.setNOTE(note);
		try{
			obj_dao.save(obj_save);
			
		}catch (Exception e) {
			OBJ_UTILITY.ShowMessageError("Khong the luu so the : " + empsn);
		}
	}
	
	
}
