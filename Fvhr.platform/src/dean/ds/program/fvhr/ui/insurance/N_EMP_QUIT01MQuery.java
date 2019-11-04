package ds.program.fvhr.ui.insurance;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import fv.util.BundleUtils;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_EMP_QUIT01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private DscField dscFSoThe;

	/**
	 * Creates a new <code>N_EMP_QUIT01MQuery</code>.
	 */
	public N_EMP_QUIT01MQuery() {
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


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
		return ret;

	}
	
	@Override
	protected void doQuery() {
		String sql ="";
		String empsn =dscFSoThe.getText();
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
		Label lblSoThe = new Label();
		lblSoThe.setText("Số Thẻ");
		rootLayout.add(lblSoThe);
		dscFSoThe = new DscField();
		rootLayout.add(dscFSoThe);
	}

}
