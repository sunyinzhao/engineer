/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorTableDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业中所有的人员Service
 * @author hzy
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class CounselorTableService extends CrudService<CounselorTableDao, CounselorTable> {


	@Autowired
	private CounselorTableDao counselorTableDao;

	public CounselorTable get(String id) {
		CounselorTable counselorTable = super.get(id);
		return counselorTable;
	}

	public List<CounselorTable> findList(CounselorTable counselorTable) {
		return super.findList(counselorTable);
	}

	public Page<CounselorTable> findPage(Page<CounselorTable> page, CounselorTable counselorTable) {
		return super.findPage(page, counselorTable);
	}

	@Transactional(readOnly = false)
	public void save(CounselorTable counselorTable) {
		super.save(counselorTable);
	}

	@Transactional(readOnly = false)
	public void delete(CounselorTable counselorTable) {
		super.delete(counselorTable);
	}

    public CounselorTable getNew(String recordId,String type) {
		return counselorTableDao.getNew(recordId,type);
    }

    public int getSame(String recordId) {
		return counselorTableDao.getSame(recordId);
    }
}