/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.signature.dao.ApplySignaturePersonDao;

/**
 * 签章咨询师Service
 * @author xqg
 * @version 2018-09-03
 */
@Service
@Transactional(readOnly = true)
public class ApplySignaturePersonService extends CrudService<ApplySignaturePersonDao, ApplySignaturePerson> {

	@Autowired
	private ApplySignaturePersonDao  applySignaturePersonDao;
	
	public ApplySignaturePerson get(String id) {
		return super.get(id);
	}
	
	public List<ApplySignaturePerson> findList(ApplySignaturePerson applySignaturePerson) {
		return super.findList(applySignaturePerson);
	}
	
	public Page<ApplySignaturePerson> findPage(Page<ApplySignaturePerson> page, ApplySignaturePerson applySignaturePerson) {
		return super.findPage(page, applySignaturePerson);
	}
	
	@Transactional(readOnly = false)
	public void save(ApplySignaturePerson applySignaturePerson) {
		super.save(applySignaturePerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(ApplySignaturePerson applySignaturePerson) {
		super.delete(applySignaturePerson);
	}
	
	@Transactional(readOnly = false)
	public void updateDuty(ApplySignaturePerson applySignaturePerson) {
		applySignaturePersonDao.updateDuty(applySignaturePerson);
	}
	
	
	public Page<ApplySignaturePerson> findPageBuySelf(Page<ApplySignaturePerson> page, ApplySignaturePerson applySignaturePerson) {
		applySignaturePerson.setPage(page);
		page.setList(applySignaturePersonDao.findListBySelf(applySignaturePerson));
		return page;
	}
	
	
	/**
	 * 根据Id查询出 实体包含的所有属性（申请单信息）
	 * @param id
	 * @return
	 */
	public ApplySignaturePerson getAllPro(String id){
		return applySignaturePersonDao.getAllPro(id);
	}
	
	/**
	 * 根据用户Id修改签章位置偏移量
	 * @param applySignaturePerson
	 */
	@Transactional(readOnly = false)
	public void updateAbsolute(ApplySignaturePerson applySignaturePerson){
		 applySignaturePersonDao.updateAbsolute(applySignaturePerson);
	}
}