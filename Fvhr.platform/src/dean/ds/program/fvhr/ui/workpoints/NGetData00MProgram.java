package ds.program.fvhr.ui.workpoints;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.table.DefaultTableCellRenderer;
import nextapp.echo2.app.table.TableCellRenderer;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.salary.SalaryDAO;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_GET_DATA;
import ds.program.fvhr.ui.SExport2Excel;
import ds.program.fvhr.ui.hrdata.ICMasterData;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import echopointng.table.SortableTableHeaderRenderer;
import fv.util.ApplicationHelper;
import fv.util.FvLogger;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;

/**
 * Get Data * 
 */
public class NGetData00MProgram extends MaintainSProgram {

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorkPointExecPane pane;
	private Button btnPrint;
	private Button btnMonthProcess;
	private WorkpointsDAO dao;
	private Button btnUpdateToSalaryData;
	private NGetData00MDataContent dc;
	private DscField txtEmpsn;
	private BigDecimal workDaysOfMonth=new BigDecimal(-1);
	private Label lblInfo;
	private Button btnExcel;
	private String info;
	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		dc = new NGetData00MDataContent();
		setMasterDataContent(dc);
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
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		getMasterToolbar().setbtnNewVisible(false);
		getMasterToolbar().setbtnEditVisible(false);
		getMasterToolbar().setbtnSaveVisible(false);
		getMasterToolbar().setbtnCancelVisible(false);
		getMasterToolbar().setbtnEmailVisible(false);
		getMasterToolbar().setbtnConfirmVisible(false);
		getMasterToolbar().setbtnDeleteVisible(false);
		getMasterToolbar().setbtnExportVisible(false);
		getMasterToolbar().setbtnCancelConfirmVisible(false);
		
		

		//5.設定table的最大筆數及每頁筆數
		//base condition
		String sql = 
			"o.EMPSN IN (" +
			"(SELECT E.EMPSN FROM N_EMPLOYEE E, N_USER_LIMIT U " + 
			"WHERE o.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER=?))";
		ProgramCondition pc = new ProgramCondition(sql, new Object[]{ApplicationHelper.getVftUserId()});
		setBaseCondition(pc);
		//query condition
//		ProgramCondition pc1 = new ProgramCondition(getDefaultQueryString(), getDefaultQueryParams());
		ProgramCondition pc1 = new ProgramCondition("1!=1");
		setQueryCondition(pc1);

