/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

/**
 * 企业中所有的人员Entity
 * @author xqg
 * @version 2018-05-05
 */
public class ReportEnterpriseWorkers extends DataEntity<ReportEnterpriseWorkers> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 性别
	private String nation;		// 民族
	private String certificatesType;		// 证件类型
	private String certificatesNum;		// 身份证号
	private String mobile;		// 手机
	private String specialty;		// 所学专业
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private String registerCertificateNum;		// 登记证书编号
	private String engageSpecialty;		// 从事专业
	private Integer age;		// 年龄
	private String pid;		// 公司Id
	private String phone;		// 电话
	private String retire;		// 退休
	private String type;		// 人员类型
	private String companyName; //执业单位
	private String reviewCompany; //审查单位
	private String zSpecialty;//主专业执业专长
	private String fSpecialty;//辅专业执业专长
	private String companyContact;//单位联系人
	private String companyArea;//单位电话区号
	private String companyTel;//单位电话
	private String getyear;//职业资格证书取得年份
	private String professioncardNum;//职业资格证书编号
	private String address;//通讯地址
	private String decalerType; //申请单类型
	
	/**
	 * @return the decalerType
	 */
	public String getDecalerType() {
		return decalerType;
	}

	/**
	 * @param decalerType the decalerType to set
	 */
	public void setDecalerType(String decalerType) {
		this.decalerType = decalerType;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	private String isFirst; //用于判断是否第一次提交工作经历.  如果确认之后,需要设置此worker的 标识为 0; 表示不可修改增加

	public String getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}
	private String isFreeze; //用于判断是否冻结  add by gaoyongjian 20181011
	public String getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(String isfreeze) {
		this.isFreeze = isfreeze;
	}
	
	private Date freezeDate; //开始冻结日期  add by gaoyongjian 20181011
	public Date getFreezeDate() {
		return freezeDate;
	}
	public void setFreezeDate(Date freezedate) {
		this.freezeDate = freezedate;
	}
	private Date freezeCycle; //冻结周期  add by gaoyongjian 20181011
	public Date getFreezeCycle() {
		return freezeCycle;
	}
	public void setFreezeCycle(Date freezecycle) {
		this.freezeCycle = freezecycle;
	}
	public void setGetyear(String getyear) {
		this.getyear = getyear;
	}
	private String isRegister;//0：未注册，1：已注册
	private String isValid;//0：登记且无效，1：登记且有效 2:未申请登记
	private String email;//邮箱
	
	private String userId;//sys_user
	private String loginName;
	private String confimFlg;//审核状态
	private String personRecordId;
	private String declareStatus;
	private String batchStatus;
	private String officeId;//sys_user表中的office_id
	private String officeName;//所在地
	private String utilFeedback;//单位是否反馈
	private String firstCexpertId;//预审专家1
	private String secondCexpertId;		// 预审专家2ID
	private String firstExpertName;//预审专家1名字
	private String secondExpertName;// 预审专家2名字
	private String firstZexpertId;//终审专家1
	private String secondZexpertId;//终审专家2
	private String changeItemId;//变更履历表id
	private String changeType;//变更类型
	private String newValue;//变更的值
	private Date validDate;//有效期至
	private Date aollowDate;//批准日期
	private String recordNumber;//user表中的备案编号
	private String batchId;//批次ID
	private String expertName;//专家姓名
	private String expertResult;//专家结论
	private String isPunish;//处罚标识   0：未处罚   1：已处罚
	private String impropriety;//惩罚标识   1：无异常  2：执业检查不合格  注销1年  3：材料作假 注销3年  
	private String flag;//标记；
	private String firstZdeclareResult;//终审专家1结论
	private String secondZdeclareResult;//终审专家2结论
	private String firstCdeclareResult;//预审专家1结论
	private String secondCdeclareResult;//预审专家2结论
	private String contactsZy;//执业登记联系人
	private String contactsZyPhone;//执业登记联系人电话
	private String batchNo;//批次号
	private String declareType;//变更类型
	private String fdeclareResult;//最后结论

	//-----------------打印报表中所使用start--------------------------
	private int index;//序号
	private String initialRegistration;//初始登记
	private String continueRegistration;//继续登记
	private String changeUnit;//变更单位
	private String changeSpecialty;//变更专业
	private String cancellationOfRegistration;//注销登记
	private int irTatol;//初始登记总数
	private int cuTatol;//变更单位总数
	private int crTatol;//继续登记总数
	private int csTatol;//变更专业总计
	private int corTatol;//注销登记
	private int aleryReportTatol;//已报终审合计
	private long tatol;//总计
	private int subtotal;//小计
	
	/**
	 * @return the subtotal
	 */
	public int getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * @return the corTatol
	 */
	public int getCorTatol() {
		return corTatol;
	}

	/**
	 * @param corTatol the corTatol to set
	 */
	public void setCorTatol(int corTatol) {
		this.corTatol = corTatol;
	}

	/**
	 * @return the tatol
	 */
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
	 * @return the aleryReportTatol
	 */
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
	 * @return the irTatol
	 */
	public int getIrTatol() {
		return irTatol;
	}

	/**
	 * @param irTatol the irTatol to set
	 */
	public void setIrTatol(int irTatol) {
		this.irTatol = irTatol;
	}

	/**
	 * @return the cuTatol
	 */
	public int getCuTatol() {
		return cuTatol;
	}

	/**
	 * @param cuTatol the cuTatol to set
	 */
	public void setCuTatol(int cuTatol) {
		this.cuTatol = cuTatol;
	}

	/**
	 * @return the crTatol
	 */
	public int getCrTatol() {
		return crTatol;
	}

	/**
	 * @param crTatol the crTatol to set
	 */
	public void setCrTatol(int crTatol) {
		this.crTatol = crTatol;
	}

	/**
	 * @return the csTatol
	 */
	public int getCsTatol() {
		return csTatol;
	}

	/**
	 * @param csTatol the csTatol to set
	 */
	public void setCsTatol(int csTatol) {
		this.csTatol = csTatol;
	}

	/**
	 * @return the getyear
	 */
	public String getGetyear() {
		return getyear;
	}

	public ReportEnterpriseWorkers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportEnterpriseWorkers(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	//-----------------打印报表中所使用end--------------------------
	

	/**
	 * @return the declareType
	 */
	public String getDeclareType() {
		return declareType;
	}

	/**
	 * @return the initialRegistration
	 */
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
	 * @return the continueRegistration
	 */
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
	 * @return the changeUnit
	 */
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
	 * @return the cancellationOfRegistration
	 */
	public String getCancellationOfRegistration() {
		return cancellationOfRegistration;
	}

	/**
	 * @param cancellationOfRegistration the cancellationOfRegistration to set
	 */
	public void setCancellationOfRegistration(String cancellationOfRegistration) {
		this.cancellationOfRegistration = cancellationOfRegistration;
	}

	/**
	 * @param declareType the declareType to set
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public String getFdeclareResult() {
		return fdeclareResult;
	}

	public void setFdeclareResult(String fdeclareResult) {
		this.fdeclareResult = fdeclareResult;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the contactsZy
	 */
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
	public String getContactsZyPhone() {
		return contactsZyPhone;
	}

	/**
	 * @param contactsZyPhone the contactsZyPhone to set
	 */
	public void setContactsZyPhone(String contactsZyPhone) {
		this.contactsZyPhone = contactsZyPhone;
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
	 * @return the expertResult
	 */
	public String getExpertResult() {
		return expertResult;
	}

	/**
	 * @param expertResult the expertResult to set
	 */
	public void setExpertResult(String expertResult) {
		this.expertResult = expertResult;
	}

	/**
	 * @return the firstZexpertResult
	 */
	public String getSecondZdeclareResult() {
		return secondZdeclareResult;
	}

	/**
	 * @param firstZexpertResult the firstZexpertResult to set
	 */
	public void setSecondZdeclareResult(String secondZdeclareResult) {
		this.secondZdeclareResult = secondZdeclareResult;
	}
	
	/**
	 * @return the firstZexpertResult
	 */
	public String getFirstZdeclareResult() {
		return firstZdeclareResult;
	}

	/**
	 * @param firstZexpertResult the firstZexpertResult to set
	 */
	public void setFirstZdeclareResult(String firstZdeclareResult) {
		this.firstZdeclareResult = firstZdeclareResult;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
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
	 * @return the expertName
	 */
	public String getExpertName() {
		return expertName;
	}

	/**
	 * @param expertName the expertName to set
	 */
	public void setExpertName(String expertName) {
		this.expertName = expertName;
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
	 * @return the firstExpertName
	 */
	public String getFirstExpertName() {
		return firstExpertName;
	}

	/**
	 * @param firstExpertName the firstExpertName to set
	 */
	public void setFirstExpertName(String firstExpertName) {
		this.firstExpertName = firstExpertName;
	}

	/**
	 * @return the secondExpertName
	 */
	public String getSecondExpertName() {
		return secondExpertName;
	}

	/**
	 * @param secondExpertName the secondExpertName to set
	 */
	public void setSecondExpertName(String secondExpertName) {
		this.secondExpertName = secondExpertName;
	}

	/**
	 * @return the recordNumber
	 */
	public String getRecordNumber() {
		return recordNumber;
	}

	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	/**
	 * @return the validDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate the validDate to set
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the aollowDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAollowDate() {
		return aollowDate;
	}

	/**
	 * @param aollowDate the aollowDate to set
	 */
	public void setAollowDate(Date aollowDate) {
		this.aollowDate = aollowDate;
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
	 * @return the changeItemId
	 */
	public String getChangeItemId() {
		return changeItemId;
	}

	/**
	 * @param changeItemId the changeItemId to set
	 */
	public void setChangeItemId(String changeItemId) {
		this.changeItemId = changeItemId;
	}

	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
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
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the officeId
	 */
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
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
	 * @return the personRecordId
	 */
	public String getPersonRecordId() {
		return personRecordId;
	}

	/**
	 * @param personRecordId the personRecordId to set
	 */
	public void setPersonRecordId(String personRecordId) {
		this.personRecordId = personRecordId;
	}

	/**
	 * @return the confimFlg
	 */
	public String getConfimFlg() {
		return confimFlg;
	}

	/**
	 * @param confimFlg the confimFlg to set
	 */
	public void setConfimFlg(String confimFlg) {
		this.confimFlg = confimFlg;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the isRegister
	 */
	public String getIsRegister() {
		return isRegister;
	}

	/**
	 * @param isRegister the isRegister to set
	 */
	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}

	/**
	 * @return the isValid
	 */
	public String getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the professioncardNum
	 */
	public String getProfessioncardNum() {
		return professioncardNum;
	}

	/**
	 * @param professioncardNum the professioncardNum to set
	 */
	public void setProfessioncardNum(String professioncardNum) {
		this.professioncardNum = professioncardNum;
	}

	@ExcelField(title="审查单位", align=2, sort=18)
	public String getReviewCompany() {
		return reviewCompany;
	}

	public void setReviewCompany(String reviewCompany) {
		this.reviewCompany = reviewCompany;
	}

	@ExcelField(title="主专业执业专长", align=2, sort=24)
	public String getzSpecialty() {
		return zSpecialty;
	}

	public void setzSpecialty(String zSpecialty) {
		this.zSpecialty = zSpecialty;
	}

	@ExcelField(title="辅专业执业专长", align=2, sort=26)
	public String getfSpecialty() {
		return fSpecialty;
	}


	public void setfSpecialty(String fSpecialty) {
		this.fSpecialty = fSpecialty;
	}

	@ExcelField(title="单位联系人", align=2, sort=30)
	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	@ExcelField(title="单位电话区号", align=2, sort=32)
	public String getCompanyArea() {
		return companyArea;
	}

	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}

	@ExcelField(title="单位电话", align=2, sort=34)
	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	@ExcelField(title="执业单位", align=2, sort=16)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	@ExcelField(title="性别", align=2, sort=14)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="民族长度必须介于 0 和 255 之间")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Length(min=0, max=1, message="证件类型长度必须介于 0 和 1 之间")
	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	@Length(min=0, max=18, message="身份证号长度必须介于 0 和 18 之间")
	@ExcelField(title="身份证号", align=2, sort=12)
	public String getCertificatesNum() {
		return certificatesNum;
	}

	public void setCertificatesNum(String certificatesNum) {
		this.certificatesNum = certificatesNum;
	}
	
	
	@Length(min=0, max=15, message="手机长度必须介于 0 和 15 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=50, message="所学专业长度必须介于 0 和 50 之间")
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	
	@Length(min=0, max=50, message="主专业长度必须介于 0 和 50 之间")
	@ExcelField(title="主专业", align=2, sort=20)
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}

	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}
	
	@Length(min=0, max=50, message="登记辅助专业长度必须介于 0 和 50 之间")
	@ExcelField(title="辅专业", align=2, sort=22)
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}

	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}
	
	@Length(min=0, max=255, message="登记证书编号长度必须介于 0 和 255 之间")
	@ExcelField(title="登记证书编号", align=2, sort=28)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}

	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}
	
	@Length(min=0, max=50, message="从事专业长度必须介于 0 和 50 之间")
	public String getEngageSpecialty() {
		return engageSpecialty;
	}

	public void setEngageSpecialty(String engageSpecialty) {
		this.engageSpecialty = engageSpecialty;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Length(min=0, max=32, message="申请单Id长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Length(min=0, max=15, message="电话长度必须介于 0 和 15 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=1, message="退休长度必须介于 0 和 1 之间")
	public String getRetire() {
		return retire;
	}

	public void setRetire(String retire) {
		this.retire = retire;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}