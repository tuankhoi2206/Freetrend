package fv.components;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import fv.util.VniSorter;

/**
 * Object, number, Vietnamese string comparator
 * 
 * @author Hieu
 * 
 */
public class BeanTableComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null)
			return 0;
		else if (o1 == null)
			return 1;
		else if (o2 == null)
			return -1;
		if (o1 instanceof Integer && o2 instanceof Integer) {
			return ((Integer) o1).compareTo((Integer) o2);
		} else if (o1 instanceof Long && o2 instanceof Long) {
			return ((Long) o1).compareTo((Long) o2);
		} else if (o1 instanceof Float && o2 instanceof Float) {
			return ((Float) o1).compareTo((Float) o2);
		} else if (o1 instanceof Double && o2 instanceof Double) {
			return ((Double) o1).compareTo((Double) o2);
		} else if (o1 instanceof Short && o2 instanceof Short) {
			return ((Short) o1).compareTo((Short) o2);
		} else if (o1 instanceof Date && o2 instanceof Date) {
			return ((Date) o1).compareTo((Date) o2);
		} else if (o1 instanceof BigDecimal && o2 instanceof BigDecimal) {
			return ((BigDecimal) o1).compareTo((BigDecimal) o2);
		}
		return VniSorter.compare(((String) o1), ((String) o2));
	}
}
