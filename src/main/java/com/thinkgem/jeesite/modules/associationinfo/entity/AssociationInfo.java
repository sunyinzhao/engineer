/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.associationinfo.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 协会基本信息Entity
 * @author xqg
 * @version 2018-11-19
 */
public class AssociationInfo extends DataEntity<AssociationInfo> {
	
	private static final long serialVersionUID = 1L;
	private String associationName;		// 协会名称
	private String orgNum;		// 组织机构代码
	private Office office;		// 所属地区
	private String sealPicUrl;		// 签章图片位置
	private String sealPicName;
	private String jianZhiDanWei ; //监制单位
	private Date startDate;
	private Date endDate;


	
	public AssociationInfo() {
		super();
	}

	public AssociationInfo(String id){
		super(id);
	}

	@Length(min=0, max=20, message="协会名称长度必须介于 0 和 20 之间")
	public String getAssociationName() {
		return associationName;
	}

	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}
	
	@Length(min=0, max=30, message="组织机构代码长度必须介于 0 和 30 之间")
	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=200, message="签章图片位置长度必须介于 0 和 200 之间")
	public String getSealPicUrl() {
		return sealPicUrl;
	}

	public void setSealPicUrl(String sealPicUrl) {
		this.sealPicUrl = sealPicUrl;
	}

	public String getSealPicName() {
		return sealPicName;
	}

	public void setSealPicName(String sealPicName) {
		this.sealPicName = sealPicName;
	}

	public String getJianZhiDanWei() {
		return jianZhiDanWei;
	}

	public void setJianZhiDanWei(String jianZhiDanWei) {
		this.jianZhiDanWei = jianZhiDanWei;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}