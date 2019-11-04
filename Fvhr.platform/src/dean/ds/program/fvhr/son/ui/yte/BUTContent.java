package ds.program.fvhr.son.ui.yte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.BaoHiemYT_BUT_tn;
import ds.program.fvhr.domain.BaoHiemYT_BUT_xh;
import ds.program.fvhr.domain.BaoHiemYT_BUT_yt;
import ds.program.fvhr.domain.BaoHiemYT_SYS;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;

import echopointng.DateChooser;
import echopointng.DateField;
import fv.util.HSSFUtils;
import fv.util.Vni2Uni;

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
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;

public class BUTContent extends SplitPane {
	
	public static final int STATE_NONE = 0;
	public static final int STATE_NEW = 1;
	public static final int STATE_QRY = 3;
	
	private ResourceBundle resourceBundle;
	private Button button1_new;
	private Button button2_query;
	private Button button3_save;
	private Button button4_cancel;
	private Button button5_excel;
	
	private DateField txt23;
	private TextField txt24;
	private TextField txt25;
	private TextField txt26;
	private TextField txt27;
	private TextField txt28;
	private TextField txt29;
	private TextField txt30;
	private TextField txt31;
	private Grid grid4;

	private Label lbel1;
	private Label lbel2;
	private Label lbel3;
	private Label lbel4;
	private Label lbel5;
	
	private List<Component> listComp ;
	private Map<String,Object> mapComp ;
	private int state;
	
	/*private BaoHiemYT_BUT_tn o_TN ;
	private BaoHiemYT_BUT_xh o_XH;
	private BaoHiemYT_BUT_yt o_YT;*/
	
	private DataContent dataContent;
	
	private BUTDataControl dataCtrl ;
	private BangUngTien masterFrom;
	
	public BUTContent(BangUngTien masterForm) {
		
		this.setSeparatorPosition(new Extent(30, Extent.PX));
		this.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		
		dataCtrl 		= new BUTDataControl();
		listComp 		= new ArrayList<Component>();
		state 			= STATE_NONE;
		dataContent 	= null;
		
		this.masterFrom = masterForm;
		
		initComponent();
		
		add_Comp_To_List();
		addEvent();
		initState();
		
	}
	private void initState() {
		doUIRefresh();
	}
	private void add_Comp_To_List() {
		listComp.add(txt23);	
		listComp.add(txt24);	
		listComp.add(txt25);	
		listComp.add(txt26);	
		listComp.add(txt27);	
		listComp.add(txt28);	
		listComp.add(txt29);	
		listComp.add(txt30);	
		listComp.add(txt31);	
	}

