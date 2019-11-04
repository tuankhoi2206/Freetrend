package ds.program.fvhr.ui.insurance;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_TIME_BEAR01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private nextapp.echo2.app.Label Empsn_Label;
	private dsc.echo2app.component.DscField Empsn_DscField;    

	/**
	 * Creates a new <code>N_TIME_BEAR01MQuery</code>.
	 */
	public N_TIME_BEAR01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		Empsn_DscField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doQuery();
			}
		});
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>
		conditionMgr.addCondition(Empsn_DscField, new QryCondition("EMPSN", QryCondition.Operator.EQ, QryCondition.DataType.STRING));
        String key;
        key="N_TIME_BEAR.EMPSN";
        try{key = uicaption.getResourceBundle().getString(key);}
        catch (Exception e) {
			// TODO: handle exception
        }        
        Empsn_Label.setText(key);        
        
		return ret;

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        Empsn_Label= new nextapp.echo2.app.Label();
        Empsn_Label.setText("N_TIME_BEAR.EMPSN");
        rootLayout.add(Empsn_Label);
        
        Empsn_DscField = new dsc.echo2app.component.DscField();
        Empsn_DscField.setId("Empsn_DscField");
        Empsn_DscField.setWidth(new Extent(4, Extent.CM));
        rootLayout.add(Empsn_DscField);
        
	}

}
