package ds.program.fvhr.ngan.ui.bonus;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMP_BONUS;
import ds.program.fvhr.domain.N_JOB;
import dsc.echo2app.Application;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;

/**
 * a5 * 
 */
public class N_Emp_Bonus_01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	N_Emp_Bonus_01MDataContent dc;
	private String factCondition = " AND 1<>1";

	protected void createDataContent() {
		dc	= new N_Emp_Bonus_01MDataContent();
		setMasterDataContent(dc);
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
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		//5.設定table的最大筆數及每頁筆數
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition("1<>1", new Object[]{});
		setQueryCondition(pc);
				
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
		return new N_Emp_Bonus_01MQuery();
	}


	public boolean doSave() {
		// TODO Auto-generated method stub
		return super.doSave();
	}
	

	protected void doUIRefresh() {
		// TODO Auto-generated method stub
		N_EMP_BONUS masterData 	= (N_EMP_BONUS) this.getBrowserContent().getDataObjectSet().getDataObject();
		String user		=	Application.getApp().getLoginInfo().getUserID();
		
		if(			masterData !=null && masterData.getUSER_UP()!=null 
				&&  masterData.getUSER_UP().equals(user)){
			
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
			
		}else{
			/*this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);*/
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);
		}
		
		
		super.doUIRefresh();
	}
	
	
	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
		
		Button 	btn_imp 	= new Button();
				btn_imp.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_excel.png"));
				btn_imp.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_hv_excel.png"));
				btn_imp.setPressedIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/i_press_excel.png"));
				btn_imp.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/son_disable_excel.png"));
				btn_imp.setRolloverEnabled(true);
				btn_imp.setPressedEnabled(true);
				btn_imp.setWidth(new Extent(30));
				btn_imp.setToolTipText("Cập nhật");
				btn_imp.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						N_Emp_Bonus_WPImport win_imp = new N_Emp_Bonus_WPImport();
						Application.getApp().getDefaultWindow().getContent().add(win_imp);
						
					}
				});
		
		
		Button 	btn_exp	= new Button();
				btn_exp.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_excel.png"));
				btn_exp.setRolloverIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_hv_excel.png"));
				btn_exp.setPressedIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/e_press_excel.png"));
				btn_exp.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/son/son_disable_excel.png"));
				btn_exp.setRolloverEnabled(true);
				btn_exp.setPressedEnabled(true);
				btn_exp.setWidth(new Extent(30));
				btn_exp.setToolTipText("Xuất danh sách");
				btn_exp.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						N_Emp_Bonus_WPExport win_exp	= new N_Emp_Bonus_WPExport();
						Application.getApp().getDefaultWindow().getContent().add(win_exp);
						
					}
				});
				
		this.getMasterToolbar().add(btn_imp);
		this.getMasterToolbar().add(btn_exp);
		
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */

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
        return new String[] {"EMPSN","DEPSN","DATE_EFFECT", "BONUS1", "BONUS5", "BONUS8", "USER_UP", "DATE_UP"};
	}
	
	
	
}
