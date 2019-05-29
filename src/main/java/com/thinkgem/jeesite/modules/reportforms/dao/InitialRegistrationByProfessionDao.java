package com.thinkgem.jeesite.modules.reportforms.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.InitialRegistrationByProfession;

/**
 * 初始登记按照专业划分申请情况
 * @author Administrator
 *
 */
@MyBatisDao
public interface InitialRegistrationByProfessionDao extends CrudDao<InitialRegistrationByProfession>{

	List<InitialRegistrationByProfession> findInitialRegistrationByProfession(
			InitialRegistrationByProfession initialRegistrationByProfession);

}
