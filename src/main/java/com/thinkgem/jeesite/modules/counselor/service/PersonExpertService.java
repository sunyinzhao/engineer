/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.PersonExpertDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonExpert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 预审终审选项
 * @author hzy
 * @version 2018-08-23
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class PersonExpertService extends CrudService<PersonExpertDao, PersonExpert> {


	@Autowired
	private PersonExpertDao personExpertDao;

	public PersonExpert get(String id) {
		PersonExpert personExpert = super.get(id);
		return personExpert;
	}

	public List<PersonExpert> findList(PersonExpert personExpert) {
		return super.findList(personExpert);
	}

	public Page<PersonExpert> findPage(Page<PersonExpert> page, PersonExpert personExpert) {
		return super.findPage(page, personExpert);
	}

	@Transactional(readOnly = false)
	public void save(PersonExpert personExpert) {
	super.save(personExpert);
	}

	@Transactional(readOnly = false)
	public void delete(PersonExpert personExpert) {
			super.delete(personExpert);
	}

	public List<PersonExpert> findFailList(HashMap<String, Object> map) {
		return personExpertDao.findFailList(map);

	}

	public List<PersonExpert> findFailList1(HashMap<String, Object> map) {
		return personExpertDao.findFailList1(map);

	}

	public List<PersonExpert> findOtherList(HashMap<String, Object> map) {
		return personExpertDao.findOtherList(map);
	}

	public int findSame(String recordId, String type) {
		return personExpertDao.findSame(recordId,type);
	}

	@Transactional(readOnly = false)
	public void deleteList(ArrayList<String> allResult) {
		personExpertDao.deleteList(allResult);
	}

	public List<PersonExpert> findSameList() {
		return personExpertDao.findSameList();

	}

	public List<PersonExpert> findNewSameList() {
		return personExpertDao.findNewSameList();

	}

	public List<PersonExpert> findNewList(PersonExpert personExpert) {
		return personExpertDao.findNewList(personExpert);
	}

	public List<PersonExpert> findNotRelev(int type) {
		return personExpertDao.findNotRelev(type);
	}

	public List<PersonExpert> findNotRelev35(int type) {
		return personExpertDao.findNotRelev35(type);
	}

	public List<PersonExpert> findNotRelev36(int type) {
		return personExpertDao.findNotRelev36(type);
	}

	public List<PersonExpert> findNotRelev37(int type) {
		return personExpertDao.findNotRelev37(type);
	}

	public List<PersonExpert> findNotRelev38(int type) {
		return personExpertDao.findNotRelev38(type);
	}

	public List<PersonExpert> findNotRelev39(int type) {
		return personExpertDao.findNotRelev39(type);
	}
	public List<PersonExpert> findNotRelev40(int type) {
		return personExpertDao.findNotRelev40(type);
	}

	//根据不真实的所有项,查询这个批次下,所有的这些type,进行修改item
	public List<PersonExpert> findNotReal(String batchId, String id) {
		return personExpertDao.findNotReal(batchId,id);
	}

	public List<PersonExpert> findNot99List(String recordId) {

		return personExpertDao.findNot99List(recordId);
	}
}