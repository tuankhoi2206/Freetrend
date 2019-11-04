package ds.program.fvhr.ngan.ui.Change_Depsn;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.util.OBJ_EMPSN;
import fv.util.Vni2Uni;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;

public class N_CHDEPSNMQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
    private TextField txt_sothe;
    String ma_user 				= "";
    String user_up = Application.getApp().getLoginInfo().getUserID();
    String ma_ql   = 	"";	


	/**
	 * Creates a new <code>N_CHDEPSNMQuery</code>.
	 */
	public N_CHDEPSNMQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();

        String key;
		return ret;

	}
	protected void doQuery() 
	
	{   
		OBJ_EMPSN obj_emp_ = new OBJ_EMPSN(); 
		if (!obj_emp_.Kt_vungqly_khi_nhap_st(txt_sothe.getText(), ma_user,"DEPSN"))
		{
			return;
		}
		IGenericDAO<N_EMPLOYEE,String> dao1 = Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE obj_employee = dao1.findById(txt_sothe.getText());
		
			ma_ql	= obj_employee.getUSER_MANAGE_ID();			
		

		IGenericDAO<N_USER_LIMIT, String> Dao_USER_LIMIT = Application.getApp().getDao(N_USER_LIMIT.class);
		DetachedCriteria	objDC_USER_LIMIT	= DetachedCriteria.forClass(N_USER_LIMIT.class);
							objDC_USER_LIMIT.add(Restrictions.eq("MA_USER", ma_user));
							objDC_USER_LIMIT.add(Restrictions.eq("MA_QL",ma_ql));
		List<N_USER_LIMIT> dataList = Dao_USER_LIMIT.findByCriteria(1, objDC_USER_LIMIT);
		if (dataList.size() <= 0 ){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Bạn không có quyền thao tác");	
		}else
		{
			String sql = "";
			if(!txt_sothe.getText().equals("") ){
				sql = "o.EMPSN=? ";
				getProgram().query(sql, new Object[]{txt_sothe.getText()});
			}
		}
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
        
        Label lsothe = new Label();;
        lsothe.setText("Số thẻ: ");
        rootLayout.add(lsothe);
        
        txt_sothe = new TextField();
        txt_sothe.setMaximumLength(8);
        txt_sothe.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				doQuery();
				
			}
		});
        rootLayout.add(txt_sothe);
        
        
        
        
	}
	

}
