package ds.program.users.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.PasswordField;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import dsc.dao.XmlDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;
import fv.util.DbUtils;

public class ChangePassword extends DefaultProgram {
	private static final long serialVersionUID = 7217106600024540905L;

	private ResourceBundle bundle;

	private Grid rootLayout;

	private Label lblUserID;

	private Label lblUserIDValue;

	private Label lblOldPwd;

	private PasswordField txtOldPwd;

	private Label lblNewPwd;

	private PasswordField txtNewPwd;

	private Label lblConfirmPwd;

	private PasswordField txtConfirmPwd;

	private Button btnChangePwd;

	public ChangePassword() {
		initComponents();
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		super.windowPane.setWidth(new Extent(400));
		super.windowPane.setHeight(new Extent(210));
		super.windowPane.setPositionX(new Extent(Application.getApp()
				.getScreenWidth() / 2 - 200));
		super.windowPane.setPositionY(new Extent(100));
		lblUserIDValue.setText(getLoginInfo().getUserID());
		return ret;
	}

	protected void update() {
		String oldPwd = txtOldPwd.getText();
		String newPwd = txtNewPwd.getText();
		String confirmPwd = txtConfirmPwd.getText();
		String userId = getLoginInfo().getUserID();
		XmlDAO dao = XmlDAO.doit(Application.getApp().getConnection(),
				"SYS_CheckLogin", "USER_ID='" + userId + "'");
		if (dao != null) {
			List lst = dao.getResultSet();
			if (lst.size() > 0) {
				ArrayList uf = (ArrayList) lst.get(0);
				if (!oldPwd.equals(uf.get(1))) {
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK
									+ MessageDialog.TYPE_ERROR,
							bundle.getString("USER.WRONGPWD"));
					return;
				}
				if (newPwd.length() < 6) {
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK
									+ MessageDialog.TYPE_ERROR,
							bundle.getString("USER.PWDLENERR"));
					return;
				}
				if (!newPwd.equals(confirmPwd)) {
					Application.getApp().showMessageDialog(
							MessageDialog.CONTROLS_OK
									+ MessageDialog.TYPE_ERROR,
							bundle.getString("USER.CONFIRMERR"));
					return;
				}
				String sql = "UPDATE DSPB02 SET PB_PASS='" + newPwd
						+ "' WHERE PB_USERID='" + userId + "'";
				Connection con = Application.getApp().getConnection();
				PreparedStatement pstm = null;
				try {
					pstm = con.prepareStatement(sql);
					int rec = pstm.executeUpdate();
					if (rec > 0) {
						Application.getApp().showMessageDialog(
								MessageDialog.TYPE_INFORMATION
										+ MessageDialog.CONTROLS_OK,
								bundle.getString("USER.CHANGESUCCESS"));
						this.close();
					} else {
						Application.getApp().showMessageDialog(
								MessageDialog.TYPE_ERROR
										+ MessageDialog.CONTROLS_OK,
								bundle.getString("USER.CHANGEFAIL"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DbUtils.close(pstm);
					DbUtils.close(con);
				}
			}

		}
	}

	private void initComponents() {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption",
				Application.getActive().getLocale());
		rootLayout = new Grid();
		rootLayout.setSize(2);
		add(rootLayout);
		lblUserID = new Label(bundle.getString("USER.USERID"));
		rootLayout.add(lblUserID);
		lblUserIDValue = new Label();
		rootLayout.add(lblUserIDValue);
		lblOldPwd = new Label(bundle.getString("USER.OLDPWD"));
		rootLayout.add(lblOldPwd);
		txtOldPwd = new PasswordField();
		rootLayout.add(txtOldPwd);
		lblNewPwd = new Label(bundle.getString("USER.NEWPWD"));
		rootLayout.add(lblNewPwd);
		txtNewPwd = new PasswordField();
		rootLayout.add(txtNewPwd);
		lblConfirmPwd = new Label(bundle.getString("USER.CONFIRM"));
		rootLayout.add(lblConfirmPwd);
		txtConfirmPwd = new PasswordField();
		rootLayout.add(txtConfirmPwd);
		rootLayout.add(new Label());
		btnChangePwd = new Button(bundle.getString("USER.CHANGEPASSWORD"));
		btnChangePwd.setIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/confirm_24.png"));
		btnChangePwd.setRolloverBorder(new Border(2, Color.LIGHTGRAY,
				Border.STYLE_OUTSET));
		btnChangePwd.setPressedBorder(new Border(2, Color.LIGHTGRAY,
				Border.STYLE_INSET));
		btnChangePwd.setRolloverForeground(Color.ORANGE);
		btnChangePwd.setPressedEnabled(true);
		btnChangePwd.setRolloverEnabled(true);
		btnChangePwd.setWidth(new Extent(150));
		btnChangePwd.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				update();
			}

		});
		rootLayout.add(btnChangePwd);
		GridLayoutData lblLayout = new GridLayoutData();
		lblLayout
				.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		lblLayout.setInsets(new Insets(new Extent(5)));
		for (int i = 0; i < rootLayout.getComponentCount(); i++) {
			if (i % 2 == 0) {
				rootLayout.getComponent(i).setLayoutData(lblLayout);
			}
		}
	}

}
