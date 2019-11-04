package ds.program.fvhr.ui;

import java.util.ArrayList;
import java.util.List;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_QUANLY;
import ds.program.fvhr.domain.N_USER_LIMIT;
import ds.program.fvhr.domain.pk.N_USER_LIMITPk;
import ds.program.users.domain.DSPB00;
import ds.program.users.domain.DSPB01;
import ds.program.users.domain.pk.DSPB01Pk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.components.AdminProgram;
import fv.components.EgRecord;

public class HRAdmin extends AdminProgram{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<N_QUANLY> managerList;
	
	public HRAdmin() {
		super();
		propertiesFilePath="qlns.properties";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void dataToFields() {
		String userId = getQLNSUserId();
		IGenericDAO<N_USER_LIMIT, String> dao = Application.getApp().getDao(N_USER_LIMIT.class);
		List<N_USER_LIMIT> list = dao.find(20, "from N_USER_LIMIT t where t.MA_USER=? and t.MA_QL in " + getQueryInMgId(), new Object[]{userId});
		for (int i=0;i<getManagerRights().size();i++){
			EgRecord mgrec = getManagerRights().get(i);
			mgrec.setStatus(EgRecord.REC_BLANK);
			mgrec.setOldStatus(EgRecord.REC_BLANK);
			CheckBox chk = (CheckBox) getManagerGrid().getComponent(i);
			chk.setSelected(false);
			for (int j=0;j<list.size();j++){
				N_USER_LIMIT umg = list.get(j);				
				if (mgrec.getIdObject().equals(umg.getMA_QL())){
					mgrec.setStatus(EgRecord.REC_NONE);
					mgrec.setOldStatus(EgRecord.REC_NONE);
					CheckBox chk1 = (CheckBox) getManagerGrid().getComponent(i);
					chk1.setSelected(true);
					break;
				}
			}
		}
		IGenericDAO<DSPB01, DSPB01Pk> dao1 = Application.getApp().getDao(DSPB01.class);
		for (int i=0;i<getProgramRights().size();i++){
			EgRecord rec = getProgramRights().get(i);
			rec.setStatus(EgRecord.REC_BLANK);
			rec.setOldStatus(EgRecord.REC_BLANK);
			DSPB01Pk pk = (DSPB01Pk) rec.getIdObject();
			pk.setPB_USERID(getUserId());
			DSPB01 data = dao1.findById(pk);
			if (data!=null) {
				rec.setDataObject(data);//replace default object
				rec.setStatus(EgRecord.REC_NONE);
				rec.setOldStatus(EgRecord.REC_NONE);
			}else{
				DSPB01 odata = (DSPB01) rec.getDataObject();
				data = new DSPB01();
				data.setPB_ID(odata.getPB_ID());
				data.setPB_PRG(odata.getPB_PRG());
				data.setPB_RH01("N");
				data.setPB_RH02("N");
				data.setPB_RH03("N");
				data.setPB_RH04("N");
				data.setPB_RH05("N");
				data.setPB_RH06("N");
				data.setPB_RH07("N");
				data.setPB_RH08("N");
				rec.setDataObject(data);				
			}
			bindProgramCheckBoxs(data, i+1);
		}
	}
	
	private String getQueryInMgId(){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (N_QUANLY data:managerList){
			sb.append("'").append(data.getMAQL()).append("',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void initManagerRights() {
		IGenericDAO<N_QUANLY, String> dao = Application.getApp().getDao(N_QUANLY.class);
		managerList = dao.findAll(1000);
		List<EgRecord> recList = new ArrayList<EgRecord>();
		for (N_QUANLY data:managerList){
			EgRecord rec = new EgRecord();
			rec.setDataObject(data);
			rec.setIdObject(data.getMAQL());
			recList.add(rec);
		}
		setManagerRights(recList);
	}

	@Override
	public void loadLayout() {
		getManagerGrid().setSize(2);
		sp.setSeparatorPosition(new Extent(270));
		for (N_QUANLY data:managerList){
			CheckBox chk = new CheckBox(data.getMOTA());
			getManagerGrid().add(chk);
		}
		Grid grid = getProgramGrid();
		int k=1;
		for (DSPB00 data:programList){
			final CheckBox chkAll = new CheckBox();
			final int m = k;
			chkAll.addActionListener(new ActionListener(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e) {
					checkBoxAllAction(chkAll.isSelected(), m);
				}
			});
			grid.add(chkAll);
			grid.add(new Label(data.getPB_ID()));
			grid.add(new Label(data.getPB_PRG()));
			grid.add(new Label(data.getPB_NA()));
			for (int i=0;i<8;i++){
				CheckBox chk = new CheckBox();
				//add action to do select checkbox all
				grid.add(chk);
			}
			k++;
		}
	}
	
	private void checkBoxAllAction(boolean select, int index){
		int start = index*12+4;
		for (int i=start;i<start+8;i++){
			CheckBox chk = (CheckBox) getProgramGrid().getComponent(i);
			chk.setSelected(select);
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getQLNSUserId(){
		String userId = getUserId();
		MappingPropertyEditor e = getUsersEditor();	
		e.setValue(userId);
		return e.getAsText();
//		IGenericDAO<N_USERS_LIST, String> dao = Application.getApp().getDao(N_USERS_LIST.class);
//		List<N_USERS_LIST> users = dao.find(10,"from N_USERS_LIST t where t.USER_ID=(select a.PB_USERNO from DSPB02 a where a.PB_USERID=?)", new Object[]{id});
//		if (users!=null&&users.size()>0){
//			return users.get(0).getUSER_ID();
//		}else{
//			throw new RuntimeException("PB_USERNO trong bang DSPB02 phai la user_id trong N_USERS_LIST");
//		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean applyUpdate() {
		String userId = getQLNSUserId();
		IGenericDAO<N_USER_LIMIT, N_USER_LIMITPk> dao = Application.getApp().getDao(N_USER_LIMIT.class);
		for (int i=0;i<getManagerRights().size();i++){
			EgRecord rec = getManagerRights().get(i);
			N_QUANLY data =(N_QUANLY) rec.getDataObject();			
			if (rec.getStatus()==EgRecord.REC_NEW){
				N_USER_LIMIT right = new N_USER_LIMIT();
				right.setMA_QL(data.getMAQL());
				right.setMA_USER(userId);
				dao.save(right);
				System.out.println("Insert new manager right " + i);
			} else if (rec.getStatus()==EgRecord.REC_DELETE){
				N_USER_LIMITPk pk = new N_USER_LIMITPk(userId, data.getMAQL());
				N_USER_LIMIT right = dao.findById(pk);
				if (right!=null) dao.delete(right);
				System.out.println("Delete manager right " + i);
			}
		}
		IGenericDAO<DSPB01, DSPB01Pk> rdao = Application.getApp().getDao(DSPB01.class);
		for (int i=0;i<getProgramRights().size();i++){
			EgRecord rec = getProgramRights().get(i);
			DSPB01 data = (DSPB01) rec.getDataObject();
			if (rec.getStatus()==EgRecord.REC_NEW){
				rdao.save(data);
				System.out.println("Insert new program right: " + i);
			}else if (rec.getStatus()==EgRecord.REC_EDIT){
				rdao.update(data);
				System.out.println("Modify program right: " + i);				
			}else if (rec.getStatus()==EgRecord.REC_DELETE){
				rdao.delete(data);
				System.out.println("Delete program right: " + i);
			}
		}
		return true;
	}
}
