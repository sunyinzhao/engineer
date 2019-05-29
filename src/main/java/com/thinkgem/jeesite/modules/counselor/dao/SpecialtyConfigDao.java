package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyConfig;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

@MyBatisDao
public interface SpecialtyConfigDao extends CrudDao<SpecialtyConfig> {
    public SpecialtyConfig get(String id);
    public int update(SpecialtyConfig specialtyConfig);
    public int delete(String id);
    public int insert(SpecialtyConfig specialtyConfig);
    public String getSpecialty(String specialty);
    public ArrayList<HashMap<String,String>> getCompareDate(@Param(value="batch")String batch,@Param(value="specialty") String specialty);
    public SpecialtyConfig getSpecialtyByNum(String specialty);
    public ArrayList<SpecialtyConfig> getAll();
}
