package fv.util;


import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import dsc.echo2app.special_dialog;
import dsc.echo2app.component.table.DscPageableSortableTable;
import dsc.echo2app.program.DetailContent;
import echopointng.table.PageableTableModel;

/**
 * A controller for tables containing 
 * <code>PageableTableModel</code> backed tables.
 * 
 * BUGBUG: Need to support internationalization
 * 
 */
public class Navigation extends Row {

	private ResourceBundle resourceBundle;
	private DscPageableSortableTable table;
	private PageableTableModel model;
	private Row searchRow;
    private static final Object[] ROWS_PER_PAGE_OPTIONS = new String[]{"10","25","50","100"};
    private static final List ROWS_PER_PAGE_LIST = Arrays.asList(ROWS_PER_PAGE_OPTIONS);
    private boolean showSearchRow=true;
    private boolean showSearchButton=true;
    private Extent searchTextFieldWidth;
    private ActionListener additionAction;

    
	//tigereye....20091030
	private DetailContent dc = null;
	private TextField searchTextField;
	private Button searchButton;
	public void setDetailContent(DetailContent dc){
		this.dc = dc;
	}
    
	public Navigation() {   
		setTable(new DscPageableSortableTable());
		doLayout();
	}
    
    public Navigation(DscPageableSortableTable table, int rowsPerPage){
    	setTable(table);
    	setRowsPerPage(rowsPerPage);
        doLayout();
    }
    
    /**
     * @see nextapp.echo2.app.Component#init()
     */
    public void init() {
        super.init();   //就是要呼叫就對啦!!!!!!!(tigereye)     
    }
    
    public void setTable(DscPageableSortableTable table) {
    	this.table = table;
    	setTableModel((PageableTableModel)table.getModel());//設定table的model
    	//開放搜尋功能							//取得table的model為getModel()
    	if (searchRow != null)
    		searchRow.setVisible(true);
    }
    private void setTableModel(PageableTableModel model) {//設定table的model
    	this.model = model;
    }
    
    public void setRowsPerPage(int rowsPerPage) {
    	resetRowsPerPage(rowsPerPage);
    }
    
    public void setShowSearchRow(boolean showSearchRow) {
		this.showSearchRow = showSearchRow;
	}
    
    public boolean isShowSearchRow() {
		return showSearchRow;
	}
    
