package ds.program.fvhr.ui.insurance;

import it.businesslogic.ireport.gui.MessageBox;

import java.awt.Window;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.jaxen.function.ConcatFunction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;
import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.dao.insuranse.N_EMP_QUITDAO;
import ds.program.fvhr.dao.wp.WorkpointsDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_DEDUCT_OTHER;
import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.domain.N_EMP_QUIT;
import ds.program.fvhr.domain.N_LOST_DATA_DETAIL;
import ds.program.fvhr.tien.ui.N_Emp_Quit_Import;
import ds.program.fvhr.tien.ui.N_SP_WDay_New;
import ds.program.fvhr.ui.insurance.OBJ_UTILITY;
import ds.program.users.domain.DSPB02;
import fv.util.BundleUtils;
import fv.util.DbUtils;
import fv.util.FvLogger;
import fv.util.Vni2Uni;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.util.function.UUID;

/**
 * N_EMP_QUIT01M *
 */
public class N_EMP_QUIT01MProgram extends MaintainSProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dataMode;
	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	private WorkpointsDAO dao;
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
		setMasterDataContent(new N_EMP_QUIT01MDataContent());
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		// 1.<進階可查詢欄位定義>
		this.setQBEDisplayFields(
				new String[] { "EMPSN", "REAL_OFF_DATE", "THANG_TRUBH", "THANG_TANGLAI", "TYLE_TANGLAI" });

		// 2.<固定條件>

		// 3.<預設查詢條件>

		// 4.<作業功能>
		// getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_NEW, true);
		getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_EXPORT, true);

		// 5.設定table的最大筆數及每頁筆數

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		// <初始時是否撈取資料>
		// 如果要一執行程式時就取出資料，則執行下行程式
		// this.refresh(); //取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		return new N_EMP_QUIT01MQuery();
	}

	/*
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		Button importButton = new Button();
		importButton.setText("Import Excel Nhân Viên NV Trả/Không Thẻ BHYT");
		importButton.setStyleName("Default.ToolbarButton");
		importButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				N_Emp_Quit_Import objEmpQuit = new N_Emp_Quit_Import();
				Application.getApp().getDefaultWindow().getContent().add(objEmpQuit);

			}
		});
		this.getMasterToolbar().add(importButton);
	}

	/*
	 * 使用者資料主檔Select change
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doMasterDataSelectChange()
	 */
	@Override
	protected void doMasterDataSelectChange() {
		super.doMasterDataSelectChange();
		// 6.<功能權限管控>
	}

	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		// 4.<資料瀏覽欄位>
		// return new String[] {"EMPSN", "FACT_NAME", "GROUP_NAME", "DEPT_NAME",
		// "ID_QUIT_REASON", "REAL_OFF_DATE", "THANG_TRUBH", "DATE_BHYT",
		// "NOTE_GIAM_BHYT", "FROM_DATE", "TO_DATE", "DATE_AGAIN",
		// "THANG_TANGLAI", "TYLE_TANGLAI"};
		return new String[] { "EMPSN", "DEPSN", "ID_QUIT_REASON", "OFF_DATE", "REAL_OFF_DATE", "THANG_TRUBH",
				"DATE_BHYT", "NOTE_GIAM_BHYT", "FROM_DATE", "TO_DATE", "DATE_AGAIN", "THANG_TANGLAI", "TYLE_TANGLAI" };
	}

	private boolean checkKhoaData() {
		boolean kq = true;
		// kq= true la ok, = false la ko cho thao tac
		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		N_EMP_QUIT empQuit = (N_EMP_QUIT) this.getBrowserContent().getDataObjectSet().getDataObject();

		String ngayThucNghi = sf.format(empQuit.getREAL_OFF_DATE());
		String thang = ngayThucNghi.substring(3, 5);
		String nam = ngayThucNghi.substring(6, 10);
		String ngayBaoGiam;
		if (empQuit.getFROM_DATE() == null && empQuit.getTO_DATE() == null) {
			ngayBaoGiam = null;
		} else {
			ngayBaoGiam = sf.format(empQuit.getTO_DATE().getDate() + 1);
		}

		String giamTangMoi = null;
		if (empQuit.getNOTE_GIAM_BHYT() == null) {
			giamTangMoi = null;
		} else {
			giamTangMoi = empQuit.getNOTE_GIAM_BHYT();
		}

		if (ins.checkQLyEmpsn(empQuit.getEMPSN()) == false) {
			setErrorMessage("Không có quyền thao tác.");
			kq = false;
		} else {
			kq = ins.CheckKhoaDataMonth(empQuit.getEMPSN(), thang, nam);
			// = true la chua khoa, = false la da khoa
			if (!kq) {
				setErrorMessage("Đã khóa dữ liệu, không thể thao tác.");
				kq = false;
			} else {
				// if ko co thong tin bao giam gi ca
				if (ngayBaoGiam == null && giamTangMoi == null) {
					kq = true;
				} else {
					if (ngayBaoGiam != null && giamTangMoi == null) {
						kq = ins.CheckKhoaDotBaoGiam(ngayBaoGiam);
						// = true la chua khoa, = false la da khoa
						if (!kq) {
							setErrorMessage("Đã khóa đợt báo giảm, không thể thao tác.");
							kq = false;
						} else
							kq = true;
					} else {
						kq = ins.CheckKhoaGiamTrongTangMoi(empQuit.getEMPSN(), ngayThucNghi, giamTangMoi);
						// = true la chua khoa, = false la da khoa
						if (!kq) {
							setErrorMessage("Đã khóa đợt báo giảm trong tăng mới, không thể thao tác.");
							kq = false;
						} else
							kq = true;
					}
				}
			}
		}
		return kq;
	}

	@Override
	public boolean doNew() {
		boolean ret = super.doNew();
		if (ret) {
			dataMode = DATAMODE_NEW;
			return ret;
		}
		return ret;
	}

	public WorkpointsDAO getJdbcDAO() {
		if (dao == null)
			dao = new WorkpointsDAO();
		return this.dao;
	}

	@Override
	public boolean doSave() {
		boolean ret = super.doSave();
		if (ret) {
			N_EMP_QUIT data = (N_EMP_QUIT) getMasterDataContent().getDataObject();

			Date dtRead_Off_date = data.getREAL_OFF_DATE(); // ngay thuc nghi
			// thang thuc nghi
			Calendar cal_Read_Off_date = Calendar.getInstance();
			cal_Read_Off_date.setTime(dtRead_Off_date);
			cal_Read_Off_date.setTime(dtRead_Off_date);
			Date dtprocess = null;
			String strDateprocess = "";
			dtprocess = cal_Read_Off_date.getTime();
			strDateprocess = sf.format(dtprocess);
			System.out.println(strDateprocess);
			getJdbcDAO().processData(data.getEMPSN(), strDateprocess, "mm");
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setIDUSER(ma_user);
			action.setACTIONNAME(dataMode == IProgram.DATAMODE_NEW ? "INSERT" : "UPDATE");
			action.setEMPSN(data.getEMPSN());
			action.setNOTE("Thong tin nghi viec, thuc nghi " + sf.format(data.getREAL_OFF_DATE()) + ", ly do "
					+ data.getID_QUIT_REASON());
			action.setTABLENAME("N_EMP_QUIT");
			FvLogger.log(action);
			return ret;
		}
		return ret;
	}

	@Override
	public boolean doDelete() {
		boolean retVal = true;
		// lay tu CSDL
		IGenericDAO<N_EMPLOYEE, String> objDao = Application.getApp().getDao(N_EMPLOYEE.class);
		// Lay tu data dang thao tac
		N_EMP_QUIT empQuit = (N_EMP_QUIT) this.getBrowserContent().getDataObjectSet().getDataObject();
		InsuranceDAO ins = new InsuranceDAO();
		String ngayThucNghi = sf.format(empQuit.getREAL_OFF_DATE());
		String depsn = ins.GetField("depsn", "n_emp_quit", "empsn", "to_char(real_off_date,'" + "dd/mm/yyyy" + "')", "",
				empQuit.getEMPSN(), ngayThucNghi, "");
		// neu da chuyen du lieu roi thi ko cho xoa
		retVal = checkKhoaData();
		if (retVal) {
			// cap nhat du lieu lien quan truoc khi xoa nghi viec
			N_EMP_QUITDAO empQuitDao = new N_EMP_QUITDAO();
			empQuitDao.capNhatDuLieuLienQuan(empQuit.getEMPSN(), depsn, "1", ngayThucNghi);
			super.doDelete();
			// luu lai trong action_daily
			String note = "Xoa nghi viec, thuc nghi " + ngayThucNghi + ", ly do " + empQuit.getID_QUIT_REASON();
			IGenericDAO<DSPB02, String> Dao_DSPB02 = Application.getApp().getDao(DSPB02.class);
			DSPB02 Data_DSPB02 = Dao_DSPB02.findById(Application.getApp().getLoginInfo().getUserID());
			String ma_user = Data_DSPB02.getPB_USERNO();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setIDUSER(ma_user);
			action.setACTIONNAME("DELETE");
			action.setEMPSN(empQuit.getEMPSN());
			action.setNOTE(note);
			action.setTABLENAME("N_EMP_QUIT");
			FvLogger.log(action);
		} else {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getErrorMessage());
			retVal = false;
		}
		return retVal;
	}

	@Override
	public boolean doEdit() {
		boolean kq = true;
		N_EMP_QUIT nViec = (N_EMP_QUIT) this.getBrowserContent().getDataObjectSet().getDataObject();
		InsuranceDAO ins = new InsuranceDAO();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		String thangKTru = sf.format(nViec.getREAL_OFF_DATE());
		String thang = thangKTru.substring(3, 5);
		String nam = thangKTru.substring(6, 10);
		// neu da chuyen du lieu roi thi ko cho cap nhat lai
		kq = ins.CheckKhoaDataMonth(nViec.getEMPSN(), thang, nam);
		// kq= true la chua chuyen data, =false la da chuyen roi
		if (!kq) {
			setErrorMessage("Đã chuyển dữ liệu của tháng, không thể cập nhật thông tin nghỉ việc.");
			return false;
		}
		if (super.doEdit()) {
			dataMode = IProgram.DATAMODE_EDIT;
			return true;
		}

		return false;
	}

	public boolean doExport() {
		boolean result = true;
		// MessageBox mes = new MessageBox()
		// Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
		// "Đã có thông tin báo giảm, không thể xóa nghỉ việc.");
		// Application.getApp().showMessageDialog("In báo cáo",
		// "Bạn muốn in Giấy thanh toán hay quyết định
		// NV",MessageDialog.TYPE_CONFIRM+MessageDialog.CONTROLS_YES_NO
		// );
		// MessageBox mes = new MessageBox();
		MessageDialog mes = new MessageDialog("In báo cáo",
				"Chọn Yes để in Giấy thanh toán, chọn No để in QDNV/QD Sa Thải?", MessageDialog.CONTROLS_YES_NO);
		mes.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
					doExportPdfNghiViec(0, "GiayThanhToanNV.jrxml");
				} else {
					MessageDialog mes1 = new MessageDialog("In báo cáo",
							"Chọn Yes để in QĐNV, chọn No để in QĐ Sa Thải?", MessageDialog.CONTROLS_YES_NO);
					mes1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
								doExportPdfNghiViec(1, "QDNghiViec.jrxml");
							} else {
								doExportPdfNghiViec(2, "QDSaThai.jrxml");
							}
						}
					});
				}
			}
		});

		return result;
	}

	public void doExportPdfNghiViec(Integer loaiBC, String fileMau) {
		// Lay tu data dang thao tac
		N_EMP_QUIT empQuit = (N_EMP_QUIT) this.getBrowserContent().getDataObjectSet().getDataObject();

		N_EMP_QUITDAO quitDAO = new N_EMP_QUITDAO();

		// SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		if (empQuit.getEMPSN().equals("")) {
			Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					("Chưa có thông tin số thẻ"));
			return;
		}
		String startDate = "";
		String endDate = "";

		String sql = null;
		String fname = null;
		switch (loaiBC) {
		case 0:// In giay thanh toan
			sql = quitDAO.GetSqlGiayThanhToan(empQuit.getEMPSN());
			fname = "GiayThanhToanNV";
			break;
		case 1:// In QDNV
			sql = quitDAO.GetSqlQDNV(empQuit.getEMPSN());
			fname = "QuyetDinhNghiViec";
			break;
		case 2:// In QD Sa Thai
			sql = quitDAO.GetSqlQDSaThai(empQuit.getEMPSN());
			fname = "QuyetDinhSaThai";
			break;
		}

		System.out.println(sql);
		JasperDesign jd;
		Connection con = null;
		try {
			jd = JRXmlLoader.load(ReportFileManager.getReportFormatFolder("fvhr/" + fileMau));
			JRDesignQuery query = new JRDesignQuery();
			query.setText(sql);
			jd.setQuery(query);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			con = Application.getApp().getConnection();
			HashMap<String, Object> params = new HashMap<String, Object>();

			// e.put(Vni2Uni.convertToUnicode(r.getNAME_QR()),
			// r.getID_QUIT_REASON());
			params.put(Vni2Uni.convertToUnicode(params.toString()), params.toString());

			params.put("Start_Date", startDate);
			params.put("End_Date", endDate);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
			File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
			JasperExportManager.exportReportToPdfFile(jp, f.getPath());
			// String fname = qfMachine.getTextField().getText();
			// fname = fname.replace('*', '_').replace(' ', '_');
			// if (fname.equals("")) fname="all";
			// String fname="GiayThanhToanNV";
			File saveFile = new File(f.getParentFile(),
					URLEncoder.encode(getLoginInfo().getUserID() + ";application/pdf;" + "_" + fname + ";"
							+ Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));
			ReportFileManager.saveTempReportFile(f, saveFile);
			Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con);
			System.out.println("the end");
		}
	}

	public String getViewerUrl() {
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String viewerUrl = request.getRequestURL() + "?" + WebRenderServlet.SERVICE_ID_PARAMETER + "="
				+ ReportService.INSTANCE.getId() + "&" + ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&"
				+ ReportService.PARAM_KEY + "=";
		return viewerUrl;
	}

}
