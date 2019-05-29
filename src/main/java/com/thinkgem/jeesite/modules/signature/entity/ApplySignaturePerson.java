/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;

/**
 * 签章咨询师Entity
 * @author xqg
 * @version 2018-09-03
 */
public class ApplySignaturePerson extends DataEntity<ApplySignaturePerson> {
	
	private static final long serialVersionUID = 1L;
	private String signatureId;		// 签章申请单Id
	private String personId;		// 咨询师Id
	private String duty;		// 承担工作内容职责
	private String status;		// 签章状态  0：未签章  1：已签章
	
	private ApplySignature applySignature = new ApplySignature(); //申请单
	private EnterpriseWorkers enterpriseWorkers = new EnterpriseWorkers();  //咨询师基本信息
	private String enterpriseName;		// 企业帐号Id
	
	private int absolutePage;  // 签章页码
	private int absoluteX;     // 签章X
	private int absoluteY;     // 签章Y
	
	
	
	/*只用于显示   start  
	private String name;		// 姓名
	private String sex;		// 性别
	private String nation;		// 民族
	private String certificatesType;		// 证件类型
	private String certificatesNum;		// 身份证号
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	
	
	
	private String projectName;			// 项目名称
	private String services;			// 服务范围
	private String childServices;		// 服务范围小类
	private String area;				// 地区
	private String planGrade;			// 规划级别
	private String enterpriseId;		// 企业帐号Id
	private Date completeDate;			// 完成时间
	private String projectSpecialty;		// 项目专业
	private BigDecimal projectInvestAmount;	// 建设规模或投资额
	private String signatureFilePath;       // 签章文件路径 相对路径
	*/
	/*只用于显示    end */	
	
	
	
	public ApplySignaturePerson() {
		super();
	}

	public ApplySignaturePerson(String id){
		super(id);
	}

	@Length(min=0, max=32, message="签章申请单Id长度必须介于 0 和 32 之间")
	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}
	
	@Length(min=0, max=32, message="咨询师Id长度必须介于 0 和 32 之间")
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@Length(min=0, max=2, message="承担工作内容职责长度必须介于 0 和 2 之间")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@Length(min=0, max=1, message="签章状态  0：未签章  1：已签章长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public ApplySignature getApplySignature() {
		return applySignature;
	}

	public void setApplySignature(ApplySignature applySignature) {
		this.applySignature = applySignature;
	}

	public EnterpriseWorkers getEnterpriseWorkers() {
		return enterpriseWorkers;
	}

	public void setEnterpriseWorkers(EnterpriseWorkers enterpriseWorkers) {
		this.enterpriseWorkers = enterpriseWorkers;
	}

	public int getAbsolutePage() {
		return absolutePage;
	}

	public void setAbsolutePage(int absolutePage) {
		this.absolutePage = absolutePage;
	}

	public int getAbsoluteX() {
		return absoluteX;
	}

	public void setAbsoluteX(int absoluteX) {
		this.absoluteX = absoluteX;
	}

	public int getAbsoluteY() {
		return absoluteY;
	}

	public void setAbsoluteY(int absoluteY) {
		this.absoluteY = absoluteY;
	}
	
}