package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 *导出执业单位咨询师人员信息
 * @author
 * @version 2019-04-03
 */
public class ExportCounselorCountApplyWork {

	private static final long serialVersionUID = 1L;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String index1;
	@ExcelField(title="序号", align=2, sort=2)
	public String getIndex1() {
		return index1;
	}
	public void setIndex1(String index1) {
		this.index1 = index1;
	}

	private String areaName; // 归属地名称
	@ExcelField(title="地区", align=2, sort=1)
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String enterpriseName; // 咨询师姓名
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	@ExcelField(title="姓名", align=2, sort=4)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	private String sex; // 咨询师性别
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	@ExcelField(title="性别", align=2, sort=6)
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

	private String oldWorkers;
	@ExcelField(title="原登记执业单位", align=2, sort=15)
	public String getOldWorkers() {
		return oldWorkers;
	}
	public void setOldWorkers(String oldWorkers) {
		this.oldWorkers = oldWorkers;
	}

	private String newWorkers;
	@ExcelField(title="现登记执业单位", align=2, sort=19)
	public String getNewWorkers() {
		return newWorkers;
	}
	public void setNewWorkers(String newWorkers) {
		this.newWorkers = newWorkers;
	}


	public ExportCounselorCountApplyWork() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExportCounselorCountApplyWork(ReportCounselorCount worker) {
		this.index1 = worker.getIndex1();
		this.areaName = worker.getAreaName();
		this.enterpriseName = worker.getEnterpriseName();
		this.sex = worker.getSex();
		this.registerCertificateNum = worker.getRegisterCertificateNum();
		this.oldWorkers = worker.getOldWorkers();
		this.newWorkers = worker.getNewWorkers();

	}


}