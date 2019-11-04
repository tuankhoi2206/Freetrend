package ds.program.fvhr.ui.an;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.RowLayoutData;

import ds.program.fvhr.domain.N_LABOUR;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.ui.NKYLUATProgram;
import ds.program.fvhr.ui.danhsach_CNthuviec;

import ds.program.users.domain.DSPB02;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.binder.UICaptionBinder;
import dsc.echo2app.component.event.FocusEvent;
import dsc.echo2app.component.event.FocusListener;
import dsc.echo2app.program.BrowserContent;
import dsc.echo2app.program.DetailContent;
import dsc.echo2app.program.MaintainDProgram;
import dsc.echo2app.program.MasterToolbar;
import dsc.echo2app.program.QueryPane;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import fv.components.SelectItem;
import fv.util.FVGenericInfo;
import fv.util.FvLogger;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import fv.util.library;


/**
 * N_Labour * 
 */
public class nLabourMProgram extends MaintainDProgram {
	library l=new library();
	DSPB02 u;
	public Button kyhd;
	public Button luutv;
	public Button kypl;
	private nLabourMProgram _main;
	ObjUtility util=new ObjUtility();
	private SplitPane splitPane;
	private WPCSearchRow searchRow;
	private DscField txtSothe;
	public SelectField sfxuong;
	public SelectField sfnhom;
	public SelectField sfdonvi;
	private DscDateField dsc_ngay;
	public int count;
	private Label lbl_info;
	// *********************************************************
	// * 繼承改寫的Method
	// *********************************************************

	/*
	 * 建立資料明細UI物件 <<必須繼承改寫>>
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#createDataContent()
	 */
	@Override
	protected void createDataContent() {
		setMasterDataContent(new nLabourMDataContent());

		// 建立單身資料UI物件
		UICaptionBinder bb = new UICaptionBinder();

		// 加入單身物件
        //----------------------------------------------------------------------------------
		//以下為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        DetailContent dc;
        dc = new nLabourMDetailContent0();
        this.addDetail(bb.getResourceBundle().getString("N_SUB_LABOUR"), null, dc);
        //以上為自動產生的程式碼，請勿修改此區域內之程式碼-------------------------------------------
        //----------------------------------------------------------------------------------
		bb = null;
	}

	/*
	 * 作業 UI元件之建立與設定初始化
	 * 
	 * @see dsc.echo2app.program.MaintainDProgram#doInit()
	 */
	@Override
	protected int doInit() {
		int ret = super.doInit();
		splitPane = new SplitPane();
		splitPane.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane.setSeparatorPosition(new Extent(38));
		searchRow = new WPCSearchRow(this);
		
		windowPane.setHeight(new Extent(Application.getApp().getScreenHeight()));
		windowPane.setWidth(new Extent(Application.getApp().getScreenWidth()));
		
		
		//getMasterToolbar().setBackground(new Color(0xbbddaa));
		getBrowserContent().getBrowserNav().setBackground(new Color(0xbbddaa));
		getBrowserContent().getBrowserNav().setRowsPerPage(20);
		getBrowserContent().setMaxSize(20000);
		
		lbl_info=new Label();
		lbl_info.setText("");
		RowLayoutData infoLayout1 = new RowLayoutData();
		infoLayout1.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		infoLayout1.setWidth(new Extent(200, Extent.PERCENT));
		lbl_info.setLayoutData(infoLayout1);
		lbl_info.setVisible(false);
		getBrowserContent().getBrowserNav().add(lbl_info);
		
		
		IGenericDAO<DSPB02, String> udao = Application.getApp().getDao(DSPB02.class);
		u = udao.findById(getLoginInfo().getUserID());
		
		String a=l.vungQL(u.getPB_USERNO());
		
		//String sql="o.EMPSN in(select e.EMPSN from N_EMPLOYEE e,N_DEPARTMENT d " +
		//" where o.EMPSN=e.EMPSN and e.DEPSN=d.ID_DEPT and e.DEPSN<>'00000' and d.NAME_FACT IN "+a+") ";
				//" and o.CLOCK=1 and 1<>1 ";
					//ProgramCondition pc=new ProgramCondition(sql);
		//setBaseCondition(pc);
	
		
		ListBinder.bindSelectField(sfxuong, FVGenericInfo.getFactories(), true);
		return ret;
	}
	
	private class WPCSearchRow extends Row{
		private static final long serialVersionUID = 1L;
		
		private CheckBox xuong;
		private Button btnSearch;
		private nLabourMProgram program;
		public WPCSearchRow(nLabourMProgram program){
			super();
			this.program=program;
			initComponents();
		}
		
