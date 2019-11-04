package fv.components;

public class EgRecord {
	public final static int REC_BLANK = -1;

	public final static int REC_NONE = 0;

	public final static int REC_NEW = 1;

	public final static int REC_EDIT = 2;

	public final static int REC_DELETE = 3;

	private int oldStatus;

	private int status;

	private Object dataObject;

	private Object idObject;

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public Object getIdObject() {
		return idObject;
	}

	public void setIdObject(Object idObject) {
		this.idObject = idObject;
	}

	public int getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(int oldStatus) {
		this.oldStatus = oldStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
