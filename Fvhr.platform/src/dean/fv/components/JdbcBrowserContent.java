package fv.components;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.IdClass;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;

import fv.util.BundleUtils;
import fv.util.CustomDomainUtils;
import fv.util.Vni2Uni;
import fv.util.VniSorter;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import echopointng.table.SortableTableColumn;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableColumnModel;

/**
 * A copy of <code>BrowserContent</code> for dynamic persistense unit
 * @author Hieu
 *
 */
public class JdbcBrowserContent extends ContentPane{

	private static final long serialVersionUID = -66377785500775476L;
	private DscPageNavigation navigator;
	private DscPageableSortableTable dataTable;
	private SplitPane splitPane;
	//Bean store jdbc data
	private String[] tableHeader;
	private List<?> listData;
	private int rowNo=0;
	private Class<?> clazz;//proxy class
	private Class<?> pkClass;
	//Jdbc DAO <=> Xml DAO
	private Map<String, String> columnHeaderMap;
//	private ConfigHelper config;
	private String[] vniColumns;
	
	private TroiOiSortableModel model;
	
	private RowNavigator rowNav;
	
	public JdbcBrowserContent(){
		super();
		initComponents();
	}
	
	public String[] getTableHeader(){
		return tableHeader;
	}
	
	public void setTableHeader(String[] tableHeader){
		this.tableHeader=tableHeader;
		if (columnHeaderMap==null||columnHeaderMap.size()==0){
			try {
				columnHeaderMap = initColumnHeaderMap();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		initTableColumn();		
	}
	
	public List<?> getListData(){
		return listData;
	}
	
	public void setListData(List<?> listData){
		this.listData = listData;
	}
	
	public void setDataClass(Class<?> clazz){
		this.clazz=clazz;
		IdClass idClazz = clazz.getAnnotation(IdClass.class);
		if (idClazz!=null){
			this.pkClass=idClazz.value();
		}
	}
	
	public Class<?> getDataClass(){
		return clazz;
	}
	
	public Class<?> getPkClass() {
		return pkClass;
	}

	public void setPkClass(Class<?> pkClass) {
		this.pkClass = pkClass;
	}
	
	public void setVniColumns(String[] vniCols){
		this.vniColumns=vniCols;
	}
	
	protected void doUIRefresh(){
		
	}
	
	public void setRowNavigator(RowNavigator rowNav){
		this.rowNav=rowNav;
	}
	
	public RowNavigator getRowNavigator(){
		return this.rowNav;
	}
	
	protected void initTableColumn(){
//		config = new ConfigHelper();
//		config.setConfiguration(getDataClass());
//		ResourceBundle bundle = ResourceBundle.getBundle("resource.localization.UICaption", Application.getApp().getLocale());
//		config.setResourceBundle(bundle);
		TableColumnModel columnModel = new DefaultTableColumnModel();
		for (int i=0;i<tableHeader.length;i++){
			SortableTableColumn column = new SortableTableColumn(i);			
			column.setHeaderRenderer(dataTable.getDefaultHeaderRenderer());
			column.setComparator(COMPARATOR);
			column.setModelIndex(i);
			column.setHeaderValue(getHeaderDisplay(tableHeader[i]));
			columnModel.addColumn(column);
		}
		dataTable.setColumnModel(columnModel);
		model = new TroiOiSortableModel(columnModel);
		ListSelectionModel smodel = dataTable.getSelectionModel();
		model.setSelectionModel(smodel);		
		dataTable.setModel(model);		
		dataTable.setSelectionEnabled(true);
		dataTable.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				model.selectedIndex=getRowNo();
			}
		});
		navigator.setTable(dataTable);
		RowNavigator nav = new RowNavigator(this);
		setRowNavigator(nav);
	}
	
	private String getHeaderDisplay(String columnName){
		try {
			return BundleUtils.getCaption(clazz, columnName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return columnName;
		}
	}
		
	private void initComponents(){
		splitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(30));
		add(splitPane);
		navigator = new DscPageNavigation();
		navigator.setBackground(new Color(0xc0c0c0));
		navigator.setBorder(new Border(new Extent(2, Extent.PX),
				Color.WHITE, Border.STYLE_GROOVE));
		splitPane.add(navigator);
		dataTable = new DscPageableSortableTable();
		dataTable.setRolloverEnabled(false);
		dataTable.setAutoCreateColumnsFromModel(false);
		dataTable.setStyleName("Table.DscPageableSortableTable");		
//		dataTable.addActionListener(new ActionListener(){
//			private static final long serialVersionUID = 1L;
//
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		
		splitPane.add(dataTable);
	}
	
	public void setColumnHeaderMap(Map<String, String> columnHeaderMap) {
		this.columnHeaderMap = columnHeaderMap;
	}
	
	public Map<String, String> getColumnHeaderMap(){
		return this.columnHeaderMap;
	}
	
	public Map<String, String> initColumnHeaderMap() throws NoSuchFieldException{
		String[] headers = getTableHeader();
		Map<String, String> map;
		if (headers==null || headers.length==0){			
			map = CustomDomainUtils.getHeaderColumnMap(getDataClass());
			tableHeader = new String[map.size()];
			Iterator<?> it = map.entrySet().iterator();
			int i=0;
			while (it.hasNext()){
				@SuppressWarnings("unchecked")
				Entry<String, String> ks = (Entry<String, String>) it.next();
				tableHeader[i] = ks.getKey();
				i++;
			}
		}else{
			map = CustomDomainUtils.getHeaderColumnMap(getDataClass(), headers);
		}
		return map;
	}
		
	protected void applyData(){
		PageableSortableTableModel model = (PageableSortableTableModel)dataTable.getModel();
		model.clear();
		for (int i=0;i<listData.size();i++){
			for (int j=0;j<tableHeader.length;j++){
				String header = tableHeader[j];
				try {
					Object obj = PropertyUtils.getProperty(listData.get(i), columnHeaderMap.get(tableHeader[j]));					
					if (obj instanceof java.util.Date){
						obj = new SimpleDateFormat("dd/MM/yyyy").format(obj);
					}
					if (vniColumns!=null&&vniColumns.length!=0)
						if (ArrayUtils.contains(vniColumns, header)){
							if (obj==null) throw new RuntimeException(listData.get(i) + " object null ----> Vni2Uni.convertToUnicode khong dc");
							obj = Vni2Uni.convertToUnicode(obj.toString());
						}
					model.setValueAt(obj, j, i);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		
		navigator.setRowsPerPage(20);
		dataTable.getSelectionModel().setSelectedIndex(0, true);
		navigator.reset();
		rowNav.reset();
	}
	
	public void refresh(){
		doDataContentRefresh();
		applyData();
		if (listData.size()>0){
			dataTable.getSelectionModel().setSelectedIndex(rowNo, true);
		}
	}
	
	protected void doDataContentRefresh(){
		
	}
	
	public int getRowNo(){
		rowNo = dataTable.getSelectionModel().getMaxSelectedIndex();		
		return rowNo;
	}
	
	public void setRowNo(int rowNo){
		this.rowNo=rowNo;
	}
	
	private static final Comparator<Object> COMPARATOR = new Comparator<Object>() {

		@Override
		public int compare(Object o1, Object o2) {
        	if(o1 == null && o2 == null)
        		return 0;
        	else if(o1 == null)
        		return 1;
        	else if(o2 == null)
        		return -1;
        	if (o1 instanceof Integer && o2 instanceof Integer){
        		return ((Integer)o1).compareTo((Integer)o2);
        	}else if (o1 instanceof Float && o2 instanceof Float){
        		return ((Float)o1).compareTo((Float)o2);
        	}else if (o1 instanceof Double && o2 instanceof Double){
        		return ((Double)o1).compareTo((Double)o2);
        	}else if (o1 instanceof Short && o2 instanceof Short){
        		return ((Short)o1).compareTo((Short)o2);
        	}else if (o1 instanceof Date && o2 instanceof Date){
        		return ((Date)o1).compareTo((Date)o2);
        	} else if (o1 instanceof BigDecimal && o2 instanceof BigDecimal){
        		return ((BigDecimal)o1).compareTo((BigDecimal)o2);
        	}        	
        	return VniSorter.compare(((String)o1), ((String)o2));
		}
	};
	
	public int getSelectedIndex(){
		return dataTable.getSelectionModel().getMaxSelectedIndex();
	}
	
	public PageableSortableTableModel getModel(){
		return (PageableSortableTableModel)dataTable.getModel();
	}
	
	public DscPageableSortableTable getTable(){
		return dataTable;
	}
	//ColumnName, Value
	public void locale(String columnName, Object value){
		if (ArrayUtils.contains(tableHeader, columnName)){
			int index = ArrayUtils.indexOf(tableHeader, columnName);
			int dataSize = getModel().getRows().size();
			int rowPerPage = navigator.getModel().getRowsPerPage();
			int currPage = getModel().getCurrentPage();
			getModel().setCurrentPage(0);
			for (int i=0;i<dataSize;i++)
				if (getModel().getValueAt(index, i).equals(value)){
					getModel().setCurrentPage(i/rowPerPage);
					dataTable.getSelectionModel().setSelectedIndex(i%rowPerPage, true);
					navigator.reset();
					return;
				}
			getModel().setCurrentPage(currPage);
		}
		Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK + MessageDialog.TYPE_INFORMATION, "Not found");
		
	}
	
	public void locale(int rowIndex){
		int rowPerPage = navigator.getModel().getRowsPerPage();
		int index = rowIndex%rowPerPage-1;
		dataTable.getSelectionModel().setSelectedIndex(index, true);
		getModel().setCurrentPage(rowIndex/rowPerPage);
		navigator.reset();
	}
	
	public DscPageNavigation getDscNavigator(){
		return navigator;
	}
	
	public void refreshRowNo() {
		rowNo=getSelectedIndex();
	}
	
	/**
	 * Sau khi sort có thể giữ lại dòng đang chọn
	 * @author Hieu
	 *
	 */
	private class TroiOiSortableModel extends PageableSortableTableModel{

		private static final long serialVersionUID = 1L;
		private int selectedIndex=0;
		private ListSelectionModel selectionModel;
		
		public TroiOiSortableModel(TableColumnModel columnModel) {
			super(columnModel);			
		}
		
		@Override
		public void sortByColumn(int column, boolean ascending) {
			super.sortByColumn(column, ascending);			
			selectionModel.setSelectedIndex(selectedIndex, true);
		}
		
		@Override
		public void setSelectionModel(ListSelectionModel model){
			selectionModel=model;
			super.setSelectionModel(model);
		}
	}
}
