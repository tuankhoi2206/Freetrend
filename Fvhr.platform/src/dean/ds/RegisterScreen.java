package ds;

import java.util.ResourceBundle;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;

import org.springframework.context.support.ResourceBundleMessageSource;

import dsc.dotj.license.LicenseManager;
import dsc.dotj.license.SerialNumber;
import dsc.dotj.license.LicenseManager.RegInfo;
import dsc.dotj.license.exception.LicenseException;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;

public class RegisterScreen extends ContentPane {

	private ResourceBundle resourceBundle;

	private TextField sn_textfield;

	private TextField username_textfield;

	private TextField mail_textfield;

	private Label label2;

	private Label title;

	private Label label4;

	private Label label3;

	/**
	 * Creates a new <code>RegisterScreen</code>.
	 */
	public RegisterScreen() {
		super();

		// Add design-time configured components.
		initComponents();
		ResourceBundleMessageSource msgres = (ResourceBundleMessageSource) Application
				.getSpringContext().getBean("commonMessageResource");
		title.setText(msgres.getMessage("reg.title", null, ApplicationInstance
				.getActive().getLocale()));
		label2.setText(msgres.getMessage("reg.sn", null, ApplicationInstance
				.getActive().getLocale()));
		label3.setText(msgres.getMessage("reg.username", null, ApplicationInstance
				.getActive().getLocale()));
		label4.setText(msgres.getMessage("reg.email", null, ApplicationInstance
				.getActive().getLocale()));
		LicenseManager licManager = (LicenseManager) Application
				.getSpringContext().getBean("license");
		RegInfo info = licManager.getInfo();
		sn_textfield.setText(info.getSerialNumber());
		username_textfield.setText(info.getUsername());
		mail_textfield.setText(info.getMail());
	}

	private void doRegister(ActionEvent e) {
		LicenseManager licManager = (LicenseManager) Application
				.getSpringContext().getBean("license");
		try {
			SerialNumber sn = new SerialNumber(sn_textfield.getText());
		} catch (Exception e1) {
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK,
					"無效的序號");
		}

		try {
			boolean success = licManager.register(sn_textfield.getText(),
					username_textfield.getText(), mail_textfield.getText());
			if (success) {
				Application.getApp().showLoginScreen();
			} else {
				Application.getApp().showMessageDialog(
						MessageDialog.CONTROLS_OK, "註冊失敗");
			}
		} catch (LicenseException e1) {
			e1.printStackTrace();
			Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK,
					"註冊失敗");
		}
		// Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK,
		// "註冊成功");

	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		WindowPane windowPane1 = new WindowPane();
		windowPane1.setWidth(new Extent(350, Extent.PX));
		windowPane1.setHeight(new Extent(210, Extent.PX));
		windowPane1.setStyleName("Default.Window");
		add(windowPane1);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setSeparatorPosition(new Extent(140, Extent.PX));
		windowPane1.add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100, Extent.PERCENT));
		splitPane1.add(grid1);
		title = new Label();
		title.setText("REG");
		title.setForeground(Color.RED);
		GridLayoutData titleLayoutData = new GridLayoutData();
		titleLayoutData.setColumnSpan(2);
		title.setLayoutData(titleLayoutData);
		title.setFont(new Font(null, Font.BOLD, new Extent(14, Extent.PT)));
		grid1.add(title);
		label2 = new Label();
		label2.setText("label2");
		grid1.add(label2);
		sn_textfield = new TextField();
		grid1.add(sn_textfield);
		label3 = new Label();
		label3.setText("label3");
		grid1.add(label3);
		username_textfield = new TextField();
		grid1.add(username_textfield);
		label4 = new Label();
		label4.setText("label4");
		grid1.add(label4);
		mail_textfield = new TextField();
		grid1.add(mail_textfield);
		Column column1 = new Column();
		column1
				.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
						Extent.PX), new Extent(10, Extent.PX), new Extent(0,
						Extent.PX)));
		splitPane1.add(column1);
		Button button1 = new Button();
		button1.setText("OK");
		button1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.CENTER));
		button1.setForeground(Color.BLUE);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doRegister(e);
			}
		});
		column1.add(button1);
	}
}
