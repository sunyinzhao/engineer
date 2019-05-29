package com.thinkgem.jeesite.modules.reportforms.dao;



import java.util.List;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.changeUnit;
@MyBatisDao
public interface checkResultDao extends CrudDao<changeUnit>{
	
	public List<changeUnit> findChangeUnit(changeUnit changeUnit);
	
	

}
