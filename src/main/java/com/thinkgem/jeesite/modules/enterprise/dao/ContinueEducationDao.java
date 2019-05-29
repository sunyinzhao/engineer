package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.ContinueEducation;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * 继续教育往年数据Dao
 */
@MyBatisDao
public interface ContinueEducationDao extends CrudDao<ContinueEducation> {

    public ArrayList<String> getAllYear(String id);

    public ArrayList<String> getAllYearThree(@Param(value = "id") String id,@Param(value = "year") Integer year);
}
