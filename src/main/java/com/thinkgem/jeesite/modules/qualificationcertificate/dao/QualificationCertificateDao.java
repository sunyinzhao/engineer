/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.qualificationcertificate.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.qualificationcertificate.entity.QualificationCertificate;

/**
 * 执业资格证书DAO接口
 * @author xqg
 * @version 2018-11-04
 */
@MyBatisDao
public interface QualificationCertificateDao extends CrudDao<QualificationCertificate> {
	
}