package ds.program.fvhr.ngan.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.GroupBox;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;

public class DeptUserControl_FactDetail extends Row {
//Form lua chon tim kiem co chi tiet f1,f2... va theo ngay,thang,nam
	
	private String 	_fact;
	private String 	_group;
	private String  _dept;
	private String	_id_dept;
	private String 	_error;
	private String empsn;
	
	public Label 			lb_empsn;
	public TextField		txt_empsn;
	
	public Label 			lb_fact;
	public SelectField 		sf_fact;
	
	public Label			lb_group;
	public SelectField		sf_group;
	
	public Label			lb_dept;
	public SelectField		sf_dept;
	public Label			lb_id_dept;
	public SelectField		sf_id_dept;
	
	public Label			lb_date;
	public DscDateField		df_date;
	public DscField 		tf_date;
	public Button			bt_refresh;
	
	public RadioButton rdio_f1,rdio_f2,rdio_f3,rdio_f5,rdio_f6,rdio_khac;
    public ButtonGroup bt_fact_detail,group_time;
    public GroupBox gb_fact_detail;
    public boolean flag_empsn = true;//flag_empsn = false so the nhap k hop le, bao loi
	//them ham nay de khi tke cac form khac tim thong tin = enter de thay flag_empsn = false thi k tim den ham 'Kt_vungqly_khi_nhap_st' nua neu khong se hien thi tbao nhieu lan 
    
    public RadioButton  rdio_date,rdio_month,rdio_year;
    OBJ_EMPSN obj_e = new OBJ_EMPSN();
    
    String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	public Calendar ngayhientai ;
    
	
	SimpleDateFormat _sf 	= new SimpleDateFormat("dd/MM/yyyy");
	OBJ_UTILITY obj_util 	= new OBJ_UTILITY();
	
	public TextField getEMPSN_TextField(){
		return txt_empsn;
	}
	
	
	public String getEmpsn(){
		return txt_empsn.getText();
	}
	
	public String getFact(){
		_fact	= sf_fact.getSelectedItem() == null ? "" : sf_fact.getSelectedItem().toString();
		return _fact.trim();
	}
	
	public String getGroup(){
		_group	= sf_group.getSelectedItem() == null ? "" : Vni2Uni.convertToVNI(sf_group.getSelectedItem().toString());
		return _group.trim();
	}
	
	public String getDept(){
		_dept	= sf_dept.getSelectedItem()	== null ? "" : sf_dept.getSelectedItem().toString();
		return _dept;
	}
	
	public String getNameDept(){
		_dept	= sf_dept.getSelectedItem()	== null ? "" : Vni2Uni.convertToVNI(sf_dept.getSelectedItem().toString());
		return _dept.trim();
	}
	
	public String getIDDept(){
		boolean isfullname = true;
		
		isfullname = this.getFact() == ""? true : false;	
		if(isfullname){
			return getIDdept_by_fullName(this.getNameDept());
		}
		return getIDDept_by_Fact(this.getFact(),this.getGroup(),this.getNameDept());
	}
	
	
	
	private String getIDdept_by_fullName(String nameDept) {
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(1, "select o.ID_DEPT from N_DEPARTMENT o where o.NAME_DEPT = ? ", new String[]{nameDept});
		if(list.size() > 0){
			return list.get(0).toString(); 
		}
		return "";
	}

	private String getIDDept_by_Fact(String fact, String group, String nDept) {
		List list = null;
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		//BEGIN *25/07/2012 NGAN THEM VI NEU DON VI K CO GROUP THI NAME_GROUP = '' K HIEU PHAI LA NAME GROUP IS NULL MOI HIEU
		if(group.equals(""))
		{
			list = dao.find(1, "select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT = ? and o.NAME_GROUP IS NULL and o.NAME_DEPT_NAME = ?", new String[]{fact,nDept});
		}else
		//END *25/07/2012 NGAN THEM VI NEU DON VI K CO GROUP THI NAME_GROUP = '' K HIEU PHAI LA NAME GROUP IS NULL MOI HIEU
		{
			list = dao.find(1, "select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT = ? and o.NAME_GROUP = ? and o.NAME_DEPT_NAME = ?", new String[]{fact,group,nDept});
		}
		
		if(list.size() > 0){
			return list.get(0).toString(); 
		}
		return "";
	}

	public String getError(){
		return _error;
	}
	
	public Date getDate(){

		Date date = null;
		if(!df_date.getText().equals("")){
			try {
				date = _sf.parse(df_date.getText());
			} catch (ParseException e) {
				_error = _error + " - Sai định dạng ngày tháng";
			}
		}
		return date;
	}
	
	public DeptUserControl_FactDetail() {
		InitComponent();
	}
	
