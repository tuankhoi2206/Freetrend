package ds.program.fvhr.baby.ui;

import java.util.ResourceBundle;

import javax.management.Query;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class EMP_ICCARDQuery extends WindowPane {

	private ResourceBundle resourceBundle;
	private Button btnFind;
	private TextField txtIC;
	private TextField txtEmpsn;

	/**
	 * Creates a new <code>EMP_ICCARDQuery</code>.
	 */
	public EMP_ICCARDQuery() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	private void doQuery(ActionEvent e) {
		String empsn = txtEmpsn.getText().trim();
		String empcn = txtIC.getText().trim();
		String cond = "o.USE_STATUS = '1'";
		if(!empsn.isEmpty())
			cond += "and o.EMPSN = '"+empsn+"'";
		if(!empcn.isEmpty())
			cond += "and o.EMPCN = '"+empcn+"'";
		EMP_ICCARDProgram.doQuery(cond);
	}	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnSearch.png");
		this.setIcon(imageReference1);
		this.setTitle("Tìm kiếm");
		this.setHeight(new Extent(200, Extent.PX));
		this.setWidth(new Extent(300, Extent.PX));
		this.setResizable(false);
		this.setModal(true);
		this.setTitleFont(new Font(new Font.Typeface("monospace"), Font.PLAIN,
				new Extent(13, Extent.PT)));
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		grid1.setSize(2);
		add(grid1);
		Label label1 = new Label();
		label1.setText("Số thẻ: ");
		grid1.add(label1);
		txtEmpsn = new TextField();
		grid1.add(txtEmpsn);
		Label label2 = new Label();
		label2.setText("Số IC:");
		grid1.add(label2);
		txtIC = new TextField();
		grid1.add(txtIC);
		btnFind = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/vu/btnFind.png");
		btnFind.setIcon(imageReference2);
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doQuery(e);
			}
		});
		grid1.add(btnFind);
	}
}
