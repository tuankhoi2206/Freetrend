package fv.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.program.DefaultProgram;
import dsc.util.function.UUID;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;


public abstract class SimpleReportProgram extends DefaultProgram {
  /**
   * 
   */
  private static final long serialVersionUID = 786877800155877684L;
  private SplitPane splitPane;
  private ReportToolbar toolbar;
  private String reportFileName;

  public SimpleReportProgram() {
    super();
    initComponents();
  }

  public SplitPane getMainSplitPane() {
    return splitPane;
  }

  public ReportToolbar getToolbar() {
    return toolbar;
  }

  @Override
  protected int doInit() {
    int ret = super.doInit();
    splitPane = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
    splitPane.setSeparatorPosition(new Extent(38));
    toolbar = new ReportToolbar();
    toolbar.addActionListener(new ActionListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        doToolbarActionPerformed(e);
      }
    });
    splitPane.add(toolbar);
    return ret;
  }

  @Override
  protected void doLayout() {
    super.doLayout();
    windowPane.removeAll();
    windowPane.add(splitPane);
    splitPane.add(this);
  }

  private void initComponents() {

  }

  private void doExportExcel(HSSFWorkbook wb) throws IOException {
    if (wb == null) {
      Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, "Không có dữ liệu.");
      return;
    }
    File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
    f.deleteOnExit();
    FileOutputStream out = new FileOutputStream(f);
    wb.write(out);
    out.flush();
    out.close();
    File saveFile =
        new File(f.getParentFile(), URLEncoder.encode(getLoginInfo().getUserID() + ";application/vnd.ms-excel;"
            + reportFileName + "_" + Calendar.getInstance().getTimeInMillis() + ".xls", "UTF-8"));
    ReportFileManager.saveTempReportFile(f, saveFile);
    saveFile.deleteOnExit();
    Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
  }

  public void doExportPdf(JasperPrint jp) {
    if (jp == null) {
      Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK, "Không có dữ liệu.");
      return;
    }
    File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
    f.deleteOnExit();
    try {
      JasperExportManager.exportReportToPdfFile(jp, f.getPath());
      File saveFile = new File(f.getParentFile(), URLEncoder.encode(getLoginInfo().getUserID() + ";application/pdf;"
          + reportFileName + "_" + Calendar.getInstance().getTimeInMillis() + ".pdf", "UTF-8"));
      ReportFileManager.saveTempReportFile(f, saveFile);
      saveFile.deleteOnExit();
      Application.getApp().enqueueCommand(new BrowserRedirectCommand(getViewerUrl() + saveFile.getName()));
    } catch (JRException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  protected void doToolbarActionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(ReportToolbar.CMD_SEARH)) {
      doSearch();
    } else if (e.getActionCommand().equals(ReportToolbar.CMD_REFRESH)) {
      doRefresh();
    } else if (e.getActionCommand().equals(ReportToolbar.CMD_EXCEL)) {

      if (!validateUI()) {
        Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR, getErrorMessage());
        return;
      }

      try {
        doExportExcel(generateWorkbook());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getActionCommand().equals(ReportToolbar.CMD_PDF)) {
      if (!validateUI()) {
        Application.getApp().showMessageDialog(MessageDialog.CONTROLS_OK + MessageDialog.TYPE_ERROR, getErrorMessage());
        return;
      }
      try {
        doExportPdf(generateJasperPrint());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getActionCommand().equals(ReportToolbar.CMD_RESET)) {
      doReset();
    }
  }

  protected void doReset() {
  }

  protected abstract HSSFWorkbook generateWorkbook() throws IOException;

  protected JasperPrint generateJasperPrint() throws IOException {
    return null;
  }

  protected abstract void doRefresh();

  protected abstract void doSearch();

  public String getViewerUrl() {
    HttpServletRequest request = WebRenderServlet.getActiveConnection().getRequest();
    String viewerUrl =
        request.getRequestURL() + "?" + WebRenderServlet.SERVICE_ID_PARAMETER + "=" + ReportService.INSTANCE.getId()
            + "&" + ReportService.PARAM_TYPE + "=" + ReportService.TYPE_TEMP + "&" + ReportService.PARAM_KEY + "=";
    return viewerUrl;
  }

  public String getReportFileName() {
    return reportFileName;
  }

  public void setReportFileName(String reportFileName) {
    this.reportFileName = reportFileName;
  }

  public boolean validateUI() {
    return true;
  }
}
