/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业中所有的人员Entity
 * @author xqg
 * @version 2018-05-05
 */
public class PersonRecord extends DataEntity<PersonRecord> {
	
	private static final long serialVersionUID = 1L;
	private String batchId;		// 批次ID  用来获得可多类申请的所有内容
	private String personId;		// 咨询师ID
	private Date declareDate;	// 申请日期
	private String batchType;		// 批次申请类型   记录所选的所有类型,中间用逗号隔开
	private String declareType;	// 申请类型       某一个所选的类型 
	private String batchStatus;	// 批次申请的流程节点     参照单据查询画面
	private String declareStatus;	// 某一个申请的流程节点   参照单据查询画面
	private String expertId;		// 专家ID 
	private String declareResult;	//某一个申请的结果 1:通过  2：部分通过 3：不通过
	private String batchResult;	//批次的结果   1:通过  2：部分通过 3：不通过
	private String utilFeedback;//单位是否反馈
	private String firstCexpertId;		// 预审专家1ID 
	private String secondCexpertId;		// 预审专家2ID
	private String firstCdeclareResult; //预审专家1结论
	private String secondCdeclareResult;//预审专家2结论
	private String firstZexpertId;//终审专家1
	private String secondZexpertId;//终审专家2
	private String firstZdeclareResult;//终审专家1结论
	private String secondZdeclareResult;//终审专家2结论
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private String oldRegisterMainSpecialty;		// 原主专业
	private String oldRegisterAuxiliarySpecialty;		// 原辅专业
	private String companyName; //执业单位
	private String oldCompanyName; //原执业单位
	private String companyId; //执业单位
	private String officeName;//地方
	private String returnReason;//地方退回单位申请单原因
	private String localReceiveReturnReason;//地方待接收退回原因
	private Date publicDate;//公告日期
	private String isPunish;//处罚标识   0：未处罚   1：已处罚
	private String impropriety;//惩罚标识   1：无异常  2：执业检查不合格  注销1年  3：材料作假 注销3年  
	private Date declareStartDate;
	private Date declareEndDate;
	private String fdeclareResult;//结论
	private String batchNo;//批次号
	private String returnType;//退回类型
	private String fExpertId; //复议专家分配
	
	/**
	 * @return the fExpertId
	 */
	public String getfExpertId() {
		return fExpertId;
	}
	/**
	 * @param fExpertId the fExpertId to set
	 */
	public void setfExpertId(String fExpertId) {
		this.fExpertId = fExpertId;
	}
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	/**
	 * @return the fdeclareResult
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param fdeclareResult the fdeclareResult to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	/**
	 * @return the fdeclareResult
	 */
	public String getFdeclareResult() {
		return fdeclareResult;
	}
	/**
	 * @param fdeclareResult the fdeclareResult to set
	 */
	public void setFdeclareResult(String fdeclareResult) {
		this.fdeclareResult = fdeclareResult;
	}
	/*
	 * worker表字段
	 */
	private String workerName;
	private String certificatesNum;
	
