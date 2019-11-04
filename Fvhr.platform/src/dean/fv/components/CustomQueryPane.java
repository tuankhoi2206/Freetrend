package fv.components;

import java.util.ResourceBundle;

import dsc.echo2app.Application;
import dsc.echo2app.component.DscWindowPane;
import dsc.echo2app.program.DefaultProgram;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

public class CustomQueryPane extends ContentPane implements WindowPaneListener {

	public static final int RET_OK = 1;
	public static final int RET_FAIL = 0;
	public static final int SHOW_HIDE = 0;
	public static final int SHOW_SHOW = 1;
	public static final int SHOW_MODAL = 2;
	private ResourceBundle resourceBundle;
	private DefaultProgram program;
	protected SplitPane splitPane;
	private Row toolbar;
	protected DscWindowPane windowPane;
	private String errorMessage;
	/**
	 * Creates a new <code>CustomQueryPane</code>.
	 */
	public CustomQueryPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	public void init(DefaultProgram program){
		this.program=program;
		int ret = doInit();
		if (ret == RET_OK){
			doLayout();
		}
	}
	
	protected int doInit(){
		windowPane = new DscWindowPane();
		windowPane.setModal(true);
		windowPane.setStyleName("Default.Window");
		windowPane.addWindowPaneListener(this);
		windowPane.setVisible(false);
		windowPane.setPositionX(new Extent(140));
		windowPane.setPositionY(new Extent(100));
		windowPane.setWidth(new Extent(550));
		windowPane.setHeight(new Extent(350));
		windowPane.setMaximumWidth(new Extent(Application.getApp().getScreenWidth()));
		windowPane.setMaximumHeight(new Extent(Application.getApp().getScreenHeight()));
		Window win = Application.getApp().getDefaultWindow();
		if ((win != null) && (win.getContent() != null)) {
			win.getContent().add(windowPane);
		}
		
		toolbar = new Row();
		toolbar.setStyleName("Default.BottomButtonRow");
		Button queryButton = new Button();
		queryButton.setStyleName("Default.Button");
		queryButton.setText(getProgram().getCommMsgRes().getString("QBE.doQuery"));
		queryButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doQuery();				
			}
		});
		toolbar.add(queryButton);
		return RET_OK;
	}
	
	protected void doQuery() {
	}

	protected void doLayout(){
		splitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		splitPane.setSeparatorPosition(new Extent(32, Extent.PX));
		splitPane.add(toolbar);
		splitPane.add(this);
		windowPane.add(splitPane);
	}
	
	public DefaultProgram getProgram(){
		return this.program;
	}
	
	public int showIt(int value) {
		Window win = Application.getApp().getDefaultWindow();
		if ((win != null) && (win.getContent() != null)) {
			switch (value) {
			case SHOW_HIDE:
				windowPane.setVisible(false);
				break;
			case SHOW_SHOW:
				windowPane.setModal(false);
				windowPane.setVisible(true);
				break;
			case SHOW_MODAL:
				windowPane.setModal(true);
				windowPane.setVisible(true);
				break;
			}
		}
		return 0;
	}
	
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}

	@Override
	public void windowPaneClosing(WindowPaneEvent e) {
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
