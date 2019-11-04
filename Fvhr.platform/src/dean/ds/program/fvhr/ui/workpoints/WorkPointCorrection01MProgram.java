package ds.program.fvhr.ui.workpoints;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_LOST_DATA_DETAIL;
import ds.program.fvhr.domain.pk.N_LOST_DATA_DETAILPk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import fv.util.ApplicationHelper;
import fv.util.BundleUtils;
import fv.util.DateUtils;
import fv.util.DbUtils;
import fv.util.FvLogger;
import fv.util.FvStringUtils;
import fv.util.HRUtils;

/**
 * WPCorrection * 
 */
public class WorkPointCorrection01MProgram extends MaintainSProgram {

	private SplitPane splitPane;
	private WPCSearchRow searchRow;
	private WPCorrectBatchPane multiPane;

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
		setMasterDataContent(new WorkPointCorrection01MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(38));
		searchRow = new WPCSearchRow(this);
		
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		//5.設定table的最大筆數及每頁筆數
		
		String sql = 
			"o.EMPSN IN (" +
			"(SELECT E.EMPSN FROM N_EMPLOYEE E, N_USER_LIMIT U " + 
			"WHERE o.EMPSN=E.EMPSN AND E.USER_MANAGE_ID=U.MA_QL AND U.MA_USER=?))";
		
		
		ProgramCondition bpc = new ProgramCondition(sql, new Object[]{ApplicationHelper.getVftUserId()});
		setBaseCondition(bpc);
		
		ProgramCondition pc = new ProgramCondition("o.EMPSN=?",new Object[]{""});		
		setQueryCondition(pc);
		
		getBrowserContent().getBrowserNav().setSearchTextFieldVisible(false);
		getBrowserContent().getBrowserNav().setSearchButtonVisible(false);
		
