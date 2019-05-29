/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.certificate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.certificate.entity.EngineerCertificate;
import com.thinkgem.jeesite.modules.certificate.dao.EngineerCertificateDao;

/**
 * 咨询工程师证书Service
 * @author xqg
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly = true)
public class EngineerCertificateService extends CrudService<EngineerCertificateDao, EngineerCertificate> {

    @Autowired
    EngineerCertificateDao  engineerCertificateDao;
	public EngineerCertificate get(String id) {
		return super.get(id);
	}
	
	public List<EngineerCertificate> findList(EngineerCertificate engineerCertificate) {
		return super.findList(engineerCertificate);
	}
	
	public Page<EngineerCertificate> findPage(Page<EngineerCertificate> page, EngineerCertificate engineerCertificate) {
		return super.findPage(page, engineerCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(EngineerCertificate engineerCertificate) {
		super.save(engineerCertificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(EngineerCertificate engineerCertificate) {
		super.delete(engineerCertificate);
	}

	public EngineerCertificate getByWorkerId(String workerId) {
		return engineerCertificateDao.getByWorkerId(workerId);

	}
	
}