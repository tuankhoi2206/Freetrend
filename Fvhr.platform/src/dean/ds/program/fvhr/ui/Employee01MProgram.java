package ds.program.fvhr.ui;

import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.domain.pk.N_GET_DATAPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import fv.util.ApplicationHelper;
import fv.util.DbUtils;

/**
 * Employee * 
 */
public class Employee01MProgram extends MaintainSProgram {

	//	private LT2JdbcDAO lt2DAO;
	private int dataMode;
	private DscField txtEmpsn;
	private Button btnCheckId;
	private IDCheckForm checkForm;

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new Employee01MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		//1.<進階可查詢欄位定義>

		//2.<固定條件>

		//3.<預設查詢條件>

		//4.<作業功能>
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, true);

		btnCheckId = new Button();
		btnCheckId.setStyleName("Default.ToolbarButton");
		btnCheckId.setText("Kiem Tra CMND");
		btnCheckId.setIcon(new ResourceImageReference("dsc/echo2app/resource/image/btnFSave.gif"));
		btnCheckId.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkForm = new IDCheckForm(getMasterDataContent());
				Application.getApp().getDefaultWindow().getContent().add(checkForm);
			}
		});
		getMasterToolbar().add(btnCheckId);

		txtEmpsn = new DscField();
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				ProgramCondition pc = new ProgramCondition("o.EMPSN=?", new Object[]{txtEmpsn.getText()});
				setQueryCondition(pc);
				refresh();
				if (getMasterDataContent().getLCB().equals("")) {
					doRefresh();//FIXME not full refresh
					logger.debug("....refresh");
				}
				
				txtEmpsn.requestFocus();
			}
		});
		getMasterToolbar().add(txtEmpsn);


		//5.設定table的最大筆數及每頁筆數
		ProgramCondition basePC = new ProgramCondition("o.USER_MANAGE_ID in " + DbUtils.parseInStringParamValues(ApplicationHelper.getRightList()) + "");
		setBaseCondition(basePC);

		ProgramCondition pc = new ProgramCondition("1<>1");
		setQueryCondition(pc);			
	
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
		return new Employee01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();		
	}

	/**
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
		return new String[]{"EMPSN", "FNAME", "LNAME", "ID_NO", "CITY", "SEX", "WORK_STATUS"};
	}

	@Override
	public boolean doNew() {
		//check LT2 connection/service
		if (!getMasterDataContent().initPolicy()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getMasterDataContent().getErrorMessage());
			return false;
		}
		if (super.doNew()){
			dataMode = IProgram.DATAMODE_NEW;
			return true;
		}
		return false;
	}

	@Override
	public boolean doEdit() {
		//check LT2
		if (!getMasterDataContent().initPolicy()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, getMasterDataContent().getErrorMessage());
			return false;
		}
		if (super.doEdit()){
			dataMode = IProgram.DATAMODE_EDIT;
			return true;
		}
		return false;
	}

	@Override
	public boolean doDelete() {
		if (!canDelete()){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR, "Không thể xóa. Nhân viên này đã có dữ liệu quét thẻ");
			return false;
		}
		N_EMPLOYEE data =(N_EMPLOYEE) getMasterDataContent().getDataObject();
		getMasterDataContent().deleteOtherInfo(data.getEMPSN(),data.getEMPCN());
		return super.doDelete();
	}
	
	private boolean canDelete(){
		N_EMPLOYEE data = (N_EMPLOYEE) getMasterDataContent().getDataObject();
		IGenericDAO<N_GET_DATA, N_GET_DATAPk> dao = Application.getApp().getDao(N_GET_DATA.class);
		List<N_GET_DATA> list = dao.find(1, "from N_GET_DATA t where t.EMPSN=?", data.getEMPSN());
		if (list.size()>0){
			return false;
		}
		return true;
	}
	
	/*private boolean isLT2Connected(){
		if (lt2DAO==null) lt2DAO = new LT2JdbcDAO();
		Connection con = null;
		try {
			con = lt2DAO.getDataSource().getConnection();
			if (con!=null) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con);
		}
		return false;
	}

	public LT2JdbcDAO getLT2DAO(){
		return lt2DAO;
	}*/

	@Override
	public Employee01MDataContent getMasterDataContent() {
		return (Employee01MDataContent) super.getMasterDataContent();
	}

	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			N_EMPLOYEE e = (N_EMPLOYEE) getMasterDataContent().getDataObject();
			getMasterDataContent().loadCurrentInfo(e);
			return true;
		}
		return false;
	}

	@Override
	public boolean doSave() {
		return super.doSave();
	};
	
	@Override
	protected void saveOK() {
		super.saveOK();
		if (dataMode==IProgram.DATAMODE_NEW){
			getMasterDataContent().saveOtherInfo();
			dataMode=IProgram.DATAMODE_BROWSER;
		}
		N_EMPLOYEE emp = (N_EMPLOYEE) getMasterDataContent().getDataObject();
		ProgramCondition pc = new ProgramCondition("o.EMPSN=?", new Object[]{emp.getEMPSN()});
		setQueryCondition(pc);
	}

	public DscField getSearchField(){
		return txtEmpsn;
	}

}
