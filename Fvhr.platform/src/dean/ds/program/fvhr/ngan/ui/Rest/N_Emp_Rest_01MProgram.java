package ds.program.fvhr.ngan.ui.Rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.ui.workpoints.DataDailyBrowserContent;
import ds.program.fvhr.ui.workpoints.NDataDailyBrowserContent;
import ds.program.fvhr.ui.workpoints.NGetData00MDataContent;
import ds.program.fvhr.ui.workpoints.WorkPointExecPane;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.domain.N_REST_ANNUAL_TYPE;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.util.Vni2Uni;

/**
 * a1 * 
 */
public class N_Emp_Rest_01MProgram extends MaintainDProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */
	private N_Emp_Rest_01MDataContent dc;
	private WorkPointExecPane pane;
	private String factCondition = "AND 1 <> 1";
	public Button 	btn_reg_rest;
	private N_Emp_Rest_01MProgram _main;
	private Button btn_count_reg;
	private Button btn_export;
	private  N_Emp_Rest_01MDetailContent0 dc0;
	private  N_Emp_Rest_01MQuery queryPane;
	
	String user_up = Application.getApp().getLoginInfo().getUserID();
	@Override
	protected void createDataContent() {
		dc0 = new N_Emp_Rest_01MDetailContent0();
		setMasterDataContent(new N_Emp_Rest_01MDataContent(dc0));

	//	// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();
		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
       
        
        this.addDetail(bb.getResourceBundle().getString("N_REST_DETAIL"), null, dc0);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	//	bb = null;
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		//<從此以下加入使用者程式>
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition("1<>1", new Object[]{});
		setQueryCondition(pc);
		
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
	//	getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_SAVE, false);
	//	getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CANCEL, false);
	
		//5.設定table的最大筆數及每頁筆數
				
		return ret;
	}
	

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		if(queryPane == null)
			queryPane = new N_Emp_Rest_01MQuery();
		    return queryPane;
	}

	/* 
	 * 調整UI Layout
	 */
	
	
	@Override
	protected void doLayout() {
		super.doLayout();
		_main	= this;
		this.getMasterToolbar().getBtnNew().setVisible(false);
		this.getMasterToolbar().setbtnDeleteVisible(false);
		this.getMasterToolbar().setbtnEmailVisible(false);
		this.getMasterToolbar().setbtnExportVisible(false);
		this.getMasterToolbar().setbtnQueryQBEVisible(false);
		this.getMasterToolbar().setbtnCancelConfirmVisible(false);
		this.getMasterToolbar().setbtnConfirmVisible(false);
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		
				btn_reg_rest = new Button();
				btn_reg_rest.setHeight(new Extent(12));
				btn_reg_rest.setStyleName("Default.ToolbarButton");
				btn_reg_rest.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
				btn_reg_rest.setForeground(Color.WHITE);
				btn_reg_rest.setText("N.Phép");
				btn_reg_rest.setBackground(Color.DARKGRAY);
				btn_reg_rest.addActionListener(new ActionListener() {
					
				
					public void actionPerformed(ActionEvent e) {
						
						N_Registry_Rest_Form	reg_rest = new N_Registry_Rest_Form(_main);
						Application.getApp().getDefaultWindow().getContent().add(reg_rest);
					}
				});
				
				btn_export		= 	new Button();
				btn_export.setHeight(new Extent(12));
				btn_export.setStyleName("Default.ToolbarButton");
				btn_export.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_OUTSET));
				btn_export.setForeground(Color.WHITE);
				btn_export.setText("X.Phép");
				btn_export.setBackground(Color.DARKGRAY);
				btn_export.addActionListener(new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e) {
						N_Export_Rest_Form exp_form = new N_Export_Rest_Form();
						Application.getApp().getDefaultWindow().getContent().add(exp_form);
					}
				});
				
		Row	row_rest = new Row();
			row_rest.setCellSpacing(new Extent(5));
			row_rest.add(btn_reg_rest);
			row_rest.add(btn_export);
		getMasterToolbar().add(row_rest);
		
	}

	@Override
	public boolean doSave() {
		
		return super.doSave();
	}
	
	
	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[]{"EMPSN","EMPSN_Object.FNAME","EMPSN_Object.LNAME","EMPSN_Object.DEPSN","YEAR","STORED","OBTAIN","USED","REMAIN","DEBT","NOTE","STITCHING1","STITCHING2","SENIORITY"};
	}
	
	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			N_REST data = (N_REST) getMasterDataContent().getDataObject();
			String name = Vni2Uni.convertToUnicode(data.getEMPSN_Object().getFNAME() + " " + data.getEMPSN_Object().getLNAME());
			((N_Emp_Rest_01MDataContent)getMasterDataContent()).setName(name);
			return true;
		}
			return false;
		
	}
	

}
