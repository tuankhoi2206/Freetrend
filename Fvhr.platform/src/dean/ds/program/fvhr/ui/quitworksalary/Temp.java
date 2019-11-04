package ds.program.fvhr.ui.quitworksalary;

import java.util.ResourceBundle;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscGroupCheckBox;
import dsc.echo2app.component.DscGroupRadioButton;
import echopointng.GroupBox;

public class Temp extends ContentPane {

	private ResourceBundle resourceBundle;

	private RadioButton radFact1;

	private RadioButton radFact2;

	private RadioButton radFG1;

	private RadioButton radFG2;

	private SelectField sfMonth;

	private SelectField sfYear;

	private Row rowMonthYear;

	private RadioButton radQuitDate;

	private RadioButton radMonthYear;

	private DscDateField dfDotTV;

	private RadioButton radDot1;

	private RadioButton radDot2;

	private RadioButton radDot3;

	private CheckBox chkTV;

	private CheckBox chkBV;

	private Grid rootLayout;

	private RadioButton radTonghop;

	private RadioButton radBangTong;

	private RadioButton radBHXH;

	private RadioButton radBHYT;

	private RadioButton radBHTN;

	private RadioButton radBuBH;

	private RadioButton radExcel;

	private RadioButton radTongLuongTV;

	private RadioButton radTongTroCap;

	private RadioButton radBangThanhToan;

	private RadioButton radBangKyNhan;

	private SelectField sfFact;

	private DscGroupRadioButton groupTongHop;

	private DscGroupRadioButton groupExcel;

	private DscGroupCheckBox groupFVL;

	private Row rowDotTV;

	/**
	 * Creates a new <code>Temp</code>.
	 */
	public Temp() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	private void sfFactoriesChanged(ActionEvent e) {
		// TODO Implement.
	}

	private void donviChanged(ActionEvent e) {
		// TODO Implement.
	}

	private void bangluongChanged(ActionEvent e) {
		// TODO Implement.
	}

