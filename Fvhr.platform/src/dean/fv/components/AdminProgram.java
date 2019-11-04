package fv.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;
import ds.program.users.domain.DSPB00;
import ds.program.users.domain.DSPB01;
import ds.program.users.domain.DSPB02;
import ds.program.users.domain.pk.DSPB01Pk;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DefaultProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import fv.util.ListBinder;

/**
 * DSC and FV permision program
 * @author Hieu
 *
 */
public abstract class AdminProgram extends DefaultProgram {
	private MappingPropertyEditor usersEditor;
	private List<EgRecord> managerRights;
	private List<EgRecord> programRights;
	private Row toolbar;
	private Button btnEdit;
	private Button btnSave;
	private Button btnCancel;
	protected SplitPane sp;
	private Grid fgrid;
	private SelectField sfUsers;
	private Grid managerGrid;
	private Grid pgrid;
	protected List<DSPB00> programList;
	
	protected String propertiesFilePath;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdminProgram(){
		super();
		initComponents();
	}

	public List<EgRecord> getManagerRights() {
		return managerRights;
	}

	public void setManagerRights(List<EgRecord> factoryRights) {
		this.managerRights = factoryRights;
	}

	public List<EgRecord> getProgramRights() {
		return programRights;
	}

	public void setProgramRights(List<EgRecord> programRights) {
		this.programRights = programRights;
	}

	public MappingPropertyEditor getUsersEditor() {
		return usersEditor;
	}

	public void setUsersEditor(MappingPropertyEditor usersEditor) {
		this.usersEditor = usersEditor;
	}

	@SuppressWarnings("unchecked")
	private void initUsersEditor(){
		usersEditor = new MappingPropertyEditor();
		IGenericDAO<DSPB02, String> dao = Application.getApp().getDao(DSPB02.class);
		List<DSPB02> list = dao.findAll(1000);
		for (DSPB02 data:list){
			if (data.getPB_USERNO()!=null&&!data.getPB_USERNO().trim().equals(""))
			usersEditor.put(data.getPB_USERNO(), data.getPB_USERID());
		}
	}
	
