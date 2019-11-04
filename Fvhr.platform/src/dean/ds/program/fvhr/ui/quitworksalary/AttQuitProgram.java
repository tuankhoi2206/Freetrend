package ds.program.fvhr.ui.quitworksalary;

import dsc.echo2app.component.DscPageNavigator;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.IProgram;
import fv.components.PreMaintainSProgram;

public class AttQuitProgram extends PreMaintainSProgram {
	private static final long serialVersionUID = 1L;

	private AttQuitDataContent masterDataContent;
	private BrowserContent browserContent;
	public AttQuitProgram(){
		super();
		masterDataContent = new AttQuitDataContent();
		browserContent = new BrowserContent();
	}
	
	public AttQuitDataContent getMasterDataContent() {
		return masterDataContent;
	}

	public void setMasterDataContent(AttQuitDataContent masterDataContent) {
		this.masterDataContent = masterDataContent;
	}
	
	public BrowserContent getBrowserContent() {
		return browserContent;
	}

	public void setBrowserContent(BrowserContent browserContent) {
		this.browserContent = browserContent;
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		getMasterDataContent().init(this);
		getBrowserContent().init(getMasterDataContent().getDataObjectSet(), getBrowserDisplayColumns());
		getMasterDataContent().setVniColumns(new String[]{"EMPNA"});
		DscPageNavigator nav = getBrowserContent().getBrowserNav();
		nav.setRowsPerPage(20);
		nav.reset();
		ProgramCondition pc = new ProgramCondition("1<>1");
		setQueryCondition(pc);
		setDataMode(IProgram.DATAMODE_NONE);
		return ret;
	}
	
	@Override
	protected void doLayout() {
		super.doLayout();
		add(browserContent);
	}
	
	public String[] getBrowserDisplayColumns(){
		return new String[]{"EMPSN","EMPNA"};
	}
}
