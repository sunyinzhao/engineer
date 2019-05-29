/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.associationinfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 协会基本信息DAO接口
 * @author xqg
 * @version 2018-11-19
 */
@MyBatisDao
public interface AssociationInfoDao extends CrudDao<AssociationInfo> {
	
	/**
	 * 更新协会电子章图片
	 * @param associationInfo
	 */
	public void updateSealUrl(AssociationInfo associationInfo);
	
	/**
	 * 根据电子章序列号查询出协会基本信息
	 * @param serialNumber
	 * @return
	 */
	public List< AssociationInfo> getAssocicationBySerialNumber(@Param(value = "serialNumber") String serialNumber);
	
	/**
	 * 根据组织机构代码查询出协会基本信息
	 * @param officeId
	 * @return
	 */
	public  List< AssociationInfo>  getAssocicationByOfficeId(@Param(value = "officeId") String officeId);
	
}