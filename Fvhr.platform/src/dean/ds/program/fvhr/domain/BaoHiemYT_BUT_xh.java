package ds.program.fvhr.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name="N_BHYT_BUT")
@DiscriminatorValue("XH")
public class BaoHiemYT_BUT_xh extends BaoHiemYT_BUT{

	@Transient
	private double money_add_more = 0;
	
	public BaoHiemYT_BUT_xh() {
		super();
	}
	public BaoHiemYT_BUT_xh(Date date, double du_tru,String up_user) {
		super(1, date, du_tru,up_user);
	}
	
	
		
}
