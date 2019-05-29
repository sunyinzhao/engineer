/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 基本数据Entity
 * @author xqg
 * @version 2018-05-05
 */
public class EnterpriseBase extends DataEntity<EnterpriseBase> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 所属企业
	private Office office;   // 所属机构
	private String companyName;		// 公司名称
	private String companyIntroduction;		// 公司简介
	private String organizationCode;		// 统一社会信用代码
	private String applicationCode;		// 备案编号
	private String comanpyType;		// 公司性质
	private Date establishmentDate;		// 成立时间
	private String masterComanpy;		// 主管单位
	private Integer years;		// 从事咨询业务年限
	private Integer officeAreaAll;		// 办公面积共有
	private Integer officeAreaSelf;		// 办公面积自有
	private String oldDeclareGrade;		// 原有工程咨询单位资格证书级别
	private String oldDeclareNum;		// 原有工程咨询单位资格证书编号
	private String registerAddress;		// 注册地址
	private String url;		// 网址
	private String postalAddress;		// 通讯地址
	private String zipCode;		// 邮编
	private String manberMain;		// 中资会员
	private String manberLocal;		// 地方会员
	private String manberCode;		// 会员号
	private String legalPerson;		// 法人代码
	private String post;		// 职务
	private String phone;		// 电话
	private String mobile;		// 手机
	private String contactDept;		// 联系部门
	private String contactPhone;		// 联系电话
	
	private String contactsZx;    		//资信联系人
	private String contactsZxPhone; 
	private String contactsZy;    		//职业联系人
	private String contactsZyPhone;
	private String contactsPy;    		//评优联系人
	private String contactsPyPhone;    
	
	
	
	
	private String file;		// 附件
	
	public EnterpriseBase() {
		super();
	}

	public EnterpriseBase(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=200, message="公司名称长度必须介于 0 和 200 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=2000, message="公司简介长度必须介于 0 和 2000 之间")
	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
	}
	
	@Length(min=0, max=25, message="统一社会信用代码长度必须介于 0 和 25 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=30, message="备案编号长度必须介于 0 和 30 之间")
	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Length(min=0, max=5, message="公司性质长度必须介于 0 和 5 之间")
	public String getComanpyType() {
		return comanpyType;
	}

	public void setComanpyType(String comanpyType) {
		this.comanpyType = comanpyType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}
	
	@Length(min=0, max=32, message="主管单位长度必须介于 0 和 32 之间")
	public String getMasterComanpy() {
		return masterComanpy;
	}

	public void setMasterComanpy(String masterComanpy) {
		this.masterComanpy = masterComanpy;
	}
	
	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}
	
	public Integer getOfficeAreaAll() {
		return officeAreaAll;
	}

	public void setOfficeAreaAll(Integer officeAreaAll) {
		this.officeAreaAll = officeAreaAll;
	}
	
	public Integer getOfficeAreaSelf() {
		return officeAreaSelf;
	}

	public void setOfficeAreaSelf(Integer officeAreaSelf) {
		this.officeAreaSelf = officeAreaSelf;
	}
	
	@Length(min=0, max=1, message="原有工程咨询单位资格证书级别长度必须介于 0 和 1 之间")
	public String getOldDeclareGrade() {
		return oldDeclareGrade;
	}

	public void setOldDeclareGrade(String oldDeclareGrade) {
		this.oldDeclareGrade = oldDeclareGrade;
	}
	
	@Length(min=0, max=20, message="原有工程咨询单位资格证书编号长度必须介于 0 和 20 之间")
	public String getOldDeclareNum() {
		return oldDeclareNum;
	}

	public void setOldDeclareNum(String oldDeclareNum) {
		this.oldDeclareNum = oldDeclareNum;
	}
	
	@Length(min=0, max=300, message="注册地址长度必须介于 0 和 300 之间")
	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	
	@Length(min=0, max=100, message="网址长度必须介于 0 和 100 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=300, message="通讯地址长度必须介于 0 和 300 之间")
	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	
	@Length(min=0, max=10, message="邮编长度必须介于 0 和 10 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Length(min=0, max=1, message="中资会员长度必须介于 0 和 1 之间")
	public String getManberMain() {
		return manberMain;
	}

	public void setManberMain(String manberMain) {
		this.manberMain = manberMain;
	}
	
	@Length(min=0, max=1, message="地方会员长度必须介于 0 和 1 之间")
	public String getManberLocal() {
		return manberLocal;
	}

	public void setManberLocal(String manberLocal) {
		this.manberLocal = manberLocal;
	}
	
	@Length(min=0, max=20, message="会员号长度必须介于 0 和 20 之间")
	public String getManberCode() {
		return manberCode;
	}

	public void setManberCode(String manberCode) {
		this.manberCode = manberCode;
	}
	
	@Length(min=0, max=15, message="法人代码长度必须介于 0 和 15 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@Length(min=0, max=255, message="职务长度必须介于 0 和 255 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=15, message="电话长度必须介于 0 和 15 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=15, message="手机长度必须介于 0 和 15 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=30, message="联系部门长度必须介于 0 和 30 之间")
	public String getContactDept() {
		return contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}
	
	@Length(min=0, max=15, message="联系电话长度必须介于 0 和 15 之间")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getContactsZx() {
		return contactsZx;
	}

	public void setContactsZx(String contactsZx) {
		this.contactsZx = contactsZx;
	}

	public String getContactsZxPhone() {
		return contactsZxPhone;
	}

	public void setContactsZxPhone(String contactsZxPhone) {
		this.contactsZxPhone = contactsZxPhone;
	}

	public String getContactsZy() {
		return contactsZy;
	}

	public void setContactsZy(String contactsZy) {
		this.contactsZy = contactsZy;
	}

	public String getContactsZyPhone() {
		return contactsZyPhone;
	}

	public void setContactsZyPhone(String contactsZyPhone) {
		this.contactsZyPhone = contactsZyPhone;
	}

	public String getContactsPy() {
		return contactsPy;
	}

	public void setContactsPy(String contactsPy) {
		this.contactsPy = contactsPy;
	}

	public String getContactsPyPhone() {
		return contactsPyPhone;
	}

	public void setContactsPyPhone(String contactsPyPhone) {
		this.contactsPyPhone = contactsPyPhone;
	}


	
}