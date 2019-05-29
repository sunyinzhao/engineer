/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.Counselor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 咨询师
 * @author hzy
 * @version 2018-08-14
 */
@MyBatisDao
public interface CounselorDao extends CrudDao<Counselor> {

    /*
    * 判断是否可以再次选择类型
    * */
    int findCheck(Map map);

    int findSame(@Param(value = "personId") String id);

    List<Counselor> findBatchId(@Param(value = "batchId") String batchId);

    int findStatus(@Param(value = "batchId") String batchId, @Param(value = "status") String status);

    void updateDelcareDate(Counselor counselor);

    int findStatus1(@Param(value = "batchId") String batchId, @Param(value = "status") String status);

    String isFifth(@Param(value = "recordId") String recordId);
    
    int getAutoNumber(@Param(value = "batchNo") String batchNo, @Param(value = "officeCode") String officeCode);
    
    void updateAutoNumber(@Param(value = "batchNo") String batchNo, @Param(value = "officeCode") String officeCode);

    List<Counselor> findListByDate(@Param(value = "startDate") Date start,@Param(value = "endDate") Date end,@Param(value = "declareType") String declareType,@Param(value = "batchNo") String batchNo,@Param(value = "declareStatus") String declareStatus);

    List<Counselor> findWorkRecord(@Param(value = "personId") String personId);

    Integer getRead(@Param(value = "personId") String personId);

    String findBatchIdById(String id);

    void deleteByBatchId(String batchId);

}