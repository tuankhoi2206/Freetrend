package ds.program.fvhr.ui.quitworksalary.task;

import ds.program.fvhr.ui.quitworksalary.AttQuitHandler;

public class AttQuitTaskExecuterFacory {
	private static AttQuitTaskExecuterFacory INSTANSE;

	private static ThreadLocal<AttQuitHandler> activeHandler = new ThreadLocal<AttQuitHandler>();

	public static final AttQuitTaskExecuterFacory getFactory() {
		if (INSTANSE == null)
			INSTANSE = new AttQuitTaskExecuterFacory();
		return INSTANSE;
	}

	public AttQuitHandler getHandler() {
		if (activeHandler.get() == null) {
			System.out.println("instanse new handler");
			activeHandler.set(new AttQuitHandler());
		}
		return activeHandler.get();
	}
}
