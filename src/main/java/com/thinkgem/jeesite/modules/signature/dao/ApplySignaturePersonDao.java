/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;

/**
 * 签章咨询师DAO接口
 * @author xqg
 * @version 2018-09-03
 */
@MyBatisDao
public interface ApplySignaturePersonDao extends CrudDao<ApplySignaturePerson> {
	/**
	 * 根据Id 修改咨询师的职责
	 * @param applySignaturePerson
	 */
	void updateDuty(ApplySignaturePerson applySignaturePerson);
	
	/**
	 * 查询出某一个咨询师参与的项目  
	 * @param applySignaturePerson
	 * @return
	 */
	public List<ApplySignaturePerson> findListBySelf(ApplySignaturePerson applySignaturePerson );
	
	/**
	 * 根据Id查询出 实体包含的所有属性（申请单信息）
	 * @param id
	 * @return
	 */
	public ApplySignaturePerson getAllPro(String id);
	
	
	/**
	 * 根据用户Id修改签章位置偏移量
	 * @param applySignaturePerson
	 */
	void updateAbsolute(ApplySignaturePerson applySignaturePerson);
	
}