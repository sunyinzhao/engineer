/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseAttachmentDao;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseBaseDao;

/**
 * 基本数据Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseBaseService extends CrudService<EnterpriseBaseDao, EnterpriseBase> {
	@Autowired
	private EnterpriseBaseDao enterprisebasedao;
	
	public EnterpriseBase get(String id) {
		return super.get(id);
	}

	public EnterpriseBase get(EnterpriseBase base) {
		List <EnterpriseBase>list = this.findList(base);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public EnterpriseBase getbyUserId(String userid) {
		return enterprisebasedao.findEBase(userid);
	}
	
	public List<EnterpriseBase> findList(EnterpriseBase enterpriseBase) {
		return super.findList(enterpriseBase);
	}
	
	public Page<EnterpriseBase> findPage(Page<EnterpriseBase> page, EnterpriseBase enterpriseBase) {
		return super.findPage(page, enterpriseBase);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseBase enterpriseBase) {
		super.save(enterpriseBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseBase enterpriseBase) {
		super.delete(enterpriseBase);
	}

	public List<User> checkOrganizationCode(User user) {
		// TODO Auto-generated method stub
		List<User> orgCode=dao.checkOrganizationCode(user);
		return orgCode;
	}
	
}