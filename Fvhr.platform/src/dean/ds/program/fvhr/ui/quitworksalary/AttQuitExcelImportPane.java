package ds.program.fvhr.ui.quitworksalary;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.StopWatch;

import ds.program.fvhr.dao.quitsalary.AttQuitDAO;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.services.validator.AttException;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Grid;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.components.MessagePane;
import fv.util.FvLogger;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.Vni2Uni;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Alignment;

public class AttQuitExcelImportPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private int phase=0;
	private MessagePane msgPane;
	private Grid rootLayout;
	private FileUploadSelect fs;
	private SelectField sfMonth;
	private SelectField sfYear;
	private DscDateField dfDotTV;
	private RadioButton rad1;
	private RadioButton rad2;
	private SplitPane splitPane1;
	private AttQuit01Program program;
	private Button btnExecute;
	private Button btnFind;
	/**
	 * Creates a new <code>AttQuitExcelImportPane</code>.
	 */
	public AttQuitExcelImportPane(AttQuit01Program program) {
		super();

		// Add design-time configured components.
		initComponents();
		
		setWidth(new Extent(500));
		setHeight(new Extent(330));
		
		this.program=program;
		
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getJavaMonthEditor(), true);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1, false), true);
		Calendar cal = Calendar.getInstance();
		ListBinder.refreshIndex(sfMonth, cal.get(Calendar.MONTH));
		sfYear.setSelectedIndex(1);
		dfDotTV.getDateChooser().setLocale(new Locale("en"));
		dfDotTV.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDotTV.setSelectedDate(cal);
		msgPane = new MessagePane();
		splitPane1.add(msgPane);
	}
	
	public boolean validateForm(){
		String month = sfMonth.getSelectedItem().toString();
		String year = sfYear.getSelectedItem().toString();
		program.doCheckAndCreateTable(month, year);
		Calendar c = dfDotTV.getSelectedDate();
		if (phase==0){
			if (fs.getFileWrapper()==null||fs.getFileWrapper().getFile()==null){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,"Chọn file excel");
				return false;
			}else {
				phase++;
				validateForm();
			}
		}else if (phase==1){		
			if (c.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY&&rad1.isSelected()){			
				warning("Đợt thôi việc không phải ngày thứ 2 trong tuần.\r\nTiếp tục cập nhật?");
			}else {
				phase++;
				validateForm();
			}
		}else if (phase==2){
			if (rad2.isSelected()){
				if (c.get(Calendar.MONTH)!=sfMonth.getSelectedIndex()){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,"Bỏ việc: tháng tính lương phải bằng với tháng của đợt thôi việc");
					return false;
				}else {
					phase++;
					validateForm();
				}
			}else{
				phase++;
				validateForm();
			}
		}else if (phase==3){
			if (c.getActualMaximum(Calendar.DAY_OF_MONTH)!=c.get(Calendar.DAY_OF_MONTH)&&rad2.isSelected()){
				warning("Bỏ việc: đợt thôi việc không phải ngày cuối tháng.\r\nTiếp tục cập nhật?");
			}else {
				phase++;
				validateForm();
			}
		}else{
			doImport();
		}
		return false;
	}
	
	
	public void warning(String message){//caller must recursive
		MessageDialog dlg = new MessageDialog(MessageDialog.TYPE_WARNING+MessageDialog.CONTROLS_YES_NO, message);
		dlg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {			
				if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
					phase++;
					validateForm();					
				}
			}
		});		
	}

	protected void execute(ActionEvent e) {
		validateForm();
	}
	
	private void doImport(){
		StopWatch sw = new StopWatch();
		sw.start();
		msgPane.clearMessage();
		try {
			FileWrapper fw = fs.getFileWrapper();
			msgPane.addMessage(fw.getName(), MessagePane.MSG_INFO);
			String month = sfMonth.getSelectedItem().toString();
			String year = sfYear.getSelectedItem().toString();
			if (program.getValidator()==null) throw new NullPointerException("validator is null");			
			AttQuitWorkbook attwb = new AttQuitWorkbook(fw.getFile(), program.getValidator(), month, year);
			saveAttQuitData(attwb, month, year);
			fs.doSelectNextFile(null);
			fs.refresh();
		} catch (IOException e1) {
			msgPane.addMessage(e1.getMessage(), MessagePane.MSG_ERROR);
		} catch (AttException e2) {
			msgPane.addMessage(e2.getMessage(), MessagePane.MSG_ERROR);
		}
		sw.stop();
		msgPane.addMessage("Elapse time: " + (float) sw.getTime()
				/ 1000 + "s", MessagePane.MSG_INFO);
		/////////////////////////
		phase=0;
	}
	
	protected int saveAttQuitData(AttQuitWorkbook attwb, String month, String year) {
		Integer[] range = attwb.getRange();
		msgPane.addMessage("Cập nhật dữ liệu từ dòng " + (range[0] + 1)	+ " đến dòng " + range[1], MessagePane.MSG_INFO);
		ArrayList<AttQuitInitData> listInitData = new ArrayList<AttQuitInitData>();
		AttQuitDAO dao = program.getDao();
		dao.setMonth(month);
		dao.setYear(year);
		dao.setTable("ATTENDANTDB_QUIT_"+month+year);
		String type = rad1.isSelected()?null:"BV";
		for (int i = range[0]; i < range[1]; i++) {
			ATTENDANTDB_QUIT att = dao.getInitAttendantDbQuitData(attwb.getStringValue(i, "EMPSN"));
			att.setCLASS(type);
			att.setEMPNA(Vni2Uni.convertToVNI(att.getEMPNA()));
			att.setBSALY(attwb.getNumericValue(i, "BSALY"));
			att.setDEPT_KT(attwb.getDeptKt(i));
			att.setCOMBSALY(attwb.getNumericValue(i, "COMBSALY"));
			att.setBONUS1(attwb.getNumericValue(i, "BONUS1"));
			att.setBONUS2(attwb.getNumericValue(i, "BONUS2"));
			att.setBONUS3(attwb.getNumericValue(i, "BONUS3"));
			att.setBONUS4(attwb.getBonus4(i));
			att.setBONUS5(attwb.getNumericValue(i, "BONUS5"));
			att.setBONUS8(attwb.getNumericValue(i, "BONUS8"));
			att.setBORM(attwb.getNumericValue(i, "BORM"));
			att.setKQT(attwb.getNumericValue(i, "KQT"));
			att.setJOINLUM(attwb.getNumericValue(i, "JOINLUM"));
			att.setYLBX(attwb.getNumericValue(i, "YLBX"));
			att.setJOININSU(attwb.getNumericValue(i, "JOININSU"));
			att.setBH_TNGHIEP(attwb.getNumericValue(i, "BH_TNGHIEP"));
			att.setBONUS9(attwb.getNumericValue(i, "BONUS9"));
			att.setTEMP1(attwb.getNumericValue(i, "TEMP1"));
			att.setTEMP2(attwb.getNumericValue(i, "TEMP2"));
			att.setBAC(attwb.getNumericValue(i, "BAC"));
			att.setBSAL_AVG(attwb.getNumericValue(i, "BSALY_AVG"));
			att.setBONUS2_AVG(attwb.getNumericValue(i, "BONUS2_AVG"));
			att.setMM_DENBU(attwb.getNumericValue(i, "M_DENBU"));
			att.setREST_REMAIN(attwb.getNumericValue(i, "PN_CONLAI"));
			att.setMM_BONUS(attwb.getNumericValue(i, "M_TROCAP"));
			att.setDAY_BEFORE(attwb.getNumericValue(i, "D_NGHITRUOC"));
			att.setBU_BHYT(attwb.getNumericValue(i, "BU_BH"));
			att.setTHU_BHYT(attwb.getNumericValue(i, "THU_BH"));
			att.setBUTHU_BHYT(attwb.getNumericValue(i, "BUTHU_BH"));
			att.setNOTE_BH(attwb.getNoteBh(i));
			att.setDOT_TV(dfDotTV.getSelectedDate().getTime());
			att.setADDCLS1_O(attwb.getNumericValue(i, "ADDCLS1_O"));
			att.setNADDCLS_O(attwb.getNumericValue(i, "NADDCLS_O"));
			att.setADDHOL_O(attwb.getNumericValue(i, "ADDHOL_O"));
			att.setADDHOLN_O(attwb.getNumericValue(i, "ADDHOLN_O"));
			att.setACNM_O(attwb.getNumericValue(i, "ACNM_O"));
			att.setDEPSN_BHYT(attwb.getStringValue(i, "DEPSN_BHYT"));
			att.setBSALY_LAST_MONTH(attwb.getNumericValue(i, "BSALY_LAST_MONTH"));
			BigDecimal ttTet = attwb.getNumericValue(i, "BONUS1_HOL");
			if (ttTet.compareTo(BigDecimal.ZERO)>0){
				att.setBONUS1_HOL(ttTet);
			}
			String poss = attwb.getStringValue(i, "POSSN");
			if (poss==null) poss="";
			else poss=poss.trim();
			String atm = attwb.getStringValue(i, "ATM");
			if (atm!=null) atm=atm.trim(); else atm="";
			if (!"".equals(atm)){
				poss = poss + "_" + atm;
			}
			att.setPOSSN(poss);
			
			AttQuitInitData initAtt = new AttQuitInitData();
			initAtt.setEmpsn(att.getEMPSN());
			initAtt.setEmpna(att.getEMPNA());
			initAtt.setHired(att.getHIRED());
			initAtt.setDateOff(att.getDATE_OFF());
			initAtt.setPossn(att.getPOSSN());
			initAtt.setDotTv(att.getDOT_TV());
			initAtt.setDeptKt(att.getDEPT_KT());
			initAtt.setDepsn(att.getDEPSN());
			initAtt.setNoteBh(att.getNOTE_BH());
			listInitData.add(initAtt);
			
			//save attendantdb
			dao.saveAttentdantData(att);
		}
		Collections.sort(listInitData, ATTQUIT_COMPARATOR);
		int stt = 1;
		for (AttQuitInitData att : listInitData) {
			att.setStt(BigDecimal.valueOf(stt++));
			dao.saveInitAttData(att);
			dao.doCalculate(att.getEmpsn(), att.getDotTv(), month, year, type);
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setACTIONNAME("IMPORT");
			action.setEMPSN(att.getEmpsn());
			action.setTABLENAME("ATTQUIT"+month+year);
			FvLogger.log(action);
		}
		msgPane.addMessage("Cập nhật thành công", MessagePane.MSG_SUCCESS);
		return 0;
	}
	
	private static final Comparator<AttQuitInitData> ATTQUIT_COMPARATOR = new Comparator<AttQuitInitData>() {

		@Override
		public int compare(AttQuitInitData o1, AttQuitInitData o2) {
			return o1.getEmpsn().compareTo(o2.getEmpsn());
		}
	};
	
	private void showSearchForm(ActionEvent e) {
		program.showSearchPane(sfMonth.getSelectedIndex(), (Integer)ListBinder.get(sfYear), dfDotTV.getSelectedDate(), rad1.isSelected());
		userClose();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Cập nhật lương từ file excel");
		this.setModal(true);
		splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(160, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setInsets(new Insets(new Extent(3, Extent.PX)));
		splitPane1.add(rootLayout);
		Label label1 = new Label();
		label1.setText("Chọn file excel");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label1.setLayoutData(label1LayoutData);
		rootLayout.add(label1);
		fs = new FileUploadSelect();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fs.setIcon(imageReference1);
		fs.setFileType("application/vnd.ms-excel");
		fs.setIconWidth(new Extent(24, Extent.PX));
		fs.setUploadFileSize(20971520);
		fs.setIconHeight(new Extent(24, Extent.PX));
		rootLayout.add(fs);
		Label label2 = new Label();
		label2.setText("Tính lương tháng");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label2.setLayoutData(label2LayoutData);
		rootLayout.add(label2);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(1, Extent.PX));
		rootLayout.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label3 = new Label();
		label3.setText("Năm");
		row1.add(label3);
		sfYear = new SelectField();
		row1.add(sfYear);
		Label label5 = new Label();
		label5.setText("Đợt thôi việc");
		GridLayoutData label5LayoutData = new GridLayoutData();
		label5LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label5.setLayoutData(label5LayoutData);
		rootLayout.add(label5);
		dfDotTV = new DscDateField();
		rootLayout.add(dfDotTV);
		Label label6 = new Label();
		rootLayout.add(label6);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(3, Extent.PX));
		rootLayout.add(row2);
		rad1 = new RadioButton();
		rad1.setText("Thôi việc");
		rad1.setSelected(true);
		ButtonGroup ml = new ButtonGroup();
		rad1.setGroup(ml);
		row2.add(rad1);
		rad2 = new RadioButton();
		rad2.setText("Bỏ việc");
		rad2.setGroup(ml);
		row2.add(rad2);
		Label label4 = new Label();
		rootLayout.add(label4);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(3, Extent.PX));
		rootLayout.add(row3);
		btnExecute = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau_hover.gif");
		btnExecute.setRolloverBackgroundImage(new FillImage(imageReference2));
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau_press.gif");
		btnExecute.setPressedBackgroundImage(new FillImage(imageReference3));
		btnExecute.setHeight(new Extent(26, Extent.PX));
		btnExecute.setWidth(new Extent(87, Extent.PX));
		btnExecute.setPressedEnabled(true);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/batdau.gif");
		btnExecute.setBackgroundImage(new FillImage(imageReference4));
		btnExecute.setToolTipText("Bắt đầu import dữ liệu và tính lương");
		btnExecute.setRolloverEnabled(true);
		btnExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				execute(e);
			}
		});
		row3.add(btnExecute);
		btnFind = new Button();
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnFormSearch.gif");
		btnFind.setIcon(imageReference5);
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnFormSearch_hover.gif");
		btnFind.setRolloverIcon(imageReference6);
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnFormSearch_press.gif");
		btnFind.setPressedIcon(imageReference7);
		btnFind.setPressedEnabled(true);
		btnFind.setToolTipText("Tìm kiếm");
		btnFind.setRolloverEnabled(true);
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSearchForm(e);
			}
		});
		row3.add(btnFind);
	}
}
