/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseCertificate;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseCertificateDao;

/**
 * 企业获得证书Service
 * @author xqg
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseCertificateService extends CrudService<EnterpriseCertificateDao, EnterpriseCertificate> {

	public EnterpriseCertificate get(String id) {
		return super.get(id);
	}
	
	public List<EnterpriseCertificate> findList(EnterpriseCertificate enterpriseCertificate) {
		return super.findList(enterpriseCertificate);
	}
	
	public Page<EnterpriseCertificate> findPage(Page<EnterpriseCertificate> page, EnterpriseCertificate enterpriseCertificate) {
		return super.findPage(page, enterpriseCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseCertificate enterpriseCertificate) {
		super.save(enterpriseCertificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseCertificate enterpriseCertificate) {
		super.delete(enterpriseCertificate);
	}
	
}