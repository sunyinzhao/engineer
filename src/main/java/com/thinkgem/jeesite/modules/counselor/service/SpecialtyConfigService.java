package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.SpecialtyConfigDao;
import com.thinkgem.jeesite.modules.counselor.entity.SpecialtyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Lazy(false)
public class SpecialtyConfigService extends CrudService<SpecialtyConfigDao, SpecialtyConfig> {

    @Autowired
    private SpecialtyConfigDao specialtyConfigDao;

    public Page<SpecialtyConfig> findPage(Page<SpecialtyConfig> page, SpecialtyConfig specialtyConfig) {
        return super.findPage(page, specialtyConfig);
    }

    @Transactional(readOnly = false)
    public void delete(String id){
        specialtyConfigDao.delete(id);
    }

    public String getSpecialty(String specialty){
        return this.specialtyConfigDao.getSpecialty(specialty);
    }

    public ArrayList<HashMap<String,String>> getCompareDate(String batch,String specialty){
        return this.specialtyConfigDao.getCompareDate(batch,specialty);
    }

    public SpecialtyConfig getSpecialtyByNum(String specialty){
        return this.specialtyConfigDao.getSpecialtyByNum(specialty);
    }

    public HashMap<String,List<String>> getAll(){
       ArrayList<SpecialtyConfig> list =  this.specialtyConfigDao.getAll();
       HashMap<String,List<String>> map = new HashMap<String,List<String>>();
       for(SpecialtyConfig specialtyConfig:list){
           List<String> newList = Arrays.asList(specialtyConfig.getSpecialtyLabel().split("、"));
           if(newList.contains("")){
               newList.remove(newList.indexOf(""));
           }
           map.put(specialtyConfig.getSpecialty(),newList);
       }
       return map;
    }
}
