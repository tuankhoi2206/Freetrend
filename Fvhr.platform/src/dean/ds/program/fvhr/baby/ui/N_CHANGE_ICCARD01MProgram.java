package ds.program.fvhr.baby.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.baby.tools.DateTools;
import ds.program.fvhr.baby.tools.ProcessProcedure;
import ds.program.fvhr.domain.N_CHANGE_ICCARD;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_IC_CARD;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.ProgramManager;
import dsc.echo2app.program.QueryPane;
import fv.util.JdbcDAO;
import fv.util.Vni2Uni;

public class N_CHANGE_ICCARD01MProgram extends MaintainSProgram {

	private static final long serialVersionUID = 1L;
	private Button btn_EMP_ICCARD;
	private Button btn_IC_CARD;
	private SplitPane splitHorizon, splitVertical;
	private static List_EmployeeProgram browserContentEmployee;
	private static int datamode;
	private IGenericDAO<N_IC_CARD, String> daoIC;
	private IGenericDAO<N_EMP_ICCARD, String> daoEmpIc;
	private IGenericDAO<N_EMPLOYEE, String> daoEmployee;
	private JdbcDAO dao;
	private boolean delete_action = false;

	@Override
	protected void createDataContent() {
		setMasterDataContent(new N_CHANGE_ICCARD01MDataContent());
	}

