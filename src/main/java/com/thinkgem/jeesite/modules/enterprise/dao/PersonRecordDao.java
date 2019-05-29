/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkersCount;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;

import java.util.List;
import java.util.Map;

/**
 * 人员申请单
 * @author xqg
 * @version 2018-05-05
 */
@MyBatisDao
public interface PersonRecordDao extends CrudDao<PersonRecord> {

	void updateBatchStatus(PersonRecord personRecord);
	void updateBatchStatusByBid(PersonRecord personRecord);

	void updateSecondCexpertId(PersonRecord personRecord);

	void updateFirstCexpertId(PersonRecord personRecord);

	void updateSecondZexpertId(PersonRecord personRecord);
	
	void updateFirstZexpertId(PersonRecord personRecord);

	List<PersonRecord> findByPersonId(String id);

	List<PersonRecord> findById(PersonRecord personRecord);

	List<PersonRecord> findByBatchId(PersonRecord personRecord);

	void updateDeclareStatusNew(PersonRecord personRecord);
	
	void updateDeclareStatusBatch(PersonRecord personRecord);

	void updateReturnReason(PersonRecord personRecord);

	void updateLocalReceiveReturnReason(PersonRecord personRecord);

	void updateIsPunish(PersonRecord personRecord);

	PersonRecord findByPersonId01(String workers_id);
	
	List<PersonRecord> zWaitPublicApplyList(PersonRecord personRecord);

	/**
	 *  根据personId查询出这个工程师最新且有效的申请单
	 * @param personRecord
	 * @return
	 */
	List<PersonRecord> getAvailablePersonReocrd(PersonRecord personRecord);
	/*
	 * 更新退回类型
	 */
	void updateReturnType(PersonRecord personRecord);
	/*
	 * 更新复议专家id
	 */
	void updatefExpertId(PersonRecord personRecord);

	void bdgonggao(Map map);
	void chushigonggao(Map map);
	void dzgonggao(Map map);
	void jxgonggao(Map map);
	void zxgonggao(Map map);

	List<EnterpriseWorkersCount> countForPersonRecord(EnterpriseWorkersCount enterpriseWorkersCount);

}