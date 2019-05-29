/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorAttachmentDao;
import com.thinkgem.jeesite.modules.counselor.dao.TitleCertificateDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.TitleCertificate;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职称证书情况
 * @author hzy
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class TitleCertificateService extends CrudService<TitleCertificateDao,TitleCertificate> {


	@Autowired
	private TitleCertificateDao titleCertificateDao;

	@Autowired
	private CounselorAttachmentDao counselorAttachmentDao;

	public TitleCertificate get(String id) {
		TitleCertificate titleCertificate = super.get(id);
		return titleCertificate;
	}

	public List<TitleCertificate> findList(TitleCertificate titleCertificate) {
		return super.findList(titleCertificate);
	}

	public Page<TitleCertificate> findPage(Page<TitleCertificate> page, TitleCertificate titleCertificate) {
		return super.findPage(page, titleCertificate);
	}

	@Transactional(readOnly = false)
	public void save(TitleCertificate titleCertificate,String type,String remarks) {
		CounselorAttachment attachment = new CounselorAttachment();
		attachment.setPid(titleCertificate.getId());
		attachment.setType("17");
		List<CounselorAttachment> attachList = counselorAttachmentDao.findList(attachment);
		// type为1 添加状态，直接带着id，插入到数据库
		if ("1".equals(type)) {
			titleCertificateDao.insert(titleCertificate);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
		// type为2 编辑状态，实体带着id，save会执行更新
		if ("2".equals(type)){
			super.save(titleCertificate);
			for (CounselorAttachment attachmentAttach : attachList) {
				attachmentAttach.setRemarks(remarks);
				counselorAttachmentDao.update(attachmentAttach);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(TitleCertificate titleCertificate) {
		super.delete(titleCertificate);
	}

	public List<TitleCertificate> getByTableId(String tableId) {
		return titleCertificateDao.getByTableId(tableId);
	}

	public List<Dict> findTitleList(Dict dict) {
		return titleCertificateDao.findTitleList(dict);
	}

	public Integer getMax(TitleCertificate titleCertificate) {
		return titleCertificateDao.getMax(titleCertificate);
	}

	public List<TitleCertificate> findNotRelev(String recordId) {
		return titleCertificateDao.findNotRelev(recordId);
	}

	public Integer getMaxIndex(String personId) {
		return titleCertificateDao.getMaxIndex(personId);
	}
}