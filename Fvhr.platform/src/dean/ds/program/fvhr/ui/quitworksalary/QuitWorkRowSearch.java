package ds.program.fvhr.ui.quitworksalary;

import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import fv.components.SearchPane;
import fv.util.BundleUtils;

public class QuitWorkRowSearch extends SearchPane {

	private static final long serialVersionUID = 1L;

	private Label lblEmpsn;

	private DscField txtEmpsn;

	private QuitWorkSalaryProgram program;

	public QuitWorkRowSearch(QuitWorkSalaryProgram program) {
		super();
		this.program = program;
		initComponents();
		txtEmpsn.requestFocus();
	}

	@Override
	protected void doSearch() {
		if (program.getBrowserContent().getListData() != null
				&& program.getBrowserContent().getListData().size() > 0) {
			program.getBrowserContent().locale("EMPSN", txtEmpsn.getText());
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					"Tải dữ liệu trước khi tìm kiếm");
		}
	}

	private void initComponents() {
		setTitle("Tìm kiếm");
		lblEmpsn = new Label(BundleUtils.getString("ATTQUIT.EMPSN"));
		getRootLayout().add(lblEmpsn);
		txtEmpsn = new DscField();
		txtEmpsn.setMaximumLength(8);
		txtEmpsn.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch();
			}
		});
		getRootLayout().add(txtEmpsn);
	}
}
