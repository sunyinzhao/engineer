/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseChangeInfo;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseChangeInfoDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseAttachmentDao;

/**
 * 非咨变更Service
 * @author xqg
 * @version 2018-05-28
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseChangeInfoService extends CrudService<EnterpriseChangeInfoDao, EnterpriseChangeInfo> {

	@Autowired
	private EnterpriseAttachmentDao enterpriseAttachmentDao;
	
	public EnterpriseChangeInfo get(String id) {
		EnterpriseChangeInfo enterpriseChangeInfo = super.get(id);
		EnterpriseAttachment attachment = new EnterpriseAttachment();
		attachment.setPid(enterpriseChangeInfo.getId());
		enterpriseChangeInfo.setEnterpriseAttachmentList(enterpriseAttachmentDao.findList(attachment));
		return enterpriseChangeInfo;
	}
	
	public List<EnterpriseChangeInfo> findList(EnterpriseChangeInfo enterpriseChangeInfo) {
		return super.findList(enterpriseChangeInfo);
	}
	
	public Page<EnterpriseChangeInfo> findPage(Page<EnterpriseChangeInfo> page, EnterpriseChangeInfo enterpriseChangeInfo) {
		return super.findPage(page, enterpriseChangeInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseChangeInfo enterpriseChangeInfo) {
		super.save(enterpriseChangeInfo);
		for (EnterpriseAttachment enterpriseAttachment : enterpriseChangeInfo.getEnterpriseAttachmentList()){
			if (enterpriseAttachment.getId() == null){
				continue;
			}
			if (EnterpriseAttachment.DEL_FLAG_NORMAL.equals(enterpriseAttachment.getDelFlag())){
				if (StringUtils.isBlank(enterpriseAttachment.getId())){
					enterpriseAttachment.setPid(enterpriseChangeInfo.getId());
					enterpriseAttachment.preInsert();
					enterpriseAttachmentDao.insert(enterpriseAttachment);
				}else{
					enterpriseAttachment.preUpdate();
					enterpriseAttachmentDao.update(enterpriseAttachment);
				}
			}else{
				enterpriseAttachmentDao.delete(enterpriseAttachment);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseChangeInfo enterpriseChangeInfo) {
		super.delete(enterpriseChangeInfo);
		EnterpriseAttachment attachment = new EnterpriseAttachment();
		attachment.setPid(enterpriseChangeInfo.getId());
		enterpriseAttachmentDao.delete(attachment);
	}
	
}