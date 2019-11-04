package ds.program.fvhr.ui.quitworksalary;

import java.io.IOException;
import java.util.ResourceBundle;

import dsc.echo2app.Application;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class AttQuit01PrintPane extends ContentPane {

	private ResourceBundle resourceBundle;
	private Button btnPrintAll;
	private Button btnPrintATM;
	private Button btnPrint0ATM;
	protected WindowPane window;
	private QuitWork00RProgram program;
	/**
	 * Creates a new <code>AttQuit01PrintPane</code>.
	 */
	public AttQuit01PrintPane(QuitWork00RProgram program) {
		super();

		// Add design-time configured components.
		initComponents();
		window = new WindowPane();
		window.setHeight(new Extent(250));
		window.setWidth(new Extent(180));
		window.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		window.setModal(true);
		window.setTitle("In phiếu lương");
		window.setStyleName("Default.Window");
		window.add(this);
		this.program=program;
	}
	
	public void show(){
		Application.getApp().getDefaultWindow().getContent().add(window);
	}
	
	private void doPrintAll(ActionEvent e) {
		try {
			program.doExportPdf(program.generateJasperPrint());
			window.userClose();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void doPrintATM(ActionEvent e) {
		try {
			program.doExportPdf(program.generateATMJasperPrint(QuitWork00RProgram.PDF_PHIEULUONG_ATM));
			window.userClose();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void doPrint0ATM(ActionEvent e) {
		try {
			program.doExportPdf(program.generateATMJasperPrint(QuitWork00RProgram.PDF_PHIEULUONG_0ATM));
			window.userClose();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Column column1 = new Column();
		column1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10,
				Extent.PX)));
		column1.setCellSpacing(new Extent(5, Extent.PX));
		add(column1);
		btnPrintAll = new Button();
		btnPrintAll.setText("In tất cả");
		btnPrintAll.setWidth(new Extent(140, Extent.PX));
		btnPrintAll.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnPrintAll.setPressedEnabled(true);
		btnPrintAll.setRolloverBackground(new Color(0x004080));
		btnPrintAll.setPressedBackground(new Color(0xff8000));
		btnPrintAll.setBorder(new Border(new Extent(3, Extent.PX), new Color(
				0x0080c0), Border.STYLE_DOUBLE));
		btnPrintAll.setRolloverForeground(Color.RED);
		btnPrintAll.setRolloverEnabled(true);
		ColumnLayoutData btnPrintAllLayoutData = new ColumnLayoutData();
		btnPrintAllLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnPrintAll.setLayoutData(btnPrintAllLayoutData);
		btnPrintAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPrintAll(e);
			}
		});
		column1.add(btnPrintAll);
		btnPrintATM = new Button();
		btnPrintATM.setText("In ATM");
		btnPrintATM.setWidth(new Extent(140, Extent.PX));
		btnPrintATM.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnPrintATM.setPressedEnabled(true);
		btnPrintATM.setRolloverBackground(new Color(0x004080));
		btnPrintATM.setPressedBackground(new Color(0xff8000));
		btnPrintATM.setBorder(new Border(new Extent(3, Extent.PX), new Color(
				0x0080c0), Border.STYLE_DOUBLE));
		btnPrintATM.setRolloverForeground(Color.RED);
		btnPrintATM.setRolloverEnabled(true);
		ColumnLayoutData btnPrintATMLayoutData = new ColumnLayoutData();
		btnPrintATMLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnPrintATM.setLayoutData(btnPrintATMLayoutData);
		btnPrintATM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPrintATM(e);
			}
		});
		column1.add(btnPrintATM);
		btnPrint0ATM = new Button();
		btnPrint0ATM.setText("In không ATM");
		btnPrint0ATM.setWidth(new Extent(140, Extent.PX));
		btnPrint0ATM.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnPrint0ATM.setPressedEnabled(true);
		btnPrint0ATM.setRolloverBackground(new Color(0x004080));
		btnPrint0ATM.setPressedBackground(new Color(0xff8000));
		btnPrint0ATM.setBorder(new Border(new Extent(3, Extent.PX), new Color(
				0x0080c0), Border.STYLE_DOUBLE));
		btnPrint0ATM.setRolloverForeground(Color.RED);
		btnPrint0ATM.setRolloverEnabled(true);
		ColumnLayoutData btnPrint0ATMLayoutData = new ColumnLayoutData();
		btnPrint0ATMLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.DEFAULT));
		btnPrint0ATM.setLayoutData(btnPrint0ATMLayoutData);
		btnPrint0ATM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPrint0ATM(e);
			}
		});
		column1.add(btnPrint0ATM);
	}
}
