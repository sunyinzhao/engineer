/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
	public Area getByParam(Area arae);
	public Area getAreaByParentIdName(Area arae);
	/**
	 * 查询出所有的根节点
	 * @param arae
	 * @return
	 */
	public List<Area> findParentIdsById(Area arae);
	
	
	
}
