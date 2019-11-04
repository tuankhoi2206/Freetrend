package fv.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class ReportToolbar extends Row implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2410278976555609491L;

	public static final String CMD_SEARH = "search";

	public static final String CMD_REFRESH = "refresh";

	public static final String CMD_CONFIG = "config";

	public static final String CMD_EXCEL = "excel";

	public static final String CMD_PDF = "pdf";
	
	public static final String CMD_RESET = "reset";

	private Button btnSearch;

	private Button btnRefresh;

	private Button btnExcel;

	private Button btnPdf;

	private ResourceBundle bundle;

	private List<ActionListener> list;

	private Button btnConfig;
	
	private Button btnReset;

	public ReportToolbar() {
		super();
		initComponents();
		btnSearch.setActionCommand(CMD_SEARH);
		btnSearch.addActionListener(this);
		btnRefresh.setActionCommand(CMD_REFRESH);
		btnRefresh.addActionListener(this);
		btnConfig.setActionCommand(CMD_CONFIG);
		btnConfig.addActionListener(this);
		btnExcel.setActionCommand(CMD_EXCEL);
		btnExcel.addActionListener(this);
		btnPdf.setActionCommand(CMD_PDF);
		btnPdf.addActionListener(this);
		btnReset.setActionCommand(CMD_RESET);
		btnReset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list != null) {
			for (ActionListener action : list) {
				action.actionPerformed(e);
			}
		}
	}

	public void addActionListener(ActionListener e) {
		if (list == null)
			list = new ArrayList<ActionListener>();
		list.add(e);
	}

	public void setSearchButtonEnable(boolean enable) {
		btnSearch.setEnabled(enable);
	}

	public void setRefreshButtonEnable(boolean enable) {
		btnRefresh.setEnabled(false);
	}

	public void setConfigButtonEnable(boolean enable) {
		btnConfig.setEnabled(false);
	}

	public void setExcelButtonEnable(boolean enable) {
		btnExcel.setEnabled(enable);
	}

	public void setPdfButtonEnable(boolean enable) {
		btnPdf.setEnabled(enable);
	}
	
	public void setResetButtonEnable(boolean enable){
		btnReset.setEnabled(enable);
	}

	private void initComponents() {
		bundle = ResourceBundle.getBundle("resource.localization.UICaption",
				ApplicationInstance.getActive().getLocale());
		setStyleName("Default.Toolbar");
		btnSearch = new Button();
		btnSearch.setStyleName("Default.ToolbarButton");
		ResourceImageReference searchIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnQuery.gif");
		btnSearch.setIcon(searchIcon);
		ResourceImageReference searchIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnQueryD.gif");
		btnSearch.setDisabledIcon(searchIconD);
		btnSearch.setToolTipText(bundle.getString("ReportToolbar.Search"));
		add(btnSearch);
		btnRefresh = new Button();
		btnRefresh.setStyleName("Default.ToolbarButton");
		ResourceImageReference refreshIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnRefresh.gif");
		btnRefresh.setIcon(refreshIcon);
		ResourceImageReference refreshIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnRefreshD.gif");
		btnRefresh.setDisabledIcon(refreshIconD);
		btnRefresh.setToolTipText(bundle.getString("ReportToolbar.Refresh"));

		add(btnRefresh);
		btnConfig = new Button();
		btnConfig.setStyleName("Default.ToolbarButton");
		ResourceImageReference configIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnBatch1.png");
		btnConfig.setIcon(configIcon);
		ResourceImageReference configIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnBatch1D.png");
		btnConfig.setDisabledIcon(configIconD);
		btnConfig.setToolTipText(bundle.getString("ReportToolbar.Preferences"));

		add(btnConfig);
		btnExcel = new Button();
		btnExcel.setStyleName("Default.ToolbarButton");
		ResourceImageReference excelIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif");
		btnExcel.setIcon(excelIcon);
		ResourceImageReference excelIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcelD.gif");
		btnExcel.setDisabledIcon(excelIconD);
		btnExcel.setToolTipText(bundle.getString("ReportToolbar.ExportExcel"));

		add(btnExcel);
		btnPdf = new Button();
		btnPdf.setStyleName("Default.ToolbarButton");
		ResourceImageReference pdfIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnPDF.gif");
		btnPdf.setIcon(pdfIcon);
		ResourceImageReference pdfIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnPDFD.gif");
		btnPdf.setDisabledIcon(pdfIconD);
		btnPdf.setToolTipText(bundle.getString("ReportToolbar.ExportPdf"));

		add(btnPdf);
		
		btnReset = new Button();
		btnReset.setStyleName("Default.ToolbarButton");
		ResourceImageReference resetIcon = new ResourceImageReference(
				"/dsc/echo2app/resource/image/lightning.png");
		btnReset.setIcon(resetIcon);
		ResourceImageReference resetIconD = new ResourceImageReference(
				"/dsc/echo2app/resource/image/lightningD.gif");
		btnReset.setDisabledIcon(resetIconD);
		btnReset.setToolTipText(bundle.getString("ReportToolbar.Reset"));

		add(btnReset);
	}
}
