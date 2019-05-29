/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;


import java.util.Date;

/**
 * 企业中所有的人员Entity
 * @author xqg
 * @version 2018-05-05
 */
public class EnterpriseWorkersExport2 {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 性别
	private String isValid;//0：登记且无效，1：登记且有效 2:未申请登记
	private String type;		// 人员类型
	private Date createDate;	// 创建日期
	private String batchStatus; //登记事项批次状态
	private String declareStatus; //登记事项状态
	
	public EnterpriseWorkersExport2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EnterpriseWorkersExport2(EnterpriseWorkers worker) {
		this.name = worker.getName();
		this.sex = DictUtils.getDictLabel(worker.getSex(), "sex", "");
		this.isValid =DictUtils.getDictLabel(worker.getIsValid(), "isValid", "");
		this.type =DictUtils.getDictLabel(worker.getType(), "counselor_type", "");
		this.createDate = worker.getCreateDate();
		this.batchStatus =DictUtils.getDictLabel(worker.getBatchStatus(), "counselor_status", "");
		this.declareStatus =DictUtils.getDictLabel(worker.getDeclareStatus(), "counselor_status", "");
	}
	
	
	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title="性别", align=2, sort=2)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@ExcelField(title="咨询工程师（投资）状态", align=2, sort=3)
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	@ExcelField(title="登记类型", align=2, sort=4)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ExcelField(title="登记日期", align=2, sort=5)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@ExcelField(title="登记事项批次状态", align=2, sort=6)
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	@ExcelField(title="登记事项状态", align=2, sort=7)
	public String getDeclareStatus() {
		return declareStatus;
	}
	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}
	
}