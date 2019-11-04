package ds.program.fvhr.ui.training;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SelectField;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import ds.program.fvhr.domain.N_DEPARTMENT;
import ds.program.fvhr.domain.N_KHOA_HOC;
import fv.util.FVGenericInfo;
import fv.util.VniEditor;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import echopointng.ComboBox;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.app.Insets;

public class KhoaHoc00DDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label MA_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField MA_KHOA_DscField1;
    private nextapp.echo2.app.Label TEN_KHOA_CaptionLabel;
    private dsc.echo2app.component.DscField TEN_KHOA_DscField2;
    private nextapp.echo2.app.Label TRAINING_TYPE_CaptionLabel;
//    private dsc.echo2app.component.DscField TRAINING_TYPE_DscField3;
    private nextapp.echo2.app.Label DEPSN_CaptionLabel;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
    private dsc.echo2app.component.DscField NOTE_DscField5;
	private SelectField sfType;
	private ComboBox cboDepsn;

	/**
	 * Creates a new <code>KhoaHoc00DDataContent</code>.
	 */
	public KhoaHoc00DDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}


	/*
	 * UI資料欄位屬性更新，一般是資料模式變換時會呼叫此method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doUIRefresh()
	 */
	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE) || 
				(this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
			getProgram().getBrowserContent().getBrowserNav().setEnabled(true);
			getProgram().getBrowserContent().getBrowserTable().setEnabled(true);
		} else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				MA_KHOA_DscField1.setEnabled(true);
			} else {
				MA_KHOA_DscField1.setEnabled(false);
			}
			getProgram().getBrowserContent().getBrowserNav().setEnabled(false);
			getProgram().getBrowserContent().getBrowserTable().setEnabled(false);
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}
	
	@Override
	public boolean insert() {
		if (super.insert()){
			getProgram().getBrowserContent().getBrowserTable().refresh();
			return true;
		}
		return false;
	}
	
	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	@Override
	public Class getDataObjectClass() {
		return N_KHOA_HOC.class;
	}

	
	/*
	 * 資料儲存前設定一些欄位內定值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#saveUIToDataObject()
	 */
	@Override
	public void saveUIToDataObject() {
		super.saveUIToDataObject();
		//使用者可在以下區域填入控制欄位數值
		//N_KHOA_HOC data = (N_KHOA_HOC) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_KHOA_HOC data = (N_KHOA_HOC) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			IGenericDAO<N_KHOA_HOC, String> dao = Application.getApp().getDao(N_KHOA_HOC.class);
			List<N_KHOA_HOC> list;
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				N_KHOA_HOC obj = dao.findById(data.getMA_KHOA());
				if (obj!=null){
					setErrorMessage("Đã tồn tại mã " + data.getMA_KHOA() + ". Nhập mã khác");
					return false;
				}
				list = dao.find(10, "from N_KHOA_HOC t where t.TEN_KHOA=?", new Object[]{data.getTEN_KHOA()});
			}else{
				list = dao.find(10, "from N_KHOA_HOC t where t.TEN_KHOA=? and t.MA_KHOA!=?", new Object[]{data.getTEN_KHOA(), data.getMA_KHOA()});
			}
			if (list.size()>0){
				setErrorMessage("Tên khóa đã tồn tại. Nhập tên khác.");
				return false;
			}
		}
		return ret;
	}
	

	/*
	 * 元件初始化Method
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doInit()
	 */
	@Override
	protected int doInit() {
		int nRet = super.doInit();

		//1.註冊資料欄位之顯示方式
		registPropertyEditor();

		//2.設定資料欄位與 UI之 Binding資訊
		bindUI();

		return nRet;
	}
	
	private void registPropertyEditor() {
	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor
        getUIDataObjectBinder().registerCustomEditor(N_KHOA_HOC.class, "TRAINING_TYPE", getTypeEditor());
        getUIDataObjectBinder().registerCustomEditor(N_KHOA_HOC.class, "TEN_KHOA", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_KHOA_HOC.class, "NOTE", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_KHOA_HOC.class, "DEPSN", depsnEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("MA_KHOA", MA_KHOA_DscField1, MA_KHOA_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TEN_KHOA", TEN_KHOA_DscField2, TEN_KHOA_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TRAINING_TYPE", sfType, TRAINING_TYPE_CaptionLabel);
        getUIDataObjectBinder().addBindMap("DEPSN", cboDepsn, DEPSN_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", NOTE_DscField5, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
	}
	
	private MappingPropertyEditor getTypeEditor(){
		MappingPropertyEditor e = new MappingPropertyEditor();
		e.put("", "");
		e.put("1 LẦN","1 LAÀN");
		e.put("CẬP NHẬT", "CAÄP NHAÄT");
		return e;
	}
	
	private MappingPropertyEditor depsnEditor(){
		MappingPropertyEditor de = FVGenericInfo.getDepartmentEditor();
		MappingPropertyEditor e = new MappingPropertyEditor();
		String[] disp = de.getDisplays();
		for (String str:disp){
			de.setAsText(str);
			N_DEPARTMENT d = (N_DEPARTMENT) de.getValue();
			e.put(d.getNAME_DEPT(), str);
		}
		return e;
	}

	/*
	 * 畫面Layout初始化Method
	 * 
	 * @see dsc.echo2app.program.DataContent#doLayout()
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		//<<從此以下加入使用者程式>>
	}

	/*
	 * 做資料新增時內定預設數值
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#doNewDefaulData()
	 */
	@Override
	protected void doNewDefaulData() {
		N_KHOA_HOC data = (N_KHOA_HOC) getDataObject();
		data.setMA_KHOA("KH");
		//<如果要預先取號則在此加入>
		//autoPrimaryKeyValue();
		//使用者可在以下區域填入新增時內定的預設數值
		//<內定值>
	}

	/*
	 * 儲存資料物件至DataObjectSet之前處理
	 * 
	 * @see dsc.echo2app.program.DataContent#beforeSaveToDataObjectSet()
	 */
	@Override
	public void beforeSaveToDataObjectSet() {
		N_KHOA_HOC data = (N_KHOA_HOC) getDataObject();
		if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
			getProgram().updateChildPK(data.getMA_KHOA());
		}
//		data.setTEN_KHOA(Vni2Uni.convertToVNI(data.getTEN_KHOA()));
//		data.setNOTE(Vni2Uni.convertToVNI(data.getNOTE()));
	}
	
	@Override
	public KhoaHoc00DProgram getProgram() {
		return (KhoaHoc00DProgram) super.getProgram();
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setInsets(new Insets(new Extent(2, Extent.PX)));
		rootLayout.setSize(4);
		add(rootLayout);
		MA_KHOA_CaptionLabel = new Label();
		MA_KHOA_CaptionLabel.setText("N_KHOA_HOC.MA_KHOA");
		rootLayout.add(MA_KHOA_CaptionLabel);
		MA_KHOA_DscField1 = new DscField();
		MA_KHOA_DscField1.setId("MA_KHOA_DscField1");
		rootLayout.add(MA_KHOA_DscField1);
		TEN_KHOA_CaptionLabel = new Label();
		TEN_KHOA_CaptionLabel.setText("N_KHOA_HOC.TEN_KHOA");
		rootLayout.add(TEN_KHOA_CaptionLabel);
		TEN_KHOA_DscField2 = new DscField();
		TEN_KHOA_DscField2.setId("TEN_KHOA_DscField2");
		TEN_KHOA_DscField2.setWidth(new Extent(300, Extent.PX));
		rootLayout.add(TEN_KHOA_DscField2);
		TRAINING_TYPE_CaptionLabel = new Label();
		TRAINING_TYPE_CaptionLabel.setText("N_KHOA_HOC.TRAINING_TYPE");
		rootLayout.add(TRAINING_TYPE_CaptionLabel);
		sfType = new SelectField();
		sfType.setWidth(new Extent(150, Extent.PX));
		rootLayout.add(sfType);
		DEPSN_CaptionLabel = new Label();
		DEPSN_CaptionLabel.setText("N_KHOA_HOC.DEPSN");
		rootLayout.add(DEPSN_CaptionLabel);
		cboDepsn = new ComboBox();
		cboDepsn.setWidth(new Extent(300, Extent.PX));
		rootLayout.add(cboDepsn);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_KHOA_HOC.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		NOTE_DscField5 = new DscField();
		NOTE_DscField5.setId("NOTE_DscField5");
		NOTE_DscField5.setWidth(new Extent(500, Extent.PX));
		GridLayoutData NOTE_DscField5LayoutData = new GridLayoutData();
		NOTE_DscField5LayoutData.setColumnSpan(3);
		NOTE_DscField5.setLayoutData(NOTE_DscField5LayoutData);
		rootLayout.add(NOTE_DscField5);
	}

}
