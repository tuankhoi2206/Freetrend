package ds.program.fvhr.tien.ui;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import ds.program.fvhr.domain.ATTLOCK;
import ds.program.fvhr.domain.N_CHANGE_HOSPITAL;
import ds.program.fvhr.domain.N_DATA_MAIN;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_FACTORY;
import ds.program.fvhr.domain.N_FACT_TRANSFER_LOCK;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.ui.workpoints.NGetData00MProgram;
import ds.program.fvhr.util.OBJ_EMPSN;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;

/**
 * N_GET_DATAMAIN * 
 */
public class N_GET_DATAMAIN00MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	private String user_up = Application.getApp().getLoginInfo().getUserID();
	OBJ_EMPSN objEmpsn=new OBJ_EMPSN();
	private int intTimeout;
	private int intTimein;
	private  String strTimeint;
    private String strTimeout;
	private String strEMPSN;	
	private WorkpointsDAO dao;
	private DefaultProgram program;
	private N_GET_DATAMAIN00MQuery query;
	
    public String getStrTimeint() {
		return strTimeint;
	}
	public void setStrTimeint(String strTimeint) {
		this.strTimeint = strTimeint;
	}
	public String getStrTimeout() {
		return strTimeout;
	}
	public void setStrTimeout(String strTimeout) {
		this.strTimeout = strTimeout;
	}
	public String getStrEMPSN() {
		return strEMPSN;
	}
	public void setStrEMPSN(String strEMPSN) {
		this.strEMPSN = strEMPSN;
	}	
	public WorkpointsDAO getJdbcDAO(){
		if (dao == null)
			dao = new WorkpointsDAO();
		return this.dao;
	}
	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_GET_DATAMAIN00MDataContent());
	}
	public void dogetMinMaxtime(String strTimes)
	{
		int[] inttemp=new int[100];
		int i,j,cdai,tem;
		 String so="";
		cdai=strTimes.length();
		for(i=0;i<cdai/4;i++)
		{   
			 so=strTimes.substring(i*4,(i+1)*4);
			 
			 inttemp[i]=Integer.parseInt(so);
			
		}
		// sap xep theo kieu tang dan
		
	
	   for(int a=0;a<cdai-1;a++)
		    {
		    	for(int b=0;b<i-1;b++)
		    	{
		    		if(inttemp[a]>inttemp[b])
		    		{
		    			  tem=inttemp[a];
			    	      inttemp[a]=inttemp[b];
			    	      inttemp[b]=tem;
		    		}
		    		  
		    	}
		    }
	   
	   intTimeout =inttemp[0];
	   intTimein=inttemp[(cdai/4)-1];
	   this.setStrTimeint(String.valueOf( intTimein));
	   this.setStrTimeout(String.valueOf( intTimeout));
	   if(strTimeint.length()<4)
     	{
     		this.setStrTimeint("0"+strTimeint);
     	}
     	if(strTimeout.length()<4)
     	{
     		this.setStrTimeout("0"+strTimeout);
     	}
	}
	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		 getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
	
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

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
		if (query==null)
		query = new N_GET_DATAMAIN00MQuery();
		return query;
	}
	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();	
		this.getBrowserContent().setPageSize(20);
		this.getBrowserContent().setMaxSize(100000);
		Button 	importButton = new Button();
				importButton.setText("Thay Đổi Theo Danh Sách");
				importButton.setStyleName("Default.ToolbarButton");
				importButton.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent e) {
						
						N_Emp_Edit_Data objEmp_Edit_data = new N_Emp_Edit_Data();
						Application.getApp().getDefaultWindow().getContent().add(objEmp_Edit_data);
						
					}
				});
				this.getMasterToolbar().add(importButton);
		
	}
	
	public  void  doGetempsn(String empcn)
	{
		IGenericDAO<N_EMP_ICCARD, String> objDao=Application.getApp().getDao(N_EMP_ICCARD.class);
		List<N_EMP_ICCARD> listIcCard=objDao.find(1," from N_EMP_ICCARD where EMPCN=? AND USE_STATUS='1'", empcn);
		N_EMP_ICCARD objN_EMP_ICCARD=null;		
		if(listIcCard.size()>0)
		{
			objN_EMP_ICCARD=listIcCard.get(0);
			this.setStrEMPSN(objN_EMP_ICCARD.getEMPSN());			
		}
	}
	@Override
	public boolean doSave()
	{
		N_DATA_MAIN data_old=(N_DATA_MAIN)this.getBrowserContent().getDataObjectSet().getDataObject();
		String times_old="";
		times_old=data_old.getTIMES();		
		if( super.doSave())
		{			
			N_DATA_MAIN data_new=(N_DATA_MAIN)this.getBrowserContent().getDataObjectSet().getDataObject();
			String strNote="Gio cu:"+times_old + "Gio moi:"+data_new.getTIMES();
			// lưu vào bảng Action_data_daily
			this.doGetempsn(data_new.getEMPCN());
			objEmpsn.Insert_N_Action_Daily(user_up, "N_DATA_MAIN", "UPDATE", this.getStrEMPSN(), strNote);
			changeLock();
		}
		return true;
	}		
	
	protected void changeLock()
	{
		 SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
		Date date_now=new Date();
		String strdate_now=sp.format(date_now).substring(3, 10);
		System.out.println(strdate_now);
		N_DATA_MAIN data_new=(N_DATA_MAIN)this.getBrowserContent().getDataObjectSet().getDataObject();	
		String strDate_Data=data_new.getDATES().substring(3, 10);
		this.doGetempsn(data_new.getEMPCN());
		String strEmpsn=this.getStrEMPSN();
		String strMonth=strDate_Data.substring(0, 2);
		String strYear=strDate_Data.substring(3, 7);
		String dept="";
		String dept_temp="";
		
		
		if(!strdate_now.equals("")&& !strDate_Data.equals(""))
		{
			if(strDate_Data.equals(strdate_now))
			{
				// mo khoa N_GET_DATA
				
				/*IGenericDAO<N_GET_DATA, String> objdaoN_get_data=Application.getApp().getDao(N_GET_DATA.class);
				List<N_GET_DATA> listN_get_data=objdaoN_get_data.find(1, "from N_GET_DATA where EMPSN=? and MONTHS=? and YEARS=? and LOCKED=1",new Object[]{strEmpsn,strMonth,strYear});
				IGenericDAO<N_EMPLOYEE, String> objdaoEmployee =Application.getApp().getDao(N_EMPLOYEE.class);
				List<N_EMPLOYEE> listEmployee =objdaoEmployee.find(1,"from N_EMPLOYEE where EMPSN=?" , strEmpsn);
				IGenericDAO<N_FACT_TRANSFER_LOCK,String> objdaoN_fact_trans_lock=Application.getApp().getDao(N_FACT_TRANSFER_LOCK.class);				
				IGenericDAO<ATTLOCK, String>  objdaoAttLock=Application.getApp().getDao(ATTLOCK.class);
				List<ATTLOCK> listAttlock=objdaoAttLock.find(1, "from ATTLOCK where TABNAME=? and TABLOCK='Y'","ATT"+strYear+strMonth);
				N_FACT_TRANSFER_LOCK objFact_lock_1=null;
				N_EMPLOYEE objEmployee=null;
				N_GET_DATA obj_N_get_data=null;
				ATTLOCK objAttlock=null;
				N_FACT_TRANSFER_LOCK objFact_lock_2=null;
				if(listN_get_data.size()>0)
				{
					obj_N_get_data=listN_get_data.get(0);
					obj_N_get_data.setLOCKED(BigDecimal.valueOf(0));
					objdaoN_get_data.update(obj_N_get_data);
				}
				// mo khoa bang n_fact_transfer_lock			
				if(listEmployee.size()>0)
				{
					 objEmployee=listEmployee.get(0);
					 dept=objEmployee.getDEPSN();
					 dept_temp=objEmployee.getDEPSN_TEMP();					
				}
				List<N_FACT_TRANSFER_LOCK> list_fact_lock=objdaoN_fact_trans_lock.find(2, " from N_FACT_TRANSFER_LOCK where DEPSN in ('"+dept+"','"+dept_temp+"') and YEAR=? and MONTH=? and LOCKED=1", new Object[]{strYear,strMonth});
				if(list_fact_lock.size()>0)
				{	
					objFact_lock_1=list_fact_lock.get(0);
					objFact_lock_1.setLOCKED(0L);
					objdaoN_fact_trans_lock.update(objFact_lock_1);	
					if(!dept.equals(dept_temp))
					{
					    objFact_lock_2=list_fact_lock.get(1);						
						objFact_lock_2.setLOCKED(0L);											
						objdaoN_fact_trans_lock.update(objFact_lock_2);
					}
				}				
				// mo khoa bang luong				
				if(listAttlock.size()>0)
				{
					objAttlock=listAttlock.get(0);
					objAttlock.setTABLOCK("N");
					objdaoAttLock.update(objAttlock);
				}*/
				// xu ly du lieu cua thang
				getJdbcDAO().processData(strEmpsn,data_new.getDATES(), "mm");
				// khoa du lieu bang n_get_data
				/*if(obj_N_get_data!=null)
				{
					obj_N_get_data.setLOCKED(BigDecimal.valueOf(1));
					objdaoN_get_data.update(obj_N_get_data);	
				}			
				// khoa du lieu bang N_FACT_TRANSFER_LOCK
				if(objFact_lock_1!=null)
				{
					objFact_lock_1.setLOCKED(1L);
					objdaoN_fact_trans_lock.update(objFact_lock_1);						
				}				
				if(objFact_lock_2!=null)
				{
					objFact_lock_2.setLOCKED(1L);											
					objdaoN_fact_trans_lock.update(objFact_lock_2);
					
				}
				if(objAttlock!=null)
				{
					objAttlock.setTABLOCK("Y");
					objdaoAttLock.update(objAttlock);
				}*/
				
				
				
			}
		}
		
		
		
	}
		
		
			

		

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		//6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
		  return new String[]{"EMPCN","DATES","TIMES"};
	      
	}
}
