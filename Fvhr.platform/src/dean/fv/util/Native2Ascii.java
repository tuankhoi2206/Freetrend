package fv.util;

public class Native2Ascii {

	private static String int2HexString(int code) {
		String hexString = Integer.toHexString(code);
		if (hexString.length() == 1)
			hexString = "0" + hexString;
		return hexString;
	}

	private static StringBuffer native2Ascii(char charater) {
		StringBuffer sb = new StringBuffer();

		if (charater > 127) {
			sb.append("\\u");
			int lowByte = (charater >>> 8);
			sb.append(int2HexString(lowByte));
			int highByte = (charater & 0xFF);
			sb.append(int2HexString(highByte));
		} else {
			sb.append(charater);
		}
		return sb;
	}

	public static String convert(String str) {
		StringBuffer sb = new StringBuffer(str.length());
		sb.setLength(0);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			sb.append(native2Ascii(c));
		}
		return (new String(sb));
	}
}
