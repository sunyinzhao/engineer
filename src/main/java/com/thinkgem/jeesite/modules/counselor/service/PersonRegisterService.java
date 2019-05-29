/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.PersonRegisterDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请登记情况
 * @author hzy
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class PersonRegisterService extends CrudService<PersonRegisterDao,PersonRegister> {


	@Autowired
	private PersonRegisterDao personRegisterDao;

	public PersonRegister get(String id) {
		PersonRegister personRegister = super.get(id);
		return personRegister;
	}

	public List<PersonRegister> findList(PersonRegister personRegister) {
		return super.findList(personRegister);
	}

	public Page<PersonRegister> findPage(Page<PersonRegister> page, PersonRegister personRegister) {
		return super.findPage(page, personRegister);
	}

	@Transactional(readOnly = false)
	public void save(PersonRegister personRegister) {
		super.save(personRegister);
	}

	@Transactional(readOnly = false)
	public void delete(PersonRegister personRegister) {
		super.delete(personRegister);
	}

	public PersonRegister getByTableId(String tableId) {
		return personRegisterDao.getByTableId(tableId);

	}
}