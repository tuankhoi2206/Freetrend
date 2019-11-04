package ds.program.fvhr.ngan.ui.KiemTraTienTruBHYT;

import dsc.echo2app.program.DefaultProgram;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jaxen.function.SubstringFunction;

import com.sun.star.util.DateTime;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_N_HEALTH_R;
import ds.program.fvhr.util.OBJ_EMPSN;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.program.DefaultProgram;
import echopointng.ComboBox;

public class KiemTraTienTruBHYTProgram extends DefaultProgram{


	private ResourceBundle resourceBundle;
	private ComboBox cbx_fact;
	private ComboBox cbx_group;
	private ComboBox cbx_dept;
	private DscDateField date_export;
	public Date date_input;
	public Date date_att_pre;


	IGenericDAO<N_N_HEALTH_R, String> obj_dao;
	OBJ_UTILITY obj_util;
	OBJ_EMPSN obj_emp;
	SimpleDateFormat sf = OBJ_UTILITY.Get_format_date();
	Calendar	ca = Calendar.getInstance();

	public static String date_str ;
	public static String name_fact;
	public static String str;
	String user_name = Application.getApp().getLoginInfo().getUserID();//Lay user name tu form login
	String madv = "";
	String note_quit_work_again = "";
	String note_increase_labour_times_1 = "";
	long sothang_dc_bt     	= 0; //28/09/2012 them bien toan cuc nay de neu = 2 update lai luong cban trong update_data_bt khi giua quy d/c duoc 2 thang (theo yc moi dc 2T ...(haizzzzz))
	boolean flag_dc         = false; //11/10/2012 neu false lay luong cu, neu true lay luong moi chi ap dung cho BT va NS con NV thi lay salary_cb_nv ben duoi

	private Button btn_export;
	private long num_work_again = 0;
	private long money_work_again = 0;
	private String note_work_again = "";
	private long salary_b_quit_not_enough_dayWork = 0; //khong su dung nua
	private long salary_cb_nv   = 0; //htai sdung cai nay  cho NV
	/**
	 * Creates a new <code>N_HEALTH_REPORT_MProgram</code>.
	 */
	public KiemTraTienTruBHYTProgram() {
		super();

		obj_dao 	= Application.getApp().getDao(N_N_HEALTH_R.class); 
		obj_util 	= new OBJ_UTILITY();
		obj_emp 	= new OBJ_EMPSN();
		// Add design-time configured components.
		initComponents();

	}

	@Override
	protected void doLayout() {
		super.doLayout();
		date_export.getDateChooser().setLocale(Locale.ENGLISH);
		date_export.setDateFormat(sf);
		date_export.getTextField().setText(sf.format(ca.getTime()));
		date_export.setMouseCursor(DATAMODE_EDIT);

	}

	private void doAction_Fact(ActionEvent e) {

		//	cbx_group.setListModel(obj_util.Get_Model_Group(cbx_fact.getText()));
		cbx_group.setText("");
	}

	private void doAction_Group(ActionEvent e) {
		//	cbx_dept.setListModel(obj_util.Get_Model_Dept(cbx_fact.getText(), cbx_group.getText()));
		cbx_dept.setText("");
	}


	private void doAction_export(ActionEvent e) {


		date_str = date_export.getText();
		name_fact = cbx_fact.getText();
		try {
			date_input = sf.parse(date_str);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String  month_			= sf.format(date_input).substring(3,5);
		String  year_			= sf.format(date_input).substring(6,10);
		str		  = "";	
		String sql = "";
		//	String user = ""; //user lay tu trong dspb01_limit
		if(date_str.equals("") || date_str == null){
			obj_util.ShowMessageError("Chua chon thang");
		}else{
			//Begin kiem tra thang cap nhat phai nho hon thang hien hanh
			Calendar current = Calendar.getInstance();
			Calendar componentCal = date_export.getSelectedDate();
			current.set(Calendar.DAY_OF_MONTH, componentCal.get(Calendar.DAY_OF_MONTH));
			current.set(Calendar.MONTH, current.get(Calendar.MONTH)-1);
			if (current.compareTo(componentCal)<0){
				obj_util.ShowMessageError("Khong hop le vi thang "+month_+"/"+year_+" chua cap nhat du lieu");
				return;
			}
			//End kiem tra thang cap nhat phai nho hon thang hien hanh

			if (name_fact.equals("")) {name_fact = "ALL";}
			sql = 

				"select t.userid from dspb01_limit t\n" +
				" where t.userid = '"+user_name+"'"+
				" and t.limit = '"+name_fact+"'"+
				" AND T.PB_ID = 'KiemTraTienTruBHYT'";

			List<String> list_limit = obj_util.Exe_Sql_String(sql);
			//	 	user 	= list_limit.get(0);//user lay tu trong dspb01_limit
			if(list_limit != null && list_limit.size() > 0)//kiem tra co quyen thao tac hay khong?neu co vao day xet tiep
			{

				if ( name_fact.equals("ALL"))
				{
					String sql1 = 

						"select T.NAME_FACT \n" +
						"from n_n_health_r_status t\n" + 
						"WHERE TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '"+month_+"/"+year_+"'"+ 
						"AND T.STATUS = 1 "+
						" AND (T.NAME_FACT = 'TB' OR T.NAME_FACT = 'FVL' OR T.NAME_FACT = 'FVLS' "+
						" OR T.NAME_FACT = 'KDAO' OR T.NAME_FACT = 'FVJ')";

					List<Object> list_status = obj_util.Exe_sql_nfield_1row(sql1, 1);

					if(list_status != null && list_status.size() == 5 )
					{
						//bdau them 07/12/2011
						String str_date = sf.format(date_input);

						String month 	=  str_date.substring(3, 5);
						String year 	=  str_date.substring(6,10);

						Connection con			=	Application.getApp().getConnection();
						String sql3 				= "{call N_CHECK_HEALTH_EXPORT(?,?,?,?)}";

						try{
							CallableStatement stm   = con.prepareCall(sql3); 
							stm.setString(1, name_fact);
							stm.setString(2,month);
							stm.setString(3,year);
							stm.setString(4,user_name);


							stm.execute();

						}catch (Exception e1) {
							System.out.println(e1);
						}finally{
							try {
								con.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						//Kthuc them 07/12/2011	//Luu thao tac truoc roi moi xuat ra excel
						str = "";
						obj_util.doExport("Health_RTask_KT","KT_BH_KT");	

					}

					else//*
					{
						OBJ_UTILITY.ShowMessageOK("HIEN TAI CO XUONG CHUA CAP NHAT DU LIEU THANH CONG NEN KHONG THE XUAT EXCEL");
					}		

				}else //Nguoc lai cua if( name_fact = ALL)
				{
					String sql2 = 

						"select T.NAME_FACT \n" +
						"from n_n_health_r_status t\n" + 
						"WHERE T.NAME_FACT = '"+name_fact+"'"+
						"AND TO_CHAR(T.MONTH_REPORT,'MM/YYYY') = '"+month_+"/"+year_+"'"+ 
						"AND T.STATUS = 1";

					List<Object> list_status2 = obj_util.Exe_sql_nfield_1row(sql2, 1);

					if(list_status2 != null && list_status2.size() > 0 )
					{
						str = " and d.name_fact = '"+name_fact+"'";
						obj_util.doExport("Health_RTask_KT","KT_BH_KT");
					}else//*
					{
						OBJ_UTILITY.ShowMessageOK("DU LIEU "+name_fact+" CHUA CAP NHAT THANH CONG NEN KHONG THE XUAT EXCEL");
					}//*
				}
			}else 
			{
				if (!name_fact.equals("ALL"))
					OBJ_UTILITY.ShowMessageOK("BAN KHONG CO QUYEN THAO TAC XUONG "+name_fact+"");
				else
				{
					OBJ_UTILITY.ShowMessageOK("VUI LONG CHON XUONG CAN XUAT DU LIEU");
				}
			}

		}
	}


	private void doAction_update(ActionEvent e) {
		String str_err ="";
		date_str = date_export.getText();
		name_fact = cbx_fact.getText();
		/*if(date_str.equals("") || date_str == null  ){

			obj_util.ShowMessageError("Chua chon thang");

		}else{*/

		try {

			date_input = sf.parse(date_str);

		} catch (ParseException e1) {

			obj_util.ShowMessageError("Ngay thang sai dinh dang " + date_str);
			return;
		}
		//Begin kiem tra thang cap nhat phai nho hon thang hien hanh
		Calendar current = Calendar.getInstance();
		Calendar componentCal = date_export.getSelectedDate();
		current.set(Calendar.DAY_OF_MONTH, componentCal.get(Calendar.DAY_OF_MONTH));
		current.set(Calendar.MONTH, current.get(Calendar.MONTH)-1);//current.get(Calendar.MONTH)-1
		if (current.compareTo(componentCal)<0){ //old <0
			obj_util.ShowMessageError("Khong hop le vi thang cap nhat phai nho hon thang hien hanh");
			return;
		}
		//End kiem tra thang cap nhat phai nho hon thang hien hanh
		str_err = "OK";

		//str_err = Add_Emp_New(); // THEM NHUNG NGUOI MOI KI HOP DONG LAN DAU TIEN
		
	//	if(str_err.equals("OK"))
	//	{
	//		str_err = Add_Emp_Quit_Work_Again();// Them or cap nhat nhung nguoi nghi viec di lam lai 1 tay hoac 20 tay
	//	}	
			Add_Emp_New();
			Add_Emp_Quit_Work_Again();
			str_err = Update_Emp();
	//	if(str_err.equals("OK")){

		//	str_err = Update_Emp();	// UPDATE TRANG THAI

			if(str_err.equals("OK")){

				//OBJ_UTILITY.ShowMessageOK("CAP NHAT THANH CONG DU LIEU NGAY : " + sf.format(date_input));
				//Begin 31/10/2011 Ngan them de kiem tra la xuong do da cap nhat dl thanh cong chua
				MessageDialog dlg = new MessageDialog(MessageDialog.CONTROLS_OK,"CAP NHAT THANH CONG DU LIEU NGAY : " + sf.format(date_input));
				dlg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
							//									bc.setMessageVisible(true);
							String str_date = sf.format(date_input);

							String month 	=  str_date.substring(3, 5);
							String year 	=  str_date.substring(6,10);

							Connection con			=	Application.getApp().getConnection();
							String sql 				= "{call N_CHECK_HEALTH(?,?,?,?)}";

							try{
								CallableStatement stm   = con.prepareCall(sql); 
								stm.setString(1, name_fact);
								stm.setString(2,month);
								stm.setString(3,year);
								stm.setString(4,user_name);
								stm.execute();
								stm.close();

							}catch (Exception e1) {
								System.out.println(e1);
							}finally{
								try {
									con.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}


						}
					}
				});


				//End 31/10/2011 Ngan them de kiem tra la xuong do da cap nhat dl thanh cong chua


			}


	}


	// 
	
		private String Update_Emp() {
		String  empsn_			= "";
		String  str_err			= "OK";
		String  month_			= sf.format(date_input).substring(3,5);
		String  year_			= sf.format(date_input).substring(6,10);
		long 	num				= 0;
		Date 	date_now_1 		= OBJ_UTILITY.MONTH_NOW("01", date_input);
		Date	date_now_15		= OBJ_UTILITY.MONTH_NOW("15", date_input);
		Date 	date_pre_15 	= OBJ_UTILITY.MONTH_PRE("15", date_input);
		Date	date_lock		= null;
		Date    bear_date_b		= null;
		String  note			= "";
		int 	n_bear			= 0;
		boolean flag_update		= true;
		String  name_fact		= cbx_fact.getText();
		String  name_group		= cbx_group.getText();
		//	OBJ_EMPSN obj_emp;
		String sql 	= "";
		String  my				= sf.format(date_input).substring(3,10);
		String  dmy_01			= "01/"+my;
		
		Date bear_date_e	= null;	// ngay ket thuc NS

		long money =	0;

		System.out.println(name_fact);
		/**
		 * ======== THEO DOI NHUNG NGUOI DI LAM BINH THUONG ====
		 * 	 	status = 1
		 */


		//			sql =
		//				"SELECT h.empsn\n" +
		//				"FROM n_n_health_r h,n_get_data e,n_department d\n" + 
		//				"WHERE h.empsn = e.empsn\n" + 
		//				"      AND e.depsn = d.id_dept\n" + 
		//				str+ 
		//				" AND e.months = '"+month_+"'"+
		//				" AND e.years = '"+year_+"'"+
		//				"      AND  h.status = 1\n" +
		//				"" +
		//				"\n" ;

		




		/*// begin 22/09/2011 sua neu k chon xuong thi update toan bo	
		if (name_fact.equals("")) {
		sql = 

			"SELECT h.empsn\n" +
			"FROM n_n_health_r h,n_get_data e,n_department d\n" + 
			"WHERE h.empsn = e.empsn\n" + 
			"      AND e.depsn = d.id_dept\n" + 
			" AND e.months = '"+month_+"'"+
			" AND e.years = '"+year_+"'"+
			"      AND h.status = 1\n" + 
	//		"		and h.empsn in ('11111111')"+// test

			"" +
			"\n" ;
		}else

		sql =
			"SELECT h.empsn\n" +
			"FROM n_n_health_r h,n_get_data e,n_department d\n" + 
			"WHERE h.empsn = e.empsn\n" + 
			"      AND e.depsn = d.id_dept\n" + 
			"      AND (d.name_fact ='"+name_fact+"')\n" + 
			" AND e.months = '"+month_+"'"+
			" AND e.years = '"+year_+"'"+
			"      AND h.status = 1\n" +
//			"		and h.empsn in ('11111111')"+       // test

			"" +
			"\n" ;

		// end 22/09/2011 sua neu k chon xuong thi update toan bo
		 */

		//*********BINH THUONG***************/
		sql = "select t.empsn from n_n_health_r t where t.status=1"+
		"\n "+

		" and (to_char(t.lock_date,'MM/yyyy') <> '"+my+"'"+
		"                                    or t.lock_date is null)"+

		" AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n" +
		"                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n" + 
		"                         <= TO_DATE('"+dmy_01+"','DD/MM/YYYY'))"+


	//	"		and t.empsn in ('06110118')"+// test
		"\n";
		List<String> list_1 = obj_util.Exe_Sql_String(sql);
		
		for(String empsn : list_1){
			sothang_dc_bt = 0;
			madv=obj_emp.Get_depsn(empsn, date_input);
			if (!madv.equals("ENULL")){
				if (thuoc_xuong(madv)){
					//update
					N_N_HEALTH_R obj_update = obj_dao.findById(empsn);

					//	empsn 			= obj_update.getEMPSN();
					date_lock 		= obj_update.getLOCK_DATE() ==null? null:obj_update.getLOCK_DATE();
					if(empsn.equals("10020288")){
						System.out.println("CHECK ");
					}
					System.out.println("________________ " + empsn +"_____________");
					if(Check_Locked(date_lock,date_input) == false){
						continue; // false : khong cho phep Update_Data09040984
					}

					obj_emp 	= new OBJ_EMPSN(empsn,date_input);

					/**
					 * 	________NGHI VIEC ________
					 */

					if((obj_update.getNUM_USED() != 0 && obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV)
							||(obj_update.getNUM_USED() == 0 && obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV //10/02/2012 : NV k tra the,tang lai 20 tay xet xem den ky gia han the tiep chua ma nghi viec
									&& obj_update.getTHANG_TANGMOI()!= null && obj_update.getSTATUS_TT_TMOI() ==2 )
									||(obj_update.getNUM_USED() == 0 && obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV //28/02/2012 : Xet tang moi lai 1 tay rui nghi viec
											&& obj_update.getTHANG_TANGMOI()!= null && obj_update.getSTATUS_TT_TMOI() ==1 ))

					{

						System.out.println("XET NGHI VIEC");
						Update_Emp_Off_Work(obj_update,obj_emp,false);
						continue;

					}

					/**
					 * _________ DANG KY NGHI SAN ___________
					 */

					if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS && obj_emp.Get_Reg_Bear()){
						System.out.println("XET DANG KY NGHI SAN");
						Update_Emp_Reg_Bear(obj_update,obj_emp);
						continue;

					}
					
					/** 02/10/2012 THEM VAO NEU DANG BINH THUONG MA NSU LAI SUA THANG NGHI SAN TRUOC THANG DANG KY TRINH THI
					 *  UPDATE STATUS = 0 DE VE NGHI SAN
					 *  --------NSU THAY DOI DANG BINH THUONG NHAP NGHI SAN-----
					 */
					
					if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS && obj_emp.Get_Reg_Bear()== false){//Tai thoi diem ky trinh k co nghi san, nsu thay doi, neu k sua lai cho nay CT bo qua mac du thang 7 da tru duoc//
						System.out.println("THAY DOI THONG TIN NGHI SAN");//vd so the 99110178 t7 tru 3T(7,8,9), T9 lai nhap nsan tu 22/07/2012 den 22/11/2012
						int n_Bear			= obj_emp.Get_N_Bear(); 
						bear_date_b 	= obj_emp.Get_Bear_Date_Begin();
						bear_date_e	    = OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_Bear) ;
						String bear_date_e_str = sf.format(bear_date_e);
						String note_ = obj_update.getNOTE()+"//=> "+my+ ":Dang hien hanh Thay doi thong tin nghi san(Nsan tu: "+bear_date_b+" den: "+bear_date_e_str+")";
						obj_update.setSTATUS((long) 0);
						obj_update.setNOTE(note_);
						obj_update.setBEAR_DATE_E(bear_date_e);
						obj_dao.update(obj_update);
						//xuong duoi se xet giong nhu dang nghi san

					}

					/**
					 * ______BINH THUONG _________________
					 */

					if(obj_update.getNUM_USED() == 0 || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT){
						// chi Update nhung so the co date_input > LOCK_DATE
						System.out.println("XET BINH THUONG");
						Update_Num(obj_update, date_input,false);
						continue;
					}

				} //--------------------END : for(Object[] arr_info : list)
				else
				{
					continue;
				}
			}	
		}	
		/**
		 * ======= THEO DOI NHUNG NGUOI DANG NGHI SAN =========
		 *		status = 0
		 */

		/*DetachedCriteria obj_dc_emp_0 = DetachedCriteria.forClass(N_N_HEALTH_R.class);
				obj_dc_emp_0.add(Restrictions.eq("STATUS", Long.valueOf(0)));
				List<N_N_HEALTH_R> list_0 = obj_dao.findByCriteria(50000, obj_dc_emp_0);*/
		sql = "select t.empsn from n_n_health_r t where t.status=0"+
		"\n "+
		" and to_char(t.lock_date,'MM/yyyy') <> '"+my+"'"+
		" AND T.EMPSN NOT IN (SELECT S.EMPSN FROM N_NOT_INSURANCE S\n" +
		"                                           WHERE TO_DATE('01/'||TO_CHAR(S.DATES,'MM/YYYY'),'DD/MM/YYYY')\n" + 
		"                         <= TO_DATE('"+dmy_01+"','DD/MM/YYYY'))"+
