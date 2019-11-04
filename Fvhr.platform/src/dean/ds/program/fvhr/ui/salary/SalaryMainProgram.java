package ds.program.fvhr.ui.salary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscPageNavigator;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.program.IProgram;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.dao.salary.SalaryDAO2;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.domain.ATTENDANTDB;
import ds.program.fvhr.domain.ATTLOCK;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMP_TAXCODE;
import ds.program.fvhr.domain.pk.N_EMP_TAXCODEPk;
import ds.program.fvhr.ui.SExport2Excel;
import ds.program.fvhr.ui.salary.SalaryMainPane;
import fv.components.CustomQueryPane;
import fv.util.FvLogger;
import fv.util.GenericJdbcDAO;
import fv.util.ReportUtils;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import dsc.echo2app.program.MasterToolbar;
/**
 * Salary management program
 * @author Hieu
 * FIXME: must be extended from MaintainSProgram
 */
public class SalaryMainProgram extends DefaultProgram {

	private ResourceBundle resourceBundle;
	private SplitPane mainSplitPane;
	private SalaryMainPane mainPane;
	private String month;
	private String year;
	private GenericJdbcDAO<ATTENDANTDB, String> atteDAO;
	private MasterToolbar masterToolbar;
	private SalaryQuery query;
	private boolean editable;
	private ATTENDANTDB atte;
	private String[] printParams;
	private Button btnPhieuLuong;
	private Button btnBangTong;
	private Button btnImportExcel;
	private Button btnPhieuLuong2;
	private Button btnExcel;
	private Button btnKynhanluong;
	BigDecimal bigSum;
	private String id_dept="";
	/**
	 * Creates a new <code>SalaryMainProgram</code>.
	 */
	public SalaryMainProgram() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	public AwesomeSalaryDataContent getMasterDataContent(){
		return mainPane.getDataContent();
	}
	
