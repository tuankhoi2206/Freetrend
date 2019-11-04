package ds.program.fvhr.ui.an;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.poi.hssf.record.formula.TblPtg;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SelectField;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import echopointng.table.SortableTableColumn;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.FvLogger;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.library;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumnModel;
import nextapp.echo2.app.Table;
import dsc.echo2app.component.DscPageNavigation;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.component.table.PageableSortableTableModel;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.Border;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;

public class luu_cnth extends WindowPane {
	private nLabourMProgram _father_form;
	private ResourceBundle resourceBundle;
	private DscDateField dsc_tungay;
	private DscDateField dsc_denngay;
	private SelectField sf_nhom;
	private SelectField sf_donvi;
	private SplitPane splitPane2;
	
	private DscPageableSortableTable Table1;
	DefaultListModel m_xuong,m_nhom,m_dovi;
	PageableSortableTableModel model;
	DSPB02 u;
	library l=new library();
	Vni2Uni c=new Vni2Uni();
	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	
	int cc=0;
	String chuoi="";
	int ischeck=0;
	int pos1=-1;
	int pos2=-1;
	int pos3=-1;
	String mang[]=new String[20];
	private Label lbl_info;

	/**
	 * Creates a new <code>luu_cnth</code>.
	 * @param __main 
	 */
	public luu_cnth(nLabourMProgram _main) {
		super();
		_father_form	= _main;

		// Add design-time configured components.
		initComponents();
		
		lbl_info=new Label();
		lbl_info.setText("");
		lbl_info.setForeground(Color.RED);
		RowLayoutData infoLayout1 = new RowLayoutData();
		infoLayout1.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		infoLayout1.setWidth(new Extent(200, Extent.PERCENT));
		lbl_info.setLayoutData(infoLayout1);
		
		Navigation1.setBackground(new Color(0xafd8d8));
		
		
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(Application.getApp().getLoginInfo().getUserID());
		setdefault();
	}
	private void cell_click(ActionEvent e) {
		//TODO Implement.
		int page=model.getCurrentPage();
		int cell=Table1.getSelectionModel().getMinSelectedIndex();
		
		/*if(radoption.isSelected()){
			if(model.getValueAt(8,cell).equals("Y")){
				model.setValueAt("", 8, (15*page)+cell);
			}else{
				model.setValueAt("Y", 8, (15*page)+cell);
			}
		}
		else{
			model.setValueAt("", 8, (15*page)+cell);
		}*/
		
	}
	
	
	public void setdefault(){
		
		TableColumnModel columnModel=loadColumnModel();
		Table1.setColumnModel(columnModel);
		PageableSortableTableModel model = new PageableSortableTableModel(columnModel);
		model.setSelectionModel(Table1.getSelectionModel());
		model.setRowsPerPage(25);
		Table1.setModel(model);
		Table1.setSelectionEnabled(true);
		Navigation1.setTable(Table1);
		
		this.setHeight(new Extent(Application.getApp().getScreenHeight()));
		this.setWidth(new Extent(Application.getApp().getScreenWidth()));
		this.setTitle("Lưu thử việc");
		
		
				
		l.setdate(dsc_tungay);
		l.setdate(dsc_denngay);
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		
	}
	
	
	private DscPageNavigation Navigation1;
	private Label lbsum;
	private SelectField sf_xuong;
	private Button btn_tk;
	private Button btn_save;
	private Button btn_reset;
	private DscField dsc_sothe;
	private CheckBox chkSearchList;
	private TableColumnModel loadColumnModel()
	{
		TableColumnModel columnModel =new DefaultTableColumnModel();
		SortableTableColumn column1 =null; 
		for (int i=0;i<10;i++){
		column1=new SortableTableColumn(i);
		column1.setHeaderRenderer(Table1.getDefaultHeaderRenderer());
		column1.setComparator(l.INT_COMPARATOR);
		column1.setModelIndex(i);
		
		column1.setHeaderValue(getColumnHeader()[i]);
		columnModel.addColumn(column1);
		if(i==0){
			column1.setWidth(new Extent(10,Extent.PX));
		}
		if(i==1)
		{
		column1.setHeaderRenderer(new TableCellRenderer() {
			
			@Override
			public nextapp.echo2.app.Component getTableCellRendererComponent(
					Table table, Object value, int column, int row) {
				// TODO Auto-generated method stub
				TableLayoutData lay=new TableLayoutData();
				lay.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
				final CheckBox chk = new CheckBox();
				
				if(column==1){
					
					chk.setLayoutData(lay);
					chk.setText("Y/N");
					chk.setForeground(Color.WHITE);
					chk.setBackground(new Color(0x0080C0));
					chk.setStatePosition(new Alignment(Alignment.DEFAULT, Alignment.BOTTOM));
					chk.setStateAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					
					//chk.setInsets(new Insets(0,5,0,5));
					chk.setId((String) value);	
					Navigation1.add(lbl_info);
					if(cc==1)
					{chk.setSelected(true);}
					chk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							// TODO Auto-generated method stub
							if(Table1.getModel().getRowCount()>0){
							if(chk.isSelected())
							{
								cc=1;
								
									for (int j = 0; j < model.getRowCount(); j++) 
									{
										CheckBox a=(CheckBox) Table1.getCellComponent(1, j);
										a.setSelected(true);										
									}							
									int t = model.getTotalRows();
									for(int u=0;u<t;u++)
									{
										chuoi+=u+"-";
									}
								}
							else
							{
								for (int j = 0; j < model.getRowCount(); j++) 
								{
								CheckBox a=(CheckBox) Table1.getCellComponent(1, j);
								a.setSelected(false);
								chuoi="";
								cc=0;
							}
						}
					}
				}
			});
					
					return chk;
				}
				
				Label lbl = new Label();
				
				//lbl.setText(column1.getHeaderValue().toString());
				lbl.setForeground(Color.WHITE);
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0x0080CC));
				//layout.setInsets(new Insets(3));
				lbl.setLayoutData(layout);
				return lbl;
				
			}
		});
		
