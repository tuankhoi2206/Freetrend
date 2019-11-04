package ds;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.binder.SelectBinder;
import dsc.echo2app.info.CompanyInfo;
import dsc.echo2app.info.LoginInfo;
import dsc.echo2app.info.SystemInfo;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.util.RightManagement;
import fv.util.RightsHolder;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.FillImageBorder;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.PasswordField;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.webrender.WebRenderServlet;

public class LoginScreen extends ContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceBundle resourceBundle;

	private SelectBinder companyBinder;

	private WindowPane loginForm;

	private TextField txtUserID;

	private PasswordField txtPassword;

	private SelectField selCompany;

	private SelectField selLanguage;

	/**
	 * Creates a new <code>LoginScreen</code>.
	 */
	public LoginScreen() {
		super();

		// Add design-time configured components.
		initComponents();

		// user add
		MappingPropertyEditor editor = createCompanyEditor();
		selCompany.setSelectedIndex(0);
		CompanyInfo companyInfo = new CompanyInfo();
		companyBinder = new SelectBinder(companyInfo, selCompany, editor);
		companyBinder.objectToComponent(companyBinder);

		selLanguage.setModel(createLangModel());
		selLanguage.setSelectedIndex(3);
		CompanyInfo companyInfos[] = null;
		SystemInfo systemInfo = (SystemInfo) Application.getSpringContext()
				.getBean("systemInfo");
		companyInfos = systemInfo.getCompanies().toArray(
				new CompanyInfo[systemInfo.getCompanies().size()]);
		if (companyInfos == null) {
			ResourceBundle msgBundle = ResourceBundle.getBundle(
					"dsc.echo2app.resource.localization.CommonMsg",
					ApplicationInstance.getActive().getLocale());
			Application.getApp().showExitSystemDialog(
					msgBundle.getString("Generic.Error"),
					msgBundle.getString("Generic.MSG.CompanyInfoError"),
					MessageDialog.CONTROLS_OK,
					msgBundle.getString("Generic.MSG.ExitSystem"));
		}
	}
	

	private MappingPropertyEditor createCompanyEditor() {
		SystemInfo sysInfo = (SystemInfo) Application.getSpringContext()
				.getBean("systemInfo");

		MappingPropertyEditor editor = new MappingPropertyEditor();
		for (CompanyInfo each : sysInfo.getCompanies()) {
			editor.put(each.getCompanyName(), each.getCompanyID());
		}
		return editor;
	}

	/*
	 * 從appCtx-hibernate.xml取出支援語系列表 @return
	 */
	private DefaultListModel createLangModel() {
		DefaultListModel model = new DefaultListModel();
		Set<String> langKeys = Application.getApp().getSystemInfo().getLangs()
				.keySet();
		for (String key : langKeys) {
			String value = Application.getApp().getSystemInfo().getLangs().get(
					key);
			model.add(value);
		}
		return model;
	}

	/*
	 * 由所選的語系內容從appCtx-hibernate.xml取出相對應的local的設定值 @return
	 */
	private String getSelectedLang() {
		String language = "";
		String lang = (String) selLanguage.getSelectedItem();
		Map<String, String> langs = Application.getApp().getSystemInfo()
				.getLangs();
		Set<String> keys = langs.keySet();

		for (String key : keys) {
			if (StringUtils.equals(lang, langs.get(key))) {
				language = key;
				break;
			}
		}
		return language;
	}

	private void doLogin(ActionEvent e) {
		companyBinder.componentToObject("companyID");
		CompanyInfo ci = (CompanyInfo) companyBinder.getObject();

		String name;
		String pass;
		
		name = txtUserID.getText().toUpperCase();
		pass = txtPassword.getText();
	
		HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
		String clientIP = request.getRemoteAddr();
		//DA SUA
		/*if (!clientIP.equals("127.0.0.1")){//dev
			 if(name.equals("")){
			 name ="ADMIN";
			 }
		
			 if(pass.equals("")){
			 pass = "0811";
			 }
		}*/

		boolean isLoginOK = Application.getApp().processLogin(name,pass,
		// txtUserID.getText(), txtPassword.getText(),
				ci.getCompanyID(), getSelectedLang());

		if (!isLoginOK) {
			MessageDialog messageDialog = new MessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					Application.getApp().getErrorMessage());
		}else{
			RightsHolder rh = (RightsHolder) Application.getSpringContext().getBean("rightHolder");
			LoginInfo login = Application.getApp().getLoginInfo();			
			rh.update(login.getUserID(), new RightManagement(login));
		}
	}

	/**
	 * Configures initial state of component. WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		resourceBundle = ResourceBundle.getBundle(
				"resource.localization.UICaption", ApplicationInstance
						.getActive().getLocale());
		this.setBackground(new Color(0xeeeee3));
		loginForm = new WindowPane();
		loginForm.setTitleHeight(new Extent(0, Extent.PX));
		loginForm.setHeight(new Extent(356, Extent.PX));
		loginForm.setClosable(false);
		loginForm.setInsets(new Insets(new Extent(100, Extent.PX), new Extent(
				0, Extent.PX)));
		loginForm.setWidth(new Extent(505, Extent.PX));
		loginForm.setPositionY(new Extent(100, Extent.PX));
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/login.gif");
		loginForm.setBackgroundImage(new FillImage(imageReference1, null, null,
				FillImage.NO_REPEAT));
		loginForm.setMovable(false);
		FillImageBorder fillImageBorder1 = new FillImageBorder(null,
				new Insets(new Extent(2, Extent.PX)), new Insets(new Extent(2,
						Extent.PX)));
		loginForm.setBorder(fillImageBorder1);
		loginForm.setModal(true);
		add(loginForm);
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(300, Extent.PX));
		grid1
				.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(10,
						Extent.PX), new Extent(5, Extent.PX), new Extent(10,
						Extent.PX)));
		grid1.setRowHeight(0, new Extent(120, Extent.PX));
		grid1.setSize(2);
		loginForm.add(grid1);
		Label label1 = new Label();
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setColumnSpan(2);
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		Label lblUserID = new Label();
		lblUserID.setText(resourceBundle.getString("LoginWindow.CTL_UserID"));
		grid1.add(lblUserID);
		txtUserID = new TextField();
		txtUserID.setStyleName("Default.TextField");
		txtUserID.setWidth(new Extent(150, Extent.PX));
		grid1.add(txtUserID);
		Label lblPassword = new Label();
		lblPassword.setText(resourceBundle
				.getString("LoginWindow.CTL_Password"));
		grid1.add(lblPassword);
		txtPassword = new PasswordField();
		txtPassword.setStyleName("Default.TextField");
		txtPassword.setWidth(new Extent(150, Extent.PX));
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin(e);
			}
		});
		grid1.add(txtPassword);
		Label lblCompany = new Label();
		lblCompany.setText(resourceBundle.getString("LoginWindow.CTL_Company"));
		grid1.add(lblCompany);
		selCompany = new SelectField();
		selCompany.setWidth(new Extent(150, Extent.PX));
		grid1.add(selCompany);
		Label lblLanguage = new Label();
		lblLanguage.setText(resourceBundle
				.getString("LoginWindow.CTL_Language"));
		grid1.add(lblLanguage);
		selLanguage = new SelectField();
		selLanguage.setWidth(new Extent(150, Extent.PX));
		grid1.add(selLanguage);
		Button btnLogin = new Button();
		btnLogin.setStyleName("Default.Button");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/loginButton.gif");
		btnLogin.setIcon(imageReference2);
		GridLayoutData btnLoginLayoutData = new GridLayoutData();
		btnLoginLayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		btnLoginLayoutData.setColumnSpan(2);
		btnLogin.setLayoutData(btnLoginLayoutData);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin(e);
			}
		});
		grid1.add(btnLogin);
	}
}
