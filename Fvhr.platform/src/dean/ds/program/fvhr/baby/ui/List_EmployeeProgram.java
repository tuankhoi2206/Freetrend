package ds.program.fvhr.baby.ui;

import java.beans.PropertyEditorSupport;

import javax.swing.text.TabExpander;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.baby.ui.Search_EmployeeProgram;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.dao.IQuery;
import dsc.echo2app.Application;
import dsc.echo2app.component.binder.UIDataObjectBinder;
import dsc.echo2app.program.BrowserContent;
import fv.util.VniEditor;

@SuppressWarnings("unused")
public class List_EmployeeProgram extends ContentPane {

	private static final long serialVersionUID = 1L;
	private IGenericDAO<N_EMPLOYEE, String> dao_Employee;
	private   DataObjectSet dataSourceTable;
	private  BrowserContent table;
	private  IQuery query;
	private ActionListener SelectedChangeItem;
	private Button btn_Finder ;

	public ActionListener getSelectedChangeItem() {
		return SelectedChangeItem;
	}
	
	public void setSelectedChangeItem(ActionListener selectedChangeItem) {
		SelectedChangeItem = selectedChangeItem;
	}
	
	public IGenericDAO<N_EMPLOYEE, String> getDao_Employee() {
		return dao_Employee;
	}

	public void setDao_Employee(IGenericDAO<N_EMPLOYEE, String> dao_Employee) {
		this.dao_Employee = dao_Employee;
	}

	public DataObjectSet getDataSourceTable() {
		return dataSourceTable;
	}

	public void setDataSourceTable(DataObjectSet dataSourceTable) {
		this.dataSourceTable = dataSourceTable;
	}

	public BrowserContent getTable() {
		return table;
	}

	public void setTable(BrowserContent table) {
		this.table = table;
	}

	public IQuery getQuery() {
		return query;
	}

	public void setQuery(IQuery query) {
		this.query = query;
	}

	public List_EmployeeProgram() {
		super();
	}

	
	protected int doInit() {
		int ret = -1;
		dao_Employee = Application.getApp().getDao(N_EMPLOYEE.class); // lấy DAO
																		// class
																		// N_EMPLOYEE
		dataSourceTable = new DataObjectSet(dao_Employee, N_EMPLOYEE.class); // Set
																				// kiểu
																				// DataSourceTable
																				// (DataObjectSet)
																				// là
																				// N_EMPLOYEE
		table = new BrowserContent();
		
		// tham khảo MaintainSProgram.class

		/*
		 * _____________________________________________________________ | Trong
		 * 1 browserContent bao gồm 1 table và 1 Nagivator Bar | | |
		 * |_____________________________________________________________|
		 */

		table.init(dataSourceTable, DisplayColumnsTable()); // khởi tạo mặc đinh
															// cho Table
															// (BrowserContent)
		// khởi tạo câu truy vấn
		query = table.getDataObjectSet().getQuery();
		
		query.setSelectClause("select EMPSN,EMPCN,FNAME,LNAME");
		table.getDataObjectSet().setQuery(query); // đưa câu truy vấn vào
													// dataObjectSet
		
	
		table.getBrowserNav().setSearchButtonVisible(false);
		table.setPageSize(20);
		setBtn_Finder(new Button("Finder"));
		getBtn_Finder().setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnFind.png"));
		getBtn_Finder().addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Search_EmployeeProgram frm_SearchEmployee = new Search_EmployeeProgram();
				getApplicationInstance().getDefaultWindow().getContent()
						.add(frm_SearchEmployee);
			}
		});
		table.getBrowserNav().add(getBtn_Finder());
		this.add(table);
		registPropertyEditor();
		ret = 0;
		return ret; // 0 = RET_OK
	}

	@SuppressWarnings("unused")
	private void registPropertyEditor() // dùng để bind các object vào component
										// (combobox, selectedfield,
										// browserContent...)
	{
		UIDataObjectBinder binder = dataSourceTable.getUIDataObjectBinder();
		binder.registerCustomEditor(N_EMPLOYEE.class, "FNAME",new VniEditor());
		binder.registerCustomEditor(N_EMPLOYEE.class,"LNAME",new VniEditor());
	}

	protected String[] DisplayColumnsTable() // Qui đinh những cột nào được hiển
												// thị lên Table
												// (BrowserContent)
	{
		return new String[] { "EMPSN", "EMPCN", "FNAME", "LNAME" };
	}
	public void refresh()
	{
		
		table.refresh();
		if(dataSourceTable.getRecordCount()>0)
		{
			table.setCurrentSelectRowNo(0);
		}
	}

	public void setBtn_Finder(Button btn_Finder) {
		this.btn_Finder = btn_Finder;
	}

	public Button getBtn_Finder() {
		return btn_Finder;
	}
	
}
