package com.thinkgem.jeesite.modules.reportforms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *导出继续登记咨询师人员信息
 * @author
 * @version 2019-04-03
 */
public class ExportCounselorCountContinue {

	private static final long serialVersionUID = 1L;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	private String areaName; // 归属地名称
	@ExcelField(title="地区", align=2, sort=2)
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String index1;
	@ExcelField(title="序号", align=2, sort=4)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}

	private String enterpriseName; // 咨询师姓名
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=7)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	private String sex; // 咨询师性别
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	@ExcelField(title="性别", align=2, sort=9)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	private Date declareDate; // 登记日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ExcelField(title="登记日期", align=2, sort=11)
	public Date getDeclareDate() {
		return declareDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	private String registerCertificateNum;		// 登记证书编号
	@Length(min=0, max=255, message="登记证书编号长度必须介于 0 和 255 之间")
	@ExcelField(title="登记证书编号", align=2, sort=15)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}
	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}

	private String companyName; // 执业单位
	@ExcelField(title="登记执业单位", align=2, sort=18)
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public ExportCounselorCountContinue() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportCounselorCountContinue(ReportCounselorCount counselor) {
		this.areaName = counselor.getAreaName();
		this.index1 = counselor.getIndex1();
		this.enterpriseName = counselor.getEnterpriseName();
		this.sex = counselor.getSex();
		this.declareDate = counselor.getDeclareDate();
		this.registerCertificateNum = counselor.getRegisterCertificateNum();
		this.companyName = counselor.getCompanyName();

	}


}