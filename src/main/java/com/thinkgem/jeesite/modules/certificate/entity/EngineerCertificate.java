/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.certificate.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 咨询工程师证书Entity
 * @author xqg
 * @version 2018-11-29
 */
public class EngineerCertificate extends DataEntity<EngineerCertificate> {

	private static final long serialVersionUID = 1L;
	private String name;                    // 姓名
	private String sex;                        // 性别
	private String registerCertificateNum;        // 证书编号
	private String awardOrg;                // 颁发机构
	private String specialtyMain;            // 主专业
	private String specialtyAuxiliary;        // 辅专业
	private Date startDate;                    // 批准日期
	private Date endDate ;						// 截止日期
	private String batchId;                    // 批次ID
	private String practisingCompany;        // 执业单位
	private String certificateNum;            // 身份证号
	private String certificatePdf;            // 未盖章的原pdf文件
	private String certificateOriginal;        // 证书正本
	private String workerId;
	private String officeId;  //所属地区Id
	private String officeName; //所属地区Name

	public EngineerCertificate() {
		super();
	}

	public EngineerCertificate(String id) {
		super(id);
	}

	@Length(min = 0, max = 200, message = "姓名长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 30, message = "性别长度必须介于 0 和 30 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Length(min = 0, max = 50, message = "颁发机构长度必须介于 0 和 50 之间")
	public String getAwardOrg() {
		return awardOrg;
	}

	public void setAwardOrg(String awardOrg) {
		this.awardOrg = awardOrg;
	}

	@Length(min = 0, max = 300, message = "主专业长度必须介于 0 和 300 之间")
	public String getSpecialtyMain() {
		return specialtyMain;
	}

	public void setSpecialtyMain(String specialtyMain) {
		this.specialtyMain = specialtyMain;
	}

	@Length(min = 0, max = 20, message = "辅专业长度必须介于 0 和 20 之间")
	public String getSpecialtyAuxiliary() {
		return specialtyAuxiliary;
	}

	public void setSpecialtyAuxiliary(String specialtyAuxiliary) {
		this.specialtyAuxiliary = specialtyAuxiliary;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Length(min = 0, max = 32, message = "批次ID长度必须介于 0 和 32 之间")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Length(min = 0, max = 50, message = "执业单位长度必须介于 0 和 50 之间")
	public String getPractisingCompany() {
		return practisingCompany;
	}

	public void setPractisingCompany(String practisingCompany) {
		this.practisingCompany = practisingCompany;
	}


	@Length(min = 0, max = 200, message = "未盖章的原pdf文件长度必须介于 0 和 200 之间")
	public String getCertificatePdf() {
		return certificatePdf;
	}

	public void setCertificatePdf(String certificatePdf) {
		this.certificatePdf = certificatePdf;
	}

	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}

	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public String getCertificateOriginal() {
		return certificateOriginal;
	}

	public void setCertificateOriginal(String certificateOriginal) {
		this.certificateOriginal = certificateOriginal;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}