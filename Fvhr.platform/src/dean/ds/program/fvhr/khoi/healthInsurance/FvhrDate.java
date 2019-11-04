package ds.program.fvhr.khoi.healthInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FvhrDate extends Date {

	/**
	 * @author TuanKhoi
	 */
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat splDefaultFormat;

	public FvhrDate() {
		super();
		splDefaultFormat = new SimpleDateFormat("dd/mm/yyyy");
	}

	@SuppressWarnings("deprecation")
	public String getMonthYear() {
		return (getMonth() + 1) + "/" + (getYear() + 1900);
	}
	
}
