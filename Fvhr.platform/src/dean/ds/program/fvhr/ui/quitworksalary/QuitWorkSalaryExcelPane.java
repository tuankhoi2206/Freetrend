package ds.program.fvhr.ui.quitworksalary;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;

import org.apache.commons.lang.time.StopWatch;

import ds.program.fvhr.dao.quitsalary.QuitWorkSalaryDAO;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import ds.program.fvhr.services.validator.AttException;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.binder.ListBaseBinder;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.components.MessagePane;
import fv.util.BundleUtils;
import fv.util.MappingPropertyUtils;

public class QuitWorkSalaryExcelPane extends WindowPane {
	private static final long serialVersionUID = 1L;

	private Grid rootLayout;

	private Label lblMonth;

	private Label lblYear;

	private Label lblDate;

	// private Label lblFact;
	private SelectField sfMonth;

	private SelectField sfYear;

	private DscDateField dfDate;

	// private SelectField sfFact;
	private Label lblType;

	private SelectField sfType;

	private Button btnStart;

	private MessagePane msgPane;

	// private FileSelect fs;
	private FileUploadSelect fs;

	private QuitWorkSalaryProgram program;

	private Button btnReview;

	// private ArrayList<AttQuitInitData> listInitData;

	public QuitWorkSalaryExcelPane(QuitWorkSalaryProgram program) {
		super();
		this.program = program;
		initComponents();
		initUI();
	}

