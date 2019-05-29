/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseAttachmentDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 附件Service
 * @author xqg
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseAttachmentService extends CrudService<EnterpriseAttachmentDao, EnterpriseAttachment> {
	@Autowired
	private EnterpriseAttachmentDao enterpriseAttachmentDao;

	
	public EnterpriseAttachment get(String id) {
		return super.get(id);
	}
	
	public List<EnterpriseAttachment> findList(EnterpriseAttachment enterpriseAttachment) {
		return super.findList(enterpriseAttachment);
	}
	
		
	public List<EnterpriseAttachment> findZhengShuList(EnterpriseAttachment enterpriseAttachment) {
		return enterpriseAttachmentDao.findZhengShuList(enterpriseAttachment);
	}
	
	
	public Page<EnterpriseAttachment> findPage(Page<EnterpriseAttachment> page, EnterpriseAttachment enterpriseAttachment) {
		return super.findPage(page, enterpriseAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseAttachment enterpriseAttachment) {
		super.save(enterpriseAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseAttachment enterpriseAttachment) {
		super.delete(enterpriseAttachment);
	}

	/*
	* 根据type与pid查询图片集合
	* */
    public List<EnterpriseAttachment> findJpgList(String declareRecordId, String type) {
		return enterpriseAttachmentDao.findJpgList(declareRecordId,type);
    }

    //按照type进行排序
	public List<EnterpriseAttachment> findListBytype(EnterpriseAttachment enterpriseAttachment) {
		return enterpriseAttachmentDao.findListBytype(enterpriseAttachment);
	}

	//查询出的是组合的type
	public List<EnterpriseAttachment> findMixJpg(String declareRecordId) {
		return enterpriseAttachmentDao.findMixJpg(declareRecordId);
	}

	public List<EnterpriseAttachment> findPPPJpg(String declareRecordId) {

    	return enterpriseAttachmentDao.findPPPJpg(declareRecordId);
	}

	public List<EnterpriseAttachment> findType1(String id) {
		return enterpriseAttachmentDao.findType1(id);
	}
	public List<EnterpriseAttachment> findType2(String id) {
		return enterpriseAttachmentDao.findType2(id);
	}
	public List<EnterpriseAttachment> findType3(String id) {
		return enterpriseAttachmentDao.findType3(id);
	}
	public List<EnterpriseAttachment> findType4(String id) {
		return enterpriseAttachmentDao.findType4(id);
	}


	public List<EnterpriseAttachment> findType5(String id) {
		return enterpriseAttachmentDao.findType5(id);
	}



}