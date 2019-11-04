package fv.util;

import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;

public class JDataObjectSet extends DataObjectSet {
	

	public JDataObjectSet(IGenericDAO dao, Class value) {
		super(dao, value);
		setQuery(new JdbcDataObjectSetStatement(dao, value));
	}
}
