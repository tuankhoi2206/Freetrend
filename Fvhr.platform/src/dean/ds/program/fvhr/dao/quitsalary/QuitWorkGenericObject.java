package ds.program.fvhr.dao.quitsalary;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Bao cao bang tong
 * 
 * @author Hieu
 * 
 */
public class QuitWorkGenericObject {
	private String DEPT_KT;

	private BigDecimal TONG;

	private BigDecimal TTIN;

	private BigDecimal TSHT;

	private BigDecimal TTC;

	private BigDecimal TBORM;

	private BigDecimal TPAY;

	private BigDecimal TE_BHXH = BigDecimal.ZERO;

	private BigDecimal TT_BHXH = BigDecimal.ZERO;

	private BigDecimal TE_BHYT = BigDecimal.ZERO;

	private BigDecimal TT_BHYT = BigDecimal.ZERO;

	private BigDecimal TE_BHTN = BigDecimal.ZERO;

	private BigDecimal TT_BHTN = BigDecimal.ZERO;

	public String getDEPT_KT() {
		return DEPT_KT;
	}

	public void setDEPT_KT(String dEPT_KT) {
		DEPT_KT = dEPT_KT;
	}

	public BigDecimal getTONG() {
		return TONG;
	}

	public void setTONG(BigDecimal tONG) {
		TONG = tONG;
	}

	public BigDecimal getTTIN() {
		return TTIN;
	}

	public void setTTIN(BigDecimal tTIN) {
		TTIN = tTIN;
	}

	public BigDecimal getTSHT() {
		return TSHT;
	}

	public void setTSHT(BigDecimal tSHT) {
		TSHT = tSHT;
	}

	public BigDecimal getTTC() {
		return TTC;
	}

	public void setTTC(BigDecimal tTC) {
		TTC = tTC;
	}

	public BigDecimal getTBORM() {
		return TBORM;
	}

	public void setTBORM(BigDecimal tBORM) {
		TBORM = tBORM;
	}

	public BigDecimal getTPAY() {
		return TPAY;
	}

	public void setTPAY(BigDecimal tPAY) {
		TPAY = tPAY;
	}

	public BigDecimal getTE_BHXH() {
		return TE_BHXH;
	}

	public void setTE_BHXH(BigDecimal tE_BHXH) {
		TE_BHXH = tE_BHXH;
	}

	public BigDecimal getTT_BHXH() {
		return TT_BHXH;
	}

	public void setTT_BHXH(BigDecimal tT_BHXH) {
		TT_BHXH = tT_BHXH;
	}

	public BigDecimal getTE_BHYT() {
		return TE_BHYT;
	}

	public void setTE_BHYT(BigDecimal tE_BHYT) {
		TE_BHYT = tE_BHYT;
	}

	public BigDecimal getTT_BHYT() {
		return TT_BHYT;
	}

	public void setTT_BHYT(BigDecimal tT_BHYT) {
		TT_BHYT = tT_BHYT;
	}

	public BigDecimal getTE_BHTN() {
		return TE_BHTN;
	}

	public void setTE_BHTN(BigDecimal tE_BHTN) {
		TE_BHTN = tE_BHTN;
	}

	public BigDecimal getTT_BHTN() {
		return TT_BHTN;
	}

	public void setTT_BHTN(BigDecimal tT_BHTN) {
		TT_BHTN = tT_BHTN;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuitWorkGenericObject))
			return false;
		QuitWorkGenericObject other = (QuitWorkGenericObject) obj;
		return new EqualsBuilder().append(this.DEPT_KT, other.DEPT_KT)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.DEPT_KT).toHashCode();
	}
}