		btnMonthProcess = new Button();
		btnMonthProcess.setToolTipText("Xử lý dữ liệu tháng");
		btnMonthProcess.setStyleName("Default.ToolbarButton");
		btnMonthProcess.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/cog_add.png"));
		btnMonthProcess.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/cog_addD.png"));
		btnMonthProcess.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				showProcessPane();
			}
		});		
		getMasterToolbar().add(btnMonthProcess);
		
		btnPrint = new Button();
		btnPrint.setToolTipText("In dữ liệu tháng");
		btnPrint.setStyleName("Default.ToolbarButton");
		btnPrint.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printer.png"));
		btnPrint.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/printerD.png"));
		btnPrint.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				doPrintWorkPointsTable();
			}
		});
		btnPrint.setEnabled(false);
		getMasterToolbar().add(btnPrint);
		
		btnUpdateToSalaryData = new Button();
		btnUpdateToSalaryData.setToolTipText("Chuyển dữ liệu ngày công cho kế toán.");
		btnUpdateToSalaryData.setStyleName("Default.ToolbarButton");
		btnUpdateToSalaryData.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/table_go.png"));
		btnUpdateToSalaryData.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/table_goD.png"));
		btnUpdateToSalaryData.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				MessageDialog dlg = new MessageDialog("Chuyển dữ liệu", "Chuyển dữ liệu ngày công tháng " + pane.getMonths() + "/" + pane.getYears(), MessageDialog.CONTROLS_YES_NO);
				dlg.addActionListener(new ActionListener(){
					private static final long serialVersionUID = 1L;
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
							doUpdateToSalary();
						}
					}
				});
			}
		});
		btnUpdateToSalaryData.setEnabled(false);
		getMasterToolbar().add(btnUpdateToSalaryData);
		
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
		getMasterToolbar().add(btnExcel);
		
		xetQuyenNSKT();
		
		lblInfo = new Label("Chọn Tìm kiếm");
		lblInfo.setForeground(new Color(0xcc3300));
		Font f = new Font(Font.ARIAL, Font.BOLD, new Extent(14));
		lblInfo.setFont(f);
		RowLayoutData infoLayout = new RowLayoutData();
		infoLayout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.CENTER));
		infoLayout.setWidth(new Extent(100, Extent.PERCENT));
		lblInfo.setLayoutData(infoLayout);
		
		getMasterToolbar().add(lblInfo);
		
		dc.getRowNavigator().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int recNo = dc.getRowNavigator().getCurrentRecord();
				if (e.getActionCommand().equals("first")){
					recNo=1;
				}else if (e.getActionCommand().equals("previous")){
					recNo--;
				}else if (e.getActionCommand().equals("next")){
					recNo++;
				}else if (e.getActionCommand().equals("last")){
					recNo = dc.getRowNavigator().getTotalRecord();
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
		dc.getRowNavigator().add(txtEmpsn);		
		getBrowserContent().setMaxSize(20000);
		return ret;
	}
	
	private void doUpdateToSalary(){
		
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		//int locks = pane.checkLock();
		int locks = pane.checkLock("trans");		
		if (locks>0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Đã khóa dữ liệu " + locks + "/" + ds.getRecordCount());
			return;
		}
		StopWatch sw = new StopWatch();
		sw.start();
		
		int n = 0;
		WorkpointsDAO dao = getJdbcDAO();
		dao.setMonth(getMonth());
		dao.setYear(getYear());
		for (int i=0;i<ds.getRecordCount();i++){
			N_GET_DATA data = (N_GET_DATA) ds.getDataObject(i);
			dao.transferICData(data.getEMPSN());
			dao.updateSalaryData(data.getEMPSN());
			//save action
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setEMPSN(data.getEMPSN());
			action.setACTIONNAME("TRANSFER");
			action.setNOTE("CHUYEN DU LIEU");
			action.setTABLENAME("ATTENDANTDB_" + dao.getMonth() + dao.getYear());
			FvLogger.log(action);
			n++;
			if (n%1000==0) System.gc();
		}
		sw.stop();
		System.out.println("Elapse time: " + (float)sw.getTime()/1000 + "s");
		MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_YES_NO+MessageDialog.TYPE_INFORMATION, "Đã chuyển dữ liệu " + n + "/" + ds.getRecordCount() + "\r\nBạn có muốn xuất danh sách chuyển ra file Excel?");
		dlg.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
					doExportToExcel();
				}
			}
			
		});
	}
	
	private void doExportToExcel() {
		
		DataObjectSet ds = getMasterDataContent().getDataObjectSet();
		if (ds.getRecordCount()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		
		SExport2Excel.run(true, ds, new SalaryDAO(getMonth(), getYear()), getMonth(), getYear());
	}
	
	public void setInfo(String info){
		this.info = info;		
	}
	
	@Override
	protected void doRefresh() {
		super.doRefresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()>0){
			btnPrint.setEnabled(true);
			btnUpdateToSalaryData.setEnabled(true);
		}else{
			btnPrint.setEnabled(false);
			btnUpdateToSalaryData.setEnabled(false);
		}
		lblInfo.setText(this.info+" / Số thẻ: " + getMasterDataContent().getDataObjectSet().getRecordCount());
		xetQuyenNSKT();
	}
	
	@Override
	public int refresh() {
		int ret = super.refresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()>0){
			btnPrint.setEnabled(true);
			btnUpdateToSalaryData.setEnabled(true);
		}else{
			btnPrint.setEnabled(false);
			btnUpdateToSalaryData.setEnabled(false);
		}
		lblInfo.setText(this.info+" / Số thẻ: " + getMasterDataContent().getDataObjectSet().getRecordCount());
		xetQuyenNSKT();
		return ret;
	}
	
	public String getDefaultQueryString(){
		return "o.MONTHS=? and o.YEARS=?";
	}
	
	public Object[] getDefaultQueryParams(){
		return new Object[]{ApplicationHelper.getMonthString(), ApplicationHelper.getYearString()};
	}
	
	private void showProcessPane(){
		if (pane==null){
			pane = new WorkPointExecPane(this);
			Application.getApp().getDefaultWindow().getContent().add(pane);
		}
		pane.setType(WorkPointExecPane.TYPE_EXCEC);
		pane.setTitle("Xử lý dữ liệu tháng");
		pane.setVisible(true);
	}
	
	public WorkpointsDAO getJdbcDAO(){
		if (dao == null)
			dao = new WorkpointsDAO();
		return this.dao;
	}
	
	private void doPrintWorkPointsTable(){
		DataObjectSet dos = getMasterDataContent().getDataObjectSet();
		if (dos.getRecordCount()<=0){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Không có dữ liệu");
			return;
		}
		if (dos.getRecordCount()>1000){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chỉ in tối đa 1000 số thẻ");
			return;
		}
		String month = pane.getMonths();
		String year = pane.getYears();
		int cmonth = Integer.parseInt(month)-1;
		List<ICMasterData> list;
		List<String> listEmpsn = new ArrayList<String>();
		for (int i=0;i<dos.getRecordCount();i++){
			N_GET_DATA data = (N_GET_DATA) dos.getDataObject(i);
			listEmpsn.add(data.getEMPSN());
		}
		getJdbcDAO().setMonth(month);
		getJdbcDAO().setYear(year);
		list = getJdbcDAO().getDataList(listEmpsn);
		for (ICMasterData data:list){
			data.setFULLNAME(Vni2Uni.convertToUnicode(data.getFULLNAME()));
			data.setNAME_DEPT(Vni2Uni.convertToUnicode(data.getNAME_DEPT()));
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mon", month);
		params.put("yy", year);
		params.put("SUBREPORT_DIR", ReportFileManager.getReportFormatFolder("fvhr").getAbsolutePath()+"/");
		params.put("USER_ID", getLoginInfo().getUserID());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cmonth);
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int col_break = days/2;
		if (days%2>0) col_break=col_break+1;
		params.put("col_break", col_break);
		params.put("REPORT_CONNECTION", Application.getApp().getConnection());
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/bangchamcong.jrxml"));
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
			ReportUtils.doExportPdf(jp, "BangChamCong");
//			ReportUtils.doExportHtml(jp, "D:/test.html");
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public String getMonth(){
		return pane.getMonths();
	}
	
	public String getYear(){
		return pane.getYears();
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
		return new NGetData00MQuery();
	}
	
	@Override
	protected void doQueryNormal() {
		if (pane==null){
			pane = new WorkPointExecPane(this);
			Application.getApp().getDefaultWindow().getContent().add(pane);
		}
		pane.setType(WorkPointExecPane.TYPE_SEARCH);
		pane.setTitle("Tìm kiếm dữ liệu tháng");
		pane.setVisible(true);
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		getBrowserContent().getBrowserTable().setDefaultRenderer(Object.class, defaultRenderer);
		getBrowserContent().getBrowserTable().setDefaultHeaderRenderer(new DscSortableTableHeaderRenderer1());
	}
	
	//override render
	private TableCellRenderer defaultRenderer = new DefaultTableCellRenderer() {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
			TableLayoutData layoutData = new TableLayoutData();
			layoutData.setBackground((Color) (row % 2 == 0 ? getRenderProperty(DscPageableSortableTable.PROPERTY_ODD_ROW_BACKGROUND) : getRenderProperty(DscPageableSortableTable.PROPERTY_EVEN_ROW_BACKGROUND)));
			
			Component component=null;
			
			if(value != null)
				component = new Label(value.toString());
			else
				component = new Label("");
			
			component.setForeground((Color) (row % 2 == 0 ? getRenderProperty(DscPageableSortableTable.PROPERTY_ODD_ROW_FOREGROUND) : getRenderProperty(DscPageableSortableTable.PROPERTY_EVEN_ROW_FOREGROUND)));			
			
			if (column==3){
				BigDecimal val = new BigDecimal(String.valueOf(value));
				if (val.compareTo(workDaysOfMonth)!=0)
					component.setForeground(Color.RED);
				
			}
			component.setLayoutData(layoutData);
			return component;
		}
	};

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

	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			N_GET_DATA data = (N_GET_DATA) getMasterDataContent().getDataObject();
			DataDailyBrowserContent dtc = dc.getDailyContent();
			dtc.setEmpsn(data.getEMPSN());
			String mm = pane.getMonths()+"/"+pane.getYears();
			dtc.setDateString(mm);
			dtc.refresh();
			//Update 30/08/2013, HA
			//dc.bindEmployeeInfo(data.getEMPSN());
			dc.bindEmployeeInfo(data.getEMPSN(),pane.getMonths(),pane.getYears());
			dc.getRowNavigator().setCurrentRecord(recNo+1);
			dc.getRowNavigator().reset();
			return true;
		}
		return false;
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        return new String[] {"EMPSN", "EMPSN_Object.FNAME", "EMPSN_Object.LNAME", "TOTAL_DAYS", "DUCLS", "NDUCLS", "REST", "REST_PAY", "REST_SICK", "OTHER", "NWHOUR", "LMATER", "LATE", "LOCKED"};
        
