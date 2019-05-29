package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.UpdateServiceDao;
import com.thinkgem.jeesite.modules.counselor.entity.TitleCertificate;
import com.thinkgem.jeesite.modules.counselor.entity.UpdateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional(readOnly = true)
@Lazy(false)
public class UpdateServiceService extends CrudService<UpdateServiceDao, UpdateService> {

    @Autowired
    private UpdateServiceDao updateServiceDao;

    public Date getDate() {
        return updateServiceDao.getDate();
    }
	@Transactional(readOnly = false)
	public void save(UpdateService updateService) {
		super.save(updateService);
	}
}
