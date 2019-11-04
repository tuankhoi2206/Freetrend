package ds.program.fvhr.son.ui.job;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;


import nextapp.echo2.app.Extent;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_JOB;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * a1 * 
 */
public class N_JOB_01MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	private N_JOB_01MDataContent _dc;
	

	protected void createDataContent() {
		_dc	= new N_JOB_01MDataContent();
		setMasterDataContent(_dc);
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
		
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);

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
		return new N_JOB_01MQuery();
	}


	protected void doUIRefresh() {
		// TODO Auto-generated method stub
		N_JOB masterData 	= (N_JOB) this.getBrowserContent().getDataObjectSet().getDataObject();
		String user		=	Application.getApp().getLoginInfo().getUserID();
		
		if(			masterData !=null && masterData.getUSER_UP()!=null 
				&&  masterData.getUSER_UP().equals(user)){
			
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, true);
		}else{
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
			this.getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		}
		
		
		super.doUIRefresh();
	}
	/* 
	 * 調整UI Layout
	 */

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		super.doRefresh();
	}
	
	protected void doLayout() {
		super.doLayout();
	}


	public boolean doSave() {
		// TODO Auto-generated method stub
			if(super.doSave()){
				System.out.println(_dc.getFlagNew());
				if(_dc.getFlagNew() == true){
					N_JOB	jobData	= _dc.getJob();
					String[] arrKind 	= new String[]{"B","C","D"}; 
					String 		key 	=	jobData.getID_KEY();
					DataObjectSet dataSet = _dc.getDataObjectSet();
					for(int i = 0 ; i< arrKind.length ; i++){
						
						N_JOB 	objData	= new N_JOB();
								objData	= jobData;
								objData.setID_KEY(key.replace("A", arrKind[i]));
								objData.setKIND_JOB(arrKind[i]);
								dataSet.add(objData);
							
								
					}
					dataSet.applyUpdate();
				}
				return true;
				
			}
			return false;
			
	}
	

	public boolean doDelete() {
		boolean rs = false;
		N_JOB jobData 			= (N_JOB) this.getBrowserContent().getDataObjectSet().getDataObject();
		
		if(super.doDelete()){
			
			IGenericDAO<N_JOB, String> objDao = Application.getApp().getDao(N_JOB.class);
			DetachedCriteria	objDC	= DetachedCriteria.forClass(N_JOB.class);
								objDC.add(Restrictions.eq("CODE_JOB", jobData.getCODE_JOB()));
								objDC.add(Restrictions.ne("ID_KEY",	 jobData.getID_KEY()));
								
			List<N_JOB> dataList	= objDao.findByCriteria(10, objDC);
			
			objDao.deleteAll(dataList);
			
			rs = true;
		}
		this.doDataRefresh();
		
		return rs;
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
		return null;
		//4.<資料瀏覽欄位>
//        return new String[] {"NAME_JOB", "CODE_JOB", "KIND_JOB", "IN_FACT", "MONEY", "USER_UP", "DATE_UP"};
	}
	
}
