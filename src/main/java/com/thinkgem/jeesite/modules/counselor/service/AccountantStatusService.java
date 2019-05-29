/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.AccountantStatusDao;
import com.thinkgem.jeesite.modules.counselor.entity.AccountantStatus;
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
public class AccountantStatusService extends CrudService<AccountantStatusDao, AccountantStatus> {


	@Autowired
	private AccountantStatusDao accountantStatusDao;

	public AccountantStatus get(String id) {
		AccountantStatus accountantStatus = super.get(id);
		return accountantStatus;
	}

	public List<AccountantStatus> findList(AccountantStatus accountantStatus) {
		return super.findList(accountantStatus);
	}

	public Page<AccountantStatus> findPage(Page<AccountantStatus> page, AccountantStatus accountantStatus) {
		return super.findPage(page, accountantStatus);
	}

	@Transactional(readOnly = false)
	public void save(AccountantStatus accountantStatus) {
	super.save(accountantStatus);
	}

	@Transactional(readOnly = false)
	public void delete(AccountantStatus accountantStatus) {
			super.delete(accountantStatus);
	}

    public AccountantStatus getByTableId(String tableId) {
        return accountantStatusDao.getByTableId(tableId);
    }
}