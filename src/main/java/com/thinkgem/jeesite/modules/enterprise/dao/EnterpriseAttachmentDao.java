/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 附件DAO接口
 * @author xqg
 * @version 2018-05-09
 */
@MyBatisDao
public interface EnterpriseAttachmentDao extends CrudDao<EnterpriseAttachment> {

    List<EnterpriseAttachment> findJpgList(@Param("pid") String declareRecordId, @Param(value = "type") String type);
	
    /**
     * 根据附件类型查询附件 高级职称证书
     * @param enterpriseAttachment
     * @return
     */
    List<EnterpriseAttachment> findZhengShuList(EnterpriseAttachment enterpriseAttachment);

    //根据type进行排序
    List<EnterpriseAttachment> findListBytype(EnterpriseAttachment enterpriseAttachment);

    List<EnterpriseAttachment> findMixJpg(@Param(value = "pid") String declareRecordId);

    List<EnterpriseAttachment> findPPPJpg(@Param(value = "pid") String declareRecordId);

    List<EnterpriseAttachment> findType1(@Param(value = "pid")String id);
    List<EnterpriseAttachment> findType2(@Param(value = "pid")String id);
    List<EnterpriseAttachment> findType3(@Param(value = "pid")String id);
    List<EnterpriseAttachment> findType4(@Param(value = "pid")String id);


    List<EnterpriseAttachment> findType5(@Param(value = "pid")String id);
}