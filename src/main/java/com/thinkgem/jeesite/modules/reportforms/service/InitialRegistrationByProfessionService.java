package com.thinkgem.jeesite.modules.reportforms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportforms.dao.InitialRegistrationByProfessionDao;
import com.thinkgem.jeesite.modules.reportforms.entity.InitialRegistrationByProfession;

@Service
@Transactional(readOnly=false)
@Lazy(false)
public class InitialRegistrationByProfessionService extends CrudService<InitialRegistrationByProfessionDao, InitialRegistrationByProfession>{
	
	@Autowired
	private InitialRegistrationByProfessionDao initialRegistrationByProfessionDao;

	/*
	 * 初始登记按照专业划分申请情况
	 */
	public Page<InitialRegistrationByProfession> findInitialRegistrationByProfession(Page<InitialRegistrationByProfession> page,
			InitialRegistrationByProfession initialRegistrationByProfession) {
		// TODO Auto-generated method stub
		initialRegistrationByProfession.setPage(page);
		List<InitialRegistrationByProfession>  list= this.initialRegistrationByProfessionDao.findInitialRegistrationByProfession(initialRegistrationByProfession);
		page.setList(list);
		return page;
	}

	public List<InitialRegistrationByProfession> findInitialRegistrationByProfession(
			InitialRegistrationByProfession initialRegistrationByProfession) {
		// TODO Auto-generated method stub
		List<InitialRegistrationByProfession>  list= this.initialRegistrationByProfessionDao.findInitialRegistrationByProfession(initialRegistrationByProfession);
		return list;
	}

}
