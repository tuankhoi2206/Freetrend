/**
 * Project:		éš†å…¸Webå¹³å�°é–‹ç™¼å°ˆæ¡ˆ
 * Filename: 	Application
 * Author: 		Wilson
 * Create Date:	2005/11/08
 * Version: 	0.90
 * Description:	ç³»çµ±ä¸»ç•«é�¢
 */
package ds;

//do hoi quaaaaaaaaaaaaaaa
import java.util.ResourceBundle;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webrender.ClientConfiguration;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.info.LoginInfo;
import dsc.echo2app.info.ModuleInfo;
import dsc.echo2app.info.ProgramInfo;
import dsc.echo2app.menu.ImageMenu;
import dsc.echo2app.menu.TreeMenu;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.ProgramManager;
import echopointng.Tree;
import echopointng.tree.DefaultMutableTreeNode;
import echopointng.tree.DefaultTreeCellRenderer;
import echopointng.tree.TreeIcons;
import echopointng.tree.TreeModel;
import echopointng.tree.TreePath;
import echopointng.tree.TreeSelectionEvent;
import echopointng.tree.TreeSelectionListener;
import fv.util.BundleUtils;

public class MainScreen extends ContentPane {
	private static final long serialVersionUID = 1L;

	private static final String TREENUMUFILE = "/conf/menu/TreeMenu.xml";

	private ResourceBundle resourceBundle;

	private ImageMenu imageMenu;

	private TreeMenu treeMenu;

	private ProgramManager programMgr;

	private ContentPane ctpProgramList;

	private ContentPane ctpImageMap;

	private SelectField selRunning;