		final TextField txtEmpsn = new TextField();
		txtEmpsn.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ProgramCondition pc = new ProgramCondition("o.EMPSN=?",new Object[]{txtEmpsn.getText()});
				setQueryCondition(pc);
				refresh();
			}
		});
		
		getBrowserContent().getBrowserNav().add(txtEmpsn);
		
		Button btnBatch = new Button("Nhập nhiều số thẻ");
		btnBatch.setStyleName("Default.ToolbarButton");
		btnBatch.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (multiPane==null)
					multiPane = new WPCorrectBatchPane(WorkPointCorrection01MProgram.this);
				Application.getApp().getDefaultWindow().getContent().add(multiPane);
				multiPane.focusSoThe();
			}
		});
		getMasterToolbar().add(btnBatch);
		
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
		return new WorkPointCorrection01MQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.removeAll();
		mainSplitPane.removeAll();
		windowPane.add(mainSplitPane);
		mainSplitPane.add(getMasterToolbar());
		mainSplitPane.add(splitPane);
		splitPane.add(searchRow);
		splitPane.add(getBrowserContent());
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
        return new String[]{"EMPSN_Object.EMPSN","EMPSN_Object.FNAME", "EMPSN_Object.LNAME", "DATE_LOST","REASONS","TIN","TOUT","TTEMP"};
	}
	
	@Override
	public WorkPointCorrection01MDataContent getMasterDataContent() {
		return (WorkPointCorrection01MDataContent) super.getMasterDataContent();
	}
	
	@Override
	protected boolean doDataContentRefresh(int recNo) {
		if (super.doDataContentRefresh(recNo)){
			getMasterDataContent().showInfo();
			return true;
		}
		return false;
	}
	
	@Override
	protected void doRefresh() {
		if(mainSplitPane.getComponent(1) == splitPane) {
			//瀏覽頁面
			this.doBrowserContentRefresh();
		}
		else {
			//明細頁面
			//取得目前所選取的資料指標
			int recNo = getBrowserContent().getCurrentSelectRowNo();
			//重查該筆資料，並顯示該資料內容
			doDataContentRefresh(recNo);
		}
	}
	
	@Override
	public boolean doDelete() {
		N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getMasterDataContent().getDataObject();
		HRUtils util = ApplicationHelper.getHRUtils();
		if (!util.getPermissionValidator().hasEmpsnPermission(data.getEMPSN())){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Anh/chị không có quyền thao tác số thẻ này.");
			return false;
		}
		
		String month = DateUtils.getMonth(data.getDATE_LOST());
		String year = DateUtils.getYear(data.getDATE_LOST());
		
		if (util.getWorkpointsValidator().isWPLocked(data.getEMPSN(), month, year)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Đã khóa dữ liệu số thẻ " + data.getEMPSN() + " tháng "+month+"/"+year);
			return false;
		}
		return super.doDelete();
	}
	
	@Override
	public boolean doSave() {
		if (super.doSave()){
			N_LOST_DATA_DETAIL data = (N_LOST_DATA_DETAIL) getMasterDataContent().getDataObject();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setACTIONNAME("INSERT");
			action.setEMPSN(data.getEMPSN());
			action.setNOTE(BundleUtils.getDateFormat().format(data.getDATE_LOST()));
			action.setTABLENAME("N_LOST_DATA_DETAIL");
			FvLogger.log(action);
			N_LOST_DATA_DETAIL d = getMasterDataContent().getDataEx();
			if (d!=null){
				saveEx(d);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		if (super.doNew()){
			getMasterDataContent().focusOnEmpsn();
			return true;
		}
		return false;
	}
	
	private void saveEx(N_LOST_DATA_DETAIL d) {
		IGenericDAO<N_LOST_DATA_DETAIL, N_LOST_DATA_DETAILPk> dao = Application.getApp().getDao(N_LOST_DATA_DETAIL.class);
		N_LOST_DATA_DETAILPk pk = new N_LOST_DATA_DETAILPk(d.getEMPSN(), d.getDATE_LOST());
		N_LOST_DATA_DETAIL db = dao.findById(pk);
		if (db!=null){
			db.setTTEMP(d.getTTEMP());
			dao.update(db);
		}else{
			dao.save(d);
		}
	}

	@Override
	protected void switchContent(int view) {
		if (mainSplitPane.getComponentCount() > 1) {
			if (((view == 0) && (mainSplitPane.getComponent(1) == splitPane))
					|| ((view == 1) && (mainSplitPane.getComponent(1) == this))) {
				return;
			}
			mainSplitPane.remove(1);
		}
		switch (view) {
		case 0:
			mainSplitPane.add(splitPane);
			getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
			getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, true);
			getMasterToolbar().refresh();
			break;
		case 1:
			mainSplitPane.add(this);
			getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, true);
			getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
			getMasterToolbar().refresh();
			break;
		}
		if (view==0) doBrowserContentRefresh();
	}
	
	public void setSearchToolbarCriteria(String sothe1, String sothe2, Calendar date1, Calendar date2){
		searchRow.setSothe(sothe1);
		searchRow.setSothe2(sothe2);
		searchRow.setDate1(date1);
		searchRow.setDate2(date2);
	}
	
	private class WPCSearchRow extends Row{
		private static final long serialVersionUID = 1L;
		private DscField txtSothe;
		private CheckBox chkDenSoThe;
		private DscField txtSothe2;
		private DscDateField dfFromDate;
		private CheckBox chkDenNgay;
		private DscDateField dfToDate;
		private Button btnSearch;
		private WorkPointCorrection01MProgram program;
		public WPCSearchRow(WorkPointCorrection01MProgram program){
			super();
			this.program=program;
			initComponents();
		}
		
		public void setSothe(String st){
			txtSothe.setText(st);
		}
		
		public void setSothe2(String st){
			if (st!=null){
				txtSothe2.setText(st);
				chkDenSoThe.setSelected(true);
				txtSothe2.setEnabled(true);
			}else{
				txtSothe2.setText("");
				chkDenSoThe.setSelected(false);
				txtSothe2.setEnabled(false);
			}
		}
		
		public void setDate1(Calendar date1){
			if (date1!=null)
				dfFromDate.setSelectedDate(date1);
			else{
				dfFromDate.setSelectedDate(Calendar.getInstance());
				dfFromDate.getTextField().setText("");
			}
		}
		
		public void setDate2(Calendar date2){
			if (date2!=null){
				dfToDate.setSelectedDate(date2);
				chkDenNgay.setSelected(true);
				dfToDate.setEnabled(true);
			}else{
				dfToDate.setSelectedDate(Calendar.getInstance());
				dfToDate.getTextField().setText("");
				chkDenNgay.setSelected(false);
				dfToDate.setEnabled(false);
			}
		}
		
		private void initComponents(){
			this.setCellSpacing(new Extent(2));
			this.setStyleName("Default.Toolbar");
			this.setBackground(new Color(0xCCCC99));
			this.add(new Label("Số thẻ"));
			txtSothe = new DscField();
			txtSothe.setMaximumLength(8);
			txtSothe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					doSearch();
				}
			});
			
			this.add(txtSothe);
			chkDenSoThe = new CheckBox("đến số thẻ");
			chkDenSoThe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chkDenSoThe.isSelected()){
						txtSothe2.setEnabled(true);
					}else{
						txtSothe2.setEnabled(false);
					}
				}
			});
			this.add(chkDenSoThe);
			txtSothe2 = new DscField();
			txtSothe2.setMaximumLength(8);
			txtSothe2.setEnabled(false);
			txtSothe2.setDisabledBackground(Color.DARKGRAY);
			txtSothe2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					doSearch();
				}
			});
			this.add(txtSothe2);
			this.add(new Label("Ngày"));
			dfFromDate = new DscDateField();
			dfFromDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
			dfFromDate.getDateChooser().setLocale(new Locale("en"));
			dfFromDate.getTextField().setText("");
			this.add(dfFromDate);
			chkDenNgay = new CheckBox("đến ngày");
			chkDenNgay.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (chkDenNgay.isSelected()){
						dfToDate.setEnabled(true);
					}else{
						dfToDate.setEnabled(false);
					}
				}
			});
			this.add(chkDenNgay);
			dfToDate = new DscDateField();
			dfToDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
			dfToDate.getDateChooser().setLocale(new Locale("en"));
			dfToDate.getTextField().setText("");
			dfToDate.setEnabled(false);
			dfToDate.getTextField().setDisabledBackground(Color.DARKGRAY);
			this.add(dfToDate);
			btnSearch = new Button();
			btnSearch.setStyleName("Default.ToolbarButton");
			btnSearch.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQuery.gif"));
			btnSearch.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQueryD.gif"));
			btnSearch.setToolTipText("Tìm kiếm");
			btnSearch.addActionListener(new ActionListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e) {
					doSearch();
				}
			});
			this.add(btnSearch);
		}

		protected void doSearch() {
			//validate
			String empsn1 = txtSothe.getText();
			if (empsn1.equals("")) return;
			if (!empsn1.matches("[0-9]{8}")){
				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
				txtSothe.requestFocus();
				return;
			}
			
			if (chkDenSoThe.isSelected()){
				String empsn2 = txtSothe2.getText();
				if (!empsn2.matches("[0-9]{8}")){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ 2 không hợp lệ");
					return;
				}
				String e1 = StringUtils.substring(empsn1, 0, 4);
				String e2 = StringUtils.substring(empsn2, 0, 4);
				Integer e12 = Integer.parseInt(StringUtils.substring(empsn1, 4, 8));
				Integer e22 = Integer.parseInt(StringUtils.substring(empsn2, 4, 8));
				if(!e1.equals(e2)){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "6 số đầu của số thẻ 1 phải giống với 6 số đầu của số thẻ 2");
					return;
				}
				if (e22<=e12){
					Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ 2 phải có giá trị lớn hơn số thẻ 1");
					return;
				}
			}
			
