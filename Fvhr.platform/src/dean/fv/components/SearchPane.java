package fv.components;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;
import nextapp.echo2.app.layout.SplitPaneLayoutData;

/**
 * <code>QueryPane</code>
 * 
 * @author Hieu
 * 
 */
public abstract class SearchPane extends WindowPane implements
		WindowPaneListener {

	private static final long serialVersionUID = 1L;

	private SplitPane splitPane;

	private Grid rootLayout;

	private Row row;

	private Button btnSearch;

	private Button btnCancel;

	public SearchPane() {
		super();
		initComponents();
		initUI();
	}

	protected abstract void doSearch();

	protected void initUI() {
		setModal(true);
		setTitle("Search");
		setStyleName("Default.Window");
		btnSearch.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 6721371097393321025L;

			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch();
				userClose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			private static final long serialVersionUID = -3717634648921595383L;

			@Override
			public void actionPerformed(ActionEvent e) {
				userClose();
			}
		});
	}

	public Grid getRootLayout() {
		return rootLayout;
	}

	private void initComponents() {

		splitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		splitPane.setSeparatorPosition(new Extent(32));
		add(splitPane);
		row = new Row();
		row.setInsets(new Insets(2, 3, 2, 2));
		row.setCellSpacing(new Extent(2));
		row.setAlignment(new Alignment(Alignment.RIGHT, Alignment.CENTER));
		FillImage rowFill = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/bar.gif"));
		SplitPaneLayoutData rowLayout = new SplitPaneLayoutData();
		rowLayout.setBackgroundImage(rowFill);
		row.setLayoutData(rowLayout);
		splitPane.add(row);
		rootLayout = new Grid(2);
		splitPane.add(rootLayout);
		btnSearch = new Button();
		btnSearch.setWidth(new Extent(53));
		btnSearch.setHeight(new Extent(26));
		btnSearch.setRolloverEnabled(true);
		btnSearch.setPressedEnabled(true);
		FillImage fill = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_ok.gif"));
		FillImage fill_hover = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_ok_hover.gif"));
		FillImage fill_press = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_ok_press.gif"));
		btnSearch.setBackgroundImage(fill);
		btnSearch.setRolloverBackgroundImage(fill_hover);
		btnSearch.setPressedBackgroundImage(fill_press);
		row.add(btnSearch);
		btnCancel = new Button();
		btnCancel.setWidth(new Extent(59));
		btnCancel.setHeight(new Extent(26));
		btnCancel.setRolloverEnabled(true);
		btnCancel.setPressedEnabled(true);
		fill = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_cancel.gif"));
		fill_hover = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_cancel_hover.gif"));
		fill_press = new FillImage(new ResourceImageReference(
				"/dsc/echo2app/resource/image/search_cancel_press.gif"));
		btnCancel.setBackgroundImage(fill);
		btnCancel.setRolloverBackgroundImage(fill_hover);
		btnCancel.setPressedBackgroundImage(fill_press);
		row.add(btnCancel);
	}

	@Override
	public void windowPaneClosing(WindowPaneEvent e) {
	}

}
