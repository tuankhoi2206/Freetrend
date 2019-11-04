package ds.program.fvhr.ui.training;

import org.apache.commons.beanutils.PropertyUtils;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;
import dsc.dao.DataObjectSet;
import echopointng.Tree;
import echopointng.tree.DefaultMutableTreeNode;
import echopointng.tree.DefaultTreeCellRenderer;
import echopointng.tree.DefaultTreeModel;
import echopointng.tree.TreePath;
import echopointng.tree.TreeSelectionModel;
import fv.util.Vni2Uni;

public class DataObjectSetTree extends Tree{
	private static final long serialVersionUID = 1L;

	private DataObjectSet dataObjectSet;
	private String captionField;
	private boolean convertVni=false;
	private int selectedRowNo=0;
	
	public DataObjectSetTree(){
		super();
		setCellRenderer(new TreeCellRenderer());
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setRootVisible(false);		
	}
	
	public void sortByField(String field, boolean asc){
		dataObjectSet.sortByField(field, asc);
	}
	
	public void refresh(){
		System.out.println("Refresh " + dataObjectSet.getRecNo());
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");		
		for (int i=0;i<dataObjectSet.getRecordCount();i++){
			DefaultMutableTreeNode node = new DefaultMutableTreeNode();
			Object obj = dataObjectSet.getDataObject(i);
			node.setUserObject(obj);
			root.add(node);
			if (i==selectedRowNo) {
				TreePath path = new TreePath(node.getPath());
				setSelectionPath(path);
			}
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		setModel(model);
	}
	
	public void setDataObjectSet(DataObjectSet dataObjectSet) {
		this.dataObjectSet = dataObjectSet;
	}
	
	public DataObjectSet getDataObjectSet() {
		return dataObjectSet;
	}
	
	public void setCaptionField(String captionField){
		this.captionField=captionField;
	}
	
	public String getCaptionField(){
		return this.captionField;
	}
	
	public void setConvertVni(boolean convertVni) {
		this.convertVni = convertVni;
	}
	
	public boolean isConvertVni() {
		return convertVni;
	}
	
	public int getSelectedRowNo() {
		return selectedRowNo;
	}
	
	public void setSelectedRowNo(int selectedRowNo) {
		this.selectedRowNo = selectedRowNo;
	}
	
	public class TreeCellRenderer extends DefaultTreeCellRenderer{
		private static final long serialVersionUID = 1L;

		@Override
		public Label getTreeCellRendererText(Tree tree, Object node, boolean sel, boolean expanded, boolean leaf) {
			String stringValue = "";
    		if (node instanceof DefaultMutableTreeNode && !((DefaultMutableTreeNode) node).isRoot()) {
    			DefaultMutableTreeNode n = (DefaultMutableTreeNode) node;    			
    			Object value = (n).getUserObject();
    			if (value != null) {
    				try {
    					stringValue = String.valueOf(PropertyUtils.getProperty(value, captionField));
						if (convertVni) stringValue = Vni2Uni.convertToUnicode(stringValue);						
					} catch (Exception e){
						stringValue = String.valueOf(value);
					}
    			}
    			else
    				stringValue = node.toString();
    		} else {
    			if (node != null)
    				stringValue = node.toString();
    		}
    		if(stringValue == null) stringValue = "";

    		// text 
    		setText(stringValue);
    		if (sel) {
    			super.setForeground(new Color(0x006666));
    			super.setBackground(new Color(0xCCCCFF));
    		} else {
    			super.setForeground(tree.getForeground());
    			super.setBackground(tree.getBackground());
    		}
			return this;
		}
	}
}
