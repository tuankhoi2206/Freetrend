package ds.program.fvhr.minh;

import it.businesslogic.ireport.gui.MessageBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.table.DefaultTableModel;
import nextapp.echo2.app.table.TableCellRenderer;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import ds.program.fvhr.domain.BHXH_TN;
import ds.program.fvhr.domain.N_BHYT_SYS_N;
import ds.program.fvhr.domain.N_CT_BHXH_TN;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_FACTORY;
import ds.program.fvhr.domain.N_GROUP_DEPT;
import ds.program.fvhr.domain.pk.N_BHYT_SYS_NPk;
import ds.program.fvhr.minh.dao.ConnectionDatabase;
import ds.program.fvhr.minh.dao.InsurDAO;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;
import fv.util.JdbcDAO;
import fv.util.Vni2Uni;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.CheckBox;

public class BaoCaoForm extends DefaultProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MODEEXPORT_XH = 3;
	private final int MODEEXPORT_TN = 6;
	private final int MODEEXPORT_XHTN = 0;
	private List<N_FACTORY> listFact;
	private List<N_GROUP_DEPT> listGroup;
	private List<N_DEPARTMENT> listDept;
	private List<N_CT_BHXH_TN> listCT;
	private List<BHXH_TN> listBHXH_TN;
	private String date = "";
	private int mode;
	private int stt = 1;
	private ExportExcelBC ex;
	private IGenericDAO daoBHYT_SYS;
	private String dateCT = "";
	private String name_fact = "";
	private String name_group = "";
	private String name_dept = "";
	private String name = "";
	private SelectField slfThang;
	private SelectField slfNam;
	private SelectField slfXuong;
	private SelectField slfNhom;
	private SelectField slfDonvi;
	private RadioButton rbtnXH;
	private RadioButton rbtnTN;
	private RadioButton rbtnXHTN;
	private Button btnBCTH;
	private Button btnExcel;
	private Button btnBCCT;
	private Button btnHuy;
	private Table tblData;
	private Row row;
	private InsurDAO ins = new InsurDAO();
	private CheckBox ckbLan1;
	private Button btnMua1;
	private Button btnMua2;

	/**
	 * Creates a new <code>BaoCaoForm</code>.
	 */
	@SuppressWarnings("unchecked")
	public BaoCaoForm() {
		super();

		// Add design-time configured components.
		initComponents();
		initmore();
		createDefaultTable();
		loadSlfXuong();
		loadslfThangNam();
		ex = new ExportExcelBC();
		try {
			daoBHYT_SYS = Application.getApp().getDao(N_BHYT_SYS_N.class);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// luu ca mua va ko mua, ko mua voi trang thai j( Rora,
		// NS....)metttttttttttttttttttttttttttt
	}

	public void loadslfThangNam() {
		DefaultListModel modelThang = (DefaultListModel) slfThang.getModel();
		DefaultListModel modelNam = (DefaultListModel) slfNam.getModel();
		Date d = new Date(System.currentTimeMillis());
		for (int i = 1; i < 13; i++) {
			if (i < 10)
				modelThang.add("0" + i);
			else
				modelThang.add(i);
			int year = d.getYear() + 1900;
			modelNam.add(year - 12 + i);
		}
		slfThang.setSelectedIndex(d.getMonth() - 1);
		slfNam.setSelectedIndex(11);
	}

	public void loadSlfXuong() {
		try {
			JdbcDAO dao = new JdbcDAO();
			listFact = dao.getSimpleJdbcTemplate().query("select distinct name_fact from vft.n_factory",
					new ParameterizedRowMapper<N_FACTORY>() {
						public N_FACTORY mapRow(ResultSet rs, int rowNum) throws SQLException {
							N_FACTORY data = new N_FACTORY();
							data.setNAME_FACT(rs.getString("name_fact"));
							return data;
						}
					}, new Object[] {});

			DefaultListModel model = (DefaultListModel) slfXuong.getModel();
			for (N_FACTORY f : listFact) {
				if (f.getNAME_FACT() != null)
					model.add(f.getNAME_FACT());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void doslfXuongisSelect() {
		try {
			if (!ins.checkQLyFact(slfXuong.getSelectedItem().toString())) {
				slfXuong.setSelectedIndex(-1);
				DefaultListModel modelGroup = (DefaultListModel) slfNhom.getModel();
				slfNhom.setSelectedIndex(-1);
				modelGroup.removeAll();
				DefaultListModel modelDept = (DefaultListModel) slfDonvi.getModel();
				slfDonvi.setSelectedIndex(-1);
				modelDept.removeAll();
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.",
						MessageBox.OK);
				return;
			}
			DefaultListModel modelGroup = (DefaultListModel) slfNhom.getModel();
			slfNhom.setSelectedIndex(-1);
			modelGroup.removeAll();
			DefaultListModel modelDept = (DefaultListModel) slfDonvi.getModel();
			slfDonvi.setSelectedIndex(-1);
			modelDept.removeAll();

			JdbcDAO dao = new JdbcDAO();
			listGroup = dao.getSimpleJdbcTemplate().query(
					"select distinct name_group from vft.n_department where name_fact=?",
					new ParameterizedRowMapper<N_GROUP_DEPT>() {
						public N_GROUP_DEPT mapRow(ResultSet rs, int rowNum) throws SQLException {
							N_GROUP_DEPT data = new N_GROUP_DEPT();
							data.setNAME_GROUP(rs.getString("name_group"));
							return data;
						}
					}, new Object[] { slfXuong.getSelectedItem().toString() });

			for (N_GROUP_DEPT f : listGroup) {
				if (f.getNAME_GROUP() != null)
					modelGroup.add(f.getNAME_GROUP());
			}
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}

	}

	public void doslfNhomisSelect() {
		try {
			if (!ins.checkQLyGroup(slfXuong.getSelectedItem().toString(), slfNhom.getSelectedItem().toString())) {
				slfNhom.setSelectedIndex(-1);
				DefaultListModel modelDept = (DefaultListModel) slfDonvi.getModel();
				slfDonvi.setSelectedIndex(-1);
				modelDept.removeAll();
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên nhóm này.",
						MessageBox.OK);
				return;
			}
			DefaultListModel modelDept = (DefaultListModel) slfDonvi.getModel();
			slfDonvi.setSelectedIndex(-1);
			modelDept.removeAll();

			JdbcDAO dao = new JdbcDAO();
			listDept = dao.getSimpleJdbcTemplate().query(
					"select distinct name_dept_name from vft.n_department where name_fact=? and name_group=?",
					new ParameterizedRowMapper<N_DEPARTMENT>() {
						public N_DEPARTMENT mapRow(ResultSet rs, int rowNum) throws SQLException {
							N_DEPARTMENT data = new N_DEPARTMENT();
							data.setNAME_DEPT_NAME(rs.getString("name_dept_name"));
							return data;
						}
					}, new Object[] { slfXuong.getSelectedItem().toString(), slfNhom.getSelectedItem().toString() });

			for (N_DEPARTMENT f : listDept) {
				if (f.getNAME_DEPT_NAME() != null)
					modelDept.add(Vni2Uni.convertToUnicode(f.getNAME_DEPT_NAME()));
			}
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}

	}

	public void doslfDonviisSelect() {
		try {

			if (!ins.checkQLyDept(slfXuong.getSelectedItem().toString(), slfNhom.getSelectedItem().toString(),
					Vni2Uni.convertToVNI(slfDonvi.getSelectedItem().toString()))) {
				slfDonvi.setSelectedIndex(-1);
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên nhóm này.",
						MessageBox.OK);
				return;
			}
		} catch (Exception e) {
			System.out.println(e.toString() + "ggggggg");
		}

	}

	public void createDefaultTable() {
		tblData.setDefaultHeaderRenderer(new TableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
				Label lbl = new Label();
				lbl.setText((String) value);
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
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setColumnCount(3);
		model.setColumnName(0, "STT");
		model.setColumnName(1, "Tên");
		model.setColumnName(2, "Giá trị");
		tblData.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(Table arg0, Object arg1, int arg2, int arg3) {
				Label l = new Label();
				l.setText(arg1.toString() + " ");
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0xc8eaf0));
				layout.setInsets(new Insets(new Extent(5), new Extent(0), new Extent(5), new Extent(0)));
				layout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
				if (arg3 % 2 == 0)
					layout.setBackground(new Color(0xc8eaf0));
				else
					layout.setBackground(new Color(0xfdfce3));
				l.setLayoutData(layout);
				return l;
			}
		});
		tblData.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(Table arg0, Object arg1, int arg2, int arg3) {
				Label l = new Label();
				l.setText(arg1.toString() + " ");
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0xc8eaf0));
				layout.setInsets(new Insets(new Extent(5), new Extent(0), new Extent(5), new Extent(0)));
				if (arg3 % 2 == 0)
					layout.setBackground(new Color(0xc8eaf0));
				else
					layout.setBackground(new Color(0xfdfce3));
				l.setLayoutData(layout);
				return l;
			}
		});

		tblData.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(Table arg0, Object arg1, int arg2, int arg3) {
				Label l = new Label();
				l.setText(arg1.toString() + " ");
				TableLayoutData layout = new TableLayoutData();
				layout.setBackground(new Color(0xc8eaf0));
				layout.setInsets(new Insets(new Extent(5), new Extent(0), new Extent(5), new Extent(0)));
				layout.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
				if (arg3 % 2 == 0)
					layout.setBackground(new Color(0xc8eaf0));
				else
					layout.setBackground(new Color(0xfdfce3));
				l.setLayoutData(layout);
				return l;
			}
		});
	}

	public void doCancel() {
		slfXuong.setSelectedIndex(-1);

		DefaultListModel modelGroup = (DefaultListModel) slfNhom.getModel();
		slfNhom.setSelectedIndex(-1);
		modelGroup.removeAll();

		DefaultListModel modelDept = (DefaultListModel) slfDonvi.getModel();
		slfDonvi.setSelectedIndex(-1);
		modelDept.removeAll();
	}

	public void doBCTH() {
		ConnectionDatabase con = new ConnectionDatabase();
		if (slfThang.getSelectedIndex() < 0 || slfNam.getSelectedIndex() < 0) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chưa chọn tháng năm xuất báo cáo",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}
		if (!rbtnTN.isSelected() && !rbtnXH.isSelected() && !rbtnXHTN.isSelected()) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chưa chọn loại báo cáo",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}
		Date d = new Date(System.currentTimeMillis());
		int year = d.getYear() + 1900;
		int month = d.getMonth() + 1;
		int day = d.getDate();
		int slfm = Integer.valueOf(slfThang.getSelectedItem().toString());
		int slfy = Integer.valueOf(slfNam.getSelectedItem().toString());
		if (slfy > year || (slfy == year && slfm > month)) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);

			return;
		}
		if (slfy == year && slfm == month) {
			if (stt == 2 || (stt == 1 && day < 17)) {
				dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
						MessageDialog.CONTROLS_OK);
				Application.getApp().getDefaultWindow().getContent().add(dlg);

				return;
			}
		}

		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setRowCount(0);

		try {

			// dung de xet quyen quan ly toan bo.
			/*
			 * if (!ins.checkQLyNDept(null, null,null)) {
			 * Application.getApp().showMessageDialog("Thông Báo",
			 * "Bạn không có quyền thao tác trên tất cả các xưởng.", MessageBox.OK); return
			 * ; }
			 */
			String id_bhxh_tn = slfNam.getSelectedItem().toString() + slfThang.getSelectedItem().toString();
			String sdate = "01/" + slfThang.getSelectedItem().toString() + "/" + slfNam.getSelectedItem().toString();
			date = sdate;
			String sql = "select t.name_fact from n_social_status t where t.id_report ='MAU' and t.name_fact not in"
					+ "(select a.name_fact from n_social_status a where a.name_fact = t.name_fact "
					+ "and a.id_report='0" + stt + id_bhxh_tn + "' and a.status = 1)";
			OBJ_UTILITY obj = new OBJ_UTILITY();
			List<String> listData = obj.Exe_Sql_String(sql);
			if (listData.size() > 0) {
				String tb = "Xưởng ";
				for (int i = 0; i < listData.size(); i++) {
					tb += listData.get(i) + " ";
				}
				tb += "chưa cập nhật dữ liệu!";
				dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Thông báo", tb,
						MessageDialog.CONTROLS_OK);
				Application.getApp().getDefaultWindow().getContent().add(dlg);
				return;
			}

			String stmp[] = new String[] { "*ĐẠI ĐẾ:", "*X.DAO CHẶT 1:", "*X.DAO CHẶT 2:", "* BẾP 1+2+3:", "*FVL:",
					"*FVL - GIÀY MẪU:", "*FVL1:", "*FVL2:", "*FVL3:", "*FVL5:", "*FVL.KS", "*MSHAN:", "*TỔNG BỘ:",
					"*MÁY DÁN:", "*XƯỞNG THÊU:" };
			listBHXH_TN = new ArrayList<BHXH_TN>();
			listBHXH_TN = con.getBCTH(id_bhxh_tn, stt);

			int j = 0;
			for (BHXH_TN b : listBHXH_TN) {
				model.addRow(new Object[] { j + 1, stmp[j] + " Số người", b.getSo_nguoi() });
				model.addRow(new Object[] { "", " -Tổng quỹ lương", b.getTong_luong() });
				j++;
			}
			// luu vao n_bhyt_sys_n // cai nay phai lam sao day
			// -----------------------------------------------------

			float tyle[] = con.BHXHTN();
			float tyleBHXH = tyle[0];
			float tyleBHTN = tyle[1];
			N_BHYT_SYS_N bh = new N_BHYT_SYS_N();
			bh.setID_PKEY("0" + stt + id_bhxh_tn);
			long tongluong = con.getTHLuong(id_bhxh_tn, stt);
			bh.setSO_TIEN((tongluong * (int) (tyleBHTN * 100) / 100));
			bh.setTYPE(0);
			if (daoBHYT_SYS.findById(new N_BHYT_SYS_NPk(bh.getID_PKEY(), bh.getTYPE())) == null)
				daoBHYT_SYS.save(bh);

			bh.setSO_TIEN((tongluong * (int) (tyleBHXH * 100) / 100));
			bh.setTYPE(1);
			if (daoBHYT_SYS.findById(new N_BHYT_SYS_NPk(bh.getID_PKEY(), bh.getTYPE())) == null)
				daoBHYT_SYS.save(bh);

			MessageDialog dlg = new MessageDialog("Thông báo", "Hoàn tất", MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);

		} catch (Exception e) {
			System.out.println(e.toString() + "lass");
		}

	}

	public void doBCCT() {
		ConnectionDatabase con = new ConnectionDatabase();
		if (slfThang.getSelectedIndex() < 0 || slfNam.getSelectedIndex() < 0) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chưa chọn tháng năm xuất báo cáo",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}
		Date d = new Date(System.currentTimeMillis());
		int year = d.getYear() + 1900;
		int month = d.getMonth() + 1;
		int day = d.getDate();
		int slfm = Integer.valueOf(slfThang.getSelectedItem().toString());
		int slfy = Integer.valueOf(slfNam.getSelectedItem().toString());
		if (slfy > year || (slfy == year && slfm > month)) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);

			return;
		}
		if (slfy == year && slfm == month) {
			if (stt == 2 || (stt == 1 && day < 17)) {
				dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
						MessageDialog.CONTROLS_OK);
				Application.getApp().getDefaultWindow().getContent().add(dlg);

				return;
			}
		}
		DefaultTableModel model = (DefaultTableModel) tblData.getModel();
		model.setRowCount(0);

		date = slfThang.getSelectedItem().toString() + "/" + slfNam.getSelectedItem().toString();
		String id_bhxh_tn = slfNam.getSelectedItem().toString() + slfThang.getSelectedItem().toString();
		dateCT = date;
		name_fact = "";
		name_group = "";
		name_dept = "";
		name = "";
		if (slfXuong.getSelectedIndex() < 0) {/*
												 * /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\
												 * //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\xuat tat ca
												 * //\\//\\//\\//\\//\\//\\//\\ \\//\\//\\//\\//\\//\\//\\//\\//\\//\\//
												 * \\//\\//\\//\\//\\//\\//\\// \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/
												 * \/ \/ \/ \/ || || || || || || || || || || || || || || || || ||
												 * [__][__][__][__][__][__][__][__][__][__] [__][__][__][__][__][__][__]
												 */
			if (!ins.checkQLyNDept(null, null, null)) {
				Application.getApp().showMessageDialog("Thông Báo",
						"Bạn không có quyền thao tác trên tất cả các xưởng.", MessageBox.OK);
				return;
			}

			////// xet xem cac xuong da cap nhat du lieu chua
			String sql = "select t.name_fact from n_social_status t where t.id_report ='MAU' and t.name_fact not in"
					+ "(select a.name_fact from n_social_status a where a.name_fact = t.name_fact "
					+ "and a.id_report='0" + stt + id_bhxh_tn + "' and a.status = 1)";
			OBJ_UTILITY obj = new OBJ_UTILITY();
			List<String> listData = obj.Exe_Sql_String(sql);
			if (listData.size() > 0) {
				String tb = "Xưởng ";
				for (int i = 0; i < listData.size(); i++) {
					tb += listData.get(i) + " ";
				}
				tb += "chưa cập nhật dữ liệu!";
				Application.getApp().showMessageDialog("Thông báo", tb, MessageDialog.CONTROLS_OK);
				return;
			}
			name = "";

		} else {
			if (slfNhom.getSelectedIndex() < 0) {
				name_fact = slfXuong.getSelectedItem().toString();
				name = name_fact + ".";
				if (!ins.checkQLyNDept(name_fact, null, null)) {
					Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.",
							MessageBox.OK);
					return;
				}
			} else if (slfDonvi.getSelectedIndex() < 0) {
				name_fact = slfXuong.getSelectedItem().toString();
				name_group = slfNhom.getSelectedItem().toString();
				name = name_fact + "." + name_group + ".";
				if (!ins.checkQLyNDept(name_fact, name_group, null)) {
					Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên nhóm này.",
							MessageBox.OK);
					return;
				}
			} else {
				name_fact = slfXuong.getSelectedItem().toString();
				name_group = slfNhom.getSelectedItem().toString();
				name_dept = Vni2Uni.convertToVNI(slfDonvi.getSelectedItem().toString());
				name = name_fact + "." + name_group + "." + name_dept;
			}
		}

		////////////////////////////////////////////

		listCT = new ArrayList<N_CT_BHXH_TN>();

		listCT = con.getBCCTday(date, name, stt);

		if (listCT.size() > 0) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("THÔNG BÁO", "Bạn có muốn xuất EXCEL không",
					MessageDialog.CONTROLS_YES_NO);
			dlg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
						exportExcelCT(getNameExcel(), stt);
					}
				}
			});
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		} else {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi",
					"Không có dữ liệu hoặc bạn không có quyền truy xuất.", MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}

	}

	public String getNameExcel() {
		String name = "";
		if (slfXuong.getSelectedIndex() > -1)
			name += slfXuong.getSelectedItem().toString() + ".";
		if (slfNhom.getSelectedIndex() > -1)
			name += slfNhom.getSelectedItem().toString() + ".";
		// if(slfDonvi.getSelectedIndex()>-1)
		// name+=
		// slfDonvi.getText();//Vni2Uni.convertToVNI(slfDept.getSelectedItem().toString());//Ten
		// file co dau cach nen loi.
		return name;
	}

	protected void doMua1() {
		doMua(1);
	}

	public void doMua(int stts) {
		final int status = stts;
		dateCT = slfThang.getSelectedItem().toString() + "/" + slfNam.getSelectedItem().toString();
		ConnectionDatabase con = new ConnectionDatabase();
		if (slfThang.getSelectedIndex() < 0 || slfNam.getSelectedIndex() < 0) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chưa chọn tháng năm xuất báo cáo",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}
		Date d = new Date(System.currentTimeMillis());
		int year = d.getYear() + 1900;
		int month = d.getMonth() + 1;
		int slfm = Integer.valueOf(slfThang.getSelectedItem().toString());
		int slfy = Integer.valueOf(slfNam.getSelectedItem().toString());
		if (slfy > year || (slfy == year && slfm > month)) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);

			return;
		}
		if (slfy == year && slfm == month) {
			dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("Lỗi", "Chọn tháng năm không hợp lệ",
					MessageDialog.CONTROLS_OK);
			Application.getApp().getDefaultWindow().getContent().add(dlg);
			return;
		}

		String id_bhxh_tn = slfNam.getSelectedItem().toString() + slfThang.getSelectedItem().toString();
		if (slfXuong.getSelectedIndex() < 0) {
			if (!ins.checkQLyNDept(null, null, null)) {
				Application.getApp().showMessageDialog("Thông Báo",
						"Bạn không có quyền thao tác trên tất cả các xưởng.", MessageBox.OK);
				return;
			}

			/////// xet xem cac xuong da cap nhat du lieu chua
			String sql = "select t.name_fact from n_social_status t where t.id_report ='MAU' and t.name_fact not in"
					+ "(select a.name_fact from n_social_status a where a.name_fact = t.name_fact "
					+ "and a.id_report='0" + stt + id_bhxh_tn + "' and a.status = 1)";
			OBJ_UTILITY obj = new OBJ_UTILITY();
			List<String> listData = obj.Exe_Sql_String(sql);
			if (listData.size() > 0) {
				String tb = "Xưởng ";
				for (int i = 0; i < listData.size(); i++) {
					tb += listData.get(i) + " ";
				}
				tb += "chưa cập nhật dữ liệu!";
				Application.getApp().showMessageDialog("Thông báo", tb, MessageDialog.CONTROLS_OK);
				return;
			}

		} else {
			name_fact = slfXuong.getSelectedItem().toString();
			if (!ins.checkQLyNDept(name_fact, null, null)) {
				Application.getApp().showMessageDialog("Thông Báo", "Bạn không có quyền thao tác trên xưởng này.",
						MessageBox.OK);
				return;
			}

			if (ins.getSimpleJdbcTemplate()
					.queryForInt("select count(t.name_fact) from n_social_status t where t.name_fact =? "
							+ "and t.id_report = ? and t.status ='1'", name_fact, "0" + stt + id_bhxh_tn) < 1) {
				Application.getApp().showMessageDialog("Thông Báo", "Xưởng này chưa cập nhật dữ liệu!", MessageBox.OK);
				return;
			}

		}

		///////////////////////////// ---------------

		listCT = new ArrayList<N_CT_BHXH_TN>();
		Object[] arrObj = con.getKMLAN(id_bhxh_tn, name_fact);////////////////
		listCT = (List<N_CT_BHXH_TN>) arrObj[0];
		final List<N_CT_BHXH_TN> listTHo = (List<N_CT_BHXH_TN>) arrObj[1];
		System.out.println(listCT.size());
		dsc.echo2app.MessageDialog dlg = new dsc.echo2app.MessageDialog("THÔNG BÁO", "Bạn có muốn xuất EXCEL không",
				MessageDialog.CONTROLS_YES_NO);
		dlg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(MessageDialog.COMMAND_OK)) {
					try {
						if (row.getComponentCount() > 0)
							row.remove(0);
						row.add(ex);
						ex.exportCTBSTH(listCT, listTHo, dateCT, name_fact);
					} catch (Exception e1) {
						System.out.println(e1.toString());
					}
				}
			}
		});
		Application.getApp().getDefaultWindow().getContent().add(dlg);

	}

	public void exportExcelCT(String name, int stt) {
		try {
			if (listCT.size() > 0) {
				if (row.getComponentCount() > 0)
					row.remove(0);
				row.add(ex);
				ex.exportCT(listCT, dateCT, name, stt);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void exportExcelTH() {
		try {
			if (listBHXH_TN.size() > 0) {
				ex.setModeexport(mode);
				if (row.getComponentCount() > 0)
					row.remove(0);
				row.add(ex);
				ex.exportTH(listBHXH_TN, date, stt);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	private void initmore() {
		ButtonGroup bg = new ButtonGroup();
		rbtnTN.setGroup(bg);
		rbtnXH.setGroup(bg);
		rbtnXHTN.setGroup(bg);
		slfXuong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doslfXuongisSelect();

			}
		});
		slfNhom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doslfNhomisSelect();

			}
		});
		slfDonvi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doslfDonviisSelect();

			}
		});
		rbtnXH.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rbtnXH.isSelected())
					mode = MODEEXPORT_XH;

			}
		});
		rbtnTN.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rbtnTN.isSelected())
					mode = MODEEXPORT_TN;

			}
		});
		rbtnXHTN.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rbtnXHTN.isSelected())
					mode = MODEEXPORT_XHTN;

			}
		});
		btnBCTH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doBCTH();

			}
		});
		btnExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportExcelTH();
			}
		});
		btnBCCT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doBCCT();

			}
		});
		btnHuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCancel();

			}
		});
		ckbLan1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ckbLan1.isSelected()) {
					stt = 1;
					btnMua1.setEnabled(false);
				} else {
					stt = 2;
					btnMua1.setEnabled(true);
				}
			}
		});
		btnMua1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doMua1();

			}
		});
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(80, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(false);
		add(splitPane1);
		ContentPane contentPane2 = new ContentPane();
		splitPane1.add(contentPane2);
		Row row1 = new Row();
		contentPane2.add(row1);
		Button button1 = new Button();
		button1.setText("BÁO CÁO BẢO HIỂM THEO THÁNG");
		button1.setFont(new Font(null, Font.PLAIN, new Extent(20, Extent.PT)));
		button1.setWidth(new Extent(600, Extent.PX));
		button1.setForeground(Color.RED);
		RowLayoutData button1LayoutData = new RowLayoutData();
		button1LayoutData.setInsets(new Insets(new Extent(200, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX)));
		button1.setLayoutData(button1LayoutData);
		row1.add(button1);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setSeparatorPosition(new Extent(275, Extent.PX));
		splitPane2.setResizable(false);
		splitPane1.add(splitPane2);
		Column column1 = new Column();
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX)));
		column1.setLayoutData(column1LayoutData);
		splitPane2.add(column1);
		Row row2 = new Row();
		column1.add(row2);
		ckbLan1 = new CheckBox();
		ckbLan1.setSelected(true);
		ckbLan1.setText("Chạy ký trình lần 1");
		ckbLan1.setRolloverForeground(new Color(0x43afc2));
		ckbLan1.setRolloverEnabled(true);
		ckbLan1.setForeground(new Color(0x136993));
		row2.add(ckbLan1);
		Row row15 = new Row();
		column1.add(row15);
		Row row3 = new Row();
		column1.add(row3);
		Label label2 = new Label();
		label2.setText("Tháng");
		RowLayoutData label2LayoutData = new RowLayoutData();
		label2LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label2.setLayoutData(label2LayoutData);
		row3.add(label2);
		slfThang = new SelectField();
		slfThang.setWidth(new Extent(50, Extent.PX));
		RowLayoutData slfThangLayoutData = new RowLayoutData();
		slfThangLayoutData.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		slfThang.setLayoutData(slfThangLayoutData);
		row3.add(slfThang);
		Label label3 = new Label();
		label3.setText("năm");
		RowLayoutData label3LayoutData = new RowLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(5, Extent.PX),
				new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		row3.add(label3);
		slfNam = new SelectField();
		slfNam.setWidth(new Extent(70, Extent.PX));
		RowLayoutData slfNamLayoutData = new RowLayoutData();
		slfNamLayoutData.setInsets(new Insets(new Extent(20, Extent.PX), new Extent(5, Extent.PX),
				new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		slfNam.setLayoutData(slfNamLayoutData);
		row3.add(slfNam);
		Row row4 = new Row();
		column1.add(row4);
		Label label4 = new Label();
		label4.setText("Xưởng");
		RowLayoutData label4LayoutData = new RowLayoutData();
		label4LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label4.setLayoutData(label4LayoutData);
		row4.add(label4);
		slfXuong = new SelectField();
		slfXuong.setWidth(new Extent(187, Extent.PX));
		RowLayoutData slfXuongLayoutData = new RowLayoutData();
		slfXuongLayoutData.setInsets(new Insets(new Extent(19, Extent.PX), new Extent(5, Extent.PX),
				new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		slfXuong.setLayoutData(slfXuongLayoutData);
		row4.add(slfXuong);
		Row row5 = new Row();
		column1.add(row5);
		Label label5 = new Label();
		label5.setText("Nhóm");
		RowLayoutData label5LayoutData = new RowLayoutData();
		label5LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label5.setLayoutData(label5LayoutData);
		row5.add(label5);
		slfNhom = new SelectField();
		slfNhom.setEnabled(false);
		slfNhom.setWidth(new Extent(187, Extent.PX));
		RowLayoutData slfNhomLayoutData = new RowLayoutData();
		slfNhomLayoutData.setInsets(new Insets(new Extent(25, Extent.PX), new Extent(5, Extent.PX),
				new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		slfNhom.setLayoutData(slfNhomLayoutData);
		row5.add(slfNhom);
		Row row6 = new Row();
		column1.add(row6);
		Label label6 = new Label();
		label6.setText("Đơn vị");
		RowLayoutData label6LayoutData = new RowLayoutData();
		label6LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		label6.setLayoutData(label6LayoutData);
		row6.add(label6);
		slfDonvi = new SelectField();
		slfDonvi.setEnabled(false);
		slfDonvi.setWidth(new Extent(187, Extent.PX));
		RowLayoutData slfDonviLayoutData = new RowLayoutData();
		slfDonviLayoutData.setInsets(new Insets(new Extent(19, Extent.PX), new Extent(5, Extent.PX),
				new Extent(10, Extent.PX), new Extent(5, Extent.PX)));
		slfDonvi.setLayoutData(slfDonviLayoutData);
		row6.add(slfDonvi);
		Row row8 = new Row();
		column1.add(row8);
		Label label7 = new Label();
		label7.setText("Lựa chọn");
		label7.setForeground(new Color(0x136993));
		RowLayoutData label7LayoutData = new RowLayoutData();
		label7LayoutData.setInsets(new Insets(new Extent(50, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		label7.setLayoutData(label7LayoutData);
		row8.add(label7);
		Row row7 = new Row();
		row7.setBorder(new Border(new Extent(1, Extent.PX), Color.BLACK, Border.STYLE_SOLID));
		ColumnLayoutData row7LayoutData = new ColumnLayoutData();
		row7LayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0, Extent.PX),
				new Extent(5, Extent.PX), new Extent(0, Extent.PX)));
		row7.setLayoutData(row7LayoutData);
		column1.add(row7);
		Column column4 = new Column();
		row7.add(column4);
		Row row11 = new Row();
		column4.add(row11);
		rbtnXH = new RadioButton();
		rbtnXH.setText("Báo Cáo BHXH");
		rbtnXH.setRolloverForeground(new Color(0x43afc2));
		rbtnXH.setRolloverEnabled(true);
		rbtnXH.setForeground(new Color(0x136993));
		RowLayoutData rbtnXHLayoutData = new RowLayoutData();
		rbtnXHLayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		rbtnXH.setLayoutData(rbtnXHLayoutData);
		row11.add(rbtnXH);
		rbtnTN = new RadioButton();
		rbtnTN.setText("Báo Cáo BHTN");
		rbtnTN.setRolloverForeground(new Color(0x43afc2));
		rbtnTN.setRolloverEnabled(true);
		rbtnTN.setForeground(new Color(0x136993));
		RowLayoutData rbtnTNLayoutData = new RowLayoutData();
		rbtnTNLayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		rbtnTN.setLayoutData(rbtnTNLayoutData);
		row11.add(rbtnTN);
		Row row12 = new Row();
		column4.add(row12);
		rbtnXHTN = new RadioButton();
		rbtnXHTN.setSelected(true);
		rbtnXHTN.setText("BC BHXH_TN");
		rbtnXHTN.setRolloverForeground(new Color(0x43afc2));
		rbtnXHTN.setRolloverEnabled(true);
		rbtnXHTN.setForeground(new Color(0x136993));
		RowLayoutData rbtnXHTNLayoutData = new RowLayoutData();
		rbtnXHTNLayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(5, Extent.PX),
				new Extent(5, Extent.PX), new Extent(5, Extent.PX)));
		rbtnXHTN.setLayoutData(rbtnXHTNLayoutData);
		row12.add(rbtnXHTN);
		Row row9 = new Row();
		row9.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		column1.add(row9);
		btnBCTH = new Button();
		btnBCTH.setText("BC TỔNG HỢP");
		btnBCTH.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnBCTH.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnBCTH.setBackground(new Color(0x43afc2));
		btnBCTH.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnBCTH.setPressedEnabled(true);
		btnBCTH.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnBCTH.setRolloverEnabled(true);
		btnBCTH.setForeground(Color.WHITE);
		RowLayoutData btnBCTHLayoutData = new RowLayoutData();
		btnBCTHLayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnBCTHLayoutData.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnBCTHLayoutData.setWidth(new Extent(125, Extent.PX));
		btnBCTH.setLayoutData(btnBCTHLayoutData);
		row9.add(btnBCTH);
		btnExcel = new Button();
		btnExcel.setText("XUẤT EXCEL");
		btnExcel.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnExcel.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnExcel.setBackground(new Color(0x43afc2));
		btnExcel.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnExcel.setPressedEnabled(true);
		btnExcel.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnExcel.setRolloverEnabled(true);
		btnExcel.setForeground(Color.WHITE);
		RowLayoutData btnExcelLayoutData = new RowLayoutData();
		btnExcelLayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnExcelLayoutData.setWidth(new Extent(125, Extent.PX));
		btnExcel.setLayoutData(btnExcelLayoutData);
		row9.add(btnExcel);
		Row row10 = new Row();
		row10.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		column1.add(row10);
		btnBCCT = new Button();
		btnBCCT.setText("BC CHI TIẾT");
		btnBCCT.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnBCCT.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnBCCT.setBackground(new Color(0x43afc2));
		btnBCCT.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnBCCT.setPressedEnabled(true);
		btnBCCT.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnBCCT.setRolloverEnabled(true);
		btnBCCT.setForeground(Color.WHITE);
		RowLayoutData btnBCCTLayoutData = new RowLayoutData();
		btnBCCTLayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnBCCTLayoutData.setWidth(new Extent(125, Extent.PX));
		btnBCCT.setLayoutData(btnBCCTLayoutData);
		row10.add(btnBCCT);
		btnHuy = new Button();
		btnHuy.setText("BỎ QUA");
		btnHuy.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnHuy.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnHuy.setBackground(new Color(0x43afc2));
		btnHuy.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnHuy.setPressedEnabled(true);
		btnHuy.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnHuy.setRolloverEnabled(true);
		btnHuy.setForeground(Color.WHITE);
		RowLayoutData btnHuyLayoutData = new RowLayoutData();
		btnHuyLayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnHuyLayoutData.setWidth(new Extent(125, Extent.PX));
		btnHuy.setLayoutData(btnHuyLayoutData);
		row10.add(btnHuy);
		Row row13 = new Row();
		row13.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		column1.add(row13);
		btnMua1 = new Button();
		btnMua1.setEnabled(false);
		btnMua1.setText("DANH SÁCH BỔ SUNG/THU HỒI");
		btnMua1.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnMua1.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnMua1.setBackground(new Color(0x43afc2));
		btnMua1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnMua1.setPressedEnabled(true);
		btnMua1.setDisabledBorder(new Border(new Extent(2, Extent.PX), new Color(0xc0c0c0), Border.STYLE_GROOVE));
		btnMua1.setDisabledBackground(new Color(0xc0c0c0));
		btnMua1.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnMua1.setRolloverEnabled(true);
		btnMua1.setForeground(Color.WHITE);
		RowLayoutData btnMua1LayoutData = new RowLayoutData();
		btnMua1LayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnMua1LayoutData.setWidth(new Extent(250, Extent.PX));
		btnMua1.setLayoutData(btnMua1LayoutData);
		row13.add(btnMua1);
		Row row14 = new Row();
		row14.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		column1.add(row14);
		btnMua2 = new Button();
		btnMua2.setText("CHI MUA LAN 2");
		btnMua2.setVisible(false);
		btnMua2.setPressedBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_INSET));
		btnMua2.setRolloverBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_OUTSET));
		btnMua2.setBackground(new Color(0x43afc2));
		btnMua2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		btnMua2.setPressedEnabled(true);
		btnMua2.setBorder(new Border(new Extent(2, Extent.PX), new Color(0x80ffff), Border.STYLE_GROOVE));
		btnMua2.setRolloverEnabled(true);
		btnMua2.setForeground(Color.WHITE);
		RowLayoutData btnMua2LayoutData = new RowLayoutData();
		btnMua2LayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnMua2LayoutData.setWidth(new Extent(250, Extent.PX));
		btnMua2.setLayoutData(btnMua2LayoutData);
		row14.add(btnMua2);
		Column column2 = new Column();
		SplitPaneLayoutData column2LayoutData = new SplitPaneLayoutData();
		column2LayoutData.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		column2.setLayoutData(column2LayoutData);
		splitPane2.add(column2);
		tblData = new Table();
		tblData.setWidth(new Extent(600, Extent.PX));
		tblData.setRolloverBackground(new Color(0xaaf4c2));
		tblData.setBorder(new Border(new Extent(1, Extent.PX), new Color(0xc0c0c0), Border.STYLE_SOLID));
		tblData.setRolloverEnabled(true);
		column2.add(tblData);
		row = new Row();
		column2.add(row);
	}
}
