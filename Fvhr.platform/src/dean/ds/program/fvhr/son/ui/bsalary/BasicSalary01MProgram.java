package ds.program.fvhr.son.ui.bsalary;

import nextapp.echo2.app.Extent;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;

/**
 * a4 * 
 */
public class BasicSalary01MProgram extends MaintainDProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */

	protected void createDataContent() {
		setMasterDataContent(new BasicSalary01MDataContent());

		// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();

		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        DetailContent dc1;
        DetailContent dc2;
        dc1 = new BasicSalary01MDetailContent0();
        dc2	= new BonusPoss01MDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_BASIC_SALARY"), null, dc1);
        this.addDetail(bb.getResourceBundle().getString("N_BONUS_POSS"), null, dc2);
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
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		
		ProgramCondition pc = new ProgramCondition("o.DEPSN!=?", new Object[]{"00000"});
		setBaseCondition(pc);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */

	protected void doInitProgramOK() {
	}


	protected QueryPane createNormalQuery() {
		return new BasicSalary01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */

	protected void doLayout() {
		super.doLayout();
	}



	protected String[] getBrowserDisplayColumns() {
        return new String[] { "EMPSN", "FNAME","LNAME", "DEPSN", "POSITION",  "USER_MANAGE_ID", "WORK_STATUS"};

	}
}
