package ds.program.fvhr.khoi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

//primary key
@Entity
@Table(name = "N_TIME_BEAR")
public class EmployeeBear {
	private String EMPSN;
	private Date B_DATES;
	private Date E_DATES;
	private Date DATES_BEAR; // NGAY SINH E BE
	private String NOTE; // SO THANG NS
	private int bSalary;

	/**
	 * @return EMPSN
	 */
	@Id
	@NotBlank
	@Column(name = "EMPSN")
	@Config(key = "N_TIME_BEAR.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}

	/**
	 * @param EMPSN
	 */
	public void setEMPSN(java.lang.String EMPSN) {
		this.EMPSN = EMPSN;
	}

	/**
	 * @return BB_DATES
	 */
	@Id
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "B_DATES")
	@Config(key = "N_TIME_BEAR.B_DATES")
	public Date getB_DATES() {
		return B_DATES;
	}

	/**
	 * @param BB_DATES
	 */
	public void setB_DATES(java.util.Date B_DATES) {
		this.B_DATES = B_DATES;
	}

	/**
	 * @return EE_DATES
	 */
	@NotBlank
	@Temporal(TemporalType.DATE)
	@Column(name = "E_DATES")
	@Config(key = "N_TIME_BEAR.E_DATES")
	public Date getE_DATES() {
		return E_DATES;
	}

	/**
	 * @param EE_DATES
	 */
	public void setE_DATES(java.util.Date EE_DATES) {
		this.E_DATES = EE_DATES;
	}

	/**
	 * 取得NGAY SINH E BE
	 * 
	 * @return DATES_BEAR NGAY SINH E BE
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DATES_BEAR")
	@Config(key = "N_TIME_BEAR.DATES_BEAR")
	public Date getDATES_BEAR() {
		return DATES_BEAR;
	}

	/**
	 * 設定NGAY SINH E BE
	 * 
	 * @param DATES_BEAR
	 *            NGAY SINH E BE
	 */
	public void setDATES_BEAR(java.util.Date DATES_BEAR) {
		this.DATES_BEAR = DATES_BEAR;
	}

	/**
	 * 取得SO THANG NS
	 * 
	 * @return NOTE SO THANG NS
	 */
	@Length(max = 150)
	@Column(name = "NOTE")
	@Config(key = "N_TIME_BEAR.NOTE")
	public String getNOTE() {
		return NOTE;
	}

	public void setNOTE(String note) {
		this.NOTE = note;
	}

	public int getBSALARY() {
		return bSalary;
	}

	public void setBSALARY(int bSalary) {
		this.bSalary = bSalary;
	}

}
