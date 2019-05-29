/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.certificate.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.certificate.entity.EngineerCertificate;
import org.apache.ibatis.annotations.Param;

/**
 * 咨询工程师证书DAO接口
 * @author xqg
 * @version 2018-11-29
 */
@MyBatisDao
public interface EngineerCertificateDao extends CrudDao<EngineerCertificate> {

    public EngineerCertificate getByWorkerId(@Param(value = "workerId")String workerId);

}