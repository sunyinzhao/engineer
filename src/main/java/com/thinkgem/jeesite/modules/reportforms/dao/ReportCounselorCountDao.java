/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;

import java.util.List;

/**
 * 咨询师公示前信息报表统计Dao
 * @version 2019-03-28
 */
@MyBatisDao
public interface ReportCounselorCountDao extends CrudDao<ReportCounselorCount> {


	/*
	 查询某批次  初始登记  的人员信息
	  */
	public List<ReportCounselorCount> findReportCounselorFirstYesInfo(ReportCounselorCount reportCounselorCount);
    public List<ReportCounselorCount> findReportCounselorFirstNoInfo(ReportCounselorCount reportCounselorCount);

	/*
	 * 查询某批次 继续登记 的人员信息
	 */
	List<ReportCounselorCount> findReportCounselorContinueYesInfo(ReportCounselorCount reportCounselorCount);
	List<ReportCounselorCount> findReportCounselorContinueNoInfo(ReportCounselorCount reportCounselorCount);

	/*
	 * 查询某批次 注销登记 的人员信息
	 */
	List<ReportCounselorCount> findReportCounselorCancelYesInfo(ReportCounselorCount reportCounselorCount);
	List<ReportCounselorCount> findReportCounselorCancelNoInfo(ReportCounselorCount reportCounselorCount);

	/*
	 * 查询某批次 变更执业单位 的人员信息
	 */
	List<ReportCounselorCount> findReportCounselorApplyWorkYesInfo(ReportCounselorCount reportCounselorCount);
	List<ReportCounselorCount> findReportCounselorApplyWorkNoInfo(ReportCounselorCount reportCounselorCount);

	/*
	 * 查询某批次 变更专业 的人员信息
	 */
	List<ReportCounselorCount> findReportCounselorSpecialtyYesInfo(ReportCounselorCount reportCounselorCount);
	List<ReportCounselorCount> findReportCounselorSpecialtyNoInfo(ReportCounselorCount reportCounselorCount);

}