	public DeptUserControl_FactDetail(boolean vbleEmpsnRow,boolean vbleDateRow){
		InitComponent();
		this.setVisibleEmpsnRow(vbleEmpsnRow);
		this.setVisebleDateRow(vbleDateRow);
	}
	
	public void setExFactAction(ActionListener exFactAction) {
		if (exFactAction!=null) sf_fact.addActionListener(exFactAction);
	}	

	public void reset_group_dept()//Ngan xet nhom va don vi ve null
	{
		ListBinder.refreshIndex(sf_group, null);
		ListBinder.refreshIndex(sf_dept, null);
		ListBinder.refreshIndex(sf_id_dept, null);
		
	}
	
	public void setTF(boolean value)//Ngan hien thi enable cho chon hay k cho chon
	{
		sf_group.setEnabled(value);
	//	sf_dept.setEnabled(value); 
	}
	
	private void setTF1(boolean value)
	{
		sf_dept.setEnabled(value);
		sf_id_dept.setEnabled(value);
	}
	
	protected void setF(boolean value){
		rdio_f1.setEnabled(value);
		rdio_f2.setEnabled(value);
		rdio_f3.setEnabled(value);
		rdio_f5.setEnabled(value);
		rdio_f6.setEnabled(value);
		rdio_khac.setEnabled(value);
	}
	
	protected void reset_fact_detail()
	{   
		rdio_f1.setSelected(false);
		rdio_f2.setSelected(false);
		rdio_f3.setSelected(false);
		rdio_f5.setSelected(false);
		rdio_f6.setSelected(false);
		rdio_khac.setSelected(false);
		gb_fact_detail.setForeground(Color.LIGHTGRAY);
		
	}

