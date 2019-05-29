/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.counselor.service.ChangeItemService;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseAttachmentDao;
import com.thinkgem.jeesite.modules.enterprise.dao.EnterpriseWorkersDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseAttachment;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 企业中所有的人员Service
 * @author xqg
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
@Lazy(false)
public class EnterpriseWorkersService extends CrudService<EnterpriseWorkersDao, EnterpriseWorkers> {

	@Autowired
	private EnterpriseAttachmentDao enterpriseAttachmentDao;

	@Autowired
	private EnterpriseWorkersDao enterpriseWorkersDao;
	
	@Autowired
	private PersonRecordService personRecordService;
	
	@Autowired
	private ChangeItemService changeItemService;

	public EnterpriseWorkers get(String id) {
		EnterpriseWorkers enterpriseWorkers = super.get(id);
		EnterpriseAttachment attachment =new EnterpriseAttachment();
		attachment.setPid(id);

		enterpriseWorkers.setEnterpriseAttachmentList(enterpriseAttachmentDao.findAllList(attachment)); //根据人员Id获取 附件
		return enterpriseWorkers;
	}
	
	public EnterpriseWorkers getById(String id){
		return super.get(id);
	}


	public List<EnterpriseWorkers> findList(EnterpriseWorkers enterpriseWorkers) {
		return super.findList(enterpriseWorkers);
	}

	public Page<EnterpriseWorkers> findPage(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		page.setPageSize(10);
		return super.findPage(page, enterpriseWorkers);
	}

