/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorAttachmentDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 咨询师附件Service
 * @author hzy
 * @version 2018-08-27
 */
@Service
@Transactional(readOnly = true)
public class CounselorAttachmentService extends CrudService<CounselorAttachmentDao, CounselorAttachment> {
	@Autowired
	private CounselorAttachmentDao counselorAttachmentDao;

	
	public CounselorAttachment get(String id) {
		return super.get(id);
	}
	
	public List<CounselorAttachment> findList(CounselorAttachment counselorAttachment) {
		return super.findList(counselorAttachment);
	}

	public List<CounselorAttachment> findListByTypeAndPid(CounselorAttachment counselorAttachment) {
		return counselorAttachmentDao.findListByTypeAndPid(counselorAttachment);
	}

	public Page<CounselorAttachment> findPage(Page<CounselorAttachment> page, CounselorAttachment counselorAttachment) {
		return super.findPage(page, counselorAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(CounselorAttachment counselorAttachment) {
		super.save(counselorAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(CounselorAttachment counselorAttachment) {
		super.delete(counselorAttachment);
	}

	//新的查找方式
    public List<CounselorAttachment> findNewList(String personId, String tableType, String tableId) {
		return counselorAttachmentDao.findNewList(personId,tableType,tableId);
    }

	public List<CounselorAttachment> findAllAttach(String personId, String type) {
		return counselorAttachmentDao.findAllAttach(personId,type);
	}


	public List<CounselorAttachment> findListByType(HashMap<String, Object> map) {

		return counselorAttachmentDao.findListByType(map);
	}
	// 职称信息附件
	public List<CounselorAttachment> findListByTypeMap(HashMap<String, Object> map) {

		return counselorAttachmentDao.findListByTypeMap(map);
	}
	// 删除图片
	@Transactional(readOnly = false)
	public void deletePicture(CounselorAttachment counselorAttachment) {

		this.counselorAttachmentDao.deletePicture(counselorAttachment);
	}
}