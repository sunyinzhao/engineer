package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 导出申请人次
 * @author Administrator
 *
 */
public class ExportApplyPersonTimeInfo {

	private static final long serialVersionUID = 1L;
	private int index;
	private String officeName;
	private long tatol;
	private String initialRegistration;//初始登记
	private int aleryReportTatol;//已上报终审
	private String changeSpecialty;//变更专业
	private String changeUnit;//变更单位
	private String continueRegistration;//继续登记
	private String cancellationOfRegistration;//注销登记
	public ExportApplyPersonTimeInfo(){
		super();
	}
	
	public ExportApplyPersonTimeInfo(ReportEnterpriseWorkers worker) {
		this.index = worker.getIndex();
		this.officeName = worker.getOfficeName();
		this.tatol = worker.getTatol();
		this.initialRegistration = worker.getInitialRegistration();
		this.aleryReportTatol = worker.getAleryReportTatol();
		this.changeSpecialty = worker.getChangeSpecialty();
		this.changeUnit = worker.getChangeUnit();
		this.continueRegistration = worker.getContinueRegistration();
		this.cancellationOfRegistration = worker.getCancellationOfRegistration();
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
	 * @return the officeName
	 */
	@ExcelField(title="预审单位", align=2, sort=2)
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
	 * @return the tatol
	 */
	@ExcelField(title="总计", align=2, sort=3)
	public long getTatol() {
		return tatol;
	}

	/**
	 * @param tatol the tatol to set
	 */
	public void setTatol(long tatol) {
		this.tatol = tatol;
	}

	/**
	 * @return the initialRegistration
	 */
	@ExcelField(title="初始登记", align=2, sort=4)
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
	 * @return the aleryReportTatol
	 */
	@ExcelField(title="已报终审合计", align=2, sort=5)
	public int getAleryReportTatol() {
		return aleryReportTatol;
	}
	
	/**
	 * @param aleryReportTatol the aleryReportTatol to set
	 */
	public void setAleryReportTatol(int aleryReportTatol) {
		this.aleryReportTatol = aleryReportTatol;
	}
	
	/**
	 * @return the changeSpecialty
	 */
	@ExcelField(title="变更专业", align=2, sort=6)
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
	 * @return the changeUnit
	 */
	@ExcelField(title="变更单位", align=2, sort=7)
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
	 * @return the continueRegistration
	 */
	@ExcelField(title="继续登记", align=2, sort=7)
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
	 * @return the cancellationOfRegistration
	 */
	@ExcelField(title="注销登记", align=2, sort=8)
	public String getCancellationOfRegistration() {
		return cancellationOfRegistration;
	}
	
	/**
	 * @param cancellationOfRegistration the cancellationOfRegistration to set
	 */
	public void setCancellationOfRegistration(String cancellationOfRegistration) {
		this.cancellationOfRegistration = cancellationOfRegistration;
	}

}