	private void InitComponent() {
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		
		Column col1 = new Column();
		Column col2	= new Column();
		Column col3	= new Column();
		Column col4 = new Column();
		
		
		
		this.add(col1);
		this.add(col2);
		this.add(col3);
		this.add(col4);
		
		this.setCellSpacing(new Extent(10));
		this.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		
		col1.setCellSpacing(new Extent(11));
		col2.setCellSpacing(new Extent(5));
		col3.setCellSpacing(new Extent(5));
		col4.setCellSpacing(new Extent(5));
		
		lb_fact = new Label("Xưởng");
		sf_fact	= new SelectField();
		sf_fact.setWidth(new Extent(170));
		
		
		
		lb_group = new Label("Nhóm ");
		sf_group = new SelectField();
		sf_group.setWidth(new Extent(170));

		lb_dept = new Label("Đơn vị");
		sf_dept	= new SelectField();
		sf_dept.setWidth(new Extent(167));
		//sf_dept.setListRowCount(5);
		sf_dept.setBackground(Color.WHITE);
		
		lb_id_dept = new Label("Mã đơn vị:");
		sf_id_dept = new SelectField();
		sf_id_dept.setWidth(new Extent(170));
		
		lb_empsn = new Label("Số thẻ");
		txt_empsn = new TextField();
		txt_empsn.setWidth(new Extent(164));
		txt_empsn.setMaximumLength(8);
		
		
		lb_date		= new Label("Ngày");
		df_date		= new DscDateField();
		df_date.setDateFormat(_sf);
		df_date.getDateChooser().setLocale(Locale.ENGLISH);
		df_date.setWidth(new Extent(167));
		tf_date	= new DscField();
		/*tf_date.setText("-- Chọn ngày --");
		df_date.setTextField(tf_date);*/
		df_date.setSelectedDate(Calendar.getInstance());
		
		//*Begin chon f1,f2...
		    gb_fact_detail = new GroupBox();
	        bt_fact_detail = new ButtonGroup();
	        gb_fact_detail.setWidth(new Extent(60));
	        gb_fact_detail.setHeight(new Extent(100));
	        rdio_f1			= new RadioButton("F1");
	        rdio_f1.setGroup(bt_fact_detail);
	        rdio_f2			= new RadioButton("F2");
	        rdio_f2.setGroup(bt_fact_detail);
	        rdio_f3			= new RadioButton("F3");
	        rdio_f3.setGroup(bt_fact_detail);
	        rdio_f5			= new RadioButton("F5");
	        rdio_f5.setGroup(bt_fact_detail);
	        rdio_f6			= new RadioButton("F6");
	        rdio_f6.setGroup(bt_fact_detail);
	        rdio_khac 		= new RadioButton("Khac");	
	        rdio_khac.setGroup(bt_fact_detail);
	        
	        gb_fact_detail.add(rdio_f1);
	        gb_fact_detail.add(rdio_f2);
	        gb_fact_detail.add(rdio_f3);
	        gb_fact_detail.add(rdio_f5);
	        gb_fact_detail.add(rdio_f6);
	        gb_fact_detail.add(rdio_khac);
		//*End chon f1,f2,f3...
	        
	        //SET NHOM & DVI VE NULL KHI CLICK VAO bt_fact_detail
	        rdio_f1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	        
	        rdio_f2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	        
	        rdio_f3.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	        
	        rdio_f5.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	        
	        rdio_f6.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	        
	        rdio_khac.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					reset_group_dept();
					setTF(false);
					setTF1(false);
					
				}
			});
	       
	        //END SET NHOM & DVI VE NULL KHI CLICK VAO bt_fact_detail
	        
	    //**Begin chon theo ngay,thang,nam
	        group_time	= new ButtonGroup();
	        rdio_date 	= new RadioButton("Theo ngày");
			rdio_date.setSelected(true);
			rdio_date.setGroup(group_time);
			rdio_month	= new RadioButton("Theo tháng");
			rdio_month.setGroup(group_time);
			rdio_year	= new RadioButton("Theo năm");
			rdio_year.setGroup(group_time);
	    //**End chon theo ngay,thang,nam  
			bt_refresh 	= new Button();
			bt_refresh.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnRefresh.gif"));
		//	bt_refresh.setText("Refresh");
				
				
		
		col1.add(lb_fact);				col2.add(sf_fact);
		col1.add(lb_group);				col2.add(sf_group);
		col1.add(lb_dept);				col2.add(sf_dept);
		col1.add(lb_id_dept);			col2.add(sf_id_dept);
		col1.add(lb_empsn);				col2.add(txt_empsn);
		col1.add(lb_date);				col2.add(df_date);
										
		
		/*GridLayoutData	group_time_layout = new GridLayoutData();
		group_time_layout.setAlignment(new Alignment(Alignment.DEFAULT, Alignment.BOTTOM));*/
		
		col3.add(gb_fact_detail);
		col3.add(rdio_date);
		col3.add(rdio_month);
		col3.add(rdio_year);
		//col3.setLayoutData(group_time_layout);
		col4.add(bt_refresh);
		bt_refresh.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			//	ListBinder.refreshIndex(sf_fact, e);
				ListBinder.refreshIndex(sf_group, e);
				ListBinder.refreshIndex(sf_dept, e);
			//	ListBinder.refreshIndex(sf_id_dept, e);
				ListBinder.bindSelectField(sf_fact, FVGenericInfo.getFactories(), false);
			    ListBinder.bindSelectField(sf_id_dept, FVGenericInfo.getAllDept(), false);
				txt_empsn.setText("");
				txt_empsn.setEnabled(true);
				reset_fact_detail();
				setF(false);
				setTF(false);
				setTF1(true);
				sf_dept.setEnabled(false); 
				
			}
		});
		//---------actionEvent----------
		
	
		    ListBinder.bindSelectField(sf_fact, FVGenericInfo.getFactories(), false);
			ListBinder.bindSelectField(sf_id_dept, FVGenericInfo.getAllDept(), false);
				
        setF(false);
        gb_fact_detail.setForeground(Color.LIGHTGRAY);
        
       
        
        txt_empsn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				 flag_empsn = true;
				 empsn = txt_empsn.getText().toString();
				if(!obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
				{   
					flag_empsn = false;//neu = false khong thoa dk
					return; 
				}
			
			}
		});
        
		sf_fact.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(sf_fact.getSelectedIndex() != 0){
					txt_empsn.setText("");
					txt_empsn.setEnabled(false);
				}
				
				//if(sf_fact.getSelectedItem() != null ){
					sf_group.setSelectedIndex(-1);
					sf_id_dept.setSelectedIndex(-1);
					sf_dept.setSelectedIndex(-1);
					ListBinder.bindSelectField(sf_group, FVGenericInfo.getGroup(sf_fact.getSelectedItem().toString()), true);
				//	ListBinder.bindSelectField(sf_dept, FVGenericInfo.getDeptName(sf_fact.getSelectedItem().toString()), true);
				//	ListBinder.bindSelectField(sf_id_dept, FVGenericInfo.getDept(sf_fact.getSelectedItem().toString()), false);
				//*Begin 20/02/2012 Ngan them	
					ListBinder.refreshIndex(sf_dept, e);
					ListBinder.refreshIndex(sf_id_dept, e);
					sf_dept.setEnabled(false);   
					sf_id_dept.setEnabled(false);   
					if(sf_fact.getSelectedItem().toString().equals("FVL"))
					{
						gb_fact_detail.setForeground(Color.BLACK);
						setF(true);
						setTF(true);
					}else
					{
						reset_fact_detail();
						setF(false);
						setTF(true);
					}
				//End 20/02/2012 Ngan them	
					
				//}
				
			}
		});
		
		sf_group.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				//if(sf_group.getSelectedItem() != null){
					//sf_id_dept.setSelectedIndex(-1);
					MappingPropertyEditor mpe = FVGenericInfo.getNameDeptName(sf_fact.getSelectedItem().toString(),Vni2Uni.convertToVNI(sf_group.getSelectedItem().toString()));
					ListBinder.bindSelectField(sf_id_dept, mpe, false);
					ListBinder.bindSelectField(sf_dept, mpe, true);
			//		sf_dept.setEnabled(true);//13/12/2012
				//}
			}  
		});
		
	sf_dept.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			sf_id_dept.setSelectedIndex(sf_dept.getSelectedIndex());
		}
	});
	
	sf_id_dept.addActionListener(new ActionListener() {
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			
			if(sf_id_dept.getSelectedItem() != null)
			{   
				txt_empsn.setText("");
				txt_empsn.setEnabled(false);
			}
			
			
			SelectItem item = (SelectItem) sf_id_dept.getSelectedItem();	
			//*BEGIN 24/07/2012 NGAN THEM DE SELECT NHUNG DON VI KHONG CO GROUP_NAME
			String groupName = "";
			String factName  = "";
			
			String sql = "SELECT D.NAME_GROUP FROM N_DEPARTMENT D"+
						 " WHERE D.ID_DEPT = '"+item+"'";
			
			List<String> obj_gName = obj_util.Exe_Sql_String(sql);
			
			if(obj_gName.get(0) == null)
			{
				factName  = FVGenericInfo.findFactFollowDept(item.getValue().toString());
			}else
			{
				groupName = FVGenericInfo.findGroup(item.getValue().toString());
				factName  = FVGenericInfo.findFactFollowDept(item.getValue().toString());
			}
			//*END 24/07/2012 NGAN THEM DE SELECT NHUNG DON VI KHONG CO GROUP_NAME
			
			/*String groupName = FVGenericInfo.findGroup(item.getValue().toString());
			String factName  = FVGenericInfo.findFactFollowDept(item.getValue().toString());*/
			String deptName = FVGenericInfo.findDeptNameFolowDept(item.getValue().toString());
			ListBinder.refreshIndex(sf_fact, factName);
			ListBinder.bindSelectField(sf_group, FVGenericInfo.getGroup(sf_fact.getSelectedItem().toString()), true);//13/12/2012
			
			//if (groupName!=null)
			if (!groupName.equals(""))
			{
				
				ListBinder.refreshIndex(sf_group, groupName);
			    ListBinder.bindSelectField(sf_dept, FVGenericInfo.getDeptName(sf_fact.getSelectedItem().toString(), sf_group.getSelectedItem().toString()),true);
			    ListBinder.refreshIndex(sf_dept, deptName);
			}else
			{
				ListBinder.bindSelectField(sf_dept, FVGenericInfo.getDeptName(sf_fact.getSelectedItem().toString()), true);
				ListBinder.refreshIndex(sf_dept, deptName);
			}
		}
			
			
	});
		
	}
	
	 
	
	public void setVisibleEmpsnRow(Boolean a){
		lb_empsn.setVisible(a);
		txt_empsn.setVisible(a);
	}
	
	
	public void setVisebleDateRow(Boolean a){
		lb_date.setVisible(a);
		df_date.setVisible(a);
	}
	
	
	public void setDateInstance(Date date){
		tf_date.setText(_sf.format(date));
		
	}
	
	
	public List<String> getListEmpsn(){
		String sql = "";
		ArrayList<String> listEmp = new ArrayList<String>();
		if(!this.getIDDept().equals("")){
			IGenericDAO<N_EMPLOYEE, String> dao = Application.getApp().getDao(N_EMPLOYEE.class);
			DetachedCriteria dc = DetachedCriteria.forClass(N_EMPLOYEE.class);
							 dc.add(Restrictions.eq("DEPSN", getIDDept()));
			List<N_EMPLOYEE> listEmps = dao.findByCriteria(5000, dc);
			for(N_EMPLOYEE ele : listEmps){
				listEmp.add(ele.getEMPSN());
			}
			return listEmp;
		}
		else if(!this.getGroup().equals("")){
			sql =   " Select e.EMPSN From N_EMPLOYEE e,N_DEPARTMENT d " +
					" Where e.DEPSN = d.ID_DEPT " +
					" And d.NAME_GROUP = '"+this.getGroup()+"'" +
					" And d.NAME_FACT  = '"+this.getFact() +"'";
			
			return listEmp = (ArrayList<String>) obj_util.Exe_Sql_String(sql);
		
		}else if(!this.getFact().equals("")){
			sql =   " Select e.EMPSN From N_EMPLOYEE e,N_DEPARTMENT d " +
			" Where e.DEPSN = d.ID_DEPT " +
			" And d.NAME_FACT  = '"+this.getFact() +"'";
	
			return listEmp = (ArrayList<String>) obj_util.Exe_Sql_String(sql);
		}
		return listEmp = null;
	}
	


}
