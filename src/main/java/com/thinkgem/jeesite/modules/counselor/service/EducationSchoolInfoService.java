package com.thinkgem.jeesite.modules.counselor.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.counselor.dao.CounselorAttachmentDao;
import com.thinkgem.jeesite.modules.counselor.dao.EducationSchoolInfoDao;
import com.thinkgem.jeesite.modules.counselor.entity.CounselorAttachment;
import com.thinkgem.jeesite.modules.counselor.entity.EducationSchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service
 * @author
 * @version
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class EducationSchoolInfoService extends CrudService<EducationSchoolInfoDao, EducationSchoolInfo> {


	@Autowired
	private EducationSchoolInfoDao educationSchoolInfoDao;

	public EducationSchoolInfo get(String id) {
		EducationSchoolInfo educationSchoolInfo = super.get(id);
		return educationSchoolInfo;
	}

	public List<EducationSchoolInfo> findList(EducationSchoolInfo educationSchoolInfo) {
		return super.findList(educationSchoolInfo);
	}

	public Page<EducationSchoolInfo> findPage(Page<EducationSchoolInfo> page, EducationSchoolInfo educationSchoolInfo) {
		return super.findPage(page, educationSchoolInfo);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 */
//	public Page<EducationSchoolInfo> getPageList(Page<EducationSchoolInfo> page, List<EducationSchoolInfo> list) {
//		if (list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).setPage(page);
//			}
//		}
//		page.setList(list);
//		return page;
//	}

	@Transactional(readOnly = false)
	public void save(EducationSchoolInfo educationSchoolInfo) {
		super.save(educationSchoolInfo);
	}

	@Transactional(readOnly = false)
	public void delete(EducationSchoolInfo educationSchoolInfo) {
		super.delete(educationSchoolInfo);
	}
	/** 学校代码查询 */
	public EducationSchoolInfo findListBySchoolCode(String schoolCode) {
		return educationSchoolInfoDao.findListBySchoolCode(schoolCode);
	}
	/**学历证书验证显示信息*/
	public List<EducationSchoolInfo> findCheckEducationalList (EducationSchoolInfo educationSchoolInfo){
		return educationSchoolInfoDao.findCheckEducationalList(educationSchoolInfo);
	}

}