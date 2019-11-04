package fv.util;

/**
 * VNI font to unicode font<br />
 * Unicode to VNI
 * 
 * @author sourceforge.net, Hieu
 * 
 */
public class Vni2Uni {
	private final static String[] VNI_char = { "O\u00C2", "o\u00E2", "y\u00F5",
			"Y\u00D5", "y\u00FB", "Y\u00DB", "y\u00F8", "Y\u00D8",
			"\u00F6\u00EF", "\u00D6\u00CF", "\u00F6\u00F5", "\u00D6\u00D5",
			"\u00F6\u00FB", "\u00D6\u00DB", "\u00F6\u00F8", "\u00D6\u00D8",
			"\u00F6\u00F9", "\u00D6\u00D9", "u\u00FB", "U\u00DB", "u\u00EF",
			"U\u00CF", "\u00F4\u00EF", "\u00D4\u00CF", "\u00F4\u00F5",
			"\u00D4\u00D5", "\u00F4\u00FB", "\u00D4\u00DB", "\u00F4\u00F8",
			"\u00D4\u00D8", "\u00F4\u00F9", "\u00D4\u00D9", "o\u00E4",
			"O\u00C4", "o\u00E3", "O\u00C3", "o\u00E5", "O\u00C5", "o\u00E0",
			"O\u00C0", "o\u00E1", "O\u00C1", "o\u00FB", "O\u00DB", "o\u00EF",
			"O\u00CF", "e\u00E4", "E\u00C4", "e\u00E3", "E\u00C3", "e\u00E5",
			"E\u00C5", "e\u00E0", "E\u00C0", "e\u00E1", "E\u00C1", "e\u00F5",
			"E\u00D5", "e\u00FB", "E\u00DB", "e\u00EF", "E\u00CF", "a\u00EB",
			"A\u00CB", "a\u00FC", "A\u00DC", "a\u00FA", "A\u00DA", "a\u00E8",
			"A\u00C8", "a\u00E9", "A\u00C9", "a\u00E4", "A\u00C4", "a\u00E3",
			"A\u00C3", "a\u00E5", "A\u00C5", "a\u00E0", "A\u00C0", "a\u00E1",
			"A\u00C1", "a\u00FB", "A\u00DB", "a\u00EF", "A\u00CF", "u\u00F5",
			"U\u00D5", "a\u00EA", "A\u00CA", "y\u00F9", "u\u00F9", "u\u00F8",
			"o\u00F5", "o\u00F9", "o\u00F8", "e\u00E2", "e\u00F9", "e\u00F8",
			"a\u00F5", "a\u00E2", "a\u00F9", "a\u00F8", "Y\u00D9", "U\u00D9",
			"U\u00D8", "O\u00D5", "O\u00D9", "O\u00D8", "E\u00C2", "E\u00D9",
			"E\u00D8", "A\u00D5", "A\u00C2", "A\u00D9", "A\u00D8" };

