package ds.program.fvhr.ui;

import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_IC_CARD;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import fv.util.ListBinder;

public class Employee01MQuery extends QueryNormal2 {

	private static final long serialVersionUID = 1L;
	private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPSN_DscField1;
    private nextapp.echo2.app.Label ID_NO_CaptionLabel;
    private dsc.echo2app.component.DscField ID_NO_DscField2;
    private nextapp.echo2.app.Label EMPCN_CaptionLabel;
    private dsc.echo2app.component.DscField EMPCN_DscField3;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private nextapp.echo2.app.SelectField DEPSN_SelectField1;
	private Button btnReset;

	/**
	 * Creates a new <code>Employee01MQuery</code>.
	 */
	public Employee01MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		
	}
	
	@Override
	public Employee01MProgram getProgram() {
		return (Employee01MProgram) super.getProgram();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);

		ListBinder.bindSelectField(DEPSN_SelectField1, getProgram().getMasterDataContent().getDepsnEditor(), true);
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
        key = "N_EMPLOYEE.EMPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPSN_CaptionLabel.setText(key);
        key = "N_EMPLOYEE.ID_NO";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        ID_NO_CaptionLabel.setText(key);
        key = "N_EMPLOYEE.EMPCN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        EMPCN_CaptionLabel.setText(key);
        key = "N_EMPLOYEE.DEPSN";
        try {key=uicaption.getResourceBundle().getString(key);} catch(Exception e){}
        DEPSN_CaptionLabel.setText(key);
		return ret;
	}
	
	public void checkIC(String empcn) {		
		if (empcn!=null){
			if (!empcn.matches("[0-9]{10}")){
				Application.getApp().showMessageDialog("Trạng thái IC", "IC không hợp lệ", MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK);				
			}
			String icsql = "from N_IC_CARD e where e.IC_NO=?";
			IGenericDAO<N_IC_CARD, String> icdao = Application.getApp().getDao(N_IC_CARD.class);
			List<N_IC_CARD> list = icdao.find(1, icsql, new Object[]{empcn});
			if (list!=null&&list.size()>0){
				if (list.get(0).getUSE_STATUS().equals("1")){
					Application.getApp().showMessageDialog("Trạng thái IC", "IC đang sử dụng", MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK);					
				}
				else if (list.get(0).getUSE_STATUS().equals("4")){
					Application.getApp().showMessageDialog("Trạng thái IC", "IC bị hư or mất", MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK);					
				}				
			}else{
				Application.getApp().showMessageDialog("Trạng thái IC", "IC không tồn tại", MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK);
			}
		}
	}
	@Override
	protected void doQuery() {
		int ret =0;
		String dk ="";
		String col ="";
		
		String sql = "";
		if (!EMPSN_DscField1.getText().trim().equals("")){
			sql = "o.EMPSN='" + EMPSN_DscField1.getText().trim() + "'";
			dk = EMPSN_DscField1.getText().trim();
			col="EMPSN";
		}else if (!EMPCN_DscField3.getText().trim().equals("")){
			sql = "o.EMPCN='"+ EMPCN_DscField3.getText().trim() + "'";
			//checkIC(EMPCN_DscField3.getText().trim());
		}else if (!ID_NO_DscField2.getText().trim().equals("")){
			sql= "o.ID_NO='" + ID_NO_DscField2.getText().trim() + "'";
			dk = ID_NO_DscField2.getText().trim();
			col="ID_NO";
		} else if (DEPSN_SelectField1.getSelectedIndex()>=0){
			sql = "o.DEPSN='" + ListBinder.get(DEPSN_SelectField1) + "'";			
		}
		if (!sql.equals("")){
			ProgramCondition pc = new ProgramCondition(sql);
			getProgram().setQueryCondition(pc);
			getProgram().refresh();
		}else{
			ProgramCondition pc = new ProgramCondition("1<>1");
			getProgram().setQueryCondition(pc);
			getProgram().refresh();
		}
		// HA bo sung	06/09/2013			
		// if thong tin CNV do user ko co quyen quan ly thi canh bao vung quan ly cua CNV do cho User duoc biet
		// chi tim theo so the va CMND vi don vi va EMCN se tra ve >=1 row		
		if (dk !=""){
			InsuranceDAO ins = new InsuranceDAO();
			String vungQL = ins.GetField("USER_MANAGE_ID", "N_EMPLOYEE", col, "", "", dk, "", "");			
			String fact = ins.GetField("NAME_FACT", "N_QUANLY", "MAQL", "", "", vungQL, "", "");
			Application.getApp().showMessageDialog("Cảnh Báo", "CNV này thuộc "+fact+".", MessageDialog.CONTROLS_OK);
		}
		// end bo sung			
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
        rootLayout = new Grid();
//        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("");        
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setId("EMPSN_DscField1");
        EMPSN_DscField1.setMaximumLength(8);
        EMPSN_DscField1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				doQuery();
			}
		});
        rootLayout.add(EMPSN_DscField1);
        ID_NO_CaptionLabel = new nextapp.echo2.app.Label();
        ID_NO_CaptionLabel.setText("");
        rootLayout.add(ID_NO_CaptionLabel);
        ID_NO_DscField2 = new dsc.echo2app.component.DscField();
        ID_NO_DscField2.setId("ID_NO_DscField2");
        ID_NO_DscField2.setMaximumLength(9);
        ID_NO_DscField2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				doQuery();
			}
		});        
        rootLayout.add(ID_NO_DscField2);
        EMPCN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPCN_CaptionLabel.setText("");
        rootLayout.add(EMPCN_CaptionLabel);
        EMPCN_DscField3 = new dsc.echo2app.component.DscField();
        EMPCN_DscField3.setId("EMPCN_DscField3");
        EMPCN_DscField3.setMaximumLength(12);
        EMPCN_DscField3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				doQuery();
			}
		});          
        rootLayout.add(EMPCN_DscField3);
        DEPSN_CaptionLabel = new nextapp.echo2.app.Label();
        DEPSN_CaptionLabel.setText("");
        rootLayout.add(DEPSN_CaptionLabel);
        DEPSN_SelectField1 = new nextapp.echo2.app.SelectField();
        DEPSN_SelectField1.setId("DEPSN_SelectField1");
        rootLayout.add(DEPSN_SelectField1);        
        rootLayout.add(new Label());
        btnReset = new Button("Reset");
        btnReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				EMPSN_DscField1.setText("");
				EMPCN_DscField3.setText("");
				ID_NO_DscField2.setText("");
				DEPSN_SelectField1.setSelectedIndex(-1);
			}
        });
        rootLayout.add(btnReset);
	}

}
