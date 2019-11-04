package fv.components;

import java.util.ArrayList;
import java.util.List;
//import java.util.ResourceBundle;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ResourceImageReference;
import dsc.echo2app.component.DscField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.Alignment;

public class AnyRowNavigator extends Row implements ActionListener {
	private static final long serialVersionUID = 1L;
//	private ResourceBundle resourceBundle;
	private Button btnFirst;
	private Button btnPrevious;
	private Label lblTotalRecord;
	private Button btnNext;
	private Button btnLast;
	private DscField txtCurrentRecord;
	private List<ActionListener> listAction;
	private int totalRow=0;
	private int currentRecord;

	/**
	 * Creates a new <code>DataObjectSetRowNavigator</code>.
	 */
	public AnyRowNavigator() {
		super();

		// Add design-time configured components.
		initComponents();
		doInit();
	}
	
	protected int doInit(){
		btnFirst.addActionListener(this);
		btnPrevious.addActionListener(this);
		txtCurrentRecord.addActionListener(this);
		btnNext.addActionListener(this);
		btnLast.addActionListener(this);
		return 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (listAction!=null)
		for (ActionListener action:listAction)
			action.actionPerformed(e);
	}
	
	public void addActionListener(ActionListener action){
		if (listAction==null) listAction = new ArrayList<ActionListener>();
		listAction.add(action);
	}
	
	public void setTotalRow(int totalRow){
		this.totalRow=totalRow;
	}
	
	public void reset(){
		lblTotalRecord.setText(String.valueOf(totalRow));
		txtCurrentRecord.setText(String.valueOf(currentRecord));
		if (totalRow<=1){
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
			txtCurrentRecord.setEnabled(false);
		}else{
			txtCurrentRecord.setEnabled(true);
		}
		
		if (currentRecord<=1){
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
		}else{
			btnFirst.setEnabled(true);
			btnPrevious.setEnabled(true);
		}
		
		if (currentRecord==totalRow){
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
		}else{
			btnNext.setEnabled(true);
			btnLast.setEnabled(true);
		}
		
	}
	
	public void setCurrentRecord(int currentRecord){
		this.currentRecord=currentRecord;
	}
	
	public int getCurrentRecord(){
		return this.currentRecord;		
	}
	
	public int getTotalRecord(){
		return this.totalRow;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Toolbar");
		this.setCellSpacing(new Extent(1, Extent.PX));
		btnFirst = new Button();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_first.png");
		btnFirst.setIcon(imageReference1);
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_first_d.png");
		btnFirst.setDisabledIcon(imageReference2);
		btnFirst.setActionCommand("first");
		add(btnFirst);
		btnPrevious = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_previous.png");
		btnPrevious.setIcon(imageReference3);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_previous_d.png");
		btnPrevious.setDisabledIcon(imageReference4);
		btnPrevious.setActionCommand("previous");
		add(btnPrevious);
		txtCurrentRecord = new DscField();
		txtCurrentRecord.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		txtCurrentRecord.setText("10000");
		txtCurrentRecord.setInputType(DscField.INPUT_TYPE_POSITIVE_INTEGER);
		txtCurrentRecord.setWidth(new Extent(35, Extent.PX));
		txtCurrentRecord.setActionCommand("recnumber");
		add(txtCurrentRecord);
		Label label1 = new Label();
		label1.setText("/");
		add(label1);
		lblTotalRecord = new Label();
		lblTotalRecord.setText("10000");
		add(lblTotalRecord);
		btnNext = new Button();
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_next.png");
		btnNext.setIcon(imageReference5);
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_next_d.png");
		btnNext.setDisabledIcon(imageReference6);
		btnNext.setActionCommand("next");
		add(btnNext);
		btnLast = new Button();
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_last.png");
		btnLast.setIcon(imageReference7);
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/resultset_last_d.png");
		btnLast.setDisabledIcon(imageReference8);
		btnLast.setActionCommand("last");
		add(btnLast);
	}
}
