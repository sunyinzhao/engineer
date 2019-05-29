package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 导出有效咨询师信息
 * @author Administrator
 *
 */
public class exportEffectiveCounselorData {

	private static final long serialVersionUID = 1L;
	private int index;
	private String name;
	private String certificatesNum;
	private String sex;
	private String companyName;
	private String officeName;
	private String registerMainSpecialty;
	private String registerAuxiliarySpecialty;
	private String registerCertificateNum;
	private String companyContact;
	private String companyArea;
	private String companyTel;
	private String contactsZy;
	private String contactsZyPhone;
	
	public exportEffectiveCounselorData(){
		super();
	}
	
	public exportEffectiveCounselorData(ReportEnterpriseWorkers worker) {
		this.index = worker.getIndex();
		this.name = worker.getName();
		this.certificatesNum = worker.getCertificatesNum();
		this.sex = DictUtils.getDictLabel(worker.getSex(), "sex", "");
		this.companyName = worker.getCompanyName();
		this.officeName = worker.getOfficeName();
		this.registerMainSpecialty = DictUtils.getDictLabel(worker.getRegisterMainSpecialty(),"specialty_type","");
		this.registerAuxiliarySpecialty = DictUtils.getDictLabel(worker.getRegisterAuxiliarySpecialty(),"specialty_type","");
		this.registerCertificateNum = worker.getRegisterCertificateNum();
		this.companyContact = worker.getCompanyContact();
		this.companyArea = worker.getCompanyArea();
		this.companyTel = worker.getCompanyTel();
		this.contactsZy = worker.getContactsZy();
		this.contactsZyPhone = worker.getContactsZyPhone();
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
	@ExcelField(title="身份证号", align=2, sort=3)
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
	@ExcelField(title="性别", align=2, sort=4)
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
	@ExcelField(title="执业单位", align=2, sort=5)
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
	@ExcelField(title="审查单位", align=2, sort=6)
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
	/**
	 * @return the registerCertificateNum
	 */
	@ExcelField(title="登记证书编号", align=2, sort=9)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}
	/**
	 * @param registerCertificateNum the registerCertificateNum to set
	 */
	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}
	
	/**
	 * @return the companyContact
	 */
	@ExcelField(title="单位联系人", align=2, sort=10)
	public String getCompanyContact() {
		return companyContact;
	}
	
	/**
	 * @param companyContact the companyContact to set
	 */
	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}
	
	/**
	 * @return the companyArea
	 */
	@ExcelField(title="单位电话区号", align=2, sort=11)
	public String getCompanyArea() {
		return companyArea;
	}
	/**
	 * @param companyArea the companyArea to set
	 */
	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}
	/**
	 * @return the companyTel
	 */
	@ExcelField(title="单位电话", align=2, sort=12)
	public String getCompanyTel() {
		return companyTel;
	}
	/**
	 * @param companyTel the companyTel to set
	 */
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	/**
	 * @return the contactsZy
	 */
	@ExcelField(title="执业登记联系人", align=2, sort=13)
	public String getContactsZy() {
		return contactsZy;
	}
	/**
	 * @param contactsZy the contactsZy to set
	 */
	public void setContactsZy(String contactsZy) {
		this.contactsZy = contactsZy;
	}
	/**
	 * @return the contactsZyPhone
	 */
	@ExcelField(title="联系人电话", align=2, sort=14)
	public String getContactsZyPhone() {
		return contactsZyPhone;
	}
	/**
	 * @param contactsZyPhone the contactsZyPhone to set
	 */
	public void setContactsZyPhone(String contactsZyPhone) {
		this.contactsZyPhone = contactsZyPhone;
	}
}
