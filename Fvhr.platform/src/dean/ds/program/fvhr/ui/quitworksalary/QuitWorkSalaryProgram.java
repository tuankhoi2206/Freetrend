package ds.program.fvhr.ui.quitworksalary;

import java.util.Calendar;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.program.MasterToolbar;
import fv.components.JdbcBrowserContent;
import fv.components.PreMaintainSProgram;

/**
 * A release of quit work salary program
 * 
 * @author Hieu
 * @since 20/04/2011 ->
 */
public class QuitWorkSalaryProgram extends PreMaintainSProgram {
	private static final long serialVersionUID = 122454179873913232L;

	private QuitWorkSalaryBrowserContent browserContent;

	private QuitWorkSalaryDataContent dc;

	private QuitWorkExcelInputPane userInputPane;

	private Button btnExcel;

	private QuitWorkSearchPane searchPane;

	private QuitWorkRowSearch rowSearchPane;

	private Button btnUpdateOrder;

	private Button btnRowSearch;

	private Button btnRemoveICData;

	public QuitWorkSalaryProgram() {
		super();
		initComponents();

	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		btnExcel = new Button();
		btnExcel.setToolTipText("Import excel");
		btnExcel.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif"));
		btnExcel.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcelD.gif"));
		btnExcel.setStyleName("Default.ToolbarButton");
		btnExcel.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				doImportExcel();
			}
		});
		getMasterToolbar().add(btnExcel);

		btnUpdateOrder = new Button();
		btnUpdateOrder.setToolTipText("Cập nhật số thứ tự");
		btnUpdateOrder.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/add_order.gif"));
		btnUpdateOrder.setDisabledIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/add_orderD.gif"));
		btnUpdateOrder.setStyleName("Default.ToolbarButton");
		btnUpdateOrder.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog dlg = new MessageDialog(
						MessageDialog.TYPE_INFORMATION
								+ MessageDialog.CONTROLS_YES_NO,
						"Cập nhật số thứ tự theo danh sách đang sắp xếp?");
				dlg.addActionListener(new ActionListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(
								MessageDialog.COMMAND_OK)) {
							doUpdateOrder();
						}
					}
				});
			}
		});
		getMasterToolbar().add(btnUpdateOrder);

		btnRowSearch = new Button();
		btnRowSearch.setToolTipText("Tìm trong danh sách");
		btnRowSearch.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/find16.gif"));
		btnRowSearch.setStyleName("Default.ToolbarButton");
		btnRowSearch.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				showRowSearchPane();
			}
		});
		getMasterToolbar().add(btnRowSearch);

		btnRemoveICData = new Button("Bù lương");
		btnRemoveICData.setStyleName("Default.ToolbarButton");
		btnRemoveICData.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog dlg = new MessageDialog(
						MessageDialog.TYPE_INFORMATION
								+ MessageDialog.CONTROLS_YES_NO,
						"Bỏ dữ liệu ngày công và tính lương lại cho số thẻ đang chọn?");
				dlg.addActionListener(new ActionListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(
								MessageDialog.COMMAND_OK)) {
							doRemoveICData();
						}
					}
				});
			}
		});
		getMasterToolbar().add(btnRemoveICData);
		// setDataContent()
		browserContent = new QuitWorkSalaryBrowserContent();
		browserContent.setTableHeader(getDisplayColumns());
		browserContent.setVniColumns(new String[] { "EMPNA" });
		add(browserContent);
		dc = new QuitWorkSalaryDataContent(this);
		return ret;
	}

	protected void doRemoveICData() {
		PageableSortableTableModel model = browserContent.getModel();
		int rowcount = model.getRowCount();
		int rowNo = browserContent.getRowNo();
		if (rowNo < rowcount) {
			String empsn = model.getValueAt(1, browserContent.getRowNo())
					.toString();
			browserContent.removeICData(empsn,"");
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Chọn số thẻ cần bỏ ngày công");
		}
	}

	private void showRowSearchPane() {
		if (rowSearchPane == null)
			rowSearchPane = new QuitWorkRowSearch(this);
		Application.getApp().getDefaultWindow().getContent().add(rowSearchPane);
	}

	protected void doUpdateOrder() {
		if (browserContent.getListData() != null
				&& browserContent.getListData().size() > 0) {
			PageableSortableTableModel model = browserContent.getModel();
			for (int i = 0; i < model.getRows().size(); i++) {
				browserContent.getDao().updateAttQuitDataOrder(
						model.getValueAt(1, i).toString(),
						browserContent.getDate(), i + 1);
			}
		}
	}

	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
		if (getDataMode() != DATAMODE_BROWSER) {
			btnExcel.setEnabled(false);
			btnUpdateOrder.setEnabled(false);
			btnRowSearch.setEnabled(false);
		} else {
			btnExcel.setEnabled(true);
			btnUpdateOrder.setEnabled(true);
			btnRowSearch.setEnabled(true);
		}
	}

	@Override
	protected void doDataRefresh() {
		super.doDataRefresh();

	}

	private void initComponents() {
	}

	public String[] getDisplayColumns() {
		return new String[] { "STT", "EMPSN", "EMPNA", "DOT_TV", "BSALY", "TS",
				"TS1" };// "EMPSN", "EMPNA", "DOT_TV"
	}

	protected void doImportExcel() {
		QuitWorkSalaryExcelPane pane = new QuitWorkSalaryExcelPane(this);
		Application.getApp().getDefaultWindow().getContent().add(pane);
	}

	@Override
	protected void doCancel() {
		setDataMode(DATAMODE_BROWSER);
		getMainSplitPane().remove(1);
		getMainSplitPane().add(browserContent);
		getMasterToolbar().refresh();
	}

	@Override
	protected boolean doSave() {

		return false;
	}

	@Override
	protected boolean doDelete() {

		return false;
	}

	@Override
	protected boolean doEdit() {

		return false;
	}

	@Override
	protected boolean doNew() {
		setDataMode(DATAMODE_NEW);
		removeAll();
		if (userInputPane == null)
			userInputPane = new QuitWorkExcelInputPane(this);
		add(userInputPane);
		getMasterToolbar().refresh();
		return true;
	}

	@Override
	protected void doContent() {
		if (dc.refetch()) {
			setDataMode(DATAMODE_NONE);
			removeAll();
			add(dc);
			switchContent(1);
			dc.getNavigator().reset();
		} else
			Application.getApp().showMessageDialog(
					MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR,
					"Chọn dữ liệu cần xem");
	}

	@Override
	protected void doBrowse() {
		setDataMode(DATAMODE_BROWSER);
		removeAll();
		add(browserContent);
		switchContent(0);
	}

	@Override
	protected void doRefresh() {
		if (getComponent(0) instanceof JdbcBrowserContent) {
			refresh();
			dc.getNavigator().reset();
		} else {
			dc.refetch();
			dc.getNavigator().reset();
		}
	}

	@Override
	public int refresh() {
		int ret = super.refresh();
		browserContent.refresh();
		if (getDataMode() == DATAMODE_BROWSER) {
			if (browserContent.getListData() != null
					&& browserContent.getListData().size() > 0) {
				switchContent(0);
				getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT,
						true);
				getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE,
						true);
				getMasterToolbar().refresh();
			} else {
				getMasterToolbar().addUserDefineRight(
						MasterToolbar.CMD_BROWSER, false);
				getMasterToolbar().addUserDefineRight(
						MasterToolbar.CMD_CONTENT, false);
				getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT,
						false);
				getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_DELETE,
						false);
				getMasterToolbar().refresh();
			}
		}
		return ret;
	}

	@Override
	protected void doQueryNormal() {
		if (searchPane == null){
			searchPane = new QuitWorkSearchPane(this);
			Application.getApp().getDefaultWindow().getContent().add(searchPane);
		}
		searchPane.setVisible(true);
	}

	public QuitWorkSalaryBrowserContent getBrowserContent() {
		return browserContent;
	}

	public void showSearchPane(int month, int year, Calendar quitDate, int typeIndex){
		doQueryNormal();
		searchPane.setMonth(month);
		searchPane.setYear(year);
		searchPane.setQuitDate(quitDate);
		searchPane.setType(typeIndex);
	}
	
	@Override
	public void dispose() {
		if (searchPane!=null)
			searchPane.dispose();
		super.dispose();
	}
}
