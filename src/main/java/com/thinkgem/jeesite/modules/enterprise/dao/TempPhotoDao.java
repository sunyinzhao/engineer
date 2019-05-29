/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.TempPhoto;
import org.apache.ibatis.annotations.Param;

/**
 * 人员申请单
 * @author xqg
 * @version 2018-05-05
 */
@MyBatisDao
public interface TempPhotoDao extends CrudDao<TempPhoto> {

	TempPhoto findByCardNum(String cardNum);

	Integer findByNameAndCard(@Param(value = "name") String name,@Param(value = "cardNum") String cardNum);

	public Integer findByCardAndYear(@Param(value="cardNum") String cardNum,@Param(value="year") Integer year);

}