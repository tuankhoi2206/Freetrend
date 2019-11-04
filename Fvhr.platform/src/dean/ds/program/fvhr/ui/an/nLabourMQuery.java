package ds.program.fvhr.ui.an;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import fv.util.library;

public class nLabourMQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    
    private Label lblempsn;
    private DscField txtempsn;
    library l=new library();

	/**
	 * Creates a new <code>nLabourMQuery</code>.
	 */
	public nLabourMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	@Override
	protected void doQuery() {
		if(txtempsn.getText().equals("")){
			return;
		}
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		DSPB02 u = udao.findById(getProgram().getLoginInfo().getUserID());
		if(!l.check_QL(txtempsn.getText(), "", "", "", "", u.getPB_USERNO())){
			return;
		}
		String sql= "o.EMPSN='"+txtempsn.getText()+"'";		
		if (sql.equals("")){
			return;
		}else{
			ProgramCondition pc = new ProgramCondition(sql,new Object[]{});
			getProgram().setBaseCondition(pc);
		}
		getProgram().query(sql, new Object[]{});
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		windowPane.setWidth(new Extent(250));
		windowPane.setHeight(new Extent(110));
		windowPane.setPositionX(new Extent(Application.getApp().getScreenWidth()/2-250));
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
	        rootLayout.setInsets(new Insets(new Extent(5, Extent.PX)));	        
	        rootLayout.setSize(2);
	        add(rootLayout);
	        lblempsn = new nextapp.echo2.app.Label();
	        lblempsn.setText("SỐ THẺ");
	        rootLayout.add(lblempsn);
	        txtempsn = new dsc.echo2app.component.DscField();
	        txtempsn.setMaximumLength(8);
	        txtempsn.requestFocus();
	        txtempsn.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e) {
					doQuery();
				}
	        });
	        rootLayout.add(txtempsn);
	}

}
