/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorAttachmentDao;
import com.thinkgem.jeesite.modules.counselor.dao.EducationtblDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.Educationtbl;
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
public class EducationtblService extends CrudService<EducationtblDao, Educationtbl> {


	@Autowired
	private CounselorAttachmentDao counselorAttachmentDao;

	@Autowired
	private EducationtblDao educationtblDao;

	public Educationtbl get(String id) {
		Educationtbl educationtbl = super.get(id);
		return educationtbl;
	}

	public List<Educationtbl> findList(Educationtbl educationtbl) {
		return super.findList(educationtbl);
	}

	public Page<Educationtbl> findPage(Page<Educationtbl> page, Educationtbl educationtbl) {
		return super.findPage(page, educationtbl);
	}

	@Transactional(readOnly = false)
	public void save(Educationtbl educationtbl,String type,String remarks) {
		CounselorAttachment attachment = new CounselorAttachment();
		attachment.setPid(educationtbl.getId());
		attachment.setType("16");
		List<CounselorAttachment> attachList = counselorAttachmentDao.findList(attachment);
		// type为1 添加状态，直接带着id，插入到数据库
		if ("1".equals(type)) {
			educationtblDao.insert(educationtbl);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
		// type为2 编辑状态，实体带着id，save会执行更新
		if ("2".equals(type)){
			super.save(educationtbl);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(Educationtbl educationtbl) {
		super.delete(educationtbl);
	}

	public List<Educationtbl> getByTableId(String tableId) {
		return educationtblDao.getByTableId(tableId);
	}

	public Integer getMax(Educationtbl educationtbl) {
		return educationtblDao.getMax(educationtbl);
	}

	public List<Educationtbl> findNotRelev(String recordId) {
		return educationtblDao.findNotRelev(recordId);
	}

	public Integer getMaxIndex(String personId) {
		return educationtblDao.getMaxIndex(personId);
	}
}