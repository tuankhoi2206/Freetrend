package ds.program.fvhr.ui;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.layout.GridLayoutData;

public class EmployeeCheckPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private DscField txtEmpsn;
	private DscDateField dfNNX;
	private Button btnCheck;
	private Employee01MProgram program;
	private Label lblMessage;

	/**
	 * Creates a new <code>EmployeeCheckPane</code>.
	 */
	public EmployeeCheckPane() {
		super();

		// Add design-time configured components.
		initComponents();
		dfNNX.getDateChooser().setLocale(new Locale("en"));
		dfNNX.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfNNX.setSelectedDate(Calendar.getInstance());
	}
	
	public EmployeeCheckPane(Employee01MProgram program){
		this();
		this.program=program;
	}

	private void doCheck(ActionEvent e) {
		
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Kiểm tra CMND");
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(3, Extent.PX)));
		add(grid1);
		lblMessage = new Label();
		GridLayoutData lblMessageLayoutData = new GridLayoutData();
		lblMessageLayoutData.setColumnSpan(2);
		lblMessage.setLayoutData(lblMessageLayoutData);
		grid1.add(lblMessage);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		grid1.add(label1);
		txtEmpsn = new DscField();
		txtEmpsn.setMaximumLength(8);
		grid1.add(txtEmpsn);
		Label label2 = new Label();
		label2.setText("Ngày nhập xưởng");
		grid1.add(label2);
		dfNNX = new DscDateField();
		grid1.add(dfNNX);
		Label label3 = new Label();
		grid1.add(label3);
		btnCheck = new Button();
		btnCheck
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnCheck.setText("Kiểm tra");
		btnCheck.setBackground(new Color(0x0080c0));
		btnCheck.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnCheck.setWidth(new Extent(80, Extent.PX));
		btnCheck.setPressedEnabled(true);
		btnCheck.setPressedForeground(Color.RED);
		btnCheck.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x008080), Border.STYLE_SOLID));
		btnCheck.setRolloverForeground(new Color(0xff8000));
		btnCheck.setRolloverEnabled(true);
		btnCheck.setForeground(new Color(0xc0c0c0));
		btnCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doCheck(e);
			}
		});
		grid1.add(btnCheck);
	}
}
