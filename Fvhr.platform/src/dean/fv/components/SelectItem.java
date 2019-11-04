package fv.components;

import java.io.Serializable;

public class SelectItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String text;

	private Object value;

	public SelectItem() {
	}

	public SelectItem(String text, Object value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SelectItem) {
			SelectItem data = (SelectItem) obj;
			if (data.text.equals(text) && data.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

}