//check box
		Table1.setDefaultRenderer(Object.class,new TableCellRenderer() {
			
			
			@Override
			public nextapp.echo2.app.Component getTableCellRendererComponent(
					Table table, Object value, int column, int row) {
				// TODO Auto-generated method stub
				
				ischeck=0;
				pos1=-1;
				pos2=-1;
				pos3=-1;
				
				TableLayoutData lo=new TableLayoutData();
				final CheckBox chk = new CheckBox();
				if(column==1)
				{
					if(chuoi.length()>0)
					{
						if(timkiem(Integer.parseInt(value.toString()),chuoi)==true)
						{
							chk.setSelected(true);
						}
						else
						{
							chk.setSelected(false);
						}
					}
					else
					{
						chk.setId(""+value);
					}
					
					lo.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					chk.setLayoutData(lo);
					chk.setId(""+value);
					chk.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							if(chk.isSelected())
							{							
								chuoi+=chk.getId()+"-";
								mang=chuoi.split("-");
								//System.out.print(oo+"\n");
								//System.out.print(mang.length);
							}
							else
							{
								mang=chuoi.split("-");
								mang=remove(mang,chk.getId());
								
								/*System.err.println("id:"+chk.getId());
								System.out.println(chuoi);
								
								System.out.print("Sau xoa "+mang.length);*/
								chuoi="";
								for(int i=0;i<mang.length;i++)
								{
									chuoi+=mang[i]+"-";
								}
								//System.out.print("mang ok "+chuoi);
							}
						}
					});
					return chk;
				}
				Label lbl = new Label();
				if(value!=null)
				{
					lbl.setText(value.toString());						
				}
				TableLayoutData layout = new TableLayoutData();
				if(row%2==0)
				{
					
					layout.setBackground(new Color(0xffffdd));
					lbl.setLayoutData(layout);
				}
				if(column==0){
					TableLayoutData tld=new TableLayoutData();
					tld.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					tld.setBackground(Color.LIGHTGRAY);
					lbl.setLayoutData(tld);
				}
				return lbl;
				
			}
		});
		}
		
	}
		
		return columnModel;
}
	
	
	private void searchEmpsnInTable() {
		
		if (!dsc_sothe.getText().equals("")) {

			if (chkSearchList.isSelected()) {
				String empsn = dsc_sothe.getText().trim();
				int page=model.getTotalPages();
				int index=0;
				while(index<page){
					model.setCurrentPage(index);
					for (int i = 0; i < model.getRowCount(); i++) {
						if (empsn
								.equals(model.getValueAt(2, i).toString())) {
							
							
							// có trong danh sách
							ListSelectionModel listSelectionModel = Table1
									.getSelectionModel();
							listSelectionModel.setSelectedIndex(i, true);
							Navigation1.reset();
							return;
						}
					}
					index++;
				}
				l.ShowMessageInfo("không tìm thấy dữ liệu.");
				model.setCurrentPage(0);

			}else{
				if(Table1.getModel().getRowCount()>0){
					model.setCurrentPage(0);
				}
				btn_tk.doAction();
			}

		} else {
			l.ShowMessageError("Số thẻ nhập vào không hợp lệ.");
		}
	}
	
	public boolean timkiem(int a,String tt)
	{	
		boolean f =false;		
		mang=tt.split("-");
		for(int i=0;i<mang.length;i++)
		{
			if(Integer.parseInt(mang[i].toString())==a)
			{				
				f=true;
				break;
			}			
		}
		return f;
	}
	public String[] remove(String[]arr,String para)
	{
		int position=0;
		String[]arr_temp=new String[arr.length-1];
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].toString().compareTo(para)==0)
			{
				position=i;
				break;
			}
		}		
		
		if(position==0)
		{
			arr_temp=new String[arr.length-1];
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i+1];
			}
			arr=new String[arr_temp.length];
			for(int i=0;i<arr_temp.length;i++)
			{
				arr[i]=arr_temp[i];
			}								
		}			
		if((position==arr.length-1)&&(arr.length!=1))
		{
			arr_temp=new String[arr.length-1];
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i];
			}				
			arr=new String[arr_temp.length];
			for(int i=0;i<arr.length;i++)
			{
				arr[i]=arr_temp[i];
			}			
		}
				
		if((position>0)&&(position<arr.length-1)&&(arr.length!=2))
		{
			arr_temp=new String[arr.length-1];
			for(int i=position;i<arr.length-1;i++)
			{
				arr[i]=arr[i+1];
			}
			for(int i=0;i<arr.length-1;i++)
			{
				arr_temp[i]=arr[i];
			}
			arr=new String[arr_temp.length];
			for(int i=0;i<arr.length;i++)
			{
				arr[i]=arr_temp[i];
			}			
		}
		return arr_temp;
	}
	
	
	private String[]getColumnHeader(){
		return new String[]{"STT","YES/NO","SỐ THẺ","HỌ TÊN","CHỨC VỤ", "ĐƠN VỊ","XƯỞNG","NHÓM",
				"NGÀY NX","TRÌNH ĐỘ"};
				
	}
	public void loadData(){
		Connection con=Application.getApp().getConnection();
		Statement st=null;
		ResultSet rs=null;
	
		String temp=l.whereclause_id_dept(dsc_sothe.getText(), sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem(), ListBinder.get(sf_donvi));
		String sql=" SELECT  a.empsn ,a.fname||' '||a.lname as fname,a.position,dt.name_dept," +
				" dt.name_fact,dt.name_group,a.date_hired,c.e_group  " +
				" FROM n_Employee a, n_newworker_test b,n_User_Limit f,n_department dt," +
				" n_education_new c "+temp+
				" and a.education=c.id_education  and a.empsn=b.empsn and b.is_test='0'" +
				" and a.User_Manage_Id=f.ma_ql AND f.ma_user='"+u.getPB_USERNO()+"' and a.date_hired " +
				" between to_date('"+dsc_tungay.getText()+"','dd/mm/yyyy') " +
				" and to_date('"+dsc_denngay.getText()+"','dd/mm/yyyy')" +
						" order by dt.name_group,a.empsn";
		model=(PageableSortableTableModel) Table1.getModel();
		model.clear();
		int i=0;
		try {
			st=con.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
			rs=st.executeQuery(sql);
			if(rs.next()==false){
				model.clear();
				//lbsum.setText("");
				
				
				Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK,
						"Không có dữ liệu...");
				lbl_info.setText("");
				return;
			}else{
				rs.last();
				//lbsum.setText("Có "+rs.getRow()+" CNV thử việc");
				lbl_info.setText("Có "+rs.getRow()+" CNV thử việc");
				//lbsum.setForeground(new Color(255,0,0));
				
				
				rs.beforeFirst();
				while (rs.next()) {
					model.setValueAt(i+1,0, i);
					model.setValueAt(i,1, i);
					model.setValueAt(rs.getString(1), 2, i);
					model.setValueAt(c.convertToUnicode(rs.getString(2)), 3, i);					
					model.setValueAt(c.convertToUnicode(rs.getString(3)), 4, i);
					model.setValueAt(c.convertToUnicode(rs.getString(4)), 5, i);
					model.setValueAt(rs.getString(5), 6, i);
					model.setValueAt(c.convertToUnicode(rs.getString(6)), 7, i);
					model.setValueAt(df.format(rs.getDate(7)), 8, i);
					model.setValueAt(c.convertToUnicode(rs.getString(8)), 9, i);
					
					i++;
					Navigation1.reset();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private void tim_ac(ActionEvent e) {
		//TODO Implement.
		if(sf_xuong.getSelectedIndex()==-1 && dsc_sothe.getText().equals("")){
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
					"Chưa chọn thông tin...");
			return;
		}else{
			loadData();
		}
	}

	private void xuong_ch(ActionEvent e) {
		//TODO Implement.
		dsc_sothe.setText("");
		SelectItem item = (SelectItem) sf_xuong.getSelectedItem();
		
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);		
	}

	private void nhom_ch(ActionEvent e) {
		//TODO Implement.
		SelectItem item = (SelectItem) sf_nhom.getSelectedItem();
		
		//ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(item.getValue()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDept(sf_xuong.getSelectedItem().toString(), item.getValue()), true);
	}
	private void cell(ActionEvent e) {
		//TODO Implement.
	}
	private void radop(ActionEvent e) {
		//TODO Implement.
		int count=model.getTotalRows();
		
	}
	private void save(ActionEvent e) {
		//TODO Implement.
			boolean save_ok=false;
			String mang[]=chuoi.split("-");
			int record=0;
			if(chuoi.equals("")){
				l.ShowMessageInfo("Không có thông tin.");
				return;
			}
			for (int i = 0; i < mang.length; i++) {
					String st=model.getValueAt(2,Integer.parseInt(mang[i].toString())).toString();
					save_ok=l.luu_tv(st, "1");
					if(save_ok){
						record++;
						String note_input="Lưu thử việc đạt số thẻ "+st;
						l.Insert_N_Action_Daily(u.getPB_USERNO(), "N_NEWWORKER_TEST", "INSERT", st, c.convertToVNI(note_input));
					}
				}
			
			cc=0;
			loadData();
			chuoi="";
			l.ShowMessageInfo("Đã lưu thử việc "+record+" số thẻ.");
			
	}
	private void ac_reset(ActionEvent e) {
		//TODO Implement.
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
		
		model=(PageableSortableTableModel) Table1.getModel();
		model.clear();		
		Navigation1.reset();
		cc=0;
		lbl_info.setText("");
		
	}
	
	private void f_empsn(FocusEvent e) {
		//TODO Implement.
		ListBinder.bindSelectField(sf_xuong, FVGenericInfo.getFactories(), true);
		ListBinder.bindSelectField(sf_nhom, FVGenericInfo.getGroup(sf_xuong.getSelectedItem()), true);
		ListBinder.bindSelectField(sf_donvi, FVGenericInfo.getDeptName(sf_xuong.getSelectedItem(), sf_nhom.getSelectedItem()), true);
		
	}
	private void ac_empsn(ActionEvent e) {
		//TODO Implement.
		if(chkSearchList.isSelected()){
			if(Table1.getModel().getRowCount()==0){
				l.ShowMessageInfo("Chưa có danh sách để tìm kiếm.");
				return;
			}
		}
		searchEmpsnInTable();
	}
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		ContentPane contentPane1 = new ContentPane();
		add(contentPane1);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(250, Extent.PX));
		splitPane1.setBackground(new Color(0xeef7f7));
		splitPane1.setSeparatorWidth(new Extent(1, Extent.PX));
		splitPane1.setResizable(true);
		contentPane1.add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(2,
				Extent.PX), new Extent(0, Extent.PX), new Extent(2, Extent.PX)));
		grid1.setSize(2);
		splitPane1.add(grid1);
		Label label1 = new Label();
		label1.setText("NX từ ngày");
		grid1.add(label1);
		dsc_tungay = new DscDateField();
		grid1.add(dsc_tungay);
		Label label2 = new Label();
		label2.setText("đến ngày");
		grid1.add(label2);
		dsc_denngay = new DscDateField();
		grid1.add(dsc_denngay);
		Label label9 = new Label();
		label9.setText("Số thẻ");
		grid1.add(label9);
		dsc_sothe = new DscField();
		dsc_sothe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac_empsn(e);
			}
		});
		dsc_sothe.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				f_empsn(e);
			}
	
			public void focusLost(FocusEvent e) {
			}
		});
		grid1.add(dsc_sothe);
		Label label10 = new Label();
		grid1.add(label10);
		chkSearchList = new CheckBox();
		chkSearchList.setText("Tìm trong danh sách");
		grid1.add(chkSearchList);
		Label label3 = new Label();
		label3.setText("Xưởng");
		grid1.add(label3);
		sf_xuong = new SelectField();
		sf_xuong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xuong_ch(e);
			}
		});
		grid1.add(sf_xuong);
		Label label4 = new Label();
		label4.setText("Nhóm");
		grid1.add(label4);
		sf_nhom = new SelectField();
		sf_nhom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nhom_ch(e);
			}
		});
		grid1.add(sf_nhom);
		Label label5 = new Label();
		label5.setText("Đơn vị");
		grid1.add(label5);
		sf_donvi = new SelectField();
		grid1.add(sf_donvi);
		Label label6 = new Label();
		grid1.add(label6);
		btn_tk = new Button();
		btn_tk.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_tk.setText("Tìm kiếm thông tin");
		btn_tk.setHeight(new Extent(20, Extent.PX));
		btn_tk.setPressedBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x00ff80), Border.STYLE_INSET));
		btn_tk.setRolloverBorder(new Border(new Extent(1, Extent.PX),
				new Color(0x00ff80), Border.STYLE_OUTSET));
		btn_tk.setWidth(new Extent(160, Extent.PX));
		btn_tk.setBackground(new Color(0x0080ff));
		btn_tk.setPressedEnabled(true);
		btn_tk.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_tk.setForeground(Color.WHITE);
		btn_tk.setRolloverEnabled(true);
		btn_tk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tim_ac(e);
			}
		});
		grid1.add(btn_tk);
		Label label8 = new Label();
		grid1.add(label8);
		Row row3 = new Row();
		grid1.add(row3);
		btn_save = new Button();
		btn_save.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btn_save.setText("Save");
		btn_save.setHeight(new Extent(20, Extent.PX));
		btn_save.setPressedBorder(new Border(new Extent(1, Extent.PX),
				new Color(0x00ff80), Border.STYLE_INSET));
		btn_save.setRolloverBorder(new Border(new Extent(1, Extent.PX),
				new Color(0x00ff80), Border.STYLE_OUTSET));
		btn_save.setBackground(new Color(0x0080ff));
		btn_save.setPressedEnabled(true);
		btn_save.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_save.setRolloverEnabled(true);
		btn_save.setForeground(Color.WHITE);
		RowLayoutData btn_saveLayoutData = new RowLayoutData();
		btn_saveLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(1, Extent.PX), new Extent(
						0, Extent.PX)));
		btn_saveLayoutData.setWidth(new Extent(80, Extent.PX));
		btn_save.setLayoutData(btn_saveLayoutData);
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save(e);
			}
		});
		row3.add(btn_save);
		btn_reset = new Button();
		btn_reset.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btn_reset.setText("Reset");
		btn_reset.setHeight(new Extent(20, Extent.PX));
		btn_reset.setPressedBorder(new Border(new Extent(1, Extent.PX),
				new Color(0x00ff80), Border.STYLE_INSET));
		btn_reset.setRolloverBorder(new Border(new Extent(1, Extent.PX),
				new Color(0x00ff80), Border.STYLE_OUTSET));
		btn_reset.setBackground(new Color(0x0080ff));
		btn_reset.setPressedEnabled(true);
		btn_reset.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x8080c0), Border.STYLE_GROOVE));
		btn_reset.setRolloverEnabled(true);
		btn_reset.setForeground(Color.WHITE);
		RowLayoutData btn_resetLayoutData = new RowLayoutData();
		btn_resetLayoutData.setWidth(new Extent(80, Extent.PX));
		btn_reset.setLayoutData(btn_resetLayoutData);
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac_reset(e);
			}
		});
		row3.add(btn_reset);
		Label label7 = new Label();
		grid1.add(label7);
		lbsum = new Label();
		grid1.add(lbsum);
		splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(22, Extent.PX));
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setResizable(false);
		splitPane1.add(splitPane2);
		Navigation1 = new DscPageNavigation();
		Navigation1.setBackground(new Color(0xc0c0c0));
		splitPane2.add(Navigation1);
		Table1 = new DscPageableSortableTable();
		Table1.setStyleName("Table.DscPageableSortableTable");
		Table1.setInsets(new Insets(new Extent(0, Extent.PX)));
		Table1.setAutoCreateColumnsFromModel(false);
		SplitPaneLayoutData Table1LayoutData = new SplitPaneLayoutData();
		Table1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX)));
		Table1.setLayoutData(Table1LayoutData);
		Table1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cell_click(e);
			}
		});
		splitPane2.add(Table1);
	}
}
