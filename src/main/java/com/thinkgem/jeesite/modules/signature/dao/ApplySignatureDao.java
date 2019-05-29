/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;

/**
 * 电子签章业绩DAO接口
 * @author xqg
 * @version 2019-03-19
 */
@MyBatisDao
public interface ApplySignatureDao extends CrudDao<ApplySignature> {

    public  void updateLock(ApplySignature applySignature);
    
    /**项目名称不重复**/
    int checkProjectName(ApplySignature applySignature);
    
    List<T> findListPid(CounselorAttachment counselorAttachment);

	public int deleteattach(CounselorAttachment counselorAttachment);
	/**更改签章状态**/
	List<ApplySignaturePerson> selectsignature(String projectName);
	int updatesignature(ApplySignaturePerson applySignaturePerson);

}