//			if (dfFromDate.getText().equals("")){
//				Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Chọn ngày");
//				return;
//			}else{
				if (!dfFromDate.getText().equals("")&&chkDenNgay.isSelected()&&!dfToDate.getText().equals("")){
					if (dfFromDate.getSelectedDate().compareTo(dfToDate.getSelectedDate())>=0){
						Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Ngày 1 phải nhỏ hơn ngày 2");
						return;
					}
				}
//			}
			//end validate
			//criteria query
			List<String> emps = new ArrayList<String>();
			emps.add(txtSothe.getText());
			if (chkDenSoThe.isSelected()){
				Integer e1 = Integer.parseInt(txtSothe.getText());
				Integer e2 = Integer.parseInt(txtSothe2.getText());
				for (int i=e1+1;i<=e2;i++){
					String emp = FvStringUtils.fixEmpsn(i);
					emps.add(emp);
				}
			}
			List<Date> dates = null;
			Date d1 = null;
			Date d2 = null;
			if (!dfFromDate.getText().equals("")){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					d1 = sdf.parse(dfFromDate.getText());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				dates = new ArrayList<Date>();
				dates.add(d1);
				if (chkDenNgay.isSelected()&&!dfToDate.getText().equals("")){
					try {
						d2 = sdf.parse(dfToDate.getText());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					Calendar c1 = Calendar.getInstance();
					c1.setTime(d1);
					c1.add(Calendar.DAY_OF_MONTH, 1);
					Calendar c2 = Calendar.getInstance();
					c2.setTime(d2);
					while (c2.compareTo(c1)>=0){
						Date date = new Date();
						date.setTime(c1.getTimeInMillis());
						dates.add(date);
						c1.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
			}
			Set<String> eemps = new HashSet<String>();
			for (String empsn:emps){
				if (!FvGenericDAO.getInstanse().isWorking(empsn, true)){
					continue;
				}
				if (dates!=null){
				for (Date date:dates){
						HRUtils util = ApplicationHelper.getHRUtils();
						if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
							continue;
						}
						
						eemps.add(empsn);
					}
				}else{
					eemps.add(empsn);
				}
			}
			String sql = "o.EMPSN in " + DbUtils.parseInStringParamValues(eemps);
			Object[] params = new Object[]{};
			if (!dfFromDate.getText().equals("")){
				if (chkDenNgay.isSelected()&&!dfToDate.getText().equals("")){
					sql = sql + " and o.DATE_LOST>=? and o.DATE_LOST<=?";
					params = new Object[]{d1, d2};
				}else{
					sql = sql + " and o.DATE_LOST=?";
					params = new Object[]{d1};
				}
			}
			ProgramCondition pc = new ProgramCondition(sql, params);
			program.setQueryCondition(pc);
			program.refresh();
		}
	}
}
