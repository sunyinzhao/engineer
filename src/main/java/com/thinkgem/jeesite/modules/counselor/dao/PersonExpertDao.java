/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.PersonExpert;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author hzy
 * @version 2018-09-05
 */
@MyBatisDao
public interface PersonExpertDao extends CrudDao<PersonExpert> {

    List<PersonExpert> findFailList(HashMap<String, Object> map);

    List<PersonExpert> findFailList1(HashMap<String, Object> map);

    List<PersonExpert> findOtherList(HashMap<String, Object> map);

    int findSame(@Param(value = "recordId") String recordId, @Param(value = "examineType") String type);

    List<PersonExpert> findSameList();

    void deleteList(@Param(value = "allResult")List allResult);

    List<PersonExpert> findNewList(PersonExpert personExpert);

    List<PersonExpert> findNewSameList();

    List<PersonExpert> findNotRelev(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev35(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev36(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev37(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev38(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev39(@Param(value = "type") int type);

    List<PersonExpert> findNotRelev40(@Param(value = "type") int type);

    //根据不真实的所有项,查询这个批次下,所有的这些type,进行修改item
    List<PersonExpert> findNotReal(@Param(value = "batchId") String batchId,@Param(value = "id") String id);

    List<PersonExpert> findNot99List(@Param(value = "recordId") String recordId);
}