//		"		and t.empsn in ('06110118')"+// test
		"\n";
		List<String> list_0 = obj_util.Exe_Sql_String(sql);

		//	for(N_N_HEALTH_R obj_update : list_0){ 
		for(String empsn : list_0){
			sothang_dc_bt = 0;
			madv=obj_emp.Get_depsn(empsn, date_input);
			if (!madv.equals("ENULL")){
				if (thuoc_xuong(madv)){
					//update
					N_N_HEALTH_R obj_update = obj_dao.findById(empsn);
					flag_update     = true;
					empsn_			= obj_update.getEMPSN();
					num				= obj_update.getNUM()==null?0:obj_update.getNUM();
					date_lock		= obj_update.getLOCK_DATE()==null?null:obj_update.getLOCK_DATE();
					note			= obj_update.getNOTE() + " -- Thang " + month_ +":";

					if(empsn_.equals("09100868")){
						System.out.println("CHECK ");
					}

					if(Check_Locked(date_lock, date_input) == false){	
						continue; // false : khong dc update_data : bo qua vong lap nay
					}


					obj_emp			= new OBJ_EMPSN(empsn_, date_input);
					bear_date_b		= obj_emp.Get_Bear_Date_Begin();
					n_bear			= obj_emp.Get_N_Bear();

					System.out.println("________________ " + empsn +"_____________");
					System.out.println(" --> TRANG THAI : " +obj_emp.Get_Status_Work());

					if(obj_emp.Get_Status_Work() != 0){
						System.out.println("CHECK : " + empsn_);


					}


					switch ((int)num) {
					/**
					 *		num = 0 : _ Ngay tu dau khong tru dc tien  _ (phai xet KT_NS < ngay 15) -> BT 
					 *				 |								  |_ (num = 0 && bear_date_b == null )		
					 *				 |_ Tai num = 1 , nhung KT_NS > ngay 15 (dc uu tien) => num = num - 1 = 0
					 *						-> xang thang sau (num = 0 && bear_date_b = null) 
					 *											
					 *											// bear_date_b = null vi khong lay dc 
					 *											// trang thai NS cua thang sau												 
					 */
					case 0:	// NS vao sau ngay 15 = Nsan vao truoc ngay 15 ma k tru dc het so thang nsan
						if(     bear_date_b == null  
								||	OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_bear).compareTo(date_now_15) <= 0 ){

							obj_update.setSTATUS(Long.valueOf(1)); // NS vao, tro ve trang thai BT
							note = note + " -- NS vao ";
							obj_update.setNOTE(note);
							if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV){ // NS vao - NV 

								Update_Emp_Off_Work(obj_update, obj_emp,true);
								flag_update = false;

							}else{
								Update_Num(obj_update, date_now_1, true);
							}



						}else{

							note = note + " Dang NS " ;
							obj_update.setMONEY(Long.valueOf(0));
							obj_update.setNOTE(note);
							UPDATE_DATA_BT(obj_update);
						}
						break;

						// co truong hop nao num = 0 va ngay KT_NS > 15 khong ?????
						// --> khi tru het 4 thang DK_NS thi num = 1
						// --> neu < 15 -> status = BT
						// -->     > 15 -> num = 0 && next month : status = BT
						// => khong co truong hop nay

					case 1:// NS vao truoc ngay 15 = nsan vao sau ngay 15 ma chua tru den duoc thang nsan cuoi cung
						if(		bear_date_b == null 
								|| OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_bear).compareTo(date_now_15) <= 0 ){

							obj_update.setSTATUS(Long.valueOf(1)); // NS vao, tro ve trang thai BT
							note = note + " NS vao :";
							obj_update.setNOTE(note);
							if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV){	// NS vao - NV (tang moi)

								obj_update.setNUM(Long.valueOf(0));
								Update_Emp_Off_Work(obj_update, obj_emp,true);
								flag_update = false;
							}else{
								Update_Num(obj_update, date_now_1, true);
							}

						}else{	
							obj_update.setMONEY(Long.valueOf(0));
							obj_update.setNUM(num - 1);		// giam 1 thang
							obj_update.setNOTE(note + " Dang NS ");
							UPDATE_DATA_BT(obj_update);

							if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV){  // NS vao - NV lun

								//	obj_update.setSTATUS(Long.valueOf(-1));
								obj_update.setNOTE(note + "Dang NS -- NV truoc khi tang moi cho NS vao"); 
								UPDATE_DATA_BT(obj_update);
								Update_Emp_Off_Work(obj_update, obj_emp,true);
								flag_update = false;

							}

						}
						break;
							
					case 2 : case 3: case 4 : case 5 :case 6:case 7:case 8:case 9:case 10://11/11/2011 them 7,8,9,10 vi co nghi san >=6T
						long salary_total 	= 0;
						obj_update.setMONEY(Long.valueOf(0));
						obj_update.setNUM(num - 1);		// giam 1 thang
						//		bear_date_e = obj_update.getBEAR_DATE_E();
						// lay ra ngay ket thuc NS
						// neu be hon ngay 15 cua thang thien tai && status = 0
						// => xet dieu chinh luong va cap nhat lai luong Salary_M
						// truong hop nay la NS vao van dang con Num tru tien BH 6 thang tu dau quy
						/*if(bear_date_e.before(date_now_15) && obj_update.getSTATUS() == 0){

										salary_total 		= obj_emp.Get_Salary_Total_By_Month(empsn_, date_input);
										long salary_e = obj_emp.Get_Salary_Basic(empsn_, date_input); // luong thang hien tai
										long salary_b = obj_update.getSALARY_B();
										money 	=  money + (long)((salary_e - obj_update.getSALARY_B()) * 1.5/100);
										if (salary_total == 0){
											money = 0;
											note 	=note+ " --Chua DC luong " + month_;
											obj_update.setSALARY_M(obj_update.getSALARY_B());
										}else {
											money = money;
											note 	=note + " -- DC luong " + month_;
											obj_update.setSALARY_M(salary_e);
									//		obj_update.setDATE_B(bear_date_e);
										}


										obj_update.setMONEY(money);
										//obj_update.setDATE_B(bear_date_e);//Ngan them vao 04/07/2011 de xet lay luong NS vao
										note	= note + " NS vao " + bear_date_e;
										obj_update.setNOTE(note);//Ngan them vao 04/07/2011 de xet lay luong NS vao

										// TRUONG HOP NAY CO THE SAI VI THANG NAY KHONG DU LUONG DE TRU TIEN DIEU CHINH LUONG

									}*/
						obj_update.setNOTE(note + " Dang NS ");
						UPDATE_DATA_BT(obj_update);

						break;
					default:
						break;
					}

					if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NV && flag_update){
						Update_Emp_Off_Work(obj_update , obj_emp,false);
						continue;
					}

				}else
				{
					continue;
				}
					
				}
			}
			 //---------------------END for(N_N_HEALTH_R obj_update : list_0){

		return str_err;

	}




	private boolean thuoc_xuong(String madv) {//DUNG CHO KY TRINH BHYT

		String sql = "select count(*) from n_department d"+" where d.name_fact='"+cbx_fact.getText()+"' and d.id_dept='"+madv+"'";
		BigDecimal c = (BigDecimal) obj_util.Exe_Sql_Obj(sql);
		return c.compareTo(BigDecimal.ZERO)>0;
	}
	
	
	private void Update_Num(N_N_HEALTH_R obj_health_r, Date date_input_, boolean flag_bear) {
		String message_error 	= "";
		String month_			= sf.format(date_input_).substring(3, 5);
		String year_			= sf.format(date_input_).substring(6,10);
		String empsn			= obj_health_r.getEMPSN();
		long num 				= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long num_h 				= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long num_k				= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();//XET TRONG RECOUNT_NV NEU LUONG K DU TIEN DE TRU THI LAY NUM_K
		long debt 				= obj_health_r.getDEBT()==null?0:obj_health_r.getDEBT();
		long money				= obj_health_r.getMONEY()==null?0:obj_health_r.getMONEY();
		long num_used_			= obj_health_r.getNUM_USED()==null?0:obj_health_r.getNUM_USED();
		long status_			= obj_health_r.getSTATUS()==null?0:obj_health_r.getSTATUS();
		String note				= obj_health_r.getNOTE();
		long note_int			= 1;
		String note_recount		="";		
		long money_dc			= 0;
		long debt_dc  			= 0;
		
		//Begin 12/10/2011 Ngan them bien de xet nviec -->tang moi
		String month_tm			= "";
		String year_tm			= "";
		Date thang_tangmoi		= obj_health_r.getTHANG_TANGMOI();
		long status_tt_tm		= obj_health_r.getSTATUS_TT_TMOI()==null?0:obj_health_r.getSTATUS_TT_TMOI();
		if (thang_tangmoi != null){
			month_tm			= sf.format(thang_tangmoi).substring(3, 5);
			year_tm			= sf.format(thang_tangmoi).substring(6,10);
		}
		Date lock_date_			= obj_health_r.getLOCK_DATE();
		//End 12/10/2011 Ngan them bien de xet nviec -->tang moi

		long num_max 			= 0;
		long num_recount		= 0;
		OBJ_EMPSN obj_emp_		= new OBJ_EMPSN(empsn,date_input_);
		long salary_b			= obj_emp_.Get_Salary_Basic(empsn, date_input);//luong hien tai
		float heso_1_5			= (float) (1.5* salary_b)/100; 	// he so 1.5%/T
		ArrayList<Long> list_recount ;

		boolean flag_init 		= false;
		boolean flag_recount	= false;
		String thang_tm_nv = "";
		String thang_lock_date_01 = "01/"+month_+"/"+year_;//lay ngay 01 cua lock_date de so sanh thang_tm

		note	= note + "  // => THANG " + month_;
        

		switch (Integer.valueOf(month_)) {

		case 1:	case 4:	case 7:	case 10:
			num_max	 				= 3;
			flag_init 	 			= true; 			//  bat co luu toan bo so the 
			break;

		case 2:	case 5:	case 8:	case 11:
			num_max					= 2;
			flag_init 				= false;
			break;
		case 3:	case 6:	case 9:	case 12:
			num_max 				= 1;
			flag_init 				= false;
			break;
		default:
			break;
		}

		//* Neu la nghi viec k tra the da tinh luong thoi viec trong thoi gian gtri the co hieu luc -> tang moi vao 20 tay
		if ((thang_tangmoi != null)  
				&&(num_used_ == 0)&& ((lock_date_ == null)||(lock_date_ != null)) && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 3))//status_tt_tm == 3 nghi viec k tra the(lock_date co the !=null vi chay tang 20tay thang nay truoc khi chay ky trinh cho thang truoc
		{

			if ((month_tm.equals("01")) || (month_tm.equals("02"))|| (month_tm.equals("03")) 
					|| (month_tm.equals("04")) || (month_tm.equals("05"))|| (month_tm.equals("06")))
			{
				thang_tm_nv = "01/07/"+year_tm; 
				try {
					obj_health_r.setTHANG_TANGMOI(sf.parse(thang_tm_nv));
					obj_health_r.setSTATUS_TT_TMOI((long) 2);//status_tt_tm == 2 cho den thoi han gia han the moi
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if ((month_tm.equals("07")) || (month_tm.equals("08"))|| (month_tm.equals("09")) 
					|| (month_tm.equals("10")) || (month_tm.equals("11"))|| (month_tm.equals("12")))
			{
				long nam_tm_nv = Integer.valueOf(year_tm)+ 1;
				thang_tm_nv = "01/01/"+String.valueOf(nam_tm_nv);
				try {
					obj_health_r.setTHANG_TANGMOI(sf.parse(thang_tm_nv));
					obj_health_r.setSTATUS_TT_TMOI((long) 2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//** Neu la nghi viec k tra the da tinh luong thoi viec trong thoi gian gtri the co hieu luc -> tang moi vao 20 tay

		/**
		 * 	Neu la dau quy || tang moi || NS vao = > init NUM
		 */

		if ((flag_init && (lock_date_ != null) && ((thang_tangmoi == null)||(thang_tangmoi != null)) && (num_used_ != 0))//dau quy (08/02/2012 thang_tangmoi != null vi co the di lam lai tang moi lai thang tang moi < lock_date
				||((num_used_ == 0)&& (lock_date_ == null) && (thang_tangmoi == null))//Tang moi do ky hop dong 
				|| ((thang_tangmoi != null) && (num_used_ == 0)&& ((lock_date_ == null)||(lock_date_ != null)) && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 1))//nghi viec tang moi hoan toan
				|| ((thang_tangmoi != null) && (num_used_ == 0)&& (lock_date_ != null) && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 2))//status_tt_tm == 2 trong trang thai cho gia han the = thang_tm
				|| 	flag_bear){		//Nsan vao					

			//	note = "||==>> INIT thang " + month_ + " : ";
			num_k--;
			if ((num_k <= 0) || flag_bear){ num_k = 0; }

			//  if (flag_bear){num_k = 0;}

			if (debt == 0 )	{//Ngan them 23/09/2011
				num 			= num_max;
				num_recount		= num_max ;
				debt			= 0;
				money 			= (long) (num *  heso_1_5);
				flag_recount	= true;	
				note			= note +  " tru " + num +"T";
				num_used_++;
				status_			= 1;
				obj_health_r.setSALARY_B(salary_b);


				if(obj_emp_.Get_Status_Work() == OBJ_EMPSN.empsn_NV){
					note = note + " -- Tang moi - NV : " ;
					Update_Emp_Off_Work(obj_health_r, obj_emp_, true);
					return;
				}
			}else{//else debt <> 0
				num_recount		= num_max + debt ;
				num 			= num_max;
				money 			= (long) (num_recount *  heso_1_5);
				flag_recount	= true;	
				note			= note +  " tru " + num +"T + "+debt+"T no";
				num_used_++;
				status_			= 1;
				debt			= 0;
				obj_health_r.setSALARY_B(salary_b);

				if(obj_emp_.Get_Status_Work() == OBJ_EMPSN.empsn_NV){
					note = note + " -- Tang moi - NV : " ;
					//	obj_health_r.setSALARY_B(salary_b);//Ngan them vao 30/08/2011 vi tang moi thang truoc la thang cuoi quy,thang nay nghi viec
					Update_Emp_Off_Work(obj_health_r, obj_emp_, true);
					return;
				}
			}
		}
		else if ((thang_tangmoi != null) && (num_used_ == 0)&& ((lock_date_ == null)||(lock_date_ != null)) && (month_tm.equals(month_)) && (year_tm.equals(year_)) && (status_tt_tm == 3))
			//Them else if nay vi neu roi vao truong hop nay thi k chay vao xet nua
		{
			obj_health_r.setSTATUS_TT_TMOI((long) 2);
			note = "||==>>Nviec khong tra the tang lai T"+month_tm+"/"+year_tm+" --> Den ky gia han the tiep theo moi tang lai BHYT ";

		}
		else if ((thang_tangmoi != null) && (num_used_ == 0)&& ((lock_date_ == null)||(lock_date_ != null)) 
				&& ((Integer.valueOf(year_tm) == Integer.valueOf(year_) && Integer.valueOf(month_tm) > Integer.valueOf(month_))
						||	(Integer.valueOf(year_tm) > Integer.valueOf(year_)) )
						&& (status_tt_tm == 3))
			//Them else if nay vi neu chay tang 20 tay truoc khi chay ky trinh cho thang truoc(vd:chay tang 20 tay T3 truoc khi chay ky trinh T2)
		{
			note = note+"";
		}

		else if ((thang_tangmoi != null) && (num_used_ == 0)&& (lock_date_ != null) 
				&& ((Integer.valueOf(year_tm) == Integer.valueOf(year_) && Integer.valueOf(month_tm) > Integer.valueOf(month_))
						||	(Integer.valueOf(year_tm) > Integer.valueOf(year_) )  )
						&& (status_tt_tm == 2))
			//Them else if nay vi neu chua den ky gia han the thi khong xet
		{
			note = note+"";
		}
		else if ((thang_tangmoi != null) && (num_used_ == 0)&& (lock_date_ == null) 
				&& ((Integer.valueOf(year_tm) == Integer.valueOf(year_) && Integer.valueOf(month_tm) > Integer.valueOf(month_))
						||	(Integer.valueOf(year_tm) > Integer.valueOf(year_) )  )
						&& (status_tt_tm == 1))

			//Them else if nay vi neu roi vao truong hop nay thi k chay vao xet nua
		{
			note = "";
		}

		else { 


			num 	--;				// theo doi moi thang NUM - 1
			money	= 0;			// binh thuong khong tru tien vi da tru dau quy
			num_k--;
			if (num_k <= 0){ num_k = 0; }

			if((salary_b > obj_health_r.getSALARY_B()) && (num_k >= 1)  ) { 	// DC luong
				//money 	=  money + (long)((salary_b - obj_health_r.getSALARY_B()) * 1.5/100);
				money_dc = (long)((salary_b - obj_health_r.getSALARY_B()) * (num_k*1.5/100));
				flag_recount = true;
			//	note 	=note + " -- DC luong " + month_;
				note 	=note + " -- DC luong " +num_k +"T("+ month_+" --> "+((Integer.valueOf(month_)+num_k) - 1)+")";
			}
			switch (Integer.valueOf(month_)) {	
			case 2:case 5:	case 8:	case 11:
				if(debt > 0 ){ // xet DEBT

					num_recount		= debt;
					num 			= 2;
					money 			= money + (long) (debt * heso_1_5);	// tru no thang truoc
					flag_recount	= true;
					note  			= note + " -- Tru them no thang truoc : " + debt +"T";
					debt 			= 0;
					//Neu dau quy k tru duoc thi phai set salary_b lai
					if (num_k == 0){
						obj_health_r.setSALARY_B(salary_b);}
					sothang_dc_bt   = 2; //01/10/2012 gan gia lap la neu tru duoc thi xuong update_data_bt se set lai luong cb de cuoi quy k dieu chinh so voi luong dau quy nua

				}
			case 3:case 6:	case 9:	case 12:
				if(debt > 0 ){ // xet DEBT --cuoi quy ma con no thi chac chan la co no it nhat 1 thang cuoi quy

					num_recount		= debt;
					num 			= 1;
					money 			= money + (long) (debt * heso_1_5);	// tru no thang truoc
					flag_recount	= true;
					note  			= note + " -- Tru them no thang truoc : " + debt +"T";
					debt 			= 0;
					

				}	


			}
		}

        money = money + money_dc;
		
		list_recount = ReCount_BT(empsn, num_recount,num_k,money_dc,date_input_);	
        

		if(flag_recount && list_recount != null){ // chi recount lai so tien khi co tru tien
			
			num 		= list_recount.get(0);
			debt		= list_recount.get(1);
			money		= list_recount.get(2); 
			note_int	=   list_recount.get(3);
			debt_dc =  list_recount.get(4);

			long num_tru = num_recount - list_recount.get(1);
			if (num_tru <= 0){num_tru = 0;} 

			//if((note_int == 0) && (status_tt_tm == 0)){ //old
			
			if(note_int == 0 ){ 
				if (debt_dc == 0 && sothang_dc_bt == 2 ){

					note_recount	= " --> Xet luong thuc nhan d/c luong duoc "+sothang_dc_bt+"T + tru duoc: " +num_tru+"T  //--Con no: "+list_recount.get(1)+"T";
				}else if(debt_dc == 0 && sothang_dc_bt == 1){
					note_recount	= " --> Xet luong thuc nhan d/c luong duoc "+sothang_dc_bt+"T + tru duoc: " +num_tru+"T  //--Con no: "+list_recount.get(1)+"T";
				}else if (debt_dc == 0 && sothang_dc_bt == 0){
					note_recount	= " --> Xet luong thuc nhan d/c luong duoc "+num_k+"T + tru duoc: " +num_tru+"T  //--Con no: "+list_recount.get(1)+"T";
					//29/09/2012 gia lap cho thang giua quy neu chua tru den thang cuoi quy ma co su dc luong 1 thang thi gia lap gan cho sothang_dc_bt = 2 de ct cap nhat lai 
					    // luong can ban = luong thang giua quy, de qua cuoi thang khong xet d/c nua
					if(month_.equals("02")||month_.equals("05") || month_.equals("08")||  month_.equals("11") )
					{
						sothang_dc_bt = 2; //gan ao cho truong hop nay de CT chay vao UPdate_data_bt update lai luong cb vi t7 chi tru duoc 2T(7,8) phai up lai luong cb de qua T9 chi d/c so voi luong T8, k co tac dung gi o day
					}
				}else if (debt_dc == 1){
					note_recount	= " --> Xet luong thuc nhan khong du tien de tru //--Con no: " + list_recount.get(1) +"T"; 
				}else//khong co su thay doi luong bao gom ca luong tai thang ky trinh < luong dau quy (lay luong dau quy) _ gan debt_dc = 4 cho truong hop nay
				{
					note_recount	= " --> Xet luong thuc nhan tru duoc: " +num_tru+"T  //--Con no: "+list_recount.get(1)+"T";
				}
			}else{
				note_recount = "";
			}
		}
		

		obj_health_r.setNUM(num);

		obj_health_r.setNUM_USED(num_used_);
		obj_health_r.setSTATUS(status_);
		obj_health_r.setDEBT(debt);
		obj_health_r.setMONEY(money);
		obj_health_r.setNOTE(note + note_recount);
		obj_health_r.setDEBT_DC(debt_dc);

		//	UPDATE_DATA(obj_health_r);
		UPDATE_DATA_BT(obj_health_r);

	}


	private void Update_Emp_Reg_Bear(N_N_HEALTH_R obj_health_r, OBJ_EMPSN obj_emp) {
		//Theo nhu thong bao Nsu ycau khong tru tien nhung nguoi Nsan (IT nhan don
		//ngay 13/06/2012--> nhung nguoi nsan se k co trong ky trinh nua thi: 
		//set num va money ve = 0  

		String month_		= sf.format(date_input).substring(3,5); 
		String year_		= sf.format(date_input).substring(6,10);

		String empsn		= obj_health_r.getEMPSN();
		long num_h 			= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long num 			= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long money			= obj_health_r.getMONEY()==null?0:obj_health_r.getMONEY();
		long debt			= obj_health_r.getDEBT()==null?0:obj_health_r.getDEBT();
		long debt_h			= obj_health_r.getDEBT()==null?0:obj_health_r.getDEBT();
		String note			= obj_health_r.getNOTE();
		long num_used_		= obj_health_r.getNUM_USED() == null?0:obj_health_r.getNUM_USED();

		long salary_e		= obj_emp.Get_Salary_Basic(empsn, date_input);
		int n_Bear			= obj_emp.Get_N_Bear(); 					// so thang dang ky NS
		Date bear_date_b 	= obj_emp.Get_Bear_Date_Begin();
		String day_bear_	= sf.format(bear_date_b).substring(0, 2); 	// ngay dang ky NS
		Date bear_date_e	= OBJ_UTILITY.MONTH_ROLL(bear_date_b, true, n_Bear) ;

		List<Long> list_recount;
		long note_int		= 1;
		String note_recount	= "";
		int num_max			= 0;
		long num_recount	= 0;
		long money_dc 		= 0;
		long debt_dc  		= 0; //debt_dc == 0 dchinh dc,1 k dchinh dc
		long sothang_dc		= 0;


		long note_int1		= 0;


		boolean flag_DC_luong = false;

		// binh thuong sang thang la NUM -- 
		num --;
		num_h--;

		long heso_1_5		= (long) (salary_e * 1.5/100);

		long thang_bd_ns    = 0;
		long thang_kt_ns    = 0;
		long nam_bd_ns		= 0;
		long nam_kt_ns		= 0;
		//*Begin 21/06/2012: Nsan neu da tru tien truoc dau quy thi den thang nsan phai tra tien lai
		float heso_1_5_return	= (float) (1.5* obj_health_r.getSALARY_B())/100;
		long money_return		= 0; //so tien tra lai cho nhan vien
		//End 21/06/2012: Nsan neu da tru tien truoc dau quy thi den thang nsan phai tra tien lai

		//* Begin 15/10/2011 xet ghi chu cho Nsan
		if (Integer.valueOf    (day_bear_) <= 15)//nsan bd <= 15
		{
			thang_bd_ns		= Integer.valueOf(month_);
			nam_bd_ns		= Integer.valueOf(year_);

			if ((Integer.valueOf(month_)+n_Bear) -1 > 12)
			{
				thang_kt_ns		= ((Integer.valueOf(month_)+n_Bear) -1)-12;
				nam_kt_ns		= Integer.valueOf(year_)+1;
			}else
			{
				thang_kt_ns		= (Integer.valueOf(month_)+n_Bear) -1;
				nam_kt_ns		= Integer.valueOf(year_);
			}
		}else //nsan bd > 15
		{
			if (Integer.valueOf(month_)== 12)
			{
				thang_bd_ns		= (Integer.valueOf(month_)+ 1)-12;
				nam_bd_ns		= Integer.valueOf(year_)+1;

				thang_kt_ns		= Integer.valueOf(month_)+n_Bear-12;
				nam_kt_ns		= Integer.valueOf(year_)+1;	
			}else// month_ < 12
			{
				thang_bd_ns		= Integer.valueOf(month_)+1;
				nam_bd_ns		= Integer.valueOf(year_);

				if ((Integer.valueOf(month_)+n_Bear) > 12)
				{
					thang_kt_ns		= (Integer.valueOf(month_)+n_Bear)-12;
					nam_kt_ns		= Integer.valueOf(year_)+1;
				}else
				{
					thang_kt_ns		= Integer.valueOf(month_)+n_Bear;
					nam_kt_ns		= Integer.valueOf(year_);
				}
			}
		}


		String tgian_bd_ns = String.valueOf(thang_bd_ns)+"/"+String.valueOf(nam_bd_ns);
		String tgian_kt_ns = String.valueOf(thang_kt_ns)+"/"+String.valueOf(nam_kt_ns);
		String tgian_ns	   = tgian_bd_ns+" --> "+tgian_kt_ns;//dung ghi chu cho Nsan

		//* End 15/10/2011 xet ghi chu cho Nsan

		if(Integer.valueOf(day_bear_) > 15){ // phai chiu BH thang hien tai
			num 	--;

			//			flag_DC_luong = true;

		}
		//nghi san k dieu chinh luong, tru bu tiep voi muc luong moi va ky trinh la muc luong moi tai thoi diem nghi san
		note 	= note + "  //=> THANG " + month_ + " :";
		if(num_h < 0){num_h = 0;}
		switch (Integer.valueOf(month_)) {

		case 1: case 4: case 7: case 10:
			if (Integer.valueOf(day_bear_) <= 15){//12/09/2011 Ngan them dk nay
				num				= 0;
				num_h			= 0;
				num_recount		= debt_h;//21/06/2012 nsu yc k tru tien nhung thang NSan
				money			= heso_1_5 * debt_h;
				note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru "+debt_h+"T no "; 
				debt			= 0;
				flag_DC_luong	= false;
				obj_health_r.setSALARY_B(salary_e);
			}else//12/09/2011 Ngan them dk ns > 15
			{
				num			= 1;//vi chiu fi cho T04 or T10
				num_h		= 0;
				num_recount	= num+debt_h;//21/06/2012 nsu yc k tru tien nhung thang NSan
				money		= heso_1_5 * (num+debt_h);
				note		= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru T"+month_+"+"+debt_h+"T no ";
				debt		= 0;
				flag_DC_luong	= false;
				obj_health_r.setSALARY_B(salary_e);
			}

			break;

		case 2: case 5: case 8: case 11: 
			if (Integer.valueOf(day_bear_) <= 15){//12/09/2011 Ngan them dk nay
				if(num_h >= 1) //Neu num >= 1 thi num_recount khong cong them debt
				{//21/06/2012 Dau quy da tru den thang bd dk Nsan --> tra lai tien
					num_recount 	= 0;
					money			= heso_1_5 * num_recount;
					money_return    =(long) (-num_h * heso_1_5_return);
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tra lai tien "+num_h+"T ";
					num				= 0;
					debt			= 0;
					flag_DC_luong	= false;
				}
				else//num_h <= 0
				{
					num_recount		= debt_h-2;
					money			= heso_1_5 * num_recount;
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T no ";
					num				= 0;
					debt			= 0;
					flag_DC_luong	= false;
				}


			}else//12/09/2011 Ngan them dk ns > 15 // fai d/c luong neu da tru dc truoc do
			{
				if ( num_h >= 1)
				{
					num_recount 	= 0;
					money			= heso_1_5 * num_recount;
					money_return 	= (long)(-(num_h-1)*heso_1_5_return);	
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T,Tra lai tien "+(num_h-1)+"T";
					num				= 1;
					debt			= 0;
					flag_DC_luong	= true;
				}
				else
				{
					num_recount		= debt_h-1;
					money			= heso_1_5 * num_recount;
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T ";
					num				= 1;
					debt			= 0;
					flag_DC_luong	= false;
				}
			}
			break;
		case 3: case 6: case 9: case 12: 
			if (Integer.valueOf(day_bear_) <= 15){//12/09/2011 Ngan them dk nay
				if(num_h >= 1) //Neu num >= 1 thi num_recount khong cong them debt
				{
					num_recount 	= 0;
					money			= heso_1_5 * num_recount;
					money_return    =(long) (-num_h * heso_1_5_return);
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T,tra lai tien "+num_h+"T";
					num				= 0;
					debt			= 0;
					flag_DC_luong	= false;
				}
				else//num_h <= 0
				{
					num_recount		= debt_h-1;
					money			= heso_1_5 * num_recount;
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T ";
					num				= 0;
					debt			= 0;
					flag_DC_luong	= false;
				}
			}else//Ngan them dk ns > 15
			{
				if ( num_h >= 1)
				{
					num_recount 	= 0 ;
					money			= heso_1_5 * num_recount;
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T ";
					num				= 1;
					debt			= 0;
					flag_DC_luong	= true;
				}
				else
				{
					num_recount		= debt_h;
					money			= heso_1_5 * num_recount;
					note			= note + " -- Dang ki NS : "+bear_date_b+" : " + n_Bear + "T("+tgian_ns+") => tru them "+num_recount+"T ";
					num				= 1;
					debt			= 0;
					flag_DC_luong	= false;
				}
			}
			break;

		default:
			break;
		}	
		//Xet d/c luong tai thang Nsan
		/*	if (num_h >= 1)//Vi dau quy da tru duoc den T02
			{
				flag_DC_luong = true;
			}
			else
			{
				flag_DC_luong = false;
			}*/




		// dieu chinh luong tai thang cuoi truoc khi DK_NS, no phai chiu BH thang do.
		if(flag_DC_luong) { 


			if (salary_e <= obj_health_r.getSALARY_B()) {
				note = note;
			//	money = money;
				money_dc = 0;
			}
			else{
				note 	=note + " -- DC luong T" + month_;
				//	note 	=note + " + DC luong " +num_h +"T("+ month_+" --> "+((Integer.valueOf(month_)+num_h) - 1)+")";//10/09/2011 Ngan sua
				money_dc = (long)((salary_e - obj_health_r.getSALARY_B()) * (1.5/100));//10/09/2011 Ngan sua so tien dieu chinh
				//money = money +money_dc;

				//	money 	=  money + (long)((salary_e - obj_health_r.getSALARY_B()) * (num_h*1.5/100));		 // dieu chinh luong
			}

		}

		money = money+money_dc+money_return;//vi gio cong ca tien tra lai de tinh so tien bi tru
		//list_recount = ReCount(empsn, num_recount,date_input);
		//Old//	list_recount = ReCount_ns(empsn, num_recount,num_h,money_dc ,date_input);//10/09/2011 Ngan them num_h va money_dc vi xet uu tien d/c luong truoc
		//New:23/06/2012:
		list_recount  = ReCount_ns_new(empsn,money, num_recount,num, money_dc, date_input);//d/c luong max nhat la 1

		if(list_recount!= null){
			// neu co kha nang tru dc het tien thi lay NUM
			long num_tru = num_recount - list_recount.get(1);//26/09/2011 Ngan them(get(1) so thang no sau khi da xet

			money		= list_recount.get(2); 
			note_int	=   list_recount.get(3);
			debt_dc     =  list_recount.get(4);
			sothang_dc  = list_recount.get(5);
			//Xet lai num cho Nsan	
			switch (Integer.valueOf(month_)) {
			case 1: case 4: case 7: case 10:
				if (num_tru <= debt_h)
				{
					if (num_tru <= 0){num_tru = 0;}
					
					if (Integer.valueOf(day_bear_) <= 15)
					{//Neu ngay Nsan <= ngay 15 thi tru no-> khong co trong ky trinh thang do(num=0)
						num 	= 0;
						debt	= debt_h - num_tru;
					}else
					{//Neu ngay Nsan > ngay 15 thi tru YT thang hien tai con no thi treo lai->co trong ky trinh thang do (num = 1)
						if(num_tru <= 0)
						{
							num		= 0;
							debt	= debt_h - (num_tru-1);
						}else
						{
							num		= 1;
							debt	= debt_h - (num_tru-1);
						}
					}
				} 
				else 
				{
					num 		=  num_tru - debt_h;  
					debt		= 0;
				}
				break;
			case 2: case 5 :case 8: case 11:

				if (Integer.valueOf(day_bear_) <= 15){	
					if ( num_h >=1 )//thuong neu num_h >= 1 thi khong con no nua
					{
						num  = 0;
						debt = 0;
					}
					else 
					{//num_h <= 0 thi debt it nhat la 2T
						if(num_tru <= (debt_h-2)){ num = 0 ; debt = (debt_h - 2)-num_tru;}
						else { num = 0; debt = 0; }
					}

				}else //(Integer.valueOf(day_bear_) > 15)
				{
					if ( num_h >=1 )//T1 tru 2 or 3T//ok
					{
						num  = 1;
						debt = 0;

					}else //num_h <= 0 debt it nhat la 2T
					{
						if( num_tru == 0 ){num = 0; debt = debt_h - 1;}//ok
						else if( num_tru > 0 && num_tru <= (debt_h -1) )//ok
						{
							num 	= 1;
							debt	= (debt_h-1)-num_tru;
						}else{ num = 1; debt = 0; }//ok
					}
				}
				break;
			case 3: case 6 :case 9: case 12:
				if (Integer.valueOf(day_bear_) <= 15){	
					if ( num_h >=1 )//ok
					{
						num  = 0;
						debt = 0;
					}
					else 
					{//num_h <= 0 thi debt it nhat la 1T
						if(num_tru <= (debt_h-1)){ num = 0 ; debt = (debt_h - 1)-num_tru;}//ok

					}
				}else //(Integer.valueOf(day_bear_) > 15)
				{
					if ( num_h >=1 )//T1 tru 2 or 3T//ok
					{
						num  = 1;
						debt = 0;

					}else //num_h <= 0 debt it nhat la 1T
					{
						if( num_tru == 0 ){num = 0; debt = debt_h;}//ok
						else if( num_tru > 0 && num_tru <= debt_h )
						{
							num 	= 1;
							debt	= debt_h - num_tru;//ok
						}
					}
				}
				break;
			}
			//End xet lai num cho nsan  	
			if(note_int == 0 ){ 
				if (debt_dc == 0 && sothang_dc == 2 ){

					note_recount	= " --> Xet luong thuc nhan chi d/c luong duoc "+sothang_dc+"T("+ month_+" --> "+(Integer.valueOf(month_)+1)+") + tru : " +num_tru+"T -- Con no "+debt+"T";
				}else if(debt_dc == 0 && sothang_dc == 1){
					note_recount	= " --> Xet luong thuc nhan chi d/c luong duoc "+sothang_dc+"T("+ month_+") + tru : " +num_tru+"T -- Con no "+debt+"T";
				}else if (debt_dc == 0 && sothang_dc == 0){
					note_recount	= " --> Xet luong thuc nhan chi tru duoc "+num_tru+" T, Con no "+debt+"T";
				}else if (debt_dc == 1){
					note_recount	= " --> Xet luong thuc nhan khong du tien de tru dieu chinh, Con no "+debt+"T";

				}else//khong co su thay doi luong bao gom ca luong tai thang ky trinh < luong dau quy (lay luong dau quy) _ gan debt_dc = 4 cho truong hop nay
				{
					note_recount	= " --> Xet luong thuc nhan tru duoc: " +num_tru+"T  //--Con no: "+debt+"T";
				}
			}else{//Neu tong luong ok se tru d/c luong tai thang nsan:vd nsan 06/02-->06/05  ma luong t02<> t01 thi d/c luong t02 va t03
				note_recount = "-- Luong OK ";
			}

		}



		if(num < 0){
			num = 0; 
		}




		obj_health_r.setSTATUS(Long.valueOf(0)); // chuyen sang trang thai theo doi NS
	//	obj_health_r.setSALARY_B(salary_e);
		obj_health_r.setNUM(num);
		obj_health_r.setMONEY(money);
		//	obj_health_r.setDEBT(Long.valueOf(0)); // tru truoc thi khogn can quan tam den so thang con no
		obj_health_r.setDEBT(debt);
		obj_health_r.setNOTE(note + note_recount);
		obj_health_r.setNUM_USED(num_used_ + 1);
		obj_health_r.setBEAR_DATE_E(bear_date_e);
		obj_health_r.setDEBT_DC(debt_dc);
		UPDATE_DATA_BT(obj_health_r);//Vi Nsan tru duocc hay k tru duoc cung lay luong luc bd nghi san

	}

	// flag_new  :  xet nhung thang Tang moi - NV

	private void Update_Emp_Off_Work(N_N_HEALTH_R obj_health_r, OBJ_EMPSN obj_emp,boolean flag_new) {

		String empsn 			= obj_health_r.getEMPSN();
		String month			= sf.format(date_input).substring(3,5);
		String year				= sf.format(date_input).substring(6,10);

		long salary_b			= obj_emp.Get_Salary_Basic(empsn, date_input);// old:(htai minh cu gan = luong moi roi xuong duoi xet lai chu dat = 0 thi nguy hiem qua hihi
		//15/10/2012 lay luong cho nghi viec theo mail ngay 26/09/2012 c.Uyen TB
		if(	flag_new ||	month.equals("01")||month.equals("07")
				||month.equals("04")||month.equals("10")){
			salary_b 			= obj_emp.Get_Salary_Basic(empsn, date_input);
		}else
		{
			salary_b			= obj_emp.Get_Salary_For_QuitW(empsn, date_input);
		}
		
		long num				= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long num_h				= obj_health_r.getNUM()==null?0:obj_health_r.getNUM();
		long debt				= obj_health_r.getDEBT()==null?0:obj_health_r.getDEBT();
		long debt_h				= obj_health_r.getDEBT()==null?0:obj_health_r.getDEBT();
		String note_h			= obj_health_r.getNOTE(); 
		String note				= obj_health_r.getNOTE();
		String note_recount		= "";
		long note_int			= 1;
		float heso_1_5			= (float) (1.5* salary_b)/100; 	// he so 1.5%/T
		float heso_3_0			= (float) (3* salary_b)/100;	// he so thu hoi 3%/T 
		float heso_1_5_return	= (float) (1.5* obj_health_r.getSALARY_B())/100; 	// he so 1.5%/T 		
		Date date_now_10		= OBJ_UTILITY.MONTH_NOW("10", date_input);
		boolean flag_DC_luong	= false;
		float day_of_month_not_sun = obj_util.GET_NUM_DAY_OF_MONTH_NOT_SUN(date_input);
		Date date_return_card = obj_emp.Get_Return_Card_Date();  // ngay tra the
		boolean flag_trathe		= false;//Ngan them 09/08/2011 vi neu tra the truoc ngay 10 cua dau quy thi num = 0
		long flag_dc_luong_trathe 	= 0;//Ngan them neu = 1 la k xet dieu chinh luong trong Recount NV
		// *begin 10/10/2011_N them de xet dang nghi san ma nghi viec
		Date bear_date_e		= null; 
		bear_date_e = obj_health_r.getBEAR_DATE_E();
		Date	date_now_15		= OBJ_UTILITY.MONTH_NOW("15", date_input);

		// *end  10/10/2011_N them de xet dang nghi san ma nghi viec     
		long money_dc			= 0; //so tien dieu chinh
		long money_return		= 0; //so tien tra lai cho nhan vien

		long num_recovery		= 0; //num thu hoi
		long money_recovery		= 0; // so tien thu hoi

		long num_ht				= 0; //de xet khi k du tien de tru(num tru tien hien tai)
		long money				= 0; // so tien BHYT cua thang hien tai

		long num_debt			= 0; // so thang no old
		long money_debt			= 0;// so tien BHYT con no lai

		long debt_dc  			= 0;
		
		//Bo sung 02/11/2012, thay doi dk mua BHXHTN , so ngay ko luong>=14 la ko mua
		// tuong duong songaycong>TS_ngaytrongthang-14 thi mua
		// HA
		float dk_ngaycong =day_of_month_not_sun-14;

		if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am

		note_h = note_h + " __ || ==>> THANG " + month +":";

		num	= num - 1; // giam so num cho thang truoc
		num_h = num;

		if (num <= 0 ){
			num   = 0;
			if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)
			{
				num_h = num_h;
			}else {num_h = 0; }
		}
		/*if(empsn.equals("07080562")){
			System.out.println("CHECK ");
		}*/
		if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//Vi neu dang nghi san ma nghi viec thi update lai num_h vi truoc khi chay qua xet nghi viec da update_data cho nsan roi
		{

			note		=  note + " -->Nviec";
			if (num_h <= 0){ num_h = num_h + 1; }
		}else
		{
			if(Integer.valueOf(month) == 1 || Integer.valueOf(month) == 4 || Integer.valueOf(month) == 7 || Integer.valueOf(month) == 10){
				obj_health_r.setSALARY_B(salary_b);
			}
		}


		if(obj_emp.Get_Status_Card()){  // Co tra the

			if(date_return_card.after(date_now_10)){  // tra the sau ngay 10 
				flag_DC_luong = true;
				//num  = num -1 ; // giam so num cho thang hien tai vi khong tra the, phai chiu BH
				
				// dk truoc 02/11/2012
				//if(obj_emp.Get_WORK_DAY() >= day_of_month_not_sun/2){ // DC 
				if(obj_emp.Get_WORK_DAY() > dk_ngaycong ){ // DC
					//	if(10 >dk_ngaycong){//Ngan Test

					if(	flag_new ||	month.equals("01")||month.equals("07")
							||month.equals("04")||month.equals("10")){  // OK

						num 		= 1;
						num_ht		= 1;
						num_debt	= debt_h;
						num_recovery= 0; 
						money   	= (long) heso_1_5;
						money_debt	= (long)(num_debt * heso_1_5);
						money_recovery = (long)(num_recovery * heso_3_0);
						note		= "-- NV -- TT > 10 -- DC => tru 1T + "+num_debt+"T no";
						debt		= 0;
						flag_DC_luong = false;
						if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
						{

							if (num_h >= 1)//Nsan vao ma truoc do da tru den thang nay rui nen k tru nua
							{
								num 		= 1;
								num_ht		= 0;
								num_debt	= debt_h;
								num_recovery= 0; 
								money   	= (long)(num_ht * heso_1_5);
								money_debt	= (long)(num_debt * heso_1_5);
								money_recovery = (long)(num_recovery * heso_3_0);
								note		= "-- NV -- TT > 10 -- DC => ";
								debt		= 0;
								flag_DC_luong = true;

							}
							else//Nsan vao ma truoc do chua tru den thang nay nen gio tru// old
							{//31/07/2012 sua lai nghi san vao sau ngay 15 thi k tru tien thang do nua
								num 		= 0;
								num_ht		= 0;
								num_debt	= debt_h;
								num_recovery= 0; 
								money   	= (long)(num_ht * heso_1_5);
								money_debt	= (long)(num_debt * heso_1_5);
								money_recovery = (long)(num_recovery * heso_3_0);
								note		= "-- NV -- TT > 10 -- DC => ";
								debt		= 0;
								flag_DC_luong = false;	
							}	
						}
					}else if (	month.equals("02")||month.equals("05")
							||month.equals("08")||month.equals("11")){
						if (num_h == 2)//da tru duoc T2,T3 => D/C luong t2(neu co)+tra lai tien T3
						{

							num			= 1;
							num_ht		= 0;
							num_debt	= 0;
							num_recovery= 0;
							money		= 0;
							money_debt 	= (long)(num_debt * heso_1_5);
							money_recovery = 0;
							money_return = (long)(-num * heso_1_5_return);
							note		= "-- NV -- TT > 10 -- DC => tru them "+num_debt+" T no => tra lai tien 1T";
							debt		= 0;
							num_h		= 1; // Vi chi dieu chinh luong 1T(2) do tra the >10/2 tra lai tien T3
						}else if( num_h == 1 )//da tru duoc T2,no T3=> d/c luong T2
						{
							num			= 1;
							num_ht		= 0;
							num_debt	= debt_h - 1;
							if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							num_recovery= 0;
							money		= 0;
							money_debt 	= (long)(num_debt * heso_1_5);
							money_recovery = 0;
							money_return = 0;
							note		= "-- NV -- TT > 10 -- DC => tru them "+num_debt+" T no";
							debt		= 0;
						}else if ( num_h <= 0 )//no >= 2 => tru 1T(2) + no old
						{
							if (debt_h <= 0){debt_h = 2;}//vi de note ben duoi neu co tru no thi num_debt k de gia tri am
							num			= 1;
							num_ht		= 1;//Tru t2 hien tai
							//*Begin 31/07/2012: them vao nsan thi k tru thang do
							if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							{
								num			= 0;
								num_ht		= 0;
							}
							//End 31/07/2012: them vao nsan thi k tru thang do
							num_debt	= debt_h - 2;//Tru lai T3 & t2 da tru o num_ht o tren
							if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							num_recovery= 0;
							money		= (long)(num_ht * heso_1_5);
							money_debt 	= (long)(num_debt * heso_1_5);
							money_recovery = 0;
							money_return = 0;
							note		= "-- NV -- TT > 10 -- DC => tru "+(num_debt+num_ht)+" T("+num_debt+"T no)";
							debt		= 0;
						}

					}else//cuoi quy
					{
						if (num_h >= 1)//da tru duoc T03
						{

							num			= 1;
							num_ht		= 0;
							num_debt	= debt_h;
							num_recovery= 0;
							money		= 0;
							money_debt 	= (long)(debt_h * heso_1_5);
							money_recovery = 0;
							note		= "-- NV -- TT > 10 -- DC => tru them "+num_debt+" T no";
							debt		= 0;
						}else//Chua tru duoc T03
						{
							num    	   		= 1;	
							num_ht	   		= 1;
							//*Begin 31/07/2012: them vao nsan thi k tru thang do
							if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							{
								num			= 0;
								num_ht		= 0;
							}
							//End 31/07/2012: them vao nsan thi k tru thang do
							num_debt   		= debt_h - 1;
							if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							num_recovery 	= 0;
							money_debt	    = (long)((debt_h -1) * heso_1_5);
							money	   		= (long)(num_ht * heso_1_5);	
							money_recovery 	= 0;
							note	   		= "-- NV -- TT > 10 -- DC => tru 1T("+month+") + tru them "+(num_debt)+"T no";
							debt	  	    = 0;	
						}

					}

				}else{ 	// NV -> (Tra the > 10) -> KDC 		-> co tra the 	 -> tra lai tien
					//	-> khong du cong -> xet thu hoi 3%
					/* //05/07/2012 khong du cong can xet lai luong khi tru tien k lay giong nhu du cong nhu truoc nua
				 salary_b = Get_Salary_b_quit_notEnougDayWork(empsn, date_input);	
				 while(salary_b == 0)
				 {
					 salary_b = Get_Salary_b_quit_notEnougDayWork(empsn, date_att_pre); 	
				 }
				 heso_1_5			= (float) (1.5* salary_b)/100; 	// he so 1.5%/T
				 heso_3_0			= (float) (3* salary_b)/100;	// he so thu hoi 3%/T 
				 heso_1_5_return	= (float) (1.5* salary_b)/100; 	// he so 1.5%/T 
				 //End 05/07/2012 khong du cong can xet lai luong khi tru tien k lay giong nhu du cong nhu truoc nua	
					 */	if(flag_new || month.equals("01")||month.equals("07")||month.equals("04")||month.equals("10")){ // chua thu tien BH
						 // tru 1T + thu hoi 1T 							// OK
						 num 			= 1;
						 num_ht		    = 1;
						 num_recovery	= 1;
						 num_debt		= debt_h;
						 money 			= (long) (num * heso_1_5);  	// chiu BH thang hien tai
						 money_debt		= (long) (num_debt * heso_1_5);
						 money_recovery	= (long) (num * heso_3_0);		// thu hoi 1T (khong du cong)
						 note			= "-- NV -- TT > 10 -- K_DC => tru 1T + "+num_debt+"T no + thu hoi 1T ";
						 debt			= 0;
						 flag_DC_luong = false;
						 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
						 {
							 if (num_h >= 1)
							 {
								 num 		= 1;
								 num_ht		= 0;
								 num_debt	= debt_h;
								 num_recovery= 0; 
								 money   	= (long)(num_ht * heso_1_5);
								 money_debt	= (long)(num_debt * heso_1_5);
								 money_recovery = (long)(num_recovery * heso_3_0);
								 note		= "-- NV -- TT > 10 -- KDC => TH 1T("+month+")";
								 debt		= 0;
								 flag_DC_luong = true;

							 }
							 else
							 {
								 num 		= 0;
								 num_ht		= 0;
								 num_debt	= debt_h;
								 num_recovery= 0; 
								 money   	= (long)(num_ht * heso_1_5);
								 money_debt	= (long)(num_debt * heso_1_5);
								 money_recovery = (long)(num_recovery * heso_3_0);
								 note		= "-- NV -- TT > 10 -- KDC => tru "+num_ht+"T("+month+") + TH T("+month+")";
								 debt		= 0;
								 flag_DC_luong = true;	
							 }
						 }
						 //
					 }else if (	month.equals("02")||month.equals("05")
							 ||month.equals("08")||month.equals("11")){// da thu tien dau quy cho 3 thang rui => tra tien + thu hoi 1T

						 if (num_h == 2)//da tru duoc T2,T3 => D/C luong t2(neu co)+tra lai tien T3+TH 1T(2)
						 {

							 num			= 1;
							 num_ht		= 0;
							 num_debt	= 0;
							 num_recovery= 1;
							 money		= 0;
							 money_debt 	= (long)(num_debt * heso_1_5);
							 money_recovery = (long)(num_recovery * heso_3_0);
							 money_return = (long)(-num * heso_1_5_return);
							 note		= "-- NV -- TT > 10 -- K_DC => tru "+num_debt+"T no + TH 1T - tra lai tien 1T";
							 debt		= 0;
							 num_h		= 1;
						 }else if( num_h == 1 )//da tru duoc T2,no T3=> d/c luong T2(neu co)+TH 1T(2)
						 {
							 num			= 1;
							 num_ht		= 0;
							 num_debt	= debt_h - 1;
							 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							 num_recovery= 1;
							 money		= 0;
							 money_debt 	= (long)(num_debt * heso_1_5);
							 money_recovery = (long)(num_recovery * heso_3_0);
							 money_return = 0;
							 note		= "-- NV -- TT > 10 -- K_DC => tru 1T("+month+") + "+num_debt+"T no + TH 1T ";
							 debt		= 0;
						 }else if ( num_h <= 0 )//no >= 2 => tru 1T(2) + debt old + TH 1T(2) 
						 {
							 if (debt_h <= 0){debt_h = 2;}//vi de note ben duoi neu co tru no thi num_debt k de gia tri am
							 num			= 1;
							 num_ht		= 1;//Tru t2 hien tai
							 num_debt	= debt_h - 2;//Tru lai T3 & t2 da tru o num_ht o tren
							 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							 num_recovery= 1;
							 //*Begin 31/07/2012: them vao nsan thi k tru thang do
							 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							 {
								 num			= 0;
								 num_ht		= 0;
								 num_recovery= 0;
							 }
							 //End 31/07/2012: them vao nsan thi k tru thang do
							 money		= (long)(num_ht * heso_1_5 );
							 money_debt 	= (long)(num_debt * heso_1_5);
							 money_recovery = (long)(num_recovery * heso_3_0);
							 money_return = 0;
							 note		= "-- NV -- TT > 10 -- K_DC => tru "+(num_debt+num_ht)+" T("+num_debt+"T no) + TH "+num_ht+"T";
							 debt		= 0;
						 }

					 }else
					 {
						 if( num_h >= 1)//Da tru duoc den T3
						 {
							 num				= 1;
							 num_ht			= 0;
							 num_debt		= debt_h;
							 num_recovery    = 1;
							 //*Begin 31/07/2012: them vao nsan thi k tru thang do
							 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							 {
								 num			= 0;
								 num_ht		= 0;
								 num_recovery= 0;
							 }
							 //End 31/07/2012: them vao nsan thi k tru thang do
							 money			= 0;
							 money_debt		= (long) (num_debt * heso_1_5)   ;
							 money_recovery  = (long)(num*heso_3_0);
							 note			= "-- NV -- TT > 10 -- K_DC => tru "+num_debt+"T no + thu hoi "+num_recovery+"T";
							 debt			= 0;
						 }else
						 {
							 num 			= 1;
							 num_ht			= 1;
							 num_debt		= debt_h - 1;
							 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							 num_recovery	= 1;
							 //*Begin 31/07/2012: them vao nsan thi k tru thang do
							 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							 {
								 num			= 0;
								 num_ht		= 0;
								 num_recovery= 0;
							 }
							 //End 31/07/2012: them vao nsan thi k tru thang do
							 money_debt		= (long)(num_debt * heso_1_5);
							 money_recovery  = (long)(num*heso_3_0);
							 money			= (long)(num*heso_1_5);
							 note			= "-- NV -- TT > 10 -- K_DC => tru "+num_ht+"T +"+(num_debt)+"T no + thu hoi "+num_recovery+"T";
							 debt			= 0;
						 }
					 }



				}


			}else{ // tra the truoc ngay 10, tra lai NUM thang
				if(flag_new ||  month.equals("01")||month.equals("07")||month.equals("04")||month.equals("10")){
					// khong tham gia BHYT
					flag_trathe = true;
					num         = 0;
					num_ht		= 0;
					num_debt	= debt_h;
					num_recovery= 0;
					money		= 0;
					money_debt	= (long)(num_debt*heso_1_5);
					money_recovery = 0;
					note		= "KHONG THAM GIA BH --- Tru "+num_debt+"T no";
					debt		= 0;
					flag_DC_luong = false;
					flag_dc_luong_trathe = 1;//= 1 k xet d/c luong trong Recount_NV

					if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
					{
						if (num_h >= 1)
						{
							num 		= 1;
							num_ht		= 0;
							num_debt	= debt_h;
							num_recovery= 0; 
							money   	= (long)(num_ht * heso_1_5);
							money_debt	= (long)(num_debt * heso_1_5);
							money_recovery = (long)(num_recovery * heso_3_0);
							note		= "-- NV -- TT <= 10 => ";
							debt		= 0;
							flag_DC_luong = false;
							flag_dc_luong_trathe = 1;
						}
						else
						{
							num 		= 0;
							num_ht		= 0;
							num_debt	= debt_h;
							num_recovery= 0; 
							money   	= (long)(num_ht * heso_1_5);
							money_debt	= (long)(num_debt * heso_1_5);
							money_recovery = (long)(num_recovery * heso_3_0);
							note		= "-- NV -- TT <= 10 => ";
							debt		= 0;
							flag_DC_luong = false;
							flag_dc_luong_trathe = 1;
						}
					}

				}else if (month.equals("02")||month.equals("05")
						||month.equals("08")||month.equals("11")){

					if (debt_h <= 0){debt_h = 2;}//vi de note ben duoi neu co tru no thi debt_h - 1 k de gia tri am
					num			 = -(num);
					num_ht		 = 0;
					num_debt	 = debt_h - 2;
					if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
					num_recovery = 0;
					money		 = 0;
					money_recovery = 0;
					money_return = (long) (num * heso_1_5_return) ;
					money_debt	 = (long)(num_debt*heso_1_5); 
					note		 = "-- NV -- TT < 10 => tru "+num_debt+"T no + tra lai tien BH : " + (-num) + "T";
					debt		 = 0;
					flag_dc_luong_trathe = 1;//= 1 k xet d/c luong trong Recount_NV
				}else
				{   
					if (debt_h <= 0){debt_h = 1;}//vi de note ben duoi neu co tru no thi debt_h - 1 k de gia tri am
					num			 = -(num);
					num_ht		 = 0;
					num_debt	 = debt_h - 1;
					if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
					num_recovery = 0;
					money		 = 0;
					money_recovery = 0;
					money_return = (long) (num * heso_1_5_return) ;
					money_debt	 = (long)((num_debt)*heso_1_5); 
					note		 = "-- NV -- TT < 10 => tru "+(debt_h - 1)+"T no + tra lai tien BH : " + (-num) + "T";
					debt		 = 0;
					flag_dc_luong_trathe = 1;//= 1 k xet d/c luong trong Recount_NV
				}
			}

		}else{	// (II)khong tra the

			flag_DC_luong = true;

			/*if(flag_new){ // voi nhung thang tang moi NV 
				debt = -1;
				num  = 0;
				debt = month.equals("02") && debt == -1 ? 2 : debt;
				debt = month.equals("08") && debt == -1 ? 2 : debt;
				debt = month.equals("05") && debt == -1 ? 2 : debt;
				debt = month.equals("11") && debt == -1 ? 2 : debt;
				debt = month.equals("03") && debt == -1 ? 1 : debt;
				debt = month.equals("09") && debt == -1 ? 1 : debt;
				debt = month.equals("06") && debt == -1 ? 1 : debt;
				debt = month.equals("12") && debt == -1 ? 1 : debt;
				debt = debt == -1 ? 0: debt; 
			}*/


			if(obj_emp.Get_WORK_DAY() >dk_ngaycong){ // (a)
				//	if(15 >dk_ngaycong){//Ngan test
				// NV - KTT - DC 
				if( month.equals("01") || month.equals("07")){  // OK
					num 			= 6; // dau quy num = 0  
					num_ht			= num;
					num_debt		= debt_h;
					num_recovery    = num - 1;
					money 			= (long) (num_ht * heso_1_5);
					money_debt		= (long)(num_debt* heso_1_5);
					money_recovery 	= (long) (num_recovery * heso_3_0); // khong thu hoi 1T du cong
					note			= "-- NV -- K_TT -- DC => tru "+num+"T + thu hoi "+num_recovery+"T + tru "+num_debt+"T no" ;
					debt			= 0;
					flag_DC_luong = false;
					if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
					{
						if (num_h >= 1)
						{
							num 		= 5;
							num_ht		= 5;
							num_debt	= debt_h;
							num_recovery= 5; 
							money   	= (long)(num_ht * heso_1_5);
							money_debt	= (long)(num_debt * heso_1_5);
							money_recovery = (long)(num_recovery * heso_3_0);
							note		= "-- NV -- KTT -- DC => Tru 5T + TH 5T + tru "+num_debt+"T no";
							debt		= 0;
							flag_DC_luong = true;

						}
						else
						{
							num 		= 5;
							num_ht		= 5;
							num_debt	= debt_h;
							num_recovery= 5; 
							money   	= (long)(num_ht * heso_1_5);
							money_debt	= (long)(num_debt * heso_1_5);
							money_recovery = (long)(num_recovery * heso_3_0);
							note		= "-- NV -- KTT -- DC => Tru 5T + TH 5T + tru "+num_debt+"T no";
							debt		= 0;
							flag_DC_luong = true;

						}
					}

				}else
					if(month.equals("04") || month.equals("10")){
						num				= 3; // dau quy num = 0
						num_ht			= num;
						num_debt		= debt_h;
						num_recovery    = num - 1;
						money 			= (long) (num_ht * heso_1_5);		// tru het 3 thang 
						money_debt		= (long)(num_debt*heso_1_5);
						money_recovery 	= (long) (num_recovery * heso_3_0); // khong thu hoi 1T du cong
						note			= "-- NV -- K_TT -- DC => tru 3T + thu hoi 2T + "+num_debt+"T no" ;
						debt  			= 0;
						flag_DC_luong = false;
						if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
						{
							if (num_h >= 1)
							{
								num 		= 2;
								num_ht		= 2;
								num_debt	= debt_h;
								num_recovery= 2; 
								money   	= (long)(num_ht * heso_1_5);
								money_debt	= (long)(num_debt * heso_1_5);
								money_recovery = (long)(num_recovery * heso_3_0);
								note		= "-- NV -- K_TT -- DC => tru 2T + thu hoi 2T + "+num_debt+"T no" ;
								debt		= 0;
								flag_DC_luong = true;

							}
							else
							{
								num 		= 2;
								num_ht		= 2;
								num_debt	= debt_h;
								num_recovery= 2; 
								money   	= (long)(num_ht * heso_1_5);
								money_debt	= (long)(num_debt * heso_1_5);
								money_recovery = (long)(num_recovery * heso_3_0);
								note		= "-- NV -- K_TT -- DC => tru 2T + thu hoi 2T + "+num_debt+"T no" ;
								debt		= 0;
								flag_DC_luong = true;

							}
						}

					}else
						if(month.equals("02")  || month.equals("08") ){
							if (num_h == 2)//Da tru duoc t2&t3=> D/c luong 2T(2,3) + tru 3T(4,5,6)+ debt old + TH 4T(3,4,5,6)
							{
								num			= 5;
								num_ht		= 3;
								num_debt	= 0;
								num_recovery= 4;
								money		= (long)(num_ht*heso_1_5);
								money_debt	= (long)(num_debt*heso_1_5);
								money_recovery = (long)(num_recovery*heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru "+num_ht+"T + "+num_debt+"T no + thu hoi "+num_recovery+"T ";
								debt		= 0;

							}else
								if (num_h == 1)//Da tru duoc t2, no t3=> D/c luong 1T(2) + tru 4T(3,4,5,6)+ debt old + TH 4T(3,4,5,6)
								{
									num			= 5;
									num_ht		= 4;
									num_debt	= debt_h -1;
									if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
									num_recovery= 4;
									money		= (long)(num_ht*heso_1_5);
									money_debt	= (long)(num_debt*heso_1_5);
									money_recovery = (long)(num_recovery*heso_3_0);
									note			= "-- NV -- K_TT -- DC => tru "+num_ht+"T + "+num_debt+"T no + thu hoi "+num_recovery+"T ";
									debt		= 0;

								}else
									if (num_h <= 0)//(debt >= 2_t1 tru dc 1T(1) or T1 k tru dc) => Tru 5T(2,3,4,5,6)+ debt old + TH 4T(3,4,5,6)
									{
										num			= 5;
										num_ht		= 5;
										//*Begin 31/07/2012: them vao nsan thi k tru thang do
										if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
										{
											num			= 4;
											num_ht		= 4;
										}
										//End 31/07/2012: them vao nsan thi k tru thang do
										num_debt	= debt_h -2;
										if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
										num_recovery= 4;
										money		= (long)(num_ht*heso_1_5);
										money_debt	= (long)(num_debt*heso_1_5);
										money_recovery = (long)(num_recovery*heso_3_0);
										note			= "-- NV -- K_TT -- DC => tru "+num_ht+"T + "+num_debt+"T no + thu hoi "+num_recovery+"T ";
										debt		= 0;

									}
						}else if (month.equals("05") || month.equals("11"))//du cong
						{
							if (num_h == 2)//da tru dc T5 & T6 =>D/c luong T5&T6 + debt old + TH 1T(6)
							{
								num				= 2;
								num_ht			= 0;
								num_debt		= 0;
								num_recovery	= 1;
								money			= (long)(num_ht * heso_1_5);
								money_debt		= (long)(num_debt * heso_1_5);
								money_recovery	= (long)(num_recovery * heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
								debt			= 0;
							}else if (num_h == 1)//(debt = T6) =>d/c luong t5 + tru 1T(6) + debt old + TH 1T(6)
							{
								num				= 2;
								num_ht			= 1;
								num_debt		= debt_h - 1;
								if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								num_recovery	= 1;
								money			= (long)(num_ht * heso_1_5);
								money_debt		= (long)(num_debt * heso_1_5);
								money_recovery	= (long)(num_recovery * heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
								debt			= 0;
							}else if (num_h <= 0)//(debt >= 2) =>T1 k tru dc or t1 tru dc 1T(1)==> tru 2T(5,6) + debt old + TH 1T(6)
							{
								num				= 2;
								num_ht			= 2;
								//*Begin 31/07/2012: them vao nsan thi k tru thang do
								if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
								{
									num			= 1;
									num_ht		= 1;
								}
								//End 31/07/2012: them vao nsan thi k tru thang do
								num_debt		= debt_h - 2;
								if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								num_recovery	= 1;
								money			= (long)(num_ht * heso_1_5);
								money_debt		= (long)(num_debt * heso_1_5);
								money_recovery	= (long)(num_recovery * heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
								debt			= 0;
							}

						}else if (month.equals("03") || month.equals("09"))
						{
							if(num_h >= 1)//Da tru dc den T03=>tru 3T(4,5,6) + TH(4,5,6)
							{
								num				= 4;//1T(3)+3T cua quy sau
								num_ht			= num - 1;
								num_debt		= debt_h;
								num_recovery	= num - 1;
								money			= (long) (num_ht * heso_1_5);
								money_debt		= (long) (num_debt * heso_1_5);
								money_recovery	= (long)(num_recovery*heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru them "+(num_debt + num_ht)+"T, thu hoi "+num_recovery+"T";
								debt			= 0;
							}else//==>tru 4T(3,4,5,6) + TH(4,5,6)
							{
								num				= 4;//1T(3)+3T cua quy sau
								num_ht			= num;
								num_debt		= debt_h -1;
								if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								num_recovery    = num - 1;
								//*Begin 31/07/2012: them vao nsan thi k tru thang do
								if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
								{
									num			= 3;
									num_ht		= 3;
									num_recovery=3;
								}
								//End 31/07/2012: them vao nsan thi k tru thang do
								money			= (long) (num_ht * heso_1_5);
								money_debt		= (long) ( num_debt * heso_1_5);
								money_recovery	= (long)(num_recovery*heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru them "+((num_debt) + num_ht)+"T, thu hoi "+num_recovery+"T";
								debt			= 0;
							}

						}else if (month.equals("06") || month.equals("12")){ //5,6,11,12
							if(num_h >= 1)//Da tru dc den T06=>D/C luong T6(neu co)+tru No
							{
								num				= 1;
								num_ht			= 0;
								num_debt		= debt_h;
								num_recovery	= 0;
								money			= 0;
								money_debt		= (long) (num_debt* heso_1_5);
								money_recovery	= (long)(num_recovery*heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru them "+(num_debt)+"T no, thu hoi "+num_recovery+"T";
								debt	        = 0;
							}else //chua tru duoc den T6 => Tru 1T(6) + no
							{
								num				= 1;
								num_ht			= 1;
								num_debt		= debt_h-1;
								if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								num_recovery	= 0;
								//*Begin 31/07/2012: them vao nsan thi k tru thang do
								if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
								{
									num			= 0;
									num_ht		= 0;
									num_recovery=0;
								}
								//End 31/07/2012: them vao nsan thi k tru thang do
								money			= (long)(num_ht*heso_1_5);
								money_debt		= (long) (num_debt* heso_1_5);
								money_recovery	= (long)(num_recovery*heso_3_0);
								note			= "-- NV -- K_TT -- DC => tru them "+((num_debt) + num_ht )+"T, thu hoi "+num_recovery+"T";
								debt	        = 0;
							}
						}			
			}else{	// NV - KTT - KDC 

				/* //05/07/2012 khong du cong can xet lai luong khi tru tien k lay giong nhu du cong nhu truoc nua
				 salary_b = Get_Salary_b_quit_notEnougDayWork(empsn, date_input);	
				 while(salary_b == 0)
				 {
					 salary_b = Get_Salary_b_quit_notEnougDayWork(empsn, date_att_pre); 	
				 }
				 heso_1_5			= (float) (1.5* salary_b)/100; 	// he so 1.5%/T
				 heso_3_0			= (float) (3* salary_b)/100;	// he so thu hoi 3%/T 
				 heso_1_5_return	= (float) (1.5* salary_b)/100; 	// he so 1.5%/T 
				 //End 05/07/2012 khong du cong can xet lai luong khi tru tien k lay giong nhu du cong nhu truoc nua
				 */				   if(month.equals("01") || month.equals("07")){	// OK
					 num 			= 6;
					 num_ht			= num;
					 num_debt		= debt_h;
					 num_recovery    = 6;
					 money 			= (long) (num_ht * heso_1_5);
					 money_debt		= (long)(num_debt * heso_1_5);
					 money_recovery 	= (long) (num_recovery * heso_3_0);
					 note			= "-- NV - K_TT - K_DC => tru 6T + + tru "+num_debt+"T no + thu hoi 6T ";
					 debt			= 0;
					 flag_DC_luong = false;
					 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
					 {
						 if (num_h >= 1)
						 {
							 num 		= 5;
							 num_ht		= 5;
							 num_debt	= debt_h;
							 num_recovery= 5; 
							 money   	= (long)(num_ht * heso_1_5);
							 money_debt	= (long)(num_debt * heso_1_5);
							 money_recovery = (long)(num_recovery * heso_3_0);
							 note		= "-- NV -- K_TT -- KDC => tru 5T + thu hoi 5T + "+num_debt+"T no" ;
							 debt		= 0;
							 flag_DC_luong = true;

						 }
						 else
						 {
							 num 		= 5;
							 num_ht		= 5;
							 num_debt	= debt_h;
							 num_recovery= 5; 
							 money   	= (long)(num_ht * heso_1_5);
							 money_debt	= (long)(num_debt * heso_1_5);
							 money_recovery = (long)(num_recovery * heso_3_0);
							 note		= "-- NV -- KTT -- DC => Tru 5T + TH 5T + tru "+num_debt+"T no";
							 debt		= 0;
							 flag_DC_luong = true;

						 }
					 }
				 }else
					 if(month.equals("02") ||month.equals("08") ){ 

						 if (num_h == 2)//t1 da tru dc T2&T3=>d/c luong T2&T3 + tru 3T(4,5,6)+ debt old + TH 5T(2,3,4,5,6)
						 {
							 num				= 5;
							 num_ht			= 3;
							 num_debt		= 0;
							 num_recovery	= 5;
							 money			= (long)(num_ht*heso_1_5);
							 money_debt		= (long)(num_debt*heso_1_5);
							 money_recovery	= (long)(num_recovery*heso_3_0);
							 note			= "-- NV - K_TT - K_DC => tru them "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T";
							 debt			= 0;
						 }else
							 if (num_h == 1)//t1 da tru dc T2, no T3=>d/c luong 1T(2) + tru 4T(3,4,5,6)+ debt old + TH 5T(2,3,4,5,6)
							 {
								 num				= 5;
								 num_ht			= 4;
								 num_debt		= debt_h - 1;
								 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								 num_recovery	= 5;
								 money			= (long)(num_ht*heso_1_5);
								 money_debt		= (long)(num_debt*heso_1_5);
								 money_recovery	= (long)(num_recovery*heso_3_0);
								 note			= "-- NV - K_TT - K_DC => tru them "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T";
								 debt			= 0;
							 }
							 else
								 if (num_h <= 0)//t1 k tru dc or t1 tru dc 1T(1) ==> tru 5T(2,3,4,5,6)+ debt old + TH 5T(2,3,4,5,6)
								 {
									 num				= 5;
									 num_ht			= 5;
									 num_debt		= debt_h - 2;
									 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
									 num_recovery	= 5;
									 //*Begin 31/07/2012: them vao nsan thi k tru thang do
									 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
									 {
										 num			= 4;
										 num_ht		= 4;
										 num_recovery= 4;
									 }
									 //End 31/07/2012: them vao nsan thi k tru thang do
									 money			= (long)(num_ht*heso_1_5);
									 money_debt		= (long)(num_debt*heso_1_5);
									 money_recovery	= (long)(num_recovery*heso_3_0);
									 note			= "-- NV - K_TT - K_DC => tru them "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T";
									 debt			= 0;
								 } 

					 }else if (month.equals("05") || month.equals("11"))
					 {
						 if (num_h == 2)//da tru dc T5 & T6 =>D/c luong T5&T6 + debt old + TH 2T(5,6)
						 {
							 num				= 2;
							 num_ht			= 0;
							 num_debt		= 0;
							 num_recovery	= 2;
							 money			= (long)(num_ht * heso_1_5);
							 money_debt		= (long)(num_debt * heso_1_5);
							 money_recovery	= (long)(num_recovery * heso_3_0);
							 note			= "-- NV -- K_TT -- KDC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
							 debt			= 0;
						 }else if (num_h == 1)//(debt = T6) =>d/c luong t5 + tru 1T(6) + debt old + TH 2T(5,6)
						 {
							 num				= 2;
							 num_ht			= 1;
							 num_debt		= debt_h - 1;
							 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							 num_recovery	= 2;
							 money			= (long)(num_ht * heso_1_5);
							 money_debt		= (long)(num_debt * heso_1_5);
							 money_recovery	= (long)(num_recovery * heso_3_0);
							 note			= "-- NV -- K_TT -- KDC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
							 debt			= 0;
						 }else if (num_h <= 0)//(debt >= 2) =>T1 k tru dc or t1 tru dc 1T(1)==> tru 2T(5,6) + debt old + TH 1T(6)
						 {
							 num				= 2;
							 num_ht			= 2;
							 num_debt		= debt_h - 2;
							 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
							 num_recovery	= 2;
							 //*Begin 31/07/2012: them vao nsan thi k tru thang do
							 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
							 {
								 num			= 1;
								 num_ht		= 1;
								 num_recovery= 1;
							 }
							 //End 31/07/2012: them vao nsan thi k tru thang do
							 money			= (long)(num_ht * heso_1_5);
							 money_debt		= (long)(num_debt * heso_1_5);
							 money_recovery	= (long)(num_recovery * heso_3_0);
							 note			= "-- NV -- K_TT -- KDC => tru "+(num_ht+num_debt)+"T("+num_debt+"T no) + thu hoi "+num_recovery+"T ";
							 debt			= 0;
						 }


					 }else
						 if (month.equals("03") || month.equals("09")){
							 if(num_h >= 1)//Da tru den T03-->Tru 3T(4,5,6)+TH 4T(3,4,5,6)+ no
							 {

								 num 			= 4;
								 num_ht			= num - 1;
								 num_debt		= debt_h;
								 num_recovery    = 4;
								 money 			= (long)(num_ht * heso_1_5);
								 money_debt		= (long)(num_debt*heso_1_5);
								 money_recovery	= (long)(num_recovery* heso_3_0);
								 note			= "-- NV - K_TT - K_DC => tru them "+(num_debt + num_ht)+"T("+num_debt+"T no), thu hoi "+num_recovery+"T";
								 debt			= 0;
							 }else //Chua tru dc T03 --> Tru 4T(3,4,5,6) + TH 4T(3,4,5,6)
							 {
								 if (debt_h <= 0){debt_h = 1;}//vi de note ben duoi neu co tru no thi debt_h - 1 k de gia tri am
								 num				= 4;
								 num_ht			= num;
								 num_debt		= debt_h - 1;
								 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
								 num_recovery    = 4;
								 //*Begin 31/07/2012: them vao nsan thi k tru thang do
								 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
								 {
									 num			= 3;
									 num_ht		= 3;
									 num_recovery= 3;
								 }
								 //End 31/07/2012: them vao nsan thi k tru thang do
								 money			= (long)(num_ht * heso_1_5);//tru 1T(3)
								 money_debt		= (long)(num_debt* heso_1_5);
								 money_recovery	= (long)(num_recovery * heso_3_0);
								 note			= "-- NV - K_TT - K_DC => tru them "+(num_debt + num_ht)+"T("+(num_debt)+"T no), thu hoi "+num_recovery+"T";
								 debt			= 0;
							 }

						 }else
							 if(month.equals("04") || month.equals("10")){
								 num 			= 3;
								 num_ht			= num;
								 num_debt		= debt_h;
								 num_recovery	= 3;
								 money 			= (long) (num_ht * heso_1_5);
								 money_debt		= (long) (num_debt * heso_1_5);
								 money_recovery 	= (long) (num_recovery * heso_3_0);
								 note			= "-- NV - K_TT - K_DC => tru 3T + thu hoi 3T + "+num_debt+"T no";
								 debt			= 0;
								 flag_DC_luong = false;
								 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
								 {
									 if (num_h >= 1)
									 {
										 num 		= 2;
										 num_ht		= 2;
										 num_debt	= debt_h;
										 num_recovery= num; 
										 money   	= (long)(num_ht * heso_1_5);
										 money_debt	= (long)(num_debt * heso_1_5);
										 money_recovery = (long)(num_recovery * heso_3_0);
										 note		= "-- NV -- K_TT -- KDC => tru 2T + thu hoi 2T + "+num_debt+"T no" ;
										 debt		= 0;
										 flag_DC_luong = true;

									 }
									 else
									 {
										 num 		= 2;
										 num_ht		= 2;
										 num_debt	= debt_h;
										 num_recovery= num; 
										 money   	= (long)(num_ht * heso_1_5);
										 money_debt	= (long)(num_debt * heso_1_5);
										 money_recovery = (long)(num_recovery * heso_3_0);
										 note		= "-- NV -- K_TT -- KDC => tru 2T + thu hoi 2T + "+num_debt+"T no" ;
										 debt		= 0;
										 flag_DC_luong = true;

									 }
								 }
							 }else
								 if(month.equals("06")|| month.equals("12")){
									 if(num_h >= 1)//Da tru den T06-->D/C luong T6(neu co) + Tru No + TH 1T(6)
									 {

										 num 			= 1;
										 num_ht			= 0;
										 num_debt		= debt_h;
										 num_recovery    = 1;
										 money 			= 0;
										 money_debt		= (long)(num_debt*heso_1_5);
										 money_recovery	= (long)(num_recovery * heso_3_0);
										 note			= "-- NV - K_TT - K_DC => tru them "+(num_debt+num_ht)+"T("+num_debt+"T no), thu hoi "+num_recovery+"T";
										 debt			= 0;
									 }else//Chua tru T6=> Tru 1T(6) + Tru No + TH 1T(6)
									 {
										 num 			= 1;
										 num_ht			= 1;
										 num_debt		= debt_h-1;
										 if (debt_h == 0){ num_debt = 0;}//Vi o duoi co truong hop num_debt = debt_h -1( or -2) nen gan lai = 0 de k lay gia tri am
										 num_recovery    = 1;
										 //*Begin 31/07/2012: them vao nsan thi k tru thang do
										 if(bear_date_e != null && bear_date_e.after(date_now_15) && obj_health_r.getSTATUS() == 0)//27/10/2011 Ngan them vi neu dang Nsan ma nviec thi lay luong hien tai de ky trinh-->Neu da tru bu Nsan truoc do thi d/c luong
										 {
											 num			= 0;
											 num_ht		= 0;
											 num_recovery= 0;
										 }
										 //End 31/07/2012: them vao nsan thi k tru thang do
										 money 			= (long)(num_ht*heso_1_5);
										 money_debt		= (long)(num_debt*heso_1_5);
										 money_recovery	= (long)(num_recovery * heso_3_0);
										 note			= "-- NV - K_TT - K_DC => tru them "+(num_debt+num_ht)+"T("+num_debt+"T no), thu hoi "+num_recovery+"T";
										 debt			= 0;
									 }
								 }
			}
		}	



		if(flag_DC_luong) { 
			//Ngan sua neu nv KTT o giua quy ma dc luong thi 
			if( (salary_b > obj_health_r.getSALARY_B()) && num_h >= 1 
					&& (date_return_card.after(date_now_10) || obj_emp.Get_Status_Card()==false  )){
				note 	=note + " + DC luong " +num_h +"T("+ month+" --> "+((Integer.valueOf(month)+num_h) - 1)+")";//10/09/2011 Ngan sua
				//money 	=  money + (long)((salary_b - obj_health_r.getSALARY_B()) * (num*1.5)/100);// dieu chinh luong
				money_dc    = (long)((salary_b - obj_health_r.getSALARY_B()) * (num_h*1.5)/100);// dieu chinh luong	

			}else if (salary_b == obj_health_r.getSALARY_B())//17/10/2011 Ngan xet vi vd:09120306 luong = nhau nhung do num_h >=1 nen vao recount_NV xet d/c luong se sai note
			{
				flag_dc_luong_trathe = 1;//=1 k xet d/c luong trong Recount NV(flag_ == 0 se xet)
			}
		}

		note = note_h + note;
		System.out.println(empsn);
		System.out.println(note);
		money = money + money_debt + money_recovery + money_dc+money_return;

		ArrayList<Long> list_recount = new ArrayList<Long>();
		list_recount = ReCount_NV(empsn, money,money_dc,num_ht,num_debt,num_recovery,num_h,date_input,flag_dc_luong_trathe,salary_b); //15/10/2012 them salary_b de lay luong Nviec theo mail 26/09/2012 C.Uyen TB

		if(list_recount != null){
			long debt_note      = 0; //debt de ghi vao note la tru duoc bao nhieu thang no
			long sothang_dc     = 0;
			long sothang_thuhoi = 0;
			if (num_h >= 1 && (date_return_card.after(date_now_10) || obj_emp.Get_Status_Card()==false  ))
			{
				num 			= list_recount.get(0)+ num_h;
			}else {num 				=   list_recount.get(0);}

			debt_note			=   list_recount.get(1);
			debt				=   num_debt- debt_note;
			money				=   list_recount.get(2);  
			note_int			=   list_recount.get(3);
			debt_dc   			=   list_recount.get(4);
			sothang_dc			=   list_recount.get(5);
			sothang_thuhoi		=   list_recount.get(6);

			if(note_int == 0 ){ 
				if (debt_dc == 0 && sothang_dc == 2 ){

					note_recount	= " --> Xet luong thuc nhan chi d/c luong duoc "+sothang_dc+"T("+ month+" --> "+(Integer.valueOf(month)+1)+") + tru : " +(num-num_h)+"T + "+debt_note+"T no + TH "+sothang_thuhoi+"T";
				}else if(debt_dc == 0 && sothang_dc == 1){
					note_recount	= " --> Xet luong thuc nhan chi d/c luong duoc "+sothang_dc+"T("+ month+") + tru : " +(num-num_h)+"T + "+debt_note+"T no + TH "+sothang_thuhoi+"T";
				}else if (debt_dc == 0 && sothang_dc == 0){
					note_recount	= " --> Xet luong thuc nhan d/c luong duoc "+num_h+"T + tru duoc : " +(num-num_h)+"T + "+debt_note+"T no + TH "+sothang_thuhoi+"T";
				}else if (debt_dc == 1){
					note_recount	= " --> Xet luong thuc nhan khong du tien de tru"; 
				}else
				{
					note_recount	= " --> Xet luong thuc nhan tru duoc : " +(num-num_h)+"T + "+debt_note+"T no + TH "+sothang_thuhoi+"T";
				}
			}else{//Neu tong luong ok se tru d/c luong tai thang nsan:vd nsan 06/02-->06/05  ma luong t02<> t01 thi d/c luong t02 va t03
				note_recount = "-- Luong OK ";
			}
		}

		//*Begin xet check_condition_quit_work 29/02/2012
		if (check_condition_quit_work_again(empsn))
		{
			num 	= 0;
			money 	= 0;
			note	= note_work_again;
		}
		//*End xet check_condition_quit_work 29/02/2012


		salary_b_quit_not_enough_dayWork = salary_b;//06/07/2012 them vao de xet trong UPDATE_DATA_BT khi nviec k du cong
		System.out.println(money);
		obj_health_r.setNUM(num);
	//	obj_health_r.setSALARY_B(salary_b);
		obj_health_r.setSALARY_B(salary_cb_nv);
		obj_health_r.setSALARY_M(salary_b * num); // Nhan luong cho NV dua theo so thang co the tru dc tien
		obj_health_r.setSTATUS(Long.valueOf(-1));
		obj_health_r.setMONEY(money);
		obj_health_r.setDEBT(debt);
		obj_health_r.setNOTE(note + note_recount);
		obj_health_r.setDEBT_DC(debt_dc);

		//UPDATE_DATA(obj_health_r);
		UPDATE_DATA_BT(obj_health_r);
	}


	/**
	 * Cap nhat nhung nguoi dang ky tham gia BHYT :
	 * empsn - salary_b - status - num (-1)
	 * 
	 * @param date_input
	 * @return
	 */


	private String Add_Emp_New(){

		String str_error_ = "OK";
		long	salary_ 	= 0;
		List<String> list_ = null;
		N_N_HEALTH_R obj_save_ ;
		OBJ_EMPSN obj_emp_ = new OBJ_EMPSN();
		String  name_fact		= cbx_fact.getText();

		String date_str_01_ 		= sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input)); 
		String date_str_pre_15_ 	= sf.format(OBJ_UTILITY.MONTH_PRE("15", date_input));
		String sql_ = ""; 
		String month_				= sf.format(date_input).substring(3,5); 
		String year_				= sf.format(date_input).substring(6,10);

		// lay nhung thang moi ki hop dong ngay 01 va ngay 15 thang truoc de theo doi
		sql_ =	" SELECT t.empsn\n" +

		"FROM n_labour t" + 


		" where (t.date_s = to_date('"+date_str_01_+"','dd/mm/yyyy')\n" + 
		"			or t.date_s = to_date('"+date_str_pre_15_+"','dd/mm/yyyy'))\n" + 
		" AND t.times = 1" +
		
//		" AND t.empsn ='12080485'"+
		"\n"+	
		//add them nhung nhan vien dieu dong khu--thang nhap xuong moi se la thang tang moi 02/12/2011
		" union\n" +
		"\n" + 
		"select t.empsn\n" + 
		"from n_newworker_test t,n_employee e,n_labour lb\n" + 
		"where t.empsn = e.empsn\n" + 
		"and e.empsn   = lb.empsn\n" + 
		"and t.dd_khu  = '1'\n" + 
		"and lb.clock  = '1'\n" + 
		"and to_char(e.ngaynx_moi,'mm/yyyy') = '"+month_+"/"+year_+"'"+
//		" AND t.empsn ='12080485'"+


		"";



		list_ = obj_util.Exe_Sql_String(sql_);

		for (String empsn : list_) {
			System.out.println(" Add_Emp_New()-----"+empsn+"--------");
			if(check_condition(empsn)== false){
				continue;//bo qua duyet tiep
			}
			madv=obj_emp.Get_depsn(empsn, date_input);
			if (!madv.equals("ENULL")){
				if (thuoc_xuong(madv))
				{
					
					obj_save_ 	= new N_N_HEALTH_R();
					salary_		= obj_emp_.Get_Salary_Basic(empsn, date_input);

					obj_save_.setEMPSN(empsn);
					obj_save_.setSALARY_B(salary_);
					obj_save_.setSTATUS((long) OBJ_EMPSN.empsn_BT);
					obj_save_.setNUM(Long.valueOf(0));
					obj_save_.setNUM_USED(Long.valueOf(0));

					try{
						obj_dao.save(obj_save_);
					}catch (Exception e) {
						str_error_ = str_error_ + empsn;
					}


				}else
				{
					continue;
				}
			}
		}
		return str_error_;
	
	}
	
	//*Begin 14/08/2012 ADD NHUNG NGUOI NGHI VIEC TANG LAI TRONG N_EMP_QUIT:
	    //co nhap thang tang lai la lay k quan tam from_date vi co the tu lt2 chuyen ve
	       //Nsu k nhap bao giam ma chi nhap cho co nghi viec de tang moi lai. Viec nay Nsu phai tu quan ly
		//VOI THANG TANG LAI = THANG KY TRINH 
		//-TY LE TANG LAI = 1 --> STATUS_TT_TMOI  = 1
		//-TY LE TANG LAI = 20 --> STATUS_TT_TMOI = 3
		//STATUS = 1, NUM_USED = 0, LOCK_DATE = NULL
	private String Add_Emp_Quit_Work_Again(){

		String str_error_ = "OK";
		long	salary_ 	= 0;
		List<String> list_  = null;
	//	N_N_HEALTH_R objData = new N_N_HEALTH_R();
		OBJ_EMPSN obj_emp_  = new OBJ_EMPSN();
		String  name_fact		    = cbx_fact.getText();
		String date_str_01_ 		= sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input)); 
		Date date_01				= null;
			 try {
				date_01				= sf.parse(date_str_01_);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//	String date_str_pre_15_ 	= sf.format(OBJ_UTILITY.MONTH_PRE("15", date_input));
		String sql_ = ""; 
		String sql_1= "";
		List<String> list_1 		= null;
		String month_				= sf.format(date_input).substring(3,5); 
		String year_				= sf.format(date_input).substring(6,10);
		
		

		
		sql_ =	
			"SELECT DISTINCT T.EMPSN  FROM N_EMP_QUIT T\n" +
			"                   WHERE T.THANG_TANGLAI IS NOT NULL\n" + 
			"                   AND TO_DATE('01/'||TO_CHAR(T.THANG_TANGLAI,'MM/YYYY'),'DD/MM/YYYY')\n" + 
			"                            = TO_DATE('"+date_str_01_+"','DD/MM/YYYY')\n" + 
			" AND (T.TYLE_TANGLAI = 1 OR T.TYLE_TANGLAI = 20)"+

	//     	" AND t.empsn ='12080485'"+


		"";

		list_ = obj_util.Exe_Sql_String(sql_);

		for (String empsn : list_) {
			System.out.println("Add_Emp_Quit_Work_Again()-----"+empsn+"--------");
			/*if(check_condition(empsn)== false){
				continue;//bo qua duyet tiep
			}*/
			
			N_N_HEALTH_R objData = obj_dao.findById(empsn);
			long salary_b			= obj_emp_.Get_Salary_Basic(empsn, date_input);
			madv=obj_emp.Get_depsn(empsn, date_input);
			if (!madv.equals("ENULL")){
				if (thuoc_xuong(madv))
				{
					String str_info			   = "";
					
					sql_1 = 
						"SELECT DISTINCT T.EMPSN  FROM N_EMP_QUIT T\n" +
						"WHERE T.EMPSN = '"+empsn+"'\n" + 
						"AND T.TYLE_TANGLAI = 1";
					list_1 = obj_util.Exe_Sql_String(sql_1);
					if(list_1.size()>0)
					{ //insert or update tang 1 tay
						if(objData == null)// khong co trong N_N_HELTH_R thi insert
						{
						   
							String sql_i = 
								" INSERT INTO N_N_HEALTH_R(EMPSN,NUM,SALARY_B,NUM_USED,STATUS,THANG_TANGMOI,STATUS_TT_TMOI)\n" +
								" VALUES('"+empsn+"',0,"+salary_b+",0,1,TO_DATE('"+date_str_01_+"','DD/MM/YYYY'),1)";
							 
							 OBJ_UTILITY obj_util = new OBJ_UTILITY();
						     obj_util.Exe_Update_Sql(sql_i);
							
						}else//co thi update di lam lai 1 tay
						{
							/*Date date_lock 		= objData.getLOCK_DATE() ==null? null:objData.getLOCK_DATE();
							if(Check_Locked(date_lock,date_input) == false){
								continue; // false : khong cho phep Update_Data09040984
							}*/
							 
							 objData.setDATE_B(null);
							 objData.setNUM((long)(0));
							 objData.setSALARY_B(salary_b);
							 objData.setLIMIT_CARD(null);
							 objData.setNUM_USED((long)0);
							 objData.setNOTE(null);
							 objData.setSTATUS((long)1);
							 objData.setDEBT((long)0);
							 objData.setMONEY((long)0);
							 objData.setLOCK_DATE(null);
							 objData.setDEPSN(madv);
							 objData.setSALARY_M((long)0);
							 objData.setBEAR_DATE_E(null);
							 objData.setDEBT_DC(null);
							 objData.setTHANG_TANGMOI(date_01);
							 objData.setSTATUS_TT_TMOI((long)1);
							 try{
									obj_dao.update(objData);
								}catch (Exception e) {
									str_error_ = str_error_ + empsn;
								}
						}
						
					}else
					{ //insert or update tang 20 tay
						if(objData == null)// khong co trong N_N_HELTH_R thi insert
						{
						    
							String sql_i = 
								" INSERT INTO N_N_HEALTH_R(EMPSN,NUM,SALARY_B,NUM_USED,STATUS,THANG_TANGMOI,STATUS_TT_TMOI)\n" +
								" VALUES('"+empsn+"',0,"+salary_b+",0,1,TO_DATE('"+date_str_01_+"','DD/MM/YYYY'),3)";
							 
							 OBJ_UTILITY obj_util = new OBJ_UTILITY();
						     obj_util.Exe_Update_Sql(sql_i);
							
						}else//co thi update di lam lai 20 tay
						{
							/*Date date_lock 		= objData.getLOCK_DATE() ==null? null:objData.getLOCK_DATE();
							if(Check_Locked(date_lock,date_input) == false){
								continue; // false : khong cho phep Update_Data09040984
							} */
							
							 objData.setDATE_B(null);
							 objData.setNUM((long)(0));
							 objData.setSALARY_B(salary_b);
							 objData.setLIMIT_CARD(null);
							 objData.setNUM_USED((long)0);
							 objData.setNOTE(null);
							 objData.setSTATUS((long)1);
							 objData.setDEBT((long)0);
							 objData.setMONEY((long)0);
							 objData.setLOCK_DATE(null);
							 objData.setDEPSN(madv);
							 objData.setSALARY_M((long)0);
							 objData.setBEAR_DATE_E(null);
							 objData.setDEBT_DC(null);
							 objData.setTHANG_TANGMOI(date_01);
							 objData.setSTATUS_TT_TMOI((long)3);
							 try{
									obj_dao.update(objData);
								}catch (Exception e) {
									str_error_ = str_error_ + empsn;
								}
						}
						
					}

					
				}else
				{
					continue;
				}
			}
		}
		return str_error_;
	}
	    
	
    //*End 14/08/2012 ADD NHUNG NGUOI NGHI VIEC TANG LAI TRONG N_EMP_QUIT:	
	

	//(*)Ngan begin 04/07/2012_lay luong tru BHYT khi nghi viec khong du cong
	// *Begin select lay ra xem nguoi do co phai la tang moi tai thang bat ky dua vao khong?
	private boolean Check_Emp_New(String empsn,Date date_input){
		boolean flag = false;
		String sql	 = "";
		List<String> list_ = null;

		String date_str_01_ 		= sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input)); 
		String date_str_pre_15_ 	= sf.format(OBJ_UTILITY.MONTH_PRE("15", date_input));
		String month_				= sf.format(date_input).substring(3,5); 
		String year_				= sf.format(date_input).substring(6,10);

		sql =	" SELECT t.empsn\n" +

		"FROM n_labour t" + 


		" where (t.date_s = to_date('"+date_str_01_+"','dd/mm/yyyy')\n" + 
		"			or t.date_s = to_date('"+date_str_pre_15_+"','dd/mm/yyyy'))\n" + 
		" AND t.times = 1" +
		
	//	" AND t.empsn ='12050070'"+
		"\n"+	
		//add them nhung nhan vien dieu dong khu--thang nhap xuong moi se la thang tang moi 02/12/2011
		" union\n" +
		"\n" + 
		"select t.empsn\n" + 
		"from n_newworker_test t,n_employee e,n_labour lb\n" + 
		"where t.empsn = e.empsn\n" + 
		"and e.empsn = lb.empsn\n" + 
		"and t.dd_khu = '1'\n" + 
		"and lb.clock = '1'\n" + 
		"and to_char(e.ngaynx_moi,'mm/yyyy') = '"+month_+"/"+year_+"'"+
	//	" AND t.empsn ='12050070'"+


		"";
		list_ = obj_util.Exe_Sql_String(sql);

		if(list_.size() == 0)
		{
			return false;//khong co tang moi tai date_input
		}else
			return true;//co tang moi tai date_input

	}
	//*End select lay ra xem nguoi do co phai la tang moi tai thang bat ky dua vao khong?
	private String sql_select_combsaly(String empsn,String month,String year,String table)
	{
		String sql 			 = "";
		sql = 
			"select t.combsaly,t.ylbx from "+table+year+month+" t\n" +
			"where t.empsn = '"+empsn+"'\n" + 
			"and t.ylbx is not null\n";

		return sql;
	}

	//**Begin select ylbx <> tu so the va bang luong dua vao
	private long Check_tru_BHYT_tu_bangLuongKT(String empsn,Date date_input)
	{
		String sql           = "";
		String sql1			= "";	
		String month_		= sf.format(date_input).substring(3,5); 
		String year_			= sf.format(date_input).substring(6,10);
		String month_pre		= "";
		String year_pre		= "";
		String date_att_str  = "";
		Date   date_att		= date_input;
		ArrayList<Object> list_   = null;
		ArrayList<Object> list_1	 = null;
		long salary_		    = 0;
		long ylbx			= 0;

		if (Check_Emp_New(empsn, date_att))
		{
			salary_ = obj_emp.Get_Salary_Basic(empsn, date_att);
		}else
		{
			sql = sql_select_combsaly(empsn, month_, year_, "att");


			list_ = obj_util.Exe_sql_nfield_1row(sql, 2);
			if(list_.size() == 0)
			{
				sql1 =	sql_select_combsaly(empsn, month_, year_, "attquit");

				list_1 = obj_util.Exe_sql_nfield_1row(sql1, 2);
				if(list_1.size() == 0)
				{
					//salary_ = obj_emp.Get_Salary_Basic(empsn, date_att);
					salary_ = 0;//tiep tuc lui de tim xem nhung thang truoc co tru YT khong?
				}else
				{
					ylbx = Integer.valueOf(list_.get(1).toString());
					if(ylbx == 0)
					{
						salary_ = 0;
					}else
					{
						salary_ = Integer.valueOf(list_1.get(0).toString());
					}
				}

			}else 
			{
				ylbx = Integer.valueOf(list_.get(1).toString());
				if(ylbx == 0)
				{
					salary_ = 0;
				}else
				{
					salary_ = Integer.valueOf(list_.get(0).toString());
				}
			}
		}	
		return salary_;
	}

	//**End select ylbx <> tu so the va bang luong dua vao
	private long Get_Salary_b_quit_notEnougDayWork(String empsn, Date date_input)
	{
		long salary 			= 0;
		String month_		= sf.format(date_input).substring(3,5); 
		String year_			= sf.format(date_input).substring(6,10);
		String month_pre		= "";
		String year_pre		= "";
		String date_att_str  = "";
		Date   date_att		= date_input;
		if (month_ != null && month_.equals("01"))
		{
			month_pre 		= "12";
			year_pre			= String.valueOf(Integer.valueOf(year_)-1);
		}else
		{
			month_pre		= "0"+String.valueOf(Integer.valueOf(month_)-1);
			year_pre			= year_;
		}
		date_att_str = "01/"+month_pre+"/"+year_pre;
		try {
			date_att     = sf.parse(date_att_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Check_Emp_New(empsn, date_input))
		{
			salary = obj_emp.Get_Salary_Basic(empsn, date_input);
		}else//khong co tang moi thi lui ve bang luong KToan de lay luong 
		{//xet ylbx neu <> 0 thi lay combsaly con = 0 thi lui tiep ve truoc
			salary = Check_tru_BHYT_tu_bangLuongKT(empsn, date_att);
			while(salary == 0)
			{
				month_pre = month_pre;
				year_pre  = year_pre;
				if (month_pre != null && month_pre.equals("01"))
				{
					month_pre 		= "12";
					year_pre			= String.valueOf(Integer.valueOf(year_pre)-1);
				}else
				{
					month_pre		= "0"+String.valueOf(Integer.valueOf(month_pre)-1);
					year_pre			= year_pre;
				} 
				date_att_str = "01/"+month_pre+"/"+year_pre;
				try {
					date_att     = sf.parse(date_att_str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				salary = Check_tru_BHYT_tu_bangLuongKT(empsn, date_att);	
				date_att_pre = date_att;
			}
		}

		return salary;
	}

	//Ngan end 04/07/2012_lay luong tru BHYT khi nghi viec khong du cong	


	//thay doi moi ngay 16/10/2012 xet cho truong hop tang moi do ky hop dong va dieu dong khu
	private boolean check_condition(String empsn) {//select count = 1 bo qua gan flag = flase
		boolean flag = true;//false bo qua khong add vao theo doi,true add vao theo doi
		String date_str_01_ 		= sf.format(OBJ_UTILITY.MONTH_NOW("01", date_input));
		String my_str 			    = sf.format(date_input).substring(3, 10);
		OBJ_UTILITY obj_Utility 	= new OBJ_UTILITY();
		String sql_1 = 

				"SELECT COUNT(T.EMPSN) FROM N_NOT_INSURANCE T\n" +
						"WHERE TO_DATE('01/'||TO_CHAR(T.DATES,'MM/YYYY'),'DD/MM/YYYY')\n" + 
						"<= TO_DATE('"+date_str_01_+"','DD/MM/YYYY')\n" + 
						"AND T.EMPSN = '"+empsn+"'";
	

		Object rs = obj_Utility.Exe_Sql_Obj(sql_1);
		if(Long.valueOf(rs.toString())==0)// neu khong ton tai thi xet tiep
		{
			String sql_2 = 
					"SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n" +
			         "WHERE T.EMPSN = '"+empsn+"'";
			
		    Object rs2 = obj_Utility.Exe_Sql_Obj(sql_2);
		    if(Long.valueOf(rs2.toString())==0)//neu khong ton tai trong emp_quit thi add vao tang 1 tay
		    {
		    	flag = true;
		    			
		    }else //neu ton tai trong emp_quit thi xet tiep 
			    {
			    	String sql_3 =
			    			"SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n" +
			    			"WHERE T.EMPSN = T.EMPSN\n" + 
			    			"AND TO_CHAR(T.THANG_TANGLAI,'MM/YYYY') = '"+my_str+"'\n"+
			    			"AND T.EMPSN = '"+empsn+"'";
			    	Object rs3 = obj_Utility.Exe_Sql_Obj(sql_3);
				    if(Long.valueOf(rs3.toString())==0)//neu khong ton tai thi xet tiep
				    {
				    	String sql_4 =
				    			"SELECT COUNT(T.EMPSN) FROM N_EMP_QUIT T\n" +
				    			"WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + 
				    			"       = TO_DATE('"+date_str_01_+"','DD/MM/YYYY')\n" + 
				    			"AND T.NOTE_GIAM_BHYT = 'GIAM TANG MOI'\n" + 
				    			"AND T.EMPSN = '"+empsn+"'";
				    	
				    	Object rs4 = obj_Utility.Exe_Sql_Obj(sql_4);
				    	if(Long.valueOf(rs4.toString())==0)////neu khong ton tai thi xet tiep
				    	{
				    		String sql_5 =
	
				    				"SELECT COUNT(T.EMPSN)    FROM N_EMP_QUIT T\n" +
				    			    "WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + 
				    			    "      = TO_DATE('"+date_str_01_+"','DD/MM/YYYY')\n" + 
				    			    "AND T.FROM_DATE IS NOT NULL AND T.TO_DATE IS NOT NULL\n" + 
				    			    "AND T.EMPSN = '"+empsn+"'";
				    		
				    		Object rs5 = obj_Utility.Exe_Sql_Obj(sql_5);
					    	if(Long.valueOf(rs5.toString())==0)//neu khong ton tai thi kiem tra xem ngay thuc nghi max I co > hon thang ky trinh k(> add vao theo doi, else bo qua)
					    	{
					    		String sql_6 = 
					    				"SELECT COUNT(T.EMPSN)    FROM N_EMP_QUIT T\n" +
					    				"WHERE TO_DATE('01/'||TO_CHAR(T.REAL_OFF_DATE,'MM/YYYY'),'DD/MM/YYYY')\n" + 
							    		"      > TO_DATE('"+date_str_01_+"','DD/MM/YYYY')\n" + 
							    	    "AND T.EMPSN = '"+empsn+"'";
					    		
					    		Object rs6 = obj_Utility.Exe_Sql_Obj(sql_6);
					    		if(Long.valueOf(rs6.toString())==0)
					    		{
					    			flag = false;//neu khong ton tai thi chac chan nguoi nay nhan su khong bao tang 1 tay(bo qua)
					    		}else //neu ton tai thi add vao theo doi 1 tay vi chua den ngay nghi
					    		{
					    			flag = true;
					    		}
					    			
					    	}else//neu co ton tai thi fai add vao tang 1 tay
					    	{
					    		flag = true;
					    	}
	
				    	}else//NEU CO THI BO QUA TUC NO DA BI GIAM TRONG TANG MOI
				    	{
				    		flag = false;
				    	}
				    }else//neu co thi bo qua de qua ham add_emp_quit_W_again add nviec tang lai 1tay or 20tay
				    {
				    	flag = false;
				    }
	
			    }
		}else // neu ton tai thi k add vao tang 1 tay vi nsu khong mua bao hiem nhung nguoi nay bat dau tu thang do tro ve sau
		{
			flag = false;
		}
		
		return flag;

	}

	//xet cho nhung truong hop nghi viec di lam lai tang 1 tay_Giam tang moi
	// or tang 20 tay ma chua den ky gia han the da nghi viec
	private boolean check_condition_quit_work_again(String empsn)
	{
		//*Begin kiem tra neu nghi viec di lam lai tang 1tay ma 'Giam tang moi' tai thang tai moi lai 25/02/2012
		String date_kytrinh_str 			= sf.format(date_input).substring(3, 10);
		String month_kt	 					= date_kytrinh_str.substring(0,2);
		String year_kt						= date_kytrinh_str.substring(3,7);	
		String sql =
			"select count(t.empsn)\n" +
			"from n_n_health_r t,n_emp_quit q\n" + 
			"where t.empsn = q.empsn\n" + 
			"and to_char(q.thang_trubh,'MM/yyyy') = '"+date_kytrinh_str+"'\n" +  
			" and q.note_giam_bhyt = 'GIAM TANG MOI'\n" + 
			" and t.status_tt_tmoi = 1 "+
			" and t.empsn = '"+empsn+"'";

		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		Object eq 			 = obj_util.Exe_Sql_Obj(sql);
		if (Long.valueOf(eq.toString()) == 0){
			sql =//co the chua kip tang moi da nghi viec or chua den ky gia han the da nviec(status_tt_tmoi = 1or 2)
				"select count(t.empsn)\n" +
				"from n_n_health_r t,n_emp_quit q\n" + 
				"where t.empsn = q.empsn\n" + 
				"and to_char(q.thang_trubh,'MM/yyyy') = '"+date_kytrinh_str+"'\n" + 
				"	and to_number(to_char(t.thang_tangmoi,'yyyy')) > "+Integer.valueOf(year_kt)+
				" and t.empsn = '"+empsn+"'";
			obj_util 		 = new OBJ_UTILITY();
			eq 			 = obj_util.Exe_Sql_Obj(sql);
			if (Long.valueOf(eq.toString()) == 0){
				sql =
					"select count(t.empsn)\n" +
					"from n_n_health_r t,n_emp_quit q\n" + 
					"where t.empsn = q.empsn\n" + 
					"and to_char(q.thang_trubh,'MM/yyyy') = '"+date_kytrinh_str+"'\n" + 
					"	and to_number(to_char(t.thang_tangmoi,'yyyy')) = "+Integer.valueOf(year_kt)+
					"	and to_number(to_char(t.thang_tangmoi,'MM')) > "+Integer.valueOf(month_kt)+
					" and t.empsn = '"+empsn+"'";
				obj_util 		 = new OBJ_UTILITY();
				eq 			 = obj_util.Exe_Sql_Obj(sql);
				if (Long.valueOf(eq.toString()) == 0){
					return false;
				}
			}



		}


		note_work_again = "--Thang "+date_kytrinh_str+" 'GIAM TANG MOI' hoac chua den thang tang moi hoac chua den ky gia han the da nghi viec ";
		return true;
	}

	/**
	 * <P> * Tinh lai so NUM tuy thuoc vao kha nang co the tru tien BHYT cua thang
	 * @return 	0 : NUM
	 *<P>		1 : DEBT
	 *<P>		2 : MONEY
	 *<P>		3 : note_int	=   list_recount.get(3);
	 *
	 *<P>				if(note_int == 0){
	 *<P>					note_recount	= " -- Luong thuc nhan qua it -> xet lai tien BHYT : ";
	 *<P>				}
	 *
	 */



	public ArrayList<Long> ReCount_BT(String empsn,long num_input,long num_h,long money_dc,Date date_input_){//num_h la so thang lay tu num trong n_n_health_r da giam theo thang trong code

		ArrayList<Long> list = new ArrayList<Long>();
		long salary_total 	= 0;
		long salary_b	  	= 0;
		long  num_real		= 0;
		long num_ht 		= 1;//tru thang hien tai truoc
		long debt_          = 0;//xet xem co con no hay khong??
		String month_		= sf.format(date_input_).substring(3, 5);
		long debt_dc  		= 0;
		long dc_numh		= 0;
		flag_dc				= false;
		
		if (money_dc == 0 && num_h <= 0){ dc_numh = 0;}
		else { dc_numh = money_dc/num_h;}

		OBJ_EMPSN obj_emp 	= new OBJ_EMPSN(empsn,date_input_);

		if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS)
			salary_total 		= obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
		else{
			salary_total		= obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
		}
		
		//28/09/2012_them tru dc luong giong ben nghi viec co so thang d/c
		if(money_dc > 0) //chi xet d/c khi co su thay doi luong
		{
				if (month_.equals("02")||month_.equals("05")||month_.equals("08")||month_.equals("11") )
				{
				  
					if (salary_total <  dc_numh )
					{
						salary_total 	 = salary_total;
						money_dc 		 = 0;
						debt_dc		     = 1;  //khong dieu chinh luong duoc
						flag_dc			 = false;
					}else
					{
						if(num_h >= 2)
						{
							if( salary_total - money_dc < 0  )//o day so tien chac chan >= dc_numh
							{
								salary_total = salary_total - dc_numh;
								money_dc 		 = dc_numh;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc_bt	 = 1;	//dc duoc 1 thang
								flag_dc			 = true;
							}else if(salary_total - money_dc >= 0 )
							{
								salary_total = salary_total - money_dc;
								money_dc		 = money_dc;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc_bt	 = 2;	//dc duoc 2 thang
								flag_dc			 = true;
							}
						}else // truong hop dau quy chi tru duoc den T8
						{
							if(salary_total - money_dc >= 0)
							{
								salary_total	 = salary_total - money_dc;
								money_dc		 = money_dc;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc_bt	 = 0;	//dc duoc 1 thang nhung gan = 0 de phan biet voi truong hop T7 tru 3T(7,8,9) nhung chi d/c duoc 1T(8) vi truong hop nay se tru them no T9 voi muc luong T8 nen phai up lai luong de T9 chi d/c so voi muc luong T8
								flag_dc			 = true;
							}else
							{
								salary_total 	 = salary_total;
								money_dc 		 = 0;
								debt_dc		     = 1;  //khong dieu chinh luong duoc
								flag_dc			 = false;
							}
						}
					
		
					}
				}else if(month_.equals("03")||month_.equals("06")||month_.equals("09")||month_.equals("12"))
				{
					if (salary_total <  money_dc )
					{
						salary_total 	 = salary_total;
						money_dc 		 = 0;
						debt_dc		     = 1;  //khong dieu chinh luong duoc
						flag_dc			 = false;
					}else
					{
							salary_total = salary_total - money_dc;
							money_dc		 = money_dc;
							debt_dc          = 0;//dc luong duoc  
							sothang_dc_bt	 = 1; //dc duoc 1 thang
							flag_dc	    	 = true;
							
					}
				}
		}else
		{
			if(num_h > 0) // 10/10/2012 da tru den thang hien tai nhung thang hien tai khong co thay doi luong
			{	salary_total = salary_total;
				money_dc     = 0;
				debt_dc		 = 0; //
				sothang_dc_bt= 4; //= 4 khong co su thay doi luong, dat bang 4 de k xet ghi chu la co thay doi luong
				flag_dc		 = false;
			}else //// 10/10/2012 chua tru duoc den thang hien tai--> hien tai tru voi muc luong hien hanh--> ky trinh lay luong hien hanh
			{
				salary_total = salary_total;
				money_dc     = 0;
				debt_dc		 = 0; 
				sothang_dc_bt= 5; //= 5 tru luon thang hien hanh voi muc luong moi vi chua tru duoc den thang hien hanh
				flag_dc		 = true;
			}
		}
		salary_b			= obj_emp.Get_Salary_Basic(empsn, date_input_);

		num_real			= (long) (salary_total/(salary_b * 1.5/100));
		debt_ 				= num_input - num_real;


		if (num_real == 0) {
			System.out.println("-          - tong luong qua it");
			list.add(num_h); 							// NUM 0
			list.add((long)(num_input - num_real)); 		//DEBT 1
			list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY 2
			list.add((long) 0);	//Note 3
			list.add(debt_dc); // 4
			list.add(sothang_dc_bt);//5
		}
		else if ((num_real < num_input) && (debt_ > 0)) {
			switch (Integer.valueOf(month_)){
			case 2:case 5:	case 8:	case 11:
			case 3:	case 6:	case 9:	case 12:
				System.out.println("-          - tong luong qua it");
				list.add(num_ht); 							// NUM
				list.add((long)(num_input - num_real)); 		//DEBT
				list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY
				list.add((long) 0);								//Note_int Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT
				list.add(debt_dc);
				list.add(sothang_dc_bt);//5
				break;
			case 1:case 4:	case 7:	case 10:
				System.out.println("-          - tong luong qua it");
				if (debt_ == 1){
					list.add(num_ht+1); 							// NUM
					list.add((long)(num_input - num_real)); 		//DEBT
					list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY
					list.add((long) 0);	
					list.add(debt_dc);
					list.add(sothang_dc_bt);//5
				}
				else if (debt_ >= 2){
					list.add(num_ht); 							// NUM
					list.add((long)(num_input - num_real)); 		//DEBT
					list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY
					list.add((long) 0);
					list.add(debt_dc);
					list.add(sothang_dc_bt);//5
				}
			}
		}else{
			System.out.println(" -          - tong luong OK");
			list = null;
		}




		return list;

	}

	public ArrayList<Long> ReCount_NV(String empsn,long money,long money_dc,long num_,long num_debt,long num_recovery,long num_h,Date date_input_,long flag_,long salary){

		ArrayList<Long> list = new ArrayList<Long>();
		long salary_total 	= 0;
		long salary_b	  	= 0;
		long  num_real		= 0;
		long sothang_dc     = 0;
		String month_		= sf.format(date_input_).substring(3, 5);
		long debt_dc  		= 0;
		long dc_numh		= 0;
		flag_dc 			= false;
		

		if (money_dc == 0 && num_h <= 0){ dc_numh = 0;}
		else { dc_numh = money_dc/num_h;}

		OBJ_EMPSN obj_emp 	= new OBJ_EMPSN(empsn,date_input_);
		//*mail 26/09/2012 dau quy thi nghi viec lay luong dau quy de tru
		  	// giua quy : luong giua quy >= luong dau quy ky trinh  lay luong giua quy nguoi lai lay luong dau quy
		    // cuoi quy : luong cuoi quy
		N_N_HEALTH_R obj_update = obj_dao.findById(empsn);
		

		if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS)
			salary_total 		= obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
		else{
			salary_total		= obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
		}

	/*	if (month_.equals("01")||month_.equals("04")||month_.equals("07")||month_.equals("10"))//27/10/2011 them vao cho dang Nsan --> Nviec dau quy nhung van d/c de lay luong htai(chi co dang nsan cua thang cuoi cung > 15)
		{
			if (salary_total < dc_numh)
			{
				money_dc		 = 0;
				debt_dc		     = 1;//khong d/c duoc
				flag_dc			 = false;
			}else{ money_dc = dc_numh;
			debt_dc  = 0;
			flag_dc	 = true;}
		}*/
		if (month_.equals("01")||month_.equals("04")||month_.equals("07")||month_.equals("10"))
		{
			salary_cb_nv = salary;
		}
		//24/09/2011_them tru dc luong
		if(money_dc > 0)
		{
			
				if (month_.equals("02")||month_.equals("05")||month_.equals("08")||month_.equals("11") )
				{
		
					if (salary_total <  dc_numh )
					{
						salary_total 	 = salary_total;
						money_dc 		 = 0;
						debt_dc		     = 1;  //khong dieu chinh luong duoc
						flag_dc 		 = false;
						salary_cb_nv     = obj_update.getSALARY_B()==null?0:obj_update.getSALARY_B();
					}else
					{
						
						if(num_h >= 2)
						{
							if( (salary_total - money_dc < 0) &&( flag_ == 0) )
							{
								salary_total = salary_total - dc_numh;
								money_dc 		 = dc_numh;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc		 = 1;	//dc duoc 1 thang
								flag_dc			 = true;
								salary_cb_nv     = salary;
							}else if(salary_total - money_dc >= 0  && flag_ == 0)
							{
								salary_total = salary_total - money_dc;
								money_dc		 = money_dc;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc		 = 2;	//dc duoc 2 thang
								flag_dc			 = true;
								salary_cb_nv     = salary;
							}
						}else //truong hop num_h = 1
						{
							if(salary_total - money_dc >= 0  && flag_ == 0)
							{
								salary_total = salary_total - money_dc;
								money_dc		 = money_dc;
								debt_dc          = 0;//dc luong duoc  
								sothang_dc		 = 0;	
								flag_dc			 = true;
								salary_cb_nv     = salary;
							}
						}
		
					}
				 
				}else if(month_.equals("03")||month_.equals("06")||month_.equals("09")||month_.equals("12"))
				{
					if (salary_total <  money_dc && flag_ == 0)
					{
						salary_total 	 = salary_total;
						money_dc 		 = 0;
						debt_dc		     = 1;  //khong dieu chinh luong duoc
						flag_dc 		 = false;
						salary_cb_nv     = obj_update.getSALARY_B()==null?0:obj_update.getSALARY_B(); 
					}else if (salary_total >= money_dc && flag_ == 0)
					{
							salary_total = salary_total - money_dc;
							money_dc		 = money_dc;
							debt_dc          = 0;//dc luong duoc  
							sothang_dc_bt	 = 0;	//dc duoc luong thang cuoi quy k can ghi chu 1 hay 2
							flag_dc			 = true;
							salary_cb_nv     = salary;
						}
				}
		  }else //lay theo luong moi
		  {
			  salary_cb_nv = salary;
		  }
			
		
		//	salary_b			= obj_emp.Get_Salary_Basic(empsn, date_input_); OLD
			salary_b			= salary;
		

		if ((salary_total+money_dc) < money )
		{
			//**
			System.out.println("-          - tong luong qua it");
			num_real			= (long) (salary_total/(salary_b * 1.5/100));
			if (num_real >= (num_+num_debt))//Tru duoc num+num_debt thang du tinh
			{
				salary_total   = (long) (salary_total - (salary_b*(num_+num_debt)*0.015));
				num_real	   = (long) (salary_total/(salary_b * 0.03));//set lai num_real de xet tru num_recovery
				if (num_real <= num_recovery)
				{   
					long num_recovery_ht = num_real;//so thang thu hoi hien tai tru duoc
					list.add(num_); 	//NUM 0
					list.add((long)(num_debt)); 	//1	//DEBT de set vao ghi chu vi nghi viec k can xet debt con lai
					list.add((long) ((salary_b*(num_+num_debt)*0.015)+(salary_b*num_recovery_ht*0.03) + money_dc)) ;//2
					list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
					list.add(debt_dc); //4
					list.add(sothang_dc);//5
					list.add(num_recovery_ht);//6
				}
			}else
			{
				if(num_real >= num_)
				{
					salary_total   = (long) (salary_total - (salary_b*num_*0.015));
					num_real   	   = (long)(salary_total/(salary_b*0.015));
					if (num_real < num_debt)
					{
						long num_debt_ht  = num_real;	
						list.add(num_); 	//NUM 0
						list.add((long)(num_debt_ht)); 		//DEBT 1
						list.add((long) ((salary_b*(num_*0.015))+ (salary_b*(num_debt_ht*0.015)) + money_dc)) ;
						list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
						list.add(debt_dc);
						list.add(sothang_dc);
						list.add((long)0);// thu hoi duoc OT
					}
				}else //num_real < num_
				{
					long num_ht = num_real; //so thang hien tai tru duoc
					list.add(num_ht); //0
					list.add((long)(0)); 		//DEBT 1 (khong tru duoc no)
					list.add((long) ((salary_b*(num_ht*0.015)) + money_dc)) ;
					list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
					list.add(debt_dc);
					list.add(sothang_dc);
					list.add((long)0);// thu hoi duoc OT
				}
			}
			//**

		}else{
			System.out.println(" -          - tong luong OK");
			list = null;
		}

		return list;

	}

	//26/09/2011 Ngan sua lai reCount-->recount_ns cho nghi san them num_h va money_dc
	//Tu T06/2012 khong su dung ham nay nua ma su dung ReCount_ns_new o duoi de tra lai tien Nsan
	public ArrayList<Long> ReCount_ns(String empsn,long num_input,long num_h,long money_dc,Date date_input_){

		ArrayList<Long> list = new ArrayList<Long>();
		long salary_total 	= 0;
		long salary_b	  	= 0;
		long  num_real		= 0;
		long debt_dc  		= 0;
		long sothang_dc     = 0;
		String month_		= sf.format(date_input_).substring(3, 5);
		long dc_numh		= 0;

		if (money_dc == 0 && num_h <= 0){ dc_numh = 0;}
		else { dc_numh = money_dc/num_h;}


		OBJ_EMPSN obj_emp 	= new OBJ_EMPSN(empsn,date_input_);

		if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS)
			salary_total 		= obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
		else{
			salary_total		= obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
		}

		salary_b			= obj_emp.Get_Salary_Basic(empsn, date_input_);
		//10/09/2011 Ngan sua
		if (month_.equals("02")||month_.equals("05")||month_.equals("08")||month_.equals("11") 
				||month_.equals("03")||month_.equals("06")||month_.equals("09")||month_.equals("12")){

			if (salary_total <  dc_numh )
			{
				salary_total 	 = salary_total;
				money_dc 		 = 0;
				debt_dc		     = 1;  //khong dieu chinh luong duoc
			}else
			{
				if( (salary_total - money_dc < 0) || (salary_total - money_dc > 0 && num_h == 1) )
				{
					salary_total = salary_total - dc_numh;
					money_dc 		 = dc_numh;
					debt_dc          = 0;//dc luong duoc  
					sothang_dc		 = 1;	//dc duoc 1 thang
				}else if(salary_total - money_dc > 0 && num_h == 2)
				{
					salary_total = salary_total - money_dc;
					money_dc		 = money_dc;
					debt_dc          = 0;//dc luong duoc  
					sothang_dc		 = 2;	//dc duoc 2 thang
				}else
				{
					salary_total = salary_total - money_dc;
					money_dc		 = money_dc;
					debt_dc          = 0;//dc luong duoc  
					sothang_dc		 = 0;
				}

			}
		}


		//end 10/09/2011

		num_real			= (long) (salary_total/(salary_b * 1.5/100));

		if(num_real < num_input){
			System.out.println("-          - tong luong qua it");
			list.add(num_real); 							// NUM 0
			list.add((long)(num_input - num_real)); 		//DEBT 1
			list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY 2
			list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
			list.add(debt_dc);//4
			list.add(sothang_dc);//5
		}else{
			System.out.println(" -          - tong luong OK");
			list = null;
		}

		return list;

	}

	//*23/06/2012 Viet ReCount_ns_new vi yc moi co tra lai tien Nsan nen tinh theo money tru k tinh theo num_input nua 
	public ArrayList<Long> ReCount_ns_new(String empsn,long money,long num_input,long num_h,long money_dc,Date date_input_){

		ArrayList<Long> list = new ArrayList<Long>();
		long salary_total 	= 0;
		long salary_b	  	= 0;
		long  num_real		= 0;
		long debt_dc  		= 0;
		long sothang_dc     = 0;
		String month_		= sf.format(date_input_).substring(3, 5);
		long dc_numh		= 0;
		flag_dc				= false;

		if (money_dc == 0 && num_h <= 0){ dc_numh = 0;}
		else { dc_numh = money_dc/num_h;}


		OBJ_EMPSN obj_emp 	= new OBJ_EMPSN(empsn,date_input_);

		if(obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_BT || obj_emp.Get_Status_Work() == OBJ_EMPSN.empsn_NS)
			salary_total 		= obj_emp.Get_Salary_Total_By_Month(empsn, date_input_);
		else{
			salary_total		= obj_emp.Get_Salary_Total_By_Month_for_NV(empsn, date_input_);
		}

		salary_b			= obj_emp.Get_Salary_Basic(empsn, date_input_);
		//10/09/2011 Ngan sua
		if(money_dc > 0)
		{
				if (month_.equals("02")||month_.equals("05")||month_.equals("08")||month_.equals("11") 
						||month_.equals("03")||month_.equals("06")||month_.equals("09")||month_.equals("12")){
		
					if (salary_total <  dc_numh )
					{
						if(money>=0)
						{
							salary_total 	 = salary_total;
							money_dc 		 = 0;
							debt_dc		     = 1;  //khong dieu chinh luong duoc
							flag_dc			 = false;
						}else
						{
							salary_total 	 = salary_total;
							money_dc 		 = dc_numh;
							debt_dc          = 0;//dc luong duoc  
							sothang_dc		 = 1;	//dc duoc 1 thang
							flag_dc			 = true;
						}
					}else
					{
						if( (salary_total - money_dc < 0) || (salary_total - money_dc > 0 && num_h == 1) )
						{
							salary_total = salary_total - dc_numh;
							money_dc 		 = dc_numh;
							debt_dc          = 0;  //dc luong duoc  
							sothang_dc		 = 1;	//dc duoc 1 thang
							flag_dc			 = true;
						}else if(salary_total - money_dc > 0 && num_h == 2)
						{
							salary_total = salary_total - money_dc;
							money_dc		 = money_dc;
							debt_dc          = 0;//dc luong duoc  
							sothang_dc		 = 2;	//dc duoc 2 thang
							flag_dc			 = true;
						}else
						{
							salary_total = salary_total - money_dc;
							money_dc		 = money_dc;
							debt_dc          = 0;//dc luong duoc  
							sothang_dc		 = 0;
							flag_dc			 = true;
						}
		
					}
				}
		}else
		{
			if(num_h > 0) // 10/10/2012 da tru den thang hien tai nhung thang hien tai khong co thay doi luong
			{	salary_total = salary_total;
				money_dc     = 0;
				debt_dc		 = 0; //
				sothang_dc_bt= 4; //= 4 khong co su thay doi luong, dat bang 4 de k xet ghi chu la co thay doi luong
				flag_dc		 = false;
			}else //// 10/10/2012 chua tru duoc den thang hien tai--> hien tai tru voi muc luong hien hanh--> ky trinh lay luong hien hanh
			{
				salary_total = salary_total;
				money_dc     = 0;
				debt_dc		 = 0; 
				sothang_dc_bt= 5; //= 5 tru luon thang hien hanh voi muc luong moi vi chua tru duoc den thang hien hanh
				flag_dc		 = true;
			}
		}


		//end 10/09/2011

		num_real			= (long) (salary_total/(salary_b * 1.5/100));

		if(salary_total+money_dc < money)
		{
			System.out.println("-          - tong luong qua it");
			if(salary_total+money_dc < money_dc)
			{
				list.add((long)0); 							// NUM 0
				list.add((long)(num_input)); 		//DEBT 1
				list.add((long)0) ;	// MONEY 2
				list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
				list.add(debt_dc);//4
				list.add(sothang_dc);//5
			}else if(salary_total+money_dc == money_dc)
			{
				list.add((long)1); 							// NUM 0
				list.add((long)(num_input)); 		//DEBT 1
				list.add((long)money_dc) ;	// MONEY 2
				list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
				list.add(debt_dc);//4
				list.add(sothang_dc);//5
			}else //salary_total+money_dc > money_dc
			{
				if(num_real < num_input){
					System.out.println("-          - tong luong qua it");
					list.add(num_real); 							// NUM 0
					list.add((long)(num_input - num_real)); 		//DEBT 1
					list.add((long)(salary_b*num_real*1.5/100)+ money_dc) ;	// MONEY 2
					list.add((long) 0);	// Ghi chu = 0 : Luong thuc nhan qua it -> xet lai tien dong BHYT 3
					list.add(debt_dc);//4
					list.add(sothang_dc);//5
				}else{
					System.out.println(" -          - tong luong OK");
					list = null;
				}
			}
		}else//Tong luong > so tien can phai tru (Money)
		{
			System.out.println(" -          - tong luong OK");
			list = null;
		}


		return list;

	}

	/**
	 * 	Kiem tra ngay DATE_LOCK = null => true
	 *  	hoac :
	 * 	Kiem tra DATE_INPUT phai lon hon ngay DATE_LOCK la 1 thang => true
	 * 	
	 * @param date_lock
	 * @param date_input_
	 * @return true -> co the Update_Data
	 */

	private boolean Check_Locked(Date date_lock, Date date_input_) {
		boolean flag_up 		= date_lock == null ? true : false; // date_lock  = null 
		Date 	temp_date_1_ 	= null;
		Date 	temp_date_2_	= null;

		if(flag_up == false){
			Calendar ca_	= Calendar.getInstance();
			temp_date_1_ 	= OBJ_UTILITY.MONTH_NOW("01", date_lock);
			temp_date_2_	= OBJ_UTILITY.MONTH_NOW("01", date_input_);

			ca.setTime(temp_date_1_);
			ca.add(Calendar.MONTH, 1);

			flag_up			= temp_date_2_.compareTo(ca.getTime())== 0? true : false; 
			// (date_input_)  nhiu hon 1 thang (date_lock) thi OK
		}
		return flag_up;
	}

	/*public void UPDATE_DATA(N_N_HEALTH_R obj_health_r){
		try{
				long num_nv = obj_health_r.getNUM();
				if (num_nv <= 0 ){ num_nv = 1; }//De lay salary_m k bi = 0 hoac am
			    obj_emp = new OBJ_EMPSN(obj_health_r.getEMPSN(),date_input);

					if(obj_health_r != null){ // NS phai lay luong tai thoi diem bat dau NS xuat ra ky trinh
						//	if( (obj_health_r.getSTATUS() == -1) || ((obj_health_r.getSTATUS() == 0) && (obj_health_r.getDATE_B() == null)))  {
							if( (obj_health_r.getDEBT_DC() != null) && ( obj_health_r.getDEBT_DC() == 1 ) ){//1 k dc luong duoc

								obj_health_r.setSALARY_M(obj_health_r.getSALARY_B());
							}else{
								obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));
							}

							if ( (obj_health_r.getSTATUS() == -1)){
								 if (obj_health_r.getDEBT_DC() == 1){ //debt_dc == 1--> k dc luong dc lay luong old
								 obj_health_r.setSALARY_M(obj_health_r.getSALARY_B() *  num_nv);
								 }else 
									{
										obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input)* num_nv);
									}
							}
					obj_health_r.setSALARY_B(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));
					obj_health_r.setMONEY(OBJ_UTILITY.Round_Salary(obj_health_r.getMONEY()));
					obj_health_r.setLOCK_DATE(date_input);
					String dept_ = obj_emp.Get_depsn(obj_health_r.getEMPSN(), date_input);
					obj_health_r.setDEPSN(dept_);
					obj_health_r.setDATE_B(null);
					try{
						obj_dao.update(obj_health_r);

					}
					catch (Exception e) {
						System.out.println(obj_health_r.getEMPSN());
					}

				}
		}catch (Exception e) {

			System.out.println(obj_health_r.getEMPSN());
		}
	}*/

	public void UPDATE_DATA_BT(N_N_HEALTH_R obj_health_r){//24/09/2011 Ngan them
		try{
			obj_emp = new OBJ_EMPSN(obj_health_r.getEMPSN(),date_input);
			long num_nv = obj_health_r.getNUM();
			if (num_nv <= 0 ){ num_nv = 1; }//De lay salary_m k bi = 0 hoac am
			float day_of_month_not_sun = obj_util.GET_NUM_DAY_OF_MONTH_NOT_SUN(date_input);
			java.sql.Timestamp date_action =  obj_emp.GetDateTimeOracle();
            System.out.println(date_action);
         /*   java.sql.Date date_ = (java.sql.Date) sf.parse(sf.format(date_action));
            System.out.println(date_);*/

			if(obj_health_r !=null){ // NS phai lay luong tai thoi diem bat dau NS xuat ra ky trinh
				//	if( (obj_health_r.getSTATUS() == -1) || ((obj_health_r.getSTATUS() == 0) && (obj_health_r.getDATE_B() == null)))  {
				if ( (obj_health_r.getSTATUS() == -1))
				{
					//OLD
					/*	if (flag_dc == false){ //debt_dc == 1--> k dc luong dc lay luong old
							obj_health_r.setSALARY_M(obj_health_r.getSALARY_B() *  num_nv);
						}else 
						{
							obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input)* num_nv);
						}*/
					//NEW
					  obj_health_r.setSALARY_M(obj_health_r.getSALARY_B() *  num_nv);
				}else
				{
					// if( ((obj_health_r.getSTATUS() == 0) && (obj_health_r.getDATE_B() == null)) || obj_health_r.getDEBT_DC() == 1 ){
					if( (obj_health_r.getSTATUS() == 0 && flag_dc == false) 
							||(obj_health_r.getSTATUS() == 1 &&  obj_health_r.getDEBT_DC() == null ) // debt_dc == null :luong khog thay doi
							|| (obj_health_r.getSTATUS() == 1 && (obj_health_r.getDEBT_DC() != null &&  obj_health_r.getDEBT_DC() == 1))//debt_dc == 1: khong dieu chinh dc lay luong dau quy
					  ) { 
						obj_health_r.setSALARY_M(obj_health_r.getSALARY_B());
					}else{
						//Begin 10/10/2012 them vi mac dinh deb_dc = 0 la d/c duoc nen xet them neu luong htai >= luong dau quy k van den gi, neu luong htai < luong dau quy lay luong dau quy
						long luong_old = 0;
							 luong_old = obj_health_r.getSALARY_B()==null?0:obj_health_r.getSALARY_B();
					    long luong_new = 0;
					     	 luong_new = obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input);
					    
					    if(luong_old > luong_new)
					    {
					    	obj_health_r.setSALARY_M(luong_old);
					    }else 
					    {
					    	obj_health_r.setSALARY_M(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));//old
					    }
					  //End 10/10/2012 them vi mac dinh deb_dc = 0 la d/c duoc nen xet them neu luong htai >= luong dau quy k van den gi, neu luong htai < luong dau quy lay luong dau quy
					    
						if(sothang_dc_bt == 2) //28/09/2012 d/c duoc 2T thi update lai luong cb tai thang giua quy de cuoi quy moi lay duoc luong giua quy khi luong k thay doi or k d/c them duoc
							{
								obj_health_r.setSALARY_B(obj_emp.Get_Salary_Basic(obj_health_r.getEMPSN(), date_input));
							}
					}
				}


				obj_health_r.setMONEY(OBJ_UTILITY.Round_Salary(obj_health_r.getMONEY()));
				//	String dept_ = obj_emp.Get_depsn(obj_health_r.getEMPSN(), date_input);
				obj_health_r.setDEPSN(madv);
				obj_health_r.setLOCK_DATE(date_input);
				obj_health_r.setDATE_B(null);
				obj_health_r.setUSER_UP(user_name);
				obj_health_r.setDATE_UP(date_action);
				try{
					obj_dao.update(obj_health_r);

				}
				catch (Exception e) {
					System.out.println(obj_health_r.getEMPSN());
				}

			}
		}catch (Exception e) {

			System.out.println(obj_health_r.getEMPSN());
		}
	}

	public void UPDATE_DATA_DANG_NS_THI_NV(N_N_HEALTH_R obj_health_r){//10/10/2011 Ngan them
		try{


			obj_emp = new OBJ_EMPSN(obj_health_r.getEMPSN(),date_input);

			if(obj_health_r !=null){ // NS phai lay luong tai thoi diem bat dau NS xuat ra ky trinh

				obj_health_r.setLOCK_DATE(date_input);
				obj_health_r.setSALARY_M(obj_health_r.getSALARY_B());
				try{
					obj_dao.update(obj_health_r);

				}
				catch (Exception e) {
					System.out.println(obj_health_r.getEMPSN());
				}

			}
		}catch (Exception e) {

			System.out.println(obj_health_r.getEMPSN());
		}
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Grid rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(5, Extent.PX)));
		rootLayout.setSize(2);
		add(rootLayout);
		Label label1 = new Label();
		label1.setText("Xuong");
		rootLayout.add(label1);
		cbx_fact = new ComboBox();
		cbx_fact.setListModel(obj_util.Get_Model_Fact());
		cbx_fact.setActionOnSelection(true);
		cbx_fact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction_Fact(e);

			}
		});
		rootLayout.add(cbx_fact);
		Label label2 = new Label();
		label2.setText("Nhom");
		rootLayout.add(label2);
		cbx_group = new ComboBox();
		cbx_group.setActionOnSelection(true);
		cbx_group.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				doAction_Group(e);
			}
		});


		rootLayout.add(cbx_group);
		Label label3 = new Label();
		label3.setText("Don vi");
		rootLayout.add(label3);
		cbx_dept = new ComboBox();
		rootLayout.add(cbx_dept);
		Label label4 = new Label();
		label4.setText("Ngay");
		rootLayout.add(label4);
		date_export = new DscDateField();
		rootLayout.add(date_export);
		Label label5 = new Label();
		rootLayout.add(label5);

		
		Label label6 = new Label();
		rootLayout.add(label6);
		btn_export = new Button();
		btn_export.setStyleName("Default.ToolbarButton");
		btn_export.setText("Export");
		btn_export.setWidth(new Extent(70, Extent.PX));
		btn_export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction_export(e);
			}
		});
		rootLayout.add(btn_export);
	}

}
