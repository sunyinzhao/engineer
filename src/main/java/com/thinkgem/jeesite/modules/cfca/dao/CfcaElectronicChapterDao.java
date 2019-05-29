/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cfca.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cfca.entity.CfcaElectronicChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 电子签章申请结果DAO接口
 * @author xqg
 * @version 2018-09-03
 */
@MyBatisDao
public interface CfcaElectronicChapterDao extends CrudDao<CfcaElectronicChapter> {

	void updateChapterImage(CfcaElectronicChapter cfcaElectronicChapter);

	List<CfcaElectronicChapter> findSelfList(CfcaElectronicChapter cfcaElectronicChapter);

	/**
	 * 查询出所有未下载证书的记录
	 * @param cfcaElectronicChapter
	 * @return
	 */
	List<CfcaElectronicChapter> findNoDonloadList(CfcaElectronicChapter cfcaElectronicChapter);

	/**
	 * 查询UkeyId 的个数
	 * @param ukeyId
	 * @return
	 */
	Integer getUkeyIdCount(@Param(value = "ukeyId") String ukeyId );

	
}