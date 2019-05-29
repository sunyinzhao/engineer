/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonCompliance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 咨询师不符合原因
 * @author hzy
 * @version 2018-08-23
 */
@MyBatisDao
public interface PersonComplianceDao extends CrudDao<PersonCompliance> {

    List<PersonCompliance> findRemarks(@Param(value = "recordId") String recordId);
}