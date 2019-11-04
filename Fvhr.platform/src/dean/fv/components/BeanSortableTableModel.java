package fv.components;

import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableColumnModel;
import dsc.echo2app.component.table.PageableSortableTableModel;

public class BeanSortableTableModel extends PageableSortableTableModel {
	private static final long serialVersionUID = 1L;

	private int selectedIndex = 0;

	private ListSelectionModel selectionModel;

	public BeanSortableTableModel(TableColumnModel columnModel) {
		super(columnModel);
	}

	@Override
	public void sortByColumn(int column, boolean ascending) {
		super.sortByColumn(column, ascending);
		selectionModel.setSelectedIndex(selectedIndex, true);
	}

	@Override
	public void setSelectionModel(ListSelectionModel model) {
		selectionModel = model;
		super.setSelectionModel(model);
	}

	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}
}
