/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.JobKnowledgeDao;
import com.thinkgem.jeesite.modules.counselor.entity.JobKnowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业培训
 * @author hzy
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class JobKnowledgeService extends CrudService<JobKnowledgeDao,JobKnowledge> {


	@Autowired
	private JobKnowledgeDao jobKnowledgeDao;

	public JobKnowledge get(String id) {
		JobKnowledge jobKnowledge = super.get(id);
		return jobKnowledge;
	}

	public List<JobKnowledge> findList(JobKnowledge jobKnowledge) {
		return super.findList(jobKnowledge);
	}

	public Page<JobKnowledge> findPage(Page<JobKnowledge> page, JobKnowledge jobKnowledge) {
		return super.findPage(page, jobKnowledge);
	}

	@Transactional(readOnly = false)
	public void save(JobKnowledge jobKnowledge) {
		super.save(jobKnowledge);
	}

	@Transactional(readOnly = false)
	public void save(List<JobKnowledge> jobKnowledgeList,String tableId,String personId) {
		if(jobKnowledgeList==null||jobKnowledgeList.size()==0){
			return;
		}
		for(JobKnowledge jobKnowledge:jobKnowledgeList){
			jobKnowledge.setTableId(tableId);
			jobKnowledge.setPersonId(personId);
			if(jobKnowledge.getDelFlag().equals("1")){
				super.delete(jobKnowledge);
			}else if(jobKnowledge.getStartDate()!=null){
				super.save(jobKnowledge);
			}
		}
	}

	@Transactional(readOnly = false)
	public void selfSave(List<JobKnowledge> jobKnowledgeList,String personId) {
		if(jobKnowledgeList==null||jobKnowledgeList.size()==0){
			return;
		}
		for(JobKnowledge jobKnowledge:jobKnowledgeList){
			jobKnowledge.setPersonId(personId);
			if(jobKnowledge.getDelFlag().equals("1")){
				super.delete(jobKnowledge);
			}else if(jobKnowledge.getStartDate()!=null){
				super.save(jobKnowledge);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(JobKnowledge jobKnowledge) {
		super.delete(jobKnowledge);
	}


	public List<JobKnowledge> findByTableId(String tableId) {
		return jobKnowledgeDao.findByTableId(tableId);
	}

	public JobKnowledge findLast(String personId) {
		return jobKnowledgeDao.findLast(personId);
	}

	public JobKnowledge findFirst(String personId) {
		return jobKnowledgeDao.findFirst(personId);
	}
}