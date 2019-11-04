package ds.program.fvhr.ui.training;

import dsc.echo2app.program.DefaultProgram;

public class TestTrainingProgram extends DefaultProgram {
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		TrainingBrowserContent br = new TrainingBrowserContent();
		int bi = br.doInit();
		if (bi==RET_OK){
			add(br);
//		br.getDataObjectSet().query("o.DEPSN='TB006'", new Object[]{});
			return ret;
		}
		return RET_DBERROR;
	}
}
