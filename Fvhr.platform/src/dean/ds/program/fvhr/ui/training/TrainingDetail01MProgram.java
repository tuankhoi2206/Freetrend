package ds.program.fvhr.ui.training;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.ReportUtils;

/**
 * TrainingDetail * 
 */
public class TrainingDetail01MProgram extends MaintainSProgram {

	private TrainingBrowserContent masterBrowserContent;
	private int dataMode;
	private SplitPane splitPane;
	private SplitPane programSplitPane;
	private TrainingDetail01MQuery query;
	private Button btnMulti;
	private Button btnReport;

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
		setMasterDataContent(new TrainingDetail01MDataContent());
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
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);
		getMasterToolbar().setbtnBrowserVisible(false);
		getMasterToolbar().setbtnContentVisible(false);
		getMasterToolbar().setbtnConfirmVisible(false);
		getMasterToolbar().setbtnEmailVisible(false);
		getMasterToolbar().setbtnCancelConfirmVisible(false);
		getMasterToolbar().setbtnExportVisible(false);
		
		btnMulti = new Button();
		btnMulti.setToolTipText("Cập nhật dữ liệu từ file Excel");
		btnMulti.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_import.png"));
		btnMulti.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/document_importD.png"));
		btnMulti.setStyleName("Default.ToolbarButton");
		btnMulti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TrainingDetailMultiImportPane pane = new TrainingDetailMultiImportPane(TrainingDetail01MProgram.this);
				Application.getApp().getDefaultWindow().getContent().add(pane);
			}
		});
		getMasterToolbar().add(btnMulti);
		
		btnReport = new Button();
		btnReport.setToolTipText("Báo cáo");
		btnReport.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrint.gif"));
		btnReport.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPrintD.gif"));
		btnReport.setStyleName("Default.ToolbarButton");
		btnReport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doPrint();
			}
		});
		getMasterToolbar().add(btnReport);
		
		//5.設定table的最大筆數及每頁筆數
		
		splitPane = new SplitPane();
		splitPane.setSeparatorHorizontalImage(new FillImage(new ResourceImageReference("/dsc/echo2app/resource/image/SplitHerzBar.png")));
		programSplitPane = new SplitPane();
		programSplitPane.setSeparatorVerticalImage(new FillImage(new ResourceImageReference("/dsc/echo2app/resource/image/SplitVertBar.png")));
		masterBrowserContent = new TrainingBrowserContent();
		if (masterBrowserContent.doInit()!=RET_OK){
			return RET_DBERROR;
		}
		ProgramCondition basePC = new ProgramCondition("o.EMPSN in (select t.EMPSN from N_TRAINING_DETAILS t where t.EMPSN=o.EMPSN)");
		masterBrowserContent.setBaseCondition(basePC);
		ProgramCondition pc = new ProgramCondition("1<>1");
		setQueryCondition(pc);
		masterBrowserContent.setQueryCondition(pc);
		masterBrowserContent.getBrowserContent().addSelectChangeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doMasterBrowserContentSelectedChange();
			}
		});
		masterBrowserContent.setSearchFoundListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doMasterBrowserContentSelectedChange();
			}
		});
		getBrowserContent().addSelectChangeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int recNo = getBrowserContent().getCurrentSelectRowNo();
				doDataContentRefresh(recNo);
			}
		});
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	protected void doPrint() {
		ProgramCondition bpc = masterBrowserContent.getBaseCondition();
		ProgramCondition pc = masterBrowserContent.getQueryCondition();
		String cond = "";
		if (!bpc.condition.equals("")){
			cond = bpc.condition + " and " + pc.condition;
		}
		//ignore bind params cause query form
		
//		System.out.println(cond);
		
		String jrsql = "select o.fname, o.lname, o.date_hired, o.empsn, o.position, " +
			"(select d.name_dept from n_department d where o.depsn=d.id_dept) as depsn from n_employee o where " + cond;
//		System.out.println(jrsql);
//		if (true) return;
		try {
			JasperDesign jd = JRXmlLoader.load(ReportFileManager
					.getReportFormatFolder("fvhr/training.jrxml"));
			JRDesignQuery query = new JRDesignQuery();
			query.setText(jrsql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SUBREPORT_DIR", ReportFileManager.getReportFormatFolder("fvhr").getAbsolutePath()+"/");			
			JasperPrint jp = JasperFillManager.fillReport(jr, params, Application.getApp().getConnection());
			ReportUtils.doExportPdf(jp, "Training");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TrainingDetail01MDataContent getMasterDataContent() {
		return (TrainingDetail01MDataContent) super.getMasterDataContent();
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
		if (query==null) query = new TrainingDetail01MQuery();
		return query;
	}
	
	public void doMasterQuery(ProgramCondition pc){
		masterBrowserContent.setQueryCondition(pc);
		masterBrowserContent.refresh();
		if (masterBrowserContent.getDataObjectSet().getRecordCount()>0){
			doMasterBrowserContentSelectedChange();
			int recNo = getBrowserContent().getCurrentSelectRowNo();
			doDataContentRefresh(recNo);
		}else{
			getMasterDataContent().getDataObjectSet().clearAll();
			getBrowserContent().refresh();
			doUIRefresh();
			//ensure select field must has element <=> fix PropertyBinder resetComponent
			getMasterDataContent().getUIDataObjectBinder().bindDataToFields(new N_TRAINING_DETAILS());
			getMasterDataContent().clearAllComponentValue();
		}
	}
	
	public void doMasterBrowserContentSelectedChange(){
		int recNo = masterBrowserContent.getBrowserContent().getCurrentSelectRowNo();
		N_EMPLOYEE data = (N_EMPLOYEE) masterBrowserContent.getDataObjectSet().getDataObject(recNo);
		if (data!=null){
			ProgramCondition pc = new ProgramCondition("o.EMPSN=?", new Object[]{data.getEMPSN()});
			setQueryCondition(pc);
		}else{
			ProgramCondition pc = new ProgramCondition("o.EMPSN=''");
			setQueryCondition(pc);
		}
		refresh();
	}
	
	@Override
	protected void doRefresh() {
		masterBrowserContent.refresh();
	}
	
	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.setWidth(new Extent(1200));
		windowPane.removeAll();
		mainSplitPane.removeAll();
		mainSplitPane.add(getMasterToolbar());
		splitPane.setResizable(true);
		splitPane.setSeparatorPosition(new Extent(300));
		windowPane.add(splitPane);		
		splitPane.add(masterBrowserContent);
		splitPane.add(mainSplitPane);
		this.removeAll();
		programSplitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		programSplitPane.setResizable(true);
		programSplitPane.setSeparatorPosition(new Extent(200));
		this.add(programSplitPane);
		programSplitPane.add(getMasterDataContent().getMainContentPane());
		programSplitPane.add(getBrowserContent());
		mainSplitPane.add(programSplitPane);
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
        return new String[] {"COURSE_ID", "SUBJECT_ID", "BDATE", "EDATE", "EMPSN"};
	}
	
	@Override
	protected void switchContent(int view) {
		
	}
	
	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		if (getDataMode()==IProgram.DATAMODE_EDIT||getDataMode()==IProgram.DATAMODE_NEW){
			btnReport.setEnabled(false);
			btnMulti.setEnabled(false);
			masterBrowserContent.setEnabled(false);
			getBrowserContent().setEnabled(false);
			getBrowserContent().refresh();
		}else{
			if (getMasterDataContent().getDataObjectSet().getRecordCount()<=0){
				btnReport.setEnabled(false);
			}else{
				btnReport.setEnabled(true);
			}
			btnMulti.setEnabled(true);
			masterBrowserContent.setEnabled(true);
			getBrowserContent().setEnabled(true);
		}
	}
	
	@Override
	public int refresh() {
		int ret = super.refresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()<=0){
			btnReport.setEnabled(false);
		}else{
			btnReport.setEnabled(true);
		}
		return ret;
	}
	
	@Override
	public boolean doNew() {
		if (super.doNew()){
			dataMode = IProgram.DATAMODE_NEW;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doEdit() {
		getMasterDataContent().courseSelect(null);
		if (super.doEdit()){
			dataMode = IProgram.DATAMODE_EDIT;
			return true;
		}
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (query!=null)
			query.dispose();
	}
	
	@Override
	protected void saveOK() {
		super.saveOK();
		if (dataMode==IProgram.DATAMODE_NEW){
			N_TRAINING_DETAILS detail = (N_TRAINING_DETAILS) getMasterDataContent().getDataObject();
			boolean inList=false;
			for (int i=0;i<masterBrowserContent.getDataObjectSet().getRecordCount();i++){
				N_EMPLOYEE emp = (N_EMPLOYEE) masterBrowserContent.getDataObjectSet().getDataObject(i);
				if (emp.getEMPSN().equals(detail.getEMPSN())){
//					masterBrowserContent.getBrowserContent().setCurrentSelectRowNo(i);
//					doMasterBrowserContentSelectedChange();
					inList = true;
					break;
				}
			}
			if (!inList){
				ProgramCondition pc = new ProgramCondition("o.EMPSN='" + detail.getEMPSN() + "'");
				masterBrowserContent.setQueryCondition(pc);
				masterBrowserContent.refresh();
				masterBrowserContent.getBrowserContent().setCurrentSelectRowNo(masterBrowserContent.getDataObjectSet().getRecordCount()-1);
				doMasterBrowserContentSelectedChange();
			}
		}
	}
}
