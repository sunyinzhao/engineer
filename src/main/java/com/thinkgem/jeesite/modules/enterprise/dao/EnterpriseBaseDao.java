/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseBase;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 基本数据DAO接口
 * @author xqg
 * @version 2018-05-05
 */
@MyBatisDao
public interface EnterpriseBaseDao extends CrudDao<EnterpriseBase> {

	List<User> checkOrganizationCode(User user);
	
    EnterpriseBase findEBase(@Param(value = "userid")String userid);

}