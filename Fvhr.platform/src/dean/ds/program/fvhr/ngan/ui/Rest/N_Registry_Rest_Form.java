package ds.program.fvhr.ngan.ui.Rest;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import fv.util.Vni2Uni;
import ds.program.fvhr.domain.N_REST;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.N_REST_PBAN_NKL;
import ds.program.fvhr.domain.pk.N_RESTPk;
import ds.program.fvhr.domain.pk.N_REST_DETAILPk;
import ds.program.fvhr.domain.pk.N_REST_PBAN_NKLPk;
import ds.program.fvhr.ngan.ui.DeptUserControl_FactDetail;
import ds.program.fvhr.son.ui.ObjError;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportService;
import dsc.echo2app.component.DscField;
import dsc.echo2app.task.ATask;
import echopointng.DateField;
import echopointng.GroupBox;
import echopointng.TabbedPane;
import echopointng.tabbedpane.DefaultTabModel;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import dsc.echo2app.ReportFileManager;
import dsc.util.function.UUID;

public class N_Registry_Rest_Form extends WindowPane {

	private DeptUserControl_FactDetail	dept_ctrl;
	
	ArrayList<list_emp_rest_error> list_all;
	HSSFWorkbook wb;
	Label   errLabel;
	
	private TabbedPane			tab_reason;
	
	private ButtonGroup			group_huongluong;
	private ButtonGroup			group_phepkhac;
	
	private RadioButton			radio_tet_am,radio_cty,radio_cnv,radio_ton,radio_no_1,radio_no_2;
	private RadioButton			radio_cuoi,radio_tang,radio_le,radio_hh,radio_tnld,radio_coluong;
	private RadioButton			radio_ban,radio_bu,radio_khongluong,radio_khangcong,radio_CN_moi,radio_benh,radio_ds;
	private RadioButton			radio_gio_nghi_trua1,radio_gio_nghi_trua2,radio_gio_nghi_trua3,radio_gio_nghi_trua4,radio_gio_nghi_trua5,radio_gio_nghi_trua6;
	private RadioButton			radio_ngan_ngay,radio_dai_ngay;
	private RadioButton 		rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac;
	
//	private TextField			txt_time_per_day;
	private TextField			txt_tu_gio;
	private TextField			txt_den_gio;
	private SelectField			sf_note_ban;
	private SelectField			sf_note_benh;
	private GroupBox		    gb_gio_nghi_trua;	
	private ButtonGroup			bt_group_nghi_trua;
	private GroupBox		    gb_ngan_dai_ngay;	
	private ButtonGroup			bt_group_ngan_dai_ngay;
	private DateField			dateF_to_date;
	private DscField 			tf_date;
	private ListBox				list_error;
	
	
	public long numRestCNV		= 0;
	public long numRestCty 		= 0;
	public long numRestTet		= 0;
	public long numRestTon		= 0;
	
	String rest_kind 	= "";
	String rest_type	= "";
	String rest_sal		= "";
	String year			= "";
	String note			= "";
	Date date_from	= null;
	Date date_to	= null;
//	String str_date  = "";
	String mid_time_in  = "";
	String den_gio 		= "";
	String note_ngan_dai= "";
	String ghichu	    = "";
	
	private SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy");
	String user_up = Application.getApp().getLoginInfo().getUserID();
	String ma_user = "";
	
	 boolean flag = false;
	 String ca    = "";
	 String note_pn_error = "";
	 String note_DS_NS_error = "";
	 
	  private Label hoten ;
	  private Label tendv;
	  private Label madv;
	  String kt_ngay_CN = "" ;
	  
	  Row   row_tieude_PN,row_hienthi_PN,row_hienthi_PTet,row_hienthi_PCty,row_hienthi_PCNV,row_hienthi_pTon;
	  Label loaiphep,tongngay,daSD,conlai;
	  Label ten_pn,ten_pTet,ten_pCty,ten_pCNV,ten_pTon;
	  Label pn,p_tet,p_cty,p_cnv,p_ton;
	  Label pn_dsd,p_tet_dsd,p_cty_dsd,p_cnv_dsd,p_ton_dsd;
	  Label pn_conlai,p_tet_conlai,p_cty_conlai,p_cnv_conlai,p_ton_conlai;
	  
	  public long pno = 0;
	
		
	
	/**
	 * Creates a new <code>N_Registry_Rest</code>.
	 * @param _main 
	 */
	
	private N_Emp_Rest_01MProgram _father_form;
	
	public N_Registry_Rest_Form(N_Emp_Rest_01MProgram _main) {
		super();
		_father_form	= _main;
		// Add design-time configured components.
		
		initComponents();
	}

	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	
	private void setF_Refresh()
	{
		row_tieude_PN.setVisible(false);
		row_hienthi_PCNV.setVisible(false);
		row_hienthi_PCty.setVisible(false);
		row_hienthi_PN.setVisible(false);
		row_hienthi_PTet.setVisible(false);
		row_hienthi_pTon.setVisible(false);
		/*hoten.setVisible(false);
		tendv.setVisible(false);
		madv.setVisible(false);*/
		hoten.setText("");
		tendv.setText("");
		madv.setText("");
		setcolor_enable();
	}
	private void hienThiPN(String sothe,Date date_)
	{   
		String sql_tet = "";
		String sql_cty = "";
		String sql_cnv = "";
		String sql_ton = "";
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		
		loaiphep.setVisible(true);
		tongngay.setVisible(true);
		daSD.setVisible(true);
		conlai.setVisible(true);
		ten_pn.setVisible(true);
		ten_pTet.setVisible(true);
		ten_pCty.setVisible(true);
		ten_pCNV.setVisible(true);
		ten_pTon.setVisible(true);
		
		row_tieude_PN.setVisible(true);
		row_hienthi_PCNV.setVisible(true);
		row_hienthi_PCty.setVisible(true);
		row_hienthi_PN.setVisible(true);
		row_hienthi_PTet.setVisible(true);
		row_hienthi_pTon.setVisible(true);
		hoten.setVisible(true);
		tendv.setVisible(true);
		madv.setVisible(true);
	
		
		String str_date = _sf.format(date_);
		String year		= str_date.substring(6, 10); 
		
		//*Lay tong phep nam
		IGenericDAO<N_REST,N_RESTPk> Dao_Rest = Application.getApp().getDao(N_REST.class);
		N_RESTPk pk = new N_RESTPk(sothe, year);
		N_REST Data_Rest = Dao_Rest.findById(pk);
		
	//	pn.setText(Data_Rest.getOBTAIN().toString());
		if(Data_Rest == null)
		{
			pn.setText("");
			p_tet.setText("");
			p_cty.setText("");
			p_cnv.setText("");
			p_ton.setText("");
			pn_dsd.setText("");
			pn_conlai.setText("");
			p_tet_dsd.setText("");
			p_cty_dsd.setText("");
			p_cnv_dsd.setText("");
			p_ton_dsd.setText("");
			p_tet_conlai.setText("");
			p_cty_conlai.setText("");
			p_cnv_conlai.setText("");
			p_ton_conlai.setText("");
			OBJ_UTILITY.ShowMessageError("Không tìm thấy thông tin phép năm");
			return;
		}else
		{
			pn.setText(Data_Rest.getOBTAIN()==null?"":Data_Rest.getOBTAIN().toString());
			p_tet.setText(Data_Rest.getHOL_LUNAR()==null?"":Data_Rest.getHOL_LUNAR().toString());
			p_cty.setText(Data_Rest.getCO_ARR()==null?"":Data_Rest.getCO_ARR().toString());
			p_cnv.setText(Data_Rest.getEMP_APP()==null?"":Data_Rest.getEMP_APP().toString());
			p_ton.setText(Data_Rest.getSTORED()==null?"":Data_Rest.getSTORED().toString());
			pn_dsd.setText(Data_Rest.getUSED()==null?"":Data_Rest.getUSED().toString());
			pn_conlai.setText(Data_Rest.getREMAIN()==null?"":Data_Rest.getREMAIN().toString());
		}
		//** Lay chi tiet tung loai phep da nghi
		  //**PN(Tet) da su dung & con lai
			sql_tet = 
				"select count(t.empsn) from n_rest_detail t\n" +
				"where t.rest_type = 'TET'\n" + 
				"and t.empsn = '"+sothe+"'\n" + 
				"and t.rest_kind = 'PN'\n" + 
				"and to_char(t.date_off,'yyyy') = '"+year+"'";
			
			Object obj_tet = obj_util.Exe_Sql_Obj(sql_tet);
			p_tet_dsd.setText(obj_tet.toString());
			int tam_tet = Integer.valueOf(Data_Rest.getHOL_LUNAR()==null?"0":Data_Rest.getHOL_LUNAR().toString()) - Integer.valueOf(obj_tet.toString());
			p_tet_conlai.setText(String.valueOf(tam_tet));	
		
		//**PN(Cty sx) da su dung & con lai
			sql_cty = 
				"select count(t.empsn) from n_rest_detail t\n" +
				"where t.rest_type = 'CTY'\n" + 
				"and t.empsn = '"+sothe+"'\n" + 
				"and t.rest_kind = 'PN'\n" + 
				"and to_char(t.date_off,'yyyy') = '"+year+"'";
			
			Object obj_cty = obj_util.Exe_Sql_Obj(sql_cty);
			p_cty_dsd.setText(obj_cty.toString());
			int tam_ct = Integer.valueOf(Data_Rest.getCO_ARR()==null?"0":Data_Rest.getCO_ARR().toString()) - Integer.valueOf(obj_cty.toString());
			p_cty_conlai.setText(String.valueOf(tam_ct));	
			
		//**PN(CNV tu xin) da su dung & con lai
			sql_cnv = 
				"select count(t.empsn) from n_rest_detail t\n" +
				"where t.rest_type = 'CNV'\n" + 
				"and t.empsn = '"+sothe+"'\n" + 
				"and t.rest_kind = 'PN'\n" + 
				"and to_char(t.date_off,'yyyy') = '"+year+"'";
			
			Object obj_cnv = obj_util.Exe_Sql_Obj(sql_cnv);
			p_cnv_dsd.setText(obj_cnv.toString());
			int tam_cn = Integer.valueOf(Data_Rest.getEMP_APP()==null?"0":Data_Rest.getEMP_APP().toString()) - Integer.valueOf(obj_cnv.toString());
			p_cnv_conlai.setText(String.valueOf(tam_cn));	
			
		//**PN(TON) da su dung & con lai
			sql_ton = 
				"select count(t.empsn) from n_rest_detail t\n" +
				"where t.rest_type = 'TON'\n" + 
				"and t.empsn = '"+sothe+"'\n" + 
				"and t.rest_kind = 'PN'\n" + 
				"and to_char(t.date_off,'yyyy') = '"+year+"'";
			
			Object obj_ton = obj_util.Exe_Sql_Obj(sql_ton);
			p_ton_dsd.setText(obj_ton.toString());
			int tam_ton = Integer.valueOf(Data_Rest.getSTORED()==null?"0":Data_Rest.getSTORED().toString()) - Integer.valueOf(obj_ton.toString());
			p_ton_conlai.setText(String.valueOf(tam_ton));	
		
	}
	
	
	
