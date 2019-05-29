/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.FeedbackDao;
import com.thinkgem.jeesite.modules.counselor.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Lazy(false)
public class FeedbackService extends CrudService<FeedbackDao, Feedback> {
	@Autowired
	private FeedbackDao feedbackDao;

	public Feedback get(String id) {
		Feedback feedback = super.get(id);
		return feedback;
	}

	public List<Feedback> findList(Feedback feedback) {
		return super.findList(feedback);
	}

	public Page<Feedback> findPage(Page<Feedback> page, Feedback feedback) {
		return super.findPage(page, feedback);
	}

	@Transactional(readOnly = false)
	public void save(Feedback feedback) {
	super.save(feedback);
	}

	@Transactional(readOnly = false)
	public void delete(Feedback feedback) {
			super.delete(feedback);
	}

}