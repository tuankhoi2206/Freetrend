package fv.components;

import fv.util.FVGenericInfo;
import fv.util.ListBinder;
import fv.util.Vni2Uni;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.GridLayoutData;


public class DeptSearchPane extends Column {

  private static final long serialVersionUID = 1L;
  private Grid rootLayout;
  public Label lblMaDonVi;
  public SelectField sfMaDonVi;
  private Label lblTenDonVi;
  private SelectField sfTenDonVi;
  private Label lblNhom;
  private SelectField sfNhom;
  private Label lblFact;
  private SelectField sfXuong;
  public String nameDept;

  private String _dept;

  /**
   * Creates a new <code>DepartmentSearchPane</code>.
   */
  public DeptSearchPane() {
    super();

    // Add design-time configured components.
    initComponents();
    moreInit();
  }

  public String Get_Dept() {//26/12/2011 Ngan them de lay don vi
    _dept = sfMaDonVi.getSelectedItem() == null ? "" : sfMaDonVi.getSelectedItem().toString();
    return _dept;
  }

  private void moreInit() {
    GridLayoutData labelLayout = new GridLayoutData();
    labelLayout.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
    GridLayoutData comLayout = new GridLayoutData();
    comLayout.setInsets(new Insets(1, 2, 0, 2));
    for (int i = 0; i < rootLayout.getComponentCount(); i++) {
      if (i % 2 == 0) {
        rootLayout.getComponent(i).setLayoutData(labelLayout);
      } else {
        rootLayout.getComponent(i).setLayoutData(comLayout);
      }
    }

    ListBinder.bindSelectField(sfXuong, FVGenericInfo.getFactories(), false);
    //	ListBinder.bindSelectField(sfNhom, FVGenericInfo.getAllGroup(), true);//	
    //	ListBinder.bindSelectField(sfTenDonVi, FVGenericInfo.getAllDeptName(), true);//
    ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getAllDept(), false);
    sfXuong.addActionListener(new ActionListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {

        ListBinder.bindSelectField(sfNhom, FVGenericInfo.getGroup(sfXuong.getSelectedItem().toString()), true);
        ListBinder.bindSelectField(sfTenDonVi, FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString()), true);
        ListBinder.bindSelectField(sfMaDonVi, FVGenericInfo.getDept(sfXuong.getSelectedItem().toString()), false);
        //*Begin 20/02/2012 Ngan them	
        ListBinder.refreshIndex(sfTenDonVi, e);
        ListBinder.refreshIndex(sfMaDonVi, e);
        sfTenDonVi.setEnabled(false);
        sfMaDonVi.setEnabled(false);
        //End 20/02/2012 Ngan them	
      }
    });
    sfNhom.addActionListener(new ActionListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        ListBinder.bindSelectField(sfMaDonVi,
            FVGenericInfo.getDept(sfXuong.getSelectedItem().toString(), sfNhom.getSelectedItem().toString()), false);
        ListBinder.bindSelectField(sfTenDonVi,
            FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString(), sfNhom.getSelectedItem().toString()), true);
      }
    });

    sfMaDonVi.addActionListener(new ActionListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        SelectItem item = (SelectItem) sfMaDonVi.getSelectedItem();
        String groupName = FVGenericInfo.findGroup(item.getValue().toString());
        String factName = FVGenericInfo.findFactFollowDept(item.getValue().toString());
        //	String deptName = FVGenericInfo.findDeptName(item.getValue().toString(), factName);
        String deptName = FVGenericInfo.findDeptNameFolowDept(item.getValue().toString());
        //	ListBinder.refreshIndex(sfTenDonVi, deptName);
        ListBinder.refreshIndex(sfXuong, factName);
        ListBinder.bindSelectField(sfNhom, FVGenericInfo.getGroup(sfXuong.getSelectedItem().toString()), true);
        if (groupName != null) {
          ListBinder.refreshIndex(sfNhom, groupName);
          ListBinder.bindSelectField(sfTenDonVi,
              FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString(), sfNhom.getSelectedItem().toString()),
              true);
          ListBinder.refreshIndex(sfTenDonVi, deptName);
        } else {
          ListBinder.bindSelectField(sfTenDonVi, FVGenericInfo.getDeptName(sfXuong.getSelectedItem().toString()), true);
          ListBinder.refreshIndex(sfTenDonVi, deptName);
        }
      }
    });

    sfTenDonVi.addActionListener(new ActionListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {

        sfMaDonVi.setSelectedIndex(sfTenDonVi.getSelectedIndex());

      }
    });

  }

  /*public String getOutputCondition(){//old
  	SelectItem factItem = (SelectItem) sfXuong.getSelectedItem();
  	SelectItem groupItem = (SelectItem) sfNhom.getSelectedItem();
  	SelectItem deptItem = (SelectItem) sfMaDonVi.getSelectedItem();
  	
  	if (sfMaDonVi.getSelectedIndex()>=0&&deptItem.getValue()!=null){
  		return " AND D.ID_DEPT='"+deptItem.getValue()+"'";
  	}
  	if (sfNhom.getSelectedIndex()>=0 && groupItem.getValue()!=null){
  		return " AND D.NAME_FACT='"+factItem.getValue()+"' AND D.NAME_GROUP='"+groupItem.getValue()+"'";
  	}
  	if (sfXuong.getSelectedIndex()>=0 && factItem.getValue()!=null){
  		return " AND D.NAME_FACT='"+factItem.getValue()+"'";
  	}	
  	
  	return "";
  }*/
  //new 18092012
  public String getOutputCondition() {
    SelectItem factItem = (SelectItem) sfXuong.getSelectedItem();
    SelectItem groupItem = (SelectItem) sfNhom.getSelectedItem();
    SelectItem deptItem = (SelectItem) sfTenDonVi.getSelectedItem();

    if (sfTenDonVi.getSelectedIndex() >= 0 && deptItem.getValue() != null) {
      return " AND D.NAME_FACT='" + factItem.getValue() + "' AND D.NAME_GROUP='" + groupItem.getValue()
          + "' AND D.NAME_DEPT_NAME ='" + Vni2Uni.convertToVNI(deptItem.getValue().toString()) + "'";
    }
    if (sfNhom.getSelectedIndex() >= 0 && groupItem.getValue() != null) {
      return " AND D.NAME_FACT='" + factItem.getValue() + "' AND D.NAME_GROUP='" + groupItem.getValue() + "'";
    }
    if (sfXuong.getSelectedIndex() >= 0 && factItem.getValue() != null) {
      return " AND D.NAME_FACT='" + factItem.getValue() + "'";
    }

    return "";
  }

  public void reset() {
    sfXuong.setSelectedIndex(-1);
    ListBinder.bindSelectField(sfNhom, null, true);
    //	ListBinder.bindSelectField(sfTenDonVi, null, true);
    //	ListBinder.bindSelectField(sfMaDonVi, null, false);

  }

  /**
   * Configures initial state of component.
   * WARNING: AUTO-GENERATED METHOD.
   * Contents will be overwritten.
   */
  private void initComponents() {
    rootLayout = new Grid();
    rootLayout.setColumnWidth(0, new Extent(100, Extent.PX));
    add(rootLayout);
    lblFact = new Label();
    lblFact.setText("Xưởng: ");
    rootLayout.add(lblFact);
    sfXuong = new SelectField();
    sfXuong.setWidth(new Extent(80, Extent.PX));
    rootLayout.add(sfXuong);
    lblNhom = new Label();
    lblNhom.setText("Nhóm: ");
    rootLayout.add(lblNhom);
    sfNhom = new SelectField();
    sfNhom.setWidth(new Extent(80, Extent.PX));
    rootLayout.add(sfNhom);
    lblTenDonVi = new Label();
    lblTenDonVi.setText("Tên đơn vị: ");
    rootLayout.add(lblTenDonVi);
    sfTenDonVi = new SelectField();
    sfTenDonVi.setWidth(new Extent(80, Extent.PX));
    rootLayout.add(sfTenDonVi);
    lblMaDonVi = new Label();
    lblMaDonVi.setText("Mã đơn vị: ");
    rootLayout.add(lblMaDonVi);
    sfMaDonVi = new SelectField();
    sfMaDonVi.setWidth(new Extent(80, Extent.PX));
    rootLayout.add(sfMaDonVi);
  }
}
