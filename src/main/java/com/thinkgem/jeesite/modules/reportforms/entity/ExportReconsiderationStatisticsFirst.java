
package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出初始登记咨询师人员信息
 *
 */

public class ExportReconsiderationStatisticsFirst{
	private static final long serialVersionUID = 1L;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String index;//序号
	private String batchNo;//批次号
	private String officeId;//sys_user表中的office_id
	@ExcelField(title = "序号", align = 2)
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
	private String finalOpinion; // 终审意见
	private String feedBack; // 反馈意见
	private String fexpertName; // 复议专家
	private String finalResult; // 复议结论
	private String advice; // 复议意见

	@ExcelField(title="上报日期", align=2, sort=8)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getDeclareDate() {
		return declareDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	@ExcelField(title="性别", align=2, sort=2)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@ExcelField(title="单位名称", align=2, sort=3)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@ExcelField(title="所属协会", align=2, sort=4)
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@ExcelField(title="登记类型", align=2, sort=5)
	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	@ExcelField(title="主专业", align=2, sort=6)
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}

	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}
	@ExcelField(title="辅专业", align=2, sort=7)
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}

	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}

	@ExcelField(title="终审主结论", align=2, sort=10)
	public String getFzResult() {
		return fzResult;
	}

	public void setFzResult(String fzResult) {
		this.fzResult = fzResult;
	}

	@ExcelField(title="终审辅结论", align=2, sort=12)
	public String getSzResult() {
		return szResult;
	}

	public void setSzResult(String szResult) {
		this.szResult = szResult;
	}
	@ExcelField(title="终审意见", align=2, sort=13)
	public String getFinalOpinion() {
		return finalOpinion;
	}

	public void setFinalOpinion(String finalOpinion) {
		this.finalOpinion = finalOpinion;
	}
	@ExcelField(title="终审主专家", align=2, sort=9)
	public String getFinalZhuName() {
		return finalZhuName;
	}

	public void setFinalZhuName(String finalZhuName) {
		this.finalZhuName = finalZhuName;
	}
	@ExcelField(title="终审辅专家", align=2, sort=11)
	public String getFinalFuName() {
		return finalFuName;
	}

	public void setFinalFuName(String finalFuName) {
		this.finalFuName = finalFuName;
	}
	@ExcelField(title="反馈意见", align=2, sort=14)
	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	@ExcelField(title="复议专家", align=2, sort=15)
	public String getFexpertName() {
		return fexpertName;
	}

	public void setFexpertName(String fexpertName) {
		this.fexpertName = fexpertName;
	}
	@ExcelField(title="复议结论", align=2, sort=16)
	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}
	@ExcelField(title="复议意见", align=2, sort=17)
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}


	public ExportReconsiderationStatisticsFirst(ReportReconsiderationStatistics rrs) {
		this.index = rrs.getIndex();
		this.batchNo = rrs.getBatchNo();
		this.officeId = rrs.getOfficeId();
		this.enterpriseName = rrs.getEnterpriseName();
		this.sex = rrs.getSex();
		this.companyName = rrs.getCompanyName();
		this.areaName = rrs.getAreaName();
		this.declareType = rrs.getDeclareType();
		this.registerMainSpecialty = rrs.getRegisterMainSpecialty();
		this.registerAuxiliarySpecialty = rrs.getRegisterAuxiliarySpecialty();
		this.declareDate = rrs.getDeclareDate();
		this.finalZhuName = rrs.getFinalZhuName();
		this.fzResult = rrs.getFzResult();
		this.finalFuName = rrs.getFinalFuName();
		this.szResult = rrs.getSzResult();
		this.finalOpinion = rrs.getFinalOpinion();
		this.feedBack = rrs.getFeedBack();
		this.fexpertName = rrs.getFexpertName();
		this.finalResult = rrs.getFinalResult();
		this.advice = rrs.getAdvice();
	}
}