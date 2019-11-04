package ds.program.fvhr.ui.salary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ds.program.fvhr.dao.generic.FvGenericDAO;
import ds.program.fvhr.domain.ATT200000;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.RadioButton;
import fv.components.FileUploadSelect;
import fv.components.FileWrapper;
import fv.components.SelectItem;
import fv.components.SimpleReportProgram;
import fv.util.ExcelBinder;
import fv.util.FVGenericInfo;
import fv.util.HSSFUtils;
import fv.util.JdbcDAO;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import fv.util.ResultSet2ExcelFiller;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Extent;
import echopointng.Separator;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.Alignment;
import echopointng.GroupBox;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscGroupCheckBox;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;

public class SalaryReport extends SimpleReportProgram {

	private static final String CFG_PATH="C:/FV/";

	private ResourceBundle resourceBundle;
	private SelectField sfMonth;
	private SelectField sfYear;
	private SelectField sfFact;
	private RadioButton radAuto;
	private RadioButton radExcel;
	private RadioButton radBangTong;
	private RadioButton radBangTongCB;
	private RadioButton radBangTongCN;	
	private SelectField sfFile;
	private ArrayList<Object[]> listTsalInfo;
	private String bindTypeId="";
	private FileUploadSelect fileUploadSelect;

	private Grid rootLayout;

	private DscGroupCheckBox groupFVL;

	private Row row2;

	private JdbcDAO dao;

	private RadioButton radBangTongTTGT;

	private RadioButton radBangTongATM;

	/**
	 * Creates a new <code>SalaryReport</code>.
	 */
	public SalaryReport() {
		super();

		// Add design-time configured components.
		initComponents();
		initUI();
	}
	
	public JdbcDAO getDao(){
		if (dao==null) dao = new JdbcDAO();
		return dao;
	}
	
	
	protected int doInit() {
		int ret = super.doInit();
		getToolbar().setConfigButtonEnable(false);
		getToolbar().setRefreshButtonEnable(false);
		getToolbar().setSearchButtonEnable(false);
		getToolbar().setPdfButtonEnable(false);		
		return ret;
	}

	private void initUI(){
		Calendar cal = Calendar.getInstance();		
		ListBinder.bindSelectField(sfMonth, MappingPropertyUtils.getJavaMonthEditor(), true);
		ListBinder.bindSelectField(sfYear, MappingPropertyUtils.getYearEditor(1), true);
		ListBinder.bindSelectField(sfFact, FVGenericInfo.getFactories(), true);

		ListBinder.refreshIndex(sfMonth, cal.get(Calendar.MONTH));
		ListBinder.refreshIndex(sfYear, cal.get(Calendar.YEAR));

		fileUploadSelect.setUploadFinishedListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				saveAndUpdateModel();
			}
		});
