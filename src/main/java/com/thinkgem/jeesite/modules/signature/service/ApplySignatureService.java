/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.signature.service;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignature;
import com.thinkgem.jeesite.modules.signature.entity.ApplySignaturePerson;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.signature.dao.ApplySignatureDao;

/**
 * 电子签章业绩Service
 * @author xqg
 * @version 2018-09-03
 */
@Service
@Transactional(readOnly = true)
public class ApplySignatureService extends CrudService<ApplySignatureDao, ApplySignature> {
	@Autowired
	private ApplySignatureDao applySignatureDao;

	public ApplySignature get(String id) {
		return super.get(id);
	}
	
	public List<ApplySignature> findList(ApplySignature applySignature) {
		return super.findList(applySignature);
	}
	
	public Page<ApplySignature> findPage(Page<ApplySignature> page, ApplySignature applySignature) {
		return super.findPage(page, applySignature);
	}
	
	@Transactional(readOnly = false)
	public void save(ApplySignature applySignature) {
		super.save(applySignature);
	}
	
	@Transactional(readOnly = false)
	public void delete(ApplySignature applySignature) {
		super.delete(applySignature);
	}
	@Transactional(readOnly = false)
	public void cancellation(ApplySignature applySignature) {
	//TODO	super.cancellation(applySignature);
	}

    /**
     *  更新签章人锁定状态
     * @param applySignature
     */
    @Transactional(readOnly = false)
    public  void updateLock(ApplySignature applySignature){
        applySignatureDao.updateLock(applySignature);
    }
    /**
     *  项目名称不重复
     * @param checkProjectName
     */
	public int checkProjectName(ApplySignature applySignature) {
		return applySignatureDao.checkProjectName(applySignature);
	}
	

	
	public List<T> findListPid(CounselorAttachment counselorAttachment) {
		return applySignatureDao.findListPid(counselorAttachment);
	}
	
	@Transactional(readOnly = false)
	public void deleteattach(CounselorAttachment counselorAttachment) {
		applySignatureDao.deleteattach(counselorAttachment);
	}
	/**
     *  更改签章状态
     * @param selectsignature
     */
	public List<ApplySignaturePerson> selectsignature(String projectName) {
		return applySignatureDao.selectsignature(projectName);
	}
	@Transactional(readOnly = false)
	public int updatesignature(ApplySignaturePerson applySignaturePerson) {
		return applySignatureDao.updatesignature(applySignaturePerson);
	}
	


	
}