	@Transactional(readOnly = false)
	public void save(EnterpriseWorkers enterpriseWorkers) {
		super.save(enterpriseWorkers);
		for (EnterpriseAttachment enterpriseAttachment : enterpriseWorkers.getEnterpriseAttachmentList()){
			if (enterpriseAttachment.getId() == null){
				continue;
			}
			if (EnterpriseAttachment.DEL_FLAG_NORMAL.equals(enterpriseAttachment.getDelFlag())){
				if (StringUtils.isBlank(enterpriseAttachment.getId())){
					enterpriseAttachment.setPid(enterpriseWorkers.getId());
					enterpriseAttachment.preInsert();
					enterpriseAttachmentDao.insert(enterpriseAttachment);
				}else{
					enterpriseAttachment.preUpdate();
					enterpriseAttachmentDao.update(enterpriseAttachment);
				}
			}else{
				enterpriseAttachmentDao.delete(enterpriseAttachment);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(EnterpriseWorkers enterpriseWorkers) {
		super.delete(enterpriseWorkers);
		//删除附件
		EnterpriseAttachment attachment =new EnterpriseAttachment();
		attachment.setPid(enterpriseWorkers.getId());
		enterpriseAttachmentDao.delete(attachment);
	}

//	@Scheduled(cron = "* 0/1 * * * ?")
//	@Transactional(readOnly = false)
//	public void birthdayReminder() {
		//update enterprise_workers w, sys_user u set w.pid = u.id where u.name = w.company_name and u.user_type = '7'
		//enterpriseWorkersDao.updateCompany();
//	}


	public Integer findMaxNo(String pid){
		return enterpriseWorkersDao.findMaxNo(pid);
	}

	public List<EnterpriseWorkers> findListByAttachment(String pid, String type,String declareRecordId,String retire) {
		return enterpriseWorkersDao.findListByAttachment(pid,type,declareRecordId,retire);
	}
	
	public List<EnterpriseWorkers> checkCardNumberAdd(EnterpriseWorkers enterprise) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> enterpriseWorkers = dao.checkCardNumberAdd(enterprise);
		return enterpriseWorkers;
	}

	
	public List<EnterpriseWorkers> checkCardNumberUpdate(EnterpriseWorkers enterprise) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> enterpriseWorkers = dao.checkCardNumberUpdate(enterprise);
		return enterpriseWorkers;
	}

	public List<EnterpriseWorkers> findOrder(String pid, String declareRecordId) {
		return enterpriseWorkersDao.findOrder(pid,declareRecordId);
	}
	//查询专业为ppp的人员列表
    public List<EnterpriseWorkers> findListBySpecialty(String pid, String speciatltyType ,String declareRecordId) {
		return enterpriseWorkersDao.findListBySpecialty(pid,speciatltyType,declareRecordId );
    }
    
    /**
     * 根据申请单查询出技术力量中被选中人员的信息 
     * @param declareRecordId
     * @return
     */
    public List<EnterpriseWorkers> findListByDeclareId( String declareRecordId) {
		return enterpriseWorkersDao.findListByDeclareId(declareRecordId);
    }

/*
    public List<EnterpriseWorkers> findNewList(String specialtyId,String num,String pid) {
    	//num 进行分类
		//1.根据单位ID从works表里面取人（只取工程师，works表里type是1的）
		//2.根据单位ID从works表里面取人（只取工程师，works表里type是1的，并且是退休的）
		//3.根据单位ID从works表里面取人（只取工程师，works表里type是2或3的）
		//4.根据单位ID从works表里面取人（只取工程师，works表里type是1、2或3的）退休状态的
		//5.根据单位ID从works表里面取人（只取工程师，works表里type是3的，并且专业是40的）
		if(num.equals("1")){
				return enterpriseWorkersDao.findNewList1(pid,specialtyId);
		}else if(num.equals("2")){
			return enterpriseWorkersDao.findNewList2(pid,specialtyId);
		}else if(num.equals("3")){
			return enterpriseWorkersDao.findNewList3(pid,specialtyId);
		}else if(num.equals("4")){
			return enterpriseWorkersDao.findNewList4(pid,specialtyId);
		}else if(num.equals("5")){
			return enterpriseWorkersDao.findNewList5(pid,specialtyId);
		}else if(num.equals("6")){
			return enterpriseWorkersDao.findNewList6(pid);
		}
		return null;
    }*/

	public List<EnterpriseWorkers> findNewList3(String declareRecordId, String specialtyId) {
		return enterpriseWorkersDao.findNewList3(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList4(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList4(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList6(String userId) {
        return enterpriseWorkersDao.findNewList6(userId);
	}

	public List<EnterpriseWorkers> findNewList7(String userId) {
        return enterpriseWorkersDao.findNewList7(userId);
	}

	public List<EnterpriseWorkers> findNewList8(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList8(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList9(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList9(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList10(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList10(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList11(String userId) {
        return enterpriseWorkersDao.findNewList11(userId);
	}

	public List<EnterpriseWorkers> findNewList12(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList12(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList13(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList13(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList14(String userId) {
        return enterpriseWorkersDao.findNewList14(userId);
	}

	public List<EnterpriseWorkers> findNewList16(String declareRecordId, String specialtyId) {
        return enterpriseWorkersDao.findNewList16(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList17(String declareRecordId) {

		return enterpriseWorkersDao.findNewList17(declareRecordId);
	}

    public List<EnterpriseWorkers> findNewList18(String declareRecordId) {
		return enterpriseWorkersDao.findNewList18(declareRecordId);
    }
    
   /**
    * 查询出咨询师可以当高工的人员信息
    * @param declareRecordId
    * @return
    */
    public List<EnterpriseWorkers> findPersonApplyCount(String declareRecordId) {
		return enterpriseWorkersDao.findPersonApplyCount(declareRecordId);
    }

	public List<EnterpriseWorkers> findNewList21(String declareRecordId, String specialtyId) {
		return enterpriseWorkersDao.findNewList21(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList22(String declareRecordId, String specialtyId) {
		return enterpriseWorkersDao.findNewList22(declareRecordId,specialtyId);
	}

	public List<EnterpriseWorkers> findNewList23(String userId) {
		return enterpriseWorkersDao.findNewList23(userId);
	}
	
	
	public EnterpriseWorkers getByTablePerson(String id, String personId) {
		return enterpriseWorkersDao.getByTablePerson(id,personId);
	}
	
	public EnterpriseWorkers getWorkerAndOfficeCode(String id) {
		return enterpriseWorkersDao.getWorkerAndOfficeCode(id);
	}
	
	public EnterpriseWorkers getMobileByRecordId(String id) {
		return enterpriseWorkersDao.getMobileByRecordId(id);
	}
	
	public EnterpriseWorkers getByPersonId(String personId) {
		return enterpriseWorkersDao.getByPersonId(personId);
	}
	
	public EnterpriseWorkers findByCertificatesNum(EnterpriseWorkers enterprise) {
		// TODO Auto-generated method stub
		EnterpriseWorkers enterpriseWorkers = this.enterpriseWorkersDao.findByCertificatesNum(enterprise);
		return enterpriseWorkers;
	}
	public EnterpriseWorkers getTempPhoto(EnterpriseWorkers enterprise) 
	{
		EnterpriseWorkers enterpriseWorkers = this.enterpriseWorkersDao.getTempPhoto(enterprise);
		return enterpriseWorkers;
	}

	@Transactional(readOnly=false)
	public void savaCounselorToUser(User user) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.savaCounselorToUser(user);
	}
	@Transactional(readOnly=false)
	public void updateIsRegister(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateIsRegister(enterpriseWorks);
	}

	public EnterpriseWorkers findPerInfo(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		EnterpriseWorkers enterpriseWorker = this.enterpriseWorkersDao.findPerInfo(enterpriseWorkers);
		return enterpriseWorker;
	}
	@Transactional(readOnly=false)
	public void updateZXS(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateZXS(enterpriseWorks);
	}
	@Transactional(readOnly=false)
	public void insertZXS(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.insertZXS(enterpriseWorks);
	}

	@Transactional(readOnly=false)
	public void updateZXSInfo(EnterpriseWorkers enterpriseWorker) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateZXSInfo(enterpriseWorker);
	}

	public Integer findName(String name) {
		// TODO Auto-generated method stub
		Integer count = this.enterpriseWorkersDao.findName(name);
		return count;
	}

	@Transactional(readOnly=false)
	public void updateZXSInfo01(EnterpriseWorkers enterpriseWorker) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateZXSInfo01(enterpriseWorker);
	}

	@Transactional(readOnly=false)
	public void updateZXSInfo02(EnterpriseWorkers enterpriseWorker) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateZXSInfo02(enterpriseWorker);
	}
	@Transactional(readOnly=false)
	public void updateForAdmin(EnterpriseWorkers enterpriseWorker) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateForAdmin(enterpriseWorker);
	}
	

	public EnterpriseWorkers getNew(String id) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.getNew(id);
	}

	public Page<EnterpriseWorkers> findPageList(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> findPageList01(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList01(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> findPageList02(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList02(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> findPageList03(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList03(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> findPageList031(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList031(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> findPageList032(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList032(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> findPageList033(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList033(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> findPageList034(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList034(enterpriseWorkers));
		return page;
	}
	
	public Page<EnterpriseWorkers> findPageList035(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList035(enterpriseWorkers));
		return page;
	}

	public List<User> checkMobile(User user2) {
		// TODO Auto-generated method stub
		List<User> user = this.enterpriseWorkersDao.checkMobile(user2);
		return user;
	}

	public EnterpriseWorkers getAddEmail(String personId) {
		return enterpriseWorkersDao.getAddEmail(personId);
	}


	//通过name 与 cardNumber 查找到这个人员
	public EnterpriseWorkers getByNameNum(String name, String cardNumber) {
		return enterpriseWorkersDao.getByNameNum(name,cardNumber);
	}

	public Page<EnterpriseWorkers> findPageList04(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList04(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> findPageList05(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList05(enterpriseWorkers));
		return page;
	}

	/**
	 * 中咨协会待分配与已分配申请单数据查询
	 * @param page
	 * @param enterpriseWorkers
	 * @return
	 */
	public Page<EnterpriseWorkers> zzDistributeExpertList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		List<EnterpriseWorkers> zzDistributeExpertList = this.enterpriseWorkersDao.zzDistributeExpertList(enterpriseWorkers);
		page.setList(zzDistributeExpertList);
		return page;
	}
	
	/**
	 * 地方协会待分配与已分配申请单数据查询
	 * @param page
	 * @param enterpriseWorkers
	 * @return
	 */
	public Page<EnterpriseWorkers> localDistributeExpertList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		List<EnterpriseWorkers> localDistributeExpertList = this.enterpriseWorkersDao.localDistributeExpertList(enterpriseWorkers);
		page.setList(localDistributeExpertList);
		return page;
	}
	
	public List<EnterpriseWorkers> localdistributeExpertList1(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> localdistributeExpertList = this.enterpriseWorkersDao.localdistributeExpertList1(enterpriseWorkers);
		return localdistributeExpertList;
	}

	public List<EnterpriseWorkers> localdistributeExpertList2(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> localdistributeExpertList = this.enterpriseWorkersDao.localdistributeExpertList2(enterpriseWorkers);
		return localdistributeExpertList;
	}

	@Transactional(readOnly = false)
	public void updatePictureUrl(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updatePictureUrl(enterpriseWorkers);
	}

	public EnterpriseWorkers getPicture(String id) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.getPicture(id);
	}

	@Transactional(readOnly = false)
	public void deletePicture(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.deletePicture(enterpriseWorkers);
	}

	@Transactional(readOnly = false)
	public void updateCompanyInfo(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateCompanyInfo(enterpriseWorkers);
	}

	@Transactional(readOnly = false)
	public void updateIsRegister1(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateIsRegister1(enterpriseWorkers);
	}

	public List<EnterpriseWorkers> findIsValid(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> list = enterpriseWorkersDao.findIsValidList(enterpriseWorkers);
		return list;
	}

	public Page<EnterpriseWorkers> findPageList005(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList005(enterpriseWorkers));
		return page;
	}
	
	public Page<EnterpriseWorkers> findPageList0051(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList0051(enterpriseWorkers));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void updateisValid(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateisValid(enterpriseWorkers);
	}

	public List<EnterpriseWorkers> checkCertificatesNum(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> list = this.enterpriseWorkersDao.checkCertificatesNum(enterpriseWorkers);
		return list;
	}
	
	public List<EnterpriseWorkers> checkCertificatesNumisRegister(EnterpriseWorkers enterpriseWorkers){
		// TODO Auto-generated method stub
		List<EnterpriseWorkers> list = this.enterpriseWorkersDao.checkCertificatesNumisRegister(enterpriseWorkers);
		return list;
	}

	public EnterpriseWorkers findByCertificatesNumAndName(EnterpriseWorkers enterpriseWorker) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findByCertificatesNumAndName(enterpriseWorker);
	}

	public List<User> findSysUserName1(User user) {
		return enterpriseWorkersDao.findSysUserName1(user);
	}
	/**
	 * 得到所有“已上报的申请单”
	 * @param enterpriseWorkers //查询出某一企业的
	 * @return
	 */
	public List<EnterpriseWorkers> findPageList02All( EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findPageList02(enterpriseWorkers);
	}

	public Page<EnterpriseWorkers> findCounselorList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findCounselorList(enterpriseWorkers));
		return page;
	}
	public List<EnterpriseWorkers> findCounselorList(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		return enterpriseWorkersDao.findCounselorList(enterpriseWorkers);
	}
	
	public Page<EnterpriseWorkers> findCounselorInList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findCounselorInList(enterpriseWorkers));
		return page;
	}
	
	public Page<EnterpriseWorkers> findCounselorOutList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findCounselorOutList(enterpriseWorkers));
		return page;
	}

	public String getOfficeName(String companyName) {
		return enterpriseWorkersDao.getOfficeName(companyName);
	}

	public Page<EnterpriseWorkers> expertSuggestionCheckList(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.expertSuggestionCheckList(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> expertSuggestionCheckList1(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.expertSuggestionCheckList1(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> punishList(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.punlishList(enterpriseWorkers));
		return page;
	}

	public EnterpriseWorkers findByNameAndCertificatesNum(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findByNameAndCertificatesNum(enterpriseWorks);
	}

	public EnterpriseWorkers findEnterpriseWorkers(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findEnterpriseWorkers(enterpriseWorks);
	}

	@Transactional(readOnly = false)
	public void updateEnterpriseWorkers(EnterpriseWorkers enterpriseWorks) {
		// TODO Auto-generated method stub
		this.enterpriseWorkersDao.updateEnterpriseWorkers(enterpriseWorks);
	}

	public Page<EnterpriseWorkers> findPageList06(Page<EnterpriseWorkers> page, EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList06(enterpriseWorkers));
		return page;
	}

	public List<EnterpriseWorkers> findPageList04All(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findPageList04(enterpriseWorkers);
	}

	public Page<EnterpriseWorkers> findApplicationBills(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findApplicationBills(enterpriseWorkers));
		return page;
	}
	public Page<EnterpriseWorkers> findApplicationBillsClose(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findApplicationBillsClose(enterpriseWorkers));
		return page;
	}
	
	public Page<EnterpriseWorkers> findPageListByAllPass(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		page.setPageSize(1000);
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList031(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> findPageListByAllPass2(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		page.setPageSize(1000);
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList032(enterpriseWorkers));
		return page;
	}
	
	public Page<EnterpriseWorkers> findPageListByAllPass1(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		page.setPageSize(1000);
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.findPageList03(enterpriseWorkers));
		return page;
	}

	public List<EnterpriseWorkers> findApplicationBills(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.findApplicationBills(enterpriseWorkers);
	}


	/**
	 * 有效的，并且显示出证书的状态
	 * @param enterpriseWorkers
	 * @return
	 */
	public  Page<EnterpriseWorkers>  availableWorkersList( Page<EnterpriseWorkers> page,EnterpriseWorkers enterpriseWorkers) {

		enterpriseWorkers.setPage(page);
		page.setList(enterpriseWorkersDao.availableWorkersList(enterpriseWorkers));
		return page;

	}

	public Page<EnterpriseWorkers> expertSuggestionCheckListPage(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		page.setPageSize(1000);
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.expertSuggestionCheckList(enterpriseWorkers));
		return page;
	}

	public Page<EnterpriseWorkers> reconsiderDistributeExpert(Page<EnterpriseWorkers> page,
			EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		enterpriseWorkers.setPage(page);
		page.setList(this.enterpriseWorkersDao.reconsiderDistributeExpert(enterpriseWorkers));
		return page;
	}

	public List<EnterpriseWorkers> reconsiderDistributeExpert(EnterpriseWorkers enterpriseWorkers) {
		// TODO Auto-generated method stub
		return this.enterpriseWorkersDao.reconsiderDistributeExpert(enterpriseWorkers);
	}



	/**
	 * 更新证书状态
	 * @param enterpriseWorks
	 */
	@Transactional(readOnly = false)
	public void updateZhengShuFlag(EnterpriseWorkers enterpriseWorks){
		enterpriseWorkersDao.updateZhengShuFlag(enterpriseWorks);
	}

	public List<EnterpriseWorkers> findUpdateService(Date resultDate) {
		return enterpriseWorkersDao.findUpdateService(resultDate);
	}
}