    protected void doLayout() {
    	resourceBundle = ResourceBundle.getBundle(
				"dsc.echo2app.resource.localization.CommonMsg",
				ApplicationInstance.getActive().getLocale());
    	
        add(getFirstButton());     //到第一頁按鈕.
        add(getPreviousButton());  //到前一頁按鈕.
        Row pageInfoRow = new Row();
        pageInfoRow.setStyleName("DscPageNavigation.PageInfoRow");
        pageInfoRow.add(getPrePageLabel());
        pageInfoRow.add(getPageText());
        pageInfoRow.add(getInterPageLabel());
        pageInfoRow.add(getPageCountLabel());
        pageInfoRow.add(getPostPageLabel());
        add(pageInfoRow);        
        add(getNextButton());      //到下一頁按鈕.
        add(getLastButton());      //到最末頁按鈕.
        
//以上4個按鈕, 其getButton()的呼叫, 都會new 1個 Button 與new 1個 ActionListener,
//這是因為每換一頁都 重新 作Layout, 所以都要再new.
        
        if (showSearchRow){//Hieu 20111111 .... reuse without searchRow
        //搜尋功能
        searchRow = new Row();// tigereye:　貼在此row之上.
        searchTextField = new TextField();
        if (searchTextFieldWidth!=null) searchTextField.setWidth(searchTextFieldWidth);
        searchTextField.addActionListener(new ActionListener(){//hieu...20111114
			public void actionPerformed(ActionEvent e) {
				
				doSearch();
			}
        });
        if (additionAction!=null) searchTextField.addActionListener(additionAction);
        searchRow.add(new Label("Tìm kiếm"));
        searchRow.add(searchTextField);
        if (showSearchButton){
	        searchButton = new Button(new ResourceImageReference("/dsc/echo2app/resource/image/btnQuery.gif"));
	        searchRow.add(searchButton);
	        searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					doSearch();
				}
	        });
	        if (additionAction!=null) searchButton.addActionListener(additionAction);
        }
        add(searchRow);
        //dung cho DscPageableSortableTable nhung form tu design set--->true
        searchRow.setVisible(true);
        }
    }
    

    //tim vi tri cua doi tuong trong table
    public boolean find_index(String empsn,int col){
    	int page=model.getTotalPages();
    	int index=0;
    	boolean a=false;
    	while(index<page){
    		model.setCurrentPage(index);
    		for (int i = 0; i < model.getRowCount(); i++) {
    			//System.out.println(model.getValueAt(col, i));
    			Object obj=model.getValueAt(col, i);
				if(empsn.equals(obj.toString())){
					table.getSelectionModel().setSelectedIndex(i, true);
					a =true;
					return a;
				}else{
					a= false;
				}
			}
    		index++;
    	}
    	return a;
    }
    
    private void doSearch(){
    	
    	int col=table.getColumnModel().getColumnCount();
    	for (int i = 0; i < col; i++) {
    		
    			if(find_index(searchTextField.getText(), i)){
    				return;
    			}
    		}
    	special_dialog a=new special_dialog(special_dialog.TYPE_INFORMATION+special_dialog.CONTROLS_OK,
		"Không tìm thấy dữ liệu.");
    	model.setCurrentPage(0);
    	}
    //An show SearchTextField 04102013
    public TextField getSearchTextField(){
    	return this.searchTextField;
    }
    
    public Button getSearchButton(){
    	return this.searchButton;
    }
    
    public void setShowSearchButton(boolean value){
    	this.showSearchButton=value;
    }
    
    public void setSearchTextFieldWidth(Extent value){
    	this.searchTextFieldWidth=value;
    }
    
    public void setAdditionAction(ActionListener action){
    	this.additionAction=action;
    }
    //end...........extend
    public void reset() {
    	removeAll();
        doLayout();
    }
    
    public PageableTableModel getModel(){
        return model;
    }
    
    private SelectField getPageSelect() {
        String[] pages = new String[model.getTotalPages() + 1];
        for (int i=0; i<pages.length; i++){
            pages[i] = "" + (i+1);
        }
        
        SelectField select = new SelectField(pages);
        select.setSelectedIndex(model.getCurrentPage());
        select.addActionListener(getPageSelectListener());
        return select;
    }
    
    /////tigereye...20090526
    private TextField pageText;
    
    private TextField getPageText() {
    	TextField pageText = new TextField();
    	this.pageText = pageText;
    	pageText.setWidth(new Extent(30, Extent.PX));
    	pageText.setStyleName("DscPageNavigation.PageTextField");
    	pageText.addActionListener(getPageTextListener());
    	if (model.getTotalPages() > 0)//頁文字方塊 加1.    tigereye
    		pageText.setText(String.valueOf(model.getCurrentPage()+1));
    	else
    		pageText.setText("0");
    	return pageText;
    }
    
    /////tigereye...20090526
    public void setPageTextANDLabel(int pageNumber){
    	pageText.setText(pageNumber+"");
    }
    
    private ActionListener getPageTextListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int maxPage = model.getTotalPages();
            	int newPage;
            	TextField pageText = (TextField)e.getSource();
            	try {
            		newPage = Integer.parseInt(pageText.getText());
            		if ((newPage > 0) && (newPage <= maxPage))
            			model.setCurrentPage(newPage-1);
            	}
            	catch (Exception e2) {            		
            	}
                reset();
            }
        };
    }
    
    private ActionListener getPageSelectListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectField select = (SelectField)e.getSource();
                int selected = select.getSelectedIndex();
                model.setCurrentPage(selected);
                reset();
            }
        };
    }
    
    private Label getPrePageLabel() {
        Label label = new Label(resourceBundle.getString("DscPageNavigation.CTL_PageInfo_PreText"));
        return label;
    }
    
    private Label getInterPageLabel() {
        Label label = new Label(resourceBundle.getString("DscPageNavigation.CTL_PageInfo_InterText"));
        return label;
    }
    
    private Label getPostPageLabel() {
        Label label = new Label(resourceBundle.getString("DscPageNavigation.CTL_PageInfo_PostText"));
        return label;
    }
    
    private Label getPageCountLabel() {
        Label label = new Label();
        label.setText("" + (model.getTotalPages()));
        setPageCountLabelText();
        return label;
    }
    
    private void setPageCountLabelText() {
    }
    
    private Button getFirstButton(){    	
        Button firstButton = new Button();
        firstButton.addActionListener(getFirstListener());
        firstButton.setToolTipText(resourceBundle.getString("DscPageNavigation.CTL_FirstPage"));  
        firstButton.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnFirstPage.gif"));
        firstButton.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnFirstPageD.gif"));

        if(model.getCurrentPage() == 0)
        	firstButton.setEnabled(false);

        return firstButton;
    }

    private Button getLastButton(){    	
        Button lastButton = new Button();
        lastButton.addActionListener(getLastListener());
        lastButton.setToolTipText(resourceBundle.getString("DscPageNavigation.CTL_LastPage"));
        lastButton.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnLastPage.gif")); 
        lastButton.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnLastPageD.gif"));
        
        if(model.getCurrentPage() == model.getTotalPages()-1 || model.getTotalPages() == 0)
        	lastButton.setEnabled(false);

        return lastButton;
    } 
    
    private Button getPreviousButton(){    	
        Button previousButton = new Button();
        previousButton.addActionListener(getPreviousListener());
        previousButton.setToolTipText(resourceBundle.getString("DscPageNavigation.CTL_PreviousPage"));
        previousButton.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPreviousPage.gif"));
        previousButton.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnPreviousPageD.gif"));

        if(model.getCurrentPage() == 0)
        	previousButton.setEnabled(false);

        return previousButton;
    }

    private Button getNextButton(){
        Button nextButton = new Button();
        nextButton.addActionListener(getNextListener());
        nextButton.setToolTipText(resourceBundle.getString("DscPageNavigation.CTL_NextPage"));
        nextButton.setIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnNextPage.gif")); 
        nextButton.setDisabledIcon(new ResourceImageReference("/dsc/echo2app/resource/image/btnNextPageD.gif"));

        if(model.getCurrentPage() == model.getTotalPages()-1 || model.getTotalPages() == 0)
        	nextButton.setEnabled(false);

        return nextButton;
    }
    
    private SelectField getResultsPerPageSelect(){
        SelectField resultsPerPage = new SelectField(ROWS_PER_PAGE_OPTIONS);
        resultsPerPage.addActionListener(getRowsPerPageListener());
        int index = ROWS_PER_PAGE_LIST.indexOf("" + model.getRowsPerPage());
        resultsPerPage.setSelectedIndex(index);
        return resultsPerPage;
    }
    
    private ActionListener getPreviousListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getModel().getCurrentPage() > 0) {
                    getModel().setCurrentPage(getModel().getCurrentPage() - 1);
                    reset();
                }
            }
        };
    }
    
    private ActionListener getNextListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) { //tigereye 要減1歐....
                if (getModel().getCurrentPage() < getModel().getTotalPages()-1) {
                    getModel().setCurrentPage(getModel().getCurrentPage() + 1);
                    reset();   //作Layout.   tigereye
                }
            }
        };
    }
    
    private ActionListener getFirstListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	getModel().setCurrentPage(0);
            	reset();
            }
        };
    }
    
    private ActionListener getLastListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getModel().setCurrentPage(model.getTotalPages()-1);
                reset();
            }
        };
    }
    
    private ActionListener getRowsPerPageListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectField select = (SelectField)e.getSource();
                Integer selected = new Integer((String)select.getSelectedItem());
                getModel().setRowsPerPage(selected.intValue());
                getModel().setCurrentPage(0);
                reset();
            }
        };
    }
    
    private void resetRowsPerPage(int rowsPerPage){
    	getModel().setRowsPerPage(rowsPerPage);
    	getModel().setCurrentPage(0);
    }
}

