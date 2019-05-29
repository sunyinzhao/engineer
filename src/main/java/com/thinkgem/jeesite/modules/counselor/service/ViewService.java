/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.ViewDao;
import com.thinkgem.jeesite.modules.counselor.entity.ReturnPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查看列表
 * @author hzy
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ViewService extends CrudService<ViewDao, ReturnPojo> {


	@Autowired
	private ViewDao viewDao;

	public List<ReturnPojo> findList(ReturnPojo returnPojo) {
		return super.findList(returnPojo);
	}

	public Page<ReturnPojo> findPage(Page<ReturnPojo> page, ReturnPojo returnPojo) {
		return super.findPage(page, returnPojo);
	}
}