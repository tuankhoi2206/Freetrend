package ds.program.fvhr.ui.workpoints;

import java.util.Calendar;
import java.util.ResourceBundle;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;
import fv.util.ListBinder;
import fv.util.MappingPropertyUtils;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.FillImage;

public class DataCheckPane extends WindowPane {

	private ResourceBundle resourceBundle;
	private SelectField sfFrom;
	private SelectField sfTo;
	private Label lblMonth;
	private CheckBox chkOption;
	private Label lblTitle;
	private Label lblDaysOfMonth;
	private Button btnSearch;
	private Button btnExcel;
	private SplitPane splitPane1;
	/**
	 * Creates a new <code>DataCheckPane</code>.
	 */
	public DataCheckPane() {
		super();

		// Add design-time configured components.
		initComponents();
		
	}
	
	public DataCheckPane(NGetData00MProgram program){
		this();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, Integer.parseInt(program.getMonth())-1);
		c.set(Calendar.YEAR, Integer.parseInt(program.getYear()));		
		int month = c.get(Calendar.MONTH);
		ListBinder.bindSelectField(sfFrom, MappingPropertyUtils
				.getDayEditor(month), true);
		sfFrom.setSelectedIndex(0);
		ListBinder.bindSelectField(sfTo, MappingPropertyUtils
				.getDayEditor(month), true);
		sfTo.setSelectedIndex(sfTo.getModel().size()-1);
		lblDaysOfMonth.setText(String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH)));
		lblMonth.setText(program.getMonth()+"/"+program.getYear());
	}
	
	private void upDays(ActionEvent e) {
		//TODO Implement.
	}
	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Default.Window");
		this.setTitle("Kiểm tra dữ liệu quét thẻ");
		this.setHeight(new Extent(500, Extent.PX));
		this.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
		this.setWidth(new Extent(600, Extent.PX));
		this.setModal(true);
		splitPane1 = new SplitPane();
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/SplitVertBar.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setResizable(true);
		add(splitPane1);
		Column column1 = new Column();
		column1
				.setInsets(new Insets(new Extent(5, Extent.PX), new Extent(0,
						Extent.PX), new Extent(0, Extent.PX), new Extent(0,
						Extent.PX)));
		column1.setCellSpacing(new Extent(5, Extent.PX));
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(5, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setText("Từ ngày");
		row1.add(label1);
		sfFrom = new SelectField();
		sfFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDays(e);
			}
		});
		row1.add(sfFrom);
		Label label2 = new Label();
		label2.setText("Đến ngày");
		row1.add(label2);
		sfTo = new SelectField();
		sfTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDays(e);
			}
		});
		row1.add(sfTo);
		lblMonth = new Label();
		lblMonth.setText("()");
		row1.add(lblMonth);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(5, Extent.PX));
		column1.add(row3);
		lblTitle = new Label();
		lblTitle.setText("Số ngày phải làm: ");
		lblTitle.setForeground(new Color(0x008080));
		row3.add(lblTitle);
		lblDaysOfMonth = new Label();
		lblDaysOfMonth.setText("31");
		lblDaysOfMonth.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		lblDaysOfMonth.setForeground(Color.RED);
		row3.add(lblDaysOfMonth);
		Row row2 = new Row();
		column1.add(row2);
		chkOption = new CheckBox();
		chkOption.setText("Chỉ liệt kê dữ liệu chênh lệch");
		row2.add(chkOption);
		Row row4 = new Row();
		row4.setCellSpacing(new Extent(5, Extent.PX));
		column1.add(row4);
		btnSearch = new Button();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnQuery.gif");
		btnSearch.setIcon(imageReference2);
		btnSearch.setText("Tìm kiếm");
		btnSearch.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnSearch.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnSearch.setBackground(new Color(0xc0c0c0));
		btnSearch.setPressedEnabled(true);
		btnSearch.setPressedForeground(Color.GREEN);
		btnSearch.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004080), Border.STYLE_SOLID));
		btnSearch.setRolloverForeground(new Color(0xff8000));
		btnSearch.setRolloverEnabled(true);
		btnSearch.setForeground(new Color(0x004000));
		row4.add(btnSearch);
		btnExcel = new Button();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/dsc/echo2app/resource/image/btnExcel.gif");
		btnExcel.setIcon(imageReference3);
		btnExcel.setEnabled(false);
		btnExcel.setText("Xuất ra Excel");
		btnExcel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		btnExcel.setPressedForeground(Color.GREEN);
		btnExcel.setBackground(new Color(0xc0c0c0));
		btnExcel.setInsets(new Insets(new Extent(3, Extent.PX)));
		btnExcel.setPressedEnabled(true);
		btnExcel.setBorder(new Border(new Extent(1, Extent.PX), new Color(
				0x004080), Border.STYLE_SOLID));
		btnExcel.setRolloverForeground(new Color(0xff8000));
		btnExcel.setForeground(new Color(0x004000));
		btnExcel.setRolloverEnabled(true);
		btnExcel.setDisabledForeground(new Color(0x808080));
		row4.add(btnExcel);
	}
}
