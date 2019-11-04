package fv.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class SimpleBeanUtils {
	public static Class<?> getPropertyType(Class<?> clazz, String property)
			throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor propDescriptor : propDescriptors) {
			// String name of a property
			if (property.equals(propDescriptor.getName())) {
				// Class the getter corresponds to.
				return propDescriptor.getPropertyType();
			}
		}
		return Object.class;
	}
}
