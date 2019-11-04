package fv.util;

import java.util.Comparator;

public class VniComparator implements Comparator<String>{
	@Override
	public int compare(String o1, String o2) {
		return VniSorter.compare(o1, o2);
	}
}
