package ds.program.fvhr.ui;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.collections.set.CompositeSet.SetMutator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.dao.DeadlockLoserDataAccessException;

import sun.security.action.GetPropertyAction;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableColumnModel;
import ds.MainScreen;
import ds.program.fvhr.domain.N_KYLUAT;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import dsc.echo2app.propertyeditors.PropertyEditors;

import echopointng.SelectFieldEx;
import echopointng.table.SortableTableColumn;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.VniEditor;
import fv.util.library;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NKYLUATDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label EMPSN_CaptionLabel;
    public dsc.echo2app.component.DscField EMPSN_DscField1;
    private Label xuong;
    private Label a;
    public Label nhom;
    public Label hoten;
    private Label donvi;
    private SelectFieldEx sfdonvi;
    private nextapp.echo2.app.Label ID_DIEU_CaptionLabel;
    //private nextapp.echo2.app.SelectField ID_DIEU_SelectField1;
    private SelectFieldEx sfdieu;
    private nextapp.echo2.app.Label ID_KHOAN_CaptionLabel;
    //private nextapp.echo2.app.SelectField ID_KHOAN_SelectField2;
    private SelectFieldEx sfkhoan;
    private nextapp.echo2.app.Label ID_PHAT_CaptionLabel;
    //private nextapp.echo2.app.SelectField ID_PHAT_SelectField3;
    private SelectFieldEx sfhp;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField2;
    private nextapp.echo2.app.Label QDKL_CaptionLabel;
    private dsc.echo2app.component.DscField QDKL_DscField3;
    private nextapp.echo2.app.Label DATE_P_CaptionLabel;
    public dsc.echo2app.component.DscDateField DATE_P_DscDateField1;
    private nextapp.echo2.app.Label DATE_HL_CaptionLabel;
    public dsc.echo2app.component.DscDateField DATE_HL_DscDateField2;
    private ResourceBundle bundle;
    private DscField dscngayBD;
    public DscField dscngayKT;
    library l=new library();
    private DefaultListModel m_nhom,m_donvi,m_xuong,m_dieu,m_khoan,m_hp;
    Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    List lists=new ArrayList();
    private String fill; 
    SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
    public String ac,nkt,fac;
    private int dataMode;
    DSPB02 u;
    
    public DscPageableSortableTable Table;
	public DscPageNavigation Navigation;
	
	PageableSortableTableModel model_t;
	public int i=0;
	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	OBJ_UTILITY obj=new OBJ_UTILITY();
	public boolean ac_cmd=false;
	
	String temp="";

	/**
	 * Creates a new <code>NKYLUATDataContent</code>.
	 * @param fill 
	 */
	public NKYLUATDataContent() {
		super();
		//this.fill=fill;

		// Add design-time configured components.
		initComponents();
	}


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
			nhom.setText("");
			hoten.setText("");
			
		} else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				//新增時，設定哪些元件Enable/Disable
			} else {
				//修改時，設定哪些元件Enable/Disable
			}
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	public Class getDataObjectClass() {
		return N_KYLUAT.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_KYLUAT data = (N_KYLUAT) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_KYLUAT data = (N_KYLUAT) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			String mm=dscngayBD.getText().substring(3, 5);
			String yy=dscngayBD.getText().substring(6, 10);
			String lock=l.getfiled2("N_GET_DATA", "EMPSN", EMPSN_DscField1.getText(), "MONTHS", mm, "YEARS", yy, "LOCKED");
			if(lock.equals("1")){
				setErrorMessage("Đã khoá dl của tháng "+mm+"/"+yy);
				return false;
			}
			if(data.getID_DIEU().equals("Chon dieu")){
				setErrorMessage("Điều bắt buộc phải nhập");
				return false;
			}
			if(data.getID_KHOAN().equals("Chon khoan")){
				setErrorMessage("Khoản bắt buộc phải nhập");
				return false;
			}
			boolean exit=l.check_exits2("N_KHOAN", "ID_DIEU", data.getID_DIEU(), "ID_KHOAN", data.getID_KHOAN());
			if(!exit){
				setErrorMessage("Điều "+data.getID_DIEU()+" chưa có khoản "+data.getID_KHOAN()+".");
				return false;
			}
			
		}
		
		
		return ret;
	}
	
	

	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		
		//ListBinder.bindSelectField(sfkhoan, l.getIDkhoan(), true);
		//ListBinder.bindSelectField(sfhp, l.getIDhinhphat(), true);
		//1.註冊資料欄位之顯示方式
		registPropertyEditor();
		sfdieu.setSelectedIndex(0);
		sfkhoan.setSelectedIndex(0);
		sfhp.setSelectedIndex(0);

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();
		
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());
		
		TableColumnModel columnModel=loadColumnModel();
		Table.setColumnModel(columnModel);
		PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table.getSelectionModel());
		model.setRowsPerPage(15);
		Table.setModel(model);
		Table.setSelectionEnabled(true);
		Navigation.setTable(Table);
		model_t = (PageableSortableTableModel)Table.getModel();
	
		return nRet;
	}
	
	private TableColumnModel loadColumnModel(){
		TableColumnModel columnModel =new DefaultTableColumnModel();
		for (int i=0;i<8;i++){
		SortableTableColumn column=new SortableTableColumn(i);
		column.setHeaderRenderer(Table.getDefaultHeaderRenderer());
		column.setComparator(INT_COMPARATOR);
		column.setModelIndex(i);
		column.setHeaderValue(getColumnHeader()[i]);
		columnModel.addColumn(column);
		}
		return columnModel;
	}
	private String[]getColumnHeader(){
		return new String[]{"SỐ THẺ","HỌ TÊN","ĐIỀU","KHOẢN","NGÀY HL","NGÀY KT","HÌNH THỨC PHẠT","SỐ QĐKL"};
	}
	private static final Comparator INT_COMPARATOR =new Comparator(){
		public int compare(Object o1, Object o2){
			if(o1 == null && o2 == null)
				return 0;
        	else if(o1 == null)
        		return 1;
        	else if(o2 == null)
        		return -1;
			if (o1 instanceof String && o1 instanceof String)
        		return ((String)o1).compareTo((String)o2);
        	else
        		return ((Long)o1).compareTo((Long)o2);
		}
	};

	
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        //dsc.echo2app.propertyeditors.MappingPropertyEditor editor = null;
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_DIEU", l.getIDdieu());
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_KHOAN",l.getIDkhoan());
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "ID_PHAT", library.getIDhinhphat());
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "NOTE", new VniEditor());
		//getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "DATE_P", SimpleDateFormat("dd/MM/yyyy"));
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "DATE_P", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		getUIDataObjectBinder().registerCustomEditor(N_KYLUAT.class, "DATE_HL", PropertyEditors.createDateEditor(bundle.getString("LANG_DATEFORMAT")));
		
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1, EMPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_DIEU", sfdieu, ID_DIEU_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_KHOAN", sfkhoan, ID_KHOAN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("ID_PHAT", sfhp, ID_PHAT_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField2, NOTE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("QDKL", QDKL_DscField3, QDKL_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("DATE_P", DATE_P_DscDateField1, DATE_P_CaptionLabel);
        //getUIDataObjectBinder().addBindMap("DATE_HL", DATE_HL_DscDateField2, DATE_HL_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_P", dscngayBD, DATE_P_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DATE_HL", dscngayKT, DATE_HL_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
		
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		//N_KYLUAT data = (N_KYLUAT) getDataObject();
		EMPSN_DscField1.requestFocus();
		hoten.setText("");
		nhom.setText("");
		sfdieu.setSelectedIndex(0);
		sfkhoan.setSelectedIndex(0);
		sfdonvi.setSelectedIndex(0);
		sfhp.setSelectedIndex(0);
		
		
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_KYLUAT data = (N_KYLUAT) getDataObject();
		if(data.getNOTE()!=null){
			data.setNOTE(data.getNOTE().toUpperCase());
		}
		
		
		if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
		    autoPrimaryKeyValue();
		}
		
		//使用者可在以下區域做儲存資料物件至DataObjectSet之前處理
		//<儲存前處理>
	}
	
	public void load_qdkl(){
		String fact,year,f1,f2,f12,sql,so_qdkl_old,nam_hientai,stt,nam_old,so_qdkl,sql_s;
		Integer stt2;
		con=Application.getApp().getConnection();
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		DSPB02 u = udao.findById(getProgram().getLoginInfo().getUserID());
		sql="select A.USER_MANAGE_ID,substr(to_char(sysdate,'dd/mm/yyyy'),7,5) as namhientai " +
				"from n_employee a,DUAL C where  a.user_manage_id in " +
				"(SELECT ma_QL FROM n_user_limit WHERE ma_user='"+u.getPB_USERNO()+"')and empsn='"+EMPSN_DscField1.getText()+"'";
		
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()){
				fact=rs.getString(1);
				year=rs.getString(2);
				f1=fact.substring(0,1);
				f2=fact.substring(fact.length()-1, fact.length());
				f12=f1+f2;
				stt2=0;
			if(fact.equals("FVB")){
				sql_s="SELECT 'FS'||max(to_number(substr(q.qdkl,3,length(q.qdkl)))) as qdkl_old,TO_CHAR(SYSDATE,'YYYY') AS NAM FROM N_kyluat q,DUAL where q.qdkl like 'FS%'";
			}
			else if(fact.equals("KDA")){
				sql_s="SELECT 'KD'||max(to_number(substr(q.qdkl,3,length(q.qdkl)))) as qdkl_old,TO_CHAR(SYSDATE,'YYYY') AS NAM FROM N_kyluat q,DUAL where q.qdkl like 'KD%'";
			}else{
				sql_s="SELECT '"+f12+"'||max(to_number(substr(q.qdkl,3,length(q.qdkl)))) as qdkl_old,TO_CHAR(SYSDATE,'YYYY') AS NAM FROM n_kyluat q,DUAL where q.qdkl like '"+f12+"'||TO_CHAR(SYSDATE,'YYYY')||'%'";
				//sql_s="SELECT '"+f12+"'||max(to_number(substr(q.qdkl,3,length(q.qdkl)))) as qdkl_old,TO_CHAR(SYSDATE,'YYYY') AS NAM FROM N_kyluat q,DUAL where q.qdkl like '"+f12+"%'";
			}
				rs=st.executeQuery(sql_s);
				if(rs.next()){
					so_qdkl_old=rs.getString(1);
					nam_hientai=rs.getString(2);
					if(so_qdkl_old.length()>2){
						stt=so_qdkl_old.substring(6, so_qdkl_old.length());
						nam_old=so_qdkl_old.substring(2, 6);
					}else {
						stt="";
						nam_old="";
					}
					
					if(nam_hientai.equals(nam_old)){
						stt2=Integer.parseInt(stt)+1;
						if(stt2<10){
							if(fact.equals("FVB")){
								so_qdkl="FS"+nam_hientai+"0"+stt2;
							}
							else if(fact.equals("KDA")){
								so_qdkl="KD"+nam_hientai+"0"+stt2;
							}else{
								so_qdkl=f12+nam_hientai+"0"+stt2;
							}
						}else{
							if(fact.equals("FVB")){
								so_qdkl="FS"+nam_hientai+stt2;
							}
							else if(fact.equals("KDA")){
								so_qdkl="KD"+nam_hientai+stt2;
							}else{
								so_qdkl=f12+nam_hientai+stt2;
							}
						}
					}else{
						if(fact.equals("FVB")){
							so_qdkl="FS"+nam_hientai+"01";
						}
						else if(fact.equals("KDA")){
							so_qdkl="KD"+nam_hientai+"01";
						}else{
							so_qdkl=f12+nam_hientai+"01";
						}
						
					}
				}else{
					if(fact.equals("FVB")){
						so_qdkl="FS"+year+"01";
					}
					else if(fact.equals("KDA")){
						so_qdkl="KD"+year+"01";
					}else{
						so_qdkl=f12+year+"01";
					}
				}
				QDKL_DscField3.setText(so_qdkl);
			}else{
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
						"SỐ THẺ KHÔNG NẰM TRONG VÙNG QUẢN LÝ CỦA BẠN HOẶC KHÔNG CÓ DỮ LIỆU");
				EMPSN_DscField1.requestFocus();
				return;
			}
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
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	public void fill_data_table(){
		Connection con=Application.getApp().getConnection();
		Statement st=null;
		ResultSet rs=null;
		String sql="select a.empsn,a.id_dieu,a.id_khoan,a.date_p,a.date_hl,b.name_phat,a.qdkl" +
				" from n_kyluat a, n_hinhphat b where a.id_phat=b.id_phat " +
				" and a.empsn='"+EMPSN_DscField1.getText()+"'";
		try {
			st=con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
			rs=st.executeQuery(sql);
			//model_t.clear();
			//i=0;
			if(rs.next()){
				rs.beforeFirst();
				while(rs.next()){
					
					model_t.setValueAt(rs.getString(1), 0, i);//st
					model_t.setValueAt(obj.findNameEmployee(EMPSN_DscField1.getText()), 1, i);//ten
					model_t.setValueAt(rs.getString(2), 2, i);//dieu
					model_t.setValueAt(rs.getString(3), 3, i);//khoan
					model_t.setValueAt(sdf.format(rs.getDate(4)), 4, i);
					model_t.setValueAt(sdf.format(rs.getDate(5)), 5, i);
					model_t.setValueAt(rs.getString(6), 6, i);
					model_t.setValueAt(rs.getString(7), 7, i);
					Navigation.reset();
					i++;
				}
			}else{
				//reset();
			}
			
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
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
			
	}
	
	
	public void loaddata(){
		Vni2Uni c=new Vni2Uni();
		con=Application.getApp().getConnection();
		String sql="select a.empsn, b.fname||''||b.lname,c.name_fact,c.name_group," +
		" c.name_dept_name,h.name_phat,a.id_khoan,a.id_dieu,a.qdkl," +
		" a.date_hl,a.date_p,a.note  from n_kyluat a,n_department c," +
		" n_employee b,n_hinhphat h where c.id_dept=b.depsn and  " +
		" a.empsn=b.empsn and a.id_phat=h.id_phat" +
		" and b.empsn='"+EMPSN_DscField1.getText()+"'" +
		" and date_hl=(select max(date_hl) from n_kyluat where empsn='"+EMPSN_DscField1.getText()+"')";
		m_hp=(DefaultListModel) sfhp.getModel();
		m_khoan=(DefaultListModel) sfkhoan.getModel();
		m_dieu=(DefaultListModel) sfdieu.getModel();
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
			
			String x,n,dv;
			if(rs.next()){
				
				hoten.setText(c.convertToUnicode(rs.getString(2)));
				x=rs.getString(3);
				n=rs.getString(4);
				dv=c.convertToUnicode(rs.getString(5));
				//
				//sfdonvi.setSelectedIndex(0);
				nhom.setText(x+"."+n+"."+dv);
				hoten.setForeground(Color.RED);
				nhom.setForeground(Color.BLUE);
				sfhp.setSelectedIndex(m_hp.indexOf(c.convertToUnicode(rs.getString(6))));
				sfkhoan.setSelectedIndex(m_khoan.indexOf(rs.getString(7)));
				sfdieu.setSelectedIndex(m_dieu.indexOf(rs.getString(8)));
				QDKL_DscField3.setText(rs.getString(9));
				nkt=df.format(rs.getDate(10));
				dscngayKT.setText(df.format(rs.getDate(10)));
				temp=df.format(rs.getDate(10));
				dscngayBD.setText(df.format(rs.getDate(11)));
				NOTE_DscField2.setText(c.convertToUnicode(rs.getString(12)));
				EMPSN_DscField1.requestFocus();
				
				if(model_t.getRowCount()>0){
					model_t.clear();
					i=0;
				}
				fill_data_table();
		
			}else{
				
				Application.getApp().showMessageDialog(MessageDialog.TYPE_WARNING+MessageDialog.CONTROLS_OK,
						"Nhân viên này chưa bị kỷ luật.");
				
				String  h=l.getstring("N_EMPLOYEE", "EMPSN", EMPSN_DscField1.getText(), "FNAME");
				String  t=l.getstring("N_EMPLOYEE", "EMPSN", EMPSN_DscField1.getText(), "LNAME");
				String [] all=l.index_all_empsn(EMPSN_DscField1.getText());
				x=all[0];
				n=c.convertToUnicode(all[1]);
				dv=all[2];
				hoten.setText(h+" "+t);
				nhom.setText(x+"."+n+"."+dv);
				
				sfdieu.setSelectedIndex(0);
				sfhp.setSelectedIndex(0);
				sfkhoan.setSelectedIndex(0);
				
				NOTE_DscField2.setText("");
				dscngayBD.setText("");
				dscngayKT.setText("");
			}
			
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}finally{
			try {
				if(con!=null){
					con.close();
				}
				if(st!=null){
					st.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption", Application.getActive().getLocale());
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(300, Extent.PX));
		splitPane1.setSeparatorColor(Color.LIGHTGRAY);
		splitPane1.setResizable(true);
		add(splitPane1);
		ContentPane contentPane1 = new ContentPane();
		splitPane1.add(contentPane1);
        rootLayout = new Grid();
        rootLayout.setWidth(new Extent(100, Extent.PERCENT));
        rootLayout.setSize(2);
        contentPane1.add(rootLayout);
        EMPSN_CaptionLabel = new nextapp.echo2.app.Label();
        EMPSN_CaptionLabel.setText("N_KYLUAT.EMPSN");
        rootLayout.add(EMPSN_CaptionLabel);
        EMPSN_DscField1 = new dsc.echo2app.component.DscField();
        EMPSN_DscField1.setMaximumLength(8);
        //EMPSN_DscField1.setId("EMPSN_DscField1");
        EMPSN_DscField1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if((EMPSN_DscField1.getText()==null)|| (EMPSN_DscField1.getText().toString().equals(""))){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
							"Vui lòng nhập số thẻ");
					EMPSN_DscField1.requestFocus();
						return;
				}
				if(!EMPSN_DscField1.getText().matches("[0-9]{8}")){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
							"Số thẻ không hợp lệ...");
					EMPSN_DscField1.requestFocus();
					return;
				}
				boolean ok=l.check_QL(EMPSN_DscField1.getText(),"", "", "", "",u.getPB_USERNO() );
				if(ok==false){
					return;
				}else{
					load_qdkl();
					loaddata();
					
				}
			}
        	
        });
        
        
        rootLayout.add(EMPSN_DscField1);
        a=new Label();
        a.setText("");
        rootLayout.add(a);
        hoten=new Label();
        hoten.setText("");
        rootLayout.add(hoten);
        xuong=new Label();
        xuong.setText("");
        rootLayout.add(xuong);
       
        nhom=new Label();
        nhom.setText("");
        rootLayout.add(nhom);         
        donvi=new Label();
        donvi.setText("");
        //rootLayout.add(donvi);
        sfdonvi=new SelectFieldEx();
        sfdonvi.setWidth(new Extent(148));
        sfdonvi.setEnabled(false);
        //rootLayout.add(sfdonvi);
        
        ID_DIEU_CaptionLabel = new nextapp.echo2.app.Label();
        ID_DIEU_CaptionLabel.setText("N_KYLUAT.ID_DIEU");
        rootLayout.add(ID_DIEU_CaptionLabel);
        sfdieu = new SelectFieldEx();
        sfdieu.setWidth(new Extent(148));
        //ID_DIEU_SelectField1.setId("ID_DIEU_SelectField1");
        sfdieu.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				/*List khoan=l.any("n_khoan", "id_dieu", sfdieu.getSelectedItem().toString(), "id_khoan", "ID_KHOAN");
				//if(m_khoan.size()!=null){
				sfkhoan.setSelectedIndex(0);	
				m_khoan=(DefaultListModel) sfkhoan.getModel();
				m_khoan.removeAll();

				if(khoan.size()==0){
					m_khoan.add("");
					sfkhoan.setSelectedIndex(0);
					return;
				}else{
					for (int i = 0; i < khoan.size(); i++) {
						
						m_khoan.add(khoan.get(i));
					}
				}*/
				//SelectItem item=(SelectItem) sfdieu.getSelectedItem();
				ListBinder.bindSelectField(sfkhoan, l.getIDKHOAN(sfdieu.getSelectedItem()), true);
				sfkhoan.setSelectedIndex(0);
				
				
			}
        	
        });
        rootLayout.add(sfdieu);
        ID_KHOAN_CaptionLabel = new nextapp.echo2.app.Label();
        ID_KHOAN_CaptionLabel.setText("N_KYLUAT.ID_KHOAN");
        rootLayout.add(ID_KHOAN_CaptionLabel);
        sfkhoan = new SelectFieldEx();
        sfkhoan.setWidth(new Extent(148));
        //ID_KHOAN_SelectField2.setId("ID_KHOAN_SelectField2");
        rootLayout.add(sfkhoan);
        ID_PHAT_CaptionLabel = new nextapp.echo2.app.Label();
        ID_PHAT_CaptionLabel.setText("N_KYLUAT.ID_PHAT");
        rootLayout.add(ID_PHAT_CaptionLabel);
        sfhp = new SelectFieldEx();
        sfhp.setWidth(new Extent(148));
        
        //ID_PHAT_SelectField3.setId("ID_PHAT_SelectField3");
        rootLayout.add(sfhp);
        NOTE_CaptionLabel = new nextapp.echo2.app.Label();
        NOTE_CaptionLabel.setText("N_KYLUAT.NOTE");
        rootLayout.add(NOTE_CaptionLabel);
        NOTE_DscField2 = new dsc.echo2app.component.DscField();
        NOTE_DscField2.setId("NOTE_DscField2");
        rootLayout.add(NOTE_DscField2);
       
        DATE_P_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_P_CaptionLabel.setText("N_KYLUAT.DATE_P");
        rootLayout.add(DATE_P_CaptionLabel);
        //DATE_P_DscDateField1 = new dsc.echo2app.component.DscDateField();
       // DATE_P_DscDateField1 = new DscDateField();
        //DATE_P_DscDateField1.setId("DATE_P_DscDateField1");
        //DATE_P_DscDateField1.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
        //DATE_P_DscDateField1.getDateChooser().setLocale(new Locale("en"));
        //DATE_P_DscDateField1.addPropertyChangeListener(new PropertyChangeListener() {
			//public void propertyChange(PropertyChangeEvent e) {
				
			//}
		//});
        dscngayBD=new DscField();
        dscngayBD.setInputType(DscField.INPUT_TYPE_NUMERIC);
        dscngayBD.setToolTipText("Nhập vào chuổi 8 ký tự số theo định dạng ddmmyyyy");
        dscngayBD.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
	
			public void focusLost(FocusEvent e) {
				boolean isvalid =l.check_input_day(dscngayBD.getText());
				if(isvalid==false){
					dscngayBD.requestFocus();
					return;
				}else{
					dscngayBD.setInputType(DscField.INPUT_TYPE_TEXT);
					String daychage=l.changeday(dscngayBD.getText());
					dscngayBD.setText(daychage);
				}
			}
		});
        if(dataMode==IProgram.DATAMODE_NEW){
        dscngayBD.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(dscngayBD.getText().length()>=8){
				if(sfhp.getSelectedIndex()!=-1){
					if((sfhp.getSelectedItem().toString().equals("CÁCH CHỨC")) || (sfhp.getSelectedItem().toString().equals("SA THAI"))){
						//dscngayKT.setEnabled(true);
						dscngayKT.setText(dscngayBD.getText().substring(0, 6)+"2100");
						dscngayKT.setEnabled(false);
					}
					/*else{
						dscngayKT.setEnabled(true);
					}*/
					
				}
			}
		}
	});
        }
        rootLayout.add(dscngayBD);
        
        
        DATE_HL_CaptionLabel = new nextapp.echo2.app.Label();
        DATE_HL_CaptionLabel.setText("N_KYLUAT.DATE_HL");
        rootLayout.add(DATE_HL_CaptionLabel);
        //DATE_HL_DscDateField2 = new dsc.echo2app.component.DscDateField();
        //DATE_HL_DscDateField2.setId("DATE_HL_DscDateField2");
        //DATE_HL_DscDateField2.setDateFormat(new SimpleDateFormat(bundle.getString("LANG_DATEFORMAT")));
        //DATE_HL_DscDateField2.getDateChooser().setLocale(new Locale("en"));
        dscngayKT=new DscField();
        dscngayKT.setInputType(DscField.INPUT_TYPE_NUMERIC);
        dscngayBD.setToolTipText("Nhập vào chuổi 8 ký tự số theo định dạng ddmmyyyy-->Enter");
      
        dscngayKT.addActionListener(new ActionListener() {
			@SuppressWarnings({ "serial", "unused" })
			public void actionPerformed(ActionEvent e) {
				boolean isvalid =l.check_input_day(dscngayKT.getText());
				if(isvalid==false){
					dscngayKT.requestFocus();
					dscngayKT.setText("");
					ac_cmd=false;
					return;
				}else{
					dscngayKT.setInputType(DscField.INPUT_TYPE_TEXT);
					String daychage=l.changeday(dscngayKT.getText());
					dscngayKT.setText(daychage);
					try {
						if(sdf.parse(temp).compareTo(sdf.parse(dscngayKT.getText()))<0){
							load_qdkl();
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					ac_cmd=true;
		
				}
				
				
			}
		});
        rootLayout.add(dscngayKT);
        QDKL_CaptionLabel = new nextapp.echo2.app.Label();
        QDKL_CaptionLabel.setText("N_KYLUAT.QDKL");
        rootLayout.add(QDKL_CaptionLabel);
        QDKL_DscField3 = new dsc.echo2app.component.DscField();
        QDKL_DscField3.setId("QDKL_DscField3");
        QDKL_DscField3.setEnabled(false);
        rootLayout.add(QDKL_DscField3);
        
        
        SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(25, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorColor(Color.BLUE);
		splitPane2.setResizable(true);
		splitPane1.add(splitPane2);
		Navigation = new DscPageNavigation();
		Navigation.setRowsPerPage(20);
		Navigation.setBackground(Color.LIGHTGRAY);
		splitPane2.add(Navigation);
		Table = new DscPageableSortableTable();
		Table.setStyleName("Table.DscPageableSortableTable");
		Table.setAutoCreateColumnsFromModel(false);
		Table.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//cell_click();
			}
		});
		splitPane2.add(Table);
	}

}
