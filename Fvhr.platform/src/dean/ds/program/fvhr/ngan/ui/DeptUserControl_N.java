package ds.program.fvhr.ngan.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.border.EtchedBorder;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;

import echopointng.ComboBox;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;

public class DeptUserControl_N extends Row {
	
	private String 	_fact;
	private String 	_group;
	private String  _dept;
	private String	_id_dept;
	private String 	_error;
	
	private Label 			lb_empsn;
	private TextField		txt_empsn;
	private Label 			lb_fact;
	private SelectField 	sf_fact;
	private Label			lb_group;
	private SelectField		sf_group;
	private Label			lb_dept;
	private ComboBox		cb_dept;
	private Label			lb_date;
	private DscDateField	df_date;
	private DscField 		tf_date;
	
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
		_group	= sf_group.getSelectedItem() == null ? "" : sf_group.getSelectedItem().toString();
		return _group.trim();
	}
	
	public String getNameDept(){
		_dept	= cb_dept.getText()	== null ? "" : cb_dept.getText();
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
		IGenericDAO<N_DEPARTMENT, String> dao = Application.getApp().getDao(N_DEPARTMENT.class);
		List list = dao.find(1, "select o.ID_DEPT from N_DEPARTMENT o where o.NAME_FACT = ? and o.NAME_GROUP = ? and o.NAME_DEPT_NAME = ?", new String[]{fact,group,nDept});
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
	
	public DeptUserControl_N() {
		InitComponent();
	}
	
	public DeptUserControl_N(boolean vbleEmpsnRow,boolean vbleDateRow){
		InitComponent();
		this.setVisibleEmpsnRow(vbleEmpsnRow);
		this.setVisebleDateRow(vbleDateRow);
	}

	private void InitComponent() {
		
		
		Column col1 = new Column();
		Column col2	= new Column();
		
		
		
		this.add(col1);
		this.add(col2);
		
		this.setCellSpacing(new Extent(10));
		this.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		
		col1.setCellSpacing(new Extent(11));
		col2.setCellSpacing(new Extent(5));
		lb_fact = new Label("Xưởng");
		sf_fact	= new SelectField();
		sf_fact.setWidth(new Extent(170));
		
		
		lb_group = new Label("Nhóm ");
		sf_group = new SelectField();
		sf_group.setWidth(new Extent(170));

		lb_dept = new Label("Đơn vị");
		cb_dept	= new ComboBox();
		cb_dept.setWidth(new Extent(167));
		cb_dept.setListRowCount(5);
		cb_dept.setBackground(Color.WHITE);
		
		lb_empsn = new Label("Số thẻ");
		txt_empsn = new TextField();
		txt_empsn.setWidth(new Extent(164));
		
		
		lb_date		= new Label("Ngày");
		df_date		= new DscDateField();
		df_date.setDateFormat(_sf);
		df_date.getDateChooser().setLocale(Locale.ENGLISH);
		df_date.setWidth(new Extent(167));
		tf_date	= new DscField();
		tf_date.setText("-- Chọn ngày --");
		df_date.setTextField(tf_date);
		
		col1.add(lb_fact);				col2.add(sf_fact);
		col1.add(lb_group);				col2.add(sf_group);
		col1.add(lb_dept);				col2.add(cb_dept);
		col1.add(lb_empsn);				col2.add(txt_empsn);
		col1.add(lb_date);				col2.add(df_date);
		
		//---------actionEvent----------
		
		sf_fact.setModel(obj_util.Get_Model_Fact());
		sf_fact.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				if(sf_fact.getSelectedIndex() != 0){
					txt_empsn.setText("");
					txt_empsn.setEnabled(false);
				}
				
				if(sf_fact.getSelectedIndex() == 0){
					cb_dept.setListModel(obj_util.Get_Model_Dept());
					cb_dept.setText("");
					sf_group.setModel(obj_util.Get_Model_Group(""));
					sf_group.setSelectedIndex(0);
					txt_empsn.setEnabled(true);
				}else
				if(sf_fact.getSelectedItem() != null && sf_fact.getSelectedIndex() !=0){
					
					sf_group.setModel(obj_util.Get_Model_Group(sf_fact.getSelectedItem().toString()));
					sf_group.setSelectedIndex(0);
					DefaultListModel model_null = new DefaultListModel();
					cb_dept.setListModel(model_null);
					
				}
				
			}
		});
		
		sf_group.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				if(sf_group.getSelectedIndex() == 0){
					DefaultListModel model_null = new DefaultListModel();
					cb_dept.setListModel(model_null);
				}else
				if(sf_group.getSelectedItem() != null){
					
					String n_fact	= sf_fact.getSelectedItem()	== null ? "" : sf_fact.getSelectedItem().toString();
					String n_group 	= sf_group.getSelectedItem() == null ? "" : sf_group.getSelectedItem().toString();
					cb_dept.setListModel(obj_util.Get_Model_Dept(n_fact, n_group));
					
				}
			}  
		});
		
		cb_dept.setListModel(obj_util.Get_Model_Dept());
		
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