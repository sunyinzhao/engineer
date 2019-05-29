/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportEnterpriseWorkers;

import java.util.List;

/**
 * 咨询师注册
 * @author xqg
 * @version 2018-05-05
 */
@MyBatisDao
public interface ReportEnterpriseWorkersDao extends CrudDao<ReportEnterpriseWorkers> {
	
	/*
	 * 查询出所有有效咨询师信息
	 */
	public List<ReportEnterpriseWorkers> findEffectiveCounselorInfo(ReportEnterpriseWorkers reportEnterpriseWorkers);
	
	/*
	 * 查询出预审单位上报信息
	 */
	public List<ReportEnterpriseWorkers> findPretrialUnitsReportInfo(ReportEnterpriseWorkers reportEnterpriseWorkers);
	/*
	 *预审单位上报信息汇总
	 */
	public List<ReportEnterpriseWorkers> findUnitsReportInfo(ReportEnterpriseWorkers reportEnterpriseWorkers);
	/*
	 * 登记申请汇总-申请人次
	 */
	public List<ReportEnterpriseWorkers> findApplyPersonTime(ReportEnterpriseWorkers reportEnterpriseWorkers);
	/*
	 * 登记申请汇总-申请人数-材料
	 */
	public List<ReportEnterpriseWorkers> findApplyPersonNum(ReportEnterpriseWorkers reportEnterpriseWorkers);
}