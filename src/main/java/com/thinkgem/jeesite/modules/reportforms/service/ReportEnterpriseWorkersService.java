/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportforms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.reportforms.dao.ReportEnterpriseWorkersDao;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportEnterpriseWorkers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 报表Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ReportEnterpriseWorkersService extends CrudService<ReportEnterpriseWorkersDao, ReportEnterpriseWorkers> {
	
	@Autowired
	private ReportEnterpriseWorkersDao reportEnterpriseWorkersDao;
	
	@Autowired
	private PersonRecordService personRecordService;
	
	@Autowired
	private ChangeItemService changeItemService;
	
	
	/*
	 * 分页查询出所有有效咨询师信息
	 */
	public Page<ReportEnterpriseWorkers> findEffectiveCounselorInfo(Page<ReportEnterpriseWorkers> page, ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		reportEnterpriseWorkers.setPage(page);
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findEffectiveCounselorInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			//根据enterprise_workers的id查询 取得person_record表的数据 ;条件：type=2 and declare_status ！= 20
			PersonRecord personRecord= this.personRecordService.findByPersonId01(list.get(i).getId());
			if(personRecord !=null){
				//根据person_record的id到change_item中查找type=1
				ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");
				if(changeItem != null){
					list.get(i).setCompanyName(changeItem.getOldValue());
				}
			}
		}
		page.setList(list);
		return page;
	}
	
	
	/*
	 * 导出查询出所有有效咨询师信息
	 * 执业单位：如果changeItem表中存在变更执业单位，则取改变中的oldvalue字段，如果不存在，则取enterprise_worker表中的company_name字段
	 */
	public List<ReportEnterpriseWorkers> findEffectiveCounselorInfo(ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findEffectiveCounselorInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			//根据enterprise_workers的id查询 取得person_record表的数据 ;条件：type=2 and declare_status ！= 20
			PersonRecord personRecord= this.personRecordService.findByPersonId01(list.get(i).getId());
			if(personRecord !=null){
				//根据person_record的id到change_item中查找type=1
				ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");
				if(changeItem != null){
					list.get(i).setCompanyName(changeItem.getOldValue());
				}
			}
		}
		return list;
	}
	
	
	/*
	 * 分页查询预审单位上报信息
	 */
	public Page<ReportEnterpriseWorkers> findPretrialUnitsReportInfo(Page<ReportEnterpriseWorkers> page,ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		reportEnterpriseWorkers.setPage(page);
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findPretrialUnitsReportInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			//根据enterprise_workers的id查询 取得person_record表的数据 ;条件：type=2 and declare_status ！= 20
			PersonRecord personRecord= this.personRecordService.findByPersonId01(list.get(i).getId());
			if(personRecord !=null){
				//根据person_record的id到change_item中查找type=1
				ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");
				list.get(i).setCompanyName(changeItem.getOldValue());
			}
			
			if("0".equals(list.get(i).getCancellationOfRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("×");
				} else {
					list.get(i).setCancellationOfRegistration("");
				}
			}else{
				list.get(i).setCancellationOfRegistration("");
			}
			if("1".equals(list.get(i).getInitialRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("×");
				} else {
					list.get(i).setInitialRegistration("");
				}
			}else{
				list.get(i).setInitialRegistration("");
			}
			if("2".equals(list.get(i).getChangeUnit())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("×");
				} else {
					list.get(i).setChangeUnit("");
				}
			}else{
				list.get(i).setChangeUnit("");
			}
			if("3".equals(list.get(i).getChangeSpecialty())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("×");
				} else {
					list.get(i).setChangeSpecialty("");
				}
			}else{
				list.get(i).setChangeSpecialty("");
			}
			if("4".equals(list.get(i).getContinueRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("×");
				} else {
					list.get(i).setContinueRegistration("");
				}
			}else{
				list.get(i).setContinueRegistration("");
			}
		}
		page.setList(list);
		return page;
	}
	
	/*
	 * 导出预审单位上报信息
	 * 执业单位：如果changeItem表中存在变更执业单位，则取改变中的oldvalue字段，如果不存在，则取enterprise_worker表中的company_name字段
	 */
	public List<ReportEnterpriseWorkers> findPretrialUnitsReportInfo(ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findPretrialUnitsReportInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			//根据enterprise_workers的id查询 取得person_record表的数据 ;条件：type=2 and declare_status ！= 20
			PersonRecord personRecord= this.personRecordService.findByPersonId01(list.get(i).getId());
			if(personRecord !=null){
				//根据person_record的id到change_item中查找type=1
				ChangeItem changeItem = this.changeItemService.getByRecordId(personRecord.getId(), "1");
				list.get(i).setCompanyName(changeItem.getOldValue());
			}
			
			/*if("0".equals(list.get(i).getCancellationOfRegistration())){
				list.get(i).setCancellationOfRegistration("√");
			}else{
				list.get(i).setCancellationOfRegistration("");
			}
			if("1".equals(list.get(i).getInitialRegistration())){
				list.get(i).setInitialRegistration("√");
			}else{
				list.get(i).setInitialRegistration("");
			}
			if("2".equals(list.get(i).getChangeUnit())){
				list.get(i).setChangeUnit("√");
			}else{
				list.get(i).setChangeUnit("");
			}
			if("3".equals(list.get(i).getChangeSpecialty())){
				list.get(i).setChangeSpecialty("√");
			}else{
				list.get(i).setChangeSpecialty("");
			}
			if("4".equals(list.get(i).getContinueRegistration())){
				list.get(i).setContinueRegistration("√");
			}else{
				list.get(i).setContinueRegistration("");
			}*/
			if("0".equals(list.get(i).getCancellationOfRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setCancellationOfRegistration("×");
				} else {
					list.get(i).setCancellationOfRegistration("");
				}
			}else{
				list.get(i).setCancellationOfRegistration("");
			}
			if("1".equals(list.get(i).getInitialRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setInitialRegistration("×");
				} else {
					list.get(i).setInitialRegistration("");
				}
			}else{
				list.get(i).setInitialRegistration("");
			}
			if("2".equals(list.get(i).getChangeUnit())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeUnit("×");
				} else {
					list.get(i).setChangeUnit("");
				}
			}else{
				list.get(i).setChangeUnit("");
			}
			if("3".equals(list.get(i).getChangeSpecialty())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setChangeSpecialty("×");
				} else {
					list.get(i).setChangeSpecialty("");
				}
			}else{
				list.get(i).setChangeSpecialty("");
			}
			if("4".equals(list.get(i).getContinueRegistration())){
				if ("1".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("√");
				} else if ("2".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("√");
				} else if ("3".equals(list.get(i).getFdeclareResult())) {
					list.get(i).setContinueRegistration("×");
				} else {
					list.get(i).setContinueRegistration("");
				}
			}else{
				list.get(i).setContinueRegistration("");
			}
		}
		return list;
	}
	

	/**
	 * 分页查询出预审单位上报信息汇总，返回到前台对应页面
	 * @param page
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public Page<ReportEnterpriseWorkers> findUnitsReportInfo(Page<ReportEnterpriseWorkers> page, ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		reportEnterpriseWorkers.setPage(page);
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findUnitsReportInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			if("0".equals(list.get(i).getChangeSpecialty())){
				list.get(i).setChangeSpecialty("");
			}
			if("0".equals(list.get(i).getChangeUnit())){
				list.get(i).setChangeUnit("");
			}
			if("0".equals(list.get(i).getInitialRegistration())){
				list.get(i).setInitialRegistration("");
			}
			if("0".equals(list.get(i).getContinueRegistration())){
				list.get(i).setContinueRegistration("");
			}
		}
		page.setList(list);
		return page;
	}

	/**
	 * 导出预审单位上报信息汇总数据
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public List<ReportEnterpriseWorkers> findUnitsReportInfo(ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findUnitsReportInfo(reportEnterpriseWorkers);
		for (int i = 0; i < list.size(); i++) {
			if("0".equals(list.get(i).getChangeSpecialty())){
				list.get(i).setChangeSpecialty("");
			}
			if("0".equals(list.get(i).getChangeUnit())){
				list.get(i).setChangeUnit("");
			}
			if("0".equals(list.get(i).getInitialRegistration())){
				list.get(i).setInitialRegistration("");
			}
			if("0".equals(list.get(i).getContinueRegistration())){
				list.get(i).setContinueRegistration("");
			}
		}
		return list;
	}


	/**
	 * 分页查询
	 * 登记申请汇总表-申请人次
	 * @param page
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public Page<ReportEnterpriseWorkers> findApplyPersonTime(Page<ReportEnterpriseWorkers> page,
			ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		reportEnterpriseWorkers.setPage(page);
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findApplyPersonTime(reportEnterpriseWorkers);
		page.setList(list);
		return page;
	}

	/**
	 * 导出
	 * 登记申请汇总表-申请人次
	 * @param page
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public List<ReportEnterpriseWorkers> findApplyPersonTime(ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findApplyPersonTime(reportEnterpriseWorkers);
		return list;
	}


	/**
	 * 分页查询
	 * 登记申请汇总表-申请人数-材料
	 * @param page
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public Page<ReportEnterpriseWorkers> findApplyPersonNum(Page<ReportEnterpriseWorkers> page,
			ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		reportEnterpriseWorkers.setPage(page);
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findApplyPersonNum(reportEnterpriseWorkers);
		page.setList(list);
		return page;
	}


	/**
	 * 导出
	 * 登记申请汇总表-申请人数-材料
	 * @param page
	 * @param reportEnterpriseWorkers
	 * @return
	 */
	public List<ReportEnterpriseWorkers> findApplyPersonNum(ReportEnterpriseWorkers reportEnterpriseWorkers) {
		// TODO Auto-generated method stub
		List<ReportEnterpriseWorkers> list = this.reportEnterpriseWorkersDao.findApplyPersonNum(reportEnterpriseWorkers);
		return list;
	}
	
}