package ds.program.fvhr.baby.ui;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.springframework.web.servlet.support.BindStatus;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

import ds.program.fvhr.baby.tools.BinderSourceToComponent;
import ds.program.fvhr.baby.ui.register_Shift.REGISTER_SHIFTProgram;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_IC_CARD;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import echopointng.ButtonEx;
import nextapp.echo2.app.Alignment;

public class IC_CARDProgram extends WindowPane {

	private ResourceBundle resourceBundle;
	private SplitPane mainSplitPane;
	private static BrowserContent browserContent;
	private static DataObjectSet dataObjectSet;
	private static N_IC_CARD dataObject;

	public static N_IC_CARD getDataObject() {
		return dataObject;
	}

	public static void setDataObject(N_IC_CARD dataObject) {
		IC_CARDProgram.dataObject = dataObject;
	}

	private IGenericDAO<N_IC_CARD, String> daoIC;
	private String condition;
	private SelectField sfStatus;
	private DscField txtIC;
	private DscField txtLocalSearch;
	private Button btnNew;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnCancel;
	private Button btnAccept;
	private final int MODE_NEW = 1;
	private final int MODE_EDIT = 2;
	private final int MODE_NONE = 0;
	private final int MODE_BROWSER = 3;
	private int MODE_PROGRAM;
	private String errorMessage = "";

	/**
	 * Creates a new <code>IC_CARDProgram</code>.
	 */
	public IC_CARDProgram() {
		super();

		// Add design-time configured components.
		initComponents();
		doInit();
	}

