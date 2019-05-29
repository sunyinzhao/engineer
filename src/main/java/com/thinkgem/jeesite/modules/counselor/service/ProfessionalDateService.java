package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.ProfessionalDateDao;
import com.thinkgem.jeesite.modules.counselor.entity.ProfessionalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@Lazy(false)
public class ProfessionalDateService extends CrudService<ProfessionalDateDao, ProfessionalDate> {

    @Autowired
    private ProfessionalDateDao professionalDateDao;

    public Page<ProfessionalDate> findPage(Page<ProfessionalDate> page, ProfessionalDate professionalDate) {
        return super.findPage(page, professionalDate);
    }

    public ArrayList<ProfessionalDate> findListToExport(ProfessionalDate professionalDate){
        return this.professionalDateDao.findListToExport(professionalDate);
    }
}
