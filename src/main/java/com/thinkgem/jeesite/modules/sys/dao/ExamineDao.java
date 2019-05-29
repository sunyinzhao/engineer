/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Examine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ExamineDao extends CrudDao<Examine> {

	public List<Examine> findByParentIdsLike(Examine examine);

	public List<Examine> findByUserId(Examine examine);
	
	public int updateParentIds(Examine examine);
	
	public int updateSort(Examine examine);

	public Examine getName(@Param(value = "name")String name);

    List<Examine> findAllNewExamine(@Param(value="declareRecordId")String declareRecordId);


	//根据  例如:甲综id,以及 类型 查找 到类型的rootid,
	List<Examine> findExamineRootId(@Param(value = "parentId") String examineId,@Param(value = "kind") String kind);

	//查询专家 有颜色的树
    List<Examine> findNewExpertExamine(@Param(value = "declareRecordId") String declareRecordId, @Param(value = "specialtyId") String specialtyId, @Param(value = "kind") String kind);

    List<Examine> outNode();

    List<Examine> findExamineByParentId(@Param(value = "id") String id);
}
