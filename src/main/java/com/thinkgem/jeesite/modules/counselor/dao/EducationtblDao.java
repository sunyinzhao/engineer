/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.Educationtbl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学历教育情况
 * @author hzy
 * @version 2018-08-16
 */
@MyBatisDao
public interface EducationtblDao extends CrudDao<Educationtbl> {


    List<Educationtbl> getByTableId(String tableId);

    Integer getMax(Educationtbl educationtbl);

    List<Educationtbl> findNotRelev(@Param(value = "recordId") String recordId);

    Integer getMaxIndex(@Param(value = "personId") String personId);
}