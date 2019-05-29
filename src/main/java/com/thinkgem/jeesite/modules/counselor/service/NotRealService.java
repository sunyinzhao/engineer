/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.NotRealDao;
import com.thinkgem.jeesite.modules.counselor.entity.NotReal;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 不符合列表Service
 * @author hzy
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class NotRealService extends CrudService<NotRealDao, NotReal> {

	public NotReal get(String id) {
		NotReal notReal = super.get(id);
		return notReal;
	}

	public List<NotReal> findList(NotReal notReal) {
		return super.findList(notReal);
	}

	public Page<NotReal> findPage(Page<NotReal> page, NotReal notReal) {
		return super.findPage(page, notReal);
	}


}