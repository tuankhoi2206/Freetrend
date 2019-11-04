package ds.program.fvhr.ui.quitworksalary;

import java.util.ResourceBundle;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import ds.program.fvhr.domain.ATTENDANTDB_QUIT;
import dsc.echo2app.component.DscField;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class AttQuitAppendData2MonthPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private SelectField sfMonth1;
	private SelectField sfMonth2;
	private Button btnCong;
	private DscField txtEmpsn;
	private AttQuit01Program program;

	/**
	 * Creates a new <code>AttQuitAppendData2MonthPane</code>.
	 */
	public AttQuitAppendData2MonthPane() {
		super();

		// Add design-time configured components.
		initComponents();
		
	}
	
	public AttQuitAppendData2MonthPane(AttQuit01Program program){
		this();
		this.program=program;
		ListBinder.bindSelectField(sfMonth1, MappingPropertyUtils.getStringMonthEditor(), true);
		ListBinder.bindSelectField(sfMonth2, MappingPropertyUtils.getStringMonthEditor(), true);
		ListBinder.refreshIndex(sfMonth1, program.getMasterDataContent().getSelectedMonth());
		if (sfMonth1.getSelectedIndex()<11){
			sfMonth2.setSelectedIndex(sfMonth1.getSelectedIndex()+1);
		}else{
			sfMonth2.setSelectedIndex(sfMonth1.getSelectedIndex());
		}
		ATTENDANTDB_QUIT att = (ATTENDANTDB_QUIT) program.getMasterDataContent().getDataObject();
		txtEmpsn.setText(att.getEMPSN());
	}

	private void doAdd(ActionEvent e) {
		ATTENDANTDB_QUIT att = (ATTENDANTDB_QUIT) program.getMasterDataContent().getDataObject();
		String empsn = att.getEMPSN();
		String month=program.getMasterDataContent().getSelectedMonth();
		String year=program.getMasterDataContent().getSelectedYear();
		program.getDao().congNgayCong(empsn, 
				sfMonth1.getSelectedItem().toString(), sfMonth2.getSelectedItem().toString(),year, month,att.getDOT_TV());
		program.getDao().doCalculate(empsn, att.getDOT_TV(), month, year, att.getCLASS());
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setTitle("Cộng ngày công");
		Grid grid1 = new Grid();
		add(grid1);
		Label label1 = new Label();
		label1.setText("Số thẻ");
		grid1.add(label1);
		txtEmpsn = new DscField();
		txtEmpsn.setEnabled(false);
		grid1.add(txtEmpsn);
		Label label2 = new Label();
		label2.setText("Tháng 1");
		grid1.add(label2);
		sfMonth1 = new SelectField();
		grid1.add(sfMonth1);
		Label label3 = new Label();
		label3.setText("Tháng 2");
		grid1.add(label3);
		sfMonth2 = new SelectField();
		grid1.add(sfMonth2);
		Label label4 = new Label();
		grid1.add(label4);
		btnCong = new Button();
		btnCong
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnCong.setText("Cộng");
		btnCong.setWidth(new Extent(80, Extent.PX));
		btnCong.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnCong.setBackground(new Color(0xc0c0c0));
		btnCong.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004080), Border.STYLE_SOLID));
		btnCong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doAdd(e);
			}
		});
		grid1.add(btnCong);
	}
}
