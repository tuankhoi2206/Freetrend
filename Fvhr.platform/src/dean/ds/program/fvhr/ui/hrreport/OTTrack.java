package ds.program.fvhr.ui.hrreport;

import java.util.Date;
//import java.util.List;
import java.util.Map;

public class OTTrack {
	private String empsn;
	private String fullName;
	private String fact;
	private String group;
	private String nameDept;
	private String empcn;
	private String shift;
//	private List<OTCRTrackDetail> detailsCR;
//	private List<OTRSTrackDetail> detailsRS;
	private Map<Date, OTCRTrackDetail> detailsCRMap;
	private Map<Date, OTRSTrackDetail> detailsRSMap;
	
//	public List<OTCRTrackDetail> getDetailsCR() {
//		return detailsCR;		
//	}
//	public void setDetailsCR(List<OTCRTrackDetail> detailsCR) {
//		this.detailsCR = detailsCR;
//	}
	public String getEmpcn() {
		return empcn;
	}
	public void setEmpcn(String empcn) {
		this.empcn = empcn;
	}
	public String getEmpsn() {
		return empsn;
	}
	public void setEmpsn(String empsn) {
		this.empsn = empsn;
	}
	public String getFact() {
		return fact;
	}
	public void setFact(String fact) {
		this.fact = fact;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getNameDept() {
		return nameDept;
	}
	public void setNameDept(String nameDept) {
		this.nameDept = nameDept;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
//	public List<OTRSTrackDetail> getDetailsRS() {
//		return detailsRS;
//	}
//	public void setDetailsRS(List<OTRSTrackDetail> detailsRS) {
//		this.detailsRS = detailsRS;
//	}
	public Map<Date, OTCRTrackDetail> getDetailsCRMap() {
		return detailsCRMap;
	}
	public void setDetailsCRMap(Map<Date, OTCRTrackDetail> detailsCRMap) {
		this.detailsCRMap = detailsCRMap;
	}
	public Map<Date, OTRSTrackDetail> getDetailsRSMap() {
		return detailsRSMap;
	}
	public void setDetailsRSMap(Map<Date, OTRSTrackDetail> detailsRSMap) {
		this.detailsRSMap = detailsRSMap;
	}
}
