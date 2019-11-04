package ds.program.fvhr.ui;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;

public class ListDependent01MQuery extends QueryNormal2 {

	private nextapp.echo2.app.Grid rootLayout;
	private DscField dscFSoThe;
	private Label lbSoThe;
	/**
	 * Creates a new <code>ListDependent01MQuery</code>.
	 */
	public ListDependent01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		dscFSoThe .addActionListener(new ActionListener() {
			
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

		// 使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
		// TODO 請修改元件之PropertyEditor

		// <一般查詢條件定義>

		String key;
		return ret;

	}
	
	@Override
	protected void doQuery() {
		ProgramCondition pc = new ProgramCondition("o.EMPSN=?", new Object[]{dscFSoThe.getText()});
		getProgram().setQueryCondition(pc);
		getProgram().refresh();
	}
	

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(2);
		add(rootLayout);
		Label lbSoThe = new Label();
		lbSoThe.setText("Số Thẻ");
		rootLayout.add(lbSoThe);
		dscFSoThe = new DscField();
		rootLayout.add(dscFSoThe);		
	}

}
