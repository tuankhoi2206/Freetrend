package ds.program.fvhr.ui.insurance;


import java.util.ArrayList;
import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
//import nextapp.echo2.app.Label;

public class N_KYLUAT01MQuery extends QueryNormal2 {
    

	/**
	 * Creates a new <code>N_KYLUAT01MQuery</code>.
	 */
	private Grid rootLayout;
	private nextapp.echo2.app.Label Empsn_Label;
	private dsc.echo2app.component.DscField Empsn_DscField;
	
	@Override
	protected void doQuery() {
		// TODO Auto-generated method stub
		String sql ="";
		String empsn =Empsn_DscField.getText();
		List<Object> params = new ArrayList<Object>();
		if (!empsn.equals(""))
		{
			InsuranceDAO ins = new InsuranceDAO();
			String chuoiTB = ins.checkEmpsn(empsn);
			if (chuoiTB!= null)
			{
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, chuoiTB);
				return;
			}
			else
			{
				boolean qly = ins.checkQLyEmpsn(empsn);
				if (qly==false)
				{
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có quyền thao tác trên dữ liệu.");
					return;					
				}				
			}
			sql = "o.EMPSN=?";
			params.add(empsn);
		}
		else
		{
			sql = "o.EMPSN=''";
		}
		
		if (sql.equals("")) sql = "1<>1";
		ProgramCondition pc = new ProgramCondition(sql, params.toArray());
		getProgram().setQueryCondition(pc);
		getProgram().refresh();
	}
	
	public N_KYLUAT01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		Empsn_DscField.addActionListener(new ActionListener() {
			
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
        key = "N_KYLUAT.EMPSN";
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
	//@Override
	private void initComponents() {
		
		
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        
        Empsn_Label = new nextapp.echo2.app.Label();
        Empsn_Label.setText("N_KYLUAT.EMPSN");
        rootLayout.add(Empsn_Label);
        
        Empsn_DscField = new DscField();
        Empsn_DscField.setId("Empsn_DscField");
        Empsn_DscField.setWidth(new Extent(4, Extent.CM));
        rootLayout.add(Empsn_DscField);
				
	}

}
