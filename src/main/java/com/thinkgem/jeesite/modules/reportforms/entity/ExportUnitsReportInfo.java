package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 导出预审单位上报信息汇总
 * @author Administrator
 *
 */
public class ExportUnitsReportInfo {

	private static final long serialVersionUID = 1L;
	private String companyName;
	private String continueRegistration;//继续登记
	private String initialRegistration;//初始登记
	private String changeUnit;//变更单位
	private String changeSpecialty;//变更专业
	public ExportUnitsReportInfo(){
		super();
	}
	
	public ExportUnitsReportInfo(ReportEnterpriseWorkers worker) {
		this.companyName = worker.getCompanyName();
		this.initialRegistration = worker.getInitialRegistration();
		this.continueRegistration = worker.getContinueRegistration();
		this.changeUnit = worker.getChangeUnit();
		this.changeSpecialty = worker.getChangeSpecialty();
	}
	/**
	 * @return the companyName
	 */
	@ExcelField(title="登记单位", align=2, sort=1)
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
	 * @return the continueRegistration
	 */
	@ExcelField(title="继续登记", align=2, sort=2)
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
	 * @return the initialRegistration
	 */
	@ExcelField(title="初始登记", align=2, sort=3)
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
	 * @return the changeUnit
	 */
	@ExcelField(title="变更单位", align=2, sort=4)
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
	@ExcelField(title="变更专业", align=2, sort=5)
	public String getChangeSpecialty() {
		return changeSpecialty;
	}

	/**
	 * @param changeSpecialty the changeSpecialty to set
	 */
	public void setChangeSpecialty(String changeSpecialty) {
		this.changeSpecialty = changeSpecialty;
	}
}