	private void addEvent() {
		
		button1_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = STATE_NEW;
				resetTextInput();
				doUIRefresh();
			}
		});
		
		button4_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = STATE_NONE;
				resetTextInput();
				doUIRefresh();
			}
		});
		
		button2_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = STATE_QRY;
				resetTextInput();
				doQuery();
			}
		});
		button3_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doConfirm();
			}
		});
		
		button5_excel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				doExcel();
			}
		});
		
	}

	protected void doExcel() {
		SimpleDateFormat sf = new SimpleDateFormat();
		String tempDate_1 = "Ngày ";
		sf.applyPattern("dd");
		tempDate_1 = tempDate_1 + sf.format(new Date()) + " tháng ";
		sf.applyPattern("MM");
		tempDate_1 = tempDate_1 + sf.format(new Date()) + " năm ";
		sf.applyPattern("yyyy");
		tempDate_1 = tempDate_1 + sf.format(new Date());
		
		
		String tempDate_MMyyyy 	= "";
		String tempDate_next 	= "";
		String tempStr_1 		= "";
		String tempStr_2 		= "";
		HSSFWorkbook 	wb;
		HSSFSheet		sheet;
		HSSFRow	 		row;
		HSSFCell		cell;
		if(dataContent!=null && !dataContent.isNull()){
			wb = masterFrom.createWorkbook("FileUngTien");
			sf.applyPattern("MM/yyyy");
			tempDate_MMyyyy = sf.format(dataCtrl.getDateInput());
			tempDate_next	= sf.format(ObjUtility.MONTH_NEXT("01", dataCtrl.getDateInput()));
				// sheet 0 = XH
				sheet 	= wb.getSheetAt(0);
				row 	= sheet.getRow(2);
					
					cell	= row.getCell(0);
					tempStr_1 	= "SỐ TIỀN BHXH THÁNG " + tempDate_MMyyyy + " DỰ TRÙ NỘP LÀ : " ;
					tempStr_2	= "\n"+tempDate_MMyyyy + "月份已繳納的失業保險已付的預額金額 ： ";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);
					
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoXH().getDu_tru());
					
				row		= sheet.getRow(3);
					cell	= row.getCell(0);
					tempStr_1 	= "SỐ TIỀN BHXH THỰC NỘP THÁNG " + tempDate_MMyyyy + " LÀ : " ;
					tempStr_2	= "\n"+tempDate_MMyyyy + "月份實際繳納的社會保險金額：（核對後）";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoXH().getBaoHiemYT_SYS().getSo_tien());
				
				row		= sheet.getRow(4);
					cell	= row.getCell(0);
					tempStr_1 	= "TỔNG SỐ TIỀN BHXH NỘP THÊM THÁNG " + tempDate_MMyyyy + " LÀ : " ;
					tempStr_2	= "\n"+tempDate_MMyyyy + "月份繳納總金額：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);
					
				row 	= sheet.getRow(7);
					cell	= row.getCell(1);
					cell.setCellValue(Vni2Uni.convertToVNI(tempDate_1));
					
					
				// sheet 1 = TN 
				sheet 	= wb.getSheetAt(1);
				row 	= sheet.getRow(2);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHTN THÁNG " + tempDate_MMyyyy + " DỰ TRÙ NỘP LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份已繳納的失業保險已付的預額金額：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);
					
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoTN().getMoney_of_pre_Month());
					
				row		= sheet.getRow(3);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHTN THỰC NỘP THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份實際繳納的失業保險金額：（核對後）";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoTN().getBaoHiemYT_SYS().getSo_tien());
					
				row		= sheet.getRow(4);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN THIẾU BHTN PHẢI NỘP THÊM THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月本司須補納失業保險差額金額";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
				row		= sheet.getRow(5);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHTN THÁNG " + tempDate_next+ " DỰ TRÙ NỘP LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份繳納的失業保險的預額： (暫付金額）";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoTN().getDu_tru());
				
				row		= sheet.getRow(6);
					cell	= row.getCell(0);
						tempStr_1 	= "TỔNG SỐ TIỀN BHTN THIẾU THÁNG " + tempDate_MMyyyy + " PHẢI NỘP THÊM VÀ SỐ TIỀN BHTN DỰ TRÙ NỘP CHO THÁNG " + tempDate_next + " LÀ : " ;
						tempStr_2	= "\n須補納"+tempDate_MMyyyy + "月份尚欠失業保險以及" +tempDate_next+"月份失業及失業保險預額";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					
				row 	= sheet.getRow(9);
					cell	= row.getCell(1);
					cell.setCellValue(Vni2Uni.convertToVNI(tempDate_1));
				
				// sheet 2 = YT 
				sheet 	= wb.getSheetAt(2);
				row 	= sheet.getRow(2);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHYT THÁNG " + tempDate_MMyyyy + " DỰ TRÙ NỘP LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份已繳納的醫療保險預額金額：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getMoney_of_pre_Month());
					
				row		= sheet.getRow(3);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHYT THỰC NỘP THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份實際繳納的醫療保險金額：（核對後）";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getBaoHiemYT_SYS().getSo_tien());
					
				row		= sheet.getRow(4);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN THIẾU BHYT PHẢI NỘP THÊM THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月本司須給醫療保險機關補納的差額是： ";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					
				row		= sheet.getRow(5);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHYT THÁNG " + tempDate_next + " DỰ TRÙ NỘP LÀ : " ;
						tempStr_2	= "\n"+tempDate_MMyyyy + "月份繳納的醫療保險的預額： (暫付金額）　";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getDu_tru());
				row		= sheet.getRow(6);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN CÔNG TY GIỮ LẠI ĐÊ CHI TRẢ BHYT CHO NHỮNG TRƯỜNG HỢP THAI SẢN " ;
						tempStr_2	= "\n 公司留下來支付醫保給予產假場合";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getNo_1());	
				row		= sheet.getRow(7);
					cell	= row.getCell(0);
					tempStr_1 	= "TỔNG SỐ TIỀN BHYT THIẾU THÁNG " + tempDate_MMyyyy + " PHẢI NỘP THÊM VÀ SỐ TIỀN BHYT DỰ TRÙ NỘP CHO THÁNG " + tempDate_next + " LÀ : " ;
					tempStr_2	= "\n須補納"+tempDate_MMyyyy + "月份尚欠失業保險以及" +tempDate_next+"月份失業及失業保險預額";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
					
				row 	= sheet.getRow(10);
					cell	= row.getCell(1);
					cell.setCellValue(Vni2Uni.convertToVNI(tempDate_1));
					
				// sheet 3 = Bỏ việc 
				sheet 	= wb.getSheetAt(3);
				row 	= sheet.getRow(2);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN BHYT KHÔNG ĐỦ CÔNG - CTY NỘP THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n 當月沒有工資扣醫療保險 - " + tempDate_MMyyyy + "月份繳交金額：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getNo_2());
				row		= sheet.getRow(3);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN CNV NGHỈ VIỆC - BỎ VIỆC KẾ TOÁN TRỪ ĐƯỢC TIỀN THÁNG " + tempDate_MMyyyy + " LÀ : " ;
						tempStr_2	= "\n員工離職場合有薪資扣除的金額 ：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getNo_3());
				row		= sheet.getRow(4);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN CNV NGHỈ VIỆC - BỎ VIỆC CÔNG TY TRẢ TIỀN THÁNG " + tempDate_MMyyyy  ;
						tempStr_2	= "\n員工離職，自離場合-公司付的金額 :";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getNo_4());
				
				row		= sheet.getRow(5);
					cell	= row.getCell(0);
						tempStr_1 	= "SỐ TIỀN CNV NGHỈ VIỆC - BỎ VIỆC THU TIỀN MẶT THÁNG " + tempDate_MMyyyy  ;
						tempStr_2	= "\n員工離職，自離可扣到現金的金額 ：";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
					cell	= row.getCell(1);
					cell.setCellValue(dataContent.getoYT().getNo_5());	
					
				row		= sheet.getRow(6);
					cell	= row.getCell(0);
						tempStr_1 	= "TỔNG SỐ TIỀN NỘP THÁNG " + tempDate_MMyyyy + " LÀ" ;
						tempStr_2	= "\n員工離職，自離場合-公司付的金額 :";
					cell.setCellValue(Vni2Uni.convertToVNI(tempStr_1) + tempStr_2);	
				
				row 	= sheet.getRow(9);
					cell	= row.getCell(1);
					cell.setCellValue(Vni2Uni.convertToVNI(tempDate_1));
				
			masterFrom.doExport(wb);
		}else{
			ObjUtility.ShowMessageError("Hãy tìm dữ liệu trước");
		}
	}
	protected void doQuery() {
		refreshButton();
		refreshTextInput();
	}
	protected void resetTextInput() {
		for(Component comp : listComp){
			if(comp instanceof TextField){
				((TextField) comp).setText("");
			}
			else if(comp instanceof DateField){
				((DateField) comp).getTextField().setText("");
			}
		}
	}
	
	protected void doUIRefresh() {
		refreshButton();
		refreshTextInput();
		bind_dataContent_to_Component();
	}

	private void bind_dataContent_to_Component() {
		
		if(dataContent!=null && !dataContent.isNull()){
			
			lbel1.setText(dataContent.getoTN().getMoney_of_pre_Month().toString());
			lbel2.setText(dataContent.getoTN().getBaoHiemYT_SYS().getSo_tien().toString());
			lbel3.setText(dataContent.getoTN().getMoney_add_more().toString());
			lbel4.setText(dataContent.getoTN().getDu_tru().toString());
			lbel5.setText(dataContent.getoTN().getMoney_total().toString());
		}else{
			lbel1.setText("");
			lbel2.setText("");
			lbel3.setText("");
			lbel4.setText("");
			lbel5.setText("");
		}
		
	}
	private void refreshTextInput() {
		switch (state) {
		case STATE_NONE:
			for(Component comp : listComp){
				if(comp instanceof TextField){
					comp.setBackground(Color.LIGHTGRAY);
					comp.setEnabled(false);
				}
				else if(comp instanceof DateField){
					((DateField) comp).getTextField().setBackground(Color.LIGHTGRAY);
					comp.setEnabled(false);
				}
			}
			break;

		case STATE_NEW:
		
			for(Component comp : listComp){
				if(comp instanceof TextField){
					comp.setEnabled(true);
					comp.setBackground(Color.WHITE);
				}
				else if(comp instanceof DateField){
					((DateField) comp).getTextField().setBackground(Color.WHITE);
					comp.setEnabled(true);
				}
			}
			break;	
		case STATE_QRY:
			for(Component comp : listComp){
				if(comp instanceof DateField){
					((DateField) comp).getTextField().setBackground(Color.WHITE);
					comp.setEnabled(true);
				}
			}
			break;
		default:
			break;
		}
	}

	private void refreshButton() {
		switch (state) {
		case STATE_NONE:
			button3_save.setEnabled(false);
			button4_cancel.setEnabled(false);
			button1_new.setEnabled(true);
			button2_query.setEnabled(true);
			break;

		case STATE_NEW:
			button3_save.setEnabled(true);
			button4_cancel.setEnabled(true);
			button1_new.setEnabled(false);
			button2_query.setEnabled(false);
			break;
		case STATE_QRY:
			button3_save.setEnabled(true);
			button4_cancel.setEnabled(true);
			button1_new.setEnabled(false);
			button2_query.setEnabled(false);
		default:
			break;
		}
		
	}

	protected void query(){
			Date date = null ;
			if(txt23.getText().equals("")){
				ObjUtility.ShowMessageError("Hãy chọn ngày tháng");
				return;
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			try{
				date = sf.parse(txt23.getText().trim());
			}catch (Exception e) {
				ObjUtility.ShowMessageError("Ngày tháng không đúng định dạng dd/MM/yyyy");
				return;
			}
			
//			dataCtrl.setDateInput(txt23.getDisplayedDate().getTime());
			dataCtrl.setDateInput(date);
			dataContent = new DataContent(dataCtrl.getBHYT_yt(), dataCtrl.getBHYT_tn(), dataCtrl.getBHYT_xh()) ;
			
			if(dataContent.isNull()){
				ObjUtility.ShowMessageError("Không tìm thấy dữ liệu");
			}
			
			state = STATE_NONE;
			doUIRefresh();
	}
	
	protected void doConfirm() {
		boolean ret = true;
		if(state == STATE_QRY){
			query();
			return;
		}else if(state == STATE_NEW){
			
			ret = checkData();
			if(!ret){return ;}
			
			// save to database
			ret = doSave();
			
			if(!ret){return;}
			
			// after save ok , we show datas have just saved
			state = STATE_QRY;
			query();
			
			doUIRefresh();
			ObjUtility.ShowMessageOK("Lưu dữ liệu thành công !");
		}
		
		
	}
	
	private boolean doSave() {
		
		mapComp = new HashMap<String, Object>();
		try{
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sf.parse(txt23.getText().trim());
//			mapComp.put("DATE", txt23.getDisplayedDate().getTime());
			mapComp.put("DATE", date);
			mapComp.put("TN", Double.valueOf(txt24.getText().trim()));
			mapComp.put("XH", Double.valueOf(txt25.getText().trim()));
			mapComp.put("YT", Double.valueOf(txt26.getText().trim()));
			mapComp.put("NO1", Double.valueOf(txt27.getText().trim()));
			mapComp.put("NO2", Double.valueOf(txt28.getText().trim()));
			mapComp.put("NO3", Double.valueOf(txt29.getText().trim()));
			mapComp.put("NO4", Double.valueOf(txt30.getText().trim()));
			mapComp.put("NO5", Double.valueOf(txt31.getText().trim()));
			dataCtrl.mapToEntities(mapComp);
		}catch (Exception e) {
			ObjUtility.ShowMessageError("Can't mapping Component to DataControl");
			return false;
		}
		
		return dataCtrl.save();
	}
	private boolean checkData() {
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		if(txt23.getText().equals("")){
			ObjUtility.ShowMessageError("Chưa nhập ngày tháng");
			return false;
		}
		
		try{
			sf.parse(txt23.getText().trim());
		}catch (Exception e) {
			ObjUtility.ShowMessageError("Ngày tháng nhập sai quy tắc (dd/MM/yyyy)");
			return false;
		}
		
		try{
			Double.valueOf(txt24.getText().trim());
			Double.valueOf(txt25.getText().trim());
			Double.valueOf(txt26.getText().trim());
			Double.valueOf(txt27.getText().trim());
			Double.valueOf(txt28.getText().trim());
			Double.valueOf(txt29.getText().trim());
			Double.valueOf(txt30.getText().trim());
			
		}catch (Exception e) {
			ObjUtility.ShowMessageError("Dữ liệu không phù hợp");
			return false;
		}
		
		
		
		return true;
	}
	private void initComponent() {
		Row row1 = new Row();
		row1.setInsets(new Insets(new Extent(4, Extent.PX)));
		row1.setBackground(new Color(0xc0c0c0));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		row1.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.CENTER));
		row1.setLayoutData(row1LayoutData);
		this.add(row1);
		button1_new = new Button();
		button1_new.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnNew.gif");
		button1_new.setIcon(imageReference1);
		ResourceImageReference imageReference22 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/btnNewD.gif");
		button1_new.setDisabledIcon(imageReference22);
		row1.add(button1_new);
		button2_query = new Button();
		button2_query.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnQuery.gif");
		button2_query.setIcon(imageReference2);
		ResourceImageReference imageReference21 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/btnQueryD.gif");
		button2_query.setDisabledIcon(imageReference21);
		row1.add(button2_query);
		button3_save = new Button();
		button3_save.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnSave.gif");
		button3_save.setIcon(imageReference3);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnSaveD.gif");
		button3_save.setDisabledIcon(imageReference4);
		row1.add(button3_save);
		button4_cancel = new Button();
		button4_cancel.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnCancel.gif");
		button4_cancel.setIcon(imageReference5);
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnCancelD.gif");
		button4_cancel.setDisabledIcon(imageReference6);
		row1.add(button4_cancel);
		
		button5_excel = new Button();
		button5_excel.setStyleName("Default.ToolbarButton");
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif");
		button5_excel.setIcon(imageReference7);
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcelD.gif");
		button5_excel.setDisabledIcon(imageReference8);
		row1.add(button5_excel);
		
		
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(500, Extent.PX));
		splitPane2.setResizable(true);
		this.add(splitPane2);
		
		grid4 = new Grid();
		grid4.setWidth(new Extent(300, Extent.PX));
		grid4.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid4.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		grid4.setSize(1);
		splitPane2.add(grid4);
		Grid grid_BHTN5 = new Grid();
		grid_BHTN5.setInsets(new Insets(new Extent(3, Extent.PX)));
		GridLayoutData grid_BHTN5LayoutData = new GridLayoutData();
		grid_BHTN5LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		grid_BHTN5.setLayoutData(grid_BHTN5LayoutData);
		grid4.add(grid_BHTN5);
		Label label8 = new Label();
		label8.setText("Tháng");
		grid_BHTN5.add(label8);
		txt23 = new DateField();
		txt23.setTextField(new TextField());
		txt23.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		txt23.setToolTipText("Số tiền BHTN dự trù cho tháng kế tiếp");
		grid_BHTN5.add(txt23);
		Grid grid_BHTN6 = new Grid();
		grid_BHTN6.setInsets(new Insets(new Extent(3, Extent.PX)));
		GridLayoutData grid_BHTN6LayoutData = new GridLayoutData();
		grid_BHTN6LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		grid_BHTN6.setLayoutData(grid_BHTN6LayoutData);
		grid4.add(grid_BHTN6);
		Label label9 = new Label();
		label9.setText("BHTN dự trù");
		grid_BHTN6.add(label9);
		txt24 = new TextField();
		txt24.setToolTipText("Số tiền BHTN dự trù cho tháng kế tiếp");
		grid_BHTN6.add(txt24);
		Grid grid_BHXH4 = new Grid();
		grid_BHXH4.setInsets(new Insets(new Extent(3, Extent.PX)));
		GridLayoutData grid_BHXH4LayoutData = new GridLayoutData();
		grid_BHXH4LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		grid_BHXH4.setLayoutData(grid_BHXH4LayoutData);
		grid4.add(grid_BHXH4);
		Label label21 = new Label();
		label21.setText("BHXH dự trù");
		grid_BHXH4.add(label21);
		txt25 = new TextField();
		txt25.setToolTipText("Số tiền BHXH dự trù nộp cho cơ quan BH");
		grid_BHXH4.add(txt25);
		Grid gridBHYT4 = new Grid();
		gridBHYT4.setInsets(new Insets(new Extent(3, Extent.PX)));
		GridLayoutData gridBHYT4LayoutData = new GridLayoutData();
		gridBHYT4LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		gridBHYT4.setLayoutData(gridBHYT4LayoutData);
		gridBHYT4.setSize(1);
		grid4.add(gridBHYT4);
		Grid grid11 = new Grid();
		grid11.setInsets(new Insets(new Extent(3, Extent.PX)));
		GridLayoutData grid11LayoutData = new GridLayoutData();
		grid11LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		grid11.setLayoutData(grid11LayoutData);
		grid11.setSize(2);
		gridBHYT4.add(grid11);
		Label label22 = new Label();
		label22.setText("BHYT dự trù");
		grid11.add(label22);
		txt26 = new TextField();
		txt26.setToolTipText("Số tiền BHYT dự trù nộp cho tháng kế tiếp");
		grid11.add(txt26);
		Label label23 = new Label();
		label23.setText("BHYT giữ lại");
		grid11.add(label23);
		txt27 = new TextField();
		txt27.setToolTipText("Số tiền Cty giữ lại để chi trả BHYT cho những trường hợp nghỉ thai sản");
		grid11.add(txt27);
		Column column4 = new Column();
		column4.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		GridLayoutData column4LayoutData = new GridLayoutData();
		column4LayoutData.setAlignment(new Alignment(Alignment.TRAILING,
				Alignment.DEFAULT));
		column4.setLayoutData(column4LayoutData);
		gridBHYT4.add(column4);
		Grid grid12 = new Grid();
		grid12.setInsets(new Insets(new Extent(3, Extent.PX)));
		grid12.setSize(2);
		column4.add(grid12);
		Label label24 = new Label();
		label24.setText("Không đủ công");
		grid12.add(label24);
		txt28 = new TextField();
		txt28.setToolTipText("Số tiền BHYT không đủ công-cty nộp");
		grid12.add(txt28);
		Label label25 = new Label();
		label25.setText("KT trừ được");
		grid12.add(label25);
		txt29 = new TextField();
		txt29.setToolTipText("Số tiền CNV nghỉ việc - bỏ việc Kế Toán trừ được tiền");
		grid12.add(txt29);
		Label label26 = new Label();
		label26.setText("Cty trả tiền");
		grid12.add(label26);
		txt30 = new TextField();
		txt30.setToolTipText("Số tiền CNV nghỉ việc - bỏ việc Cty trả tiền");
		grid12.add(txt30);
		
		Label label27 = new Label();
		label27.setText("Thu tiền mặt");
		grid12.add(label27);
		txt31 = new TextField();
		txt31.setToolTipText("Số tiền CNV nghỉ việc - bỏ việc thu tiền mặt");
		grid12.add(txt31);
		
		//////////////////// show Browser Table here
		splitPane2.add(grid_show_data());
		
		
		
	}
	private Grid grid_show_data(){
		Grid 	grid_tn = new Grid();
				grid_tn.setSize(3);
				grid_tn.setInsets(new Insets(5));
				grid_tn.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));

		Label 	lbel = new Label("V/V DIỄN GIẢI SỐ DƯ CHUYỂN TIỀN THAM GIA BHTN - FREETREND");
		GridLayoutData 	layout_grid_colSpan = new GridLayoutData();
						layout_grid_colSpan.setColumnSpan(3);
				lbel.setLayoutData(layout_grid_colSpan);
				
