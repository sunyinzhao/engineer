/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.qualificationcertificate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.qualificationcertificate.entity.QualificationCertificate;
import com.thinkgem.jeesite.modules.qualificationcertificate.dao.QualificationCertificateDao;

/**
 * 执业资格证书Service
 * @author xqg
 * @version 2018-11-04
 */
@Service
@Transactional(readOnly = true)
public class QualificationCertificateService extends CrudService<QualificationCertificateDao, QualificationCertificate> {

	public QualificationCertificate get(String id) {
		return super.get(id);
	}
	
	public List<QualificationCertificate> findList(QualificationCertificate qualificationCertificate) {
		return super.findList(qualificationCertificate);
	}
	
	public Page<QualificationCertificate> findPage(Page<QualificationCertificate> page, QualificationCertificate qualificationCertificate) {
		return super.findPage(page, qualificationCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(QualificationCertificate qualificationCertificate) {
		super.save(qualificationCertificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualificationCertificate qualificationCertificate) {
		super.delete(qualificationCertificate);
	}
	
}