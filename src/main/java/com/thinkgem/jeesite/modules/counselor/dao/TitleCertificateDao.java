/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.TitleCertificate;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职称证书情况
 * @author hzy
 * @version 2018-08-16
 */
@MyBatisDao
public interface TitleCertificateDao extends CrudDao<TitleCertificate> {


    List<TitleCertificate> getByTableId(String tableId);

    //用于查询dict
    List<Dict> findTitleList(Dict dict);

    Integer getMax(TitleCertificate titleCertificate);

    List<TitleCertificate> findNotRelev(@Param(value = "recordId") String recordId);

    Integer getMaxIndex(@Param(value = "personId") String personId);
}