package fv.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.persistence.IdClass;

import org.apache.commons.beanutils.PropertyUtils;

import dsc.echo2app.Application;
import dsc.echo2app.program.Config;

/**
 * Resource bundle fast access. DSC Web context only.
 * 
 * @author Hieu
 * @since 16/04/2011
 */
public class BundleUtils {
	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption",
				Application.getApp().getLocale());
	}

	private static ResourceBundle getBundle() {
		if (!bundle.getLocale().equals(Application.getApp().getLocale())) {
			bundle = ResourceBundle.getBundle(
					"resource.localization.UICaption", Application.getApp()
							.getLocale());
		}
		return bundle;
	}

	public static String getString(String key) {
		String ret = null;
		try {
			ret = getBundle().getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			ret = key;
		}
		return ret;
	}

	public static String getCaption(Class<?> domainClass, String fieldName)
			throws NoSuchFieldException {
		IdClass idClazz = domainClass.getAnnotation(IdClass.class);
		if (idClazz != null) {
			Class<?> clazz = idClazz.value();
			PropertyDescriptor[] pd = PropertyUtils
					.getPropertyDescriptors(clazz);
			for (PropertyDescriptor p : pd) {
				Method m = p.getReadMethod();
				Config config = m.getAnnotation(Config.class);
				if (config != null) {
					if (config.key().equals(
							domainClass.getSimpleName() + "." + fieldName))
						return getString(config.key());
				}
			}
		}
		PropertyDescriptor[] pd = PropertyUtils
				.getPropertyDescriptors(domainClass);
		for (PropertyDescriptor p : pd) {
			Method m = p.getReadMethod();
			Config config = m.getAnnotation(Config.class);
			if (config != null) {
				if (config.key().equals(
						domainClass.getSimpleName() + "." + fieldName))
					return getString(config.key());
			}
		}
		throw new NoSuchFieldException();
	}

	public static String getDateFormatPattern() {
		return getString("LANG_DATEFORMAT");
	}

	public static SimpleDateFormat getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormatPattern());
		return sdf;
	}
}
