package ds.program.fvhr.baby.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

import org.springframework.beans.PropertyEditorRegistrySupport;

import ds.program.fvhr.baby.tools.BinderSourceToComponent;
import ds.program.fvhr.baby.tools.CheckRule;
import ds.program.fvhr.baby.tools.ProcessProcedure;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_IC_CARD;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.util.Vni2Uni;

public class EMP_ICCARDProgram extends WindowPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private IGenericDAO<N_IC_CARD, String> daoIc;
	private IGenericDAO<N_EMP_ICCARD, String> daoEmpIC;
	private static BrowserContent browserContent;

	private static DataObjectSet dataObjectset;
	private Grid girdMain;
	private TextField txt_IC;
	private SelectField sf_status;
	private static String condition;
	private static Object[] params;

	private SplitPane splitPanel;
	private Button btnEdit;
	private Button btnRefresh;

	private TextField txt_Empsn;
	private DscDateField df_Begin;
	private DscDateField df_End;
	private final static int MODE_EDIT = 2;
	private final static int MODE_NEW = 1;
	private final static int MODE_BROWSER = 0;
	private Label lblNote1;
	private Label lblNote2;
	private Calendar calendar;
	private Button btnNew;
	private Button btnCancel;
	private Button btnSearch;
	private Button btnDelete;
	private Button btnAccept;
	private int datamode = 0;
	private String errorMessage = "";

	public static void doQuery(String cond, Object... param) {
		condition = cond;
		params = param;
		dataObjectset.query(cond, params);
		browserContent.refresh();
	}

	/**
	 * Creates a new <code>IC_CARDProgram</code>.
	 */
	public EMP_ICCARDProgram() {
		super();

		// Add design-time configured components.

		initComponents();
		doInit();
		doLayout();

	}

	@SuppressWarnings("unchecked")
	public void doInit() {
		splitPanel = new SplitPane();
		BinderSourceToComponent.putSourceToSelectField(bindStatus(), sf_status);
		daoIc = Application.getApp().getDao(N_IC_CARD.class);
		daoEmpIC = Application.getApp().getDao(N_EMP_ICCARD.class);
		dataObjectset = new DataObjectSet(daoEmpIC, N_EMP_ICCARD.class);
		browserContent = new BrowserContent();
		condition = "1<>1";
		dataObjectset.query(condition);
		browserContent.init(dataObjectset, displayColumns());
		browserContent.addSelectChangeActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				doBrowserContentSelectChange();
			}
		});
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		df_Begin.setDateFormat(sdf);
		df_End.setDateFormat(sdf);
		calendar = Calendar.getInstance();
		df_Begin.setSelectedDate(calendar);
		df_End.setSelectedDate(calendar);
		Color color = new Color(255, 244, 253);
		txt_Empsn.setBackground(color);
		txt_IC.setBackground(color);
		df_Begin.setBackground(color);
		sf_status.setBackground(color);

		registPropertyEditor();
		SwitchDataMode(MODE_BROWSER);
	}

	private String[] displayColumns() {
		return new String[] { "EMPSN", "EMPCN", "BEGIN_DATE", "END_DATE",
				"USE_STATUS" };
	}

	private void doLayout() {
		this.setWidth(new Extent(900));
		this.setHeight(new Extent(700));
		splitPanel.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPanel.setResizable(true);
		splitPanel.setSeparatorPosition(new Extent(200));
		splitPanel.add(girdMain);
		splitPanel.add(browserContent);
		this.add(splitPanel);
	}

	private MappingPropertyEditor bindStatus() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		map.put("Sử Dụng", 1);
		map.put("Không sử dụng", 0);
		return map;
	}

	private void registPropertyEditor() {
		PropertyEditorRegistrySupport propertyEditor = new PropertyEditorRegistrySupport();
		propertyEditor.registerCustomEditor(N_EMP_ICCARD.class, "USE_STATUS",
				bindStatus());
	}

	private void doBrowserContentRefresh() {
		browserContent.refresh();
		if (dataObjectset.getRecordCount() > 0) {
			browserContent.setCurrentPage(0);
			browserContent.setCurrentSelectRowNo(0);
			doBrowserContentSelectChange();
		}
	}

	private void doBrowserContentSelectChange() {
		int recNo = browserContent.getCurrentSelectRowNo();
		N_EMP_ICCARD obj = (N_EMP_ICCARD) dataObjectset.getDataObject(recNo);
		txt_IC.setText(obj.getEMPCN());
		txt_Empsn.setText(obj.getEMPSN());
		calendar.setTime(obj.getBEGIN_DATE());
		df_Begin.setSelectedDate(calendar);
		if (obj.getEND_DATE() != null)
			calendar.setTime(obj.getEND_DATE());
		else
			calendar.setTime(new Date());
		df_End.setSelectedDate(calendar);
		sf_status.setSelectedIndex(obj.getUSE_STATUS().equals("1") ? 0 : 1);
		btnEdit.setEnabled(true);
		btnDelete.setEnabled(true);
	}

	private boolean CheckDataObject() {
		if (txt_Empsn.getText().trim().isEmpty()
				|| txt_IC.getText().trim().isEmpty()
				|| sf_status.getSelectedIndex() < 0
				|| df_Begin.getSelectedDate().getTime().toString().isEmpty()) {
			errorMessage = "Không được bỏ trống những ô tô màu đỏ";
			return false;
		}
		if (df_Begin.getSelectedDate().after(df_End.getSelectedDate())) {
			errorMessage = "Ngày bắt đầu phải lớn hơn ngày kết thúc";
			return false;
		}
		if (!CheckRule.checkRuleManager(txt_Empsn.getText())) {
			errorMessage = "Bạn không có quyền thao tác trên số thẻ này !";
			return false;
		}
		return true;
	}

	private void inputActionDaily(String action, String empsn, String note) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("IDUser", Application.getApp().getLoginInfo().getUserID());
		maps.put("TableName", "N_EMP_ICCARD");
		maps.put("ActionName", action);
		maps.put("EMPSN", empsn);
		maps.put("Note", Vni2Uni.convertToVNI(note));
		ProcessProcedure.inputActionDaily();
		ProcessProcedure.storedProcedure.execute(maps);
	}

	private void doAccept(ActionEvent e) {
		if (CheckDataObject()) {

			try {
				N_EMP_ICCARD obj = new N_EMP_ICCARD();
				obj.setEMPSN(txt_Empsn.getText());	
				obj.setEMPCN(txt_IC.getText());
				obj.setBEGIN_DATE(df_Begin.getSelectedDate().getTime());
				obj.setEND_DATE(df_End.getSelectedDate().getTime());
				obj.setUSE_STATUS(sf_status.getSelectedIndex() == 0 ? "1" : "0");
				if (datamode == MODE_NEW) {
					daoEmpIC.save(obj);
					inputActionDaily(
							"INSERT",
							obj.getEMPSN(),
							"Cập nhật số thẻ " + obj.getEMPSN()
									+ " sử dụng IC " + obj.getEMPCN()
									+ " bắt đầu từ ngày " + obj.getBEGIN_DATE()
									+ "Thời gian cập nhật "
									+ Calendar.getInstance().getTime());
				}

				if (datamode == MODE_EDIT) {
					daoEmpIC.update(obj);
					inputActionDaily("UPDATE", txt_Empsn.getText(),
							"Sửa thông tin số thẻ " + obj.getEMPSN()
							+ " sử dụng IC " + obj.getEMPCN()
							+ " bắt đầu từ ngày " + obj.getBEGIN_DATE()
							+ "Thời gian sửa "
							+ Calendar.getInstance().getTime());
				}

			} catch (Exception e1) {
				Application.getApp().showMessageDialog(
						MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
						e1.toString());
			} finally {
				SwitchDataMode(MODE_BROWSER);
				dataObjectset.query(condition, params);
				doBrowserContentRefresh();
			}
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
					errorMessage);
			return;
		}
	}

	private void SwitchDataMode(int mode) {
		if (mode != MODE_BROWSER) {
			if (mode == MODE_EDIT) {
				txt_Empsn.setEnabled(false);
				txt_IC.setEnabled(false);
				df_Begin.setEnabled(false);
				sf_status.setEnabled(false);
				df_End.setEnabled(true);
				browserContent.setEnabled(false);
				datamode = MODE_EDIT;
			}
			if (mode == MODE_NEW) {
				txt_Empsn.setEnabled(true);
				txt_IC.setEnabled(true);
				df_Begin.setEnabled(true);
				df_End.setEnabled(true);
				sf_status.setEnabled(true);
				browserContent.setEnabled(false);
				datamode = MODE_NEW;
			}
			btnSearch.setEnabled(false);
			btnDelete.setEnabled(false);
			btnRefresh.setEnabled(false);
			btnNew.setEnabled(false);
			btnEdit.setEnabled(false);
			btnAccept.setEnabled(true);
			btnCancel.setEnabled(true);
		}
		if (mode == MODE_BROWSER) {
			txt_Empsn.setEnabled(false);
			txt_IC.setEnabled(false);
			df_Begin.setEnabled(false);
			sf_status.setEnabled(false);
			df_End.setEnabled(false);
			btnAccept.setEnabled(false);
			btnCancel.setEnabled(false);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);

			btnNew.setEnabled(true);
			btnRefresh.setEnabled(true);
			btnSearch.setEnabled(true);
			browserContent.setEnabled(true);
			datamode = MODE_BROWSER;
		}
	}

	private void doRefresh(ActionEvent e) {
		if (dataObjectset.getRecordCount() < 0) {
			dataObjectset.query("1<>1");
		}
		txt_Empsn.setText("");
		txt_IC.setText("");
		df_Begin.setSelectedDate(Calendar.getInstance());
		df_Begin.setSelectedDate(Calendar.getInstance());
		sf_status.setSelectedIndex(-1);
		doBrowserContentRefresh();
	}

	private void doNew(ActionEvent e) {
		SwitchDataMode(MODE_NEW);
	}

	private void doCancel(ActionEvent e) {
		SwitchDataMode(MODE_BROWSER);
	}

	private void doEdit(ActionEvent e) {
		SwitchDataMode(MODE_EDIT);
	}

	private void doSearch(ActionEvent e) {
		EMP_ICCARDQuery query = new EMP_ICCARDQuery();
		this.getApplicationInstance().getDefaultWindow().getContent()
				.add(query);
	}

	private void doDelete(ActionEvent e) {
		
		if(CheckDataObject())
		{
		final MessageDialog dlg = new MessageDialog(
				MessageDialog.CONTROLS_YES_NO, "ban co chac muon xoa");
		dlg.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == dlg.COMMAND_OK) {
					try {
						int recNo = browserContent.getCurrentSelectRowNo();
						N_EMP_ICCARD obj = (N_EMP_ICCARD) browserContent
								.getDataObjectSet().getDataObject(recNo);
						daoEmpIC.delete(obj);
						inputActionDaily("UPDATE", txt_Empsn.getText(),
								"Xoá thông tin số thẻ " + obj.getEMPSN()
								+ " sử dụng IC " + obj.getEMPCN()
								+ " bắt đầu từ ngày " + obj.getBEGIN_DATE()
								+ " Thời gian xoá "
								+ Calendar.getInstance().getTime());
					} catch (Exception e1) {
						Application.getApp().showMessageDialog(
								MessageDialog.CONTROLS_OK
										+ MessageDialog.TYPE_ERROR,
								e1.toString());
						return;
					}
				} else
					System.out.println("Khong xoa");
			}
		});
		}
		else
		{
			Application.getApp().showMessageDialog(
					MessageDialog.CONTROLS_OK
							+ MessageDialog.TYPE_ERROR,
					errorMessage);
			return;
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setTitle("Chi tiết thẻ IC");
		this.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		this.setFont(new Font(new Font.Typeface("sans-serif"), Font.PLAIN,
				new Extent(10, Extent.PT)));
		this.setBackground(new Color(0xf0f8ff));
		this.setResizable(false);
		this.setModal(true);
		this.setTitleFont(new Font(new Font.Typeface("monospace"), Font.BOLD,
				new Extent(13, Extent.PT)));
		girdMain = new Grid();
		girdMain.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		girdMain.setSize(4);
		add(girdMain);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(20, Extent.PX), new Extent(0, Extent.PX)));
		grid1.setSize(1);
		GridLayoutData grid1LayoutData = new GridLayoutData();
		grid1LayoutData.setRowSpan(1);
		grid1.setLayoutData(grid1LayoutData);
		girdMain.add(grid1);
		Row row7 = new Row();
		row7.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		GridLayoutData row7LayoutData = new GridLayoutData();
		row7LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						10, Extent.PX)));
		row7.setLayoutData(row7LayoutData);
		grid1.add(row7);
		Label label3 = new Label();
		label3.setText("Số thẻ đeo: ");
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(35, Extent.PX),
				new Extent(0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row7.add(label3);
		txt_Empsn = new TextField();
		txt_Empsn.setWidth(new Extent(150, Extent.PX));
		txt_Empsn.setDisabledBackground(new Color(0xc0c0c0));
		row7.add(txt_Empsn);
		Row row1 = new Row();
		row1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		GridLayoutData row1LayoutData = new GridLayoutData();
		row1LayoutData.setRowSpan(2);
		row1.setLayoutData(row1LayoutData);
		grid1.add(row1);
		Label label1 = new Label();
		label1.setText("Số thẻ IC: ");
		RowLayoutData label1LayoutData = new RowLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(45, Extent.PX),
				new Extent(0, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		row1.add(label1);
		txt_IC = new TextField();
		txt_IC.setWidth(new Extent(150, Extent.PX));
		txt_IC.setDisabledBackground(new Color(0xc0c0c0));
		row1.add(txt_IC);
		Row row8 = new Row();
		row8.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid1.add(row8);
		Label label4 = new Label();
		label4.setText("Ngày bắt đầu:");
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row8.add(label4);
		df_Begin = new DscDateField();
		df_Begin.setWidth(new Extent(150, Extent.PX));
		row8.add(df_Begin);
		Row row9 = new Row();
		row9.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid1.add(row9);
		Label label5 = new Label();
		label5.setText("Ngày kết thúc: ");
		RowLayoutData label5LayoutData = new RowLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(15, Extent.PX),
				new Extent(0, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		row9.add(label5);
		df_End = new DscDateField();
		df_End.setWidth(new Extent(150, Extent.PX));
		row9.add(df_End);
		Row row2 = new Row();
		row2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setRowSpan(2);
		row2.setLayoutData(row2LayoutData);
		grid1.add(row2);
		Label label2 = new Label();
		label2.setText("Trạng thái: ");
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(40, Extent.PX),
				new Extent(0, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row2.add(label2);
		sf_status = new SelectField();
		sf_status.setWidth(new Extent(100, Extent.PX));
		sf_status.setDisabledBackground(new Color(0xc0c0c0));
		row2.add(sf_status);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(20, Extent.PX), new Extent(0, Extent.PX)));
		grid3.setSize(1);
		girdMain.add(grid3);
		Row row11 = new Row();
		row11.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid3.add(row11);
		btnSearch = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnSearch1.png");
		btnSearch.setIcon(imageReference1);
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnSearch1Disable.png");
		btnSearch.setDisabledIcon(imageReference2);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearch(e);
			}
		});
		row11.add(btnSearch);
		Row row3 = new Row();
		row3.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		GridLayoutData row3LayoutData = new GridLayoutData();
		row3LayoutData.setRowSpan(1);
		row3.setLayoutData(row3LayoutData);
		grid3.add(row3);
		btnRefresh = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnReset.png");
		btnRefresh.setIcon(imageReference3);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnResetDisable.png");
		btnRefresh.setDisabledIcon(imageReference4);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doRefresh(e);
			}
		});
		row3.add(btnRefresh);
		Row row10 = new Row();
		row10.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid3.add(row10);
		btnNew = new Button();
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnNew.png");
		btnNew.setIcon(imageReference5);
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnNewDisable.png");
		btnNew.setDisabledIcon(imageReference6);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew(e);
			}
		});
		row10.add(btnNew);
		Row row6 = new Row();
		row6.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		grid3.add(row6);
		btnEdit = new Button();
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnEdit.png");
		btnEdit.setIcon(imageReference7);
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnEditDisable.png");
		btnEdit.setDisabledIcon(imageReference8);
		RowLayoutData btnEditLayoutData = new RowLayoutData();
		btnEditLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						10, Extent.PX)));
		btnEdit.setLayoutData(btnEditLayoutData);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEdit(e);
			}
		});
		row6.add(btnEdit);
		Row row12 = new Row();
		grid3.add(row12);
		btnDelete = new Button();
		ResourceImageReference imageReference9 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnDelete.png");
		btnDelete.setIcon(imageReference9);
		ResourceImageReference imageReference10 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnDeleteDisable.png");
		btnDelete.setDisabledIcon(imageReference10);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete(e);
			}
		});
		row12.add(btnDelete);
		Grid grid4 = new Grid();
		grid4.setSize(1);
		girdMain.add(grid4);
		Row row5 = new Row();
		row5.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid4.add(row5);
		btnAccept = new Button();
		ResourceImageReference imageReference11 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnAccept.png");
		btnAccept.setIcon(imageReference11);
		ResourceImageReference imageReference12 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnAcceptDisable.png");
		btnAccept.setDisabledIcon(imageReference12);
		RowLayoutData btnAcceptLayoutData = new RowLayoutData();
		btnAcceptLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		btnAccept.setLayoutData(btnAcceptLayoutData);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAccept(e);
			}
		});
		row5.add(btnAccept);
		Row row4 = new Row();
		grid4.add(row4);
		btnCancel = new Button();
		ResourceImageReference imageReference13 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnCancel.png");
		btnCancel.setIcon(imageReference13);
		ResourceImageReference imageReference14 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnCancelDisable.png");
		btnCancel.setDisabledIcon(imageReference14);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCancel(e);
			}
		});
		row4.add(btnCancel);
		Grid grid2 = new Grid();
		grid2.setFont(new Font(new Font.Typeface("sans-serif"), Font.ITALIC,
				new Extent(10, Extent.PT)));
		grid2.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		grid2.setSize(1);
		girdMain.add(grid2);
		Label label6 = new Label();
		label6.setText("*Ghi chú");
		grid2.add(label6);
		lblNote1 = new Label();
		lblNote1.setText("1. Thẻ IC được tính theo Ngày Bắt Đầu gần đây nhất");
		grid2.add(lblNote1);
		lblNote2 = new Label();
		lblNote2.setText("2. Số thẻ IC cần sử dụng phải là số thẻ đang còn hoạt động");
		grid2.add(lblNote2);
	}
}
