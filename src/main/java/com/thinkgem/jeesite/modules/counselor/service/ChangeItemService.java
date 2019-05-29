/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.ChangeItemDao;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 变更履历
 * @author hzy
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ChangeItemService extends CrudService<ChangeItemDao, ChangeItem> {


	@Autowired
	private ChangeItemDao changeItemDao;

	public ChangeItem get(String id) {
		ChangeItem changeItem = super.get(id);
		return changeItem;
	}

	public List<ChangeItem> findList(ChangeItem changeItem) {
		return super.findList(changeItem);
	}

	public Page<ChangeItem> findPage(Page<ChangeItem> page, ChangeItem changeItem) {
		return super.findPage(page, changeItem);
	}

	@Transactional(readOnly = false)
	public void save(ChangeItem changeItem) {
	super.save(changeItem);
	}

	@Transactional(readOnly = false)
	public void delete(ChangeItem changeItem) {
			super.delete(changeItem);
	}
	
	@Transactional(readOnly = false)
	public void updateIsAssigned(ChangeItem changeItem) {
		changeItemDao.updateIsAssigned(changeItem);
	}

	public ChangeItem getByRecordId(String recordId, String changeType) {
		return changeItemDao.getByRecordId(recordId,changeType);
	}

	public List<ChangeItem> findItemList(HashMap<String, Object> map) {
		return changeItemDao.findItemList(map);
	}
}