	private void inputActionDaily(String action, String empsn, String note) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("IDUser", Application.getApp().getLoginInfo().getUserID());
		maps.put("TableName", "N_CHANGE_ICCARD");
		maps.put("ActionName", action);
		maps.put("EMPSN", empsn);
		maps.put("Note", Vni2Uni.convertToVNI(note));
		ProcessProcedure.inputActionDaily();
		ProcessProcedure.storedProcedure.execute(maps);
	}

	@Override
	protected boolean saveToDataObjectSet() {
		// TODO Auto-generated method stub
		return super.saveToDataObjectSet();
	}

	@Override
	protected boolean checkDataObject() {
		// TODO Auto-generated method stub
		return super.checkDataObject();
	}

	@Override
	public N_CHANGE_ICCARD01MDataContent getMasterDataContent() {
		// TODO Auto-generated method stub
		return (N_CHANGE_ICCARD01MDataContent) super.getMasterDataContent();
	}

	@Override
	protected void saveUIToDataObject() {
		// TODO Auto-generated method stub
		super.saveUIToDataObject();
	}

	@Override
	protected int doInit() {
		try {
			int ret = super.doInit();
			getMasterToolbar().setbtnBrowserVisible(false);
			getMasterToolbar().setbtnExportVisible(false);
			getMasterToolbar().setbtnEmailVisible(false);
			getMasterToolbar().setbtnQueryQBEVisible(false);
			getMasterToolbar().setbtnQueryNormalVisible(false);
			getMasterToolbar().setbtnCancelConfirmVisible(false);
			getMasterToolbar().setbtnConfirmVisible(false);
			getMasterToolbar().setbtnContentVisible(false);
			getMasterToolbar().setbtnEditVisible(false);

			dao = new JdbcDAO();
			daoEmployee = Application.getApp().getDao(N_EMPLOYEE.class);
			daoEmpIc = Application.getApp().getDao(N_EMP_ICCARD.class);
			daoIC = Application.getApp().getDao(N_IC_CARD.class);

			btn_EMP_ICCARD = new Button();
			btn_EMP_ICCARD.setIcon(new ResourceImageReference(
					"/dsc/echo2app/resource/image/vu/btnICCard.png"));
			btn_EMP_ICCARD.setToolTipText("Quản lý nhân viên sử dụng IC");
			btn_EMP_ICCARD.setInsets(new Insets(20, 0, 0, 0));
			btn_EMP_ICCARD.addActionListener(new ActionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					EMP_ICCARDProgram emp_ic_cardProgram = new EMP_ICCARDProgram();
					getMasterDataContent().getApplicationInstance()
							.getDefaultWindow().getContent()
							.add(emp_ic_cardProgram);
				}
			});

			if (Application.getApp().getLoginInfo().getUserID().equals("ADMIN"))
				btn_EMP_ICCARD.setEnabled(true);
			else
				btn_EMP_ICCARD.setEnabled(false);

			btn_IC_CARD = new Button("IC Card");
			btn_IC_CARD.setRolloverBorder(new Border(new Extent(1, Extent.PX),
					new Color(0xc0c0c0), Border.STYLE_OUTSET));
			btn_IC_CARD.setRolloverEnabled(true);
			btn_IC_CARD.setToolTipText("Quản lý thẻ IC");
			btn_IC_CARD.setInsets(new Insets(20, 0, 0, 0));
			btn_IC_CARD.addActionListener(new ActionListener() {

				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					IC_CARDProgram ic_cardProgram = new IC_CARDProgram();
					getMasterDataContent().getApplicationInstance()
							.getDefaultWindow().getContent()
							.add(ic_cardProgram);
				}
			});

			getMasterToolbar().add(btn_IC_CARD);
			getMasterToolbar().add(btn_EMP_ICCARD);

			// Tạo split chia đôi theo chiều dọc
			splitHorizon = new SplitPane();
			splitHorizon.setSeparatorHorizontalImage(new FillImage(
					new ResourceImageReference(
							"/dsc/echo2app/resource/image/SplitHerzBar.png")));

			// tao split chia đôi theo chiều ngang
			splitVertical = new SplitPane();
			splitVertical.setSeparatorVerticalImage(new FillImage(
					new ResourceImageReference(
							"/dsc/echo2app/resource/image/SplitVertBar.png")));

			// Khởi tạo phần List_EmployeeProgram (đây là 1 browserContent)
			browserContentEmployee = new List_EmployeeProgram();
			browserContentEmployee.setBackground(new Color(242, 251, 255));
			if (browserContentEmployee.doInit() != RET_OK)
				return RET_DBERROR;

			// mainSplitPane
			mainSplitPane.setBackground(new Color(252, 254, 241));

			// ProgramCondition dùng để đưa câu truy vấn mặc định vào
			// doQueryNormal
			// của BrowserContent
			setQueryCondition(new ProgramCondition("1<>1"));

			// Sự kiện thi thay đổi item trong table BrowserContentEmployee
			browserContentEmployee.getTable().addSelectChangeActionListener(
					new ActionListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent e) {
							doBrowserContentEmployeeSelectChange();
						}
					});
			browserContentEmployee.setSelectedChangeItem(new ActionListener() {
				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					doBrowserContentEmployeeSelectChange();
				}
			});

			// đây là browserContent của N_CHANGE_ICCARDProgram
			this.getBrowserContent().addSelectChangeActionListener(
					new ActionListener() {
						/**
					 * 
					 */
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent e) {
							int recNo = getBrowserContent()
									.getCurrentSelectRowNo();
							doDataContentRefresh(recNo);
						}
					});
			return super.RET_OK;
		} catch (Exception ex) {
			return super.RET_GENERROR;
		}
	}

	public static void loadbrowserEmployee(String condition, Object... params) {
		browserContentEmployee.getTable().getDataObjectSet()
				.query(condition, params);
		browserContentEmployee.refresh();
	}

	// doRefresh là sự kiện của btnRefresh trên MasterToolBar
	@Override
	protected void doRefresh() {
		browserContentEmployee.refresh();
	}

	// refresh sự kiện refresh loại toàn bộ 1 windows pane
	@Override
	public int refresh() {
		// TODO Auto-generated method stub
		return super.refresh();
	}

	// Sự kiên save (insert hoặc update) data
	@Override
	public boolean doSave() {
		// TODO Auto-generated method stub
		return super.doSave();
	}

	@Override
	public boolean doDelete() {
		// TODO Auto-generated method stub
		N_CHANGE_ICCARD obj = ((N_CHANGE_ICCARD) getMasterDataContent()
				.getDataObject());

		boolean check = super.doDelete();
		if (check) {
			delete_action = true;
			saveOtherInformation(obj);
		}
		return check;
	}

	private List<N_EMP_ICCARD> getCurrentIC(String empsn) {
		List<N_EMP_ICCARD> list1 = daoEmpIc
				.find(-1,
						"Select t from N_EMP_ICCARD t where t.EMPSN  = ? and t.USE_STATUS = ? order by t.BEGIN_DATE DESC ",
						empsn, "1");
		return list1;
	}

	@SuppressWarnings({ "unchecked", "unused", "deprecation" })
	private void saveOtherInformation(N_CHANGE_ICCARD obj) {

		N_EMP_ICCARD empIc = new N_EMP_ICCARD();
		List<N_EMP_ICCARD> list1 = getCurrentIC(obj.getEMPSN());

		if (!delete_action) {
			if (list1.size() > 0) {
				empIc = list1.get(0);
				empIc.setEND_DATE(DateTools.AddOrMinusDateByDate(
						obj.getDATE_CHANGE(), -1));
				daoEmpIc.update(empIc);
			}
			empIc = new N_EMP_ICCARD(obj.getEMPSN(), obj.getEMPCN_NEW(), "1",
					obj.getDATE_CHANGE(), null);
			daoEmpIc.save(empIc);
			
			N_IC_CARD ictemp = new N_IC_CARD();
			ictemp.setIC_NO(obj.getEMPCN_NEW());
			ictemp.setUSE_STATUS("1");
			daoIC.update(ictemp);
		}
		else{
			empIc = new N_EMP_ICCARD(obj.getEMPSN(), obj.getEMPCN_NEW(), "1",
					obj.getDATE_CHANGE(), null);
			daoEmpIc.delete(empIc);
	
			N_IC_CARD ictemp = new N_IC_CARD();
			ictemp.setIC_NO(obj.getEMPCN_NEW());
			ictemp.setUSE_STATUS("0");
			daoIC.update(ictemp);
		}
		String empcnCurrent ="";
		if(getCurrentIC(obj.getEMPSN()).size()>0)
		{
			empcnCurrent = getCurrentIC(obj.getEMPSN()).get(0).getEMPCN();
			Date dateTemp = new Date(); 
			double updateTime = obj.getDATE_CHANGE().getTime()-dateTemp.getTime();
			dao.getJdbcTemplate().update("Update N_EMPLOYEE e set  e.EMPCN = ? where e.EMPSN = ?"
					,new Object[]{empcnCurrent,obj.getEMPSN()});
			//BO SUNG MAY THE KHUON MAT		 
			dao.getJdbcTemplate().update("Update MF_EMPLOYEE e set  e.CARDNO = ?, e.UPDATETIME=? where e.USERNO = ?"
					,new Object[]{empcnCurrent,updateTime,obj.getEMPSN()});
			dao.getJdbcTemplate().update("Update MF_USERS e set  e.CARDNO = ?, e.UPDATETIME=? where e.USERNO = ?"
					,new Object[]{empcnCurrent,updateTime,obj.getEMPSN()});			
		}		
	}

	// Sự kiện sau khi save hoàn tất
	@Override
	protected void saveOK() {
		super.saveOK();
		N_CHANGE_ICCARD obj = ((N_CHANGE_ICCARD) getMasterDataContent()
				.getDataObject());

		if (datamode == IProgram.DATAMODE_NEW) {
			saveOtherInformation(obj);
			inputActionDaily("INSERT", obj.getEMPSN(),
					"Thay đổi số IC cho số thẻ");
		}
		if (datamode == IProgram.DATAMODE_EDIT) {
			inputActionDaily("UPDATE", obj.getEMPSN(),
					"Chỉnh sửa Thay đổi số IC số thẻ");
		}

	}

	// switchContent là sự kiên khi chọn những nút trên masterPage nó sẽ chuyển
	// đổi sang content tương ứng
	@Override
	protected void switchContent(int view) {
		// TODO Auto-generated method stub
		// super.switchContent(view);
	}

	@Override
	public boolean doEdit() {
		// TODO Auto-generated method stub
		if (super.doEdit()) {
			datamode = IProgram.DATAMODE_EDIT;
			delete_action = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		if (super.doNew()) {
			datamode = IProgram.DATAMODE_NEW;
			delete_action = false;
			return true;
		}
		return false;
	}

	// doUIRefresh là sự kiện refresh lại các component trong window pane
	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		if (getDataMode() == IProgram.DATAMODE_EDIT
				|| getDataMode() == IProgram.DATAMODE_NEW) {
			getBrowserContent().setEnabled(false);
			browserContentEmployee.setEnabled(false);
			getBrowserContent().refresh();

		} else {
			getBrowserContent().setEnabled(true);
			browserContentEmployee.setEnabled(true);
		}
	}

	// doBrowserContentRefresh là sự kiện browserContent khi refresh
	@Override
	protected boolean doBrowserContentRefresh() {
		return super.doBrowserContentRefresh();
	}

	protected void doBrowserContentEmployeeSelectChange() {
		int recNo = browserContentEmployee.getTable().getCurrentSelectRowNo();
		N_EMPLOYEE data = (N_EMPLOYEE) browserContentEmployee
				.getDataSourceTable().getDataObject(recNo);
		if (data != null) {
			ProgramCondition pc = new ProgramCondition("o.EMPSN = ?",
					data.getEMPSN());
			/*
			 * setQueryCondition dùng trong doQueryNormal thay vì tìm kiếm bằng
			 * doQueryNormal thì ta gán nó vào đây
			 */
			this.setQueryCondition(pc);
		} else {
			ProgramCondition pc = new ProgramCondition("o.EMPSN = ''");
			setQueryCondition(pc);
		}
		this.refresh();
	}

	@Override
	protected void doInitProgramOK() {

	}

	@Override
	protected QueryPane createNormalQuery() {
		// hiển thị lên windowPane query
		return new N_CHANGE_ICCARD01MQuery();
	}

	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.setWidth(new Extent(1200));
		windowPane.removeAll(); // xóa hết tất cả các component trong windowPane
		mainSplitPane.removeAll(); // mainSplitPane chứa đựng MasterToolbar,
									// BrowserContent, DataContent
		mainSplitPane.add(getMasterToolbar()); // add MasterToolBar

		// chia đôi windowPane
		splitHorizon.setResizable(true);
		splitHorizon.setSeparatorPosition(new Extent(400));
		windowPane.add(splitHorizon);
		splitHorizon.add(browserContentEmployee); // đưa BrowserContentPanel vào
													// bên trái windowPane
		splitHorizon.add(mainSplitPane); // đưa mainSplitPane vào bên phải
											// windowPane

		// this.removeAll();
		// chia phần mainSlitPanel thành 2 phần Top và Bottom
		splitVertical.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitVertical.setResizable(true);
		splitVertical.setSeparatorPosition(new Extent(100));
		splitVertical.add(this.getMasterDataContent().getMainContentPane());
		splitVertical.add(this.getBrowserContent());
		mainSplitPane.add(splitVertical);
	}

	// doMasterDataSelectChange sự kiện khi chọn data trong browserContent
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		return new String[] { "EMPSN", "EMPCN_NEW", "EMPCN_OLD", "DATE_CHANGE",
				"REASON", "TEMP" };
	}
}