//
		loadXLSInfo();

	}

	protected void saveAndUpdateModel() {
		if (bindTypeId.equals("")) return;
		FileWrapper fw = fileUploadSelect.getFileWrapper();
		String uid = Application.getApp().getLoginInfo().getUserID();
		if (fw!=null){
			String bp = CFG_PATH + uid + "/" + bindTypeId;
			String fn = fw.getName();
			File f = new File(bp+"/"+fn);
			int m=0;
			while (f.exists()){
				m++;
				fn = fw.getName();
				fn=StringUtils.substringBeforeLast(fn, ".")+"("+m+")." + StringUtils.substringAfterLast(fn, ".");
				f = new File(bp+"/"+fn); 
			}
//			System.out.println(fw.getFile().renameTo(f));			
			File file = new File(CFG_PATH+"bcthcfg.xls");
			if (!file.exists()) return;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				File dir = new File(bp);
				if (!dir.exists()) {
					if(!dir.mkdir()){
						dir.mkdirs();
					}
				}
				fw.getFile().renameTo(f);
				fis = new FileInputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook(fis);
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				for (int i=0;i<listTsalInfo.size();i++){
					String str = (String) listTsalInfo.get(i)[0];
					String p = StringUtils.substringBeforeLast(str, "/");
					String tsalId = StringUtils.substringAfterLast(p, "/");
					if (tsalId.equals(bindTypeId)){
						int rowindex = (Integer) listTsalInfo.get(i)[2];
						row = sheet.getRow(rowindex);
						cell = row.getCell(2);
						if (cell!=null) cell.setCellValue(Boolean.FALSE);
					}
				}
				Object[] ts = new Object[3];
				ts[0]=bp+"/"+fn;
				ts[1]=Boolean.TRUE;
				ts[2]=sheet.getPhysicalNumberOfRows();
				listTsalInfo.add(ts);
				DefaultListModel model = (DefaultListModel) sfFile.getModel();
				SelectItem item = new SelectItem();
				item.setText(fn);
				item.setValue(ts[0]);
				model.add(item);
				sfFile.setSelectedIndex(model.size()-1);
				row = sheet.createRow(sheet.getPhysicalNumberOfRows());
				cell=row.createCell(0);
				cell.setCellValue(uid);
				cell = row.createCell(1);
				cell.setCellValue(bp+"/"+fn);
				cell = row.createCell(2);
				cell.setCellValue(Boolean.TRUE);

				fos = new FileOutputStream(new File(CFG_PATH+"bcthcfg.xls"));
				wb.write(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis!=null)
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (fos!=null)
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fileUploadSelect.doSelectNextFile(null);
						fileUploadSelect.refresh();
			}
		}
	}

	private void loadXLSInfo(){
		File file = new File(CFG_PATH+"bcthcfg.xls");
		if (!file.exists()) {
			File lf = ReportFileManager.getReportFormatFolder("fvhr/bcthcfg.xls");
			if (!lf.renameTo(file)){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không tìm thấy file cấu hình");
				rootLayout.setVisible(false);		
				return;
			}
		}
		String uid = Application.getApp().getLoginInfo().getUserID();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			listTsalInfo = new ArrayList<Object[]>();
			for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
				row = sheet.getRow(i);
				cell = row.getCell(0);
				String id=cell.getStringCellValue();
				if (uid.equals(id)){
					Object[] item = new Object[3];
					cell = row.getCell(1);//path
					item[0]=cell.getStringCellValue();
					cell = row.getCell(2);//default
					if (cell!=null){
						item[1]=cell.getBooleanCellValue();
					}
					item[2]=i;
					listTsalInfo.add(item);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private HSSFWorkbook loadWorkbook(){
		File file = null;
		FileInputStream fis = null;
		if (radAuto.isSelected()){
			if (radBangTong.isSelected()){
				file = ReportFileManager.getReportFormatFolder("fvhr/TSAL02.XLS");
			}else if (radBangTongCB.isSelected()){
				file = ReportFileManager.getReportFormatFolder("fvhr/TSAL03.XLS");
			}else if (radBangTongCN.isSelected()){
				file = ReportFileManager.getReportFormatFolder("fvhr/TSAL01.XLS");
			}else if (radBangTongATM.isSelected()){
				file = ReportFileManager.getReportFormatFolder("fvhr/TSAL04.XLS");
			}else if (radBangTongTTGT.isSelected()){
				file = ReportFileManager.getReportFormatFolder("fvhr/TSAL05.XLS");				
			}
		}else{
			SelectItem item = (SelectItem) sfFile.getSelectedItem();			
			file = new File(item.getValue().toString());
			if (!file.exists()) return null;
		}

		try {
			fis = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			return wb;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}


	/*private void loadXMLInfo(){
		File file = ReportFileManager.getReportFormatFolder("fvhr/bcth.xml");
		if (!file.exists()) return;
		String uid = Application.getApp().getLoginInfo().getUserID();		
		try {
			Document doc = XmlUtil.openXmlFile(file.getAbsolutePath(), false);
			NodeList list = doc.getElementsByTagName("user");
			for (int i=0;i<list.getLength();i++){
				Element e = (Element) list.item(i);
				String id = e.getAttribute("id");
				if (id!=null&&uid.equals(id)){
					DefaultListModel model = new DefaultListModel();
					int sfindex=-1;
					NodeList tsal = e.getElementsByTagName("tsal");
					for (int j=0;j<tsal.getLength();j++){
						Element e1 = (Element) tsal.item(j);
						String tsalId = e1.getAttribute("id");
						if (tsalId!=null&&tsalId.equals("02")){
							NodeList files = e1.getElementsByTagName("file");
							for (int k=0;k<files.getLength();k++){
								Element e3 = (Element) files.item(k);
								String def = e3.getAttribute("default");
								if (def!=null&&def.equals("true")){
									sfindex=k;
								}
								String path = e3.getTextContent();
								SelectItem item = new SelectItem();
								item.setText(StringUtils.substringAfterLast(path, "/"));
								item.setValue(path);
								model.add(item);			
							}
							break;
						}
					}
					sfFile.setModel(model);
					sfFile.setSelectedIndex(sfindex);
					break;//stop first child found
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


	
	protected void doRefresh() {
	}

	
	protected void doSearch() {
	}

	
	protected HSSFWorkbook generateWorkbook() throws IOException {
		HSSFWorkbook wb = loadWorkbook();
		if (wb==null) return null;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		String[] sqls = buildSql();
		String sql = sqls[0];
		String s1=sqls[1];
		String s2=sqls[2];
		String s3=sqls[3];
		List<ATT200000> list;
		ExcelBinder binder;
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		if (radAuto.isSelected()){
			list = execute(sql, new Object[]{});
			int ri=3;
			binder = binder(true);
			binder.setCellStyle(style);
			binder.setWorkBook(wb);
			for (ATT200000 data:list){
				binder.setDataObject(data);
				Integer[] count = getCount(s1,s2,s3,data.getDEPSN());
				List<Object[]> co = new ArrayList<Object[]>();
				co.add(new Object[]{BigDecimal.valueOf(count[0]),23,Number.class});
				co.add(new Object[]{BigDecimal.valueOf(count[1]),25,Number.class});
				co.add(new Object[]{BigDecimal.valueOf(count[2]),27,Number.class});
				binder.setCustomObjectList(co);
				binder.objectToRow(ri++);
			}
		}else{
			binder = binder(false);
			binder.setCellStyle(style);
			binder.setWorkBook(wb);
			List<String> listId = new ArrayList<String>();
			StringBuilder sb = new StringBuilder("(");
			for (int i=3;i<sheet.getPhysicalNumberOfRows();i++){	
				row = sheet.getRow(i);
				cell = row.getCell(4);
				String d = HSSFUtils.getStringCellValue(cell, true);
				if (cell==null||d.equals("")) continue;
				listId.add(HSSFUtils.getStringCellValue(cell, true));
				sb.append("?,");
			}
			if (sb.length()>1)
				sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			String sql1 = StringUtils.substringBefore(sql, "group by");
			String sql2 = StringUtils.substringAfter(sql, "group by");
			sql = sql1 + sb.toString() + " group by" + sql2;
			list = execute(sql, listId.toArray());
			Map<String, ATT200000> map = new HashMap<String, ATT200000>();
			for (ATT200000 data:list){
				map.put(data.getDEPSN(), data);
			}
			for (int i=3;i<sheet.getPhysicalNumberOfRows();i++){
				row = sheet.getRow(i);
				cell = row.getCell(4);
				if (cell==null||HSSFUtils.getStringCellValue(cell, true).equals("")) continue;
				String key = HSSFUtils.getStringCellValue(cell, true);
				ATT200000 data = map.get(key);
				if (data!=null){
					binder.setDataObject(data);
					Integer[] count = getCount(s1,s2,s3,data.getDEPSN());
					List<Object[]> co = new ArrayList<Object[]>();
					co.add(new Object[]{BigDecimal.valueOf(count[0]),23,Number.class});
					co.add(new Object[]{BigDecimal.valueOf(count[1]),25,Number.class});
					co.add(new Object[]{BigDecimal.valueOf(count[2]),27,Number.class});
					binder.setCustomObjectList(co);
					binder.objectToRow(i);
				}else{
					data = new ATT200000();
					binder.setDataObject(data);
					List<Object[]> co = new ArrayList<Object[]>();
					co.add(new Object[]{BigDecimal.ZERO,23,Number.class});
					co.add(new Object[]{BigDecimal.ZERO,25,Number.class});
					co.add(new Object[]{BigDecimal.ZERO,27,Number.class});
					binder.setCustomObjectList(co);
					binder.objectToRow(i);
				}
			}
		}

		if (radBangTong.isSelected()) setReportFileName("BangTongCBCN");
		else if (radBangTongCB.isSelected()) setReportFileName("BangTongCB");
		else if (radBangTongCN.isSelected()) setReportFileName("BangTongCB");
		else if (radBangTongATM.isSelected()) setReportFileName("BangTongATM");
		else if (radBangTongTTGT.isSelected()) setReportFileName("BangTongTTGT");
		return wb;
	}

	//thiet la bo chieu bo chieu...
	private Integer[] getCount(String s1, String s2, String s3, String exid) {
		if (exid!=null&&!exid.equals("")){
			s1 = s1 + "=?";
			s2 = s2 + "=?";
			s3 = s3 + "=?";
		}else{
			s1 = s1 + " is null";
			s2 = s2 + " is null";
			s3 = s3 + " is null";
		}
		String sql = s1 + " union all " + s2 + " union all " + s3;
		System.out.println(sql);

		Object[] params;
		if (exid!=null&&!exid.equals("")){
			params = new Object[3];
			params[0]=exid;
			params[1]=exid;
			params[2]=exid;
		}else params = new Object[]{};

		List<Integer> list = getDao().getJdbcTemplate().queryForList(sql, params, Integer.class);
		return list.toArray(new Integer[0]);
	}
	
	private List<ATT200000> execute(String sql, Object[] params){
		System.out.println(sql);
		return getDao().getSimpleJdbcTemplate().query(sql, new ParameterizedRowMapper<ATT200000>(){
			
			public ATT200000 mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				ATT200000 data = new ATT200000();
				for (int i=1;i<=meta.getColumnCount();i++){
					Object obj = rs.getObject(i);
					String columnName = meta.getColumnName(i);
					try {
						PropertyUtils.setProperty(data, columnName, obj);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
				return data;
			}
		}, params);
	}

	private void reportTypeChanged(ActionEvent e) {
		if (e.getActionCommand().equals("type_bt")){
			bindType("02");
		}else if (e.getActionCommand().equals("type_btcb")){
			bindType("03");
		}else if (e.getActionCommand().equals("type_btcn")){
			bindType("01");
		}else if (e.getActionCommand().equals("type_btatm")){
			bindType("04");
		}else if (e.getActionCommand().equals("type_btttgt")){
			bindType("05");			
		}else{
			sfFile.setModel(new DefaultListModel());
			sfFile.setSelectedIndex(-1);
		}
	}

	private void bindType(String id){
		if (id.equals(bindTypeId)) return;
		DefaultListModel model = new DefaultListModel();
		int si = -1;
		int k = -1;
		for (int i=0;i<listTsalInfo.size();i++){
			String str = (String) listTsalInfo.get(i)[0];			
			String p = StringUtils.substringBeforeLast(str, "/");			
			String tsalId = StringUtils.substringAfterLast(p, "/");
			if (tsalId.equals(id)){
				k++;
				String name = StringUtils.substringAfterLast(str, "/");
				SelectItem item = new SelectItem();
				item.setText(name);
				item.setValue(str);
				model.add(item);
				Boolean b = (Boolean) listTsalInfo.get(i)[1];
				if (b) si=k;
			}
		}
		sfFile.setModel(model);
		sfFile.setSelectedIndex(si);
		bindTypeId=id;
	}

	private void sfFactChanged(ActionEvent e) {
		if (sfFact.getSelectedIndex()>=0&&sfFact.getSelectedItem().toString().equals("FVL")){
			groupFVL.setEnabled(true);
		}else{
			groupFVL.setEnabled(false);
		}
	}

	private void templateSelected(ActionEvent e) {
		if (radAuto.isSelected()){
			sfFact.setEnabled(true);
			if (sfFact.getSelectedIndex()>=0&&sfFact.getSelectedItem().toString().equals("FVL"))
				groupFVL.setEnabled(true);
			else
				groupFVL.setEnabled(false);
			row2.setEnabled(false);
			fileUploadSelect.setVisible(false);
		}else{
			sfFact.setEnabled(false);
			groupFVL.setEnabled(false);
			row2.setEnabled(true);
			fileUploadSelect.setVisible(true);
		}
	}

	//easy way but connection bug...
	private ResultSet2ExcelFiller filler(int[] exclude){
		ResultSet2ExcelFiller filler = new ResultSet2ExcelFiller(ResultSet2ExcelFiller.RS_COLUMN_INDEX);
		Map<Object, Integer> map = filler.getExcelMap();
		for (int i=0;i<18;i++){
			if (ArrayUtils.contains(exclude, i)) continue;
			map.put(i+1, i+4);
		}
		map.put(19, 23);
		map.put(20, 25);
		for (int i=21;i<29;i++){
			if (ArrayUtils.contains(exclude, i)) continue;
			map.put(i, i+6);
		}
		return filler;
	}

	//minnor way
	private ExcelBinder binder(boolean includeDept){
		ExcelBinder binder = new ExcelBinder();
		List<Object[]> list = new ArrayList<Object[]>();
		if (includeDept)
			list.add(new Object[]{"DEPSN",4,String.class});
		list.add(new Object[]{"BSALY",5,Number.class});//SO NGUOI
		list.add(new Object[]{"DUCLS_S",6,Number.class});
		list.add(new Object[]{"REST_PAY_S",7,Number.class});
		list.add(new Object[]{"ADDCLS1_S",8,Number.class});
		list.add(new Object[]{"NADDCLS_S",9,Number.class});
		list.add(new Object[]{"ADDHOL_S",10,Number.class});
		list.add(new Object[]{"ADDHOLN_S",11,Number.class});
		list.add(new Object[]{"BSALY_N",12,Number.class});
		list.add(new Object[]{"BONUS1",13,Number.class});
		list.add(new Object[]{"BONUS2",14,Number.class});
		list.add(new Object[]{"BONUS3",15,Number.class});
		list.add(new Object[]{"BONUS6",16,Number.class});
		list.add(new Object[]{"BONUS_ACN",17,Number.class});
		list.add(new Object[]{"BONUS4",18,Number.class});
		list.add(new Object[]{"BONUS7",19,Number.class});
		list.add(new Object[]{"BONUS5",20,Number.class});
		list.add(new Object[]{"BONUS10",21,Number.class});
		list.add(new Object[]{"TINCOME",22,Number.class});
//		list.add(new Object[]{"",22,Number.class});//count joininsu>0
		list.add(new Object[]{"JOININSU",24,Number.class});
//		list.add(new Object[]{"",24,Number.class});//count bh_tnghiep>0
		list.add(new Object[]{"BH_TNGHIEP",26,Number.class});
//		list.add(new Object[]{"",26,Number.class});//count ylbx>0
		list.add(new Object[]{"YLBX",28,Number.class});
		list.add(new Object[]{"BORM",29,Number.class});
		list.add(new Object[]{"JOINLUM",30,Number.class});
		list.add(new Object[]{"PAYTAX",31,Number.class});
		list.add(new Object[]{"KQT",32,Number.class});
		list.add(new Object[]{"TKQ",33,Number.class});
		list.add(new Object[]{"TS1",34,Number.class});
		list.add(new Object[]{"TTS",35,Number.class});
		list.add(new Object[]{"BONUS1_HOL",36,Number.class});
		binder.setIndexPropertiesList(list);
		return binder;
	}

	private String[] buildSql(){
		String table = "ATT" + sfYear.getSelectedItem().toString() + sfMonth.getSelectedItem().toString();
		String factCondition="";
		String sql1Dept="";
		String sql2Dept="";
		String sql3Dept="";
		String sql4Dept="";
		String cond="";
		//lay theo chon lua  xuong
		if (radAuto.isSelected()){
			String fact = ListBinder.get(sfFact).toString();
			String infvl = "";
			if (fact.equals("FVL")) {
				int chkCount = 0;
				CheckBox chkOther = (CheckBox) groupFVL.getComponent(5);
				if (chkOther.isSelected()) {
					chkCount++;
					CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
					if (!chk1.isSelected()) {
						infvl = "and (d.name_group not like 'F1%' or d.name_group like 'F12%') ";
					} else
						chkCount++;
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (!chk.isSelected()) {
							int f=i+1;
							if (i>=3)f=i+2;
							infvl = infvl + "and d.name_group not like 'F" + f + "%' ";
						} else {
							chkCount++;
						}
					}
					if (chkCount != 6)
						infvl = "and ("
							+ StringUtils.substringAfter(infvl, "and ")
							+ ")";
					else
						infvl = "";
				} else {
					CheckBox chk1 = (CheckBox) groupFVL.getComponent(0);
					if (chk1.isSelected()) {
						infvl = "or (d.name_group like 'F1%' and d.name_group not like 'F12%')";
					}
					for (int i = 1; i < groupFVL.getComponentCount() - 1; i++) {
						CheckBox chk = (CheckBox) groupFVL.getComponent(i);
						if (chk.isSelected()) {
							infvl = infvl + " or d.name_group like 'F"
							+ (i + 1) + "%'";
						}
					}
					if (!infvl.equals("")) {
						infvl = "and ("
							+ StringUtils.substringAfter(infvl, "or ")
							+ ")";
					}
				}
			}
			factCondition = "and d.name_fact='" + fact + "' " + infvl;		
			sql1Dept = "and t.depsn in (select d.id_dept from n_department d where t.depsn=d.id_dept " + factCondition + ")\n";
			sql2Dept = factCondition;
			sql3Dept = factCondition + " and a.n_fact='"+fact+"'\n";
			sql4Dept = factCondition ;
			if (radBangTong.isSelected()){
				cond="and instr(t.possn,'_')<=0 ";
			}else if (radBangTongCN.isSelected()){
				cond="and t.possn='CN'";
			}else if (radBangTongCB.isSelected()){
				cond="and t.possn<>'CN' and instr(t.possn,'_')<=0 ";
			}else if (radBangTongATM.isSelected()){
				cond="and a.code=substr(t.possn,instr(t.possn,'_')+1) and a.n_fact='"+fact+"' ";
			}else if (radBangTongTTGT.isSelected()){
				cond="and d.id_group_direct_kt=a.id_group ";				
			}
		}
		//lay theo file mau excel cua nguoi dung
		else{
			sql1Dept = "and t.depsn in ";
			sql2Dept = "and d.depgb in ";
			sql3Dept = "and a.code in ";
			sql4Dept = "and a.id_group in ";
			if (radBangTong.isSelected()){
				cond="and instr(t.possn,'_')<=0 ";
			}else if (radBangTongCN.isSelected()){
				cond="and t.possn='CN'";
			}else if (radBangTongCB.isSelected()){
				cond="and t.possn<>'CN' and instr(t.possn,'_')<=0 ";
			}else if (radBangTongATM.isSelected()){
				cond="and a.code=substr(t.possn,instr(t.possn,'_')+1) ";
			}else if (radBangTongTTGT.isSelected()){
				cond="and d.id_group_direct_kt=a.id_group ";			
			}
		}
		//tsal02, tsal01
		String sql1 = 
			"select t.depsn, count(t.empsn) as bsaly, sum(t.ducls_s+t.nucls_s) as ducls_s, sum(t.rest_pay_s) as rest_pay_s,\n" +
			"sum(t.addcls1_s) as addcls1_s, sum(t.naddcls_s) as naddcls_s, sum(t.addhol_s) as addhol_s, sum(t.addholn_s) as addholn_s, sum(t.bsaly_n) as bsaly_n,\n" + 
			"sum(t.bonus1) as bonus1, sum(t.bonus1_hol) as bonus1_hol, sum(t.bonus2) as bonus2, sum(t.bonus3) as bonus3, sum(t.bonus6) as bonus6, sum(t.bonus_acn) as bonus_acn,\n" + 
			"sum(t.bonus4+t.bonus9) as bonus4, sum(bonus10) as bonus10, sum(t.bonus7) as bonus7, sum(t.bonus5) as bonus5, sum(t.tincome) as tincome,\n" + 
			"sum(t.joininsu) as joininsu, sum(t.bh_tnghiep) as bh_tnghiep, sum(t.ylbx) as ylbx, sum(t.borm) as borm, sum(t.joinlum) as joinlum,\n" + 
			"sum(t.paytax) as paytax, sum(t.kqt) as kqt, sum(t.tkq) as tkq, sum(t.ts1) as ts1, sum(t.ts) as tts\n" + 
			"from " + table + " t\n" + 
			"where " + StringUtils.substringAfter(cond, "and ") + sql1Dept +
			"group by t.depsn";

		//tsal03
		String sql2 = 
			"select d.depgb as depsn, count(t.empsn) as bsaly, sum(t.ducls_s+t.nucls_s) as ducls_s, sum(t.rest_pay_s) as rest_pay_s,\n" +
			"sum(t.addcls1_s) as addcls1_s, sum(t.naddcls_s) as naddcls_s, sum(t.addhol_s) as addhol_s, sum(t.addholn_s) as addholn_s, sum(t.bsaly_n) as bsaly_n,\n" + 
			"sum(t.bonus1) as bonus1, sum(t.bonus1_hol) as bonus1_hol, sum(t.bonus2) as bonus2, sum(t.bonus3) as bonus3, sum(t.bonus6) as bonus6, sum(t.bonus_acn) as bonus_acn,\n" + 
			"sum(t.bonus4+t.bonus9) as bonus4, sum(bonus10) as bonus10, sum(t.bonus7) as bonus7, sum(t.bonus5) as bonus5, sum(t.tincome) as tincome,\n" + 
			"sum(t.joininsu) as joininsu, sum(t.bh_tnghiep) as bh_tnghiep, sum(t.ylbx) as ylbx, sum(t.borm) as borm, sum(t.joinlum) as joinlum,\n" + 
			"sum(t.paytax) as paytax, sum(t.kqt) as kqt, sum(t.tkq) as tkq, sum(t.ts1) as ts1, sum(t.ts) as tts\n" + 
			"from " + table + " t, n_department d\n" + 
			"where t.depsn=d.id_dept " + cond + sql2Dept +
			"group by d.depgb";
		//tsal04
		String sql3 = 
			"select a.code as depsn, count(t.empsn) as bsaly, sum(t.ducls_s+t.nucls_s) as ducls_s, sum(t.rest_pay_s) as rest_pay_s,\n" +
			"sum(t.addcls1_s) as addcls1_s, sum(t.naddcls_s) as naddcls_s, sum(t.addhol_s) as addhol_s, sum(t.addholn_s) as addholn_s, sum(t.bsaly_n) as bsaly_n,\n" + 
			"sum(t.bonus1) as bonus1, sum(t.bonus1_hol) as bonus1_hol, sum(t.bonus2) as bonus2, sum(t.bonus3) as bonus3, sum(t.bonus6) as bonus6, sum(t.bonus_acn) as bonus_acn,\n" + 
			"sum(t.bonus4+t.bonus9) as bonus4, sum(bonus10) as bonus10, sum(t.bonus7) as bonus7, sum(t.bonus5) as bonus5, sum(t.tincome) as tincome,\n" + 
			"sum(t.joininsu) as joininsu, sum(t.bh_tnghiep) as bh_tnghiep, sum(t.ylbx) as ylbx, sum(t.borm) as borm, sum(t.joinlum) as joinlum,\n" + 
			"sum(t.paytax) as paytax, sum(t.kqt) as kqt, sum(t.tkq) as tkq, sum(t.ts1) as ts1, sum(t.ts) as tts\n" + 
			"from " + table + " t, n_department d, atm a\n" + 
			"where t.depsn=d.id_dept " + cond + sql3Dept +
			"group by a.code";
		
		//tsal05_ TT_GT_GM_VP
		String sql4 =
				"select a.id_group as depsn, count(t.empsn) as bsaly, sum(t.ducls_s+t.nucls_s) as ducls_s, sum(t.rest_pay_s) as rest_pay_s,\n" +
				"sum(t.addcls1_s) as addcls1_s, sum(t.naddcls_s) as naddcls_s, sum(t.addhol_s) as addhol_s, sum(t.addholn_s) as addholn_s, sum(t.bsaly_n) as bsaly_n,\n" + 
				"sum(t.bonus1) as bonus1, sum(t.bonus1_hol) as bonus1_hol, sum(t.bonus2) as bonus2, sum(t.bonus3) as bonus3, sum(t.bonus6) as bonus6, sum(t.bonus_acn) as bonus_acn,\n" + 
				"sum(t.bonus4+t.bonus9) as bonus4, sum(bonus10) as bonus10, sum(t.bonus7) as bonus7, sum(t.bonus5) as bonus5, sum(t.tincome) as tincome,\n" + 
				"sum(t.joininsu) as joininsu, sum(t.bh_tnghiep) as bh_tnghiep, sum(t.ylbx) as ylbx, sum(t.borm) as borm, sum(t.joinlum) as joinlum,\n" + 
				"sum(t.paytax) as paytax, sum(t.kqt) as kqt, sum(t.tkq) as tkq, sum(t.ts1) as ts1, sum(t.ts) as tts\n" + 
				"from " + table + " t, n_department d, n_groupdirect_indirect_kt a\n" + 
				"where t.depsn=d.id_dept " + cond + sql4Dept +
				"group by a.id_group";				

		String s1 = "";
		String s2 = "";
		String s3 = "";

		if (radBangTong.isSelected()||radBangTongCN.isSelected()){
			s1="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.joininsu>0 "+cond+" and t.depsn";
			s2="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.bh_tnghiep>0 "+cond+" and t.depsn";
			s3="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.ylbx>0 "+cond+" and t.depsn";

			return new String[]{sql1,s1,s2,s3};
		}else if (radBangTongCB.isSelected()){
			s1="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.joininsu>0 "+cond+" and d.depgb";
			s2="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.bh_tnghiep>0 "+cond+" and d.depgb";
			s3="select count(*) from "+table+" t, n_department d where t.depsn=d.id_dept and t.ylbx>0 "+cond+" and d.depgb";

			return new String[]{sql2,s1,s2,s3};
		}else if (radBangTongATM.isSelected()){
			s1="select count(*) from "+table+" t, atm a where t.joininsu>0 "+cond+" and a.code";
			s2="select count(*) from "+table+" t, atm a where t.bh_tnghiep>0 "+cond+" and a.code";
			s3="select count(*) from "+table+" t, atm a where t.ylbx>0 "+cond+" and a.code";

			return new String[]{sql3,s1,s2,s3};
		}
		else if (radBangTongTTGT.isSelected()){
			s1="select count(*) from "+table+" t, n_department d, n_groupdirect_indirect_kt a where t.depsn=d.id_dept and d.id_group_direct_kt=a.id_group and t.joininsu>0 "+cond+" and a.id_group";
			s2="select count(*) from "+table+" t, n_department d, n_groupdirect_indirect_kt a where t.depsn=d.id_dept and d.id_group_direct_kt=a.id_group and t.bh_tnghiep>0 "+cond+" and a.id_group";
			s3="select count(*) from "+table+" t, n_department d, n_groupdirect_indirect_kt a where t.depsn=d.id_dept and d.id_group_direct_kt=a.id_group and t.ylbx>0 "+cond+" and a.id_group";
		
			return new String[]{sql4,s1,s2,s3};
		}		
		return null;
	}

	
	public boolean validateUI() {
		String table = "ATT"+sfYear.getSelectedItem().toString()+sfMonth.getSelectedItem().toString();
		if (!FvGenericDAO.getInstanse().checkTableExist(table)){
			setErrorMessage("Không có bảng lương tháng " + sfMonth.getSelectedItem() + "/" + sfYear.getSelectedItem());
			return false;
		}
		if (radAuto.isSelected()){
			if (sfFact.getSelectedIndex()<0){
				setErrorMessage("Chọn xưởng");
				return false;
			}
		}else{
			if (sfFile.getSelectedIndex()<0){
				setErrorMessage("Chọn file mẫu");
				return false;
			}
			//check file exist
			SelectItem item = (SelectItem) sfFile.getSelectedItem();
			File file = new File(item.getValue().toString());
			if (!file.exists()){
				setErrorMessage("Không tìm thấy file " + item.getValue().toString());
				return false;
			}
		}
		if (!radBangTong.isSelected()&&!radBangTongCB.isSelected()&&!radBangTongCN.isSelected()&&!radBangTongATM.isSelected() &&!radBangTongTTGT.isSelected()){
			setErrorMessage("Chọn loại báo cáo");
			return false;
		}
		return true;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(2, Extent.PX)));
		add(rootLayout);
		Label label1 = new Label();
		label1.setText("Tháng");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		label1.setLayoutData(label1LayoutData);
		rootLayout.add(label1);
		Row row1 = new Row();
		rootLayout.add(row1);
		sfMonth = new SelectField();
		row1.add(sfMonth);
		Label label2 = new Label();
		label2.setText("Năm");
		row1.add(label2);
		sfYear = new SelectField();
		row1.add(sfYear);
		radAuto = new RadioButton();
		radAuto.setSelected(true);
		radAuto.setText("Toàn bộ đơn vị xưởng");
		ButtonGroup report = new ButtonGroup();
		radAuto.setGroup(report);
		radAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				templateSelected(e);
			}
		});
		rootLayout.add(radAuto);
		sfFact = new SelectField();
		sfFact.setWidth(new Extent(80, Extent.PX));
		sfFact.setDisabledBackground(new Color(0x808080));
		sfFact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sfFactChanged(e);
			}
		});
		rootLayout.add(sfFact);
		Label label3 = new Label();
		rootLayout.add(label3);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		rootLayout.add(groupFVL);
		CheckBox chkFv1 = new CheckBox();
		chkFv1.setText("FV1");
		chkFv1.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv1);
		CheckBox chkFv2 = new CheckBox();
		chkFv2.setText("FV2");
		chkFv2.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv2);
		CheckBox chkFv3 = new CheckBox();
		chkFv3.setText("FV3");
		chkFv3.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv3);
		CheckBox chkFv4 = new CheckBox();
		chkFv4.setText("FV4");
		chkFv4.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv4);
		CheckBox chkFv5 = new CheckBox();
		chkFv5.setText("FV5");
		chkFv5.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv5);
		CheckBox chkOther = new CheckBox();
		chkOther.setText("Khác");
		chkOther.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkOther);
		radExcel = new RadioButton();
		radExcel.setText("Theo mẫu excel");
		radExcel.setGroup(report);
		radExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				templateSelected(e);
			}
		});
		rootLayout.add(radExcel);
		row2 = new Row();
		row2.setEnabled(false);
		rootLayout.add(row2);
		sfFile = new SelectField();
		sfFile.setDisabledBackground(new Color(0x808080));
		row2.add(sfFile);
		Separator separator1 = new Separator();
		row2.add(separator1);
		fileUploadSelect = new FileUploadSelect();
		fileUploadSelect.setEnabled(false);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/excel_upload_icon.png");
		fileUploadSelect.setIcon(imageReference1);
		fileUploadSelect.setFileType("application/vnd.ms-excel");
		fileUploadSelect.setVisible(false);
		fileUploadSelect.setIconWidth(new Extent(24, Extent.PX));
		fileUploadSelect.setIconHeight(new Extent(24, Extent.PX));
		row2.add(fileUploadSelect);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setWidth(new Extent(220, Extent.PX));
		groupBox1.setInsets(new Insets(new Extent(15, Extent.PX), new Extent(5,
				Extent.PX)));
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(10, Extent.PX)));
		groupBox1LayoutData.setColumnSpan(2);
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setSize(1);
		groupBox1.add(grid2);
		radBangTong = new RadioButton();
		radBangTong.setText("Bảng tổng");
		ButtonGroup type = new ButtonGroup();
		radBangTong.setGroup(type);
		radBangTong.setActionCommand("type_bt");
		radBangTong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportTypeChanged(e);
			}
		});
		grid2.add(radBangTong);
		radBangTongCB = new RadioButton();
		radBangTongCB.setText("Bảng tổng cán bộ");
		radBangTongCB.setGroup(type);
		radBangTongCB.setActionCommand("type_btcb");
		radBangTongCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportTypeChanged(e);
			}
		});
		grid2.add(radBangTongCB);
		radBangTongCN = new RadioButton();
		radBangTongCN.setText("Bảng tổng công nhân");
		radBangTongCN.setGroup(type);
		radBangTongCN.setActionCommand("type_btcn");
		radBangTongCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportTypeChanged(e);
			}
		});
		grid2.add(radBangTongCN);
		radBangTongATM = new RadioButton();
		radBangTongATM.setText("Bảng tổng ATM");
		radBangTongATM.setGroup(type);
		radBangTongATM.setActionCommand("type_btatm");
		radBangTongATM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportTypeChanged(e);
			}
		});
		grid2.add(radBangTongATM);
		radBangTongTTGT = new RadioButton();
		radBangTongTTGT.setText("Bảng tổng TT_GT_GM_VP");
		radBangTongTTGT.setGroup(type);
		radBangTongTTGT.setActionCommand("type_btttgt");
		radBangTongTTGT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportTypeChanged(e);
			}
		});
		grid2.add(radBangTongTTGT);
	}
}
