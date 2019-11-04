package ds.program.fvhr.ui.workpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextapp.echo2.app.Grid;
import dsc.echo2app.Application;
import dsc.echo2app.MessageDialog;
import dsc.echo2app.program.QueryNormal2;
import dsc.echo2app.program.DefaultProgram.ProgramCondition;
import dsc.echo2app.query.ConditionManager;
import nextapp.echo2.app.Label;
import dsc.echo2app.component.DscField;
import dsc.echo2app.component.DscDateField;

public class WorkPointCorrection01MQuery extends QueryNormal2 {

    private nextapp.echo2.app.Grid rootLayout;
	private DscField txtEmpsn;
	private DscDateField dfDate;
	private DscDateField dfToDate;
	/**
	 * Creates a new <code>WorkPointCorrection01MQuery</code>.
	 */
	public WorkPointCorrection01MQuery() {
		super();
		initComponents();
		dfDate.getDateChooser().setLocale(new Locale("en"));
		dfToDate.getDateChooser().setLocale(new Locale("en"));
		dfDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfToDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDate.getTextField().setText("");
		dfToDate.getTextField().setText("");
	}

	@Override
	protected int doInit() {
		int ret = super.doInit();
		ConditionManager conditionMgr = new ConditionManager();
		setConditionMgr(conditionMgr);


	    //使用者可以在以下區域撰寫註冊各元件Property Editor的程式碼
        //TODO 請修改元件之PropertyEditor

		//<一般查詢條件定義>

        String key;
		return ret;

	}
	
	@Override
	protected void doQuery() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String empsn = txtEmpsn.getText();
		String sql = "";
		List<Object> params = new ArrayList<Object>();
		if (!empsn.equals("")){
			if (!empsn.matches("[0-9]{8}")){
				Application.getApp().showMessageDialog(MessageDialog.TYPE_ERROR+MessageDialog.CONTROLS_OK, "Số thẻ không hợp lệ");
				return;
			}
			sql = "o.EMPSN=?";
			params.add(empsn);
		}else{
			sql = "o.EMPSN=''";
		}
		
		if (!dfDate.getText().equals("")&&!dfToDate.getText().equals("")){
			sql = sql + " and o.DATE_LOST>=? and o.DATE_LOST<=?";			
			try {
				Date d1 = sdf.parse(dfDate.getText());
				Date d2 = sdf.parse(dfToDate.getText());
				params.add(d1);
				params.add(d2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (!dfDate.getText().equals("")){
			sql = sql + " and o.DATE_LOST=?";
			try {
				Date d1 = sdf.parse(dfDate.getText());
				params.add(d1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (sql.equals("")) sql = "1<>1";
		ProgramCondition pc = new ProgramCondition(sql, params.toArray());
		getProgram().setQueryCondition(pc);
		getProgram().refresh();
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
		Label label1 = new Label();
		label1.setText("Số thẻ");
		rootLayout.add(label1);
		txtEmpsn = new DscField();
		txtEmpsn.setMaximumLength(8);
		rootLayout.add(txtEmpsn);
		Label label2 = new Label();
		label2.setText("Từ ngày");
		rootLayout.add(label2);
		dfDate = new DscDateField();
		rootLayout.add(dfDate);
		Label label3 = new Label();
		label3.setText("Đến ngày");
		rootLayout.add(label3);
		dfToDate = new DscDateField();
		rootLayout.add(dfToDate);
	}
}
