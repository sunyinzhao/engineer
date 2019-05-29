/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseChangeInfo;

/**
 * 非咨变更DAO接口
 * @author xqg
 * @version 2018-05-28
 */
@MyBatisDao
public interface EnterpriseChangeInfoDao extends CrudDao<EnterpriseChangeInfo> {
	
}