package ds.program.fvhr.domain;
import javax.persistence.IdClass;
import ds.program.fvhr.domain.pk.N_EMP_QUITPk;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import dsc.util.hibernate.validator.NotBlank;
import org.hibernate.validator.Length;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import dsc.echo2app.program.Config;
/**
* NHAN VIEN NGHI VIEC
**/
@IdClass(N_EMP_QUITPk.class)
@Entity
@Table(name = "N_EMP_QUIT")
public class N_EMP_QUIT {
    private java.lang.String EMPSN;	//SO THE
    private java.math.BigDecimal ID_QUIT_REASON;	//LY DO NGHI VIEC
    private java.lang.String NOTE;	//GHI CHU
    private java.util.Date OFF_DATE;	//NGAY NGHI VIEC
    private java.lang.String DEPT;	//DON VI CU
    private java.lang.String FACT_NAME;	//XUONG CU
    private java.lang.String GROUP_NAME;	//NHOM CU
    private java.lang.String DEPT_NAME;	//DON VI CU
    private java.util.Date REAL_OFF_DATE;	//NGAY THUC NGHI
    private java.lang.String DEPSN;
    private java.util.Date APPLY_DATE;	//NGAY NOP DON XIN NGHI
    private java.lang.Long PAY;	//Tra luong thoi viec
    private java.lang.Long QDNV;	//STT phat sinh tren tong so CNV nghi viec trong 1 nam
    private java.util.Date DATE_HEN;	//NGAY HEN DEN LANH LUONG NGHI VIEC
    private java.lang.String SO_QDNV;	//26112011 QUY UOC CHUNG : 2KYTU TEN XUONG_NAM_STT FVL=FL,FVLS=FS,FVJ=FJ,KDAO=KD,TB=TB VD : FL20111,FL20112......; SO QDNV TUY XUONG, CO KHI LA KY TU , CO KHI LA SO
    private java.lang.String THE_BHYT;	//KHI NGHI VIEC CO TRA THE BHYT HAY KO 0: KO TRA , 1: TRA
    private java.util.Date DATE_BHYT;	//NGAY TRA THE BHYT
    private java.lang.Long STATUS;	//1:DA XUAT BC,0 OR NULL CHUA XUAT BC
    private java.lang.String MONTH_GIAMBH;	//THANG BAO GIAM CHO NV
    private java.util.Date FROM_DATE;	//SO THE NAY NAM TRONG DS GIAM TU NGAY
    private java.util.Date TO_DATE;	//SO THE NAY NAM TRONG DS GIAM DEN NGAY
    private java.lang.String NOTE_BH;	//GHI CHU CHO BAO HIEM
    private java.lang.String LOCKED;	//HS KO HOP LE BUT CHU QUAN VAN MUON NHAN THI Y, NGUOC LAI LA N OR NULL
    private java.lang.String THE_GH_QUY;	//N: CHUA CO THE GIA HAN QUY KE TIEP VE(VD:CUOI QUY 3 THI LA THE GIA HAN CUA Q4(T10,11,12)
    private java.lang.String GIAM_BHYT;	//Y:DA GIAM BSBHYT
    private java.lang.String NOTE_GIAM_BHYT;	//GHI CHU GIAM QUY NAO, NAM NAO
    private java.util.Date DATE_AGAIN;	//NGAY CNV XIN DI LAM TRO LAI VOI THONG TIN OLD. PS 12/2010
    private java.util.Date DOT_TV;	//NGAY LANH LUONG THOI VIEC
    private java.lang.Double MM_DENBU;
    private java.util.Date THANG_TRUBH;	//THANG SE TRU CAC LOAI BAO HIEM, KY TRINH
    private java.util.Date THANG_TANGLAI;	//THANG SE TANG MOI LAI CHO NHUNG CNV NVIEC DI LAM LAI VOI SO THE OLD
    private java.lang.Double TYLE_TANGLAI;	//1: tang 1 tay, 20: tang 20 tay : se luu thoi diem tang lai, ko luu ty le nua 28.5 la tang lai hoan tona luc 1 tay, 24 la tang BHXH-TN ko tang y te luc 20tay
    /**
     * 取得SO THE
     * @return EMPSN SO THE
     */
    @Id
    @NotBlank
    @Column(name = "EMPSN")
    @Config(key = "N_EMP_QUIT.EMPSN")
    public java.lang.String getEMPSN() {
        return EMPSN;
    }
    /**
     * 設定SO THE
     * @param EMPSN SO THE
     */
    public void setEMPSN(java.lang.String EMPSN) {
        this.EMPSN = EMPSN;
    }
    /**
     * 取得LY DO NGHI VIEC
     * @return ID_QUIT_REASON LY DO NGHI VIEC
     */
    @Column(name = "ID_QUIT_REASON")
    @Config(key = "N_EMP_QUIT.ID_QUIT_REASON")
    public java.math.BigDecimal getID_QUIT_REASON() {
        return ID_QUIT_REASON;
    }
    /**
     * 設定LY DO NGHI VIEC
     * @param ID_QUIT_REASON LY DO NGHI VIEC
     */
    public void setID_QUIT_REASON(java.math.BigDecimal ID_QUIT_REASON) {
        this.ID_QUIT_REASON = ID_QUIT_REASON;
    }
    /**
     * 取得GHI CHU
     * @return NOTE GHI CHU
     */
    @Length(max = 150)
    @Column(name = "NOTE")
    @Config(key = "N_EMP_QUIT.NOTE")
    public java.lang.String getNOTE() {
        return NOTE;
    }
    /**
     * 設定GHI CHU
     * @param NOTE GHI CHU
     */
    public void setNOTE(java.lang.String NOTE) {
        this.NOTE = NOTE;
    }
    /**
     * 取得NGAY NGHI VIEC
     * @return OFF_DATE NGAY NGHI VIEC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "OFF_DATE")
    @Config(key = "N_EMP_QUIT.OFF_DATE")
    public java.util.Date getOFF_DATE() {
        return OFF_DATE;
    }
    /**
     * 設定NGAY NGHI VIEC
     * @param OFF_DATE NGAY NGHI VIEC
     */
    public void setOFF_DATE(java.util.Date OFF_DATE) {
        this.OFF_DATE = OFF_DATE;
    }
    /**
     * 取得DON VI CU
     * @return DEPT DON VI CU
     */
    @Length(max = 50)
    @Column(name = "DEPT")
    @Config(key = "N_EMP_QUIT.DEPT")
    public java.lang.String getDEPT() {
        return DEPT;
    }
    /**
     * 設定DON VI CU
     * @param DEPT DON VI CU
     */
    public void setDEPT(java.lang.String DEPT) {
        this.DEPT = DEPT;
    }
    /**
     * 取得XUONG CU
     * @return FACT_NAME XUONG CU
     */
    @Length(max = 10)
    @Column(name = "FACT_NAME")
    @Config(key = "N_EMP_QUIT.FACT_NAME")
    public java.lang.String getFACT_NAME() {
        return FACT_NAME;
    }
    /**
     * 設定XUONG CU
     * @param FACT_NAME XUONG CU
     */
    public void setFACT_NAME(java.lang.String FACT_NAME) {
        this.FACT_NAME = FACT_NAME;
    }
    /**
     * 取得NHOM CU
     * @return GROUP_NAME NHOM CU
     */
    @Length(max = 20)
    @Column(name = "GROUP_NAME")
    @Config(key = "N_EMP_QUIT.GROUP_NAME")
    public java.lang.String getGROUP_NAME() {
        return GROUP_NAME;
    }
    /**
     * 設定NHOM CU
     * @param GROUP_NAME NHOM CU
     */
    public void setGROUP_NAME(java.lang.String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }
    /**
     * 取得DON VI CU
     * @return DEPT_NAME DON VI CU
     */
    @Length(max = 40)
    @Column(name = "DEPT_NAME")
    @Config(key = "N_EMP_QUIT.DEPT_NAME")
    public java.lang.String getDEPT_NAME() {
        return DEPT_NAME;
    }
    /**
     * 設定DON VI CU
     * @param DEPT_NAME DON VI CU
     */
    public void setDEPT_NAME(java.lang.String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }
    /**
     * 取得NGAY THUC NGHI
     * @return REAL_OFF_DATE NGAY THUC NGHI
     */
    @Id
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(name = "REAL_OFF_DATE")
    @Config(key = "N_EMP_QUIT.REAL_OFF_DATE")
    public java.util.Date getREAL_OFF_DATE() {
        return REAL_OFF_DATE;
    }
    /**
     * 設定NGAY THUC NGHI
     * @param REAL_OFF_DATE NGAY THUC NGHI
     */
    public void setREAL_OFF_DATE(java.util.Date REAL_OFF_DATE) {
        this.REAL_OFF_DATE = REAL_OFF_DATE;
    }
    /**
     * @return DEPSN 
     */
    @Length(max = 5)
    @Column(name = "DEPSN")
    @Config(key = "N_EMP_QUIT.DEPSN")
    public java.lang.String getDEPSN() {
        return DEPSN;
    }
    /**
     * @param DEPSN 
     */
    public void setDEPSN(java.lang.String DEPSN) {
        this.DEPSN = DEPSN;
    }
    /**
     * 取得NGAY NOP DON XIN NGHI
     * @return APPLY_DATE NGAY NOP DON XIN NGHI
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "APPLY_DATE")
    @Config(key = "N_EMP_QUIT.APPLY_DATE")
    public java.util.Date getAPPLY_DATE() {
        return APPLY_DATE;
    }
    /**
     * 設定NGAY NOP DON XIN NGHI
     * @param APPLY_DATE NGAY NOP DON XIN NGHI
     */
    public void setAPPLY_DATE(java.util.Date APPLY_DATE) {
        this.APPLY_DATE = APPLY_DATE;
    }
    /**
     * 取得Tra luong thoi viec
     * @return PAY Tra luong thoi viec
     */
    @Column(name = "PAY")
    @Config(key = "N_EMP_QUIT.PAY")
    public java.lang.Long getPAY() {
        return PAY;
    }
    /**
     * 設定Tra luong thoi viec
     * @param PAY Tra luong thoi viec
     */
    public void setPAY(java.lang.Long PAY) {
        this.PAY = PAY;
    }
    /**
     * 取得STT phat sinh tren tong so CNV nghi viec trong 1 nam
     * @return QDNV STT phat sinh tren tong so CNV nghi viec trong 1 nam
     */
    @Column(name = "QDNV")
    @Config(key = "N_EMP_QUIT.QDNV")
    public java.lang.Long getQDNV() {
        return QDNV;
    }
    /**
     * 設定STT phat sinh tren tong so CNV nghi viec trong 1 nam
     * @param QDNV STT phat sinh tren tong so CNV nghi viec trong 1 nam
     */
    public void setQDNV(java.lang.Long QDNV) {
        this.QDNV = QDNV;
    }
    /**
     * 取得NGAY HEN DEN LANH LUONG NGHI VIEC
     * @return DATE_HEN NGAY HEN DEN LANH LUONG NGHI VIEC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_HEN")
    @Config(key = "N_EMP_QUIT.DATE_HEN")
    public java.util.Date getDATE_HEN() {
        return DATE_HEN;
    }
    /**
     * 設定NGAY HEN DEN LANH LUONG NGHI VIEC
     * @param DATE_HEN NGAY HEN DEN LANH LUONG NGHI VIEC
     */
    public void setDATE_HEN(java.util.Date DATE_HEN) {
        this.DATE_HEN = DATE_HEN;
    }
    /**
     * 取得26112011 QUY UOC CHUNG : 2KYTU TEN XUONG_NAM_STT FVL=FL,FVLS=FS,FVJ=FJ,KDAO=KD,TB=TB VD : FL20111,FL20112......; SO QDNV TUY XUONG, CO KHI LA KY TU , CO KHI LA SO
     * @return SO_QDNV 26112011 QUY UOC CHUNG : 2KYTU TEN XUONG_NAM_STT FVL=FL,FVLS=FS,FVJ=FJ,KDAO=KD,TB=TB VD : FL20111,FL20112......; SO QDNV TUY XUONG, CO KHI LA KY TU , CO KHI LA SO
     */
    @Length(max = 20)
    @Column(name = "SO_QDNV")
    @Config(key = "N_EMP_QUIT.SO_QDNV")
    public java.lang.String getSO_QDNV() {
        return SO_QDNV;
    }
    /**
     * 設定26112011 QUY UOC CHUNG : 2KYTU TEN XUONG_NAM_STT FVL=FL,FVLS=FS,FVJ=FJ,KDAO=KD,TB=TB VD : FL20111,FL20112......; SO QDNV TUY XUONG, CO KHI LA KY TU , CO KHI LA SO
     * @param SO_QDNV 26112011 QUY UOC CHUNG : 2KYTU TEN XUONG_NAM_STT FVL=FL,FVLS=FS,FVJ=FJ,KDAO=KD,TB=TB VD : FL20111,FL20112......; SO QDNV TUY XUONG, CO KHI LA KY TU , CO KHI LA SO
     */
    public void setSO_QDNV(java.lang.String SO_QDNV) {
        this.SO_QDNV = SO_QDNV;
    }
    /**
     * 取得KHI NGHI VIEC CO TRA THE BHYT HAY KO 0: KO TRA , 1: TRA
     * @return THE_BHYT KHI NGHI VIEC CO TRA THE BHYT HAY KO 0: KO TRA , 1: TRA
     */
    @Length(max = 1)
    @Column(name = "THE_BHYT")
    @Config(key = "N_EMP_QUIT.THE_BHYT")
    public java.lang.String getTHE_BHYT() {
        return THE_BHYT;
    }
    /**
     * 設定KHI NGHI VIEC CO TRA THE BHYT HAY KO 0: KO TRA , 1: TRA
     * @param THE_BHYT KHI NGHI VIEC CO TRA THE BHYT HAY KO 0: KO TRA , 1: TRA
     */
    public void setTHE_BHYT(java.lang.String THE_BHYT) {
        this.THE_BHYT = THE_BHYT;
    }
    /**
     * 取得NGAY TRA THE BHYT
     * @return DATE_BHYT NGAY TRA THE BHYT
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BHYT")
    @Config(key = "N_EMP_QUIT.DATE_BHYT")
    public java.util.Date getDATE_BHYT() {
        return DATE_BHYT;
    }
    /**
     * 設定NGAY TRA THE BHYT
     * @param DATE_BHYT NGAY TRA THE BHYT
     */
    public void setDATE_BHYT(java.util.Date DATE_BHYT) {
        this.DATE_BHYT = DATE_BHYT;
    }
    /**
     * 取得1:DA XUAT BC,0 OR NULL CHUA XUAT BC
     * @return STATUS 1:DA XUAT BC,0 OR NULL CHUA XUAT BC
     */
    @Column(name = "STATUS")
    @Config(key = "N_EMP_QUIT.STATUS")
    public java.lang.Long getSTATUS() {
        return STATUS;
    }
    /**
     * 設定1:DA XUAT BC,0 OR NULL CHUA XUAT BC
     * @param STATUS 1:DA XUAT BC,0 OR NULL CHUA XUAT BC
     */
    public void setSTATUS(java.lang.Long STATUS) {
        this.STATUS = STATUS;
    }
    /**
     * 取得THANG BAO GIAM CHO NV
     * @return MONTH_GIAMBH THANG BAO GIAM CHO NV
     */
    @Length(max = 7)
    @Column(name = "MONTH_GIAMBH")
    @Config(key = "N_EMP_QUIT.MONTH_GIAMBH")
    public java.lang.String getMONTH_GIAMBH() {
        return MONTH_GIAMBH;
    }
    /**
     * 設定THANG BAO GIAM CHO NV
     * @param MONTH_GIAMBH THANG BAO GIAM CHO NV
     */
    public void setMONTH_GIAMBH(java.lang.String MONTH_GIAMBH) {
        this.MONTH_GIAMBH = MONTH_GIAMBH;
    }
    /**
     * 取得SO THE NAY NAM TRONG DS GIAM TU NGAY
     * @return FROM_DATE SO THE NAY NAM TRONG DS GIAM TU NGAY
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE")
    @Config(key = "N_EMP_QUIT.FROM_DATE")
    public java.util.Date getFROM_DATE() {
        return FROM_DATE;
    }
    /**
     * 設定SO THE NAY NAM TRONG DS GIAM TU NGAY
     * @param FROM_DATE SO THE NAY NAM TRONG DS GIAM TU NGAY
     */
    public void setFROM_DATE(java.util.Date FROM_DATE) {
        this.FROM_DATE = FROM_DATE;
    }
    /**
     * 取得SO THE NAY NAM TRONG DS GIAM DEN NGAY
     * @return TO_DATE SO THE NAY NAM TRONG DS GIAM DEN NGAY
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE")
    @Config(key = "N_EMP_QUIT.TO_DATE")
    public java.util.Date getTO_DATE() {
        return TO_DATE;
    }
    /**
     * 設定SO THE NAY NAM TRONG DS GIAM DEN NGAY
     * @param TO_DATE SO THE NAY NAM TRONG DS GIAM DEN NGAY
     */
    public void setTO_DATE(java.util.Date TO_DATE) {
        this.TO_DATE = TO_DATE;
    }
    /**
     * 取得GHI CHU CHO BAO HIEM
     * @return NOTE_BH GHI CHU CHO BAO HIEM
     */
    @Length(max = 100)
    @Column(name = "NOTE_BH")
    @Config(key = "N_EMP_QUIT.NOTE_BH")
    public java.lang.String getNOTE_BH() {
        return NOTE_BH;
    }
    /**
     * 設定GHI CHU CHO BAO HIEM
     * @param NOTE_BH GHI CHU CHO BAO HIEM
     */
    public void setNOTE_BH(java.lang.String NOTE_BH) {
        this.NOTE_BH = NOTE_BH;
    }
    /**
     * 取得HS KO HOP LE BUT CHU QUAN VAN MUON NHAN THI Y, NGUOC LAI LA N OR NULL
     * @return LOCKED HS KO HOP LE BUT CHU QUAN VAN MUON NHAN THI Y, NGUOC LAI LA N OR NULL
     */
    @Length(max = 1)
    @Column(name = "LOCKED")
    @Config(key = "N_EMP_QUIT.LOCKED")
    public java.lang.String getLOCKED() {
        return LOCKED;
    }
    /**
     * 設定HS KO HOP LE BUT CHU QUAN VAN MUON NHAN THI Y, NGUOC LAI LA N OR NULL
     * @param LOCKED HS KO HOP LE BUT CHU QUAN VAN MUON NHAN THI Y, NGUOC LAI LA N OR NULL
     */
    public void setLOCKED(java.lang.String LOCKED) {
        this.LOCKED = LOCKED;
    }
    /**
     * 取得N: CHUA CO THE GIA HAN QUY KE TIEP VE(VD:CUOI QUY 3 THI LA THE GIA HAN CUA Q4(T10,11,12)
     * @return THE_GH_QUY N: CHUA CO THE GIA HAN QUY KE TIEP VE(VD:CUOI QUY 3 THI LA THE GIA HAN CUA Q4(T10,11,12)
     */
    @Length(max = 1)
    @Column(name = "THE_GH_QUY")
    @Config(key = "N_EMP_QUIT.THE_GH_QUY")
    public java.lang.String getTHE_GH_QUY() {
        return THE_GH_QUY;
    }
    /**
     * 設定N: CHUA CO THE GIA HAN QUY KE TIEP VE(VD:CUOI QUY 3 THI LA THE GIA HAN CUA Q4(T10,11,12)
     * @param THE_GH_QUY N: CHUA CO THE GIA HAN QUY KE TIEP VE(VD:CUOI QUY 3 THI LA THE GIA HAN CUA Q4(T10,11,12)
     */
    public void setTHE_GH_QUY(java.lang.String THE_GH_QUY) {
        this.THE_GH_QUY = THE_GH_QUY;
    }
    /**
     * 取得Y:DA GIAM BSBHYT
     * @return GIAM_BHYT Y:DA GIAM BSBHYT
     */
    @Length(max = 1)
    @Column(name = "GIAM_BHYT")
    @Config(key = "N_EMP_QUIT.GIAM_BHYT")
    public java.lang.String getGIAM_BHYT() {
        return GIAM_BHYT;
    }
    /**
     * 設定Y:DA GIAM BSBHYT
     * @param GIAM_BHYT Y:DA GIAM BSBHYT
     */
    public void setGIAM_BHYT(java.lang.String GIAM_BHYT) {
        this.GIAM_BHYT = GIAM_BHYT;
    }
    /**
     * 取得GHI CHU GIAM QUY NAO, NAM NAO
     * @return NOTE_GIAM_BHYT GHI CHU GIAM QUY NAO, NAM NAO
     */
    @Length(max = 100)
    @Column(name = "NOTE_GIAM_BHYT")
    @Config(key = "N_EMP_QUIT.NOTE_GIAM_BHYT")
    public java.lang.String getNOTE_GIAM_BHYT() {
        return NOTE_GIAM_BHYT;
    }
    /**
     * 設定GHI CHU GIAM QUY NAO, NAM NAO
     * @param NOTE_GIAM_BHYT GHI CHU GIAM QUY NAO, NAM NAO
     */
    public void setNOTE_GIAM_BHYT(java.lang.String NOTE_GIAM_BHYT) {
        this.NOTE_GIAM_BHYT = NOTE_GIAM_BHYT;
    }
    /**
     * 取得NGAY CNV XIN DI LAM TRO LAI VOI THONG TIN OLD. PS 12/2010
     * @return DATE_AGAIN NGAY CNV XIN DI LAM TRO LAI VOI THONG TIN OLD. PS 12/2010
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_AGAIN")
    @Config(key = "N_EMP_QUIT.DATE_AGAIN")
    public java.util.Date getDATE_AGAIN() {
        return DATE_AGAIN;
    }
    /**
     * 設定NGAY CNV XIN DI LAM TRO LAI VOI THONG TIN OLD. PS 12/2010
     * @param DATE_AGAIN NGAY CNV XIN DI LAM TRO LAI VOI THONG TIN OLD. PS 12/2010
     */
    public void setDATE_AGAIN(java.util.Date DATE_AGAIN) {
        this.DATE_AGAIN = DATE_AGAIN;
    }
    /**
     * 取得NGAY LANH LUONG THOI VIEC
     * @return DOT_TV NGAY LANH LUONG THOI VIEC
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DOT_TV")
    @Config(key = "N_EMP_QUIT.DOT_TV")
    public java.util.Date getDOT_TV() {
        return DOT_TV;
    }
    /**
     * 設定NGAY LANH LUONG THOI VIEC
     * @param DOT_TV NGAY LANH LUONG THOI VIEC
     */
    public void setDOT_TV(java.util.Date DOT_TV) {
        this.DOT_TV = DOT_TV;
    }
    /**
     * @return MM_DENBU 
     */
    @Column(name = "M_DENBU")
    @Config(key = "N_EMP_QUIT.M_DENBU")
    public java.lang.Double getMM_DENBU() {
        return MM_DENBU;
    }
    /**
     * @param MM_DENBU 
     */
    public void setMM_DENBU(java.lang.Double MM_DENBU) {
        this.MM_DENBU = MM_DENBU;
    }
    /**
     * 取得THANG SE TRU CAC LOAI BAO HIEM, KY TRINH
     * @return THANG_TRUBH THANG SE TRU CAC LOAI BAO HIEM, KY TRINH
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "THANG_TRUBH")
    @Config(key = "N_EMP_QUIT.THANG_TRUBH")
    public java.util.Date getTHANG_TRUBH() {
        return THANG_TRUBH;
    }
    /**
     * 設定THANG SE TRU CAC LOAI BAO HIEM, KY TRINH
     * @param THANG_TRUBH THANG SE TRU CAC LOAI BAO HIEM, KY TRINH
     */
    public void setTHANG_TRUBH(java.util.Date THANG_TRUBH) {
        this.THANG_TRUBH = THANG_TRUBH;
    }
    /**
     * 取得THANG SE TANG MOI LAI CHO NHUNG CNV NVIEC DI LAM LAI VOI SO THE OLD
     * @return THANG_TANGLAI THANG SE TANG MOI LAI CHO NHUNG CNV NVIEC DI LAM LAI VOI SO THE OLD
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "THANG_TANGLAI")
    @Config(key = "N_EMP_QUIT.THANG_TANGLAI")
    public java.util.Date getTHANG_TANGLAI() {
        return THANG_TANGLAI;
    }
    /**
     * 設定THANG SE TANG MOI LAI CHO NHUNG CNV NVIEC DI LAM LAI VOI SO THE OLD
     * @param THANG_TANGLAI THANG SE TANG MOI LAI CHO NHUNG CNV NVIEC DI LAM LAI VOI SO THE OLD
     */
    public void setTHANG_TANGLAI(java.util.Date THANG_TANGLAI) {
        this.THANG_TANGLAI = THANG_TANGLAI;
    }
    /**
     * 取得1: tang 1 tay, 20: tang 20 tay : se luu thoi diem tang lai, ko luu ty le nua 28.5 la tang lai hoan tona luc 1 tay, 24 la tang BHXH-TN ko tang y te luc 20tay
     * @return TYLE_TANGLAI 1: tang 1 tay, 20: tang 20 tay : se luu thoi diem tang lai, ko luu ty le nua 28.5 la tang lai hoan tona luc 1 tay, 24 la tang BHXH-TN ko tang y te luc 20tay
     */
    @Column(name = "TYLE_TANGLAI")
    @Config(key = "N_EMP_QUIT.TYLE_TANGLAI")
    public java.lang.Double getTYLE_TANGLAI() {
        return TYLE_TANGLAI;
    }
    /**
     * 設定1: tang 1 tay, 20: tang 20 tay : se luu thoi diem tang lai, ko luu ty le nua 28.5 la tang lai hoan tona luc 1 tay, 24 la tang BHXH-TN ko tang y te luc 20tay
     * @param TYLE_TANGLAI 1: tang 1 tay, 20: tang 20 tay : se luu thoi diem tang lai, ko luu ty le nua 28.5 la tang lai hoan tona luc 1 tay, 24 la tang BHXH-TN ko tang y te luc 20tay
     */
    public void setTYLE_TANGLAI(java.lang.Double TYLE_TANGLAI) {
        this.TYLE_TANGLAI = TYLE_TANGLAI;
    }
}
