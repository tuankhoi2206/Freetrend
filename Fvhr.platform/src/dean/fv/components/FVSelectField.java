package fv.components;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo2.app.SelectField;
import nextapp.echo2.app.event.ListDataListener;
import nextapp.echo2.app.list.ListModel;

/**
 * An extend of SelectField for dynamic adding item
 * 
 * @author Hieu <br />
 *         27/11/2010
 */
public class FVSelectField extends SelectField {
	private static final long serialVersionUID = 1L;

	public FVSelectField() {
		items = new ArrayList<SelectItem>();
		this.setModel(model);
	}

	public FVSelectField(List<SelectItem> list) {
		items = list;
		this.setModel(model);
	}

	private List<SelectItem> items;

	private ListModel model = new ListModel() {
		private static final long serialVersionUID = 1L;

		@Override
		public void addListDataListener(ListDataListener l) {
		}

		@Override
		public Object get(int index) {
			return items.get(index);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
		}

		@Override
		public int size() {
			return items.size();
		}

	};

	public void add(SelectItem item) {
		items.add(item);
	}

	public void add(String text, Object value) {
		SelectItem item = new SelectItem();
		item.setText(text);
		item.setValue(value);
		items.add(item);
	}

	@Override
	public void remove(int index) {
		items.remove(index);
	}

	@Override
	public void removeAll() {
		items = new ArrayList<SelectItem>();
		this.setModel(model);
		this.setSelectedIndex(-1);
	}

	public void sort(boolean asc) {
		if (asc) {
			for (int i = 0; i < items.size() - 1; i++) {
				SelectItem item = items.get(i);
				for (int j = i + 1; j < items.size(); j++) {
					SelectItem item1 = items.get(j);
					if (item.getText().compareToIgnoreCase(item1.getText()) > 0) {
						SelectItem temp = item;
						items.set(i, item1);
						items.set(j, temp);
						item = items.get(i);
						item1 = items.get(j);
					}
				}
			}
		} else {
			for (int i = 0; i < items.size() - 1; i++) {
				SelectItem item = items.get(i);
				for (int j = i + 1; j < items.size(); j++) {
					SelectItem item1 = items.get(j);
					if (item.getText().compareToIgnoreCase(item1.getText()) < 0) {
						SelectItem temp = item;
						items.set(i, item1);
						items.set(j, temp);
						item = items.get(i);
						item1 = items.get(j);
					}
				}
			}
		}
	}

	public void setList(List<SelectItem> list) {
		this.items = list;
	}

	public List<SelectItem> getItems() {
		return items;
	}
}
