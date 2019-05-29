/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseProject;

/**
 * 企业完成项目DAO接口
 * @author xqg
 * @version 2018-05-08
 */
@MyBatisDao
public interface EnterpriseProjectDao extends CrudDao<EnterpriseProject> {
	
}