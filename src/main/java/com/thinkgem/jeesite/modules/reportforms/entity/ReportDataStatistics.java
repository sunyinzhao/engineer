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
 * Entity
 *
 */
public class ReportDataStatistics extends DataEntity<ReportDataStatistics> {
	private static final long serialVersionUID = 1L;

	public ReportDataStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportDataStatistics(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	private int index;//序号

	@ExcelField(title="序号", align=2, sort=4)
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	private String index1;
	@ExcelField(title="序号", align=2, sort=4)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}


	private Date declareDate; // 登记日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getDeclareDate() {
		return declareDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	private String specialty;		// 所学专业
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private String registerCertificateNum;		// 登记证书编号

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

    private String enterpriseName; // 咨询师姓名
    private String batchStatus;
    private String officeId;//sys_user表中的office_id
    private String officeName;//所在地
    private String batchNo;//批次号
    private String declareType;//变更类型   |   申请类型
    private String title; // 职称
    private String titleSpecialty; // 职称专业
    private String suoxueSpecialty; // 所学专业
    private String peixunSpecialty; // 培训专业
    private String companyName; //执业单位
    private String areaName; // 归属地名称 | 预审单位
	private String recordType; // 登记类型

	private String zexpert1Name; // 专家1姓名
	private String fzResult; // 预审主专家给的结果
	private String zexpert2Name; // 专家2姓名
	private String szResult; // 预审辅专家给的结果
	private String zdResult; // 专家评审结论
	private String zhuanjiayijian; // 专家意见
	private String fexpertName; // 终审专家姓名
	private String zResult; // 最终结果
	private String fResult; // 结果

	private String finalOpinion; // 专家意见
	private String baseOpinion; // 基本原因
	private String mainOpinion; // 主专业意见
	private String auxiliaryOpinion; // 辅专业意见

	private String feedBack; // 反馈意见
	private String finalResult; // 复核结论
	private String advice; // 复核意见
	private String fy_advice; // 最终建议
	private String originalMajor; // 原主
	private String newMainProfessional; // 新主
	private String originalAuxiliaryProfession; // 原辅
	private String newAuxiliaryProfession; // 新辅
	private String yuanDeclareWork; // 原来执业单位
	private String xinDeclareWork; // 新的执业单位

	public String getYuanDeclareWork() {
		return yuanDeclareWork;
	}

	public void setYuanDeclareWork(String yuanDeclareWork) {
		this.yuanDeclareWork = yuanDeclareWork;
	}

	public String getXinDeclareWork() {
		return xinDeclareWork;
	}

	public void setXinDeclareWork(String xinDeclareWork) {
		this.xinDeclareWork = xinDeclareWork;
	}

	public String getOriginalMajor() {
		return originalMajor;
	}

	public void setOriginalMajor(String originalMajor) {
		this.originalMajor = originalMajor;
	}

	public String getNewMainProfessional() {
		return newMainProfessional;
	}

	public void setNewMainProfessional(String newMainProfessional) {
		this.newMainProfessional = newMainProfessional;
	}

	public String getOriginalAuxiliaryProfession() {
		return originalAuxiliaryProfession;
	}

	public void setOriginalAuxiliaryProfession(String originalAuxiliaryProfession) {
		this.originalAuxiliaryProfession = originalAuxiliaryProfession;
	}

	public String getNewAuxiliaryProfession() {
		return newAuxiliaryProfession;
	}

	public void setNewAuxiliaryProfession(String newAuxiliaryProfession) {
		this.newAuxiliaryProfession = newAuxiliaryProfession;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleSpecialty() {
		return titleSpecialty;
	}

	public void setTitleSpecialty(String titleSpecialty) {
		this.titleSpecialty = titleSpecialty;
	}

	public String getSuoxueSpecialty() {
		return suoxueSpecialty;
	}

	public void setSuoxueSpecialty(String suoxueSpecialty) {
		this.suoxueSpecialty = suoxueSpecialty;
	}

	public String getPeixunSpecialty() {
		return peixunSpecialty;
	}

	public void setPeixunSpecialty(String peixunSpecialty) {
		this.peixunSpecialty = peixunSpecialty;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getZexpert1Name() {
		return zexpert1Name;
	}

	public void setZexpert1Name(String zexpert1Name) {
		this.zexpert1Name = zexpert1Name;
	}

	public String getFzResult() {
		return fzResult;
	}

	public void setFzResult(String fzResult) {
		this.fzResult = fzResult;
	}

	public String getZexpert2Name() {
		return zexpert2Name;
	}

	public void setZexpert2Name(String zexpert2Name) {
		this.zexpert2Name = zexpert2Name;
	}

	public String getSzResult() {
		return szResult;
	}

	public void setSzResult(String szResult) {
		this.szResult = szResult;
	}

	public String getZdResult() {
		return zdResult;
	}

	public void setZdResult(String zdResult) {
		this.zdResult = zdResult;
	}

	public String getZhuanjiayijian() {
		return zhuanjiayijian;
	}

	public void setZhuanjiayijian(String zhuanjiayijian) {
		this.zhuanjiayijian = zhuanjiayijian;
	}

	public String getFexpertName() {
		return fexpertName;
	}

	public void setFexpertName(String fexpertName) {
		this.fexpertName = fexpertName;
	}

	public String getzResult() {
		return zResult;
	}

	public void setzResult(String zResult) {
		this.zResult = zResult;
	}

	public String getfResult() {
		return fResult;
	}

	public void setfResult(String fResult) {
		this.fResult = fResult;
	}

    public String getFinalOpinion() {
        return finalOpinion;
    }

    public void setFinalOpinion(String finalOpinion) {
        this.finalOpinion = finalOpinion;
    }

    public String getBaseOpinion() {
		return baseOpinion;
	}

	public void setBaseOpinion(String baseOpinion) {
		this.baseOpinion = baseOpinion;
	}

	public String getMainOpinion() {
		return mainOpinion;
	}

	public void setMainOpinion(String mainOpinion) {
		this.mainOpinion = mainOpinion;
	}

	public String getAuxiliaryOpinion() {
		return auxiliaryOpinion;
	}

	public void setAuxiliaryOpinion(String auxiliaryOpinion) {
		this.auxiliaryOpinion = auxiliaryOpinion;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getFy_advice() {
		return fy_advice;
	}

	public void setFy_advice(String fy_advice) {
		this.fy_advice = fy_advice;
	}
}