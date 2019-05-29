
package com.thinkgem.jeesite.modules.reportforms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportDataStatistics;

import java.util.List;

/**
 * Dao
 * @version 2019-05-15
 */
@MyBatisDao
public interface ReportDataStatisticsDao extends CrudDao<ReportDataStatistics> {
	/**
	 终审专家复核后意见对比表
	 */
	public List<ReportDataStatistics> findReportDataStatisticsOpinionLogout(ReportDataStatistics reportDataStatistics);
	public List<ReportDataStatistics> findReportDataStatisticsOpinionInitial(ReportDataStatistics reportDataStatistics);
	public List<ReportDataStatistics> findReportDataStatisticsOpinionWorkunit(ReportDataStatistics reportDataStatistics);
	public List<ReportDataStatistics> findReportDataStatisticsOpinionSpecialty(ReportDataStatistics reportDataStatistics);
	public List<ReportDataStatistics> findReportDataStatisticsOpinionContinue(ReportDataStatistics reportDataStatistics);


}