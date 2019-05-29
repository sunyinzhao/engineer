/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业获得证书Entity
 * @author xqg
 * @version 2018-11-20
 */
public class EnterpriseCertificate extends DataEntity<EnterpriseCertificate> {
	
	private static final long serialVersionUID = 1L;
	private String certificateType;		// 证书类型
	private String awardOrg;		// 颁发机构
	private String specialty;		// 专业
	private String grade;		// 级别
	private Date startDate;		// 批准时间
	private Date endDate;		// 有效期截至
	private String recordId;		// 申请单Id
	private String companyName;		// 公司名称
	private String organizationCode;		// 统一社会信用代码
	private String legalPerson;		// 法人代表
	private String technologyLeader;		// 技术负责人
	private String address;		// 地址
	private String certificateNum;		// 证书编号
	private String certificatePdf;        //未盖章文件位置
	private String certificateOriginal;		// 证书正本
	private String certificateCounterpart;		// 证书副本
	
	public EnterpriseCertificate() {
		super();
	}

	public EnterpriseCertificate(String id){
		super(id);
	}

	@Length(min=0, max=1, message="证书类型长度必须介于 0 和 1 之间")
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	
	@Length(min=0, max=50, message="颁发机构长度必须介于 0 和 50 之间")
	public String getAwardOrg() {
		return awardOrg;
	}

	public void setAwardOrg(String awardOrg) {
		this.awardOrg = awardOrg;
	}
	
	@Length(min=0, max=1, message="专业长度必须介于 0 和 1 之间")
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Length(min=0, max=1, message="级别长度必须介于 0 和 1 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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
	
	@Length(min=0, max=32, message="申请单Id长度必须介于 0 和 32 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Length(min=0, max=200, message="公司名称长度必须介于 0 和 200 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=30, message="统一社会信用代码长度必须介于 0 和 30 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=50, message="法人代表长度必须介于 0 和 50 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@Length(min=0, max=20, message="技术负责人长度必须介于 0 和 20 之间")
	public String getTechnologyLeader() {
		return technologyLeader;
	}

	public void setTechnologyLeader(String technologyLeader) {
		this.technologyLeader = technologyLeader;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=30, message="证书编号长度必须介于 0 和 30 之间")
	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	
	@Length(min=0, max=200, message="证书正本长度必须介于 0 和 200 之间")
	public String getCertificateOriginal() {
		return certificateOriginal;
	}

	public void setCertificateOriginal(String certificateOriginal) {
		this.certificateOriginal = certificateOriginal;
	}
	
	@Length(min=0, max=200, message="证书副本长度必须介于 0 和 200 之间")
	public String getCertificateCounterpart() {
		return certificateCounterpart;
	}

	public void setCertificateCounterpart(String certificateCounterpart) {
		this.certificateCounterpart = certificateCounterpart;
	}

	public String getCertificatePdf() {
		return certificatePdf;
	}

	public void setCertificatePdf(String certificatePdf) {
		this.certificatePdf = certificatePdf;
	}
	
}