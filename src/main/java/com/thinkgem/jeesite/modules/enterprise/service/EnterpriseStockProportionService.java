/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseStockProportion;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseStockProportionDao;

/**
 * 股权结构Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
public class EnterpriseStockProportionService extends CrudService<EnterpriseStockProportionDao, EnterpriseStockProportion> {

	public EnterpriseStockProportion get(String id) {
		return super.get(id);
	}
	
	public List<EnterpriseStockProportion> findList(EnterpriseStockProportion enterpriseStockProportion) {
		return super.findList(enterpriseStockProportion);
	}
	
	public Page<EnterpriseStockProportion> findPage(Page<EnterpriseStockProportion> page, EnterpriseStockProportion enterpriseStockProportion) {
		return super.findPage(page, enterpriseStockProportion);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterpriseStockProportion enterpriseStockProportion) {
		super.save(enterpriseStockProportion);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterpriseStockProportion enterpriseStockProportion) {
		super.delete(enterpriseStockProportion);
	}
	
}