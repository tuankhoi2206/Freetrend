package ds.program.fvhr.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.lowagie.text.Table;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableModel;
import nextapp.echo2.app.table.TableModel;




import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.util.ListBinder;
import fv.util.ReportUtils;
import fv.util.Vni2Uni;
import fv.util.library;




/**
 * N_KYLUAT * 
 */
public class NKYLUATProgram extends MaintainSProgram {
	
	
	private NKYLUATDataContent dtc;
	library l=new library();
	Vni2Uni c=new Vni2Uni();
	private Button excel;
	private SplitPane splitPane;
	private WPCSearchRow searchRow;
	
	public SelectField sfxuong;
	public SelectField sfhphat;
	private DscField txtSothe;
	private int dataMode;
	private Label st;
	private Label x;
	private Label hp;
	
	public int count;
	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	DSPB02 u;
	

	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		//setMasterDataContent(new NKYLUATDataContent());
		
		dtc=new NKYLUATDataContent();
		setMasterDataContent(dtc);
		
	}
	
	@Override
	public boolean doNew(){
		if(super.doNew()){
			dataMode=IProgram.DATAMODE_NEW;
			return true;
		}
		return false;
	}
	
	
	
	private class WPCSearchRow extends Row{
		private static final long serialVersionUID = 1L;
		
		private CheckBox xuong;
		private Button btnSearch;
		private NKYLUATProgram program;
		public WPCSearchRow(NKYLUATProgram program){
			super();
			this.program=program;
			initComponents();
		}
		
		public void setSothe(String st){
			txtSothe.setText(st);
		}

		
		private void initComponents(){
			this.setCellSpacing(new Extent(2));
			this.setStyleName("Default.Toolbar");
			this.setBackground(new Color(0xCCCC99));
			this.add(new Label("Số thẻ"));
			txtSothe = new DscField();
			txtSothe.setMaximumLength(8);
			//final TextField txtEmpsn = new TextField();
			txtSothe.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					doSearch();
				}
			});
			txtSothe.addFocusListener(new FocusListener() {
				
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					if(sfxuong.getSelectedIndex()!=-1){
						sfxuong.setSelectedIndex(-1);
					}
					if(sfhphat.getSelectedIndex()!=-1){
						sfhphat.setSelectedIndex(-1);
					}
				}
			});
			this.add(txtSothe);
			this.add(new Label("Hình phạt"));
			sfhphat=new SelectField();
			this.add(sfhphat);
			this.add(new Label("Xưởng"));
			sfxuong=new SelectField();
			sfxuong.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(sfxuong.getSelectedIndex()!=-1){
						txtSothe.setText("");
					}
				}
			});
			this.add(sfxuong);
			
			
			btnSearch = new Button();
			btnSearch.setStyleName("Default.ToolbarButton");
			btnSearch.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQuery.gif"));
			btnSearch.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQueryD.gif"));
			btnSearch.setToolTipText("Tìm kiếm");
			btnSearch.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doSearch();
				}
			});
		
			this.add(btnSearch);
		}

		protected void doSearch() {
			String sql="";
				if((txtSothe.getText().equals(""))&&(sfxuong.getSelectedIndex()==-1)){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
							"Chưa chọn thông tin cần tìm..");
					return;
				}
				if(!txtSothe.getText().equals("")){
					sql+=" o.EMPSN='"+txtSothe.getText()+"'";
					//params=new Object[]{txtSothe.getText()};
				}
				if(sfxuong.getSelectedIndex()!=-1){
					sql+=" o.EMPSN in(select a.EMPSN from N_KYLUAT a,N_EMPLOYEE e," +
					" N_DEPARTMENT d where e.EMPSN=a.EMPSN and d.ID_DEPT=e.DEPSN_TEMP1 " +
					" and d.NAME_FACT='"+sfxuong.getSelectedItem().toString()+"')";
					//params=new Object[]{sfxuong.getSelectedItem().toString()};
					
				}
				if(sfhphat.getSelectedIndex()!=-1){
					
					if(sfhphat.getSelectedItem().toString().equals("PHAT CANH CAO")){
						sql+=" and o.ID_PHAT='01'";
						//params=new Object[]{"01"};
					}
					if(sfhphat.getSelectedItem().toString().equals("NGUNG NANG LUONG")){
						sql+=" and o.ID_PHAT='02'";
						//params=new Object[]{"02"};
					}
					if(sfhphat.getSelectedItem().toString().equals("SA THAI")){
						sql+=" and o.ID_PHAT='03'";
						//params=new Object[]{"03"};
					}
					if(sfhphat.getSelectedItem().toString().equals("CÁCH CHỨC")){
						sql+=" and o.ID_PHAT='04'";
						//params=new Object[]{"04"};
					}
					if(sfhphat.getSelectedItem().toString().equals("CHUYỂN CÔNG VIỆC KHÁC-MỨC LƯƠNG THẤP HƠN")){
						sql+=" and o.ID_PHAT='05'";
						//params=new Object[]{"05"};
					}
					
				}
				
				
				
				ProgramCondition pc = new ProgramCondition(sql, new Object[]{});
				program.setQueryCondition(pc);
				program.refresh();
			}			
		
	}
	
	@Override
	protected void switchContent(int view) {
		if (mainSplitPane.getComponentCount() > 1) {
			if (((view == 0) && (mainSplitPane.getComponent(1) == splitPane))
					|| ((view == 1) && (mainSplitPane.getComponent(1) == this))) {
				return;
			}
			mainSplitPane.remove(1);
		}
		switch (view) {
		case 0:
			mainSplitPane.add(splitPane);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, true);
			//getMasterToolbar().refresh();
			break;
		case 1:
			mainSplitPane.add(this);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, true);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
			getMasterToolbar().refresh();
			break;
		}
		//if (view==0) doBrowserContentRefresh();
	}
	@Override
	protected void doRefresh() {
		if(mainSplitPane.getComponent(1) == splitPane) {
			//瀏覽頁面
			this.doBrowserContentRefresh();
		}
		else {
			//明細頁面
			//取得目前所選取的資料指標
			int recNo = getBrowserContent().getCurrentSelectRowNo();
			//重查該筆資料，並顯示該資料內容
			doDataContentRefresh(recNo);
		}
	}
	
	@Override
	public int refresh() {
		int ret = super.refresh();
		//int a=getBrowserContent().getBrowserTable().getModel().getRowCount();
		count= getBrowserContent().getBrowserNav().getModel().getTotalRows();
		//System.out.println(count);
		if(count>0){
			excel.setEnabled(true);
		}else{
			excel.setEnabled(false);
			l.ShowMessageInfo("Không tìm thấy dữ liệu..");
		}
		return ret;
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	

	@Override
	protected int doInit() {
		int ret = super.doInit();
		splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(38));
		searchRow = new WPCSearchRow(this);
	
		getBrowserContent().getBrowserNav().setRowsPerPage(20);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		
		
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(getLoginInfo().getUserID());
		
		String a=l.vungQL(u.getPB_USERNO());
		//System.out.println(a);
		String sql="o.EMPSN in(select e.EMPSN from N_EMPLOYEE e,N_DEPARTMENT d " +
				" where o.EMPSN=e.EMPSN and e.DEPSN_TEMP1=d.ID_DEPT  and d.NAME_FACT IN "+a+")";
		ProgramCondition pc=new ProgramCondition(sql);
		setBaseCondition(pc);
		
		excel = new Button();
		excel.setEnabled(false);
		excel.setToolTipText("Xuất dữ liệu ra Excel");
		excel.setStyleName("Default.ToolbarButton");
		excel.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcel.gif"));
		excel.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcelD.gif"));
		getMasterToolbar().add(excel);
		excel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				doExportFromModel();
			}
		});
		
		
		DefaultListModel model=(DefaultListModel) sfxuong.getModel();
		List li=l.vungQL1(u.getPB_USERNO());
		for (int i = 0; i < li.size(); i++) {
			model.add(li.get(i));
		}
		//dtc.getDataObjectSet().getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_PHAT", library.getIDhinhphat());
		
		ListBinder.bindSelectField(sfhphat, l.getIDhinhphat(), true);
		
		
		dtc.dscngayKT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(dtc.ac_cmd){
					doSave();
				}
			}
		});
		return ret;
	}
	
	private void doExportFromModel(){
		getBrowserContent().getBrowserNav().getModel().setCurrentPage(0);
		HSSFWorkbook wb = ReportUtils.loadTemplate("ex", "phat.xls");
		if (wb==null) return;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		//fill header
		row=sheet.createRow(0);
		cell=row.createCell(0);
		cell.setCellValue("Ngày: "+df.format(Calendar.getInstance().getTime()));
		row=sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,6));
		cell=row.createCell(0);
		if(sfxuong.getSelectedIndex()!=-1){
			cell.setCellValue("DANH SÁCH BỊ KỶ LUẬT XƯỞNG "+sfxuong.getSelectedItem());
			}else{
			cell.setCellValue("DANH SÁCH BỊ KỶ LUẬT");
		}
		cell.setCellStyle(style);
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//////////
		count=getBrowserContent().getBrowserNav().getModel().getTotalRows();
		if (count==0) return;
		//System.out.println(a);
		for (int i=0;i<count;i++){
			row = sheet.createRow(i+4);
			String empsn="";
			cell = row.createCell(0);
			Object obj = getBrowserContent().getBrowserTable().getModel().getValueAt(0, i);
				if (obj!=null){
					String s = String.valueOf(obj);
					cell.setCellValue(s);					
				}
				cell = row.createCell(1);
				String  h=l.getstring("N_EMPLOYEE", "EMPSN", obj.toString(), "FNAME");
				String  t=l.getstring("N_EMPLOYEE", "EMPSN", obj.toString(), "LNAME");
				cell.setCellValue(h+" "+t);
				cell = row.createCell(2);
				Object obj1 = getBrowserContent().getBrowserTable().getModel().getValueAt(5, i);
				if (obj1!=null){
					String s = String.valueOf(obj1);
					cell.setCellValue(s);					
				}
				cell = row.createCell(3);
				Object obj2 = getBrowserContent().getBrowserTable().getModel().getValueAt(6, i);
				if (obj2!=null){
					String s = String.valueOf(obj2);
					cell.setCellValue(s);					
				}
				cell = row.createCell(4);
				Object obj3 = getBrowserContent().getBrowserTable().getModel().getValueAt(4, i);
				if (obj3!=null){
					String s = String.valueOf(obj3);
					cell.setCellValue(s);					
				}
				cell = row.createCell(5);
				Object obj4 = getBrowserContent().getBrowserTable().getModel().getValueAt(7, i);
				if (obj4!=null){
					String s = String.valueOf(obj4);
					cell.setCellValue(s);					
				}
				cell = row.createCell(6);
				Date  nnx=l.getdate("N_EMPLOYEE", "EMPSN", obj.toString(), "DATE_HIRED");
					//java.sql.Date sqld=new java.sql.Date (nnx.getTime());
					cell.setCellValue(sdf.format(nnx));
				
				
		}
		try {
			for (int i = 0; i < 6; i++) {
				sheet.autoSizeColumn(i);
			}
			String nameEX="";
			if(sfxuong.getSelectedIndex()!=-1){
				nameEX="Danh_sach_ky_luat_xuong_"+sfxuong.getSelectedItem();
			}else{
				nameEX="Danh_sach_ky_luat";
			}
			ReportUtils.doExportExcel(wb, nameEX);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
		
	}

	@Override
	protected QueryPane createNormalQuery() {
		return new NKYLUATQuery();
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.removeAll();
		mainSplitPane.removeAll();
		windowPane.add(mainSplitPane);
		mainSplitPane.add(getMasterToolbar());
		mainSplitPane.add(splitPane);
		splitPane.add(searchRow);
		//splitPane.add(browser);
		splitPane.add(getBrowserContent());
		
		
	}
	/*@Override
	public boolean doNew(){
		if(super.doNew()){
			getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_SAVE, false);
			return true;
		}
		return false;
	}*/
	
	@Override
	public boolean doSave(){
		N_KYLUAT data=(N_KYLUAT) getMasterDataContent().getDataObject();
		
	
			if(super.doSave()){
				if(dataMode==IProgram.DATAMODE_NEW){
					String note=c.convertToVNI("NHẬP KỶ LUẬT D_"+data.getID_DIEU()+" K_"+data.getID_KHOAN()+" HP_"+data.getID_PHAT()+" BG_"+df.format(data.getDATE_P())+" EN_"+df.format(data.getDATE_HL()));
					l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_KYLUAT", "INSERT", data.getEMPSN(), note);
					//dtc.i++;
					dtc.fill_data_table();
					
					doNew();
				}
			return true;			
		}
		return false;

	}
	@Override
	public void doCancel() {
		// TODO Auto-generated method stub
		super.doCancel();
		if(dtc.model_t.getRowCount()>0){
			dtc.model_t.clear();
			dtc.i=0;
		}
	}
	@Override
	public boolean doDelete(){
		N_KYLUAT data=(N_KYLUAT) getMasterDataContent().getDataObject();
		Connection con=Application.getApp().getConnection();
		Statement st=null;
		ResultSet rs=null;
		CallableStatement cs=null;
		String sql="select * from n_labour where date_s>=to_date('"+df.format(data.getDATE_P())+"','dd/mm/yyyy') and" +
				" date_s<=to_date('"+df.format(data.getDATE_HL())+"','dd/mm/yyyy') and empsn='"+data.getEMPSN()+"'";
		String hp=l.getfiled2("N_KYLUAT", "EMPSN", data.getEMPSN(), "to_char(date_p,'dd/mm/yyyy')", df.format(data.getDATE_P()).toString(),"to_char(date_hl,'dd/mm/yyyy')",
				df.format(data.getDATE_HL()).toString(),"ID_PHAT");
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()){
				if(l.equals("02")){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
							"Đã ký HPLD trong thời gian bị kỷ luật không được xoá..");
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		String mm=sdf.format(data.getDATE_P()).toString().substring(3, 5);
		String yy=sdf.format(data.getDATE_P()).toString().substring(6, 10);
		boolean check=l.CheckKhoaDataMonth(data.getEMPSN(), mm, yy);
		if(check==false){
			l.ShowMessageError("DL đã khoá");
			return false;
		}
		
		
		
		if(super.doDelete()){
			try {
				
				String note=c.convertToVNI("XÓA KỶ LUẬT D_"+data.getID_DIEU()+" K_"+data.getID_KHOAN()+" HP_"+data.getID_PHAT()+" BG_"+df.format(data.getDATE_P())+" EN_"+df.format(data.getDATE_HL()));
				l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_KYLUAT", "DELETE", data.getEMPSN(), note);
				dtc.fill_data_table();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					if(con!=null){
						con.close();
					}
					if(st!=null){
						st.close();
					}
					if(cs!=null){
						cs.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			return true;
		}
		return false;
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		dtc.ac="";
		int row=getBrowserContent().getBrowserTable().getSelectionModel().getMaxSelectedIndex();
		String empsn=getBrowserContent().getBrowserTable().getModel().getValueAt(0, row).toString();
		String  h=l.getstring("N_EMPLOYEE", "EMPSN", empsn, "FNAME");
		String  t=l.getstring("N_EMPLOYEE", "EMPSN", empsn, "LNAME");
		String [] all=l.index_all_empsn(empsn);
		String x=all[0];
		String n=all[1];
		String dv=all[2];
		dtc.hoten.setText(h+" "+t);
		dtc.nhom.setText(x+"."+n+"."+dv);
		dtc.hoten.setForeground(Color.RED);
		dtc.nhom.setForeground(Color.BLUE);
		
		//6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        //return null;
		return new String[]{"EMPSN","NOTE","ID_DIEU","ID_KHOAN","ID_PHAT","DATE_P","DATE_HL","QDKL"};
	}
}
