package ds.program.fvhr.ui.salary;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.layout.GridLayoutData;
import ds.program.fvhr.domain.ATT200000;
import ds.program.fvhr.domain.ATTENDANTDB;
import dsc.echo2app.component.DscField;
import echopointng.GroupBox;
import fv.components.JdbcDataContent;

public class SalaryDataContent extends JdbcDataContent {

	private static final long serialVersionUID = 1L;

	private SalaryProgram program;

	private DscField txtEMPSN;

	private TextArea txtEMPNA;

	private DscField txtDEPSN;

	private DscField txtPOSSN;

	private DscField txtHIRED;

	private DscField txtBSALY;

	private DscField txtCOMBSALY;

	private DscField txtBSALY_N;

	private DscField txtBONUS1;

	private DscField txtBONUS2;

	private DscField txtBONUS3;

	private DscField txtBONUS4;

	private DscField txtBONUS5;

	private DscField txtBONUS6;

	private DscField txtBONUS7;

	private DscField txtBONUS_ACN;

	private DscField txtJOININSU;

	private DscField txtJOINLUM;

	private DscField txtBORM;

	private DscField txtYLBX;

	private DscField txtPAYTAX;

	private DscField txtKQT;

	private DscField txtLATE;

	private DscField txtREST;

	private DscField txtNWHOUR;

	private DscField txtDUCLS;

	private DscField txtNUCLS;

	private DscField txtREST_PAY;

	private DscField txtADDCLS1;

	private DscField txtNADDCLS;

	private DscField txtADDHOL;

	private DscField txtADDHOLN;

	private TextArea txtNOTE;

	private DscField txtTBASIC;

	private DscField txtTADDS;

	private DscField txtTBONUS;

	private DscField txtTINCOME;

	private DscField txtTKQ;

	private DscField txtTTS;

	private DscField txtREST_SICK;

	private DscField txtTYADDCLS;

	private DscField txtBONUS9;

	private DscField txtOTHER;

	private DscField txtPHEP_NS;

	private DscField txtSIGN;

	private DscField txtBAC;

	private DscField txtBH_TNGHIEP;

	private DscField txtACNM;

	private TextArea txtDEPT_NAME;

	private DscField txtATM;

	private DscField txtSSALY;

	private DscField txtBONUS11;

	private DscField txtBONUS21;

	private DscField txtBONUS31;

	private DscField txtBONUS8;

	private DscField txtBONUS48;

	private DscField txtJOININSU1;

	private DscField txtBHTN1;

	public SalaryDataContent(SalaryProgram program) {
		this.program = program;
		initComponents();
	}

	@Override
	public boolean refetch() {
		return false;
	}

	// multi class data content
	public Class<?>[] dataClasses() {
		return new Class[] { ATT200000.class, ATTENDANTDB.class };
	}

