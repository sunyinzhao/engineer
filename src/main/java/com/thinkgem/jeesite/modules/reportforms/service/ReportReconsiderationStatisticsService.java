package com.thinkgem.jeesite.modules.reportforms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportReconsiderationStatisticsDao;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportReconsiderationStatisticsDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportReconsiderationStatistics;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportReconsiderationStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 咨询师复核统计Service
 * @version 2019-04-12
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ReportReconsiderationStatisticsService extends CrudService<ReportReconsiderationStatisticsDao, ReportReconsiderationStatistics> {
	
	@Autowired
	private ReportReconsiderationStatisticsDao reportReconsiderationStatisticsDao;



	/*
	 * 查询某批次 初始登记 的人员信息
	 */

    public List<ReportReconsiderationStatistics> findReconsiderationStatisticsFirstInfo(ReportReconsiderationStatistics reportReconsiderationStatistics) {
        List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsFirstInfo(reportReconsiderationStatistics);
        return list;
    }

	public Page<ReportReconsiderationStatistics> findReconsiderationStatisticsFirstInfo(Page<ReportReconsiderationStatistics> page,ReportReconsiderationStatistics reportReconsiderationStatistics) {
        reportReconsiderationStatistics.setPage(page);
		List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsFirstInfo(reportReconsiderationStatistics);
		page.setList(list);
		return page;
	}


	/*
	 * 查询某批次 继续登记 的人员信息
	 */
	public List<ReportReconsiderationStatistics> findReconsiderationStatisticsContinueInfo(ReportReconsiderationStatistics reportReconsiderationStatistics) {
		List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsContinueInfo(reportReconsiderationStatistics);
		return list;
	}
	
    public Page<ReportReconsiderationStatistics> findReconsiderationStatisticsContinueInfo(Page<ReportReconsiderationStatistics> page,ReportReconsiderationStatistics reportReconsiderationStatistics) {
        reportReconsiderationStatistics.setPage(page);
        List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsContinueInfo(reportReconsiderationStatistics);
        page.setList(list);
        return page;
    }

	/*
	 * 查询某批次 注销登记 的人员信息
	 */
	
	public List<ReportReconsiderationStatistics> findReconsiderationStatisticsCancelInfo(ReportReconsiderationStatistics reportReconsiderationStatistics) {
		List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsCancelInfo(reportReconsiderationStatistics);
		return list;
	}

    public Page<ReportReconsiderationStatistics> findReconsiderationStatisticsCancelInfo(Page<ReportReconsiderationStatistics> page,ReportReconsiderationStatistics reportReconsiderationStatistics) {
        reportReconsiderationStatistics.setPage(page);
        List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsCancelInfo(reportReconsiderationStatistics);
        page.setList(list);
        return page;
    }
    
	/*
	 * 查询某批次 变更执业单位 的人员信息
	 */
	public List<ReportReconsiderationStatistics> findReconsiderationStatisticsApplyWorkInfo(ReportReconsiderationStatistics reportReconsiderationStatistics) {
		List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsApplyWorkInfo(reportReconsiderationStatistics);
		return list;
	}
    public Page<ReportReconsiderationStatistics> findReconsiderationStatisticsApplyWorkInfo(Page<ReportReconsiderationStatistics> page,ReportReconsiderationStatistics reportReconsiderationStatistics) {
        reportReconsiderationStatistics.setPage(page);
        List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsApplyWorkInfo(reportReconsiderationStatistics);
        page.setList(list);
        return page;
    }

	/*
	 * 查询某批次 变更专业 的人员信息
	 */
	public List<ReportReconsiderationStatistics> findReconsiderationStatisticsSpecialtyInfo(ReportReconsiderationStatistics reportReconsiderationStatistics) {
		List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsSpecialtyInfo(reportReconsiderationStatistics);
		return list;
	}
    public Page<ReportReconsiderationStatistics> findReconsiderationStatisticsSpecialtyInfo(Page<ReportReconsiderationStatistics> page,ReportReconsiderationStatistics reportReconsiderationStatistics) {
        reportReconsiderationStatistics.setPage(page);
        List<ReportReconsiderationStatistics> list = reportReconsiderationStatisticsDao.findReconsiderationStatisticsSpecialtyInfo(reportReconsiderationStatistics);
        page.setList(list);
        return page;
    }

}