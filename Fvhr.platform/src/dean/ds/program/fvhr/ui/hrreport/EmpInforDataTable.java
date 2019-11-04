package ds.program.fvhr.ui.hrreport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ds.program.fvhr.dao.hrreport.ReportDao;

public class EmpInforDataTable extends AbstractReportDataTable<EmpInforData> {
	public int demSL =0;
	public EmpInforDataTable(ReportDao dao) {
		super(dao);		
	}

	@Override
	public String[] getHeaders() {
		return new String[]{"nameFact","nameGroup","nameDeptName","empsn","empcn","fname","lname"
				,"ngayNX","gioiTinh","ngaySinh","trinhDo","cmnd","ngayCapID","noiCapID","hoKhau"
				,"noiSinh","danToc","tonGiao","ngayNXMoi","donVi","chucVu","vungQL","ttSX"
				,"luongCB","luongHD","pccvu","pcNghe"};		
	}

	@Override
	public Class<EmpInforData> getDataClass() {
		return EmpInforData.class;
	}

/*	public int getDemSL() {
		return demSL;
	}

	public void setDemSL(int demSL) {
		this.demSL = demSL;
	}
*/
	@Override
	public List<EmpInforData> getListData(Map<String, Object> params) {		
		List<EmpInforData> list = getDao().getListCNV(params);
		demSL = list.size();
		return list;
		//return getDao().getListCNV(params);
	}
	

	@Override
	public Map<String, String> getColumnHeaderCaptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nameFact", "EMPINFOR_REPORT.NAMEFACT");
		map.put("nameGroup", "EMPINFOR_REPORT.NAMEGROUP");				
		map.put("nameDeptName", "EMPINFOR_REPORT.NAMEDEPTNAME");				
		map.put("empsn", "EMPINFOR_REPORT.EMPSN");
		map.put("empcn", "EMPINFOR_REPORT.EMPCN");
		map.put("fname", "EMPINFOR_REPORT.FNAME");				
		map.put("lname", "EMPINFOR_REPORT.LNAME");				
		map.put("ngayNX", "EMPINFOR_REPORT.NGAYNX");		
		map.put("gioiTinh", "EMPINFOR_REPORT.GIOITINH");
		map.put("ngaySinh", "EMPINFOR_REPORT.NGAYSINH");				
		map.put("trinhDo", "EMPINFOR_REPORT.TRINHDO");				
		map.put("cmnd", "EMPINFOR_REPORT.CMND");
		map.put("ngayCapID", "EMPINFOR_REPORT.NGAYCAPID");
		map.put("noiCapID", "EMPINFOR_REPORT.NOICAPID");				
		map.put("hoKhau", "EMPINFOR_REPORT.HOKHAU");				
		map.put("noiSinh", "EMPINFOR_REPORT.NOISINH");		
		map.put("danToc", "EMPINFOR_REPORT.DANTOC");
		map.put("tonGiao", "EMPINFOR_REPORT.TONGIAO");				
		map.put("ngayNXMoi", "EMPINFOR_REPORT.NGAYNXMOI");				
		map.put("donVi", "EMPINFOR_REPORT.DONVI");
		map.put("chucVu", "EMPINFOR_REPORT.CHUCVU");
		map.put("vungQL", "EMPINFOR_REPORT.VUNGQL");				
		map.put("ttSX", "EMPINFOR_REPORT.TTSX");				
		map.put("luongCB", "EMPINFOR_REPORT.LUONGCB");
		map.put("luongHD", "EMPINFOR_REPORT.LUONGHD");
		map.put("pccvu", "EMPINFOR_REPORT.PCCVU");
		map.put("pcNghe", "EMPINFOR_REPORT.PCNGHE");
		return map;
	}
}
