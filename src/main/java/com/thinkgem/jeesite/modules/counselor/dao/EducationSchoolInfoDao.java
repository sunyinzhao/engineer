package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.EducationSchoolInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 高校数据
 * @author
 * @version 2019-05-16
 */
@MyBatisDao
public interface EducationSchoolInfoDao extends CrudDao<EducationSchoolInfo> {

    /*学校代码查询- 不重复- 异步*/
    EducationSchoolInfo findListBySchoolCode(String schoolCode);

    /*学历证书验证显示信息*/
    List<EducationSchoolInfo> findCheckEducationalList(EducationSchoolInfo educationSchoolInfo);

}