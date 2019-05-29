/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseProject;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseProjectDao;

/**
 * 企业完成项目Service
 * @author xqg
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseProjectService extends CrudService<EnterpriseProjectDao, EnterpriseProject> {

	public EnterpriseProject get(String id) {
		return super.get(id);
	}
	
	public List<EnterpriseProject> findList(EnterpriseProject enterpriseProject) {
		return super.findList(enterpriseProject);
	}
	
	public Page<EnterpriseProject> findPage(Page<EnterpriseProject> page, EnterpriseProject enterpriseProject) {
		return super.findPage(page, enterpriseProject);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseProject enterpriseProject) {
		super.save(enterpriseProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseProject enterpriseProject) {
		super.delete(enterpriseProject);
	}
	
}