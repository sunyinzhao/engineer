/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 非咨变更Entity
 * @author xqg
 * @version 2018-05-28
 */
public class EnterpriseChangeInfo extends DataEntity<EnterpriseChangeInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 原企业名称
	private String nameNew;		// 企业名称
	private String legalPerson;		// 原法人
	private String legalPersonNew;		// 法人
	private String registerAddress;		// 原注册地址
	private String registerAddressNew;		// 注册地址
	private String applicationCode;		// 原备案号
	private String applicationCodeNew;		// 备案号
	private String pid;		// 申请单Id
	private String status; // 状态
	private List<EnterpriseAttachment> enterpriseAttachmentList = Lists.newArrayList();		// 子表列表
	
	public EnterpriseChangeInfo() {
		super();
	}

	public EnterpriseChangeInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="原企业名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="企业名称长度必须介于 0 和 100 之间")
	public String getNameNew() {
		return nameNew;
	}

	public void setNameNew(String nameNew) {
		this.nameNew = nameNew;
	}
	
	@Length(min=0, max=30, message="原法人长度必须介于 0 和 30 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@Length(min=0, max=30, message="法人长度必须介于 0 和 30 之间")
	public String getLegalPersonNew() {
		return legalPersonNew;
	}

	public void setLegalPersonNew(String legalPersonNew) {
		this.legalPersonNew = legalPersonNew;
	}
	
	@Length(min=0, max=100, message="原注册地址长度必须介于 0 和 100 之间")
	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	
	@Length(min=0, max=100, message="注册地址长度必须介于 0 和 100 之间")
	public String getRegisterAddressNew() {
		return registerAddressNew;
	}

	public void setRegisterAddressNew(String registerAddressNew) {
		this.registerAddressNew = registerAddressNew;
	}
	
	@Length(min=0, max=30, message="原备案号长度必须介于 0 和 30 之间")
	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Length(min=0, max=30, message="备案号长度必须介于 0 和 30 之间")
	public String getApplicationCodeNew() {
		return applicationCodeNew;
	}

	public void setApplicationCodeNew(String applicationCodeNew) {
		this.applicationCodeNew = applicationCodeNew;
	}
	
	@Length(min=0, max=32, message="申请单Id长度必须介于 0 和 32 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public List<EnterpriseAttachment> getEnterpriseAttachmentList() {
		return enterpriseAttachmentList;
	}

	public void setEnterpriseAttachmentList(List<EnterpriseAttachment> enterpriseAttachmentList) {
		this.enterpriseAttachmentList = enterpriseAttachmentList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}