package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 *导出初始登记咨询师人员信息
 * @author
 * @version 2019-04-03
 */
public class ExportCounselorCountFirst {

	private static final long serialVersionUID = 1L;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String index1;
	@ExcelField(title="序号", align=2, sort=5)
	public String getIndex1() {
		return index1;
	}
	public void setIndex(String index1) {
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

	private String zhuzhuanye; // 主专业
	@Length(min=0, max=50, message="主专业长度必须介于 0 和 50 之间")
	@ExcelField(title="登记主专业", align=2, sort=11)
	public String getZhuzhuanye() {
		return zhuzhuanye;
	}
	public void setZhuzhuanye(String zhuzhuanye) {
		this.zhuzhuanye = zhuzhuanye;
	}

	private String fuzhuanye; // 辅专业
	@Length(min=0, max=50, message="登记辅助专业长度必须介于 0 和 50 之间")
	@ExcelField(title="登记辅专业", align=2, sort=17)
	public String getFuzhuanye() {
		return fuzhuanye;
	}
	public void setFuzhuanye(String fuzhuanye) {
		this.fuzhuanye = fuzhuanye;
	}

	private String companyName; // 执业单位
	@ExcelField(title="登记执业单位", align=2, sort=18)
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public ExportCounselorCountFirst() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportCounselorCountFirst(ReportCounselorCount worker) {
		this.index1 = worker.getIndex1();
		this.areaName = worker.getAreaName();
		this.enterpriseName = worker.getEnterpriseName();
		this.sex = worker.getSex();
		this.zhuzhuanye = worker.getZhuzhuanye();
		this.fuzhuanye = worker.getFuzhuanye();
		this.companyName = worker.getCompanyName();

	}


}