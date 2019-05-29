package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 *导出变更专业不符合的咨询师人员信息
 * @author
 * @version 2019-04-03
 */
public class ExportCounselorCountSpecialtyNo {

	private static final long serialVersionUID = 1L;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String index1;
	@ExcelField(title="序号", align=2, sort=3)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}

	private String areaName; // 归属地名称
	@ExcelField(title="地区", align=2, sort=2)
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String enterpriseName; // 咨询师姓名
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=5)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	private String sex; // 咨询师性别
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	@ExcelField(title="性别", align=2, sort=7)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	private String registerCertificateNum;		// 登记证书编号
	@Length(min=0, max=255, message="登记证书编号长度必须介于 0 和 255 之间")
	@ExcelField(title="登记证书编号", align=2, sort=8)
	public String getRegisterCertificateNum() {
		return registerCertificateNum;
	}
	public void setRegisterCertificateNum(String registerCertificateNum) {
		this.registerCertificateNum = registerCertificateNum;
	}

	private String yuanzhu; // 原主专业
	@Length(min=0, max=50, message="主专业长度必须介于 0 和 50 之间")
	@ExcelField(title="原登记主专业", align=2, sort=11)
	public String getYuanzhu() {
		return yuanzhu;
	}
	public void setYuanzhu(String yuanzhu) {
		this.yuanzhu = yuanzhu;
	}

	private String yuanfu; // 原辅专业
	@Length(min=0, max=50, message="登记辅助专业长度必须介于 0 和 50 之间")
	@ExcelField(title="原登记辅专业", align=2, sort=12)
	public String getYuanfu() {
		return yuanfu;
	}
	public void setYuanfu(String yuanfu) {
		this.yuanfu = yuanfu;
	}

	private String xinzhu; // 现申请主专业
	@Length(min=0, max=50, message="主专业长度必须介于 0 和 50 之间")
	@ExcelField(title="现申请主专业", align=2, sort=14)
	public String getXinzhu() {
		return xinzhu;
	}
	public void setXinzhu(String xinzhu) {
		this.xinzhu = xinzhu;
	}

	private String xinfu; // 现申请辅专业
	@Length(min=0, max=50, message="登记辅助专业长度必须介于 0 和 50 之间")
	@ExcelField(title="现申请辅专业", align=2, sort=17)
	public String getXinfu() {
		return xinfu;
	}
	public void setXinfu(String xinfu) {
		this.xinfu = xinfu;
	}

	private String companyName; // 执业单位
	@ExcelField(title="登记执业单位", align=2, sort=18)
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public ExportCounselorCountSpecialtyNo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportCounselorCountSpecialtyNo(ReportCounselorCount specialtyn) {
		this.areaName = specialtyn.getAreaName();
		this.index1 = specialtyn.getIndex1();
		this.enterpriseName = specialtyn.getEnterpriseName();
		this.sex = specialtyn.getSex();
		this.registerCertificateNum = specialtyn.getRegisterCertificateNum();
		this.yuanzhu = specialtyn.getYuanzhu();
		this.yuanfu = specialtyn.getYuanfu();
		this.xinzhu = specialtyn.getXinzhu();
		this.xinfu = specialtyn.getXinfu();
		this.companyName = specialtyn.getCompanyName();

	}


}