/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBranchOrg;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseBranchOrgDao;

/**
 * 分支机构Service
 * @author xqg
 * @version 2018-05-06
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseBranchOrgService extends CrudService<EnterpriseBranchOrgDao, EnterpriseBranchOrg> {

	public EnterpriseBranchOrg get(String id) {
		return super.get(id);
	}
	
	public List<EnterpriseBranchOrg> findList(EnterpriseBranchOrg enterpriseBranchOrg) {
		return super.findList(enterpriseBranchOrg);
	}
	
	public Page<EnterpriseBranchOrg> findPage(Page<EnterpriseBranchOrg> page, EnterpriseBranchOrg enterpriseBranchOrg) {
		return super.findPage(page, enterpriseBranchOrg);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseBranchOrg enterpriseBranchOrg) {
		super.save(enterpriseBranchOrg);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseBranchOrg enterpriseBranchOrg) {
		super.delete(enterpriseBranchOrg);
	}
	
}