/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 咨询师附件DAO接口
 * @author hzy
 * @version 2018-08-27
 */
@MyBatisDao
public interface CounselorAttachmentDao extends CrudDao<CounselorAttachment> {

    List<CounselorAttachment> findNewList(@Param(value = "personId") String personId, @Param(value = "tableType") String tableType, @Param(value = "tableId") String tableId);

    List<CounselorAttachment> findAllAttach(@Param(value = "personId") String personId, @Param(value = "type") String type);

    List<CounselorAttachment> findListByType(HashMap<String, Object> map);

    List<CounselorAttachment> findListByTypeMap(HashMap<String, Object> map); // 职称信息附件查询


    List<CounselorAttachment> findJpgList(@Param("pid") String declareRecordId, @Param(value = "type") String type);


    List<CounselorAttachment> findZhengShuList(CounselorAttachment enterpriseAttachment);


    List<CounselorAttachment> findListByTypeAndPid(CounselorAttachment enterpriseAttachment);

    //根据type进行排序
    List<CounselorAttachment> findListBytype(CounselorAttachment enterpriseAttachment);

    List<CounselorAttachment> findMixJpg(@Param(value = "pid") String declareRecordId);

    List<CounselorAttachment> findPPPJpg(@Param(value = "pid") String declareRecordId);

    List<CounselorAttachment> findType1(@Param(value = "pid")String id);
    List<CounselorAttachment> findType2(@Param(value = "pid")String id);
    List<CounselorAttachment> findType3(@Param(value = "pid")String id);
    List<CounselorAttachment> findType4(@Param(value = "pid")String id);
    List<CounselorAttachment> findType5(@Param(value = "pid")String id);


//    List<CounselorAttachment> findSysAttachment(@Param(value = "declareRecordId") String declareRecordId ,@Param(value = "type") String type);
    List<CounselorAttachment> findSysAttachmentBySelectType(@Param(value = "pid") String pid ,@Param(value = "type") String type);

    public void deletePicture(CounselorAttachment counselorAttachment);
}