	private void initComponents() {
		
		IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
		DSPB02 Data_DSPB02 = Dao_DSPB02.findById(user_up);
		ma_user = Data_DSPB02.getPB_USERNO();
		
		hoten = new Label();
        hoten.setForeground(Color.BLUE);
        tendv = new Label();
        tendv.setForeground(Color.BLUE);
        madv = new Label();
        madv.setForeground(Color.BLUE);
        
       /* radio_cnv.setEnabled(true);
        radio_cty.setEnabled(true);
        radio_ton.setEnabled(true);
        radio_tet_am.setEnabled(true);
        radio_no_1.setEnabled(true);
        radio_no_2.setEnabled(true);
        */
		
		this.setTitle("Nhập Chi Tiết Phép");
		this.setInsets(new Insets(1));
		this.setWidth(new Extent(830));
		this.setHeight(new Extent(630));
		this.setInsets(new Insets(1));
		this.setResizable(false);
		//this.setClosable(false);
		this.setStyleName("Default.Window");
		Grid	rootLayout	= new Grid();
				rootLayout.setInsets(new Insets(1));
		this.add(rootLayout);
		
						dept_ctrl	= new DeptUserControl_FactDetail();
						dept_ctrl.setAlignment(new Alignment(Alignment.LEFT, Alignment.TOP));
				rootLayout.add(dept_ctrl);
				
				    
				
				
				
				Column	col_right = new Column();
						GridLayoutData	layout	= new GridLayoutData();
										layout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.CENTER));
										layout.setInsets(new Insets(8));
						col_right.setLayoutData(layout);	
						col_right.setCellSpacing(new Extent(5));
						
						/*Row		row_so_gio	= new Row();
								row_so_gio.setCellSpacing(new Extent(10));
								row_so_gio.add(new Label("Số giờ/ngày"));	
									txt_time_per_day	= new TextField();
									txt_time_per_day.setText("8");
									txt_time_per_day.setWidth(new Extent(60));
								row_so_gio.add(txt_time_per_day);
								
						Row		row_note	= new Row();
								row_note.setCellSpacing(new Extent(10));
									txt_note_time		= new TextArea();
									txt_note_time.setToolTipText("Ghi chú thời gian nghỉ trong 1 ngày");
									txt_note_time.setWidth(new Extent(158));
								row_note.add(new Label("Ghi chú"));
								row_note.add(txt_note_time);*/
								
						Row		row_tu_gio = new Row();
								row_tu_gio.setCellSpacing(new Extent(10));
								row_tu_gio.add(new Label("Nghỉ từ giờ:"));
									txt_tu_gio = new TextField();
									txt_tu_gio.setMaximumLength(4);
									txt_tu_gio.setWidth(new Extent(33));
								row_tu_gio.add(txt_tu_gio);
								
								row_tu_gio.add(new Label(" Đến giờ:"));
									txt_den_gio = new TextField();
									txt_den_gio.setMaximumLength(4);
									txt_den_gio.setWidth(new Extent(33));
							    row_tu_gio.add(txt_den_gio);
							    
							    	gb_gio_nghi_trua	 	= new GroupBox();
							    	bt_group_nghi_trua		= new ButtonGroup();
							    	gb_gio_nghi_trua.setWidth(new Extent(60));
							    	gb_gio_nghi_trua.setHeight(new Extent(100));
							    	radio_gio_nghi_trua1	= new RadioButton("10:45");
							    	radio_gio_nghi_trua1.setGroup(bt_group_nghi_trua);
							    	radio_gio_nghi_trua2	= new RadioButton("11:00");
							    	radio_gio_nghi_trua2.setGroup(bt_group_nghi_trua);
							    	radio_gio_nghi_trua3	= new RadioButton("11:30");
							    	radio_gio_nghi_trua3.setGroup(bt_group_nghi_trua);
							    	radio_gio_nghi_trua4	= new RadioButton("11:45");
							    	radio_gio_nghi_trua4.setGroup(bt_group_nghi_trua);
							    	radio_gio_nghi_trua5	= new RadioButton("12:00");
							    	radio_gio_nghi_trua5.setGroup(bt_group_nghi_trua);
							    	radio_gio_nghi_trua6	= new RadioButton("12:15");
							    	radio_gio_nghi_trua6.setGroup(bt_group_nghi_trua);
							         
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua1);
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua2);
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua3);
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua4);
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua5);
							    	gb_gio_nghi_trua.add(radio_gio_nghi_trua6);
							    row_tu_gio.add(gb_gio_nghi_trua);	
						
						final Row		row_note	= new Row();
								    row_note.setCellSpacing(new Extent(10));
									Label label_note = new Label("Ghi chú:");
									sf_note_ban	= new SelectField();
									sf_note_ban.setWidth(new Extent(160));
									
									DefaultListModel list_note_pban = new DefaultListModel();
									list_note_pban.add("");
									list_note_pban.add("PBAN");
									list_note_pban.add("CON_OM_75%");
									sf_note_ban.setModel(list_note_pban);
									
									sf_note_benh	= new SelectField();
									sf_note_benh.setWidth(new Extent(160));
									
									DefaultListModel list_note_pbenh = new DefaultListModel();
									list_note_pbenh.add("");
									list_note_pbenh.add("NV_BENH");
									list_note_pbenh.add("NV_BENH_75%");
									list_note_pbenh.add("KHAM_THAI_100%");
									list_note_pbenh.add("SAY_THAI_100%");
									list_note_pbenh.add("KE_HH_100%");
									sf_note_benh.setModel(list_note_pbenh);
									
									
								
								row_note.add(label_note);
								row_note.add(sf_note_ban);	
								
									gb_ngan_dai_ngay 		= new GroupBox();
									bt_group_ngan_dai_ngay	= new ButtonGroup();
									gb_ngan_dai_ngay.setWidth(new Extent(100));
									gb_ngan_dai_ngay.setHeight(new Extent(30));
									radio_ngan_ngay			= new RadioButton("Ngắn Ngày");
									radio_ngan_ngay.setGroup(bt_group_ngan_dai_ngay);
									radio_dai_ngay			= new RadioButton("Dài Ngày");
									radio_dai_ngay.setGroup(bt_group_ngan_dai_ngay);
									
									gb_ngan_dai_ngay.add(radio_ngan_ngay);
									gb_ngan_dai_ngay.add(radio_dai_ngay);
								row_note.add(gb_ngan_dai_ngay);
				//*Hien thi PN	
						RowLayoutData tdLayout = new RowLayoutData();
						tdLayout.setWidth(new Extent(100));
						tdLayout.setAlignment(new Alignment(Alignment.CENTER,Alignment.CENTER));
						tdLayout.setBackground(Color.GREEN);
						
						RowLayoutData col2Layout = new RowLayoutData();
						col2Layout.setWidth(new Extent(100));
						col2Layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
					//	col2Layout.setBackground(Color.MAGENTA);
						row_tieude_PN = new Row(); 			
							
							   	pn   		= new Label();
							   	p_tet   	= new Label();
							   	p_cty 	 	= new Label();
							   	p_cnv 	 	= new Label();
							   	p_ton 		= new Label();
							   	pn_dsd	 	= new Label();
							   	pn_conlai	= new Label();
							   	p_tet_dsd	= new Label();
							   	p_tet_conlai= new Label();
							   	p_cty_dsd 	= new Label();
							   	p_cty_conlai= new Label();
							   	p_cnv_dsd 	= new Label();
							   	p_cnv_conlai= new Label();
							   	p_ton_dsd 	= new Label();
							   	p_ton_conlai= new Label();
							   	
							  	loaiphep = new Label("Loại phép");
							   	loaiphep.setForeground(Color.BLUE);
							   	loaiphep.setVisible(false);
							   	loaiphep.setLayoutData(tdLayout);
							   	row_tieude_PN.add(loaiphep);
							   	
							   	tongngay = new Label("Tổng ngày");
							   	tongngay.setForeground(Color.BLUE);
							   	tongngay.setVisible(false);
							   	tongngay.setLayoutData(tdLayout);
							   	row_tieude_PN.add(tongngay);
							   	
							   	daSD = new Label("Đã SD");
							   	daSD.setForeground(Color.BLUE);
							   	daSD.setVisible(false);
							   	daSD.setLayoutData(tdLayout);							   	
							   	row_tieude_PN.add(daSD);
							   	
								conlai = new Label("Còn lại");
								conlai.setForeground(Color.BLUE);
								conlai.setVisible(false);
								conlai.setLayoutData(tdLayout);
							   	row_tieude_PN.add(conlai);
				
					   row_hienthi_PN = new Row();
					    		ten_pn = new Label("TỔNG PN:");
					    		ten_pn.setForeground(Color.MAGENTA);
					    		ten_pn.setVisible(false);
					    		ten_pn.setLayoutData(col2Layout);
					    		row_hienthi_PN.add(ten_pn);
					    		
					    		pn.setForeground(Color.RED);
					    		pn_dsd.setForeground(Color.RED);
					    		pn_conlai.setForeground(Color.RED);
					    		pn.setLayoutData(col2Layout);
					    		row_hienthi_PN.add(pn);
					    		pn_dsd.setLayoutData(col2Layout);
					    		row_hienthi_PN.add(pn_dsd);
					    		pn_conlai.setLayoutData(col2Layout);
					    		row_hienthi_PN.add(pn_conlai);
					    		
					   row_hienthi_PTet = new Row();
					   			ten_pTet = new Label("(P.TẾT:");
					   			ten_pTet.setForeground(Color.MAGENTA);
					   			ten_pTet.setVisible(false);
					   			ten_pTet.setLayoutData(col2Layout);
					    		row_hienthi_PTet.add(ten_pTet);
					    		
					    		p_tet.setForeground(Color.RED);
					    		p_tet_dsd.setForeground(Color.RED);
					    		p_tet_conlai.setForeground(Color.RED);
					    		p_tet.setLayoutData(col2Layout);
					    		row_hienthi_PTet.add(p_tet);
					    		p_tet_dsd.setLayoutData(col2Layout);
					    		row_hienthi_PTet.add(p_tet_dsd);
					    		p_tet_conlai.setLayoutData(col2Layout);
					    		row_hienthi_PTet.add(p_tet_conlai);
					    		
					    row_hienthi_PCty = new Row();
					    		ten_pCty = new Label("CTY SX:");
					    		ten_pCty.setForeground(Color.MAGENTA);
					    		ten_pCty.setVisible(false);
					    		ten_pCty.setLayoutData(col2Layout);
					    		row_hienthi_PCty.add(ten_pCty);
			    		
					    		p_cty.setForeground(Color.RED);
					    		p_cty_dsd.setForeground(Color.RED);
					    		p_cty_conlai.setForeground(Color.RED);
					    		p_cty.setLayoutData(col2Layout);
					    		row_hienthi_PCty.add(p_cty);
					    		p_cty_dsd.setLayoutData(col2Layout);
					    		row_hienthi_PCty.add(p_cty_dsd);
					    		p_cty_conlai.setLayoutData(col2Layout);
					    		row_hienthi_PCty.add(p_cty_conlai);
					   
					    row_hienthi_PCNV = new Row();
					    		ten_pCNV = new Label("CNV:)");
					    		ten_pCNV.setForeground(Color.MAGENTA);
					    		ten_pCNV.setVisible(false);
					    		ten_pCNV.setLayoutData(col2Layout);
					    		row_hienthi_PCNV.add(ten_pCNV);
			    		
					    		p_cnv.setForeground(Color.RED);
					    		p_cnv_dsd.setForeground(Color.RED);
					    		p_cnv_conlai.setForeground(Color.RED);
					    		p_cnv.setLayoutData(col2Layout);
					    		row_hienthi_PCNV.add(p_cnv);
					    		p_cnv_dsd.setLayoutData(col2Layout);
					    		row_hienthi_PCNV.add(p_cnv_dsd);
					    		p_cnv_conlai.setLayoutData(col2Layout);
					    		row_hienthi_PCNV.add(p_cnv_conlai);
					   
					    row_hienthi_pTon = new Row();
						    	ten_pTon = new Label("TỒN: ");
						    	ten_pTon.setForeground(Color.ORANGE);
						    	ten_pTon.setVisible(false);
						    	ten_pTon.setLayoutData(col2Layout);
						    	row_hienthi_pTon.add(ten_pTon);
				    		
						    	p_ton.setForeground(Color.ORANGE);
						    	p_ton_dsd.setForeground(Color.ORANGE);
					    		p_ton_conlai.setForeground(Color.ORANGE);
					    		p_ton.setLayoutData(col2Layout);
						    	row_hienthi_pTon.add(p_ton);
						    	p_ton_dsd.setLayoutData(col2Layout);
						    	row_hienthi_pTon.add(p_ton_dsd);
						    	p_ton_conlai.setLayoutData(col2Layout);
						    	row_hienthi_pTon.add(p_ton_conlai);
					    
					//*End hien thi PN		   	
							   	
						col_right.add(new Label("."));
						col_right.add(row_tu_gio);
						col_right.add(row_note);
						
					
				rootLayout.add(col_right);
				
				tf_date	= new DscField();
				tf_date.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));		
		Row		row_to_date	= new Row();
				row_to_date.setCellSpacing(new Extent(10));
				row_to_date.add(new Label("đến ngày "));
					dateF_to_date	= new DateField();
					dateF_to_date.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
					dateF_to_date.setTextField(tf_date);
				row_to_date.add(dateF_to_date);
		rootLayout.add(row_to_date);
		rootLayout.add(row_tieude_PN);
		
		
		      
				rootLayout.add(hoten);
				rootLayout.add(row_hienthi_PN);
				rootLayout.add(tendv);
				rootLayout.add(row_hienthi_PTet);
				rootLayout.add(madv);		
				rootLayout.add(row_hienthi_PCty);
				rootLayout.add(new Label(""));
				rootLayout.add(row_hienthi_PCNV);
				rootLayout.add(new Label(""));
				rootLayout.add(row_hienthi_pTon);
			
			
				
				        
						
						group_huongluong 	= new ButtonGroup();
						
						group_phepkhac		= new ButtonGroup();	
						
						radio_tet_am		= new RadioButton("(PN)Nghỉ tết");
						radio_tet_am.setId("1");
						radio_tet_am.setGroup(group_huongluong);
						
						radio_cty			= new RadioButton("(PN)Cty sắp xếp");
						radio_cty.setGroup(group_huongluong);
						
						radio_cnv			= new RadioButton("(PN)CNV tự xin");
						radio_cnv.setGroup(group_huongluong);
					//	radio_cnv.setEnabled(false);
						radio_ton	= new RadioButton("Phép tồn");
						radio_ton.setGroup(group_huongluong);
					//	radio_ton.setEnabled(false);
						radio_no_1	= new RadioButton("P.Nợ (Cty sx)");
						radio_no_1.setGroup(group_huongluong);
					//	radio_no_1.setEnabled(false);
						radio_no_2	= new RadioButton("P.Nợ (cn tx)");
						radio_no_2.setGroup(group_huongluong);
					//	radio_no_2.setEnabled(false);
						
						radio_cuoi	= new RadioButton("Phép cưới");
						radio_cuoi.setGroup(group_huongluong);
						radio_tang	= new RadioButton("Phép tang");
						radio_tang.setGroup(group_huongluong);
						radio_le	= new RadioButton("Nghỉ Tết,Lễ");
						radio_le.setGroup(group_huongluong);
						radio_hh	= new RadioButton("Nghỉ hết hàng");
						radio_hh.setGroup(group_huongluong);
						radio_tnld	= new RadioButton("Nghỉ TNLĐ");
						radio_tnld.setGroup(group_huongluong);
						radio_coluong	= new RadioButton("N.có lương khác");
						radio_coluong.setGroup(group_huongluong);
						
						final Grid 	content_tab_1	= new Grid();
								content_tab_1.setColumnWidth(0, new Extent(140));
								content_tab_1.setInsets(new Insets(new Extent(3)));
								content_tab_1.add(radio_tet_am);			content_tab_1.add(radio_cuoi);
								content_tab_1.add(radio_cty);				content_tab_1.add(radio_tang);
								content_tab_1.add(radio_cnv);				content_tab_1.add(radio_le);
								content_tab_1.add(radio_ton);   			content_tab_1.add(radio_hh);
								content_tab_1.add(radio_no_1);				content_tab_1.add(radio_tnld);
								content_tab_1.add(radio_no_2);				content_tab_1.add(radio_coluong);
								
								
						
						radio_ban	= new RadioButton("Phép bận");
						radio_ban.setGroup(group_phepkhac);
						radio_bu	= new RadioButton("Phép bù");
						radio_bu.setGroup(group_phepkhac);
						radio_khongluong	= new RadioButton("Nghỉ khác");
						radio_khongluong.setGroup(group_phepkhac);
						radio_khangcong		= new RadioButton("Nghỉ không phép");
						radio_khangcong.setGroup(group_phepkhac);
						radio_CN_moi	= new RadioButton("Công nhân mới NX");
						radio_CN_moi.setGroup(group_phepkhac);
						radio_benh		= new RadioButton("Phép bệnh");
						radio_benh.setGroup(group_phepkhac);
						radio_ds	= new RadioButton("Nghỉ dưỡng sức");
						radio_ds.setGroup(group_phepkhac);
						final Grid 	content_tab_2	= new Grid();
								content_tab_2.setInsets(new Insets(new Extent(3)));
								content_tab_2.setColumnWidth(0, new Extent(140));
								content_tab_2.add(radio_ban);				content_tab_2.add(radio_benh);
								content_tab_2.add(radio_bu);				content_tab_2.add(radio_ds);
								content_tab_2.add(radio_khongluong);		
								content_tab_2.add(radio_khangcong);			
								content_tab_2.add(radio_CN_moi);			
								content_tab_2.add(new Label());
								DefaultTabModel tabModel = new DefaultTabModel();
										tabModel.addTab("Hưởng lương", content_tab_1);
										tabModel.addTab("Phép khác", content_tab_2);
										
						
			
						radio_ban.addActionListener(new ActionListener() {
							
						
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								row_note.remove(sf_note_benh);
								row_note.add(sf_note_ban);
								row_note.remove(gb_ngan_dai_ngay);
								row_note.add(gb_ngan_dai_ngay);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(true);
								sf_note_ban.setBackground(Color.WHITE);
						        
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(true);
								gb_ngan_dai_ngay.setBackground(Color.WHITE);
								
								
							}
						});
						
						radio_benh.addActionListener(new ActionListener() {
							
						
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								row_note.remove(sf_note_ban);
								row_note.add(sf_note_benh);
								row_note.remove(gb_ngan_dai_ngay);
								row_note.add(gb_ngan_dai_ngay);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_benh.setEnabled(true);
								sf_note_benh.setBackground(Color.WHITE);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(true);
								gb_ngan_dai_ngay.setBackground(Color.WHITE);
								
							}
						});
						
						radio_bu.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(false);
								sf_note_benh.setEnabled(false);
								sf_note_ban.setBackground(Color.LIGHTGRAY);
								sf_note_benh.setBackground(Color.LIGHTGRAY);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(false);
								gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
								reset_ngan_dai_ngay();
								
							}
						});
						
						radio_khongluong.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(false);
								sf_note_benh.setEnabled(false);
								sf_note_ban.setBackground(Color.LIGHTGRAY);
								sf_note_benh.setBackground(Color.LIGHTGRAY);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(false);
								gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
								reset_ngan_dai_ngay();
							}
						});
						
						radio_khangcong.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(false);
								sf_note_benh.setEnabled(false);
								sf_note_ban.setBackground(Color.LIGHTGRAY);
								sf_note_benh.setBackground(Color.LIGHTGRAY);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(false);
								gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
								reset_ngan_dai_ngay();
							}
						});
						
						radio_CN_moi.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(false);
								sf_note_benh.setEnabled(false);
								sf_note_ban.setBackground(Color.LIGHTGRAY);
								sf_note_benh.setBackground(Color.LIGHTGRAY);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(false);
								gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
								reset_ngan_dai_ngay();
							}
						});
						
						radio_ds.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								txt_tu_gio.setEnabled(true);
								txt_den_gio.setEnabled(true);
								txt_tu_gio.setBackground(Color.WHITE);
								txt_den_gio.setBackground(Color.WHITE);
								
								sf_note_ban.setSelectedIndex(0);
								sf_note_benh.setSelectedIndex(0);
								sf_note_ban.setEnabled(false);
								sf_note_benh.setEnabled(false);
								sf_note_ban.setBackground(Color.LIGHTGRAY);
								sf_note_benh.setBackground(Color.LIGHTGRAY);
								
								gb_gio_nghi_trua.setEnabled(true);
								gb_gio_nghi_trua.setBackground(Color.WHITE);
								gb_ngan_dai_ngay.setEnabled(false);
								gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
								reset_ngan_dai_ngay();
							}
						});
										
						tab_reason	= new TabbedPane();						
						tab_reason.setModel(tabModel);
						tab_reason.setInsets(new Insets(10));
						tab_reason.setWidth(new Extent(300));
						tab_reason.setHeight(new Extent(500));
						tab_reason.setBorder(new Border(2, Color.DARKGRAY, Border.STYLE_RIDGE));
						tab_reason.setSelectedIndex(0);
									
				rootLayout.add(tab_reason);
				//Set mac dinh khi load form len
				txt_tu_gio.setText("");
				txt_tu_gio.setBackground(Color.LIGHTGRAY);
				txt_den_gio.setText("");
				txt_den_gio.setBackground(Color.LIGHTGRAY);
				txt_tu_gio.setEnabled(false);
				txt_den_gio.setEnabled(false);
				sf_note_ban.setEnabled(false);
				sf_note_benh.setEnabled(false);
				sf_note_ban.setBackground(Color.LIGHTGRAY);
				sf_note_benh.setBackground(Color.LIGHTGRAY);
				gb_gio_nghi_trua.setEnabled(false);
				gb_gio_nghi_trua.setBackground(Color.LIGHTGRAY);
				gb_ngan_dai_ngay.setEnabled(false);
				gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
				//end 
				tab_reason.getSelectionModel().addChangeListener(new ChangeListener() {
					
					public void stateChanged(ChangeEvent e) {
						
						txt_tu_gio.setText("");
						txt_tu_gio.setBackground(Color.LIGHTGRAY);
						txt_den_gio.setText("");
						txt_den_gio.setBackground(Color.LIGHTGRAY);
						txt_tu_gio.setEnabled(false);
						txt_den_gio.setEnabled(false);
						sf_note_ban.setEnabled(false);
						sf_note_benh.setEnabled(false);
						sf_note_ban.setBackground(Color.LIGHTGRAY);
						sf_note_benh.setBackground(Color.LIGHTGRAY);
						reset_gio_nghitrua();
						reset_ngan_dai_ngay();
						gb_gio_nghi_trua.setEnabled(false);
						gb_gio_nghi_trua.setBackground(Color.LIGHTGRAY);
						gb_ngan_dai_ngay.setEnabled(false);
						gb_ngan_dai_ngay.setBackground(Color.LIGHTGRAY);
						for (int i=0;i<content_tab_1.getComponentCount();i++){
							Component c = content_tab_1.getComponent(i);
							if (c instanceof RadioButton){
								RadioButton btn = (RadioButton) c;
								btn.setSelected(false);
							   
							}
							
						}
						for (int i=0;i<content_tab_2.getComponentCount();i++){
							Component c = content_tab_2.getComponent(i);
							
							if (c instanceof RadioButton){
								RadioButton btn = (RadioButton) c;
								btn.setSelected(false);
							}
						}
						
						
					}
				});
				
			
				
				GridLayoutData 	layout_for_Col_btn	= new GridLayoutData();
								layout_for_Col_btn.setAlignment(new Alignment(Alignment.CENTER, Alignment.TOP));
				Column	col_btn = new Column();
						col_btn.setLayoutData(layout_for_Col_btn);
						col_btn.setInsets(new Insets(50, 30, 50, 0));
						col_btn.setCellSpacing(new Extent(10));
				rootLayout.add(col_btn);
				
					Button 	btn_ok = new Button();
							btn_ok.setText("Nhập Phép");
							btn_ok.setStyleName("Default.ToolbarButton");
							btn_ok.setBackground(Color.DARKGRAY);
							btn_ok.addActionListener(new ActionListener() {
								
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									action_ok();
								}
							});
							
					Button  btn_cancel	= new Button();
							btn_cancel.setText("Thoát");
							btn_cancel.setStyleName("Default.ToolbarButton");
							btn_cancel.setBackground(Color.DARKGRAY);
							btn_cancel.addActionListener(new ActionListener() {
								
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									userClose();
								}
							});
			    	list_error	= new ListBox();
				//	list_error.setWidth(new Extent(150));
					col_btn.add(btn_ok);
					col_btn.add(btn_cancel);
					col_btn.add(new Label("Danh Sách không thể cập nhật"));
					col_btn.add(list_error);
					
					Button btn_export_list_error = new Button();
					btn_export_list_error.setText("Xuất Excel");
					btn_export_list_error.setBorder(new Border(2,Color.LIGHTGRAY,Border.STYLE_OUTSET) );
					btn_export_list_error.setForeground(Color.BLACK);
					btn_export_list_error.setBackground(Color.DARKGRAY);
					btn_export_list_error.setStyleName("Default.ToolbarButton");
					col_btn.add(btn_export_list_error);
					btn_export_list_error.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							action_Export();
							doExport();
						}
					});
					
					dept_ctrl.getEMPSN_TextField().addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							boolean flag = dept_ctrl.flag_empsn;//vi trong dept_ctrl da kt khi nhap so the nen bien flag dc gan de k kt nua, k thoa dk thi thoat thoat luon
							if(flag == false)
							{
							    hoten.setText("");
							    tendv.setText("");
							    madv.setText("");
								return;
							}else
							{
								doSelect(e);
							}
							
						}
					});
					
					RadioButton rdio_date  = dept_ctrl.rdio_date;
					RadioButton rdio_month = dept_ctrl.rdio_month;
					RadioButton rdio_year  = dept_ctrl.rdio_year;
					
					rdio_date.setVisible(false);
					rdio_month.setVisible(false);
					rdio_year.setVisible(false);
					
					dept_ctrl.bt_refresh.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							setF_Refresh();
							
						}
					});
					
					dept_ctrl.sf_fact.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							setF_Refresh();
						}
					});
					
					dept_ctrl.sf_id_dept.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							setF_Refresh();
							
						}
					});
					
		
	}
	
	private void add(GridLayout grid_PN) {
		// TODO Auto-generated method stub
		
	}

	private void setcolor_enable()
	{
		radio_cty.setEnabled(true);
		radio_cnv.setEnabled(true);
		radio_tet_am.setEnabled(true);
		radio_ton.setEnabled(true);
		radio_no_1.setEnabled(true);
		radio_no_2.setEnabled(true);
		radio_cty.setForeground(Color.BLACK);
		radio_cnv.setForeground(Color.BLACK);
		radio_tet_am.setForeground(Color.BLACK);
		radio_ton.setForeground(Color.BLACK);
		radio_no_1.setForeground(Color.BLACK);
		radio_no_2.setForeground(Color.BLACK);
	}

	private void doSelect(ActionEvent e)
	{
		setcolor_enable();
		
		String sothe = dept_ctrl.getEmpsn();
		Date date    = dept_ctrl.getDate();
		N_EMPLOYEE data1 = new N_EMPLOYEE();
		IGenericDAO dao1 = Application.getApp().getDao(N_EMPLOYEE.class);
		String empsn = dept_ctrl.getEmpsn();
		List list1 = dao1.find(1, "select a.FNAME, a.LNAME, b.NAME_DEPT,b.ID_DEPT,a.USER_MANAGE_ID, a.TRUCTIEP_SX from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{dept_ctrl.getEmpsn()});
		if (list1.size()>0){
			Object[] obj1 = (Object[]) list1.get(0);
			String ho  = obj1[0]==null?"":obj1[0].toString().trim();
			String ten = obj1[1]==null?"":obj1[1].toString().trim();
			hoten.setText(Vni2Uni.convertToUnicode(ho+ " " + ten));
			tendv.setText(Vni2Uni.convertToUnicode(obj1[2].toString()));
			madv.setText(obj1[3].toString());
		}
		
		hienThiPN(sothe, date);
		
		//////////////////
		String str_date  		 = _sf.format(dept_ctrl.getDate());
	    String year 			 = 	str_date .substring(6,10);
		
		 IGenericDAO<N_REST, N_RESTPk> Dao_Rest = Application.getApp().getDao(N_REST.class);
		 N_RESTPk pk = new N_RESTPk(sothe, year);
		 N_REST Data_Rest = Dao_Rest.findById(pk);
		
		if(an_hien_PN(sothe, "CNV") == false)
		{
			radio_cnv.setEnabled(false);
			radio_cnv.setForeground(Color.LIGHTGRAY);
			radio_cnv.setSelected(false);
		}
		
		if (an_hien_PN(sothe, "CTY") == false)
		{
			radio_cty.setEnabled(false);
			radio_cty.setForeground(Color.LIGHTGRAY);
			radio_cty.setSelected(false);
		}
		
	    if (an_hien_PN(sothe, "TET") == false)
		{
			radio_tet_am.setEnabled(false);
			radio_tet_am.setForeground(Color.LIGHTGRAY);
			radio_tet_am.setSelected(false);
		}
	    
	    if (an_hien_PN(sothe, "TON") == false)
		{
			radio_ton.setEnabled(false);
			radio_ton.setForeground(Color.LIGHTGRAY);
			radio_ton.setSelected(false);
		}
	    
	    System.out.println(Long.valueOf(Data_Rest.getREMAIN().toString()));
	    if (Long.valueOf(Data_Rest.getREMAIN().toString()) <= 0)
	    	
	    {
	    	radio_no_1.setEnabled(true);
	    	radio_no_2.setEnabled(true);
	    	radio_tet_am.setEnabled(false);
	    	radio_cnv.setEnabled(false);
	    	radio_cty.setEnabled(false);
	    	radio_tet_am.setForeground(Color.LIGHTGRAY);
	    	radio_cnv.setForeground(Color.LIGHTGRAY);
	    	radio_cty.setForeground(Color.LIGHTGRAY);
	    }else
	    {
	    	radio_no_1.setEnabled(false);
	    	radio_no_2.setEnabled(false);
	    	radio_no_1.setForeground(Color.LIGHTGRAY);
			radio_no_2.setForeground(Color.LIGHTGRAY);
	    }
	}
	
	class list_emp_rest_error
	{
		String empsn;
		String note;
		public String getEmpsn() {
			return empsn;
		}
		public void setEmpsn(String empsn) {
			this.empsn = empsn;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		@Override
		public String toString() {
			return empsn + " " + note;
		}
	}


	protected void action_ok() {   //baby
		
			OBJ_UTILITY OUtil	= new OBJ_UTILITY();
			OBJ_EMPSN Obj_e		= new OBJ_EMPSN();
		
			
			String empsn 	= "";
			String fact		= "";
			String group	= "";
			String nameDept	= "";
			String id_dept	= "";
			
			float  total		= 0;
		//	double hour			= 0;
			
			int n_reason		= 0;
			ArrayList<String> list_emp	= new ArrayList<String>();
			ArrayList<Date> list_date_off	= new ArrayList<Date>();
			
			empsn		= dept_ctrl.getEmpsn();
			fact		= dept_ctrl.getFact();
			group		= dept_ctrl.getGroup();
			nameDept	= dept_ctrl.getNameDept();
			id_dept		= dept_ctrl.getIDDept();
		//	Date date_f  = dept_ctrl.getDate();
			
			rd_f1	   = dept_ctrl.rdio_f1;
			rd_f2	   = dept_ctrl.rdio_f2;
			rd_f3	   = dept_ctrl.rdio_f3;
			rd_f5	   = dept_ctrl.rdio_f5;
			rd_f6	   = dept_ctrl.rdio_f6;
			rd_khac    = dept_ctrl.rdio_khac;
			
			
			
			if (!Obj_e.check_lock(user_up))//KIEM TRA KHOA CHUC NANG XLY DLIEU
			{
				return ;
			}
			
			if(dept_ctrl.getFact().equals(""))
			{
				if (!Obj_e.Kt_vungqly_khi_nhap_st(empsn, ma_user,"DEPSN"))
				{
					return;
				}else
				{   
					if(!Kt_nhap_ngay_nghi_phep())
					{
						return;
					}else
					if( !Obj_e.check_lock_month(empsn,"","","","", date_from,"DEPSN",ma_user,rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac))//khoa du lieu thang
					{
						return;
					}else
					{
					  list_emp = OUtil.getListEmpsn(empsn,fact,group,nameDept,id_dept,ma_user,"DEPSN");
					}
				}
				
			}else
			{   
			//	if(!Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, nameDept, ma_user))
				String sql_maql = Obj_e.Kt_vungqly_khi_nhap_xuong_nhom_tendvi(fact, group, nameDept,"DEPSN",id_dept, ma_user,rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac);
				if(sql_maql.equals("NO"))
				{   
					OBJ_UTILITY.ShowMessageError("Bạn không có quyền thao tác");
					return;
				}else
				{
					if(!Kt_nhap_ngay_nghi_phep())
					{
						return;
					}else
					if( !Obj_e.check_lock_month("",fact,group,nameDept,"" ,date_from,"DEPSN",ma_user,rd_f1,rd_f2,rd_f3,rd_f5,rd_f6,rd_khac))
					{
						return;
					}else
					{
						list_emp = OUtil.getListEmpsn(empsn,fact,group,nameDept,id_dept,ma_user,"DEPSN");
					}
				}
			}
			
			
			if(!kiemtra_chonphep())
			{
				return;
			}
			Get_gio_nghitrua();
			//Kt_nhap_ghichu();
			if(Kt_nhap_ghichu() == false)
			{
				return;
			}
			Get_ghichu();
			Get_note_ngan_dai();
			
			
			
			
			DefaultListModel list_emp_eror = (DefaultListModel) list_error.getModel();
			list_emp_eror.removeAll();
			list_all = new ArrayList<list_emp_rest_error>();
		//	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for( String emp : list_emp)
			{   
				list_emp_rest_error list = new list_emp_rest_error();
				list_date_off	= getListDateOff(date_from,date_to,emp);
				if(!kt_ngay_CN.equals("") && kt_ngay_CN.equals("CN"))//neu la CN thi thong bao CN k can dk phep
				{
					OBJ_UTILITY.ShowMessageError("Nhập không thành công vì:"+_sf.format(date_from)+" là Chủ Nhật không cần đăng ký phép");
					return;
					
				}
				
				if(kiemtra_PN(emp)== false )
				{   
					list_emp_eror.add(emp + " " + note_pn_error);
					list.setEmpsn(emp);
					list.setNote(note_pn_error);
					list_all.add(list);
					if(!dept_ctrl.getEmpsn().equals(""))
					{
						hienThiPN(empsn, date_from);
					}	
					continue;
				}
				
				for(Date date_ : list_date_off)
				{   
					
					if (Kt_DS_NS(emp, date_) == false)
					{
						list_emp_eror.add(emp + " " +_sf.format(date_) + " "+ note_DS_NS_error);
						list.setEmpsn(emp);
						list.setNote(_sf.format(date_)+" "+note_DS_NS_error);
						list_all.add(list);
						System.out.println(list_all);
						continue;
					}
					
					Kt_loai_ca(emp, date_);
					if(Kt_nhap_gio_nghi_phep(emp, "", flag, date_) == false)
					{
						return;
					}
					total = Tinh_tgian_nghi(emp, date_);
					nhap_phep(emp, date_, total);
					
				}
				
				if(!dept_ctrl.getEmpsn().equals(""))
				{
					hienThiPN(empsn, date_from);
				}	
		}
			
			if(!dept_ctrl.getFact().equals(""))
			{
				if(list_all.size() > 0)
				{
					OBJ_UTILITY.ShowMessageError("Nhập thành công,có 1 vài số thẻ không thể cập nhật. Vui lòng kiểm tra lại những số thẻ đó trong 'Danh Sách không thể cập nhật'.");
					return;
				}else
				{
					OBJ_UTILITY.ShowMessageOK("Nhập thành công");
					return;
				}
			}else
			{
				if(list_all.size() > 0)
				{   
					OBJ_UTILITY.ShowMessageError(""+list_all+": không nhập được");
					return;
				}else
				{
					OBJ_UTILITY.ShowMessageOK("Nhập thành công");
					return;
				}
				
			}
		    
	
	}
	
	private ArrayList<Date> getListDateOff(Date dateFrom, Date dateTo,String sothe) {//co kt neu la CN k hop le (neu la BV thi CN hop le cho nhap phep)
		
		ArrayList<Date> listDate = new ArrayList<Date>();
		
		if((Check_is_Sunday(dateFrom)== false) || (Check_is_Sunday(dateFrom) && Kt_Donvi_Baove(sothe))){//27/03/2012 Ngan them k can kiem tra CN cho dv = "BV" 
			listDate.add(dateFrom);
		}else{
			//ObjUtility.ShowMessageError("Chu Nhat không cần đăng ký phép");
			kt_ngay_CN = "CN";
			return listDate;
		}
		
			dateFrom = ObjUtility.DAY_NEXT(dateFrom);
		
			while(dateFrom.before(dateTo) || dateFrom.equals(dateTo)){
				
				if( (!Check_is_Sunday(dateFrom)) || (Check_is_Sunday(dateFrom) && Kt_Donvi_Baove(sothe)) ){
					
					listDate.add(dateFrom);
				}
				
				dateFrom = ObjUtility.DAY_NEXT(dateFrom);
			}
		return listDate;
	}


	private void doSaveData(ArrayList<String> list_emp,ArrayList<Date> list_date_off, 
										String rest_kind, String rest_type,
										float total, long hour, String note) {
		
	
		Rest_utility		utilRest	= new Rest_utility();
		String 				error		= "";
		SimpleDateFormat	sf_ 		= new SimpleDateFormat("dd/MM/yyyy");
		String 				user_up 	= Application.getApp().getLoginInfo().getUserID();
		Calendar			calenderOff	= Calendar.getInstance();
		Date 				date_up		= ObjUtility.DateFormat_DDMMYYYY(calenderOff.getTime());
		N_REST_DETAIL		objData 	;
		
		IGenericDAO<N_REST_DETAIL, String> objDAO 	= Application.getApp().getDao(N_REST_DETAIL.class);
		ObjError err = new ObjError();    	
		
		DefaultListModel 	listErr = new DefaultListModel();
							
		for (String empsn : list_emp) {
			
			for(Date dateOff : list_date_off){
				
				calenderOff.setTime(dateOff); 
				
				if(rest_kind.equals("PN") && checkAvailableRestType(empsn,rest_type,calenderOff.get(Calendar.YEAR)) == false ){
					err.setIdError(empsn);
					err.setErrorStr("hết phép năm ( " + sf_.format(dateOff) + ")");
					listErr.add(empsn);
					continue;
				}
				
					objData = new N_REST_DETAIL();
					objData.setREST_KIND(rest_kind);
					objData.setREST_TYPE(rest_type);
					objData.setHOUR(hour);
					objData.setTOTAL(total);
					objData.setNOTE_TIME(Vni2Uni.convertToVNI(note));
					objData.setUSER_UP(user_up);
					objData.setDATE_UP(date_up);
					
					objData.setEMPSN(empsn);
					objData.setYEAR(String.valueOf(calenderOff.get(Calendar.YEAR)));
					objData.setDATE_OFF(dateOff);
					
					try{
						System.out.println("Save complete : " + empsn);
						//objDAO.save(objData);
					}catch (Exception e) {
						listErr.add(objData.getEMPSN());
					}
			}
		}
		
		if(listErr.size() > 0){
			OBJ_UTILITY.ShowMessageError("Kiểm tra lại danh sách những số thẻ không thể cập nhật");
		}else{
			OBJ_UTILITY.ShowMessageOK("Nhập phép thành công");
		}
		
		list_error.setModel(listErr);
		
	}

	/**
	 * return true : available and else not available
	 * 
	 * @param empsn
	 * @param rest_type
	 * @param year
	 * @return
	 */
	
	private boolean checkAvailableRestType(String empsn, String rest_type, int year) {
		// dem so phep da nghi so voi so phep trong n_rest
		IGenericDAO<N_REST, String> daoRest = Application.getApp().getDao(N_REST.class);
		DetachedCriteria 	dcRest	= DetachedCriteria.forClass(N_REST.class);
		List listRest_cty	= daoRest.find(1,"SELECT o.CO_ARR from N_REST o where o.EMPSN = ? and o.YEAR = ?) ", new String[]{empsn,String.valueOf(year)});
		List listRest_tet	= daoRest.find(1,"SELECT o.HOL_LUNAR from N_REST o where o.EMPSN = ? and o.YEAR = ?) ", new String[]{empsn,String.valueOf(year)});
		OBJ_UTILITY OUtil = new OBJ_UTILITY();
		
		String sql = 
					"SELECT count(r.empsn) from n_rest_detail r\n" +
					" WHERE r.empsn ='"+empsn+"'\n" + 
					" AND r.year ='"+ String.valueOf(year)+ "'\n" + 
					" AND r.rest_kind='PN'\n" + 
					" AND r.rest_type ='"+rest_type+"'";
		Object 	obj		= OUtil.Exe_Sql_Obj(sql);
		System.out.println(obj);
		long	numRest = Long.parseLong((obj == null ? "0" : obj.toString()));
		
		if(numRest == 0)      return true ; 	// chua nghi phep nao
		
		if(listRest_cty.size() > 0 || listRest_tet.size() > 0 ){
			long cty = Long.valueOf(listRest_cty.get(0).toString());
			long tet = Long.valueOf(listRest_tet.get(0).toString());
			if(		(rest_type.equals("CTY") && numRest < cty)
				||	(rest_type.equals("TET") && numRest < tet))
			return true;
		}
		return false;
	}


	//  loai bo ngay CN
	private boolean Check_is_Sunday(Date date_in){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date_in);
		if(ca.get(ca.DAY_OF_WEEK) == Calendar.SUNDAY){
			return true;
		}
		
		return false;
	}

	//20/03/2012 Ngan them
	public boolean kiemtra_chonphep()
	{
		if(radio_tet_am.isSelected()){
			rest_kind	= "PN";
			rest_type	= "TET";
			rest_sal	= "REST_PAY";
		}
		else if(radio_cty.isSelected()){
			rest_kind	= "PN";
			rest_type	= "CTY";
			rest_sal	= "REST_PAY";
		}
		else if(radio_cnv.isSelected()){
			rest_kind	= "PN";
			rest_type	= "CNV";
			rest_sal	= "REST_PAY";
		}
		else if(radio_ton.isSelected()){
			rest_kind	= "PN";
			rest_type	= "TON";
			rest_sal	= "REST_PAY";
		}
		else if(radio_no_1.isSelected()){
			rest_kind	= "PN";
			rest_type	= "PNO_CTY";
			rest_sal	= "REST_PAY";
		}
		else if(radio_no_2.isSelected()){
			rest_kind	= "PN";
			rest_type	= "PNO_CNX";
			rest_sal	= "REST_PAY";
		}
		else if(radio_cuoi.isSelected()){
			rest_kind	= "PC";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
	
		else if(radio_tang.isSelected()){
			rest_kind	= "PT";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
		else if(radio_le.isSelected()){
			rest_kind	= "TL";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
		else if(radio_hh.isSelected()){
			rest_kind	= "HH";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
		else if(radio_tnld.isSelected()){				// phep nam, phep tang
			rest_kind	= "TNLD";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
		else if(radio_coluong.isSelected()){
			rest_kind	= "CLK";
			rest_type	= "";
			rest_sal	= "REST_PAY";
		}
		
		else if(radio_ban.isSelected()){
			rest_kind	= "PBAN";
			rest_type	= "";
			rest_sal	= "REST";
		}
		else if(radio_bu.isSelected()){
			rest_kind	= "NBU";
			rest_type	= "";
			rest_sal	= "OTHER";
		}
		else if(radio_khongluong.isSelected()){
			rest_kind	= "K";
			rest_type	= "";
			rest_sal	= "REST";
		}
		else if(radio_khangcong.isSelected()){
			rest_kind	= "KC";
			rest_type	= "";
			rest_sal	= "NWHOUR";
		}
		else if(radio_CN_moi.isSelected()){
			rest_kind	= "CNM";
			rest_type	= "";
			rest_sal	= "REST";
		}
		else if(radio_benh.isSelected()){
			rest_kind	= "PBENH";
			rest_type	= "";
			rest_sal	= "REST_SICK";
		}
		else if(radio_ds.isSelected()){
			rest_kind	= "DS";
			rest_type	= "";
			rest_sal	= "OTHER";
		}else 
		{
			OBJ_UTILITY.ShowMessageError("Chọn lý do");
			return false;
		}
		return true;
	}
	
//Begin 15/12/2012 viet de an hien may radio_tetam... khi het pnam
	public boolean an_hien_PN(String sothe, String loaiphep)
	{
		String str_date  		 = _sf.format(dept_ctrl.getDate());
	    String year 			 = 	str_date .substring(6,10);
	    String sql 				 = "";
		
		 IGenericDAO<N_REST, N_RESTPk> Dao_Rest = Application.getApp().getDao(N_REST.class);
		 N_RESTPk pk = new N_RESTPk(sothe, year);
		 N_REST Data_Rest = Dao_Rest.findById(pk);
		 
		 
		 if(Data_Rest == null)
		 {
			 OBJ_UTILITY.ShowMessageError("Không tìm thấy phép năm");
			   radio_cnv.setEnabled(false);
		       radio_cty.setEnabled(false);
		       radio_ton.setEnabled(false);
		       radio_tet_am.setEnabled(false);
		       radio_no_1.setEnabled(false);
		       radio_no_2.setEnabled(false);
			 return false;
		 }
		 else
		 {
			 long phepTet = Data_Rest.getHOL_LUNAR();
			 long phepCty = Data_Rest.getCO_ARR();
			 long phepCNV = Data_Rest.getEMP_APP();
			 long phepTon = Data_Rest.getSTORED();
			 
			 
			 sql = 
			    		"select count(*) from n_rest_detail t\n" +
			    		"where to_char(t.date_off,'YYYY') = '"+year+"'"+
			    		"	and t.rest_kind = 'PN'\n" + 
			    		"	and t.rest_type = '"+loaiphep+"'"+
			    		" 	and t.empsn = '"+sothe+"'";
			    	
			    	OBJ_UTILITY obj_util1		= new OBJ_UTILITY();
			    	Object obj1			   		= obj_util1.Exe_Sql_Obj(sql);
			    	
			    	if(loaiphep.equals("TET") && Long.valueOf(obj1.toString()) >= phepTet)
			    	{
			    		return false;
			    	}else if (loaiphep.equals("CTY") && Long.valueOf(obj1.toString()) >= phepCty)
			    	{
			    		return false;
			    	}else if (loaiphep.equals("CNV") && Long.valueOf(obj1.toString()) >= phepCNV)
			    	{
			    		return false;
			    	}else if (loaiphep.equals("TON") && Long.valueOf(obj1.toString()) >= phepTon)
			    	{
			    		return false;
			    	}else
			    	{
			    		return true;
			    	}
			    
		 }
		 
	}
	
//End 15/12/2012 viet de an hien may radio_tetam... khi het pnam
	
	public boolean kiemtra_PN(String sothe){
		
		OBJ_EMPSN obj_e = new OBJ_EMPSN();
		ArrayList<Date> ds_ngay_nghi	= new ArrayList<Date>();
		String loaiphep = "";
		String loaiphep_tv = "";
		long sophep_trongnam = 0;
		long sophep_ht_con	 = 0;
		
		
		
		if(radio_tet_am.isSelected()){ loaiphep = "HOL_LUNAR";loaiphep_tv = "TET";}
		else if (radio_cty.isSelected()){loaiphep = "CO_ARR";loaiphep_tv = "CTY";}
		else if (radio_cnv.isSelected()){loaiphep = "EMP_APP";loaiphep_tv = "CNV";}
		else if (radio_ton.isSelected()){loaiphep = "STORED";loaiphep_tv = "TON";}
		else{loaiphep = "NOT_PN";}//17/08/2012 THEM VAO DE NEU K TIM THAY THONG TIN PHEP NAM TRONG N_REST THI K CHO NHAP ALL CAC LOAI PHEP
		if(loaiphep != "")
		{
		String str_date  		 = _sf.format(dept_ctrl.getDate());
	    String year 			 = 	str_date .substring(6,10);
	    
	    String sql_n = 
    			"select t.remain  from n_rest t\n" +
    			 " where t.year = '"+year+"'"+
    			 " and t.empsn = '"+sothe+"'";
	    
	    OBJ_UTILITY obj_util_n = new OBJ_UTILITY();
	    Object obj_n			 = obj_util_n.Exe_Sql_Obj(sql_n);
	    
	    //if(obj_n == null)
    	//Update 30/08/2013, HA
    	//if ko tim thay du lieu phep trong N_REST va dang muon nhap PN thi bao loi, else neu nhap phep khac thi binh thuong, ko phai kiem tra PN gi ca	    
	    if(obj_n == null && !loaiphep.equals("NOT_PN"))
	    {
	    	note_pn_error = "Khong tim thay phep nam con lai "+year+" trong bang phep";
			return false;
	    }else
	    {
	    	long sophep_conlai  = Long.valueOf(obj_n.toString()); //remain trong n_rest
	    	//Update 30/08/2013, HA
	    	//if PN_con lai<=0 va dang muon nhap PN thi bao loi, else neu nhap phep khac thi binh thuong, ko phai kiem tra PN gi ca
	    	//if(sophep_conlai <= 0)
	    	if(sophep_conlai <= 0 && !loaiphep.equals("NOT_PN"))
	    	{
	    		note_pn_error = "Khong con phep nam con lai "+year+" trong bang phep";
				return false;
	    	}else
	    	{		
	    		if(!loaiphep.equals("NOT_PN"))
				{
				    
				    String sql = 
				    			"select t."+loaiphep+"  from n_rest t\n" +
				    			 " where t.year = '"+year+"'"+
				    			 " and t.empsn = '"+sothe+"'";
				    
				    OBJ_UTILITY obj_util = new OBJ_UTILITY();
				    Object obj			 = obj_util.Exe_Sql_Obj(sql);
				    
					if(obj == null ){
					// 	Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không có dữ liệu để nhập");
						note_pn_error = "Khong tim thay phep "+year;
						return false;
					}
					else
					{   
				    	ds_ngay_nghi		= getListDateOff(date_from,date_to,sothe);
				    	for(Date date_ : ds_ngay_nghi)
				    	{
				    		if (Kt_DS_NS(sothe, date_) == false)
				    		{
				    			continue;
				    		}else
				    		{
				    			obj_e.XOA_PHEP(sothe, date_, ma_user);
				    		}
				    	}
				    	sql = 
				    		"select count(*) from n_rest_detail t\n" +
				    		"where to_char(t.date_off,'YYYY') = '"+year+"'"+
				    		"	and t.rest_kind = 'PN'\n" + 
				    		"	and t.rest_type = '"+loaiphep_tv+"'"+
				    		" 	and t.empsn = '"+sothe+"'";
				    	
				    	OBJ_UTILITY obj_util1		= new OBJ_UTILITY();
				    	Object obj1			   		= obj_util1.Exe_Sql_Obj(sql);
				    	
				    	sophep_trongnam 	= Long.valueOf(obj.toString());	
				    	
				    	/*ds_ngay_nghi		= getListDateOff(date_from,date_to,sothe);*/
				    	int sophep_xinnghi  = ds_ngay_nghi.size();
							
				    	if(obj1 == null)
				    	{   
				    		if(sophep_xinnghi > sophep_trongnam)
				    		{
				    			note_pn_error = "Xin nghi nhieu hon PN: '"+loaiphep_tv+"' dang co,kiem tra lai";
				    			return false;
				    		}		
				    		return true;
				    	}
				    	else 
				    	{
				    		long sophep_danghi  = Long.valueOf(obj1.toString());
				    		sophep_ht_con		= sophep_trongnam - sophep_danghi;
				    		
				    		if(sophep_ht_con <= 0)
				    		{	
				    			note_pn_error = "Het PN: '"+loaiphep_tv+"'";
				    			return false;
				    		}
				    		else if(sophep_ht_con > 0 && sophep_xinnghi > sophep_ht_con )
				    		{
				    			note_pn_error = "Xin nghi nhieu hon PN: '"+loaiphep_tv+"' dang co,kiem tra lai";
				    			return false;
				    		}
				    	}
					
					}
				}
	    	}	    	
	    }
	}
		
    return true;
}
	 //21/03/2012 NGAN
	 public float Tinh_tgian_nghi(String sothe, Date date_)
	 {
		float rs = 0;
	    if (Kt_nhap_gio_nghi_phep(sothe,ca, flag,date_))	
		{
				float T 		= 0;// thoi gian thuc nghi
				long T1		= 0;//Gio bdau nghi doi ra phut:gio*60+phut
				long T2		= 0;//Gio ket thuc nghi doi ra phut:gio*60+phut
				float T3		= 0;//Bien ao de xet khi nghi trong gio nghi trua
				float T4		= 0;//Bien ao de xet khi nghi trong gio nghi trua
				long T5		= 0;//Lay 2 so thap phan sau dau phay de so sanh lam tron
				long t_mid_in	= 0;//Gio bat dau nghi trua doi ra phut
				long t_mid_out	= 0;//Gio ket thuc nghi trua(t_mid_in + 60) doi ra phut
				String A		= "";//Gio bat dau nghi
				String B		= "";//Phut bdau nghi
				String C		= "";//Gio ket thuc nghi
				String D		= "";//Phut ket thuc nghi
			if(!txt_tu_gio.getText().equals("") && !txt_den_gio.getText().equals(""))
			{  //Nghi tu gio den gio
				A = txt_tu_gio.getText().substring(0,2);
				B = txt_tu_gio.getText().substring(2,4);
				C = den_gio; //dat bien nay vi co CA3 lam tu dem den sang hom sau
				D = txt_den_gio.getText().substring(2,4);
				
				T1 = (Integer.valueOf(A)*60) + Integer.valueOf(B);
				T2 = (Integer.valueOf(C)*60) + Integer.valueOf(D);
				
				if(ca.equals(""))
				{
					t_mid_in	= (Integer.valueOf(mid_time_in.substring(0,2))*60 + Integer.valueOf(mid_time_in.substring(3,5)));
					t_mid_out	= t_mid_in + 60;
					
					 if (T2<=t_mid_in  ||  T1>=t_mid_out) // nghi buoi sang or chieu
					 {
		                   T = T2 -	T1;
					 }else
					 {
		                  T3 = t_mid_in - T1;
		                  if (T3<0) { T3=0;}
		                  T4 = T2 - t_mid_out;
		                  if (T4<0) { T4=0;}
		                  T =T3+T4;
					 }
				}else //di lam ca khong co gio nghi trua
				{
					if(T2 >= T1)
					{
					    T = T2-T1;
					}else
					{//Ca3 vi lam tu dem den sang nen co the gio ra< gio vao
						  long mui_gio = 24*60;
						  T = ((mui_gio-(Integer.valueOf(A)*60))- Integer.valueOf(D)) + T2;

					}
				}	
				        T3 =(T/60)/8;
				        if (T3<0)
				        { T3 = T3*(-1);}
				          A  = String.valueOf(T3);
				        
				        
				        if (A.length() <=3) { B=A;}
				        else if (A.length()>3)
				        {
		
				             T  = Integer.valueOf(A.substring(2,3));
				             T1 = Integer.valueOf(A.substring(3,4)); //gia tri can lam tron
				             T5 = Integer.valueOf(A.substring(2,4));
				             B  = A.substring(0,2);
				             
				             if (T5 >= 95 )
				             {
				               B = "1";
				             }
				             else
				             {
				               if (T1 >= 5)
				               { T = T+1;}
				                 B = B+String.valueOf(T);
				                 B = B.substring(0,3);
				               }
				             
				        }
				        
				        if (B.substring(0,1).equals("1"))
				        { B = "1";}
				          rs = Float.valueOf(B);
							
			    
		}else
		{   //Nghi 1 ngay
					rs = 1;
		}
	  }
	  
	  return rs;
	 }
	 
	 public void Kt_loai_ca(String sothe,Date date_)
	 {
		 String str_date  		 = _sf.format(date_);
		 
		 String sql = 
			 "select  count(t.empsn) from n_register_shift t\n" +
			 "WHERE T.SHIFT_DATE =  TO_DATE('"+str_date+"','DD/MM/YYYY')"+ 
			 "	AND T.ID_SHIFT IN (SELECT S.ID_SHIFT FROM N_SHIFT S\n" + 
			 "                                  WHERE S.NOTE LIKE 'CA%')\n" + 
			 "	AND T.EMPSN = '"+sothe+"'";
		 
		 OBJ_UTILITY obj_util = new OBJ_UTILITY();
		 Object obj 		  = obj_util.Exe_Sql_Obj(sql); 
		 
		 if ((obj == null) || (Long.valueOf(obj.toString()) == 0 ) )
		 {
			 ca = "";
			
			 
		 }else 
		 {
			 String sql1 = "select  count(t.empsn) from n_register_shift t\n" +
			 "WHERE T.SHIFT_DATE =  TO_DATE('"+str_date+"','DD/MM/YYYY')"+ 
			 "	AND T.ID_SHIFT IN (SELECT S.ID_SHIFT FROM N_SHIFT S\n" + 
			 "                                  WHERE S.NOTE LIKE 'CA 3%')\n" + 
			 "	AND T.EMPSN = '"+sothe+"'";
			 
			 Object obj1 		  = obj_util.Exe_Sql_Obj(sql1); 
			 if(obj1 == null || Long.valueOf(obj1.toString()) == 0)
			 {
				 ca = "ca";
				
			 }else {
				 
				 
				 ca = "ca";
				 flag = true; //Xet ca 3 giao giua ngay nay va ngay kia
				 
			 }
			 
			
		 }
	 }
	 
	 public boolean Kt_nhap_gio_nghi_phep(String sothe,String ca,boolean flag,Date date_)
	 { String time_in = "";//Gio vao cua ca trong DB
	   String time_out = "";//Gio ra cua ca trong DB
	   OBJ_UTILITY obj_util = new OBJ_UTILITY();
	   String sql = "";
	   String str_date  		 = _sf.format(date_);
	   
   if (txt_tu_gio.isEnabled() && txt_den_gio.isEnabled())
   {
	  
	   
	   if ( !txt_den_gio.getText().equals("") && Integer.valueOf(txt_den_gio.getText().substring(0,2)) == 0)
	   {
		   den_gio = "24"; // Gan 24 moi tru cho "tu_gio" de tinh duoc gio
	   }else if ( !txt_den_gio.getText().equals("") && Integer.valueOf(txt_den_gio.getText().substring(0,2)) != 0)
	   {
		   den_gio = txt_den_gio.getText().substring(0,2);
	   }
	   
	   if (    (txt_tu_gio.getText().equals("") && !txt_den_gio.getText().equals(""))
			   ||(!txt_tu_gio.getText().equals("") && txt_den_gio.getText().equals(""))
			   || (!txt_tu_gio.getText().equals("") && txt_tu_gio.getText().length() < 4) 
			   || (!txt_tu_gio.getText().equals("") && txt_den_gio.getText().length() < 4) 
			   
			   || (!txt_tu_gio.getText().equals("")
			    && (Integer.valueOf(txt_tu_gio.getText().substring(0,2)) >= 24 //gio
			   ||  Integer.valueOf(txt_tu_gio.getText().substring(0,2)) < 0
			   || Integer.valueOf(txt_tu_gio.getText().substring(2,4)) < 0  //phut
			   || Integer.valueOf(txt_tu_gio.getText().substring(2,4)) > 59))
			   
			   || (!txt_den_gio.getText().equals("")
			    && ( Integer.valueOf(txt_den_gio.getText().substring(0,2)) < 0
			   || Integer.valueOf(txt_den_gio.getText().substring(0,2)) >= 24
		   	   || Integer.valueOf(txt_den_gio.getText().substring(2,4)) < 0  //phut
		       || Integer.valueOf(txt_den_gio.getText().substring(2,4)) > 59)) )
		   
		  
	   {
		   OBJ_UTILITY.ShowMessageError("Giờ nghỉ phép không hợp lệ");
		   return false;
	   }
	   
	   //*Begin Kt phai nhap gio nghi trua
	   if(!txt_tu_gio.getText().equals("") || !txt_den_gio.getText().equals(""))
	   {
		   if(!radio_gio_nghi_trua1.isSelected() && !radio_gio_nghi_trua2.isSelected() && !radio_gio_nghi_trua3.isSelected()
			  && !radio_gio_nghi_trua4.isSelected() && !radio_gio_nghi_trua5.isSelected() && !radio_gio_nghi_trua6.isSelected() )
		   {
			   OBJ_UTILITY.ShowMessageError("Chọn giờ nghỉ trưa của CNV");
			   return false;
		   }
	   }
	   //*End Kt phai nhap gio nghi trua
	   
	   if ( (!txt_tu_gio.getText().equals("") && !txt_tu_gio.getText().matches("[0-9]{4}")) || (!txt_den_gio.getText().equals("") && !txt_den_gio.getText().matches("[0-9]{4}"))){
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK+MessageDialog.TYPE_ERROR, "Giờ nghỉ phép không hợp lệ.");
			return false;
		}
	   
	   if (ca.equals(""))
	    {
	    	sql = 
	    		"select  s.time_in,s.time_out from n_employee t,n_shift s\n" +
	    		"where t.shift = s.id_shift\n" + 
	    		"and t.empsn = '"+sothe+"'";
	    	
	    	List<Object[]> list_shift  = obj_util.Exe_Sql_nfield_nrow(sql, 2);
	    	if (list_shift.size() > 0)
	    	{
	    		Object[] obj_shift = 	(Object[]) list_shift.get(0);
	    		time_in 	= obj_shift[0].toString();
	    		time_out	= obj_shift[1].toString(); 
	    	}else
	    	{
	    		OBJ_UTILITY.ShowMessageError("Không tìm thấy ca làm việc");
	    		return false;
	    	}
	
	    }else
	    {
	    	sql = 
	    		"select  s.time_in,s.time_out, t.rowid from n_register_shift t,n_shift s\n" +
	    		"where t.id_shift  = s.id_shift\n" + 
	    		" and t.shift_date = to_date('"+str_date+"','dd/MM/yyyy')"+
	    		" and t.empsn = '"+sothe+"'";
	    	
	    	List<Object[]> list_shift  = obj_util.Exe_Sql_nfield_nrow(sql, 2);
	    	if (list_shift.size() > 0)
	    	{
	    		Object[] obj_shift = 	(Object[]) list_shift.get(0);
	    		time_in 	= obj_shift[0].toString();
	    		time_out	= obj_shift[1].toString(); 
	    	}else
	    	{
	    		OBJ_UTILITY.ShowMessageError("Không tìm thấy ca làm việc");
	    		return false;
	    	}	
	    }
		//Begin KT gio nhap co thuoc ca lam viec khong?
	   if (!txt_tu_gio.getText().equals("") && !txt_den_gio.getText().equals(""))
	   {
	   		if  (flag == false && (Integer.valueOf(txt_tu_gio.getText().substring(0,2)) < Integer.valueOf(time_in.substring(0,2))
	   			|| (Integer.valueOf(txt_tu_gio.getText().substring(0,2)) == Integer.valueOf(time_in.substring(0,2)) && Integer.valueOf(txt_tu_gio.getText().substring(2,4)) < Integer.valueOf(time_in.substring(3,5)))
	   			|| Integer.valueOf(txt_den_gio.getText().substring(0,2)) > Integer.valueOf(time_out.substring(0,2))
	   			|| (Integer.valueOf(txt_den_gio.getText().substring(0,2)) == Integer.valueOf(time_out.substring(0,2)) && Integer.valueOf(txt_den_gio.getText().substring(2,4)) > Integer.valueOf(time_out.substring(3,5)))))
	   		{
	   			OBJ_UTILITY.ShowMessageError("Giờ nghỉ phép không thuộc ca làm việc.Kiểm tra lại");
	    		return false;
	   		}	
	   		
	   		if (flag == true && ((Integer.valueOf(txt_tu_gio.getText().substring(0,2)) < Integer.valueOf(time_in.substring(0,2))
	   					&& (Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 0  
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 1
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 2
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 3
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 4
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 5
	   						&& Integer.valueOf(txt_tu_gio.getText().substring(0,2)) != 6)) //khac time_out
			   			|| (Integer.valueOf(txt_tu_gio.getText().substring(0,2)) == Integer.valueOf(time_in.substring(0,2)) && Integer.valueOf(txt_tu_gio.getText().substring(2,4)) < Integer.valueOf(time_in.substring(3,5)))
			   			|| (Integer.valueOf(txt_den_gio.getText().substring(0,2)) > Integer.valueOf(time_out.substring(0,2)) && Integer.valueOf(txt_den_gio.getText().substring(0,2)) < Integer.valueOf(time_in.substring(0,2)))
			   			|| (Integer.valueOf(txt_den_gio.getText().substring(0,2)) == Integer.valueOf(time_out.substring(0,2)) && Integer.valueOf(txt_den_gio.getText().substring(2,4)) > Integer.valueOf(time_out.substring(3,5)))	
	   			  )	)
	   		
	   		{
	   			OBJ_UTILITY.ShowMessageError("Giờ nghỉ phép không thuộc ca làm việc.Kiểm tra lại");
	    		return false;
	   		}
	   }
   }
	   //End  KT gio nhap co thuoc ca lam viec khong?
	   		return true;
	 }
	 
	 public boolean Kt_nhap_ngay_nghi_phep()
	 {   
		 date_from		= dept_ctrl.getDate();
		 date_to		= OBJ_UTILITY.DateFormat_DDMMYYYY(dateF_to_date.getText().trim());
		 if(date_from == null){
				OBJ_UTILITY.ShowMessageError("Chọn ngày nghỉ phép");
				return false;
			}
		 
		 if (date_to == null)
		{
				date_to = date_from;
		}else if(date_from.after(date_to))
		{
				OBJ_UTILITY.ShowMessageError("Đến ngày phải lớn hơn hoặc bằng ngày");
				return false;
		}else
		{
				if(date_from.before(date_to) && (!txt_tu_gio.getText().equals("") || !txt_den_gio.getText().equals("")))
				{
					ObjUtility.ShowMessageError("Nghỉ nhiều ngày không thể nhập giờ nghỉ phép");
					return false;
				}
		}
		 return true;
	 }
	 
	 public void reset_gio_nghitrua()
	 {
		 radio_gio_nghi_trua1.setSelected(false);
		 radio_gio_nghi_trua2.setSelected(false);
		 radio_gio_nghi_trua3.setSelected(false);
		 radio_gio_nghi_trua4.setSelected(false);
		 radio_gio_nghi_trua5.setSelected(false);
		 radio_gio_nghi_trua6.setSelected(false);
		 
	 }
	 
	 public void reset_ngan_dai_ngay()
	 {
		 radio_ngan_ngay.setSelected(false);
		 radio_dai_ngay.setSelected(false);
	 }
	 
	 public boolean Kt_Donvi_Baove(String sothe)
	 {   //Neu note trong N_department la 'BV'-->Bao ve thi van cho nhap phep ngay CNhat binh thuong
		 String sql = 
			 "select t.note  from n_department t\n" +
			 "where t.id_dept = (select e.depsn  from n_employee e where e.empsn = '"+sothe+"')";
		 
		 OBJ_UTILITY obj_util = new OBJ_UTILITY();
		 Object obj			 = obj_util.Exe_Sql_Obj(sql); 
		 if(obj == null || !obj.equals("BV"))
		 {
			 return false;
		 }
		 return true;
	 }
	 
	 public boolean Kt_nhap_ghichu() //Kiem tra ghi chu cho PBAN & PBENH
	 {   
		 if(sf_note_ban.isEnabled() || sf_note_benh.isEnabled())
		 {
			 if(sf_note_ban.getSelectedItem().toString().equals("") && sf_note_benh.getSelectedItem().toString().equals(""))
			 {
				 OBJ_UTILITY.ShowMessageError("Vui lòng chọn ghi chú cho loại phép này");
				 return false;
			 }
		 }
		 
		 if(gb_ngan_dai_ngay.isEnabled())
		 {
			 if(!radio_ngan_ngay.isSelected() && !radio_dai_ngay.isSelected())
			 {
				 OBJ_UTILITY.ShowMessageError("Vui lòng chọn ngắn ngày hoặc dài ngày cho loại phép này");
				 return false; 
			 }
		 }
		 return true;
	 }
	 
	 public void Get_gio_nghitrua()
	 {
		if (!txt_tu_gio.getText().equals("") && !txt_den_gio.getText().equals(""))
		{
		 if(gb_gio_nghi_trua.isEnabled())
		 {
			 if(radio_gio_nghi_trua1.isSelected())
			 {
				 mid_time_in = "10:45";
			 }else if(radio_gio_nghi_trua2.isSelected())
			 {
				 mid_time_in = "11:00";
			 }else if(radio_gio_nghi_trua3.isSelected())
			 {
				 mid_time_in = "11:30";
			 }else if(radio_gio_nghi_trua4.isSelected())
			 {
				 mid_time_in = "11:45";
			 }else if(radio_gio_nghi_trua5.isSelected())
			 {
				 mid_time_in = "12:00";
			 }else if(radio_gio_nghi_trua6.isSelected())
			 {
				 mid_time_in = "12:15";
			 }
		 }
	   }else
	   {
		   mid_time_in = "";
	   }
	 }
	 
	 public void Get_note_ngan_dai()
	 {
		 note_ngan_dai	= "";
		 if(gb_ngan_dai_ngay.isEnabled())
		 {
			 if(radio_ngan_ngay.isSelected())
			 {
				 note_ngan_dai = "NGAN_NGAY";
			 }else if(radio_dai_ngay.isSelected())
			 {
				 note_ngan_dai = "DAI_NGAY";
			 }else
			 {
				 note_ngan_dai =  "";
			 }
		 }
	 }
	 
	 public void Get_ghichu()//Lay ghi chu cho 2 loai PBENH & PBAN
	 {
		 if(radio_ban.isSelected() && !sf_note_ban.getSelectedItem().toString().equals(""))
		 {
			 if(sf_note_ban.getSelectedIndex() == 1)
			 {
				 ghichu = "PBAN";
			 }else if(sf_note_ban.getSelectedIndex() == 2)
			 {
				 ghichu = "CON_OM_75%";
			 }
		 }else if(radio_benh.isSelected() && !sf_note_benh.getSelectedItem().toString().equals(""))
		 {
			 if(sf_note_benh.getSelectedIndex() == 1)
			 {
				 ghichu = "NV_BENH";
			 }else if (sf_note_benh.getSelectedIndex() == 2)
			 {
				 ghichu = "NV_BENH_75%";
			 }else if (sf_note_benh.getSelectedIndex() == 3)
			 {
				 ghichu = "KHAM_THAI_100%";
			 }else if (sf_note_benh.getSelectedIndex() == 4)
			 {
				 ghichu = "SAY_THAI_100%";
			 }else if (sf_note_benh.getSelectedIndex() == 5)
			 {
				 ghichu = "KE_HH_100%";
			 }
		 }else
		 {
			 ghichu = "";
		 }
	 }
	
	 public boolean Kt_DS_NS(String sothe,Date date_)//Neu la Duong Suc hoac NSan thi khong cho xoa or update
	 { //Kiem tra 2 loai phep nay trong N_REST_DETAIL voi ngay nhap phep
		 IGenericDAO<N_REST_DETAIL, N_REST_DETAILPk> Dao_Rest = Application.getApp().getDao(N_REST_DETAIL.class);
		 N_REST_DETAILPk pk = new N_REST_DETAILPk(sothe, date_);
		 N_REST_DETAIL Data_Rest = Dao_Rest.findById(pk);
		 
		 if(Data_Rest != null && (Data_Rest.getREST_KIND().equals("DS") || Data_Rest.getREST_KIND().equals("NS")))
		 {   
			 note_DS_NS_error = Data_Rest.getREST_KIND();
			 return false;
		 }
		 
		 return true;
	 }
	 
	 private void nhap_phep(String sothe,Date date_,float total)
	 {
		 String str_date  		 = _sf.format(date_);
		 String r_kind_old 		 = "";
		 String strnote			 = "";
		 OBJ_EMPSN obj_e = new OBJ_EMPSN();
				 
				 IGenericDAO<N_REST_DETAIL, N_REST_DETAILPk> Dao_Rest = Application.getApp().getDao(N_REST_DETAIL.class);
				 N_REST_DETAILPk pk = new N_REST_DETAILPk(sothe, date_);
				 N_REST_DETAIL Data_Rest = Dao_Rest.findById(pk);
				 
				 if (Data_Rest == null)
				 {
					 String sql = "insert into n_rest_detail(empsn,Date_off,total,mid_time,tin,tout,rest_kind,rest_type,rest_sal,note,note1)"+
		 			   " values('"+sothe+"',to_date('"+str_date+"','dd/MM/yyyy'),"+total+","+
		 			   "'"+mid_time_in+"','"+txt_tu_gio.getText().toString()+"','"+txt_den_gio.getText().toString()+"',"+
		 			   "'"+rest_kind+"','"+rest_type+"','"+rest_sal+"','"+ghichu+"','"+note_ngan_dai+"')";
					 
					 OBJ_UTILITY obj_util = new OBJ_UTILITY();
				     obj_util.Exe_Update_Sql(sql);
				     
				     strnote = "Insert Ngay phep "+str_date+", So ngay phep: "+total+""+
				     		   ", Loai phep: "+rest_kind+", Loai PN: "+rest_type;
					 obj_e.Insert_N_Action_Daily(ma_user, "N_REST_DETAIL", "INSERT", sothe, strnote);
				 }else
				 {
					
					 r_kind_old = Data_Rest.getREST_KIND();
		
					     strnote = "Update Ngay phep "+str_date+" Old:So ngay phep: "+Data_Rest.getTOTAL()+", Loai phep: "+Data_Rest.getREST_KIND()+""+
					             ", Loai PN: "+Data_Rest.getREST_TYPE()+"-->New: So ngay phep: "+total+""+
					             ", Loai phep: "+rest_kind+", Loai PN: "+rest_type;
				   //Begin update
					 	// Data_Rest.setTOTAL(Long.valueOf(String.valueOf(total)));
					     Data_Rest.setTOTAL(total);
					 	 Data_Rest.setMID_TIME(mid_time_in);
					 	 Data_Rest.setTIN(txt_tu_gio.getText().toString());
					 	 Data_Rest.setTOUT(txt_den_gio.getText().toString());
					 	 Data_Rest.setREST_KIND(rest_kind);
					 	 Data_Rest.setREST_TYPE(rest_type);
					 	 Data_Rest.setREST_SAL(rest_sal);
					 	 Data_Rest.setNOTE(ghichu);
					 	 Data_Rest.setNOTE1(note_ngan_dai);
					 	 
					 	 Dao_Rest.update(Data_Rest);
					//End update
					 	
					     
					 	obj_e.Insert_N_Action_Daily(ma_user, "N_REST_DETAIL", "UPDATE", sothe, strnote);
				 }
				 
				 if (r_kind_old.equals("PN"))
				 {
					 obj_e.PN_CONLAI(sothe, date_); 
				 }
		
				 if (rest_kind.equals("PN"))
				 {
					 obj_e.PN_CONLAI(sothe, date_);
				 }
		      
			
	 }
	 
	 protected HSSFWorkbook action_Export() {
			
			//	List<Object[]> list_arr = obj_util.Exe_Sql_nfield_nrow(sql,7);
				  try {	// get data and export into Workbook
						
						File f = ReportFileManager.getReportFormatFolder("dshr");
						InputStream in 		= new FileInputStream(new File(f.getPath(), "temp.xls"));
						
						wb = new HSSFWorkbook(in);
						HSSFSheet 	sheet	= wb.getSheetAt(0);
						HSSFRow		row;
						HSSFCell	cell;
						
						row				= sheet.createRow(0);
							cell		= row.createCell(0);
										cell.setCellValue("DS KHONG THE CAP NHAT");
										
											
						int n_row	= 1;
						int n_col	= 0;
						
						for(list_emp_rest_error arr : list_all){
							
							row		= sheet.createRow(n_row);
							
							n_col	= 0;
						
							String emp  = arr.getEmpsn();
							String note = arr.getNote();
							
								cell	= row.createCell(n_col);
						      
								switch (n_col) {
								case 0:	// DSach khong the cap nhat
									cell.setCellValue(emp+" -- "+note);
									break;
									
								default:
									break;
									
								}
								n_col ++ ;
								n_row ++ ;	
							
						}
					
					} catch (Exception e) {
						OBJ_UTILITY.ShowMessageError(e.getMessage());
					}
				
			
		
			return wb;
		}
	 
	 private void doExport(){
			if(wb == null){
				errLabel.setText(" !!! ĐIỀU KIỆN XUẤT ???");
				errLabel.setForeground(Color.RED);
				return;
			}
			
			File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
			FileOutputStream out;
			try {
				
				out = new FileOutputStream(f1);
				wb.write(out);
				out.flush();
				out.close();
				String name = "DANH_SACH";
				File saveFile = new File(f1.getParentFile(),URLEncoder.encode(Application.getApp().getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
				ReportFileManager.saveTempReportFile(f1, saveFile);
				Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//userClose();
			//winPane.userClose();
			
		}
		
		private String getViewerUrl() {
			HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
			String viewerUrl = request.getRequestURL() + "?" +
			WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId() + "&" + 
			ReportService.PARAM_TYPE + "=" + (ATask.EXECTYPE_DIRECT == 0 ? ReportService.TYPE_TEMP : ReportService.TYPE_OUTPUT) + "&" +
			ReportService.PARAM_KEY + "=";
			return viewerUrl;
		}
	 //*Nhap PBAN_NKL khong su dung den
	 public void nhap_PBAN_NKL(String empsn,Date date_begin, Date date_end,String r_kind,float sum_dayoff)
	 {  //04/04/2012 CHUA CO YEU CAU K SD(C.HA tu lam them) 
		 String str_date_b  		 = _sf.format(date_begin);
		 String str_date_e  		 = _sf.format(date_end);
		 String note				 = "PBAN/NKL hon 1 thang";
		 OBJ_EMPSN obj_e 			 = new OBJ_EMPSN();
		 
		 if (sum_dayoff >= 5 &&  ((r_kind.equals("PBAN") || r_kind.equals("PBENH")) && (ghichu.equals("NV_BENH") || ghichu.equals("PBAN")))
				 || r_kind.equals("K") )
		 {
			 IGenericDAO<N_REST_PBAN_NKL, N_REST_PBAN_NKLPk> Dao_NKL = Application.getApp().getDao(N_REST_PBAN_NKL.class);
			 N_REST_PBAN_NKLPk pk = new N_REST_PBAN_NKLPk(empsn, date_begin,date_end);
			 N_REST_PBAN_NKL Data_NKL = Dao_NKL.findById(pk);
			 
			 if(Data_NKL == null)
			 {
				 String sql = "Insert into n_rest_pban_nkl(empsn,b_dates,e_dates,note,rest_kind,ts_ngay) "+
				 			  " values('"+empsn+"',to_date('"+str_date_b+"','dd/MM/yyyy'),to_date('"+str_date_e+"','dd/MM/yyyy')"+
				 			  ",'"+note+"','"+r_kind+"',"+sum_dayoff+")";
				 
				 OBJ_UTILITY obj_util = new OBJ_UTILITY();
			     obj_util.Exe_Update_Sql(sql);
			     
			     obj_e.Insert_N_Action_Daily(ma_user, "N_REST_PBAN_NKL", "INSERT", empsn,"PBAN/NKL: "+sum_dayoff);
			 }else
			 {
				Data_NKL.setNOTE(note);
				Data_NKL.setREST_KIND(r_kind);
				Data_NKL.setTS_NGAY(Long.valueOf(String.valueOf(sum_dayoff)));
				
				obj_e.Insert_N_Action_Daily(ma_user, "N_REST_PBAN_NKL", "UPDATE", empsn,"PBAN/NKL: "+sum_dayoff); 
			 }
		 }
	 }
}
