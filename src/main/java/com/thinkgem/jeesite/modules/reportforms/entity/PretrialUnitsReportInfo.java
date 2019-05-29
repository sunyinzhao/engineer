package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 导出预审单位上报信息汇总
 * @author Administrator
 *
 */
public class PretrialUnitsReportInfo {

	private static final long serialVersionUID = 1L;
	private int index;//序号
	private String name;
	private String batchNo;
	private String certificatesNum;
	private String sex;
	private String companyName;
	private String officeName;
	private String initialRegistration;//初始登记
	private String continueRegistration;//继续登记
	private String changeUnit;//变更单位
	private String changeSpecialty;//变更专业
	private String cancellationOfRegistration;//注销登记
	private String utilType;//单位分类
	private String registerCertificateNum;
	
	public PretrialUnitsReportInfo(){
		super();
	}
	
	public PretrialUnitsReportInfo(ReportEnterpriseWorkers worker) {
		this.index = worker.getIndex();
		this.name = worker.getName();
		this.batchNo = worker.getBatchNo();
		this.certificatesNum = worker.getCertificatesNum();
		this.sex = DictUtils.getDictLabel(worker.getSex(), "sex", "");
		this.companyName = worker.getCompanyName();
		this.officeName = worker.getOfficeName();
		this.initialRegistration = worker.getInitialRegistration();
		this.continueRegistration = worker.getContinueRegistration();
		this.changeUnit = worker.getChangeUnit();
		this.changeSpecialty = worker.getChangeSpecialty();
		this.cancellationOfRegistration = worker.getCancellationOfRegistration();
		this.utilType = "";
		this.registerCertificateNum = worker.getRegisterCertificateNum();
	}

	/**
	 * @return the index
	 */
	@ExcelField(title="序号", align=2, sort=1)
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the name
	 */
	@ExcelField(title="姓名", align=2, sort=2)
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the batchNo
	 */
	@ExcelField(title="上报编号", align=2, sort=3)
	public String getBatchNo() {
		return batchNo;
	}
	
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the certificatesNum
	 */
	@ExcelField(title="证件号", align=2, sort=4)
	public String getCertificatesNum() {
		return certificatesNum;
	}
	/**
	 * @param certificatesNum the certificatesNum to set
	 */
	public void setCertificatesNum(String certificatesNum) {
		this.certificatesNum = certificatesNum;
	}
	/**
	 * @return the sex
	 */
	@ExcelField(title="性别", align=2, sort=5)
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the companyName
	 */
	@ExcelField(title="登记单位", align=2, sort=6)
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the officeName
	 */
	@ExcelField(title="审查单位", align=2, sort=7)
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	/**
	 * @return the initialRegistration
	 */
	@ExcelField(title="初始登记", align=2, sort=8)
	public String getInitialRegistration() {
		return initialRegistration;
	}

	/**
	 * @param initialRegistration the initialRegistration to set
	 */
	public void setInitialRegistration(String initialRegistration) {
		this.initialRegistration = initialRegistration;
	}

	/**
	 * @return the continueRegistration
	 */
	@ExcelField(title="继续登记", align=2, sort=9)
	public String getContinueRegistration() {
		return continueRegistration;
	}

	/**
	 * @param continueRegistration the continueRegistration to set
	 */
	public void setContinueRegistration(String continueRegistration) {
		this.continueRegistration = continueRegistration;
	}

	/**
	 * @return the changeUnit
	 */
	@ExcelField(title="变更单位", align=2, sort=10)
	public String getChangeUnit() {
		return changeUnit;
	}

	/**
	 * @param changeUnit the changeUnit to set
	 */
	public void setChangeUnit(String changeUnit) {
		this.changeUnit = changeUnit;
	}

	/**
	 * @return the changeSpecialty
	 */
	@ExcelField(title="变更专业", align=2, sort=11)
	public String getChangeSpecialty() {
		return changeSpecialty;
	}

	/**
	 * @param changeSpecialty the changeSpecialty to set
	 */
	public void setChangeSpecialty(String changeSpecialty) {
		this.changeSpecialty = changeSpecialty;
	}

	/**
	 * @return the cancellationOfRegistration
	 */
	@ExcelField(title="注销登记", align=2, sort=12)
	public String getCancellationOfRegistration() {
		return cancellationOfRegistration;
	}

	/**
	 * @param cancellationOfRegistration the cancellationOfRegistration to set
	 */
	public void setCancellationOfRegistration(String cancellationOfRegistration) {
		this.cancellationOfRegistration = cancellationOfRegistration;
	}

	/**
	 * @return the utilType
	 */
	@ExcelField(title="单位分类", align=2, sort=13)
	public String getUtilType() {
		return utilType;
	}

	/**
	 * @param utilType the utilType to set
	 */
	public void setUtilType(String utilType) {
		this.utilType = utilType;
	}


	/**
	 * @return the registerCertificateNum
	 */
	@ExcelField(title="登记证书编号", align=2, sort=14)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}
	/**
	 * @param registerCertificateNum the registerCertificateNum to set
	 */
	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}
	
}
