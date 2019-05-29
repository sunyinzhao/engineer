/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.JobKnowledge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专业培训
 * @author hzy
 * @version 2018-08-20
 */
@MyBatisDao
public interface JobKnowledgeDao extends CrudDao<JobKnowledge> {

    List<JobKnowledge> findByTableId(@Param(value = "tableId") String tableId);

    JobKnowledge findLast(@Param(value = "personId") String personId);

    JobKnowledge findFirst(@Param(value = "personId") String personId);
}