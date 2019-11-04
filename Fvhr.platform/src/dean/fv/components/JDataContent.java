package fv.components;

import dsc.echo2app.program.DataContent;
import fv.util.GenericJdbcDAO;
import fv.util.JDataObjectSet;
/**
 * Dynamic DataContent
 * @author Hieu
 *
 */
public class JDataContent extends DataContent {
	private static final long serialVersionUID = 1L;
	private String tableName;

	@Override
	protected void initDataObjectSet() {
		dataObjectSet = new JDataObjectSet(new GenericJdbcDAO(tableName, getDataObjectClass()), getDataObjectClass());
		dataObjectSet.setRightInfo(super.mainProgram.getRightInfo());
	}
	
	public void setVniColumns(String[] vniColumns) {
		GenericJdbcDAO dao = (GenericJdbcDAO) getDataObjectSet().getDao();
		dao.setVniColumns(vniColumns);
	}
	
	public String[] getVniColumns() {
		GenericJdbcDAO dao = (GenericJdbcDAO) getDataObjectSet().getDao();
		return dao.getVniColumns();
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;		
	}
}
