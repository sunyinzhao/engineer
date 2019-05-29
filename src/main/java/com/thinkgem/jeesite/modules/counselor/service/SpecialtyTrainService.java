/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorAttachmentDao;
import com.thinkgem.jeesite.modules.counselor.dao.SpecialtyTrainDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业培训
 * @author hzy
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class SpecialtyTrainService extends CrudService<SpecialtyTrainDao,SpecialtyTrain> {


	@Autowired
	private SpecialtyTrainDao specialtyTrainDao;
	
	@Autowired
	private CounselorAttachmentDao counselorAttachmentDao;
	
//	@Autowired
//	private SysAttachmentService sysAttachmentService;

	public SpecialtyTrain get(String id) {
		SpecialtyTrain specialtyTrain = super.get(id);
		return specialtyTrain;
	}

	public List<SpecialtyTrain> findList(SpecialtyTrain specialtyTrain) {
		return super.findList(specialtyTrain);
	}

	public Page<SpecialtyTrain> findPage(Page<SpecialtyTrain> page, SpecialtyTrain specialtyTrain) {
		return super.findPage(page, specialtyTrain);
	}

	@Transactional(readOnly = false)
	public void save(SpecialtyTrain specialtyTrain1,String type,String remarks) {
		
		CounselorAttachment attachment = new CounselorAttachment();
		attachment.setPid(specialtyTrain1.getId());
		attachment.setType("13");
		List<CounselorAttachment> attachList = counselorAttachmentDao.findList(attachment);
		// type为1 添加状态，直接带着id，插入到数据库
		if ("1".equals(type)) {
			specialtyTrainDao.insert(specialtyTrain1);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
		// type为2 编辑状态，实体带着id，save会执行更新
		if ("2".equals(type)){
			super.save(specialtyTrain1);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
		
		
		
		
	}

	@Transactional(readOnly = false)
	public void delete(SpecialtyTrain specialtyTrain) {
		super.delete(specialtyTrain);
	}

	public List<SpecialtyTrain> getByTableId(String tableId) {

		return specialtyTrainDao.getByTableId(tableId);
	}

	public Integer getMax(SpecialtyTrain specialtyTrain) {
		return specialtyTrainDao.getMax(specialtyTrain);
	}

	public List<SpecialtyTrain> findNotRelev(String recordId) {
		return specialtyTrainDao.findNotRelev(recordId);
	}

	public Integer getMaxIndex(String personId) {
		return specialtyTrainDao.getMaxIndex(personId);
	}
}