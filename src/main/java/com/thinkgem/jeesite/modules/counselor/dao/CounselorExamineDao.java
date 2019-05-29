/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorExamine;

import java.util.List;

/**
 */
@MyBatisDao
public interface CounselorExamineDao extends CrudDao<CounselorExamine> {

	public List<CounselorExamine> findByParentIdsLike(CounselorExamine counselorExamine);

	public List<CounselorExamine> findByUserId(CounselorExamine counselorExamine);
	
	public int updateParentIds(CounselorExamine counselorExamine);
	
	public int updateSort(CounselorExamine counselorExamine);
	
}
