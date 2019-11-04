package ds.program.fvhr.ui.training;

import java.util.List;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import ds.program.fvhr.domain.N_TRAINING_LIST;
import fv.util.FvStringUtils;
import fv.util.VniEditor;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.TextArea;

public class MonHoc00MDataContent extends DataContent {

    private nextapp.echo2.app.Grid rootLayout;
    private nextapp.echo2.app.Label MA_MON_HOC_CaptionLabel;
    private dsc.echo2app.component.DscField MA_MON_HOC_DscField1;
    private nextapp.echo2.app.Label TEN_MON_HOC_CaptionLabel;
    private dsc.echo2app.component.DscField TEN_MON_HOC_DscField2;
    private nextapp.echo2.app.Label NOTE_CaptionLabel;
//    private dsc.echo2app.component.DscField NOTE_DscField4;
	private TextArea txtNote;/**
	 * Creates a new <code>MonHoc00MDataContent</code>.
	 */
	public MonHoc00MDataContent() {
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
		} else {
			rootLayout.setEnabled(true);
            //----------------------------------------------------------------------------------
			//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
            //----------------------------------------------------------------------------------
			if(this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				MA_MON_HOC_DscField1.setEnabled(true);
			} else {
				MA_MON_HOC_DscField1.setEnabled(false);
			}
		}

		//7.<資料權限管控>


		//自動設定元件風格
		getUIDataObjectBinder().doDataBindUIStyle();
	}

	/*
	 * 取得該畫面的DataObject Class
	 * <<必須繼承改寫>>
	 * @see dsc.echo2app.program.DataContent#getDataObjectClass()
	 */
	@Override
	public Class getDataObjectClass() {
		return N_TRAINING_LIST.class;
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
		//N_TRAINING_LIST data = (N_TRAINING_LIST) getDataObject();
	}

	/*
	 * 做資料儲存前，內容合理值判斷
	 * 
	 * @see dsc.echo2app.program.DataContent#checkDataObject()
	 */
	@Override
	public boolean checkDataObject() {
		N_TRAINING_LIST data = (N_TRAINING_LIST) getDataObject();
		boolean ret = super.checkDataObject();
		if (ret) {
			IGenericDAO<N_TRAINING_LIST, String> dao = Application.getApp().getDao(N_TRAINING_LIST.class);
			List<N_TRAINING_LIST> list;
			if (getProgram().getDataMode()==IProgram.DATAMODE_NEW){
				N_TRAINING_LIST obj = dao.findById(data.getMA_MON_HOC());
				if (obj!=null){
					setErrorMessage("Đã có mã môn học " + data.getMA_MON_HOC() + ". Nhập mã khác");
					return false;
				}
				list = dao.find(5, "from N_TRAINING_LIST t where t.TEN_MON_HOC=?", new Object[]{data.getTEN_MON_HOC()});
			}else{
				list = dao.find(5, "from N_TRAINING_LIST t where t.TEN_MON_HOC=? and t.MA_MON_HOC!=?", new Object[]{data.getTEN_MON_HOC(),data.getMA_MON_HOC()});
			}
			if (list.size()>0){
				setErrorMessage("Tên khóa học đã có. Đặt tên khác.");
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
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_LIST.class, "TEN_MON_HOC", new VniEditor());
        getUIDataObjectBinder().registerCustomEditor(N_TRAINING_LIST.class, "NOTE", new VniEditor());
	}
	
	private void bindUI() {
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        getUIDataObjectBinder().addBindMap("MA_MON_HOC", MA_MON_HOC_DscField1, MA_MON_HOC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("TEN_MON_HOC", TEN_MON_HOC_DscField2, TEN_MON_HOC_CaptionLabel);
        getUIDataObjectBinder().addBindMap("NOTE", txtNote, NOTE_CaptionLabel);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
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
		//N_TRAINING_LIST data = (N_TRAINING_LIST) getDataObject();
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
		N_TRAINING_LIST data = (N_TRAINING_LIST) getDataObject();
		data.setMA_MON_HOC(FvStringUtils.removeSpaceCharAndUpper(data.getMA_MON_HOC()));
		data.setNOTE(FvStringUtils.cleanAndUpper(data.getNOTE()));
	}


	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setSize(2);
		add(rootLayout);
		MA_MON_HOC_CaptionLabel = new Label();
		MA_MON_HOC_CaptionLabel.setText("N_TRAINING_LIST.MA_MON_HOC");
		rootLayout.add(MA_MON_HOC_CaptionLabel);
		MA_MON_HOC_DscField1 = new DscField();
		MA_MON_HOC_DscField1.setId("MA_MON_HOC_DscField1");
		MA_MON_HOC_DscField1.setInputType(DscField.INPUT_TYPE_TEXT);
		MA_MON_HOC_DscField1.setMaximumLength(6);
		rootLayout.add(MA_MON_HOC_DscField1);
		TEN_MON_HOC_CaptionLabel = new Label();
		TEN_MON_HOC_CaptionLabel.setText("N_TRAINING_LIST.TEN_MON_HOC");
		rootLayout.add(TEN_MON_HOC_CaptionLabel);
		TEN_MON_HOC_DscField2 = new DscField();
		TEN_MON_HOC_DscField2.setId("TEN_MON_HOC_DscField2");
		TEN_MON_HOC_DscField2.setWidth(new Extent(350, Extent.PX));
		TEN_MON_HOC_DscField2.setInputType(DscField.INPUT_TYPE_TEXT);
		TEN_MON_HOC_DscField2.setMaximumLength(100);
		rootLayout.add(TEN_MON_HOC_DscField2);
		NOTE_CaptionLabel = new Label();
		NOTE_CaptionLabel.setText("N_TRAINING_LIST.NOTE");
		rootLayout.add(NOTE_CaptionLabel);
		txtNote = new TextArea();
		txtNote.setWidth(new Extent(350, Extent.PX));
		rootLayout.add(txtNote);
	}

}
