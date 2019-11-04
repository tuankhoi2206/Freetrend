package fv.components;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MasterToolbar;

/**
 * A copy of <code>MaintainSProgram</code> with simple UI
 * 
 * @author Hieu
 * 
 */
public class PreMaintainSProgram extends DefaultProgram {

	private static final long serialVersionUID = 8010825622232468177L;

	private SplitPane mainSplitPane;

	private MasterToolbar masterToolbar;

	public PreMaintainSProgram() {
		super();
	}
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		mainSplitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		mainSplitPane.setSeparatorPosition(new Extent(36));
		masterToolbar = new MasterToolbar(this);
		masterToolbar.setbtnConfirmVisible(false);
		masterToolbar.setbtnCancelConfirmVisible(false);
		masterToolbar.setbtnEmailVisible(false);
		masterToolbar.setbtnExportVisible(false);
		masterToolbar.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				masterToolbarActionPerformed(e);
			}
		});
		masterToolbar.addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
		masterToolbar.addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
		masterToolbar.addUserDefineRight(MasterToolbar.CMD_EDIT, false);
		masterToolbar.addUserDefineRight(MasterToolbar.CMD_DELETE, false);
		masterToolbar.refresh();
		return ret;
	}
	
	@Override
	protected void doLayout() {
		if(logger.isDebugEnabled()) {
			logger.debug(this.getClass().getName() + "-doLayout()");
		}
		Window win = Application.getApp().getDefaultWindow();
		if( (win != null) && (win.getContent() != null) ) {
			win.getContent().add(windowPane);
		}
		windowPane.removeAll();		
		windowPane.add(mainSplitPane);
		mainSplitPane.add(masterToolbar);
		mainSplitPane.add(this);
	}

	protected void switchContent(int view) {
		if (view == 0) {// browser
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_CONTENT, true);
			masterToolbar.refresh();
		} else {
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_BROWSER, true);
			masterToolbar.addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
			masterToolbar.refresh();
		}
	}

	protected void masterToolbarActionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(MasterToolbar.CMD_QUERY_NORMAL)) {
			doQueryNormal();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_REFRESH)) {
			doRefresh();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_BROWSER)) {
			doBrowse();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_CONTENT)) {
			doContent();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_NEW)) {
			doNew();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_EDIT)) {
			doEdit();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_DELETE)) {
			doDelete();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_SAVE)) {
			doSave();
		} else if (e.getActionCommand().equals(MasterToolbar.CMD_CANCEL)) {
			doCancel();
		}

	}

	@Override
	protected void doUIRefresh() {
		super.doUIRefresh();
	}

	public MasterToolbar getMasterToolbar() {
		return this.masterToolbar;
	}

	public SplitPane getMainSplitPane() {
		return this.mainSplitPane;
	}

	@Override
	protected boolean doCloseCheck() {
		if (super.doCloseCheck()) {
			if ((this.getDataMode() == IProgram.DATAMODE_EDIT)
					|| (this.getDataMode() == IProgram.DATAMODE_NEW)) {
				MessageDialog msgDialog = new MessageDialog(
						MessageDialog.TYPE_WARNING
								+ MessageDialog.CONTROLS_YES_NO, this
								.getCommMsgRes().getString(
										"Generic.MSG.EditModeCloseConfirm"));
				msgDialog.addActionListener(new ActionListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						if (MessageDialog.COMMAND_OK.equals(e
								.getActionCommand())) {
							PreMaintainSProgram.this.doClose();
							windowPane.getParent().remove(windowPane);
						}
					}
				});
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	protected void doCancel(){		
	}

	protected boolean doSave() {
		return false;
	}

	protected boolean doDelete() {
		return false;
	}

	protected boolean doEdit() {
		return false;
	}

	protected boolean doNew() {
		return false;
	}

	protected void doContent() {
	}

	protected void doBrowse() {
	}

	protected void doRefresh() {
	}

	protected void doQueryNormal() {
	}
}
