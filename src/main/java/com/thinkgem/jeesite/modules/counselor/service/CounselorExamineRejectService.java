/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorExamineRejectDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorExamineReject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨询师不符合原因Service
 * @author hzy
 * @version 2018-08-23
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class CounselorExamineRejectService extends CrudService<CounselorExamineRejectDao, CounselorExamineReject> {


	@Autowired
	private CounselorExamineRejectDao counselorExamineRejectDao;

	public CounselorExamineReject get(String id) {
		CounselorExamineReject counselorExamineReject = super.get(id);
		return counselorExamineReject;
	}

	public List<CounselorExamineReject> findList(CounselorExamineReject counselorExamineReject) {
		return super.findList(counselorExamineReject);
	}

	public Page<CounselorExamineReject> findPage(Page<CounselorExamineReject> page, CounselorExamineReject counselorExamineReject) {
		return super.findPage(page, counselorExamineReject);
	}

	@Transactional(readOnly = false)
	public void save(CounselorExamineReject counselorExamineReject) {
	super.save(counselorExamineReject);
	}

	@Transactional(readOnly = false)
	public void delete(CounselorExamineReject counselorExamineReject) {
		for (CounselorExamineReject c:counselorExamineReject.getRejectList()) {
			super.delete(counselorExamineReject);
		}
	}

}