/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.ProjectInvestmentDao;
import com.thinkgem.jeesite.modules.counselor.entity.ProjectInvestment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hzy
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ProjectInvestmentService extends CrudService<ProjectInvestmentDao, ProjectInvestment> {


	@Autowired
	private ProjectInvestmentDao projectInvestmentDao;

	public ProjectInvestment get(String id) {
		ProjectInvestment projectInvestment = super.get(id);
		return projectInvestment;
	}

	public List<ProjectInvestment> findList(ProjectInvestment projectInvestment) {
		return super.findList(projectInvestment);
	}

	public Page<ProjectInvestment> findPage(Page<ProjectInvestment> page, ProjectInvestment projectInvestment) {
		return super.findPage(page, projectInvestment);
	}

	@Transactional(readOnly = false)
	public void save(ProjectInvestment projectInvestment) {
	super.save(projectInvestment);
	}

	@Transactional(readOnly = false)
	public void delete(ProjectInvestment projectInvestment) {
			super.delete(projectInvestment);
	}

}