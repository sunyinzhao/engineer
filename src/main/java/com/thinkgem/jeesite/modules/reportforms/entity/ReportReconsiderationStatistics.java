
package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询师 复议统计 所需Entity
 *
 */
public class ReportReconsiderationStatistics extends DataEntity<ReportReconsiderationStatistics> implements Serializable {
	public ReportReconsiderationStatistics() {
	}

	public ReportReconsiderationStatistics(String id) {
		super(id);
	}

	private static final long serialVersionUID = 1L;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String index;//序号
	private String batchNo;//批次号
	private String officeId;//sys_user表中的office_id

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	private String enterpriseName; // 咨询师姓名
	private String sex;		// 性别
	private String companyName; //执业 单位名称
	private String areaName; // 归属地名称；所属协会
	private String declareType;//登记类型
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private Date declareDate; // 登记日期；上报日期
	private String finalZhuName; // 终审主专家
	private String fzResult; // 终审主结论
	private String finalFuName; // 终审辅专家
	private String szResult; // 终审辅结论
	private String zResult; // 终审结论
	private String finalOpinion; // 终审意见
	private String feedBack; // 反馈意见
	private String fexpertName; // 复议专家
	private String finalResult; // 复议结论
	private String advice; // 复议意见
	private String registerCertificateNum; // 证书编号
	private String originalMajor; // 原主专业
	private String newMainProfessional; // 新主专业
	private String originalAuxiliaryProfession; // 原辅专业
	private String newAuxiliaryProfession; // 新辅专业


    public String getzResult() {
        return zResult;
    }

    public void setzResult(String zResult) {
        this.zResult = zResult;
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

    public String getRegisterCertificateNum() {
        return registerCertificateNum;
    }

    public void setRegisterCertificateNum(String registerCertificateNum) {
        this.registerCertificateNum = registerCertificateNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getDeclareDate() {
		return declareDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}

	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}

	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}

	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}


	public String getFzResult() {
		return fzResult;
	}

	public void setFzResult(String fzResult) {
		this.fzResult = fzResult;
	}


	public String getSzResult() {
		return szResult;
	}

	public void setSzResult(String szResult) {
		this.szResult = szResult;
	}

	public String getFinalOpinion() {
		return finalOpinion;
	}

	public void setFinalOpinion(String finalOpinion) {
		this.finalOpinion = finalOpinion;
	}

	public String getFinalZhuName() {
		return finalZhuName;
	}

	public void setFinalZhuName(String finalZhuName) {
		this.finalZhuName = finalZhuName;
	}

	public String getFinalFuName() {
		return finalFuName;
	}

	public void setFinalFuName(String finalFuName) {
		this.finalFuName = finalFuName;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getFexpertName() {
		return fexpertName;
	}

	public void setFexpertName(String fexpertName) {
		this.fexpertName = fexpertName;
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
}