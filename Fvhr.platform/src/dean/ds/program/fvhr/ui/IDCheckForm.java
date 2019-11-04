package ds.program.fvhr.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import fv.components.MessagePane;
import fv.util.ListBinder;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import ds.program.fvhr.domain.N_EMPLOYEE;
import dsc.echo2app.component.DscDateField;
import dsc.echo2app.propertyeditors.MappingPropertyEditor;
import echopointng.ComboBox;

public class IDCheckForm extends WindowPane {

	private ResourceBundle resourceBundle;
	private Button btnCheck;
	private Employee01MDataContent dataContent;
	private MessagePane message;
	private ContentPane contentPane1;
	private TextField txtId;
	private DscDateField dfDate;
	private ComboBox cboDept;
	private MappingPropertyEditor map;
	private Object depsnObj;
	/**
	 * Creates a new <code>IDCheckForm</code>.
	 */
	public IDCheckForm(Employee01MDataContent dataContentt) {
		super();

		// Add design-time configured components.
		initComponents();
		message = new MessagePane();
		contentPane1.add(message);
		dfDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		dfDate.setSelectedDate(Calendar.getInstance());
		this.dataContent =dataContentt;
		map =  dataContent.getDepsnEditor();
		ListBinder.bindComboBox(cboDept, map);//dataContent.getDepsnEditor());
		//depsnObj = ListBinder.getCboValue(cboDept, map);//dataContent.getDepsnEditor());
	}

	private void doCheck(ActionEvent e) {
		contentPane1.removeAll();
		message = new MessagePane();
		contentPane1.add(message);
		if (!txtId.getText().matches("[0-9]{9}")){
			message.addMessage("CMND không hợp lệ (9 ký tự số)", MessagePane.MSG_ERROR);
			return;
		}
		dataContent = new Employee01MDataContent();
		Object depsnObj = ListBinder.getCboValue(cboDept, map);//dataContent.getDepsnEditor());
		if (depsnObj==null){
//			message.addMessage("Chọn đơn vị", MessagePane.MSG_ERROR);
//			return;
			depsnObj="00000";
		}		
		if (!dataContent.initPolicy()){
			message.addMessage(dataContent.getErrorMessage(), MessagePane.MSG_ERROR);
			return;
		}
		N_EMPLOYEE data = new N_EMPLOYEE();
		data.setID_NO(txtId.getText());
		data.setDATE_HIRED(dfDate.getSelectedDate().getTime());
		if (depsnObj!=null)
			data.setDEPSN(depsnObj.toString());
		
		boolean checkCMND = true;
		boolean checkOk = true;
		checkCMND= dataContent.getLt1Policy().isValid(data, false);		
		message.addMessage(dataContent.getLt1Policy().getErrorMessage(), MessagePane.MSG_ERROR);
		// if LT1 hop le thi checkOK=true, hop le
		checkOk = checkCMND?checkCMND:false;		
		for (NewEmployeePolicy p:dataContent.getPp()){
			checkCMND = p.isValid(data, false);			
			message.addMessage(p.getErrorMessage(), MessagePane.MSG_ERROR);	
			System.out.println(p.getErrorMessage());
			//if LT1 ok, va co bat ky 1 xuong nao ko ok thi se thong bao o day
			if(checkOk){
				checkOk=!checkCMND?checkCMND:checkOk;				
			}
		}
		
		if(checkOk)
			message.addMessage("Số CMND " + txtId.getText() + " hợp lệ", MessagePane.MSG_SUCCESS);
		else message.addMessage("Số CMND " + txtId.getText() + " không hợp lệ", MessagePane.MSG_ERROR);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Kiểm tra CMND");
		this.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		this.setModal(true);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setSeparatorPosition(new Extent(85, Extent.PX));
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		add(splitPane1);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(1, Extent.PX)));
		SplitPaneLayoutData grid1LayoutData = new SplitPaneLayoutData();
		grid1LayoutData.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.setLayoutData(grid1LayoutData);
		grid1.setSize(3);
		splitPane1.add(grid1);
		Label label4 = new Label();
		label4.setText("Đơn vị");
		grid1.add(label4);
		cboDept = new ComboBox();
		cboDept.setPopUpAlwaysOnTop(true);
		grid1.add(cboDept);
		Label label5 = new Label();
		grid1.add(label5);
		Label label2 = new Label();
		label2.setText("Ngày nhập xưởng");
		grid1.add(label2);
		dfDate = new DscDateField();
		grid1.add(dfDate);
		Label label3 = new Label();
		grid1.add(label3);
		Label label1 = new Label();
		label1.setText("CMND:");
		grid1.add(label1);
		txtId = new TextField();
		txtId.setMaximumLength(9);
		txtId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCheck(e);
			}
		});
		grid1.add(txtId);
		btnCheck = new Button();
		btnCheck.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnCheck.setText("Kiểm tra");
		btnCheck.setInsets(new Insets(new Extent(2, Extent.PX)));
		btnCheck.setBackground(new Color(0xc0c0c0));
		btnCheck.setPressedEnabled(true);
		btnCheck.setPressedForeground(new Color(0x800040));
		btnCheck.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004080), Border.STYLE_SOLID));
		btnCheck.setForeground(new Color(0x004080));
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCheck(e);
			}
		});
		grid1.add(btnCheck);
		contentPane1 = new ContentPane();
		splitPane1.add(contentPane1);
	}
}