	private final static String[] Unicode_char = { "\u00C6", "\u00E6",
			"\u1EF9", "\u1EF8", "\u1EF7", "\u1EF6", "\u1EF3", "\u1EF2",
			"\u1EF1", "\u1EF0", "\u1EEF", "\u1EEE", "\u1EED", "\u1EEC",
			"\u1EEB", "\u1EEA", "\u1EE9", "\u1EE8", "\u1EE7", "\u1EE6",
			"\u1EE5", "\u1EE4", "\u1EE3", "\u1EE2", "\u1EE1", "\u1EE0",
			"\u1EDF", "\u1EDE", "\u1EDD", "\u1EDC", "\u1EDB", "\u1EDA",
			"\u1ED9", "\u1ED8", "\u1ED7", "\u1ED6", "\u1ED5", "\u1ED4",
			"\u1ED3", "\u1ED2", "\u1ED1", "\u1ED0", "\u1ECF", "\u1ECE",
			"\u1ECD", "\u1ECC", "\u1EC7", "\u1EC6", "\u1EC5", "\u1EC4",
			"\u1EC3", "\u1EC2", "\u1EC1", "\u1EC0", "\u1EBF", "\u1EBE",
			"\u1EBD", "\u1EBC", "\u1EBB", "\u1EBA", "\u1EB9", "\u1EB8",
			"\u1EB7", "\u1EB6", "\u1EB5", "\u1EB4", "\u1EB3", "\u1EB2",
			"\u1EB1", "\u1EB0", "\u1EAF", "\u1EAE", "\u1EAD", "\u1EAC",
			"\u1EAB", "\u1EAA", "\u1EA9", "\u1EA8", "\u1EA7", "\u1EA6",
			"\u1EA5", "\u1EA4", "\u1EA3", "\u1EA2", "\u1EA1", "\u1EA0",
			"\u0169", "\u0168", "\u0103", "\u0102", "\u00FD", "\u00FA",
			"\u00F9", "\u00F5", "\u00F3", "\u00F2", "\u00EA", "\u00E9",
			"\u00E8", "\u00E3", "\u00E2", "\u00E1", "\u00E0", "\u00DD",
			"\u00DA", "\u00D9", "\u00D5", "\u00D3", "\u00D2", "\u00CA",
			"\u00C9", "\u00C8", "\u00C3", "\u00C2", "\u00C1", "\u00C0" };

	private final static Character[] uni = { 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ị', 'ị', 'Ỉ',
			'ỉ', 'Ỵ', 'ỵ', 'Ô', 'ô', 'ỹ', 'Ỹ', 'ỷ', 'Ỷ', 'ỳ', 'Ỳ', 'ự', 'Ự',
			'ữ', 'Ữ', 'ử', 'Ử', 'ừ', 'Ừ', 'ứ', 'Ứ', 'ủ', 'Ủ', 'ụ', 'Ụ', 'ợ',
			'Ợ', 'ỡ', 'Ỡ', 'ở', 'Ở', 'ờ', 'Ờ', 'ớ', 'Ớ', 'ộ', 'Ộ', 'ỗ', 'Ỗ',
			'ổ', 'Ổ', 'ồ', 'Ồ', 'ố', 'Ố', 'ỏ', 'Ỏ', 'ọ', 'Ọ', 'ệ', 'Ệ', 'ễ',
			'Ễ', 'ể', 'Ể', 'ề', 'Ề', 'ế', 'Ế', 'ẽ', 'Ẽ', 'ẻ', 'Ẻ', 'ẹ', 'Ẹ',
			'ặ', 'Ặ', 'ẵ', 'Ẵ', 'ẳ', 'Ẳ', 'ằ', 'Ằ', 'ắ', 'Ắ', 'ậ', 'Ậ', 'ẫ',
			'Ẫ', 'ẩ', 'Ẩ', 'ầ', 'Ầ', 'ấ', 'Ấ', 'ả', 'Ả', 'ạ', 'Ạ', 'ũ', 'Ũ',
			'ă', 'Ă', 'ý', 'ú', 'ù', 'õ', 'ó', 'ò', 'ê', 'é', 'è', 'ã', 'â',
			'á', 'à', 'Ý', 'Ú', 'Ù', 'Õ', 'Ó', 'Ò', 'Ê', 'É', 'È', 'Ã', 'Â',
			'Á', 'À', 'Ơ', 'ơ', 'Ư', 'ư' };

