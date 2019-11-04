package ds.program.fvhr.ui.hrreport;

import java.math.BigDecimal;
import java.util.Date;

public class OTCRTrackDetail {
	private String outTime;
	private String overTime;
	private BigDecimal otd;
	private Date date;
	public BigDecimal getOtd() {
		return otd;
	}
	public void setOtd(BigDecimal otd) {
		this.otd = otd;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
