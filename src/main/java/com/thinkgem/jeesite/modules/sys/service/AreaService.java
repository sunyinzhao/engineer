/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {
	@Autowired
	private AreaDao areaDao;
	

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	public Area getByParam(Area arae){
		return areaDao.getByParam(arae);
	}
	
	/**
	 * 查询出pid以下区域查询出名称一致的
	 * @param arae
	 * @return
	 */
	public Area getAreaByParentIdName(Area arae){
		return areaDao.getAreaByParentIdName(arae);
	}
	
	/**
	 * 查询出所有的根节点
	 * @param arae
	 * @return
	 */
	public List<Area> findParentIdsById(Area arae){
		return areaDao.findParentIdsById( arae);
	}
	
	/**
	 * 1:办公区  2：特殊区  3:家属区
	 * @param arae
	 * @return
	 */
	public String getAreaType(Area arae){
		
		String type = "";
		Area a =this.get(arae);
		if(a.getParentIds().indexOf("0,1,f850941cb6cf4da2b4aeb40f6e5c0a88,")>=0){
			type="1";
		}else if(a.getParentIds().indexOf("0,1,e104e3aa2b294cf4b47ca6a682b6d260,")>=0){
			type="2";
		}else if(a.getParentIds().indexOf("0,1,aaf9f0a3d754464599a4db8b31449700,")>=0){
			type="3";
		}
		return type;
	}
	
}
