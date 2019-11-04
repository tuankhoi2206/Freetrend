package ds.program.fvhr.ui.salary;

import dsc.echo2app.program.MasterToolbar;
import fv.components.JdbcBrowserContent;
import fv.components.JdbcDataContent;
import fv.components.PreMaintainSProgram;

public class ExcelSalaryDataProgram extends PreMaintainSProgram {
	private static final long serialVersionUID = 1L;

	private ExcelSalaryBrowserContent browserContent;

	public ExcelSalaryDataProgram() {

	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		browserContent = new ExcelSalaryBrowserContent();
		browserContent.setTableHeader(getDisplayColumns());
		browserContent.setVniColumns(new String[] { "EMPNA" });
		getMainSplitPane().add(browserContent);
		browserContent.getRowNavigator().setDataContent(new JdbcDataContent() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean refetch() {
				return true;
			}
		});
		return ret;
	}

	public String[] getDisplayColumns() {
		return new String[] { "EMPSN", "EMPNA", "BSALY", "COMSALY", "BONUS1",
				"BONUS2", "BONUS3", "BONUS4", "BONUS8", "BONUS5", "BORM",
				"KQT", "JOINLUM", "YLBX", "JOININSU", "BHTN", "BONUS9",
				"TEMP1", "TEMP2", "BAC" };
	}

	@Override
	protected void doCancel() {
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
		return false;
	}

	@Override
	protected void doContent() {
	}

	@Override
	protected void doBrowse() {
	}

	@Override
	protected void doQueryNormal() {
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
	protected void doRefresh() {
		if (getMainSplitPane().getComponent(1) instanceof JdbcBrowserContent) {
			refresh();
		}
	}
}
