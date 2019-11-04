package ds.program.fvhr.son.ui.job;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_EMP_INFO;
import ds.program.fvhr.domain.N_EMP_JOB_DETAIL;
import ds.program.fvhr.domain.N_JOB;
import ds.program.fvhr.domain.N_JOB_DETAIL;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * a2 * 
 */
public class N_JOB_INFO_01MProgram extends MaintainDProgram {


	private N_JOB_INFO_01MDetailContent0 _dc;
	private Button btn_refresh;
	

	protected void createDataContent() {
		setMasterDataContent(new N_JOB_INFO_01MDataContent());

		UICaptionBinder bb = new UICaptionBinder();

        _dc = new N_JOB_INFO_01MDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_JOB_DETAIL"), null, _dc);
		bb = null;
	}

	protected int doInit() {
		int ret = super.doInit();
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		
		return ret;
	}


	protected void doInitProgramOK() {
		
	}


	protected QueryPane createNormalQuery() {
		return new N_JOB_INFO_01MQuery();
	}

	protected void doLayout() {
		super.doLayout();
		btn_refresh = new Button();
		btn_refresh.setText("Refresh");
		btn_refresh.setStyleName("Default.ToolbarButton");
		btn_refresh.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				doButtonRefresh();
			}
		});
		this.getMasterToolbar().add(btn_refresh);
	}

	protected void doButtonRefresh() {
		
		ObjUtility obj_util = new ObjUtility();
		BigDecimal money_ = BigDecimal.ZERO;
		DataObjectSet	ds = this.getBrowserContent().getDataObjectSet();
		for(int i = 0 ; i < ds.getRecordCount() ; i ++){
			N_JOB 	job 	= (N_JOB) ds.getDataObject(i);
					System.out.println(job.getID_KEY());
					System.out.println(job.getUSER_UP());
					money_ 	= obj_util.Get_Money_Job_By_Month(job.getID_KEY(), new Date());
					job.setMONEY(money_);
			ds.modify(i,job);
		}
		ds.applyUpdate();
		doBrowserContentRefresh();
		
	}

	
	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */

	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"NAME_JOB", "CODE_JOB", "KIND_JOB", "IN_FACT", "MONEY","USER_UP","DATE_UP"};
	}
	

}