//		grid_tn.add(new Label("V/V DIỄN GIẢI SỐ DƯ CHUYỂN TIỀN THAM GIA BHTN - FREETRÉO"));
				grid_tn.add(lbel);
		
				
		grid_tn.add(new Label("Số tiền BHTN dự trù tháng trước đã nộp là : "));
		grid_tn.add(new Label("A"));
		grid_tn.add(lbel1 = new Label());
		
		grid_tn.add(new Label("Số tiền BHTN thực nộp là :"));
		grid_tn.add(new Label("B"));
		grid_tn.add(lbel2 = new Label());
		
		grid_tn.add(new Label("Số tiền thiếu BHTN phải nộp thêm là :"));
		grid_tn.add(new Label("C = B - A"));
		grid_tn.add(lbel3 = new Label());
		
		grid_tn.add(new Label("Số tiền BHTN dự trù nộp là :"));
		grid_tn.add(new Label("D"));
		grid_tn.add(lbel4 = new Label());
		
		grid_tn.add(new Label("Tổng số tiền BHTN :"));
		grid_tn.add(new Label("F = C + D"));
		grid_tn.add(lbel5 = new Label());
		return grid_tn;
	}
}

class DataContent{
	
	private BaoHiemYT_BUT_yt oYT;
	private BaoHiemYT_BUT_tn oTN;
	private BaoHiemYT_BUT_xh oXH;
	public BaoHiemYT_BUT_yt getoYT() {
		return oYT;
	}
	public BaoHiemYT_BUT_tn getoTN() {
		return oTN;
	}
	public BaoHiemYT_BUT_xh getoXH() {
		return oXH;
	}
	
	public DataContent(BaoHiemYT_BUT_yt oYT,BaoHiemYT_BUT_tn oTN,BaoHiemYT_BUT_xh oXH) {
		this.oYT = oYT;
		this.oTN = oTN;
		this.oXH = oXH;
	}
	
	public boolean isNull(){
		if(oYT == null || oTN == null || oXH == null) return true;
		return false;
	}
}

