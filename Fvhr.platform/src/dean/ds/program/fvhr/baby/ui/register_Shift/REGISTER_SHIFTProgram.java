package ds.program.fvhr.baby.ui.register_Shift;

import it.businesslogic.ireport.gui.MessageBox;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JFormattedTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import com.sun.star.util.DateTime;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.baby.tools.CheckRule;
import ds.program.fvhr.baby.tools.DateTools;
import ds.program.fvhr.baby.ui.List_EmployeeProgram;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_EALRY_AFTER_B;
import ds.program.fvhr.domain.N_EALRY_BEFOR_B;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_REGISTER_OVERTIME;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_TRAINING_DETAILS;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import echopointng.ButtonEx;
import fv.util.FvLogger;
import fv.util.JdbcDAO;


@SuppressWarnings("unused")
public class REGISTER_SHIFTProgram extends MaintainSProgram {

	private SplitPane splitHorizon, splitVertical;
	public static EmployeeBrowserContent browserContentEmployee;
	private IGenericDAO<N_EMPLOYEE, String> daoEmployee;
	private REGISTER_SHIFTQuery query;
	private int datamode;
	private JdbcDAO dao;
	private ProgramCondition pc_temp;
	IGenericDAO<N_REGISTER_SHIFT, String> daoShift;
	String user_up 				= Application.getApp().getLoginInfo().getUserID();
	String ma_user 				= "";
	String actionName			="";
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static boolean check_hs; // if = true la doi ca lam viec tren quan ly ho so
	public static boolean check_vs; // if = true la dang ve som con nho
	IGenericDAO<N_EALRY_AFTER_B, String> daoN_EALRY_AFTER_B=Application.getApp().getDao(N_EALRY_AFTER_B.class);
	IGenericDAO<N_EALRY_BEFOR_B, String> daoN_EALRY_BEFOR_B=Application.getApp().getDao(N_EALRY_BEFOR_B.class);
	
