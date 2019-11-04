package ds.program.fvhr.ui.training;

import java.util.ArrayList;
import java.util.List;

import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.component.DscPageNavigator;
import dsc.echo2app.component.binder.UIDataObjectBinder;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.util.VniEditor;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionListener;

public class TrainingBrowserContent extends ContentPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BrowserContent browserContent;
	private IGenericDAO<N_EMPLOYEE, String> dao;
	private DataObjectSet dataObjectSet;
	private ProgramCondition baseCondition;
	private ProgramCondition queryCondition;
	private ActionListener searchFoundListener;
	public TrainingBrowserContent(){
		super();
	}
	
	protected int doInit(){
		int ret = -1;
		dao = Application.getApp().getDao(N_EMPLOYEE.class);
		dataObjectSet = new DataObjectSet(dao, N_EMPLOYEE.class);
		browserContent = new BrowserContent();
		browserContent.init(dataObjectSet, new String[]{"EMPSN", "FULL_NAME"});
		//override default
		browserContent.getDataObjectSet().getQuery().setSelectClause("select EMPSN,FNAME,LNAME,DEPSN,SHIFT");		
		DscPageNavigator nav = browserContent.getBrowserNav();
		nav.setSearchButtonVisible(false);
		nav.getSearchTextField().setWidth(new Extent(80));		
		add(browserContent);
		registerPropertiesEditor();
		ret = 0;
		return ret;
	}

	private void registerPropertiesEditor() {
		UIDataObjectBinder binder = dataObjectSet.getUIDataObjectBinder();
		binder.registerCustomEditor(N_EMPLOYEE.class, "FULL_NAME", new VniEditor());
	}

	public BrowserContent getBrowserContent() {
		return browserContent;
	}

	public void setBrowserContent(BrowserContent browserContent) {
		this.browserContent = browserContent;
	}

	public IGenericDAO<N_EMPLOYEE, String> getDao() {
		return dao;
	}

	public void setDao(IGenericDAO<N_EMPLOYEE, String> dao) {
		this.dao = dao;
	}

	public DataObjectSet getDataObjectSet() {
		return dataObjectSet;
	}

	public void setDataObjectSet(DataObjectSet dataObjectSet) {
		this.dataObjectSet = dataObjectSet;
	}

	public ProgramCondition getBaseCondition() {
		return baseCondition;
	}

	public void setBaseCondition(ProgramCondition baseCondition) {
		this.baseCondition = baseCondition;
	}

	public ProgramCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(ProgramCondition queryCondition) {
		this.queryCondition = queryCondition;
	}	
	
	public void refresh(){
		String sql="";
		List params = new ArrayList();
		if (baseCondition!=null){
			sql = baseCondition.condition;
			for (Object obj: baseCondition.parameters){
				params.add(obj);
			}
		}
		if (queryCondition!=null){
			if ((sql.length() > 0) && (getQueryCondition().condition.length() > 0)) {
				sql += " and ";
			}
			if (getQueryCondition().condition.length() > 0) {
				sql += "(" + getQueryCondition().condition + ")";
			}
			for (Object obj:queryCondition.parameters){
				params.add(obj);
			}
		}
		browserContent.getDataObjectSet().query(sql, params.toArray());
		browserContent.refresh();
		if (dataObjectSet.getRecordCount()>0){
			browserContent.setCurrentSelectRowNo(0);
		}
	}

	public ActionListener getSearchFoundListener() {
		return searchFoundListener;
	}

	public void setSearchFoundListener(ActionListener searchFoundListener) {
		this.searchFoundListener = searchFoundListener;
		DscPageNavigator nav = browserContent.getBrowserNav();
		nav.setSearchFoundAction(searchFoundListener);
	}
}