	protected void doInit() {
		this.setWidth(new Extent(700));
		this.setHeight(new Extent(400));
		daoIC = Application.getApp().getDao(N_IC_CARD.class);
		dataObject = new N_IC_CARD();
		mainSplitPane.setSeparatorPosition(new Extent(340));
		browserContent = new BrowserContent();
		condition = "1<>1";
		dataObjectSet = new DataObjectSet(daoIC, N_IC_CARD.class);
		browserContent.init(dataObjectSet, displayColumns());
		browserContent.getBrowserNav().setSearchButtonVisible(false);
		browserContent.getBrowserNav().setSearchTextFieldVisible(false);
		txtLocalSearch = new DscField();
		txtLocalSearch.setMaximumLength(10);
		txtLocalSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ic = txtLocalSearch.getText().trim();
				dataObjectSet.query("o.IC_NO = ?", ic);
				browserContent.refresh();
				if (dataObjectSet.getRecordCount() <= 0)
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK, "Không có dữ liệu !");
			}
		});
		browserContent.addSelectChangeActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doBrowserSelectChange();
			}
		});
		browserContent.getBrowserNav().add(txtLocalSearch);
		mainSplitPane.setSeparatorPosition(new Extent(300));
		mainSplitPane.add(browserContent);
		BinderSourceToComponent
				.putSourceToSelectField(binderStatus(), sfStatus);

		MODE_PROGRAM = MODE_NONE;
		UIRefresh();
	}

	private void doBrowserSelectChange() {
		N_IC_CARD obj = (N_IC_CARD) browserContent.getDataObjectSet()
				.getDataObject(browserContent.getCurrentSelectRowNo());
		txtIC.setText(obj.getIC_NO());
		MappingPropertyEditor map = binderStatus();
		sfStatus.setSelectedIndex(Integer.valueOf(obj.getUSE_STATUS()));
		switchContent(MODE_BROWSER);
	}

	private MappingPropertyEditor binderStatus() {
		MappingPropertyEditor map = new MappingPropertyEditor();
		map.put("Đang còn trống", 0);
		map.put("Đang sử dụng", 1);
		map.put("Ly do khac", 2);
		map.put("So the ao", 3);
		map.put("Đã hư hoặc mất", 4);

		return map;
	}

	private String[] displayColumns() {
		return new String[] { "IC_NO", "USE_STATUS" };
	}

	private void UIRefresh() {
		if (MODE_PROGRAM == MODE_NEW || MODE_PROGRAM == MODE_EDIT) {
			btnAccept.setEnabled(true);
			btnCancel.setEnabled(true);
			btnDelete.setEnabled(false);
			browserContent.setEnabled(false);
			btnEdit.setEnabled(false);
			btnNew.setEnabled(false);
			if (MODE_PROGRAM == MODE_NEW)
				txtIC.setEnabled(true);
			if (MODE_BROWSER == MODE_EDIT)
				txtIC.setEnabled(false);
			sfStatus.setEnabled(true);
		}
		if (MODE_PROGRAM == MODE_NONE || MODE_PROGRAM == MODE_BROWSER) {
			btnAccept.setEnabled(false);
			btnCancel.setEnabled(false);
			if (MODE_PROGRAM == MODE_BROWSER) {
				btnDelete.setEnabled(true);
				btnEdit.setEnabled(true);
			}
			if (MODE_PROGRAM == MODE_NONE) {
				btnDelete.setEnabled(false);
				btnEdit.setEnabled(false);
			}
			browserContent.setEnabled(true);
			btnNew.setEnabled(true);
			txtIC.setEnabled(false);
			sfStatus.setEnabled(false);
		}
	}

	private void switchContent(int mode_program) {
		MODE_PROGRAM = mode_program;
		UIRefresh();
	}

	private void doNew(ActionEvent e) {
		switchContent(MODE_NEW);
	}

	private void doEdit(ActionEvent e) {
		switchContent(MODE_EDIT);
	}

	private void doCancel(ActionEvent e) {
		switchContent(MODE_NONE);
	}

	private void saveUItoDataObject() {
		dataObject.setIC_NO(txtIC.getText());
		dataObject.setUSE_STATUS(String.valueOf(sfStatus.getSelectedIndex()));
		dataObject.setNOTE("");
	}

	private boolean checkDataBeforeSave() {
		if (txtIC.getText().trim().equals("")) {
			errorMessage = "Không được bỏ trống số thẻ IC";
			return false;
		}
		if (sfStatus.getSelectedIndex() < 0) {
			errorMessage = "Không được bỏ trống Trạng Thái";
			return false;
		}
		if (!txtIC.getText().trim().matches("[0-9]{10}")) {
			errorMessage = "IC không đúng kiểu ";
			return false;
		}

		return true;
	}

	private void doAccept(ActionEvent e) {

		if (checkDataBeforeSave()) {
			try {
				saveUItoDataObject();
				if (MODE_PROGRAM == MODE_NEW) {
					daoIC.save(dataObject);
				}
				if (MODE_PROGRAM == MODE_EDIT) {
					daoIC.update(dataObject);
				}
			} catch (Exception e1) {
				Application.getApp().showMessageDialog(
						MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
						e1.toString());
				return;
			} finally {
				switchContent(MODE_NONE);
			}
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
					errorMessage);
			return;
		}
		switchContent(MODE_NONE);
	}

	
	
	private void doDelete(ActionEvent e) {
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
						N_IC_CARD obj = (N_IC_CARD) browserContent
								.getDataObjectSet().getDataObject(recNo);
						daoIC.delete(obj);
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

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setTitle("Quản lý thẻ IC");
		mainSplitPane = new SplitPane();
		mainSplitPane.setResizable(true);
		add(mainSplitPane);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX), new Extent(10, Extent.PX),
				new Extent(10, Extent.PX)));
		grid2.setSize(1);
		mainSplitPane.add(grid2);
		Row row4 = new Row();
		grid2.add(row4);
		Label label3 = new Label();
		label3.setText("Số thẻ IC");
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row4.add(label3);
		txtIC = new DscField();
		txtIC.setDisabledBackground(new Color(0xc0c0c0));
		txtIC.setMaximumLength(10);
		row4.add(txtIC);
		Row row5 = new Row();
		grid2.add(row5);
		Label label4 = new Label();
		label4.setText("Trạng Thái");
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(12, Extent.PX),
				new Extent(0, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row5.add(label4);
		sfStatus = new SelectField();
		sfStatus.setDisabledBackground(new Color(0xc0c0c0));
		row5.add(sfStatus);
		Row row1 = new Row();
		GridLayoutData row1LayoutData = new GridLayoutData();
		row1LayoutData.setRowSpan(4);
		row1.setLayoutData(row1LayoutData);
		grid2.add(row1);
		btnNew = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnNew.png");
		btnNew.setIcon(imageReference1);
		btnNew.setText("New");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnNewDisable.png");
		btnNew.setDisabledIcon(imageReference2);
		btnNew.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnNew.setPressedEnabled(true);
		btnNew.setRolloverBackground(new Color(0xd2e9ff));
		btnNew.setPressedBackground(new Color(0xffe1f0));
		btnNew.setRolloverEnabled(true);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew(e);
			}
		});
		row1.add(btnNew);
		btnEdit = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnEdit.png");
		btnEdit.setIcon(imageReference3);
		btnEdit.setText("Edit");
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnEditDisable.png");
		btnEdit.setDisabledIcon(imageReference4);
		btnEdit.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnEdit.setPressedEnabled(true);
		btnEdit.setRolloverBackground(new Color(0xd9f2ff));
		btnEdit.setPressedBackground(new Color(0xffe6f2));
		btnEdit.setRolloverEnabled(true);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEdit(e);
			}
		});
		row1.add(btnEdit);
		btnDelete = new Button();
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnCancel.png");
		btnDelete.setIcon(imageReference5);
		btnDelete.setText("Delete");
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnCancelDisable.png");
		btnDelete.setDisabledIcon(imageReference6);
		btnDelete.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnDelete.setPressedEnabled(true);
		btnDelete.setRolloverBackground(new Color(0xcce6ff));
		btnDelete.setPressedBackground(new Color(0xffddee));
		btnDelete.setRolloverEnabled(true);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete(e);
			}
		});
		row1.add(btnDelete);
		Row row2 = new Row();
		grid2.add(row2);
		btnAccept = new Button();
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnAccept.png");
		btnAccept.setIcon(imageReference7);
		btnAccept.setText("Accept");
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnAcceptDisable.png");
		btnAccept.setDisabledIcon(imageReference8);
		btnAccept.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnAccept.setPressedEnabled(true);
		btnAccept.setRolloverBackground(new Color(0xd5eaff));
		btnAccept.setPressedBackground(new Color(0xffd7eb));
		btnAccept.setRolloverEnabled(true);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAccept(e);
			}
		});
		row2.add(btnAccept);
		btnCancel = new Button();
		ResourceImageReference imageReference9 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnDelete.png");
		btnCancel.setIcon(imageReference9);
		btnCancel.setText("Cancel");
		ResourceImageReference imageReference10 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnDeleteDisable.png");
		btnCancel.setDisabledIcon(imageReference10);
		btnCancel.setInsets(new Insets(new Extent(5, Extent.PX)));
		btnCancel.setPressedEnabled(true);
		btnCancel.setRolloverBackground(new Color(0xbbddff));
		btnCancel.setPressedBackground(new Color(0xffd5ea));
		btnCancel.setRolloverEnabled(true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCancel(e);
			}
		});
		row2.add(btnCancel);
	}
}
