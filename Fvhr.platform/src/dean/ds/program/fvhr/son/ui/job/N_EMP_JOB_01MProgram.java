package ds.program.fvhr.son.ui.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.util.function.UUID;

/**
 * 1 * 
 */
public class N_EMP_JOB_01MProgram extends MaintainDProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */
	
	SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy");
	private N_EMP_JOB_01MDetailContent0 _dc;
	private String _user_name = "";

	protected void createDataContent() {
		setMasterDataContent(new N_EMP_JOB_01MDataContent());
		// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();

		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        
        _dc = new N_EMP_JOB_01MDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_EMP_JOB_DETAIL"), null, _dc);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
		bb = null;
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#doInit()
	 */

	protected int doInit() {
		int ret = super.doInit();
		
		_user_name  = Application.getApp().getLoginInfo().getUserID();
		
        this.setQBEDisplayFields(new String[] {"EMPSN"});

		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);

		this.getBrowserContent().setMaxSize(50000);
		this.getBrowserContent().setPageSize(100);
		
		ProgramCondition pc = new ProgramCondition("o.DEPSN !='00000' OR o.DEPSN = null ", new Object[]{});
		setBaseCondition(pc);
		return ret;
	}

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */

	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
	}


	protected QueryPane createNormalQuery() {
		return new N_EMP_JOB_01MQuery();
	}


	public boolean doSave() {
	
		//DetailToDelete = detailcontent.get....
		//if (data!=null){
		//call pro...
		//clean--> setDeletedRecord(null)
		
		if(super.doSave()){	
		
			N_EMP_JOB_DETAIL data_deleted = _dc._deletedRecord;
			
			if(data_deleted!=null){
				
				Connection con = Application.getApp().getConnection();
				String sql = "{call CALLPRO_DELROW_EMPJOBDETAIL(?,?)}" ;
				
				java.sql.Date date_sql = new java.sql.Date(data_deleted.getDATE_B().getTime());
				
				try {
					
					CallableStatement stm = con.prepareCall(sql);
					stm.setString(1, data_deleted.getEMPSN());
					stm.setDate(2,date_sql);
					
					stm.execute();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				finally{
					_dc._deletedRecord = null;
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			doDataContentRefresh(getMasterDataContent().getDataObjectSet().getRecNo());
			
			return true;
		}
		return false;
	}
	

	protected void doUIRefresh() {
		// TODO Auto-generated method stub
		super.doUIRefresh();
		
		N_EMP_INFO masterData_ = (N_EMP_INFO)this.getBrowserContent().getBrowserTable().getDataObjectSet().getDataObject();
		String user_ = Application.getApp().getLoginInfo().getUserID();
		
		if(masterData_ != null){
			ObjUtility obj_util = new ObjUtility();
			if(obj_util.CheckRight(user_,masterData_.getEMPSN())){
				this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
			}else{
				this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
			}
		}
	}
	
	

	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		
		Button btn_export = new Button();
		Button btn_import = new Button();
		Button btn_refresh = new Button();
		btn_export.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_excel.png"));
		btn_export.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_hv_excel.png"));
		btn_export.setPressedIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_press_excel.png"));
		btn_export.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/son_disable_excel.png"));
		btn_export.setRolloverEnabled(true);
		btn_export.setPressedEnabled(true);
		btn_export.setWidth(new Extent(30));
		btn_export.setToolTipText("Xuất danh sách");
		btn_export.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
					
					N_Emp_Job_WPExport win_exp	= new N_Emp_Job_WPExport();
					win_exp.setStyleName("Default.Window");
					Application.getApp().getDefaultWindow().getContent().add(win_exp);
					//do_Export();
				
			}
		});
		
		btn_import.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_excel.png"));
		btn_import.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_hv_excel.png"));
		btn_import.setPressedIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_press_excel.png"));
		btn_import.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/son_disable_excel.png"));
		btn_import.setRolloverEnabled(true);
		btn_import.setPressedEnabled(true);
		btn_import.setWidth(new Extent(30));
		btn_import.setToolTipText("Cập nhật công việc");
		btn_import.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				do_Import();
				
			}
		});
		this.getMasterToolbar().add(btn_import);
		
		this.getMasterToolbar().add(btn_export);
		
		/*if(_user_name.equals("ADMIN")){
			btn_import.setEnabled(true);
		}else{
			btn_import.setEnabled(false);
		}*/
		
		
		btn_refresh.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/r_fresh.png"));
		btn_refresh.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/hv_r_fresh.png"));
		btn_refresh.setPressedIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/press_r_fresh.png"));
		btn_refresh.setRolloverEnabled(true);
		btn_refresh.setPressedEnabled(true);
		btn_refresh.setToolTipText("Refresh");
		btn_refresh.setWidth(new Extent(30));
		btn_refresh.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				do_Refresh();
				doDataRefresh();
			}
		});
		
		this.getMasterToolbar().add(btn_refresh);
		
		/*Button btn_exp = new Button();
		btn_exp.setText("Export by Date");
		btn_exp.setStyleName("Default.ToolbarButton");
		btn_exp.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				N_Emp_Job_WPExport win_exp	= new N_Emp_Job_WPExport();
				win_exp.setStyleName("Default.Window");
				Application.getApp().getDefaultWindow().getContent().add(win_exp);
				
			}
		});
		this.getMasterToolbar().add(btn_exp);*/
	}


	protected void do_Refresh() {
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		IGenericDAO<N_EMP_INFO, String> obj_dao = Application.getApp().getDao(N_EMP_INFO.class);
		ObjUtility obj_util = new ObjUtility();
		boolean rs_ = false;
		String id_job_ = "";
		for (int i = 0; i < ds.getRecordCount(); i++) {
			N_EMP_INFO data = (N_EMP_INFO) ds.getDataObject(i);
			id_job_ = obj_util.Get_Id_Job_By_Month(data.getEMPSN(), new Date());
			if(id_job_ != null){
				System.out.println(data.getEMPSN());
			}
			id_job_	= id_job_ == null ? "" : id_job_;
			rs_ = obj_util.Update_field("N_EMP_INFO", "ID_JOB", id_job_, "EMPSN", data.getEMPSN());
			if(rs_ == false){
				System.out.println(data.getEMPSN());
			}
			id_job_ = "";
		}
		
	}

	protected void do_Import() {
		N_Emp_Job_WPImport win_Import = new N_Emp_Job_WPImport();
		win_Import.setStyleName("Default.Window");
		Application.getApp().getDefaultWindow().getContent().add(win_Import);
		
	}
	
	

	protected void do_Export() throws IOException {
		
		File f = ReportFileManager.getReportFormatFolder("dshr");
		InputStream in = new FileInputStream(new File(f.getPath(), "phan_cong_cong_viec.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(in);
		HSSFSheet s = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		IGenericDAO dao = Application.getApp().getDao(getLoginInfo().getCompanyID());
		
		String sql_call 		= "{? = call SON_GET_JOB_ID_FOR_EMP(?,?)}";
		String sql				= "";
		Connection	con 		= Application.getApp().getConnection();
		CallableStatement stm 	;
		ObjUtility	obj_util	= new ObjUtility();
		List	list		= null;
		List	list1		= null;
		String id_job_		= "";
		String name_job_	= "";
		String note			= "";
		int r=0;
		java.sql.Date date_sql = new java.sql.Date(new Date().getTime());
		
			try {
			
			for (int i=0;i<ds.getRecordCount();i++){
				
				N_EMP_INFO data = (N_EMP_INFO) ds.getDataObject(i);
				// STT(0) -- Ho ten (1) -- Xuong(2) -- Nhom (3) -- Don vi (4) -- Ten CV (5) -- MaCV(6) -- Kind (7) -- InFact (8) -- Money (9)
				r++;
				row = s.createRow(r);
				cell = row.createCell(0);	
				cell.setCellValue(r);	// STT
				
				cell = row.createCell(1);	
				cell.setCellValue(data.getEMPSN()); // EMSPN
				
				
				
					
					
					stm = con.prepareCall(sql_call);
					stm.setString(2, data.getEMPSN());
					stm.setDate(3, date_sql);
					stm.registerOutParameter(1, Types.VARCHAR);
					
					stm.execute();
					
					id_job_ = stm.getString(1);
					id_job_ = id_job_ == null ? "" : id_job_;
					
					stm.close();
				
				if(!id_job_.equals("")){
					sql	=
						"select nvl(t.note,'') from n_emp_job_detail t\n" +
						"where t.id_job = '"+id_job_+"' \n"  + 
						"      and t.date_e is null" +
						" 	   and t.empsn = '" +data.getEMPSN()+"'" ;
		
					Object	obj_	= obj_util.Exe_Sql_Obj(sql);
					note 			= obj_== null ? "" : obj_.toString();
				}else{
					note			= "";
				}
				
				list 	= dao.find(1, "select a.FNAME, a.LNAME, b.NAME_FACT,b.NAME_GROUP, b.NAME_DEPT_NAME from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{data.getEMPSN()});
				list1 	= dao.find(1, "select b.NAME_JOB,b.CODE_JOB,b.KIND_JOB,b.IN_FACT from N_JOB b where b.ID_KEY = ?", new Object[]{id_job_});
				
				
				
				if (list.size()>0){
					
					Object[] obj = (Object[]) list.get(0);
					
					cell = row.createCell(2);	//HO TEN
					cell.setCellValue(obj[0].toString().trim() + " " + obj[1].toString().trim());
					
					cell = row.createCell(3);	//NAME FACT
					cell.setCellValue(obj[2]==null?"":obj[2].toString());
					
					cell = row.createCell(4);	//NAME GROUP
					cell.setCellValue(obj[3]==null?"":obj[3].toString());
					
					cell = row.createCell(5);	//NAME DEPT
					cell.setCellValue(obj[4]==null?"":obj[4].toString());
					
					if (list1.size()>0){
						
						Object[] obj1 = (Object[]) list1.get(0);
					
						cell = row.createCell(6);	// NAME_JOB
						name_job_	=  obj1[0] == null ? "" : obj1[0].toString() ; 
						cell.setCellValue(name_job_);
						
						cell = row.createCell(7);// CODE_JOB
						cell.setCellValue(obj1[1] == null ? "" : obj1[1].toString());
					
						cell = row.createCell(8);// KIND
						cell.setCellValue(obj1[2] == null ? "" : obj1[2].toString());
						
						cell = row.createCell(9);//IN FACT
						cell.setCellValue(obj1[3] == null ? "" : obj1[3].toString());
						
						
						
					}
					
					cell = row.createCell(10);//NOTE
					cell.setCellValue(note);
					
				}else{
					cell = row.createCell(1);
					cell.setCellValue("Không tìm thấy thông tin");
				}
			}
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
		FileOutputStream out = new FileOutputStream(f1);
		wb.write(out);
		out.flush();
		out.close();
		String name = "Danh_sach_phan_cong_cong_viec";
		File saveFile = new File(f1.getParentFile(),URLEncoder.encode(getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
		ReportFileManager.saveTempReportFile(f1, saveFile);
		Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"EMPSN", "LNAME", "DEPSN", "ID_JOB", "USER_MANAGE_ID"};
	}
	
	
}
