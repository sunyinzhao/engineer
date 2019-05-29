
package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 意见对比变更单位导出Entity
 *
 */
public class exportDataStatisticsWorkunit {
	private static final long serialVersionUID = 1L;

	private String index1;
	@ExcelField(title="序号", align=2, sort=1)
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

	private String declareType;//变更类型   |   申请类型
	@ExcelField(title="申请类型", align=2, sort=3)
	public String getDeclareType() {
		return declareType;
	}
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	private String enterpriseName; // 咨询师姓名
	@ExcelField(title="姓名", align=2, sort=4)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业

	@Length(min=0, max=50, message="主专业长度必须介于 0 和 50 之间")
	@ExcelField(title="登记主专业", align=2, sort=6)
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}
	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}

	@Length(min=0, max=50, message="登记辅助专业长度必须介于 0 和 50 之间")
	@ExcelField(title="登记辅专业", align=2, sort=7)
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}
	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}

	private String title; // 职称
	@ExcelField(title="职称", align=2, sort=8)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private String titleSpecialty; // 职称专业
	@ExcelField(title="职称专业", align=2, sort=10)
	public String getTitleSpecialty() {
		return titleSpecialty;
	}
	public void setTitleSpecialty(String titleSpecialty) {
		this.titleSpecialty = titleSpecialty;
	}

	private String suoxueSpecialty; // 所学专业
	@ExcelField(title="所学专业", align=2, sort=12)
	public String getSuoxueSpecialty() {
		return suoxueSpecialty;
	}
	public void setSuoxueSpecialty(String suoxueSpecialty) {
		this.suoxueSpecialty = suoxueSpecialty;
	}

	private String peixunSpecialty; // 培训专业
	@ExcelField(title="培训专业", align=2, sort=14)
	public String getPeixunSpecialty() {
		return peixunSpecialty;
	}
	public void setPeixunSpecialty(String peixunSpecialty) {
		this.peixunSpecialty = peixunSpecialty;
	}

	private String companyName; //执业单位
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private String yuanDeclareWork; // 原来执业单位
	private String xinDeclareWork; // 新的执业单位
	@ExcelField(title="原执业单位", align=2, sort=15)
	public String getYuanDeclareWork() {
		return yuanDeclareWork;
	}

	public void setYuanDeclareWork(String yuanDeclareWork) {
		this.yuanDeclareWork = yuanDeclareWork;
	}
	@ExcelField(title="新执业单位", align=2, sort=16)
	public String getXinDeclareWork() {
		return xinDeclareWork;
	}

	public void setXinDeclareWork(String xinDeclareWork) {
		this.xinDeclareWork = xinDeclareWork;
	}


	private String areaName; // 归属地名称 | 预审单位
	@ExcelField(title="预审单位", align=2, sort=18)
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String officeId;//sys_user表中的office_id
    private String batchNo;//批次号

	private String zexpert1Name; // 专家1姓名
	@ExcelField(title="专家1姓名", align=2, sort=20)
	public String getZexpert1Name() {
		return zexpert1Name;
	}

	public void setZexpert1Name(String zexpert1Name) {
		this.zexpert1Name = zexpert1Name;
	}

	private String fzResult; // 预审主专家给的结果
	@ExcelField(title="专家1结论", align=2, sort=21)
	public String getFzResult() {
		return fzResult;
	}

	public void setFzResult(String fzResult) {
		this.fzResult = fzResult;
	}

	private String zexpert2Name; // 专家2姓名
	public String getZexpert2Name() {
		return zexpert2Name;
	}

	public void setZexpert2Name(String zexpert2Name) {
		this.zexpert2Name = zexpert2Name;
	}

	private String szResult; // 预审辅专家给的结果
	public String getSzResult() {
		return szResult;
	}

	public void setSzResult(String szResult) {
		this.szResult = szResult;
	}

	private String zdResult; // 专家评审结论
	@ExcelField(title="专家评审结论", align=2, sort=25)
	public String getZdResult() {
		return zdResult;
	}

	public void setZdResult(String zdResult) {
		this.zdResult = zdResult;
	}

	private String finalOpinion; // 专家评审意见
	@ExcelField(title="专家评审意见", align=2, sort=26)
	public String getFinalOpinion() {
		return finalOpinion;
	}

	public void setFinalOpinion(String finalOpinion) {
		this.finalOpinion = finalOpinion;
	}

	private String baseOpinion; // 基本原因
	public String getBaseOpinion() {
		return baseOpinion;
	}

	public void setBaseOpinion(String baseOpinion) {
		this.baseOpinion = baseOpinion;
	}

	private String mainOpinion; // 主专业意见
	public String getMainOpinion() {
		return mainOpinion;
	}

	public void setMainOpinion(String mainOpinion) {
		this.mainOpinion = mainOpinion;
	}

	private String auxiliaryOpinion; // 辅专业意见
	public String getAuxiliaryOpinion() {
		return auxiliaryOpinion;
	}

	public void setAuxiliaryOpinion(String auxiliaryOpinion) {
		this.auxiliaryOpinion = auxiliaryOpinion;
	}

	private String feedBack; // 反馈意见
	@ExcelField(title="反馈意见", align=2, sort=30)
	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	private String finalResult; // 复核结论
	@ExcelField(title="复核结论", align=2, sort=31)
	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	private String advice; // 复核意见
	@ExcelField(title="复核意见", align=2, sort=32)
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public exportDataStatisticsWorkunit() {
	}

	public exportDataStatisticsWorkunit(ReportDataStatistics rds) {
		this.index1 = rds.getIndex1();
		this.declareDate = rds.getDeclareDate();
		this.declareType = rds.getDeclareType();
		this.enterpriseName = rds.getEnterpriseName();
		this.registerMainSpecialty = DictUtils.getDictLabel(rds.getRegisterMainSpecialty(),"specialty_type","");
		this.registerAuxiliarySpecialty = DictUtils.getDictLabel(rds.getRegisterAuxiliarySpecialty(),"specialty_type","");
		this.title = DictUtils.getDictLabel(rds.getTitle(),"worker_title","");
		this.titleSpecialty = rds.getTitleSpecialty();
		this.suoxueSpecialty = rds.getSuoxueSpecialty();
		this.peixunSpecialty = rds.getPeixunSpecialty();
		this.companyName = rds.getCompanyName();
		this.yuanDeclareWork = rds.getYuanDeclareWork();
		this.xinDeclareWork = rds.getXinDeclareWork();
		this.areaName = rds.getAreaName();
		this.officeId = rds.getOfficeId();
		this.batchNo = rds.getBatchNo();
		this.zexpert1Name = rds.getZexpert1Name();
		this.fzResult = rds.getFzResult();
		this.zdResult = rds.getZdResult();
		this.finalOpinion = rds.getFinalOpinion();
		this.feedBack = rds.getFeedBack();
		this.finalResult = rds.getFinalResult();
		this.advice = rds.getAdvice();
	}
}