package ds.program.fvhr.minh.bear;

import java.util.ResourceBundle;
import ds.program.fvhr.domain.N_EALRY_AFTER_B;
import ds.program.fvhr.domain.N_EALRY_BEFOR_B;
import ds.program.fvhr.domain.N_VIEW_TIME_BEAR;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.VniEditor;

public class BearDataContent extends DataContent {


	private Class cls;

	/**
	 * Creates a new <code>BearDataContent</code>.
	 */
	public BearDataContent(Class cls) {
		super();
		this.cls =cls;
		// Add design-time configured components.
		initComponents();
	}
	
	public Class getDataObjectClass() {
		return cls;
	}
	
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		
		if(cls.equals(N_EALRY_BEFOR_B.class))
		{
			registPropertyEditorB();
		}
		else
			if(cls.equals(N_EALRY_AFTER_B.class))
			{
				registPropertyEditorA();
			}
			else
				if(cls.equals(N_VIEW_TIME_BEAR.class))
				{
					registPropertyEditorBear();
				}

		return nRet;
	}
	
	
	private void registPropertyEditorB() {
		VniEditor vni = new VniEditor();
		getUIDataObjectBinder().registerCustomEditor(N_EALRY_BEFOR_B.class, "KIND", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EALRY_BEFOR_B.class, "NOTE", vni);
        getUIDataObjectBinder().registerCustomEditor(N_EALRY_BEFOR_B.class, "BB_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EALRY_BEFOR_B.class, "EE_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        
	}
	
	private void registPropertyEditorA() {
		VniEditor vni = new VniEditor();
		getUIDataObjectBinder().registerCustomEditor(N_EALRY_AFTER_B.class, "KIND", vni);
		getUIDataObjectBinder().registerCustomEditor(N_EALRY_AFTER_B.class, "NOTE", vni);
        getUIDataObjectBinder().registerCustomEditor(N_EALRY_AFTER_B.class, "BB_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_EALRY_AFTER_B.class, "EE_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        
	}
	
	private void registPropertyEditorBear() {
		VniEditor vni = new VniEditor();
		getUIDataObjectBinder().registerCustomEditor(N_VIEW_TIME_BEAR.class, "HOTEN", vni);
		getUIDataObjectBinder().registerCustomEditor(N_VIEW_TIME_BEAR.class, "TGBHXHTN", vni);
        getUIDataObjectBinder().registerCustomEditor(N_VIEW_TIME_BEAR.class, "BB_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_VIEW_TIME_BEAR.class, "EE_DATES", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        getUIDataObjectBinder().registerCustomEditor(N_VIEW_TIME_BEAR.class, "DATES_BEAR", PropertyEditors.createDateEditor("dd/MM/yyyy"));
        
	}
	
	

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}
}
