package ds.program.fvhr.baby.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import ds.program.fvhr.baby.tools.CheckRule;
import ds.program.fvhr.domain.N_CHANGE_ICCARD;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import dsc.echo2app.program.DataContent;
import dsc.echo2app.program.IProgram;
import dsc.echo2app.propertyeditors.PropertyEditors;
import fv.util.JdbcDAO;
import fv.util.VniEditor;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.component.DscDateField;

public class N_CHANGE_ICCARD01MDataContent extends DataContent {

	private nextapp.echo2.app.Grid rootLayout;
	private nextapp.echo2.app.Label EMPSN_CaptionLabel;
	private dsc.echo2app.component.DscField EMPSN_DscField1;
	private nextapp.echo2.app.Label EMPCN_NEW_CaptionLabel;
	private dsc.echo2app.component.DscField EMPCN_NEW_DscField2;
	private nextapp.echo2.app.Label EMPCN_OLD_CaptionLabel;
	private dsc.echo2app.component.DscField EMPCN_OLD_DscField3;
	private nextapp.echo2.app.Label DATE_CHANGE_CaptionLabel;
	private dsc.echo2app.component.DscDateField DATE_CHANGE_DscDateField1;
	private nextapp.echo2.app.Label REASON_CaptionLabel;
	private dsc.echo2app.component.DscField REASON_DscField4;
	private nextapp.echo2.app.Label TEMP_CaptionLabel;
	private dsc.echo2app.component.DscField TEMP_DscField5;
	private fv.util.JdbcDAO dao;

	/**
	 * Creates a new <code>N_CHANGE_ICCARD01MDataContent</code>.
	 */
	public N_CHANGE_ICCARD01MDataContent() {
		super();
		initComponents();
		DATE_CHANGE_DscDateField1.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
	}

	@Override
	public void doUIRefresh() {
		super.doUIRefresh();
		if ((this.getProgram().getDataMode() == IProgram.DATAMODE_NONE)
				|| (this.getProgram().getDataMode() == IProgram.DATAMODE_BROWSER)) {
			rootLayout.setEnabled(false);
		} else {
			rootLayout.setEnabled(true);
			if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				EMPSN_DscField1.setEnabled(true);
				EMPCN_NEW_DscField2.setEnabled(true);
				DATE_CHANGE_DscDateField1.setEnabled(true);
				EMPCN_OLD_DscField3.setEnabled(false);
			} else {
				EMPSN_DscField1.setEnabled(false);
				EMPCN_NEW_DscField2.setEnabled(false);
				DATE_CHANGE_DscDateField1.setEnabled(false);
				EMPCN_OLD_DscField3.setEnabled(false);
			}
		}

