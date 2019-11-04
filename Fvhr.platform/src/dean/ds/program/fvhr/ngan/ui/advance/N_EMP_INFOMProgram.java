package ds.program.fvhr.ngan.ui.advance;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMP_ADVANCE;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.ngan.ui.advance.Advance_WPane;
import ds.program.fvhr.ngan.ui.advance.N_Update_Advance_monthly;
import ds.program.fvhr.util.OBJ_EMPSN;
import dsc.echo2app.Application;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;

/**
 * KNGAN * 
 */
public class N_EMP_INFOMProgram extends MaintainSProgram {
	Button 	btn_add;
	Button 	btn_check;
	private String factCondition = "AND 1<>1";
	String user_up 				= Application.getApp().getLoginInfo().getUserID();
	OBJ_EMPSN obj_e  		    = new OBJ_EMPSN();

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */

	protected void createDataContent() {
		setMasterDataContent(new N_EMP_INFOMDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected int doInit() {
		int ret = super.doInit();
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition(condStr, new Object[]{});
		setBaseCondition(pc);
		
		this.getBrowserContent().setMaxSize(100000);
		this.getBrowserContent().setPageSize(20);
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);
			btn_add	= new Button();
		//	btn_add.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/add.png"));
		//	btn_add.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/add_hv.png"));
			btn_add.setStyleName("Default.ToolbarButton");
			btn_add.setText("Dữ liệu cơ bản");
			
			btn_add.setRolloverEnabled(true);
			btn_add.setPressedEnabled(true);
			btn_add.setBorder(new Border(2,Color.LIGHTGRAY,Border.STYLE_OUTSET));
		//	btn_add.setWidth(new Extent(30));
			//btn_add.setToolTipText("Cập nhật");
			btn_add.setToolTipText("Dữ liệu cơ bản");
		
		//*******
			btn_check = new Button(); //dung de update va xem thong tin ung luong tung thang
		
			btn_check.setStyleName("Default.ToolbarButton");
			btn_check.setText("Dữ liệu ứng lương theo tháng");
			
			btn_check.setRolloverEnabled(true);
			btn_check.setPressedEnabled(true);
			btn_check.setBorder(new Border(2,Color.LIGHTGRAY,Border.STYLE_OUTSET));
			btn_check.setToolTipText("Dữ liệu ứng lương theo tháng");
		//*******
		
		btn_add.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				Advance_WPane Advance_add = new Advance_WPane();
				Application.getApp().getDefaultWindow().getContent().add(Advance_add);
				
			}
		});
		
		btn_check.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				N_Update_Advance_monthly Advance_check = new N_Update_Advance_monthly();
				Application.getApp().getDefaultWindow().getContent().add(Advance_check);
				
			}
		});
			
		this.getMasterToolbar().add(btn_add);
		this.getMasterToolbar().add(btn_check);
		//5.設定table的最大筆數及每頁筆數
				
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
		return new N_EMP_INFOMQuery();
	}

	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		if(obj_e.check_user_KToan(user_up) || obj_e.check_user_Admin(user_up))
		{
			 btn_check.setEnabled(true);
			
		}else
		{
			 btn_check.setEnabled(false);
			 btn_check.setBorder(new Border(2, Color.LIGHTGRAY, Border.STYLE_SOLID));
			 btn_check.setBackground(Color.LIGHTGRAY);
		}
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	
	protected void doUIRefresh() {
		// TODO Auto-generated method stub
		N_EMP_INFO masterData 	= (N_EMP_INFO) this.getBrowserContent().getDataObjectSet().getDataObject();
		String user		=	Application.getApp().getLoginInfo().getUserID();
		
		if(			masterData !=null )
				{
			
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
			
		}else{
			/*this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);*/
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		}
		
		
		super.doUIRefresh();
	}

	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		//6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
     //   return new String[] {"ADVANCE", "CODE", "DATE_HIRED", "DEPSN", "EMPCN", "EMPSN", "ID_JOB", "ID_NO", "ID_POSS", "LNAME", "NOTE", "SALARY_B", "SHIFT", "UP_DATE", "UP_USER", "USER_MANAGE_ID", "WORK_STATUS"};
		return new String[] {"EMPSN","ADVANCE","UP_USER","UP_DATE"};
	}
	

}
