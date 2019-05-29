/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorTable;
import org.apache.ibatis.annotations.Param;

/**
 * 咨询师table
 * @author hzy
 * @version 2018-08-14
 */
@MyBatisDao
public interface CounselorTableDao extends CrudDao<CounselorTable> {


    CounselorTable getNew(@Param(value = "recordId") String recordId, @Param(value = "type") String type);

    int getSame(@Param(value = "recordId") String recordId);
}