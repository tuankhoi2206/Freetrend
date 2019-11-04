package fv.util;

/**
 * Generic logger for ACTION DAILY table
 * 
 * @author Hieu
 * 
 */
public interface Logger {
	public static final String ACT_INSERT = "INSERT";

	public static final String ACT_UPDATE = "UPDATE";

	public static final String ACT_DELETE = "DELETE";

	public static final String ACT_LOGIN = "LOGIN";

	public static final String ACT_IMPORT = "IMPORT";

	public static final String ACT_EXPORT = "EXPORT";

	public static final String ACT_REPORT = "REPORT";

	public void log(String action, String tableName, String pk, String note);
}
