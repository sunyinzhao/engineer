/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorDao;
import com.thinkgem.jeesite.modules.counselor.entity.Counselor;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseAttachmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业中所有的人员Service
 * @author hzy
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class CounselorService extends CrudService<CounselorDao, Counselor> {

	@Autowired
	private EnterpriseAttachmentDao enterpriseAttachmentDao;

	@Autowired
	private CounselorDao counselorDao;

	public Counselor get(String id) {
		Counselor counselor = super.get(id);
		return counselor;
	}

	public List<Counselor> findList(Counselor counselor) {
		return super.findList(counselor);
	}

	public Page<Counselor> findPage(Page<Counselor> page, Counselor counselor) {
		return super.findPage(page, counselor);
	}

	@Transactional(readOnly = false)
	public void save(Counselor counselor) {
		super.save(counselor);
	}

	@Transactional(readOnly = false)
	public void delete(Counselor counselor) {
		super.delete(counselor);
	}

	public int findCheck(Map map) {
		return counselorDao.findCheck(map);
	}
	
	public int findSame(String id) {
		return counselorDao.findSame(id);
	}
	public int getAutoNumber(String batchno,String officecode) {
		return counselorDao.getAutoNumber(batchno,officecode);
	}
	//更新declareDate , 当批次全部点击提交之后,全部改动
	@Transactional(readOnly=false)
	public void updateAutoNumber(String batchno,String officecode) {
		counselorDao.updateAutoNumber(batchno,officecode);
	}
	public Map findByBatchId(String batchId, String status) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Counselor>list=counselorDao.findBatchId(batchId);
		int num = counselorDao.findStatus(batchId,status);
		map.put("list",list);
		map.put("num",num);
		return map;
	}

	//更新declareDate , 当批次全部点击提交之后,全部改动
	@Transactional(readOnly=false)
	public void updateDeclareDate(Counselor counselor) {
		counselorDao.updateDelcareDate(counselor);
	}

	public Map findByBatchId1(String batchId, String status) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Counselor>list=counselorDao.findBatchId(batchId);
		int num = counselorDao.findStatus1(batchId,status);
		map.put("list",list);
		map.put("num",num);
		return map;
	}

	public  List<Counselor> findBatchId(String batchId){
		return counselorDao.findBatchId(batchId);
	}

	public String isFifth(String recordId) {
		return counselorDao.isFifth(recordId);
	}

	public List<Counselor> findListByDate(Date start,Date end,String declareType,String batchNo,String declareStatus) {
		return counselorDao.findListByDate(start,end,declareType,batchNo,declareStatus);
	}

	public List<Counselor> findWorkRecord(String personId) {
		return counselorDao.findWorkRecord(personId);
	}
	

	public Integer getRead(String personId) {
		return counselorDao.getRead(personId);
	}

	public String findBatchIdById(String id){return counselorDao.findBatchIdById(id);}

	@Transactional(readOnly=false)
	public void deleteByBatchId(String batchId){counselorDao.deleteByBatchId(batchId);}
}