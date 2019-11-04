package ds.program.fvhr.minh.bear;

import it.businesslogic.ireport.gui.MessageBox;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Table;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
//import ds.program.fvhr.domain.N_BIRTH_CERTIFICATE;
import ds.program.fvhr.domain.N_BIRTH_CERTIFICATE;
import ds.program.fvhr.domain.N_EALRY_AFTER_B;
import ds.program.fvhr.domain.N_EALRY_BEFOR_B;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_REGISTER_SHIFT;
import ds.program.fvhr.domain.N_REST_DETAIL;
import ds.program.fvhr.domain.N_SHIFT;
import ds.program.fvhr.domain.N_TIME_BEAR;
import ds.program.fvhr.domain.N_VIEW_TIME_BEAR;
import ds.program.fvhr.minh.dao.BearJdbcDAO;
import dsc.dao.DataObjectSet;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;
import echopointng.GroupBox;
import fv.util.FvLogger;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.component.table.DscDataObjectSetTable;
import nextapp.echo2.extras.app.BorderPane;
import nextapp.echo2.app.FillImageBorder;
import fv.components.SelectItem;
import groovy.sql.Sql;
import dsc.echo2app.component.DscDateField;
import echopointng.ExpandableSection;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import echopointng.Separator;

public class BearProgram extends DefaultProgram {
	
