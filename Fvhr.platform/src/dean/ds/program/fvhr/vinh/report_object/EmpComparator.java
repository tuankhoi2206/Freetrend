package ds.program.fvhr.vinh.report_object;

import java.util.Comparator;


public class EmpComparator implements Comparator<EmployeePlain> {
	/**
	 * Some tablecolumn name
	 */

	public static String EMPSN = "Số thẻ";
	public static String EMP_FNAME = "Họ";
	public static String EMP_LNAME = "Tên";
	public static String EMP_POSITION = "Chức vụ";
	public static String DEPT_ID = "Mã đơn vị";
	public static String DEPT_NAME = "Tên đơn vị";
	public static String OFF_DAY = "Ngày không đi làm";
	public static String NO_SALARY_DAY = "Tổng ngày không lương";
	public static String BASIC_SALARY = "Lương cơ bản";
	public static String COM_SALARY = "Lương hợp đồng";
	public static String SIGN_DATE = "Ngày ký hợp đồng";
	public static String LABOUR_KIND = "Thời hạn";
	public static String DUCLS = "Ngày làm + nghỉ có lương";
	public static String MATERNITY_LEAVE = "Nghỉ sản";
	public static String PAIR_VACATION = "Nghỉ có phép";
	public static String ABSENT_WITHOUT_LEAVE = "Kháng công";
	public static String SOCIAL_INSURANCE = "Tiền BHXH";
	public static String UNEMPLOYMENT_INSURANCE = "Tiền BHXH";
	public static String QUIT_DAY = "Ngày thực nghỉ";
	
	public static String ASC_ORDER = "ASC";
	public static String DESC_ORDER = "DESC";
	private String order;
	private String key;

	public EmpComparator(String order, String key) {
		super();
		this.order = order;
		this.key = key;
	}

	@Override
	public int compare(EmployeePlain o1, EmployeePlain o2) {
		if (key == EMPSN) {
			if (order.equals(ASC_ORDER))
				return o1.getEmpsn().compareTo(o2.getEmpsn());
			else
				return -o1.getEmpsn().compareTo(o2.getEmpsn());
		}
		if (key == EMP_FNAME) {
			if (order.equals(ASC_ORDER))
				return o1.getFname().compareTo(o2.getFname());
			else
				return -o1.getFname().compareTo(o2.getFname());
		}
		if (key == EMP_LNAME) {
			if (order.equals(ASC_ORDER))
				return o1.getLname().compareTo(o2.getLname());
			else
				return -o1.getLname().compareTo(o2.getLname());
		}
		if (key == EMP_POSITION) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getPositionBonus()).compareTo(((Double) o2
						.getPositionBonus()));
			else
				return -((Double) o1.getPositionBonus()).compareTo(((Double) o2
						.getPositionBonus()));
		}
		if (key == DEPT_ID) {
			if (order.equals(ASC_ORDER))
				return o1.getDeptID().compareTo(o2.getDeptID());
			else
				return -o1.getDeptID().compareTo(o2.getDeptID());
		}
		if (key == DEPT_NAME) {
			if (order.equals(ASC_ORDER))
				return o1.getDeptName().compareTo(o2.getDeptName());
			else
				return -o1.getDeptName().compareTo(o2.getDeptName());
		}
		if (key == BASIC_SALARY) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getBasicSalary()).compareTo(((Double) o2
						.getBasicSalary()));
			else
				return -((Double) o1.getBasicSalary()).compareTo(((Double) o2
						.getBasicSalary()));
		}
		if (key == COM_SALARY) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getComSalary()).compareTo(((Double) o2
						.getComSalary()));
			else
				return -((Double) o1.getComSalary()).compareTo(((Double) o2
						.getComSalary()));
		}

		if (key == DUCLS) {
			if (order.equals(ASC_ORDER))
				return ((Double) (o1.getDucls() + o1.getRestPay()))
						.compareTo(((Double) (o2.getDucls() + o2.getRestPay())));
			else
				return -((Double) (o1.getDucls() + o1.getRestPay()))
						.compareTo(((Double) (o2.getDucls() + o2.getRestPay())));
		}
		if (key == MATERNITY_LEAVE) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getMaternityLeave()).compareTo(((Double) o2
						.getMaternityLeave()));
			else
				return -((Double) o1.getMaternityLeave())
						.compareTo(((Double) o2.getMaternityLeave()));
		}
		if (key == PAIR_VACATION) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getPaidVacation()).compareTo(((Double) o2
						.getPaidVacation()));
			else
				return -((Double) o1.getPaidVacation()).compareTo(((Double) o2
						.getPaidVacation()));
		}
		if (key == ABSENT_WITHOUT_LEAVE) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getAbsentWithoutLeave())
						.compareTo(((Double) o2.getAbsentWithoutLeave()));
			else
				return -((Double) o1.getAbsentWithoutLeave())
						.compareTo(((Double) o2.getAbsentWithoutLeave()));
		}
		if (key == OFF_DAY) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getDayOFF()).compareTo(((Double) o2
						.getDayOFF()));
			else
				return -((Double) o1.getDayOFF()).compareTo(((Double) o2
						.getDayOFF()));
		}
		if (key == NO_SALARY_DAY) {
			if (order.equals(ASC_ORDER))
				return ((Double) o1.getUnpaidVacation()).compareTo(((Double) o2
						.getUnpaidVacation()));
			else
				return -((Double) o1.getUnpaidVacation())
						.compareTo(((Double) o2.getUnpaidVacation()));
		}

		else {
			return -0;
		}
	}
}
