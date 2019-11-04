package ds.program.fvhr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import ds.program.fvhr.domain.pk.N_BIRTH_CERTIFICATEPk;
import dsc.echo2app.program.Config;
import dsc.util.hibernate.validator.NotBlank;

@IdClass(N_BIRTH_CERTIFICATEPk.class)
@Entity
@Table(name = "N_BIRTH_CERTIFICATE")
public class N_BIRTH_CERTIFICATE {
	
	private java.lang.String EMPSN;
    private java.util.Date DATES;
    private java.util.Date DATE_BEAR;
	private java.lang.String MONTH;
	private java.lang.String YEAR;
	private java.lang.String NOTE;

	
	@Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_BIRTH_CERTIFICATE.EMPSN")
	public java.lang.String getEMPSN() {
		return EMPSN;
	}
	public void setEMPSN(java.lang.String eMPSN) {
		EMPSN = eMPSN;
	}
	
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATES")
    @Config(key = "N_BIRTH_CERTIFICATE.DATES")
	public java.util.Date getDATES() {
		return DATES;
	}
	public void setDATES(java.util.Date dATES) {
		DATES = dATES;
	}
	
	@Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BEAR")
    @Config(key = "N_BIRTH_CERTIFICATE.DATE_BEAR")
	public java.util.Date getDATE_BEAR() {
		return DATE_BEAR;
	}
	public void setDATE_BEAR(java.util.Date dATE_BEAR) {
		DATE_BEAR = dATE_BEAR;
	}
	
    @Length(max = 2)
    @Column(name = "MONTH")
    @Config(key = "N_BIRTH_CERTIFICATE.MONTH")
	public java.lang.String getMONTH() {
		return MONTH;
	}
	public void setMONTH(java.lang.String mONTH) {
		MONTH = mONTH;
	}
	
    @Length(max = 4)
    @Column(name = "YEAR")
    @Config(key = "N_BIRTH_CERTIFICATE.YEAR")
	public java.lang.String getYEAR() {
		return YEAR;
	}
	public void setYEAR(java.lang.String yEAR) {
		YEAR = yEAR;
	}
	
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_BIRTH_CERTIFICATE.NOTE")
	public java.lang.String getNOTE() {
		return NOTE;
	}
	public void setNOTE(java.lang.String nOTE) {
		NOTE = nOTE;
	}

}