	@Override
	protected void createDataContent() {
		setMasterDataContent(new REGISTER_SHIFTDataContent());
	}
	@Override
	protected int doInit() {
		int ret = super.doInit();
		getMasterToolbar().setbtnBrowserVisible(false);
		getMasterToolbar().setbtnExportVisible(false);
		getMasterToolbar().setbtnEmailVisible(false);
		getMasterToolbar().setbtnQueryQBEVisible(false);
		getMasterToolbar().setbtnQueryNormalVisible(true);
		getMasterToolbar().setbtnCancelConfirmVisible(false);
		getMasterToolbar().setbtnConfirmVisible(false);
		getMasterToolbar().setbtnContentVisible(false);
		dao = new JdbcDAO();
		daoShift = Application.getApp().getDao(N_REGISTER_SHIFT.class);
		daoEmployee = Application.getApp().getDao(N_EMPLOYEE.class);
		splitHorizon = new SplitPane();
		splitHorizon.setSeparatorHorizontalImage(new FillImage(
				new ResourceImageReference(
						"/dsc/echo2app/resource/image/SplitHerzBar.png")));
		splitVertical = new SplitPane();
		splitVertical.setSeparatorPosition(new Extent(700, Extent.PX));
		splitVertical.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitVertical.setSeparatorVerticalImage(new FillImage(
				new ResourceImageReference(
						"/dsc/echo2app/resource/image/SplitVertBar.png")));

		ProgramCondition basePC = new ProgramCondition(
				"o.EMPSN in (select t.EMPSN from N_EMPLOYEE t where t.EMPSN = o.EMPSN and t.USER_MANAGE_ID in ("
						+ CheckRule.listManager() + "))");
		browserContentEmployee = new EmployeeBrowserContent();
		browserContentEmployee.setBackground(new Color(242, 251, 255));
		browserContentEmployee.setBaseCondition(basePC);
		setQueryCondition(new ProgramCondition("1<>1"));
		browserContentEmployee.setQueryCondition(new ProgramCondition("1<>1"));
		browserContentEmployee.getBrowserContent()
				.addSelectChangeActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						doBrowserContentEmployeeSelectChange();
					}
				});
		browserContentEmployee.setSearchFoundListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBrowserContentEmployeeSelectChange();
			}
		});
		getBrowserContent().addSelectChangeActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int recNo = getBrowserContent().getCurrentSelectRowNo();
				doDataContentRefresh(recNo);
			}
		});
		return ret;
	}
	@Override
	protected void switchContent(int view) {
		// TODO Auto-generated method stub
		// super.switchContent(view);
	}
	@Override
	protected boolean delete(int recNo) {						
			actionName ="DELETE";	
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
			ma_user = Data_DSPB02.getPB_USERNO();		
			// Lay tu data dang thao tac
			N_REGISTER_SHIFT empShift = (N_REGISTER_SHIFT) this.getBrowserContent()
					.getDataObjectSet().getDataObject();		
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setIDUSER(ma_user);
			action.setTABLENAME("N_REGISTER_SHIFT");
			action.setEMPSN(empShift.getEMPSN().toString());
			action.setACTIONNAME(actionName);
			action.setNOTE("Xoa ca lam viec "+empShift.getID_SHIFT()+", ngay"+ sdf.format(empShift.getSHIFT_DATE()));
			FvLogger.log(action);				
			return	super.delete(recNo);
	}
	@Override
	protected boolean doDataContentRefresh(int recNo) {
		// TODO Auto-generated method stub
		return super.doDataContentRefresh(recNo);
	}
	public void doBrowserContentEmployeeSelectChange() {
		int recNo = browserContentEmployee.getBrowserContent()
				.getCurrentSelectRowNo();
		N_EMPLOYEE data = (N_EMPLOYEE) browserContentEmployee
				.getDataObjectSet().getDataObject(recNo);
		if (data != null) {
			ProgramCondition pc = new ProgramCondition("o.EMPSN=?",
					new Object[] { data.getEMPSN() });
			setQueryCondition(pc);

		} else {
			ProgramCondition pc = new ProgramCondition("o.EMPSN=''");
			setQueryCondition(pc);
		}
		refresh();
	}
	@Override
	protected boolean doBrowserContentRefresh() {
		// TODO Auto-generated method stub
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
			if ((queryString.length() > 0)
					&& (getQueryCondition().condition.length() > 0)) {
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
			logger.info("QUERY:" + queryString + ", ["
					+ Arrays.toString(paramList.toArray()) + "]");
		}
		if (getMasterDataContent().getDataObjectSet().query(queryString,
				paramList.toArray(new Object[paramList.size()])) < 0) {
			MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_ERROR
					+ MessageDialog.CONTROLS_OK, getMasterDataContent()
					.getDataObjectSet().getErrorMessage());
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("fetch record count:"
					+ getMasterDataContent().getDataObjectSet()
							.getRecordCount());
		}
		getBrowserContent().refresh();
		getBrowserContent().setCurrentPage(0);
		if (getMasterDataContent().getDataObjectSet().getRecordCount() > 0) {

			if (getBrowserContent().getCurrentSelectRowNo() != -1
					&& getBrowserContent().getCurrentSelectRowNo() < getBrowserContent()
							.getDataObjectSet().getRecordCount()) {
				getBrowserContent().setSelectedIndex(
						getBrowserContent().getCurrentSelectRowNo(), true);
			} else {
				getBrowserContent().setSelectedIndex(0, true);
			}
			doMasterDataSelectChange();
		} else {
			getBrowserContent().getBrowserTable().getSelectionModel()
					.clearSelection();

		}
		return true;
	}
	public void doSaveShift_hs(String strShift)	
	{
	        	
				int rec = browserContentEmployee.getDataObjectSet()
						.getRecordCount();
				N_ACTION_DAILY action=new N_ACTION_DAILY();
				for (int i = 0; i < rec; i++)
				{					
					String empsn = ((N_EMPLOYEE) browserContentEmployee
							.getDataObjectSet().getDataObject(i))
							.getEMPSN();
					IGenericDAO<N_EMPLOYEE, String> daoEmp=Application.getApp().getDao(N_EMPLOYEE.class);
					List<N_EMPLOYEE> listemp=daoEmp.find(1,"from N_EMPLOYEE where EMPSN='"+empsn+"'");
					if(listemp.size()>0)
					{
						N_EMPLOYEE data=listemp.get(0);
						if(checkupdateShift_hs(data.getEMPSN()))
						{
					     data.setSHIFT(strShift);
					     daoEmp.update(data);
					     action.setIDUSER(Application.getApp().getLoginInfo().getUserID());
						 action.setTABLENAME("N_EMPLOYEE");
						 action.setEMPSN(data.getEMPSN());
						 FvLogger.log(action);
						}	
					}
				
				}
	}
	private boolean checkupdateShift_hs(String strEmpsn)
	{
		Date dtDate=new Date();
		check_hs=true;
		String strDateNow=sdf.format(dtDate);			
		// kiem tra lam viec 7h, if VS chi the hien canh bao, van cho luu tren N_EMPLOYEE	
		
		List<N_EALRY_AFTER_B> listN_EALRY_AFTER_B=daoN_EALRY_AFTER_B.find(100,"from N_EALRY_AFTER_B where EMPSN ='"+strEmpsn+"' AND  B_DATES<=TO_DATE('"+strDateNow+"','DD/MM/YYYY')" +
						"AND E_DATES>=TO_DATE('"+strDateNow+"','DD/MM/YYYY')");
		if(listN_EALRY_AFTER_B.size()>0)
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ '"+strEmpsn+"' đang đăng ký ca về sớm 7h!");
		    return check_hs;
		}
		List<N_EALRY_BEFOR_B> listN_EALRY_BEFOR_B=daoN_EALRY_BEFOR_B.find(100,"from N_EALRY_BEFOR_B where EMPSN ='"+strEmpsn+"' AND  B_DATES<=TO_DATE('"+strDateNow+"','DD/MM/YYYY')" +
						"AND E_DATES>=TO_DATE('"+strDateNow+"','DD/MM/YYYY')");
		if(listN_EALRY_BEFOR_B.size()>0)
		{
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ '"+strEmpsn+"' đang đăng ký ca về sớm 7h!");
		    return check_hs;
		}
		else
			return check_hs;
	}
	@Override
	protected QueryPane createNormalQuery() {
		if (query == null)
			query = new REGISTER_SHIFTQuery();
		return query;
	}
	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.setWidth(new Extent(1200));
		windowPane.removeAll();
		mainSplitPane.removeAll();
		mainSplitPane.add(getMasterToolbar());
		// chia đôi windowPane
		splitHorizon.setResizable(true);
		splitHorizon.setSeparatorPosition(new Extent(250));
		windowPane.add(splitHorizon);
		splitHorizon.add(browserContentEmployee);
		splitHorizon.add(mainSplitPane);
		splitVertical.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitVertical.setResizable(true);
		splitVertical.setSeparatorPosition(new Extent(250));
		splitVertical.add(this.getMasterDataContent().getMainContentPane());
		splitVertical.add(this.getBrowserContent());
		mainSplitPane.add(splitVertical);
	}

	public void doMasterQuery(ProgramCondition pc) {
		browserContentEmployee.setQueryCondition(pc);
		pc_temp = pc;
		browserContentEmployee.refresh();
		if (browserContentEmployee.getDataObjectSet().getRecordCount() > 0) {
			doBrowserContentEmployeeSelectChange();
			int recNo = getBrowserContent().getCurrentSelectRowNo();
			if (recNo != -1)
				doDataContentRefresh(recNo);
		} else {
			getMasterDataContent().getDataObjectSet().clearAll();
			getBrowserContent().refresh();
			doUIRefresh();		
			getMasterDataContent().getUIDataObjectBinder().bindDataToFields(
					new N_REGISTER_SHIFT());
			getMasterDataContent().clearAllComponentValue();
		}
	}

	private N_REGISTER_SHIFT getDataObject() {
		return (N_REGISTER_SHIFT) getMasterDataContent().getDataObject();

	}

	@Override
	public boolean doSave() {
		// if cap nhat ca lam viec tren quan ly ho so
		// gan ve som con nho
		check_vs=false;
		check_hs=false;
	   System.out.println(check_hs);	   
		if (check_hs){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Bạn vừa cập nhập ca LV trong HS nên không thể đăng ký ca cho nhân viên này! \n "+
																																														". Vui lòng bỏ chọn cập nhật ca làm việc trên hồ sơ để đăng ký ca làm việc mới.");
			 return false;
		}		
		else		
			// else la dk ca lam viec
		{
			this.saveUIToDataObject();			
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
			ma_user = Data_DSPB02.getPB_USERNO();
			if (!this.checkDataObject()) {
				return false;
			}
	
			beforeSaveToDataObjectSet();
	
			if (!saveToDataObjectSet()) {
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
						this.getErrorMessage());
				return false;
			}
			
			N_REGISTER_SHIFT obj = new N_REGISTER_SHIFT();
			N_REGISTER_SHIFT obj_temp = (N_REGISTER_SHIFT) getMasterDataContent()
					.getDataObject();
			Calendar calto = REGISTER_SHIFTDataContent.dateTo;
			Calendar calfrom = Calendar.getInstance();
			Date date = obj_temp.getSHIFT_DATE();
			calfrom.setTime(date);
			int dayRanger = DateTools.subtractDate(calto, calfrom);
					try {
						obj = obj_temp;
						int rec = browserContentEmployee.getDataObjectSet()
								.getRecordCount();
						Date date_temp = obj_temp.getSHIFT_DATE();						
						String tuNgay =  sdf.format(calfrom.getTime());							
						InsuranceDAO ins = new InsuranceDAO();
						
						for (int d = 0; d <= dayRanger; d++) {					
							obj.setSHIFT_DATE(DateTools.AddOrMinusDateByDate(date_temp, d));
							tuNgay = sdf.format(obj.getSHIFT_DATE());
							if (REGISTER_SHIFTDataContent.SaveMultiObject) 
							{																	
								for (int i = 0; i < rec; i++) {									
									String empsn = ((N_EMPLOYEE) browserContentEmployee
											.getDataObjectSet().getDataObject(i))
											.getEMPSN();
									// kiem tra VS con nho, canh bao nhung van cho luu
									check_vs = ins.Check_LamViec7H("N_EALRY_BEFOR_B", empsn, tuNgay);
									if (check_vs==false){
										check_vs = ins.Check_LamViec7H("N_EALRY_AFTER_B", empsn, tuNgay);
									}
									// if la CNV dang VS con nho thi van cho luu but canh bao cho nguoi dung biet
									System.out.println(check_vs);
									if (check_vs){										
										Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK
												, "Đã lưu thành công nhưng số thẻ '"+empsn+"' đang đăng ký ca về sớm 7h ngày "+tuNgay);
									}										
									obj.setEMPSN(empsn);
									daoShift.saveOrUpdate(obj);
								}
							}
							else{
								daoShift.saveOrUpdate(obj);
								// if la CNV dang VS con nho thi van cho luu but canh bao cho nguoi dung biet
								System.out.println(check_vs);
								if (check_vs){										
									Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION + MessageDialog.CONTROLS_OK
											, "Đã lưu thành công nhưng số thẻ '"+obj.getEMPSN()+"' đang đăng ký ca về sớm 7h ngày "+sdf.format(obj.getSHIFT_DATE()));
								}								
							}							
							// luu vao action_daily	
							N_ACTION_DAILY action=new N_ACTION_DAILY();
							action.setIDUSER(ma_user);
							action.setTABLENAME("N_REGISTER_SHIFT");
							action.setEMPSN(obj.getEMPSN().toString());
							action.setACTIONNAME(actionName);
							action.setNOTE("Them moi ca lam viec "+obj.getID_SHIFT()+", ngay "+tuNgay);
							FvLogger.log(action);							
						}
	
					} catch (Exception e) {
						daoShift.delete(obj_temp);
						Application.getApp().showMessageDialog(
								MessageDialog.TYPE_ERROR
										+ MessageDialog.CONTROLS_OK, e.toString());
					}
			
					dataMode_DscQryValidate = DATAMODE_SAVE;
					ProgramCondition pc = new ProgramCondition("o.EMPSN = ?", new Object[]{obj.getEMPSN()});
					setQueryCondition(pc);
					refresh();
					setDataMode(DATAMODE_BROWSER);
					saveOK();
					return true;	
			}
		}
		

	

	@Override
	protected void doMasterDataSelectChange() {
		// TODO Auto-generated method stub
		super.doMasterDataSelectChange();
	}

	@Override
	public boolean doEdit() {
		// TODO Auto-generated method stub
		if (super.doEdit()) {
			datamode = IProgram.DATAMODE_EDIT;
			actionName ="UPDATE";
			return true;
		}
		return false;
	}

	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		if (super.doNew()) {
			datamode = IProgram.DATAMODE_NEW;	
			actionName ="INSERT";
			return true;
		}
		return false;
	}

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

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		browserContentEmployee.refresh();
	}

	@Override
	protected String[] getBrowserDisplayColumns() {
		// TODO Auto-generated method stub
		return new String[] { "EMPSN", "SHIFT_DATE", "ID_SHIFT", "TIME_IN",
				"TIME_OUT", "ID_SPDEPT" };
	}
}
