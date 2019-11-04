package fv.components;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Label;

import org.apache.commons.beanutils.PropertyUtils;

import dsc.dao.DataObjectSet;
import dsc.echo2app.program.DetailContent;
import echopointng.Tree;
import echopointng.tree.DefaultMutableTreeNode;
import echopointng.tree.DefaultTreeCellRenderer;
import echopointng.tree.DefaultTreeModel;
import echopointng.tree.TreePath;
import echopointng.tree.TreeSelectionEvent;
import echopointng.tree.TreeSelectionListener;
import echopointng.tree.TreeSelectionModel;
import fv.util.Vni2Uni;
/**
 * Simple DataObjectSet browser content
 * @author Hieu
 *
 */
public class TreeBrowserContent extends Tree{
	private static final long serialVersionUID = 1L;
	
	private DetailContent detailContent;
	private String captionField;
	private boolean convertVni=false;
	private int selectedRowNo=0;
	private Color selectedForeground=new Color(0x006666);
	private Color selectedBackground=new Color(0xCCCCFF);

	public TreeBrowserContent(){
		super();
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(false);
		addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath op = e.getOldLeadSelectionPath();
				TreePath np = e.getNewLeadSelectionPath();
				if (np==null&&op!=null) {
					setSelectionPath(op);
					return;
				}
				doNodeSelect(e);
			}			
		});
	}
	
	public TreeBrowserContent(DetailContent dc){
		this();
		this.detailContent=dc;
	}
	
	protected void doNodeSelect(TreeSelectionEvent e) {
		
	}

	public DataObjectSet getDataObjectSet(){
		return detailContent.getDataObjectSet();
	}
	
	public void refresh(){
		getDataObjectSet().sortByField(captionField, true);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");		
		for (int i=0;i<getDataObjectSet().getRecordCount();i++){
			DefaultMutableTreeNode node = new DefaultMutableTreeNode();
			Object obj = getDataObjectSet().getDataObject(i);
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
    			super.setForeground(selectedForeground);
    			super.setBackground(selectedBackground);
    		} else {
    			super.setForeground(tree.getForeground());
    			super.setBackground(tree.getBackground());
    		}
			return this;
		}
	}

	public String getCaptionField() {
		return captionField;
	}

	public void setCaptionField(String captionField) {
		this.captionField = captionField;
	}

	public boolean isConvertVni() {
		return convertVni;
	}

	public void setConvertVni(boolean convertVni) {
		this.convertVni = convertVni;
	}

	public DetailContent getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(DetailContent detailContent) {
		this.detailContent = detailContent;
	}

	public Color getSelectedBackground() {
		return selectedBackground;
	}

	public void setSelectedBackground(Color selectedBackground) {
		this.selectedBackground = selectedBackground;
	}

	public Color getSelectedForeground() {
		return selectedForeground;
	}

	public void setSelectedForeground(Color selectedForeground) {
		this.selectedForeground = selectedForeground;
	}

	public int getSelectedRowNo() {
		return selectedRowNo;
	}

	public void setSelectedRowNo(int selectedRowNo) {
		this.selectedRowNo = selectedRowNo;
	}
}
