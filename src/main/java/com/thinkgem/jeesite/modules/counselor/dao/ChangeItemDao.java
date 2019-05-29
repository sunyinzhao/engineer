/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.ChangeItem;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 变更履历
 * @author hzy
 * @version 2018-09-11
 */
@MyBatisDao
public interface ChangeItemDao extends CrudDao<ChangeItem> {

    ChangeItem getByRecordId(@Param(value = "recordId") String recordId, @Param(value = "changeType") String changeType);

    List<ChangeItem> findItemList(HashMap<String, Object> map);
    
    void updateIsAssigned(ChangeItem changeItem);
}