package fv.components;

import dsc.echo2app.component.DscField;
import dsc.echo2app.component.table.PageableSortableTableModel;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class RowNavigator extends Row{
	private static final long serialVersionUID = 1L;

	private JdbcBrowserContent browserContent;
	
	private JdbcDataContent dataContent;

	private ResourceImageReference firstImg;

	private ResourceImageReference firstImgD;

	private ResourceImageReference previousImg;

	private ResourceImageReference previousImgD;

	private ResourceImageReference nextImg;

	private ResourceImageReference nextImgD;

	private ResourceImageReference lastImg;

	private ResourceImageReference lastImgD;

	private Button btnFirst;

	private Button btnPrevious;

	private Button btnNext;

	private Button btnLast;

	private DscField txtCurrentRecord;

	private Label lblTotalRecord;

	private DscField txtEmpsnSearch;
		
	public RowNavigator(JdbcBrowserContent browserContent){
		super();
		this.browserContent=browserContent;
		initComponents();
		reset();
	}
	
	public PageableSortableTableModel getModel(){
		return browserContent.getModel();
	}
	
	public void setDataContent(JdbcDataContent dataContent){
		this.dataContent=dataContent;
	}
	
	public JdbcDataContent getDataContent(){
		return this.dataContent;
	}
	
	private void next(){
		int index = browserContent.getTable().getSelectionModel().getMaxSelectedIndex();
		int page = getModel().getCurrentPage();
		int realIndex = page*getModel().getRowsPerPage()+index;
		realIndex++;
		if (realIndex%getModel().getRowsPerPage()==0){//start new page
			page++;
			getModel().setCurrentPage(page);
			browserContent.getTable().getSelectionModel().setSelectedIndex(0, true);
		}else{
			browserContent.getTable().getSelectionModel().setSelectedIndex(index+1, true);
		}
		browserContent.refreshRowNo();
		reset();
	}
	
	private void previous(){
		int index = browserContent.getTable().getSelectionModel().getMaxSelectedIndex();
		int page = getModel().getCurrentPage();
//		int realIndex = page*getModel().getRowsPerPage()+index;
		index--;
		if (index==-1){//start new page
			page--;
			getModel().setCurrentPage(page);
			browserContent.getTable().getSelectionModel().setSelectedIndex(getModel().getRowsPerPage()-1, true);
		}else{
			browserContent.getTable().getSelectionModel().setSelectedIndex(index, true);
		}
		browserContent.refreshRowNo();
		reset();
	}
	
	private void last(){
		int lastIndex = getModel().getRows().size()%getModel().getRowsPerPage()-1;
		getModel().setCurrentPage(getModel().getTotalPages()-1);
		browserContent.getTable().getSelectionModel().setSelectedIndex(lastIndex, true);
		browserContent.refreshRowNo();
		reset();
	}
	
	private void first(){
		getModel().setCurrentPage(0);
		browserContent.getTable().getSelectionModel().setSelectedIndex(0, true);
		browserContent.refreshRowNo();
		reset();
	}
	
	public void reset(){
		int index = browserContent.getTable().getSelectionModel().getMaxSelectedIndex();
		int page = getModel().getCurrentPage();
		int realIndex = page*getModel().getRowsPerPage()+index;
		txtCurrentRecord.setText(String.valueOf(realIndex+1));
		lblTotalRecord.setText(String.valueOf(getModel().getRows().size()));
		if (getModel().getRows().size()<=1){
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
			return;
		}else{
			if (browserContent.getTable().getSelectionModel().getMaxSelectedIndex()==0&&getModel().getCurrentPage()==0){
				btnPrevious.setEnabled(false);
				btnFirst.setEnabled(false);
			} else {
				btnPrevious.setEnabled(true);
				btnFirst.setEnabled(true);
			}
			if (getModel().getCurrentPage()==getModel().getTotalPages()-1 && browserContent.getTable().getSelectionModel().getMaxSelectedIndex()==(getModel().getRows().size()-1)%getModel().getRowsPerPage()){
				btnNext.setEnabled(false);
				btnLast.setEnabled(false);
			} else {
				btnNext.setEnabled(true);
				btnLast.setEnabled(true);
			}
		}
		browserContent.getDscNavigator().reset();
		dataContent.refetch();
	}
	
	private void initComponents(){
		setStyleName("Default.Toolbar");
		firstImg = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_first.png");
		firstImgD = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_first_d.png");
		previousImg = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_previous.png");
		previousImgD = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_previous_d.png");
		nextImg = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_next.png");
		nextImgD = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_next_d.png");
		lastImg = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_last.png");
		lastImgD = new ResourceImageReference("/dsc/echo2app/resource/image/resultset_last_d.png");
		btnFirst = new Button();
		btnFirst.setIcon(firstImg);
		btnFirst.setDisabledIcon(firstImgD);
		btnFirst.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		add(btnFirst);
		btnPrevious = new Button();
		btnPrevious.setIcon(previousImg);
		btnPrevious.setDisabledIcon(previousImgD);
		btnPrevious.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});
		add(btnPrevious);
		//show current record
		txtCurrentRecord = new DscField(DscField.INPUT_TYPE_POSITIVE_INTEGER);		
		txtCurrentRecord.setWidth(new Extent(30));
		txtCurrentRecord.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtCurrentRecord.getText().equals("")){
					int requestRecNo = Integer.parseInt(txtCurrentRecord.getText());
					if (requestRecNo==0){
						browserContent.locale(1);
						reset();
					}else if (requestRecNo>getModel().getRows().size()){
						browserContent.locale(getModel().getRows().size());
						reset();
					} else {
						browserContent.locale(requestRecNo);
						reset();
					}
				}else{
					reset();
				}
			}
		});
		add(txtCurrentRecord);
		Label lblSlash = new Label("/");
		add(lblSlash);
		lblTotalRecord = new Label();
		add(lblTotalRecord);
		//
		btnNext = new Button();
		btnNext.setIcon(nextImg);
		btnNext.setDisabledIcon(nextImgD);
		btnNext.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		
		add(btnNext);
		btnLast = new Button();
		btnLast.setIcon(lastImg);
		btnLast.setDisabledIcon(lastImgD);
		btnLast.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				last();
			}
		});
		add(btnLast);
		Label lbl = new Label();
		lbl.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/toolbar_seperator.png"));
		add(lbl);
		txtEmpsnSearch = new DscField();
		txtEmpsnSearch.setWidth(new Extent(80));
		txtEmpsnSearch.setToolTipText("Gõ số thẻ và nhấn enter để tìm");
		txtEmpsnSearch.setMaximumLength(8);
		txtEmpsnSearch.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				browserContent.locale("EMPSN", txtEmpsnSearch.getText());
				reset();
			}
		});
		add(txtEmpsnSearch);
	}
}