	private void initComponents() {
		SplitPane dataSplitPane = new SplitPane(
				SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(dataSplitPane);
		ContentPane optionPane = new ContentPane();
		dataSplitPane.add(optionPane);
		Grid mainGrid = new Grid(3);
		dataSplitPane.add(mainGrid);
		GridLayoutData labelLayout = new GridLayoutData();
		labelLayout.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		GridLayoutData twoColumnSpan = new GridLayoutData();
		twoColumnSpan.setColumnSpan(2);
		GridLayoutData threeRowSpan = new GridLayoutData();// group 1,2 layout
		threeRowSpan.setRowSpan(3);
		threeRowSpan.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		GridLayoutData groupBoxLayout = new GridLayoutData();
		groupBoxLayout.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		GridLayoutData summaryGroupLayout = new GridLayoutData();
		summaryGroupLayout.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		GroupBox firstGroup = new GroupBox("Thông tin nhân viên");
		firstGroup.setHeight(new Extent(100, Extent.PERCENT));
		firstGroup.setLayoutData(threeRowSpan);
		mainGrid.add(firstGroup);
		GroupBox secondGroup = new GroupBox("Biểu thời gian");
		// secondGroup.setHeight(new Extent(440));
		secondGroup.setLayoutData(threeRowSpan);
		mainGrid.add(secondGroup);
		GroupBox thirdGroup = new GroupBox("Thưởng/phụ cấp");
		thirdGroup.setLayoutData(groupBoxLayout);
		mainGrid.add(thirdGroup);
		GroupBox fourthGroup = new GroupBox("Khấu trừ");
		fourthGroup.setLayoutData(groupBoxLayout);
		mainGrid.add(fourthGroup);
		GroupBox summaryGroup = new GroupBox();
		summaryGroup.setLayoutData(summaryGroupLayout);
		mainGrid.add(summaryGroup);
		// //First group elements
		Grid firstGrid = new Grid();
		firstGrid.setInsets(new Insets(1, 1, 1, 0));
		firstGrid.add(new Label("Số thẻ"));
		txtEMPSN = new DscField();
		txtEMPSN.setWidth(new Extent(85));
		firstGrid.add(txtEMPSN);
		firstGrid.add(new Label("Họ tên"));
		txtEMPNA = new TextArea();
		txtEMPNA.setHeight(new Extent(36));
		txtEMPNA.setWidth(new Extent(85));
		firstGrid.add(txtEMPNA);
		firstGrid.add(new Label("Ngày NX"));
		txtHIRED = new DscField();
		txtHIRED.setWidth(new Extent(85));
		firstGrid.add(txtHIRED);
		firstGrid.add(new Label("Chức vụ"));
		txtPOSSN = new DscField();
		txtPOSSN.setWidth(new Extent(85));
		firstGrid.add(txtPOSSN);
		firstGrid.add(new Label("Đơn vị"));
		txtDEPSN = new DscField();
		txtDEPSN.setWidth(new Extent(85));
		firstGrid.add(txtDEPSN);
		firstGrid.add(new Label("Tên đơn vị"));
		txtDEPT_NAME = new TextArea();
		txtDEPT_NAME.setHeight(new Extent(36));
		txtDEPT_NAME.setWidth(new Extent(85));
		firstGrid.add(txtDEPT_NAME);
		firstGrid.add(new Label("Mã ATM"));
		txtATM = new DscField();
		txtATM.setWidth(new Extent(85));
		firstGrid.add(txtATM);
		firstGrid.add(new Label("Lương cơ bản"));
		txtBSALY = new DscField();
		txtBSALY.setWidth(new Extent(85));
		firstGrid.add(txtBSALY);
		firstGrid.add(new Label("Lương hợp đồng"));
		txtCOMBSALY = new DscField();
		txtCOMBSALY.setWidth(new Extent(85));
		firstGrid.add(txtCOMBSALY);
		firstGrid.add(new Label("Lương thử việc"));
		txtSSALY = new DscField();
		txtSSALY.setWidth(new Extent(85));
		firstGrid.add(txtSSALY);
		firstGrid.add(new Label("Lũy kế tăng ca"));
		txtTYADDCLS = new DscField();
		txtTYADDCLS.setWidth(new Extent(85));
		firstGrid.add(txtTYADDCLS);
		firstGrid.add(new Label("Ghi chú"));
		txtNOTE = new TextArea();
		txtNOTE.setHeight(new Extent(36));
		txtNOTE.setWidth(new Extent(85));
		firstGrid.add(txtNOTE);
		for (int i = 0; i < firstGrid.getComponentCount(); i++) {
			if (firstGrid.getComponent(i) instanceof Label) {
				firstGrid.getComponent(i).setFont(
						new Font(Font.ARIAL, Font.PLAIN, new Extent(9,
								Extent.PT)));
				firstGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}
		firstGroup.add(firstGrid);

		// //Second group elements
		Grid secondGrid = new Grid();
		secondGrid.setInsets(new Insets(1, 1, 1, 0));
		secondGrid.add(new Label("Số ngày làm"));
		txtDUCLS = new DscField();
		txtDUCLS.setWidth(new Extent(35));
		secondGrid.add(txtDUCLS);// atte
		secondGrid.add(new Label("Số đêm làm"));
		txtNUCLS = new DscField();
		txtNUCLS.setWidth(new Extent(35));
		secondGrid.add(txtNUCLS);// atte
		secondGrid.add(new Label("Tăng ca ngày"));
		txtADDCLS1 = new DscField();
		txtADDCLS1.setWidth(new Extent(35));
		secondGrid.add(txtADDCLS1);// atte		
		secondGrid.add(new Label("Tăng ca đêm"));
		txtNADDCLS = new DscField();
		txtNADDCLS.setWidth(new Extent(35));
		secondGrid.add(txtNADDCLS);// atte
		secondGrid.add(new Label("Tăng ca chủ nhật"));
		txtADDHOL = new DscField();
		txtADDHOL.setWidth(new Extent(35));
		secondGrid.add(txtADDHOL);// atte
		secondGrid.add(new Label("Tăng ca lễ"));
		txtADDHOLN = new DscField();
		txtADDHOLN.setWidth(new Extent(35));
		secondGrid.add(txtADDHOLN);// atte
		secondGrid.add(new Label("Số đêm tăng ca"));
		txtACNM = new DscField();
		txtACNM.setWidth(new Extent(35));
		secondGrid.add(txtACNM);// atte
		secondGrid.add(new Label("Ký tên"));
		txtSIGN = new DscField();
		txtSIGN.setWidth(new Extent(35));
		secondGrid.add(txtSIGN);// atte
		secondGrid.add(new Label("Trễ/sớm"));
		txtLATE = new DscField();
		txtLATE.setWidth(new Extent(35));
		secondGrid.add(txtLATE);// atte
		for (int i = 0; i < 18; i++) {
			if (secondGrid.getComponent(i) instanceof Label) {
				secondGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}

		Label sepRest = new Label("Phép--------------");
		sepRest.setLayoutData(twoColumnSpan);
		secondGrid.add(sepRest);
		secondGrid.add(new Label("Có phép"));
		txtREST = new DscField();
		txtREST.setWidth(new Extent(35));
		secondGrid.add(txtREST);// atte
		secondGrid.add(new Label("Có lương"));
		txtREST_PAY = new DscField();
		txtREST_PAY.setWidth(new Extent(35));
		secondGrid.add(txtREST_PAY);// atte
		secondGrid.add(new Label("Phép bệnh"));
		txtREST_SICK = new DscField();
		txtREST_SICK.setWidth(new Extent(35));
		secondGrid.add(txtREST_SICK);// atte
		secondGrid.add(new Label("Kháng công"));
		txtNWHOUR = new DscField();
		txtNWHOUR.setWidth(new Extent(35));
		secondGrid.add(txtNWHOUR);// atte
		secondGrid.add(new Label("Dưỡng sức"));
		txtOTHER = new DscField();
		txtOTHER.setWidth(new Extent(35));
		secondGrid.add(txtOTHER);// atte
		secondGrid.add(new Label("Nghỉ sản"));
		txtPHEP_NS = new DscField();
		txtPHEP_NS.setWidth(new Extent(35));
		secondGrid.add(txtPHEP_NS);// atte
		for (int i = 19; i < secondGrid.getComponentCount(); i++) {
			if (secondGrid.getComponent(i) instanceof Label) {
				secondGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}
		for (int i = 0; i < secondGrid.getComponentCount(); i++) {
			if (secondGrid.getComponent(i) instanceof Label) {
				secondGrid.getComponent(i).setFont(
						new Font(Font.ARIAL, Font.PLAIN, new Extent(9,
								Extent.PT)));
			}
		}
		secondGroup.add(secondGrid);

		// //Third group elements
		Grid thirdGrid = new Grid(6);
		thirdGrid.setInsets(new Insets(1, 1, 1, 0));
		// thirdGrid.setBorder(new Border(new Extent(1), Color.BLUE,
		// Border.STYLE_SOLID));
		thirdGrid.add(new Label("Tiền thưởng"));
		txtBONUS1 = new DscField();
		txtBONUS1.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS1);// atte
		thirdGrid.add(new TT());
		txtBONUS11 = new DscField();// att
		txtBONUS11.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS11);
		thirdGrid.add(new Label("Bù bảo hiểm"));
		txtBONUS9 = new DscField();// att
		txtBONUS9.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS9);// ---------------
		thirdGrid.add(new Label("PC chức vụ"));
		txtBONUS2 = new DscField();
		txtBONUS2.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS2);// atte
		thirdGrid.add(new TT());
		txtBONUS21 = new DscField();
		txtBONUS21.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS21);// att
		thirdGrid.add(new Label("Chuyên cần"));
		txtBONUS6 = new DscField();// att
		txtBONUS6.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS6);// ---------------
		thirdGrid.add(new Label("PC công việc"));
		txtBONUS3 = new DscField();
		txtBONUS3.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS3);// atte
		thirdGrid.add(new TT());
		txtBONUS31 = new DscField();
		txtBONUS31.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS31);
		thirdGrid.add(new Label("PC tiền cơm"));
		txtBONUS7 = new DscField();// att
		txtBONUS7.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS7);// ---------------
		thirdGrid.add(new Label("PC sinh hoạt"));
		txtBONUS4 = new DscField();
		txtBONUS4.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS4);
		for (int i = 0; i < 20; i++) {
			if (thirdGrid.getComponent(i) instanceof Label) {
				thirdGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}
		GridLayoutData twoRowSpan = new GridLayoutData();
		twoRowSpan.setRowSpan(2);
		Label lblPlus = new Label("+");
		lblPlus.setForeground(Color.RED);
		lblPlus.setLayoutData(twoRowSpan);
		thirdGrid.add(lblPlus);
		txtBONUS48 = new DscField();// att
		txtBONUS48.setWidth(new Extent(65));
		txtBONUS48.setLayoutData(twoRowSpan);
		thirdGrid.add(txtBONUS48);
		thirdGrid.add(new Label("Chuyên cần tc"));
		txtBONUS_ACN = new DscField();// att
		txtBONUS_ACN.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS_ACN);// --------------
		thirdGrid.add(new Label("PC làm 8H"));
		txtBONUS8 = new DscField();// atte
		txtBONUS8.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS8);
		thirdGrid.add(new Label("Chênh lệch PCTC"));
		txtBSALY_N = new DscField();// att
		txtBSALY_N.setWidth(new Extent(65));
		thirdGrid.add(txtBSALY_N);// ----------------
		thirdGrid.add(new Label("Bù lương"));
		txtBONUS5 = new DscField();// atte
		txtBONUS5.setWidth(new Extent(65));
		thirdGrid.add(txtBONUS5);
		thirdGrid.add(new Label());
		thirdGrid.add(new Label());
		thirdGrid.add(new Label("Người lệ thuộc"));
		txtBAC = new DscField();// att
		txtBAC.setWidth(new Extent(65));
		thirdGrid.add(txtBAC);// -------------------
		for (int i = 22; i < thirdGrid.getComponentCount(); i++) {
			if (thirdGrid.getComponent(i) instanceof Label) {
				thirdGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}
		for (int i = 0; i < thirdGrid.getComponentCount(); i++) {
			if (thirdGrid.getComponent(i) instanceof Label) {
				thirdGrid.getComponent(i).setFont(
						new Font(Font.ARIAL, Font.PLAIN, new Extent(9,
								Extent.PT)));
			}
		}
		thirdGroup.add(thirdGrid);
		// //===============================================================

		// //fourthGroup elements
		Grid fourthGrid = new Grid(6);
		fourthGrid.setColumnWidth(0, new Extent(75));
		fourthGrid.setColumnWidth(4, new Extent(103));
		fourthGrid.setInsets(new Insets(1, 1, 1, 0));
		fourthGrid.add(new Label("BHXH"));
		txtJOININSU = new DscField();// atte
		txtJOININSU.setWidth(new Extent(65));
		fourthGrid.add(txtJOININSU);
		fourthGrid.add(new TT());
		txtJOININSU1 = new DscField();// att
		txtJOININSU1.setWidth(new Extent(65));
		fourthGrid.add(txtJOININSU1);
		fourthGrid.add(new Label("Tạm ứng"));
		txtBORM = new DscField();// atte
		txtBORM.setWidth(new Extent(65));
		fourthGrid.add(txtBORM);// ---------------
		fourthGrid.add(new Label("BHTN"));
		txtBH_TNGHIEP = new DscField();// atte
		txtBH_TNGHIEP.setWidth(new Extent(65));
		fourthGrid.add(txtBH_TNGHIEP);
		fourthGrid.add(new TT());
		txtBHTN1 = new DscField();// att
		txtBHTN1.setWidth(new Extent(65));
		fourthGrid.add(txtBHTN1);
		fourthGrid.add(new Label("Khấu trừ khác"));
		txtKQT = new DscField();// atte
		txtKQT.setWidth(new Extent(65));
		fourthGrid.add(txtKQT);// -----------
		fourthGrid.add(new Label("BHYT"));
		txtYLBX = new DscField();// atte
		txtYLBX.setWidth(new Extent(65));
		fourthGrid.add(txtYLBX);
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		fourthGrid.add(new Label("Thuế thu nhập"));
		txtPAYTAX = new DscField();// att
		txtPAYTAX.setWidth(new Extent(65));
		fourthGrid.add(txtPAYTAX);// ---------
		fourthGrid.add(new Label("Đoàn phí"));
		txtJOINLUM = new DscField();
		txtJOINLUM.setWidth(new Extent(65));
		fourthGrid.add(txtJOINLUM);
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		fourthGrid.add(new Label());
		for (int i = 0; i < fourthGrid.getComponentCount(); i++) {
			if (fourthGrid.getComponent(i) instanceof Label) {
				fourthGrid.getComponent(i).setFont(
						new Font(Font.ARIAL, Font.PLAIN, new Extent(9,
								Extent.PT)));
				fourthGrid.getComponent(i).setLayoutData(labelLayout);
			}
		}
		fourthGroup.add(fourthGrid);
		// //=====================================================

		Grid summaryGrid = new Grid(6);
		// --row1
		summaryGrid.add(new Label());
		summaryGrid.add(new Label("Tổng lương CB"));
		summaryGrid.add(new Label());
		summaryGrid.add(new Label("Tiền tăng ca"));
		summaryGrid.add(new Label());
		summaryGrid.add(new Label("Tổng phụ cấp"));
		// --row2
		summaryGrid.add(new Label());
		txtTBASIC = new DscField();
		txtTBASIC.setWidth(new Extent(90));
		summaryGrid.add(txtTBASIC);
		Label plusLabel = new Label("+");
		plusLabel.setForeground(Color.RED);
		summaryGrid.add(plusLabel);
		txtTADDS = new DscField();
		txtTADDS.setWidth(new Extent(90));
		summaryGrid.add(txtTADDS);
		Label plusLabel1 = new Label("+");
		plusLabel1.setForeground(Color.RED);
		summaryGrid.add(plusLabel1);
		txtTBONUS = new DscField();
		txtTBONUS.setWidth(new Extent(90));
		summaryGrid.add(txtTBONUS);
		// --row3
		summaryGrid.add(new Label());
		summaryGrid.add(new Label());
		summaryGrid.add(new Label());
		summaryGrid.add(new Label("Khấu trừ"));
		summaryGrid.add(new Label());
		summaryGrid.add(new Label("Thực nhận"));
		// --row4
		Label equalLabel = new Label("=");
		equalLabel.setForeground(Color.RED);
		summaryGrid.add(equalLabel);
		txtTINCOME = new DscField();
		txtTINCOME.setWidth(new Extent(90));
		summaryGrid.add(txtTINCOME);
		Label minusLabel = new Label("-");
		minusLabel.setForeground(Color.RED);
		summaryGrid.add(minusLabel);
		txtTKQ = new DscField();
		txtTKQ.setWidth(new Extent(90));
		summaryGrid.add(txtTKQ);
		Label equalLabel1 = new Label("=");
		equalLabel1.setForeground(Color.RED);
		summaryGrid.add(equalLabel1);
		txtTTS = new DscField();
		txtTTS.setWidth(new Extent(90));
		txtTTS.setBackground(new Color(0xCCFFFF));
		txtTTS.setDisabledForeground(Color.RED);
		txtTTS.setForeground(Color.RED);
		summaryGrid.add(txtTTS);
		summaryGroup.add(summaryGrid);
	}

	private class TT extends Label {
		private static final long serialVersionUID = 1L;

		public TT() {
			super();
			setText(">");
			setForeground(Color.RED);
		}
	}
}
