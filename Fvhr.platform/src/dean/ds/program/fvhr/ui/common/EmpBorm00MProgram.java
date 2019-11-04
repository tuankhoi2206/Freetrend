package ds.program.fvhr.ui.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.N_N_EMP_BORM;
import dsc.dao.DataObjectSet;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import dsc.util.function.UUID;
import fv.util.Vni2Uni;

/**
 * N_EMP_BORM * 
 */
public class EmpBorm00MProgram extends MaintainSProgram {

	private String factCondition = "AND 1<>1";
	private QueryPane queryPane;
	private Button btnInfo;
	private Button btnExportXls;

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
		setMasterDataContent(new EmpBorm00MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		//1.<進階可查詢欄位定義>

		//2.<固定條件>
		
		//3.<預設查詢條件>

		//4.<作業功能>
		//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);

		//5.設定table的最大筆數及每頁筆數
		String condStr = "o.EMPSN IN (SELECT E.EMPSN FROM N_EMPLOYEE E, N_DEPARTMENT D WHERE E.EMPSN=o.EMPSN AND E.DEPSN=D.ID_DEPT " + factCondition  + ")";
		ProgramCondition pc = new ProgramCondition(condStr, new Object[]{});
		setBaseCondition(pc);
		getBrowserContent().setPageSize(20);
		getBrowserContent().setMaxSize(30000);
		
		//toolbar button		
		btnInfo = new Button();
		btnInfo.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/info.gif"));
		btnInfo.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/infoD.gif"));
		btnInfo.setToolTipText("Thông tin số thẻ");
		btnInfo.setStyleName("Default.ToolbarButton");
		btnInfo.setEnabled(false);
		btnInfo.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				N_N_EMP_BORM data = (N_N_EMP_BORM) getMasterDataContent().getDataObject();
				IGenericDAO dao = Application.getApp().getDao(getLoginInfo().getCompanyID());
				List list = dao.find(1, "select a.FNAME, a.LNAME, b.NAME_FACT, b.NAME_DEPT_NAME from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{data.getEMPSN()});
				if (list.size()>0){
					Object[] obj = (Object[]) list.get(0);
					Application.getApp().showMessageDialog(MessageDialog.TYPE_INFORMATION+MessageDialog.CONTROLS_OK, "Họ tên: " + Vni2Uni.convertToUnicode(obj[0].toString().trim() + " " + obj[1].toString().trim()) + "\r\nĐơn vị: " + obj[2] + " - " + Vni2Uni.convertToUnicode(obj[3].toString()));
				}else{
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Không tìm thấy thông tin");
				}
			}
		});
		getMasterToolbar().add(btnInfo);
		
		btnExportXls = new Button();
		btnExportXls.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcel.gif"));
		btnExportXls.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnExcelD.gif"));
		btnExportXls.setEnabled(false);
		btnExportXls.setStyleName("Default.ToolbarButton");
		btnExportXls.setToolTipText("Xuất ra excel");
		btnExportXls.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mainSplitPane.getComponent(1) instanceof BrowserContent){
					MessageDialog msgDialog = new MessageDialog(
							MessageDialog.TYPE_WARNING + MessageDialog.CONTROLS_YES_NO,
							"Xuất danh sách ra file Excel?");
					msgDialog.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
								try {
									doExportDataObjectSet();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}

						private void doExportDataObjectSet() throws IOException {
							File f = ReportFileManager.getReportFormatFolder("dshr");
							InputStream in = new FileInputStream(new File(f.getPath(), "ds_ungluong.xls"));
							HSSFWorkbook wb = new HSSFWorkbook(in);
							HSSFSheet s = wb.getSheetAt(0);
							HSSFRow row;
							HSSFCell cell;
							DataObjectSet ds = getMasterDataContent().getDataObjectSet();
							IGenericDAO dao = Application.getApp().getDao(getLoginInfo().getCompanyID());
							for (int i=0;i<ds.getRecordCount();i++){
								row = s.createRow(i+1);
								cell = row.createCell(0);
								N_N_EMP_BORM data = (N_N_EMP_BORM) ds.getDataObject(i);
								cell.setCellValue(data.getEMPSN());
								List list = dao.find(1, "select a.FNAME, a.LNAME, b.NAME_FACT, b.NAME_DEPT_NAME from N_EMPLOYEE a, N_DEPARTMENT b where a.DEPSN=b.ID_DEPT and a.EMPSN=?", new Object[]{data.getEMPSN()});
								if (list.size()>0){
									Object[] obj = (Object[]) list.get(0);
									cell = row.createCell(1);//Ho ten
									cell.setCellValue(obj[0].toString().trim() + " " + obj[1].toString().trim());
									cell = row.createCell(2);//don vi
									cell.setCellValue(obj[3].toString());
								}else{
									cell = row.createCell(1);
									cell.setCellValue("Không tìm thấy thông tin");
								}
							}
							File f1 = new File(System.getProperty("java.io.tmpdir"), UUID.generate());			
							FileOutputStream out = new FileOutputStream(f1);
							wb.write(out);
							out.flush();
							out.close();
							String name = "Danh_sach_ung_luong";
							File saveFile = new File(f1.getParentFile(),URLEncoder.encode(getLoginInfo().getUserID() + ";application/vnd.ms-excel;" + name + Calendar.getInstance().getTimeInMillis() + "" + ".xls", "UTF-8"));			
							ReportFileManager.saveTempReportFile(f1, saveFile);
							Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
						}
					});
				}
			}
		});
		getMasterToolbar().add(btnExportXls);
		return ret;
	}
	
	@Override
	protected void switchContent(int view) {
		super.switchContent(view);
		if (view==1){
			btnExportXls.setEnabled(false);
		}else{
			if (getMasterDataContent().getDataObjectSet().getRecordCount()>0)
				btnExportXls.setEnabled(true);
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
	public int refresh() {
		int ret = super.refresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()>0){
			btnInfo.setEnabled(true);
			btnExportXls.setEnabled(true);
		}else{
			btnInfo.setEnabled(false);
			btnExportXls.setEnabled(false);
		}
		return ret;
	}
	
	@Override
	protected void doRefresh() {
		super.doRefresh();
		if (getMasterDataContent().getDataObjectSet().getRecordCount()>0){
			btnInfo.setEnabled(true);
			btnExportXls.setEnabled(true);
		}else{
			btnInfo.setEnabled(false);
			btnExportXls.setEnabled(false);
		}
	}

	@Override
	protected QueryPane createNormalQuery() {
		if (queryPane==null)
			queryPane =new EmpBorm00MQuery(); 
		return queryPane;
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
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
        return new String[] {"EMPSN", "STATUS", "UP_DATE"};
	}
}