	private void baocaoChanged(ActionEvent e) {
		// TODO Implement.
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(rootLayout);
		GroupBox groupBox1 = new GroupBox();
		groupBox1.setTitle("Đơn vị");
		groupBox1.setHeight(new Extent(130, Extent.PX));
		GridLayoutData groupBox1LayoutData = new GridLayoutData();
		groupBox1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox1.setLayoutData(groupBox1LayoutData);
		rootLayout.add(groupBox1);
		Grid grid2 = new Grid();
		grid2.setInsets(new Insets(new Extent(2, Extent.PX)));
		groupBox1.add(grid2);
		radFact1 = new RadioButton();
		radFact1.setSelected(true);
		radFact1.setText("Xưởng");
		ButtonGroup donvi = new ButtonGroup();
		radFact1.setGroup(donvi);
		radFact1.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radFact1.setActionCommand("xuong");
		radFact1.setForeground(new Color(0x008080));
		radFact1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				donviChanged(e);
			}
		});
		grid2.add(radFact1);
		sfFact = new SelectField();
		sfFact.setDisabledBackground(new Color(0x808080));
		sfFact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sfFactoriesChanged(e);
			}
		});
		grid2.add(sfFact);
		groupFVL = new DscGroupCheckBox();
		groupFVL.setEnabled(false);
		groupFVL.setSize(3);
		GridLayoutData groupFVLLayoutData = new GridLayoutData();
		groupFVLLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupFVLLayoutData.setColumnSpan(2);
		groupFVL.setLayoutData(groupFVLLayoutData);
		grid2.add(groupFVL);
		CheckBox chkFv1 = new CheckBox();
		chkFv1.setText("FV1");
		chkFv1.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv1);
		CheckBox chkFv2 = new CheckBox();
		chkFv2.setText("FV2");
		chkFv2.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv2);
		CheckBox chkFv3 = new CheckBox();
		chkFv3.setText("FV3");
		chkFv3.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv3);
		CheckBox chkFv4 = new CheckBox();
		chkFv4.setText("FV4");
		chkFv4.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv4);
		CheckBox chkFv5 = new CheckBox();
		chkFv5.setText("FV5");
		chkFv5.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkFv5);
		CheckBox chkOther = new CheckBox();
		chkOther.setText("Khác");
		chkOther.setDisabledBackground(new Color(0xc0c0c0));
		groupFVL.add(chkOther);
		radFact2 = new RadioButton();
		radFact2.setText("Nhóm đơn vị");
		radFact2.setGroup(donvi);
		radFact2.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radFact2.setActionCommand("nhomdv");
		radFact2.setForeground(new Color(0x008080));
		GridLayoutData radFact2LayoutData = new GridLayoutData();
		radFact2LayoutData.setColumnSpan(2);
		radFact2.setLayoutData(radFact2LayoutData);
		radFact2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				donviChanged(e);
			}
		});
		grid2.add(radFact2);
		radFG1 = new RadioButton();
		radFG1.setEnabled(false);
		radFG1.setSelected(true);
		radFG1.setText("FVL - KDAO - TB019");
		ButtonGroup nhomdv = new ButtonGroup();
		radFG1.setGroup(nhomdv);
		radFG1.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radFG1LayoutData = new GridLayoutData();
		radFG1LayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		radFG1LayoutData.setColumnSpan(2);
		radFG1.setLayoutData(radFG1LayoutData);
		grid2.add(radFG1);
		radFG2 = new RadioButton();
		radFG2.setEnabled(false);
		radFG2.setText("FVS - TB020");
		radFG2.setGroup(nhomdv);
		radFG2.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radFG2LayoutData = new GridLayoutData();
		radFG2LayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		radFG2LayoutData.setColumnSpan(2);
		radFG2.setLayoutData(radFG2LayoutData);
		grid2.add(radFG2);
		GroupBox groupBox2 = new GroupBox();
		groupBox2.setTitle("Bảng lương");
		groupBox2.setHeight(new Extent(130, Extent.PX));
		GridLayoutData groupBox2LayoutData = new GridLayoutData();
		groupBox2LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		groupBox2.setLayoutData(groupBox2LayoutData);
		rootLayout.add(groupBox2);
		Grid grid3 = new Grid();
		grid3.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid3.setSize(2);
		groupBox2.add(grid3);
		rowMonthYear = new Row();
		GridLayoutData rowMonthYearLayoutData = new GridLayoutData();
		rowMonthYearLayoutData.setColumnSpan(2);
		rowMonthYear.setLayoutData(rowMonthYearLayoutData);
		grid3.add(rowMonthYear);
		radMonthYear = new RadioButton();
		radMonthYear.setText("Tháng");
		ButtonGroup bangluong = new ButtonGroup();
		radMonthYear.setGroup(bangluong);
		radMonthYear.setFont(new Font(null, Font.BOLD,
				new Extent(10, Extent.PT)));
		radMonthYear.setActionCommand("thangnam");
		radMonthYear.setForeground(new Color(0x008080));
		radMonthYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bangluongChanged(e);
			}
		});
		rowMonthYear.add(radMonthYear);
		sfMonth = new SelectField();
		sfMonth.setEnabled(false);
		sfMonth.setWidth(new Extent(40, Extent.PX));
		sfMonth.setDisabledBackground(new Color(0xc0c0c0));
		rowMonthYear.add(sfMonth);
		Label label1 = new Label();
		label1.setText("Năm");
		rowMonthYear.add(label1);
		sfYear = new SelectField();
		sfYear.setEnabled(false);
		sfYear.setWidth(new Extent(60, Extent.PX));
		sfYear.setDisabledBackground(new Color(0xc0c0c0));
		rowMonthYear.add(sfYear);
		radQuitDate = new RadioButton();
		radQuitDate.setSelected(true);
		radQuitDate.setText("Đợt TV");
		radQuitDate.setGroup(bangluong);
		radQuitDate
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radQuitDate.setActionCommand("dottv");
		radQuitDate.setForeground(new Color(0x008080));
		radQuitDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bangluongChanged(e);
			}
		});
		grid3.add(radQuitDate);
		dfDotTV = new DscDateField();
		grid3.add(dfDotTV);
		rowDotTV = new Row();
		GridLayoutData rowDotTVLayoutData = new GridLayoutData();
		rowDotTVLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		rowDotTVLayoutData.setColumnSpan(2);
		rowDotTV.setLayoutData(rowDotTVLayoutData);
		grid3.add(rowDotTV);
		radDot1 = new RadioButton();
		radDot1.setText("Đợt 1");
		ButtonGroup dottv = new ButtonGroup();
		radDot1.setGroup(dottv);
		radDot1.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot1);
		radDot2 = new RadioButton();
		radDot2.setSelected(true);
		radDot2.setText("Đợt 2");
		radDot2.setGroup(dottv);
		radDot2.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot2);
		radDot3 = new RadioButton();
		radDot3.setText("Cả 2");
		radDot3.setGroup(dottv);
		radDot3.setDisabledBackground(new Color(0xc0c0c0));
		rowDotTV.add(radDot3);
		Label label2 = new Label();
		label2.setText("---------------------------");
		GridLayoutData label2LayoutData = new GridLayoutData();
		label2LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		label2LayoutData.setColumnSpan(2);
		label2.setLayoutData(label2LayoutData);
		grid3.add(label2);
		Row row2 = new Row();
		GridLayoutData row2LayoutData = new GridLayoutData();
		row2LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		row2LayoutData.setColumnSpan(2);
		row2.setLayoutData(row2LayoutData);
		grid3.add(row2);
		chkTV = new CheckBox();
		chkTV.setSelected(true);
		chkTV.setText("Thôi việc");
		chkTV.setForeground(new Color(0x004000));
		row2.add(chkTV);
		chkBV = new CheckBox();
		chkBV.setText("Bỏ việc");
		chkBV.setForeground(new Color(0x004000));
		row2.add(chkBV);
		GroupBox groupBox3 = new GroupBox();
		groupBox3.setTitle("Loại báo cáo");
		GridLayoutData groupBox3LayoutData = new GridLayoutData();
		groupBox3LayoutData.setColumnSpan(2);
		groupBox3.setLayoutData(groupBox3LayoutData);
		rootLayout.add(groupBox3);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(2, Extent.PX)));
		grid1.setSize(1);
		groupBox3.add(grid1);
		radTonghop = new RadioButton();
		radTonghop.setText("Báo cáo tổng hợp");
		ButtonGroup loaibc = new ButtonGroup();
		radTonghop.setGroup(loaibc);
		radTonghop
				.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radTonghop.setActionCommand("bctonghop");
		radTonghop.setForeground(new Color(0x008080));
		radTonghop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				baocaoChanged(e);
			}
		});
		grid1.add(radTonghop);
		groupTongHop = new DscGroupRadioButton();
		groupTongHop.setEnabled(false);
		GridLayoutData groupTongHopLayoutData = new GridLayoutData();
		groupTongHopLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupTongHop.setLayoutData(groupTongHopLayoutData);
		grid1.add(groupTongHop);
		radBangTong = new RadioButton();
		radBangTong.setSelected(true);
		radBangTong.setText("Bảng tổng");
		radBangTong.setDisabledBackground(new Color(0xc0c0c0));
		GridLayoutData radBangTongLayoutData = new GridLayoutData();
		radBangTongLayoutData.setColumnSpan(2);
		radBangTong.setLayoutData(radBangTongLayoutData);
		groupTongHop.add(radBangTong);
		radBHXH = new RadioButton();
		radBHXH.setText("DS CNV mua BHXH");
		radBHXH.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHXH);
		radBHYT = new RadioButton();
		radBHYT.setText("DS CNV mua BHYT");
		radBHYT.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHYT);
		radBHTN = new RadioButton();
		radBHTN.setText("DS CNV mua BHTN");
		radBHTN.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBHTN);
		radBuBH = new RadioButton();
		radBuBH.setText("DS CNV được bù BH");
		radBuBH.setDisabledBackground(new Color(0xc0c0c0));
		groupTongHop.add(radBuBH);
		radExcel = new RadioButton();
		radExcel.setSelected(true);
		radExcel.setText("Xuất dữ liệu ra excel");
		radExcel.setGroup(loaibc);
		radExcel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		radExcel.setActionCommand("bcexcel");
		radExcel.setForeground(new Color(0x008080));
		radExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				baocaoChanged(e);
			}
		});
		grid1.add(radExcel);
		groupExcel = new DscGroupRadioButton();
		GridLayoutData groupExcelLayoutData = new GridLayoutData();
		groupExcelLayoutData.setInsets(new Insets(new Extent(15, Extent.PX),
				new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(
						0, Extent.PX)));
		groupExcel.setLayoutData(groupExcelLayoutData);
		grid1.add(groupExcel);
		radTongLuongTV = new RadioButton();
		radTongLuongTV.setSelected(true);
		radTongLuongTV.setText("Tổng lương thôi việc");
		radTongLuongTV.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radTongLuongTV);
		radTongTroCap = new RadioButton();
		radTongTroCap.setText("Tổng trợ cấp");
		radTongTroCap.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radTongTroCap);
		radBangThanhToan = new RadioButton();
		radBangThanhToan.setText("Bảng thanh toán");
		radBangThanhToan.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radBangThanhToan);
		radBangKyNhan = new RadioButton();
		radBangKyNhan.setText("Bảng ký nhận");
		radBangKyNhan.setDisabledBackground(new Color(0xc0c0c0));
		groupExcel.add(radBangKyNhan);
	}
}
