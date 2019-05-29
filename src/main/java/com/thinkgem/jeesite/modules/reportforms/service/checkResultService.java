package com.thinkgem.jeesite.modules.reportforms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportCounselorCountDao;
import com.thinkgem.jeesite.modules.reportforms.dao.checkResultDao;
import com.thinkgem.jeesite.modules.reportforms.entity.InitialRegistrationByProfession;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.changeUnit;
@Service
public class checkResultService extends CrudService<checkResultDao, changeUnit> {

	@Autowired
	checkResultDao checkresultdao;

	public Page<changeUnit> findChangeUnit(Page<changeUnit> page,changeUnit changeUnit){
		changeUnit.setPage(page);
		 List<changeUnit> list = checkresultdao.findChangeUnit(changeUnit);
		 page.setList(list);
		 return page;
	}
	
	public List<changeUnit> exportChangeUnit(changeUnit changeUnit) {
		// TODO Auto-generated method stub
		List<changeUnit>  list=checkresultdao.findChangeUnit(changeUnit);
		return list;
	}
}
