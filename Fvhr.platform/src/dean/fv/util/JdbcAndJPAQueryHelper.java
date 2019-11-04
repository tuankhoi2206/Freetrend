package fv.util;

import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class JdbcAndJPAQueryHelper {
	public static String jpa2sql(String sql, Class<?> supportsClass, String tableName){
		String prs = StringUtils.substringBetween(sql, "SELECT ", " FROM");
		if (prs!=null&&!prs.equals("")){
			String[] projection = prs.split(",\\s*");		
			Map<String, String> info = CustomDomainUtils.info(supportsClass);
			ArrayList<String> list = new ArrayList<String>();
			for (String str:projection){
				String f = StringUtils.substringAfter(str, ".");
				String col = info.get(f);
				String alias = StringUtils.substringBefore(str, ".");
				if (!StringUtils.isBlank(col)){
					list.add(alias+"."+col+" AS " + f);
				}
			}
			return "SELECT " + StringUtils.substringBetween(list.toString(), "[", "]") + " FROM" + StringUtils.substringAfter(sql, "FROM");
		}else{
			Map<String, String> info = CustomDomainUtils.info(supportsClass);
			StringBuilder sb = new StringBuilder();
			for (String key:info.keySet()){
				sb.append(info.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return "SELECT " + sb.toString() + " FROM " + tableName;
		}
	}
}