	private final static String[] vni = { "Ñ", "ñ", "Ó", "ó", "Ò", "ò", "Æ",
			"æ", "Î", "î", "OÂ", "oâ", "yõ", "YÕ", "yû", "YÛ", "yø", "YØ",
			"öï", "ÖÏ", "öõ", "ÖÕ", "öû", "ÖÛ", "öø", "ÖØ", "öù", "ÖÙ", "uû",
			"UÛ", "uï", "UÏ", "ôï", "ÔÏ", "ôõ", "ÔÕ", "ôû", "ÔÛ", "ôø", "ÔØ",
			"ôù", "ÔÙ", "oä", "OÄ", "oã", "OÃ", "oå", "OÅ", "oà", "OÀ", "oá",
			"OÁ", "oû", "OÛ", "oï", "OÏ", "eä", "EÄ", "eã", "EÃ", "eå", "EÅ",
			"eà", "EÀ", "eá", "EÁ", "eõ", "EÕ", "eû", "EÛ", "eï", "EÏ", "aë",
			"AË", "aü", "AÜ", "aú", "AÚ", "aè", "AÈ", "aé", "AÉ", "aä", "AÄ",
			"aã", "AÃ", "aå", "AÅ", "aà", "AÀ", "aá", "AÁ", "aû", "AÛ", "aï",
			"AÏ", "uõ", "UÕ", "aê", "AÊ", "yù", "uù", "uø", "oõ", "où", "oø",
			"eâ", "eù", "eø", "aõ", "aâ", "aù", "aø", "YÙ", "UÙ", "UØ", "OÕ",
			"OÙ", "OØ", "EÂ", "EÙ", "EØ", "AÕ", "AÂ", "AÙ", "AØ", "Ô", "ô",
			"Ö", "ö" };

	private static String replaceString(String text, final String[] pattern,
			final String[] replace) {
		int startIndex;
		int foundIndex;
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < pattern.length; i++) {
			startIndex = 0;
			// Clear the buffer
			result.setLength(0);

			// Look for a pattern to replace
			while ((foundIndex = text.indexOf(pattern[i], startIndex)) >= 0) {
				result.append(text.substring(startIndex, foundIndex));
				result.append(replace[i]);
				startIndex = foundIndex + pattern[i].length();
			}
			result.append(text.substring(startIndex));
			text = result.toString();
		}
		return text;
	}

	public static String convertToUnicode(String vniStr) {
		if (vniStr == null)
			return null;
		vniStr = vniStr.replace('\u00D1', '\u0110').replace('\u00F1', '\u0111')
				.replace('\u00D3', '\u0128').replace('\u00F3', '\u0129')
				.replace('\u00D2', '\u1ECA').replace('\u00F2', '\u1ECB')
				.replace('\u00C6', '\u1EC8').replace('\u00E6', '\u1EC9')
				.replace('\u00CE', '\u1EF4').replace('\u00EE', '\u1EF5');
		vniStr = replaceString(vniStr, VNI_char, Unicode_char);
		vniStr = vniStr.replace('\u00D4', '\u01A0').replace('\u00F4', '\u01A1')
				.replace('\u00D6', '\u01AF').replace('\u00F6', '\u01B0')
				.replace('\u00C6', '\u00D4').replace('\u00E6', '\u00F4');
		return vniStr;
	}

	public static String convertToVNI(String unicodeStr) {
		if (unicodeStr == null)
			return null;
		;
		String before, after;
		for (int i = 0; i < unicodeStr.length(); i++) {
			if (unicodeStr.charAt(i) > 127) {
				for (int j = 0; j < uni.length; j++) {
					if (uni[j] == unicodeStr.charAt(i)) {
						before = unicodeStr.substring(0, i);
						after = unicodeStr.substring(i + 1);
						unicodeStr = before + vni[j] + after;
						if (vni[j].length() > 1)
							i++;
						break;
					}
				}
			}
		}
		return unicodeStr;
	}

	public String removeInvalidXMLCharacters(String s) {

		StringBuilder out = new StringBuilder(); // Used to hold the output.

		int codePoint; // Used to reference the current character.

		// String ss = "\ud801\udc00"; // This is actualy one unicode character,
		// represented by two code units!!!.

		// System.out.println(ss.codePointCount(0, ss.length()));// See: 1

		int i = 0;

		while (i < s.length()) {

			// System.out.println("i=" + i);

			codePoint = s.codePointAt(i); // This is the unicode code of the
											// character.

			if ((codePoint == 0x9) || // Consider testing larger ranges first
										// to improve speed.

					(codePoint == 0xA) ||

					(codePoint == 0xD) ||

					((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||

					((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||

					((codePoint >= 0x10000) && (codePoint <= 0x10FFFF))) {

				out.append(Character.toChars(codePoint));

			}

			i += Character.charCount(codePoint); // Increment with the number
													// of code units(java chars)
													// needed to represent a
													// Unicode char.

		}

		return out.toString();

	}
}
