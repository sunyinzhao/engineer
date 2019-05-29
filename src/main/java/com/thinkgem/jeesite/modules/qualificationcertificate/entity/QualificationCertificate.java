/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.qualificationcertificate.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 执业资格证书Entity
 * @author xqg
 * @version 2018-11-04
 */
public class QualificationCertificate extends DataEntity<QualificationCertificate> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String sex;		// sex
	private String registerMainSpecialty;		// 登记主专业
	private String registerAuxiliarySpecialty;		// 登记辅助专业
	private String registerCertificateNum;		// 登记证书编号
	private String companyName;		// company_name
	private Date startDate;		// 批准日期
	private Date endDate;		// 截止日期
	
	public QualificationCertificate() {
		super();
	}

	public QualificationCertificate(String id){
		super(id);
	}

	@Length(min=0, max=20, message="name长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=2, message="sex长度必须介于 0 和 2 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=20, message="登记主专业长度必须介于 0 和 20 之间")
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}

	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}
	
	@Length(min=0, max=20, message="登记辅助专业长度必须介于 0 和 20 之间")
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}

	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}
	
	@Length(min=0, max=20, message="登记证书编号长度必须介于 0 和 20 之间")
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}

	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}
	
	@Length(min=0, max=200, message="company_name长度必须介于 0 和 200 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}