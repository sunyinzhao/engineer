/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业完成项目Entity
 * @author xqg
 * @version 2018-05-08
 */
public class EnterpriseProject extends DataEntity<EnterpriseProject> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	private String projectServiceRang;		// 项目服务范围
	private String specialty;		// 专业
	private String applyServiceRang;		// 申请的服务范围
	private BigDecimal projectInvestAmount;		// 项目总投资（万元）
	private String constructionScale;		// 建设规模
	private Date completeDate;		// 完成时间
	private String entrustCompany;		// 委托单位
	private String pid;		// 所属用户Id
	
	public EnterpriseProject() {
		super();
	}

	public EnterpriseProject(String id){
		super(id);
	}

	@Length(min=0, max=200, message="项目名称长度必须介于 0 和 200 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=50, message="项目服务范围长度必须介于 0 和 50 之间")
	public String getProjectServiceRang() {
		return projectServiceRang;
	}

	public void setProjectServiceRang(String projectServiceRang) {
		this.projectServiceRang = projectServiceRang;
	}
	
	@Length(min=0, max=50, message="专业长度必须介于 0 和 50 之间")
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Length(min=0, max=50, message="申请的服务范围长度必须介于 0 和 50 之间")
	public String getApplyServiceRang() {
		return applyServiceRang;
	}

	public void setApplyServiceRang(String applyServiceRang) {
		this.applyServiceRang = applyServiceRang;
	}
	
	public BigDecimal getProjectInvestAmount() {
		return projectInvestAmount;
	}

	public void setProjectInvestAmount(BigDecimal projectInvestAmount) {
		this.projectInvestAmount = projectInvestAmount;
	}
	
	@Length(min=0, max=50, message="建设规模长度必须介于 0 和 50 之间")
	public String getConstructionScale() {
		return constructionScale;
	}

	public void setConstructionScale(String constructionScale) {
		this.constructionScale = constructionScale;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	@Length(min=0, max=200, message="委托单位长度必须介于 0 和 200 之间")
	public String getEntrustCompany() {
		return entrustCompany;
	}

	public void setEntrustCompany(String entrustCompany) {
		this.entrustCompany = entrustCompany;
	}
	
	@Length(min=0, max=32, message="所属用户Id长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}