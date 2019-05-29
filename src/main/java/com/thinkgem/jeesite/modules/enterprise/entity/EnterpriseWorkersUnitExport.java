package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 本单位咨询师列表导出
 * @author Administrator
 *
 */
public class EnterpriseWorkersUnitExport {

	private static final long serialVersionUID = 1L;
	private int index;
	private String name;
	private String certificatesNum;
	private String sex;
	private String companyName;//
	private String registerMainSpecialty;
	private String registerAuxiliarySpecialty;
	private String registerCertificateNum;
	private String isValid;

	public EnterpriseWorkersUnitExport(){
		super();
	}

	public EnterpriseWorkersUnitExport(EnterpriseWorkers worker) {
		this.index = worker.getIndex();
		this.name = worker.getName();
		this.certificatesNum = worker.getCertificatesNum();
		this.sex = DictUtils.getDictLabel(worker.getSex(), "sex", "");
//		this.companyName = worker.getCompanyName();
		this.isValid = DictUtils.getDictLabel(worker.getIsValid(),"isValid","");
		this.registerMainSpecialty = DictUtils.getDictLabel(worker.getRegisterMainSpecialty(),"specialty_type","");
		this.registerAuxiliarySpecialty = DictUtils.getDictLabel(worker.getRegisterAuxiliarySpecialty(),"specialty_type","");
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
	@ExcelField(title="性别", align=2, sort=3)
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
//	@ExcelField(title="执业单位", align=2, sort=5)
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
	 * @return the registerMainSpecialty
	 */
	@ExcelField(title="主专业", align=2, sort=7)
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}
	/**
	 * @param registerMainSpecialty the registerMainSpecialty to set
	 */
	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}
	/**
	 * @return the registerAuxiliarySpecialty
	 */
	@ExcelField(title="辅专业", align=2, sort=8)
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}
	
	/**
	 * @param registerAuxiliarySpecialty the registerAuxiliarySpecialty to set
	 */
	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}

	@ExcelField(title="咨询工程师（投资）状态", align=2, sort=9)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the registerCertificateNum
	 */
	@ExcelField(title="登记证书编号", align=2, sort=11)
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