		public void setSothe(String st){
			txtSothe.setText(st);
		}

		
		private void initComponents(){
			this.setCellSpacing(new Extent(2));
			this.setStyleName("Default.Toolbar");
			this.setBackground(new Color(0xCCCC99));
			this.add(new Label("Số thẻ"));
			txtSothe = new DscField();
			txtSothe.setMaximumLength(8);
			//final TextField txtEmpsn = new TextField();
			txtSothe.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					doSearch();
				}
			});
			txtSothe.addFocusListener(new FocusListener() {
				
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					ListBinder.bindSelectField(sfxuong, FVGenericInfo.getFactories(), true);
					ListBinder.bindSelectField(sfnhom, FVGenericInfo.getGroup(sfxuong.getSelectedItem()), true);
					ListBinder.bindSelectField(sfdonvi, FVGenericInfo.getDeptName(sfxuong.getSelectedItem(), sfnhom.getSelectedItem()), true);
				}
			});
			this.add(txtSothe);			
			this.add(new Label("Xưởng"));
			sfxuong=new SelectField();
			sfxuong.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(sfxuong.getSelectedIndex()!=-1){
						txtSothe.setText("");
					}
					SelectItem item = (SelectItem) sfxuong.getSelectedItem();
					ListBinder.bindSelectField(sfnhom, FVGenericInfo.getGroup(item.getValue()), true);
				}
			});
			this.add(sfxuong);
			this.add(new Label("Nhóm"));
			sfnhom=new SelectField();
			sfnhom.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					SelectItem item = (SelectItem) sfnhom.getSelectedItem();
					ListBinder.bindSelectField(sfdonvi, FVGenericInfo.getDept(sfxuong.getSelectedItem().toString(), item.getValue()), true);
				}
			});
			this.add(sfnhom);
			this.add(new Label("Đơn vị"));
			sfdonvi=new SelectField();
			this.add(sfdonvi);
			
			
			btnSearch = new Button();
			btnSearch.setStyleName("Default.ToolbarButton");
			btnSearch.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQuery.gif"));
			btnSearch.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnQueryD.gif"));
			btnSearch.setToolTipText("Tìm kiếm");
			btnSearch.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doSearch();
				}
			});
		
			this.add(btnSearch);
		}

		protected void doSearch() {
			String sql="";
				if((txtSothe.getText().equals(""))&&(sfxuong.getSelectedIndex()==-1)){
					Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK,
							"Chưa chọn thông tin cần tìm..");
					return;
				}
				if(!txtSothe.getText().equals("")){
					sql+=" o.EMPSN='"+txtSothe.getText()+"'";
					lbl_info.setVisible(false);
				}				
				else{
					sql+=" o.EMPSN in(select a.EMPSN from N_LABOUR a,N_EMPLOYEE e," +
					" N_DEPARTMENT d where e.EMPSN=a.EMPSN and d.ID_DEPT=e.DEPSN ";
					if(sfxuong.getSelectedIndex()!=-1){
						sql+=" and d.NAME_FACT='"+sfxuong.getSelectedItem().toString()+"'";
					}
					if(sfnhom.getSelectedIndex()!=-1){
						sql+="and d.NAME_GROUP='"+ListBinder.get(sfnhom)+"'";
					}
					if(sfdonvi.getSelectedIndex()!=-1){
						sql+="and d.ID_DEPT='"+ListBinder.get(sfdonvi)+"'";
					}
					sql+=") and o.CLOCK=1";
				}
				ProgramCondition pc = new ProgramCondition(sql, new Object[]{});
				program.setQueryCondition(pc);
				program.refresh();
			}			
		
	}
	@Override
	protected void switchContent(int view) {
		if (mainSplitPane.getComponentCount() > 1) {
			if (((view == 0) && (mainSplitPane.getComponent(1) == splitPane))
					|| ((view == 1) && (mainSplitPane.getComponent(1) == this))) {
				return;
			}
			mainSplitPane.remove(1);
		}
		switch (view) {
		case 0:
			mainSplitPane.add(splitPane);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, false);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, true);
			//getMasterToolbar().refresh();
			break;
		case 1:
			mainSplitPane.add(this);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_BROWSER, true);
			//getMasterToolbar().addUserDefineRight(MasterToolbar.CMD_CONTENT, false);
			getMasterToolbar().refresh();
			break;
		}
		//if (view==0) doBrowserContentRefresh();
	}
	@Override
	public int refresh() {
		int ret = super.refresh();
		//int a=getBrowserContent().getBrowserTable().getModel().getRowCount();
		count= getBrowserContent().getBrowserNav().getModel().getTotalRows();
		//System.out.println(count);
		if(count>0){
			if(txtSothe.getText().equals("")){
				lbl_info.setText("Có "+count+" hợp đồng.");
				lbl_info.setVisible(true);
				
			}
			//l.ShowMessageOK(count+"");
		}else{
			lbl_info.setVisible(false);
			l.ShowMessageInfo("Không tìm thấy dữ liệu..");
		}
		return ret;
	}
	
	