		getUIDataObjectBinder().doDataBindUIStyle();
	}

	public Class getDataObjectClass() {
		return N_CHANGE_ICCARD.class;
	}

	// Kiểm tra đối tượng trước khi save vào database
	@Override
	public boolean checkDataObject() {
		boolean ret = super.checkDataObject();
		if (!CheckRule.checkRuleManager(EMPSN_DscField1.getText())) {
			setErrorMessage("Bạn không có quyền thao tác trên số thẻ này !");
			return false;
		} else {
			Date date_change = DATE_CHANGE_DscDateField1.getSelectedDate()
					.getTime();
			int check = N_CHANGE_ICCARDprocess.checkRule(
					EMPSN_DscField1.getText(), date_change,
					EMPCN_NEW_DscField2.getText());
			if (check != 0
					&& this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
				if (check == 1)
					setErrorMessage("Ngày thay đổi phải lớn hơn ngày thay đổi gần đây nhất");
				if (check == 2)
					setErrorMessage("Số thẻ IC và ngày thay đổi này đã tồn tại");
				if (check == 3 || check == 4)
					setErrorMessage("Số thẻ IC này không tồn tại, bị mất hoặc đã hư \n Bạn nên kiểm tra lại trong phần thẻ IC");
				return false;
			} else {

				return ret;
			}
		}
	}

	@Override
	public boolean delete(int recNo) {
		// TODO Auto-generated method stub
		return super.delete(recNo);
	}
	
	@Override
	protected int doInit() {
		int nRet = super.doInit();
		
		dao = new JdbcDAO();
		registPropertyEditor();

		bindUI();

		return nRet;
	}

	private void registPropertyEditor() {

		getUIDataObjectBinder().registerCustomEditor(N_CHANGE_ICCARD.class,
				"REASON", new VniEditor());
		getUIDataObjectBinder().registerCustomEditor(N_CHANGE_ICCARD.class,
				"DATE_CHANGE", PropertyEditors.createDateEditor("dd/MM/yyyy"));
	}

	private void bindUI() {

		getUIDataObjectBinder().addBindMap("EMPSN", EMPSN_DscField1,
				EMPSN_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EMPCN_NEW", EMPCN_NEW_DscField2,
				EMPCN_NEW_CaptionLabel);
		getUIDataObjectBinder().addBindMap("EMPCN_OLD", EMPCN_OLD_DscField3,
				EMPCN_OLD_CaptionLabel);
		getUIDataObjectBinder().addBindMap("DATE_CHANGE",
				DATE_CHANGE_DscDateField1, DATE_CHANGE_CaptionLabel);
		getUIDataObjectBinder().addBindMap("REASON", REASON_DscField4,
				REASON_CaptionLabel);
		getUIDataObjectBinder().addBindMap("TEMP", TEMP_DscField5,
				TEMP_CaptionLabel);

	}

	@Override
	protected void doLayout() {
		super.doLayout();

	}
	

	@Override
	public void beforeSaveToDataObjectSet() {

		if (this.getProgram().getDataMode() == IProgram.DATAMODE_NEW) {
			autoPrimaryKeyValue();
		}
	}

	private void doEnter(ActionEvent e) {
		String empsn = EMPSN_DscField1.getText()==null?"":EMPSN_DscField1.getText();
		String sql = "select nvl(t.EMPCN,0) from N_EMP_ICCARD t where " +
				" t.BEGIN_DATE = (select max(e.BEGIN_DATE) from N_EMP_ICCARD e" +
				" where e.EMPSN = t.EMPSN and e.EMPSN = ? and e.USE_STATUS = '1')" ;
		try{
		String EMPCN_OLD =(String) dao.getJdbcTemplate().queryForObject(sql,new Object[]{empsn},String.class);
		EMPCN_OLD_DscField3.setText(EMPCN_OLD);
		}catch (Exception ex) {
			EMPCN_OLD_DscField3.setText("");
		}

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		rootLayout = new Grid();
		rootLayout.setWidth(new Extent(100, Extent.PERCENT));
		rootLayout.setSize(4);
		add(rootLayout);
		EMPSN_CaptionLabel = new Label();
		EMPSN_CaptionLabel.setText("N_CHANGE_ICCARD.EMPSN");
		rootLayout.add(EMPSN_CaptionLabel);
		EMPSN_DscField1 = new DscField();
		EMPSN_DscField1.setId("EMPSN_DscField1");
		EMPSN_DscField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEnter(e);
			}
		});
		rootLayout.add(EMPSN_DscField1);
		EMPCN_NEW_CaptionLabel = new Label();
		EMPCN_NEW_CaptionLabel.setText("N_CHANGE_ICCARD.EMPCN_NEW");
		rootLayout.add(EMPCN_NEW_CaptionLabel);
		EMPCN_NEW_DscField2 = new DscField();
		EMPCN_NEW_DscField2.setId("EMPCN_NEW_DscField2");
		rootLayout.add(EMPCN_NEW_DscField2);
		EMPCN_OLD_CaptionLabel = new Label();
		EMPCN_OLD_CaptionLabel.setText("N_CHANGE_ICCARD.EMPCN_OLD");
		rootLayout.add(EMPCN_OLD_CaptionLabel);
		EMPCN_OLD_DscField3 = new DscField();
		EMPCN_OLD_DscField3.setId("EMPCN_OLD_DscField3");
		rootLayout.add(EMPCN_OLD_DscField3);
		DATE_CHANGE_CaptionLabel = new Label();
		DATE_CHANGE_CaptionLabel.setText("N_CHANGE_ICCARD.DATE_CHANGE");
		rootLayout.add(DATE_CHANGE_CaptionLabel);
		DATE_CHANGE_DscDateField1 = new DscDateField();
		DATE_CHANGE_DscDateField1.setId("DATE_CHANGE_DscDateField1");
		rootLayout.add(DATE_CHANGE_DscDateField1);
		REASON_CaptionLabel = new Label();
		REASON_CaptionLabel.setText("N_CHANGE_ICCARD.REASON");
		rootLayout.add(REASON_CaptionLabel);
		REASON_DscField4 = new DscField();
		REASON_DscField4.setId("REASON_DscField4");
		rootLayout.add(REASON_DscField4);
		TEMP_CaptionLabel = new Label();
		TEMP_CaptionLabel.setText("N_CHANGE_ICCARD.TEMP");
		rootLayout.add(TEMP_CaptionLabel);
		TEMP_DscField5 = new DscField();
		TEMP_DscField5.setId("TEMP_DscField5");
		rootLayout.add(TEMP_DscField5);
	}

}
