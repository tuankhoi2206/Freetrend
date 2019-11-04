package ds.program.fvhr.ui.salary;

import dsc.echo2app.Application;
import dsc.echo2app.program.MasterToolbar;
import fv.components.JdbcBrowserContent;
import fv.components.PreMaintainSProgram;

public class SalaryProgram extends PreMaintainSProgram {
	private static final long serialVersionUID = 1L;

	private SalaryBrowserContent browserContent;

	private SalaryDataContent dc;

	private SalarySearchPane sp;

	@Override
	protected int doInit() {
		int ret = super.doInit();
		browserContent = new SalaryBrowserContent();
		browserContent.setTableHeader(getDisplayColumns());
		browserContent.setVniColumns(new String[] { "EMPNA" });
		getMainSplitPane().add(browserContent);
		dc = new SalaryDataContent(this);
		browserContent.getRowNavigator().setDataContent(dc);
		return ret;
	}

	public String[] getDisplayColumns() {
		return new String[] { "EMPSN", "EMPNA", "BSALY", "COMBSALY", "TS",
				"TS1" };
	}

	public SalaryBrowserContent getBrowserContent() {
		return browserContent;
	}

	public void setBrowserContent(SalaryBrowserContent browserContent) {
		this.browserContent = browserContent;
	}

	public SalaryDataContent getDc() {
		return dc;
	}

	public void setDc(SalaryDataContent dc) {
		this.dc = dc;
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
		setDataMode(DATAMODE_NEW);
		getMainSplitPane().remove(1);
		getMainSplitPane().add(dc);
		getMasterToolbar().refresh();
		return true;
	}

	@Override
	protected void doContent() {
	}

	@Override
	protected void doBrowse() {
	}

	@Override
	protected void doRefresh() {
		if (getMainSplitPane().getComponent(1) instanceof JdbcBrowserContent) {
			refresh();
		} else {
			dc.refetch();
		}
	}

	@Override
	protected void doQueryNormal() {
		if (sp == null)
			sp = new SalarySearchPane(this);
		Application.getApp().getDefaultWindow().getContent().add(sp);
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
	public void dispose() {
		super.dispose();
		if (sp!=null)
			sp.dispose();
	}
}