	/**
	 * Creates a new <code>MainScreen</code>.
	 */
	public MainScreen() {
		super();

		// Add design-time configured components.
		initComponents();

		// Banner info
		// HttpServletRequest request =
		// WebRenderServlet.getActiveConnection().getRequest();
		// lblServerValue.setText(request.getServerName());
		Application app = (Application) ApplicationInstance.getActive();
		LoginInfo loginInfo = app.getLoginInfo();

		// lblClientIPValue.setText(loginInfo.getClientIP());
		// lblLoginTimeValue.setText(
		// DateFormat.getInstance().format(loginInfo.getLoginDate())
		// );
		// lblUserValue.setText(loginInfo.getUserName());
		// lblCompanyValue.setText(loginInfo.getCompanyName());
		// lblDepartmentValue.setText(loginInfo.getDepartmentName());
		Application
				.getApp()
				.getDefaultWindow()
				.setTitle(
						loginInfo.getCompanyName() + " "
								+ loginInfo.getDepartmentName() + " "
								+ loginInfo.getUserName() + " ["
								+ loginInfo.getClientIP() + "]");

		// Banner Menu

		// tree programlist
		treeMenu = new TreeMenu();

		TreeMenu.setTreeModuleIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/treemenuModule.gif"));
		TreeMenu.setTreeProgramIcon(new ResourceImageReference(
				"/dsc/echo2app/resource/image/treemenuProgram.gif"));

		treeMenu.readMenuFile(TREENUMUFILE);
		treeMenu.setActionCommand("executeprogram");
		treeMenu.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent evt) {
				treeProgramListValueChange(evt);
			}
		});

		ctpProgramList.add(treeMenu);

		// ImageMap
		imageMenu = new ImageMenu();
		imageMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionImageMapCommand(e);
			}
		});

		ctpImageMap.add(imageMenu);

		programMgr = new ProgramManager();

		programMgr.addChangListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				programManagerChange();
			}
		});

		treeMenu.setSelectionPath(new TreePath(treeMenu.getModel().getChild(
				treeMenu.getModel().getRoot(), 0)));

		ContainerContext cc = (ContainerContext) Application.getApp()
				.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		ClientConfiguration config = new ClientConfiguration();
		config.setProperty(ClientConfiguration.PROPERTY_SERVER_ERROR_MESSAGE,
				BundleUtils.getString("MainScreen.ServerError"));
		config.setProperty(
				ClientConfiguration.PROPERTY_SESSION_EXPIRATION_MESSAGE,
				BundleUtils.getString("MainScreen.ServerExpired"));
		cc.setClientConfiguration(config);
	}

	public void treeProgramListValueChange(TreeSelectionEvent evt) {
		TreeMenu treeMenu = (TreeMenu) evt.getSource();
		TreePath treePath = treeMenu.getSelectionPath();
		if (treePath == null) {
			System.out.println("execprogram treepath=null");
			return;
		}

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath
				.getLastPathComponent();

		Object o = treeNode.getUserObject();
		
		if (o != null) {
			if (o instanceof ProgramInfo) {
				ProgramInfo item = (ProgramInfo) o;
				this.doExecuteProgram(item);
			} else if (o instanceof ModuleInfo) {
				ModuleInfo mi = (ModuleInfo) o;
				if (!mi.getImageMap().equals("")) {
					imageMenu.readMenuFile("/conf/menu/" + mi.getImageMap());
				} else {
				}
			}
			treeMenu.getSelectionModel().clearSelection();
		} else
			System.out.println("execprogram treenode userobject=null");
	}

	private void actionImageMapCommand(ActionEvent e) {
		// TODO Implement.
		ProgramInfo item = Application.getApp().getLoginInfo()
				.findProgramInfo(e.getActionCommand());
		if (item != null)
			this.doExecuteProgram(item);

	}

	/**
	 * åŸ·è¡Œç¨‹å¼�é …ç›®
	 * 
	 * @param e
	 * @return
	 */
	public int doExecuteProgram(ProgramInfo value) {
		IProgram prog = programMgr.executeProgram(value.getId());
		if (prog == null) {
			MessageDialog msgDialog = new MessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					programMgr.getErrorMessage());
			msgDialog.show();
		} else {
			refreshRunningProgram();
		}
		return 0;
	}

	public void doLogout() {
		// Close all program
		while (programMgr.getProgramCount() > 0) {
			IProgram p = programMgr.getProgram(0);
			p.close();
		}
		// ...

		// exit
		Application.getApp().processLogout();
		/*
		 * BackContent bc = new BackContent();
		 * Application.getApp().getDefaultWindow().setContent(bc);
		 * bc.setMessage(resourceBundle.getString("Generic.MSG.ExitSystem"));
		 * bc.setMessageVisible(true);
		 * 
		 * LoginWindow loginWin = new LoginWindow(); if (loginWin.doInit()) {
		 * bc.add(loginWin); }
		 */
	}// doLogout

	public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Label getTreeCellRendererText(Tree tree, Object node,
				boolean sel, boolean expanded, boolean leaf) {
			String stringValue = "";
			if (node instanceof DefaultMutableTreeNode) {
				Object value = ((DefaultMutableTreeNode) node).getUserObject();
				if ((value != null) && (value instanceof ProgramInfo))
					stringValue = ((ProgramInfo) value).getCaption();
				else
					stringValue = node.toString();
			} else {
				if (node != null)
					stringValue = node.toString();
			}

			// text
			setText(stringValue);

			// fonts and colors
			if (sel) {
				super.setForeground(getSelectedForeground());
				super.setBackground(getSelectedBackground());
			} else {
				super.setForeground(tree.getForeground());
				super.setBackground(tree.getBackground());
			}

			// icons
			boolean isRoot = false;
			TreeModel model = tree.getModel();
			if (model != null)
				isRoot = (node == model.getRoot());

			setIcon(null);
			TreeIcons icons = tree.getTreeIcons();
			if (icons != null) {
				if (isRoot && expanded) {
					setIcon(icons.getIcon(TreeIcons.ICON_ROOTOPEN));
				} else if (isRoot) {
					setIcon(icons.getIcon(TreeIcons.ICON_ROOT));
				} else if (leaf) {
					setIcon(icons.getIcon(TreeIcons.ICON_LEAF));
				} else if (expanded) {
					setIcon(icons.getIcon(TreeIcons.ICON_FOLDEROPEN));
				} else {
					setIcon(icons.getIcon(TreeIcons.ICON_FOLDER));
				}
			}

			// enabledness
			// setEnabled(tree.isEnabled());
			return this;
		}
	}

	private void actionLogoout(ActionEvent e) {
		// TODO Implement.
		MessageDialog msgDialog = new MessageDialog(MessageDialog.TYPE_WARNING
				+ MessageDialog.CONTROLS_YES_NO,
				resourceBundle.getString("MainScreen.MSG_SureLogout"));
		msgDialog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MessageDialog.COMMAND_OK.equals(e.getActionCommand())) {
					doLogout();
				}
			}
		});
	}// actionLogoout

	private void refreshRunningProgram() {
		DefaultListModel listModel = new DefaultListModel();
		listModel.add("");
		for (int i = 0; i < programMgr.getProgramCount(); i++) {
			IProgram p = programMgr.getProgram(i);
			listModel.add(p.getProgramName());
		}
		selRunning.setSelectedIndex(0);
		selRunning.setModel(listModel);
	}

	public void programManagerChange() {
		refreshRunningProgram();
	}

	private void runningSelectChange(ActionEvent e) {
		int n = selRunning.getSelectedIndex();
		if (n > 0) {
			IProgram p = programMgr.getProgram(n - 1);
			p.showIt(IProgram.SHOW_SHOW);
		}
		selRunning.setSelectedIndex(0);
	}

	private void actionTaskSchedule(ActionEvent e) {
		// add(new TaskSchedule().getWindowPane());

		IProgram prog = programMgr.executeProgram("TaskSchedule",
				dsc.echo2app.schedule.TaskSchedule.class, Application.getApp()
						.getLoginInfo(), null);
		if (prog == null) {
			MessageDialog msgDialog = new MessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					programMgr.getErrorMessage());
			msgDialog.show();
		} else {
			refreshRunningProgram();
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
		SplitPane spUp = new SplitPane();
		spUp.setSeparatorHeight(new Extent(1, Extent.PX));
		spUp.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		spUp.setSeparatorPosition(new Extent(60, Extent.PX));
		spUp.setSeparatorColor(new Color(0x808080));
		add(spUp);
		ContentPane contentPane2 = new ContentPane();
		SplitPaneLayoutData contentPane2LayoutData = new SplitPaneLayoutData();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/banner.gif");
		contentPane2LayoutData
				.setBackgroundImage(new FillImage(imageReference1));
		contentPane2.setLayoutData(contentPane2LayoutData);
		spUp.add(contentPane2);
		Column column1 = new Column();
		column1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		contentPane2.add(column1);
		Row row1 = new Row();
		ColumnLayoutData row1LayoutData = new ColumnLayoutData();
		row1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.BOTTOM));
		row1.setLayoutData(row1LayoutData);
		column1.add(row1);
		Button button1 = new Button();
		button1.setText(resourceBundle.getString("MainScreen.CTL_TaskSchedule"));
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/schedule.gif");
		button1.setIcon(imageReference2);
		button1.setStyleName("Default.Button");
		RowLayoutData button1LayoutData = new RowLayoutData();
		button1LayoutData.setWidth(new Extent(80, Extent.PX));
		button1LayoutData.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		button1.setLayoutData(button1LayoutData);
		button1.setFont(new Font(null, Font.PLAIN, new Extent(11, Extent.PX)));
		button1.addActionListener(new ActionListener() {
			/**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
			public void actionPerformed(ActionEvent e) {
				actionTaskSchedule(e);
			}
		});
		row1.add(button1);
		Button btnLogout = new Button();
		btnLogout.setWidth(new Extent(200, Extent.PX));
		btnLogout.setText(resourceBundle.getString("MainScreen.CTL_Logout"));
		btnLogout.setActionCommand("logout");
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/logout.gif");
		btnLogout.setIcon(imageReference3);
		btnLogout
				.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		btnLogout.setStyleName("Default.Button");
		RowLayoutData btnLogoutLayoutData = new RowLayoutData();
		btnLogoutLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(20, Extent.PX),
				new Extent(0, Extent.PX)));
		btnLogoutLayoutData.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		btnLogout.setLayoutData(btnLogoutLayoutData);
		btnLogout
				.setFont(new Font(null, Font.PLAIN, new Extent(11, Extent.PX)));
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionLogoout(e);
			}
		});
		row1.add(btnLogout);
		Row row3 = new Row();
		ColumnLayoutData row3LayoutData = new ColumnLayoutData();
		row3LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.BOTTOM));
		row3.setLayoutData(row3LayoutData);
		column1.add(row3);
		Label label1 = new Label();
		label1.setText(resourceBundle
				.getString("MainScreen.CTL_RunningProgram"));
		RowLayoutData label1LayoutData = new RowLayoutData();
		label1LayoutData.setWidth(new Extent(80, Extent.PX));
		label1LayoutData.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		label1.setLayoutData(label1LayoutData);
		label1.setFont(new Font(null, Font.PLAIN, new Extent(11, Extent.PX)));
		row3.add(label1);
		selRunning = new SelectField();
		RowLayoutData selRunningLayoutData = new RowLayoutData();
		selRunningLayoutData.setWidth(new Extent(170, Extent.PX));
		selRunningLayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(50, Extent.PX),
				new Extent(0, Extent.PX)));
		selRunningLayoutData.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		selRunning.setLayoutData(selRunningLayoutData);
		selRunning
				.setFont(new Font(null, Font.PLAIN, new Extent(11, Extent.PX)));
		selRunning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runningSelectChange(e);
			}
		});
		row3.add(selRunning);
		SplitPane sppCenter = new SplitPane();
		sppCenter.setSeparatorWidth(new Extent(0, Extent.PX));
		sppCenter.setResizable(true);
		sppCenter.setSeparatorPosition(new Extent(280, Extent.PX));
		spUp.add(sppCenter);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setSeparatorPosition(new Extent(24, Extent.PX));
		sppCenter.add(splitPane1);
		Row row2 = new Row();
		SplitPaneLayoutData row2LayoutData = new SplitPaneLayoutData();
		row2LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/label.gif");
		row2LayoutData.setBackgroundImage(new FillImage(imageReference4));
		row2.setLayoutData(row2LayoutData);
		splitPane1.add(row2);
		Label lblProgramList = new Label();
		lblProgramList.setText(resourceBundle
				.getString("MainScreen.CTL_ProgramList"));
		RowLayoutData lblProgramListLayoutData = new RowLayoutData();
		lblProgramListLayoutData.setWidth(new Extent(100, Extent.PERCENT));
		lblProgramListLayoutData
				.setInsets(new Insets(new Extent(2, Extent.PX)));
		lblProgramListLayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		lblProgramList.setLayoutData(lblProgramListLayoutData);
		row2.add(lblProgramList);
		ctpProgramList = new ContentPane();
		SplitPaneLayoutData ctpProgramListLayoutData = new SplitPaneLayoutData();
		ctpProgramList.setLayoutData(ctpProgramListLayoutData);
		splitPane1.add(ctpProgramList);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorPosition(new Extent(24, Extent.PX));
		sppCenter.add(splitPane2);
		Row row5 = new Row();
		SplitPaneLayoutData row5LayoutData = new SplitPaneLayoutData();
		row5LayoutData.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.BOTTOM));
		row5LayoutData.setBackgroundImage(new FillImage(imageReference4));
		row5.setLayoutData(row5LayoutData);
		splitPane2.add(row5);
		ctpImageMap = new ContentPane();
		ctpImageMap.setBackground(Color.WHITE);
		splitPane2.add(ctpImageMap);

	}
}
