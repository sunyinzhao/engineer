/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonRegister;
import org.apache.ibatis.annotations.Param;

/**
 * 申请登记情况
 * @author hzy
 * @version 2018-08-16
 */
@MyBatisDao
public interface PersonRegisterDao extends CrudDao<PersonRegister> {


    PersonRegister getByTableId(@Param(value = "tableId") String tableId);
}