//		return null;
	}
	
	@Override
	protected boolean doBrowserContentRefresh() {
		if (super.doBrowserContentRefresh()){
			dc.getRowNavigator().setTotalRow(getMasterDataContent().getDataObjectSet().getRecordCount());
			dc.getRowNavigator().setCurrentRecord(getMasterDataContent().getDataObjectSet().getRecNo());
			dc.getRowNavigator().reset();
			return true;
		}
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (pane!=null)
			pane.dispose();
	}

	public BigDecimal getWorkDaysOfMonth() {
		return workDaysOfMonth;
	}

	public void setWorkDaysOfMonth(BigDecimal workDaysOfMonth) {
		this.workDaysOfMonth = workDaysOfMonth;
	}
	
	public class DscSortableTableHeaderRenderer1 extends SortableTableHeaderRenderer{		
		private static final long serialVersionUID = 1L;
		
		public DscSortableTableHeaderRenderer1(){
			super();
		}
		public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
			TableLayoutData layoutData = new TableLayoutData();
			layoutData.setBackground(new Color(0x0080C0));
			Component comp = super.getTableCellRendererComponent(table, value, column, row);
			comp.setForeground(Color.WHITE);
			comp.setBackground(new Color(0x0080C0));
			comp.setLayoutData(layoutData);
			if (NGetData00MProgram.this.getMasterDataContent().getDataObjectSet() != null) {
				if (comp instanceof Button) {
					((Button) comp).setText(NGetData00MProgram.this.getMasterDataContent().getDataObjectSet().getDisplayCaption(value.toString()));
				}
			}
			return comp;
		}		
	}
	
	public void xetQuyenNSKT(){
		//If la ke toan thi ko duoc xu ly du liue thang va chuyen du lieu ngay cong
		// bo sung 18/10/2013
		String userGroup;
		String userID;
		userID= Application.getApp().getLoginInfo().getUserID();
		InsuranceDAO ins = new InsuranceDAO();
		userGroup = ins.GetField("ID_LIMIT","DSPB02", "PB_USERID", "", "", userID, "", "");
		if(userGroup.equals("L01") || (userGroup.equals("ABC"))){
			btnMonthProcess.setEnabled(false);
			btnUpdateToSalaryData.setEnabled(false);
		}		
	}
	
}
