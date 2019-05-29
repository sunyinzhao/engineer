/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.associationinfo.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.associationinfo.dao.AssociationInfoDao;
import com.thinkgem.jeesite.modules.associationinfo.entity.AssociationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 协会基本信息Service
 * @author xqg
 * @version 2018-11-19
 */
@Service
@Transactional(readOnly = true)
public class AssociationInfoService extends CrudService<AssociationInfoDao, AssociationInfo> {
	@Autowired
	private AssociationInfoDao associationInfoDao;
	
	
	public AssociationInfo get(String id) {
		return super.get(id);
	}
	
	public List<AssociationInfo> findList(AssociationInfo associationInfo) {
		return super.findList(associationInfo);
	}
	
	public Page<AssociationInfo> findPage(Page<AssociationInfo> page, AssociationInfo associationInfo) {
		return super.findPage(page, associationInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AssociationInfo associationInfo) {
		super.save(associationInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AssociationInfo associationInfo) {
		super.delete(associationInfo);
	}
	
	@Transactional(readOnly = false)
	public void updateSealUrl(AssociationInfo associationInfo) {
		associationInfoDao.updateSealUrl(associationInfo);
	}
	
	/**
	 * 根据电子章序列号查询出协会基本信息
	 * @param serialNumber
	 * @return
	 */
	public  AssociationInfo getAssocicationBySerialNumber(String serialNumber){
		List <AssociationInfo>list =associationInfoDao.getAssocicationBySerialNumber(serialNumber);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据组织机构代码查询出协会基本信息
	 * @param officeId
	 * @return
	 */
	public  AssociationInfo getAssocicationByOfficeId(String officeId){
		List <AssociationInfo>list =associationInfoDao.getAssocicationByOfficeId(officeId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
}