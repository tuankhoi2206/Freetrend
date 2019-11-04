package ds.program.fvhr.tien.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.domain.N_EMP_ICCARD;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import dsc.echo2app.query.QryCondition;
import echopointng.DateField;
import fv.util.ApplicationHelper;
import fv.util.HRUtils;
import fv.util.Vni2Uni;

public class N_GET_DATAMAIN00MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private Label lblThang;
	private DateField dscThang;
	private Label lblEmpsn;
	private DscField txtEmpns;
	private DscField tf_date;
	SimpleDateFormat sp=new SimpleDateFormat("dd/MM/yyyy");
	Date dtNow=new Date();
	private Label lblHovaten_;

	/**
	 * Creates a new <code>N_GET_DATAMAIN00MQuery</code>.
	 */
	public N_GET_DATAMAIN00MQuery() {
		super();

		// Add design-time configured components.
		initComponents();
		doMoreint();
	}
	private void doEmployee(ActionEvent e) {
		//TODO Implement.
		//N_SP_WDAY data = (N_SP_WDAY) getDataObject();
		IGenericDAO<N_EMPLOYEE, String> dao=Application.getApp().getDao(N_EMPLOYEE.class);
		N_EMPLOYEE emsp=dao.findById(txtEmpns.getText());
		if(emsp==null)
		{
			lblHovaten_.setText("Số thẻ không tồn tại");			
		}
		else
		{
			String dept=emsp.getDEPSN();
			IGenericDAO<N_DEPARTMENT,String> objDep_dao=Application.getApp().getDao(N_DEPARTMENT.class);
			N_DEPARTMENT obj_Department=objDep_dao.findById(dept);
			if(obj_Department!=null)
			{
				lblHovaten_.setText(Vni2Uni.convertToUnicode(emsp.getFULL_NAME()+"."+obj_Department.getNAME_FACT()+"."+obj_Department.getNAME_DEPT_NAME()));
			}
			
		}				
	}
	private void doMoreint()
	{
		rootLayout.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
				10, Extent.PX), new Extent(10, Extent.PX), new Extent(10,
				Extent.PX)));
		lblThang=new  Label();
		dscThang=new DateField();
		//dscThang.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(0,
			//	Extent.PX), new Extent(10, Extent.PX), new Extent(0, Extent.PX)));
		lblThang.setText("Thang:");
		tf_date	= new DscField();
		tf_date.setVisible(true);
		tf_date.setText(sp.format(dtNow));
		RowLayoutData lblThangLayoutData = new RowLayoutData();
		lblThangLayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(100, Extent.PX),
				new Extent(0, Extent.PX)));
		lblThang.setLayoutData(lblThangLayoutData);
		dscThang.setTextField(tf_date);
		Row row1=new Row();
		row1.add(lblThang);
		row1.add(dscThang);
		
		rootLayout.add(row1);
		lblEmpsn=new Label();
		lblEmpsn.setText("Số thẻ:");
		lblEmpsn.setLayoutData(lblThangLayoutData);
	    txtEmpns=new 	DscField();
	    txtEmpns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmployee(e);
			}
		});
	    Row row2=new Row();
	    row2.add(lblEmpsn);
	    row2.add(txtEmpns);
	    lblHovaten_ = new Label();
		lblHovaten_.setVisible(true);
		
		row2.add(lblHovaten_);
	    rootLayout.add(row2);
		
	}
	@Override
	protected void doQuery()
	{
		String empsn =txtEmpns.getText();
		List<Object> params = new ArrayList<Object>();
		String strSQL="";
		if (!empsn.matches("[0-9]{8}")){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Số thẻ không hợp lệ");
			return;
		}	
		
		HRUtils util = ApplicationHelper.getHRUtils();
		if (!util.isWorkingOrQuit(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Số thẻ không có trong hệ thống");
			return;
		}
		//check permission
		if (!util.getPermissionValidator().hasEmpsnPermission(empsn)){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR,"Anh/chị không có quyền thao tác số thẻ này");
			return;
		}
		txtEmpns.requestFocus();
		Date dtThang= OBJ_UTILITY.DateFormat_DDMMYYYY(dscThang.getText().trim());
		String strThang=sp.format(dtThang);
		
		String strThang_=strThang.substring(3, 10);
		String strEMPCN="";
		IGenericDAO<N_EMP_ICCARD, String> objDao=Application.getApp().getDao(N_EMP_ICCARD.class);
		List<N_EMP_ICCARD> listIcCard=objDao.find(1," from N_EMP_ICCARD where EMPSN=? AND USE_STATUS='1'", empsn);
		N_EMP_ICCARD objN_EMP_ICCARD=null;		
		if(listIcCard.size()>0)
		{
			objN_EMP_ICCARD=listIcCard.get(0);
			strEMPCN=objN_EMP_ICCARD.getEMPCN();			
		}
		if (!strEMPCN.equals(""))
		{
			strSQL=strSQL+ "  and o.EMPCN=?";
			params.add(strEMPCN);
		}
		
		if(!strThang_.equals(""))
		{
			strSQL=strSQL+ " and o.DATES like '%"+strThang_+"%'";
		}
		strSQL = StringUtils.substringAfter(strSQL, " and ");
		ProgramCondition pc = new ProgramCondition(strSQL, params.toArray());			
		getProgram().setQueryCondition(pc);
		getProgram().refresh();	
		
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
        rootLayout.setSize(1);
        add(rootLayout);
	}

}
