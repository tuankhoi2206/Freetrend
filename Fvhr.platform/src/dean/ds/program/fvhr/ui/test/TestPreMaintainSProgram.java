package ds.program.fvhr.ui.test;

import fv.components.PreMaintainSProgram;

public class TestPreMaintainSProgram extends PreMaintainSProgram {

	private static final long serialVersionUID = 7538693010620348821L;

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
	protected void doRefresh() {
		// AttQuitTaskExecuterFacory f = AttQuitTaskExecuterFacory.getFactory();
		// AttQuitHandler h = f.getHandler();
		// System.out.println(h);
		// h.calculate(new AttQuitExcelData((int) (Math.random()*100)));
	}

	@Override
	protected void doQueryNormal() {

	}

}
