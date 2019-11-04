package fv.util;

public class VniSorter {
	private static String vnCharaters = "AaÁáÀàẢảÃãẠạĂăẮắẰằẲẳẴẵẶặÂâẤấẦầẨảẪãẬậBbCcDdĐđEeÉéÈèẺẻẼẽẸẹÊêẾếỀềỂểỄễỆệFfGgHhIiÍíÌìỈỉĨĩỊịJjKkLlMmNnOoÓóÒòỎỏÕõỌọƠơỚớỜờỞởỠỡỢợÔôỐốỒồỔổỖỗỘộPpQqRrSsTtUuÚúÙùỦủŨũỤụƯưỨứỪừỬửỮữỰựVvWwXxYyÝýỲỳỶỷỸỹỴỵZz";

	/**
	 * Copy from String.class
	 * 
	 * @param str
	 * @param strToCompare
	 * @return
	 */
	public static int compare(String str, String strToCompare) {
		int len1 = str.length();
		int len2 = strToCompare.length();
		int n = Math.min(len1, len2);
		char v1[] = str.toCharArray();
		char v2[] = strToCompare.toCharArray();
		int i = 0;
		int j = 0;

		if (i == j) {
			int k = i;
			int lim = n + i;
			while (k < lim) {
				char c1 = v1[k];
				char c2 = v2[k];
				if (c1 != c2) {
					if (vnCharaters.indexOf(c1) >= 0
							&& vnCharaters.indexOf(c2) >= 0) {
						return vnCharaters.indexOf(c1)
								- vnCharaters.indexOf(c2);
					}
					return c1 - c2;
				}
				k++;
			}
		} else {
			while (n-- != 0) {
				char c1 = v1[i++];
				char c2 = v2[j++];
				if (c1 != c2) {
					if (vnCharaters.indexOf(c1) >= 0
							&& vnCharaters.indexOf(c2) >= 0) {
						return vnCharaters.indexOf(c1)
								- vnCharaters.indexOf(c2);
					}
					return c1 - c2;
				}
			}
		}
		return len1 - len2;
	}
}
