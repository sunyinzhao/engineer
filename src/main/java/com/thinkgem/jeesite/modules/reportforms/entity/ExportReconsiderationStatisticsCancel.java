
package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.util.Date;

/**
 * 导出注销登记咨询师人员信息
 *
 */

public class ExportReconsiderationStatisticsCancel {
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
	private String registerCertificateNum; // 证书编号
	private String companyName; //执业 单位名称
	private String areaName; // 归属地名称；所属协会
	private String declareType;//登记类型
	private String registerMainSpecialty;		// 主专业
	private String registerAuxiliarySpecialty;		// 辅专业
	private Date declareDate; // 登记日期；上报日期
	private String finalZhuName; // 审核专家
//	private String fzResult; // 终审主结论
	private String finalResult; // 审核结论

	@ExcelField(title="上报日期", align=2, sort=17)
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
	@ExcelField(title="执业登记证书编号", align=2, sort=3)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}

	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}

	@ExcelField(title="单位名称", align=2, sort=6)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@ExcelField(title="所属协会", align=2, sort=9)
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@ExcelField(title="登记类型", align=2, sort=12)
	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	@ExcelField(title="主专业", align=2, sort=15)
	public String getRegisterMainSpecialty() {
		return registerMainSpecialty;
	}

	public void setRegisterMainSpecialty(String registerMainSpecialty) {
		this.registerMainSpecialty = registerMainSpecialty;
	}
	@ExcelField(title="辅专业", align=2, sort=16)
	public String getRegisterAuxiliarySpecialty() {
		return registerAuxiliarySpecialty;
	}

	public void setRegisterAuxiliarySpecialty(String registerAuxiliarySpecialty) {
		this.registerAuxiliarySpecialty = registerAuxiliarySpecialty;
	}
	@ExcelField(title="审核专家", align=2, sort=18)
	public String getFinalZhuName() {
		return finalZhuName;
	}

	public void setFinalZhuName(String finalZhuName) {
		this.finalZhuName = finalZhuName;
	}

	@ExcelField(title="审核结论", align=2, sort=19)
	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}


	public ExportReconsiderationStatisticsCancel(ReportReconsiderationStatistics rrs) {
		this.index = rrs.getIndex();
		this.batchNo = rrs.getBatchNo();
		this.officeId = rrs.getOfficeId();
		this.enterpriseName = rrs.getEnterpriseName();
		this.sex = rrs.getSex();
		this.registerCertificateNum = rrs.getRegisterCertificateNum();
		this.companyName = rrs.getCompanyName();
		this.areaName = rrs.getAreaName();
		this.declareType = rrs.getDeclareType();
		this.registerMainSpecialty = DictUtils.getDictLabel(rrs.getRegisterMainSpecialty(),"specialty_type","");
		this.registerAuxiliarySpecialty = DictUtils.getDictLabel(rrs.getRegisterAuxiliarySpecialty(),"specialty_type","");
		this.declareDate = rrs.getDeclareDate();
		this.finalZhuName = rrs.getFinalZhuName();
		this.finalResult = rrs.getFinalResult();
	}
}