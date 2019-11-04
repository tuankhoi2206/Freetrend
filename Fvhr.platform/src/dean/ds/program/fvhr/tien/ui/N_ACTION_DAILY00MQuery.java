package ds.program.fvhr.tien.ui;

import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_ACTION_DAILY00MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private Label lblEmpsn;
	private DscField txtEmpsn;

	/**
	 * Creates a new <code>N_ACTION_DAILY00MQuery</code>.
	 */
	public N_ACTION_DAILY00MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	protected void doQuery() {
		String sql = "";
		String strEmpsn=txtEmpsn.getText();
			
		if (!strEmpsn.equals("") ){
			sql = "o.EMPSN=?";
			getProgram().query(sql, new Object[]{strEmpsn});
		}	
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
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
        lblEmpsn=new Label();
        lblEmpsn.setText("Số Thẻ:");
        rootLayout.add(lblEmpsn);
        txtEmpsn=new DscField();
        rootLayout.add(txtEmpsn);
        add(rootLayout);
	}

}
