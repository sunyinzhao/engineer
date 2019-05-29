/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 企业中所有的人员Entity
 * @author xqg
 * @version 2018-05-05
 */
public class EnterpriseWorkers extends DataEntity<EnterpriseWorkers> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 性别
	private String nation;		// 民族
	private String certificatesType;		// 证件类型
	private String certificatesNum;		// 身份证号
	private String education;		// 学历
	private String mobile;		// 手机
	private String title;		// 职称
	private String titleSpecialty;//职称专业
	private String specialty;		// 所学专业
	private String school;		// 毕业院校
	private String nationality;		// 国籍
	private Date graduationDate;		// 毕业日期
	private Date entryDate;		// 入职日期
	private String workResume;		// 工作简历
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private Integer ziXunYeWuNianXian;		// 从事工程咨询业务年限
	private String registerCertificateNum;		// 登记证书编号
	private String engageSpecialty;		// 从事专业
	private Integer age;		// 年龄
	private String pid;		// 公司Id
	private String phone;		// 电话
	private String retire;		// 退休
	private String type;		// 人员类型
	private String fileNo;	//	文件路径
	private String companyName; //执业单位
	private String reviewCompany; //审查单位
	private String zSpecialty;//主专业执业专长
	private String fSpecialty;//辅专业执业专长
	private String companyContact;//单位联系人
	private String companyArea;//单位电话区号
	private String companyTel;//单位电话
	private String getyear;//职业资格证书取得年份
	private String professioncardNum;//职业资格证书编号
	private String pictureUrl;//个人图片路径
	private String pictureName;//个人图片名字
	private String address;//通讯地址
	private String decalerType; //申请单类型
	private String zdeclareResult;//终审结论
	private String firstFexpertResult;//
    private String secondFexpertResult;//
    private String fexpertName;
    private String officeCode;
	private String zhengShuFlag; //证书是否生成 0：未生成 1：已生成
	private String electronicChapterFlag; //是否生成电子证书0：未生成 1：已生成
	private String companyNameold;
    public String getCompanyNameold() {
        return companyNameold;
    }

    public void setCompanyNameold(String companyNameold) {
        this.companyNameold = companyNameold;
    }
    
    
    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public String getFexpertName() {
        return fexpertName;
    }

    public void setFexpertName(String fexpertName) {
        this.fexpertName = fexpertName;
    }
    

    public String getSecondFexpertResult() {
        return secondFexpertResult;
    }

    public void setSecondFexpertResult(String secondFexpertResult) {
        this.secondFexpertResult = secondFexpertResult;
    }

    public String getFirstFexpertResult() {
        return firstFexpertResult;
    }

    public void setFirstFexpertResult(String firstFexpertResult) {
        this.firstFexpertResult = firstFexpertResult;
    }
	
	
	/**
	 * @return the zdeclareResult
	 */
	public String getZdeclareResult() {
		return zdeclareResult;
	}

	/**
	 * @param zdeclareResult the zdeclareResult to set
	 */
	public void setZdeclareResult(String zdeclareResult) {
		this.zdeclareResult = zdeclareResult;
	}
	private String fdeclareResult;//复审结论
	/**
	 * @return the zdeclareResult
	 */
	public String getFdeclareResult() {
		return fdeclareResult;
	}

	/**
	 * @param zdeclareResult the zdeclareResult to set
	 */
	public void setFdeclareResult(String fdeclareResult) {
		this.fdeclareResult = fdeclareResult;
	}



	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	private String areaId;//

	
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

	/**
	 * @return the pictureName
	 */
	public String getPictureName() {
		return pictureName;
	}

	/**
	 * @param pictureName the pictureName to set
	 */
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * @param pictureUrl the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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
	private String pictureLock; //一寸照片  add by gaoyongjian 20181011
	public String getPictureLock() {
		return pictureLock;
	}
	public void setPictureLock(String picturelock) {
		this.pictureLock = picturelock;
	}

	public String getGetyear() {
		return getyear;
	}
    
	public void setGetyear(String getyear) {
		this.getyear = getyear;
	}
	private String selectStatus;  //查询时包含的状态
	
	private String isRegister;//0：未注册，1：已注册
	private String isValid;//0：登记且无效，1：登记且有效 2:未申请登记
	private String email;//邮箱
	
	private String userId;//sys_user
	private String loginName;
	private String password;
	private String confimFlg;//审核状态
	private String personRecordId;
	private String personRecordIds;//用于接受未分配的申请单Id
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
	private String roleId;//权限
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
	private String fExpertId;//复议专家Id
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


	private int index;//序号
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
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the personRecordIds
	 */
	public String getPersonRecordIds() {
		return personRecordIds;
	}

	/**
	 * @param personRecordIds the personRecordIds to set
	 */
	public void setPersonRecordIds(String personRecordIds) {
		this.personRecordIds = personRecordIds;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	@ExcelField(title="执业单位", align=2, sort=16)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private List<EnterpriseAttachment> enterpriseAttachmentList = Lists.newArrayList();		// 子表列表
//	private String file;		// 附件
	
	public EnterpriseWorkers() {
		super();
	}

	public EnterpriseWorkers(String id){
		super(id);
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
	
	@Length(min=0, max=10, message="学历长度必须介于 0 和 10 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=15, message="手机长度必须介于 0 和 15 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=10, message="职称长度必须介于 0 和 10 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=50, message="所学专业长度必须介于 0 和 50 之间")
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Length(min=0, max=50, message="毕业院校长度必须介于 0 和 50 之间")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	@Length(min=0, max=50, message="国籍长度必须介于 0 和 50 之间")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public String getWorkResume() {
		return workResume;
	}

	public void setWorkResume(String workResume) {
		this.workResume = workResume;
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
	

	public Integer getZiXunYeWuNianXian() {
		return ziXunYeWuNianXian;
	}

	public void setZiXunYeWuNianXian(Integer ziXunYeWuNianXian) {
		this.ziXunYeWuNianXian = ziXunYeWuNianXian;
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

	public List<EnterpriseAttachment> getEnterpriseAttachmentList() {
		return enterpriseAttachmentList;
	}

	public void setEnterpriseAttachmentList(List<EnterpriseAttachment> enterpriseAttachmentList) {
		this.enterpriseAttachmentList = enterpriseAttachmentList;
	}

	public String getTitleSpecialty() {
		return titleSpecialty;
	}

	public void setTitleSpecialty(String titleSpecialty) {
		this.titleSpecialty = titleSpecialty;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

	public String getZhengShuFlag() {
		return zhengShuFlag;
	}

	public void setZhengShuFlag(String zhengShuFlag) {
		this.zhengShuFlag = zhengShuFlag;
	}

	public String getElectronicChapterFlag() {
		return electronicChapterFlag;
	}

	public void setElectronicChapterFlag(String electronicChapterFlag) {
		this.electronicChapterFlag = electronicChapterFlag;
	}
}