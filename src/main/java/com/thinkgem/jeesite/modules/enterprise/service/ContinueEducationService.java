package com.thinkgem.jeesite.modules.enterprise.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.enterprise.dao.ContinueEducationDao;
import com.thinkgem.jeesite.modules.enterprise.entity.ContinueEducation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 继续教育往年数据Service
 */
@Service
@Transactional(readOnly = true)
public class ContinueEducationService extends CrudService<ContinueEducationDao, ContinueEducation> {
    @Autowired
    private ContinueEducationDao continueEducationDao;


    public ArrayList<String> getAllYear(String id){
        return continueEducationDao.getAllYear(id);
    }

    public ArrayList<String> getAllYearThree(String id,Integer year){


        return continueEducationDao.getAllYearThree(id,year);
    }

}
