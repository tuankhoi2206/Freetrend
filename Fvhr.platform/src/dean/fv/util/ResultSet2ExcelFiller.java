package fv.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ResultSet2ExcelFiller {
	
	public static final int RS_COLUMN_NAME=0;
	public static final int RS_COLUMN_INDEX=1;
	
	//	fieldName-excelIndex
	protected Map<Object, Integer> excelMap = new HashMap<Object, Integer>();
	protected Map<Integer, Object> customObject = new HashMap<Integer, Object>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private int type;
	
	public ResultSet2ExcelFiller(){
		
	}
	
	public ResultSet2ExcelFiller(int type){
		this.type=type;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void fill(ResultSet rs, HSSFRow row, boolean isRsNull) {
		ResultSetMetaData meta;
		try {
			meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			HSSFCell cell;
			for (int i=1;i<=columnCount;i++){
//				String columnName = meta.getColumnName(i);
//				Integer index = excelMap.get(columnName);
				Integer index;
				if (type==RS_COLUMN_NAME){
					index = excelMap.get(meta.getColumnName(i));
				}else{
					index = excelMap.get(i);
				}
				if (index==null||index==-1) continue;
				
				int type = meta.getColumnType(i);
				if (type==Types.VARCHAR||type==Types.CHAR||type==Types.NCHAR||type==Types.NVARCHAR){
					if (isRsNull){
						cell = row.createCell(index);
						cell.setCellValue("");
						continue;
					}
					String str = rs.getString(i);
					if (str!=null){
						cell = row.createCell(index);
						cell.setCellValue(str);
					}
				}else if (type==Types.DATE||type==Types.TIMESTAMP){
					if (isRsNull){
						cell = row.createCell(index);
						cell.setCellValue("");
						continue;
					}
					Timestamp t = rs.getTimestamp(i);
					if (t!=null){
						Date date = new Date(t.getTime());
						cell = row.createCell(index);
						cell.setCellValue(sdf.format(date));
					}
				}else if (type==Types.NUMERIC){
					if (isRsNull){
						cell = row.createCell(index);
						cell.setCellValue(0d);
						continue;
					}
					BigDecimal val = rs.getBigDecimal(i);
					if (val!=null){
						cell = row.createCell(index);
						cell.setCellValue(val.doubleValue());
					}
				}
			}
			if (customObject.size()>0){
				for (Integer index:customObject.keySet()){
					cell = row.createCell(index);
					Object o = customObject.get(index);
					if (o instanceof String){
						cell.setCellValue(String.valueOf(o));
					}else if (o instanceof Number){						
						cell.setCellValue(Double.valueOf(String.valueOf(o)));
					}else if (o instanceof java.util.Date){
						cell.setCellValue((Date)o);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Object, Integer> getExcelMap() {
		return excelMap;
	}
	
	public Map<Integer, Object> getCustomObject(){
		return customObject;
	}
}
