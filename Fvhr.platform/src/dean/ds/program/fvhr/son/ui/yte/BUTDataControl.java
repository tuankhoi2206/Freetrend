package ds.program.fvhr.son.ui.yte;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo2.app.Color;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ds.program.fvhr.domain.BaoHiemYT_BUT;
import ds.program.fvhr.domain.BaoHiemYT_BUT_tn;
import ds.program.fvhr.domain.BaoHiemYT_BUT_xh;
import ds.program.fvhr.domain.BaoHiemYT_BUT_yt;
import ds.program.fvhr.son.ui.ObjUtility;
import dsc.dao.GenericHibernateDAO;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.ReportFileManager;
import dsc.echo2app.ReportService;
import dsc.echo2app.task.ATask;
import dsc.util.function.UUID;

public class BUTDataControl {
	private static final Logger	logger			= Logger.getLogger(BUTDataControl.class);
	
	private BaoHiemYT_BUT_tn oTN;
	private BaoHiemYT_BUT_xh oXH;
	private BaoHiemYT_BUT_yt oYT;
	
	private Date dateInput ;
	private String user = Application.getApp().getLoginInfo().getUserName();
	public BUTDataControl() {
	}
	
	public void mapToEntities(Map<String, Object> mapComp) {
		dateInput = (Date) mapComp.get("DATE");
		
		oTN = new BaoHiemYT_BUT_tn(dateInput, (Double) mapComp.get("TN"),user);
		oXH = new BaoHiemYT_BUT_xh(dateInput, (Double) mapComp.get("XH"),user);
		oYT = new BaoHiemYT_BUT_yt(dateInput, (Double) mapComp.get("YT")
											, (Double) mapComp.get("NO1")
											, (Double) mapComp.get("NO2")
											, (Double) mapComp.get("NO3")
											, (Double) mapComp.get("NO4")
											, (Double) mapComp.get("NO5"),user);
	}

	public boolean save() {
		if(logger.isDebugEnabled()){
			logger.debug("start saving");
		}
		try{
			IGenericDAO<BaoHiemYT_BUT, String> dao = Application.getApp().getDao(BaoHiemYT_BUT.class);
			dao.save(oTN);
			dao.save(oXH);
			dao.save(oYT);
		}catch (Exception e) {
			ObjUtility.ShowMessageError(e.toString());
			return false;
		}
		return true;
	}
	
	public BaoHiemYT_BUT_tn getBHYT_tn(){
		if(oTN==null || !oTN.getD_apply().equals(dateInput)){
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			IGenericDAO<BaoHiemYT_BUT_tn, String> dao = Application.getApp().getDao(BaoHiemYT_BUT_tn.class);
			List oTN_f = dao.find(1,"from BaoHiemYT_BUT_tn a where a.d_apply = ?  ",new Object[]{dateInput});
			
			if(oTN_f.size() == 0) return null;
			
			Iterator it = oTN_f.iterator();
			while(it.hasNext()){
				oTN  = (BaoHiemYT_BUT_tn) it.next();
			}
		}
		return oTN;
	}
	
	public BaoHiemYT_BUT_xh getBHYT_xh(){
		if(oXH==null || !oXH.getD_apply().equals(dateInput)){
			IGenericDAO<BaoHiemYT_BUT_xh, String> dao = Application.getApp().getDao(BaoHiemYT_BUT_xh.class);
			List oTN_xh = dao.find(1,"from BaoHiemYT_BUT_xh a where a.d_apply = ?  ",new Object[]{dateInput });
			if(oTN_xh.size() == 0) return null;
			Iterator it = oTN_xh.iterator();
			while(it.hasNext()){
				oXH  = (BaoHiemYT_BUT_xh) it.next();
			}
		}
		return oXH;
	}
	
	

	public BaoHiemYT_BUT_yt getBHYT_yt(){
		if(oYT==null || !oYT.getD_apply().equals(dateInput)){
			IGenericDAO<BaoHiemYT_BUT_yt, String> dao = Application.getApp().getDao(BaoHiemYT_BUT_yt.class);
			List oTN_xh = dao.find(1,"from BaoHiemYT_BUT_yt a where a.d_apply = ?  ",new Object[]{dateInput });
			
			if(oTN_xh.size() == 0) return null;
			
			Iterator it = oTN_xh.iterator();
			while(it.hasNext()){
				oYT  = (BaoHiemYT_BUT_yt) it.next();
			}
		}
		return oYT;
	}
	
	public void setDateInput(Date dateInput) {
		this.dateInput = dateInput;
	}
	
	public Date getDateInput(){
		return dateInput;
	}
	
}