	public BrowserContent getBrowserContent(){
		return mainPane.getBrowserContent();
	}
	
	
	protected int doInit() {
		int ret = super.doInit();
		masterToolbar.setbtnBrowserVisible(false);
		masterToolbar.setbtnContentVisible(false);
		masterToolbar.setbtnNewVisible(false);
		masterToolbar.setbtnDeleteVisible(false);
		masterToolbar.setbtnConfirmVisible(false);
		masterToolbar.setbtnCancelConfirmVisible(false);
		masterToolbar.setbtnEmailVisible(false);
		masterToolbar.setbtnExportVisible(false);
		masterToolbar.addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		masterToolbar.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				masterToolbarActionPerformed(e);
			}
		});
		atteDAO = new GenericJdbcDAO<ATTENDANTDB, String>("ATTENDANTDB", ATTENDANTDB.class);
		windowPane.setWidth(new Extent(1054));
		getMasterDataContent().init(this);
		getBrowserContent().init(getMasterDataContent().getDataObjectSet(), new String[]{"EMPSN","EMPNA"});
		getMasterDataContent().setVniColumns(new String[]{"EMPNA"});
		DscPageNavigator nav = getBrowserContent().getBrowserNav();
		nav.getSearchTextField().setWidth(new Extent(70));		
		nav.setSearchButtonVisible(false);
		nav.setRowsPerPage(20);
		nav.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				getBrowserContent().setSelectedIndex(0, true);
				doMasterDataSelectChange();
			}
		});
		
		nav.setSearchFoundAction(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				doMasterDataSelectChange();
			}
		});
		
		nav.reset();
		nav.setInsets(new Insets(new Extent(0), new Extent(3)));
		/////////////////
		setMonth(getMasterDataContent().getMonth());
		setYear(getMasterDataContent().getYear());//default month year
		/////////////////
		getBrowserContent().getBrowserTable().addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				doMasterDataSelectChange();
			}
		});
		////Condition
		ProgramCondition pc = new ProgramCondition("1<>1");
		setQueryCondition(pc);
		
		////toolbar button
		btnImportExcel = new Button();
		btnImportExcel.setStyleName("Default.ToolbarButton");
		btnImportExcel.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_import.png"));
		btnImportExcel.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_importD.png"));
		btnImportExcel.setToolTipText("Cập nhật dữ liệu lương từ Excel");
		btnImportExcel.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				//SalaryExcelImportPane pane = new SalaryExcelImportPane();
				//Application.getApp().getDefaultWindow().getContent().add(pane);
			}
		});
		masterToolbar.add(btnImportExcel);
		btnBangTong = new Button();
		btnBangTong.setEnabled(false);
		btnBangTong.setStyleName("Default.ToolbarButton");
		btnBangTong.setToolTipText("In danh sách bảng tổng");
		btnBangTong.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_export.png"));
		btnBangTong.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_exportD.png"));
		btnBangTong.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				MessageDialog dlg = new MessageDialog("In danh sách bảng tổng", "Cập nhật số thứ tự theo danh sách hiện tại?", MessageDialog.CONTROLS_YES_NO);
				dlg.addActionListener(new ActionListener(){
					private static final long serialVersionUID = 1L;
					
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
							doPrintSalaryTable(true);
						}else{
							doPrintSalaryTable(false);
						}
					}
				});
			}
		});
		masterToolbar.add(btnBangTong);
		
		btnPhieuLuong = new Button();
		btnPhieuLuong.setEnabled(false);
		btnPhieuLuong.setToolTipText("Phiếu lương (Mẫu 1)");
		btnPhieuLuong.setStyleName("Default.ToolbarButton");
		btnPhieuLuong.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrint.gif"));
		btnPhieuLuong.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrintD.gif"));
		btnPhieuLuong.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				doPrintSalary(1);
			}
		});
		masterToolbar.add(btnPhieuLuong);
		
		btnPhieuLuong2 = new Button();
		btnPhieuLuong2.setEnabled(false);
		btnPhieuLuong2.setToolTipText("Phiếu lương (Mẫu 2)");
		btnPhieuLuong2.setStyleName("Default.ToolbarButton");
		btnPhieuLuong2.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printer.png"));
		btnPhieuLuong2.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printerD.png"));
		btnPhieuLuong2.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				doPrintSalary(2);
			}
		});
		masterToolbar.add(btnPhieuLuong2);
		
		btnExcel = new Button();
		btnExcel.setToolTipText("Xuất dữ liệu ra file Excel");
		btnExcel.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		btnExcel.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcelD.gif"));
		btnExcel.setStyleName("Default.ToolbarButton");
		btnExcel.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				doExportToExcel();
			}			
		});
		masterToolbar.add(btnExcel);
		////
		btnKynhanluong=new Button();
		btnKynhanluong.setText("Ký Nhận Lương");
		btnKynhanluong.setToolTipText("Ký Nhận Lương");
		btnKynhanluong.setStyleName("Default.ToolbarButton");
		btnKynhanluong.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				dokynhanluong();
			}			
		});
		masterToolbar.add(btnKynhanluong);
		setDataMode(IProgram.DATAMODE_NONE);
		return ret;
	}
	protected void dokynhanluong() {
		List<ATT200000> list = preparePhantomLancerDatasource();
		if (list==null||list.size()==0) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu");
			return;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			String file = "fvhr/Kynhanluong.jrxml";		
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder(file));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("bigSum", bigSum);
			params.put("id_dept", id_dept);
			params.put("month", getMonth()+"/"+getYear());
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "DanhSachKyNhanLuong");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	protected void doExportToExcel() {
		if (!FvGenericDAO.getInstanse().checkTableExist("ATT"+getYear()+getMonth())){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không tồn tại bảng lương tháng "+getMonth()+"/"+getYear());
			return;
		}
		
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		
		if (ds.getRecordCount()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		SExport2Excel.run(false, ds, new SalaryDAO(getMonth(), getYear()), getMonth(), getYear());
	}

	protected void doPrintSalary(int template) {
		List<ATT200000> list = preparePhantomLancerDatasource();
		if (list==null||list.size()==0) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu");
			return;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			String file = "fvhr/phieuluong.jrxml";
			if (template==2) file="fvhr/phieuluong2.jrxml";
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder(file));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			
			Map<String, Object> params = new HashMap<String, Object>();			
			params.put("month", getMonth()+"/"+getYear());
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "PhieuLuong");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private List<ATT200000> preparePhantomLancerDatasource() {
		bigSum=new BigDecimal(0);
		ProgramCondition pc = getQueryCondition();
		if (pc==null) return null;
		GenericJdbcDAO dao = (GenericJdbcDAO) getMasterDataContent().getDataObjectSet().getDao();
		List<ATT200000> list = dao.fetch(-1, "o", pc.condition, pc.parameters);
		if (list==null) return null;
		Collections.sort(list, new ATTComparator());
		GenericJdbcDAO<N_DEPARTMENT, String> dao1 = new GenericJdbcDAO<N_DEPARTMENT, String>("N_DEPARTMENT", N_DEPARTMENT.class);
		dao1.setVniColumns(new String[]{"NAME_DEPT"});
		GenericJdbcDAO<N_EMP_TAXCODE, N_EMP_TAXCODEPk> dao2 = new GenericJdbcDAO<N_EMP_TAXCODE, N_EMP_TAXCODEPk>("N_EMP_TAXCODE", N_EMP_TAXCODE.class);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ATT200000 data=null;
		for (int i=0;i<list.size();i++){
			data = list.get(i);
			bigSum=BigDecimal.valueOf(bigSum.longValue() + data.getTTS().longValue());
			System.out.println(data.getEMPNA());
			N_DEPARTMENT dept = dao1.findById(data.getDEPSN());
			if (dept!=null) 
			{
		
				data.setDEPSN(dept.getNAME_DEPT());
				id_dept=dept.getID_DEPT();
			}
			N_EMP_TAXCODEPk pk = new N_EMP_TAXCODEPk(data.getEMPSN(), data.getCODE_TAX(), "1");
			N_EMP_TAXCODE tax = dao2.findById(pk);
			
			if (tax!=null) data.setCODE_TAX(tax.getCODE_TAX() + " " + sdf.format(tax.getDATES()));
		}
		
		
	

		return list;
	}

	protected void doPrintSalaryTable(boolean updateOrder) {
		//query condition
		if (printParams==null||printParams[0]==null||printParams[0].equals("")){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Chỉ in bảng tổng theo đơn vị hoặc nhóm ATM");
			return;
		}
		List<ATT200000> list = prepareJasperDatasource(updateOrder);
		if (list==null||list.size()==0) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu");
			return;
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/bangluongtong.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("month", getMonth()+"/"+getYear());
			if (printParams[2]!=null) printParams[1]=printParams[1]+" (" + printParams[2] + ")";
			params.put("dept", printParams[0]);
			params.put("pos", printParams[1]);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "BangTong");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	protected boolean doBrowserContentRefresh() {
		String queryString = "";
		List paramList = new ArrayList();

		if (getBaseCondition() != null) {
			if (getBaseCondition().condition.trim().length() > 0) {
				queryString += "(" + getBaseCondition().condition + ")";
			}
			for (Object element : getBaseCondition().parameters) {
				paramList.add(element);
			}
		}
	
		if (getQueryCondition() != null) {
			if ((queryString.length() > 0) && (getQueryCondition().condition.length() > 0)) {
				queryString += " and ";
			}
			if (getQueryCondition().condition.length() > 0) {
				queryString += "(" + getQueryCondition().condition + ")";
			}
			for (Object element : getQueryCondition().parameters) {
				paramList.add(element);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.info("QUERY:" + queryString + ", [" + Arrays.toString(paramList.toArray()) + "]");
		}
	
		
		if (getMasterDataContent().getDataObjectSet().query(queryString,
				paramList.toArray(new Object[paramList.size()])) < 0) {
			MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getMasterDataContent().getDataObjectSet().getErrorMessage());
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("fetch record count:" + getMasterDataContent().getDataObjectSet().getRecordCount());
		}
	
		getBrowserContent().refresh();
		getBrowserContent().setCurrentPage(0);
	
		if (getMasterDataContent().getDataObjectSet().getRecordCount() > 0) {
			if (getBrowserContent().getCurrentSelectRowNo() != -1 && getBrowserContent().getCurrentSelectRowNo() < getBrowserContent().getDataObjectSet().getRecordCount()) {
				getBrowserContent().setSelectedIndex(getBrowserContent().getCurrentSelectRowNo(), true);
			} else {
				getBrowserContent().setSelectedIndex(0, true);
			}
			doMasterDataSelectChange();
			btnBangTong.setEnabled(true);
			btnPhieuLuong.setEnabled(true);
			btnPhieuLuong2.setEnabled(true);
		}
		else {
			getBrowserContent().getBrowserTable().getSelectionModel().clearSelection();
			getMasterDataContent().clearAllComponentValue();
			btnBangTong.setEnabled(false);
			btnPhieuLuong.setEnabled(false);
			btnPhieuLuong2.setEnabled(false);
		}
		return true;
	}
	
	protected void doMasterDataSelectChange() {
		int recNo = getBrowserContent().getCurrentSelectRowNo();
		getMasterDataContent().moveTo(recNo);
		doDataContentRefresh(recNo);
		this.doUIRefresh();
	}
	
	protected boolean doDataContentRefresh(int recNo) {
		if (!getMasterDataContent().moveTo(recNo)) {
			setErrorMessage(getMasterDataContent().getErrorMessage());
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getMasterDataContent().getErrorMessage());
			return false;
		}
		
		if (!getMasterDataContent().refetchData()) {
			setErrorMessage(getMasterDataContent().getErrorMessage());
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getMasterDataContent().getErrorMessage());
			getMasterDataContent().clearAllComponentValue();
			return false;
		}else{
			ATT200000 data = (ATT200000) getMasterDataContent().getDataObject();
			
			if (data.getBONUS1_HOL()!=null&&data.getBONUS1_HOL().compareTo(BigDecimal.ZERO)!=0){
				DataObjectSet ds = getMasterDataContent().getDataObjectSet();
				data.setBONUS1(data.getBONUS1().add(data.getBONUS1_HOL()));
				ds.modify(data);
				ds.rollback();
				getMasterDataContent().getUIDataObjectBinder().bindDataToFields(data);
			}
			
			atteDAO.setTable("ATTENDANTDB_"+getMonth()+getYear());			
			try{
				atte = atteDAO.findById(data.getEMPSN());
				editable=true;
			}catch(Exception e){
				atte = null;
				editable=false;
			}
			if (atte!=null){
				getMasterDataContent().getAttendantdbBinder().bindDataToFields(atte);
			}else{
				getMasterDataContent().getAttendantdbBinder().bindDataToFields(new ATTENDANTDB());
			}
		}
		return true;
	}//doDataContentRefresh

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String[] getPrintParams(){
		return this.printParams;
	}
	
	public void setPrintParams(String[] printParams){
		this.printParams=printParams;
	}
	
	protected void masterToolbarActionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(MasterToolbar.CMD_QUERY_NORMAL)) {
			doQueryNormal();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_REFRESH)) {
			doRefresh();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_EDIT)) {
			doEdit();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_SAVE)) {
			doSave();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_CANCEL)) {
			doCancel();
		}
	}
	
	protected void beforeSaveToDataObjectSet(){		
		//
	}
	
	protected boolean updateDataObjectSet(){
		try{
			ATT200000 data = (ATT200000) getMasterDataContent().getDataObject();
			data.setNOTE(atte.getNOTE());
			atteDAO.update(atte);
			getMasterDataContent().getDataObjectSet().modify(data);
			getMasterDataContent().beginTranscation();
			getMasterDataContent().getDataObjectSet().applyUpdate();
			getMasterDataContent().commit();
			//FIXME no transaction GenericJdbcDAO
			//call stored procedure			
			return true;
		}catch(Exception e){
			return false;
		}
	}

	private void doCancel() {
		getBrowserContent().setEnabled(true);		
		getMasterDataContent().getAttendantdbBinder().bindDataToFields(atte);
		getMasterDataContent().cancel();
		setDataMode(IProgram.DATAMODE_NONE);
		doUIRefresh();
	}

	private boolean doSave() {
		getMasterDataContent().getAttendantdbBinder().bindFieldsToData(atte);
		//checkData
		beforeSaveToDataObjectSet();
		
		if (updateDataObjectSet()){
			//salarydao.save;
//			SalaryDAO2 dao = new SalaryDAO2("ATTENDANTDB_"+month+year,ATTENDANTDB.class);
//			dao.calculate(true, atte.getEMPSN(), month, year);
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setEMPSN(atte.getEMPSN());
			action.setACTIONNAME("UPDATE");
			action.setNOTE("TINH LUONG LAI~~~JAVA");
			action.setTABLENAME("ATT" + getYear() + getMonth());
			FvLogger.log(action);
			setDataMode(IProgram.DATAMODE_NONE);
			doDataRefresh();
			doUIRefresh();
			return true;
		}
		setErrorMessage("Lỗi!!! Không thể lưu");
		return false;
	}

	private boolean doEdit() {
		if (!editable){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không tìm thấy dữ liệu gốc để chỉnh sửa");
			return false;
		}
		if (!checkEditable()){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Đã khóa bảng lương");
			return false;
		}
		int recNo = getMasterDataContent().getDataObjectSet().getRecNo();
		if (getMasterDataContent().edit(recNo)){
			setDataMode(IProgram.DATAMODE_EDIT);
			masterToolbar.refresh();
			getBrowserContent().setEnabled(false);
			return true;
		}
		return false;
	}

	private void doRefresh() {
		refresh();
	}

	private void doQueryNormal() {
		if (query==null){
			query = new SalaryQuery();
			query.init(this);
		}
		query.showIt(CustomQueryPane.SHOW_MODAL);
	}
	
	
	public void dispose() {
		if (query!=null)
			query.dispose();
		super.dispose();
	}
	
	
	protected boolean doCloseCheck() {
		if (super.doCloseCheck()) {
			if ((this.getDataMode() == IProgram.DATAMODE_EDIT)
					|| (this.getDataMode() == IProgram.DATAMODE_NEW)) {
				MessageDialog msgDialog = new MessageDialog(
						MessageDialog.TYPE_WARNING
								+ MessageDialog.CONTROLS_YES_NO, this
								.getCommMsgRes().getString(
										"Generic.MSG.EditModeCloseConfirm"));
				msgDialog.addActionListener(new ActionListener() {
					private static final long serialVersionUID = 1L;
					
					public void actionPerformed(ActionEvent e) {
						if (MessageDialog.COMMAND_OK.equals(e
								.getActionCommand())) {
							SalaryMainProgram.this.doClose();
							windowPane.getParent().remove(windowPane);
						}
					}
				});
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	
	protected void doDataRefresh() {
		int recNo = getBrowserContent().getCurrentSelectRowNo();
		super.doDataRefresh();
		this.doBrowserContentRefresh();
		
		/*
		 * FIXED <a href="http://211.20.130.197/mantis/view.php?id=82">ISSUE 0000082</a>
		 * 查詢瀏灠GRID，點選在第九筆，重新查詢時，若小於第9筆，會出現指定資料筆數超過範圍
		 */
		if (getBrowserContent().getBrowserTable().getModel().getRowCount() < recNo) {
			recNo = -1;
		}
		
		if(recNo >=0 &&  getBrowserContent().getCurrentSelectRowNo() != -1) {
			this.doDataContentRefresh(recNo);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		mainSplitPane = new SplitPane();
		mainSplitPane.setSeparatorPosition(new Extent(36, Extent.PX));
		mainSplitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		mainSplitPane.setSeparatorVerticalImage(new FillImage(imageReference1));
		mainSplitPane.setResizable(false);
		add(mainSplitPane);
		ContentPane contentPane1 = new ContentPane();
		mainSplitPane.add(contentPane1);
		masterToolbar = new MasterToolbar(this);
		contentPane1.add(masterToolbar);
		mainPane = new SalaryMainPane();
		mainSplitPane.add(mainPane);
	}

	public void updateTable(String month, String year) {
		GenericJdbcDAO dao = (GenericJdbcDAO) getMasterDataContent().getDataObjectSet().getDao();
		dao.setTable("ATT"+year+month);
	}
	
	
	protected void doUIRefresh() {
		super.doUIRefresh();
		masterToolbar.refresh();
		getMasterDataContent().doUIRefresh();
		if (getDataMode()!=IProgram.DATAMODE_EDIT){
			btnBangTong.setEnabled(true);
			btnImportExcel.setEnabled(true);
			btnPhieuLuong.setEnabled(true);
			btnPhieuLuong2.setEnabled(true);
		}else{
			btnBangTong.setEnabled(false);
			btnImportExcel.setEnabled(false);
			btnPhieuLuong.setEnabled(false);
			btnPhieuLuong2.setEnabled(false);
		}
		if (getMasterDataContent().getDataObjectSet().getRecordCount()<=0){
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		}else{
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_EDIT, true);
		}
	}
	
	
	private boolean checkEditable(){
		IGenericDAO<ATTLOCK, String> dao = Application.getApp().getDao(ATTLOCK.class);
		ATTLOCK data = dao.findById("ATT"+getYear()+getMonth());
		if (data==null||data.getTABLOCK().equalsIgnoreCase("y")){
			return false;
		}
		return true;
	}
	
	private List<ATT200000> prepareJasperDatasource(boolean updateOrder){
		ProgramCondition pc = getQueryCondition();
		if (pc==null) return null;
		GenericJdbcDAO dao = (GenericJdbcDAO) getMasterDataContent().getDataObjectSet().getDao();
		List<ATT200000> list = dao.fetch(-1, "o", pc.condition, pc.parameters);
		if (list==null) return null;
		Collections.sort(list, new ATTComparator());
		final BigDecimal DD = BigDecimal.valueOf(1000);
		List<ATT200000> ret = new ArrayList<ATT200000>(list.size());
		for (int i=0;i<list.size();i++){
			ATT200000 data = list.get(i);
			try {				
				data.setBSALY(data.getBSALY().divide(DD));
				data.setREST_SICK(data.getREST_SICK().add(data.getOTHER()));
				data.setDUCLS_S(data.getDUCLS_S().add(data.getNUCLS_S()).divide(DD));
				data.setREST_PAY_S(data.getREST_PAY_S().divide(DD));
				data.setADDCLS1_S(data.getADDCLS1_S().divide(DD));
				data.setNADDCLS_S(data.getNADDCLS_S().divide(DD));
				data.setADDHOL_S(data.getADDHOL_S().divide(DD));
				data.setADDHOLN_S(data.getADDHOLN_S().divide(DD));
				data.setBONUS6(data.getBONUS6().divide(DD));
				data.setBONUS_ACN(data.getBONUS_ACN().divide(DD));
				data.setBSALY_N(data.getBSALY_N().divide(DD));
				data.setBONUS4(data.getBONUS4().add(data.getBONUS9()).divide(DD));
				data.setBONUS5(data.getBONUS5().divide(DD));
				data.setBONUS7(data.getBONUS7().divide(DD));
				data.setBONUS1(data.getBONUS1().add(data.getBONUS1_HOL()).divide(DD));
				data.setBONUS2(data.getBONUS2().divide(DD));
				data.setBONUS3(data.getBONUS3().divide(DD));
				data.setTINCOME(data.getTINCOME().divide(DD));
				data.setJOININSU(data.getJOININSU().add(data.getBH_TNGHIEP()).divide(DD));
				data.setYLBX(data.getYLBX().divide(DD));
				data.setBORM(data.getBORM().divide(DD));
				data.setJOINLUM(data.getJOINLUM().divide(DD));
				data.setPAYTAX(data.getPAYTAX().divide(DD));
				data.setKQT(data.getKQT().divide(DD));
				data.setTKQ(data.getTKQ().divide(DD));
				data.setTS1(data.getTS1().divide(DD));
				data.setTTS(data.getTTS().divide(DD));
				data.setSTT(BigDecimal.valueOf(i+1));
				data.setBONUS10(data.getBONUS10().divide(DD));
				if (updateOrder)
					updateSTT(dao, data.getEMPSN(), data.getSTT());
				ret.add(data);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return ret;
	}
	
	private void updateSTT(GenericJdbcDAO dao, String empsn, BigDecimal stt) {
		String sql = "UPDATE " + dao.getTable() + " SET STT=? WHERE EMPSN=?";
		int up = dao.getSimpleJdbcTemplate().update(sql, new Object[]{stt, empsn});
		if (logger.isDebugEnabled()){
			logger.debug("--- " + sql + " [" + empsn + "," + stt +"] > " + up);
		}
	}

	private class ATTComparator implements Comparator<ATT200000>{
		
		public int compare(ATT200000 o1, ATT200000 o2) {
			int cp=o1.getDEPSN().compareTo(o2.getDEPSN());
			if (cp!=0) return cp;
			return o1.getEMPSN().compareTo(o2.getEMPSN());
		}
	}
}
