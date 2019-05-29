/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分支机构Entity
 * @author xqg
 * @version 2018-05-06
 */
public class EnterpriseBranchOrg extends DataEntity<EnterpriseBranchOrg> {
	
	private static final long serialVersionUID = 1L;
	private String branchOrgName;		// 分支机构名称
	private Office area;		// 所在地区
	private String pid;		// 所属用户ID
	private Integer workersCount;		// 职工人数
	
	public EnterpriseBranchOrg() {
		super();
	}

	public EnterpriseBranchOrg(String id){
		super(id);
	}

	@Length(min=0, max=200, message="分支机构名称长度必须介于 0 和 200 之间")
	public String getBranchOrgName() {
		return branchOrgName;
	}

	public void setBranchOrgName(String branchOrgName) {
		this.branchOrgName = branchOrgName;
	}
	
	public Office getArea() {
		return area;
	}

	public void setArea(Office area) {
		this.area = area;
	}
	
	@Length(min=0, max=32, message="所属用户ID长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public Integer getWorkersCount() {
		return workersCount;
	}

	public void setWorkersCount(Integer workersCount) {
		this.workersCount = workersCount;
	}
	
}