	private void initToolbar(){
		toolbar = new Row();
		toolbar.setInsets(new Insets(2));
		btnEdit = new Button();
		btnEdit.setEnabled(false);
		btnEdit.setBorder(new Border(new Extent(2), Color.WHITE, Border.STYLE_SOLID));
		btnEdit.setRolloverBorder(new Border(new Extent(2), Color.ORANGE, Border.STYLE_SOLID));
		btnEdit.setRolloverEnabled(true);
		btnEdit.setInsets(new Insets(3));
		btnEdit.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnEdit.gif"));
		btnEdit.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnEditD.gif"));
		btnEdit.setToolTipText("Edit permission");
		btnEdit.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doEdit();
			}
		});
		toolbar.add(btnEdit);
		btnSave = new Button();
		btnSave.setInsets(new Insets(3));
		btnSave.setBorder(new Border(new Extent(2), Color.WHITE, Border.STYLE_SOLID));
		btnSave.setRolloverBorder(new Border(new Extent(2), Color.ORANGE, Border.STYLE_SOLID));
		btnSave.setRolloverEnabled(true);
		btnSave.setEnabled(false);
		btnSave.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnSave.gif"));
		btnSave.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnSaveD.gif"));
		btnSave.setToolTipText("Save");
		btnSave.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		toolbar.add(btnSave);
		btnCancel = new Button();
		btnCancel.setBorder(new Border(new Extent(2), Color.WHITE, Border.STYLE_SOLID));
		btnCancel.setRolloverBorder(new Border(new Extent(2), Color.ORANGE, Border.STYLE_SOLID));
		btnCancel.setRolloverEnabled(true);
		btnCancel.setInsets(new Insets(3));
		btnCancel.setEnabled(false);
		btnCancel.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnCancel.gif"));
		btnCancel.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnCancelD.gif"));
		btnCancel.setToolTipText("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				doCancel();
			}
		});
		toolbar.add(btnCancel);
	}
	
	/**
	 * Load tat ca quyen quan ly tu database, managerGrid + tao ra cac checkbox tuong ung
	 *  
	 */
	protected abstract void initManagerRights();
	
	/**
	 * Load tat ca quyen thao tac chuong trinh (DSPB01), tao ra grid checkbox
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void initProgramRights() {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream(propertiesFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>();
		Iterator ite = prop.keySet().iterator();
		while (ite.hasNext()){
			list.add(ite.next().toString());
		}

		IGenericDAO<DSPB00, String> dao = Application.getApp().getDao(DSPB00.class);
		DetachedCriteria dc = DetachedCriteria.forClass(DSPB00.class);
		dc.add(Restrictions.in("PB_ID", list));
		programList = dao.findByCriteria(50, dc);
		List<EgRecord> programRights = new ArrayList<EgRecord>();
		for (DSPB00 prg:programList){
			DSPB01 data = new DSPB01();
			data.setPB_ID(prg.getPB_ID());
			data.setPB_PRG(prg.getPB_PRG());
			data.setPB_RH01("N");
			data.setPB_RH02("N");
			data.setPB_RH03("N");
			data.setPB_RH04("N");
			data.setPB_RH05("N");
			data.setPB_RH06("N");
			data.setPB_RH07("N");
			data.setPB_RH08("N");
			//required PB_USERID
			DSPB01Pk pk = new DSPB01Pk();
			pk.setPB_ID(prg.getPB_ID());
//			required PB_USERID
			EgRecord rec = new EgRecord();
			rec.setDataObject(data);
//			rec.setOldStatus(EgRecord.REC_BLANK);
//			rec.setStatus(EgRecord.REC_BLANK);
			rec.setIdObject(pk);
			programRights.add(rec);
		}
		setProgramRights(programRights);
	}
	
	protected abstract void dataToFields();
	
	protected void fieldsToData() {
		//add or remove manager right
		for (int i=0;i<getManagerRights().size();i++){
			EgRecord rec = getManagerRights().get(i);
			CheckBox chk = (CheckBox) getManagerGrid().getComponent(i);
			if (rec.getStatus()==EgRecord.REC_BLANK){
				if (chk.isSelected()){
					rec.setStatus(EgRecord.REC_NEW);
				}
			}else{
				if (!chk.isSelected()){
					rec.setStatus(EgRecord.REC_DELETE);
				}
			}
		}
		for (int i=0;i<getProgramRights().size();i++){
			EgRecord rec = getProgramRights().get(i);
			DSPB01 data = (DSPB01) rec.getDataObject();
			data.setPB_USERID(getUserId());
			rec.setStatus(checkProgramRightChanged(data, i+1, rec.getStatus()));
		}
	}
	
	private int checkProgramRightChanged(DSPB01 data, int index, int status) {
		int count = 0;
		int start = index*12+4;
		
		CheckBox chk1 = (CheckBox) getProgramGrid().getComponent(start);
		CheckBox chk2 = (CheckBox) getProgramGrid().getComponent(start+1);
		CheckBox chk3 = (CheckBox) getProgramGrid().getComponent(start+2);
		CheckBox chk4 = (CheckBox) getProgramGrid().getComponent(start+3);
		CheckBox chk5 = (CheckBox) getProgramGrid().getComponent(start+4);
		CheckBox chk6 = (CheckBox) getProgramGrid().getComponent(start+5);
		CheckBox chk7 = (CheckBox) getProgramGrid().getComponent(start+6);
		CheckBox chk8 = (CheckBox) getProgramGrid().getComponent(start+7);
		if (status==EgRecord.REC_NONE)//none => delete
			if (!chk1.isSelected()&&!chk2.isSelected()&&!chk3.isSelected()&&!chk4.isSelected()
					&&!chk5.isSelected()&&!chk6.isSelected()&&!chk7.isSelected()&&!chk8.isSelected()){
				return EgRecord.REC_DELETE;
			}
		String rh01 = chk1.isSelected()?"Y":"N";
		if (!data.getPB_RH01().equalsIgnoreCase(rh01)) {
			count++;
			data.setPB_RH01(rh01);
		}//rh01
		
		String rh02 = chk2.isSelected()?"Y":"N";
		if (!data.getPB_RH02().equalsIgnoreCase(rh02)) {
			count++;
			data.setPB_RH02(rh02);
		}//rh02
		
		String rh03 = chk3.isSelected()?"Y":"N";
		if (!data.getPB_RH03().equalsIgnoreCase(rh03)) {
			count++;
			data.setPB_RH03(rh03);
		}//rh03
		
		String rh04 = chk4.isSelected()?"Y":"N";
		if (!data.getPB_RH04().equalsIgnoreCase(rh04)) {
			count++;
			data.setPB_RH04(rh04);
		}//rh04
		
		String rh05 = chk5.isSelected()?"Y":"N";
		if (!data.getPB_RH05().equalsIgnoreCase(rh05)) {
			count++;
			data.setPB_RH05(rh05);
		}//rh05
		
		String rh06 = chk6.isSelected()?"Y":"N";
		if (!data.getPB_RH06().equalsIgnoreCase(rh06)) {
			count++;
			data.setPB_RH06(rh06);
		}//rh06
		
		String rh07 = chk7.isSelected()?"Y":"N";
		if (!data.getPB_RH07().equalsIgnoreCase(rh07)) {
			count++;
			data.setPB_RH07(rh07);
		}//rh07
		
		String rh08 = chk8.isSelected()?"Y":"N";
		if (!data.getPB_RH08().equalsIgnoreCase(rh08)) {
			count++;
			data.setPB_RH08(rh08);
		}//rh08
		if (count>0){//has change as least one right
			if (status==EgRecord.REC_BLANK)
				return EgRecord.REC_NEW;
			else
				return EgRecord.REC_EDIT;
		}
		return status;
	}
	
	protected abstract boolean applyUpdate();
	
	@Override
	protected int doInit() {
		int ret = super.doInit();
		
		//1. Load all user
		initUsersEditor();
		//2. Load all factory rights
		initManagerRights();
		//3. Load all program rights;
		initProgramRights();
		
		loadLayout();
		
		ListBinder.bindSelectField(sfUsers, usersEditor, false);
		
		sfUsers.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				btnEdit.setEnabled(true);
				dataToFields();
			}
		});
		
		return ret;
	}
	
	
	public boolean doEdit(){
		dataToFields();
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
		btnEdit.setEnabled(false);
		managerGrid.setEnabled(true);
		pgrid.setEnabled(true);
		sfUsers.setEnabled(false);
		return true;
	}
	
	public boolean doSave(){
		fieldsToData();
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		btnEdit.setEnabled(true);
		managerGrid.setEnabled(false);
		pgrid.setEnabled(false);
		sfUsers.setEnabled(true);
		return applyUpdate();
	}
	
	public boolean doCancel(){
		dataToFields();
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		btnEdit.setEnabled(true);
		managerGrid.setEnabled(false);
		pgrid.setEnabled(false);
		sfUsers.setEnabled(true);
		return true;	
	}
	
	public abstract void loadLayout();
	
	protected void bindProgramCheckBoxs(DSPB01 data, int index){
		int start = index*12+4;
		CheckBox chkAll = (CheckBox) getProgramGrid().getComponent(index*12);
		if (data==null){
			for (int i=start;i<start+8;i++){
				CheckBox chk = (CheckBox) getProgramGrid().getComponent(i);
				chk.setSelected(false);
			}
			chkAll.setSelected(false);
			return;
		}
		Boolean[] rights = new Boolean[8];
		String rh01 = data.getPB_RH01();
		if (rh01!=null&&rh01.equalsIgnoreCase("y")) rights[0]=true; else rights[0]=false;
		String rh02 = data.getPB_RH02();
		if (rh02!=null&&rh02.equalsIgnoreCase("y")) rights[1]=true; else rights[1]=false;
		String rh03 = data.getPB_RH03();
		if (rh03!=null&&rh03.equalsIgnoreCase("y")) rights[2]=true; else rights[2]=false;
		String rh04 = data.getPB_RH04();
		if (rh04!=null&&rh04.equalsIgnoreCase("y")) rights[3]=true; else rights[3]=false;
		String rh05 = data.getPB_RH05();
		if (rh05!=null&&rh05.equalsIgnoreCase("y")) rights[4]=true; else rights[4]=false;
		String rh06 = data.getPB_RH06();
		if (rh06!=null&&rh06.equalsIgnoreCase("y")) rights[5]=true; else rights[5]=false;
		String rh07 = data.getPB_RH07();
		if (rh07!=null&&rh07.equalsIgnoreCase("y")) rights[6]=true; else rights[6]=false;
		String rh08 = data.getPB_RH08();
		if (rh08!=null&&rh08.equalsIgnoreCase("y")) rights[7]=true; else rights[7]=false;
		int j=0;
		boolean select = true;
		for (int i=start;i<start+8;i++){
			CheckBox chk = (CheckBox) getProgramGrid().getComponent(i);
			chk.setSelected(rights[j]);
			select = select&&rights[j];
			j++;
		}
		chkAll.setSelected(select);
	}
	
	public SplitPane getMainSplitPane(){
		return this.sp;
	}
	
	public Grid getManagerGrid(){
		return this.managerGrid;
	}
	
	public Grid getProgramGrid(){
		return this.pgrid;
	}
	
	public String getUserId(){
		SelectItem item = (SelectItem) sfUsers.getSelectedItem();
		return item.getValue().toString();
	}
	
	private void initComponents(){
		sp = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		sp.setSeparatorPosition(new Extent(55));
		sp.setResizable(true);
		sp.setSeparatorVerticalImage(new FillImage(new ResourceImageReference("/dsc/echo2app/resource/image/SplitVertBar.png")));
		add(sp);
		fgrid = new Grid(3);
		fgrid.setWidth(new Extent(100, Extent.PERCENT));
		fgrid.add(new Label("Select user:"));
		sfUsers = new SelectField();
		sfUsers.setWidth(new Extent(200));
		fgrid.add(sfUsers);//user list
		initToolbar();
		fgrid.add(toolbar);//toolbar
		fgrid.add(new Label("Manager ID:"));
		managerGrid = new Grid();
		managerGrid.setEnabled(false);
		GridLayoutData mly = new GridLayoutData();
		mly.setColumnSpan(2);
		managerGrid.setLayoutData(mly);
		fgrid.add(managerGrid);
		sp.add(fgrid);
		pgrid = new Grid(12);
		pgrid.setEnabled(false);
		pgrid.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
		pgrid.add(new Label());
		pgrid.add(new Label("Program ID"));
		pgrid.add(new Label("Program Name"));
		pgrid.add(new Label("Description"));
		pgrid.add(new Label("New"));
		pgrid.add(new Label("Query"));
		pgrid.add(new Label("Delete"));
		pgrid.add(new Label("Modify"));
		pgrid.add(new Label("Confirm"));
		pgrid.add(new Label("Unconfirm"));
		pgrid.add(new Label("Cost"));
		pgrid.add(new Label("Print"));
		pgrid.setInsets(new Insets(2));
		GridLayoutData layout = new GridLayoutData();
		layout.setBackground(new Color(0x356DA6));
		for (int i=0;i<12;i++){
			pgrid.getComponent(i).setLayoutData(layout);
			pgrid.getComponent(i).setForeground(Color.ORANGE);			
		}
		sp.add(pgrid);
	}
}
