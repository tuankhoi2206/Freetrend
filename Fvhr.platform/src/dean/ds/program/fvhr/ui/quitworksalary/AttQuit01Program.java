package ds.program.fvhr.ui.quitworksalary;

import java.util.Calendar;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import org.apache.log4j.Logger;

import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.quitsalary.AttQuitDAO;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.ATTQUIT200900;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.pk.ATTQUIT200900Pk;
import ds.program.fvhr.ui.quitworksalary.validator.AttQuitValidator;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscPageNavigator;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.FvLogger;
import fv.util.GenericJdbcDAO;
import fv.util.Vni2Uni;

public class AttQuit01Program extends MaintainSProgram{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AttQuit01Program.class);

	private static final long serialVersionUID = 1L;
	private GenericJdbcDAO<ATTQUIT200900, ATTQUIT200900Pk> attDao;
	private AttQuitDAO dao;
	private AttQuitValidator validator;
	private int mode;

	private DscField txtEmpsn;

	private Button btnExcel;

	private AttQuitQuery query;

	private Button btnUpdateOrder;

	private Button btnCongNgayCong;

	private int view;

	@Override
	protected void createDataContent() {
		setMasterDataContent(new AttQuitDataContent());
	}
	
	@Override
	protected QueryPane createNormalQuery() {
		if (query==null)
			query = new AttQuitQuery();
		return query;
	}
	
	@Override
	public AttQuitDataContent getMasterDataContent() {
		return (AttQuitDataContent) super.getMasterDataContent();
	}
	
	@Override
	public int refresh() {
		if (!FvGenericDAO.getInstanse().checkTableExist(getMasterDataContent().getTableName())){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có bảng lương tháng cần tìm. Liên hệ phòng vi tính");
			return IProgram.RET_DBERROR;
		}
		int ret = super.refresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()<=0){
			btnUpdateOrder.setEnabled(false);
			btnCongNgayCong.setEnabled(false);
		}else{
			btnUpdateOrder.setEnabled(true);
			if (view==1){
				if (getDataMode()==IProgram.DATAMODE_EDIT||getDataMode()==IProgram.DATAMODE_NEW){
					btnCongNgayCong.setEnabled(false);
				}else{
					btnCongNgayCong.setEnabled(true);
				}
			}
		}
		return ret;
	}
	
	@Override
	protected void doRefresh() {
		if (!FvGenericDAO.getInstanse().checkTableExist(getMasterDataContent().getTableName())){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có bảng lương tháng cần tìm. Liên hệ phòng vi tính");
			return;
		}
		super.doRefresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()<=0){
			btnUpdateOrder.setEnabled(false);
			btnCongNgayCong.setEnabled(false);
		}else{
			btnUpdateOrder.setEnabled(true);
			if (view==1){
				if (getDataMode()==IProgram.DATAMODE_EDIT||getDataMode()==IProgram.DATAMODE_NEW){
					btnCongNgayCong.setEnabled(false);
				}else{
					btnCongNgayCong.setEnabled(true);
				}
			}
		}
	}
	
	public AttQuitValidator getValidator() {
		return validator;
	}
	
	public AttQuitDAO getDao() {
		return dao;
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getMasterToolbar().setbtnConfirmVisible(false);
		getMasterToolbar().setbtnCancelConfirmVisible(false);
		getMasterToolbar().setbtnEmailVisible(false);
		getMasterToolbar().setbtnExportVisible(false);
		dao = new AttQuitDAO("ATTENDANTDB_QUIT",ATTENDANTDB_QUIT.class);
		dao.setMonth(getMasterDataContent().getSelectedMonth());
		dao.setYear(getMasterDataContent().getSelectedYear());
		validator = new AttQuitValidator(dao);
		attDao = new GenericJdbcDAO<ATTQUIT200900, ATTQUIT200900Pk>("ATTQUIT"+getMasterDataContent().getSelectedYear()+getMasterDataContent().getSelectedMonth(), ATTQUIT200900.class);
		attDao.setVniColumns(new String[]{"EMPNA"});
		getMasterDataContent().setVniColumns(new String[]{"EMPNA","NOTE","NOTE_BH"});
		
		btnExcel = new Button();
		btnExcel.setToolTipText("Import excel");
		btnExcel.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		btnExcel.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcelD.gif"));
		btnExcel.setStyleName("Default.ToolbarButton");
		btnExcel.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				doImportExcel();
			}
		});
		getMasterToolbar().add(btnExcel);
		
		//TODO add order button
		btnUpdateOrder = new Button();
		btnUpdateOrder.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/add_order.gif"));
		btnUpdateOrder.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/add_orderD.gif"));
		btnUpdateOrder.setStyleName("Default.ToolbarButton");
		btnUpdateOrder.setEnabled(false);
		btnUpdateOrder.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog dlg = new MessageDialog("Cập nhật số thứ tự","Cập nhật số thứ tự theo danh sách đang sắp xếp", MessageDialog.CONTROLS_YES_NO);
				dlg.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
							doUpdateOrder();
						}
					}
				});
			}
		});
		getMasterToolbar().add(btnUpdateOrder);
		
		btnCongNgayCong = new Button(" + ");
		btnCongNgayCong.setToolTipText("Cộng ngày công 2 tháng");
		btnCongNgayCong.setStyleName("Default.ToolbarButton");
		btnCongNgayCong.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getApp().getDefaultWindow().getContent().add(new AttQuitAppendData2MonthPane(AttQuit01Program.this));
			}
		});
		btnCongNgayCong.setEnabled(false);
		getMasterToolbar().add(btnCongNgayCong);
		
		DscPageNavigator nav = getBrowserContent().getBrowserNav();
		nav.setRowsPerPage(20);
		nav.reset();
		
		getMasterDataContent().getNavigator().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int recNo = getMasterDataContent().getNavigator().getCurrentRecord();
				if (e.getActionCommand().equals("first")){
					recNo=1;
				}else if (e.getActionCommand().equals("previous")){
					recNo--;
				}else if (e.getActionCommand().equals("next")){
					recNo++;
				}else if (e.getActionCommand().equals("last")){
					recNo = getMasterDataContent().getNavigator().getTotalRecord();
				}else if (e.getActionCommand().equals("recnumber")){
					DscField txt = (DscField) e.getSource();
					recNo = Integer.valueOf(txt.getText());
					if (recNo<=0) {
						recNo=1;
					}
					int total = getMasterDataContent().getDataObjectSet().getRecordCount();
					if (recNo>total){
						recNo=total;
					}
				}
				getBrowserContent().setCurrentSelectRowNo(recNo-1);
				doDataContentRefresh(recNo-1);
			}
		});
		txtEmpsn = new DscField();
		txtEmpsn.setWidth(new Extent(80));
		txtEmpsn.setToolTipText("Nhập số thẻ và nhấn enter để tìm nhanh");
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] o = {{"EMPSN", "~=", txtEmpsn.getText()}};
				int y = getMasterDataContent().getDataObjectSet().locate(o);
				if (y>=0){
					getBrowserContent().setCurrentSelectRowNo(y);
					doDataContentRefresh(y);
				}else{
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không tìm thấy");
				}
			}
		});
		getMasterDataContent().getNavigator().add(txtEmpsn);
		
		ProgramCondition pc = new ProgramCondition("1<>1");
		setQueryCondition(pc);
		return ret;
	}
	
	protected void doImportExcel() {
		AttQuitExcelImportPane pane = new AttQuitExcelImportPane(this);
		Application.getApp().getDefaultWindow().getContent().add(pane);
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		return new String[]{"EMPSN", "EMPNA", "DEPSN", "DOT_TV", "DEPT_KT"};
	}
	
	@Override
	protected boolean doBrowserContentRefresh() {
		if (super.doBrowserContentRefresh()){
			getMasterDataContent().getNavigator().setTotalRow(getMasterDataContent().getDataObjectSet().getRecordCount());
			getMasterDataContent().getNavigator().setCurrentRecord(getMasterDataContent().getDataObjectSet().getRecNo());
			getMasterDataContent().getNavigator().reset();
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getMasterDataContent().getDataObject();
			ATTQUIT200900Pk pk = new ATTQUIT200900Pk(data.getEMPSN(), data.getDOT_TV());
			
			ATTQUIT200900 atte = attDao.findById(pk);
			if (atte!=null){
				getMasterDataContent().getAttQuitBinder().bindDataToFields(atte);
				getMasterDataContent().setUserDataObject(atte);
			}else{
				getMasterDataContent().getAttQuitBinder().bindDataToFields(new ATTQUIT200900());
				getMasterDataContent().setUserDataObject(null);
			}
			getMasterDataContent().getNavigator().setCurrentRecord(recNo+1);
			getMasterDataContent().getNavigator().reset();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doNew() {
		if (super.doNew()){
			mode = IProgram.DATAMODE_NEW;
			getMasterDataContent().setUserDataObject(new ATTQUIT200900());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doDelete() {
		int recNo = getMasterDataContent().getDataObjectSet().getRecNo();
		doDataContentRefresh(recNo);
		if (super.doDelete()){
			attDao.delete(getMasterDataContent().getUserDataObject());
			return true;
		}
		return false;
	}
	
	public void doCheckAndCreateTable(String month, String year){
		String tableName = "ATTENDANTDB_QUIT_"+month+year;
		boolean e = FvGenericDAO.getInstanse().checkTableExist(tableName);
		if (!e){
			String alter = 
				"alter table " + tableName +
				"  add constraint "+tableName+"_PK primary key (EMPSN, DOT_TV)\n" + 
				"  using index\n" + 
				"  tablespace VFT\n" + 
				"  pctfree 10\n" + 
				"  initrans 2\n" + 
				"  maxtrans 255\n" + 
				"  storage\n" + 
				"  (\n" + 
				"    initial 64K\n" + 
				"    minextents 1\n" + 
				"    maxextents unlimited\n" + 
				"  )";
			FvGenericDAO.getInstanse().createTableFromOtherTable(tableName, "ATTENDANTDB_QUIT", alter);
			if (logger.isDebugEnabled()){
				logger.debug("create table: " + tableName);
			}
		}
		String attTableName = "ATTQUIT"+year+month;
		boolean ae = FvGenericDAO.getInstanse().checkTableExist(attTableName);
		if (!ae){
			String alter = 
				"alter table " + attTableName +
				"  add constraint "+attTableName+"_PK primary key (EMPSN, DOT_TV)\n" + 
				"  using index\n" + 
				"  tablespace VFT\n" + 
				"  pctfree 10\n" + 
				"  initrans 2\n" + 
				"  maxtrans 255\n" + 
				"  storage\n" + 
				"  (\n" + 
				"    initial 64K\n" + 
				"    minextents 1\n" + 
				"    maxextents unlimited\n" + 
				"  )";
			FvGenericDAO.getInstanse().createTableFromOtherTable(attTableName, "ATTQUIT200900", alter);
			if (logger.isDebugEnabled()){
				logger.debug("create table: " + attTableName);
			}
		}
	}
	
	@Override
	protected void saveUIToDataObject() {
		super.saveUIToDataObject();
		updateDAO(getMasterDataContent().getSelectedMonth(), getMasterDataContent().getSelectedYear());
	}
	
	@Override
	protected void beforeSaveToDataObjectSet() {
		super.beforeSaveToDataObjectSet();
//		check table already
		if (getDataMode()==IProgram.DATAMODE_NEW){
			doCheckAndCreateTable(getMasterDataContent().getSelectedMonth(), getMasterDataContent().getSelectedYear());
			if(logger.isDebugEnabled())
				logger.debug("beforeSaveToDataObjectSet " + dao.getMonth() + "/" + dao.getYear());
		}
	}
	
	@Override
	protected void saveOK() {
		super.saveOK();
		ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) getMasterDataContent().getDataObject();
		AttQuitInitData initAtt = new AttQuitInitData();
		initAtt.setEmpsn(data.getEMPSN());
		initAtt.setEmpna(Vni2Uni.convertToVNI(data.getEMPNA()));
		initAtt.setHired(data.getHIRED());
		initAtt.setDateOff(data.getDATE_OFF());
		initAtt.setPossn(data.getPOSSN());
		initAtt.setDotTv(data.getDOT_TV());
		initAtt.setDeptKt(data.getDEPT_KT());
		initAtt.setDepsn(data.getDEPSN());
		initAtt.setNoteBh(data.getNOTE_BH());
		dao.saveInitAttData(initAtt);
		dao.doCalculate(data.getEMPSN(), data.getDOT_TV(), getMasterDataContent().getSelectedMonth(), getMasterDataContent().getSelectedYear(),data.getCLASS());
		/////save action///////
		N_ACTION_DAILY action = new N_ACTION_DAILY();		
		action.setACTIONNAME("CALC");
		action.setEMPSN(data.getEMPSN());
		action.setTABLENAME(attDao.getTable());
		FvLogger.log(action);
		///////////////////////
		int recNo = getMasterDataContent().getDataObjectSet().getRecNo();
		if (mode==IProgram.DATAMODE_NEW){
			ProgramCondition pc = new ProgramCondition("o.EMPSN=?", new Object[]{data.getEMPSN()});
			setQueryCondition(pc);
			doDataRefresh();
		}else{
			doDataContentRefresh(recNo);
		}
		mode=IProgram.DATAMODE_NONE;
	}

	public void updateDAO(String month, String year) {
		getMasterDataContent().updateDAO(month, year);
		dao.setMonth(month);
		dao.setYear(year);
		attDao.setTable("ATTQUIT"+year+month);
	}
	
	public void showSearchPane(int monthIndex, int year, Calendar date, boolean tv){
		if (query==null) query = new AttQuitQuery();
		query.updateModel(monthIndex, year, date, tv);
		doQueryNormal();		
	}
	
	public void doUpdateOrder(){
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		for (int i=0;i<ds.getRecordCount();i++){
			ATTENDANTDB_QUIT data = (ATTENDANTDB_QUIT) ds.getDataObject(i);
			getDao().updateOrder(data.getEMPSN(), data.getDOT_TV(), i+1);
		}
	}
	
	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		if (getDataMode()==IProgram.DATAMODE_BROWSER||getDataMode()==IProgram.DATAMODE_NONE){
			getMasterDataContent().getNavigator().setEnabled(true);
		}else{
			getMasterDataContent().getNavigator().setEnabled(false);
		}
	}
		
	@Override
	protected void switchContent(int view) {
		super.switchContent(view);
		this.view=view;
		if (view==0){
			btnCongNgayCong.setEnabled(false);
		}else{
			if (getDataMode()==IProgram.DATAMODE_EDIT||getDataMode()==IProgram.DATAMODE_NEW){
				btnCongNgayCong.setEnabled(false);
			}else{
				btnCongNgayCong.setEnabled(true);
			}
		}
	}
}