	/**
	 * @return the certificatesNum
	 */
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
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}
	/**
	 * @param workerName the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	/**
	 * @return the declareStartDate
	 */
	public Date getDeclareStartDate() {
		return declareStartDate;
	}
	/**
	 * @param declareStartDate the declareStartDate to set
	 */
	public void setDeclareStartDate(Date declareStartDate) {
		this.declareStartDate = declareStartDate;
	}
	/**
	 * @return the declareEndDate
	 */
	public Date getDeclareEndDate() {
		return declareEndDate;
	}
	/**
	 * @param declareEndDate the declareEndDate to set
	 */
	public void setDeclareEndDate(Date declareEndDate) {
		this.declareEndDate = declareEndDate;
	}
	/**
	 * @return the isPunish
	 */
	public String getIsPunish() {
		return isPunish;
	}
	/**
	 * @param isPunish the isPunish to set
	 */
	public void setIsPunish(String isPunish) {
		this.isPunish = isPunish;
	}
	/**
	 * @return the impropriety
	 */
	public String getImpropriety() {
		return impropriety;
	}
	/**
	 * @param impropriety the impropriety to set
	 */
	public void setImpropriety(String impropriety) {
		this.impropriety = impropriety;
	}
	/**
	 * @return the publicDate
	 */
	public Date getPublicDate() {
		return publicDate;
	}
	/**
	 * @param publicDate the publicDate to set
	 */
	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}
	/**
	 * @return the localReceiveReturnReason
	 */
	public String getLocalReceiveReturnReason() {
		return localReceiveReturnReason;
	}
	/**
	 * @param localReceiveReturnReason the localReceiveReturnReason to set
	 */
	public void setLocalReceiveReturnReason(String localReceiveReturnReason) {
		this.localReceiveReturnReason = localReceiveReturnReason;
	}
	private EnterpriseWorkers enterpriseWorkers;
	
	/**
	 * @return the oldCompanyName
	 */
	public String getOldCompanyName() {
		return oldCompanyName;
	}
	/**
	 * @param oldCompanyName the oldCompanyName to set
	 */
	public void setOldCompanyName(String oldCompanyName) {
		this.oldCompanyName = oldCompanyName;
	}
	/**
	 * @return the enterpriseWorkers
	 */
	public EnterpriseWorkers getEnterpriseWorkers() {
		return enterpriseWorkers;
	}
	/**
	 * @param enterpriseWorkers the enterpriseWorkers to set
	 */
	public void setEnterpriseWorkers(EnterpriseWorkers enterpriseWorkers) {
		this.enterpriseWorkers = enterpriseWorkers;
	}
	/**
	 * @return the oldRegisterMainSpecialty
	 */
	public String getOldRegisterMainSpecialty() {
		return oldRegisterMainSpecialty;
	}
	/**
	 * @param oldRegisterMainSpecialty the oldRegisterMainSpecialty to set
	 */
	public void setOldRegisterMainSpecialty(String oldRegisterMainSpecialty) {
		this.oldRegisterMainSpecialty = oldRegisterMainSpecialty;
	}
	/**
	 * @return the oldRegisterAuxiliarySpecialty
	 */
	public String getOldRegisterAuxiliarySpecialty() {
		return oldRegisterAuxiliarySpecialty;
	}
	/**
	 * @param oldRegisterAuxiliarySpecialty the oldRegisterAuxiliarySpecialty to set
	 */
	public void setOldRegisterAuxiliarySpecialty(String oldRegisterAuxiliarySpecialty) {
		this.oldRegisterAuxiliarySpecialty = oldRegisterAuxiliarySpecialty;
	}
	/**
	 * @return the returnReason
	 */
	public String getReturnReason() {
		return returnReason;
	}
	/**
	 * @param returnReason the returnReason to set
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	/**
	 * @return the officeName
	 */
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
	 * @return the companyName
	 */
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
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the firstCdeclareResult
	 */
	public String getFirstCdeclareResult() {
		return firstCdeclareResult;
	}
	/**
	 * @param firstCdeclareResult the firstCdeclareResult to set
	 */
	public void setFirstCdeclareResult(String firstCdeclareResult) {
		this.firstCdeclareResult = firstCdeclareResult;
	}
	/**
	 * @return the secondCdeclareResult
	 */
	public String getSecondCdeclareResult() {
		return secondCdeclareResult;
	}
	/**
	 * @param secondCdeclareResult the secondCdeclareResult to set
	 */
	public void setSecondCdeclareResult(String secondCdeclareResult) {
		this.secondCdeclareResult = secondCdeclareResult;
	}
	/**
	 * @return the firstZexpertId
	 */
	public String getFirstZexpertId() {
		return firstZexpertId;
	}
	/**
	 * @param firstZexpertId the firstZexpertId to set
	 */
	public void setFirstZexpertId(String firstZexpertId) {
		this.firstZexpertId = firstZexpertId;
	}
	/**
	 * @return the secondZexpertId
	 */
	public String getSecondZexpertId() {
		return secondZexpertId;
	}
	/**
	 * @param secondZexpertId the secondZexpertId to set
	 */
	public void setSecondZexpertId(String secondZexpertId) {
		this.secondZexpertId = secondZexpertId;
	}
	/**
	 * @return the firstZdeclareResult
	 */
	public String getFirstZdeclareResult() {
		return firstZdeclareResult;
	}
	/**
	 * @param firstZdeclareResult the firstZdeclareResult to set
	 */
	public void setFirstZdeclareResult(String firstZdeclareResult) {
		this.firstZdeclareResult = firstZdeclareResult;
	}
	/**
	 * @return the secondZdeclareResult
	 */
	public String getSecondZdeclareResult() {
		return secondZdeclareResult;
	}
	/**
	 * @param secondZdeclareResult the secondZdeclareResult to set
	 */
	public void setSecondZdeclareResult(String secondZdeclareResult) {
		this.secondZdeclareResult = secondZdeclareResult;
	}
	/**
	 * @return the utilFeedback
	 */
	public String getUtilFeedback() {
		return utilFeedback;
	}
	/**
	 * @param utilFeedback the utilFeedback to set
	 */
	public void setUtilFeedback(String utilFeedback) {
		this.utilFeedback = utilFeedback;
	}
	/**
	 * @return the firstCexpertId
	 */
	public String getFirstCexpertId() {
		return firstCexpertId;
	}
	/**
	 * @param firstCexpertId the firstCexpertId to set
	 */
	public void setFirstCexpertId(String firstCexpertId) {
		this.firstCexpertId = firstCexpertId;
	}
	/**
	 * @return the secondCexpertId
	 */
	public String getSecondCexpertId() {
		return secondCexpertId;
	}
	/**
	 * @param secondCexpertId the secondCexpertId to set
	 */
	public void setSecondCexpertId(String secondCexpertId) {
		this.secondCexpertId = secondCexpertId;
	}
	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	/**
	 * @return the declareDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeclareDate() {
		return declareDate;
	}
	/**
	 * @param declareDate the declareDate to set
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}
	/**
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}
	/**
	 * @param batchType the batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	/**
	 * @return the declareType
	 */
	public String getDeclareType() {
		return declareType;
	}
	/**
	 * @param declareType the declareType to set
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	/**
	 * @return the batchStatus
	 */
	public String getBatchStatus() {
		return batchStatus;
	}
	/**
	 * @param batchStatus the batchStatus to set
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	/**
	 * @return the declareStatus
	 */
	public String getDeclareStatus() {
		return declareStatus;
	}
	/**
	 * @param declareStatus the declareStatus to set
	 */
	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}
	/**
	 * @return the expertId
	 */
	public String getExpertId() {
		return expertId;
	}
	/**
	 * @param expertId the expertId to set
	 */
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	/**
	 * @return the declareResult
	 */
	public String getDeclareResult() {
		return declareResult;
	}
	/**
	 * @param declareResult the declareResult to set
	 */
	public void setDeclareResult(String declareResult) {
		this.declareResult = declareResult;
	}
	/**
	 * @return the batchResult
	 */
	public String getBatchResult() {
		return batchResult;
	}
	/**
	 * @param batchResult the batchResult to set
	 */
	public void setBatchResult(String batchResult) {
		this.batchResult = batchResult;
	}
	
	
	
}