package ds.program.fvhr.ui;

import java.util.List;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;

import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscGroupRadioButton;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import dsc.echo2app.task.AOODocReportTask;
import fv.util.library;


public class NKYLUATQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private Label lblempsn;
    private DscField txtempsn;
    private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private Label a;
	private Label xuong;
	public SelectField sfxuong;
	public String fact;
	
	
	/**
	 * Creates a new <code>NKYLUATQuery</code>.
	 */
	public NKYLUATQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected void doQuery() {
		if((txtempsn.getText().equals("")) && (sfxuong.getSelectedIndex()==-1)){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Chưa chọn thông tin để tìm kiếm");
			return;
		}
		String sql ="";
		if(radioButton1.isSelected()){
			sql+=" o.ID_PHAT='01'";
		}
		if(radioButton2.isSelected()){
			sql+=" o.ID_PHAT='02'";
		}
		if(radioButton3.isSelected()){
			sql+=" o.ID_PHAT='03'";
		}
		if(!txtempsn.getText().equals("")){
			sql+=" and o.EMPSN='"+txtempsn.getText()+"'";
		}
		if(sfxuong.getSelectedIndex()!=-1){
			fact=sfxuong.getSelectedItem().toString();
			
			sql+=" and o.EMPSN in(select a.EMPSN from N_KYLUAT a,N_EMPLOYEE e," +
					" N_DEPARTMENT d where e.EMPSN=a.EMPSN and d.ID_DEPT=e.DEPSN_TEMP1 " +
					" and d.NAME_FACT='"+sfxuong.getSelectedItem().toString()+"')";
		}
		
		if (sql.equals("")){
			return;
		}else{
			ProgramCondition pc = new ProgramCondition(sql, new Object[]{});
			getProgram().setBaseCondition(pc);
		}
		getProgram().query(sql, new Object[]{});
		
	}
	
	
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);
		DefaultListModel model=(DefaultListModel) sfxuong.getModel();
		library l=new library();
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		DSPB02 u = udao.findById(getProgram().getLoginInfo().getUserID());
		List li=l.vungQL1(u.getPB_USERNO());
		for (int i = 0; i < li.size(); i++) {
			model.add(li.get(i));
		}
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
        rootLayout.setWidth(new Extent(40, Extent.PERCENT));
        rootLayout.setSize(2);
        add(rootLayout);
        lblempsn = new nextapp.echo2.app.Label();
        lblempsn.setText("SỐ THẺ");
        rootLayout.add(lblempsn);
        txtempsn = new dsc.echo2app.component.DscField();
        txtempsn.requestFocus();
        txtempsn.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(sfxuong.getSelectedIndex()!=-1){
					sfxuong.setSelectedIndex(-1);
				}
				
			}
		});
			
        rootLayout.add(txtempsn);
        xuong=new Label();
        xuong.setText("XƯỞNG");
        rootLayout.add(xuong);
        sfxuong=new SelectField();
        sfxuong.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(sfxuong.getSelectedIndex()!=-1){
					txtempsn.setText("");
				}
				
			}
		});
        rootLayout.add(sfxuong);
        a=new Label();
        a.setText("");
        rootLayout.add(a);
        DscGroupRadioButton dscGroupRadioButton1 = new DscGroupRadioButton();
		//dscGroupRadioButton1.setOrientation(Grid.ORIENTATION_HORIZONTAL);
		//dscGroupRadioButton1.setWidth(new Extent(320, Extent.PX));
		//GridLayoutData dscGroupRadioButton1LayoutData = new GridLayoutData();
		
		//dscGroupRadioButton1LayoutData.setAlignment(new Alignment(
				//Alignment.DEFAULT, Alignment.TOP));
		//dscGroupRadioButton1LayoutData.setColumnSpan(2);
		//dscGroupRadioButton1.setLayoutData(dscGroupRadioButton1LayoutData);
        dscGroupRadioButton1.setSize(1);
		rootLayout.add(dscGroupRadioButton1);
		radioButton1 = new RadioButton();
		radioButton1.setText("PHẠT CẢNH CÁO");
		radioButton1.setSelected(true);
		radioButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		dscGroupRadioButton1.add(radioButton1);
		radioButton2 = new RadioButton();
		radioButton2.setText("NGỪNG NÂNG LƯƠNG");
		radioButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		dscGroupRadioButton1.add(radioButton2);
		
		radioButton3 = new RadioButton();
		radioButton3.setText("SA THẢI");
		radioButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		dscGroupRadioButton1.add(radioButton3);
        
	}

}
