package fv.util;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import dsc.dao.AbstractQuery;
import dsc.dao.IGenericDAO;
//FIXME fix fix fix
public class JdbcDataObjectSetStatement extends AbstractQuery{
	
	private GenericJdbcDAO dao;
	private Class supportsClass;

	public JdbcDataObjectSetStatement(IGenericDAO dao,Class supportsClass) {
		this.dao = (GenericJdbcDAO) dao;
		this.supportsClass=supportsClass;
	}
	
	@Override
	public String getSelectClause() {
		String sql = super.getSelectClause().toUpperCase();
		if (sql.contains("SELECT"))
			return sql;
		else return "SELECT";
	}
	
	@Override
	public String getFromCaluse() {
		return "FROM " + dao.getTable();
	}	
	
	@Override
	public String[] getProjection() {
		String[] props = super.getProjection();
		Object[] pks = CustomDomainUtils.getPKColumns(supportsClass);
		for (Object pk:pks){
			if (!ArrayUtils.contains(props, pk)){
				props = (String[]) ArrayUtils.add(props, pk);
			}
		}
		return props;
	}
	
	//include pk field
	@Override
	public String toQueryLanguage() {
		StringBuilder sb = new StringBuilder(getSelectClause());		
		if (this.getProjection()!=null&&this.getProjection().length!=0){
			for (String col:getProjection()){
				sb.append(col).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}else{
			Map<String, String> map = CustomDomainUtils.info(dao.getSupportsClass());
			for (String col:map.keySet()){
				sb.append(map.get(col)).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(" ").append(getFromCaluse());
		if (getWhereCaluse()!=null&&!getWhereCaluse().equals("")){
			sb.append(" ").append(getWhereCaluse());
		}
		if (getOrderbyCaluse()!=null&&!getOrderbyCaluse().equals("")){
			sb.append(" ").append(getOrderbyCaluse());
		}
		return sb.toString();
	}
	
	@Override
	public String toQueryLanguageWithAlias(String alias) {
		StringBuilder sb = new StringBuilder("SELECT ");
		if (this.getProjection()!=null&&this.getProjection().length!=0){
			for (String col:getProjection()){
				sb.append(alias).append(".").append(col).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}else{
			Map<String, String> map = CustomDomainUtils.info(dao.getSupportsClass());
			for (String col:map.keySet()){
				sb.append(alias).append(".").append(map.get(col)).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(" ").append(getFromCaluse()).append(" ").append(alias);
		if (getWhereCaluse()!=null&&!getWhereCaluse().equals("")){
			sb.append(" ").append(getWhereCaluse());
		}
		if (getOrderbyCaluse()!=null&&!getOrderbyCaluse().equals("")){
			sb.append(" ").append(getOrderbyCaluse());
		}
		return sb.toString();
	}
}
