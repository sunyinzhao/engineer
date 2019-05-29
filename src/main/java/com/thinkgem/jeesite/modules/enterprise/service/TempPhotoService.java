/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.dao.PersonRecordDao;
import com.thinkgem.jeesite.modules.enterprise.dao.TempPhotoDao;
import com.thinkgem.jeesite.modules.enterprise.entity.PersonRecord;
import com.thinkgem.jeesite.modules.enterprise.entity.TempPhoto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 人员申请单Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class TempPhotoService extends CrudService<TempPhotoDao, TempPhoto> {

	@Autowired
	private TempPhotoDao tempPhotoDao;

	public TempPhoto findByCardNum(String certificatesNum) {
		// TODO Auto-generated method stub
		return this.tempPhotoDao.findByCardNum(certificatesNum);
	}

	public Integer findByNameAndCard(String name,String cardNum){
		return this.tempPhotoDao.findByNameAndCard(name,cardNum);
	}

	public Integer findByCardAndYear(String cardNum,Integer year){
		return this.tempPhotoDao.findByCardAndYear(cardNum,year);
	}
}