package com.thinkgem.jeesite.modules.reportforms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportCounselorCountDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportCounselorCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 咨询师公示前信息报表统计Service
 * @version 2019-03-28
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ReportCounselorCountService extends CrudService<ReportCounselorCountDao, ReportCounselorCount> {
	
	@Autowired
	private ReportCounselorCountDao reportCounselorCountDao;
	


	/*
	 * 查询某批次 初始登记 的人员信息
	 */
    public List<ReportCounselorCount> findReportCounselorFirstYesInfo(ReportCounselorCount reportCounselorCount) {
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorFirstYesInfo(reportCounselorCount);
        return list;
    }
    public List<ReportCounselorCount> findReportCounselorFirstNoInfo(ReportCounselorCount reportCounselorCount) {
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorFirstNoInfo(reportCounselorCount);
        return list;
    }

	public Page<ReportCounselorCount> findReportCounselorFirstYesInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
		reportCounselorCount.setPage(page);
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorFirstYesInfo(reportCounselorCount);
		page.setList(list);
		return page;
	}
    public Page<ReportCounselorCount> findReportCounselorFirstNoInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorFirstNoInfo(reportCounselorCount);
        page.setList(list);
	    return page;
    }

	/*
	 * 查询某批次 继续登记 的人员信息
	 */
	public List<ReportCounselorCount> findReportCounselorContinueYesInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorContinueYesInfo(reportCounselorCount);
		return list;
	}
	public List<ReportCounselorCount> findReportCounselorContinueNoInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorContinueNoInfo(reportCounselorCount);
		return list;
	}

    public Page<ReportCounselorCount> findReportCounselorContinueYesInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorContinueYesInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }
    public Page<ReportCounselorCount> findReportCounselorContinueNoInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorContinueNoInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }

	/*
	 * 查询某批次 注销登记 的人员信息
	 */
	public List<ReportCounselorCount> findReportCounselorCancelYesInfo(ReportCounselorCount reportCounselorCount) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
//		reportCounselorCount.getSqlMap().put("dsf", dataScopeFilter(reportCounselorCount.getCurrentUser(), "o", "a"));
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorCancelYesInfo(reportCounselorCount);
		return list;
	}
	public List<ReportCounselorCount> findReportCounselorCancelNoInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorCancelNoInfo(reportCounselorCount);
		return list;
	}

    public Page<ReportCounselorCount> findReportCounselorCancelYesInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorCancelYesInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }
    public Page<ReportCounselorCount> findReportCounselorCancelNoInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorCancelNoInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }

	/*
	 * 查询某批次 变更执业单位 的人员信息
	 */
	public List<ReportCounselorCount> findReportCounselorApplyWorkYesInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorApplyWorkYesInfo(reportCounselorCount);
		return list;
	}
	public List<ReportCounselorCount> findReportCounselorApplyWorkNoInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorApplyWorkNoInfo(reportCounselorCount);
		return list;
	}

    public Page<ReportCounselorCount> findReportCounselorApplyWorkYesInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorApplyWorkYesInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }
    public Page<ReportCounselorCount> findReportCounselorApplyWorkNoInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorApplyWorkNoInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }

	/*
	 * 查询某批次 变更专业 的人员信息
	 */
	public List<ReportCounselorCount> findReportCounselorSpecialtyYesInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorSpecialtyYesInfo(reportCounselorCount);
		return list;
	}
	public List<ReportCounselorCount> findReportCounselorSpecialtyNoInfo(ReportCounselorCount reportCounselorCount) {
		List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorSpecialtyNoInfo(reportCounselorCount);
		return list;
	}

    public Page<ReportCounselorCount> findReportCounselorSpecialtyYesInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorSpecialtyYesInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }
    public Page<ReportCounselorCount> findReportCounselorSpecialtyNoInfo(Page<ReportCounselorCount> page,ReportCounselorCount reportCounselorCount) {
        reportCounselorCount.setPage(page);
        List<ReportCounselorCount> list = reportCounselorCountDao.findReportCounselorSpecialtyNoInfo(reportCounselorCount);
        page.setList(list);
        return page;
    }

}