	private ResourceBundle resourceBundle;
	private BearDataContent bearContentB;
	private BearDataContent bearContentA;
	private BearDataContent bearContentBear;
	private BearDataContent bearContentRegS;
	private BearDataContent bearContentRest;
	private BearDataContent bearContentTimeBear;
	private BearDataContent bearContentBirCer;
	private ContentPane ctpContent;
	private DscField txtEmpsn;
	private Label lblEmpName;
	private Label lblCaLamViec;
	private SelectField slfThangNS;
	private DscField txtNgayNGKS;
	private DscField txtNgaySinhBe;
	private GroupBox grbTGBH;
	private RadioButton rbtnTren6;
	private RadioButton rbtnDuoi6;
	private SelectField slfCaLamViec;
	private Button btnLuu;
	private Button btnXoa;
	private Button btnReSet;
	private Button btnTruoc;
	private Button btnTrong;
	private Button btnSau;
	private DscDataObjectSetTable tblTruocSanh;
	private DscDataObjectSetTable tblSauSanh;
	private DscDataObjectSetTable tblNghiSan;
	private BorderPane pneTbl;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private InsuranceDAO ins = new InsuranceDAO();    
    final private int TBLTRUOC = 0;
    final private int TBLTRONG = 1;
    final private int TBLSAU = 2;
    int modeTBL = 0;
	private Column colCaLamViec;
	private Button btnSua;
	private DscField txtEndDay;
	private DscField txtBeginDay;
	//private DAO<T, Serializable>
	public BearProgram() {
		super();

		// Add design-time configured components.
		initComponents();
		moreInit();
	}
	/////////nho them action_daily va them dong vao ban khai sinh khi nhap ve som sau sanh
	public void createDefaultTable(DscDataObjectSetTable tbl)
	{
		tbl.setDefaultHeaderRenderer(new TableCellRenderer(){
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
				Label lbl = new Label();
				lbl.setText((String)value);
				lbl.setFont(new Font(null, Font.PLAIN, new Extent(11, Extent.PT)));
				lbl.setForeground(Color.WHITE);
				TableLayoutData layout = new TableLayoutData();
				layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
				layout.setBackground(new Color(0x0080C0));
				layout.setInsets(new Insets(3));
				lbl.setLayoutData(layout);
				return lbl;
			}
		});
		

	}
	
	private void loadShift() {
			
		ListBinder.bindSelectField(slfCaLamViec, getShift(), true);
		slfCaLamViec.setSelectedIndex(-1);
	}
	
	public void loadThangNS()
	{
		DefaultListModel model = (DefaultListModel) slfThangNS.getModel();
		model.add(6);
		model.add(7);
		model.add(8);
		model.add(9);
		model.add(10);
	}
	
	public static MappingPropertyEditor getShift() {
		MappingPropertyEditor editor = new MappingPropertyEditor();
		BearJdbcDAO dao =  new BearJdbcDAO();
		List<N_SHIFT> list = new ArrayList<N_SHIFT>();
		list = dao.getN_SHIFT();
		for (N_SHIFT data: list) {
			editor.put(Vni2Uni.convertToUnicode(data.getNAME_SHIFT()), data);
		}
		return editor;
	}
	
	public boolean checkTxtempsn()
	{
		if(txtEmpsn.getText().equals(""))
		{
			return false;
		}
		try {
			Long.valueOf(txtEmpsn.getText());
		} catch (Exception e) {
			Application.getApp().showMessageDialog("Lỗi", "Số thẻ phải là số", MessageBox.OK);
			txtEmpsn.setText("");
			return false;
		}
		if(txtEmpsn.getText().length()!=8)
		{
			Application.getApp().showMessageDialog("Lỗi", "Số thẻ không hợp lệ", MessageBox.OK);
			txtEmpsn.setText("");
			return false;
		}
		return true;
	}
	
	public void doActEmpsn()
	{
		BearJdbcDAO dao =  new BearJdbcDAO();
		lblEmpName.setText("");
		lblCaLamViec.setText("");
		tblTruocSanh.getDataObjectSet().clearAll();
		tblSauSanh.getDataObjectSet().clearAll();
		tblNghiSan.getDataObjectSet().clearAll();
		tblTruocSanh.refresh();
		tblSauSanh.refresh();
		tblNghiSan.refresh();
		if(!checkTxtempsn())
		{
			return;
		}			
		if (ins.checkQLyEmpsn(txtEmpsn.getText())==false){
			txtEmpsn.setText("");
			Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên dữ liệu này.", MessageBox.OK);
			return;
		}
		N_EMPLOYEE emp = dao.getEmployee(txtEmpsn.getText());
		if(emp==null)
		{
			Application.getApp().showMessageDialog("Lỗi", "Số thẻ không tồn tại", MessageBox.OK);
			txtEmpsn.setText("");
			return;
		}
		if(emp.getSEX().equals("NU")&&!emp.getEMPSN().equals("13050099"))
		{
			lblEmpName.setText(Vni2Uni.convertToUnicode(emp.getFNAME()+" "+emp.getLNAME()));
			lblCaLamViec.setText(Vni2Uni.convertToUnicode(emp.getSHIFT()));
			// lay du lieu tu cac bang	
			tblTruocSanh.getDataObjectSet().query("EMPSN=?", new Object[]{txtEmpsn.getText()});
			tblTruocSanh.refresh();
			tblSauSanh.getDataObjectSet().query("EMPSN=?", new Object[]{txtEmpsn.getText()});
			tblSauSanh.refresh();
			tblNghiSan.getDataObjectSet().query("EMPSN=?", new Object[]{txtEmpsn.getText()});
			tblNghiSan.refresh();
			
		}
		else
		{
			Application.getApp().showMessageDialog("Lỗi", "Không phải là nữ", MessageBox.OK);
			txtEmpsn.setText("");
			
		}
	}
	
	private void moreInit() {
		tblTruocSanh = new DscDataObjectSetTable();
		tblTruocSanh.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
		tblTruocSanh.setId("0");
		pneTbl.add(tblTruocSanh);
		tblNghiSan = new DscDataObjectSetTable();
		tblNghiSan.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
		tblNghiSan.setId("1");
		tblSauSanh = new DscDataObjectSetTable();
		tblSauSanh.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
		tblSauSanh.setId("2");
		
		createDefaultTable(tblTruocSanh);
		createDefaultTable(tblNghiSan);
		createDefaultTable(tblSauSanh);
		
		bearContentB = new BearDataContent(N_EALRY_BEFOR_B.class);
		//ctpContent.add(bearContentB);
		bearContentB.init(this);
		bearContentA = new BearDataContent(N_EALRY_AFTER_B.class);
		bearContentA.init(this);
		bearContentBear = new BearDataContent(N_VIEW_TIME_BEAR.class);
		bearContentBear.init(this);		
		bearContentRegS = new BearDataContent(N_REGISTER_SHIFT.class);
		bearContentRegS.init(this);		
		bearContentRest = new BearDataContent(N_REST_DETAIL.class);
		bearContentRest.init(this);		
		bearContentTimeBear = new BearDataContent(N_TIME_BEAR.class);
		bearContentTimeBear.init(this);
		bearContentBirCer = new BearDataContent(N_BIRTH_CERTIFICATE.class);
		bearContentBirCer.init(this);
		
		tblTruocSanh.setDataObjectSet(bearContentB.getDataObjectSet());
		tblTruocSanh.setDisplayProperty(new String[]{"EMPSN","BB_DATES","EE_DATES","KIND","NOTE","KEY"});
		tblTruocSanh.setStyleName("Table.DscPageableSortableTable");
		tblTruocSanh.setWidth(new Extent(100, Extent.PERCENT));
		tblTruocSanh.setSelectionEnabled(true);
		tblTruocSanh.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTruocSanh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTableSelectChange(e, bearContentB);
				
			}
		});
		
		tblSauSanh.setDataObjectSet(bearContentA.getDataObjectSet());
		tblSauSanh.setDisplayProperty(new String[]{"EMPSN","BB_DATES","EE_DATES","KIND","GHICHU","KEY"});
		tblSauSanh.setStyleName("Table.DscPageableSortableTable");
		tblSauSanh.setWidth(new Extent(100, Extent.PERCENT));
		tblSauSanh.setSelectionEnabled(true);
		tblSauSanh.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSauSanh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTableSelectChange(e, bearContentA);
				
			}
		});
		
		tblNghiSan.setDataObjectSet(bearContentBear.getDataObjectSet());
		tblNghiSan.setDisplayProperty(new String[]{"EMPSN","HOTEN","BB_DATES","EE_DATES","DATES_BEAR","NOTE","TGBHXHTN","DK_NS"});
		tblNghiSan.setStyleName("Table.DscPageableSortableTable");
		tblNghiSan.setWidth(new Extent(100, Extent.PERCENT));
		tblNghiSan.setSelectionEnabled(true);
		tblNghiSan.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblNghiSan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTableSelectChange(e, bearContentBear);
			}
		});
		
		tblTruocSanh.setDefaultHeaderRenderer(tblTruocSanh.getDefaultHeaderRenderer());
		tblSauSanh.setDefaultHeaderRenderer(tblSauSanh.getDefaultHeaderRenderer());
		tblNghiSan.setDefaultHeaderRenderer(tblNghiSan.getDefaultHeaderRenderer());
		
		loadShift();
		loadThangNS();
		
		txtEmpsn.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtEmpsn.getText().equals(""))
					doActEmpsn();
				
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		txtEmpsn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doActEmpsn();
			}
		});
		btnTruoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTabChange(e);
			}
		});
		btnTrong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTabChange(e);
			}
		});
		btnSau.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				doTabChange(e);
				
			}	
		});
		
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("THÔNG BÁO", "Bạn chắc là muốn xóa", MessageDialog.CONTROLS_YES_NO);
				dlg.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)){
							switch (modeTBL) {
							case TBLTRUOC:
								doXoa(tblTruocSanh);
								break;
							case TBLTRONG:
								doXoa(tblNghiSan);
								break;
							case TBLSAU:
								doXoa(tblSauSanh);	
								break;

							default:
								break;
							}
						}
					}
				});
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
				
			}
		});
		btnReSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doReSet();
			}
		});
	}
	
	public void doTableSelectChange(ActionEvent e, BearDataContent cont) {
		DscDataObjectSetTable tbl = (DscDataObjectSetTable) e.getSource();
		int recNo = tbl.getCurrentSelectRowNo();
		switch (Integer.valueOf(tbl.getId())) {
		case 0:
			N_EALRY_BEFOR_B objB = (N_EALRY_BEFOR_B) ((DataObjectSet)cont.getDataObjectSet()).getDataObject(recNo);
			txtBeginDay.setText(sdf.format(objB.getBB_DATES()));
			txtEndDay.setText(sdf.format(objB.getEE_DATES()));
			break;
		case 1:
			N_VIEW_TIME_BEAR objBear = (N_VIEW_TIME_BEAR) ((DataObjectSet)cont.getDataObjectSet()).getDataObject(recNo);
			txtBeginDay.setText(sdf.format(objBear.getBB_DATES()));
			txtEndDay.setText(sdf.format(objBear.getEE_DATES()));
			if(objBear.getDK_NS()==0)
				rbtnTren6.setSelected(true);
			else
				rbtnDuoi6.setSelected(true);
			break;
		case 2:
			N_EALRY_AFTER_B objA = (N_EALRY_AFTER_B) ((DataObjectSet)cont.getDataObjectSet()).getDataObject(recNo);
			txtBeginDay.setText(sdf.format(objA.getBB_DATES()));
			txtEndDay.setText(sdf.format(objA.getEE_DATES()));
			break;

		default:
			break;
		}
	}
	
	public void doXoa(DscDataObjectSetTable tbl)
	{// chua xong 
		try {
			if(tbl.getCurrentSelectRowNo()==-1)
			{
				Application.getApp().showMessageDialog("Lỗi", "Chưa chọn dòng cần thao tác", MessageBox.OK);
				return;
			}
			else
			{
				Date bdate = new Date();
				Date edate = new Date();
				String empsn = "";
				String tblname1 = "";
				String tblname2 = "";
				String not = "";
				Object obj  = tbl.getDataObjectSet().getDataObject(tbl.getCurrentSelectRowNo());
				switch (modeTBL) {
				case TBLTRUOC:
					if(!checkData(((N_EALRY_BEFOR_B)obj).getEMPSN()
							,(((N_EALRY_BEFOR_B)obj).getBB_DATES().getMonth()+1)<10?"0"+(((N_EALRY_BEFOR_B)obj).getBB_DATES().getMonth()+1):""+(((N_EALRY_BEFOR_B)obj).getBB_DATES().getMonth()+1)
								,(((N_EALRY_BEFOR_B)obj).getBB_DATES().getYear()+1900)+""))
						return;
					bdate = ((N_EALRY_BEFOR_B)obj).getBB_DATES();
					edate = ((N_EALRY_BEFOR_B)obj).getEE_DATES();
					empsn = ((N_EALRY_BEFOR_B)obj).getEMPSN();
					tblname1 = "N_EALRY_BEFOR_B";
					tblname2 = "N_REGISTER_SHIFT";
					break;
				case TBLTRONG:
					if(!checkData(((N_VIEW_TIME_BEAR)obj).getEMPSN()
							,(((N_VIEW_TIME_BEAR)obj).getBB_DATES().getMonth()+1)<10?"0"+(((N_VIEW_TIME_BEAR)obj).getBB_DATES().getMonth()+1):""+(((N_VIEW_TIME_BEAR)obj).getBB_DATES().getMonth()+1)
								,(((N_VIEW_TIME_BEAR)obj).getBB_DATES().getYear()+1900)+""))
						return;

					bdate = ((N_VIEW_TIME_BEAR)obj).getBB_DATES();
					edate = ((N_VIEW_TIME_BEAR)obj).getEE_DATES();
					empsn = ((N_VIEW_TIME_BEAR)obj).getEMPSN();
					tblname1 = "N_TIME_BEAR";
					tblname2 = "N_REST_DETAIL";
					not = ", So thang ns: " + ((N_VIEW_TIME_BEAR)obj).getNOTE() +", TGBH: "+ ((N_VIEW_TIME_BEAR)obj).getTGBHXHTN();
					break;
				case TBLSAU:
					if(!checkData(((N_EALRY_AFTER_B)obj).getEMPSN()
							,(((N_EALRY_AFTER_B)obj).getBB_DATES().getMonth()+1)<10?"0"+(((N_EALRY_AFTER_B)obj).getBB_DATES().getMonth()+1):""+(((N_EALRY_AFTER_B)obj).getBB_DATES().getMonth()+1)
								,(((N_EALRY_AFTER_B)obj).getBB_DATES().getYear()+1900)+""))
						return;

					bdate = ((N_EALRY_AFTER_B)obj).getBB_DATES();
					edate = ((N_EALRY_AFTER_B)obj).getEE_DATES();
					empsn = ((N_EALRY_AFTER_B)obj).getEMPSN();
					tblname1 = "N_EALRY_AFTER_B";
					tblname2 = "N_REGISTER_SHIFT";
					// xoa khoi tbl khai sinh
					bearContentBirCer.getDataObjectSet().query("empsn = ? and date_bear = ?", empsn, bdate);
					bearContentBirCer.getDataObjectSet().delete(0);
					bearContentBirCer.getDataObjectSet().applyUpdate();
					break;

				default:
					break;
				}
				
				bearContentRegS.getDataObjectSet().query("EMPSN=? and shift_date >= ? and shift_date <= ?"
					, empsn ,new java.sql.Date(bdate.getTime()),new java.sql.Date(edate.getTime()));
				tbl.getDataObjectSet().delete(tbl.getCurrentSelectRowNo());
				tbl.getDataObjectSet().applyUpdate();
				tbl.refresh();
				int s = bearContentRegS.getDataObjectSet().getRecordCount();
				for(int i=0; i<s; i++)
				{
					bearContentRegS.getDataObjectSet().delete(0);
				}
				bearContentRegS.getDataObjectSet().applyUpdate();
				//luu trong action daily
				
				N_ACTION_DAILY action = new N_ACTION_DAILY();
				action.setACTIONNAME("DELETE");
				action.setEMPSN(empsn);
				String note = "Ngay bd = "+sdf.format(bdate) + ", Ngay kt = "+ sdf.format(edate) ;
				action.setNOTE(note+not);
				action.setTABLENAME(tblname1);
				FvLogger.log(action);
				
				action.setACTIONNAME("DELETE");
				action.setEMPSN(empsn);
				note = "Tu ngay "+sdf.format(bdate) + " den ngay "+ sdf.format(edate) ;
				action.setNOTE(note);
				action.setTABLENAME(tblname2);
				FvLogger.log(action);
				
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void doSave()
	{
		try {
				///chu xet dieu kien thoi gian, 2 thang truoc sanh , 1 nam sau sanh ke tu khi sanh em be.
			BearJdbcDAO dao =  new BearJdbcDAO();
			N_EMPLOYEE emp = dao.getEmployee(txtEmpsn.getText());
			if(emp==null)
			{
				Application.getApp().showMessageDialog("Lỗi", "Số thẻ không tồn tại", MessageBox.OK);
				txtEmpsn.setText("");
				return;
			}
			
			Date bdate = new Date(); Date edate = new Date(); Date nSdate = new Date(); Date nGKSdate = new Date();
			try {
				bdate = sdf.parse( txtBeginDay.getText());
				edate = sdf.parse( txtEndDay.getText());
				if(!txtNgaySinhBe.getText().equals(""))
					nSdate = sdf.parse( txtNgaySinhBe.getText());
				else
					nSdate = null;
				if(!txtNgayNGKS.getText().equals(""))
					nGKSdate = sdf.parse( txtNgayNGKS.getText());
				else
					nGKSdate = null;
			} catch (ParseException ep) {
				Application.getApp().showMessageDialog("Lỗi", "Ngày tháng không hợp lệ!", MessageBox.OK);
				return;
			}
			

			N_SHIFT sh = new N_SHIFT();
			N_REGISTER_SHIFT re = new N_REGISTER_SHIFT();
			int s;
			Calendar ca = Calendar.getInstance();
			String tblname1="";
			String tblname2="";
			String not ="";
			
			switch (modeTBL) {
			case 0:
				N_EALRY_BEFOR_B objB = new N_EALRY_BEFOR_B();
				objB.setEMPSN(txtEmpsn.getText());
				objB.setBB_DATES(bdate);
				objB.setEE_DATES(edate);
				objB.setNOTE("SIEÂU AÂM");
				objB.setKIND("TRÖÔÙC KHI SANH");
				objB.setKEY("1");
				if(!checkData(objB.getEMPSN(),(objB.getBB_DATES().getMonth()+1)<10?"0"+(objB.getBB_DATES().getMonth()+1):""+(objB.getBB_DATES().getMonth()+1)
						, objB.getBB_DATES().getYear()+1900+""))
					break;

				if(ins.getSimpleJdbcTemplate().queryForInt("select case when ? <= (select max(a.e_dates) " +
						"from n_ealry_befor_b a where a.empsn = ?) then 1 else 0 end from dual ", 
						new java.sql.Date(bdate.getTime()) , objB.getEMPSN())==1)
				{
					Application.getApp().showMessageDialog("Lỗi", "Ngày bắt đầu phải lớn hơn ngày kết thúc trước đó!", MessageBox.OK);
					return;
				}
				if(slfCaLamViec.getSelectedIndex()<0)
				{
					Application.getApp().showMessageDialog("Lỗi", "Chưa chọn ca làm việc!", MessageBox.OK);
					return;
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(bdate);
				//cal.add(Calendar.DATE, 1);
				cal.add(Calendar.MONTH, 2);
				
				if(edate.compareTo(cal.getTime())!=0)
				{
					Application.getApp().showMessageDialog("Lỗi", "Về sớm trước khi sanh 2 tháng\n Kiểm tra lại ngày bắt đầu và kết thúc!", MessageBox.OK);
					return;
				}
				
				tblTruocSanh.getDataObjectSet().add(objB);
				tblTruocSanh.getDataObjectSet().applyUpdate();
				tblTruocSanh.refresh();
				
				//luu vao n_register_shift
				sh = (N_SHIFT)((SelectItem)slfCaLamViec.getSelectedItem()).getValue();
				re = new N_REGISTER_SHIFT();
				re.setEMPSN(txtEmpsn.getText());
				re.setID_SPDEPT(new BearJdbcDAO().getId_sdept(emp.getDEPSN()));
				re.setID_SHIFT(sh.getID_SHIFT());
				re.setTIME_IN(sh.getTIME_IN());
				re.setTIME_OUT(sh.getTIME_OUT());
				re.setNOTE(sh.getNOTE());
				re.setSHIFT_DATE(bdate);
				// xoa nhung dong da co roi luu lai nhung dong moi
				bearContentRegS.getDataObjectSet().query("EMPSN=? and shift_date >= ? and shift_date <= ?"
						, re.getEMPSN() ,new java.sql.Date(bdate.getTime()),new java.sql.Date(edate.getTime()));
				s = bearContentRegS.getDataObjectSet().getRecordCount();
				for(int i=0; i<s; i++)
				{
					bearContentRegS.getDataObjectSet().delete(0);
				}
				bearContentRegS.getDataObjectSet().applyUpdate();
					
				//them dong moi , phai loai tru ngay chu nhat ra
				ca.setTime(bdate);
				while(bdate.compareTo(edate)<=0)
				{
					re.setSHIFT_DATE(bdate);
					ca.add(Calendar.DATE, 1);
					bdate = ca.getTime();
					if((re.getSHIFT_DATE().getDay()+1) == Calendar.SUNDAY)
						{
							continue;
						}
					bearContentRegS.getDataObjectSet().add(re);
				}
				
 				bearContentRegS.getDataObjectSet().applyUpdate();
				tblname1 = "N_EALRY_BEFOR_B";
				tblname2 = "N_REGISTER_SHIFT";
				break;
			case 1:
				N_TIME_BEAR objBear = new N_TIME_BEAR();
				objBear.setEMPSN(txtEmpsn.getText());
				objBear.setBB_DATES(bdate);
				objBear.setEE_DATES(edate);
				objBear.setDATES_BEAR(nSdate);
				objBear.setDATES_GKS(nGKSdate);
				if(rbtnTren6.isSelected()){
					objBear.setDK_NS(0);not+=", Tham gia BH: Tren 6 thang";}
				else{
					objBear.setDK_NS(1);not+=", Tham gia BH: duoi 6 thang";}
				objBear.setNOTE(slfThangNS.getSelectedIndex()+6+"");
				if(!checkData(objBear.getEMPSN(),(objBear.getBB_DATES().getMonth()+1)<10?"0"+(objBear.getBB_DATES().getMonth()+1):""+(objBear.getBB_DATES().getMonth()+1), objBear.getBB_DATES().getYear()+1900+""))
					break;
				if(ins.getSimpleJdbcTemplate().queryForInt("select case when ? <= (select max(a.e_dates) " +
						"from n_time_bear a where a.empsn = ?) then 1 else 0 end from dual ", 
						new java.sql.Date(bdate.getTime()) , objBear.getEMPSN())==1)
				{
					Application.getApp().showMessageDialog("Lỗi", "Ngày bắt đầu phải lớn hơn ngày kết thúc trước đó!", MessageBox.OK);
					return;
				}
				bearContentTimeBear.getDataObjectSet().add(objBear);
				bearContentTimeBear.getDataObjectSet().applyUpdate();
				tblNghiSan.getDataObjectSet().query("EMPSN=?", new Object[]{txtEmpsn.getText()});
				tblNghiSan.refresh();
				
				//luu vao n_rest_detail(chi tiet phep)
				
				N_REST_DETAIL red = new N_REST_DETAIL();
				red.setEMPSN(txtEmpsn.getText());
				red.setTOTAL(1f);
				red.setREST_KIND("NS");
				red.setREST_SAL("L_MATER");
				red.setDATE_OFF(bdate);
				// xoa nhung dong da co roi luu lai nhung dong moi
				bearContentRest.getDataObjectSet().query("EMPSN=? and date_off >= ? and date_off <= ?"
						, red.getEMPSN() ,new java.sql.Date(bdate.getTime()),new java.sql.Date(edate.getTime()));
				s = bearContentRest.getDataObjectSet().getRecordCount();
				for(int i=0; i<s; i++)
				{
					bearContentRest.getDataObjectSet().delete(0);
				}
				bearContentRest.getDataObjectSet().applyUpdate();
					
				//them dong moi , phai loai bo ngay chu nhat ra
				ca.setTime(bdate);
				while(bdate.compareTo(edate)<=0)
				{
					red.setDATE_OFF(bdate);
					ca.add(Calendar.DATE, 1);
					bdate = ca.getTime();
					if((red.getDATE_OFF().getDay()+1) == Calendar.SUNDAY)
						{
							continue;
						}
					bearContentRegS.getDataObjectSet().add(red);
				}
				
 				bearContentRegS.getDataObjectSet().applyUpdate();
				tblname1 = "N_TIME_BEAR";
				tblname2 = "N_REST_DETAIL";
				not += ", So thang NS: "+ objBear.getNOTE();
				break;
			case 2:
				N_EALRY_AFTER_B objA = new N_EALRY_AFTER_B();
				objA.setEMPSN(txtEmpsn.getText());
				objA.setBB_DATES(bdate);
				objA.setEE_DATES(edate);
				objA.setGHICHU("KHAI SINH");
				objA.setKIND("SAU KHI SANH");
				objA.setKEY("1");
				if(!checkData(objA.getEMPSN(),(objA.getBB_DATES().getMonth()+1)<10?"0"+(objA.getBB_DATES().getMonth()+1):""+(objA.getBB_DATES().getMonth()+1), objA.getBB_DATES().getYear()+1900+""))
					break;
				
				if(ins.getSimpleJdbcTemplate().queryForInt("select case when ? <= (select max(a.e_dates) " +
						"from n_ealry_befor_b a where a.empsn = ?) then 1 else 0 end from dual ", 
						new java.sql.Date(bdate.getTime()) , objA.getEMPSN())==1)
				{
					Application.getApp().showMessageDialog("Lỗi", "Ngày bắt đầu phải lớn hơn ngày kết thúc trước đó!", MessageBox.OK);
					return;
				}
				if(slfCaLamViec.getSelectedIndex()<0)
				{
					Application.getApp().showMessageDialog("Lỗi", "Chưa chọn ca làm việc!", MessageBox.OK);
					return;
				}
				
				N_BIRTH_CERTIFICATE bCer = new N_BIRTH_CERTIFICATE();
				bCer.setEMPSN(txtEmpsn.getText());
				bCer.setDATE_BEAR(bdate);
				bCer.setDATES(nGKSdate);
				String tem = sdf.format(ins.getSimpleJdbcTemplate().queryForObject("select max(b_dates) from n_time_bear t where  t.empsn =?",
		            Date.class,txtEmpsn.getText() ));
				bCer.setMONTH(tem.substring(3, 5));// lay date bat dau nghi san
				bCer.setYEAR(tem.substring(6));
				bCer.setNOTE("TINH THEO MUC LUONG CUA THANG BDNS");

				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(bdate);
				cal2.add(Calendar.YEAR, 1);
				//cal2.add(Calendar.DATE, 1);
				
				if(edate.compareTo(cal2.getTime())!=0)
				{
					Application.getApp().showMessageDialog("Lỗi", "Về sớm sau khi sanh 1 năm kể từ ngày sanh bé\n Kiểm tra lại ngày bắt đầu và kết thúc!", MessageBox.OK);
					return;
				}
				
				tblSauSanh.getDataObjectSet().add(objA);
				tblSauSanh.getDataObjectSet().applyUpdate();
				tblSauSanh.refresh();
				//them ngay sinh be vao bang  n_time_bear
				ins.getSimpleJdbcTemplate().update("update n_time_bear set dates_bear = ?" +
						"  where empsn = ? and b_dates <= ? and e_dates >= ?",
						new java.sql.Date(bdate.getTime()),txtEmpsn.getText(),
						new java.sql.Date(bdate.getTime()),new java.sql.Date(bdate.getTime()));
				
				//luu vao n_register_shift-- tu ngay sinh em be den 1 nam.
				sh = (N_SHIFT)((SelectItem)slfCaLamViec.getSelectedItem()).getValue();
				re = new N_REGISTER_SHIFT();
				re.setEMPSN(txtEmpsn.getText());
				re.setID_SPDEPT(new BearJdbcDAO().getId_sdept(emp.getDEPSN()));
				re.setID_SHIFT(sh.getID_SHIFT());
				re.setTIME_IN(sh.getTIME_IN());
				re.setTIME_OUT(sh.getTIME_OUT());
				re.setNOTE(sh.getNOTE());
				re.setSHIFT_DATE(bdate);
				// xoa nhung dong da co roi luu lai nhung dong moi
				bearContentRegS.getDataObjectSet().query("EMPSN=? and shift_date >= ? and shift_date <= ?"
						, re.getEMPSN() ,new java.sql.Date(bdate.getTime()),new java.sql.Date(edate.getTime()));
				s = bearContentRegS.getDataObjectSet().getRecordCount();
				for(int i=0; i<s; i++)
				{
					bearContentRegS.getDataObjectSet().delete(0);
				}
				bearContentRegS.getDataObjectSet().applyUpdate();
					
				//them dong moi , phai loai tru ngay chu nhat ra
				ca.setTime(bdate);
				while(bdate.compareTo(edate)<=0)
				{
					re.setSHIFT_DATE(bdate);
					ca.add(Calendar.DATE, 1);
					bdate = ca.getTime();
					if((re.getSHIFT_DATE().getDay()+1) == Calendar.SUNDAY)
						{
							continue;
						}
					bearContentRegS.getDataObjectSet().add(re);
				}
				
 				bearContentRegS.getDataObjectSet().applyUpdate();
				tblname1 = "N_EALRY_AFTER_B";
				tblname2 = "N_REGISTER_SHIFT";
				
				//them vao bang khai sinh(n_birth_certificate)
				
				bearContentBirCer.getDataObjectSet().add(bCer);
				bearContentBirCer.getDataObjectSet().applyUpdate();
				
				break;
			default:
				break;
			}
			
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setACTIONNAME("INSERT");
			action.setEMPSN(txtEmpsn.getText());
			String note = "Ngay bd = "+sdf.format(bdate) + ", Ngay kt = "+ sdf.format(edate) ;
			action.setNOTE(note+not);
			action.setTABLENAME(tblname1);
			FvLogger.log(action);
			
			action.setACTIONNAME("INSERT");
			action.setEMPSN(txtEmpsn.getText());
			note = "Tu ngay "+sdf.format(bdate) + " den ngay "+ sdf.format(edate) ;
			action.setNOTE(note);
			action.setTABLENAME(tblname2);
			FvLogger.log(action);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public boolean checkData(String empsn, String thang, String nam) {
		if(!checkTxtempsn())
		{
			return false;
		}			
		if (ins.checkQLyEmpsn(txtEmpsn.getText())==false){
			txtEmpsn.setText("");
			Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên dữ liệu này.", MessageBox.OK);
			return false;
		}
		Boolean check =ins.CheckKhoaDataMonth(empsn, thang, nam);
		if (check ==false){
			Application.getApp().showMessageDialog("Thông Báo", "Đã khóa xử lý dữ liệu trong tháng.", MessageBox.OK);
			return false;
		}
		return true;
	}
	
	public void doReSet()
	{
		txtEmpsn.setText("");
		txtBeginDay.setText("");
		txtEndDay.setText("");
	}

	public void doTabChange(ActionEvent e)
	{
		Button btn = (Button) e.getSource();
		pneTbl.remove(0);
		switch (Integer.valueOf(btn.getId())) {
		case 0:
			buttonTabOn(btn);
			buttonTabOff(btnTrong);
			buttonTabOff(btnSau);
			pneTbl.add(tblTruocSanh);
			txtBeginDay.setText("");
			txtEndDay.setText("");
			grbTGBH.setEnabled(false);
			slfCaLamViec.setEnabled(true);
			slfThangNS.setEnabled(false);
			modeTBL = TBLTRUOC;
			txtNgayNGKS.setEnabled(false);
			txtNgayNGKS.setEnabled(false);
			break;
		case 1:
			buttonTabOn(btn);
			buttonTabOff(btnTruoc);
			buttonTabOff(btnSau);
			pneTbl.add(tblNghiSan);
			txtBeginDay.setText("");
			txtEndDay.setText("");
			grbTGBH.setEnabled(true);
			slfCaLamViec.setEnabled(false);
			slfThangNS.setEnabled(true);
			modeTBL = TBLTRONG;
			txtNgayNGKS.setEnabled(false);
			break;
		case 2:
			buttonTabOn(btn);
			buttonTabOff(btnTrong);
			buttonTabOff(btnTruoc);
			pneTbl.add(tblSauSanh);
			txtBeginDay.setText("");
			txtEndDay.setText("");
			grbTGBH.setEnabled(false);
			slfCaLamViec.setEnabled(true);
			slfThangNS.setEnabled(false);
			modeTBL = TBLSAU;
			txtNgayNGKS.setEnabled(true);
			break;

		default:
			break;
		}
	}
	
	public void buttonTabOn(Button btn)
	{
		btn.setBackground(new Color(0xc4d3ff));
		btn.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0xc4d3ff), Border.STYLE_OUTSET));
		
	}
	
	public void buttonTabOff(Button btn)
	{
		btn.setBackground(new Color(0xdbdbdb));
		btn.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
	}
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		ContentPane contentPane2 = new ContentPane();
		splitPane1.add(contentPane2);
		Row row2 = new Row();
		contentPane2.add(row2);
		Button button1 = new Button();
		button1.setText("VỀ SỚM - THAI SẢN");
		button1.setFont(new Font(null, Font.PLAIN, new Extent(20, Extent.PT)));
		button1.setWidth(new Extent(500, Extent.PX));
		button1.setForeground(Color.RED);
		RowLayoutData button1LayoutData = new RowLayoutData();
		button1LayoutData.setInsets(new Insets(new Extent(150, Extent.PX),
				new Extent(20, Extent.PX), new Extent(0, Extent.PX),
				new Extent(20, Extent.PX)));
		button1.setLayoutData(button1LayoutData);
		row2.add(button1);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(300, Extent.PX));
		splitPane1.add(splitPane2);
		ctpContent = new ContentPane();
		SplitPaneLayoutData ctpContentLayoutData = new SplitPaneLayoutData();
		ctpContentLayoutData.setAlignment(new Alignment(Alignment.LEADING,
				Alignment.DEFAULT));
		ctpContent.setLayoutData(ctpContentLayoutData);
		splitPane2.add(ctpContent);
		Column column1 = new Column();
		column1.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(0,
				Extent.PX)));
		ctpContent.add(column1);
		Grid grid4 = new Grid();
		grid4.setHeight(new Extent(85, Extent.PX));
		column1.add(grid4);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.TOP));
		label1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(5, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		grid4.add(label1);
		txtEmpsn = new DscField();
		txtEmpsn.setId("txtEmpsn");
		txtEmpsn.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtEmpsn.setWidth(new Extent(200, Extent.PX));
		txtEmpsn.setMaximumLength(8);
		GridLayoutData txtEmpsnLayoutData = new GridLayoutData();
		txtEmpsnLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.TOP));
		txtEmpsnLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						5, Extent.PX)));
		txtEmpsn.setLayoutData(txtEmpsnLayoutData);
		grid4.add(txtEmpsn);
		Label label10 = new Label();
		GridLayoutData label10LayoutData = new GridLayoutData();
		label10LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		label10.setLayoutData(label10LayoutData);
		grid4.add(label10);
		lblEmpName = new Label();
		lblEmpName.setId("lblEmpName");
		GridLayoutData lblEmpNameLayoutData = new GridLayoutData();
		lblEmpNameLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		lblEmpName.setLayoutData(lblEmpNameLayoutData);
		grid4.add(lblEmpName);
		Label label12 = new Label();
		grid4.add(label12);
		lblCaLamViec = new Label();
		lblCaLamViec.setId("lblCaLamViec");
		GridLayoutData lblCaLamViecLayoutData = new GridLayoutData();
		lblCaLamViecLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		lblCaLamViec.setLayoutData(lblCaLamViecLayoutData);
		grid4.add(lblCaLamViec);
		Grid grid1 = new Grid();
		column1.add(grid1);
		Label label2 = new Label();
		label2.setText("Thời gian về sớm");
		label2.setForeground(Color.RED);
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						5, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		grid1.add(label2);
		Label label3 = new Label();
		grid1.add(label3);
		Row row4 = new Row();
		grid1.add(row4);
		Label label4 = new Label();
		label4.setText("Từ");
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX), new Extent(
						5, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row4.add(label4);
		txtBeginDay = new DscField();
		txtBeginDay.setWidth(new Extent(100, Extent.PX));
		row4.add(txtBeginDay);
		Label label15 = new Label();
		label15.setText("Đến");
		RowLayoutData label15LayoutData = new RowLayoutData();
		label15LayoutData.setInsets(new Insets(new Extent(5, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX), new Extent(
						5, Extent.PX)));
		label15.setLayoutData(label15LayoutData);
		row4.add(label15);
		txtEndDay = new DscField();
		txtEndDay.setWidth(new Extent(100, Extent.PX));
		row4.add(txtEndDay);
		Row row7 = new Row();
		column1.add(row7);
		Grid grid2 = new Grid();
		grid2.setColumnWidth(0, new Extent(100, Extent.PX));
		column1.add(grid2);
		Label label6 = new Label();
		label6.setText("Số tháng NS");
		GridLayoutData label6LayoutData = new GridLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						5, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		grid2.add(label6);
		slfThangNS = new SelectField();
		slfThangNS.setId("slfThangNS");
		slfThangNS.setEnabled(false);
		slfThangNS.setWidth(new Extent(170, Extent.PX));
		GridLayoutData slfThangNSLayoutData = new GridLayoutData();
		slfThangNSLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		slfThangNS.setLayoutData(slfThangNSLayoutData);
		grid2.add(slfThangNS);
		Label label7 = new Label();
		label7.setText("Ngày nộp GKS");
		GridLayoutData label7LayoutData = new GridLayoutData();
		label7LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		label7.setLayoutData(label7LayoutData);
		grid2.add(label7);
		txtNgayNGKS = new DscField();
		txtNgayNGKS.setId("txtNgayNGKS");
		txtNgayNGKS.setEnabled(false);
		txtNgayNGKS.setWidth(new Extent(164, Extent.PX));
		txtNgayNGKS.setDisabledBackground(new Color(0xc0c0c0));
		txtNgayNGKS.setMaximumLength(10);
		GridLayoutData txtNgayNGKSLayoutData = new GridLayoutData();
		txtNgayNGKSLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		txtNgayNGKS.setLayoutData(txtNgayNGKSLayoutData);
		grid2.add(txtNgayNGKS);
		Label label8 = new Label();
		label8.setText("Ngày sinh bé");
		label8.setVisible(false);
		GridLayoutData label8LayoutData = new GridLayoutData();
		label8LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		label8.setLayoutData(label8LayoutData);
		grid2.add(label8);
		txtNgaySinhBe = new DscField();
		txtNgaySinhBe.setId("txtNgaySinhBe");
		txtNgaySinhBe.setVisible(false);
		txtNgaySinhBe.setWidth(new Extent(164, Extent.PX));
		txtNgaySinhBe.setMaximumLength(10);
		GridLayoutData txtNgaySinhBeLayoutData = new GridLayoutData();
		txtNgaySinhBeLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		txtNgaySinhBe.setLayoutData(txtNgaySinhBeLayoutData);
		grid2.add(txtNgaySinhBe);
		Row row3 = new Row();
		column1.add(row3);
		grbTGBH = new GroupBox();
		grbTGBH.setId("grbTGBH");
		grbTGBH.setEnabled(false);
		grbTGBH.setTitle("TG tham gia BHXH-TN");
		grbTGBH.setHidden(false);
		grbTGBH.setWidth(new Extent(250, Extent.PX));
		row3.add(grbTGBH);
		rbtnTren6 = new RadioButton();
		rbtnTren6.setId("rbtnTren6");
		rbtnTren6.setText("Từ 6 tháng");
		ButtonGroup bg = new ButtonGroup();
		rbtnTren6.setGroup(bg);
		rbtnTren6.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		rbtnTren6.setDisabledForeground(new Color(0xc0c0c0));
		grbTGBH.add(rbtnTren6);
		rbtnDuoi6 = new RadioButton();
		rbtnDuoi6.setId("rbtnDuoi6");
		rbtnDuoi6.setText("Dưới 6 tháng");
		rbtnDuoi6.setGroup(bg);
		rbtnDuoi6.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5,
				Extent.PX)));
		rbtnDuoi6.setDisabledForeground(new Color(0xc0c0c0));
		grbTGBH.add(rbtnDuoi6);
		colCaLamViec = new Column();
		column1.add(colCaLamViec);
		Label label9 = new Label();
		label9.setText("Các ca làm việc");
		ColumnLayoutData label9LayoutData = new ColumnLayoutData();
		label9LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		label9.setLayoutData(label9LayoutData);
		colCaLamViec.add(label9);
		slfCaLamViec = new SelectField();
		slfCaLamViec.setId("slfCaLamViec");
		slfCaLamViec.setWidth(new Extent(271, Extent.PX));
		ColumnLayoutData slfCaLamViecLayoutData = new ColumnLayoutData();
		slfCaLamViecLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX)));
		slfCaLamViec.setLayoutData(slfCaLamViecLayoutData);
		colCaLamViec.add(slfCaLamViec);
		Grid grid3 = new Grid();
		grid3.setColumnWidth(0, new Extent(135, Extent.PX));
		grid3.setColumnWidth(1, new Extent(135, Extent.PX));
		column1.add(grid3);
		btnLuu = new Button();
		btnLuu.setId("btnLuu");
		btnLuu.setText("Lưu");
		btnLuu.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_INSET));
		btnLuu.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnLuu.setBackground(new Color(0x43afc2));
		btnLuu.setWidth(new Extent(135, Extent.PX));
		btnLuu.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX)));
		btnLuu.setPressedEnabled(true);
		btnLuu.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnLuu.setRolloverEnabled(true);
		GridLayoutData btnLuuLayoutData = new GridLayoutData();
		btnLuuLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		btnLuuLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(1, Extent.PX), new Extent(
						0, Extent.PX)));
		btnLuu.setLayoutData(btnLuuLayoutData);
		grid3.add(btnLuu);
		btnXoa = new Button();
		btnXoa.setId("btnXoa");
		btnXoa.setText("Xóa");
		btnXoa.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_INSET));
		btnXoa.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnXoa.setBackground(new Color(0x43afc2));
		btnXoa.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX)));
		btnXoa.setPressedEnabled(true);
		btnXoa.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnXoa.setRolloverEnabled(true);
		GridLayoutData btnXoaLayoutData = new GridLayoutData();
		btnXoaLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		btnXoaLayoutData.setInsets(new Insets(new Extent(1, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		btnXoa.setLayoutData(btnXoaLayoutData);
		grid3.add(btnXoa);
		btnReSet = new Button();
		btnReSet.setId("btnReSet");
		btnReSet.setText("ReSet");
		btnReSet.setPressedBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_INSET));
		btnReSet.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnReSet.setBackground(new Color(0x43afc2));
		btnReSet.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX)));
		btnReSet.setPressedEnabled(true);
		btnReSet.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnReSet.setRolloverEnabled(true);
		GridLayoutData btnReSetLayoutData = new GridLayoutData();
		btnReSetLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.TOP));
		btnReSetLayoutData.setInsets(new Insets(new Extent(1, Extent.PX),
				new Extent(2, Extent.PX), new Extent(1, Extent.PX), new Extent(
						5, Extent.PX)));
		btnReSet.setLayoutData(btnReSetLayoutData);
		grid3.add(btnReSet);
		btnSua = new Button();
		btnSua.setId("btnReSet");
		btnSua.setText("Sửa");
		btnSua.setVisible(false);
		btnSua.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_INSET));
		btnSua.setRolloverBorder(new Border(new Extent(2, Extent.PX),
				new Color(0x80ffff), Border.STYLE_OUTSET));
		btnSua.setBackground(new Color(0x43afc2));
		btnSua.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX)));
		btnSua.setPressedEnabled(true);
		btnSua.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0x80ffff), Border.STYLE_GROOVE));
		btnSua.setRolloverEnabled(true);
		GridLayoutData btnSuaLayoutData = new GridLayoutData();
		btnSuaLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.TOP));
		btnSuaLayoutData.setInsets(new Insets(new Extent(1, Extent.PX),
				new Extent(2, Extent.PX), new Extent(0, Extent.PX), new Extent(
						5, Extent.PX)));
		btnSua.setLayoutData(btnSuaLayoutData);
		grid3.add(btnSua);
		SplitPane splitPane3 = new SplitPane();
		splitPane3.setSeparatorPosition(new Extent(33, Extent.PX));
		splitPane3.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.add(splitPane3);
		Row row1 = new Row();
		splitPane3.add(row1);
		btnTruoc = new Button();
		btnTruoc.setId("0");
		btnTruoc.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnTruoc.setText("Trước  khi sanh");
		btnTruoc.setBackground(new Color(0xc4d3ff));
		btnTruoc.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX), new Extent(0, Extent.PX), new Extent(7, Extent.PX)));
		btnTruoc.setWidth(new Extent(100, Extent.PX));
		btnTruoc.setRolloverBackground(new Color(0xb1ddf1));
		btnTruoc.setBorder(new Border(new Extent(2, Extent.PX), new Color(
				0xc4d3ff), Border.STYLE_OUTSET));
		btnTruoc.setRolloverEnabled(true);
		row1.add(btnTruoc);
		btnTrong = new Button();
		btnTrong.setId("1");
		btnTrong.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnTrong.setText("Nghỉ san");
		btnTrong.setBackground(new Color(0xdbdbdb));
		btnTrong.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX), new Extent(0, Extent.PX), new Extent(7, Extent.PX)));
		btnTrong.setWidth(new Extent(100, Extent.PX));
		btnTrong.setRolloverBackground(new Color(0xb1ddf1));
		btnTrong.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
		btnTrong.setRolloverEnabled(true);
		row1.add(btnTrong);
		btnSau = new Button();
		btnSau.setId("2");
		btnSau.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnSau.setText("Sau khi sanh");
		btnSau.setBackground(new Color(0xdbdbdb));
		btnSau.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(7,
				Extent.PX), new Extent(0, Extent.PX), new Extent(7, Extent.PX)));
		btnSau.setWidth(new Extent(100, Extent.PX));
		btnSau.setRolloverBackground(new Color(0xb1ddf1));
		btnSau.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0xc0c0c0), Border.STYLE_SOLID));
		btnSau.setRolloverEnabled(true);
		row1.add(btnSau);
		pneTbl = new BorderPane();
		FillImageBorder fillImageBorder1 = new FillImageBorder(new Color(
				0xc0c0c0), new Insets(new Extent(2, Extent.PX)), new Insets(
				new Extent(2, Extent.PX)));
		pneTbl.setBorder(fillImageBorder1);
		splitPane3.add(pneTbl);
	}
}
