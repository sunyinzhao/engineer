/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportReconsiderationStatistics;

import java.util.List;

/**
 * 咨询师复核统计Dao
 * @version 2019-04-12
 */
@MyBatisDao
public interface ReportReconsiderationStatisticsDao extends CrudDao<ReportReconsiderationStatistics> {


	/*
	 查询某批次  初始登记  的人员信息
	  */
	public List<ReportReconsiderationStatistics> findReconsiderationStatisticsFirstInfo(ReportReconsiderationStatistics reportReconsiderationStatistics);

	/*
	 * 查询某批次 继续登记 的人员信息
	 */
	List<ReportReconsiderationStatistics> findReconsiderationStatisticsContinueInfo(ReportReconsiderationStatistics reportReconsiderationStatistics);

	/*
	 * 查询某批次 注销登记 的人员信息
	 */
	List<ReportReconsiderationStatistics> findReconsiderationStatisticsCancelInfo(ReportReconsiderationStatistics reportReconsiderationStatistics);

	/*
	 * 查询某批次 变更执业单位 的人员信息
	 */
	List<ReportReconsiderationStatistics> findReconsiderationStatisticsApplyWorkInfo(ReportReconsiderationStatistics reportReconsiderationStatistics);

	/*
	 * 查询某批次 变更专业 的人员信息
	 */
	List<ReportReconsiderationStatistics> findReconsiderationStatisticsSpecialtyInfo(ReportReconsiderationStatistics reportReconsiderationStatistics);

}