	private void initUI() {
		setTitle(BundleUtils.getString("QUITWORK.EXCELPANETITLE"));
		setStyleName("Default.Window");
		setModal(true);
		setWidth(new Extent(550));
		setHeight(new Extent(330));
		MappingPropertyEditor monthEditor = MappingPropertyUtils
		.getJavaMonthEditor();
		int month = Calendar.getInstance().get(Calendar.MONTH);
		ListBaseBinder mBinder = new ListBaseBinder(null, sfMonth, monthEditor);// ListBaseBinder(object
		mBinder.objectToComponent(month);
		MappingPropertyEditor yearEditor = MappingPropertyUtils.getYearEditor(
				1, false);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		ListBaseBinder ybinder = new ListBaseBinder(null, sfYear, yearEditor);
		ybinder.objectToComponent(year);
		ListBaseBinder tBinder = new ListBaseBinder(null, sfType,
				getTypeEditor());
		tBinder.objectToComponent("TV");
		// ///////////////////////////
		btnStart.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				StopWatch sw = new StopWatch();
				sw.start();
				msgPane.clearMessage();
				try {
					FileWrapper fw = fs.getFileWrapper();
					if (fw == null) {
						Application.getApp().showMessageDialog(
								MessageDialog.CONTROLS_OK
								+ MessageDialog.TYPE_ERROR,
								"Chọn file excel");
						return;
					}
					msgPane.addMessage(fw.getName(), MessagePane.MSG_INFO);
					String month = sfMonth.getSelectedItem().toString();
					String year = sfYear.getSelectedItem().toString();
					AttQuitWorkbook attwb = new AttQuitWorkbook(fw.getFile(), null, month, year);

					saveAttQuitData(attwb, month, year);

					fs.doSelectNextFile(null);// clean file
					fs.refresh();
				} catch (IOException e1) {
					msgPane.addMessage(e1.getMessage(), MessagePane.MSG_ERROR);
					e1.printStackTrace();
				} catch (AttException e2) {
					msgPane.addMessage(e2.getMessage(), MessagePane.MSG_ERROR);
					e2.printStackTrace();
				}
				sw.stop();
				msgPane.addMessage("Working time: " + (float) sw.getTime()
						/ 1000 + "s", MessagePane.MSG_INFO);
			}
		});

		btnReview.addActionListener(new ActionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				program.showSearchPane(sfMonth.getSelectedIndex(), 
						Integer.parseInt(sfYear.getSelectedItem().toString()), dfDate.getSelectedDate(), sfType.getSelectedIndex());
//				QuitWorkSearchPane sp = new QuitWorkSearchPane(program);
//				sp.setMonth(sfMonth.getSelectedIndex());
//				sp.setYear(Integer
//				.parseInt(sfYear.getSelectedItem().toString()));
//				sp.setQuitDate(dfDate.getSelectedDate());
//				sp.setType(sfType.getSelectedIndex());
//				Application.getApp().getDefaultWindow().getContent().add(sp);
				userClose();
			}
		});
	}

	protected int saveAttQuitData(AttQuitWorkbook attwb, String month,
			String year) {
		Integer[] range = attwb.getRange();
		msgPane.addMessage("Cập nhật dữ liệu từ dòng " + (range[0] + 1)
				+ " đến dòng " + range[1], MessagePane.MSG_INFO);
		@SuppressWarnings("unchecked")
		IGenericDAO<ATTENDANTDB_QUIT, String> dao = Application.getApp()
		.getDao(ATTENDANTDB_QUIT.class);

		String type = sfType.getSelectedIndex() == 1 ? "BV" : null;
		QuitWorkSalaryDAO qdao = new QuitWorkSalaryDAO(month, year);
		ArrayList<AttQuitInitData> listInitData = new ArrayList<AttQuitInitData>();// info
		// for
		// calculation
		for (int i = range[0]; i < range[1]; i++) {
			ATTENDANTDB_QUIT att = qdao.getAttQuitData(attwb.getStringValue(i,
			"EMPSN"));

			// att.setFACT(sfFact.getSelectedItem().toString());
			att.setCLASS(type);
			// fill data from excel
			att.setBSALY(attwb.getNumericValue(i, "BSALY"));
			att.setDEPT_KT(attwb.getDeptKt(i));
			att.setCOMBSALY(attwb.getNumericValue(i, "COMBSALY"));
			att.setBONUS1(attwb.getNumericValue(i, "BONUS1"));
			att.setBONUS2(attwb.getNumericValue(i, "BONUS2"));
			att.setBONUS3(attwb.getNumericValue(i, "BONUS3"));
			att.setBONUS4(attwb.getNumericValue(i, "BONUS4"));
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
			att.setDOT_TV(dfDate.getSelectedDate().getTime());
			att.setADDCLS1_O(attwb.getNumericValue(i, "ADDCLS1_O"));
			att.setNADDCLS_O(attwb.getNumericValue(i, "NADDCLS_O"));
			att.setADDHOL_O(attwb.getNumericValue(i, "ADDHOL_O"));
			att.setADDHOLN_O(attwb.getNumericValue(i, "ADDHOLN_O"));
			att.setACNM_O(attwb.getNumericValue(i, "ACNM_O"));
			att.setDEPSN_BHYT(attwb.getStringValue(i, "DEPSN_BHYT"));
			att.setBSALY_LAST_MONTH(attwb.getNumericValue(i, "BSALY_LAST_MONTH"));
			// danh cho bang ko co primary key
			if (qdao.checkAttendantExist(att.getEMPSN(), att.getDOT_TV()))
				dao.update(att);
			else
				dao.save(att);
			// bang ATTENDANTDB_QUIT can phai co primary key de tranh bi trung
			// du lieu
//			try {
//			dao.save(att);
//			} catch (Exception e) {
//			dao.update(att);
//			}
			// save Att
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
			initAtt.setType(att.getCLASS());
			listInitData.add(initAtt);
			qdao.saveInitAttData(initAtt);
		}
		// calc
		Collections.sort(listInitData, ATTQUIT_COMPARATOR);
		int stt = 1;
		for (AttQuitInitData att : listInitData) {
			qdao.updateAttQuitDataOrder(att.getEmpsn(), att.getDotTv(), stt++);
			qdao.calculate(att.getEmpsn(), att.getDotTv(), att.getType());
		}
		msgPane.addMessage("Cập nhật thành công", MessagePane.MSG_SUCCESS);
		return 0;
	}


	private MappingPropertyEditor getTypeEditor() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		editor.put("Thôi việc", "TV");
		editor.put("Bỏ việc", "BV");
		return editor;
	}

	private void initComponents() {
		GridLayoutData twoColumnLayout = new GridLayoutData();
		twoColumnLayout.setColumnSpan(2);
		rootLayout = new Grid(2);
		rootLayout.setColumnWidth(0, new Extent(150));
		// rootLayout.setBorder(new Border(new Extent(1), Color.BLACK,
		// Border.STYLE_SOLID));
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		add(rootLayout);
		rootLayout.add(new Label("Chọn file lương"));
		fs = new FileUploadSelect();
		ImageReference icon = new ResourceImageReference(
		"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fs.setIcon(icon);
		fs.setIconHeight(new Extent(24));
		fs.setIconWidth(new Extent(24));
//		fs.setFileType("application/vnd.ms-excel");// Chrome only
		rootLayout.add(fs);
		lblMonth = new Label("Tính lương tháng");
		rootLayout.add(lblMonth);
		Row row = new Row();
		sfMonth = new SelectField();
		sfMonth.setWidth(new Extent(40));
		row.add(sfMonth);
		lblYear = new Label("Năm");
		row.add(lblYear);
		sfYear = new SelectField();
		sfYear.setWidth(new Extent(60));
		row.add(sfYear);
		rootLayout.add(row);
		lblDate = new Label("Đợt thôi việc");
		rootLayout.add(lblDate);
		dfDate = new DscDateField();
		dfDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDate.setSelectedDate(Calendar.getInstance());
		dfDate.getDateChooser().setLocale(new Locale("en"));		
		rootLayout.add(dfDate);
		// lblFact = new Label("Xưởng");
		// rootLayout.add(lblFact);
		// sfFact = new SelectField();
		// sfFact.setWidth(new Extent(60));
		// rootLayout.add(sfFact);
		lblType = new Label("Loại");
		rootLayout.add(lblType);
		sfType = new SelectField();
		sfType.setWidth(new Extent(80));
		rootLayout.add(sfType);
		rootLayout.add(new Label());
		btnStart = new Button();
		btnStart.setWidth(new Extent(87));
		btnStart.setHeight(new Extent(26));
		btnStart.setRolloverEnabled(true);
		btnStart.setPressedEnabled(true);
		FillImage fill = new FillImage(new ResourceImageReference(
		"/dsc/echo2app/resource/image/capnhat.gif"));
		FillImage fill_hover = new FillImage(new ResourceImageReference(
		"/dsc/echo2app/resource/image/capnhat_hover.gif"));
		FillImage fill_press = new FillImage(new ResourceImageReference(
		"/dsc/echo2app/resource/image/capnhat_press.gif"));
		btnStart.setBackgroundImage(fill);
		btnStart.setRolloverBackgroundImage(fill_hover);
		btnStart.setPressedBackgroundImage(fill_press);
		rootLayout.add(btnStart);
		rootLayout.add(new Label());
		btnReview = new Button("Tìm");
		rootLayout.add(btnReview);

		GridLayoutData beautyLayout = new GridLayoutData();
		beautyLayout.setInsets(new Insets(5, 5, 5, 5));
		beautyLayout.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.CENTER));
		for (int i = 0; i < rootLayout.getComponentCount(); i++) {
			if (i % 2 == 0) {
				rootLayout.getComponent(i).setLayoutData(beautyLayout);
			}
		}
		msgPane = new MessagePane();
		msgPane.setLayoutData(twoColumnLayout);
		rootLayout.add(msgPane);
	}

	@Override
	public void dispose() {
		super.dispose();
		fs.doSelectNextFile(null);
	}

	private static final Comparator<AttQuitInitData> ATTQUIT_COMPARATOR = new Comparator<AttQuitInitData>() {

		@Override
		public int compare(AttQuitInitData o1, AttQuitInitData o2) {
			return o1.getEmpsn().compareTo(o2.getEmpsn());
		}
	};
}
