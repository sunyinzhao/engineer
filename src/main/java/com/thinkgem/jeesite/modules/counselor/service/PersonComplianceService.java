/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.PersonComplianceDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonCompliance;
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
public class PersonComplianceService extends CrudService<PersonComplianceDao, PersonCompliance> {


	@Autowired
	private PersonComplianceDao personComplianceDao;

	public PersonCompliance get(String id) {
		PersonCompliance personCompliance = super.get(id);
		return personCompliance;
	}

	public List<PersonCompliance> findList(PersonCompliance personCompliance) {
		return super.findList(personCompliance);
	}

	public Page<PersonCompliance> findPage(Page<PersonCompliance> page, PersonCompliance personCompliance) {
		return super.findPage(page, personCompliance);
	}

	@Transactional(readOnly = false)
	public void save(PersonCompliance personCompliance) {
	super.save(personCompliance);
	}

	@Transactional(readOnly = false)
	public void delete(PersonCompliance personCompliance) {
			super.delete(personCompliance);
	}


	public List<PersonCompliance> findRemark(String recordId) {
		return personComplianceDao.findRemarks(recordId);
	}
}