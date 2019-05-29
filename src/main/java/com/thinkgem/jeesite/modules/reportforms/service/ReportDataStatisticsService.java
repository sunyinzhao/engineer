package com.thinkgem.jeesite.modules.reportforms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportDataStatisticsDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportDataStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 复核信息对比Service
 * @version 2019-05-15
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ReportDataStatisticsService extends CrudService<ReportDataStatisticsDao, ReportDataStatistics> {

	@Autowired
	private ReportDataStatisticsDao reportDataStatisticsDao;


    /**
     终审专家复核后意见对比表
     */
    
    
    public List<ReportDataStatistics> findReportDataStatisticsOpinionLogout(ReportDataStatistics reportDataStatistics) {
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionLogout(reportDataStatistics);
        return list;
    }
    public Page<ReportDataStatistics> findReportDataStatisticsOpinionLogout(Page<ReportDataStatistics> page,ReportDataStatistics reportDataStatistics) {
        reportDataStatistics.setPage(page);
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionLogout(reportDataStatistics);
        page.setList(list);
        return page;
    }
    
    public List<ReportDataStatistics> findReportDataStatisticsOpinionInitial(ReportDataStatistics reportDataStatistics) {
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionInitial(reportDataStatistics);
        return list;
    }
    public Page<ReportDataStatistics> findReportDataStatisticsOpinionInitial(Page<ReportDataStatistics> page,ReportDataStatistics reportDataStatistics) {
        reportDataStatistics.setPage(page);
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionInitial(reportDataStatistics);
        page.setList(list);
        return page;
    }


    public List<ReportDataStatistics> findReportDataStatisticsOpinionWorkunit(ReportDataStatistics reportDataStatistics) {
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionWorkunit(reportDataStatistics);
        return list;
    }
    public Page<ReportDataStatistics> findReportDataStatisticsOpinionWorkunit(Page<ReportDataStatistics> page,ReportDataStatistics reportDataStatistics) {
        reportDataStatistics.setPage(page);
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionWorkunit(reportDataStatistics);
        page.setList(list);
        return page;
    }


    public List<ReportDataStatistics> findReportDataStatisticsOpinionSpecialty(ReportDataStatistics reportDataStatistics) {
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionSpecialty(reportDataStatistics);
        return list;
    }
    public Page<ReportDataStatistics> findReportDataStatisticsOpinionSpecialty(Page<ReportDataStatistics> page,ReportDataStatistics reportDataStatistics) {
        reportDataStatistics.setPage(page);
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionSpecialty(reportDataStatistics);
        page.setList(list);
        return page;
    }


    public List<ReportDataStatistics> findReportDataStatisticsOpinionContinue(ReportDataStatistics reportDataStatistics) {
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionContinue(reportDataStatistics);
        return list;
    }
    public Page<ReportDataStatistics> findReportDataStatisticsOpinionContinue(Page<ReportDataStatistics> page,ReportDataStatistics reportDataStatistics) {
        reportDataStatistics.setPage(page);
        List<ReportDataStatistics> list = reportDataStatisticsDao.findReportDataStatisticsOpinionContinue(reportDataStatistics);
        page.setList(list);
        return page;
    }

    
    
	

}