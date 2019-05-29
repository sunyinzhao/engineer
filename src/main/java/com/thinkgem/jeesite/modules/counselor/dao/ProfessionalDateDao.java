package com.thinkgem.jeesite.modules.counselor.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.counselor.entity.ProfessionalDate;

import java.util.ArrayList;

@MyBatisDao
public interface ProfessionalDateDao extends CrudDao<ProfessionalDate> {
    public ArrayList<ProfessionalDate> findListToExport(ProfessionalDate professionalDate);

}
