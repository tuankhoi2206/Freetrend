package ds;

import java.util.ResourceBundle;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.WindowPane;

import org.springframework.context.support.ResourceBundleMessageSource;

import dsc.echo2app.Application;

public class ExpirationScreen extends ContentPane {

	private ResourceBundle resourceBundle;

	private Label label1;

	/**
	 * Creates a new <code>ExpirationScreen</code>.
	 */
	public ExpirationScreen() {
		super();

		// Add design-time configured components.
		initComponents();
		ResourceBundleMessageSource msgres = (ResourceBundleMessageSource) Application
				.getSpringContext().getBean("commonMessageResource");
		label1.setText(msgres.getMessage("reg.expiration", null, ApplicationInstance
				.getActive().getLocale()));
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		WindowPane windowPane1 = new WindowPane();
		add(windowPane1);
		label1 = new Label();
		label1.setText("抱歉，伺服器過期，請連絡您的資訊管理人員(MIS)");
		label1.setForeground(Color.RED);
		label1.setFont(new Font(null, Font.BOLD, new Extent(20, Extent.PT)));
		windowPane1.add(label1);
	}
}
