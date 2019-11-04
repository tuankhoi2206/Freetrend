package ds.program.fvhr;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ds.program.fvhr.domain.ATTENDANTDB;
import dsc.dao.IGenericDAO;
import dsc.dao.MultiSessionFactory;
import fv.util.HSSFUtils;

public class ATM {
	public static void main(String[] args) {
		IGenericDAO<ATTENDANTDB, String> dao = getDaoByDSName("dataSource_VFT");
		dao.setSupportsClass(ATTENDANTDB.class);		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(ATM.class.getResourceAsStream("ATM_POSSN.xls"));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				row = sheet.getRow(i);
				if (row==null) continue;
				
				cell = row.getCell(0);
				String emp = HSSFUtils.getStringCellValue(cell, true).trim();
				ATTENDANTDB data = dao.findById(emp);
				if (data!=null){
					cell = row.getCell(3);
					String possn = HSSFUtils.getStringCellValue(cell, true).trim();
					data.setPOSSN(possn);
					dao.update(data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static synchronized IGenericDAO getDaoByDSName(String dataSourceName) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/conf/app*.xml");
		assert context != null : "spring context is null";
		assert StringUtils.isNotBlank(dataSourceName) : "data source name is null";
		//20081104 modified by garywu, issue #0005901
		//HotSwappableTargetSource hsts = (HotSwappableTargetSource) context.getBean("swappingDataSourceTargetSource");
		//Object dataSourceLocal = context.getBean(dataSourceName);
		//hsts.swap(dataSourceLocal);
		
		//20081104 modified by garywu, issue #0005901
		MultiSessionFactory multiSessionFactory = (MultiSessionFactory)context.getBean("&sessionFactory");
        multiSessionFactory.setDataSource((DataSource)context.getBean(dataSourceName));
		IGenericDAO result = (IGenericDAO) context.getBean("dao");
		return result;
	}
}