@Override
	public boolean doDelete() {
	// TODO Auto-generated method stub
	N_LABOUR data=(N_LABOUR) getMasterDataContent().getDataObject();
	Vni2Uni c=new Vni2Uni();
		boolean check=l.check_khoaHDLD(data.getEMPSN(), data.getID_LABOUR());
		boolean check_sub=l.check_exits("n_sub_labour", "id_labour", data.getID_LABOUR());
		if(check){
			l.ShowMessageError("HĐLĐ đã khoá không thể xoá.");
			return false;
		}else if(check_sub){
			l.ShowMessageError("HĐLĐ này đã có PLHĐ kèm theo không thể xoá.");
			return false;
		}
		else{
			boolean ok= l.xoa_HDLD(data.getEMPSN(), data.getID_LABOUR());
			if(!ok){
				return false;
			}else{
				String  note="Xoá HD"+data.getID_LABOUR()+"_"+data.getDATE_S();
				FvLogger.log(data.getEMPSN(),"DELETE","N_LABOUR",c.convertToVNI(note));
			}
		}
		
	
	return super.doDelete();
}
	/* (non-Javadoc)
	 * @see dsc.echo2app.program.DefaultProgram#doInitProgramOK()
	 */
	@Override
	protected void doInitProgramOK() {
		//<初始時是否撈取資料>
		//如果要一執行程式時就取出資料，則執行下行程式
		//this.refresh();	//取出資料必更新畫面
	}

	@Override
	protected QueryPane createNormalQuery() {
		return new nLabourMQuery();
	}
	
	@Override
	protected void doRefresh() {
		if(mainSplitPane.getComponent(1) == splitPane) {
			//瀏覽頁面
			this.doBrowserContentRefresh();
		}
		else {
			//明細頁面
			//取得目前所選取的資料指標
			int recNo = getBrowserContent().getCurrentSelectRowNo();
			//重查該筆資料，並顯示該資料內容
			doDataContentRefresh(recNo);
		}
	}

	/* 
	 * 調整UI Layout
	 */
	@Override
	protected void doLayout() {
		super.doLayout();
		windowPane.removeAll();
		mainSplitPane.removeAll();
		windowPane.add(mainSplitPane);
		mainSplitPane.add(getMasterToolbar());
		mainSplitPane.add(splitPane);
		splitPane.add(searchRow);
		//splitPane.add(browser);
		splitPane.add(getBrowserContent());
		mdSplitPane.setSeparatorPosition(new Extent(100));
		
		
		luutv = new Button();
		luutv.setRolloverEnabled(true);
		ResourceImageReference imageReference1 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_luuTV.png");
		luutv.setIcon(imageReference1);
		ResourceImageReference imageReference4 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_luuTV_ro.png");
		luutv.setRolloverIcon(imageReference4);
		luutv.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				
				luu_cnth info2 = new luu_cnth(_main);
				Application.getApp().getDefaultWindow().getContent().add(info2);
			}
		});
		getMasterToolbar().add(luutv);
		
		kyhd = new Button();
		kyhd.setRolloverEnabled(true);
		
		ResourceImageReference imageReference = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_kyHD_2.png");
		ResourceImageReference imageReference3 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_kyHD_2_ro.png");
		kyhd.setRolloverIcon(imageReference3);
		kyhd.setIcon(imageReference);
		kyhd.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				
				hdld_au	info = new hdld_au(_main);
				Application.getApp().getDefaultWindow().getContent().add(info);
			}
		});
		getMasterToolbar().add(kyhd);
		
		kypl = new Button();
		kypl.setRolloverEnabled(true);
		ResourceImageReference imageReference2 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_kyPL_2.png");
		kypl.setIcon(imageReference2);
		
		ResourceImageReference imageReference5 = new ResourceImageReference(
		"/dsc/echo2app/resource/image/a_kyPL_2_ro.png");
		kypl.setRolloverIcon(imageReference5);
		kypl.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				
				plhd_au info3 = new plhd_au(_main);
				Application.getApp().getDefaultWindow().getContent().add(info3);
			}
		});
		getMasterToolbar().add(kypl);
	}

	
	/*
	 * 資料瀏覽頁面所亦顯示的欄位定義
	 * 
	 * @see dsc.echo2app.program.MaintainSProgram#doInit()
	 */
	@Override
	protected String[] getBrowserDisplayColumns() {
		//4.<資料瀏覽欄位>
        //return null;
		return new String[]{"EMPSN","DEPT","ID_LABOUR","DATE_S","EXPIRE","SALARY","POSS","LIMIT","TIMES","NOTE","CLOCK"};
	}
}
