package ds.program.fvhr.services.excel;

import java.util.List;

public class ExcelData {
	private int size;

	private List<Object> cols;

	public List<Object> getCols() {
		return cols;
	}

	public void setCols(List<Object> cols) {
		this.cols = cols;
		this.size = cols.size();
	}

	public void addCol(Object col) {
		cols.add(col);
		size++;
	}

	public void removeCol(int index) {
		cols.remove(index);
		size--;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
