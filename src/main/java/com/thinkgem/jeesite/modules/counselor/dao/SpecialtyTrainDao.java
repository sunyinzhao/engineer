/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyTrain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专业培训
 * @author hzy
 * @version 2018-08-16
 */
@MyBatisDao
public interface SpecialtyTrainDao extends CrudDao<SpecialtyTrain> {


    List<SpecialtyTrain> getByTableId(String tableId);

    Integer getMax(SpecialtyTrain specialtyTrain);

    List<SpecialtyTrain> findNotRelev(@Param(value = "recordId") String recordId);

    Integer getMaxIndex(@Param(value = "personId") String personId);
}