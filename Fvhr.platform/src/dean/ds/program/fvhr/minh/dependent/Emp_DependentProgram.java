package ds.program.fvhr.minh.dependent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.table.DefaultTableModel;

import ds.program.fvhr.dao.insuranse.InsuranceDAO;
import ds.program.fvhr.domain.N_ACTION_DAILY;
import ds.program.fvhr.domain.N_EMP_DEPENDENT;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.table.DscDataObjectSetTable;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.program.MaintainSProgram;
import dsc.echo2app.program.QueryPane;
import fv.util.FvLogger;

public class Emp_DependentProgram extends MaintainSProgram{

	private int dataMode;
	private InsuranceDAO ins = new InsuranceDAO();
	private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	private Emp_DependentQuery reQuery;

	@Override
	protected void createDataContent() {
		// TODO Auto-generated method stub
		setMasterDataContent(new Emp_DependentDataContent());
	}
	
	@Override
	protected QueryPane createNormalQuery() {
		if (reQuery==null)
			reQuery = new Emp_DependentQuery();
		return reQuery;
	}
	
	@Override
	protected String[] getBrowserDisplayColumns() {
		
        return new String[] {"EMPSN", "NAME_RELATIVE", "BIRTHDAY", "SEX","CONFIRM_DATE", "BEGINDATE", "ENDDATE","IDKIND"};
	}
	
	@Override
	public boolean doNew() {
		// TODO Auto-generated method stub
		boolean ret= super.doNew();
		if (ret) {
			dataMode = DATAMODE_NEW;
			((DscField)getMasterDataContent().getComponent("txtEmpName")).setText("");
			//((Row)getMasterDataContent().getComponent("rowEmpsn")).setEnabled(true);
			return ret;
		}	
		return ret;
	}
	

	@Override
	public boolean doEdit() 
	{
		boolean ret= super.doEdit();
		if (ret) {
			//((Row)getMasterDataContent().getComponent("rowEmpsn")).setEnabled(false);
		}
		return ret;
	}
	
	
	@Override
	protected void doRefresh() {
		//super.doRefresh();
		List<Object> params = new ArrayList<Object>();
		ProgramCondition pc = new ProgramCondition("", params.toArray());			
		setQueryCondition(pc);
		refresh();
	}
	
	@Override
	public boolean doDelete() {
		// TODO Auto-generated method stub
		boolean retVal = true;
		// Lay tu data dang thao tac
		N_EMP_DEPENDENT emp = (N_EMP_DEPENDENT) this.getBrowserContent()
				.getDataObjectSet().getDataObject();		
		String ngayBatDau = sf.format(emp.getBEGINDATE());
		String ngayDky = sf.format(emp.getCONFIRM_DATE());
		String ngaySinh = sf.format(emp.getBIRTHDAY());
		// neu da chuyen du lieu roi thi ko cho xoa		
		String thang = ngayBatDau.substring(3, 5);
		String nam = ngayBatDau.substring(6, 10);	
		String sothe = emp.getEMPSN();


		if (ins.checkQLyEmpsn(emp.getEMPSN())==false)
		{
			setErrorMessage("Không có quyền thao tác.");
			retVal=false;
		}		
		else if (ins.CheckKhoaDataMonth(emp.getEMPSN(), thang, nam)==false)
		{	
			setErrorMessage("Đã khóa dữ liệu");
			retVal = false;			
		}
		
		if (retVal) {
			super.doDelete();
			//luu trong action daily
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setACTIONNAME("DELETE");
			action.setEMPSN(sothe);
			String note = emp.getNAME_RELATIVE()+", ngay DKy "+ngayDky+", NgSinh "+ngaySinh ;
			note=note+", TGBDau "+ngayBatDau;
			action.setNOTE(note);
			action.setTABLENAME("N_EMP_DEPENDENT");
			FvLogger.log(action);
			
		} else {
			Application.getApp().showMessageDialog(
					MessageDialog.TYPE_ERROR + MessageDialog.CONTROLS_OK,
					getErrorMessage());
			retVal = false;
		}
		super.doMasterDataSelectChange();
		
		return retVal;
	}
	
	@Override
	public boolean doSave() {
		DscField txt = ((DscField)getMasterDataContent().getComponent("txtRelativeName"));
		txt.setText(txt.getText().toUpperCase());
		boolean ret = super.doSave();
		if (ret) {
			N_EMP_DEPENDENT data = (N_EMP_DEPENDENT) getMasterDataContent()
			.getDataObject();
			N_ACTION_DAILY action = new N_ACTION_DAILY();
			action.setACTIONNAME(dataMode==IProgram.DATAMODE_NEW?"INSERT":"UPDATE");
			action.setEMPSN(data.getEMPSN());
			String note = data.getNAME_RELATIVE()+", ngay DKy "+sf.format(data.getCONFIRM_DATE())+", NgSinh "+sf.format(data.getBIRTHDAY()) ;
			note=note+", TGBDau "+data.getBEGINDATE();
			action.setNOTE(note);
			action.setTABLENAME("N_EMP_DEPENDENT");
			FvLogger.log(action);
			return ret;
		}
		return ret;
	}
	

	
}
