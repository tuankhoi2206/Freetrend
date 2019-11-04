package fv.util;

import java.io.Serializable;

import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

public class DomainClassValidator<T, PK extends Serializable> {
	private static DomainClassValidator INSTANSE;

	public static DomainClassValidator getValidator() {
		if (INSTANSE == null)
			INSTANSE = new DomainClassValidator();
		return INSTANSE;
	}

	@SuppressWarnings("unchecked")
	public boolean checkExist(T entity, PK primaryKey) {
		IGenericDAO<T, PK> dao = Application.getApp().getDao(entity.getClass());
		if (dao.findById(primaryKey) != null) {
			return true;
		}
		return false;
	}
}
