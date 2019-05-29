/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.FinalExpertReviewCount;

import java.util.List;

/**
 * 终审专家评审情况
 */
@MyBatisDao
public interface FinalExpertReviewCountDao extends CrudDao<FinalExpertReviewCount> {

	/* 专家评审 */
	List<FinalExpertReviewCount> countByExpertReview1 (FinalExpertReviewCount finalExpertReviewCount);
	/* 专家复核 */
	List<FinalExpertReviewCount> countByExpertReview2 (FinalExpertReviewCount finalExpertReviewCount);

}